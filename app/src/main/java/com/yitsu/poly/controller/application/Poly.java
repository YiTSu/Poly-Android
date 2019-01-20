package com.yitsu.poly.controller.application;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.alibaba.wxlib.util.SysUtil;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.model.type.Type;
import com.yitsu.poly.service.PhpService;
import com.yitsu.poly.view.splashUI.tribeUI.AtMessageListUI;
import com.yitsu.poly.view.splashUI.tribeUI.ChattingPageUI;
import com.yitsu.poly.view.splashUI.tribeUI.SelectTribeAtMemberUI;
import com.yitsu.poly.view.splashUI.tribeUI.TribeConversationListOperation;
import com.yitsu.poly.view.splashUI.tribeUI.TribeConversationListUI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by butterfly on 2018/12/8.
 * APP的Application，实现应用的一些初始化操作
 */

public class Poly extends Application {
    public static final String APP_KEY = "23666480";//此APP的OpenIM的APP_KEY
    public static final String MOB_APP_KEY = "1cd02a3b9a81e";
    public static final String MOB_APP_SECRET = "4755e9ed48aba9e1b54a441005fac8c0";
    public static final String BASE_URL = "http://188.131.157.253/PolyAPI/";
    public static PhpService service = null;
    public static SharedPreferences userPreference;
    public static SharedPreferences infoPreference;
    public static Location location;
    public static LocationManager locationManager;
    public static int AcsRange = 50; //默认覆盖距离
    private static Poly instance;
    private List<Activity> list = new ArrayList<>(); //放置Activity，用于Activity的清理

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(PhpService.class);
        /**
         *必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
         */
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
        /**
         *第一个参数是Application Context
         *这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
         */
        if (SysUtil.isMainProcess()) {
            YWAPI.init(this, APP_KEY);
        }

        /**
         *实现即时通讯服务的绑定
         */
        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT, TribeConversationListUI.class);
        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_OPERATION_POINTCUT, TribeConversationListOperation.class);
        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, ChattingPageUI.class);
        AdviceBinder.bindAdvice(PointCutEnum.TRIBE_ACTIVITY_SELECT_AT_MEMBER, SelectTribeAtMemberUI.class);
        AdviceBinder.bindAdvice(PointCutEnum.TRIBE_ACTIVITY_AT_MSG_LIST, AtMessageListUI.class);

        /**
         * 注册短信MobSDK
         */

        cn.smssdk.SMSSDK.initSDK(this, MOB_APP_KEY, MOB_APP_SECRET);

        userPreference = getSharedPreferences(ConstantString.USER, MODE_APPEND);

    }

    Poly() {

    }

    public synchronized static Poly getInstance() { //得到Application实例的方法，用于必要时重新初始化需要的变量（如locationManager等）
        if (instance == null) {
            instance = new Poly();
        }
        return instance;
    }

    public void addActivity(Activity activity) { //存储未被finish的Activity用于之后的清理,如果finish掉则无需调用此方法
        list.add(activity);
    }

    public void exit() {//整个APP的退出函数，将本APP现存的所有Activity结束掉之后进行安全退出
        try {
            for (Activity activity : list) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    @Override
    public void onLowMemory() {//如果碰见内存不够用的情况，调用Android自带的方法
        super.onLowMemory();
        System.gc();
    }


}
