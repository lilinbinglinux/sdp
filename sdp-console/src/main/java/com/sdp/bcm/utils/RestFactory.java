package com.sdp.bcm.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.sdp.bcm.api.CiApi;
import com.sdp.bcm.api.ImageApi;
import com.sdp.bcm.api.ServiceApi;
import com.sdp.bcm.api.StorageApi;
import com.sdp.bcm.rest.util.ClientProxy;
import com.sdp.bcm.rest.util.MethodInvoker;

public class RestFactory {

    public ImageApi creatImageClient(String url) {
        Class<ImageApi> apiClass = ImageApi.class;
        Class<?>[] interfArr = { apiClass };
        Map<Method, MethodInvoker> mathodMap = new HashMap<Method, MethodInvoker>();
        for (Method method : apiClass.getMethods()) {
            mathodMap.put(method, new MethodInvoker(url, method));
        }
        return (ImageApi) Proxy.newProxyInstance(apiClass.getClassLoader(), interfArr, new ClientProxy(mathodMap));
    }

    public CiApi creatCiApiClient(String url) {
        Class<CiApi> apiClass = CiApi.class;
        Class<?>[] interfArr = { apiClass };
        Map<Method, MethodInvoker> mathodMap = new HashMap<Method, MethodInvoker>();
        for (Method method : apiClass.getMethods()) {
            mathodMap.put(method, new MethodInvoker(url, method));
        }
        return (CiApi) Proxy.newProxyInstance(apiClass.getClassLoader(), interfArr, new ClientProxy(mathodMap));
    }

    public ServiceApi creatServiceApiClient(String url) {
        Class<ServiceApi> apiClass = ServiceApi.class;
        Class<?>[] interfArr = { apiClass };
        Map<Method, MethodInvoker> mathodMap = new HashMap<Method, MethodInvoker>();
        for (Method method : apiClass.getMethods()) {
            mathodMap.put(method, new MethodInvoker(url, method));
        }
        return (ServiceApi) Proxy.newProxyInstance(apiClass.getClassLoader(), interfArr, new ClientProxy(mathodMap));
    }

    /**
     * 创建文件存储所有接口的动态代理实例
     * @param url
     * @return
     */
    public StorageApi createStorageApiClient(String url) {
        Class<StorageApi> apiClass = StorageApi.class;
        Class<?>[] interfArr = {apiClass};
        Map<Method, MethodInvoker> mathodMap = new HashMap<Method, MethodInvoker>();
        for (Method method : apiClass.getMethods()) {
            mathodMap.put(method, new MethodInvoker(url, method));
        }
        return (StorageApi) Proxy.newProxyInstance(apiClass.getClassLoader(), interfArr, new ClientProxy(mathodMap));
    }

}
