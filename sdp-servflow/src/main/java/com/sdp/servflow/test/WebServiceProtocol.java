/**
 * 
 */
package com.sdp.servflow.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;

import com.sdp.servflow.common.BoncException;


/**
 * @author renpengyuan
 * @date 2017年9月22日
 */
public class WebServiceProtocol extends INetProtocolFactory<Map<String,String>> implements INetProtocolConstants{
	@Override
	public Map<String, String> requestApi(Map<String, Object> params) throws BoncException{
		Object soapTypeObj=params.get(SOAP_PARAMS_TYPE_KEY);
		Object soapParamsObj=params.get(SOAP_PARAMS_DATA_KEY);
		Object wsdlAddObj = params.get(SOAP_PARAMS_WSDL_KEY);
		Map<String,String> retu = new HashMap<String,String>();
		if(soapTypeObj==null||soapParamsObj==null||wsdlAddObj==null){
			throw new BoncException("params is null or blank");
		}
		String soapType=soapTypeObj.toString();
		String soapParams= soapParamsObj.toString();
		String wsdlAdd= wsdlAddObj.toString();
		if(soapType.trim().length()==0||soapParams.trim().length()==0||wsdlAdd.trim().length()==0){
			throw new BoncException("params is null or blank");
		}
		try{
			PostMethod postMethod = new PostMethod(wsdlAdd);
			byte[] b = soapParams.getBytes("utf-8");
			String contentType=(soapType.equals(SOAP_TYPE_2))?CONTENT_TYPE_SOAP12:CONTENT_TYPE_TEXTXML;

			InputStream is = new ByteArrayInputStream(b, 0, b.length);
			RequestEntity re = new InputStreamRequestEntity(is, b.length,
					contentType+"; charset=utf-8");
			postMethod.setRequestEntity(re);
			HttpClient httpClient = new HttpClient();
			int statusCode = httpClient.executeMethod(postMethod);
			if(statusCode == 200) {
				retu.put(REQUEST_MAP_CODE_KEY, SYS_SUCCESS_CODE);
				retu.put(REQUEST_MAP_RESULT_KEY, postMethod.getResponseBodyAsString());
			} else {
				retu.put(REQUEST_MAP_CODE_KEY, SYS_ERROR_CODE);
				retu.put(REQUEST_MAP_RESULT_KEY, "请求状态码:"+statusCode);
			}
		}catch(Exception e){
			throw new BoncException("unknowException,please check",e); 
		}
		return retu;
	}
}
