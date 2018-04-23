package com.sunny.fhaf;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.sunny.fhaf.utils.PageUtils;
import com.sunny.fhaf.view.activity.MainActivity;
import com.sunny.uilib.base.BaseActivity;


/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/17 14:59
 * @annotation 闪屏界面
 * 基本上每个应用都有一个用于进入到App内页
 * 的过渡界面，一般停留2s左右。
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        removeTitle();
        return R.layout.app_activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PageUtils.route(mContext, MainActivity.class);
                finish();
            }
        }, 2000);
    }
}
