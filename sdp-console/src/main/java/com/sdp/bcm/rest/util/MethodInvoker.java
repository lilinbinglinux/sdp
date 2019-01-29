package com.sdp.bcm.rest.util;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.exception.BcmException;

public class MethodInvoker {
	private static final Logger log = LoggerFactory.getLogger(MethodInvoker.class);

	private String url;

	private Method method;

	public MethodInvoker(String url, Method method) {
		this.url = url;
		this.method = method;
	}

	public Object invoke(Object[] args) {
		log.info("调用方法：" + method.getDeclaringClass() + "." + method.getName());

		Client client = ClientBuilder.newClient().register(JacksonJaxbJsonProvider.class);
		WebTarget webTarget = client.target(url);
		Path path = method.getAnnotation(Path.class);
		String pathValue = path.value();

		Entity<?> entity = null;
		Map<String, String> queryParamMap = new HashMap<String, String>();
		Parameter[] parameters = method.getParameters();
		if (parameters != null && parameters.length > 0) {
			for (int i = 0; i < parameters.length; i++) {
				Parameter parameter = parameters[i];
				PathParam pathParam = parameter.getAnnotation(PathParam.class);
				if (pathParam != null) {
					pathValue = pathValue.replace("{" + pathParam.value() + "}", String.valueOf(args[i]));
				} else {
					entity = Entity.entity(args[i], MediaType.APPLICATION_JSON_TYPE);
				}
				QueryParam queryParam = parameter.getAnnotation(QueryParam.class);
				if (null != queryParam && null != args[i]) {
					queryParamMap.put(queryParam.value(), String.valueOf(args[i]));
				}
			}
		} else {
			entity = Entity.entity("", MediaType.APPLICATION_JSON_TYPE);
		}

		WebTarget pathWebTarget = webTarget.path(pathValue);
		if (queryParamMap.size() > 0) {
			for (Entry<String, String> queryParam : queryParamMap.entrySet()) {
				pathWebTarget = pathWebTarget.queryParam(queryParam.getKey(), queryParam.getValue());
			}
		}
		Invocation.Builder invocationBuilder = pathWebTarget.request(MediaType.APPLICATION_JSON_TYPE);
		GET get = method.getAnnotation(GET.class);
		POST post = method.getAnnotation(POST.class);
		DELETE delete = method.getAnnotation(DELETE.class);
		PUT put = method.getAnnotation(PUT.class);
		Response response = null;
		String requestType = null;
		if (get != null) {
			response = invocationBuilder.get();
			requestType = get.toString();
		} else if (post != null) {
			response = invocationBuilder.post(entity);
			requestType = post.toString();
		} else if (delete != null) {
			response = invocationBuilder.delete();
			requestType = delete.toString();
		} else if (put != null) {
			response = invocationBuilder.put(entity);
			requestType = put.toString();
		}
		response.bufferEntity();

		String responseString = response.readEntity(String.class);
		if (responseString.length() > 400) {
			responseString = responseString.substring(0, 400) + "...";
		}
		log.info(url + pathValue + " -X" + requestType + "========response:" + responseString);
		try {
			if (!response.getHeaders().isEmpty() && response.getHeaders().containsKey("Etag")) {
				return response.getHeaders();
			} else if (!StringUtils.isBlank(response.readEntity(String.class))) {
				// 解决部分bcm接口的data返回值为string类型，导致xbconsole的返回值不是标准json
				Object reObj = response.readEntity(method.getReturnType());
				if (ApiResult.class.getName().equals(method.getReturnType().getName())) {
					ApiResult apiResult = (ApiResult) reObj;
					if (apiResult.getData() != null && apiResult.getData() instanceof String && !"".equals(apiResult.getData())) {
						String data = apiResult.getData().toString();
						apiResult.setData(JSONObject.parse(data));
						return apiResult;
					}
				}
				return response.readEntity(method.getReturnType());
			}
		} catch (Exception e) {
			try {
				BcmException bcmException = response.readEntity(BcmException.class);
				if (bcmException != null) {
					if (bcmException.getStatus() == 200) {
						return null;
					}
					return new ApiResult(400, bcmException.getMessage());
				}
			} catch (Exception e2) {
				return new RuntimeException("unexpect response", e2);
			}
		}
		throw new RuntimeException("unexpect response");
	}
}
