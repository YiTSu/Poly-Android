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
 * 群组消息列表Fragment
 */

public class TribeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tribe_fragment,container,false);
        getChildFragmentManager().beginTransaction().add(R.id.tribe_frame_layout,MainActivity.mIMKit.getConversationFragment()).commit();
        return rootView;
    }

}
