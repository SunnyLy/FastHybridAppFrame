package com.sunny.h5lib.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tencent.smtt.sdk.QbSdk;

/**
 * ------------------------------------------------
 * Copyright © 2014年 CLife. All Rights Reserved.
 * Shenzhen H&T Intelligent Control Co.,Ltd.
 * -----------------------------------------------
 *
 * @Author sunny
 * @Date 2017/7/27  09:39
 * @Version v1.0.0
 * @Annotation
 *   tecent的Qbsdk在加载时，会报NullPointerException:Attempt to invoke virtual method 'java.io.File android.content.Context.getDir(java.lang.String, int)' on a null object reference
07-27 10:26:50.613 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.tbs.sdk.extension.s.o(Unknown Source)
07-27 10:26:50.614 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.tbs.sdk.extension.s.q(Unknown Source)
07-27 10:26:50.614 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.tbs.sdk.extension.s.<init>(Unknown Source)
07-27 10:26:50.614 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.tbs.sdk.extension.s.a(Unknown Source)
07-27 10:26:50.614 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.tbs.sdk.extension.TbsSDKExtension.c(Unknown Source)
07-27 10:26:50.614 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.tbs.sdk.extension.TbsSDKExtension.<init>(Unknown Source)
07-27 10:26:50.614 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.tbs.sdk.extension.TbsSDKExtension.<init>(Unknown Source)
07-27 10:26:50.615 25744-25744/com.het.sleep.dolphin W/System.err:     at java.lang.reflect.Constructor.newInstance(Native Method)
07-27 10:26:50.615 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.smtt.sdk.QbSdk.b(Unknown Source)
07-27 10:26:50.615 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.smtt.sdk.QbSdk.a(Unknown Source)
07-27 10:26:50.615 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.smtt.sdk.QbSdk.a(Unknown Source)
07-27 10:26:50.615 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.smtt.sdk.TbsShareManager.findCoreForThirdPartyApp(Unknown Source)
07-27 10:26:50.615 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.smtt.sdk.TbsDownloader.a(Unknown Source)
07-27 10:26:50.615 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.smtt.sdk.TbsDownloader.needDownload(Unknown Source)
07-27 10:26:50.615 25744-25744/com.het.sleep.dolphin W/System.err:     at com.tencent.smtt.sdk.QbSdk.initX5Environment(Unknown Source)
 *
 *
 *  <a href="http://bbs.mb.qq.com/thread-1944166-1-1.html">查看官方开发者论坛</a>
 */

public class PreLoadX5Service extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public PreLoadX5Service() {
        super("PreLoadX5Service");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (intent != null){
            initX5();
            preInitX5WebCore();
        }
    }
    private void initX5() {
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }
    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
        }

        @Override
        public void onCoreInitFinished() {

        }
    };
    private void preInitX5WebCore() {
        if(!QbSdk.isTbsCoreInited()) {

            // preinit只需要调用一次，如果已经完成了初始化，那么就直接构造view
            QbSdk.preInit(getApplicationContext(), null);// 设置X5初始化完成的回调接口

        }
    }
}
