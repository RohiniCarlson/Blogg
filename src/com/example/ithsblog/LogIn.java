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
	private boolean emailValidatedSuccessfully = false;
	private boolean passwordValidatedSuccessfully = false;
	
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
			if (email.getText().hashCode() == s.hashCode())
		    {
		        if(!Validation.isValidEmail(s)){
		        	logInButton.setEnabled(false);
		        	emailValidatedSuccessfully = false;
		        	email.setError(getResources().getString(R.string.required) + " " + getResources().getString(R.string.invalid_email));
		        	
			    } else if (Validation.isEmpty(s)) {
			    	logInButton.setEnabled(false);
			    	emailValidatedSuccessfully = false;
			    	email.setError(getResources().getString(R.string.required));	
			    	
			    } else {
			    	email.setError(null);
			    	emailValidatedSuccessfully = true;
			    }
		    } else if (password.getText().hashCode() == s.hashCode()) {
		    	if (Validation.isEmpty(s)) {
		    		logInButton.setEnabled(false);
		    		passwordValidatedSuccessfully = false;
		    		password.setError(getResources().getString(R.string.required));	 
		        } else {
		        	password.setError(null);
		        	passwordValidatedSuccessfully = true;
		        }	            
		    }			
			enableLogInButton();
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
		if (event.getPropertyName().equals("checkIfAuthenticationDone")) {
			String result = (String) event.getNewValue();
			if("1".equals(result)) { // Credentials correct and registration is confirmed. Write to shared preferences.
				editor.putString("email", email.getText().toString());
				editor.putString("password", password.getText().toString());
				editor.commit();	
				Toast.makeText(getApplicationContext(),"Welcome! result = " + result ,Toast.LENGTH_SHORT).show();
				finish();
			} else if ("2".equals(result)) { // Credentials correct but registration is still pending.
				email.setText("");
				password.setText("");
				Toast.makeText(getApplicationContext(),"Please confirm your registration by clicking on the link sent to you via email. result = " + result,Toast.LENGTH_SHORT).show();
				finish();
			}
			else { // Credentials incorrect. Allow to re-enter. 
				email.setText("");
				password.setText("");
				Toast.makeText(getApplicationContext(),"Invalid Email/Password! Please re-enter if already a member.",Toast.LENGTH_SHORT).show();									
			}			
		}		
	}
	
	private void showRegisterNewScreen(){
		Intent intent = new Intent(LogIn.this, RegisterNewUser.class);
		startActivity(intent);
		finish();
	}
	
	private void doLogIn() {		
		new Authentication(this, email.getText().toString(), password.getText().toString()).execute();
	}
	
	private void enableLogInButton() {
		if (emailValidatedSuccessfully && passwordValidatedSuccessfully) {
			logInButton.setEnabled(true);
			// Reset the validation markers
			/*emailValidatedSuccessfully = false;
			passwordValidatedSuccessfully = false;*/
		}		
	}
}
