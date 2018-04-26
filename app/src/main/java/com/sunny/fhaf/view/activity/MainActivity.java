package com.sunny.fhaf.view.activity;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.sunny.baselibs.utils.CountDownTimer;
import com.sunny.fhaf.R;
import com.sunny.fhaf.db.UserBean;
import com.sunny.fhaf.model.UserData;
import com.sunny.fhaf.utils.PageUtils;
import com.sunny.uilib.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tv_info)
    TextView mTvInfo;
    @BindView(R.id.main_btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_count)
    TextView mTvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testDB();
    }

    @Override
    protected int getLayoutId() {
        setTitle(R.string.app_title_main);
        return R.layout.app_activity_main;
    }

    /**
     * 测试数据库
     */
    private void testDB() {
        UserBean userBean = new Select().from(UserBean.class).querySingle();
        if (userBean == null) {
            userBean = new UserBean();
            userBean.setUserName("Sunny");
            userBean.setBirthday("1990-01-01");
            userBean.setEmail("450512747@qq.com");
            userBean.setTel("15812345678");
            userBean.setAddress("广东省深圳市南山区");
            userBean.save();
        }
    }

    public void login(View view) {
        UserData userData = ViewModelProviders.of(this).get(UserData.class);
        userData.userBeanLiveData.observe(this, userBean -> {
            //利用UserBean数据来更新UI
            mTvInfo.setText(userBean.toString());
        });

        findViewById(R.id.main_btn_login).setOnClickListener(v -> userData.getUserInfo());

    }

    /**
     * 网络请求
     *
     * @param view
     */
    public void doNet(View view) {
        PageUtils.route(mContext, NetActivity.class);
    }

    public void dbOperate(View view) {
        PageUtils.route(mContext, DBActivity.class);
    }

    public void h5Operate(View view) {
        PageUtils.route(mContext, H5Activity.class);
    }

    public void takeCamera(View view) {
        PageUtils.route(mContext, FHAF_CameraActivity.class);
    }

    public void appFrame(View view) {
        PageUtils.route(mContext, AppFrameActivity.class);
    }

    public void startCountDown(View view) {
        CountDownTimer countDownTimer = CountDownTimer.getInstance((Activity) mContext, 60);
        if (countDownTimer != null) {
            countDownTimer.setCountDownCallback(new CountDownTimer.ICountDownCallback() {
                @Override
                public void onStart() {
                    mTvCount.setText("开始倒计时");
                }

                @Override
                public void onTick(long millisUntilFinished) {
                    mTvCount.setText("倒计时：" + millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    mTvCount.setText("倒计时结束");
                }
            });
            countDownTimer.startCountDown();
        }
    }
}
