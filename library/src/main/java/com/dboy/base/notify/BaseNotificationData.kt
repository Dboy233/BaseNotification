package com.dboy.base.notify

open interface BaseNotificationData {

    fun getSmallIcon(): Int

    fun getContentTitle(): CharSequence?

    fun getContentText(): CharSequence?

}