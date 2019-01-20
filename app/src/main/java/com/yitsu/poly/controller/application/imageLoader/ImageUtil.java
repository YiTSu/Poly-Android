package com.yitsu.poly.controller.application.imageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by butterfly on 2018/11/28.
 * 加载图片的工具类
 */
public class ImageUtil {
    /**
     * 从网络获取图片，并缓存在指定的文件中
     * @param url 图片url
     * @param file 缓存文件
     * @return
     */
    public static Bitmap loadBitmapFromWeb(String url, File file) {
        HttpURLConnection conn = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            is = conn.getInputStream();
            os = new FileOutputStream(file);
            copyStream(is, os);//将图片缓存到磁盘中
            bitmap = decodeFile(file);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if(os != null) os.close();
                if(is != null) is.close();
                if(conn != null) conn.disconnect();
            } catch (IOException e) {    }
        }
    }

    public static Bitmap decodeFile(File f) {
        try {
            return BitmapFactory.decodeStream(new FileInputStream(f), null, null);
        } catch (Exception e) { }
        return null;
    }
    private  static void copyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (;;) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Bitmap getImageBitmap(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            System.out.println("getImageBitmap！！");
            InputStream inStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inStream);
            return bitmap;
        }
        return null;
    }

}

