package com.sunny.baselibs.utils;

import android.app.Activity;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/26 14:08
 * @annotation 倒计时
 */
public class CountDownTimer {

    private Activity mActivity;
    private ScheduledExecutorService mCountDownService;
    private ScheduledFuture<?> scheduledFuture;
    private int mLessTime = 60;//默认是60s
    private int mTimeTemp = mLessTime;
    private boolean isCounting = false;

    private static CountDownTimer countDownTimer;

    private ICountDownCallback countDownCallback;

    public void setCountDownCallback(ICountDownCallback countDownCallback) {
        this.countDownCallback = countDownCallback;
    }

    /**
     * 单例获取对象
     *
     * @param time 倒计时时间，默认为60s
     * @return
     */
    public static CountDownTimer getInstance(Activity activity, int time) {
        synchronized (CountDownTimer.class) {
            if (countDownTimer == null) {
                synchronized (CountDownTimer.class) {
                    countDownTimer = new CountDownTimer(activity, time);
                }
            }
        }
        return countDownTimer;
    }

    public CountDownTimer(Activity activity, int time) {
        this.mActivity = activity;
        if (time > 0) mLessTime = time;
        mTimeTemp = mLessTime;
        if (mCountDownService == null) {
            mCountDownService = Executors.newScheduledThreadPool(1);
        }
    }

    /**
     * 开始倒计时
     */
    public void startCountDown() {
        if (mCountDownService != null && !isCounting) {
            mLessTime = mTimeTemp;
            if (countDownCallback != null) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        countDownCallback.onStart();
                    }
                });
            }
            scheduledFuture = mCountDownService.scheduleAtFixedRate(new CountDownTimeRunnable(), 0, 1, TimeUnit.SECONDS);
        }
    }

    /**
     * 关闭倒计时
     * 建议在Activity或Fragment的onDestroy()那里调用。
     *
     * @return
     */
    public List<Runnable> shutDown() {
        if (mCountDownService != null) {
            isCounting = false;
            return mCountDownService.shutdownNow();
        }
        return null;
    }

    /**
     * 倒计时
     */
    class CountDownTimeRunnable implements Runnable {
        @Override
        public void run() {
            if (mLessTime > 0) {
                isCounting = true;
                mLessTime--;
                if (countDownCallback != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            countDownCallback.onTick(mLessTime * 1000);
                        }
                    });
                }
            } else if (mLessTime == 0) {
                isCounting = false;
                if (countDownCallback != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            countDownCallback.onFinish();
                        }
                    });
                }
                try {
                    scheduledFuture.cancel(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 倒计时回调监听
     */
    public interface ICountDownCallback {
        //开始计划
        void onStart();

        //滴答倒计时
        void onTick(long millisUntilFinished);

        //倒计时结束
        void onFinish();
    }
}
