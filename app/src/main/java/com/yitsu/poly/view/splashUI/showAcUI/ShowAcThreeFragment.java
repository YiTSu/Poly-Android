package com.yitsu.poly.view.splashUI.showAcUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitsu.poly.R;

/**
 * Created by butterfly on 2018/12/23.
 * 展示活动信息的第三个Fragment
 */
public class ShowAcThreeFragment extends Fragment {

    private View rootView;
    public static TextView tvContent;
    public static Button btLove,btUnlove;
    public static ImageView imageHeart,imageHeartFull;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show_ac_three, container, false);
        tvContent = (TextView)rootView.findViewById(R.id.tv_content);
        btLove = (Button)rootView.findViewById(R.id.bt_love);
        btUnlove = (Button)rootView.findViewById(R.id.bt_unlove);
        imageHeart = (ImageView)rootView.findViewById(R.id.im_heart);
        imageHeartFull = (ImageView)rootView.findViewById(R.id.im_heart_full);
        tvContent.setText(ShowAcsActivity.act.getContent());
        if (ShowAcsActivity.isLoved){
            btLove.setVisibility(View.GONE);
            btUnlove.setVisibility(View.VISIBLE);
            imageHeart.setVisibility(View.GONE);
            imageHeartFull.setVisibility(View.VISIBLE);
        }
        return rootView;
    }
}
