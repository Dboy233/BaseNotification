package com.dboy.base.notify

import com.dboy.base.notify.utils.ContextUtil

object BroadcastConstant {
    /**
     * 广播接收器 接收到的参数
     */
    const val ACTION_NOTIFY_CLICK_VIEW_ID = "viewID"
    /**
     * 点击事件触发的通知栏id
     */
    const val ACTION_NOTIFY_CLICK_NOTIFY_ID = "notifyID"
    /**
     * 点击事件广播接收器接收 action
     */
    val ACTION_NOTIFY_CLICK: String =
        ContextUtil.getApplication().packageName.toString() + ".notify.click"
}