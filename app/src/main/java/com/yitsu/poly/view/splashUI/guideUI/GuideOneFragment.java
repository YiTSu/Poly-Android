package com.yitsu.poly.view.splashUI.guideUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yitsu.poly.R;

/**
 * Created by butterfly on 2018/11/25.
 * 引导界面的第一个Fragment页面
 */

public class GuideOneFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guide_one,container,false);
        return rootView;
    }
}
