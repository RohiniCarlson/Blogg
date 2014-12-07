package com.example.ithsblog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

public class RegisterNewUser extends ActionBarActivity implements PropertyChangeListener{
	
	private Button registerButton;	
	private EditText username, email, password1, password2;
	private boolean userNameValidatedSuccessfully = false;
	private boolean emailValidatedSuccessfully = false;
	private boolean passwordsValidatedSuccessfully = false;
	private boolean pswd1Validated = false;
	private boolean pswd2Validated = false;
	private SharedPreferences mySettings;
	private Editor editor;
	
	OnClickListener registerButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			registerNewUser();
		}		
	};
	
	private TextWatcher commonTextWatcher = new TextWatcher() {		
		@Override
		public void afterTextChanged(Editable s) {									
			if (username.getText().hashCode() == s.hashCode())
			{
				if (Validation.isEmpty(s)) {
					username.setError(getResources().getString(R.string.required));					
					registerButton.setEnabled(false);
				}else{
					username.setError(null);
					userNameValidatedSuccessfully = true;
				}				
			}
			else if (email.getText().hashCode() == s.hashCode()) {
				if(!Validation.isValidEmail(s)){
					registerButton.setEnabled(false);
					email.setError(getResources().getString(R.string.required) + " " + getResources().getString(R.string.invalid_email));

				} else if (Validation.isEmpty(s)) {
					registerButton.setEnabled(false);
					email.setError(getResources().getString(R.string.required));	

				} else {
					email.setError(null);
					emailValidatedSuccessfully = true;
				}				
			}
			else if (password1.getText().hashCode() == s.hashCode()) {
				if (Validation.isEmpty(s)) {
					password1.setError(getResources().getString(R.string.required));
					registerButton.setEnabled(false);
				}else if(!Validation.isAtleastEightCharactersLong(s)) {
					password1.setError(getResources().getString(R.string.atleast_eight_characters));
					registerButton.setEnabled(false);
				}else{
					password1.setError(null);
					pswd1Validated = true;
				}				
			}
			else if (password2.getText().hashCode() == s.hashCode()) {
				if (Validation.isEmpty(s)) {
					password2.setError(getResources().getString(R.string.required));
					registerButton.setEnabled(false);
				}else if(!Validation.isAtleastEightCharactersLong(s)) {
					password2.setError(getResources().getString(R.string.atleast_eight_characters));
					registerButton.setEnabled(false);
				}else{
					password2.setError(null);
					pswd2Validated = true;
				}
			}
			if(pswd1Validated && pswd2Validated) {
				if(!Validation.passwordsAreEqual(password1.getText().toString(), password2.getText().toString())) {
					password2.setError(getResources().getString(R.string.not_identical));
				}else {
					password2.setError(null);
					passwordsValidatedSuccessfully = true;
				}
			}			
			enableRegisterNewButton();	
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_new_user);
		registerButton = (Button)findViewById(R.id.register_button);
		registerButton.setOnClickListener(registerButtonListener);
		registerButton.setEnabled(false);
		username = (EditText)findViewById(R.id.new_username_edit);
		username.addTextChangedListener(commonTextWatcher);
		email = (EditText)findViewById(R.id.email_address_edit);
		email.addTextChangedListener(commonTextWatcher);
		password1 = (EditText)findViewById(R.id.new_password1_edit);
		password1.addTextChangedListener(commonTextWatcher);
		password2 = (EditText)findViewById(R.id.new_password2_edit);
		password2.addTextChangedListener(commonTextWatcher);
		
		// Get default shared preferences
		mySettings = PreferenceManager.getDefaultSharedPreferences(this);
		editor = mySettings.edit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register_new_user, menu);
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
		if (event.getPropertyName().equals("createNewUserDone")) {
			String result = (String) event.getNewValue();
			if("1".equals(result)) { // User can be created. Saved to database. Write to shared preferences. 
				editor.putString("name", username.getText().toString());
				editor.putString("email", email.getText().toString());
				editor.putString("password", password1.getText().toString());
				editor.commit();						
				Toast.makeText(getApplicationContext(),"Successfully registered! result = " + result ,Toast.LENGTH_SHORT).show();
				finish();				
			} else if ("0".equals(result)){ // User exists - user not created.
				username.setText("");
				email.setText("");
				password1.setText("");
				password2.setText("");
				Toast.makeText(getApplicationContext(),"Email/Password already in use. Please re-enter. result = " + result,Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),"Result = " + result,Toast.LENGTH_LONG).show();
			}
		}		
	}
	
	private void registerNewUser(){
		new CreateNewUser(this, username.getText().toString(), email.getText().toString(), password1.getText().toString()).execute();				
	}
	
	private void enableRegisterNewButton() {
		if(userNameValidatedSuccessfully && emailValidatedSuccessfully && passwordsValidatedSuccessfully) {
			registerButton.setEnabled(true);
			userNameValidatedSuccessfully = false;
			emailValidatedSuccessfully = false;
			passwordsValidatedSuccessfully = false;
		}	
	}
}
