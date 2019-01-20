package com.yitsu.poly.view.splashUI.controlAcsUI;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
/**
 * Created by butterfly on 2018/12/7.
 * 发起的活动页面Activity
 */
public class SelectCreatedAcsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private List<ActivityInfo.Act> acts;
    private CardsAdapter adapter;
    private int userId;
    String userName;

    private SwipeRefreshLayout refreshLayout;

    private SwipeRefreshLayout.OnRefreshListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_created_acs);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listener = createRefreshListener();
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout);
        refreshLayout.setOnRefreshListener(listener);

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

        userName = Poly.userPreference.getString(ConstantString.USER,null);
        Poly.infoPreference = getSharedPreferences(userName,MODE_APPEND);
        userId = Poly.infoPreference.getInt(ConstantString.USER_ID,0);

        listView = (ListView)findViewById(R.id.list);

        /*
        短按操作的定义
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActivityInfo.Act act = acts.get(i);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable(ConstantString.ACT,act);
                intent.putExtras(bundle);
                intent.putExtra(ConstantString.IS_HEART_GONE,true);
                intent.putExtra(ConstantString.IS_SHOW_OR_EXIT,ConstantString.EXIT);
                intent.setClass(SelectCreatedAcsActivity.this,ShowAcsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        listener.onRefresh();
    }

    /*
     刷新操作
     */
    private SwipeRefreshLayout.OnRefreshListener createRefreshListener(){
        listener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call<ActivityInfo> call = Poly.service.selectCreatedAcs(userId);
                        call.enqueue(new Callback<ActivityInfo>() {
                            @Override
                            public void onResponse(Call<ActivityInfo> call, Response<ActivityInfo> response) {
                                int code = response.body().getCode();
                                switch (code){
                                    case 402:
                                        Toast.makeText(getApplicationContext(),R.string.connect_error,Toast.LENGTH_SHORT).show(); break;
                                    case 436:
                                        Toast.makeText(getApplicationContext(), "你没有创建任何活动",Toast.LENGTH_SHORT).show(); break;
                                    case 435:
                                        acts = response.body().getActs();
                                        adapter= new CardsAdapter(SelectCreatedAcsActivity.this,R.layout.listview_item, acts);
                                        listView.setAdapter(adapter);
                                        refreshLayout.setRefreshing(false);
                                        break;
                                }
                                refreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onFailure(Call<ActivityInfo> call, Throwable t) {

                            }
                        });
                    }
                }).start();
            }
        };
        return listener;
    }
}
