package com.joyfulmath.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.joyfulmath.common.R;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 设备信息
 * 
 * @author Demanlu
 * 
 */
public class DeviceInfo {
    
    /** app名字 */
    public static String AppName;
    /** app label 应用名称*/
    public static String AppLabelName;

    /**package bane**/
    public static String PackageName;
    /** app版本 */
    public static String VersionName;
    /** app内部版本 */
    public static int     VersionCode;
    /** 安装包渠道号 */
    public static String AppCHANNEL;
    /** umeng key */
    public static String UmengKey;
    /** 本次提交git版本号，打包脚本会修改 */
    public static String GIT_COMMIT_VER;
    /** 设备机型 增加Android-前缀 如，Android-iphone4，Android-i9000 */
    public static String OSModel;
    /** OS版本 如，iOS5.0，android2.3.7 (OSVer) */
    public static String OSVersion;
    /** 系统描述：ro.build.description 例如：MediaQ-user 4.1.2 HuaweiMediaQ UNKNOW release-keys 用户的KEY */
    public static String OSDesc;
    /** 手机生产厂商 */
    public static String MANUFACTURER;
    /**
     * uuid app安装一次重新生成个id <br>
     * 已经安装的使用线程从SharedPreferences中读取，uuid默认为null。 PhoneInfo.initialize()后大概100ms不到后会设置该值
     */
    public static String uuid;
    /**
     * 设备ID 设备的imei，平板可能会没有 标识设备编号请使用<br>
     * 需要增加权限&#60;uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     * @modify demanlu deviceId get from api
     */
    public static String Imei;
    /** mac地址 需要增加权限&#60;uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/> */
    public static String MAC;
    /** 设备ANDROIDID */
    public static String AndroidID;

    /** app类型：vipabctv 或者其他扩展 */
    public static String AppType;

//    /** api key&&api secret */
//    public static String  ApiKey;
//    public static String  ApiSecret;

    /**
     * 用于唯一标识设备的编号，介于imei、mac地址、androidId均有可能获取不到，这里统一逻辑最大程度上获取唯一标示手机的编码。 <br>
     * <br>
     * 算法依次取:imei、androidid、mac地址, 前面未取到以后面的值替代 <br>
     * <br>
     * 取imei时需要增加权限&#60;uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static String UniqueId;


    public static String DeviceID = "";

    /**
     * 本机的手机号 不一定获取得到 <br>
     * 需要增加权限&#60;uses-permission android:name="android.permission.READ_PHONE_STATE"/>
     */
    public static String phoneNum;
    public static Context mOutContext;
    /** 是否是平板 */
    public static boolean isTablet;

    /** 签名的hash值 */
    public static String signKeyHash = "";

    /**
     * 打包时间，提供给测试区分测试包
     */
    public static String BuildTime = "";
    static {
        try {
            // 放在static方法中无法加载，只能放在非static方法中或者static块中进行加载
            System.loadLibrary("PaHaofang");
        } catch (UnsatisfiedLinkError error) {
        }
    }

    public static void initialize(Context outContext) {
        initialize(outContext, "");
    }

    public static void initialize(Context outContext, String appName) {
        mOutContext = outContext;

        PackageManager manager = outContext.getPackageManager();
        PackageInfo info = null;
        String appLabel = "";

        try {
            info = manager.getPackageInfo(outContext.getPackageName(), 0);
            // app名字
            try {
                ApplicationInfo ai = manager.getApplicationInfo(outContext.getPackageName(), 0);
                appLabel = (String) manager.getApplicationLabel(ai);
            } catch (Exception e) {
                //todo here may cause wrong for appname
                appLabel = mOutContext.getResources().getString(R.string.app_name);
            }

            AppName = TextUtils.isEmpty(appName) ? appLabel : appName;
            AppLabelName = appLabel;
            PackageName = outContext.getPackageName();

            // app版本
            if (info != null) {
                VersionName = info.versionName;
            }

            // app内部版本
            if (info != null) {
                VersionCode = info.versionCode;
            }

            // 安装包渠道号：(AppCHANNEL)、umengkey
            try {
                PackageManager localPackageManager = outContext.getPackageManager();
                ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo(
                        outContext.getPackageName(), PackageManager.GET_META_DATA);
                AppCHANNEL = String.valueOf(localApplicationInfo.metaData.get("UMENG_CHANNEL"));
                UmengKey = String.valueOf(localApplicationInfo.metaData.get("UMENG_APPKEY"));
                GIT_COMMIT_VER = String.valueOf(localApplicationInfo.metaData.get("GIT_COMMIT_VER"));
                AppType = String.valueOf(localApplicationInfo.metaData.get("APP_TYPE"));
                BuildTime = String.valueOf(localApplicationInfo.metaData.get("BUILD_TIME"));
                
            } catch (Exception e) {
                // do nothing. not use the data
            }

//            // api key & api secret
//            try {
//                HashMap<String, String> keyAndSecret = apiInit(AppType);
//                if (null != keyAndSecret) {
//                    ApiKey = keyAndSecret.get("apiKey");
//                    ApiSecret = keyAndSecret.get("apiSecret");
//                }
//            } catch (Exception e) {
//            }

            // 包签名信息
            try {
                PackageInfo signInfo = manager.getPackageInfo(outContext.getPackageName(),
                        PackageManager.GET_SIGNATURES);
                for (Signature sig : signInfo.signatures) {
                    signKeyHash = String.valueOf(sig.hashCode());
                }
            } catch (Exception e) {
                // do nothing. not use the data
            }

            // 设备ID，每台设备唯一 (DeviceID 设备的imei，平板可能会没有）
            TelephonyManager mTelephonyMgr = (TelephonyManager) outContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephonyMgr != null) {
                Imei = mTelephonyMgr.getDeviceId();
            }

            // AndroidId
            try {
                String androidId = Secure.getString(outContext.getContentResolver(), Secure.ANDROID_ID);
                AndroidID = androidId;
            } catch (Exception e) {
                // do nothing. not use the data
            }

            // MAC地址
            MAC = getLocalMacAddress(outContext);

            // 设备机型：如，iphone4，i9000 (Model)
            OSModel = "Android-" + Build.MODEL;

            // OS版本：如，iOS5.0，android2.3.7 (OSVer)
            OSVersion = Build.VERSION.RELEASE;

            //MANUFACTURER
            MANUFACTURER = Build.MANUFACTURER;
            // OS系统描述：
            OSDesc = getBuildDescription();

            // uuid app安装一次重新生成个id
            new Thread(new Runnable() {
                @Override
                public void run() {
                    uuid = getInstallID();
                }
            }).start();

            // 获取本机的手机号 有几率获取不到
            if (mTelephonyMgr != null) {
                phoneNum = mTelephonyMgr.getLine1Number();
            }

            isTablet = isTablet(mOutContext);

            // UniqueId
            UniqueId = Imei;
            if (UniqueId == null || UniqueId.trim().length() == 0) {
                UniqueId = AndroidID;
            }
            if (UniqueId == null || UniqueId.trim().length() == 0) {
                UniqueId = MAC;
            }
        } catch (Exception e) {
            Log.e(DeviceInfo.class.getName(), String.valueOf(e));
        }
    }

    /**
     * 获取MAC地址
     * 
     * @param outContext
     * @return
     */
    public static String getLocalMacAddress(Context outContext) {
        try {
            WifiManager wifi = (WifiManager) outContext.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info != null ? info.getMacAddress() : "";
        }catch (Exception e){
            return null;
        }

    }

    /**
     * 获取OS系统描述 例如：MediaQ-user 4.1.2 HuaweiMediaQ UNKNOW release-keys 用户的KEY
     * 
     * @return
     */
    public static String getBuildDescription() {
        String desc = "unknown";
        try {
            Class<?> clazz = Class.forName("android.os.Build");
            Class<?> paraTypes = Class.forName("java.lang.String");
            Method method = clazz.getDeclaredMethod("getString", paraTypes);
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            Build b = new Build();
            desc = (String) method.invoke(b, "ro.build.description");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desc;
    }

    /**
     * 得到随机数，每次均不一样
     * 
     * @return
     */
    private static String getUUId() {
        String ret;
        ret = new String(UUID.randomUUID().toString().getBytes());

        return ret;
    }

    /**
     * 一个app安装一次生成一个id
     * 
     * @return
     */
    private static String getInstallID() {
        String KEY = "uuid-sadifwenvjsadfiemadavdaei";// key填充字符串 防止与app中的字段重复

//        String uuid = SharedPreferencesHelper.getInstance(mOutContext).getString(KEY);
//        if (uuid == null || uuid.trim().length() == 0) {
//            uuid = getUUId();
//            SharedPreferencesHelper.getInstance(mOutContext).putString(KEY, uuid);
//        }

        return uuid;
    }

    /**
     * 是否是平板
     * 
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 获取屏幕分辨率的DisplayMetrics类
     * @param activity
     * @return
     */
    public static DisplayMetrics getScreenDisplay(Activity activity)
    {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static void setDeviceID(String deviceId){
        DeviceID = deviceId;
    }

    public static final String NOT_INITIALIZE_ERROR_STRING = String.format(
                                                                   "%s not initialize. Please run %s.initialize() first !",
                                                                   DeviceInfo.class.getSimpleName(),
                                                                   DeviceInfo.class.getSimpleName());

//    private static native HashMap<String, String> apiInit(String appType);

}
