package com.uea.kmg.rotaapp1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class VisualizeFavoriteScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualize_favorite_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visualize_favorite_screen, menu);
		return true;
	}

}
