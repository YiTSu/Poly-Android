package com.yitsu.poly.controller.application.adpter;

import android.content.Context;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitsu.poly.controller.application.imageLoader.AsyncImageLoader;
import com.yitsu.poly.controller.application.imageLoader.FileCache;
import com.yitsu.poly.controller.application.imageLoader.MemoryCache;
import com.yitsu.poly.model.type.info.ActivityInfo;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Created by butterfly on 2018/11/23.
 * 用于帖子Item的Adapter，目前数据未完全完成，故构造了默认条目
 */

public class NoteCardsAdapter extends ArrayAdapter<Integer> {
    private static final String POLYCACHE = "poly_cache";
    private static final String POLYIMG = "poly_img";
    private int resourceId;
    private List<Integer> objects;
    private AsyncImageLoader imageLoader;
    private Geocoder geocoder;
    private StringBuilder sb;
    private ActivityInfo.Act act;
    private Random random;
    private Context context;

    public NoteCardsAdapter(Context context, int resource, List<Integer> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.objects = objects;
        this.context = context;
        MemoryCache mcache=new MemoryCache();//内存缓存
        File sdCard = android.os.Environment.getExternalStorageDirectory();//获得SD卡
        File cacheDir = new File(sdCard, POLYCACHE );//缓存根目录
        FileCache fcache=new FileCache(context, cacheDir, POLYIMG);//文件缓存
        imageLoader = new AsyncImageLoader(context, mcache,fcache);
        random = new Random();
        //geocoder = new Geocoder(context);

    }

    @Override
    public Integer getItem(int position) {
        return objects.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        act = getItem(position);
//        //new UpdateLocationTask().execute();
        View view;
//        NoteCardsAdapter.ViewHolder viewHolder;
//        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
//            viewHolder = new NoteCardsAdapter.ViewHolder();
//            viewHolder.imageView = (ImageView)view.findViewById(R.id.main_image);
//            //viewHolder.tv1 = (TextView)view.findViewById(R.id.main_tv1);
//            viewHolder.tv2 = (TextView)view.findViewById(R.id.main_tv2);
//            viewHolder.tv3 = (TextView)view.findViewById(R.id.main_tv3);
//            viewHolder.tv4 = (TextView)view.findViewById(R.id.main_tv4);
//            viewHolder.tv5 = (TextView)view.findViewById(R.id.main_tv5);
//            viewHolder.tv6 = (TextView)view.findViewById(R.id.main_tv6);
//            view.setTag(viewHolder);
//        }else{
//            view = convertView;
//            viewHolder = (NoteCardsAdapter.ViewHolder)view.getTag();
//        }
//
//        Bitmap bmp = imageLoader.loadBitmap(viewHolder.imageView,"http://120.25.120.123/Poly/photo.php?acid="+act.getAcid());
//        setTextViewColor(viewHolder);
//        viewHolder.imageView.setImageBitmap(bmp);
//
//        //viewHolder.tv1.setText("");
//        if (act.getName().equals("")){
//            viewHolder.tv2.setText(act.getUsername());
//        }else{
//            viewHolder.tv2.setText(act.getName());
//        }
//        viewHolder.tv3.setText(act.getTitle()+" ("+ act.getRealpeople() + "/" + act.getPeople()+")");
//        viewHolder.tv4.setText("地点:"+act.getAddress());
//        viewHolder.tv5.setText(act.getDate());
//        viewHolder.tv6.setText(act.getTime());
//        return view;
        return view;
    }

    private class ViewHolder{
        ImageView imageView;
        //TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
    }


}
