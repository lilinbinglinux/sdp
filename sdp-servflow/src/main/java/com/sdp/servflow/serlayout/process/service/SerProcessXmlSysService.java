package com.sdp.servflow.serlayout.process.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

public interface SerProcessXmlSysService {
    
    /**
     * 
     * Description:从数据库中导出流程图模板  
     * 
     *@param flowchartId
     *@return String
     *
     * @see
     */
    public Map<String, Object> exportfile(String serVerId,String serId,String serName) throws Exception;
    /**
     * 
     * Description:文件下载
     * 
     *@param res
     *@param fileName
     *@return String
     *
     * @see
     */
    public void downloadFile(HttpServletResponse res,String fileName);
    
    /**
     * 文件上传
     * @param request
     * @param response
     * @param fileName
     */
    @Transactional(rollbackFor=RuntimeException.class)
	public Object uploadFile(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException;
	
	/**
	 * 数据库对应bean处理
	 * @param file
	 * @param fileName
	 * @return
	 * @throws RuntimeException
	 */
	public String dbHandler(File file,String fileName) throws Exception;;

}
