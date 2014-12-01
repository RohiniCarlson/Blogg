package com.example.ithsblog;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends ActionBarActivity {
	
private Button sign_in_button, register_new_button;
	
	private OnClickListener sign_in_button_listener = new OnClickListener(){
		@Override
		public void onClick(View v) { 
			doLogIn();
		}		
	};
	
	private OnClickListener register_new_button_listener = new OnClickListener(){
		@Override
		public void onClick(View v) { 
			showRegisterNewScreen();
		}
	};	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_log_in);
		sign_in_button = (Button)findViewById(R.id.sign_in_button);
		sign_in_button.setOnClickListener(sign_in_button_listener);
		register_new_button = (Button)findViewById(R.id.register_new_button);
		register_new_button.setOnClickListener(register_new_button_listener);
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
	
	private void showRegisterNewScreen(){
		Intent intent = new Intent(LogIn.this, RegisterNewUser.class);
		startActivity(intent);
	}
	
	private void doLogIn() {
		//If entered password matches entered user name --> Log in successful!
		//If entered password does NOT match entered user name --> Log in unsuccessful! Try again!
		String name;
		EditText userName = (EditText)findViewById(R.id.username_edit);
		name = userName.getText().toString();
		Toast.makeText(getApplicationContext(),"Successfully logged in. Welcome " + name + "!",Toast.LENGTH_LONG).show();
	}
}
