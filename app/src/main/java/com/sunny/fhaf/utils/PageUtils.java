package com.sunny.fhaf.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/18 14:39
 * @annotation 页面跳转工具
 * 我这只写两个基本的跳转，
 * 后面比如，要添加一些什么Flag之类，可以自己往后面加
 */
public class PageUtils {

    /**
     * 不带参数的页面跳转
     *
     * @param context
     * @param target
     */
    public static void route(@NonNull Context context, @NonNull Class<?> target) {
        route(context, target, null);
    }

    /**
     * 携带参数进行跳转
     *
     * @param context
     * @param target
     * @param params
     */
    public static void route(@NonNull Context context, @NonNull Class<?> target, @Nullable Bundle params) {
        Intent intent = new Intent(context, target);
        if (params != null)
            intent.putExtras(params);
        context.startActivity(intent);

    }
}
