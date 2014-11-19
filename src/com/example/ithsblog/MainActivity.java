package com.example.ithsblog;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Send to author view or reader view, check shared preferences for saved mail		
		new CheckIfAuthor().execute();
		
		if(CheckIfAuthor.isAuthor) {
			// send to author
			// Intent myTriggerActivityIntent=new Intent(this,SecondActivity.class);
			// startActivity(myTriggerActivityIntent);
		} else {
			// send to reader activity
			// Intent myTriggerActivityIntent=new Intent(this,SecondActivity.class);
			// startActivity(myTriggerActivityIntent);
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
