package com.example.ithsblog;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class LogOut {
	
	public static void doLogOut(Context c) {
		
		String sessionId = "";
		
		SharedPreferences mySettings = PreferenceManager.getDefaultSharedPreferences(c);
		Editor editor = mySettings.edit();
		
		if (mySettings.contains(sessionId)) {
			sessionId = mySettings.getString(sessionId, "");
		}
		
		//new LogoutUtil(c,sessionId).execute();
		if (mySettings.contains("sessionId")) {
			editor.remove("sessionId"); // will delete key sessionId
			editor.commit();
		}
		if (mySettings.contains("isAdmin")) {
			editor.remove("isAdmin"); // will delete key isAdmin
			editor.commit();
		}				
	}
}
