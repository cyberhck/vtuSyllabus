package com.fossdevs.vtusyllabus2014;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static String DB_NAME="vtusyllabus";
	public static int DB_VERSION=1;
	public Context context;
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, 1);
		this.context=context;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		try{
			Intent populate=new Intent(context, PopulateData.class);
			populate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(populate);
		}catch(Exception e){
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
