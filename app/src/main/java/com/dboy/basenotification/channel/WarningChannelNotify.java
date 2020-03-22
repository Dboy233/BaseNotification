package com.dboy.basenotification.channel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.dboy.base.notify.BaseNotification;
import com.dboy.base.notify.BaseNotificationData;
import com.dboy.base.notify.NotificationControl;
import com.dboy.basenotification.config.NotificationConfig;

import org.jetbrains.annotations.NotNull;

public abstract class WarningChannelNotify<T extends BaseNotificationData> extends BaseNotification<T> {


    public WarningChannelNotify(@NotNull T data) {
        super(data);
    }

    @NotNull
    @Override
    public String getChannelId() {
        return NotificationConfig.CHANNEL_ID_WARNING;
    }

    @NotNull
    @Override
    public String getChannelName() {
        return NotificationConfig.CHANNEL_NAME_WARNING;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void configureChannel(@NotNull NotificationChannel notificationChannel) {
//        notificationChannel.setSound()
        notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
    }


    @Override
    public void configureNotify(@NotNull NotificationCompat.Builder mBuilder) {
        //设置通知的共同属性
        mBuilder.setShowWhen(true)
                .setSmallIcon(getData().getSmallIcon())
                .setContentTitle(getData().getContentTitle())
                .setContentText(getData().getContentText())
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setGroup("warning");
    }

}
