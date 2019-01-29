package com.sdp.servflow.serlayout.process.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sdp.servflow.serlayout.process.service.SerProcessXmlSysService;
import com.sdp.servflow.util.SafeCode;

/**
 * 流程图xml的导入导出
 * @author zy
 *
 */

@Controller
@RequestMapping("/serProcessXmlSys")
public class SerProcessXmlSysController {
	@Autowired
    private SerProcessXmlSysService serProcessXmlSysService;
	
	/**
	 * xml导出至目标文件
	 * @param serVerId
	 * @param serId
	 * @param serName
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/dbToXmlSyc", method = RequestMethod.POST)
	public JSONObject dbToXmlSyc(String serVerId,String serId,String serName) {
		Map<String,Object> res = new HashMap<>();
        try {
             res = serProcessXmlSysService.exportfile(serVerId,serId,serName);
        }
        catch (Exception e) {
            res.put("result", "falied");
            e.printStackTrace();
        }
        return (JSONObject)JSONObject.toJSON(res);
	}
	
	/**
	 * 下载
	 * @param resp
	 * @param serName
	 */
	 @RequestMapping(value = "/downLoadFile", method = RequestMethod.GET)
	 public void downLoadFile(HttpServletResponse resp,String serName){
		serProcessXmlSysService.downloadFile(resp, SafeCode.decode(serName));
	 }
	
	 /**
	  * xml转流程图
	  * @param request
	  * @param response
	  * @return
	  * @throws UnsupportedEncodingException
	  */
	 @RequestMapping(value="/xmlTodbSyc" ,method = RequestMethod.POST)
	 @ResponseBody
	 public Object xmlTodbSyc(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		 String fileName = request.getParameter("fileName");
		 return serProcessXmlSysService.uploadFile(request,response,fileName).toString();
	    }

}
