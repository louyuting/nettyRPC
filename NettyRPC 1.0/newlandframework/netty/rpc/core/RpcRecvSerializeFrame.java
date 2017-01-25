package newlandframework.netty.rpc.core;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import newlandframework.netty.rpc.serialize.support.MessageCodecUtil;
import newlandframework.netty.rpc.serialize.support.RpcSerializeFrame;
import newlandframework.netty.rpc.serialize.support.RpcSerializeProtocol;
import newlandframework.netty.rpc.serialize.support.hessian.HessianCodecUtil;
import newlandframework.netty.rpc.serialize.support.hessian.HessianDecoder;
import newlandframework.netty.rpc.serialize.support.hessian.HessianEncoder;
import newlandframework.netty.rpc.serialize.support.kryo.KryoCodecUtil;
import newlandframework.netty.rpc.serialize.support.kryo.KryoDecoder;
import newlandframework.netty.rpc.serialize.support.kryo.KryoEncoder;
import newlandframework.netty.rpc.serialize.support.kryo.KryoPoolFactory;

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
                /** netty提供的默认解码器,解决TCP的黏包问题 */
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, MessageCodecUtil.MESSAGE_LENGTH, 0, MessageCodecUtil.MESSAGE_LENGTH));
                /** netty提供的默认解码器 */
                pipeline.addLast(new LengthFieldPrepender(MessageCodecUtil.MESSAGE_LENGTH));
                /** netty提供的默认解码器,JDK原生序列化编解码器 */
                pipeline.addLast(new ObjectEncoder());
                pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                /** RPC 服务端的handler */
                pipeline.addLast(new MessageRecvHandler(handlerMap));
                break;
            }
            case KRYOSERIALIZE: {
                KryoCodecUtil util = new KryoCodecUtil(KryoPoolFactory.getKryoPoolInstance());
                pipeline.addLast(new KryoEncoder(util));
                pipeline.addLast(new KryoDecoder(util));
                pipeline.addLast(new MessageRecvHandler(handlerMap));
                break;
            }
            case HESSIANSERIALIZE: {
                HessianCodecUtil util = new HessianCodecUtil();
                pipeline.addLast(new HessianEncoder(util));
                pipeline.addLast(new HessianDecoder(util));
                pipeline.addLast(new MessageRecvHandler(handlerMap));
                break;
            }
        }
    }
}

