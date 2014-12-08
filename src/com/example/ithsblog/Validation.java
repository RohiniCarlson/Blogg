package com.example.ithsblog;

import android.text.TextUtils;

public class Validation {	
	
	public final static boolean isValidEmail(CharSequence target) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}
	
	public final static boolean isEmpty(CharSequence target) {
		return TextUtils.isEmpty(target);
	}
	
	public final static boolean passwordsAreEqual(String target1, String target2) {
		return target1.equals(target2);
	}
	
	public final static boolean isAtleastEightCharactersLong(CharSequence target) {
		return target.toString().length() >= 8;
	}
}
