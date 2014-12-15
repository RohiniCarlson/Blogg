package com.example.ithsblog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

public class RegisterNewUser extends ActionBarActivity implements PropertyChangeListener{
	
	private Button registerButton;	
	private EditText username, email, password1, password2;
	
	OnClickListener registerButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			registerNewUser();
		}		
	};
			
	private TextWatcher commonTextWatcher = new TextWatcher() {		
		@Override
		public void afterTextChanged(Editable s) {									
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		enableDisableRegisterNewButton();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals("createNewUserDone")) {
			String result = (String) event.getNewValue();
			// Result from server side validations
			if("EmailEmpty".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.required) + "Result = " + result,Toast.LENGTH_LONG).show();				
			} else if ("EmailInvalid".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.invalid_email) + "Result = " + result,Toast.LENGTH_LONG).show();								
			} else if ("NameEmpty".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.required) + "Result = " + result,Toast.LENGTH_LONG).show();				
			} else if ("PasswordEmpty".equals(result)) {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.required) + "Result = " + result,Toast.LENGTH_LONG).show();				
			} else if ("RegistrationConfirmed".equals(result)) {
				showPopup(RegisterNewUser.this, 600, 400, R.id.login_register_popup_layout, R.layout.log_in_register_popup, R.string.registration_confirmed);
			} else if ("RegistrationPending".equals(result)) {				
				showPopup(RegisterNewUser.this, 600, 400, R.id.login_register_popup_layout, R.layout.log_in_register_popup, R.string.registration_pending);
			} else if ("MailSent".equals(result)) {	
				showPopup(RegisterNewUser.this, 600, 500, R.id.login_register_popup_layout, R.layout.log_in_register_popup, R.string.mail_sent);
			} else if ("MailUnsent".equals(result)) {
				showPopup(RegisterNewUser.this, 600, 500, R.id.login_register_popup_layout, R.layout.log_in_register_popup, R.string.mail_unsent);
			} else if ("NotCreated".equals(result)) {
				showPopup(RegisterNewUser.this, 600, 500, R.id.login_register_popup_layout, R.layout.log_in_register_popup, R.string.user_not_created);
			} else {
				Toast.makeText(getApplicationContext(), "Result = " + result,Toast.LENGTH_LONG).show();
			}
		}		
	}
	
	// Displays the pop-up dialog window
		private void showPopup(final Activity context, int width, int height, int layoutId, int xml_layout_id, int msg_id) {
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
			// Close the current activity as well.
			Button close = (Button) layout.findViewById(R.id.ok_button);
			close.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					popup.dismiss();
					context.finish();
				}
			});
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
			registerButton.setBackgroundResource(R.drawable.check);
		}else{
			registerButton.setEnabled(false);
			registerButton.setBackgroundColor(Color.parseColor("#D8D8D8"));
		}
	}
	
	private void registerNewUser(){
		// Password should be encrypted using EncryptionUtilities.encodePassword()
		new CreateNewUser(this, username.getText().toString(), email.getText().toString(), password1.getText().toString()).execute();				
	}
	
}
