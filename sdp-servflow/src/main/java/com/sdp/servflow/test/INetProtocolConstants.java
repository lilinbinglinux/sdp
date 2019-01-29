/**
 * 
 */
package com.sdp.servflow.test;

/**
 * @author renpengyuan
 * @date 2017年9月22日
 */
public interface INetProtocolConstants{
    /**内部编码*/
	public static final String SYS_ERROR_CODE="000001";
	public static final String SYS_SUCCESS_CODE="000000";
	/**webservice*/
	/**不同协议的请求参数mapkey*/
	public static final String SOAP_PARAMS_TYPE_KEY="soapType";
	public static final String SOAP_PARAMS_DATA_KEY="soapPamras";
	public static final String SOAP_PARAMS_WSDL_KEY="wsdlAdd";
	/**soapType*/
	public static final String SOAP_TYPE_1="soap";
	public static final String SOAP_TYPE_2="soap12";
	/**http*/
	public static final String HTTP_REQUEST_TYPE_GET="get";
	public static final String HTTP_REQUEST_TYPE_POST="post";
	/**http协议请求参数key*/
	public static final String HTTP_PARAMS_TYPE_KEY="requestType";
	public static final String HTTP_PARAMS_DATA_KEY="requestData";
	public static final String HTTP_PARAMS_URL_KEY="requestUrl";
	public static final String HTTP_PARMAS_HEADER_KEY="requestHeader";
	/**所有协议共同返回的参数mapkey*/
	public static final String REQUEST_MAP_CODE_KEY="code";
	public static final String REQUEST_MAP_RESULT_KEY="result";
	
	/**contentType*/
	public static final String CONTENT_TYPE_TEXTXML="text/xml";
	public static final String CONTENT_TYPE_SOAP12="application/soap+xml";
}
