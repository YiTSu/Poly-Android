package com.yitsu.poly.controller.application.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitsu.poly.R;
import com.yitsu.poly.controller.application.imageLoader.AsyncImageLoader;
import com.yitsu.poly.controller.application.imageLoader.FileCache;
import com.yitsu.poly.controller.application.imageLoader.MemoryCache;
import com.yitsu.poly.model.type.info.ActivityInfo;

import java.io.File;
import java.util.List;
import java.util.Random;

/**
 * Created by butterfly on 2018/11/10.
 * 用于所有的卡片Item的Adapter
 */
public class CardsAdapter extends ArrayAdapter<ActivityInfo.Act>{

    private static final String INETADDR = "http://188.131.157.253/PolyAPI/photo.php?acid=";
    private static final String POLYCACHE = "poly_cache";
    private static final String POLYIMG = "poly_img";
    private int resourceId;
    private List<ActivityInfo.Act> objects;
    private AsyncImageLoader imageLoader;
    private StringBuilder sb;
    private ActivityInfo.Act act;
    private Random random;
    private Context context;

    public CardsAdapter(Context context, int resource, List<ActivityInfo.Act> objects) {
        super(context, resource, objects);
        resourceId = resource;
        this.objects = objects;
        this.context = context;
        MemoryCache mcache=new MemoryCache();//内存缓存
        File sdCard = android.os.Environment.getExternalStorageDirectory();//获得SD卡
        File cacheDir = new File(sdCard, POLYCACHE);//缓存根目录
        FileCache fcache=new FileCache(context, cacheDir, POLYIMG);//文件缓存
        imageLoader = new AsyncImageLoader(context, mcache,fcache);
        random = new Random();

    }

    @Override
    public ActivityInfo.Act getItem(int position) {
        return objects.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        act = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)view.findViewById(R.id.main_image);
            viewHolder.tv2 = (TextView)view.findViewById(R.id.main_tv2);
            viewHolder.tv3 = (TextView)view.findViewById(R.id.main_tv3);
            viewHolder.tv4 = (TextView)view.findViewById(R.id.main_tv4);
            viewHolder.tv5 = (TextView)view.findViewById(R.id.main_tv5);
            viewHolder.tv6 = (TextView)view.findViewById(R.id.main_tv6);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        /*
        下载对应的活动图片
         */
        Bitmap bmp = imageLoader.loadBitmap(viewHolder.imageView,INETADDR+act.getAcid());
        setTextViewColor(viewHolder);
        viewHolder.imageView.setImageBitmap(bmp);

        if (act.getName().equals("")){
            viewHolder.tv2.setText(act.getUsername());
        }else{
            viewHolder.tv2.setText(act.getName());
        }
        viewHolder.tv3.setText(act.getTitle()+" ("+ act.getRealpeople() + "/" + act.getPeople()+")");
        viewHolder.tv4.setText("地点:"+act.getAddress());
        viewHolder.tv5.setText(act.getDate());
        viewHolder.tv6.setText(act.getTime());
        return view;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        TextView tv5;
        TextView tv6;
    }

    /*
    设置昵称的背景色
     */
    private void setTextViewColor(ViewHolder viewHolder){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            switch (random.nextInt(4)){
                case 0:
                    viewHolder.tv2.setBackground(context.getResources().getDrawable(R.drawable.rect_primary)); break;
                case 1:
                    viewHolder.tv2.setBackground(context.getResources().getDrawable(R.drawable.rect_blue)); break;
                case 2:
                    viewHolder.tv2.setBackground(context.getResources().getDrawable(R.drawable.rect_purple)); break;
                case 3:
                    viewHolder.tv2.setBackground(context.getResources().getDrawable(R.drawable.rect_pink)); break;

            }
        }
    }

}