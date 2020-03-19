package com.dboy.basenotification.notify;

import androidx.core.app.NotificationCompat;

import com.dboy.base.notify.view.BaseRemoteViews;
import com.dboy.base.notify.view.ContentRemote;
import com.dboy.basenotification.R;
import com.dboy.basenotification.channel.MessageChannelNotify;

import org.jetbrains.annotations.NotNull;

/**
 * 设置为常驻通知
 */
public class QuickNotify extends MessageChannelNotify<String> {

    /**
     * @param mData 适配你的数据 并在super之后添加你的Remote的layout id
     */
    public QuickNotify(@NotNull String mData) {
        super(mData);
        addContentRemoteViews(R.layout.notify_test_layout);
    }

    @Override
    public void convert(@NotNull BaseRemoteViews mBaseRemoteViews, @NotNull String mData) {
        ContentRemote contentRemote = mBaseRemoteViews.getContentRemote();
        if (contentRemote != null) {
            contentRemote.setTextViewText2(R.id.contentText, mData).
                    setOnClickPendingIntent2(getNotificationId(), R.id.contentText);
        }
    }

    @Override
    public void configureNotify(@NotNull NotificationCompat.Builder mBuilder) {
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setOngoing(true)
                .setShowWhen(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setGroup("abc");
    }

    @Override
    public int getNotificationId() {
        return 10;
    }
}
