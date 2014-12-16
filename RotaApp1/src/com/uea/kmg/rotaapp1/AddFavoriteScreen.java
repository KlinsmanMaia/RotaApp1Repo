package com.uea.kmg.rotaapp1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uea.kmg.rotaapp1.dao.DataBaseManager;
import com.uea.kmg.rotaapp1.model.FavoriteLocation;

public class AddFavoriteScreen extends Activity {
	EditText etFavoriteLatitude;
	EditText etFavoriteLongitude;
	EditText etFavoriteDescription;
	Button btAddFavorite;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_favorite_screen);
		setTitle("Adicionar favorito");
		
		etFavoriteDescription = (EditText) this.findViewById(R.id.etFavoriteDescription);
		etFavoriteLatitude = (EditText) this.findViewById(R.id.etFavoriteLatitude);
		etFavoriteLongitude = (EditText) this.findViewById(R.id.etFavoriteLongitude);
		btAddFavorite = (Button) this.findViewById(R.id.btAddFavorite);
		
		etFavoriteLatitude.setText(getIntent().getExtras().getString("latitude"));
		etFavoriteLongitude.setText(getIntent().getExtras().getString("longitude"));
		
		btAddFavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					DataBaseManager dataManager = new DataBaseManager(getApplicationContext());
					Double lat, lng;
					String desc;
					lat = Double.parseDouble(etFavoriteLatitude.getText().toString());
					lng = Double.parseDouble(etFavoriteLongitude.getText().toString());
					desc = etFavoriteDescription.getText().toString();
					
					dataManager.insertFavoriteLocation(new FavoriteLocation(lat, lng, desc));

					for (FavoriteLocation location : dataManager.getFavoriteLocation()){
						Log.d("Prpincipal", "-" + location.getId());
						Log.d("Prpincipal", "-" + location.getLatitude());
						Log.d("Prpincipal", "-" + location.getLongitude());
						Log.d("Prpincipal", "-" + location.getDescription());
					}
					
					Toast.makeText(getApplicationContext(), "Seu favorito adicionado", Toast.LENGTH_LONG).show();
					
					finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_favorite_screen, menu);
		return true;
	}

}
