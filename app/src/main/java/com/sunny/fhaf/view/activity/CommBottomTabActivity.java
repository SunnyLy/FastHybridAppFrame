package com.sunny.fhaf.view.activity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.sunny.fhaf.R;
import com.sunny.fhaf.view.fragment.ForthFragment;
import com.sunny.fhaf.view.fragment.HomeFragment;
import com.sunny.fhaf.view.fragment.MineFragment;
import com.sunny.fhaf.view.fragment.SecondFragment;
import com.sunny.fhaf.view.fragment.ThirdFragment;
import com.sunny.uilib.base.BaseActivity;
import com.sunny.uilib.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/24 19:45
 * @annotation 通用的底部Tab选项界面
 */
public class CommBottomTabActivity extends BaseActivity {
    private View mVGBottom;
    @BindView(R.id.layout_content)
    FrameLayout mFLContent;

    private RadioGroup mRadioGroup;

    public static final String INDEX = "index";
    public static final int HOME = 0x1;
    public static final int SECOND = 0x2;
    public static final int THIRD = 0x3;
    public static final int FORTH = 0x4;
    public static final int MINE = 0x5;
    private int mChooseIndex = HOME;

    private HomeFragment mHomeFragment;
    private SecondFragment mSecondFragment;
    private ThirdFragment mThirdFragment;
    private ForthFragment mForthFragment;
    private MineFragment mMineFragment;
    private List<BaseFragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        setTitle("底部导航栏");
        return R.layout.app_activity_tab;
    }

    @Override
    public void onContentChanged() {
        initFragment();
        mVGBottom = findViewById(R.id.layout_bottom_navigate);
        mRadioGroup = (RadioGroup) mVGBottom.findViewById(R.id.rg_tab);
        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.rb_home:
                    mChooseIndex = HOME;
                    break;
                case R.id.rb_goodnight:
                    mChooseIndex = SECOND;
                    break;
                case R.id.rb_coaxtosleep:
                    mChooseIndex = THIRD;
                    break;
                case R.id.rb_discover:
                    mChooseIndex = FORTH;
                    break;
                case R.id.rb_my:
                    mChooseIndex = MINE;
                    break;
            }
            initTabhost();
        });
        initTabhost();
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mSecondFragment = new SecondFragment();
        mThirdFragment = new ThirdFragment();
        mForthFragment = new ForthFragment();
        mMineFragment = new MineFragment();
        mFragments.clear();
        mFragments.add(mHomeFragment);
        mFragments.add(mSecondFragment);
        mFragments.add(mThirdFragment);
        mFragments.add(mForthFragment);
        mFragments.add(mMineFragment);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout_content, mHomeFragment, "Home")
                .add(R.id.layout_content, mSecondFragment, "SayNight")
                .add(R.id.layout_content, mThirdFragment, "SLeep")
                .add(R.id.layout_content, mForthFragment, "Discover")
                .add(R.id.layout_content, mMineFragment, "Mine")
                .commit();
        setFragmentVisible(0);
    }

    private void initTabhost() {
        switch (mChooseIndex) {
            case HOME:
                getSupportFragmentManager().beginTransaction()
                        .show(mHomeFragment)
                        .hide(mSecondFragment)
                        .hide(mThirdFragment)
                        .hide(mForthFragment)
                        .hide(mMineFragment)
                        .commitAllowingStateLoss();
                mRadioGroup.check(R.id.rb_home);
                setFragmentVisible(0);
                break;
            case SECOND:
                getSupportFragmentManager().beginTransaction()
                        .show(mSecondFragment)
                        .hide(mHomeFragment)
                        .hide(mThirdFragment)
                        .hide(mForthFragment)
                        .hide(mMineFragment)
                        .commitAllowingStateLoss();
                mRadioGroup.check(R.id.rb_goodnight);
                setFragmentVisible(1);
                break;

            case THIRD:
                getSupportFragmentManager().beginTransaction()
                        .show(mThirdFragment)
                        .hide(mHomeFragment)
                        .hide(mSecondFragment)
                        .hide(mForthFragment)
                        .hide(mMineFragment)
                        .commitAllowingStateLoss();
                mRadioGroup.check(R.id.rb_coaxtosleep);
                setFragmentVisible(2);
                break;
            case FORTH:
                getSupportFragmentManager().beginTransaction()
                        .show(mForthFragment)
                        .hide(mHomeFragment)
                        .hide(mThirdFragment)
                        .hide(mSecondFragment)
                        .hide(mMineFragment)
                        .commitAllowingStateLoss();
                mRadioGroup.check(R.id.rb_discover);
                setFragmentVisible(3);
                break;
            case MINE:
                getSupportFragmentManager().beginTransaction()
                        .show(mMineFragment)
                        .hide(mHomeFragment)
                        .hide(mThirdFragment)
                        .hide(mSecondFragment)
                        .hide(mForthFragment)
                        .commitAllowingStateLoss();
                mRadioGroup.check(R.id.rb_my);
                setFragmentVisible(4);
                break;
            default:
                break;
        }
    }

    /**
     * 设置哪个Fragment是用户可见的
     * 其它都 是hint的
     *
     * @param position
     */
    private void setFragmentVisible(int position) {
        for (int i = 0; i < mFragments.size(); i++) {
            if (i == position) {
                mFragments.get(i).setUserVisibleHint(true);
            } else {
                mFragments.get(i).setUserVisibleHint(false);
            }
        }
    }
}
