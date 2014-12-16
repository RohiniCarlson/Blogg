
package com.example.ithsblog;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
// import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
// import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Posts extends ActionBarActivity implements PropertyChangeListener {
	// private AddPost addPost;
	private static String logtag = "Camera";
	private static int TAKE_PICTURE = 1;
	private Uri imageUri;
	private Bitmap bitmap;
	private Button uploadButton;
	private String id, newText, newTitle;
	private Intent intentEdit;
	
	
	private OnClickListener uploadButtonListener = new OnClickListener() {
		public void onClick(View v) { 
			
			if(intentEdit.hasExtra("ID")){
			edit();
			}else{
			setView();
			}
		}
	};
	
	private OnClickListener cameraListener = new OnClickListener(){
		public void onClick(View v){
			takePhoto(v);
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_posts);

		Button cameraButton = (Button) findViewById(R.id.upload_button);
		cameraButton.setOnClickListener(cameraListener);
		uploadButton = (Button) findViewById(R.id.up_button);
		uploadButton.setOnClickListener(uploadButtonListener);
		uploadButton.setEnabled(false);
		intentEdit = getIntent();
		
		if(intentEdit.hasExtra("ID")){
			editPost();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		supportInvalidateOptionsMenu();
	}
	
	public void editPost() {
		
			newTitle = intentEdit.getStringExtra("TITLE");
			newText = intentEdit.getStringExtra("TEXT");
			id = intentEdit.getStringExtra("ID");
			
			//Title
			EditText editTitle = (EditText) findViewById(R.id.edit_view_head);				
			editTitle.setText(newTitle);
			//Text
			EditText editTxt = (EditText) findViewById(R.id.edit_view_regular);				
			editTxt.setText(newText);
			//Image
			ImageView imageView = (ImageView)findViewById(R.id.image_view);
			String url = "http://jonasekstrom.se/ANNAT/iths_blog/images/"+id+".jpg";
			if(ImageCache.checkCache(id)){
				imageView.setImageBitmap(ImageCache.getBitmap());
			}else{
				new ImageLoader(imageView, url, id).execute();			
			}
			uploadButton.setEnabled(true);
			uploadButton.setBackgroundResource(R.drawable.check);
	}

	private void edit(){
		
		// Get the title
		EditText editTitle = (EditText) findViewById(R.id.edit_view_head);				
		newTitle = editTitle.getText().toString();
		// Get the text
		EditText editTxt = (EditText) findViewById(R.id.edit_view_regular);				
		newText = editTxt.getText().toString();
		// Get the image
		ImageView img = (ImageView)findViewById(R.id.image_view);
		bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

		
		new EditPost(newTitle, newText, intentEdit.getStringExtra("ID"), bitmap).execute();
		Intent editIntent = new Intent(Posts.this, PostList.class);
		editIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(editIntent);
		finish();
	}
	
	private void setView() {

			//Rostad macka med bild.
			Toast toast = new Toast(Posts.this);
			ImageView view = new ImageView(Posts.this); 
			view.setImageResource(R.drawable.upload); 
			toast.setView(view); 
			toast.show();

			try{

				EditText editTitle = (EditText) findViewById(R.id.edit_view_head);				
				String title = editTitle.getText().toString();

				EditText editTxt = (EditText) findViewById(R.id.edit_view_regular);				
				String text = editTxt.getText().toString();
				
				// rotate was here					
				
				new AddPost(Posts.this, title,text,bitmap).execute();				
				uploadButton.setEnabled(false);
				uploadButton.setBackgroundResource(R.drawable.grey);

			}catch(Exception e){
				Log.d(logtag, e.toString());
			}	
	}
	


	private void takePhoto(View v){
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"pictures.jpg");
		imageUri = Uri.fromFile(photo);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(intent, TAKE_PICTURE);
	}

	private int exifToDegrees(int exifOrientation) {        
		if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; } 
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; } 
		else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }            
		return 0;    
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent){
		super.onActivityResult(requestCode, resultCode, intent);

		if(resultCode == Activity.RESULT_OK){

			try {
				Uri selectedImage = imageUri;
				getContentResolver().notifyChange(selectedImage, null);

				ImageView imageView = (ImageView)findViewById(R.id.image_view);
				ContentResolver cr = getContentResolver();
				bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);

				// Scale image
				bitmap = scaleImage(bitmap);

				// rotate image
				bitmap = rotateImage(bitmap);

				imageView.setImageBitmap(bitmap);

			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			uploadButton.setBackgroundResource(R.drawable.check);
			uploadButton.setEnabled(true);
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		inflateMenu(menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		inflateMenu(menu);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_listan) {	
			showPostList();
			return true;
		}else if (id == R.id.action_login) {
			showSignInScreen();
			return true;
		} else if (id == R.id.action_logout) {
			LogOut.doLogOut(this);
			supportInvalidateOptionsMenu();
			return true;
		}

		
		return super.onOptionsItemSelected(item);
	}


	private void inflateMenu(Menu menu) {
		menu.clear();
		SharedPreferences mySettings = PreferenceManager.getDefaultSharedPreferences(this);		
		if (mySettings.contains("sessionId") && mySettings.contains("isAdmin")) {			
			getMenuInflater().inflate(R.menu.logout, menu);
		} else {
			getMenuInflater().inflate(R.menu.post_list, menu);
		}
		menu.findItem(R.id.action_new_post).setVisible(false);
	}

	private void showSignInScreen() {
		Intent intent = new Intent(Posts.this, LogIn.class);
		startActivity(intent);
	}
	private void showPostList() {
		Intent intent = new Intent(Posts.this, PostList.class);
		startActivity(intent);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals("postAdded")) {									
			// Log.d("hej",(String) event.getNewValue()); 
			Intent myTriggerActivityIntent=new Intent(this,PostList.class);
			startActivity(myTriggerActivityIntent);			
		}		
	}

	private Bitmap rotateImage (Bitmap bitmap) {

		int rotation = 0;
		int rotationInDegrees = 0;

		ExifInterface exif;
		try {
			exif = new ExifInterface(imageUri.getPath());
			rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
			rotationInDegrees = exifToDegrees(rotation);
			Log.d("hej","rotation: "+rotationInDegrees);
		} catch (IOException e1) {				
			e1.printStackTrace();
		}					

		int imageWidth = bitmap.getWidth();
		int imageHeight = bitmap.getHeight();

		Matrix matrix = new Matrix();

		if (rotation != 0f) {
			matrix.preRotate(rotationInDegrees);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, imageWidth, imageHeight, matrix, true);						
		}										

		return bitmap;

	}

	private Bitmap scaleImage (Bitmap bitmap) {

		int imageWidth = bitmap.getWidth();
		int imageHeight = bitmap.getHeight();
		Log.d("hej", "widht: "+imageWidth+" height: "+imageHeight);					

		if (imageWidth > 1000){	

			long factor = imageWidth/1000;						
			imageWidth = 1000;						
			// calculate height 
			long imageHeightLong = imageHeight/factor;
			Log.d("hej",""+imageHeightLong);
			imageHeight = (int) imageHeightLong;
			Log.d("hej", "factor; "+factor);

			Log.d("hej","New imagewidth and heigh: "+imageWidth+" "+imageHeight);
			bitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, false);		
		}

		return bitmap;		
	}
}