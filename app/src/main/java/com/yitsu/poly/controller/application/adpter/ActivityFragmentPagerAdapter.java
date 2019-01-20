package com.yitsu.poly.controller.application.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yitsu.poly.view.splashUI.creatAcUI.ActivitiesActivity;
import com.yitsu.poly.view.splashUI.creatAcUI.CreateAcOneFragment;
import com.yitsu.poly.view.splashUI.creatAcUI.CreateAcThreeFragment;
import com.yitsu.poly.view.splashUI.creatAcUI.CreateAcTwoFragment;


/**
 * Created by butterfly on 2018/11/12.
 * 用于创建活动时的三个Fragment滑动页面
 */
public class ActivityFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final int COUNT = 3;//Fragment数量
    public ActivityFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == ActivitiesActivity.ONE){
            return new CreateAcOneFragment();
        }else if(position == ActivitiesActivity.TWO){
            return new CreateAcTwoFragment();
        }else{
            return new CreateAcThreeFragment();
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
