package com.yitsu.poly.view.splashUI.mainUI;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.controller.application.utils.Utils;
import com.yitsu.poly.controller.application.imageLoader.ImageUtil;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.model.type.info.UserInfo;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by butterfly on 2018/11/22.
 * 个人界面Fragment
 */

public class PersonFragment extends Fragment{
    private static final String INETADDR = "http://120.25.120.123/Poly/downloadheadimg.php?userid=";
    public static ImageView imagePerson;
    private TextView tvName,tvSex,tvHobby;
    private UserInfo.DataUserInfo userInfo;
    private Bitmap bitmap;
    private MainActivity activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View rootView = inflater.inflate(R.layout.activity_person,container,false);
        imagePerson = (ImageView)rootView.findViewById(R.id.image_person);
        tvName = (TextView)rootView.findViewById(R.id.tv_name);
        tvSex = (TextView)rootView.findViewById(R.id.tv_sex);
        tvHobby = (TextView)rootView.findViewById(R.id.tv_hobby);
        getUserInfo();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    /*
    取得用户数据
     */
    private void getUserInfo(){
        Call<UserInfo> call = Poly.service.getUserInfo(Poly.infoPreference.getInt(ConstantString.USER_ID,0));
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                int code = response.body().getCode();
                switch (code){
                    case 439:
                        Toast.makeText(getActivity(), R.string.details_get_fail,Toast.LENGTH_SHORT).show(); break;
                    case 440:
                        userInfo = response.body().getData().get(0);
                        activity.setUserInfo(userInfo);
                        initPersonFragment();
                        break;
                }

            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });


    }

    /*
    初始化个人界面
     */
    private void initPersonFragment(){
        final String uriStr = Poly.infoPreference.getString(ConstantString.IMAGE_URI,null);
        if (uriStr != null){
            Uri uri = Uri.parse(uriStr);
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagePerson.setImageBitmap(Utils.toRoundBitmap(bitmap));
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userInfo != null){
                    if (userInfo.getName() != null){
                        tvName.setText(userInfo.getName());
                    }
                    if (userInfo.getSex() != null){
                        tvSex.setText(userInfo.getSex());
                    }
                    if (userInfo.getHobby() != null){
                        tvHobby.setText(userInfo.getHobby());
                    }
                    if (uriStr == null){
                        try {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        bitmap = ImageUtil.getImageBitmap(INETADDR+Poly.infoPreference.getInt(ConstantString.USER_ID,0));
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (bitmap != null){
                                                    imagePerson.setImageBitmap(Utils.toRoundBitmap(bitmap));
                                                }
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        },300);


    }

}
