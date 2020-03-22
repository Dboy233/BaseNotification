package com.dboy.basenotification.channel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.dboy.base.notify.BaseNotification;
import com.dboy.base.notify.BaseNotificationData;
import com.dboy.base.notify.view.BaseRemoteViews;
import com.dboy.basenotification.config.NotificationConfig;

import org.jetbrains.annotations.NotNull;

/**
 * 业务基类一底层主要就是配置渠道
 *
 * @param <T>
 */
public abstract class ChatChannelNotify<T extends BaseNotificationData> extends BaseNotification<T> {

    /**
     * @param mData 适配你的数据 并在super之后添加你的Remote的layout id
     */
    public ChatChannelNotify(@NotNull T mData) {
        super(mData);
    }

    @NotNull
    @Override
    public String getChannelName() {
        return NotificationConfig.CHANNEL_ID_CHAT;
    }

    @NotNull
    @Override
    public String getChannelId() {
        return NotificationConfig.CHANNEL_ID_CHAT;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void configureChannel(@NotNull NotificationChannel notificationChannel) {
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
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setGroup("chat");
    }


}
