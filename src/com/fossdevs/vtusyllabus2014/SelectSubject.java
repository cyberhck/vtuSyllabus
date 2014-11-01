package com.fossdevs.vtusyllabus2014;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SelectSubject extends ActionBarActivity {
	public final String getSubjects="SELECT syllabus._id,syllabus.subjectCode,subject.subjectName FROM syllabus,subject WHERE syllabus.subjectCode=subject.subjectCode AND syllabus.branch=";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_subject);
		Intent intent=getIntent();
		Bundle extras=intent.getExtras();
		final String sem=extras.getString("SEM");
		final String branch=extras.getString("BRANCH");
		DatabaseHelper dbhelper=new DatabaseHelper(getApplicationContext());
		SQLiteDatabase db=dbhelper.getWritableDatabase();
		final Cursor c=db.rawQuery(getSubjects+"'"+branch+"' AND sem='"+sem+"' GROUP BY syllabus.subjectCode", null);
		try{
			int to[]={R.id.item};
			String[] from=new String[] {"subjectName"};
			ListView lv=(ListView)findViewById(R.id.subject);
			Adapter adapter=new SimpleCursorAdapter(getBaseContext(), R.layout.item_select_xml, c, from, to,1);
			lv.setAdapter((ListAdapter)adapter);
	    	lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int pos,long arg3) {
					c.moveToPosition(pos);
					String subjectCode=c.getString(c.getColumnIndex("subjectCode"));
					Intent subjectChooser=new Intent(getApplicationContext(),DisplayContent.class);
					Bundle extras=new Bundle();
					extras.putString("SEM", sem);
					extras.putString("BRANCH", branch);
					extras.putString("SUBJECTCODE", subjectCode);
					subjectChooser.putExtras(extras);
					startActivity(subjectChooser);
				}
			});
			
		}catch (Exception e){
			Log.d("mytag", e.toString());
		}
		try{
		      AdView av=(AdView)findViewById(R.id.adView);
		      AdRequest ar=new AdRequest.Builder().build();
		      av.loadAd(ar);
		    }catch (Exception e){
		      
		    }

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_subject, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
