package com.example.ithsblog;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class PostListAdapter extends ArrayAdapter<JSONObject>{

	private LayoutInflater inflater;
	private ArrayList list;
	private int pos;

	public PostListAdapter(Activity activity, ArrayList<JSONObject> list) {
		super(activity, R.layout.posts_list_item, list);
		this.list = list;
    	inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}
	public View getView(int pos, View currentView, ViewGroup parent){
		View view = null;
		
		// Make sure we have a view to work on
		if(currentView == null){
			view = inflater.inflate(R.layout.posts_list_item, parent, false);
		}else {
	        view = currentView;
		}
		
		// Find the post
		JSONObject currentObjects = (JSONObject) list.get(pos);
		
		// Fill the view
		try {
		// Image
		ImageView imageView = (ImageView) view.findViewById(R.id.item_imageView);
		String url = "http://jonasekstrom.se/ANNAT/iths_blog/images/"+currentObjects.getString("id")+".jpg";
		if(ImageCache.checkCache(currentObjects.getString("id"))){
			imageView.setImageBitmap(ImageCache.getBitmap());
		}else{
			new ImageLoader(imageView, url, currentObjects.getString("id")).execute();
		} 
		// Title
	    TextView titleView = (TextView)view.findViewById(R.id.item_titleView);
		titleView.setText(currentObjects.getString("title"));
	    // Date
	    TextView dateView = (TextView)view.findViewById(R.id.item_dateTextView);
	    dateView.setText(""+currentObjects.getString("date"));
	    
	    } catch (JSONException e) {
			e.printStackTrace();
		}
		return view;

	}
}
