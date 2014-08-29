package com.aimei.adapter;

import java.util.List;

import com.aimei.ui.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class MyHorizontalllistviewAdapter extends BaseAdapter {

	List<String> list;
	Context context;
	LayoutInflater inflater;
	
	public MyHorizontalllistviewAdapter(Context context,List<String> list) {
		this.context = context;
		this.list  =list;
		inflater = LayoutInflater.from(context);
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
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.horizontallistview_item	, null);
		}
		return convertView;
	}

}
