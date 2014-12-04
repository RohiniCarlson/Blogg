package com.example.ithsblog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class LogOut {
	public static void doLogOut(Context c) {
		SharedPreferences mySettings = PreferenceManager.getDefaultSharedPreferences(c);
		Editor editor = mySettings.edit();
		if (mySettings.contains("email")) {
			editor.remove("email"); // will delete key email
			editor.commit();
		}
		if (mySettings.contains("password")) {
			editor.remove("password"); // will delete key password
			editor.commit();
		}				
	}
}
