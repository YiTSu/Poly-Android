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
 * 展示活动信息的第一个Fragment
 */
public class ShowAcOneFragment extends Fragment {

    private View rootView;
    private TextView tvTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show_ac_one, container, false);
        tvTitle = (TextView)rootView.findViewById(R.id.tv_title);
        tvTitle.setText(ShowAcsActivity.act.getTitle());
        return rootView;
    }
}
