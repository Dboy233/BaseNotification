package com.dboy.basenotification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dboy.base.notify.NotificationControl
import com.dboy.base.notify.listener.PendingIntentListener
import com.dboy.basenotification.service.NotifyService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showChatActivity(view: View) {
        startActivity(Intent(this, ChatActivity::class.java))
    }

    fun showWarningActivity(view: View) {
        startActivity(Intent(this, WarningActivity::class.java))
    }

}
