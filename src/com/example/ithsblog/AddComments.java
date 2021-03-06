package com.example.ithsblog;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayOutputStream;
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

import android.graphics.Bitmap;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class AddComments extends AsyncTask<String,Void,String>{

	private String theId;
	private String text;
	private String sessionid;
	private PropertyChangeSupport pcs;

	// konstruktor, ta emot rubrik, text, eventuell bild
	public AddComments(PropertyChangeListener c, String theId, String text, String user_id) {
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(c);
		sessionid = user_id;
		this.text = text;
		this.theId = theId;
	}


	protected String doInBackground(String... params) {

		try { 
			HttpPost post = new HttpPost("http://jonasekstrom.se/ANNAT/iths_blog/add_comments.php"); 
			HttpClient clienten = new DefaultHttpClient(); 

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("postkey", "rkyvlbXFGLHJ52716879"));
			pairs.add(new BasicNameValuePair("post_id", theId));
			pairs.add(new BasicNameValuePair("session_id", sessionid));
			pairs.add(new BasicNameValuePair("comment", text));
			
			post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));
			
			HttpResponse response = clienten.execute(post); 

			int status = response.getStatusLine().getStatusCode();

			if (status == 200) { 

				HttpEntity entity = response.getEntity(); 
				String data = EntityUtils.toString(entity); 
				Log.d("hej","tjoo comment "+sessionid);
				return data; 
			} 

		} catch (ParseException e1) { 
			e1.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} 
		return null; 
	}

	@Override 
	protected void onPostExecute(String result) { 		
		pcs.firePropertyChange("addCommentsDone", null, result);
	} 

}