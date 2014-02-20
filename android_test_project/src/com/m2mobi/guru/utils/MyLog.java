package com.m2mobi.guru.utils;

import android.util.Log;

import static com.m2mobi.guru.BuildConfig.DEBUG;

public class MyLog {

	public static void i(String pTag, String pMessage) {
		if (DEBUG) {
			Log.i(pTag, "----- " + pMessage + " ----- ");
		}
	}

	public static void d(String pTag, String pMessage) {
		if (DEBUG) {
			Log.d(pTag, pMessage);
		}
	}

	public static void v(String pTag, String pMessage) {
		if (DEBUG) {
			Log.v(pTag, pMessage);
		}
	}

	public static void w(String pTag, String pMessage) {
		if (DEBUG) {
			Log.w(pTag, pMessage);
		}
	}

	public static void e(String pTag, String pMessage) {
		if (DEBUG) {
			Log.e(pTag, pMessage);
		}
	}

	public static void e(String pTag, String pMessage, Throwable pThrowable) {
		if (DEBUG) {
			Log.e(pTag, pMessage, pThrowable);
		}
	}
}
