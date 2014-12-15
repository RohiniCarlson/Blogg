package com.example.ithsblog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class LogIn extends ActionBarActivity implements PropertyChangeListener{
	
	private Button logInButton, registerNewButton;
	private EditText email, password;
	private SharedPreferences mySettings;
	private Editor editor;
	
	private OnClickListener logInButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v) { 
			doLogIn();
		}		
	};
	
	private OnClickListener registerNewButtonListener = new OnClickListener(){
		@Override
		public void onClick(View v) { 
			showRegisterNewScreen();
		}
	};
	
	private TextWatcher commonTextWatcher = new TextWatcher() {	
		@Override
		public void afterTextChanged(Editable s) {
			enableDisableLogInButton();
		}
	
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		// TODO Auto-generated method stub		
		}
	
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
		}		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		logInButton = (Button)findViewById(R.id.log_in_button);
		logInButton.setOnClickListener(logInButtonListener);
		logInButton.setEnabled(false);
		registerNewButton = (Button)findViewById(R.id.register_new_button);
		registerNewButton.setOnClickListener(registerNewButtonListener);
		email = (EditText)findViewById(R.id.email_address_edit);
		email.addTextChangedListener(commonTextWatcher);
		password = (EditText)findViewById(R.id.password_edit);
		password.addTextChangedListener(commonTextWatcher);
		
		// Create shared preferences -- can be accessed by all activities in application
		mySettings = PreferenceManager.getDefaultSharedPreferences(this);
		editor = mySettings.edit();		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		enableDisableLogInButton();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String sessionId = "";
		String isAdmin = "";
		boolean adminMarker = false;

		if ("checkIfAuthenticationDone".equals(event.getPropertyName())) {
			String result = (String) event.getNewValue();
			
			if ("StatusPending".equals(result)) { //Credentials correct but registration is still pending.
				showPopup(LogIn.this, 600, 400, R.id.login_register_popup_layout, R.layout.log_in_register_popup, R.string.registration_pending, true);				
			} else if ("LogInFailed".equals(result)) { //SessionId could not be created.
				showPopup(LogIn.this, 600, 400, R.id.login_register_popup_layout, R.layout.log_in_register_popup, R.string.could_not_login, true);
			} else if ("NotFound".equals(result)) { //Credentials incorrect. Allow to re-enter.
				showPopup(LogIn.this, 600, 400, R.id.login_register_popup_layout, R.layout.log_in_register_popup, R.string.invalid_credentials, false);
			} else if (!result.isEmpty() && (result.length() > 0) && (result.indexOf("$") != -1)) {
				sessionId = result.substring(0, result.indexOf("$"));
				editor.putString("sessionId", sessionId);
				isAdmin = result.substring(result.indexOf("$")+3);
				if ("1".equals(isAdmin)) {
					adminMarker = true;
					editor.putBoolean("isAdmin", true);
				} else {
					editor.putBoolean("isAdmin", false);
				}			
				editor.commit();
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.welcome), Toast.LENGTH_LONG).show();
				if (adminMarker) {
					showPostsScreen();
				} else {
					finish();
				}							
			} else {
				Toast.makeText(getApplicationContext(), "Result = " + result, Toast.LENGTH_LONG).show();
				finish();
			}						
		}		
	}
	
	private void showRegisterNewScreen(){
		Intent intent = new Intent(LogIn.this, RegisterNewUser.class);
		startActivity(intent);
		finish();
	}
	
	private void showPostsScreen(){
		Intent intent = new Intent(LogIn.this, Posts.class);
		startActivity(intent);
		finish();
	}
	
	private void doLogIn() {		
		//Password to be encrypted once server side decryption support is in place.
		//String encyptedPassword = EncryptionUtilities.encodePassword(this, password.getText().toString());
		String encyptedPassword = password.getText().toString();
		new Authentication(this, email.getText().toString(), encyptedPassword).execute();
	}
	
	private boolean validateEmail() {
		if(!Validation.isValidEmail(email.getText())){
		    email.setError(getResources().getString(R.string.invalid_email));
			return false;
		} else if (Validation.isEmpty(email.getText())) {
   			email.setError(getResources().getString(R.string.required));
			return false;
		} else {
			email.setError(null);
			return true;
		}		
	}
	
	private boolean validatePassword() {
		if (Validation.isEmpty(password.getText())) {
			password.setError(getResources().getString(R.string.required));	
			return false;
		} else {
			password.setError(null);
			return true;
		}
	}
	
	private void enableDisableLogInButton() {
		if (validateEmail() && validatePassword()) {
			logInButton.setEnabled(true);
			logInButton.setBackgroundResource(R.drawable.check);
		} else {
			logInButton.setEnabled(false);					
			logInButton.setBackgroundColor(Color.parseColor("#D8D8D8"));
		}
	}
	
	// Displays the pop-up dialog window
	private void showPopup(final Activity context, int width, int height, int layoutId, int xml_layout_id, int msg_id, final boolean closeActivity) {
		int popupWidth = width;
		int popupHeight = height;

		// Inflate the appropriate popup_layout.xml, defined by Id
		LinearLayout viewGroup = (LinearLayout) context.findViewById(layoutId);
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(xml_layout_id, viewGroup);

		// Create the pop-up dialog
		final PopupWindow popup = new PopupWindow(context);
		popup.setContentView(layout);
		popup.setWidth(popupWidth);
		popup.setHeight(popupHeight);
		popup.setFocusable(true);

		// Display the pop-up dialog at the specified location, + offsets.
		popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
		
		// Get reference to TextView and set specified message
		TextView infoText = (TextView) layout.findViewById(R.id.info_text);		
		infoText.setText(getResources().getString(msg_id));				

		// Get a reference to Ok button, and close the pop-up dialog when clicked.
		// Close the current activity as well, if so required.
		Button close = (Button) layout.findViewById(R.id.ok_button);
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popup.dismiss();
				if (closeActivity) {
					context.finish();
				}	
			}
		});
	}
}
