package com.uea.kmg.rotaapp1.notification;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.uea.kmg.rotaapp1.R;

public class NotificationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		setTitle("Estamos chegando");
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}
}
