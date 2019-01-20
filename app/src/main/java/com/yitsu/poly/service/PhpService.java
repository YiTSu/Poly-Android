package com.yitsu.poly.service;

import com.yitsu.poly.model.type.info.ActivityInfo;
import com.yitsu.poly.model.type.info.UserInfo;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by butterfly on 2018/11/19.
 * 使用Retrofit框架进行通信的Service，实现客户端和服务端的通信
 */
public interface PhpService {
    @POST("register.php")
    Call<UserInfo> searchSignCode(@Query("username") String userName, @Query("passwd") String passWd);

    @POST("forgetpasswd.php")
    Call<UserInfo> forgetPassWd(@Query("username") String userName,@Query("passwd") String passWd);

    @POST("sign.php")
    Call<UserInfo> searchLogCode(@Query("username") String userName, @Query("passwd") String passWd,@Query("longitude") double longitude,@Query("lat") double lat);

    @POST("index.php")
    Call<ActivityInfo> searchActivity(@Query("userid") int userid,@Query("type") int type,@Query("page") int page,@Query("pagesize") int size,@Query("longitude") double longitude,
                                      @Query("lat") double lat,@Query("limit") int limit);

    @POST("createac.php")
    Call<UserInfo> createActivity(@Query("userid") int userid,@Query("title") String title,@Query("time") String time,@Query("people") int people,
                                @Query("addr") String addr,@Query("content") String content,@Query("type") int type,@Query("date") String date,@Query("unq") String dateAndTime,@Query("filename") String filename,
                                  @Query("longitude") double longitude,@Query("lat") double lat);

    @POST("join.php")
    Call<UserInfo> joinActivity(@Query("userid") int userid,@Query("acid") int acid);

    @POST("quit.php")
    Call<UserInfo> quitActivity(@Query("userid") int userid,@Query("acid") int acid);

    @POST("attention.php")
    Call<UserInfo> attendActivity(@Query("userid") int uesrid,@Query("acid") int acid);

    @POST("qattenting.php")
    Call<UserInfo> pattentActivity(@Query("userid") int uesrid,@Query("acid") int acid);//取消关注活动

    @POST("selectall.php")
    Call<ActivityInfo> selectJoinedAcs(@Query("userid") int userid);

    @POST("atteninfo.php")
    Call<ActivityInfo> selectAttendedAcs(@Query("userid") int uesrid);

    @POST("mycr.php")

    Call<ActivityInfo> selectCreatedAcs(@Query("userid") int userid);

    @POST("getinfo.php")
    Call<UserInfo> getUserInfo(@Query("userid") int userid);

    @POST("updateinfo.php")
    Call<UserInfo> setUserInfo(@Query("userid") int userid,@Query("addr") String addr,@Query("sex") String sex,@Query("name") String name,
                               @Query("realname") String realname,@Query("hobby") String hobby,@Query("phone") String phone,@Query("signature") String signature,@Query("imgname") String imageName);

    @POST("downloadheadimg.php")
    Call<UserInfo> getUserHeadImage(@Query("userid") int userid);

    @POST("updatepasswd.php")
    Call<UserInfo> changePassWd(@Query("userid") int userid,@Query("username") String userName,@Query("passwd") String passWd);

    @Multipart
    @POST("debug.php")
    Call<UserInfo> upload(@Part MultipartBody.Part body);

    @Multipart
    @POST("uploadheadimg.php")
    Call<UserInfo> uploadimg(@Query("userid") int userid, @Query("username") String userName, @Query("passwd") String passWd,@Part MultipartBody.Part body);

    @POST("test1.php")
    Call<UserInfo> test();
}
