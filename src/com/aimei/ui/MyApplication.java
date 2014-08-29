package com.aimei.ui;

import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class MyApplication extends Application {
	private MyApplication application;

	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		SDKInitializer.initialize(this);
	}
	
	public MyApplication getInstance(){
		return application;
	}
}
