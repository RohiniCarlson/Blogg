package com.example.ithsblog;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterNewUser extends ActionBarActivity {
	
Button register_button;
	
	OnClickListener register_button_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			registerNewUser();
		}		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_new_user);
		register_button = (Button)findViewById(R.id.register_button);
		register_button.setOnClickListener(register_button_listener);
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
	
	private void registerNewUser(){
		// Check if entered passwords are equal.
			// If not, display appropriate error and ask to reenter.
		// Check if entered username/password already exists.
			// If it does, display appropriate error and ask user to reenter.
		// If all clear, add new user to db user table.
		// Save username/password locally on phone as well.
		String name;
		EditText userName = (EditText)findViewById(R.id.new_username_edit);
		name = userName.getText().toString();
		Toast.makeText(getApplicationContext(),"Successfully registered! Welcome, " + name + "!!",Toast.LENGTH_LONG).show();
	}
}
