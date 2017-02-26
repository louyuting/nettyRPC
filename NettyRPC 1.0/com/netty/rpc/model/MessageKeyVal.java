package com.netty.rpc.model;

import java.util.Map;

/**
 * 服务器端RPC接口定义和RPC接口实现类对象的映射关系
 */
public class MessageKeyVal {

    private Map<String, Object> messageKeyVal;

    public void setMessageKeyVal(Map<String, Object> messageKeyVal) {
        this.messageKeyVal = messageKeyVal;
    }

    public Map<String, Object> getMessageKeyVal() {
        return messageKeyVal;
    }
}
