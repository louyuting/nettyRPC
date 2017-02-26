/**
 * @filename:KryoDecoder.java
 *
 * Newland Co. Ltd. All rights reserved.
 *
 * @Description:Kryo解码器
 * @author tangjie
 * @version 1.0
 *
 */
package com.netty.rpc.serialize.support.kryo;

import com.netty.rpc.serialize.support.MessageCodecUtil;
import com.netty.rpc.serialize.support.MessageDecoder;
import com.netty.rpc.serialize.support.MessageCodecUtil;
import com.netty.rpc.serialize.support.MessageDecoder;

public class KryoDecoder extends MessageDecoder {

    public KryoDecoder(MessageCodecUtil util) {
        super(util);
    }
}

