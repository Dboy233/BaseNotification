package com.dboy.base.notify

import android.app.Service

interface IBaseNotify {

    fun notificationChannel()

    fun notificationBuilder()

    fun show()

    fun show(service: Service)

    fun show(service: Service, foregroundServiceType: Int)
}