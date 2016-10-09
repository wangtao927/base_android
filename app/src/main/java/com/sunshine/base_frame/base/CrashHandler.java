package com.sunshine.base_frame.base;

import android.content.Context;

/**
 * Created by wangtao on 2016/5/1.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String Tag = "CrashHandler";

    private Context mContext;


    private static CrashHandler INSTANCE = new CrashHandler();

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {


//        try {
//            handleException(ex);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        Intent intent = new Intent(mContext, HomeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        mContext.startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        return true;
    }

    public void init(Context ctx) {
        mContext = ctx;

        Thread.setDefaultUncaughtExceptionHandler(this);
//        Thread.setDefaultUncaughtExceptionHandler(mDefaultHandler);
    }
}
