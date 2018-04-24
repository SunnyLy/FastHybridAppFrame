package com.sunny.fhaf.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.sunny.fhaf.R;
import com.sunny.h5lib.X5WebviewActivity;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/23 11:39
 * @annotation 项目中的webview内核采用腾讯X5内核
 */
public class H5Activity extends X5WebviewActivity {
    private static final String TEST_URL = "https://www.baidu.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitleBar.setTitle(getString(R.string.app_title_h5));
//        testSetCookies();
        mWebview.loadUrl("https://www.baidu.com");
        handleCookie();
    }

    /**
     * 往请求中设置Cookie
     * cookie一般为一个key-value字符串，如果cookie有两对字符串，
     * 或者更多，需要多次setCookie()，否则webview只会获取第一对。
     * setCookie必须在loadUrl()之前调用
     */
    private void testSetCookies() {
        mWebview.setCookie(TEST_URL, "userId=123456789");
        mWebview.setCookie(TEST_URL, "userName=Sunny");
        mWebview.setCookie(TEST_URL, "city=Shenzhen");

    }

    /**
     * 获取URL中的Cookie,
     */
    private void handleCookie() {
        mWebview.setWebviewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                //要获取请求URL中的Cookie，必须在服务器响应后，它才会返回在请求头里
                //因此，只能在页面加载完成才能获取到
                String strCookie = CookieManager.getInstance().getCookie(TEST_URL);
                Log.e("xxxxx", "请求中的Cookie:" + strCookie);
                //todo 把拿到的cookie存入本地数据库，方便第二次打开链接时，如果URL中没有cookie，
                //则把cookie拼接到URL后面
                super.onPageFinished(webView, s);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
