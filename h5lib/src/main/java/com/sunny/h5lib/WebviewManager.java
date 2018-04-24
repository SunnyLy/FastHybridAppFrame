package com.sunny.h5lib;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.sunny.h5lib.service.PreLoadX5Service;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/18 14:34
 * @annotation ....
 */
public class WebviewManager {
    private static final WebviewManager ourInstance = new WebviewManager();

    public static WebviewManager getInstance() {
        return ourInstance;
    }

    private WebviewManager() {
    }

    /**
     * 提前初始化Webview
     *
     * @param context
     */
    public void preInitWebview(@NonNull Context context) {
        Intent x5Intent = new Intent(context, PreLoadX5Service.class);
        context.startService(x5Intent);
    }
}
