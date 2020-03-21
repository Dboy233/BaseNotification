package com.dboy.base.notify

import android.Manifest.permission
import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import com.dboy.base.notify.listener.PendingIntentListener
import com.dboy.base.notify.utils.ContextUtil

object NotificationControl {


    val mNotificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(ContextUtil.getApplication().baseContext)

    /**
     * 是否开启通知栏权限
     */
    fun areNotificationsEnabled(): Boolean = mNotificationManagerCompat.areNotificationsEnabled()


    /**
     * 添加点击事件监听器 监听所有通知
     */
    fun addPendingIntentListener(pendingIntentListener: PendingIntentListener) {
        BaseNotifyBroadcast.INSTANCE.addPendingIntentListener(pendingIntentListener)
    }

    /**
     * 移除监听器
     */
    fun removePendingIntentListener(pendingIntentListener: PendingIntentListener) {
        BaseNotifyBroadcast.INSTANCE.removePendingIntentListener(pendingIntentListener)
    }

    /**
     * 取消一个通知
     */
    fun cancel(notifyId: Int) {
        mNotificationManagerCompat.cancel(notifyId)
    }

    /**
     * 取消一个通知
     */
    fun cancel(notifyId: Int, tag: String) {
        mNotificationManagerCompat.cancel(tag, notifyId)
    }

    /**
     * 取消所有通知
     */
    fun cancelAllNotify() {
        mNotificationManagerCompat.cancelAll()
    }

    /**
     * 调用此方法，会释放所有与通知相关的接口，通知，类
     */
    fun releaseAll() {
        mNotificationManagerCompat.cancelAll()
        BaseNotifyBroadcast.INSTANCE.onDestroy()
    }

    /**
     * 设置展开和折叠通知栏
     */
    @RequiresPermission(permission.EXPAND_STATUS_BAR)
    fun setNotificationBarVisibility(isVisible: Boolean) {
        val methodName: String = if (isVisible) {
            if (Build.VERSION.SDK_INT <= 16) "expand" else "expandNotificationsPanel"
        } else {
            if (Build.VERSION.SDK_INT <= 16) "collapse" else "collapsePanels"
        }
        invokePanels(methodName)
    }

    private fun invokePanels(methodName: String) {
        try {
            @SuppressLint("WrongConstant") val service: Any? =
                ContextUtil.getApplication().getSystemService("statusbar")
            @SuppressLint("PrivateApi") val statusBarManager =
                Class.forName("android.app.StatusBarManager")
            val expand = statusBarManager.getMethod(methodName)
            expand.invoke(service)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}


