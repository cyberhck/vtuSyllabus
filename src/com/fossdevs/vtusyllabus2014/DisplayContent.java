package com.fossdevs.vtusyllabus2014;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayContent extends ActionBarActivity {
	final String getStatus="SELECT * FROM status WHERE topic=?";
	final String[] colors={"#4C7523","#dd4814","#181818"};
	//Green,Orange,Gray
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_content);
		try{
			Intent intent=getIntent();
			Bundle extra=intent.getExtras();
			String sem=extra.getString("SEM");
			String branch=extra.getString("BRANCH");
			String subjectCode=extra.getString("SUBJECTCODE");
			DatabaseHelper dbhelper=new DatabaseHelper(getApplicationContext());
			SQLiteDatabase db=dbhelper.getWritableDatabase();
			Cursor c=db.rawQuery("SELECT _id,subjectName,subjectCode FROM subject WHERE subjectCode='"+subjectCode+"'", null);
			c.moveToFirst();
			String subjectName=c.getString(c.getColumnIndex("subjectName"));
			getActionBar().setTitle(subjectName);
			c=db.rawQuery("SELECT syllabus.unitID,syllabus._id,unit.unitID,unit.unitName,syllabus.topic FROM syllabus,unit WHERE syllabus.unitID=unit.unitID AND sem='"+sem+"' AND branch='"+branch+"' AND subjectCode='"+subjectCode+"'", null);
			int unitID=0;
			LinearLayout v=(LinearLayout)findViewById(R.id.Container);
			while(c.moveToNext()){
				if(unitID!=Integer.parseInt(c.getString(c.getColumnIndex("unitID")))){
					unitID=Integer.parseInt(c.getString(c.getColumnIndex("unitID")));
					TextView unitView=new TextView(getApplicationContext());
					unitView.setText(c.getString(c.getColumnIndex("unitName")));
					unitView.setTextColor(Color.parseColor("#1793d0"));
					unitView.setPadding(25, 50, 10, 10);
					unitView.setTextSize(26);
					unitView.setGravity(Gravity.CENTER);
					v.addView(unitView);
				}
				TextView myTextView=new TextView(getApplicationContext());
				int topicID= Integer.parseInt(c.getString(c.getColumnIndex("_id")));
				myTextView.setId(topicID);
				int currentStatus=2;
				DatabaseHelper dbh=new DatabaseHelper(getApplicationContext());
				SQLiteDatabase dat=dbh.getWritableDatabase();
				Cursor dataTemp=dat.rawQuery("SELECT * FROM status WHERE topic='"+topicID+"'",null);
				if(dataTemp.getCount()!=0){
					dataTemp.moveToFirst();
					String temp=dataTemp.getString(dataTemp.getColumnIndex("status"));
					currentStatus=Integer.parseInt(temp);
				}
				myTextView.setTextSize(22);
				myTextView.setTextColor(Color.parseColor(colors[currentStatus]));
				myTextView.setPadding(15, 10, 10, 10);
				myTextView.setText(c.getString(c.getColumnIndex("topic")));
				myTextView.isClickable();
				myTextView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View text) {
						try{
							int topicID=text.getId();
							DatabaseHelper databaseHelperCheckTopicTable=new DatabaseHelper(getApplicationContext());
							SQLiteDatabase dbCheckTopic=databaseHelperCheckTopicTable.getWritableDatabase();
							Cursor checkTopic=dbCheckTopic.rawQuery("SELECT * FROM status WHERE topic='"+topicID+"';", null);
							if(checkTopic.getCount()==0){
								dbCheckTopic.execSQL("INSERT INTO status (topic,status) VALUES ("+topicID+",0);");
								TextView textView=(TextView)text;
								textView.setTextColor(Color.parseColor(colors[0]));
							}else{
								dbCheckTopic.execSQL("UPDATE status SET status=(status+1)%3 WHERE topic='"+topicID+"';");
								dbCheckTopic=databaseHelperCheckTopicTable.getWritableDatabase();
								checkTopic=dbCheckTopic.rawQuery("SELECT * FROM status WHERE topic='"+topicID+"'",null);
								checkTopic.moveToFirst();
								String currentsts=checkTopic.getString(checkTopic.getColumnIndex("status"));
								int newStatus=(Integer.parseInt(currentsts))%3;
								TextView textView=(TextView) text;
								textView.setTextColor(Color.parseColor(colors[newStatus]));
							}
						}catch(Exception e){
						}
					}
				});
				v.addView(myTextView);
			}
			TextView dummyTextView=new TextView(getApplicationContext());
			dummyTextView.setText("");
			dummyTextView.setPadding(0, 0, 0, 70);
			v.addView(dummyTextView);
		}catch (Exception e){
			
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
		getMenuInflater().inflate(R.menu.display_content, menu);
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
