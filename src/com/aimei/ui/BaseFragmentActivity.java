package com.aimei.ui;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import roboguice.activity.RoboFragmentActivity;

public class BaseFragmentActivity extends RoboFragmentActivity {

	public void log(String s) {
		Log.e("jackie", s);
	}
	
	public void showToast(String s) {
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
	
	public void debug(String s) {
		log(s);
		showToast(s);
	}
	public void gotoActivity(Class<?> cls){
		Intent intent = new Intent(this,cls);
		startActivity(intent);
	}
}
