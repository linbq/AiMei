package com.aimei.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class VersionUtil {

	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					"com.aimei.ui", 0).versionCode;
		} catch (NameNotFoundException e) {
			Log.e("VersionUtil", e.getMessage());
		}
		return verCode;
	}

	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					"com.aimei.ui", 0).versionName;
		} catch (NameNotFoundException e) {
			Log.e("VersionUtil", e.getMessage());
		}
		return verName;
	}
}
