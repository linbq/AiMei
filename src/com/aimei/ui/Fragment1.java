package com.aimei.ui;


import java.util.ArrayList;
import java.util.List;

import com.aimei.adapter.FragmentListViewAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Fragment1 extends Fragment {
	private ListView mListView;
	private List<String> mList = new ArrayList<String>();
	private FragmentListViewAdapter mAdapter;
 
	@Override
	public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fragment1, container, false);
		return view;
	}  
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i("jackie", "Fragment1 onActivityCreated"); 
		mListView = (ListView) getView().findViewById(R.id.mListview);
		for(int i = 0;i<5;i++){  
			mList.add("item"+(i+1));
		}
		mAdapter = new FragmentListViewAdapter(getActivity(),mList);
		mListView.setAdapter(mAdapter);
		
	} 
	
}
