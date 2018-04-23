package com.sunny.uilib.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sunny.uilib.R;
import com.sunny.uilib.utils.ScreenUtils;
import com.sunny.uilib.utils.immersive.ImmersiveStatusBarUtils;
import com.sunny.uilib.widget.FHAFTitleBar;

import butterknife.ButterKnife;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/17 14:58
 * @annotation 抽象基类，一般在此类进行一些模块方法的声明
 */
public abstract class BaseActivity extends AppCompatActivity implements LifecycleOwner, View.OnClickListener {

    public Context mContext;
    private LifecycleRegistry mLifecycleRegistry;

    public FHAFTitleBar mTitleBar;
    private LinearLayout parentLayout;
    private FrameLayout contentLayout;
    private int titleHeight;
    private int topPpadding;

    protected abstract int getLayoutId();

    @Override
    public void setTitle(int titleId) {
        this.setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mTitleBar != null) {
            mTitleBar.setTitle(title.toString());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        handleTitleBar();
        setContentView(getLayoutId());
        //沉浸式
        ImmersiveStatusBarUtils.getInstance().initBar(this);
        ButterKnife.bind(this);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    public void removeTitle() {
        if (parentLayout != null) {
            parentLayout.removeViewAt(0);
        }
    }

    public void setContentView(@LayoutRes int layoutId) {
        View cotent = LayoutInflater.from(mContext).inflate(layoutId, null);
        if (cotent != null)
            contentLayout.addView(cotent);
    }


    /**
     * 处理标题栏
     */
    private void handleTitleBar() {
        if (mTitleBar == null) {
            mTitleBar = new FHAFTitleBar(this);
        }
        this.titleHeight = (int) (this.getResources().getDisplayMetrics().density * 50.0F);
        this.topPpadding = 0;
        if (Build.VERSION.SDK_INT >= 19) {
            this.topPpadding = ScreenUtils.getStatusBarHeight(this);
            this.titleHeight += this.topPpadding;
        }

        this.parentLayout = new LinearLayout(this);
        this.parentLayout.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        this.parentLayout.setLayoutParams(layoutParams);
        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(-1, this.titleHeight);
        mTitleBar.setLayoutParams(params);
        mTitleBar.setClipToPadding(true);
        if (Build.VERSION.SDK_INT >= 19) {
            mTitleBar.setPadding(0, this.topPpadding, 0, 0);
        } else {
            mTitleBar.setPadding(0, 0, 0, 0);
        }

        this.parentLayout.addView(mTitleBar);
        this.contentLayout = new FrameLayout(this);
        android.widget.LinearLayout.LayoutParams contentParams = new android.widget.LinearLayout.LayoutParams(-1, -1);
        this.contentLayout.setLayoutParams(contentParams);
        this.parentLayout.addView(this.contentLayout);
        super.setContentView(this.parentLayout);
        this.initCustomTitleConfig();
    }

    private void initCustomTitleConfig() {
        mTitleBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTitleBar.setOnBackClickListener(new FHAFTitleBar.IOnBackClickListener() {
            @Override
            public void onBackClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
    }

    @Override
    protected void onDestroy() {
        mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        super.onDestroy();
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    public void onClick(View v) {

    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
