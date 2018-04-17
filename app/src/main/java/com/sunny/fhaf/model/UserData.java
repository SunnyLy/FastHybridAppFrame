package com.sunny.fhaf.model;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.raizlabs.android.dbflow.sql.language.Select;
import com.sunny.fhaf.db.UserBean;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/17 16:14
 * @annotation 数据源采用官方推荐的ViewModel模型
 * ViewModel，相当于是一个数据工厂，亦或是UI与云之间的桥梁
 * 其持有LiveData对象的引用
 * 业务逻辑也在这里面进行
 */
public class UserData extends ViewModel {

    public final MutableLiveData<UserBean> userBeanLiveData = new MutableLiveData<>();

    public void getUserInfo() {
//        UserBean userBean = new Select().from(UserBean.class).
//                where(UserBean_Table.userId.eq("1000")).querySingle();
//        if (userBean == null) {
//            userBean.setUserName("TestForLiveData");
//            userBean.setUserId("1000");
//            userBean.save();
//        }
//        userBeanLiveData.postValue(userBean);
    }
}
