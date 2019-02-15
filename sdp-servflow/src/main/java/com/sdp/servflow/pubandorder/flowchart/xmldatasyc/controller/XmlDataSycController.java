package com.sdp.servflow.pubandorder.flowchart.xmldatasyc.controller;
//package com.sdp.servflow.pubandorder.flowchart.xmldatasyc.controller;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSONObject;
//import com.sdp.servflow.pubandorder.flowchart.xmldatasyc.service.DBToXmlService;
//
///**
// * 
// * xml导入导出功能
// *
// * @author ZY
// * @version 2017年9月23日
// * @see XmlDataSyc
// * @since
// */
//@RestController
//@RequestMapping("/xmlDataSyc")
//public class XmlDataSycController {
//    
//    @Autowired
//    DBToXmlService dBToXmlService;
//    
//    
//    @RequestMapping(value = "downLoadFile", method = RequestMethod.GET)
//        
//    public void downLoadFile(HttpServletResponse resp,String fileName){
//        dBToXmlService.downloadFile(resp, fileName);
//    }
//    
//    @ResponseBody
//    @RequestMapping(value = "/dbToXmlSyc", method = RequestMethod.POST)
//    public JSONObject dbToXmlSyc(String flowchartId,String flowchartName){
//        
//        Map<String,Object> res = new HashMap<>();
//        try {
//             res = dBToXmlService.exportfile(flowchartId,flowchartName);
//        }
//        catch (Exception e) {
//            res.put("result", "falied");
//            e.printStackTrace();
//        }
//        return (JSONObject)JSONObject.toJSON(res);
//    }
//    
//
//}
