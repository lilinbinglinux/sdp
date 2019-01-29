package com.sdp.frame.web.controller.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.sdp.frame.util.FileUtil;
import com.sdp.frame.util.PropertyUtil;
import com.sdp.frame.util.Sequence;
import com.sdp.frame.util.SystemUtil;


/**
 * 上传文件操作父类
 * @author Administrator
 *
 */
public abstract class AbstractorUploadController extends MultiActionController{
	
	private Logger log = Logger.getLogger(AbstractorUploadController.class);
	
	@Autowired
	protected HttpServletRequest request;

	/**
	 * 获取文件路径
	 * @param type
	 * @return
	 */
	protected abstract String getFilePath();

	/**
	 * 一次上传多个文件流方法
	 * @param request 请求域
	 * @param path 默认存放路径下的指定的路径
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/uploadAll")
	public List<Map> uploadAll(HttpServletRequest request,String path){
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);     
        String dir = "";
        String webDir ="";
        if(path==null||"".equals(path)){
        	String fileDir =this.getFilePath();
        	if(StringUtils.isEmpty(fileDir)){
        		return null;
        	}
        	dir =this.getSTF()+fileDir;
        	webDir = getContextPath() + SystemUtil.getFileSeparator() + getWebStF()+getFilePath();
        }else{
        	String fileDir =this.getFilePath();
        	if(StringUtils.isEmpty(fileDir)){
        		return null;
        	}
        	dir = this.getSTF()+fileDir+ SystemUtil.getFileSeparator() + path;
            webDir = getContextPath() + SystemUtil.getFileSeparator() + getWebStF() + SystemUtil.getFileSeparator() + path+SystemUtil.getFileSeparator() + getFilePath();
        }
		log.info(dir);
		try {
			FileUtil.createFileRec(dir);
		} catch (Exception e) {
			log.error("创建文件失败");
			return null;
		}
        List<Map> mapList = new ArrayList<Map>();
        Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
        Set set = fileMap.keySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
        	MultipartFile file = fileMap.get(iterator.next());
        	String fileName = file.getOriginalFilename();
    		String suf = fileName.substring(fileName.indexOf("."));
            
//    		String fileName = UUID.randomUUID().toString()+suf;
    		if(!copyFile(new File(dir+SystemUtil.getFileSeparator()+fileName), file)){
    			log.error("保存文件失败");
    			return null;
    		}

    		Map map = new HashMap();
    		map.put("error", 0);
    		map.put("path", dir+SystemUtil.getFileSeparator()+fileName);
    		map.put("fileSize", file.getSize());
    		map.put("fileName", fileName);
    		map.put("webPath", webDir + SystemUtil.getFileSeparator()+fileName);
    		log.info(dir+SystemUtil.getFileSeparator()+fileName);
    		mapList.add(map);

		}
        System.out.println(mapList);
		return mapList;
	}
	
	@RequestMapping(value="/download")
	public void download(HttpServletResponse response,String fileName,String filePath){
		if(!"".equals(fileName) && !"".equals(filePath)){
			try {
				String suf = FileUtil.getFileSuffix(fileName);
				String mimeType = FileUtil.getMimeType(suf);
		        response.setContentType(mimeType);
		        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8")); 
				OutputStream outputStream = response.getOutputStream();
				FileInputStream ips=new FileInputStream(filePath);
				try {
					byte[] buffer = new byte[1024];
					int length = 0;
					while ((length = ips.read(buffer)) > 0) {
						outputStream.write(buffer, 0, length);
					}
					outputStream.close();
					ips.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
//		return response;
	}
	
	/**
	 * 删除文件 
	 * @return 返回微信图片名
	 */
	@ResponseBody
	@RequestMapping(value="/delete")
	public boolean deleteFile(String filePath){
		return FileUtil.deleteFile(filePath);
	}
	
	/**
	 * 获得图片路径 
	 * @return 返回微信图片名
	 */
	private String getImagePath(){
		return PropertyUtil.getPropertiesValue("system", "WX_PICTURE");
	}
	
	/**
	 * 获得文章内容路径
	 * @return 
	 */
	private String getContentFilePath(){
		return PropertyUtil.getPropertiesValue("system", "WX_CONTENT");
	}
	
	private String getAppContextPath(){
		return PropertyUtil.getPropertiesValue("system", "APP_CONTENT");
	}
	/**
	 * 上传文件，保存，返回文件保存路径，文件名，访问路径等信息
	 * @param content
	 * @param path
	 * @param name
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/warpStrToFile",params="json")
	public Map warpStrToFile(String content,String path,String name){
		String dir = this.getContentFilePath();
        String webDir = getContextPath();
		if(path==null||"".equals(path)){
			dir=getSTF()+SystemUtil.getFileSeparator()+this.getContentFilePath();
	        webDir =getWebStF() +"/"+PropertyUtil.getPropertiesValue("system", "WX_CONTENT");
		}else{
			dir =getSTF()+ SystemUtil.getFileSeparator() +  this.getContentFilePath()+SystemUtil.getFileSeparator()+path;
	        webDir = getWebStF() +"/"+PropertyUtil.getPropertiesValue("system", "WX_CONTENT") + SystemUtil.getFileSeparator() + path;
		}
        Map map = new HashMap();
        log.info(dir);
		try {
			FileUtil.createFileRec(dir);
		} catch (Exception e) {
			log.error("创建文件失败");
			map.put("error", 1);
			return map;
		}
		String fileName = UUID.randomUUID().toString()+".html";
		try {
			new File(dir+SystemUtil.getFileSeparator()+fileName).createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileUtil.writeFile(content, dir+SystemUtil.getFileSeparator()+fileName);
		map.put("path", dir+SystemUtil.getFileSeparator()+fileName);
		map.put("fileName", name);
		map.put("webPath", webDir + SystemUtil.getFileSeparator()+fileName);
		return map;
	}
	
	/**
	 * swf上传插件上传文件处理方法
	 * @param request
	 * @param path 文件服务器保存路径
	 * @param refileName 重命名文件名
	 * @return
	 */
	public Map fileSwfUpload(HttpServletRequest request,String path,String refileName){
		// 转型为MultipartHttpRequest：     
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
        
        Map<String,MultipartFile> fileMap = multipartRequest.getFileMap();
        Set set = fileMap.keySet();
        Iterator iterator = set.iterator();
        MultipartFile file = null;
        if(iterator.hasNext()){
        	file = fileMap.get(iterator.next());
        }else{
        	return null;
        }
        
      //设置文件路径
        String dir = "";
        String webDir ="";
        if(path!=null && !"".equals(path)){
        	if(SystemUtil.isWindows()){
        		webDir = getRootRealPath()+SystemUtil.getFileSeparator()+getWebStF()+SystemUtil.getFileSeparator() +path;
        	}else{
        		webDir = getWebStF()+SystemUtil.getFileSeparator() + path;
        	}
        	dir = SystemUtil.getFileSeparator()+ path;
        }else{
        	if(SystemUtil.isWindows()){
        		webDir = getRootRealPath()+SystemUtil.getFileSeparator()+getWebStF()+SystemUtil.getFileSeparator() +getContentFilePath();
        	}else{
        		webDir = getWebStF()+SystemUtil.getFileSeparator() +getContentFilePath();
        	}
        	dir = SystemUtil.getFileSeparator() +getContentFilePath();
        }
		log.info(webDir);
		
		try {
			FileUtil.createFileRec(dir);
		} catch (Exception e) {
			log.error("创建文件失败");
			return null;
		}
		
		String fileOriginalName = file.getOriginalFilename();
		String suf = fileOriginalName.substring(fileOriginalName.indexOf("."));
        
		String fileName = "";
		if(StringUtils.isNotBlank(refileName)){
			fileName = refileName+suf;
		}else{
			fileName = Sequence.getSequence()+suf;
		}		
		if(!copyFile(new File(dir+SystemUtil.getFileSeparator()+fileName), file)){
			log.error("保存文件失败");
			return null;
		}

		Map map = new HashMap();
		map.put("error", 0);
		map.put("path", dir+SystemUtil.getFileSeparator()+fileName);
		map.put("fileSize", file.getSize());
		map.put("imageName", fileOriginalName);
		map.put("webPath", webDir + SystemUtil.getFileSeparator()+fileName);
		log.info(dir+SystemUtil.getFileSeparator()+fileName);
		return map;
	}
	/**
	 * 
	 * @Description:文件上传  公共方法(多文件)
	 * @param files  上传文件对象数组
	 * @param filePath   文件存放地址。 如果为空，默认上传到微信的配置路径下面，
	 * @param refileName   重命名文件名称,不需要加后缀。
	 * 	eg：根路径+path 
	 * @return  map 格式信息     ，键值解释--- 
	 * 			error : 错误编码，0、表示上传成功。
	 * 			path:本地路径。
	 * 			fileOriginalName：源文件名。
	 * 			webPath：web路径。
	 * 			fileSize：文件大小，单位字节。
	 */		
	protected List fileUpload(CommonsMultipartFile[] files,String filePath,String refileName){
		//设置文件路径
        String dir = "";
        String webDir ="";
        if(filePath!=null && !"".equals(filePath)){
        	if(SystemUtil.isWindows()){
        		dir = getRootRealPath()+SystemUtil.getFileSeparator()+getWebStF()+SystemUtil.getFileSeparator() +filePath;
        	}else{
        		dir = getWebStF()+SystemUtil.getFileSeparator() + filePath;
        	}
        	webDir = SystemUtil.getFileSeparator() +getWebStF() +SystemUtil.getFileSeparator()+ filePath;
        }else{
        	if(SystemUtil.isWindows()){
        		dir = getRootRealPath()+SystemUtil.getFileSeparator()+getWebStF()+SystemUtil.getFileSeparator() +getContentFilePath();
        	}else{
        		dir = getWebStF()+SystemUtil.getFileSeparator() +getContentFilePath();
        	}
        	webDir = SystemUtil.getFileSeparator() +getWebStF() +SystemUtil.getFileSeparator()+getContentFilePath();
        }
		log.info(dir);
		//创建目录
		try {
			FileUtil.createFileRec(dir);
		} catch (Exception e) {
			log.error("创建路径："+dir+"失败");
			return null;
		}
		//保存图片
		List list = new ArrayList<Object>();		
		for (int i = 0; i < files.length; i++) {
			Map fileMap = new HashMap();
			String fileOriginalName = files[i].getOriginalFilename();
			String suf = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));	        
			String fileName = "";
			if(StringUtils.isNotBlank(refileName)){
				fileName = refileName+suf;
			}else{
				fileName = Sequence.getSequence()+suf;
			}		
			if(!copyFile(new File(dir+SystemUtil.getFileSeparator()+fileName), files[i])){
				log.error("保存文件失败");
				return null;
			}		
			fileMap.put("error", 0);
			fileMap.put("path", dir+SystemUtil.getFileSeparator()+fileName);
			fileMap.put("fileOriginalName", fileOriginalName);
			fileMap.put("webPath", webDir + SystemUtil.getFileSeparator()+fileName);
			fileMap.put("fileSize", files[i].getSize());
			log.info(dir+SystemUtil.getFileSeparator()+fileName);
			list.add(fileMap);
		}
		return list;	
	}
	/**
	 * 
	 * @Description:文件上传  公共方法(单文件)
	 * @param file  上传文件对象
	 * @param filePath   文件存放地址。 如果为空，默认上传到微信的配置路径下面，
	 * @param refileName   重命名文件名称,不需要加后缀。
	 * 	eg：根路径+path 
	 * @return  map 格式信息     ，键值解释--- 
	 * 			error : 错误编码，0、表示上传成功。
	 * 			path:Web路径。
	 * 			fileOriginalName：源文件名。
	 * 			rootPath：文件全路径。
	 * 			fileSize：文件大小，单位字节。
	 */		
	protected Map fileUpload(CommonsMultipartFile file,String filePath,String refileName){
		//设置文件路径
        String dir = "";
        String webDir ="";
        if(filePath!=null && !"".equals(filePath)){
    		dir = getSTF()+SystemUtil.getFileSeparator() + filePath;
        	webDir = getRootPath()+filePath.replaceAll("[\\\\]+", "/");
        }else{
    		dir = getSTF()+SystemUtil.getFileSeparator() +getContentFilePath();
        	webDir = getRootPath()+getContentFilePath().replaceAll("[\\\\]+", "/");
        }
		log.info(webDir);
		//创建目录
		try {
			FileUtil.createFileRec(dir);
		} catch (Exception e) {
			log.error("创建路径："+dir+"失败");
			return null;
		}
		//保存图片
		Map fileMap = new HashMap();
		String fileOriginalName = file.getOriginalFilename();
		String suf = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));	        
		String fileName = "";
		if(StringUtils.isNotBlank(refileName)){
			fileName = refileName+suf;
		}else{
			fileName = Sequence.getSequence()+suf;
		}		
		if(!copyFile(new File(dir+SystemUtil.getFileSeparator()+fileName), file)){
			log.error("保存文件失败");
			return null;
		}		
		fileMap.put("error", 0);
		fileMap.put("path",dir+SystemUtil.getFileSeparator()+fileName);
		fileMap.put("fileOriginalName", fileOriginalName);
		fileMap.put("webPath", webDir + SystemUtil.getFileSeparator()+fileName);
		fileMap.put("fileSize", file.getSize());
		log.info(dir+SystemUtil.getFileSeparator()+fileName);
		return fileMap;	
	}
	
	/**
	 * 复制文件方法,出入源文件为springMVC提供的对象
	 * @param target
	 * @param multipartFile
	 * @return
	 */
	protected boolean copyFile(File target, MultipartFile multipartFile) {
		try {
			InputStream ips = multipartFile.getInputStream();
			OutputStream ops = new FileOutputStream(target);
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = ips.read(buffer)) > 0) {
				ops.write(buffer, 0, length);
			}
			ops.close();
			ips.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 获取根路径地址
	 * @return
	 */
	protected String getRootRealPath(){
		return request.getRealPath("");
	}
	
	/**
	 * 得到静态资源的路径
	 * @return
	 */
	public String getSTF(){
		String path = "";
		if(SystemUtil.isWindows()){
			path = PropertyUtil.getPropertiesValue("system", "WindowResouce");
		}else{
			path = PropertyUtil.getPropertiesValue("system", "LinuxResounce");
		}
		return path + SystemUtil.getFileSeparator();
	}
	
	/**
	 * 得到静态资源的相对根路径
	 * @return
	 */
	public String getRootPath(){
		String path = "";
		if(SystemUtil.isWindows()){
			path = PropertyUtil.getPropertiesValue("system", "WindowResouce");
		}else{
			path = PropertyUtil.getPropertiesValue("system", "LinuxResounce");
		}
		return path + SystemUtil.getFileSeparator();
	}
	
	/**
	 * 得到静态资源相对系统的网络路径
	 * @return
	 */
	public String getWebStF(){
		String path = "";
		if(SystemUtil.isWindows()){
			path = PropertyUtil.getPropertiesValue("system", "WindowResouce").replace("[\\\\]+", "/");
		}else{
			path = PropertyUtil.getPropertiesValue("system", "LinuxResounce").replace("[\\\\]+", "/");
		}
		return getRequestPath()+getContextPath()+"/"+path;
	}
	
	/**
	 * 得到用户访问当前系统路径
	 * @return
	 */
	public String getRequestPath(){
 		Pattern pattern = Pattern.compile("(http|https):\\/\\/[\\d\\w\\.]+(:\\d+)?");
 		Matcher matcher = pattern.matcher( request.getRequestURL().toString()); 
 		matcher.find();
 		matcher.group();
		return matcher.group();
	}
	
	/**
	 * 获得当前路径方法
	 * @return
	 */
	protected String getContextPath(){
		return request.getContextPath();
	}
}
