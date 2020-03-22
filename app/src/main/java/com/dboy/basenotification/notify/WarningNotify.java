package com.dboy.basenotification.notify;

import com.dboy.base.notify.view.BaseRemoteViews;
import com.dboy.base.notify.view.ContentRemote;
import com.dboy.basenotification.R;
import com.dboy.basenotification.channel.WarningChannelNotify;
import com.dboy.basenotification.data.WarningData;

import org.jetbrains.annotations.NotNull;

/**
 * 设置为常驻通知
 */
public class WarningNotify extends WarningChannelNotify<WarningData> {

    /**
     * @param mData 适配你的数据 并在super之后添加你的Remote的layout id
     */
    public WarningNotify(@NotNull WarningData mData) {
        super(mData);
        addContentRemoteViews(R.layout.notify_comm_chat_layout);
    }

    @Override
    public void convert(@NotNull BaseRemoteViews mBaseRemoteViews, @NotNull WarningData data) {
        ContentRemote contentRemote = mBaseRemoteViews.getContentRemote();
        if (contentRemote != null) {

        }
    }

    @Override
    public int getNotificationId() {
        return getData().getNotifyId();
    }
}
