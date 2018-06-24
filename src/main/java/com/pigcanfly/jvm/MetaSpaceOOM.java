package com.pigcanfly.jvm;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *  -XX:MaxMetaspaceSize:10M 设置本地Metaspace大小为10M
 *  Result:OOM,java.lang.OutOfMemoryError: Metaspace
 */
public class MetaSpaceOOM {

    public static void main(String[] args) {
        //不停的创建新类
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(MetaSpaceOOM.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                @Override
                public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invoke(o,objects);
                }
            });
            enhancer.create();
        }
    }
}
