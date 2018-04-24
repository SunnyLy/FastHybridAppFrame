package com.sunny.uilib.utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/24 10:34
 * @annotation ....
 */
public class ToastUtil {

    public static void showToast(@NonNull Activity context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(@NonNull Activity context, @StringRes int strRes) {
        showToast(context, context.getResources().getString(strRes));
    }
}
