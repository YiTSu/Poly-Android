package com.yitsu.poly.view.splashUI.circleUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.controller.application.adpter.CardsAdapter;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.model.type.info.ActivityInfo;
import com.yitsu.poly.view.splashUI.showAcUI.ShowAcsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_APPEND;

/**
 * Created by butterfly on 2018/12/5.
 * 不同圈子的根布局，用于定义一类Fragment的整体样式
 */

public class BaseFragment extends Fragment {

    protected ListView listView;
    protected List<ActivityInfo.Act> acts;
    protected SwipeRefreshLayout refreshLayout;
    protected CardsAdapter adapter;
    protected SwipeRefreshLayout.OnRefreshListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView)rootView.findViewById(R.id.list);
        refreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout);
        if (acts == null){
            acts = new ArrayList<>();
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                    listener.onRefresh();
                }
            });
        }
        adapter = new CardsAdapter(getActivity(),R.layout.listview_item, acts);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActivityInfo.Act act = acts.get(i);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ConstantString.ACT,act);
                intent.putExtras(bundle);
                intent.putExtra(ConstantString.IS_SHOW_OR_EXIT,ConstantString.SHOW);
                intent.setClass(getActivity(),ShowAcsActivity.class);
                startActivity(intent);
            }
        });

        refreshLayout.setOnRefreshListener(listener);
        return rootView;
    }

    /*
    刷新操作
     */
    protected SwipeRefreshLayout.OnRefreshListener createRefreshListener(final int type){
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String userName = Poly.userPreference.getString(ConstantString.USER,null);
                        Poly.infoPreference = getActivity().getSharedPreferences(userName,MODE_APPEND);
                        int userid = Poly.infoPreference.getInt(ConstantString.USER_ID,0);
                        Call<ActivityInfo> call = Poly.service.searchActivity(userid,type, 1, 100,Poly.location.getLongitude(),
                                Poly.location.getLatitude(),Poly.AcsRange);
                        call.enqueue(new Callback<ActivityInfo>() {
                            @Override
                            public void onResponse(Call<ActivityInfo> call, Response<ActivityInfo> response) {
                                acts = response.body().getActs();
                                adapter = new CardsAdapter(getActivity(), R.layout.listview_item, acts);
                                listView.setAdapter(adapter);
                            }

                            @Override
                            public void onFailure(Call<ActivityInfo> call, Throwable t) {

                            }
                        });

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        };
        return listener;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
