package com.aimei.utils;

import android.content.Context;

public class ScreenUtil {

	/** 
     * �õ��豸��Ļ�Ŀ�� 
     */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	/** 
     * �õ��豸��Ļ�ĸ߶� 
     */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
}
