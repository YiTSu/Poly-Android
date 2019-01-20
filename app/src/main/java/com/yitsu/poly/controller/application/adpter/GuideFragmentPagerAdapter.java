package com.yitsu.poly.controller.application.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yitsu.poly.view.splashUI.guideUI.GuideOneFragment;
import com.yitsu.poly.view.splashUI.guideUI.GuideThreeFragment;
import com.yitsu.poly.view.splashUI.guideUI.GuideTwoFragment;

/**
 * Created by butterfly on 2018/11/25.
 * 用于引导界面的三个Fragment的滑动页面
 */

public class GuideFragmentPagerAdapter extends FragmentPagerAdapter {

    public GuideFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new GuideOneFragment();
        }else if (position == 1){
            return new GuideTwoFragment();
        }else {
            return new GuideThreeFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
