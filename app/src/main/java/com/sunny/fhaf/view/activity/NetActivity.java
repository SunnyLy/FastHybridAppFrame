package com.sunny.fhaf.view.activity;

import android.view.View;
import android.widget.TextView;

import com.sunny.fhaf.R;
import com.sunny.fhaf.model.ApiInfo;
import com.sunny.uilib.base.BaseActivity;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import butterknife.BindView;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ------------------------------------------------
 * Copyright © 2014-2018 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @author Sunny
 * @version v1.1.6
 * @date 2018/4/23 11:34
 * @annotation 网络请求
 * 采用第三方网络框架：https://github.com/jeasonlzy/okhttp-OkGo
 * 1，Post;
 * 2,Get;
 * 3,Cookie管理
 */
public class NetActivity extends BaseActivity {
    @BindView(R.id.tv_net_info)
    TextView mTvInfo;

    @Override
    protected int getLayoutId() {
        setTitle(R.string.app_title_net);
        return R.layout.app_activity_net;
    }

    /**
     * Get 请求
     *
     * @param view
     */
    public void getRequest(View view) {
        //测试时，可以把这个URL换成自己的后台接口
        EasyHttp.get("/v1/app/chairdressing/skinAnalyzePower/skinTestResult")
                .readTimeOut(30 * 1000)//局部定义读超时 ,可以不用定义
                .writeTimeOut(30 * 1000)
                .connectTimeout(30 * 1000)
                //.headers("","")//设置头参数
                //.params("name","张三")//设置参数
                //.addInterceptor()
                //.addConverterFactory()
                //.addCookie()
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        freshUI(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        if (response != null) {
                            freshUI(response);
                        }
                    }
                });
        //当返回的数据是一个json字符串，指定的Bean时
               /* .execute(new SimpleCallBack<TestBean>() {
                    @Override
                    public void onError(ApiException e) {
                        showToast(e.getMessage());
                    }

                    @Override
                    public void onSuccess(TestBean response) {
                        if (response != null) showToast(response.getMsg());
                    }
                });*/
    }

    /**
     * Post请求
     *
     * @param view
     */
    public void postRequest(View view) {
        EasyHttp.post("v1/app/chairdressing/news/favorite")
                .params("newsId", "552")
                .accessToken(true)
                .timeStamp(true)
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        freshUI(e.getMessage());
                    }

                    @Override
                    public void onSuccess(String response) {
                        freshUI(response);
                    }
                });
    }

    public void postRequestByParams(View view) {
        ApiInfo apiInfo = new ApiInfo();
        ApiInfo.ApiInfoBean apiInfoBean = apiInfo.new ApiInfoBean();
        apiInfoBean.setApiKey("12345");
        apiInfoBean.setApiName("zhou-you");
        apiInfo.setApiInfo(apiInfoBean);
        EasyHttp.post("/client/shipper/getCarType")
                .baseUrl("http://WuXiaolong.me")
                //如果是body的方式提交object，必须要加GsonConverterFactory.create()
                //他的本质就是把object转成json给到服务器，所以必须要加Gson Converter
                //切记！切记！切记！  本例可能地址不对只做演示用
                .addConverterFactory(GsonConverterFactory.create())
                .upObject(apiInfo)//这种方式会自己把对象转成json提交给服务器
                .execute(new SimpleCallBack<String>() {
                    @Override
                    public void onError(ApiException e) {
                        freshUI(e.getMessage() + "  " + e.getCode());
                    }

                    @Override
                    public void onSuccess(String response) {
                        freshUI(response);
                    }
                });
    }

    /**
     * 刷新界面
     *
     * @param message
     */
    private void freshUI(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("返回结果:\n");
        stringBuilder.append(message);
        mTvInfo.setText(stringBuilder.toString());
        showToast(message);

    }
}
