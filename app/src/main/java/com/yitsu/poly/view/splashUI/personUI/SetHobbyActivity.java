package com.yitsu.poly.view.splashUI.personUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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
 * 设置用户爱好的Activity
 */
public class SetHobbyActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private UserInfo.DataUserInfo userInfo;
    private EditText editSetHobby;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_hobby);

        editSetHobby = (EditText)findViewById(R.id.edit_set_hobby);
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

        Intent intent = getIntent();
        userInfo = intent.getParcelableExtra(ConstantString.USER_INFO);
        editSetHobby.setText(userInfo.getHobby());
    }

    /*
    设置爱好
     */
    public void setHobbySure(View view){
        userName = Poly.userPreference.getString(ConstantString.USER,null);
        Poly.infoPreference = getSharedPreferences(userName,MODE_APPEND);
        String newHobby = editSetHobby.getText().toString();
        Call<UserInfo> call = Poly.service.setUserInfo(Poly.infoPreference.getInt(ConstantString.USER_ID,0),null,null,null,null,newHobby,null,null,null);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                int code = response.body().getCode();
                switch (code){
                    case 437:
                        Toast.makeText(getApplicationContext(), R.string.update_details_fail,Toast.LENGTH_SHORT).show();break;
                    case 438:
                        Toast.makeText(getApplicationContext(), R.string.update_details_success,Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }
}
