package com.netty.rpc.client;

import com.netty.rpc.core.MessageSendExecutor;
import com.netty.rpc.servicebean.Calculate;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Description: 并发线程模拟
 * @version 0.1
 */
public class CalcParallelRequestThread implements Runnable {

    private CountDownLatch signal;
    private CountDownLatch finish;
    private MessageSendExecutor executor;
    private int taskNumber = 0;

    public CalcParallelRequestThread(MessageSendExecutor executor, CountDownLatch signal, CountDownLatch finish, int taskNumber) {
        this.signal = signal;
        this.finish = finish;
        this.taskNumber = taskNumber;
        this.executor = executor;
    }

    public void run() {
        try {
            signal.await();

            // 动态代理, 获得代理的实际对象
            Calculate calc = executor.execute(Calculate.class);

            // 动态代理执行方法, 这是会调用MessageSendProxy 里面的 handleInvocation()方法;
            // 在handleInvocation()方法中会给服务器发送 request
            // 然后获取到响应response, 返回接口对应的实例对象. 然后执行对应函数.
            int result = calc.add(taskNumber, taskNumber);
            //System.out.println("calc add result: [" + result + "]");

            finish.countDown();
        } catch (InterruptedException ex) {
            Logger.getLogger(CalcParallelRequestThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
