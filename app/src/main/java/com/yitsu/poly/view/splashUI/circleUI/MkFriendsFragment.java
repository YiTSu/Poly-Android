package com.yitsu.poly.view.splashUI.circleUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yitsu.poly.model.type.Type;

/**
 * Created by butterfly on 2018/12/7.
 * 交友圈的Fragment
 */

public class MkFriendsFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listener = createRefreshListener(Type.SIX);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onRefresh();
    }
}

