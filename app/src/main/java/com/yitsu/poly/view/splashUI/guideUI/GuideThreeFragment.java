package com.yitsu.poly.view.splashUI.guideUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yitsu.poly.R;
import com.yitsu.poly.view.splashUI.signuplogin.LoginActivity;

/**
 * Created by butterfly on 2018/11/25.
 * 引导界面的第三个Fragment页面
 */

public class GuideThreeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_guide_three,container,false);
        Button btStart = (Button) rootView.findViewById(R.id.bt_start);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //点击按钮结束引导进入APP
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        return rootView;
    }
}
