package com.sdp.servflow.serlayout.process.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.sdp.common.CurrentUserUtils;
import com.sdp.servflow.pubandorder.flowchart.xmldatasyc.service.DBToXmlService;
import com.sdp.servflow.serlayout.process.model.ServiceInfoPo;
import com.sdp.servflow.serlayout.process.service.ServiceInfoPoService;

@Service
@ConfigurationProperties(prefix = "system.conf", ignoreUnknownFields = true)
public class DBToXmlServiceImpl implements DBToXmlService {
	
	@Autowired
	private ServiceInfoPoService serviceInfoPoService;
    
    private String flowchartfiles;
    
    public String getFlowchartfiles() {
        return flowchartfiles;
    }

    public void setFlowchartfiles(String flowchartfiles) {
        this.flowchartfiles = flowchartfiles;
    }

    @Override
    public Map<String,Object> exportfile(String serVerId,String serId,String serName) throws Exception {
        Map<String,Object> result = new HashMap<String,Object>();
        String rs = "";
        String serFlow = "";
        
        Map<String,String> parammap = new HashMap<String,String>();
        parammap.put("serVerId", serVerId);
        parammap.put("serId", serId);
        parammap.put("delFlag","0");
        parammap.put("tenantId",CurrentUserUtils.getInstance().getUser().getTenantId());
        parammap.put("loginId",CurrentUserUtils.getInstance().getUser().getLoginId());
        
        List<ServiceInfoPo> serviceInfoPos = serviceInfoPoService.getAllByCondition(parammap);
        if(null != serviceInfoPos&&serviceInfoPos.size()>0) {
        		serFlow = serviceInfoPos.get(0).getSerFlow();
        }
        
        //输出xml文档
        FileOutputStream fos = null;
        
        StringReader sr = new StringReader(serFlow);
        InputSource is = new InputSource(sr);
        Document doc = (new SAXBuilder()).build(is);
        
        XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
        Map<Object,Object> map= createFile(serName);
        
        if((boolean)map.get("createflag")){
            fos = new FileOutputStream((String)map.get("file"),true); 
            xout.output(doc, fos);
            rs = "success";
        }else{
            rs = "failed";
        }
        
        result.put("result", rs);
        result.put("fileName", map.get("fileName"));
        return result;
    }
    
    /**
     * 
     * Description:创建文件 
     * 
     *@param fileName
     *@return Map<Object,Object>
     *
     * @see
     */
   private Map<Object,Object> createFile(String fileName){
       Map<Object,Object> map = new HashMap();
       Boolean bool = false;
       
       Date now = new Date(); 
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
       String sdate = sdf.format(now);
        
       fileName = fileName+sdate+".xml";//文件路径+文件名称+文件类型
       System.out.println(getFlowchartfiles()+fileName);
       File file = new File(getFlowchartfiles()+fileName);
       try{
           if(!file.exists()){
               file.createNewFile();
           }
           bool = true;
       }catch(Exception e){
           bool = false;
           e.printStackTrace();
       }
       
       map.put("fileName", fileName);
       map.put("file", getFlowchartfiles()+fileName);
       map.put("createflag", bool);
       
       return map;
   }
   
   
   
   @Override
   public void downloadFile(HttpServletResponse res,String fileName) {
       res.setHeader("content-type", "application/octet-stream");
       res.setContentType("application/octet-stream");
       res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
       byte[] buff = new byte[1024];
       BufferedInputStream bis = null;
       OutputStream os = null;
       try {
         os = res.getOutputStream();
         bis = new BufferedInputStream(new FileInputStream(new File(flowchartfiles
             + fileName)));
         int i = bis.read(buff);
         System.out.println(i);
         while (i != -1) {
           os.write(buff, 0, buff.length);
           os.flush();
           i = bis.read(buff);
           System.out.println(i);
         }
       } catch (IOException e) {
         e.printStackTrace();
       } finally {
         if (bis != null) {
           try {
             bis.close();
           } catch (IOException e) {
             e.printStackTrace();
           }
         }
       }
     }
}
