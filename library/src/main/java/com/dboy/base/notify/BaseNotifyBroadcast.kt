package com.dboy.base.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.dboy.base.notify.BroadcastConstant.ACTION_NOTIFY_CLICK
import com.dboy.base.notify.BroadcastConstant.ACTION_NOTIFY_CLICK_NOTIFY_ID
import com.dboy.base.notify.BroadcastConstant.ACTION_NOTIFY_CLICK_VIEW_ID
import com.dboy.base.notify.listener.PendingIntentListener
import com.dboy.base.notify.utils.ContextUtil

class BaseNotifyBroadcast : BroadcastReceiver {


    /**
     * 通知栏点击事件分发监听器列表
     */
    private val arrayList = mutableListOf<PendingIntentListener>()

    companion object {
        val INSTANCE: BaseNotifyBroadcast by lazy {
            BaseNotifyBroadcast()
        }
    }

    constructor() {
        register()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val action = intent.action!!
            if (action == ACTION_NOTIFY_CLICK) {
                val viewId = intent.getIntExtra(ACTION_NOTIFY_CLICK_VIEW_ID, 0)
                val notifyId = intent.getIntExtra(ACTION_NOTIFY_CLICK_NOTIFY_ID, -1)
                arrayList.map {
                    it.onClick(notifyId, viewId)
                }
            }
        }
    }

    fun register() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_NOTIFY_CLICK)
        ContextUtil.getApplication().registerReceiver(this, intentFilter)
    }

    fun onDestroy() {
        arrayList.clear()
        ContextUtil.getApplication().unregisterReceiver(this)
    }

    /**
     * 添加监听器
     */
    open fun addPendingIntentListener(pendingIntentListener: PendingIntentListener) {
        if (!arrayList.contains(pendingIntentListener)) {
            arrayList.add(pendingIntentListener)
        }
    }

    open fun removePendingIntentListener(pendingIntentListener: PendingIntentListener) {
        if (arrayList.contains(pendingIntentListener)) {
            arrayList.remove(pendingIntentListener)
        }
    }

}