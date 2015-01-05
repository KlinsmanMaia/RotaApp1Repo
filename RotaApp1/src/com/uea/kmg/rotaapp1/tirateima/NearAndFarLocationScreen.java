package com.uea.kmg.rotaapp1.tirateima;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uea.kmg.rotaapp1.R;
import com.uea.kmg.rotaapp1.dao.DataBaseManager;
import com.uea.kmg.rotaapp1.model.FavoriteLocation;

public class NearAndFarLocationScreen extends Activity {
	Button btShowNearLocation;
	Button btShowFarLocation;
	Button btBack;
	EditText radiusDistanceValue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_near_and_far_location_screen);
		btShowNearLocation = (Button) this.findViewById(R.id.btJsonShowNearLocation);
		btShowFarLocation = (Button) this.findViewById(R.id.btJsonShowFarLocation);
		btBack = (Button) this.findViewById(R.id.btJsonBack);
		radiusDistanceValue = (EditText) this.findViewById(R.id.etJsonSizeRadius);
		
		deleteAllData();
		runJSONFile();
		
		btShowNearLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String nearLocation = getNearLocationByCurrentLocation();
					Toast.makeText(getApplicationContext(), nearLocation, Toast.LENGTH_LONG).show();
					} catch (NumberFormatException nfe){
						Toast.makeText(getApplicationContext(), "Insira um valor numérico válido", Toast.LENGTH_LONG).show();
						nfe.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		});
		
		btShowFarLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					String nearLocation = getFarLocationByCurrentLocation();
					Toast.makeText(getApplicationContext(), nearLocation, Toast.LENGTH_LONG).show();
					} catch (NumberFormatException nfe){
						Toast.makeText(getApplicationContext(), "Insira um valor numérico válido", Toast.LENGTH_LONG).show();
						nfe.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}	
		});
		
		btBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void deleteAllData() {
		DataBaseManager dataManager = new DataBaseManager(getApplicationContext());
		dataManager.deleteAllTableFavoriteLocation();
	}
	
	private void insertLocation (FavoriteLocation newLocation){
		DataBaseManager dataManager = new DataBaseManager(getApplicationContext());
		dataManager.insertFavoriteLocation(newLocation);
	}
	
	private void runJSONFile (){
		JSONParser parser = new JSONParser();
		
		try {
			
			InputStream is = getAssets().open("teste.json");
			int size = is.available();
	        byte[] buffer = new byte[size];
	        is.read(buffer);
	        is.close();
	        String json = new String(buffer, "UTF-8");
	        Log.d("ArquivoJSON", json);
	
	        JSONObject jsonObjectTodo = (JSONObject) parser.parse(json);
	        JSONArray arrayResults= (JSONArray) jsonObjectTodo.get("results");
	
	        for(int i=0; i<arrayResults.size(); i++){
	        JSONObject objectResults = (JSONObject) arrayResults.get(i);
	        JSONObject objectGeometry = (JSONObject) objectResults.get("geometry");
	        JSONObject objectLocation = (JSONObject) objectGeometry.get("location");
	
	        String description = (String) objectResults.get("name");
	        Double latitude = (Double) objectLocation.get("lat");
	        Double longitude = (Double) objectLocation.get("lng");
	        //Log.d("Valor do nome==== ", description + " Latitude: " + latitude + " Longitude: " + longitude);
	        
	        FavoriteLocation favorite = new FavoriteLocation(latitude, longitude, description);
	        insertLocation(favorite);
        }
		} catch (Exception ex){
			Toast.makeText(getApplicationContext(), "Ocorreu um erro ao ler o arquivo JSON", Toast.LENGTH_LONG).show();
			finish();
			ex.printStackTrace();
			
		}
	}
	
	private String getNearLocationByCurrentLocation () throws Exception{
		Double distanceRadius = null;
		String shortPlaceName = null;
		
		Double currentLatitude = Double.parseDouble(getIntent().getExtras().getString("latitude"));
		Double currentLongitude = Double.parseDouble(getIntent().getExtras().getString("longitude"));	
		Double currentUserRadius = Double.parseDouble(radiusDistanceValue.getText().toString());
		
		
		ArrayList<FavoriteLocation> dataBaseLocation = new ArrayList<FavoriteLocation>();
		DataBaseManager dataManager = new DataBaseManager(getApplicationContext());
		dataBaseLocation = (ArrayList<FavoriteLocation>) dataManager.getFavoriteLocation();
		
		for (FavoriteLocation fv : dataBaseLocation){
			Double currentDistance = getDistanceBetweenPlacesLocation(currentLatitude, fv.getLatitude(), currentLongitude, fv.getLongitude());
			if (distanceRadius == null || distanceRadius > currentDistance){
				distanceRadius = currentDistance;
				shortPlaceName = fv.getDescription();
			}
			
		}
		
		if (distanceRadius < currentUserRadius) {
			shortPlaceName = "Seu local mais próximo é: " + shortPlaceName;
		} else {
			shortPlaceName = "Não foi encontrado local dentro do raio especificado";
		}
		return shortPlaceName;
	}
	
	
	
	private String getFarLocationByCurrentLocation () throws Exception{
		Double distanceRadius = null;
		String shortPlaceName = null;
		
		Double currentLatitude = Double.parseDouble(getIntent().getExtras().getString("latitude"));
		Double currentLongitude = Double.parseDouble(getIntent().getExtras().getString("longitude"));	
		Double currentUserRadius = Double.parseDouble(radiusDistanceValue.getText().toString());
		
		
		ArrayList<FavoriteLocation> dataBaseLocation = new ArrayList<FavoriteLocation>();
		DataBaseManager dataManager = new DataBaseManager(getApplicationContext());
		dataBaseLocation = (ArrayList<FavoriteLocation>) dataManager.getFavoriteLocation();
		
		for (FavoriteLocation fv : dataBaseLocation){
			Double currentDistance = getDistanceBetweenPlacesLocation(currentLatitude, fv.getLatitude(), currentLongitude, fv.getLongitude());
			if (distanceRadius == null || distanceRadius < currentDistance){
				distanceRadius = currentDistance;
				shortPlaceName = fv.getDescription();
			}
			
		}
		
		if (distanceRadius < currentUserRadius) {
			shortPlaceName = "Seu local mais distante é: " + shortPlaceName;
		} else {
			shortPlaceName = "Não foi encontrado local dentro do raio especificado";
		}
		return shortPlaceName;
	}
	
	
	private Double getDistanceBetweenPlacesLocation(Double firstLat, Double secondLat, Double firstLong, Double secondLong) {
		double firstLatToRad = Math.toRadians(firstLat);
		double secondLatToRad = Math.toRadians(secondLat);

		// Diferença das longitudes
		double deltaLongitudeInRad = Math.toRadians(secondLong - (firstLong));

		// Calcula da distância entre os pontos
		double finalDistance = Math.acos(Math.cos(firstLatToRad) * Math.cos(secondLatToRad)	
				* Math.cos(deltaLongitudeInRad) + Math.sin(firstLatToRad)
				* Math.sin(secondLatToRad)) * 6371;
		
		Double total = finalDistance * 1000;   
		total = (double) Math.round(total * 100);
		return total / 100;
		
	}
}
