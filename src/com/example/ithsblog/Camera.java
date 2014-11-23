package com.example.ithsblog;
import android.os.Bundle;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Camera extends Activity {
	
	
	
	
 private static final int CAPTURE_IMAGE_CAPTURE_CODE = 0;
 Intent i;
 private ImageButton ib;

  @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  ib = (ImageButton) findViewById(R.id.buttonToast);

   ib.setOnClickListener(new OnClickListener() {
   @Override
   public void onClick(View v) {
    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(i, CAPTURE_IMAGE_CAPTURE_CODE);
   }
  });
 }
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  if (requestCode == CAPTURE_IMAGE_CAPTURE_CODE) {
   if (resultCode == RESULT_OK) {
    Toast.makeText(this, "Image Captured", Toast.LENGTH_LONG).show();
   } else if (resultCode == RESULT_CANCELED) {
    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
   }
  }
 }
 

}
