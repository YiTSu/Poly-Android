package com.yitsu.poly.view.splashUI.showAcUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yitsu.poly.R;

/**
 * Created by butterfly on 2018/12/13.
 * 展示活动信息的第二个Activity
 */
public class ShowAcTwoFragment extends Fragment {
    private View rootView;
    public static TextView tvDate,tvTime,tvPeople,tvLocation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show_ac_two, container, false);
        tvDate = (TextView)rootView.findViewById(R.id.tv_date);
        tvTime = (TextView)rootView.findViewById(R.id.tv_time);
        tvPeople = (TextView)rootView.findViewById(R.id.tv_people);
        tvLocation = (TextView)rootView.findViewById(R.id.tv_location);
        tvDate.setText(ShowAcsActivity.act.getDate());
        tvTime.setText(ShowAcsActivity.act.getTime());
        tvPeople.setText(ShowAcsActivity.act.getRealpeople()+"");
        tvLocation.setText(ShowAcsActivity.act.getAddress());
        return rootView;
    }
}
