/**
 * @filename:MessageEncoder.java
 *
 * @Description: RPC消息编码器接口
 * @version 1.0
 *
 */
package newlandframework.netty.rpc.serialize.support;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器:将消息编码成二进制
 */
public class MessageEncoder extends MessageToByteEncoder<Object> {


    private MessageCodecUtil util = null;

    /**
     * 构造器:传入 编码方式的工具类
     */
    public MessageEncoder(final MessageCodecUtil util) {
        this.util = util;
    }

    /**
     *
     * @param ctx
     * @param msg 待编码的消息
     * @param out 编码后的二进制数据
     * @throws Exception
     */
    protected void encode(final ChannelHandlerContext ctx, final Object msg, final ByteBuf out)
            throws Exception {
        util.encode(out, msg);
    }
}

