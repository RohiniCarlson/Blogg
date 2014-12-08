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

public class DeletePost extends AsyncTask<String,Void,String>{

	private PropertyChangeSupport pcs;
	private String delete_id;
	
	// konstruktor
	public DeletePost(PropertyChangeListener c, String delete_id) {
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(c);
		
		this.delete_id = delete_id;
	}

	//	@Override 
	//	protected void onPreExecute() { 
	//		super.onPreExecute();  
	//	} 

	@Override 
	protected String doInBackground(String... params) { 
		try { 
			HttpPost post = new HttpPost("http://jonasekstrom.se/ANNAT/iths_blog/delete_posts.php"); 
			HttpClient clienten = new DefaultHttpClient(); 
			
			String delete = "" + this.delete_id;			
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("postkey", "rkyvlbXFGLHJ52716879"));
			pairs.add(new BasicNameValuePair("post_id", delete));
			post.setEntity(new UrlEncodedFormEntity(pairs));
			
			HttpResponse response = clienten.execute(post); 

			int status = response.getStatusLine().getStatusCode();

			if (status == 200) { 

				HttpEntity entity = response.getEntity(); 
				String data = EntityUtils.toString(entity); 

				return data; 
			} 

		} catch (ParseException e1) { 
			e1.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		return "0"; 
	}

	@Override 
	protected void onPostExecute(String result) { 
		pcs.firePropertyChange("deletePostDone", null, result); 
	} 
} 