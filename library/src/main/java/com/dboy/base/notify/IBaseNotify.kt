package com.dboy.base.notify

import android.app.Service

interface IBaseNotify<T> {
    /**
     * 初始化渠道
     */
    fun initChannel()

    /**
     * 初始化通知构建器
     */
    fun initBuilder()

    /**
     * 显示
     */
    fun show()

    /**
     * 显示 并配置数据
     */
    fun show(mData: T)

    /**
     * 显示为前台通知
     * android.permission.FOREGROUND_SERVICE
     */
    fun show(service: Service)

    /**
     * 显示为前台通知 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    fun show(service: Service, mData: T)

    /**
     * 显示为前台通知并设置 service type
     * android.permission.FOREGROUND_SERVICE
     */
    fun show(service: Service, foregroundServiceType: Int)

    /**
     * 显示为前台通知并设置 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    fun show(service: Service, foregroundServiceType: Int, mData: T)

    /**
     * 取消这个通知
     */
    fun cancel()

}