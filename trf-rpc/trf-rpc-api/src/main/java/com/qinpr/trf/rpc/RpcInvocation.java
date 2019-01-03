package com.qinpr.trf.rpc;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinpr on 2018/12/14.
 */
public class RpcInvocation implements Invocation, Serializable {

    private static final long serialVersionUID = 13137811572036349L;
    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] arguments;

    private Map<String, String> attachments;

    private transient Invoker<?> invoker;

    public RpcInvocation() {
    }

    public RpcInvocation(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
        this(methodName, parameterTypes, arguments, null, null);
    }

    public RpcInvocation(String methodName, Class<?>[] parameterTypes, Object[] arguments, Map<String, String> attachments, Invoker<?> invoker) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes == null ? new Class<?>[0] : parameterTypes;
        this.arguments = arguments == null ? new Object[0] : arguments;
        this.attachments = attachments == null ? new HashMap<String, String>() : attachments;
        this.invoker = invoker;
    }

    public RpcInvocation(Method method, Object[] arguments) {
        this(method.getName(), method.getParameterTypes(), arguments, null, null);
    }

    @Override
    public String getMethodName() {
        return null;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return new Class[0];
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public Map<String, String> getAttachments() {
        return null;
    }

    @Override
    public String getAttachment(String key) {
        return null;
    }

    @Override
    public String getAttachment(String key, String defaultValue) {
        return null;
    }

    @Override
    public Invoker<?> getInvoker() {
        return null;
    }

    public void addAttachments(Map<String, String> attachments) {
        if (attachments == null) {
            return;
        }
        if (this.attachments == null) {
            this.attachments = new HashMap<String, String>();
        }
        this.attachments.putAll(attachments);
    }

    public void setInvoker(Invoker<?> invoker) {
        this.invoker = invoker;
    }

    public void addAttachmentsIfAbsent(Map<String, String> attachments) {
        if (attachments == null) {
            return;
        }
        for (Map.Entry<String, String> entry : attachments.entrySet()) {
            setAttachmentIfAbsent(entry.getKey(), entry.getValue());
        }
    }

    public void setAttachmentIfAbsent(String key, String value) {
        if (attachments == null) {
            attachments = new HashMap<String, String>();
        }
        if (!attachments.containsKey(key)) {
            attachments.put(key, value);
        }
    }

    public void setAttachment(String key, String value) {
        if (attachments == null) {
            attachments = new HashMap<String, String>();
        }
        attachments.put(key, value);
    }
}
