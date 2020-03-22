# Notification通知栏的封装模式



## 前言

##### 相信很多开发者和我一样，刚刚进入工作的时候，比较棘手的就是通知栏了。
#####  比如：

#####  		1.app中要有一个常驻通知的快捷菜单。

#####  		2.计划任务通知。

#####  		3.聊天消息通知。

#####  还要适配低版本和高版本，对于新手来说会手忙脚乱。

##### 我把我的经验分享给各位，大佬看一下就可以了，新手还是要细细阅读。





## 适配

##### 主流都是从4.4适配到9.0或者10。。那么必须要用到

```java
public final class NotificationChannel
```

```java
public class NotificationCompat.Builder
```

##### 这两个类。

##### [NotificationChannel]()这个是在API26之上必须要有的，通过它创建一个通知渠道。

```java
public NotificationChannel(String channelId, CharSequence name,
                           @Importance int importance) 
```

##### 这个渠道的意思类似于一个家，隶属于这个家的人就是通知。

##### 每一个通知都要有一个归属地、发源地，它就是渠道。

##### 这个是适配API26以上的关键，没有渠道，通知是不会被展示出来。



##### [NotificationCompat.Builder]() 是每个通知的具体实现，通过一个构建者模式创建一个通知。

```java
mBuilder = NotificationCompat.Builder(mContext, channelId);
```



##### Channel渠道定义了旗下所有通知的通性。

##### 而通知标明了自己在渠道的约束下的个性。



##### 这是他们之间的关系；



## 创建一个渠道

##### 创建一个渠道前我们可以先检索一下有没有这个渠道

```java
NotificationManagerCompat manager = NotificationManagerCompat.from(mContext);

NotificationChannel channel = manager.getNotificationChannel("channel_chat");
    
```

##### 我们通过[NotificationManagerCompat.from(mContext);]()获取一个[NotificationManagerCompat]()通过它我们检索系统中有没有名字为"[channel_chat]()"的渠道；

##### 如果没有这个渠道返回null；如果有，以前创建过就会返回这个渠道实例；

###### 注：每个app都有各自的渠道，名字相同也互不干扰。当然你也不知道被人的渠道id；

##### 由此我们在创建渠道前先判断有没有这个，再做渠道的设置；

```kotlin
//获取用户的渠道id
var notificationChannel =
    mNotificationManagerCompat.getNotificationChannel("channel_chat")

if (notificationChannel == null) {

    //生成渠道实例
    notificationChannel = NotificationChannel(
        "channel_chat",//id为"channel_chat"
        "聊天推送",//名字  用户可在app设置中可见
        NotificationManager.IMPORTANCE_HIGH //通知的级别 高
    )
	notificationChannel.apply{
         setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC)//设置锁屏可见
         ...
    }
       	//创建渠道	  
    mNotificationManagerCompat.createNotificationChannel(notificationChannel)
}
```

##### 这样我们就有了一个可发布通知的渠道；



## 通知



##### 获取一个通知构建器；

```kotlin
mBuilder = NotificationCompat.Builder(mContext, "channel_chat")
```

##### 通过渠道id我们拿到一个构建器；

##### 通过构建器我们可以设置一个通知的各种属性；

```kotlin
mBuilder.setSmallIcon(R.mipmap.ic_launcher)//设置图标 没它也不能显示通知
		.setContentTitle("小明来信")//标题
        .setContentText("吃饭了么？")//内容
        .setShowWhen(true)//显示通知时间
        .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)//设置通知可见级别
        .setPriority(NotificationCompat.PRIORITY_MAX)//设置通知优先级 高
        .setGroup("friend")//设置组，如果有必要排序啥的话 在4.4上设置组名的话有时候会不显示通知，不								//知道是不是bug
```

##### 设置了通知的内容和属性，使用[NotificationManagerCompat]()发布通知；

```kotlin
mNotificationManagerCompat.notify(notifyId, mBuilder.build())
```

##### 每个通知必须要有一个唯一的id ；

##### 这样一个通知就显示了；

##### 在这里我就不展示效果了；



## 分析

##### 由此我们可以得出结论。渠道必须，通知依附于一个渠道发布通知；

##### 那我们该如何适配各种开发需求呢；

##### 因为同一类通知有着同样的属性。不同的id。不同的内容；

##### 我们也可以通过类似工厂的模式，创建一个唯一的渠道工厂。让通知实体继承这个渠道工厂；

##### 然后旗下的通知只需要关注title和contentText的内容即可；

##### 那么我们开始封装一个渠道工厂；



## 封装

##### 我们先封装一个Base类。

##### 定义每个工厂需要做什么事；写一个接口；

```kotlin
interface IBaseNotify<T> {
    /**
     * 初始化渠道
     */
    fun initChannel()

    /**
     * 初始化通知构建器
     */
    fun initBuilder()

    /**
     * 显示
     */
    fun show()

    /**
     * 显示 并配置数据
     */
    fun show(mData: T)

    /**
     * 显示为前台通知 需要权限
     * android.permission.FOREGROUND_SERVICE
     */
    fun show(service: Service)

    /**
     * 显示为前台通知 并配置数据 需要权限
     * android.permission.FOREGROUND_SERVICE
     */
    fun show(service: Service, mData: T)

    /**
     * 取消这个通知
     */
    fun cancel()

}
```



##### 再定义一个Base类实现这个接口，完成我们的初步封装；

```kotlin
abstract class BaseNotification<T : Any>(open var data: T) : IBaseNotify<T> {

    /**
     * Base上下文 通过反射获取全局application的context 具体实现看最后的源码地址
     */
    private val mContext: Context = ContextUtil.getApplication()
    /**
     * Manager 我将它放到了别的类实例化方便其他地方调用
     */
    private val mNotificationManagerCompat = NotificationControl.mNotificationManagerCompat
    /**
     *  通知栏构造器
     */
    private lateinit var mBuilder: NotificationCompat.Builder
    /**
     * 自定义视图管理类
     */
    private val mBaseRemoteViews: BaseRemoteViews = BaseRemoteViews()


    init {
        //初始化渠道
        initChannel()
        //初始化通知构建器
        initBuilder()
    }

    /**
     * 初始化通知渠道 让base渠道类继承并实现。同一个渠道不会二次执行配置
     */

    final override fun initChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //获取用户的渠道id
            var notificationChannel =
                mNotificationManagerCompat.getNotificationChannel(getChannelIdStr())

            if (notificationChannel == null) {

                //为用户配置默认渠道 让用户配置 并创建 如果渠道已经创建了，就不做任何操作 不重建渠道
                notificationChannel = NotificationChannel(
                    getChannelIdStr(),//通过抽象方法拿取渠道id
                    getChannelName(),//通过抽象方法拿取渠道名字
                    NotificationManager.IMPORTANCE_DEFAULT//默认设置渠道的优先级
                )
				//抽象方法，让继承Base类的渠道类配置属性
                configureChannel(notificationChannel)
				//创建渠道
                mNotificationManagerCompat.createNotificationChannel(notificationChannel)
            }
        }
    }

    /**
     * 初始化通知
     */
    final override fun initBuilder() {
        mBuilder = NotificationCompat.Builder(mContext, getChannelIdStr())
        configureNotify(mBuilder)
    }

    /**
     * 获取srt类型的ChannelId 形式为 {你的app报名:getChannelId()}
     */
    private fun getChannelIdStr():String {
        return mContext.packageName +":"+ getChannelId()
    }

    /**
     * 添加大图
     */
    protected fun addBigRemoteViews(layoutId: Int) {
        mBaseRemoteViews.bigRemote = BigRemote(layoutId)
    }

    /**
     * 添加小图
     */
    protected fun addContentRemoteViews(layoutId: Int) {
        mBaseRemoteViews.contentRemote = ContentRemote(layoutId)
    }

    /**
     * 添加TickerView
     */
    protected fun addTickerRemoteViews(tickerText: String, layoutId: Int) {
        mBaseRemoteViews.tickerRemote = TickerRemote(tickerText, layoutId)
    }

    /**
     * 添加自定义视图
     */
    protected fun addCustomContentRemoteView(layoutId: Int) {
        mBaseRemoteViews.customContentRemote
    }

    /**
     * 显示
     */
    override fun show() {
        show(data)
    }

    /**
     * 显示 并配置数据
     */
    override fun show(data: T) {
        this.data = data
        convert(mBaseRemoteViews, data)
        configureRemote()
        mNotificationManagerCompat.notify(getNotificationId(), mBuilder.build())
    }

    /**
     * 显示为前台通知
     * android.permission.FOREGROUND_SERVICE
     */
    override fun show(service: Service) {
        show(service, data)
    }

    /**
     * 显示为前台通知 并配置数据
     * android.permission.FOREGROUND_SERVICE
     */
    override fun show(service: Service, data: T) {
        this.data = data
        convert(mBaseRemoteViews, data)
        configureRemote()
        service.startForeground(getNotificationId(), mBuilder.build())
    }


    /**
     * 取消这个通知
     */
    override fun cancel() {
        mNotificationManagerCompat.cancel(getNotificationId())
    }

    /**
     * 配置视图
     */
    private fun configureRemote() {
        mBaseRemoteViews.customContentRemote?.let {
            mBuilder.setCustomContentView(it)
        }
        mBaseRemoteViews.contentRemote?.let {
            mBuilder.setContent(it)
        }
        mBaseRemoteViews.bigRemote?.let {
            mBuilder.setCustomBigContentView(it)
        }
        mBaseRemoteViews.tickerRemote?.let { tickerRemote ->
            tickerRemote.tickerText?.let { tickerText ->
                mBuilder.setTicker(tickerText, tickerRemote)
            }
        }
    }

    /**
     * 配置数据
     */
    abstract fun convert(mBaseRemoteViews: BaseRemoteViews, data: T)

    /**
     * 配置用户的渠道
     * @param  add @RequiresApi(api = Build.VERSION_CODES.O)
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    abstract fun configureChannel(notificationChannel: NotificationChannel)

    /**
     * 配置通知信息
     */
    abstract fun configureNotify(mBuilder: NotificationCompat.Builder)

    /**
     * 获取渠道名字 id已经默认为app的报名了
     */
    abstract fun getChannelName(): String

    /**
     * 获取通知id
     */
    abstract fun getNotificationId(): Int

    /**
     * 获取渠道id  最终类型为 {你的app报名:0} 的字符串
     */
    abstract fun getChannelId(): Int
}
```

##### 对于这个基类的封装也是非常的简单明了；

##### 它实现了[NotificationChannel]()和[NotificationCompat.Builder]()的初始化；

##### 通过抽象方法让子类返回初始化所需要的channelId、channelName和notifyId；

##### 在通过[configureChannel(channel : NotificationChannel)]()和[configureBuilder(builder : NotificationCompat.Builder()]() 让子类取实现并获取channel和builder实例取配置自己所需要的属性；让最上层实现通知的 [初始化、发布、取消]() 等这样基础的通用功能；

##### 再让第一个子类也就是继承[BaseNotification](https://github.com/Dboy233/BaseNotification)的类我定义为channel类，比如说[ChatChannelNotify]()让它如设置渠道的属性，和旗下同渠道的通知的通用属性。

###### 注：我的代码中用到了大量的kotlin和java混合开发的。不过我的封装都是kotlin，实现都是java，这也是为了照顾java用户

```java
/**
 * 业务基类一底层主要就是配置渠道 设置旗下通知的共同属性
 * 这里我定义了一个BaseNotificationData里面主要就是对数据的统一可以不用，仅供参考吧。
 * @param <T> 
 */
public abstract class ChatChannelNotify<T extends BaseNotificationData> extends BaseNotification<T> {

    /**
     * @param mData 适配你的数据 并在super之后添加你的Remote的layout id
     */
    public ChatChannelNotify(@NotNull T mData) {
        super(mData);
    }
	//设置渠道名字 我把它写在了配置类中
    @NotNull
    @Override
    public String getChannelName() {
        return NotificationConfig.CHANNEL_ID_CHAT;
    }
	//设置渠道ID 我把它写在了配置类中
    @NotNull
    @Override
    public String getChannelId() {
        return NotificationConfig.CHANNEL_ID_CHAT;
    }
	//设置渠道的属性
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void configureChannel(@NotNull NotificationChannel notificationChannel) {
        notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
    }

	//设置渠道下通知的共同属性
    @Override
    public void configureNotify(@NotNull NotificationCompat.Builder mBuilder) {
        //设置通知的共同属性
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
	//有几个方法channel类无需实现，让具体的通知类去实现完成
    //abstract fun convert(mBaseRemoteViews: BaseRemoteViews, data: T)
    //abstract fun getChannelId(): Int
    //
	//add***RemoteViews(layoutId:Int) 通知类调用
}
```

##### 通过上面的代码，我们就完成了Channel的封装，和旗下同渠道通知的配置。使得书写一个通知变得方便简洁。

##### 对业务可扩展和新人添加业务变得简单起来。



##### *大家肯定发现了，我对[RemoteViews]()和它的数据进行了封装，这其实是我封装的关键地方；*



### 我们来讲一下[RemoteViews]()

##### 它是自定义通知展示内容的关键，有很多通知都是自定义布局视图的。M信、Mq、M宝等等，很少有使用原生通知样式的。不信你看看。除了海外和谷歌自家app，国内厂商无一例外。

##### 通过设置[NotificationCompat.Builder]()属性来设置自定义layout布局，不过传入的对象却是个RemoteViews，新手肯定一脸懵逼了。

```java
mBuilder.setCustomContentView(RemoteViews)

mBuilder.setContent(RemoteViews)

mBuilder.setCustomBigContentView(RemoteViews)

mBuilder.setTicker(tickerText, RemoteViews)
```

##### 而点击事件和内容的设置，却又很繁琐。于是我对[RemoteViews]()也进行了封装改造，对其点击事件和属性的设置进行了调整优化；

##### 通知栏中的点击时间要通过[PendingIntent]()发送广播或者sendIntent实现页面跳转点击回调；

##### 我是通过一个全局的广播监听器来监听所有通知的事件回调。所以我继承了[RemoteViews]()添加了一个点击事件方法

```kotlin
open class AbRemoteViews(packageName: String?, layoutId: Int) :
    RemoteViews(packageName, layoutId) {
fun setOnClickPendingIntent2(notifyId: Int, viewId: Int): AbRemoteViews {
    val intent =
        Intent(ACTION_NOTIFY_CLICK).setPackage(ContextUtil.getApplication().packageName)
   
    intent.putExtra(ACTION_NOTIFY_CLICK_NOTIFY_ID, notifyId)//参数1通知的id
    
    intent.putExtra(ACTION_NOTIFY_CLICK_VIEW_ID, viewId)//参数2 点击的view id
  
    val pendingIntent = PendingIntent.getBroadcast(
        ContextUtil.getApplication(),
        viewId,//requestCode设置为view的Id表秒它的唯一性
        intent,//设置intent
        PendingIntent.FLAG_UPDATE_CURRENT//设置PendingIntent的flag。
    )
   //最后调用RemoteViews原本的方法设置点击事件
    super.setOnClickPendingIntent(viewId, pendingIntent)
    return this
}
}
```

##### 对这个点击事件的封装又能解决一大批问题，组件化开发模式下，用广播传递点击事件，解决无法引用目标活动类的问题。而且我不只是封装了这一个方法，我将所有的方法都实现了，使用了构建者模式。让开发者使用的时候直接链式调用方便快捷。使用以2结尾的方法，即可实现链式调用，原方法也保留了使用。大家可以去我的源码中查看。



##### 然后分别创建几个子类

```kotlin
open class BigRemote(layoutId: Int) : AbRemoteViews(ContextUtil.getApplication().packageName,layoutId) 


open class ContentRemote(layoutId: Int) : AbRemoteViews(ContextUtil.getApplication().packageName,layoutId)

open class CustomRemote(layoutId: Int) : AbRemoteViews(ContextUtil.getApplication().packageName,layoutId)

open class TickerRemote(var  tickerText: CharSequence?="", layoutId: Int) :
    AbRemoteViews(ContextUtil.getApplication().packageName, layoutId)
```

##### 来向开发者标明我所自定义的[RemoteViews]()的具体位置类别；

##### 这几个类分别对应了这几个方法。让[BaseNotification](https://github.com/Dboy233/BaseNotification)根据类别去完成set方法。

```java
mBuilder.setCustomContentView(RemoteViews)

mBuilder.setContent(RemoteViews)

mBuilder.setCustomBigContentView(RemoteViews)

mBuilder.setTicker(tickerText, RemoteViews)
```

##### 这是[BaseNotification](https://github.com/Dboy233/BaseNotification)中的实现方法

```kotlin

    /**
     * 配置视图
     */
    private fun configureRemote() {
        mBaseRemoteViews.customContentRemote?.let {
            mBuilder.setCustomContentView(it)
        }
        mBaseRemoteViews.contentRemote?.let {
            mBuilder.setContent(it)
        }
        mBaseRemoteViews.bigRemote?.let {
            mBuilder.setCustomBigContentView(it)
        }
        mBaseRemoteViews.tickerRemote?.let { tickerRemote ->
            tickerRemote.tickerText?.let { tickerText ->
                mBuilder.setTicker(tickerText, tickerRemote)
            }
        }
    }
```



##### 再使用一个类将他们统一使用；让具体的通知类去设置他们

```kotlin
data class BaseRemoteViews(

     var bigRemote: BigRemote?=null,

     var contentRemote: ContentRemote?=null,

     var customContentRemote: CustomRemote?=null,

     var tickerRemote: TickerRemote?=null

)
//===============================================================
// 下面是BaseNotification的方法，让通知子类调用，赋值给他们具体的类型实现
	/**
     * 添加大图
     */
    protected fun addBigRemoteViews(layoutId: Int) {
        mBaseRemoteViews.bigRemote = BigRemote(layoutId)
    }

    /**
     * 添加小图
     */
    protected fun addContentRemoteViews(layoutId: Int) {
        mBaseRemoteViews.contentRemote = ContentRemote(layoutId)
    }

    /**
     * 添加TickerView
     */
    protected fun addTickerRemoteViews(tickerText: String, layoutId: Int) {
        mBaseRemoteViews.tickerRemote = TickerRemote(tickerText, layoutId)
    }

    /**
     * 添加自定义视图
     */
    protected fun addCustomContentRemoteView(layoutId: Int) {
        mBaseRemoteViews.customContentRemote
    }
//================================================================

//下面是具体通知调用位置
/**
 * 普通聊天 java 先继承通知渠道类 得到渠道信息。在配置自己的数据
 */
public class CommChatNotify extends ChatChannelNotify<ChatData> {

    /**
     * @param mData 适配你的数据 并在super之后添加你的Remote的layout id
     */
    public CommChatNotify(@NotNull ChatData mData) {
        super(mData);
        addContentRemoteViews(R.layout.notify_comm_chat_layout);
    }
    //。。。。。。未展示的其他方法
}

```



##### 最后看一下点击事件所触发的广播类

```kotlin
class BaseNotifyBroadcast : BroadcastReceiver {

    /**
     * 通知栏点击事件分发监听器列表
     */
    private val arrayList = mutableListOf<PendingIntentListener>()

    companion object {
        val INSTANCE: BaseNotifyBroadcast by lazy {
            BaseNotifyBroadcast()
        }
    }

    constructor() {
        register()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val action = intent.action!!
            if (action == ACTION_NOTIFY_CLICK) {
                //点击事件viewid
                val viewId = intent.getIntExtra(ACTION_NOTIFY_CLICK_VIEW_ID, 0)
                //点击事件来自哪个通知id
                val notifyId = intent.getIntExtra(ACTION_NOTIFY_CLICK_NOTIFY_ID, -1)
                arrayList.map {
                    it.onClick(notifyId, viewId)
                }
            }
        }
    }

    /**
     * 注册广播
     */
    fun register() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_NOTIFY_CLICK)
        ContextUtil.getApplication().registerReceiver(this, intentFilter)
    }

    /**
     * 移除广播
     */
    fun onDestroy() {
        arrayList.clear()
        ContextUtil.getApplication().unregisterReceiver(this)
    }

    /**
     * 添加监听器
     */
    fun addPendingIntentListener(pendingIntentListener: PendingIntentListener) {
        if (!arrayList.contains(pendingIntentListener)) {
            arrayList.add(pendingIntentListener)
        }
    }

    /**
     * 移除点击事件监听器
     */
    fun removePendingIntentListener(pendingIntentListener: PendingIntentListener) {
        if (arrayList.contains(pendingIntentListener)) {
            arrayList.remove(pendingIntentListener)
        }
    }

}
```

##### 这样我们一个整体的封装结构就完成了。具体代码，请大家去看我[GitHub](https://github.com/Dboy233/BaseNotification)的源码



## 创建我们的工厂

接下来我把具体的流程代码展示出来

```java
public abstract class ChatChannelNotify<T> extends BaseNotification<T> {

    /**
     * @param mData 适配你的数据 并在super之后添加你的Remote的layout id
     */
    public ChatChannelNotify(@NotNull T mData) {
        super(mData);
    }

    @NotNull
    @Override
    public String getChannelName() {
        return "聊天通知";//方便大家理解，我写到了这里，建议大家统一写道一个静态类，方便管理
    }
	
	@Override
    public String getChannelId() {
        return "chat"; //方便大家理解，我写到了这里，建议大家统一写道一个静态类，方便管理
    }
	
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void configureChannel(@NotNull NotificationChannel notificationChannel) {
        notificationChannel.setSound(null, null);
       notificationChannel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
    }

    @Override
    public void configureNotify(@NotNull NotificationCompat.Builder mBuilder) {
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setShowWhen(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setGroup("chat");
    }
}

```

##### 这就是我们的工厂channel， 所有与消息相关的通知继承这个抽象的渠道类

##### 并实现未实现的方法即可方便的配置自己的通知

```java
public class CommonChatNotify extends ChatChannelNotify<Chat> {

    /**
     * @param mData 适配你的数据 并在super之后添加你的Remote的layout id
     */
    public CommonNotify(@NotNull String mData) {
        super(mData);
        addContentRemoteViews(R.layout.notify_chat_layout);//设置我们自定义的布局
    }

    @Override
    public void convert(@NotNull BaseRemoteViews mBaseRemoteViews, @NotNull Chat data) {
        //设置我们自定义布局的数据 对应自己上面add的RemoteViews类型
        ContentRemote contentRemote = mBaseRemoteViews.getContentRemote();
        if (contentRemote != null) {
            contentRemote.setTextViewText2(R.id.contentText, data.getContext())
              	   .setTextViewText2(R.id.contentTitle, data.getTitle())
                    .setOnClickPendingIntent2(getNotificationId(), R.id.contentBtn);
        }
    }
	//即使是渠道类设置了我们这里也可以进行轻微的修改 重写这个方法。不要把super给删掉！
    @Override
    public void configureNotify(@NotNull NotificationCompat.Builder mBuilder) {
        super.configureNotify(mBuilder);
        mBuilder.setSmallIcon(getData().getIcon());
    }
	//通知的id 这个id可以从data数据中获取 getData().getId()
    //因为通知都是活的。channel是固定的。所以应该是这样。
    //但是如果你的通知只有那么几个类型。可以写死在常量类中
    @Override
    public int getNotificationId() {
        return 11;
    }
}
```

##### 最后我们拿到通知类

```java
CommonChatNotify chat=new CommonChatNotify(new Chat())
chat.show()//显示我们的通知
//更新内容使用 chat.show(new Chat())
//取消通知 chat.cancel()
```

##### 由此我们可以有[群聊天通知，私密聊天通知，好友聊天]()通知。

##### 统统继承[ChatChannelNotify]()设置不同的布局类型，配置不同的聊天数据。

##### 完美。



##### 我将代码已开源，大家可直接使用[BaseNotification](https://github.com/Dboy233/BaseNotification)快速封装自己的业务逻辑

https://github.com/Dboy233/BaseNotification

```css
   allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
	        implementation 'com.github.Dboy233:BaseNotification:1.4'
	}
```
