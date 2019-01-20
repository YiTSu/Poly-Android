package com.yitsu.poly.view.splashUI.showAcUI;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.controller.application.adpter.ActivityFragmentPagerShowAdapter;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.model.type.info.ActivityInfo;
import com.yitsu.poly.model.type.info.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by butterfly on 2018/12/23.
 * 显示具体活动信息的Activity，容纳三个展示活动信息的Fragment
 */
public class ShowAcsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private RadioGroup group;
    private ImageView im_one;
    private ImageView im_two;
    private ImageView im_three;
    private Button bt_create_ac;
    public static ActivityInfo.Act act;
    private int userId;
    public static boolean isLoved;
    String userName;

    public static final int ONE = 0;
    public static final int TWO = 1;
    public static final int THREE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_acs);


        group = (RadioGroup) findViewById(R.id.radioGroup);
        im_one = (ImageView) findViewById(R.id.im_one);
        im_two = (ImageView) findViewById(R.id.im_two);
        im_three = (ImageView) findViewById(R.id.im_three);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        bt_create_ac = (Button)findViewById(R.id.bt_create_ac);

        userName = Poly.userPreference.getString(ConstantString.USER,null);
        Poly.infoPreference = getSharedPreferences(userName,MODE_APPEND);
        userId = Poly.infoPreference.getInt(ConstantString.USER_ID,0);

        Intent intent = getIntent();
        act = intent.getParcelableExtra(ConstantString.ACT);
        String isShowOrExit = intent.getStringExtra(ConstantString.IS_SHOW_OR_EXIT);
        if (isShowOrExit.equals(ConstantString.EXIT)){
            bt_create_ac.setText("退出活动");
            bt_create_ac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    quitActivity();
                }
            });
        }
        initViews();
        im_one.setSelected(true);
        bt_create_ac.setEnabled(false);
        isLoved  = intent.getBooleanExtra(ConstantString.IS_LOVED,false);

    }

    /*
    初始化
     */
    private void initViews(){
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.im_one){
                    viewPager.setCurrentItem(ONE);
                }else if (i == R.id.im_two){
                    viewPager.setCurrentItem(TWO);
                }else {
                    viewPager.setCurrentItem(THREE);
                }
            }
        });

        ActivityFragmentPagerShowAdapter adapter = new ActivityFragmentPagerShowAdapter(getSupportFragmentManager());
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
                            im_three.setSelected(false);
                            bt_create_ac.setEnabled(false);
                            break;
                        case TWO:im_one.setSelected(false);
                            im_two.setSelected(true);
                            im_three.setSelected(false);
                            bt_create_ac.setEnabled(false);
                            break;
                        case THREE:im_one.setSelected(false);
                            im_two.setSelected(false);
                            im_three.setSelected(true);
                            bt_create_ac.setEnabled(true);
                            break;
                    }
                }
            }
        });

    }

    /*
    点击关闭按钮，关闭此Activity
     */
    public void exitActivity(View view){
        finish();
    }

    /*
    关心活动
     */
    public void loveActivity(View view){
        Call<UserInfo> call = Poly.service.attendActivity(userId,act.getAcid());
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                int code = response.body().getCode();
                switch (code){
                    case 402:
                        Toast.makeText(getApplicationContext(),R.string.connect_error,Toast.LENGTH_SHORT).show();break;
                    case 425:
                        Toast.makeText(getApplicationContext(), R.string.attend_ac_fail,Toast.LENGTH_SHORT).show();break;
                    case 426:
                        Toast.makeText(getApplicationContext(), R.string.attend_ac_success,Toast.LENGTH_SHORT).show();
                        ShowAcThreeFragment.imageHeart.setVisibility(View.GONE);
                        ShowAcThreeFragment.imageHeartFull.setVisibility(View.VISIBLE);
                        ShowAcThreeFragment.btLove.setVisibility(View.GONE);
                        ShowAcThreeFragment.btUnlove.setVisibility(View.VISIBLE);
                        break;

                    case 434:
                        Toast.makeText(getApplicationContext(), R.string.have_loved_ac,Toast.LENGTH_SHORT).show();break;
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }

    /*
    取消关注活动
     */
    public void unLoveActivity(View view){
        Call<UserInfo> call = Poly.service.pattentActivity(userId,act.getAcid());
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                int code = response.body().getCode();
                switch (code) {
                    case 402:
                        Toast.makeText(getApplicationContext(), R.string.connect_error, Toast.LENGTH_SHORT).show();
                        break;
                    case 427:
                        Toast.makeText(getApplicationContext(), R.string.qat_ac_fail,Toast.LENGTH_SHORT).show();break;
                    case 428:
                        Toast.makeText(getApplicationContext(), R.string.qat_ac_success,Toast.LENGTH_SHORT).show();
                        ShowAcThreeFragment.imageHeart.setVisibility(View.VISIBLE);
                        ShowAcThreeFragment.imageHeartFull.setVisibility(View.GONE);
                        ShowAcThreeFragment.btLove.setVisibility(View.VISIBLE);
                        ShowAcThreeFragment.btUnlove.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }

    /*
    加入活动
     */
    public void joinActivity(View view){
        Call<UserInfo> call = Poly.service.joinActivity(userId,act.getAcid());

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                int code = response.body().getCode();
                switch (code){
                    case 402:
                        Toast.makeText(getApplicationContext(),R.string.connect_error,Toast.LENGTH_SHORT).show(); break;
                    case 420:
                        Toast.makeText(getApplicationContext(), R.string.people_max,Toast.LENGTH_SHORT).show();break;
                    case 421:
                        Toast.makeText(getApplicationContext(), R.string.join_ac_fail,Toast.LENGTH_SHORT).show();break;
                    case 422:
                        Toast.makeText(getApplicationContext(), R.string.join_ac_success,Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 433:
                        Toast.makeText(getApplicationContext(), R.string.can_not_join_ac,Toast.LENGTH_SHORT).show();break;
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });

    }

    /*
    退出活动
     */
    public void quitActivity(){
        Call<UserInfo> call = Poly.service.quitActivity(userId,act.getAcid());
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                int code = response.body().getCode();
                switch (code){
                    case 402:
                        Toast.makeText(getApplicationContext(),R.string.connect_error,Toast.LENGTH_SHORT).show();break;
                    case 423:
                        Toast.makeText(getApplicationContext(), R.string.quit_ac_fail,Toast.LENGTH_SHORT).show(); break;
                    case 424:
                        Toast.makeText(getApplicationContext(), R.string.quit_ac_success,Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case 443:
                        Toast.makeText(getApplicationContext(), "发起活动者不能退出此活动", Toast.LENGTH_SHORT).show(); break;
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }


}

