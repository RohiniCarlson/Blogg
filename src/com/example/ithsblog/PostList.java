package com.example.ithsblog;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PostList extends ActionBarActivity {

	private ListView listView;
	private ArrayList<Object> postList = new ArrayList<Object>(); 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_list);
		
		newList();
		startListView();
		listClick();
	}
	
	// Handle clicking the items in the list
	private void listClick() {
		
		ListView listView = (ListView) findViewById(R.id.content_list);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {
				
				Object postClicked = postList.get(position);
				
				Intent intent = new Intent(PostList.this, ReadPost.class);
				//intent.putExtra("ID", postClicked.getId());
	    		startActivity(intent);
				
			}
		});		
		
	}
	
	// 
	private void startListView() {

		ArrayAdapter<Object> adapter = new PostListAdapter(PostList.this, postList);
		listView = (ListView) findViewById(R.id.content_list);
		listView.setAdapter(adapter);


	}

	// Fill the ArrayList
	private void newList() {
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		inflateMenu(menu);	
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
		}else if (id == R.id.action_login) {
			showSignInScreen();
			Toast.makeText(getApplicationContext(),"Login!",Toast.LENGTH_LONG).show();
			return true;
		} else if (id == R.id.action_logout) {
			LogOut.doLogOut(this);
			invalidateOptionsMenu();
			Toast.makeText(getApplicationContext(),"Logout!",Toast.LENGTH_LONG).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		invalidateOptionsMenu() ;
		super.onResume();
	}
	
	private void inflateMenu(Menu menu) {
		SharedPreferences mySettings = PreferenceManager.getDefaultSharedPreferences(this);		
		if (mySettings.contains("email") && mySettings.contains("password")) {			
			getMenuInflater().inflate(R.menu.logout, menu);
		} else {
			getMenuInflater().inflate(R.menu.post_list, menu);
		}
	}
	
	private void showSignInScreen() {
		Intent intent = new Intent(PostList.this, LogIn.class);
		startActivity(intent);
	}
}
