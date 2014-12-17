package com.uea.kmg.rotaapp1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.uea.kmg.rotaapp1.adapter.FavoriteAdapter;
import com.uea.kmg.rotaapp1.dao.DataBaseManager;
import com.uea.kmg.rotaapp1.model.FavoriteLocation;

public class VisualizeFavoriteScreen extends Activity {

	private ListView lvFavorites;
	private FavoriteLocation selectedItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualize_favorite_screen);
		setTitle("Meus favoritos");
		
		lvFavorites = (ListView) this.findViewById(R.id.lvFavoritesList);
		
		runDataList();
		
		lvFavorites.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {

					FavoriteLocation favoriteLocation;
					favoriteLocation = (FavoriteLocation) adapterView.getItemAtPosition(position);
					callActitvityWithFavorteData(favoriteLocation, FavoriteDetailsScreen.class);
			}
		});
		
		lvFavorites.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				FavoriteLocation favoriteLocation;
				favoriteLocation = (FavoriteLocation) adapterView.getItemAtPosition(position);
				selectedItem = favoriteLocation;
				return false;
			}
		});
		
		
		registerForContextMenu(lvFavorites);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.visualize_favorite_screen, menu);
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
			MenuItem delete = menu.add("Apagar");
			MenuItem edit = menu.add("Editar");
		
			delete.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					DataBaseManager dataManager = new DataBaseManager(getApplicationContext());
					dataManager.deleteFavoriteLocation(selectedItem.getId());
					Toast.makeText(getApplicationContext(), "Apagado", Toast.LENGTH_LONG).show();
					runDataList();
					return false;
				}
			});
			
			edit.setOnMenuItemClickListener(new OnMenuItemClickListener() {
				
				@Override
				public boolean onMenuItemClick(MenuItem item) {
					callActitvityWithFavorteData(selectedItem, EditFavoriteScreen.class);
					return false;
				}
			});
	
	}
	
	@Override
	protected void onResume() {
		runDataList();
		super.onResume();
	}
	private void callActitvityWithFavorteData(FavoriteLocation favoriteLocation, Class<?> calledActivity){
		
		Intent intent = new Intent (VisualizeFavoriteScreen.this, calledActivity);
		Bundle bundle = new Bundle();
		bundle.putLong("id", favoriteLocation.getId());
		bundle.putString("latitude", String.valueOf(favoriteLocation.getLatitude()));
		bundle.putString("longitude", String.valueOf(favoriteLocation.getLongitude()));
		bundle.putString("description", favoriteLocation.getDescription());
		intent.putExtras(bundle);
		startActivity(intent);
		
	}
	private void runDataList(){
		ArrayList<FavoriteLocation> favo = new ArrayList<FavoriteLocation>();
		
		DataBaseManager dataManager = new DataBaseManager(getApplicationContext());
		
		favo = (ArrayList<FavoriteLocation>) dataManager.getFavoriteLocation();
		
		FavoriteAdapter adapter = new FavoriteAdapter(getApplicationContext(), favo);
		
		lvFavorites.setAdapter(adapter);
	}
	
}
