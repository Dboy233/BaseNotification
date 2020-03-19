package com.dboy.basenotification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dboy.base.notify.NotificationControl
import com.dboy.base.notify.listener.PendingIntentListener

class MainActivity : AppCompatActivity(), PendingIntentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NotificationControl.addPendingIntentListener(this)
    }

    fun showNotify(view: View) {
        startService(Intent(this, NotifyService::class.java))
    }

    override fun onClick(notifyId: Int, viewId: Int) {
        Log.d("Notify", "notifyId = " + notifyId + " view ID =" + viewId)
    }

    override fun onDestroy() {
        super.onDestroy()
        NotificationControl.releaseAll()
    }
}
