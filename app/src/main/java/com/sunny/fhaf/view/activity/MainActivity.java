package com.sunny.fhaf.view.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.sunny.fhaf.R;
import com.sunny.baselibs.base.BaseActivity;
import com.sunny.fhaf.db.UserBean;
import com.sunny.fhaf.model.UserData;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tv_info)
    TextView mTvInfo;
    @BindView(R.id.main_btn_login)
    Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testDB();
    }

    @Override
    protected int getLayoutId() {
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
}
