package com.yitsu.poly.view.splashUI.tribeUI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMTribeAtPageUI;
import com.yitsu.poly.R;

/**
 * Created by butterfly on 2018/12/31.
 * 显示群组@消息的界面
 */

public class AtMessageListUI extends IMTribeAtPageUI {
    public AtMessageListUI(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public View getCustomTitle(final Activity activity, final Context context, LayoutInflater inflater) {
        RelativeLayout customView = (RelativeLayout) inflater.inflate(R.layout.tribe_message_list_title,new RelativeLayout(context),false);
        TextView title = (TextView) customView.findViewById(R.id.title_txt);
        title.setText(context.getResources().getString(R.string.aliwx_at_message_title));
        title.setTextColor(Color.WHITE);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Toast.makeText(context, "click ", Toast.LENGTH_SHORT).show();

            }
        });
        TextView backButton = (TextView) customView.findViewById(R.id.left_button);
        backButton.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                activity.finish();
            }
        });
        return customView;
    }
}
