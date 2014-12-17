package com.uea.kmg.rotaapp1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uea.kmg.rotaapp1.dao.DataBaseManager;
import com.uea.kmg.rotaapp1.model.FavoriteLocation;

public class EditFavoriteScreen extends Activity {
	EditText etDescription;
	EditText etLatitude;
	EditText etLongitude;
	Button btSaveChanges;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_favorite_screen);
		setTitle("Editar Favorito");
		
		etDescription = (EditText) this.findViewById(R.id.etEditFavoriteDescription);
		etLatitude = (EditText) this.findViewById(R.id.etEditFavoriteLatitude);
		etLongitude = (EditText) this.findViewById(R.id.etEditFavoriteLongitude);
		btSaveChanges = (Button) this.findViewById(R.id.btEditFavoriteSave);
		
		etDescription.setText(getIntent().getExtras().getString("description"));
		etLatitude.setText(getIntent().getExtras().getString("latitude"));
		etLongitude.setText(getIntent().getExtras().getString("longitude"));
	
		btSaveChanges.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String desc;
				Double lat, lng;
				long id;
				id = getIntent().getExtras().getLong("id");
				desc = etDescription.getText().toString();
				lat = Double.parseDouble(etLatitude.getText().toString());
				lng = Double.parseDouble(etLongitude.getText().toString());
				FavoriteLocation favorite = new FavoriteLocation(id, lat, lng, desc);
				updateFavorite(favorite);
			}
		});
	}

	private void  updateFavorite (FavoriteLocation favoriteLocation){
		DataBaseManager dataManager = new DataBaseManager(getApplicationContext());
		dataManager.updateLocation(favoriteLocation);
		Toast.makeText(getBaseContext(), "Favorito atualizado", Toast.LENGTH_LONG).show();
		finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_favorite_screen, menu);
		return true;
	}

}
