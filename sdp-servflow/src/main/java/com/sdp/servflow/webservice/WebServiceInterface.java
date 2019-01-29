/**
 * 
 */
package com.sdp.servflow.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.ws.rs.Path;

import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.webservice.model.TokenResponse;

/**
 * @author renpengyuan
 * @date 2017年9月25日
 */
@WebService
@SOAPBinding(style = Style.RPC) 
public interface WebServiceInterface {
@WebMethod
public TokenResponse getToken(@WebParam(name="key") String key,@WebParam(name="appId") String appId);
@WebMethod
@Path(value="/apis")
public Response apis(@WebParam(name="busiparam") String busiparam);
@WebMethod
@Path(value="/apisPublish")
public Response apisPublish(@WebParam(name="busiparam") String busiparam);
}
