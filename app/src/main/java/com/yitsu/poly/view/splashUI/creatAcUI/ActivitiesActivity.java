package com.yitsu.poly.view.splashUI.creatAcUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.yitsu.poly.controller.application.adpter.ActivityFragmentPagerAdapter;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.model.type.info.PersonCreateAc;
import com.yitsu.poly.model.type.info.UserInfo;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by butterfly on 2018/12/7.
 * 发起活动容纳三个Fragment的Activity
 */
public class ActivitiesActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private RadioGroup group;
    private ImageView im_one;
    private ImageView im_two;
    private ImageView im_three;
    private Button bt_create_ac;

    public static final int ONE = 0;
    public static final int TWO = 1;
    public static final int THREE = 2;
    private String userName;

    private String dateAndTime;
    private Uri imageUri;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        group = (RadioGroup) findViewById(R.id.radioGroup);
        im_one = (ImageView) findViewById(R.id.im_one);
        im_two = (ImageView) findViewById(R.id.im_two);
        im_three = (ImageView) findViewById(R.id.im_three);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        bt_create_ac = (Button)findViewById(R.id.bt_create_ac);
        userName = Poly.userPreference.getString(ConstantString.USER,null);
        Poly.infoPreference = getSharedPreferences(userName,MODE_APPEND);
        initViews();
        im_one.setSelected(true);
        bt_create_ac.setEnabled(false);

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

        ActivityFragmentPagerAdapter adapter = new ActivityFragmentPagerAdapter(getSupportFragmentManager());
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
    点击关闭按钮关闭此Activity
     */
    public void exitActivity(View view){
        finish();
    }


    /*
    发起活动
     */
    public void createAc(View view){  //将用户输入数据临时存储在内存中并创建活动
        PersonCreateAc.setTitle(CreateAcOneFragment.editTitle.getText().toString());
        System.out.println("title:"+PersonCreateAc.getTitle());
        PersonCreateAc.setDate(CreateAcTwoFragment.editDate.getText().toString());
        System.out.println("date:"+PersonCreateAc.getDate());
        PersonCreateAc.setTime(CreateAcTwoFragment.editStartTime.getText().toString()+"————"+CreateAcTwoFragment.editEndTime.getText().toString());
        System.out.println("time:"+PersonCreateAc.getTime());
        if (CreateAcTwoFragment.editLimitedNum.getText().toString().equals("")){
            PersonCreateAc.setPeople(0);
        }else {
            PersonCreateAc.setPeople(Integer.parseInt(CreateAcTwoFragment.editLimitedNum.getText().toString()));
        }
        System.out.println("people:"+PersonCreateAc.getPeople());
        PersonCreateAc.setAddr(CreateAcTwoFragment.editLocation.getText().toString());
        System.out.println("location:"+PersonCreateAc.getAddr());
        PersonCreateAc.setContent(CreateAcThreeFragment.editContent.getText().toString());
        System.out.println("content:"+PersonCreateAc.getContent());
        PersonCreateAc.setUserid(Poly.infoPreference.getInt(ConstantString.USER_ID,0));
        System.out.println("userid:"+PersonCreateAc.getUserid());

        Calendar c = Calendar.getInstance();
        dateAndTime = c.get(Calendar.YEAR)+String.format("%02d",c.get(Calendar.MONTH))+String.format("%02d",c.get(Calendar.DATE))+String.format(
                "%02d",c.get(Calendar.HOUR))+String.format("%02d",c.get(Calendar.MINUTE))+String.format("%02d",c.get(Calendar.SECOND));
        System.out.println("time:"+dateAndTime);
        if (imageUri == null){
            sendMessage();
        }else {
            upLoad();
        }

    }

    public void sendImage(View view){ //设置图片
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        imageUri = getTmpUri();
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, 1);
    }


    /*
    获取返回的结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK){
            Bitmap bitmap = null;
            switch (requestCode){
                case 1:
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    CreateAcThreeFragment.imageSquare.setImageBitmap(bitmap);
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

    private void upLoad(){  //图片上传

        String imageUriPath = imageUri.getPath();
        System.out.println("imageUriPath:"+imageUriPath);
        file = new File(imageUriPath);
        System.out.println("file:"+file);

        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("myfile", file.getName(), requestFile);
        System.out.println("file.getname:"+ file.getName());

        System.out.println("requestFile:"+requestFile);

        // 添加描述
        String descriptionString = "hello";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);

        // 执行请求
        Call<UserInfo> call = Poly.service.upload(body);

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                int code = response.body().getCode();
                switch (code){
                    case 442:
                        Toast.makeText(getApplicationContext(),"图片上传失败",Toast.LENGTH_SHORT).show();break;
                    case 441:
                        sendMessage();
                        break;

                }

            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {

            }
        });
    }

    private void sendMessage(){  //将数据发给服务端
        String fileName = null;
        if (file == null){
            fileName = "";
        }else {
            fileName = file.getName();
        }
        Call<UserInfo> call = Poly.service.createActivity(PersonCreateAc.getUserid(),PersonCreateAc.getTitle(),PersonCreateAc.getTime(),
                PersonCreateAc.getPeople(),PersonCreateAc.getAddr(),PersonCreateAc.getContent(),PersonCreateAc.getType(),PersonCreateAc.getDate(),PersonCreateAc.getUserid()+""+dateAndTime,fileName,
                Poly.location.getLongitude(),Poly.location.getLatitude());

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                int code = response.body().getCode();
                switch (code){
                    case 402:
                        Toast.makeText(getApplicationContext(),R.string.connect_error,Toast.LENGTH_SHORT).show();break;
                    case 411:
                        Toast.makeText(getApplicationContext(), R.string.title_notnull,Toast.LENGTH_SHORT).show(); break;
                    case 412:
                        Toast.makeText(getApplicationContext(), R.string.time_notnull,Toast.LENGTH_SHORT).show();break;
                    case 413:
                        Toast.makeText(getApplicationContext(), R.string.addr_notnull,Toast.LENGTH_SHORT).show();break;
                    case 414:
                        Toast.makeText(getApplicationContext(), R.string.content_notnull,Toast.LENGTH_SHORT).show();break;
                    case 415:
                        Toast.makeText(getApplicationContext(), R.string.people_notnull,Toast.LENGTH_SHORT).show();break;
                    case 416:
                        Toast.makeText(getApplicationContext(), R.string.create_ac_fail,Toast.LENGTH_SHORT).show();break;
                    case 417:
                        Toast.makeText(getApplicationContext(), R.string.create_ac_success,Toast.LENGTH_SHORT).show();
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


























