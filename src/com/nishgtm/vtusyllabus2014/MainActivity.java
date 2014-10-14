package com.nishgtm.vtusyllabus2014;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
  public final String getBranchSql="SELECT * FROM branch";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            DatabaseHelper db=new DatabaseHelper(getApplicationContext());
            SQLiteDatabase database=db.getWritableDatabase();
            final Cursor c=database.rawQuery(getBranchSql, null);
            int to[]={R.id.item};
            String[] from=new String[] {"branchName"};
            ListView lv=(ListView)findViewById(R.id.branch);
            Adapter adapter=new SimpleCursorAdapter(getBaseContext(), R.layout.item_select_xml, c, from, to,1);
            lv.setAdapter((ListAdapter)adapter);
          lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int pos,long arg3) {
          c.moveToPosition(pos);
          String branch=c.getString(c.getColumnIndex("branch"));
          Intent subjectChooser=new Intent(getApplicationContext(),SelectSem.class);
          Bundle extras=new Bundle();
          extras.putString("BRANCH", branch);
          subjectChooser.putExtras(extras);
          startActivity(subjectChooser);
        }
      });
        }catch(Exception e){
          Toast.makeText(getApplicationContext(), "Please Restart Application", Toast.LENGTH_LONG).show();
        }
        try{
      AdView av=(AdView)findViewById(R.id.adView);
      AdRequest ar=new AdRequest.Builder().build();
      av.loadAd(ar);
    }catch (Exception e){
      Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
    }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
