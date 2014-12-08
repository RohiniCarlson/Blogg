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
			if (username.getText().hashCode() == s.hashCode()){
				validateUserName();
			} else if (email.getText().hashCode() == s.hashCode()) {
				validateEmail();					
			} else if (password1.getText().hashCode() == s.hashCode()) {
				validatePassword(password1, password2);							
			} else if (password2.getText().hashCode() == s.hashCode()) {
				validatePassword(password2, password1);			
			}
			enableDisableRegisterNewButton();	
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
			if("EmailEmpty".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.required) + "Result = " + result,Toast.LENGTH_LONG).show();				
			} else if ("EmailInvalid".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_email) + "Result = " + result,Toast.LENGTH_LONG).show();								
			} else if ("NameEmpty".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.required) + "Result = " + result,Toast.LENGTH_LONG).show();				
			} else if ("PasswordEmpty".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.required) + "Result = " + result,Toast.LENGTH_LONG).show();				
			} else if ("RegistrationConfirmed".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_confirmed) + "Result = " + result,Toast.LENGTH_LONG).show();
				finish();
			} else if ("RegistrationPending".equals(result)) {				
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_pending) + "Result = " + result,Toast.LENGTH_LONG).show();
				finish();
			} else if ("MailSent".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.mail_sent) + "Result = " + result,Toast.LENGTH_LONG).show();
				finish();				
			} else if ("MailUnsent".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.mail_unsent) + "Result = " + result,Toast.LENGTH_LONG).show();
				finish();
			} else if ("-1".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.user_not_created) + "Result = " + result,Toast.LENGTH_LONG).show();
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "Result = " + result,Toast.LENGTH_LONG).show();
			}
		}		
	}
	
	private boolean validateUserName() {	
		if (Validation.isEmpty(username.getText())) {
			username.setError(getResources().getString(R.string.required));					
			 return false;
		}else{
			username.setError(null);
			return true;
		}		
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
	
	private boolean validatePassword(EditText pwd, EditText otherPwd) {
		if (Validation.isEmpty(pwd.getText())) {
			pwd.setError(getResources().getString(R.string.required));
			return false;
		}else if(!Validation.isAtleastEightCharactersLong(pwd.getText())) {
			pwd.setError(getResources().getString(R.string.atleast_eight_characters));
			return false;
		}else if (!Validation.passwordsAreEqual(pwd.getText().toString(), otherPwd.getText().toString())) {
			pwd.setError(getResources().getString(R.string.not_identical));
			return false;
		}else{
			pwd.setError(null);
			return true;
		}
	}	
	
	private void enableDisableRegisterNewButton() {
		if(validateUserName() && validateEmail() && validatePassword(password1, password2) && validatePassword(password2, password1)) {
			registerButton.setEnabled(true);
		}else{
			registerButton.setEnabled(false);
		}
	}
	
	private void registerNewUser(){
		new CreateNewUser(this, username.getText().toString(), email.getText().toString(), password1.getText().toString()).execute();				
	}	
}
