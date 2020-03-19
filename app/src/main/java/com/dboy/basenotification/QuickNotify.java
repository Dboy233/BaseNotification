package com.dboy.basenotification;

import androidx.core.app.NotificationCompat;

import com.dboy.base.notify.view.BaseRemoteViews;
import com.dboy.base.notify.view.ContentRemote;

import org.jetbrains.annotations.NotNull;

public class QuickNotify extends BaseNotify<String> {


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
            contentRemote.setTextViewText(R.id.contentText,mData);
            contentRemote.setOnClickPendingIntent(getNotificationId(), R.id.contentText);
        }
    }

    @Override
    public void configureNotify(@NotNull NotificationCompat.Builder mBuilder) {
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setOngoing(true);
    }

    @Override
    public int getNotificationId() {
        return 10;
    }
}
