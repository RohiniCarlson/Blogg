package com.example.ithsblog;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
			String link = "http://jonasekstrom.se/ANNAT/iths_blog/check_credentials.php?mail="+mail+"&password="+password;
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(link));
			HttpResponse response = client.execute(request);
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
