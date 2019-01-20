package com.yitsu.poly.view.splashUI.settingsUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yitsu.poly.controller.application.utils.CountUtils;
import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.view.splashUI.SplashActivity;

public class SafetyActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);
        Poly.getInstance().addActivity(this);

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
    }

    /*
    跳入修改密码Activity
     */
    public void changePassWd(View view){
        startActivity(new Intent(SafetyActivity.this,ChangePassWdActivity.class));
    }

    /*
    退出当前账号
     */
    public void exitUsername(View view){
        String userName = Poly.userPreference.getString(ConstantString.USER,null);
        Poly.infoPreference = getSharedPreferences(userName,MODE_APPEND);
        CountUtils.exitUsername();
        startActivity(new Intent(SafetyActivity.this,SplashActivity.class));
        Poly.getInstance().exit();
    }

}
