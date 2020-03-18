package com.dboy.basenotification;

import com.dboy.base.notify.BaseNotification;

import org.jetbrains.annotations.NotNull;

public abstract class BaseNotify extends BaseNotification<String> {

    public BaseNotify(@NotNull String mData) {
        super(mData);
    }

    @NotNull
    @Override
    public String getChannelName() {
        return "消息通知";
    }

    @NotNull
    @Override
    public int getNotificationId() {
        return 0;
    }
}
