package com.yitsu.poly.view.splashUI.createNoteUI;

/**
 * Created by butterfly on 2018/11/23.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yitsu.poly.view.splashUI.creatAcUI.ActivitiesActivity;
import com.yitsu.poly.view.splashUI.creatAcUI.CreateAcOneFragment;


/**
 * Created by butterfly on 2018/11/12.
 * 用于发表帖子时的两个个Fragment滑动页面
 */
public class CreateNoteAdapter extends FragmentPagerAdapter {

    private static final int COUNT = 2; //Fragment数量
    public CreateNoteAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == ActivitiesActivity.ONE){
            return new CreateAcOneFragment();
        }else{
            return new CreateNoteTwoFragment();
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

