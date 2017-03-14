package com.netty.rpc.client;

import com.netty.rpc.core.MessageSendExecutor;
import com.netty.rpc.serialize.support.RpcSerializeProtocol;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * rpc并发测试代码
 * @version 0.1
 */
public class RpcParallelTest {
    /**
     * 执行一次并发数是 parallel 的并发请求
     * @param executor 线程池执行器
     * @param parallel 并发数
     * @param serverAddress 服务端地址
     * @param protocol 数据传输协议
     * @throws InterruptedException
     */
    public static void parallelTask(MessageSendExecutor executor, int parallel, String serverAddress, RpcSerializeProtocol protocol) throws InterruptedException {
        //开始计时
        StopWatch sw = new StopWatch();
        sw.start();

        CountDownLatch signal = new CountDownLatch(1);//并发
        CountDownLatch finish = new CountDownLatch(parallel);

        for (int index = 0; index < parallel; index++) {
            CalcParallelRequestThread client = new CalcParallelRequestThread(executor, signal, finish, index);
            new Thread(client).start();
        }

        //唤起parallel个并发线程,瞬间发起请求操作
        signal.countDown();

        //等待所有线程的计算任务结束
        finish.await();
        //统计时间
        sw.stop();
        String tip = String.format("[%s] [%s] RPC调用总共耗时: [%s] 毫秒", serverAddress, protocol, sw.getTime());
        System.out.println(tip);
    }

    /**
     * 这里模拟某一个客户端提交一次本地并行计算任务, 通信数据采用JDK原生序列化
     * @param executor 客户端线程池
     * @param parallel 并行数
     * @throws InterruptedException
     */
    public static void JdkNativeParallelTask(MessageSendExecutor executor, int parallel) throws InterruptedException {
        String serverAddress = "127.0.0.1:18887";
        RpcSerializeProtocol protocol = RpcSerializeProtocol.JDKSERIALIZE;
        executor.setRpcServerLoader(serverAddress, protocol);
        RpcParallelTest.parallelTask(executor, parallel, serverAddress, protocol);
        TimeUnit.SECONDS.sleep(3);
    }



    public static void main(String[] args) throws Exception {
        //并行度10000
        int parallel = 1000;
        MessageSendExecutor executor = new MessageSendExecutor();

        for (int i = 0; i < 5; i++) {
            JdkNativeParallelTask(executor, parallel);
            System.out.printf("[[author louyuting]] netty RPC Server 消息协议序列化第[%d]轮并发验证结束!\n", i);
        }
        executor.stop();
    }
}
