/**
 * @filename:MessageDecoder.java
 *
 * @Description: RPC消息解码接口
 * @version 1.0
 *
 */
package com.netty.rpc.serialize.support;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 解码: 把二进制数据转换成消息
 */
public class MessageDecoder extends ByteToMessageDecoder {
    //每个消息的二进制数据的长度
    final public static int MESSAGE_LENGTH = MessageCodecUtil.MESSAGE_LENGTH;

    private MessageCodecUtil util = null;

    /**
     * 构造器传入编码方式
     * @param util
     */
    public MessageDecoder(final MessageCodecUtil util) {
        this.util = util;
    }

    /**
     * 解码
     * @param ctx
     * @param in 待解码的二进制数据
     * @param out 解码之后的数据
     */
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        //首先就是判断是否获取到了足够的解码字节数
        if (in.readableBytes() < MessageDecoder.MESSAGE_LENGTH) {
            return;
        }

        //标记读索引
        in.markReaderIndex();
        //读取一个字节
        int messageLength = in.readInt();
        
        if (messageLength < 0) {
            ctx.close();
        }

        if (in.readableBytes() < messageLength) {
            in.resetReaderIndex();
            return;
        } else {
            byte[] messageBody = new byte[messageLength];
            in.readBytes(messageBody);

            try {
                //解码
                Object obj = util.decode(messageBody);
                out.add(obj);
            } catch (IOException ex) {
                Logger.getLogger(MessageDecoder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
