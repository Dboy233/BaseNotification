package com.dboy.base.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.dboy.base.notify.listener.PendingIntentListener
import com.dboy.base.notify.utils.ContextUtil
import com.dboy.base.notify.view.BaseRemoteViews
import com.dboy.base.notify.view.BigRemote
import com.dboy.base.notify.view.ContentRemote
import com.dboy.base.notify.view.TickerRemote

abstract class BaseNotification<T : Any> : IBaseNotify {

    constructor(mData: T) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel()
        }
        notificationBuilder()
    }

    /**
     * Base上下文
     */
    private val mContext: Context = ContextUtil.getApplication()
    /**
     * Manager
     */
    private val mNotificationManagerCompat = NotificationControl.mNotificationManagerCompat
    /**
     *  通知栏构造器
     */
    private lateinit var mBuilder: NotificationCompat.Builder
    /**
     * 自定义视图管理类
     */
    private val mBaseRemoteViews: BaseRemoteViews = BaseRemoteViews()

    @RequiresApi(api = Build.VERSION_CODES.O)
    final override fun notificationChannel() {
        //获取用户的渠道id
        var notificationChannel =
            mNotificationManagerCompat.getNotificationChannel(mContext.packageName)
        if (notificationChannel == null) {
            //为用户配置默认渠道
            notificationChannel = NotificationChannel(
                mContext.packageName,
                getChannelName(),
                NotificationManager.IMPORTANCE_DEFAULT
            )
        }
        configureChannel(notificationChannel)
    }

    final override fun notificationBuilder() {
        mBuilder = NotificationCompat.Builder(mContext, mContext.packageName)
        configureNotify(mBuilder)
    }


    /**
     * 显示
     */
    override fun show() {
        mNotificationManagerCompat.notify(getNotificationId(), mBuilder.build())
    }

    /**
     * 显示为前台通知
     */
    override fun show(service: Service) {
        service.startForeground(getNotificationId(), mBuilder.build())
    }

    /**
     * 显示为前台通知并设置 service type
     */
    override fun show(service: Service, foregroundServiceType: Int) {
        service.startForeground(getNotificationId(), mBuilder.build(), foregroundServiceType)
    }

    /**
     * 配置用户的渠道
     */
    abstract fun configureChannel(notificationChannel: NotificationChannel)

    /**
     * 配置通知信息
     */
    abstract fun configureNotify(mBuilder: NotificationCompat.Builder)

    /**
     * 获取渠道名字 id已经默认为app的报名了
     */
    abstract fun getChannelName(): String

    /**
     * 获取通知id
     */
    abstract fun getNotificationId(): Int

    abstract fun getIcon(): Int
    /**
     * 添加大图
     */
    protected fun addBigRemoteViews(layoutId: Int) {
        mBaseRemoteViews.mBigRemote = BigRemote(layoutId)
    }

    /**
     * 添加小图
     */
    protected fun addContentRemoteViews(layoutId: Int) {
        mBaseRemoteViews.mContentRemote = ContentRemote(layoutId)
    }

    /**
     * 添加TickerView
     */
    protected fun addTickerRemoteViews(layoutId: Int) {
        mBaseRemoteViews.mTickerRemote = TickerRemote(layoutId)
    }

    /**
     * 添加自定义视图
     */
    protected fun addCustomRemoteView(layoutId: Int) {
        mBaseRemoteViews.mCustomRemote
    }


}