package com.insightsurface.lib.utils;

import android.util.Log;

public class Logger {
	public static void d(String d) {
		Log.d("suhanglib", d);
	}
	public static void i(String i) {
		Log.i("suhanglib", i);
	}
	public static void d(Double d) {
		
		d(String.valueOf(d));
	}
	public static void d(int d) {
		d(String.valueOf(d));
	}
}
