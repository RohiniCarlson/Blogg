package com.example.ithsblog;

import java.util.ArrayList;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class ReadPost extends ActionBarActivity {
	
	private String id;
	private ArrayList<Object> list = new ArrayList<Object>();
	private ListView listView;
	private View post;
	private boolean author = false;
	private Button editButton, deleteButton, addButton;
	private EditText comment;
	private String commentText;
	
	private OnClickListener editButtonListener = new OnClickListener() {
		public void onClick(View v) { 			
			edit();
		}
    };
    private OnClickListener deleteButtonListener = new OnClickListener() {
		public void onClick(View v) { 			
			edit();
		}
    };
    private OnClickListener addButtonListener = new OnClickListener() {
		public void onClick(View v) { 			
			addComment();
		}
    };
    
    private void addComment() {
		comment = (EditText) post.findViewById(R.id.item_comment);
        commentText = comment.getText().toString();
        comment.setText("");
		
	}

	private void edit() {
		Intent intent = new Intent(ReadPost.this, Posts.class);
		//intent.putExtra("ID", value);
		finish();
		startActivity(intent);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_post);
		Intent intent = getIntent();
		id = intent.getStringExtra("ID");
		
		newList();
		startListView();
		
	}

	private void startListView() {
		
		ArrayAdapter<Object> adapter = new ReadPostListAdapter(ReadPost.this, list);
		LayoutInflater inflater = LayoutInflater.from(this);
		if(author){
		post = inflater.inflate(R.layout.author_post, null);
		editButton = (Button) post.findViewById(R.id.edit_button);
        editButton.setOnClickListener(editButtonListener);
        deleteButton = (Button) post.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(deleteButtonListener);
        addButton = (Button) post.findViewById(R.id.comment_button);
        addButton.setOnClickListener(addButtonListener);
		}else{
		post = inflater.inflate(R.layout.post, null);
		addButton = (Button) post.findViewById(R.id.comment_button);
        addButton.setOnClickListener(addButtonListener);
		}
		listView = (ListView) findViewById(R.id.comment_list);
		listView.addHeaderView(post);
		listView.setAdapter(adapter);
		
	}

	// Fill the ArrayList
	private void newList() {
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.read_post, menu);
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
