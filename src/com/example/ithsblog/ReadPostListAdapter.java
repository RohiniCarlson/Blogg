package com.example.ithsblog;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ReadPostListAdapter extends ArrayAdapter<Object>{

	private LayoutInflater inflater;
	private ArrayList list;
	private int pos;

	public ReadPostListAdapter(Activity activity, ArrayList<Object> list) {
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
		Object currentObjects = (Object) list.get(pos);
		
		// Fill the view
		// Image
		ImageView imageView = (ImageView) view.findViewById(R.id.item_imageView);
		//imageView.setImageResource(currentObjects.getImageId());
		// Title
	    TextView tv = (TextView)view.findViewById(R.id.item_titleView);
	    //tv.setText(currentObjects.getHeader());
	    // Date
	    TextView date = (TextView)view.findViewById(R.id.item_dateTextView);
	    //date.setText(""+currentObjects.getDate());
	    
		return view;

	}

}
