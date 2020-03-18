package com.dboy.base.notify.listener

interface PendingIntentListener {
    /**
     * @param notifyId 哪个通知栏的点击事件
     * @param viewId 点击的viewId
     */
    fun onClick(notifyId: Int, viewId: Int)
}