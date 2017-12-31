package com.joyfulmath.common.tracktool;

import android.content.Context;
import android.text.TextUtils;

import com.umeng.analytics.MobclickAgent;
import com.vipabc.baseframeworklibrary.common.utils.CommonUtils;
import com.vipabc.baseframeworklibrary.common.utils.DeviceInfo;


/**
 * Created by demanlu on 2017/1/22.
 *
 */

public class UmengTrackTool implements ITrackInterface {
    String mChannel = null;
    @Override
    public void initTrack(Context context) {
        mChannel = DeviceInfo.AppCHANNEL;
        if(TextUtils.isEmpty(mChannel)){
            mChannel = CommonUtils.getChannelName(context);
        }
        MobclickAgent.UMAnalyticsConfig config =
                new MobclickAgent.UMAnalyticsConfig(context, "587d7cf5f43e480f6d000d29", mChannel);
        MobclickAgent.startWithConfigure(config);
    }

    @Override
    public void onEvent(Context context, String eventId) {
        MobclickAgent.onEvent(context,eventId);
    }

    @Override
    public void onPageStart(String pageName) {
        MobclickAgent.onPageStart(pageName);
    }

    @Override
    public void onPageEnd(String pageName) {
        MobclickAgent.onPageEnd(pageName);
    }

    @Override
    public void onPause(Context ctx) {
        MobclickAgent.onPause(ctx);
    }

    @Override
    public void onResume(Context ctx) {
        MobclickAgent.onResume(ctx);
    }
}
