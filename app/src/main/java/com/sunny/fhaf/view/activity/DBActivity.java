package com.sunny.fhaf.view.activity;

import android.view.View;
import android.widget.TextView;

import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.sunny.fhaf.R;
import com.sunny.fhaf.db.UserBean;
import com.sunny.fhaf.db.UserBean_Table;
import com.sunny.uilib.base.BaseActivity;

import java.util.Arrays;
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
 * @date 2018/4/23 11:37
 * @annotation 数据库
 * 这里采用DBFlow数据库框架
 * 本例以UserBean表为实验对象
 */
public class DBActivity extends BaseActivity {
    @BindView(R.id.tv_db_info)
    TextView mTvInfo;

    @Override
    protected int getLayoutId() {
        setTitle(R.string.app_title_db);
        //为了方便测试，进来时先删除UserBean表
        new Delete().from(UserBean.class).execute();
        return R.layout.app_activity_db;
    }

    /**
     * 增加一条数据
     *
     * @param view
     */
    private int addNum = 0;

    public void dbAdd(View view) {
        ++addNum;
        UserBean userBean = new UserBean();
        userBean.setUserName("Test:");
        userBean.setUserId("" + (100 + addNum));
        userBean.setAddress("Shenzhen");
        userBean.insert();
        dbQuery(null);
    }

    public void dbDel(View view) {
        UserBean userBean = new Select().from(UserBean.class)
                .querySingle();
        if (userBean != null) {
            userBean.delete();
            dbQuery(null);
        } else {
            freshUI("没有数据了！");
        }
    }

    public void dbChange(View view) {
        UserBean userBean = new Select().from(UserBean.class)
                .querySingle();
        if (userBean != null) {
            userBean.setUserId("101010");
            userBean.setUserName("Change");
            userBean.update();
            dbQuery(null);
        } else {
            freshUI("没有数据了！");
        }
    }

    public void dbQuery(View view) {
        List<UserBean> userBeanList = new Select().from(UserBean.class)
                .queryList();
        if (userBeanList != null && userBeanList.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (UserBean userBean : userBeanList) {
                stringBuilder.append("===================\n");
                stringBuilder.append(userBean.toString());
                stringBuilder.append("===================\n");
            }
            freshUI(stringBuilder.toString());
        }
    }

    private void freshUI(String s) {
        mTvInfo.setText("数据库信息如下：\n" + s);
    }
}
