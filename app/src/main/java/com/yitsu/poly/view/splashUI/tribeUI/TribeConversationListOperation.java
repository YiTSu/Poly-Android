package com.yitsu.poly.view.splashUI.tribeUI;

import android.support.v4.app.Fragment;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListOperation;
import com.alibaba.mobileim.conversation.YWConversation;

/**
 * Created by butterfly on 2018/12/29.
 * 群组会话列表
 */

public class TribeConversationListOperation extends IMConversationListOperation{
    public TribeConversationListOperation(Pointcut pointcut) {
        super(pointcut);
    }

    @Override
    public boolean onItemClick(Fragment fragment, YWConversation conversation) {
        return super.onItemClick(fragment, conversation);
    }

    @Override
    public boolean onConversationItemLongClick(Fragment fragment, YWConversation conversation) {
        return super.onConversationItemLongClick(fragment, conversation);
    }
}
