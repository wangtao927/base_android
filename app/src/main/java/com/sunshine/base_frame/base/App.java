package com.sunshine.base_frame.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.sunshine.base_frame.R;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.HashMap;
import java.util.Map;


public class App extends Application {

    private static final String APP_ID = "900032569";
    public static Context ctx;
    private  static Application instance = null;
    private final String APP_CONTEXT_TAG = "appContext";




    @Override
    public void onCreate() {
        ctx = getApplicationContext();
        regBUgly();
//        synchronized (this) {
//            if (instance == null) {
//                instance = this;
//            }
//
//
////        CrashHandler crashHandler = CrashHandler.getInstance();
////        crashHandler.init(ctx);
//            //PosSerialHelper.getInstance().setPath();
//
//            //SerialMachineHelper.getInstance().getSerial();
//
//        /*Intent intent1 = new Intent(this, SerialInitService.class);
//        startService(intent1);*/
//            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(instance); //App鐨勭瓥鐣ean
//            strategy.setAppChannel(getPackageName());     //璁剧疆娓犻亾
//            strategy.setAppVersion(getVersion());      //App鐨勭増鏈�
//            strategy.setAppReportDelay(1000);  //璁剧疆SDK澶勭悊寤舵椂锛屾绉�
//            strategy.setDeviceID(mcNo);
//            strategy.setCrashHandleCallback(new AppCrashHandleCallback());
//
//            CrashReport.initCrashReport(instance, mcNo, true, strategy); //鑷畾涔夌瓥鐣ョ敓鏁堬紝蹇呴』鍦ㄥ垵濮嬪寲SDK鍓嶈皟鐢�
//            CrashReport.setUserId("BBDTEK");
//        }

        super.onCreate();

    }

    /**
     * 鑾峰彇鐗堟湰鍙�
     * @return 褰撳墠搴旂敤鐨勭増鏈彿
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return this.getString(R.string.app_version) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.app_version);
        }
    }
    private void regBUgly() {
        /***** Beta楂樼骇璁剧疆 *****/
        /**
         * true琛ㄧずapp鍚姩鑷姩鍒濆鍖栧崌绾фā鍧�;
         * false涓嶄細鑷姩鍒濆鍖�;
         * 寮�鍙戣�呭鏋滄媴蹇僺dk鍒濆鍖栧奖鍝峚pp鍚姩閫熷害锛屽彲浠ヨ缃负false锛�
         * 鍦ㄥ悗闈㈡煇涓椂鍒绘墜鍔ㄨ皟鐢˙eta.init(getApplicationContext(),false);
         */
        Beta.autoInit = true;

        /**
         * true琛ㄧず鍒濆鍖栨椂鑷姩妫�鏌ュ崌绾�;
         * false琛ㄧず涓嶄細鑷姩妫�鏌ュ崌绾�,闇�瑕佹墜鍔ㄨ皟鐢˙eta.checkUpgrade()鏂规硶;
         */
        Beta.autoCheckUpgrade = false;


        /**
         * 璁剧疆sd鍗＄殑Download涓烘洿鏂拌祫婧愪繚瀛樼洰褰�;
         * 鍚庣画鏇存柊璧勬簮浼氫繚瀛樺湪姝ょ洰褰曪紝闇�瑕佸湪manifest涓坊鍔燱RITE_EXTERNAL_STORAGE鏉冮檺;
         */
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


        Bugly.init(getApplicationContext(), APP_ID, false);
    }

//    public static RefWatcher getRefWatcher(Context context) {
//        App application = (App) context.getApplicationContext();
//        return application.refWatcher;
//    }


    public static Context getContext() {
        return ctx;
    }


    private class AppCrashHandleCallback extends CrashReport.CrashHandleCallback //bugly鍥炶皟
    {
        @Override
        public synchronized Map<String, String> onCrashHandleStart(int crashType, String errorType, String errorMessage, String errorStack)
        {
            String crashTypeName = null;
            switch (crashType)
            {
                case CrashReport.CrashHandleCallback.CRASHTYPE_JAVA_CATCH:
                    crashTypeName = "JAVA_CATCH";
                    break;
                case CrashReport.CrashHandleCallback.CRASHTYPE_JAVA_CRASH:
                    crashTypeName = "JAVA_CRASH";
                    break;
                case CrashReport.CrashHandleCallback.CRASHTYPE_NATIVE:
                    crashTypeName = "JAVA_NATIVE";
                    break;
                case CrashReport.CrashHandleCallback.CRASHTYPE_U3D:
                    crashTypeName = "JAVA_U3D";
                    break;
                default:
                {
                    crashTypeName = "unknown";
                }
            }

            Log.e(APP_CONTEXT_TAG, "Crash Happen Type:" + crashType + " TypeName:" + crashTypeName);
            Log.e(APP_CONTEXT_TAG, "errorType:" + errorType);
            Log.e(APP_CONTEXT_TAG, "errorMessage:" + errorMessage);
            Log.e(APP_CONTEXT_TAG, "errorStack:" + errorStack);

            Map<String, String> userDatas = super.onCrashHandleStart(crashType, errorType, errorMessage, errorStack);
            if (userDatas == null)
            {
                userDatas = new HashMap<String, String>();
            }

            userDatas.put("DEBUG", "TRUE");
            return userDatas;
        }

    }
}
