package com.example.ithsblog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity implements PropertyChangeListener {

	ArrayList<JsonObjects> objectList = new ArrayList();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Send to author view or reader view, check shared preferences for saved mail		
		new CheckIfAuthor(this).execute();
		new GetObjects(this).execute();
		// Log.d("hej","check: ");
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
			Intent intent = new Intent (MainActivity.this , Posts.class);
        	startActivity(intent);
			return true;
		} else if (id == R.id.action_sign_in) {
			showSignInScreen();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {

		if (event.getPropertyName().equals("checkIfAuthorDone")) {
			String result = (String) event.getNewValue();
			if(result.equals("1")) {
				// send to author
				Intent myTriggerActivityIntent=new Intent(this,Posts.class);
				startActivity(myTriggerActivityIntent);
				finish();
			} else {
				// send to reader activity
				Intent myTriggerActivityIntent=new Intent(this,PostList.class);
				startActivity(myTriggerActivityIntent);
			}			
		} else if (event.getPropertyName().equals("getObjectsDone")) {
			// log all object info, listarray 
			
			objectList = (ArrayList<JsonObjects>) event.getNewValue();
			
			for(int i = 0;i < objectList.size(); i++) {
				Log.d("hej",objectList.get(i).getId()+", "+objectList.get(i).getTitle()+", "+objectList.get(i).getText());
			}
			
		}
		
	}
	
	private void showSignInScreen() {
		Intent intent = new Intent(MainActivity.this, LogIn.class);
		startActivity(intent);
	}
}
