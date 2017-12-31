package com.joyfulmath.common.mvp

import android.os.Handler
import android.os.Looper
import com.joyfulmath.common.utils.TraceLog
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Created by deman.lu on 2017/12/31 0031.
 *
 */
abstract class BasePresenter {
    protected var pool: ExecutorService? = null
    protected var mMainHandler: Handler? = null


    fun onStart(){
        TraceLog.d(this::class.simpleName)
    }

    fun onResume(){
        TraceLog.d(this::class.simpleName)
    }

    fun onDestroy(){
        TraceLog.d(this::class.simpleName)
    }

    fun onBackClick(){
        TraceLog.d(this::class.simpleName)
    }


    /**
     * because BasePresenter may using much case,so using [.generatePool] & [.generateHandler]
     * to apply memory if necessary. for most presenter,they may not using pool & handler.
     */
    private fun generatePool() {
        if (pool == null) {
            pool = Executors.newFixedThreadPool(4)
        }
    }

    private fun generateHandler() {
        if (mMainHandler == null) {
            mMainHandler = Handler(Looper.getMainLooper())
        }
    }

    protected fun doBackground(runnable: Runnable) {
        generatePool()
        pool?.let{
            it.execute(runnable)
        }
    }


    protected fun updateInMainThread(runnable: Runnable) {
        generateHandler()
        mMainHandler?.let {
            it.post(runnable)
        }
    }

    protected fun updateInMainThreadDelay(runnable: Runnable, millis: Long) {
        generateHandler()
        mMainHandler?.let {
            it.postDelayed(runnable, millis)
        }
    }

    protected fun checkBackground() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw RuntimeException("it must be running in work thread!")
        }
    }
}