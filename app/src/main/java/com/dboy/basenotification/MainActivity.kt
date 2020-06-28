package com.dboy.basenotification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dboy.base.notify.NotificationControl
import com.dboy.base.notify.listener.PendingIntentListener
import com.dboy.basenotification.easy.EasyData
import com.dboy.basenotification.easy.EasyNotify
import com.dboy.basenotification.service.NotifyService

class MainActivity : AppCompatActivity(), PendingIntentListener {

    var easy_notify_id=0
    var  easyNotify:EasyNotify?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showChatActivity(view: View) {
        startActivity(Intent(this, ChatActivity::class.java))
    }

    fun showEasyNotify(view: View) {
        val easyData = EasyData(
            R.mipmap.ic_launcher,
            notifyId = ++easy_notify_id,
            title = "Easy 标题" + easy_notify_id,
            content = "Easy 通知内容"+ easy_notify_id
        )
        if (easyNotify == null) {
            easyNotify = EasyNotify(easyData)
            easyNotify?.show()
            NotificationControl.addPendingIntentListener(this)
        } else {
            easyNotify?.show(easyData)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        NotificationControl.removePendingIntentListener(this)
    }

    override fun onClick(notifyId: Int, viewId: Int) {
        Toast.makeText(this, "notifyId = "+notifyId, Toast.LENGTH_SHORT).show()
    }

}
