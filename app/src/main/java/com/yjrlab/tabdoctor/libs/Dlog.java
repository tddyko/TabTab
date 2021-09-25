package com.yjrlab.tabdoctor.libs;

import android.util.Log;

import com.yjrlab.tabdoctor.TapDoctorApplication;

/**
 * Created by yeonjukim on 2017. 6. 5..
 */
public class Dlog {
    static final String TAG = "Yeonju";

    /**
     * Log Level Error
     **/
    public static final void e(Object message) {
        if (TapDoctorApplication.DEBUG) Log.e(TAG, buildLogMsg(message + ""));
    }

    /**
     * Log Level Warning
     **/
    public static final void w(Object message) {
        if (TapDoctorApplication.DEBUG) Log.w(TAG, buildLogMsg(message + ""));
    }

    /**
     * Log Level Information
     **/
    public static final void i(Object message) {
        if (TapDoctorApplication.DEBUG) Log.i(TAG, buildLogMsg(message + ""));
    }

    /**
     * Log Level Debug
     **/
    public static final void d(Object message) {
        if (TapDoctorApplication.DEBUG) Log.d(TAG, buildLogMsg(message + ""));
    }

    /**
     * Log Level Verbose
     **/
    public static final void v(Object message) {
        if (TapDoctorApplication.DEBUG) Log.v(TAG, buildLogMsg(message + ""));
    }

    public static String buildLogMsg(Object message) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(ste.getFileName().replace(".java", ""));
        sb.append("::");
        sb.append(ste.getMethodName());
        sb.append("]");
        sb.append(message);
        return sb.toString();
    }
}
