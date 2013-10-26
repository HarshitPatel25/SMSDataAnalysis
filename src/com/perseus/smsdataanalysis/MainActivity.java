package com.perseus.smsdataanalysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	private final static String LOG_TAG = "SMSDataAnalysis_tag";
	private final static String TEH_TAGZ = "YORP";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		foo();
	}

	// Bleeding edge development method
	// Should look like a word frequency
	private void foo() {
		String scope = "sent";
		Cursor cursor = getContentResolver().query(
				Uri.parse("content://sms/" + scope), new String[] { "body" },
				null, null, null);
		cursor.moveToFirst();
		HashMap<String,Integer> freq = new HashMap<String,Integer>();
		do {
			for (int idx = 0; idx < cursor.getColumnCount(); idx++)
			{
				for(String s : cursor.getString(idx).split(" "))
				{
					if(freq.containsKey(s))
						freq.put(s, freq.get(s)+1);
					else
						freq.put(s, 1);
				}
			}
		} while (cursor.moveToNext());
		ArrayList<Entry<String, Integer>> out = new ArrayList<Entry<String, Integer>>();
		out.addAll(freq.entrySet());
		Collections.sort(out, new Comparator<Entry<String, Integer>>(){

			@Override
			public int compare(Entry<String, Integer> lhs,
					Entry<String, Integer> rhs) {
				// TODO Auto-generated method stub
				return rhs.getValue() - lhs.getValue(); 
			}

			
			
		});
		for(int x = 0; x <= 10; x++)
		{
			Log.v(TEH_TAGZ, out.get(x).getValue() + " : " + out.get(x).getKey());
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void startAnalysisActivity(View view) {
		Intent myIntent = new Intent(MainActivity.this, AnalysisMenuActivity.class);
		//myIntent.putExtra("key", value); //Optional parameters
		MainActivity.this.startActivity(myIntent);
	}

	public void startBattleActivity(View view) {
		Intent myIntent = new Intent(MainActivity.this, BattleMenuActivity.class);
		//myIntent.putExtra("key", value); //Optional parameters
		MainActivity.this.startActivity(myIntent);
	}
}