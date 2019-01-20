package com.yitsu.poly.view.splashUI.guideUI;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.yitsu.poly.R;
import com.yitsu.poly.controller.application.adpter.GuideFragmentPagerAdapter;

/**
 * Created by butterfly on 2018/11/22.
 * 引导界面的Activity，容纳三个滑动的Fragment页面
 */
public class GuideActivity extends AppCompatActivity {

    private ViewPager guideViewPager;
    private ImageView im_one;
    private ImageView im_two;
    private ImageView im_three;
    private RadioGroup group;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        guideViewPager = (ViewPager)findViewById(R.id.guide_viewpager);
        group = (RadioGroup) findViewById(R.id.radioGroup);
        im_one = (ImageView) findViewById(R.id.im_one);
        im_two = (ImageView) findViewById(R.id.im_two);
        im_three = (ImageView) findViewById(R.id.im_three);
        GuideFragmentPagerAdapter adapter = new GuideFragmentPagerAdapter(getSupportFragmentManager());
        guideViewPager.setAdapter(adapter);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.im_one){
                    guideViewPager.setCurrentItem(0);
                }else if (i == R.id.im_two){
                    guideViewPager.setCurrentItem(1);
                }else {
                    guideViewPager.setCurrentItem(2);
                }
            }
        });
        guideViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2){
                    switch (guideViewPager.getCurrentItem()){
                        case 0:im_one.setSelected(true);
                            im_two.setSelected(false);
                            im_three.setSelected(false);
                            break;
                        case 1:im_one.setSelected(false);
                            im_two.setSelected(true);
                            im_three.setSelected(false);
                            break;
                        case 2:im_one.setSelected(false);
                            im_two.setSelected(false);
                            im_three.setSelected(true);
                            break;
                    }
                }
            }
        });
        im_one.setSelected(true);
    }

}
