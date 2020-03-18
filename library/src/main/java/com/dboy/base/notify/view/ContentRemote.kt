package com.dboy.base.notify.view

import android.widget.RemoteViews
import com.dboy.base.notify.utils.ContextUtil

open class ContentRemote(layoutId: Int) : AbRemoteViews(ContextUtil.getApplication().packageName,layoutId) {
}