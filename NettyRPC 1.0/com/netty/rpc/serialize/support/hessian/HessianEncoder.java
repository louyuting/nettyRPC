/**
 * @filename:HessianEncoder.java
 *
 * Newland Co. Ltd. All rights reserved.
 *
 * @Description:Hessian编码器
 * @author tangjie
 * @version 1.0
 *
 */
package com.netty.rpc.serialize.support.hessian;

import com.netty.rpc.serialize.support.MessageEncoder;
import com.netty.rpc.serialize.support.MessageCodecUtil;
import com.netty.rpc.serialize.support.MessageEncoder;

public class HessianEncoder extends MessageEncoder {

    public HessianEncoder(MessageCodecUtil util) {
        super(util);
    }
}
