package com.yitsu.poly.model.type.info;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by butterfly on 2018/11/20.
 * 用户数据的类表示，借助于此类和Retrofit框架实现通信，并实现数据的结构化
 */
public class UserInfo {
    private int code;
    private String msg;
    private List<DataUserInfo> data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static class DataUserInfo implements Parcelable {
        private int userid;
        private String username;
        private String addr;
        private String sex;
        private String name;
        private String realname;
        private String hobby;
        private String phone;
        private String signature;
        private String password;

        protected DataUserInfo(Parcel in) {
            userid = in.readInt();
            addr = in.readString();
            sex = in.readString();
            name = in.readString();
            realname = in.readString();
            hobby = in.readString();
            phone = in.readString();
            signature = in.readString();
        }

        public static final Creator<DataUserInfo> CREATOR = new Creator<DataUserInfo>() {
            @Override
            public DataUserInfo createFromParcel(Parcel in) {
                return new DataUserInfo(in);
            }

            @Override
            public DataUserInfo[] newArray(int size) {
                return new DataUserInfo[size];
            }
        };

        public String getSignature() {
            return signature;
        }

        public String getAddr() {
            return addr;
        }

        public String getSex() {
            return sex;
        }

        public String getName() {
            return name;
        }

        public String getRealname() {
            return realname;
        }

        public String getHobby() {
            return hobby;
        }

        public String getPhone() {
            return phone;
        }

        public int getUserid() {
            return userid;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword(){
            return password;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(userid);
            parcel.writeString(addr);
            parcel.writeString(sex);
            parcel.writeString(name);
            parcel.writeString(realname);
            parcel.writeString(hobby);
            parcel.writeString(phone);
            parcel.writeString(signature);
        }
    }

    public List<DataUserInfo> getData() {
        return data;
    }
}
