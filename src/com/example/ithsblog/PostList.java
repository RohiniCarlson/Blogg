package com.example.ithsblog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

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

public class PostList extends ActionBarActivity implements PropertyChangeListener{

	private ListView listView;
	private ArrayList<JSONObject> postList = new ArrayList<JSONObject>();
	private SharedPreferences mySettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_list);
		new GetPosts(this).execute();
		mySettings = PreferenceManager.getDefaultSharedPreferences(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		supportInvalidateOptionsMenu() ;
		//new GetPosts(this).execute();

	}

	// Handle clicking the items in the list
	private void listClick() {

		ListView listView = (ListView) findViewById(R.id.content_list);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				JSONObject itemClicked = postList.get(position);

				Intent intent = new Intent(PostList.this, ReadPost.class);
				try {
					intent.putExtra("TITLE", itemClicked.getString("title"));
					intent.putExtra("TEXT", itemClicked.getString("txt"));
					intent.putExtra("DATE", itemClicked.getString("date"));
					intent.putExtra("ID", itemClicked.getString("id"));
					intent.putExtra("IMAGEURL", itemClicked.getString("image"));

				} catch (JSONException e) {
					e.printStackTrace();
				}
				startActivity(intent);

			}
		});		

	}

	// 
	private void startListView() {

		ArrayAdapter<JSONObject> adapter = new PostListAdapter(PostList.this, postList);
		listView = (ListView) findViewById(R.id.content_list);
		listView.setAdapter(adapter);


	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		inflateMenu(menu);
		return true;						
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		inflateMenu(menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_new_post) {
			Intent intent = new Intent (PostList.this , Posts.class);
			startActivity(intent);
			return true;
		}else if (id == R.id.action_login) {
			showSignInScreen();
			return true;
		} else if (id == R.id.action_logout) {
			LogOut.doLogOut(this);
			supportInvalidateOptionsMenu();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	private void inflateMenu(Menu menu) {
		menu.clear();
//		SharedPreferences mySettings = PreferenceManager.getDefaultSharedPreferences(this);		
		if (mySettings.contains("email") && mySettings.contains("password")) {			
			getMenuInflater().inflate(R.menu.logout, menu);
		} else {
			getMenuInflater().inflate(R.menu.post_list, menu);
		}
		menu.findItem(R.id.action_new_post).setVisible(mySettings.getBoolean("isAdmin", false));
	}

	private void showSignInScreen() {
		Intent intent = new Intent(PostList.this, LogIn.class);
		startActivity(intent);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		postList = (ArrayList<JSONObject>) event.getNewValue();
		startListView();
		listClick();
	}
}
