package com.sdp.servflow.pubandorder.protocol;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.apache.log4j.Logger;


/***
 * socket请求的工具类
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see HttpUtil
 * @since
 */
public class SocketUtil {
    /**
     * 连接时间
     */
    private static int CON_TIME_OUT = 60000;
    /**
     * log日志
     */
    @SuppressWarnings("unused")
    private static Logger LOG = Logger.getLogger(SocketUtil.class);

    /**
     * socket调用远程服务
     * 
     * @param ipAddr
     *            调用的服务端ip
     * @param port
     *            调用的服务端端口
     * @param command
     *            发送的byte数组
     * @param charset
     *            字符集
     * @return 调用socket返回的结果
     */
    public static String remoteOperate(String ipAddr, int port, String command, String charset) {
        String result = null;
        Socket socket = null;
        OutputStream out = null;
        InputStream input = null;
        try {
            // 创建Socket连接
            socket = new Socket(ipAddr, port);
            // 设置超时时间
            socket.setSoTimeout(CON_TIME_OUT);
            // 接收数据
            input = socket.getInputStream();
            // 得到输出流
            out = socket.getOutputStream();
            // 发送数据
            out.write(command.getBytes(charset));
            out.flush();
            // 得到输入流
            byte[] b = new byte[1024];
            int temp=0;
            int len=0;  
            while((temp=input.read())!=-1){//-1为文件读完的标志  
                // 对接收的byte数组扩容（参考ArrayList和hashmap的扩容方式）
                int oldLength = b.length;
                if(len >= oldLength) {
                    byte[]   newByte = new byte[oldLength<<1];
                    System.arraycopy(b, 0, newByte, 0,oldLength);
                    b= newByte; 
                    newByte = null;
                }
                b[len]=(byte) temp;  
                len++;  
            }  
            result = new String(b, 0,len,charset);
            b = null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (socket != null) socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (input != null) input.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) out.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * socket调用远程服务
     * 
     * @param ipAddr
     *            调用的服务端ip
     * @param port
     *            调用的服务端端口
     * @param command
     *            发送的字符串
     * @return 调用socket返回的结果
     */
    public static String remoteOperate(String ipAddr, int port, String command) {
        String result = null;
        Socket socket = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        try {
            // 创建Socket连接
            socket = new Socket(ipAddr, port);
            // 设置超时时间
            socket.setSoTimeout(CON_TIME_OUT);
            // 得到输出流
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            // 发送数据
            dataOutputStream.writeUTF(command);
            // 接收数据
            dataInputStream = new DataInputStream(socket.getInputStream());
            // 得到输入流
            result = dataInputStream.readUTF();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (socket != null) socket.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (dataInputStream != null) dataInputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (dataOutputStream != null) dataOutputStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
