package com.example.ithsblog;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.AsyncTask;

public class GetPosts extends AsyncTask<String, Void, ArrayList<JSONObject>>{

	private PropertyChangeSupport pcs;
	private String data;
	private JSONArray jasonArray;
	private ArrayList<JSONObject> theList = new ArrayList<JSONObject>();


	// konstruktor
	public GetPosts(PropertyChangeListener c) {
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(c);
	}

	@Override 
	protected ArrayList<JSONObject> doInBackground(String... params) { 
		try {
			HttpPost post = new HttpPost("http://jonasekstrom.se/ANNAT/iths_blog/json_posts.php"); 
			HttpClient clienten = new DefaultHttpClient(); 

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("post1", "value2"));
			post.setEntity(new UrlEncodedFormEntity(pairs));

			HttpResponse response = clienten.execute(post); 

			int status = response.getStatusLine().getStatusCode();


			if (status == 200) {
				HttpEntity entity = response.getEntity();
				data = EntityUtils.toString(entity);
				jasonArray = new JSONArray(data);	
			}

			for(int i =0; i<jasonArray.length(); i++){
				theList.add(jasonArray.getJSONObject(i));
			}


		} catch (ParseException e1) { 
			e1.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return theList;

	}

	@Override 
	protected void onPostExecute(ArrayList<JSONObject> l) { 
		pcs.firePropertyChange("getPostsDone", null, theList); 
	} 

}