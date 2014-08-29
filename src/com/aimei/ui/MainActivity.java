package com.aimei.ui;



import java.util.ArrayList;

import roboguice.inject.InjectView;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aimei.adapter.MyFragmentPagerAdapter;

public class MainActivity extends BaseFragmentActivity {

	private ViewPager viewPager;// ҳ������
	private ArrayList<Fragment> fragmentsList;
	private int index = 0;
	private int currentTabIndex = 0;
	private Button[] mTabs;
	private String[] titles = {"AiMei","��һ��","�Ƽ�","����"};
	private String[] sexs = {"��","Ů"};
	private int sexIndex = 0;
	@InjectView(R.id.main_title) TextView titleTV;
	@InjectView(R.id.actionbar_right_button) Button rightBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initImageView();
	}
	
	private void initView(){
		mTabs = new Button[4];
		mTabs[0] = (Button) findViewById(R.id.main_tab_btn1);
		mTabs[1] = (Button) findViewById(R.id.main_tab_btn2);
		mTabs[2] = (Button) findViewById(R.id.main_tab_btn3);
		mTabs[3] = (Button) findViewById(R.id.main_tab_btn4);
		mTabs[0].setSelected(true);
		
		viewPager = (ViewPager) findViewById(R.id.mViewPager);
		fragmentsList = new ArrayList<Fragment>();
		fragmentsList.add(new Fragment1());
		fragmentsList.add(new Fragment2());
		fragmentsList.add(new Fragment3());
		fragmentsList.add(new Fragment4());
		viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
		rightBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				switch(currentTabIndex){
				case 0 :
					break;
				case 1 :
					break;
				case 2 :
					sexIndex = (sexIndex+1) % 2;
					rightBtn.setText(sexs[sexIndex]);
					break;
				case 3 :
					break;
				}
				showToast("�����ڴ�");
			}
		});
	}
	
	/**
	 * ��ʼ���������������ҳ������ʱ������ĺ���Ҳ������Ч������������Ҫ����һЩ����
	 */
	private ImageView line;
	private float offset = 0;
	private void initImageView() {
		line = (ImageView) findViewById(R.id.cursor);
		float bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.viewpager_line).getWidth();// ��ȡͼƬ���
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		offset = screenW / 4.0f;
		float scaleX = offset / bmpW;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleX, 1.0f);
		line.setImageMatrix(matrix);// ���ö�����ʼλ��
	}
	
	public void onTabSelect(View view) {
		switch (view.getId()) {
		case R.id.main_tab_btn1:
			index = 0;
			break;
		case R.id.main_tab_btn2:
			index = 1;
			break;
		case R.id.main_tab_btn3:
			index = 2;
			break;
		case R.id.main_tab_btn4:
			index = 3;
			break;
		
		}
		if(currentTabIndex != index)
			viewPager.setCurrentItem(index);
	}
	
	
	public class MyOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int position) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int position) {
			mTabs[currentTabIndex].setSelected(false);
			//�ѵ�ǰtab��Ϊѡ��״̬
			mTabs[position].setSelected(true);
			titleTV.setText(titles[position]);
			Animation animation = new TranslateAnimation(offset * currentTabIndex, offset * position, 0, 0);// ��Ȼ����Ƚϼ�ֻ࣬��һ�д��롣
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��  
			animation.setDuration(100);
			line.startAnimation(animation);
			currentTabIndex = position;
			changeActionBar(position);
		}
		
	}
	
	private void changeActionBar(int position){
		rightBtn.setText("");
		switch(position){
		case 0 : 
			rightBtn.setBackgroundResource(R.drawable.icon_search);
			break;
		case 1 : 
			rightBtn.setBackgroundResource(R.drawable.icon_add);
			break; 
		case 2 : 
			rightBtn.setText(sexs[sexIndex]);
			rightBtn.setBackgroundResource(R.drawable.icon_sex);
			break;
		case 3 : 
			rightBtn.setBackgroundResource(R.drawable.icon_setting);
			break;
		}
	}
}
