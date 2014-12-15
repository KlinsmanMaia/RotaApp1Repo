package com.uea.kmg.rotaapp1.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DbOpenHelper extends SQLiteOpenHelper{

	private static final String DATA_BASE_NAME = "kmg.dbrota";;
	private static final int DATA_BASE_VERSION = 1;
	
	private static DbOpenHelper instance;
	
	private DbOpenHelper(Context context) {
		super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
		//http://developer.android.com/training/location/display-address.html	
	}
	
	public static DbOpenHelper getInstance(Context context){
		if (instance == null){
			instance = new DbOpenHelper(context);
		}
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase dataBase) {
		createDataBase(dataBase);
	}

	private void createDataBase (SQLiteDatabase dataBase){
		
		StringBuilder sqlCreateTable = new StringBuilder();
		
		sqlCreateTable.append("CREATE TABLE favoritelocation(");
		sqlCreateTable.append(BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ");
		sqlCreateTable.append("latitude DOUBLE, ");
		sqlCreateTable.append("longitude DOUBLE, ");
		sqlCreateTable.append("description VARCHAR (60)");
		sqlCreateTable.append(");");

		dataBase.execSQL(sqlCreateTable.toString());
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase dataBase, int actualVersion, int newVersion) {
			dataBase.execSQL("DROP TABLE IF EXISTS favoritelocation ");
			onCreate(dataBase);
	}
}
