package com.example.ithsblog;

import java.io.File;
import java.util.ArrayList;

import android.net.Uri;
import android.os.AsyncTask;

public class AddPost extends AsyncTask<String,Void,String>{

	private String title;
	private String text;
	private File img;

	// konstruktor, ta emot rubrik, text, eventuell bild
	public AddPost(String title, String text, File img) {
		setTitle(title);
		setText(text);
		setImg(img);
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

	public File getImg() {
		return img;
	}

	public void setImg(File img) {
		this.img = img;
	}

	protected String doInBackground(String... params) {
		
		//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);          
		//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		//    	bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream); //compress to which format you want.
		//    	byte [] byte_arr = stream.toByteArray();
		//    	String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);    
		//    	params.add(new BasicNameValuePair("image",image_str));
		
		return "hej";
		
	}

	@Override 
	protected void onPostExecute(String string) { 

	} 

}
