package com.sunny.h5lib.callback;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;

import com.sunny.uilib.utils.ToastUtil;

/**
 * ------------------------------------------------
 * Copyright © 2014年 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author sunny
 * @version v1.0.0
 * @date 2017/11/9  10:54
 * @annotation 通用的JS与webview互调的接口抽象类
 */

public abstract class AbsJSInterface {

    public Activity mContext;

    /**
     * 构造方法
     *
     * @param context 这里选用Activity
     */
    public AbsJSInterface(@NonNull Activity context) {
        mContext = context;
    }

    //1,弹窗
    @JavascriptInterface
    public void showToast(final String toast) {
        if (mContext != null && !mContext.isFinishing()) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.showToast(mContext, toast);
                }
            });
        }

    }

    //2,登录
    @JavascriptInterface
    public void invokeLogin() {
        //todo 处理登录流程
    }

    /**
     * 获取网页标题
     * 一般针对静态标题
     *
     * @return
     */
    public abstract String getWebTitle();
}
