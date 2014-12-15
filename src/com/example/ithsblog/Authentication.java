package com.example.ithsblog;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class Authentication extends AsyncTask<String,Void,String>{
	private String theResult;
	private PropertyChangeSupport pcs;
	private String mail, password;

	// Constructor
	public Authentication(PropertyChangeListener c, String mail, String password) {
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(c);
		this.mail = mail;
		this.password = password;
	}

	@Override
	protected String doInBackground(String... params) {
		try{
			HttpPost post = new HttpPost("http://jonasekstrom.se/ANNAT/iths_blog/check_credentials.php"); 
			
			HttpClient client = new DefaultHttpClient();
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("mail", mail));
			pairs.add(new BasicNameValuePair("password", password));
			post.setEntity(new UrlEncodedFormEntity(pairs));
			
			HttpResponse response = client.execute(post);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((in.readLine()) != null) {
				theResult = in.readLine();
				break;
			}			
			in.close();
			return theResult;
		}catch(Exception e){
			return new String("Exception: " + e.getMessage());
		}
	}
	
	@Override
	protected void onPostExecute(String result){
		pcs.firePropertyChange("checkIfAuthenticationDone", null, result);		
	}
}
