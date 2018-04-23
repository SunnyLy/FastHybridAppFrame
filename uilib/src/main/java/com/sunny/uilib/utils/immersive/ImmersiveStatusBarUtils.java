package com.sunny.uilib.utils.immersive;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/20 14:00
 * @annotation Android沉浸式状态栏
 */
public class ImmersiveStatusBarUtils {
    private static Map<String, BarParams> mMap = new HashMap<>();
    private static Map<String, BarParams> mTagMap = new HashMap<>();
    private static Map<String, ArrayList<String>> mTagKeyMap = new HashMap<>();
    private Activity mActivity;
    private Window mWindow;
    private ViewGroup mDecorView;
    private ViewGroup mContentView;
    private BarParams mBarParams;
    private BarConfig mConfig;

    private String mImmersionBarName;
    private String mActivityName;

    private static final ImmersiveStatusBarUtils ourInstance = new ImmersiveStatusBarUtils();

    public static ImmersiveStatusBarUtils getInstance() {
        return ourInstance;
    }

    private ImmersiveStatusBarUtils() {
    }

    /**
     * 初始化状态栏和导航栏
     */
    public void initBar(Activity activity) {
        initParams(activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;  //防止系统栏隐藏时内容区域大小发生变化
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !OSUtils.isEMUI3_1()) {
                uiFlags = initBarAboveLOLLIPOP(uiFlags); //初始化5.0以上，包含5.0
            } else {
                initBarBelowLOLLIPOP(); //初始化5.0以下，4.4以上沉浸式
                solveNavigation();  //解决android4.4有导航栏的情况下，activity底部被导航栏遮挡的问题和android 5.0以下解决状态栏和布局重叠问题
            }
            uiFlags = hideBar(uiFlags);  //隐藏状态栏或者导航栏
            mWindow.getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    /**
     * 初始化参数
     *
     * @param activity
     */
    private void initParams(@NonNull Activity activity) {
        WeakReference<Activity> activityWeakReference = new WeakReference<Activity>(activity);
        mActivity = activityWeakReference.get();
        mWindow = mActivity.getWindow();
        mActivityName = mActivity.getClass().getName();
        mImmersionBarName = mActivityName;
        mDecorView = (ViewGroup) mWindow.getDecorView();
        mContentView = (ViewGroup) mDecorView.findViewById(android.R.id.content);
        mConfig = new BarConfig(activity);
        if (mMap.get(mImmersionBarName) == null) {
            mBarParams = new BarParams();
            mMap.put(mImmersionBarName, mBarParams);
        } else {
            mBarParams = mMap.get(mImmersionBarName);
        }
    }

    /**
     * 初始化android 5.0以上状态栏和导航栏
     *
     * @param uiFlags the ui flags
     * @return the int
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int initBarAboveLOLLIPOP(int uiFlags) {
        uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;  //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态栏遮住。
        if (mBarParams.fullScreen && mBarParams.navigationBarEnable) {
            uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION; //Activity全屏显示，但导航栏不会被隐藏覆盖，导航栏依然可见，Activity底部布局部分会被导航栏遮住。
        }
        mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (mConfig.hasNavigtionBar()) {  //判断是否存在导航栏
            mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);  //需要设置这个才能设置状态栏颜色
        if (mBarParams.statusBarFlag)
            mWindow.setStatusBarColor(ColorUtils.blendARGB(mBarParams.statusBarColor,
                    mBarParams.statusBarColorTransform, mBarParams.statusBarAlpha));  //设置状态栏颜色
        else
            mWindow.setStatusBarColor(ColorUtils.blendARGB(mBarParams.statusBarColor,
                    Color.TRANSPARENT, mBarParams.statusBarAlpha));  //设置状态栏颜色
        if (mBarParams.navigationBarEnable)
            mWindow.setNavigationBarColor(ColorUtils.blendARGB(mBarParams.navigationBarColor,
                    mBarParams.navigationBarColorTransform, mBarParams.navigationBarAlpha));  //设置导航栏颜色
        return uiFlags;
    }

    /**
     * 初始化android 4.4和emui3.1状态栏和导航栏
     */
    private void initBarBelowLOLLIPOP() {
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//透明状态栏
        setupStatusBarView(); //创建一个假的状态栏
        if (mConfig.hasNavigtionBar()) {  //判断是否存在导航栏，是否禁止设置导航栏
            if (mBarParams.navigationBarEnable && mBarParams.navigationBarWithKitkatEnable)
                mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//透明导航栏，设置这个，如果有导航栏，底部布局会被导航栏遮住
            else
                mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            setupNavBarView();   //创建一个假的导航栏
        }
    }

    /**
     * 设置一个可以自定义颜色的状态栏
     */
    private void setupStatusBarView() {
        if (mBarParams.statusBarView == null) {
            mBarParams.statusBarView = new View(mActivity);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                mConfig.getStatusBarHeight());
        params.gravity = Gravity.TOP;
        mBarParams.statusBarView.setLayoutParams(params);
        if (mBarParams.statusBarFlag)
            mBarParams.statusBarView.setBackgroundColor(ColorUtils.blendARGB(mBarParams.statusBarColor,
                    mBarParams.statusBarColorTransform, mBarParams.statusBarAlpha));
        else
            mBarParams.statusBarView.setBackgroundColor(ColorUtils.blendARGB(mBarParams.statusBarColor,
                    Color.TRANSPARENT, mBarParams.statusBarAlpha));
        mBarParams.statusBarView.setVisibility(View.VISIBLE);
        ViewGroup viewGroup = (ViewGroup) mBarParams.statusBarView.getParent();
        if (viewGroup != null)
            viewGroup.removeView(mBarParams.statusBarView);
        mDecorView.addView(mBarParams.statusBarView);
    }

    /**
     * 解决安卓4.4和EMUI3.1导航栏与状态栏的问题，以及系统属性fitsSystemWindows的坑
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void solveNavigation() {
        for (int i = 0, count = mContentView.getChildCount(); i < count; i++) {
            View childView = mContentView.getChildAt(i);
            if (childView instanceof ViewGroup) {
                if (childView instanceof DrawerLayout) {
                    View childAt1 = ((DrawerLayout) childView).getChildAt(0);
                    if (childAt1 != null) {
                        mBarParams.systemWindows = childAt1.getFitsSystemWindows();
                        if (mBarParams.systemWindows) {
                            mContentView.setPadding(0, 0, 0, 0);
                            return;
                        }
                    }
                } else {
                    mBarParams.systemWindows = childView.getFitsSystemWindows();
                    if (mBarParams.systemWindows) {
                        mContentView.setPadding(0, 0, 0, 0);
                        return;
                    }
                }
            }

        }
        // 解决android4.4有导航栏的情况下，activity底部被导航栏遮挡的问题
        if (mConfig.hasNavigtionBar() && !mBarParams.fullScreenTemp && !mBarParams.fullScreen) {
            if (mConfig.isNavigationAtBottom()) { //判断导航栏是否在底部
                if (!mBarParams.isSupportActionBar) { //判断是否支持actionBar
                    if (mBarParams.navigationBarEnable && mBarParams.navigationBarWithKitkatEnable) {
                        if (mBarParams.fits)
                            mContentView.setPadding(0, mConfig.getStatusBarHeight(),
                                    0, mConfig.getNavigationBarHeight()); //有导航栏，获得rootView的根节点，然后设置距离底部的padding值为导航栏的高度值
                        else
                            mContentView.setPadding(0, 0, 0, mConfig.getNavigationBarHeight());
                    } else {
                        if (mBarParams.fits)
                            mContentView.setPadding(0, mConfig.getStatusBarHeight(), 0, 0);
                        else
                            mContentView.setPadding(0, 0, 0, 0);
                    }
                } else {
                    //支持有actionBar的界面
                    if (mBarParams.navigationBarEnable && mBarParams.navigationBarWithKitkatEnable)
                        mContentView.setPadding(0, mConfig.getStatusBarHeight() +
                                mConfig.getActionBarHeight() + 10, 0, mConfig.getNavigationBarHeight());
                    else
                        mContentView.setPadding(0, mConfig.getStatusBarHeight() +
                                mConfig.getActionBarHeight() + 10, 0, 0);
                }
            } else {
                if (!mBarParams.isSupportActionBar) {
                    if (mBarParams.navigationBarEnable && mBarParams.navigationBarWithKitkatEnable) {
                        if (mBarParams.fits)
                            mContentView.setPadding(0, mConfig.getStatusBarHeight(),
                                    mConfig.getNavigationBarWidth(), 0); //不在底部，设置距离右边的padding值为导航栏的宽度值
                        else
                            mContentView.setPadding(0, 0, mConfig.getNavigationBarWidth(), 0);
                    } else {
                        if (mBarParams.fits)
                            mContentView.setPadding(0, mConfig.getStatusBarHeight(), 0, 0);
                        else
                            mContentView.setPadding(0, 0, 0, 0);
                    }
                } else {
                    //支持有actionBar的界面
                    if (mBarParams.navigationBarEnable && mBarParams.navigationBarWithKitkatEnable)
                        mContentView.setPadding(0, mConfig.getStatusBarHeight() +
                                mConfig.getActionBarHeight() + 10, mConfig.getNavigationBarWidth(), 0);
                    else
                        mContentView.setPadding(0, mConfig.getStatusBarHeight() +
                                mConfig.getActionBarHeight() + 10, 0, 0);
                }
            }
        } else {
            if (!mBarParams.isSupportActionBar) {
                if (mBarParams.fits)
                    mContentView.setPadding(0, mConfig.getStatusBarHeight(), 0, 0);
                else
                    mContentView.setPadding(0, 0, 0, 0);
            } else {
                //支持有actionBar的界面
                mContentView.setPadding(0, mConfig.getStatusBarHeight() + mConfig.getActionBarHeight() + 10, 0, 0);
            }
        }
    }

    /**
     * Hide bar.
     * 隐藏或显示状态栏和导航栏。
     *
     * @param uiFlags the ui flags
     * @return the int
     */

    private int hideBar(int uiFlags) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            switch (mBarParams.barHide) {
                case FLAG_HIDE_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.INVISIBLE;
                    break;
                case FLAG_HIDE_STATUS_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.INVISIBLE;
                    break;
                case FLAG_HIDE_NAVIGATION_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                    break;
                case FLAG_SHOW_BAR:
                    uiFlags |= View.SYSTEM_UI_FLAG_VISIBLE;
                    break;
            }
        }
        return uiFlags | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    }
}
