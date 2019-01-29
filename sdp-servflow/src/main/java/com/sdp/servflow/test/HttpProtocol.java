/**
 * 
 */
package com.sdp.servflow.test;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.protocol.Protocol;

import com.sdp.servflow.common.BoncException;
import com.sdp.servflow.pubandorder.protocol.HttpUtil;

/**
 * @author renpengyuan
 * @date 2017年9月22日
 */
public class HttpProtocol extends INetProtocolFactory<Map<String,Object>> implements INetProtocolConstants{
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> requestApi(Map<String, Object> params) throws BoncException {
		Object requestTypeObj = params.get(HTTP_PARAMS_TYPE_KEY);
		Object requestUrlObj = params.get(HTTP_PARAMS_URL_KEY);
		Object requestHeaderObj = params.get(HTTP_PARMAS_HEADER_KEY);
		Object requestDataObj = params.get(HTTP_PARAMS_DATA_KEY);
		if(requestTypeObj==null||requestUrlObj==null){
			throw new BoncException("httpprotocol params is null or blank");
		}
		String requestType= requestTypeObj.toString();
		String requestUrl = requestUrlObj.toString();
		if(requestType.trim().length()==0||requestUrl.trim().length()==0){
			throw new BoncException("httpprotocol params is null or blank");
		}
		Map<String,Object> headers= null;
		Map<String,Object> requestParams=null;
		if(requestHeaderObj!=null){
			headers=(Map<String, Object>) requestHeaderObj;
		}
		if(requestDataObj!=null){
			requestParams=(Map<String, Object>) requestDataObj;
		}
		HttpClient httpClient = new HttpClient();
		//配置HTTP请求连接超时时间
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
		//配置HTTP请求响应超时时间
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
		
		if(requestUrl.contains("https://")){
			System.out.println("---检测到请求协议为https,自动忽略ssl证书校验---");
			Protocol myhttps = new Protocol("https", new MySSLProtocolSocketFactory(), 443);   
			Protocol.registerProtocol("https", myhttps);
		}
		
		Map<String,Object> retu = new HashMap<String,Object>();
		try{
			 retu.put(REQUEST_MAP_CODE_KEY, SYS_SUCCESS_CODE);
			if(requestType.equals(HTTP_REQUEST_TYPE_GET)){
                  retu.put(REQUEST_MAP_RESULT_KEY, HttpUtil.get(requestUrl,requestParams, httpClient, headers));
			}else if(requestType.equals(HTTP_REQUEST_TYPE_POST)){
				if(headers==null||headers.size()==0||requestParams==null||requestParams.size()==0){
					httpClient=null;
					throw new BoncException("headers is null or blank");
				}
				retu.put(REQUEST_MAP_RESULT_KEY, HttpUtil.post(requestUrl, requestParams, httpClient, headers));
			}
		}catch(Exception e){
			throw new BoncException("httpprotocol error ",e);
		}

		return retu;
	}
}
