package com.example.mitflashlight;

import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	//Local Variables
	
	private boolean isLightOn = false;
	private Camera camera;
	private Button button;
	private View v;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		button = (Button) findViewById(R.id.buttonFlashlight);
		v = (View) findViewById(R.id.backgroundView);

		
		
		Context context = this; 

		
		PackageManager pm = context.getPackageManager();
	
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			Log.e("err", "Device has no camera!");
			return;
		}
		
		
		
		//start the camera
		camera = Camera.open();
		final Parameters p = camera.getParameters();
		 
		
		///set the clicklistener
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (isLightOn) {

					Log.i("info", "torch is turn off!");

					p.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(p);
					camera.stopPreview();
					
					v.setBackgroundColor(Color.BLACK);
					
					
					isLightOn = false;
				


				} else {

					Log.i("info", "torch is turn on!");

					p.setFlashMode(Parameters.FLASH_MODE_TORCH);

					camera.setParameters(p);
					camera.startPreview();
					
					v.setBackgroundColor(Color.WHITE);
					
					
					isLightOn = true;
					


				}

			}
		});
		
		
	}
	
	@Override
	protected void onPause() {
		super.onStop();
		camera.stopPreview();
		
		if (camera != null) {
			isLightOn=true;
			button.performClick();
			camera.release();
			camera = null;
		}
	}

	
	
	@Override 
	protected void onResume(){
		super.onResume();

		if(camera ==null){
			camera = Camera.open();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
