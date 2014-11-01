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

public class SelectSem extends ActionBarActivity {
	public final String getSem="SELECT _id,sem FROM syllabus WHERE branch=?";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_sem);
		Intent intent=getIntent();
		Bundle extra=intent.getExtras();
		final String Branch=extra.getString("BRANCH");
		String[] branch={extra.getString("BRANCH")};
		try{
			DatabaseHelper dbhelper=new DatabaseHelper(getApplicationContext());
			SQLiteDatabase db=dbhelper.getWritableDatabase();
			final Cursor c=db.rawQuery(getSem+" GROUP BY syllabus.sem;",branch);
			int to[]={R.id.item};
			String[] from=new String[] {"sem"};
			ListView lv=(ListView)findViewById(R.id.sem);
			Adapter adapter=new SimpleCursorAdapter(getBaseContext(), R.layout.item_select_xml, c, from, to,1);
			lv.setAdapter((ListAdapter) adapter);
        	lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int pos,long arg3) {
					c.moveToPosition(pos);
					String sem=c.getString(c.getColumnIndex("sem"));
					Intent subjectChooser=new Intent(getApplicationContext(),SelectSubject.class);
					Bundle extras=new Bundle();
					extras.putString("SEM", sem);
					extras.putString("BRANCH", Branch);
					subjectChooser.putExtras(extras);
					startActivity(subjectChooser);
				}
			});

		}catch(Exception e){
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
		getMenuInflater().inflate(R.menu.select_sem, menu);
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
