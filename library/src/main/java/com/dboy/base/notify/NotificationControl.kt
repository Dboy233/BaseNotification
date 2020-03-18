package com.dboy.base.notify

import androidx.core.app.NotificationManagerCompat
import com.dboy.base.notify.listener.PendingIntentListener
import com.dboy.base.notify.utils.ContextUtil

object NotificationControl {


    val mNotificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(ContextUtil.getApplication().baseContext)


    fun addPendingIntentListener(pendingIntentListener: PendingIntentListener){
        BaseNotifyBroadcast.INSTANCE.addPendingIntentListener(pendingIntentListener)
    }

    /**
     * 调用此方法，会释放所有与通知相关的接口，通知，类
     */
    fun releaseAll() {
        mNotificationManagerCompat.cancelAll()
        BaseNotifyBroadcast.INSTANCE.onDestroy()
    }

    /**
     * 取消所有通知
     */
    fun cancelAllNotify() {
        mNotificationManagerCompat.cancelAll()
    }

    /**
     * 取消一个通知
     */
    fun cancel(notifyId: Int) {
        mNotificationManagerCompat.cancel(notifyId)
    }

}


