package com.example.ithsblog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
			if (email.getText().hashCode() == s.hashCode()) {
				validateEmail();
		    } else if (password.getText().hashCode() == s.hashCode()) {
		    	validatePassword();	            
		    }			
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
		
		if (event.getPropertyName().equals("checkIfAuthenticationDone")) {
			String result = (String) event.getNewValue();
			
			if ("StatusPending".equals(result)) { //Credentials correct but registration is still pending.
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_pending) + "Result = " + result,Toast.LENGTH_LONG).show();
				finish();				
			} else if ("LogInFailed".equals(result)) { //SessionId could not be created.
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.could_not_login) + "Result = " + result,Toast.LENGTH_LONG).show();
				finish();
			} else if ("NotFound".equals(result)) { //Credentials incorrect. Allow to re-enter.
				email.setText("");
				password.setText("");
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_credentials) + "Result = " + result,Toast.LENGTH_LONG).show();
			} else if (!result.isEmpty() && (result.length() > 0) && (result.indexOf("$") != -1)) {
				int index = result.indexOf("$");
				sessionId = result.substring(0, index);
				isAdmin = result.substring(index+3);
				editor.putString("sessionId", sessionId);
				if ("1".equals(isAdmin)) {
					editor.putBoolean("isAdmin", true);
				} else {
					editor.putBoolean("isAdmin", false);
				}				
				editor.commit();
				Toast.makeText(getApplicationContext(),getResources().getString(R.string.welcome) +" SessionID = " + sessionId + ", isAdmin = " + isAdmin + ", Result = " + result,Toast.LENGTH_LONG).show();
				finish();				
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
		} else {
			logInButton.setEnabled(false);
		}
	}
}
