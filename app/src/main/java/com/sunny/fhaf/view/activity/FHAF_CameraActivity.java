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
 * @annotation Android拍照，相册浏览
 */
public class FHAF_CameraActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        setTitle(R.string.app_title_camera);
        return R.layout.app_activity_camera;
    }
}
