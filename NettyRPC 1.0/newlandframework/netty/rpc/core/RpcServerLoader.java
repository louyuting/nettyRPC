package newlandframework.netty.rpc.core;

import com.google.common.util.concurrent.*;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import newlandframework.netty.rpc.serialize.support.RpcSerializeProtocol;
import newlandframework.netty.rpc.utils.LogUtil;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 客户端调用:rpc服务配置加载
 *
 * 这个类是单例的
 */
public class RpcServerLoader {
    /** 单例获取loader */
    private volatile static RpcServerLoader rpcServerLoader;

    /** 服务器端地址IP和端口号分隔的符号 */
    private final static String DELIMITER = ":";
    /** 序列化的协议 */
    private RpcSerializeProtocol serializeProtocol = RpcSerializeProtocol.JDKSERIALIZE;

    /** 并行处理器个数 */
    private final static int parallel = Runtime.getRuntime().availableProcessors() * 2;

    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);

    /** 客户端 消息处理线程池 */
    private static ListeningExecutorService threadPoolExecutor = MoreExecutors.listeningDecorator((ThreadPoolExecutor) RpcThreadPool.getExecutor(16, -1));


    private MessageSendHandler messageSendHandler = null;

    /** 细粒度的可重入锁 */
    private Lock lock = new ReentrantLock();
    private Condition connectStatus = lock.newCondition();
    private Condition handlerStatus = lock.newCondition();

    /**
     * 构造器
     */
    private RpcServerLoader() {

    }

    /**
     * DCL: 实现单例模式
     */
    public static RpcServerLoader getInstance() {
        if (rpcServerLoader == null) {
            synchronized (RpcServerLoader.class) {
                if (rpcServerLoader == null) {
                    rpcServerLoader = new RpcServerLoader();
                }
            }
        }
        return rpcServerLoader;
    }

    /**
     *
     * @param serverAddress 服务器端地址
     * @param serializeProtocol 序列化协议
     */
    public void load(String serverAddress, RpcSerializeProtocol serializeProtocol) {
        String[] ipAddr = serverAddress.split(RpcServerLoader.DELIMITER);
        if (ipAddr.length == 2) {
            //获取IP
            String host = ipAddr[0];
            //获取端口号
            int port = Integer.parseInt(ipAddr[1]);
            //获取socket的完整地址
            final InetSocketAddress remoteAddr = new InetSocketAddress(host, port);

            //
            ListenableFuture<Boolean> listenableFuture = threadPoolExecutor.submit(
                    new MessageSendInitializeTask(eventLoopGroup, remoteAddr, serializeProtocol));

            // 给listenableFuture 添加回调函数,当MessageSendInitializeTask执行完毕之后调用
            Futures.addCallback(listenableFuture, new FutureCallback<Boolean>() {

                /***
                 * 与服务器建立连接后,等待本地messageSendHandler获取成功
                 * @param result
                 */
                public void onSuccess(Boolean result) {
                    LogUtil.log_debug("listenableFuture-->callback");
                    try {
                        lock.lock();

                        if (messageSendHandler == null) {
                            handlerStatus.await();
                        }

                        if (result == Boolean.TRUE && messageSendHandler != null) {

                            connectStatus.signalAll();
                        }
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RpcServerLoader.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        lock.unlock();
                    }
                }

                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            }, threadPoolExecutor);// end of Futures.addCallback()

        }
    }


    /**
     *
     * @param messageInHandler
     */
    public void setMessageSendHandler(MessageSendHandler messageInHandler) {
        try {
            lock.lock();
            this.messageSendHandler = messageInHandler;
            handlerStatus.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 独占的获取 messageSendHandler
     * @return
     * @throws InterruptedException
     */
    public MessageSendHandler getMessageSendHandler() throws InterruptedException {
        try {
            lock.lock();
            //netty服务端链路没有建立完毕之前，先挂起等待
            if (messageSendHandler == null) {
                connectStatus.await();// 阻塞
            }
            return messageSendHandler;
        } finally {
            lock.unlock();
        }
    }


    public void unLoad() {
        messageSendHandler.close();
        threadPoolExecutor.shutdown();
        eventLoopGroup.shutdownGracefully();
    }

    public void setSerializeProtocol(RpcSerializeProtocol serializeProtocol) {
        this.serializeProtocol = serializeProtocol;
    }
}
