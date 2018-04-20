package com.sunny.h5lib;

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
}
