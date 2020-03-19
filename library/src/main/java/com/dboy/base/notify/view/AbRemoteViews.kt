package com.dboy.base.notify.view

import android.app.PendingIntent
import android.content.Intent
import android.widget.RemoteViews
import com.dboy.base.notify.BroadcastConstant.ACTION_NOTIFY_CLICK
import com.dboy.base.notify.BroadcastConstant.ACTION_NOTIFY_CLICK_NOTIFY_ID
import com.dboy.base.notify.BroadcastConstant.ACTION_NOTIFY_CLICK_VIEW_ID
import com.dboy.base.notify.utils.ContextUtil

open class AbRemoteViews(packageName: String?, layoutId: Int) :
    RemoteViews(packageName, layoutId) {

    fun setOnClickPendingIntent(notifyId: Int, viewId: Int): AbRemoteViews {
        val intent: Intent =
            Intent(ACTION_NOTIFY_CLICK).setPackage(ContextUtil.getApplication().packageName)
        intent.putExtra(ACTION_NOTIFY_CLICK_VIEW_ID, viewId)
        intent.putExtra(ACTION_NOTIFY_CLICK_NOTIFY_ID, notifyId)
        val pendingIntent = PendingIntent.getBroadcast(
            ContextUtil.getApplication(),
            notifyId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        setOnClickPendingIntent(viewId, pendingIntent)
        return this
    }
}