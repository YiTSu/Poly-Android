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
 * 发起活动的第三个Fragment
 */
public class CreateAcTwoFragment extends Fragment {

    private View rootView;
    public static EditText editDate,editStartTime,editEndTime,editLimitedNum,editLocation;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_create_ac_two, container, false);
        editDate = (EditText)rootView.findViewById(R.id.edit_date);
        editStartTime = (EditText)rootView.findViewById(R.id.edit_start_time);
        editEndTime = (EditText)rootView.findViewById(R.id.edit_end_time);
        editLimitedNum = (EditText)rootView.findViewById(R.id.edit_limited_num);
        editLocation = (EditText)rootView.findViewById(R.id.edit_location);
        return rootView;
    }


}
