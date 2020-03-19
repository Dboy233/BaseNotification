package com.dboy.basenotification;

import android.app.NotificationChannel;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.dboy.base.notify.BaseNotification;
import com.dboy.base.notify.view.BaseRemoteViews;

import org.jetbrains.annotations.NotNull;

/**
 *  业务基类一底层主要就是配置渠道，设置默认icon
 * @param <T>
 */
public abstract class BaseNotify<T> extends BaseNotification<T> {

    /**
     * @param mData 适配你的数据 并在super之后添加你的Remote的layout id
     */
    public BaseNotify(@NotNull T mData) {
        super(mData);
    }

    @NotNull
    @Override
    public String getChannelName() {
        return "消息通知";
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void configureChannel(@NotNull NotificationChannel notificationChannel) {
        notificationChannel.setSound(null, null);
    }
}
