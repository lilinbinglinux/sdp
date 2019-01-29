package com.sdp.servflow.pubandorder.util;

import org.springframework.boot.context.properties.ConfigurationProperties;


//import com.sun.istack.internal.NotNull;
//locations = "classpath:application.properties" ,
@ConfigurationProperties(prefix = "system.conf", ignoreUnknownFields = true)
public class Properties {

    //项目所在的服务器中的根目录位置
    private String filesavepath;

    public String getFilesavepath() {
        return filesavepath;
    }

    public void setFilesavepath(String filesavepath) {
        this.filesavepath = filesavepath;
    }
    //@NotNull
    private Ftp ftp;
    public static class Ftp {  
        //ftp文件路径
        private String filePath;  
        private String url;  
        private int port;  
        private String username;  
        private String password;
        public String getFilePath() {
            return filePath;
        }
        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public int getPort() {
            return port;
        }
        public void setPort(int port) {
            this.port = port;
        }
        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }  
    }
    public Ftp getFtp() {
        return ftp;
    }

    public void setFtp(Ftp ftp) {
        this.ftp = ftp;
    }
}
