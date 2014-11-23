package com.example.ithsblog;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;




public class Posts extends ActionBarActivity {
	private static final int CAPTURE_IMAGE_CAPTURE_CODE = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posts);
		Intent i;
		i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(i, CAPTURE_IMAGE_CAPTURE_CODE);

	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//check which intent has sent back data
		   if (requestCode == CAPTURE_IMAGE_CAPTURE_CODE) {
		//check whether the process was success
		      if (resultCode == RESULT_OK) {
		         //perform your own functionality, I have displayed a Toast indication success
		         Toast.makeText(this, "Image Captured", Toast.LENGTH_LONG).show();
		      } 
		//Check if the capture failed and perform your functionality
		      else if (resultCode == RESULT_CANCELED) {
		         Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
		      }
		  }
		
		Button cam = (Button) findViewById(R.id.upload_button);

		cam.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Posts.this, Posts.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				startActivity(intent);
				
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.posts, menu);
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
}
