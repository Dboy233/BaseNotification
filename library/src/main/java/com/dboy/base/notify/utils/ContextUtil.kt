package com.dboy.base.notify.utils

import android.annotation.SuppressLint
import android.app.Application
import java.lang.reflect.InvocationTargetException

object ContextUtil {
    private var mApplication: Application? = null

    fun getApplication(): Application {
        if (mApplication != null) {
            return mApplication!!
        }
        mApplication = getApplicationByReflect()
        return mApplication!!
    }


    private fun getApplicationByReflect(): Application {
        try {
            @SuppressLint("PrivateApi") val activityThread =
                Class.forName("android.app.ActivityThread")
            val thread =
                activityThread.getMethod("currentActivityThread").invoke(null)
            val app = activityThread.getMethod("getApplication").invoke(thread)
                ?: throw NullPointerException("u should init first")
            return app as Application
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        throw NullPointerException("u should init first")
    }
}