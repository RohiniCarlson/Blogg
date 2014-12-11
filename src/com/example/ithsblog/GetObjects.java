package com.example.ithsblog;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

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
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class GetObjects extends AsyncTask<String, Void, ArrayList<JsonObjects>>{

	private PropertyChangeSupport pcs;
	ArrayList<JsonObjects> objectList = new ArrayList<JsonObjects>();
	
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
	protected ArrayList<JsonObjects> doInBackground(String... params) { 
		try { 
			HttpPost post = new HttpPost("http://jonasekstrom.se/ANNAT/iths_blog/json_posts.php"); 
			HttpClient clienten = new DefaultHttpClient(); 
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("offset", "20"));
			post.setEntity(new UrlEncodedFormEntity(pairs));
			
			HttpResponse response = clienten.execute(post); 

			int status = response.getStatusLine().getStatusCode();

			if (status == 200) { 

				HttpEntity entity = response.getEntity(); 
				String data = EntityUtils.toString(entity); 

				JSONArray jArray = new JSONArray(data); 

				for(int i=0; i<jArray.length(); i++){ 

					JSONObject jRealObject = jArray.getJSONObject(i); 
					// Log.d("hej","json: "+jRealObject.getString("id")+", "+jRealObject.getString("title")+", "+jRealObject.getString("text"));
					JsonObjects object = new JsonObjects(jRealObject.getInt("id"), jRealObject.getString("title"), jRealObject.getString("txt"));
					objectList.add(object);

				} 
				return objectList; 
			} 

		} catch (ParseException e1) { 
			e1.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} catch (JSONException e) { 
			e.printStackTrace(); 
		} 
		return objectList; 
	}

	@Override 
	protected void onPostExecute(ArrayList<JsonObjects> objectList) { 
		pcs.firePropertyChange("getObjectsDone", null, objectList); 
	} 
} 

