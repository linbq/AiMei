package com.aimei.adapter;


import java.util.ArrayList;
import java.util.List;

import com.aimei.ui.R;
import com.aimei.ui.StoreMapActivity;
import com.aimei.utils.ScreenUtil;
import com.aimei.view.HorizontalListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentListViewAdapter extends BaseAdapter {
	Context context;
	LayoutInflater inflater;
	List<String> list;

	public FragmentListViewAdapter(Context context,List<String> list) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) { 
			holder = new ViewHolder(); 
			convertView = inflater.inflate(R.layout.fragment_listview_item, parent, false);
			holder.addr = (TextView) convertView.findViewById(R.id.store_address);
			holder.see = (Button) convertView.findViewById(R.id.store_see_person);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.addr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, StoreMapActivity.class);
				context.startActivity(intent);
			}
		});
		holder.see.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showPopupWindow();
			}
		});
		return convertView;
	}
	
	private static class ViewHolder {
		/** 地址 */
		TextView addr;
		/** 消息未读数 */    
		Button see;  
	}
	
	private PopupWindow mPopupWindow;
	private void showPopupWindow(){
		View view = inflater.inflate(R.layout.watch_technician_popupwindow, null);
		final HorizontalListView mListView = (HorizontalListView) view.findViewById(R.id.popupListview);
		View layout = view.findViewById(R.id.popupwindow_layout);
		List<String> mlist = new ArrayList<String>(); 
		for(int i=0;i<8;i++)
			mlist.add("sub item "+(i+1));
		MyHorizontalllistviewAdapter mAdapter = new MyHorizontalllistviewAdapter(context, mlist);
		mListView.setAdapter(mAdapter); 
		mPopupWindow = new PopupWindow(view,LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT); 
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable(0xb2000000));
		mPopupWindow.setAnimationStyle(R.style.AnimBottom);
		View v = ((Activity)context).findViewById(R.id.main); 
		mPopupWindow.showAtLocation(v, Gravity.BOTTOM,0,0); 
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mPopupWindow.dismiss();
			}
		});
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(context, "敬请期待", Toast.LENGTH_SHORT).show();
				Log.e("xxx", "mListView.h="+mListView.getHeight()+"  screenh="+ScreenUtil.getScreenHeight(context));
			}
		});
		
	}

}
