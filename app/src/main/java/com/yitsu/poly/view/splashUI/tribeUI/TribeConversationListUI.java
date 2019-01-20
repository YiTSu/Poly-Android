package com.yitsu.poly.view.splashUI.tribeUI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.lib.presenter.conversation.CustomViewConversation;
import com.yitsu.poly.R;

/**
 * Created by butterfly on 2018/12/29.
 * 会话列表
 */

public class TribeConversationListUI extends IMConversationListUI{

    public TribeConversationListUI(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public View getCustomConversationListTitle(final Fragment fragment, final Context context, LayoutInflater inflater) {
        LinearLayout customView = (LinearLayout)inflater.inflate(R.layout.conversation_title_bar,new LinearLayout(context),false);
        ImageView backImageView = (ImageView)customView.findViewById(R.id.back_imageview);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.getActivity().finish();
            }
        });
        return customView;
    }

    @Override
    public boolean needHideTitleView(Fragment fragment) {
        return true;
    }

    @Override
    public boolean needHideNullNetWarn(Fragment fragment) {
        return false;
    }

    @Override
    public boolean getPullToRefreshEnabled() {
        return true;
    }

    @Override
    public boolean enableSearchConversations(Fragment fragment) {
        return true;
    }

    @Override
    public void onDestroy(Fragment fragment) {
        super.onDestroy(fragment);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState, Fragment fragment) {
        super.onActivityCreated(savedInstanceState, fragment);

    }

    @Override
    public void onResume(Fragment fragment) {
        super.onResume(fragment);
    }

    @Override
    public View getCustomEmptyViewInConversationUI(Context context) {
        TextView textView = new TextView(context);
        textView.setText("目前没有会话，赶紧去找人聊聊吧！");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(18);
        return textView;
    }

    @Override
    public int getCustomBackgroundResId() {
        return R.color.colorBackground;
    }

    @Override
    public int getTribeConversationHead(Fragment fragment, YWConversation conversation) {
        return R.drawable.aliwx_tribe_head_default;
    }

    @Override
    public View getCustomView(Context context, YWConversation conversation, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.demo_conversation_custom_view_item,
                    parent,
                    false);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.conversationName = (TextView)convertView.findViewById(R.id.conversation_name);
        viewHolder.conversationContent = (TextView)convertView.findViewById(R.id.conversation_content);
        CustomViewConversation customViewConversation = (CustomViewConversation)conversation;
        if(TextUtils.isEmpty(customViewConversation.getConversationName()))
            viewHolder.conversationName.setText("可自定义View布局的会话");
        else
            viewHolder.conversationName.setText(customViewConversation.getConversationName());
        viewHolder.conversationContent.setText(customViewConversation.getLatestContent());
        return convertView;
    }

    static class ViewHolder {
        TextView conversationName;
        TextView conversationContent;
    }
}
