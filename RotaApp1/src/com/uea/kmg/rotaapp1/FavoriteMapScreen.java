package com.uea.kmg.rotaapp1;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FavoriteMapScreen extends FragmentActivity {
	
	private GoogleMap googleMap;
	private Button btBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite_map_screen);
		setTitle("Localizar meu favorito");
		btBack = (Button) this.findViewById(R.id.btFavoriteBack);
		
		btBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		 final int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

	        if(status!=ConnectionResult.SUCCESS){        	
	        	final int requestCode = 10;
	            final Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
	            dialog.show();
	            
	        }else {		
				SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFavorite);
				
				googleMap = fm.getMap();

		        	double latitude = Double.parseDouble(getIntent().getExtras().getString("latitude"));
		    		double longitude = Double.parseDouble(getIntent().getExtras().getString("longitude"));
		        	
		    		LatLng latLng = new LatLng(latitude, longitude);
		    		
		    		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		    		googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));		
		    		
		    		
		    		 googleMap.addMarker(new MarkerOptions()
		            .position(latLng)
		            .title("favorite")
		            .snippet("favorite")
		            .icon(BitmapDescriptorFactory
		                .fromResource(R.drawable.favorite_ico)));
	        }
	        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.favorite_map_screen, menu);
		return true;
	}

}
