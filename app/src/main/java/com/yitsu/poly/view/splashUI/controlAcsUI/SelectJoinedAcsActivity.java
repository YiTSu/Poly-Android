package com.yitsu.poly.view.splashUI.controlAcsUI;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yitsu.poly.controller.application.Poly;
import com.yitsu.poly.R;
import com.yitsu.poly.controller.application.adpter.CardsAdapter;
import com.yitsu.poly.model.type.ConstantString;
import com.yitsu.poly.model.type.info.ActivityInfo;
import com.yitsu.poly.view.splashUI.mainUI.MainActivity;
import com.yitsu.poly.view.splashUI.showAcUI.ShowAcsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by butterfly on 2018/12/7.
 * 加入的活动页面Activity
 */
public class SelectJoinedAcsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_select_joined_acs);
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
                intent.setClass(SelectJoinedAcsActivity.this,ShowAcsActivity.class);
                startActivity(intent);
            }
        });

        /*
        长按操作的定义
         */
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                    @Override
                    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                        contextMenu.add(0,0,0,"进入群组");
                    }
                });
                return false;
            }
        });
    }

    /*
    长按选中Item并点击进入相应群组
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        int pos = (int) info.id;// 这里的info.id对应的就是数据库中_id的值
        switch (item.getItemId()){
            case 0:
                ActivityInfo.Act act = acts.get(pos);
                Intent intent = MainActivity.mIMKit.getTribeChattingActivityIntent(Long.parseLong(act.getTribe_id()));
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
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
                        Call<ActivityInfo> call = Poly.service.selectJoinedAcs(userId);
                        call.enqueue(new Callback<ActivityInfo>() {
                            @Override
                            public void onResponse(Call<ActivityInfo> call, Response<ActivityInfo> response) {
                                int code = response.body().getCode();
                                switch (code){
                                    case 402:
                                        Toast.makeText(getApplicationContext(),R.string.connect_error,Toast.LENGTH_SHORT).show(); break;
                                    case 418:
                                        Toast.makeText(getApplicationContext(), R.string.not_join_acs,Toast.LENGTH_SHORT).show(); break;
                                    case 419:
                                        acts = response.body().getActs();
                                        adapter= new CardsAdapter(SelectJoinedAcsActivity.this,R.layout.listview_item, acts);
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
