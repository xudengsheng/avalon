Avalon protobuf网络通信协议生成器

protobuf 放置protobuf文件

src-gen 生成的java版本protobuf 文件所在位置

生成Java
MessageKey 是对应的类和消息的key
key.properties 本地保存的protobuf消息对应的键值本地文件

CS开头代表客户端发给服务器的协议 
SC开头代表服务器发给客户端的协议 

建议：客户端发给服务器的协议以CS开头，例如CS_LoginRequest
     服务器发给客户端SC_LoginInfo

buildProtobuf.sh 是在linux下生成protobuf的脚本

当前生成器所使用的protobuf为2.5 这里可以下载[protobuf2.5下载](https://github.com/google/protobuf/tree/v2.5.0)

linux 安装protobuf


```
  $ ./configure
  $ make
  $ make check
  $ make install
```