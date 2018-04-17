package com.sunny.fhaf.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/16 20:31
 * @annotation DBFlow创建数据库
 */
@Database(name = FHAFDatabase.DB_NAME, version = FHAFDatabase.DB_VERSION)
public class FHAFDatabase {
    public static final String DB_NAME = "FHAFDatabase";
    public static final int DB_VERSION = 1;
}
