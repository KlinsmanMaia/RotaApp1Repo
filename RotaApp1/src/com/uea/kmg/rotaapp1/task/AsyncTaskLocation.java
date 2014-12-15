package com.uea.kmg.rotaapp1.task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.uea.kmg.rotaapp1.listener.ResultListener;

public class AsyncTaskLocation extends AsyncTask<Double, Object, String>{
	ProgressDialog progressDialog;

	private Context context;
	public ResultListener resultListener = null;
	
	public AsyncTaskLocation(Context context) {
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
			progressDialog = ProgressDialog.show(context, "Aguarde", "Buscando sua localização", true);
	}
	
	@Override
	protected String doInBackground(Double... coordinates) {
		
		return getNameLocale(coordinates[0], coordinates[1]);
	}

	@Override
	protected void onPostExecute(String result) {
		progressDialog.dismiss();
		resultListener.getResult(result);
		super.onPostExecute(result);

	}
	
	
	private String getNameLocale (double lat, double lng){
		Geocoder myLocation = new Geocoder(context, Locale.getDefault());
		
		List<Address> myList;
		
		try {
			myList = myLocation.getFromLocation(lat,lng, 1);
		
		Address address = (Address) myList.get(0);
		         String addressStr = "";
		        addressStr += address.getAddressLine(0) + " ";
		        Log.d("Endereço:", addressStr);
		        return addressStr;
		        
		} catch (IOException e) {
			e.printStackTrace();
			return "Não foi possível verificar sua localização";
		}
	}
}
