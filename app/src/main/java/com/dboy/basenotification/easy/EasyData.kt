package com.dboy.basenotification.easy

import com.dboy.base.notify.BaseNotificationData

/**
 *   @author Dboy
 *   @date 2020/6/28
 *   Class 描述 :
 */
data class EasyData(
    val icon: Int,
    val channelName: String=EasyChannel.channel,
    val notifyId: Int,
    val title: CharSequence,
    val content: CharSequence
)