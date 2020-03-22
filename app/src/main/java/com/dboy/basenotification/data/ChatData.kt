package com.dboy.basenotification.data

import com.dboy.base.notify.BaseNotificationData

data class ChatData(val notifyId: Int, val icon: Int, val title: String, val content: String) :
    BaseNotificationData {

    override fun getSmallIcon(): Int = icon

    override fun getContentTitle(): CharSequence = title

    override fun getContentText(): CharSequence = content

}