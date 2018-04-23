package com.sunny.fhaf.view.activity;

import com.sunny.fhaf.R;
import com.sunny.uilib.base.BaseActivity;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/23 11:39
 * @annotation App框架
 */
public class AppFrameActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        setTitle(getString(R.string.app_title_frame));
        return R.layout.app_activity_frame;
    }
}
