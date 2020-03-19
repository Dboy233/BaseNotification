package com.dboy.base.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.dboy.base.notify.utils.ContextUtil
import com.dboy.base.notify.view.BaseRemoteViews
import com.dboy.base.notify.view.BigRemote
import com.dboy.base.notify.view.ContentRemote
import com.dboy.base.notify.view.TickerRemote

abstract class BaseNotification<T : Any> : IBaseNotify<T> {
    /**
     * 数据
     */
    protected val mData: T

    /**
     * @param 配置你的数据
     */
    constructor(mData: T) {
        this.mData = mData
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initChannel()
        }
        initBuilder()
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

    /**
     * 初始化通知渠道
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    final override fun initChannel() {
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
        mNotificationManagerCompat.createNotificationChannel(notificationChannel)
    }

    /**
     * 初始化通知
     */
    final override fun initBuilder() {
        mBuilder = NotificationCompat.Builder(mContext, mContext.packageName)
        configureNotify(mBuilder)
    }


    /**
     * 添加大图
     */
    protected fun addBigRemoteViews(layoutId: Int) {
        mBaseRemoteViews.bigRemote = BigRemote(layoutId)
    }

    /**
     * 添加小图
     */
    protected fun addContentRemoteViews(layoutId: Int) {
        mBaseRemoteViews.contentRemote = ContentRemote(layoutId)
    }

    /**
     * 添加TickerView
     */
    protected fun addTickerRemoteViews(tickerText: String, layoutId: Int) {
        mBaseRemoteViews.tickerRemote = TickerRemote(tickerText, layoutId)
    }

    /**
     * 添加自定义视图
     */
    protected fun addCustomCustomRemoteView(layoutId: Int) {
        mBaseRemoteViews.customContentRemote
    }

    /**
     * 显示
     */
    override fun show() {
        show(mData)
    }

    /**
     * 显示 并配置数据
     */
    override fun show(mData: T) {
        convert(mBaseRemoteViews, mData)
        configureRemote()
        mNotificationManagerCompat.notify(getNotificationId(), mBuilder.build())
    }

    /**
     * 显示为前台通知
     * android.permission.FOREGROUND_SERVICE
     */
    override fun show(service: Service) {
        show(service, mData)
    }

    /**
     * 显示为前台通知 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    override fun show(service: Service, mData: T) {
        convert(mBaseRemoteViews, mData)
        configureRemote()
        service.startForeground(getNotificationId(), mBuilder.build())
    }

    /**
     * 显示为前台通知并设置 service type
     * android.permission.FOREGROUND_SERVICE
     */
    override fun show(service: Service, foregroundServiceType: Int) {
        show(service, foregroundServiceType, mData)
    }

    /**
     * 显示为前台通知并设置 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    override fun show(service: Service, foregroundServiceType: Int, mData: T) {
        convert(mBaseRemoteViews, mData)
        configureRemote()
        service.startForeground(getNotificationId(), mBuilder.build(), foregroundServiceType)
    }

    /**
     * 取消这个通知
     */
    override fun cancel() {
        mNotificationManagerCompat.cancel(getNotificationId())
    }

    /**
     * 配置视图
     */
    private fun configureRemote() {
        mBaseRemoteViews.customContentRemote?.let {
            mBuilder.setCustomContentView(it)
        }
        mBaseRemoteViews.contentRemote?.let {
            mBuilder.setContent(it)
        }
        mBaseRemoteViews.bigRemote?.let {
            mBuilder.setCustomBigContentView(it)
        }
        mBaseRemoteViews.tickerRemote?.let { tickerRemote ->
            tickerRemote.tickerText?.let { tickerText ->
                mBuilder.setTicker(tickerText, tickerRemote)
            }
        }
    }

    /**
     * 配置数据
     */
    abstract fun convert(mBaseRemoteViews: BaseRemoteViews, mData: T)

    /**
     * 配置用户的渠道
     * @param  add @RequiresApi(api = Build.VERSION_CODES.O)
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
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


}