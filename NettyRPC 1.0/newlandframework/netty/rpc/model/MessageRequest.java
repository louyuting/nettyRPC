/**
 * @filename:MessageRequest.java
 *
 * @Description:rpc服务请求结构
 * @author tangjie
 * @version 1.0
 *
 */
package newlandframework.netty.rpc.model;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class MessageRequest implements Serializable {

    private String messageId;//消息ID
    private String className;//类名称
    private String methodName;//方法名称
    private Class<?>[] typeParameters;//参数结构
    private Object[] parametersVal;//参数值

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(Class<?>[] typeParameters) {
        this.typeParameters = typeParameters;
    }

    public Object[] getParameters() {
        return parametersVal;
    }

    public void setParameters(Object[] parametersVal) {
        this.parametersVal = parametersVal;
    }

    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, new String[]{"typeParameters", "parametersVal"});
    }
}
