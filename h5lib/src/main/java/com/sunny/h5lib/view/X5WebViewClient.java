package com.sunny.h5lib.view;

import android.graphics.Bitmap;
import android.view.KeyEvent;

import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/24 13:32
 * @annotation ....
 */
public class X5WebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String s) {
        return super.shouldOverrideUrlLoading(webView, s);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        return super.shouldOverrideKeyEvent(webView, keyEvent);
    }

    /**
     * 页面开始加载
     *
     * @param webView
     * @param s
     * @param bitmap
     */
    @Override
    public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
    }

    /**
     * 页面加载完成
     *
     * @param webView
     * @param s
     */
    @Override
    public void onPageFinished(WebView webView, String s) {
        super.onPageFinished(webView, s);
    }

    /**
     * 收到错误信息
     *
     * @param webView
     * @param i
     * @param s
     * @param s1
     */
    @Override
    public void onReceivedError(WebView webView, int i, String s, String s1) {
        super.onReceivedError(webView, i, s, s1);
    }

    /**
     * SSL认证错误，可能有些服务器的证书过期
     * 懒人做法是信任所有的证书，这样不安全，不建议这样做
     *
     * @param webView
     * @param sslErrorHandler
     * @param sslError
     */
    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        sslErrorHandler.proceed();
    }
}
