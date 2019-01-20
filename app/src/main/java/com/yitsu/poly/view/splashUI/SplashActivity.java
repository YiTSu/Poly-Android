package com.yitsu.poly.view.splashUI;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.view.splashUI.mainUI.MainActivity;
import com.yitsu.poly.view.splashUI.signuplogin.LoginActivity;
/**
 * Created by butterfly on 2018/11/13.
 * 加载页，用于加载一些必要操作
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getLocation();
    }

    /*
    获取地理位置信息，检测GPS是否打开，否则打开GPS，之后检测是否已登录并执行正确操作
     */
    private void getLocation() {
        /**
         * 获取位置信息并进行实时更新
         */
        Poly.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (Poly.locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            getLocationWork();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    String userName = Poly.userPreference.getString(ConstantString.USER,null);
                    if (userName != null){
                        SharedPreferences preferences1 = getSharedPreferences(userName,MODE_APPEND);
                        if (preferences1.getBoolean(ConstantString.IS_LOGED,false) ){
                            startActivity(new Intent(SplashActivity.this,MainActivity.class));
                            finish();
                        }
                    }else {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        finish();
                    }
                }
            },1000);
            return;
        } else {
            Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
        }

    }
    /*
    获取地理位置信息的操作
     */
    private void getLocationWork(){
        /**
         * 获取当前位置信息及位置信息变化时进行更改
         */
        Poly.location = Poly.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Poly.location = Poly.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Poly.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 8, new LocationListener() {
            @Override
            public void onLocationChanged(Location loc) {
                Poly.location = loc;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                /**
                 * 当GPS可用时，更新位置
                 */
                Poly.location = Poly.locationManager.getLastKnownLocation(s);
            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });

        Poly.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 8, new LocationListener() {
            @Override
            public void onLocationChanged(Location loc) {
                Poly.location = loc;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
                /**
                 * 当网络可用时，更新位置
                 */
                Poly.location = Poly.locationManager.getLastKnownLocation(s);
            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });

    }
}
