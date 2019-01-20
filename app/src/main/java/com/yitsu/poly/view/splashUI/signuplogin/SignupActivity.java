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
 * Created by butterfly on 2018/11/13.
 * 注册Activity，通过手机号验证进行注册
 */
public class SignupActivity extends AppCompatActivity {

    private EditText editUsername,editPasswd,editEnsurePasswd,editConfirmCode;
    private TextView tvSendCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        registerHandler();
    }

    /*
    初始化
     */
    private void initViews(){
        tvSendCode = (TextView)findViewById(R.id.tv_send_code);
        editUsername = (EditText)findViewById(R.id.edit_username);
        editPasswd = (EditText)findViewById(R.id.edit_passwd);
        editEnsurePasswd = (EditText)findViewById(R.id.edit_ensure_passwd);
        editConfirmCode = (EditText)findViewById(R.id.edit_confirm_code);
        Drawable drawableUser = getResources().getDrawable(R.drawable.icon_user);
        drawableUser.setBounds(0,0,50,50);
        editUsername.setCompoundDrawables(drawableUser,null,null,null);
        Drawable drawablePasswd = getResources().getDrawable(R.drawable.icon_passwd);
        drawablePasswd.setBounds(0,0,50,50);
        editPasswd.setCompoundDrawables(drawablePasswd,null,null,null);
        editEnsurePasswd.setCompoundDrawables(drawablePasswd,null,null,null);
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
                                signUp();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    /*
    注册
     */
    public void signUp() throws NoSuchAlgorithmException {
        String userName = editUsername.getText().toString();
        String passwd = editPasswd.getText().toString();
        String ensurePasswd = editEnsurePasswd.getText().toString();
        System.out.println(userName);
        if (passwd.equals(ensurePasswd)){
            Call<UserInfo> call = Poly.service.searchSignCode(userName, MD5.getMD5(passwd));
                call.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        int code = response.body().getCode();
                        switch (code){
                            case 400:
                                Toast.makeText(getApplicationContext(), R.string.sign_success,Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                finish();
                                break;
                            case 401:
                                Toast.makeText(getApplicationContext(), R.string.data_illegal,Toast.LENGTH_SHORT).show(); break;
                            case 402:
                                Toast.makeText(getApplicationContext(), R.string.connect_error,Toast.LENGTH_SHORT).show();break;
                            case 403:
                                Toast.makeText(getApplicationContext(), R.string.user_exit,Toast.LENGTH_SHORT).show(); break;
                            case 404:
                                Toast.makeText(getApplicationContext(), R.string.sign_fail,Toast.LENGTH_SHORT).show(); break;
                            case 408:
                                Toast.makeText(getApplicationContext(), R.string.urnmpawd_notnull,Toast.LENGTH_SHORT).show(); break;

                        }
                    }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {

                }
            });
        }
    }

    /*
    注册时检测验证码
     */
    public void signUp(View view){
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
