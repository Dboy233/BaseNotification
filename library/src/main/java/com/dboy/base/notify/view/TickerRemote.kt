package com.dboy.base.notify.view

import com.dboy.base.notify.utils.ContextUtil

open class TickerRemote(var  tickerText: CharSequence?="", layoutId: Int) :
    AbRemoteViews(ContextUtil.getApplication().packageName, layoutId) {


}