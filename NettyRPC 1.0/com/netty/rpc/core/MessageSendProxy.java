package com.netty.rpc.core;

import com.google.common.reflect.AbstractInvocationHandler;
import com.netty.rpc.model.MessageRequest;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Description:Rpc客户端消息处理
 */
public class MessageSendProxy<T> extends AbstractInvocationHandler {

    /**
     * 动态代理执行的方法
     * @param proxy
     * @param method
     * @param args
     * @return 返回代代理执行之后的结果
     * @throws Throwable
     */
    public Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
        //根据执行函数 反射 获取request的信息
        MessageRequest request = new MessageRequest();
        request.setMessageId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setTypeParameters(method.getParameterTypes());
        request.setParameters(args);

        // 获取到客户端的MessageSendHandler
        MessageSendHandler handler = RpcServerLoader.getInstance().getMessageSendHandler();
        // 调用MessageSendHandler里面的函数发送request到服务端,并获取回调函数
        MessageCallBack callBack = handler.sendRequest(request);
        return callBack.start();
    }
}
