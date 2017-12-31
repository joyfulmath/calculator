package com.joyfulmath.common.tracktool;

import android.content.Context;

/**
 * Created by demanlu on 2017/1/22.
 * just define the interface here,we may using umeng,zhuege & other tools
 */

public interface ITrackInterface {
    void initTrack(Context context);
    void onEvent(Context context, String eventId);
    void onPageStart(String pageName);
    void onPageEnd(String pageName);
    void onPause(Context ctx);
    void onResume(Context ctx);

}
