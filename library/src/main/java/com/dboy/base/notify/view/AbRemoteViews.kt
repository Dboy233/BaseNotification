package com.dboy.base.notify.view

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import com.dboy.base.notify.BroadcastConstant.ACTION_NOTIFY_CLICK
import com.dboy.base.notify.BroadcastConstant.ACTION_NOTIFY_CLICK_NOTIFY_ID
import com.dboy.base.notify.BroadcastConstant.ACTION_NOTIFY_CLICK_VIEW_ID
import com.dboy.base.notify.utils.ContextUtil

open class AbRemoteViews(packageName: String?, layoutId: Int) :
    RemoteViews(packageName, layoutId) {

    fun setOnClickPendingIntent2(notifyId: Int, viewId: Int): AbRemoteViews {
        val intent: Intent =
            Intent(ACTION_NOTIFY_CLICK).setPackage(ContextUtil.getApplication().packageName)
        intent.putExtra(ACTION_NOTIFY_CLICK_VIEW_ID, viewId)
        intent.putExtra(ACTION_NOTIFY_CLICK_NOTIFY_ID, notifyId)
        val pendingIntent = PendingIntent.getBroadcast(
            ContextUtil.getApplication(),
            notifyId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        super.setOnClickPendingIntent(viewId, pendingIntent)
        return this
    }

    fun setOnClickPendingIntent2(notifyId: Int, viewId: Int, flags: Int): AbRemoteViews {
        val intent: Intent =
            Intent(ACTION_NOTIFY_CLICK).setPackage(ContextUtil.getApplication().packageName)
        intent.putExtra(ACTION_NOTIFY_CLICK_VIEW_ID, viewId)
        intent.putExtra(ACTION_NOTIFY_CLICK_NOTIFY_ID, notifyId)
        val pendingIntent = PendingIntent.getBroadcast(
            ContextUtil.getApplication(),
            notifyId,
            intent,
            flags
        )
        super.setOnClickPendingIntent(viewId, pendingIntent)
        return this
    }


    open fun addView2(viewId: Int, nestedView: RemoteViews?): AbRemoteViews {
        super.addView(viewId, nestedView)
        return this
    }


    open fun setTextViewTextSize2(viewId: Int, units: Int, size: Float): AbRemoteViews {
        super.setTextViewTextSize(viewId, units, size)
        return this
    }

    open fun removeAllViews2(viewId: Int): AbRemoteViews {
        super.removeAllViews(viewId)
        return this
    }


    open fun setPendingIntentTemplate2(
        viewId: Int,
        pendingIntentTemplate: PendingIntent?
    ): AbRemoteViews {
        super.setPendingIntentTemplate(viewId, pendingIntentTemplate)
        return this
    }


    open fun setEmptyView2(viewId: Int, emptyViewId: Int): AbRemoteViews {
        super.setEmptyView(viewId, emptyViewId)
        return this
    }


    open fun writeToParcel2(dest: Parcel?, flags: Int): AbRemoteViews {
        super.writeToParcel(dest, flags)
        return this
    }


    open fun setLong2(viewId: Int, methodName: String?, value: Long): AbRemoteViews {
        super.setLong(viewId, methodName, value)
        return this
    }


    open fun setCharSequence2(
        viewId: Int,
        methodName: String?,
        value: CharSequence?
    ): AbRemoteViews {
        super.setCharSequence(viewId, methodName, value)
        return this
    }

    open fun setViewVisibility2(viewId: Int, visibility: Int): AbRemoteViews {
        super.setViewVisibility(viewId, visibility)
        return this
    }

    open fun setTextColor2(viewId: Int, color: Int): AbRemoteViews {
        super.setTextColor(viewId, color)
        return this
    }

    open fun setFloat2(viewId: Int, methodName: String?, value: Float): AbRemoteViews {
        super.setFloat(viewId, methodName, value)
        return this
    }

    open fun setChronometerCountDown2(viewId: Int, isCountDown: Boolean): AbRemoteViews {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.setChronometerCountDown(viewId, isCountDown)
        }
        return this
    }

    open fun setLabelFor2(viewId: Int, labeledId: Int): AbRemoteViews {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            super.setLabelFor(viewId, labeledId)
        }
        return this
    }

    open fun setAccessibilityTraversalAfter2(viewId: Int, nextId: Int): AbRemoteViews {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            super.setAccessibilityTraversalAfter(viewId, nextId)
        }
        return this
    }

    open fun setOnClickResponse2(viewId: Int, response: RemoteResponse): AbRemoteViews {
        super.setOnClickResponse(viewId, response)
        return this
    }

    open fun apply2(context: Context?, parent: ViewGroup?): View {
        return super.apply(context, parent)
    }

    open fun onLoadClass2(clazz: Class<*>?): Boolean {
        return super.onLoadClass(clazz)
    }

    open fun setInt2(viewId: Int, methodName: String?, value: Int): AbRemoteViews {
        super.setInt(viewId, methodName, value)
        return this
    }

    open fun setBundle2(viewId: Int, methodName: String?, value: Bundle?): AbRemoteViews {
        super.setBundle(viewId, methodName, value)
        return this
    }

    open fun setLightBackgroundLayoutId2(layoutId: Int): AbRemoteViews {
        super.setLightBackgroundLayoutId(layoutId)
        return this
    }

    open fun setDouble2(viewId: Int, methodName: String?, value: Double): AbRemoteViews {
        super.setDouble(viewId, methodName, value)
        return this
    }

    open fun describeContents2(): Int {
        return super.describeContents()
    }

    open fun setOnClickFillInIntent2(viewId: Int, fillInIntent: Intent?): AbRemoteViews {
        super.setOnClickFillInIntent(viewId, fillInIntent)
        return this
    }

    open fun setDisplayedChild2(viewId: Int, childIndex: Int): AbRemoteViews {
        super.setDisplayedChild(viewId, childIndex)
        return this
    }

    open fun showPrevious2(viewId: Int): AbRemoteViews {
        super.showPrevious(viewId)
        return this
    }

    open fun reapply2(context: Context?, v: View?): AbRemoteViews {
        super.reapply(context, v)
        return this
    }

    open fun setUri2(viewId: Int, methodName: String?, value: Uri?): AbRemoteViews {
        super.setUri(viewId, methodName, value)
        return this
    }

    open fun setIcon2(viewId: Int, methodName: String?, value: Icon?): AbRemoteViews {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.setIcon(viewId, methodName, value)
        }
        return this
    }

    open fun setImageViewResource2(viewId: Int, srcId: Int): AbRemoteViews {
        super.setImageViewResource(viewId, srcId)
        return this
    }

    open fun setRelativeScrollPosition2(viewId: Int, offset: Int): AbRemoteViews {
        super.setRelativeScrollPosition(viewId, offset)
        return this
    }

    open fun setBitmap2(viewId: Int, methodName: String?, value: Bitmap?): AbRemoteViews {
        super.setBitmap(viewId, methodName, value)
        return this
    }

    open fun clone2(): RemoteViews {
        return super.clone()
    }

    open fun setIntent2(viewId: Int, methodName: String?, value: Intent?): AbRemoteViews {
        super.setIntent(viewId, methodName, value)
        return this
    }

    open fun setString2(viewId: Int, methodName: String?, value: String?): AbRemoteViews {
        super.setString(viewId, methodName, value)
        return this
    }

    open fun setImageViewBitmap2(viewId: Int, bitmap: Bitmap?): AbRemoteViews {
        super.setImageViewBitmap(viewId, bitmap)
        return this
    }

    open fun setTextViewCompoundDrawables2(
        viewId: Int,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ): AbRemoteViews {
        super.setTextViewCompoundDrawables(viewId, left, top, right, bottom)
        return this
    }

    open fun setRemoteAdapter2(appWidgetId: Int, viewId: Int, intent: Intent?): AbRemoteViews {
        super.setRemoteAdapter(appWidgetId, viewId, intent)
        return this
    }

    open fun setRemoteAdapter2(viewId: Int, intent: Intent?): AbRemoteViews {
        super.setRemoteAdapter(viewId, intent)
        return this
    }

    open fun setAccessibilityTraversalBefore2(viewId: Int, nextId: Int): AbRemoteViews {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            super.setAccessibilityTraversalBefore(viewId, nextId)
        }
        return this
    }

    open fun setProgressBar2(
        viewId: Int,
        max: Int,
        progress: Int,
        indeterminate: Boolean
    ): AbRemoteViews {
        super.setProgressBar(viewId, max, progress, indeterminate)
        return this
    }

    open fun setImageViewIcon2(viewId: Int, icon: Icon?): AbRemoteViews {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.setImageViewIcon(viewId, icon)
        }
        return this
    }

    open fun showNext2(viewId: Int): AbRemoteViews {
        super.showNext(viewId)
        return this

    }

    open fun setChar2(viewId: Int, methodName: String?, value: Char): AbRemoteViews {
        super.setChar(viewId, methodName, value)
        return this
    }

    open fun setContentDescription2(viewId: Int, contentDescription: CharSequence?): AbRemoteViews {
        super.setContentDescription(viewId, contentDescription)
        return this
    }

    open fun getPackage2(): String {
        return super.getPackage()
    }

    open fun setTextViewText2(viewId: Int, text: CharSequence?): AbRemoteViews {
        super.setTextViewText(viewId, text)
        return this
    }

    open fun setChronometer2(
        viewId: Int,
        base: Long,
        format: String?,
        started: Boolean
    ): AbRemoteViews {
        super.setChronometer(viewId, base, format, started)
        return this
    }

    open fun setShort2(viewId: Int, methodName: String?, value: Short): AbRemoteViews {
        super.setShort(viewId, methodName, value)
        return this
    }

    open fun setViewPadding2(
        viewId: Int,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ): AbRemoteViews {
        super.setViewPadding(viewId, left, top, right, bottom)
        return this
    }

    open fun setTextViewCompoundDrawablesRelative2(
        viewId: Int,
        start: Int,
        top: Int,
        end: Int,
        bottom: Int
    ): AbRemoteViews {
        super.setTextViewCompoundDrawablesRelative(viewId, start, top, end, bottom)
        return this
    }

    open fun setBoolean2(viewId: Int, methodName: String?, value: Boolean): AbRemoteViews {
        super.setBoolean(viewId, methodName, value)
        return this
    }

    open fun setImageViewUri2(viewId: Int, uri: Uri?): AbRemoteViews {
        super.setImageViewUri(viewId, uri)
        return this
    }

    open fun setScrollPosition2(viewId: Int, position: Int): AbRemoteViews {
        super.setScrollPosition(viewId, position)
        return this
    }

    open fun setByte2(viewId: Int, methodName: String?, value: Byte): AbRemoteViews {
        super.setByte(viewId, methodName, value)
        return this
    }
}