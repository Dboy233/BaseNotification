package com.dboy.basenotification.easy;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.dboy.base.notify.BaseNotification;
import com.dboy.base.notify.view.BaseRemoteViews;
import com.dboy.basenotification.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author Dboy
 * @date 2020/6/28
 * Class 描述 :
 */
public class EasyNotify extends BaseNotification<EasyData> {
    public EasyNotify(@NotNull EasyData data) {
        super(data);
        addContentRemoteViews(R.layout.easy_notify_layout);
    }

    @Override
    public void convert(@NotNull BaseRemoteViews mBaseRemoteViews, @NotNull EasyData data) {
        if (mBaseRemoteViews.getContentRemote() != null) {
            mBaseRemoteViews.getContentRemote()
                    .setOnClickPendingIntent2(data.getNotifyId(), R.id.notify_easy_layout)
                    .setTextViewText2(R.id.notify_easy_title, data.getTitle())
                    .setTextViewText2(R.id.notify_easy_subtitle, data.getContent());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void configureChannel(@NotNull NotificationChannel notificationChannel) {
        notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
    }

    @Override
    public void configureNotify(@NotNull NotificationCompat.Builder mBuilder) {
        //设置通知的共同属性
        mBuilder.setShowWhen(true)
                .setSmallIcon(getData().getIcon())
                .setContentTitle(getData().getTitle())
                .setContentText(getData().getContent())
                .setTicker(getData().getTitle())
                .setContentInfo(getData().getContent())
                .setAutoCancel(true)
                .setSubText(getData().getTitle())
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setGroup("easy");
    }

    @NotNull
    @Override
    public String getChannelName() {
        return getData().getChannelName();
    }

    @Override
    public int getNotificationId() {
        return getData().getNotifyId();
    }

    @NotNull
    @Override
    public String getChannelId() {
        return getData().getChannelName();
    }
}
