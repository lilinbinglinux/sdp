package com.sdp.servflow.pubandorder.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.log4j.Logger;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

/***
 * 
 * 连接ftp时的操作类
 *
 * @author 任壮
 * @version 2017年9月11日
 * @see FtpUtil
 * @since
 */
public class FtpUtil {
    
    private static final Logger logger = Logger.getLogger(FtpUtil.class);
    /***
     * 连接ftp服务器
     * */
    public static FtpClient connectFTP(String url, int port, String username, String password) {  
        //创建ftp  
        FtpClient ftp = null;  
        try {  
            //创建地址  
            SocketAddress addr = new InetSocketAddress(url, port);  
            //连接  
            ftp = FtpClient.create();  
            ftp.connect(addr);  
            //登陆  
            ftp.login(username, password.toCharArray());  
            ftp.setBinaryType();  
        } catch (FtpProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return ftp;  
    } 
    
    /***
     * 
     * Description: 将文件从本地上传到ftp服务器中
     * 
     *@param localFile
     *@param ftpFile
     *@param ftp void
     *
     * @see
     */
    public static void upload(String localFile, String ftpFile, FtpClient ftp) {  
        OutputStream os = null;  
        FileInputStream fis = null;  
        try {  
            // 将ftp文件加入输出流中。输出到ftp上  
            os = ftp.putFileStream(ftpFile);  
            File file = new File(localFile);  
  
            // 创建一个缓冲区  
            fis = new FileInputStream(file);  
            byte[] bytes = new byte[1024];  
            int c;  
            while((c = fis.read(bytes)) != -1){  
                os.write(bytes, 0, c);  
            }  
            logger.info("upload success!!");
        } catch (FtpProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if(os!=null) {  
                    os.close();  
                }  
                if(fis!=null) {  
                    fis.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    /***
     * 
     * Description: 从ftp服务器中下载文件到本地
     * 
     *@param localFile
     *@param ftpFile
     *@param ftp void
     *
     * @see
     */
    public static void download(String localFile, String ftpFile, FtpClient ftp) {  
        InputStream is = null;  
        FileOutputStream fos = null;  
        try {  
            // 获取ftp上的文件  
            is = ftp.getFileStream(ftpFile);  
            File file = new File(localFile);  
            byte[] bytes = new byte[1024];  
            int i;  
            fos = new FileOutputStream(file);  
            while((i = is.read(bytes)) != -1){  
                fos.write(bytes, 0, i);  
            }  
            logger.info("download success!!");
  
        } catch (FtpProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if(fos!=null) {  
                    fos.close();  
                }  
                if(is!=null){  
                    is.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    
}
