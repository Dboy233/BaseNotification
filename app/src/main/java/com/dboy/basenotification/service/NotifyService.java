package com.dboy.basenotification.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dboy.basenotification.notify.CommonNotify;
import com.dboy.basenotification.notify.QuickNotify;

public class NotifyService extends Service implements Handler.Callback {

    private Handler handler = new Handler(this);

    private CommonNotify commonNotify;

    private int showSize;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new QuickNotify("前台服务").show(this);
        commonNotify = new CommonNotify("普通通知 " + showSize++);
        commonNotify.show();
        handler.sendEmptyMessageDelayed(10, 5000);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        commonNotify.show("更新 普通通知 " + showSize++);
        handler.sendEmptyMessageDelayed(10, 5000);
        return true;
    }
}
