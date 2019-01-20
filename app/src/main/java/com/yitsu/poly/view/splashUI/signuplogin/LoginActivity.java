package com.yitsu.poly.view.splashUI.signuplogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.view.splashUI.guideUI.GuideActivity;
import com.yitsu.poly.controller.application.utils.MD5;
import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.model.type.info.UserInfo;
import com.yitsu.poly.view.splashUI.mainUI.MainActivity;

import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by butterfly on 2018/11/13.
 * 登录Activity，通过手机号和密码进行登录
 */
public class LoginActivity extends AppCompatActivity {

    EditText editUsername,editPasswd;
    private String userName;
    private TextView tvSignup,tvForgetPassWd;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences = getSharedPreferences(ConstantString.PHONE,MODE_PRIVATE);
        if (preferences.getBoolean(ConstantString.IS_FIRST_START,true)){
            editor = preferences.edit();
            editor.putBoolean(ConstantString.IS_FIRST_START,false);
            editor.commit();
            startActivity(new Intent(LoginActivity.this, GuideActivity.class));
            finish();
        }
        initViews();
    }

    /*
    初始化
     */
    private void initViews(){
        editUsername = (EditText)findViewById(R.id.edit_username);
        editPasswd = (EditText)findViewById(R.id.edit_passwd);
        Drawable drawableUser = getResources().getDrawable(R.drawable.icon_user);
        drawableUser.setBounds(0,0,50,50);
        editUsername.setCompoundDrawables(drawableUser,null,null,null);
        Drawable drawablePasswd = getResources().getDrawable(R.drawable.icon_passwd);
        drawablePasswd.setBounds(0,0,50,50);
        editPasswd.setCompoundDrawables(drawablePasswd,null,null,null);
        tvSignup = (TextView)findViewById(R.id.tv_signup);
        tvForgetPassWd = (TextView)findViewById(R.id.tv_forget_passwd);
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
        tvForgetPassWd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgetPasswdActivity.class));
            }
        });
    }

    /*
    登录
     */
    public void login(View view) throws NoSuchAlgorithmException {
        userName = editUsername.getText().toString();
        SharedPreferences.Editor editor = Poly.userPreference.edit();
        editor.putString(ConstantString.USER,userName);
        editor.commit();
        final String passwd = editPasswd.getText().toString();
        Poly.infoPreference = getSharedPreferences(userName,MODE_APPEND);
        Call<UserInfo> call = Poly.service.searchLogCode(userName, MD5.getMD5(passwd),Poly.location.getLongitude(),Poly.location.getLatitude());
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                int code = response.body().getCode();
                switch (code){
                    case 401:
                        Toast.makeText(getApplicationContext(), R.string.data_illegal,Toast.LENGTH_SHORT).show(); break;
                    case 402:
                        Toast.makeText(getApplicationContext(), R.string.connect_error,Toast.LENGTH_SHORT).show();break;
                    case 405:
                        Toast.makeText(getApplicationContext(), R.string.user_notexit,Toast.LENGTH_SHORT).show(); break;
                    case 406:
                        Toast.makeText(getApplicationContext(), R.string.err_urnmpawd,Toast.LENGTH_SHORT).show(); break;
                    case 407:
                        int userId = response.body().getData().get(0).getUserid();
                        Toast.makeText(getApplicationContext(), R.string.log_success,Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = Poly.infoPreference.edit();
                        editor.putString(ConstantString.NAME,userName);
                        editor.putInt(ConstantString.USER_ID,userId);
                        editor.putBoolean(ConstantString.IS_LOGED,true);
                        try {
                            editor.putString(ConstantString.ENCODE_PSW,MD5.getMD5(passwd));
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        editor.commit();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        break;
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
