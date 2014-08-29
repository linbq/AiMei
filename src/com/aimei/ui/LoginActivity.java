package com.aimei.ui;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class LoginActivity extends BaseActivity  implements OnClickListener{
	@InjectView(R.id.login_btn) Button loginBtn;
	@InjectView(R.id.login_register_btn) Button registerBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
		log("LoginActivity onCreate..."); 
		setContentView(R.layout.activity_login);
		init();
	}
	
	private void init() {
		loginBtn.setOnClickListener(this);
		registerBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.login_btn : 
			login();
			break;
		case R.id.login_register_btn : 
			register();
			break;
		}
	}

	private void register() {
		showToast("�����ڴ�");
		//gotoActivity(RegisterActivity.class);
	}

	private void login() {
		/**
		 * ��֤����
		 */
		gotoActivity(MainActivity.class);
		finish();
	}
}
