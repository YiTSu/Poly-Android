package com.yitsu.poly.view.splashUI.tribeUI;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMSelectTribeAtMemeberPageUI;
import com.yitsu.poly.R;

/**
 * Created by butterfly on 2018/12/31.
 * @ 之后选择群成员界面
 */

public class SelectTribeAtMemberUI extends IMSelectTribeAtMemeberPageUI {
    public SelectTribeAtMemberUI(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public View getCustomTitle(final Activity activity, Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.tribe_select_member_title,new RelativeLayout(context),false);
        TextView titleText = (TextView) view.findViewById(R.id.title);
        titleText.setText("成员列表");
        titleText.setTextColor(Color.WHITE);
        TextView back = (TextView) view.findViewById(R.id.back);
        back.setVisibility(View.GONE);
        TextView titleButton = (TextView) view.findViewById(R.id.title_button);
        titleButton.setText("取消");
        titleButton.setTextColor(Color.WHITE);
        titleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        return view;
    }
}
