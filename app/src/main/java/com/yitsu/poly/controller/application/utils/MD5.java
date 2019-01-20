package com.yitsu.poly.controller.application.utils;

/**
 * Created by butterfly on 2018/11/29.
 * MD5加密操作类
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 对外提供getMD5(String)方法
 * @author randyjia
 *
 */
public class MD5 {

    public static String getMD5(String val) throws NoSuchAlgorithmException{
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(val.getBytes());
        byte[] m = md5.digest();//加密
        return getString(m);
    }
    private static String getString(byte[] b){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < b.length; i ++){
            sb.append(b[i]);
        }
        return sb.toString();
    }
}
