package com.example.ithsblog;



import java.io.InputStream;
import java.net.URL;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoader extends AsyncTask<String, Void, String>{

	private String imageURL;
	private ImageView view;
	private String id;

	
	public ImageLoader(ImageView view, String imageURL, String id){
		this.view = (ImageView) view;
		this.imageURL = imageURL;
		this.id = id;
		
	}
	@Override
	protected String doInBackground(String... params) {
		
				try {
				        URL url = new URL(imageURL);
				        HttpGet httpRequest = null;

				        httpRequest = new HttpGet(url.toURI());

				        HttpClient httpclient = new DefaultHttpClient();
				        HttpResponse response = (HttpResponse) httpclient
				                .execute(httpRequest);

				        HttpEntity entity = response.getEntity();
				        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
				        InputStream input = b_entity.getContent();

				        Bitmap bitmap = BitmapFactory.decodeStream(input);

				        ImageCache.cacheImage(id, bitmap);
				        
				        view.setImageBitmap(bitmap);
				        
				        

				    } catch (Exception ex) {

				    }
		return null;
	}
}
