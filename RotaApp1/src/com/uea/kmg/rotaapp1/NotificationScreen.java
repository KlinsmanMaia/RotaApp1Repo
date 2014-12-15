package com.uea.kmg.rotaapp1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uea.kmg.rotaapp1.notification.NotificationRegister;

public class NotificationScreen extends Activity {
	
	private Button saveNotification;
	private EditText radius;
	private EditText latitude;
	private EditText longitude;
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_screen);
		setTitle("Notificação");
		
		saveNotification = (Button) this.findViewById(R.id.btSaveNotification);
		radius = (EditText) this.findViewById(R.id.etRaio);
		latitude = (EditText) this.findViewById(R.id.etLatitude);
		longitude = (EditText) this.findViewById(R.id.etLongitude);

		saveNotification.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (registerNotification()){
					Toast.makeText(getApplicationContext(), "Notificação criada", Toast.LENGTH_LONG).show();
					finish();
				}else{
					Toast.makeText(getApplicationContext(), "Ocorreu um erro ao criar a notificação. Tente novamente", Toast.LENGTH_LONG).show();
					cleanFields();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.notification_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void cleanFields (){
		radius.setText("");
		longitude.setText("");
		latitude.setText("");
	}
	
	private boolean registerNotification (){
	try{
		NotificationRegister.radius =  Integer.parseInt(radius.getText().toString());
		NotificationRegister.latitude = Double.parseDouble(latitude.getText().toString());
		NotificationRegister.longitude = Double.parseDouble(longitude.getText().toString()); 
		NotificationRegister.flagActivated = true;
		return true;
	} catch (NumberFormatException nfe){
		return false;
		}
	}
}
