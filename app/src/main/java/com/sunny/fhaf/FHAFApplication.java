package com.sunny.fhaf;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/16 20:32
 * @annotation ....
 */
public class FHAFApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //OKGo网络框架初始化
        OkGo.getInstance().init(this);
        //DBFlow数据库初始化
        FlowManager.init(this);
    }
}
