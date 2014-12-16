package com.example.ithsblog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


public class ReadPost extends ActionBarActivity implements PropertyChangeListener {

	private String title;
	private String text;
	private String date;
	private String id;
	private String imageURL;
	private String commentText;
	private String user_id;
	private EditText comment;
	private ListView listView;
	private View post;
	private ArrayList<JSONObject> list = new ArrayList<JSONObject>();
	private ArrayAdapter<JSONObject> adapter;
	private Button editButton, deleteButton, addButton;
	private SharedPreferences mySettings;


	
	
	private OnClickListener editButtonListener = new OnClickListener() {
		public void onClick(View v) { 			
			edit();
		}
	};
	private OnClickListener deleteButtonListener = new OnClickListener() {
		public void onClick(View v) { 			
			new CheckIfAuthor(ReadPost.this).execute();
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
		if(mySettings.contains("sessionId")){
			new AddComments(this, id, commentText, mySettings.getString("sessionId", "55")).execute();
		}else{
			Toast.makeText(this, "You must be logged in to post a comment", Toast.LENGTH_LONG).show();
		}
	}


	private void edit() {
		Intent intent = new Intent(ReadPost.this, Posts.class);
		intent.putExtra("ID", id);
		intent.putExtra("DATE", date);
		intent.putExtra("TITLE", title);
		intent.putExtra("TEXT", text);
		intent.putExtra("IMAGEURL", imageURL);
		finish();
		startActivity(intent);

	}


	private void delete() {
		new DeletePost(this, id).execute();
		Intent deleteIntent = new Intent(ReadPost.this, PostList.class);
		deleteIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(deleteIntent);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_post);
		Intent intent = getIntent();
		title = intent.getStringExtra("TITLE");
		date = intent.getStringExtra("DATE");
		text = intent.getStringExtra("TEXT");
		id = intent.getStringExtra("ID");
		imageURL = intent.getStringExtra("IMAGEURL");

		mySettings = PreferenceManager.getDefaultSharedPreferences(this);
		new GetComments(this, id, "0").execute();
		startListView();
	}

	@Override
	protected void onResume(){
		super.onResume();
		supportInvalidateOptionsMenu();


		//new GetComments(this).execute();

	}

	private void startListView() {

		adapter = new ReadPostListAdapter(ReadPost.this, list, id);
		LayoutInflater inflater = LayoutInflater.from(this);
		if(mySettings.getBoolean("isAdmin", false)){
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

		//Image
		ImageView imageView = (ImageView)post.findViewById(R.id.item_imageView);
		String url = "http://jonasekstrom.se/ANNAT/iths_blog/images/"+id+".jpg";
		if(ImageCache.checkCache(id)){
			imageView.setImageBitmap(ImageCache.getBitmap());
		}else{
			new ImageLoader(imageView, url, id).execute();
		}
		// Title
		TextView titleView = (TextView)post.findViewById(R.id.item_titleView);
		titleView.setText(title);
		//Text
		TextView textView = (TextView)post.findViewById(R.id.item_textView);
		textView.setText(text);
		// Date
		TextView dateView = (TextView)post.findViewById(R.id.item_dateView);
		dateView.setText(date);

		listView = (ListView) findViewById(R.id.comment_list);
		listView.addHeaderView(post);
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
			Intent intent = new Intent (ReadPost.this , Posts.class);
			startActivity(intent);
			finish();
			return true;
		}else if (id == R.id.action_login) {
			showSignInScreen();
			return true;
		} else if (id == R.id.action_logout) {
			LogOut.doLogOut(this);
			supportInvalidateOptionsMenu();
			return true;
		}else if (id == R.id.action_listan) {
			Intent intent = new Intent (ReadPost.this , PostList.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void inflateMenu(Menu menu) {
		menu.clear();
		if (mySettings.contains("sessionId") && mySettings.contains("isAdmin")) {			
			getMenuInflater().inflate(R.menu.logout, menu);
		} else {
			getMenuInflater().inflate(R.menu.post_list, menu);
		}
		menu.findItem(R.id.action_new_post).setVisible(mySettings.getBoolean("isAdmin", false));
	}

	private void showSignInScreen() {
		Intent intent = new Intent(ReadPost.this, LogIn.class);
		startActivity(intent);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals("checkIfAuthorDone")){
			if(event.getNewValue().equals("1")){
				showPopup(ReadPost.this);
			}else{
				Toast.makeText(this, "Failed to authenticate admin user", Toast.LENGTH_LONG).show();
			}
		} else if(event.getPropertyName().equals("getCommentsDone")){
			ArrayList<JSONObject> eventList = (ArrayList<JSONObject>) event.getNewValue(); 
			for(int i = 0; i < eventList.size(); i++){
				list.add(eventList.get(i));
				adapter.notifyDataSetChanged();
			}				
		} else if(event.getPropertyName().equals("addCommentsDone")){
			if ("error session id".equals(event.getNewValue())) {
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.login_again), Toast.LENGTH_SHORT).show();
				LogOut.doLogOut(this);
				showSignInScreen();			
			} else if ("success".equals(event.getNewValue())) {
				list.clear();
				new GetComments(this, id, "0").execute();
			}						
		} 
	}

	// The method that displays the popup.
	private void showPopup(final Activity context) {
		int popupWidth = 330;
		int popupHeight = 250;

		// Inflate the popup_layout.xml
		LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.delete_popup, viewGroup);

		// Creating the PopupWindow
		final PopupWindow popup = new PopupWindow(context);
		popup.setContentView(layout);
		popup.setWidth(popupWidth);
		popup.setHeight(popupHeight);
		popup.setFocusable(true);


		// Displaying the popup at the specified location, + offsets.
		popup.showAtLocation(layout, Gravity.CENTER, 0, 0);


		// Getting a reference to Cancle button, and close the popup when clicked.
		Button close = (Button) layout.findViewById(R.id.delete_cancel_button);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popup.dismiss();
			}
		});

		// Getting a reference to Delete button, and close the popup when clicked.
		Button delete = (Button) layout.findViewById(R.id.delete_button);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				delete();
				popup.dismiss();
			}
		});
	}
}
