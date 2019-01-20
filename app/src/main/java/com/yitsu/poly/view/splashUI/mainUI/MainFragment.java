package com.yitsu.poly.view.splashUI.mainUI;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yitsu.poly.R;
import com.yitsu.poly.view.splashUI.circleUI.ArtsFragment;
import com.yitsu.poly.view.splashUI.circleUI.FindFragment;
import com.yitsu.poly.view.splashUI.circleUI.HouseFragment;
import com.yitsu.poly.view.splashUI.circleUI.MkFriendsFragment;
import com.yitsu.poly.view.splashUI.circleUI.PlayFragment;
import com.yitsu.poly.view.splashUI.circleUI.RecommendFragment;
import com.yitsu.poly.view.splashUI.circleUI.ScienceFragment;
import com.yitsu.poly.view.splashUI.circleUI.SportsFragment;
import com.yitsu.poly.view.splashUI.circleUI.TradeFragment;
import com.yitsu.poly.view.splashUI.circleUI.TransFragment;
import com.yitsu.poly.view.splashUI.creatAcUI.ActivitiesActivity;
import com.yitsu.poly.view.splashUI.createNoteUI.CreateNoteActivity;
import com.yitsu.poly.model.type.info.PersonCreateAc;
import com.yitsu.poly.view.splashUI.noteUI.BaseNoteFragment;
import com.yitsu.poly.model.type.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by butterfly on 2018/11/22.
 * 主页Fragment
 */

public class MainFragment extends Fragment {

    private Button btSelectAcs,btSelectNote;
    private TextView tvSelect;
    private PopupWindow popWindow;
    private View layout;
    private ListView menuList;
    private List<Map<String, String>> list;
    private static final String[] CIRCLES = {"推荐","运动圈","艺术圈","学术圈","出行圈","娱乐圈","交友圈","交易圈","租房圈","招领圈"};
    private static int type;
    private Toolbar toolbar;
    private ImageView createAcs;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        btSelectAcs = (Button) rootView.findViewById(R.id.bt_select_acs);
        btSelectNote = (Button) rootView.findViewById(R.id.bt_select_note);
        tvSelect = (TextView)rootView.findViewById(R.id.tv_select);
        createAcs = (ImageView)rootView.findViewById(R.id.create_acs);
        initParam();
        Drawable drawable = getResources().getDrawable(R.drawable.icon_main_select);
        drawable.setBounds(0,0,40,23);
        selectText();
        tvSelect.setCompoundDrawables(null,null,drawable,null);
        btSelectAcs.setSelected(true);
        btSelectNote.setSelected(false);
        btSelectAcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSelectAcs.setSelected(true);
                btSelectNote.setSelected(false);
                selectAcsFragment();
            }
        });btSelectNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btSelectAcs.setSelected(false);
                btSelectNote.setSelected(true);
                selectNotesFragment();
            }
        });
        selectAcsFragment();
        createAcs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == Type.ZERO){
                    Toast.makeText(getActivity(),"推荐栏不能发起活动",Toast.LENGTH_SHORT).show();
                }else {
                    if (btSelectAcs.isSelected()){
                        PersonCreateAc.setType(type);
                        startActivity(new Intent(getActivity(),ActivitiesActivity.class));
                    }else if (btSelectNote.isSelected()){
                        startActivity(new Intent(getActivity(), CreateNoteActivity.class));
                    }
                }
            }
        });

        return rootView;
    }


    /*
    初始化首页圈子选择下拉框
     */
    private void initParam(){
        list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < 10; i++) {
            HashMap<String, String> mapTemp = new HashMap<String, String>();
            mapTemp.put("item", CIRCLES[i]);
            list.add(mapTemp);
        }
        tvSelect.setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (popWindow != null && popWindow.isShowing()){
                popWindow.dismiss();
            }else {
                layout = getActivity().getLayoutInflater().inflate(R.layout.pop_menulist,null);
                menuList = (ListView) layout.findViewById(R.id.menulist);
                SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),list,R.layout.pop_menuitem,new String[]{"item"},new int[]{R.id.menuitem});
                menuList.setAdapter(simpleAdapter);
                menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        tvSelect.setText(list.get(i).get("item"));
                        type = i;//设置类型
                        if (btSelectAcs.isSelected()){
                            selectAcsFragment();
                        }else if (btSelectNote.isSelected()){
                            selectNotesFragment();
                        }
                        if (popWindow != null && popWindow.isShowing()){
                            popWindow.dismiss();
                        }
                    }
                });
                popWindow = new PopupWindow(layout,tvSelect.getWidth()*2, ViewGroup.LayoutParams.WRAP_CONTENT);
                ColorDrawable cd = new ColorDrawable(0000);
                popWindow.setBackgroundDrawable(cd);
                popWindow.setAnimationStyle(R.style.MyPopWindowStyle);
                popWindow.update();
                popWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                popWindow.setTouchable(true);
                popWindow.setOutsideTouchable(true);
                popWindow.setFocusable(true);
                popWindow.showAsDropDown(tvSelect,0,(toolbar.getBottom()-tvSelect.getHeight())/2);
                popWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE){
                            popWindow.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
            }
        }
    };

    /*
    根据下拉框选择情况设置text内容
     */
    private void selectText(){
        switch (type){
            case Type.ZERO:tvSelect.setText("推荐"); break;
            case Type.ONE:tvSelect.setText("运动圈"); break;
            case Type.TWO:tvSelect.setText("艺术圈"); break;
            case Type.THREE:tvSelect.setText("学术圈"); break;
            case Type.FOUR:tvSelect.setText("出行圈"); break;
            case Type.FIVE:tvSelect.setText("娱乐圈"); break;
            case Type.SIX:tvSelect.setText("交友圈"); break;
            case Type.SEVEN:tvSelect.setText("交易圈"); break;
            case Type.EIGHT:tvSelect.setText("租房圈"); break;
            case Type.NINE: tvSelect.setText("招领圈"); break;
        }
    }

    /*
    选择展示对应的活动Fragment
     */
    private void selectAcsFragment(){
        switch (type){
            case Type.ZERO:replaceFragment(new RecommendFragment()); break;
            case Type.ONE:replaceFragment(new SportsFragment()); break;
            case Type.TWO:replaceFragment(new ArtsFragment()); break;
            case Type.THREE:replaceFragment(new ScienceFragment()); break;
            case Type.FOUR:replaceFragment(new TransFragment()); break;
            case Type.FIVE:replaceFragment(new PlayFragment()); break;
            case Type.SIX:replaceFragment(new MkFriendsFragment()); break;
            case Type.SEVEN:replaceFragment(new TradeFragment()); break;
            case Type.EIGHT:replaceFragment(new HouseFragment()); break;
            case Type.NINE: replaceFragment(new FindFragment()); break;
        }
    }

    /*
    选择展示对应的社区Fragment
     */
    private void selectNotesFragment(){
        switch (type){
            case Type.ZERO: replaceFragment(new BaseNoteFragment());break;
            case Type.ONE:replaceFragment(new BaseNoteFragment()); break;
            case Type.TWO:replaceFragment(new BaseNoteFragment()); break;
            case Type.THREE:replaceFragment(new BaseNoteFragment()); break;
            case Type.FOUR:replaceFragment(new BaseNoteFragment()); break;
            case Type.FIVE:replaceFragment(new BaseNoteFragment()); break;
            case Type.SIX:replaceFragment(new BaseNoteFragment()); break;
            case Type.SEVEN:replaceFragment(new BaseNoteFragment()); break;
            case Type.EIGHT:replaceFragment(new BaseNoteFragment()); break;
            case Type.NINE: replaceFragment(new BaseNoteFragment()); break;
        }
    }

    /*
    替换Fragment
     */
    void replaceFragment(Fragment fragment){
        getChildFragmentManager().beginTransaction().add(R.id.main_fragment_layout,fragment).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}
