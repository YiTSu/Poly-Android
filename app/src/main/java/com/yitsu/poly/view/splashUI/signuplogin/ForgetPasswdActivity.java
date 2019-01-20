package com.yitsu.poly.view.splashUI.signuplogin;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yitsu.poly.controller.application.utils.MD5;
import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.model.type.info.UserInfo;

import java.security.NoSuchAlgorithmException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by butterfly on 2018/12/23.
 * 忘记密码Activity，用户通过验证手机来修改密码
 */
public class ForgetPasswdActivity extends AppCompatActivity {

    private EditText editUsername,editNewPasswd,editConfirmCode;
    private TextView tvSendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_passwd);
        initViews();
        registerHandler();
    }

    /*
    初始化
     */
    private void initViews(){
        tvSendCode = (TextView)findViewById(R.id.tv_send_code);
        editUsername = (EditText)findViewById(R.id.edit_username);
        editNewPasswd = (EditText)findViewById(R.id.edit_passwd);
        editConfirmCode = (EditText)findViewById(R.id.edit_confirm_code);
        Drawable drawableUser = getResources().getDrawable(R.drawable.icon_user);
        drawableUser.setBounds(0,0,50,50);
        editUsername.setCompoundDrawables(drawableUser,null,null,null);
        Drawable drawablePasswd = getResources().getDrawable(R.drawable.icon_passwd);
        drawablePasswd.setBounds(0,0,50,50);
        editNewPasswd.setCompoundDrawables(drawablePasswd,null,null,null);
        Drawable drawableConCode = getResources().getDrawable(R.drawable.icon_confirmcode);
        drawableConCode.setBounds(0,0,50,50);
        editConfirmCode.setCompoundDrawables(drawableConCode,null,null,null);
    }

    /*
    注册验证码检测的Handler
     */
    private void registerHandler(){
        EventHandler eh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE){

                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){//提交验证码成功
                        try {
                            forgetPasswd();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//获取验证码成功

                    }

                }else {
                    ((Throwable)data).printStackTrace();
                }


            }
        };
        /**
         * 注册短信回调
         */
        SMSSDK.registerEventHandler(eh);
    }

    /*
    修改密码
     */
    public void forgetPasswd() throws NoSuchAlgorithmException {
        String userName = editUsername.getText().toString();
        String newPasswd = editNewPasswd.getText().toString();
        if (!newPasswd.equals("")){
            Call<UserInfo> call = Poly.service.forgetPassWd(userName, MD5.getMD5(newPasswd));
            call.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    int code = response.body().getCode();
                    switch (code) {
                        case 449:
                            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgetPasswdActivity.this, LoginActivity.class));
                            finish();
                            break;
                        case 402:
                            Toast.makeText(getApplicationContext(), R.string.connect_error, Toast.LENGTH_SHORT).show();
                            break;
                        case 447:
                            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                            break;
                        case 448:
                            Toast.makeText(getApplicationContext(), "密码更新失败", Toast.LENGTH_SHORT).show();
                            break;

                    }
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {

                }
            });
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ForgetPasswdActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    /*
    修改密码时检测验证码
     */
    public void ensureChange(View view){
        SMSSDK.submitVerificationCode("86",editUsername.getText().toString(),editConfirmCode.getText().toString());
    }

    /*
    获取验证码
     */
    public void getConfirmCode(View view){
        SMSSDK.getVerificationCode("86",editUsername.getText().toString());
        tvSendCode.setVisibility(View.VISIBLE);
    }
}
