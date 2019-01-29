/**
 * 
 */
package com.sdp.servflow.test;

import java.util.HashMap;
import java.util.Map;

import com.sdp.servflow.common.BoncException;



/**
 * @author renpengyuan
 * @date 2017年9月22日
 * @miaoshu:提供多种接口网络协议适配器，通过抽象工厂模式进行创建协议类
 */
public abstract class INetProtocolFactory<T> {
	public static final String RESTFUL="restful";
	public static final String WEBSERVICE="webservice";
	public static final String SOCKET="socket";
	private static Map<String,INetProtocolFactory<?>> cacheObj=null;
	public synchronized static INetProtocolFactory<?> createInet(String netProctocolType) throws BoncException{
		if(netProctocolType==null||netProctocolType.trim().length()==0){
			throw new BoncException("netProctocolType is null or blank");
		}
		if(cacheObj==null){
			cacheObj=new HashMap<String,INetProtocolFactory<?>>();
		}
		if(cacheObj.get(netProctocolType)==null){
			INetProtocolFactory<?> inp=null;
			if(netProctocolType.equals(RESTFUL)){
				inp = new HttpProtocol();
			}else if(netProctocolType.equals(WEBSERVICE)){
				inp = new WebServiceProtocol();
			}
			cacheObj.put(netProctocolType, inp);
		}
		return cacheObj.get(netProctocolType);
	}
	public abstract T requestApi(Map<String,Object> params) throws BoncException;
}
