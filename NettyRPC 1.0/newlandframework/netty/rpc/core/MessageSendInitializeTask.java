package newlandframework.netty.rpc.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import newlandframework.netty.rpc.serialize.support.RpcSerializeProtocol;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * Rpc客户端线程任务处理
 * 本质是就是一个 Callable
 */
public class MessageSendInitializeTask implements Callable<Boolean> {

    private EventLoopGroup eventLoopGroup = null;
    private InetSocketAddress serverAddress = null;
    private RpcSerializeProtocol protocol;

    /**
     * 构造器
     * @param eventLoopGroup
     * @param serverAddress
     * @param protocol
     */
    MessageSendInitializeTask(EventLoopGroup eventLoopGroup, InetSocketAddress serverAddress, RpcSerializeProtocol protocol) {
        this.eventLoopGroup = eventLoopGroup;
        this.serverAddress = serverAddress;
        this.protocol = protocol;
    }

    /**
     * 客户端task 的执行函数
     *
     * 逻辑是: 使用netty向服务端发送一个RPC请求
     * @return
     */
    @Override
    public Boolean call() {
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new MessageSendChannelInitializer().buildRpcSerializeProtocol(protocol));

        // 和服务端建立连接,然后异步获取运行结果
        ChannelFuture channelFuture = b.connect(serverAddress);

        // 给结果绑定 Listener,
        channelFuture.addListener(new ChannelFutureListener() {
            /**
             * 当ChannelFuture 执行完毕之后(也就是异步IO结束之后) 会调用该函数
             * @param channelFuture
             * @throws Exception
             */
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    MessageSendHandler handler = channelFuture.channel().pipeline().get(MessageSendHandler.class);
                    RpcServerLoader.getInstance().setMessageSendHandler(handler);
                }
            }
        });
        return Boolean.TRUE;
    }
}
