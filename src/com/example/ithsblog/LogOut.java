package com.example.ithsblog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

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
		showPostListScreen(c);
	}
		
	
	private static void showPostListScreen(Context c){
		Intent intent = new Intent(c, PostList.class);
		c.startActivity(intent);	
	}

		
		
	/*private class LogoutUtil extends AsyncTask<String,Void,String>{

		private String sessionId = "";
		private String theResult ="";
		private SharedPreferences mySettings;
		private Editor editor;

		LogoutUtil(String sessionId, SharedPreferences mySettings, Editor editor) {
			this.sessionId = sessionId;
			this.mySettings = mySettings;
			this.editor = editor;
		}

		@Override
		protected String doInBackground(String... params) {
			try{
				HttpPost post = new HttpPost("http://jonasekstrom.se/ANNAT/iths_blog/logout.php"); 

				HttpClient client = new DefaultHttpClient(); 

				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				pairs.add(new BasicNameValuePair("sessionid", sessionId));
				post.setEntity(new UrlEncodedFormEntity(pairs));

				HttpResponse response = client.execute(post);

				BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

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

			if (mySettings.contains("sessionId")) {
				editor.remove("sessionId"); // will delete key sessionId
				editor.commit();
			}
			if (mySettings.contains("isAdmin")) {
				editor.remove("isAdmin"); // will delete key isAdmin
				editor.commit();
			}								
		}
	}	*/
}
