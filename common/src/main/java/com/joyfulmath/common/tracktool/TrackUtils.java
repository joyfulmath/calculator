package com.joyfulmath.common.tracktool;

import android.content.Context;

/**
 * Created by demanlu on 2017/1/22.
 *
 */

public class TrackUtils {

    private ITrackInterface iTrackInterface = null;

    private static TrackUtils sInstance = null;

    public static synchronized TrackUtils getsInstance() {

        if (sInstance == null) {
            sInstance = new TrackUtils();
        }
        return sInstance;
    }

    /**
     *  initManager
     * @param context context
     */
    public void initUtils(Context context) {
        iTrackInterface = new UmengTrackTool();
        iTrackInterface.initTrack(context);
    }

    /**
     * onEvent
     * @param context       context
     * @param eventId       eventId
     */
    public void onEvent(Context context, String eventId){
        if(iTrackInterface == null){
            throw new RuntimeException("iTrackInterface init first");
        }
        iTrackInterface.onEvent(context,eventId);
    }

    /**
     * onPageStart
     * @param pageName  pageName
     */
    public void onPageStart(String pageName) {
        if(iTrackInterface == null){
            throw new RuntimeException(this.getClass().getSimpleName()+" has not been init");
        }
        iTrackInterface.onPageStart(pageName);
    }

    /**
     * onPageEnd
     * @param pageName  pageName
     */
    public void onPageEnd(String pageName) {
        if(iTrackInterface == null){
            throw new RuntimeException(this.getClass().getSimpleName()+" has not been init");
        }
        iTrackInterface.onPageEnd(pageName);
    }

    /**
     * activity onPause
     * @param ctx   Context
     */
    public void onPause(Context ctx) {
        if(iTrackInterface == null){
            throw new RuntimeException(this.getClass().getSimpleName()+" has not been init");
        }
        iTrackInterface.onPause(ctx);
    }

    /**
     * onResume
     * @param ctx   Context
     */
    public void onResume(Context ctx) {
        if(iTrackInterface == null){
            throw new RuntimeException(this.getClass().getSimpleName()+" has not been init");
        }
        iTrackInterface.onResume(ctx);
    }
}
