package com.sunny.h5lib.view;

import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/24 13:42
 * @annotation ....
 */
public class X5WebChromeClient extends WebChromeClient {

    private ProgressBar mProgressBar;

    public X5WebChromeClient(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    /**
     * 用来控制进度条的显示与隐藏
     *
     * @param webView
     * @param progress
     */
    @Override
    public void onProgressChanged(WebView webView, int progress) {
        if (mProgressBar == null) return;
        if (progress == 100) {
            mProgressBar.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(progress);
            mProgressBar.postInvalidate();
        }
    }
}
