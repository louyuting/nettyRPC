package com.netty.rpc.core;

import com.netty.rpc.serialize.support.MessageCodecUtil;
import com.netty.rpc.serialize.support.RpcSerializeFrame;
import com.netty.rpc.serialize.support.RpcSerializeProtocol;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Map;

/**
 * RPC 服务端消息序列化协议框架
 */
public class RpcRecvSerializeFrame implements RpcSerializeFrame {

    private Map<String, Object> handlerMap = null;

    public RpcRecvSerializeFrame(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    /**
     * 根据序列化协议,选择管道的handler
     * @param protocol
     * @param pipeline
     */
    public void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline) {
        switch (protocol) {
            case JDKSERIALIZE: {
                //解码器 inbound 入站数据 解决TCP黏包问题
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, MessageCodecUtil.MESSAGE_LENGTH, 0, MessageCodecUtil.MESSAGE_LENGTH));
                //编码器 outbound 出站数据
                pipeline.addLast(new LengthFieldPrepender(MessageCodecUtil.MESSAGE_LENGTH));
                //编码器 outbound 出站数据
                pipeline.addLast(new ObjectEncoder());
                //解码器 inbound 入站数据
                pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                //解码器 inbound 入站数据
                pipeline.addLast(new MessageRecvHandler(handlerMap));
                break;

                /**
                 * 说明:
                 * 服务端入站时:也就是接收从客户端来的数据字节时:调用的是:
                 *      首先应该是解码器:LengthFieldBasedFrameDecoder-->ObjectDecoder-->MessageRecvHandler
                 *      上面这三个handler都是继承自inbound
                 *
                 * 服务端出站时:也就是服务端发送字节数据到客户端时:调用的是:
                 *      首先应该是编码器:LengthFieldPrepender-->ObjectEncoder
                 *      上面这两个handler都是继承自outbound
                 *
                 */
            }
        }
    }
}

