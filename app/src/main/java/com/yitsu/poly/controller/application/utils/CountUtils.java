package com.yitsu.poly.controller.application.utils;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.view.splashUI.mainUI.MainActivity;

/**
 * Created by butterfly on 2018/12/16.
 * 账号管理的工具类
 */

public class CountUtils {
    public static void exitUsername(){
        IYWLoginService mLoginService = MainActivity.mIMKit.getLoginService();
        mLoginService.logout(new IWxCallback() {
            @Override
            public void onSuccess(Object... objects) {
                System.out.println("OpenIM退出成功");
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i) {

            }
        });
        Poly.userPreference.edit().clear().commit();
        Poly.infoPreference.edit().clear().commit();
        MainActivity.mIMKit = null;
    }
}
