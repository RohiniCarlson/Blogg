package com.example.ithsblog;

import android.content.Context;
import android.os.AsyncTask;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.util.Log;

public class CheckIfAuthor extends AsyncTask<String,Void,String>{

	String theResult;
	private PropertyChangeSupport pcs;

	// konstruktor
	public CheckIfAuthor(PropertyChangeListener c) {
		pcs = new PropertyChangeSupport(this);
		pcs.addPropertyChangeListener(c);
	}

	@Override
	protected String doInBackground(String... arg0) {

		try{

			String mail = "jonas@jonasekstrom.se";
			String password = "sha256:1000:rr/esrAf2Mf6eDIM5WfnCtiyNYt3MYLq:U9BW/fLgtwkJYeItP2iws52tbPbB9jsX";

			String link = "http://jonasekstrom.se/ANNAT/iths_blog/check_login.php?mail="
					+mail+"&password="+password;
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(link));
			HttpResponse response = client.execute(request);
			BufferedReader in = new BufferedReader
				(new InputStreamReader(response.getEntity().getContent()));

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
		pcs.firePropertyChange("checkIfAuthorDone", null, result);		
	}
}

