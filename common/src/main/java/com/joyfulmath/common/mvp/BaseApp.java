package com.joyfulmath.common.mvp;

import android.support.multidex.MultiDexApplication;

import com.joyfulmath.common.utils.DeviceInfo;
import com.joyfulmath.common.utils.SharedPreferencesHelper;
import com.joyfulmath.common.utils.TraceLog;

/**
 * Created by demanlu on 2017/1/25.
 */

public abstract class BaseApp extends MultiDexApplication implements Thread.UncaughtExceptionHandler {
    protected static final Object object = new Object();
    protected AppInitInfo info = null;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }


    /**
     * to get some info from buildconfig
     * @return
     */
    public abstract AppInitInfo init();
    public abstract String getBrandId();

    /**
     * you can override this fun to do some init action in self-app
     */
    protected void initApplication() {
        TraceLog.d();
        info = init();
        // DeviceInfo工具
        DeviceInfo.initialize(this, info.APPLICATION_ID);
        SharedPreferencesHelper.init(this);
        ImageLoadManger.getsInstance().initManager(this);
        /**demanlu@20170109 later we will combine vipabctv & vipjrtv
         as one project,so we should not set brandid in buildconfig**/
        UserInfoUtils.setsBrandId(getBrandId());
        RetrofitManager.getInstance().initManager(this, info.apiHostEnv);
        TrackUtils.getsInstance().initUtils(this);
        Thread.setDefaultUncaughtExceptionHandler(this);
        BugReportManager.getsInstance().initBugReport(this,info.buglyId, "none");
        DataTransferTool.setFromWhere(info.fromWhere);
    }




    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        BugReportManager.getsInstance().postCatchedException(ex,thread);
        ex.printStackTrace();

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    public static class AppInitInfo{
        public String APPLICATION_ID;
        public int apiHostEnv;
        public String buglyId;
        public String fromWhere;
    }
}
