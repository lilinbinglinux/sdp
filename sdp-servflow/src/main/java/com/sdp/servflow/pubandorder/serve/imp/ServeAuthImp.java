package com.sdp.servflow.pubandorder.serve.imp;


import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.sdp.servflow.common.BoncException;
import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.format.imp.MapReplace;
import com.sdp.servflow.pubandorder.init.ResponseCollection;
import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.protocol.HttpUtil;
import com.sdp.servflow.pubandorder.protocol.SocketUtil;
import com.sdp.servflow.pubandorder.pub.model.PublisherBean;
import com.sdp.servflow.pubandorder.serve.LayoutServer;
import com.sdp.servflow.pubandorder.serve.ServeAuth;
import com.sdp.servflow.pubandorder.serve.call.Builder;
import com.sdp.servflow.pubandorder.serve.call.ConcreteBuilder;
import com.sdp.servflow.pubandorder.serve.call.Director;
import com.sdp.servflow.pubandorder.serve.mapper.ServeAuthMapper;
import com.sdp.servflow.pubandorder.serve.model.ProtocolData;
import com.sdp.servflow.pubandorder.util.IContants;
import com.sdp.servflow.pubandorder.util.Properties;
import com.sdp.servflow.test.INetProtocolConstants;
import com.sdp.servflow.test.INetProtocolFactory;

import net.sf.json.JSONObject;


/**
 * 服务鉴权
 * 
 * @author 任壮
 * @version 2017年7月21日
 * @see ServeAuthImp
 * @since
 */
@Service
@EnableConfigurationProperties(Properties.class)
public class ServeAuthImp implements ServeAuth {
	private static final Logger LOGGER = Logger.getLogger(ServeAuthImp.class);
	/***
	 * 服务鉴权的mapper
	 */
	@Autowired
	private ServeAuthMapper serveAuthMapper;
	/***
	 * 用来同步订阅的builder
	 */
	@Autowired
	private Builder builder;

	/**
	 * 服务编排
	 */
	@Autowired
	private LayoutServer layoutServer;

	@Override
	public boolean ipAuth(HashMap<String, Object> res) {
		if (null == res) {
			return false;
		}
		// 0代表已经有效的ip
		res.put("status", "0");
		HashMap<String, Object> result = serveAuthMapper.getAuth(res);
		if (null != result && result.size() > 0) {
			return true;
		}
		return false;
	}

	/***
	 * Description: 用户鉴权
	 * 
	 * @param res
	 * @return HashMap<String,Object>
	 * @see
	 */
	public HashMap<String, Object> userAuth(HashMap<String, Object> res) {
		if (null == res) {
			return null;
		}
		HashMap<String, Object> result = serveAuthMapper.getUserAuth(res);
		return result;
	}

	@Override
	public Object invocation(JSONObject sysParm, String busiParm) {
		Response response = null;
		try {
			HashMap<String, Object> res = new HashMap<>();
			res.put("ipAddr", sysParm.get("ip"));
			res.put("appId", sysParm.get("appId"));
			res.put("tenant_id", sysParm.get("tenant_id"));
			res.put("urlParam", sysParm.get("urlParam"));
			res.put("sourceType", sysParm.get("sourceType"));
			res.put("url", sysParm.get("url"));
			//服务编排
			Director director = new Director(builder);
			Response resp =   director.construct(res,busiParm);
			if(resp.getResult()!=null&&resp.getResult().startsWith("<")){
				return  toXml(resp);
			}else{
				String json = com.alibaba.fastjson.JSONObject.toJSONString(resp);
				return json;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			response = ResponseCollection.getSingleStion().get(4);
			response.setResult(e.toString());
		}
		return response;
	}


	private String toXml(Response resp){

		String xmlStr = "";
		xmlStr= "<xml><respCode>"+resp.getRespCode()+"</respCode><respDesc>"+resp.getRespDesc()+"</respDesc>"
				+ "<result> <![CDATA["+resp.getResult()+"]]></result></xml>";
		return xmlStr;


	}


	/**
	 * API在线测试
	 */
	@Override
	public Response apiOnlineTest(PublisherBean publisherBean,String sampledata) {
		Response response = null;

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("url", publisherBean.getUrl());
		map.put("pubprotocal", publisherBean.getPubprotocal());
		map.put("method", publisherBean.getMethod());

		try {
			//String returnBusiString = (String)server(map,sampledata,httpClient);
			String returnBusiString = null;
			response = ResponseCollection.getSingleStion().get(1);
			response.setResult(returnBusiString);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			response = ResponseCollection.getSingleStion().get(4);
			response.setResult(e.toString());
		}
		return response;
	}



	/**
	 * 
	 * Description:  根据注册的服务调用服务(在这一步之前所有的鉴权 以及格式已经转换完毕)
	 * 
	 *@param pubInterface
	 *@param busiParm
	 *@return 字符串
	 *@throws Exception String
	 *
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  ProtocolData  server(InterfaceNode pubInterface, ProtocolData sourceData, HttpClient httpClient)
			throws Exception {
		ProtocolData returnData = sourceData;
		String result="";
		Map<String, Object> returnMap = null;

		// returnData.setReposneformat(pubInterface.getInParameter().getFormat());
		// returnData.setReqformat(pubInterface.getOutParameter().getFormat());

		String url = pubInterface.getUrl();
		returnData.setUrl(url);
		if (IContants.HTTP.equals(pubInterface.getAgreement())) {
			if (IContants.GET.equals(pubInterface.getMethod())) {
				returnMap = http(pubInterface, returnData,httpClient,IContants.GET);
			}
			if (IContants.POST.equals(pubInterface.getMethod())) {
				returnMap = http(pubInterface, returnData,httpClient,IContants.POST);
			}
			if(returnMap.get("checkResult")!=null)
			{
				//数据校验不通过
				result =(String)returnMap.get("checkResult");
				returnData.setResponseBody(result);
			}else{
				//数据校验没有问题
				result = (String)returnMap.get("responseBody");
				returnData.setResponseHeader((Map<String, Object>)returnMap.get("responseHeader"));
				returnData.setResponseBody(result);
			}
		}
		if (IContants.WEBSERVICE.equals(pubInterface.getAgreement())) {
			//废弃
			Map<String,Object> params = new HashMap<String, Object>();
			params.put(INetProtocolConstants.SOAP_PARAMS_TYPE_KEY, INetProtocolConstants.SOAP_TYPE_1);
			params.put(INetProtocolConstants.SOAP_PARAMS_DATA_KEY, sourceData.getRequestBody());
			params.put(INetProtocolConstants.SOAP_PARAMS_WSDL_KEY, url);
			Map<String,String>    webserviceReturn=(Map<String, String>) getInetObj(INetProtocolFactory.WEBSERVICE).requestApi(params);
			result = (String)webserviceReturn.get("result");
			//returnData.setResponseHeader((Map<String, Object>)returnMap.get("responseHeader"));
			returnData.setResponseBody(result);
		}
		if (IContants.SOCKET.equals(pubInterface.getAgreement())) {
			result = SocketUtil.remoteOperate(url,
					Integer.parseInt((String)pubInterface.getPort()), (String)sourceData.getRequestBody(), pubInterface.getInParameter().getCharset());
			returnData.setResponseBody(result);
		}
		return returnData;
	}



	/***
	 * 
	 * Description: 适应post请求
	 * 
	 *@param pubInterface
	 *@param busiParm
	 *@return String
	 * @throws Exception 
	 *
	 * @see
	 */
	private Map<String, Object> http(InterfaceNode pubInterface, 
			ProtocolData protocolData,HttpClient httpClient,String type) throws Exception
	{
		String url = (String)pubInterface.getUrl();
		// 构建请求参数
		StringBuffer tagetUrl = new StringBuffer();
		Map<String, Object> headers = protocolData.getRequestHeader();

		Map<String, Object> urlParam = protocolData.getUrlParam();
		for(Entry<String, Object> parm : urlParam.entrySet()) {


			tagetUrl.append(parm.getKey());
			tagetUrl.append("=");
			//tagetUrl.append(parm.getValue());

			Object value = parm.getValue();
			if(value == null) {
				//返回前拼接&节点
				tagetUrl.append("&");
				continue;
			}else if (value instanceof String) {
				tagetUrl.append(URLEncoder.encode((String)parm.getValue(),HttpUtil.DEFAULTCHARSWT));
			}else {
				tagetUrl.append(value);
			}
			tagetUrl.append("&");
		}

		if (!"".equals(tagetUrl.toString().trim())) {

			//url = url + "?" +  URLEncoder.encode( tagetUrl.substring(0, tagetUrl.length() - 1).toString(), HttpUtil.DEFAULTCHARSWT);

			url = url + "?" + tagetUrl.substring(0, tagetUrl.length() - 1).toString();
		}
		headers.put("Content-type", (String)pubInterface.getInParameter().getFormat()+"; charset="+(String)pubInterface.getInParameter().getCharset()); 
		protocolData.setRequestHeader(headers);
		protocolData.setUrl(url);
		if(IContants.GET.equals(type))
		{
			return HttpUtil.get(url, null,httpClient,headers);
		}else if(IContants.POST.equals(type))
		{
			return HttpUtil.post(url, protocolData.getRequestBody(),httpClient,headers);
		}else{
			return null;
		}
	}



	/***
	 * 
	 * Description: 适应get请求(废弃)
	 * @deprecated
	 *@param pubInterface
	 *@param busiParm
	 *@return String
	 * @throws Exception 
	 *@
	 * @see
	 */
	@SuppressWarnings({"unused", "unchecked"})
	private Map<String, Object> get(Map<String, Object> pubInterface, String busiParm,HttpClient httpClient) throws Exception
	{
		Map<String, Object> headers = new HashMap<String, Object>();
		String url = (String)pubInterface.get("url");
		// 构建请求参数
		StringBuffer tagetUrl = new StringBuffer();
		// tagetUrl.append(url);

		JSONObject   sourceParams = JSONObject.fromObject(busiParm);


		LinkedHashMap<String,Object> target = new LinkedHashMap<String,Object>(); 

		pubInterface.put("type", "0");
		//遍历每个服务中的参数
		List<HashMap<String, Object>> params = serveAuthMapper.getPubSerParm(pubInterface);
		Map<String,Object> keyMap = null;
		String parmType  = null;
		Object value = null;
		for(HashMap<String, Object> parm : params) {
			//pub表中的数据类型
			parmType =  (String)parm.get("parampos");
			value = null;
			if(IContants.URLPARAM.equals(parmType))  {
				//说明这个参数需要拼接在url后面
				value  =MapReplace.getNode(sourceParams, (String)parm.get("ecodepath"));
				if (value != null) {
					tagetUrl.append((String)parm.get("ecode"));
					tagetUrl.append("=");
					tagetUrl.append(value);
					tagetUrl.append("&");
				}
			}else  if(IContants.REQBODY.equals(parmType)) {
				if(null!=(String)parm.get("ecodepath")&&!"".equals((String)parm.get("ecodepath")))
				{
					//其他类型的参数拼接到参数里面去
					value  =MapReplace.getNode(sourceParams, (String)parm.get("ecodepath"));
					MapReplace.AddNode(target,(String) parm.get("ecodepath"), value) ;   
				}
			}else  if(IContants.REQBODY.equals(parmType)) {
				if(null!=(String)parm.get("ecodepath")&&!"".equals((String)parm.get("ecodepath")))
				{
					//其他类型的参数拼接到参数里面去
					value  =MapReplace.getNode(sourceParams, (String)parm.get("ecodepath"));
					headers.put((String)parm.get("ecode"), value);
				}
			}
		}

		if (!"".equals(tagetUrl.toString().trim())) {
			url = url + "?" +  URLEncoder.encode( tagetUrl.substring(0, tagetUrl.length() - 1).toString(), HttpUtil.DEFAULTCHARSWT);
		}
		// Thread.sleep(10*1000);
		headers.put("Content-type", (String)pubInterface.get("reqformat")); 
		//returnBusiString = (String)(HttpUtil.post(url, target,httpClient,headers)).get("body");
		return HttpUtil.post(url, target,httpClient,headers);
	}
	INetProtocolFactory<?> getInetObj(String type){
		try {
			return INetProtocolFactory.createInet(type);
		} catch (BoncException e) {
			e.printStackTrace();
			return null;
		}
	}
}
