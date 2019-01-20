package com.yitsu.poly.view.splashUI.settingsUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yitsu.poly.controller.application.utils.CountUtils;
import com.yitsu.poly.controller.application.utils.MD5;
import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.model.type.info.UserInfo;
import com.yitsu.poly.view.splashUI.SplashActivity;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by butterfly on 2018/12/23.
 * 修改用户密码的Activity
 */
public class ChangePassWdActivity extends AppCompatActivity {

    private EditText editOldPassWd,editNewPassWd;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_wd);
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
        editOldPassWd = (EditText)findViewById(R.id.edit_old_passwd);
        editNewPassWd = (EditText)findViewById(R.id.edit_new_passwd);
        String userName = Poly.userPreference.getString(ConstantString.USER,null);
        Poly.infoPreference = getSharedPreferences(userName,MODE_APPEND);
        Poly.getInstance().addActivity(this);
    }

    /*
    修改密码
     */
    public void changePassWd(View view) throws NoSuchAlgorithmException {
        if (Poly.infoPreference.getString(ConstantString.ENCODE_PSW,null).equals(MD5.getMD5(editOldPassWd.getText().toString()))){
            if (!editNewPassWd.getText().toString().equals("")){
                Call<UserInfo> call = Poly.service.changePassWd(Poly.infoPreference.getInt(ConstantString.USER_ID,0),Poly.infoPreference.getString(ConstantString.NAME,null),
                        MD5.getMD5(editNewPassWd.getText().toString()));
                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        int code = response.body().getCode();
                        switch (code){
                            case 402:
                                Toast.makeText(ChangePassWdActivity.this,"数据库连接失败",Toast.LENGTH_LONG).show();break;
                            case 447:
                                Toast.makeText(ChangePassWdActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();break;
                            case 448:
                                Toast.makeText(ChangePassWdActivity.this,"数据库密码更新失败",Toast.LENGTH_LONG).show();break;
                            case 449:
                                Toast.makeText(ChangePassWdActivity.this,"密码更新成功",Toast.LENGTH_LONG).show();
                                CountUtils.exitUsername();
                                startActivity(new Intent(ChangePassWdActivity.this, SplashActivity.class));
                                Poly.getInstance().exit();
                                break;


                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {

                    }
                });
            }else {
                Toast.makeText(ChangePassWdActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
            }

        }
    }
}
