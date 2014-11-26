package com.example.ithsblog;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Log;

public class GetObjects extends AsyncTask<String, Void, Boolean>{

	private PropertyChangeSupport pcs;

	// konstruktor
	public GetObjects(PropertyChangeListener c) {
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(c);
	}

//	@Override 
//	protected void onPreExecute() { 
//		super.onPreExecute();  
//	} 

	@Override 
	protected Boolean doInBackground(String... params) { 
		try { 
			HttpPost post = new HttpPost("http://jonasekstrom.se/ANNAT/iths_blog/json_posts.php"); 
			HttpClient clienten = new DefaultHttpClient(); 
			HttpResponse response = clienten.execute(post); 

			int status = response.getStatusLine().getStatusCode();

			if (status == 200) { 

				HttpEntity entity = response.getEntity(); 
				String data = EntityUtils.toString(entity); 

				JSONArray jArray = new JSONArray(data); 

				String hej = "Lenght: " + jArray.length();
	
				Log.d("hej",hej);
				
				for(int i=0; i<jArray.length(); i++){ 

					JSONObject jRealObject = jArray.getJSONObject(i); 
					Log.d("hej","json: "+jRealObject.getString("name")+" , "+jRealObject.getString("mail"));					

				} 
				return true; 
			} 

		} catch (ParseException e1) { 
			e1.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} catch (JSONException e) { 
			e.printStackTrace(); 
		} 
		return false; 
	}

	@Override 
	protected void onPostExecute(Boolean result) { 
		pcs.firePropertyChange("checkIfAuthorDone", null, "hej"); 
	} 
} 

