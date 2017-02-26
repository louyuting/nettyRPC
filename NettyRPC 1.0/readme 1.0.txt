## NettyRPC 1.0 中文简介：
**NettyRPC是基于Netty构建的RPC系统，消息网络传输支持目前主流的编码解码器**

* NettyRPC基于Java语言进行编写，网络通讯依赖Netty。

* RPC服务端采用线程池对RPC调用进行异步回调处理。

* 服务定义、实现，通过Spring容器进行加载、卸载。

* 消息网络传输除了JDK原生的对象序列化方式，还支持目前主流的编码解码器：kryo、hessian。

* Netty网络模型采用主从Reactor线程模型，提升RPC服务器并行吞吐性能。

* 多线程模型采用guava线程库进行封装。