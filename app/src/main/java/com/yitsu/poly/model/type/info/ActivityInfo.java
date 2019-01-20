package com.yitsu.poly.model.type.info;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by butterfly on 2018/11/20.
 * 活动数据的类表示，借助于此类和Retrofit框架实现通信，并实现数据的结构化
 */
public class ActivityInfo {
    private int code;

    private List<Act> data;

    public int getCode() {
        return code;
    }


    public static class Act implements Parcelable {
            private int acid;
            private String title;
            private String address;
            private int type;
            private int people;
            private String content;
            private String time;
            private String username;
            private String name;
            private int realpeople;
            private String date;
            private String url;
            private String tribe_id;
            private double longitude;
            private double lat;

        protected Act(Parcel in) {
            acid = in.readInt();
            title = in.readString();
            address = in.readString();
            type = in.readInt();
            people = in.readInt();
            content = in.readString();
            time = in.readString();
            username = in.readString();
            name = in.readString();
            realpeople = in.readInt();
            date = in.readString();
        }

        public static final Creator<Act> CREATOR = new Creator<Act>() {
            @Override
            public Act createFromParcel(Parcel in) {
                return new Act(in);
            }

            @Override
            public Act[] newArray(int size) {
                return new Act[size];
            }
        };

        public String getTitle() {
                return title;
            }

        public String getAddress() {
                return address;
            }

        public int getType() {
                return type;
            }

        public int getPeople() {
                return people;
            }

        public String getContent() {
                return content;
            }

        public String getTime() {
                return time;
            }

        public String getUsername() {
                return username;
            }

        public String getName() {
                return name;
            }

        public int getRealpeople() {
                return realpeople;
            }

        public String getDate() {
                return date;
            }

        public int getAcid() {
                return acid;
            }

        public String getTribe_id(){
                return tribe_id;
            }

        public double getLongitude(){
                return longitude;
            }

        public double getLat(){
                return lat;
            }

        public String getUrl() {
            return url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(acid);
            parcel.writeString(title);
            parcel.writeString(address);
            parcel.writeInt(type);
            parcel.writeInt(people);
            parcel.writeString(content);
            parcel.writeString(time);
            parcel.writeString(username);
            parcel.writeString(name);
            parcel.writeInt(realpeople);
            parcel.writeString(date);
        }
    }

        public List<Act> getActs() {
            return data;
        }
}
