package com.yitsu.poly.view.splashUI.personUI;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.model.type.info.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by butterfly on 2018/12/23.
 * 更多个人信息Activity
 */
public class MoreInforActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private UserInfo.DataUserInfo userInfo;
    private TextView tvPhone,tvAddress,tvSignture;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_infor);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initViews();
    }

    /*
    初始化
     */
    private void initViews(){
        userName = Poly.userPreference.getString(ConstantString.USER,null);
        Poly.infoPreference = getSharedPreferences(userName,MODE_APPEND);
        getUserInfo();
        tvPhone = (TextView)findViewById(R.id.tv_phone);
        tvAddress = (TextView)findViewById(R.id.tv_address);
        tvSignture = (TextView)findViewById(R.id.tv_signature);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userInfo != null){
                    if (userInfo.getPhone() != null){
                        tvPhone.setText(userInfo.getPhone());
                    }
                    if (userInfo.getAddr() != null){
                        tvAddress.setText(userInfo.getAddr());
                    }
                    if (userInfo.getSignature() != null){
                        tvSignture.setText(userInfo.getSignature());
                    }
                }
            }
        },1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViews();
    }

    /*
    取得个人的数据
     */
    private void getUserInfo(){
        Call<UserInfo> call = Poly.service.getUserInfo(Poly.infoPreference.getInt(ConstantString.USER_ID,0));
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                int code = response.body().getCode();
                switch (code){
                    case 439:
                        Toast.makeText(getApplicationContext(), R.string.details_get_fail,Toast.LENGTH_SHORT).show(); break;
                    case 440:
                        userInfo = response.body().getData().get(0);
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }

    /*
    跳到相应的Activity
     */
    public void setPhone(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantString.USER_INFO,userInfo);
        intent.putExtras(bundle);
        intent.setClass(getApplicationContext(),SetPhoneActivity.class);
        startActivity(intent);
    }

    public void setAddress(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantString.USER_INFO,userInfo);
        intent.putExtras(bundle);
        intent.setClass(getApplicationContext(),SetAddressActivity.class);
        startActivity(intent);
    }

    public void setSignature(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantString.USER_INFO,userInfo);
        intent.putExtras(bundle);
        intent.setClass(getApplicationContext(),SetSignatureActivity.class);
        startActivity(intent);
    }
}
