package com.yitsu.poly.view.splashUI.createNoteUI;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yitsu.poly.R;
import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.model.type.ConstantString;

/**
 * Created by butterfly on 2018/12/7.
 * 发表帖子容纳两个Fragment的Activity
 */
public class CreateNoteActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private RadioGroup group;
    private ImageView im_one;
    private ImageView im_two;
    private Button bt_create_ac;

    public static final int ONE = 0;
    public static final int TWO = 1;
    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        group = (RadioGroup) findViewById(R.id.radioGroup);
        im_one = (ImageView) findViewById(R.id.im_one);
        im_two = (ImageView) findViewById(R.id.im_two);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        bt_create_ac = (Button)findViewById(R.id.bt_create_ac);
        userName = Poly.userPreference.getString(ConstantString.USER,null);
        Poly.infoPreference = getSharedPreferences(userName,MODE_APPEND);
        initViews();
        im_one.setSelected(true);
        bt_create_ac.setEnabled(false);
    }

    /*
    初始化发帖页面
     */
    private void initViews(){
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.im_one){
                    viewPager.setCurrentItem(ONE);
                }else if (i == R.id.im_two){
                    viewPager.setCurrentItem(TWO);
                }
            }
        });

        CreateNoteAdapter adapter = new CreateNoteAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2){
                    switch (viewPager.getCurrentItem()){
                        case ONE:im_one.setSelected(true);
                            im_two.setSelected(false);
                            bt_create_ac.setEnabled(false);
                            break;
                        case TWO:im_one.setSelected(false);
                            im_two.setSelected(true);
                            bt_create_ac.setEnabled(true);
                            break;
                    }
                }
            }
        });
    }

    /*
    点击关闭关闭此Activity
     */
    public void exitActivity(View view){
        finish();
    }

    public void createNote(View view){
        Toast.makeText(this,"功能正在bug修复中,先去体验我们的主要功能吧！",Toast.LENGTH_SHORT).show();
    }
}
