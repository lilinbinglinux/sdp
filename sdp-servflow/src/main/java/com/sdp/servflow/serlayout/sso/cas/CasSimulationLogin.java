package com.sdp.servflow.serlayout.sso.cas;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.jasig.cas.client.validation.TicketValidationException;
import org.springframework.stereotype.Component;

import com.sdp.servflow.serlayout.sso.model.Userinfo;
import com.sdp.servflow.serlayout.sso.util.CasCurrentUserUtils;

import net.sf.json.JSONObject;



/**
 * cas模拟登录组件接口
 * @author zy
 *
 */

@Component
public class CasSimulationLogin {
	
	public void login(HttpServletRequest request, HttpServletResponse response, 
			JSONObject resultobj) throws ClientProtocolException, IOException, TicketValidationException{
		
		String errormsg = validateParam(resultobj,request,response);
		if(!errormsg.equals("success")) {
			toError(request, response, errormsg);
			return;
		}
		
		String returnurl = resultobj.getString("returnurl");
		String casServerUrlPrefix = resultobj.getString("casServerUrlPrefix");
		String loginId = resultobj.getString("loginId");
		String password = resultobj.getString("password");
		try {
			PrintWriter	out = response.getWriter();
		
	        response.setCharacterEncoding("UTF_8");//设置Response的编码方式为UTF-8
	        response.setHeader("Content-type","text/html;charset=UTF-8");
	        out.println("<script type='text/javascript'> document.location.href='"
	                    +returnurl
	                    +"'</script>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Userinfo user = CasCurrentUserUtils.getInstance().getUser();
        //用户已经登陆则不进行模拟登陆
        if (user == null || user.getId() == null || !user.getLoginId().equals(loginId)){
            request.setAttribute("username",loginId);
            request.setAttribute("password",password);
            LoginCASOnBackstage locas = new LoginCASOnBackstage();
            locas.login(request, response, casServerUrlPrefix);
        }
		
	}
	
	private String validateParam(JSONObject resultobj,HttpServletRequest request,HttpServletResponse response) {
		if(!resultobj.has("returnurl")||StringUtils.isBlank(resultobj.getString("returnurl"))) {
			return "请设置returnurl参数";
		}
		
		if(!resultobj.has("casServerUrlPrefix")||StringUtils.isBlank(resultobj.getString("casServerUrlPrefix"))) {
			return "请设置casServerUrlPrefix参数";
		}
		
		if(!resultobj.has("casServerUrlPrefix")||StringUtils.isBlank(resultobj.getString("casServerUrlPrefix"))) {
			return "请设置loginId参数";
		}
		
		if(!resultobj.has("password")||StringUtils.isBlank(resultobj.getString("password"))) {
			return "请设置password参数";
		}
		
		return "success";
	}
	
	private void toError(HttpServletRequest request,HttpServletResponse response,String errormsg){
		try {
			String path = request.getContextPath();
			int port = request.getServerPort();
			String returnurl = request.getScheme()+"://"+request.getServerName()+(port==80?"":":"+port)+path;
			HttpSession session = request.getSession();
			session.setAttribute("errormsg", errormsg);
			response.sendRedirect(returnurl+"/ssoerror.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
