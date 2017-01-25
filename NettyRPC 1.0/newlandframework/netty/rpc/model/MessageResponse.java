/**
 * @filename:MessageResponse.java
 *
 * @Description:rpc服务应答结构
 * @version 1.0
 *
 */
package newlandframework.netty.rpc.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 响应的请求体
 */
public class MessageResponse implements Serializable {

    private String messageId;//消息ID
    private String error;//错误码
    private Object resultDesc;//返回的结果序列化对象

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return resultDesc;
    }

    public void setResult(Object resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
