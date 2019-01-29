package com.sdp.bcm.rest.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class ClientProxy implements InvocationHandler{

	private Map<Method, MethodInvoker> methodMap;
	private Class<?> classArr;
	
	public ClientProxy(Map<Method, MethodInvoker> methodMap) {
		super();
		this.methodMap = methodMap;
	}
	
	public Class<?> getClassArr() {
		return classArr;
	}



	public void setClassArr(Class<?> classArr) {
		this.classArr = classArr;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		MethodInvoker clientInvoker = methodMap.get(method);
		if(clientInvoker == null) {
			if("equals".equals(method.getName())) {
				return this.equals(proxy);
			}
			else if("hashCode".equals(method.getName())) {
				return this.hashCode();
			}
			else if("toString".equals(method.getName())) {
				return this.toString();
			}
		}
		if(clientInvoker==null) {
			throw new RuntimeException("Could not fnd a method named " + method);
		}
		
		return clientInvoker.invoke(args);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj ==null || !(obj instanceof ClientProxy)) {
			return false;
		}
		ClientProxy other = (ClientProxy) obj;
		if (other == this) {
			return true;
		}
		if(other.classArr != this.classArr) {
			return false;
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return this.classArr.hashCode();
	}
	
	public String toString() {
		return "Resteasy Client Proxy for :" + classArr.getName();
	}

}
