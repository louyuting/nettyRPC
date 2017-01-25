package newlandframework.netty.rpc.core;

import io.netty.channel.ChannelHandlerContext;
import newlandframework.netty.rpc.model.MessageRequest;
import newlandframework.netty.rpc.model.MessageResponse;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Rpc服务器消息线程任务处理
 */
public class MessageRecvInitializeTask implements Callable<Boolean> {

    private MessageRequest request = null;
    private MessageResponse response = null;
    private Map<String, Object> handlerMap = null;
    private ChannelHandlerContext ctx = null;

    public MessageResponse getResponse() {
        return response;
    }
    public MessageRequest getRequest() {
        return request;
    }
    public void setRequest(MessageRequest request) {
        this.request = request;
    }

    /**
     * 构造器
     * @param request
     * @param response
     * @param handlerMap
     */
    MessageRecvInitializeTask(MessageRequest request, MessageResponse response, Map<String, Object> handlerMap) {
        this.request = request;
        this.response = response;
        this.handlerMap = handlerMap;
        this.ctx = ctx;
    }

    public Boolean call() {
        response.setMessageId(request.getMessageId());
        try {
            Object result = reflect(request);
            response.setResult(result);
            return Boolean.TRUE;
        } catch (Throwable t) {
            response.setError(t.toString());
            t.printStackTrace();
            System.err.printf("RPC Server invoke error!\n");
            return Boolean.FALSE;
        }
    }

    /**
     * 反射
     * @param request
     * @return
     * @throws Throwable
     */
    private Object reflect(MessageRequest request) throws Throwable {
        //获取类名
        String className = request.getClassName();
        //根据类名,获取类名在服务器映射的类信息
        Object serviceBean = handlerMap.get(className);
        //获取方法名
        String methodName = request.getMethodName();
        //获取参数值数组
        Object[] parameters = request.getParameters();
        //反射执行,获取resopnse的result
        return MethodUtils.invokeMethod(serviceBean, methodName, parameters);
    }
}
