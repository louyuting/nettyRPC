package newlandframework.netty.rpc.core;

import com.google.common.util.concurrent.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import newlandframework.netty.rpc.model.MessageKeyVal;
import newlandframework.netty.rpc.model.MessageRequest;
import newlandframework.netty.rpc.model.MessageResponse;
import newlandframework.netty.rpc.serialize.support.RpcSerializeProtocol;
import newlandframework.netty.rpc.utils.LogUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;

/**
 * 服务器执行模块
 */
public class MessageRecvExecutor implements ApplicationContextAware, InitializingBean {

    /** 服务端IP地址, Spring注入 */
    private String serverAddress;
    /** 服务端序列化使用的协议,默认是JDK原生序列化, Spring注入 */
    private RpcSerializeProtocol serializeProtocol = RpcSerializeProtocol.JDKSERIALIZE;
    /** 分隔符 */
    private final static String DELIMITER = ":";
    /** 服务端处理请求体映射的Map */
    private Map<String, Object> handlerMap = new ConcurrentHashMap<String, Object>();
    /** 线程执行器,单例 */
    private static ListeningExecutorService threadPoolExecutor;

    /**
     * 构造器
     * @param serverAddress
     * @param serializeProtocol
     */
    public MessageRecvExecutor(String serverAddress, String serializeProtocol) {
        this.serverAddress = serverAddress;
        this.serializeProtocol = Enum.valueOf(RpcSerializeProtocol.class, serializeProtocol);
    }


    /**
     * 提交任务
     * @param task
     * @param ctx
     * @param request
     * @param response
     */
    public static void submit(Callable<Boolean> task, ChannelHandlerContext ctx, MessageRequest request, MessageResponse response) {
        /**
         * 这里用到了 DCL 实现单例模式
         */
        if (threadPoolExecutor == null) {
            synchronized (MessageRecvExecutor.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = MoreExecutors.listeningDecorator((ThreadPoolExecutor) RpcThreadPool.getExecutor(16, -1));
                }
            }
        }

        ListenableFuture<Boolean> listenableFuture = threadPoolExecutor.submit(task);

        Futures.addCallback(listenableFuture, new FutureCallback<Boolean>() {
            public void onSuccess(Boolean result) {
                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        System.out.println("RPC Server Send message-id respone:" + request.getMessageId());
                    }
                });
            }

            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, threadPoolExecutor);
    }

    /**
     * 当初始化Spring容器的时候会被调用, ApplicationContextAware包装接口中的方法在这里重载,
     * 在afterPropertiesSet()方法之前被调用.
     * 主要功能: 将容器中请求与实现类的映射关系存入 ConcurrentHashMap并发容器的Map中
     * @param ctx
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {

        LogUtil.log_debug("setApplicationContext()");

        try {
            /**获取 MessageKeyVal 实例*/
            MessageKeyVal keyVal = (MessageKeyVal) ctx.getBean(Class.forName("newlandframework.netty.rpc.model.MessageKeyVal"));
            /**获取Map映射关系*/
            Map<String, Object> rpcServiceObject = keyVal.getMessageKeyVal();

            Set s = rpcServiceObject.entrySet();
            Iterator<Map.Entry<String, Object>> it = s.iterator();
            Map.Entry<String, Object> entry;

            while (it.hasNext()) {
                entry = it.next();
                handlerMap.put(entry.getKey(), entry.getValue());
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MessageRecvExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /**
     * 重载InitializingBean里面的方法, 当他被BeanFactory调用
     * 在setApplicationContext()方法之后被调用
     * 主要功能: 启动netty服务端
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        LogUtil.log_debug("执行afterPropertiesSet()");

        ThreadFactory threadRpcFactory = new NamedThreadFactory("NettyRPC ThreadFactory");

        //获取机器的CPU数量 * 2 设置并行数,我的Mac是4核的.
        int parallel = Runtime.getRuntime().availableProcessors() * 2;
        /** 主从线程池,boss线程池是一个线程, worker线程池是parallel */
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup(parallel, threadRpcFactory, SelectorProvider.provider());

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .childHandler(new MessageRecvChannelInitializer(handlerMap).buildRpcSerializeProtocol(serializeProtocol))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            String[] ipAddr = serverAddress.split(MessageRecvExecutor.DELIMITER);

            if (ipAddr.length == 2) {
                //获取服务器IP地址和端口
                String host = ipAddr[0];
                int port = Integer.parseInt(ipAddr[1]);
                /**异步启动服务器*/
                ChannelFuture future = bootstrap.bind(host, port).sync();
                System.out.printf("[author louyuting] Netty RPC Server start success!\nip:%s\nport:%d\nprotocol:%s\n\n", host, port, serializeProtocol);
                future.channel().closeFuture().sync();
            } else {
                System.out.printf("[author louyuting] Netty RPC Server start fail!\n");
            }
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }
}
