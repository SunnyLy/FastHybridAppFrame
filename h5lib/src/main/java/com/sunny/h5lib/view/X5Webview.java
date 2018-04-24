package com.sunny.h5lib.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.sunny.h5lib.R;
import com.sunny.h5lib.callback.AbsJSInterface;
import com.sunny.h5lib.callback.IJsFuncions;
import com.sunny.h5lib.constant.H5Constant;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/24 10:40
 * @annotation 自定义一个X5Webview, 持有腾讯Webview的引用
 * X5Webview,主要用来处理：
 * 1，webview的基本配置；
 * 2，android 与 js互调的接口；
 * 3，webview加载错误页面的展示；
 * 4，其他
 */
public class X5Webview extends LinearLayout {
    private Context mContext;
    private ProgressBar mProgressbar;
    private WebView mWebview;
    private AbsJSInterface mJscallback;

    //Client
    private X5WebViewClient mWebviewClient;
    private X5WebChromeClient mWebChromeClient;

    //CookieManager
    private CookieManager mCookieManager;

    public X5Webview(Context context) {
        this(context, null);
    }

    public X5Webview(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public X5Webview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        mContext = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.h5_layout_webview, null);
        mProgressbar = (ProgressBar) contentView.findViewById(R.id.h5_webview_pb);
        mWebview = (WebView) contentView.findViewById(R.id.h5_webview);
        initWebview();
        addView(contentView);
    }

    public WebView getWebview() {
        return mWebview;
    }

    public boolean canGoBack() {
        return mWebview == null ? false : mWebview.canGoBack();
    }

    /**
     * Webview的初始化
     */
    private void initWebview() {
        if (mWebview != null) {
            mWebview.requestFocus();
            mWebview.setWebViewClient(new X5WebViewClient());
            mWebview.setWebChromeClient(new X5WebChromeClient(mProgressbar));
            mWebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            WebSettings webSettings = mWebview.getSettings();
            //先从缓存读取，若没有，再从网络读取
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webSettings.setDomStorageEnabled(true);            // 开启DOM storage API 功能
            webSettings.setDatabaseEnabled(true);                // 开启database storage API功能
            String cacheDirPath = mContext.getFilesDir().getAbsolutePath() +
                    H5Constant.WEBVIEW_CACHE_PATH;    // 设置数据库缓存路径
            webSettings.setAppCachePath(cacheDirPath);         // 设置Application caches缓存目录
            webSettings.setAppCacheEnabled(true);// 开启Application Cache功能
            //自动适配
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);
            //支持js
            webSettings.setJavaScriptEnabled(true);
            mWebview.addJavascriptInterface(new IJsFuncions((Activity) mContext), "IJsFunctions");
            webSettings.setDefaultTextEncodingName("GBK");//设置字符编码

            mCookieManager = CookieManager.getInstance();
            mCookieManager.setAcceptCookie(true);
        }
    }

    public WebView setCookie(@NonNull String url, @NonNull String cookie) {
        if (mWebview == null) return null;
        mCookieManager.setCookie(url, cookie);
        CookieSyncManager.getInstance().sync();
        return mWebview;
    }

    public WebView setCookies(@NonNull String url, @NonNull List<String> cookies) {
        if (mWebview == null) return null;
        Map<String, String[]> cookiesMap = new HashMap<>();
        cookiesMap.put(url, cookies == null ? null : cookies.toArray(new String[]{}));
        mCookieManager.setCookies(cookiesMap);
        return mWebview;
    }

    public WebView setWebviewClient(WebViewClient viewClient) {
        if (mWebview != null) mWebview.setWebViewClient(viewClient);
        return mWebview;
    }

    public WebView setWebChromeClient(WebChromeClient webChromeClient) {
        if (mWebview != null) mWebview.setWebChromeClient(webChromeClient);
        return mWebview;
    }

    public WebView addJavascriptInterface(Object jsFunc, String callbackName) {
        if (mWebview == null) return null;
        WebSettings settings = mWebview.getSettings();
        if (jsFunc != null && settings != null) {
            settings.setJavaScriptEnabled(true);
            mWebview.addJavascriptInterface(jsFunc, callbackName);
        }
        return mWebview;
    }

    public WebView setCachePath(@NonNull String cachePath) {
        if (mWebview == null) return null;
        mWebview.getSettings().setAppCachePath(cachePath);         // 设置Application caches缓存目录
        return mWebview;
    }

    /**
     * 加载链接,
     *
     * @param url
     */
    public void loadUrl(@Nullable String url) {
        if (mWebview == null || TextUtils.isEmpty(url)) return;
        if (url.startsWith("http://") || url.startsWith("https://"))
            mWebview.loadUrl(url);
    }

    /**
     * 加载富文本
     *
     * @param data
     */
    public void loadData(@NonNull String data) {
        if (mWebview == null) return;
        mWebview.loadData(data, "text/html; charset=utf-8", "UTF-8");
    }

    /**
     * 在Activity或Fragment的onDestroy时调用
     */
    public void destroy() {
        if (mWebview != null) {
            //10.11 在退出时候消除溢出,经验证有效降低CPU使用率
            try {
                mWebview.loadUrl("about:blank");
            } catch (Exception e) {
                e.printStackTrace();
            }
            mWebview.stopLoading();
            mWebview.removeAllViews();
            mWebview.destroy();
        }
    }

    /**
     * 回退
     */
    public void goBack() {
        if (mWebview != null) {
            mWebview.goBack();
        }
    }
}
