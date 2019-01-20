package com.yitsu.poly.view.splashUI.noteUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yitsu.poly.NoteShowActivity;
import com.yitsu.poly.R;
import com.yitsu.poly.controller.application.adpter.NoteCardsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by butterfly on 2018/11/23.
 * 不同圈子帖子的根布局，用于定义一类Fragment的整体样式
 */

public class BaseNoteFragment extends Fragment {

    protected ListView listView;
    protected List<Integer> acts;
    protected SwipeRefreshLayout refreshLayout;
    protected NoteCardsAdapter adapter;
    protected SwipeRefreshLayout.OnRefreshListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView)rootView.findViewById(R.id.list);
        acts = new ArrayList<>();
        acts.add(1);
        acts.add(2);
        acts.add(3);
        acts.add(4);
        acts.add(5);
        adapter = new NoteCardsAdapter(getActivity(),R.layout.listview_note_item,acts);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), NoteShowActivity.class));
            }
        });

//        refreshLayout.setOnRefreshListener(listener);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }
}
