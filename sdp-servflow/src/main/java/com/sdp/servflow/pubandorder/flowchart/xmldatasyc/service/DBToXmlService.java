package com.sdp.servflow.pubandorder.flowchart.xmldatasyc.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface DBToXmlService {
    
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
    public void downloadFile(HttpServletResponse res,String serName) ;

}
