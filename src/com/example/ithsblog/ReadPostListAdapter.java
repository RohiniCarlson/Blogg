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

public class ReadPostListAdapter extends ArrayAdapter<JSONObject>{

	private LayoutInflater inflater;
	private ArrayList list;
	private int pos;
	private String id;
	private int count = 10;
	private PropertyChangeListener pcl;

	public ReadPostListAdapter(Activity activity, ArrayList<JSONObject> list, String id) {
		super(activity, R.layout.posts_list_item, list);
		this.list = list;
		this.id = id;
		pcl = (PropertyChangeListener) activity;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}
	public View getView(int pos, View currentView, ViewGroup parent){
		View view = null;

		// Make sure we have a view to work on
		if(currentView == null){
			view = inflater.inflate(R.layout.comment, parent, false);
		}else {
			view = currentView;
		}

		// Find the post
		JSONObject currentObjects = (JSONObject) list.get(pos);

		// Fill the list
//		if(pos == list.size()-1 && count == list.size()){
//			new GetComments(pcl, id, ""+count).execute();
//			count = count + 10;
//		}

		// Fill the view
		try {
			// Name
			TextView textView = (TextView)view.findViewById(R.id.item_nameView);	    
			textView.setText(currentObjects.getString("name"));	
			// Comment
			TextView commentView = (TextView)view.findViewById(R.id.item_commentView);
			commentView.setText(currentObjects.getString("commenttext"));
			// Date
			TextView dateView = (TextView)view.findViewById(R.id.item_dateTextView);
			dateView.setText(currentObjects.getString("date"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return view;

	}

}
