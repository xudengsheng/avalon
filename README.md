Avalon 

高性能，可伸缩的Java Tcp服务器架构

1.Avalon基于Akka构建的服务器核心。天生分布式基因便于横向拓展

2.网络部分使用Netty

3.服务器分为单服务器和多节点分布式服务。Akka使用版本2.40



### Avalon单服务器模式

![输入图片说明](http://git.oschina.net/uploads/images/2015/0924/104510_3014dc5f_19059.jpeg "单节点")


在单节点模式下，和传统的单JVM开发游戏逻辑一样。服务器从客户端收到消息，让后可以根据消息的定义处理对应的逻辑。
我们在这样的模式下开发建议不要建立全局的管理器。注意Actor模式下，每个玩家都是独立的Actor。
处理消息的时候，对于自己的数据更改是线程安全的。如果有逻辑需要更改其他Actor的数据需要是用sendActorMessage方法。

ExampleServer为服务器事例项目。
当前结构完成单服务器部分。集群部分还有待调整。

集群模式
![集群示意图](http://git.oschina.net/uploads/images/2015/0925/145231_9f9ecfdd_19059.jpeg "集群示意图")

在集群模式中，上面的方块是网管服务器。网管服务器只接受来之客户端的网络数据。
并根据当时其当前的游戏服务器的负载情况，将数据转发到对应的逻辑服务器。
当前的模式下网管服务器会根据游戏服务器的网络会话数量，进行分发优先发送给会话少的服务器。
每个会话都会绑定到指定的游戏服务器。

class类的热替换
![输入图片说明](http://git.oschina.net/uploads/images/2015/1124/142440_9b539181_19059.png "在这里输入图片标题")

开发讨论群：7871020