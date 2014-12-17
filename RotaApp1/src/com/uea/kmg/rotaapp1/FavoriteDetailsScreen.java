package com.uea.kmg.rotaapp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FavoriteDetailsScreen extends Activity {
	private TextView tvDescription;
	private TextView tvLatitude;
	private TextView tvLongitude;
	private Button btGoToFavorite;
	private Button btBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite_details_screen);
		setTitle("Meu favorito");
		tvDescription = (TextView) this.findViewById(R.id.tvDescriptionDetail);
		tvLatitude= (TextView) this.findViewById(R.id.tvLatitudeDetail);
		tvLongitude = (TextView) this.findViewById(R.id.tvLongitudeDetail);
		btGoToFavorite = (Button) this.findViewById(R.id.btGoToFavorite);
		btBack = (Button) this.findViewById(R.id.btFavoriteDetailVoltar);
		
		
		tvDescription.setText(getIntent().getExtras().getString("description"));
		tvLatitude.setText(getIntent().getExtras().getString("latitude"));
		tvLongitude.setText(getIntent().getExtras().getString("longitude"));
	
		btGoToFavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FavoriteDetailsScreen.this, FavoriteMapScreen.class);
				Bundle bundle = getIntent().getExtras();
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		btBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorite_details_screen, menu);
		return true;
	}

}
