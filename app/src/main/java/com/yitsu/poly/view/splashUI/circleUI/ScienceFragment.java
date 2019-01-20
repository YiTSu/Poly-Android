package com.yitsu.poly.view.splashUI.circleUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yitsu.poly.model.type.Type;

/**
 * Created by butterfly on 2018/12/5.
 * 学术圈的Fragment
 */

public class ScienceFragment  extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listener = createRefreshListener(Type.THREE);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onRefresh();
    }
}
