package com.example.ithsblog;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post_list, menu);
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
