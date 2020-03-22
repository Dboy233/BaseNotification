package com.dboy.basenotification.notify;

import androidx.core.app.NotificationCompat;

import com.dboy.base.notify.view.BaseRemoteViews;
import com.dboy.base.notify.view.ContentRemote;
import com.dboy.basenotification.R;
import com.dboy.basenotification.channel.ChatChannelNotify;
import com.dboy.basenotification.data.ChatData;

import org.jetbrains.annotations.NotNull;

/**
 * 私密聊天
 */
public class CommPrivateChatNotify extends ChatChannelNotify<ChatData> {

    /**
     * @param mData 适配你的数据 并在super之后添加你的Remote的layout id
     */
    public CommPrivateChatNotify(@NotNull ChatData mData) {
        super(mData);
        addContentRemoteViews(R.layout.notify_comm_chat_layout);
    }

    @Override
    public void convert(@NotNull BaseRemoteViews mBaseRemoteViews, @NotNull ChatData data) {
        ContentRemote contentRemote = mBaseRemoteViews.getContentRemote();
        if (contentRemote != null) {
            contentRemote
                    .setImageViewResource2(R.id.notify_chat_head_img, data.getIcon())
                    .setTextViewText2(R.id.notify_chat_title, data.getContentTitle())
                    .setTextViewText2(R.id.notify_chat_subtitle, data.getContentText())
                    .setOnClickPendingIntent2(getNotificationId(), R.id.notify_chat_layout);
        }
    }

    @Override
    public void configureNotify(@NotNull NotificationCompat.Builder mBuilder) {
        super.configureNotify(mBuilder);
        //设置为安全的通知。不在锁屏上显示
        mBuilder.setVisibility(NotificationCompat.VISIBILITY_SECRET);
    }

    @Override
    public int getNotificationId() {
        return getData().getNotifyId();
    }
}
