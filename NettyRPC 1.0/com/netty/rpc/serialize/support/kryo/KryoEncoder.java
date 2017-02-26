/**
 * @filename:KryoEncoder.java
 *
 * Newland Co. Ltd. All rights reserved.
 *
 * @Description:Kryo编码器
 * @author tangjie
 * @version 1.0
 *
 */
package com.netty.rpc.serialize.support.kryo;

import com.netty.rpc.serialize.support.MessageCodecUtil;
import com.netty.rpc.serialize.support.MessageEncoder;
import com.netty.rpc.serialize.support.MessageCodecUtil;
import com.netty.rpc.serialize.support.MessageEncoder;

public class KryoEncoder extends MessageEncoder {

    public KryoEncoder(MessageCodecUtil util) {
        super(util);
    }
}
