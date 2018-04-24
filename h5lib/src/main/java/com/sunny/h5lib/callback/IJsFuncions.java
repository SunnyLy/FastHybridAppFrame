package com.sunny.h5lib.callback;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.webkit.JavascriptInterface;

/**
 * ------------------------------------------------
 * Copyright © 2014年 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @Author sunny
 * @Date 2017/6/2  14:48
 * @Version v1.0.0
 * @Annotation 提供给 h5调用的方法
 *   如果自己要用
 */

public class IJsFuncions extends AbsJSInterface {

    private String webTitle;

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    /**
     * 构造方法
     *
     * @param context 这里选用Activity
     */
    public IJsFuncions(@NonNull Activity context) {
        super(context);
    }

    @Override
    public String getWebTitle() {
        return webTitle;
    }

    //3,弹出分享框
    @JavascriptInterface
    public void invokeShare(final String sharedWebUrl) {
        mContext.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //todo 处理相关逻辑
            }
        });
    }

}
