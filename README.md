### 获取此框架 [![](https://jitpack.io/v/Dboy233/BaseNotification.svg)](https://jitpack.io/#Dboy233/BaseNotification)

```groovy
   allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	dependencies {
	        implementation 'com.github.Dboy233:BaseNotification:2.0'
	}
```



### 如何使用此封装框架？

1.自定义你的Notification继承BaseNotification

```java
public abstract class ChatChannelNotify<ChatData> extends BaseNotification<ChatData> {

   
    public ChatChannelNotify(@NotNull ChatData mData) {
        super(mData);
        //使用add*函数添加对应自定义的布局 然后使用get*
        addContentRemoteViews(R.layout.notify_comm_chat_layout);
    }
	/**
	*渠道名字
	*/
    @NotNull
    @Override
    public String getChannelName() {
        return NotificationConfig.CHANNEL_ID_CHAT;
    }
	/**
	 *渠道id
	 */
    @NotNull
    @Override
    public String getChannelId() {
        return NotificationConfig.CHANNEL_ID_CHAT;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void configureChannel(@NotNull NotificationChannel notificationChannel) {
 		//配置渠道信息。如果没有这个渠道会创建这个渠道，如果有了，这个函数是不会被调用的
       notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
    }


    @Override
    public void configureNotify(@NotNull NotificationCompat.Builder mBuilder) {
    		//配置你的通知属性
           mBuilder.setShowWhen(true)
                .setSmallIcon(getData().getSmallIcon())
                .setContentTitle(getData().getContentTitle())
                .setContentText(getData().getContentText())
                .setTicker(getData().getContentTitle())
                .setContentInfo(getData().getContentText())
                .setAutoCancel(true)
                .setSubText(getData().getContentText())
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setGroup("chat");
    }
    
    /**
     *如果你在构造函数使用了add**的函数添加自定义布局 在这里配置你布局展示的数据
     *使用**2结尾的函数可链式调用 点击事件做了单独封装
     */
     @Override
 public void convert(@NotNull BaseRemoteViews mBaseRemoteViews, @NotNull ChatData data) {
     ContentRemote contentRemote = mBaseRemoteViews.getContentRemote();
   if (contentRemote != null) {
       contentRemote
            .setImageViewResource2(R.id.notify_chat_head_img, data.getIcon())
            .setTextViewText2(R.id.notify_chat_title, data.getContentTitle())
            .setTextViewText2(R.id.notify_chat_subtitle, data.getContentText())
            .setOnClickPendingIntent2(getNotificationId(), R.id.notify_chat_layout);
        }
        
  }
	/**
	 *设置你的通知id
	 */
     @Override
    public int getNotificationId() {
        return NotificationConfig.NOTIFICATION_ID;
    }

}
```



2.实例化你的通知并展示

```java
ChatChannelNotify chat=new ChatChannelNotify(new Chat())
chat.show()//显示我们的通知
//更新内容使用 chat.show(new Chat())
//取消通知 chat.cancel()
```

3.设置点击事件

```kotlin
class ChatActivity : AppCompatActivity(), PendingIntentListener {
 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置点击事件
        NotificationControl.addPendingIntentListener(this)
    }
    
    /**
     *通知点击事件回调
     *notifId通知的id
     *ViewId点击的viewId
     */
   override fun onClick(notifyId: Int, viewId: Int) {
        Toast.makeText(this,
            "点击事件： notifyId:" + notifyId + "  ViewId:" + viewId, Toast.LENGTH_LONG)
            .show()
    }
    
     override fun onDestroy() {
        super.onDestroy()
        //销毁点击事件
        NotificationControl.removePendingIntentListener(this)
    }
}
```

