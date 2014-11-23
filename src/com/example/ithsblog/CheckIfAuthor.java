package com.example.ithsblog;

import android.os.AsyncTask;
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
	public static boolean isAuthor;

	// konstruktor
	public CheckIfAuthor() {
	}

	@Override
	protected String doInBackground(String... arg0) {

		try{
			String username = "author";
			String password = "zlatanspassword";

			String link = "http://jonasekstrom.se/ANNAT/iths_blog/check_login.php?username="
					+username+"&password="+password;
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
		
		this.isAuthor = false;
		Log.d("hej",result);		
	}
}

