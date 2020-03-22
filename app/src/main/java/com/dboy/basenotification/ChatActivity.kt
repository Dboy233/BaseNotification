package com.dboy.basenotification

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dboy.base.notify.NotificationControl
import com.dboy.base.notify.listener.PendingIntentListener
import com.dboy.basenotification.data.ChatData
import com.dboy.basenotification.notify.CommChatNotify
import com.dboy.basenotification.notify.CommPrivateChatNotify

class ChatActivity : AppCompatActivity(), PendingIntentListener {
    lateinit var chatNotify: CommChatNotify
    lateinit var privateChatNotify: CommPrivateChatNotify
    var chatSize = 0
    var privateSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_chat)
        chatNotify = CommChatNotify(
            ChatData(1, R.drawable.zha_nv_1, "小红来信", "今晚吃什么午饭啊？")
        )

        privateChatNotify = CommPrivateChatNotify(
            ChatData(11, R.drawable.zha_nv_2, "芳芳来信", "晚上来找我玩啊？")
        )
        NotificationControl.addPendingIntentListener(this)
    }

    fun showChat1(view: View) {
        if (chatSize == 0) {
            chatNotify.show()
            chatSize++
        } else {
            val chatData = ChatData(2, R.drawable.zha_nv_1, "小红来信", "怎么不回复我了啊？")
            chatNotify.show(chatData)
        }
    }

    fun showChat2(view: View) {
        if (privateSize == 0) {
            privateChatNotify.show()
            privateSize++
        } else {
            val chatData = ChatData(12, R.drawable.zha_nv_2, "芳芳来信", "你是不是外面有人了！你给我等着！")
            privateChatNotify.show(chatData)
        }
    }

    override fun onClick(notifyId: Int, viewId: Int) {
        Toast.makeText(this,
            "点击事件： notifyId:" + notifyId + "  ViewId:" + viewId, Toast.LENGTH_LONG)
            .show()
    }


    override fun onDestroy() {
        super.onDestroy()
        NotificationControl.removePendingIntentListener(this)
    }

}
