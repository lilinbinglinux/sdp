package com.sdp.servflow.serlayout.process.util;

import java.io.File;
import java.text.SimpleDateFormat;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 导入导出文件定时清理任务
 * 
 * @author zy
 * @Date 2018年1月3日
 */
@Component
@ConfigurationProperties(prefix = "system.conf", ignoreUnknownFields = true)
public class FileCleanerSchedule {
	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	 //导出文件的临时文件夹（定时任务定时清理）
    private String flowchartfiles;

    public String getFlowchartfiles() {
        return flowchartfiles;
    }

    public void setFlowchartfiles(String flowchartfiles) {
        this.flowchartfiles = flowchartfiles;
    }
    
    //导入文件临时文件夹（定时任务定时清理）
    private String filesavepath;

    public String getFilesavepath() {
        return filesavepath;
    }

    public void setFilesavepath(String filesavepath) {
        this.filesavepath = filesavepath;
    }
    
    /*
     * 每月1日0点
     * */
    @Scheduled(cron=" 0 0 0 1 * ?")
    public void timerRate() {
    		System.out.println("--------------------start clear ftpfile----------------");
    		this.clearFiles(getFlowchartfiles());
    		this.clearFiles(getFilesavepath());
    		System.out.println("--------------------end clear ftpfile----------------");
    }
    
  //删除文件和目录
    private void clearFiles(String workspaceRootPath){
         File file = new File(workspaceRootPath);
         if(file.exists()){
              deleteFile(file,workspaceRootPath);
         }
    }
    private void deleteFile(File file,String workspaceRootPath){
         if(file.isDirectory()){
              File[] files = file.listFiles();
              for(int i=0; i<files.length; i++){
                   deleteFile(files[i],workspaceRootPath);
              }
         }
         if(!file.getPath().equals(workspaceRootPath)) {
        	 	file.delete();
         }
         
    }
}
