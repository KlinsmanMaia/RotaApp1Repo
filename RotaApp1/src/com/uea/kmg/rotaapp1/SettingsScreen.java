package com.uea.kmg.rotaapp1;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SettingsScreen extends Activity {
	
	private ListView ltvSettings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings_screen);
		
		ltvSettings = (ListView) this.findViewById(R.id.ltvSettings);
		
		final ArrayList<String> settingsValues = new ArrayList<String>();
		
		settingsValues.add("Notificação");
		settingsValues.add("Sons");
		settingsValues.add("Voltar");
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, settingsValues);
		
		ltvSettings.setAdapter(adapter);
		
		ltvSettings.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int pos,
					long longId) {
					
				switch (pos){
					case 0: 
						startActivity(new Intent(SettingsScreen.this, NotificationScreen.class));
						break;
					case 1: 
						Toast.makeText(getApplicationContext(), "Implementado em versão futura", Toast.LENGTH_LONG).show();	
						break;
					case 2: 
						finish();
						break;
					default: 
						finish();
						break;
					}
			}
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings_screen, menu);
		return true;
	}

}
