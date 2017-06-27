package com.scanidcard;

import android.util.Log;

/**
 * Created by mmjbds999 on 2017/6/26.
 */
public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CustomExceptionHandler";

    private Thread.UncaughtExceptionHandler mDefaultUEH;

    public CustomExceptionHandler() {
        Log.d(TAG, "------------ CustomExceptionHandler ------------");
        mDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "------------ uncaughtException ------------ " + ex.getMessage());
        mDefaultUEH.uncaughtException(thread, ex); // 不加本语句会导致ANR
    }

}
