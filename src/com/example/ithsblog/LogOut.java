package com.example.ithsblog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class LogOut {
	
	public static void doLogOut(Context c) {
		
		SharedPreferences mySettings = PreferenceManager.getDefaultSharedPreferences(c);
		Editor editor = mySettings.edit();
		
		if (mySettings.contains("sessionId")) {
			editor.remove("sessionId"); // will delete key sessionId
			editor.commit();
		}
		if (mySettings.contains("isAdmin")) {
			editor.remove("isAdmin"); // will delete key isAdmin
			editor.commit();
		}
		Toast.makeText(c, c.getResources().getString(R.string.logged_out), Toast.LENGTH_SHORT).show();
		showPostListScreen(c);
	}
		
	
	private static void showPostListScreen(Context c){
		Intent intent = new Intent(c, PostList.class);
		c.startActivity(intent);	
	}
}
