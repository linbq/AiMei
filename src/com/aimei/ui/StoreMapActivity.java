package com.aimei.ui;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.aimei.adapter.MyHorizontalllistviewAdapter;
import com.aimei.view.HorizontalListView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.cloud.CloudListener;
import com.baidu.mapapi.cloud.CloudManager;
import com.baidu.mapapi.cloud.CloudPoiInfo;
import com.baidu.mapapi.cloud.CloudSearchResult;
import com.baidu.mapapi.cloud.DetailSearchResult;
import com.baidu.mapapi.cloud.NearbySearchInfo;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.LatLngBounds.Builder;

public class StoreMapActivity extends BaseActivity implements OnClickListener,CloudListener {
	@InjectView(R.id.bmapView)
	MapView mMapView = null;
	@InjectView(R.id.bar_back)
	ImageButton backBtn;
	@InjectView(R.id.bar_search_btn) Button searchBtn;
	private BaiduMap mBaiduMap;
	// 定位相关
	private LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private boolean isFirstLoc = true;// 是否首次定位
	private int radius = 10000;	//半径
	private LatLng myLocation;	
	private int animationTime = 200;//动画时间
	private ImageView  closeBtn;
	private Button  seeBtn;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_store_map);
		backBtn.setOnClickListener(this);
		searchBtn.setOnClickListener(this);
		CloudManager.getInstance().init(this);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);	//扫描频率
		mLocClient.setLocOption(option);
		mLocClient.start();
		
	}
	
	
	private PopupWindow mPopupWindow;
	private void showPopupWindow(Marker marker){
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(marker.getPosition());
		mBaiduMap.animateMapStatus(u,animationTime);
		
		if(mPopupWindow != null && mPopupWindow.isShowing()){
			mPopupWindow.dismiss();
		}
		// 创建InfoWindow展示的view
		View view = LayoutInflater.from(this).inflate(R.layout.popup_store_map, null);
		closeBtn = (ImageView) view.findViewById(R.id.popup_store_map_close);
		closeBtn.setOnClickListener(this);
		seeBtn = (Button) view.findViewById(R.id.popup_store_map_see);
		seeBtn.setOnClickListener(this);
		mPopupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); 
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
		mPopupWindow.setAnimationStyle(R.style.AnimAlpha);
		mPopupWindow.showAtLocation(findViewById(R.id.store_map_layout), Gravity.TOP,0,0); 
	}
	
	private PopupWindow mPopupWindow2;
	private void showPopupWindow2(){
		View view = LayoutInflater.from(this).inflate(R.layout.watch_technician_popupwindow, null);
		HorizontalListView mListView = (HorizontalListView) view.findViewById(R.id.popupListview);
		View layout = view.findViewById(R.id.popupwindow_layout);
		List<String> mlist = new ArrayList<String>(); 
		for(int i=0;i<8;i++)
			mlist.add("sub item "+(i+1));
		MyHorizontalllistviewAdapter mAdapter = new MyHorizontalllistviewAdapter(this, mlist);
		mListView.setAdapter(mAdapter); 
		mPopupWindow2 = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); 
		mPopupWindow2.setFocusable(true);
		mPopupWindow2.setOutsideTouchable(true);
		mPopupWindow2.setBackgroundDrawable(new ColorDrawable(0xb2000000));
		mPopupWindow2.setAnimationStyle(R.style.AnimBottom);
		mPopupWindow2.showAtLocation(findViewById(R.id.store_map_layout), Gravity.BOTTOM,0,0); 
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mPopupWindow2.dismiss();
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getApplicationContext(), "敬请期待", Toast.LENGTH_SHORT).show();
			}
		});
		//Log.e("xxx", "mListView.h="+mListView.getMeasuredHeight()+"  screenh="+ScreenUtil.getScreenHeight(context));
	}
	


	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		CloudManager.getInstance().destroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}

	@Override
	public void onClick(View view) {
		log("view="+view);
		if (view == backBtn) {
			onBackPressed();
		} else if(view == searchBtn){
			
		} else if(view == closeBtn){
			mPopupWindow.dismiss();
		} else if(view == seeBtn){
			showPopupWindow2();
		}
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
			// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			if (isFirstLoc) {
				isFirstLoc = false;
				myLocation = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(myLocation);
				mBaiduMap.animateMapStatus(u);
				showNearbyStore(location.getLongitude()+","+location.getLatitude());
			}
			

		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	
	private void showNearbyStore(String location){
		log(location);
		NearbySearchInfo info = new NearbySearchInfo();
		info.ak = "60f2e72f95a1c6bd3532cab4c7217244";
		info.geoTableId = 76080;
		info.q = "";
		info.radius = radius;
		info.pageSize=20;
		info.location = location;
		CloudManager.getInstance().nearbySearch(info);
	}

	@Override
	public void onGetDetailSearchResult(DetailSearchResult result, int arg1) {
		if (result != null) {
			if (result.poiInfo != null) {
				Toast.makeText(StoreMapActivity.this, result.poiInfo.title,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(StoreMapActivity.this,
						"status:" + result.status, Toast.LENGTH_SHORT).show();
			}
		}
	}


	@Override
	public void onGetSearchResult(CloudSearchResult result, int arg1) {
		if (result != null && result.poiList != null
				&& result.poiList.size() > 0) {
			Log.d("onGetSearchResult", "onGetSearchResult, result length: " + result.poiList.size());
			mBaiduMap.clear();
			OverlayOptions circleOption = new CircleOptions()
			.center(myLocation)
			.radius(radius)
			.stroke(new Stroke(5, 0X80ff0000))
			.fillColor(0x00000000)
			.visible(true);
			mBaiduMap.addOverlay(circleOption);
			
			BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_map_point);
			LatLng ll;
			LatLngBounds.Builder builder = new Builder();
			for (CloudPoiInfo info : result.poiList) {
				log("info.latitude="+info.latitude+  " info.longitude="+info.longitude);
				ll = new LatLng(info.latitude, info.longitude);
				OverlayOptions oo = new MarkerOptions().icon(bd).position(ll);
				mBaiduMap.addOverlay(oo);
				builder.include(ll);
			}
			/*LatLngBounds bounds = builder.build();
			MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
			mBaiduMap.animateMapStatus(u);*/
			
			mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

				@Override
				public boolean onMarkerClick(Marker mk) {
					//showMapPop(mk);
					showPopupWindow(mk);
					return false;
				}
			});
		}
	}
}
