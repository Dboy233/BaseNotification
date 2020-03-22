package com.dboy.basenotification.data

import com.dboy.base.notify.BaseNotificationData

data class WarningData(val notifyId: Int, val icon: Int, val content: CharSequence) :
    BaseNotificationData {
    override fun getSmallIcon(): Int = icon

    override fun getContentTitle(): CharSequence? = null

    override fun getContentText(): CharSequence = content

}