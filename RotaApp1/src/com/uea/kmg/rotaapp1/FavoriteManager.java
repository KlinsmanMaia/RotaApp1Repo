package com.uea.kmg.rotaapp1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FavoriteManager extends Activity {
	Button btAddFavorite;
	Button btVisualize;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite_manager);
		setTitle("Meus favoritos");
		
		btAddFavorite = (Button) this.findViewById(R.id.btManagerAddFavorite);
		btVisualize = (Button) this.findViewById(R.id.btManagerVisuFavorite);
		
		btAddFavorite.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FavoriteManager.this, AddFavoriteScreen.class);
				intent.putExtras(getIntent().getExtras());
				startActivity(intent);
			}
		});
		
		btVisualize.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(FavoriteManager.this, VisualizeFavoriteScreen.class);
				startActivity(intent);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorite_manager, menu);
		return true;
	}

}
