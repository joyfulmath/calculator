package com.joyfulmath.common.mvp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.joyfulmath.common.R;
import com.joyfulmath.common.tracktool.TrackUtils;
import com.joyfulmath.common.utils.TraceLog;


/**
 * Created by demanlu on 2017/1/22.
 * Each activity should extends baseactivity
 */

public abstract class BaseActivity extends AppCompatActivity {
    ProgressDialog progress = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TraceLog.i(this.getClass().getName());
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        TraceLog.i(this.getClass().getName());
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        TraceLog.i(this.getClass().getName());
        super.onResume();
        TrackUtils.getsInstance().onPageStart(this.getClass().getName());
        TrackUtils.getsInstance().onResume(this);
    }

    @Override
    protected void onStart() {
        TraceLog.i(this.getClass().getName());
        super.onStart();

    }

    @Override
    protected void onStop() {
        TraceLog.i(this.getClass().getName());
        super.onStop();
    }

    @Override
    protected void onPause() {
        TraceLog.i(this.getClass().getName());
        super.onPause();
        TrackUtils.getsInstance().onPageEnd(this.getClass().getName());
        TrackUtils.getsInstance().onPause(this);
    }

//    public boolean isProgressShowing() {
//        return progress != null && progress.isShowing();
//    }

//    public void showLoadingDialog(){
//        if (progress == null) {
//            progress = new ProgressDialog(this);
//            try {
//                progress.show();
//            } catch (WindowManager.BadTokenException e) {
//                e.printStackTrace();
//            }
//        }
////        progress.setCancelable(false);
////        progress.setContentView(R.layout.progressdialog);
////        progress.show();
//    }

//    public void showLoadingDialog(boolean isCancelable) {
//        if (progress == null) {
//            progress = new ProgressDialog(this);
//            try {
//                progress.show();
//            } catch (WindowManager.BadTokenException e) {
//                e.printStackTrace();
//            }
//        }
//        progress.setCancelable(isCancelable);
////        progress.setContentView(R.layout.progressdialog);
//        progress.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        progress.show();
//    }

//    public void dismissLoadingDialog(){
//        progress.dismiss();
//    }

    public void showToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();
    }
}
