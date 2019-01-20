package com.yitsu.poly.view.splashUI.mainUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yitsu.poly.R;

/**
 * Created by butterfly on 2018/11/22.
 * 活动管理Fragment
 */

public class ControlFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_control,container,false);
        return rootView;
    }
}
