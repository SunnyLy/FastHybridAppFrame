package com.sunny.uilib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunny.uilib.R;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/19 15:07
 * @annotation 通用标题栏打造
 * 一般的标题 栏都有以下基本元素：
 * 1，返回按钮
 * 2，标题
 * <p>
 * 根据自己业务 的不同，可以自行对其布局文件进行修改
 */
public class FHAFTitleBar extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private ImageView mIvBack;
    private TextView mTvTitle;

    private String mTitle;

    private IOnBackClickListener onBackClickListener;

    public void setOnBackClickListener(IOnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    public void setTitle(String title) {
        this.mTitle = title;
        if (mTvTitle != null) mTvTitle.setText(title);
    }

    public FHAFTitleBar(Context context) {
        this(context, null);
    }

    public FHAFTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FHAFTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.ui_layout_titlebar, null);
        initView(contentView);
        addView(contentView);
    }

    private void initView(View contentView) {
        mIvBack = (ImageView) contentView.findViewById(R.id.titlebar_back);
        mTvTitle = (TextView) contentView.findViewById(R.id.titlebar_title);
        mIvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onBackClickListener != null) {
            onBackClickListener.onBackClick(v);
        } else {

        }
    }

    public interface IOnBackClickListener {
        void onBackClick(View view);
    }
}
