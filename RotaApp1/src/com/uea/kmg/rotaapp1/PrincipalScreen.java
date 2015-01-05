package com.uea.kmg.rotaapp1;

import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uea.kmg.rotaapp1.listener.ResultListener;
import com.uea.kmg.rotaapp1.notification.NotificationActivity;
import com.uea.kmg.rotaapp1.notification.NotificationRegister;
import com.uea.kmg.rotaapp1.task.AsyncTaskLocation;

public class PrincipalScreen extends FragmentActivity implements LocationListener, ResultListener {

	private GoogleMap googleMap;
	private LocationManager locationManager;
	private String provider;
	private Marker waldeckMarker;
	private double currentLatitude;
	private double currentLongitude;
	private Button btSpeakLocation;
	private TextToSpeech textToSpeak;
	private AsyncTaskLocation asyncTaskLocation;
	private Button btManagerFavorites;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal_screen);
		
		btManagerFavorites = (Button) this.findViewById(R.id.btFavoriteManager);
		
		btSpeakLocation = (Button) this.findViewById(R.id.btSpeak);
		
		btManagerFavorites.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startSaveFavorite(currentLatitude, currentLongitude, FavoriteManager.class);
			}
		});
		
		btSpeakLocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				asyncTaskLocation = new AsyncTaskLocation(PrincipalScreen.this);
				asyncTaskLocation.resultListener = PrincipalScreen.this;
				asyncTaskLocation.execute(currentLatitude, currentLongitude);
			}
		});
		
        final int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if(status!=ConnectionResult.SUCCESS){        	
        	final int requestCode = 10;
            final Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }else {		
			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			googleMap = fm.getMap();
			googleMap.setMyLocationEnabled(true);				
	        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

	        Criteria criteria = new Criteria();
	        provider = locationManager.getBestProvider(criteria, true);
	        Location location = locationManager.getLastKnownLocation(provider);

	        locationManager.requestLocationUpdates(provider, 20000, 0, this);

	        Log.d("rerere", "dsdasdasdasdadasdasda   Provider: " + provider);
	        
	        if(location!=null){
	        	setInitialPositionAndMark(location);
	    		onLocationChanged(location);
	        }
        }
		
        
        googleMap.setOnMapClickListener(new OnMapClickListener() {
			
			@Override
			public void onMapClick(LatLng point) {
				startSaveFavorite(point.latitude, point.longitude, AddFavoriteScreen.class);
			}
		});
	}
	

	@Override
	public void onLocationChanged(Location location) {
		atualizePosition(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			startSettingsActivity();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }
	
	@Override
    protected void onPause() {
        super.onPause();
        
        locationManager.removeUpdates(this);
        
        if(textToSpeak !=null){
        	textToSpeak.stop();
        	textToSpeak.shutdown();
         }
    }
	private void startSettingsActivity() {
		Intent intent = new Intent(PrincipalScreen.this, SettingsScreen.class);
		startActivity(intent);
	}
	
	private void atualizePosition(Location location){
		TextView tvLocation = (TextView) findViewById(R.id.tv_location);

		double latitude = location.getLatitude();
		double longitude = location.getLongitude();		
		LatLng latLng = new LatLng(latitude, longitude);
		
		tvLocation.setText("Latitude: " +  latitude  + ",  Longitude: "+ longitude );
		locationManager.requestLocationUpdates(provider, 2000, 0, this);
	
		waldeckMarker.setPosition(latLng);

		currentLatitude = latitude;
		currentLongitude = longitude;
		
		verifyDistanceAndNotify(latitude, longitude);
	}
	
	
	private void setInitialPositionAndMark(Location location){
		TextView tvLocation = (TextView) findViewById(R.id.tv_location);
	
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();		
		LatLng latLng = new LatLng(latitude, longitude);
		
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(18));		
		
		tvLocation.setText("Latitude: " +  latitude  + ",  Longitude: "+ longitude );
		locationManager.requestLocationUpdates(provider, 2000, 0, this);
		
		 waldeckMarker = googleMap.addMarker(new MarkerOptions()
        .position(latLng)
        .title("Waldeck")
        .snippet("Segundo ele é pesquisador agora")
        .icon(BitmapDescriptorFactory
            .fromResource(R.drawable.waldeck_ico)));
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
	
	private void verifyDistanceAndNotify(Double latitude, Double longitude){
		
		if (getDistanceBetweenPlacesLocation(latitude, NotificationRegister.latitude, longitude, 
				NotificationRegister.longitude) <= NotificationRegister.radius && NotificationRegister.flagActivated){
			NotificationRegister.flagActivated = false;	
			createNotification();
		}
		
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void createNotification() {
		    final Intent intent = new Intent(this, NotificationActivity.class);
		    final PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

		    Uri sound = Uri.parse("android.resource://"
		            + getPackageName() + "/" + R.raw.alarm);
		    
		  //  Uri soundDefault = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		    
		    Notification notification = new Notification.Builder(this)
		        .setContentTitle("Você está próximo do seu destino")
		        .setVibrate(new long[]{ 200, 1000, 200, 1000})
		        .setSound(sound)
		        .setContentText("Ei, estamos chegando").setSmallIcon(R.drawable.ic_launcher)
		        .setContentIntent(pIntent).build();
		    final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		    notification.flags |= Notification.FLAG_AUTO_CANCEL;
		    notificationManager.notify(0, notification);

		  }


	@Override
	public void getResult(final String locationToSpeak) {
		
		textToSpeak = new TextToSpeech(PrincipalScreen.this, new TextToSpeech.OnInitListener() {
	      @Override
	      public void onInit(int status) {
	         if(status != TextToSpeech.ERROR){
	        	 
	        	 textToSpeak.setLanguage(Locale.getDefault());
	        	 Toast.makeText(PrincipalScreen.this, locationToSpeak,  Toast.LENGTH_SHORT).show();
	        	 textToSpeak.speak(locationToSpeak, TextToSpeech.QUEUE_FLUSH, null);
	        	 
	            }				
	         }
	      });
	}
	
	private void startSaveFavorite(Double latitude, Double longitude, Class<?> calledClass){
		Intent intent = new Intent(PrincipalScreen.this, calledClass);			
		Bundle bundle = new Bundle();
		bundle.putString("latitude", String.valueOf(latitude));
		bundle.putString("longitude", String.valueOf(longitude));
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
