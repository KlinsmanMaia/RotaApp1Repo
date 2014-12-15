
package com.uea.kmg.rotaapp1.dao;

import java.util.ArrayList;
import java.util.List;

import com.uea.kmg.rotaapp1.model.FavoriteLocation;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataBaseManager {

	private SQLiteDatabase dataBase;
	private DbOpenHelper dbHelper;
	
	public DataBaseManager(Context context) {
		dbHelper = DbOpenHelper.getInstance(context);
	}

	public void insertFavoriteLocation (FavoriteLocation favorite){
		ContentValues contentValues = new ContentValues();
		contentValues.put("latitude", favorite.getLatitude());
		contentValues.put("longitude", favorite.getLongitude());
		contentValues.put("description", favorite.getDescription());
		
		dataBase = dbHelper.getWritableDatabase();
		dataBase.insert("favoritelocation", null, contentValues);
		dataBase.close();
	}
	
	public List<FavoriteLocation> getFavoriteLocation (){
		dataBase = dbHelper.getReadableDatabase();
		
//		String sql = "SELECT * FROM favoritelocation ";
//		String [] a = {String.valueOf("2")};
		String[] allColumns = { "_id", "latitude", "longitude", "description" };
//		Cursor cursor = dataBase.rawQuery(sql, null);
		Cursor cursor = dataBase.query("favoritelocation", allColumns, null, null, null, null, null);
		ArrayList<FavoriteLocation> favoriteLocation = new ArrayList<FavoriteLocation>();
		
		while (cursor.moveToNext()){
			
			long id = cursor.getLong(cursor.getColumnIndex("_id"));
			Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
			Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
			String description = cursor.getString(cursor.getColumnIndex("description"));
			FavoriteLocation  favorite = new FavoriteLocation(id, latitude, longitude, description);
			favoriteLocation.add(favorite);
		}
		dataBase.close();
		cursor.close();
	
		return favoriteLocation;
	}
	
	public void updateLocation (FavoriteLocation favorite){
		ContentValues values = new ContentValues();
		values.put("latitude", favorite.getLatitude());
		values.put("longitude", favorite.getLongitude());
		values.put("description", favorite.getDescription());
		
		dataBase = dbHelper.getWritableDatabase();
		String [] idValue = {String.valueOf(favorite.getId())};
		
		dataBase.update("favoritelocation", values, "_id = ? ", idValue);
		dataBase.close();
	}
	
	
	
	public void deleteFavoriteLocation(FavoriteLocation favorite) {
		dataBase = dbHelper.getWritableDatabase();
		String [] idValues = {String.valueOf(favorite.getId())};
		dataBase.delete("favoritelocation", "_id = ?", idValues);
	}
	
}
