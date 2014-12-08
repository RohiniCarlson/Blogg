package com.example.ithsblog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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


public class ReadPost extends ActionBarActivity implements PropertyChangeListener {
	
	private String title;
	private String text;
	private String date;
	private String id;
	private String imageURL;
	private String commentText;
	private EditText comment;
	private ListView listView;
	private View post;
	private boolean author = false;
	private ArrayList<JSONObject> list = new ArrayList<JSONObject>();
	private Button editButton, deleteButton, addButton;
	
	
	private OnClickListener editButtonListener = new OnClickListener() {
		public void onClick(View v) { 			
			edit();
		}
    };
    private OnClickListener deleteButtonListener = new OnClickListener() {
		public void onClick(View v) { 			
			
			showPopup(ReadPost.this);
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
		intent.putExtra("ID", id);
		intent.putExtra("DATE", date);
		intent.putExtra("TITLE", title);
		intent.putExtra("TEXT", text);
		intent.putExtra("IMAGE", imageURL);
		finish();
		startActivity(intent);
		
	}
	
	private void delete() {
		new DeletePost(this, id).execute();
		finish();
		
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
		
		
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		new CheckIfAuthor(this).execute();
		//new GetComments(this).execute();
		
	}

	private void startListView() {
		
		ArrayAdapter<JSONObject> adapter = new ReadPostListAdapter(ReadPost.this, list);
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

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals("checkIfAuthorDone")){
			if(event.getNewValue().equals("0")){
				author = true;
				new GetComments(this, id).execute();
			}else{
				new GetComments(this, id).execute();				
			}
		} else if(event.getPropertyName().equals("getCommentsDone")){
			list = (ArrayList<JSONObject>) event.getNewValue();
			startListView();
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
		 
		 
		   // Clear the default translucent background
		   //popup.setBackgroundDrawable(new BitmapDrawable());
		   
		 
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
