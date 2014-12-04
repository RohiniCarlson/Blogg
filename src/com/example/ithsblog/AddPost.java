package com.example.ithsblog;

import java.io.ByteArrayOutputStream;
import java.io.File;
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
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

public class AddPost extends AsyncTask<String,Void,String>{

	private String title;
	private String text;
	private Bitmap bitmap;

	// konstruktor, ta emot rubrik, text, eventuell bild
	public AddPost(String title, String text, Bitmap bitmap) {
		setTitle(title);
		setText(text);
		setImg(bitmap);
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Bitmap getImg() {
		return bitmap;
	}

	public void setImg(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	protected String doInBackground(String... params) {
		
		//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);          
		//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		//    	bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
		//    	byte [] byte_arr = stream.toByteArray();
		//    	String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);    
		//    	params.add(new BasicNameValuePair("image",image_str));
		
		try { 
			HttpPost post = new HttpPost("http://jonasekstrom.se/ANNAT/iths_blog/add_posts.php"); 
			HttpClient clienten = new DefaultHttpClient(); 
			
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			pairs.add(new BasicNameValuePair("postkey", "rkyvlbXFGLHJ52716879"));
			pairs.add(new BasicNameValuePair("title", getTitle()));
			pairs.add(new BasicNameValuePair("text", getText()));
						           
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream); //compress to which format you want.
			byte [] byte_arr = stream.toByteArray();
			String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT); 
			pairs.add(new BasicNameValuePair("image", image_str));	
			
			post.setEntity(new UrlEncodedFormEntity(pairs));
			
			HttpResponse response = clienten.execute(post); 

			int status = response.getStatusLine().getStatusCode();

			if (status == 200) { 

				HttpEntity entity = response.getEntity(); 
				String data = EntityUtils.toString(entity); 
				Log.d("hej","tjoo "+data);
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
	protected void onPostExecute(String string) { 

	} 

}
