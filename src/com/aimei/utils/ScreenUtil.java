package com.aimei.utils;

import android.content.Context;

public class ScreenUtil {

	/** 
     * 得到设备屏幕的宽度 
     */
	public static int getScreenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}
	
	/** 
     * 得到设备屏幕的高度 
     */
	public static int getScreenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}
}
