package com.yitsu.poly.controller.application.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yitsu.poly.view.splashUI.creatAcUI.ActivitiesActivity;
import com.yitsu.poly.view.splashUI.showAcUI.ShowAcOneFragment;
import com.yitsu.poly.view.splashUI.showAcUI.ShowAcThreeFragment;
import com.yitsu.poly.view.splashUI.showAcUI.ShowAcTwoFragment;

/**
 * Created by butterfly on 2018/11/13.
 * 用于展示活动时的三个Fragment滑动页面
 */
public class ActivityFragmentPagerShowAdapter extends FragmentPagerAdapter {

    private static final int COUNT = 3;//Fragment数量
    public ActivityFragmentPagerShowAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == ActivitiesActivity.ONE){
            return new ShowAcOneFragment();
        }else if(position == ActivitiesActivity.TWO){
            return new ShowAcTwoFragment();
        }else{
            return new ShowAcThreeFragment();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
