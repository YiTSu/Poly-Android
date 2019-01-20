package com.yitsu.poly.view.splashUI.creatAcUI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yitsu.poly.R;

/**
 * Created by butterfly on 2018/12/13.
 * 发起活动的第一个Fragment
 */
public class CreateAcOneFragment extends Fragment {

    private View rootView;
    public static EditText editTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_ac_one, container, false);
        editTitle = (EditText)rootView.findViewById(R.id.edit_title);
        return rootView;
    }


}
