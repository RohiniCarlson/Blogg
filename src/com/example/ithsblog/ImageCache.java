package com.example.ithsblog;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class ImageCache {
	private static String img;
	private static Bitmap bitmap;
	private static LruCache<String, Bitmap> bitmapCache = new LruCache<String, Bitmap>(10 * 1024 * 1024);
	//Cache Size = 10 * 1024 * 1024; // 10MiB
	
	public ImageCache(){
	}
	
	public ImageCache(String img){
		ImageCache.img = img;	
	}
	
	public static Bitmap getBitmap(){
		return bitmap;
	}
	
	public static void cacheImage(String imgURL, Bitmap b){
		bitmapCache.put(imgURL, b);
	}
	
	public static boolean checkCache(String img){
		
		if(bitmapCache.get(img) == null){
        return false;
        }else{
        bitmap = bitmapCache.get(img);
        return true;
        }  
	}
}
