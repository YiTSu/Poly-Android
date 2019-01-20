package com.yitsu.poly.model.type.info;

/**
 * Created by butterfly on 2018/11/20.
 * 用于临时在内存中存储用户发起活动时输入的活动信息
 */
public class PersonCreateAc {
    private static int userid;
    private static String title;
    private static String time;
    private static int people;
    private static String addr;
    private static String content;
    private static int type;
    private static String date;


    public static void setUserid(int userid) {
        PersonCreateAc.userid = userid;
    }

    public static int getUserid() {
        return userid;
    }

    public static String getTitle() {
        return title;
    }

    public static String getTime() {
        return time;
    }

    public static int getPeople() {
        return people;
    }

    public static String getAddr() {
        return addr;
    }

    public static String getContent() {
        return content;
    }

    public static int getType() {
        return type;
    }

    public static String getDate() {
        return date;
    }

    public static void setTitle(String title) {
        PersonCreateAc.title = title;
    }

    public static void setTime(String time) {
        PersonCreateAc.time = time;
    }

    public static void setPeople(int people) {
        PersonCreateAc.people = people;
    }

    public static void setAddr(String addr) {
        PersonCreateAc.addr = addr;
    }

    public static void setContent(String content) {
        PersonCreateAc.content = content;
    }

    public static void setType(int type) {
        PersonCreateAc.type = type;
    }

    public static void setDate(String date) {
        PersonCreateAc.date = date;
    }
}
