package com.netty.rpc.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:rpc服务器启动模块
 */
public class RpcServerJdkNativeProtocolStarter {

    public static void main(String[] args) {
        /**
         * 加载Spring容器,启动服务器
         */
        new ClassPathXmlApplicationContext("com/netty/rpc/config/rpc-jdknative.xml");
    }
}

