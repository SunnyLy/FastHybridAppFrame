package com.sunny.h5lib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sunny.h5lib.view.X5Webview;
import com.sunny.uilib.base.BaseActivity;
import com.sunny.uilib.widget.FHAFTitleBar;

import butterknife.BindView;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/24 13:56
 * @annotation ....
 */
public abstract class X5WebviewActivity extends BaseActivity {
    public X5Webview mWebview;

    @Override
    protected int getLayoutId() {
        return R.layout.h5_layout_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebview = (X5Webview) findViewById(R.id.x5Webview);
        mTitleBar.setOnBackClickListener(new FHAFTitleBar.IOnBackClickListener() {
            @Override
            public void onBackClick(View view) {
                if (mWebview != null && mWebview.canGoBack()) {
                    mWebview.goBack();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebview.destroy();
    }
}
