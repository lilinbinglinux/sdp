package com.sdp.servflow.pubandorder.pub.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;  

/**
 * 
 * 指定路径文件上传
 *
 * @author ZY
 * @version 2017年9月7日
 * @see UploadController
 * @since
 */
@Controller
@ConfigurationProperties(prefix = "system.conf", ignoreUnknownFields = true)
@RequestMapping("/file")
public class UploadController {
    private String filesavepath;
    
    public String getFilesavepath() {
        return filesavepath;
    }

    public void setFilesavepath(String filesavepath) {
        this.filesavepath = filesavepath;
    }

    @RequestMapping(value="/importPicFile.do" ,method = RequestMethod.POST)
    public @ResponseBody String importPicFile1(@RequestParam MultipartFile myfile,HttpServletRequest request){
 
        String filePath = request.getParameter("filePath");
        System.out.println(filePath);
          Map<String,Object> map= new HashMap<String,Object>();
           if(myfile.isEmpty()){
                map.put( "result", "error");
                map.put( "msg", "上传文件不能为空" );
          } else{
                String originalFilename=myfile.getOriginalFilename();
                //String path = getFilesavepath()+sdf.format(new Date());
                
                String path = getFilesavepath()+filePath;
                
                File dir = new File(path);  
                if(!dir.exists()){
                    dir.mkdirs();
                }
                 try{
                     System.out.println(path+"\\");
                     System.out.println("originalFilename   "+originalFilename);
                      FileUtils. copyInputStreamToFile(myfile.getInputStream(), new File(path,originalFilename));
                      map.put( "result", "success");
                } catch (Exception e) {
                      map.put( "result", "error");
                      map.put( "msg",e.getMessage());
                }finally {
                }
          }
          String result=String.valueOf(JSONObject.fromObject (map));
          return result;
    }
}
    
    
