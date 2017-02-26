package com.netty.rpc.core;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import com.netty.rpc.model.MessageRequest;
import com.netty.rpc.model.MessageResponse;
import com.netty.rpc.utils.LogUtil;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rpc客户端 handler 处理模块
 *
 */
public class MessageSendHandler extends ChannelInboundHandlerAdapter {

    private ConcurrentHashMap<String, MessageCallBack> mapCallBack = new ConcurrentHashMap<String, MessageCallBack>();

    private volatile Channel channel;
    private SocketAddress remoteAddr;

    /*public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemoteAddr() {
        return remoteAddr;
    }*/

    /**
     * 当channel被注册到eventloop中时调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LogUtil.log_debug("channel 被 [register]");
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }
    /**
     * 当通道被激活时候调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.log_debug("channel 被 [actived]");
        super.channelActive(ctx);
        this.remoteAddr = this.channel.remoteAddress();
    }



    /**
     * 客户端接收到服务器端的响应时调用的函数
     * @param ctx
     * @param msg 其实就是response
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //强转成 MessageResponse
        MessageResponse response = (MessageResponse) msg;

        String messageId = response.getMessageId();
        //LogUtil.log_debug("response->id->"+messageId);

        // 根据response中的id获取对应的回调
        MessageCallBack callBack = mapCallBack.get(messageId);

        //callBack 非空,表示RPC已经成功.
        if (callBack != null) {
            mapCallBack.remove(messageId);
            callBack.over(response);
        }
    }

    /**
     * handler 中出现异常才会执行这个函数
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 客户端关闭时调用
     */
    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }


    /**
     * 每次客户端发送一次RPC请求的 时候调用.
     * @param request
     * @return
     */
    public MessageCallBack sendRequest(MessageRequest request) {
        MessageCallBack callBack = new MessageCallBack(request);
        mapCallBack.put(request.getMessageId(), callBack);

        /**
         * 写数据到channel 然后从客户端发送request到服务器
         */
        channel.writeAndFlush(request);
        return callBack;
    }
}
