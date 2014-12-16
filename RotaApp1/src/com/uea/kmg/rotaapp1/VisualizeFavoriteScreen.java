package com.uea.kmg.rotaapp1;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.uea.kmg.rotaapp1.adapter.FavoriteAdapter;
import com.uea.kmg.rotaapp1.dao.DataBaseManager;
import com.uea.kmg.rotaapp1.model.FavoriteLocation;

public class VisualizeFavoriteScreen extends Activity {

	ListView lvFavorites;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualize_favorite_screen);
		setTitle("Meus favoritos");
		
		lvFavorites = (ListView) this.findViewById(R.id.lvFavoritesList);
		
		ArrayList<FavoriteLocation> favo = new ArrayList<FavoriteLocation>();
		
		DataBaseManager dataManager = new DataBaseManager(getApplicationContext());
		
		favo = (ArrayList<FavoriteLocation>) dataManager.getFavoriteLocation();
		
		FavoriteAdapter adapter = new FavoriteAdapter(getApplicationContext(), favo);
		
		lvFavorites.setAdapter(adapter);
		
		registerForContextMenu(lvFavorites);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.visualize_favorite_screen, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
			MenuItem delete = menu.add("Apagar");
			delete.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(MenuItem item) {
						
					Toast.makeText(getApplicationContext(), "Apagado", Toast.LENGTH_LONG).show();
					return false;
				}
			});
	
	}
	
	
}
