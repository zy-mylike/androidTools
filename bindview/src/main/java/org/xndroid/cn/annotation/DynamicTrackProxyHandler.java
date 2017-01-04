package org.xndroid.cn.annotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class DynamicTrackProxyHandler implements InvocationHandler {

    private final HashMap<String, Object> handlerMap = new HashMap();

    private final HashMap<String, Method> methodMap = new HashMap();

    public DynamicTrackProxyHandler() {
    }

    public void addMethod(String name, Method method) {
        this.methodMap.put(name, method);
    }

    public void addHandler(String name, Object obj) {
        this.handlerMap.put(name, obj);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        Method targetMethod = this.methodMap.get(methodName);
        Object targetHandler = this.handlerMap.get(methodName);
        return targetMethod != null && targetHandler != null ? targetMethod.invoke(targetHandler, args) : null;
    }
}
