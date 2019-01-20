package com.yitsu.poly.view.splashUI.mainUI;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.yitsu.poly.controller.application.adpter.NoScrollViewPager;
import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.controller.application.utils.Utils;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.view.splashUI.controlAcsUI.SelectAttendedAcsActivity;
import com.yitsu.poly.view.splashUI.controlAcsUI.SelectCreatedAcsActivity;
import com.yitsu.poly.view.splashUI.controlAcsUI.SelectJoinedAcsActivity;
import com.yitsu.poly.model.type.info.UserInfo;
import com.yitsu.poly.view.splashUI.personUI.MoreInforActivity;
import com.yitsu.poly.view.splashUI.personUI.SetHobbyActivity;
import com.yitsu.poly.view.splashUI.personUI.SetNameActivity;
import com.yitsu.poly.view.splashUI.personUI.SetSexActivity;
import com.yitsu.poly.view.splashUI.settingsUI.AcsRangeActivity;
import com.yitsu.poly.view.splashUI.settingsUI.ChatActivity;
import com.yitsu.poly.view.splashUI.settingsUI.NotificationsActivity;
import com.yitsu.poly.view.splashUI.settingsUI.SafetyActivity;
import com.yitsu.poly.view.splashUI.settingsUI.UnDisturbActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yitsu.poly.view.splashUI.mainUI.PersonFragment.imagePerson;

/**
 * Created by butterfly on 2018/11/8.
 * 主页的Activity，用于进行一些基本操作并容纳五个Fragment页面进行显示
 */
public class MainActivity extends AppCompatActivity{

    private TabLayout mTabLayout;
    private NoScrollViewPager mViewPager;
    private String userName;
    private String encodePasswd;
    private Uri imageUri;
    private File file;
    private UserInfo.DataUserInfo userInfo;
    private Bitmap bitmap;

    //即时通讯的实例mIMKit
    public static YWIMKit mIMKit;

    private final String[] TAB_TITLES = {"管理","群组","圈子","个人","设置"};
    private final int[] TAB_IMGS = {R.drawable.tab_control_selecter,R.drawable.tab_tribe_selecter,R.drawable.tab_circle_selecter,
    R.drawable.tab_person_selecter,R.drawable.tab_setting_selecter};
    private Fragment[] TAB_FRAGMENTS;
    private int COUNT = TAB_TITLES.length;
    private MainFragment mainFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        userName = Poly.userPreference.getString(ConstantString.USER,null);
        Poly.infoPreference = getSharedPreferences(userName,MODE_APPEND);
        encodePasswd = Poly.infoPreference.getString(ConstantString.ENCODE_PSW,null);
        OpenIMLogin();
    }

    /*
    即时通讯功能的初始化
    */
    private void OpenIMLogin() {
        if (mIMKit == null) {
            mIMKit = YWAPI.getIMKitInstance(userName, Poly.APP_KEY);
            IYWLoginService loginService = mIMKit.getLoginService();
            YWLoginParam loginParam = YWLoginParam.createLoginParam(userName, encodePasswd);
            loginService.login(loginParam, new IWxCallback() {

                @Override
                public void onSuccess(Object... arg0) {
                    Toast.makeText(getApplicationContext(), "即时通讯功能初始化成功", Toast.LENGTH_LONG).show();
                    mainFragment = new MainFragment();
                    TAB_FRAGMENTS = new Fragment[]{new ControlFragment(),new TribeFragment(),mainFragment,new PersonFragment(),new SettingFragment()};
                    initFragments();
                }

                @Override
                public void onProgress(int arg0) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onError(int errCode, String description) {
                    Toast.makeText(getApplicationContext(),"即时通讯功能初始化失败",Toast.LENGTH_LONG).show();
                    //如果登录失败，errCode为错误码,description是错误的具体描述信息
                }
            });
        }else {
            mainFragment = new MainFragment();
            TAB_FRAGMENTS = new Fragment[]{new ControlFragment(),new TribeFragment(),mainFragment,new PersonFragment(),new SettingFragment()};
            initFragments();
        }

    }
    /*
    初始化五个Fragment
     */
    private void initFragments(){
        mViewPager = (NoScrollViewPager) findViewById(R.id.noScrollViewPager);
        mTabLayout = (TabLayout)findViewById(R.id.tabLayout);
        setTabs(mTabLayout,getLayoutInflater(),TAB_TITLES,TAB_IMGS);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
    设置底部的五个Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater,String[] tabTitles,int[] tabImgs){
        for (int i = 0; i < tabTitles.length;i++){
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.tab_custom,null);
            tab.setCustomView(view);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_tab);
            tvTitle.setText(tabTitles[i]);
            ImageView tvImg = (ImageView)view.findViewById(R.id.img_tab);
            tvImg.setImageResource(tabImgs[i]);
            tabLayout.addTab(tab);
        }
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TAB_FRAGMENTS[position];
        }

        @Override
        public int getCount() {
            return COUNT;
        }
    }

    /*
    设置头像
     */
    public void sendPersonImage(View view){
        imageUri = getTmpUri();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, 1);
    }

    /*
    获取到返回的结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            Bitmap bitmap = null;
            switch (requestCode){
                case 1:
                    SharedPreferences.Editor editor = Poly.infoPreference.edit();
                    editor.putString(ConstantString.IMAGE_URI, String.valueOf(imageUri));
                    editor.commit();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                        upLoad();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imagePerson.setImageBitmap(Utils.toRoundBitmap(bitmap));
                    break;
            }
        }

    }

    private Uri getTmpUri() {
        String IMAGE_FILE_DIR = Environment.getExternalStorageDirectory() + "/" + "poly";
        File dir = new File(IMAGE_FILE_DIR);
        File file = new File(IMAGE_FILE_DIR, Long.toString(System.currentTimeMillis()));
        //非常重要！！！如果文件夹不存在必须先手动创建
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return Uri.fromFile(file);
    }

    /*
    上传头像
     */
    private void upLoad(){

        String imageUriPath = imageUri.getPath();
        file = new File(imageUriPath);
        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("myfile", file.getName(), requestFile);

        // 添加描述
        //        String descriptionString = "";
        //        RequestBody description =
        //                RequestBody.create(
        //                        MediaType.parse("multipart/form-data"), descriptionString);

        // 执行请求
        Call<UserInfo> call = Poly.service.uploadimg(Poly.infoPreference.getInt(ConstantString.USER_ID,0),Poly.infoPreference.getString(ConstantString.NAME,null),Poly.infoPreference.getString(ConstantString.ENCODE_PSW,null),body);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                Call<UserInfo> call1 = Poly.service.setUserInfo(Poly.infoPreference.getInt(ConstantString.USER_ID,0),null,null,null,null,null,null,null,file.getName());
                call1.enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {

                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {

                    }
                });
                System.out.println("头像上传成功");
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                System.out.println("头像上传失败");
            }
        });
    }

    void setUserInfo(UserInfo.DataUserInfo userInfo){
        this.userInfo = userInfo;
    }

    /*
    一系列跳转到其余Activity的方法
     */
    public void moreInformation(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantString.USER_INFO,userInfo);
        intent.putExtras(bundle);
        intent.setClass(this,MoreInforActivity.class);
        startActivity(intent);
    }

    public void setName(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantString.USER_INFO,userInfo);
        intent.putExtras(bundle);
        intent.setClass(this,SetNameActivity.class);
        startActivity(intent);
    }

    public void setSex(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantString.USER_INFO,userInfo);
        intent.putExtras(bundle);
        intent.setClass(this,SetSexActivity.class);
        startActivity(intent);
    }

    public void setHobby(View view){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantString.USER_INFO,userInfo);
        intent.putExtras(bundle);
        intent.setClass(this,SetHobbyActivity.class);
        startActivity(intent);
    }

    public void selectJoinedAcs(View view){
        startActivity(new Intent(this,SelectJoinedAcsActivity.class));
    }

    public void selectAttendedAcs(View view){
        startActivity(new Intent(this,SelectAttendedAcsActivity.class));
    }

    public void selectCreatedAcs(View view){
        startActivity(new Intent(this,SelectCreatedAcsActivity.class));
    }

    public void Notifications(View view){
        startActivity(new Intent(this,NotificationsActivity.class));
    }
    public void UnDisturb(View view){
        startActivity(new Intent(this,UnDisturbActivity.class));
    }

    public void Chat(View view){
        startActivity(new Intent(this,ChatActivity.class));
    }

    public void acsRange(View view){
        startActivity(new Intent(this,AcsRangeActivity.class));
    }

    public void Safety(View view){
        startActivity(new Intent(this,SafetyActivity.class));
    }


}
