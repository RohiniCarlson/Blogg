package com.example.ithsblog;

import java.beans.PropertyChangeListener;
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
	private int count = 10;
	private PropertyChangeListener pcl;

	public PostListAdapter(Activity activity, ArrayList<JSONObject> list) {
		super(activity, R.layout.posts_list_item, list);
		this.list = list;
		pcl = (PropertyChangeListener) activity;
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
		
		// Reset count if necessary
				if(list.size() == 10){
					count = 10;
				}
		
		// Fill the list
		if(pos == list.size()-1 && count == list.size()){
			new GetPosts(pcl, ""+count).execute();
			count = count + 10;
		}

		// Fill the view
		try {
			// Image
			ImageView imageView = (ImageView) view.findViewById(R.id.item_imageView);
			imageView.setImageBitmap(null);
			if(currentObjects.getString("image").equals("1")){
				String url = "http://jonasekstrom.se/ANNAT/iths_blog/images/"+currentObjects.getString("id")+"_thumb.jpg";
				if(ImageCache.checkCache(url)){
					imageView.setImageBitmap(ImageCache.getBitmap());
				}else{
					new ImageLoader(imageView, url, currentObjects.getString("id")).execute();
				} 
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
