package com.sdp.servflow.pubandorder.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 服务端
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see TServer
 * @since
 */
public class TServer2 {

    public static void main(String[] args) throws IOException { 
       InputStream dataInputStream = null;
       OutputStream dataOutputStream = null;
       ServerSocket serverSocket = new ServerSocket(8888);
       while (true) {
        try {
            // 启动socket服务端
            /**
             * 加上while(true),让服务端一直保持运行，负责当有一个客户端连接成功以后，服务端就停止运行了。
             */
                    // 监听客户端连接,accept()方法是阻塞方法，如果没有客户端连接，其后的代码不会执行
                    Socket socket = serverSocket.accept();
                    InputStream strean =    socket.getInputStream();
                    byte[] b = new byte[4096];
                    strean.read(b);
                    System.out.println("from client..." + new String(b));

                    //查询天气并返回查询结果
                    JSONObject json = new JSONObject();
                    json.put("weather", "晴天");
                     dataOutputStream = socket.getOutputStream();
                    String result = "晴天";
                    dataOutputStream.write(json.toString().getBytes("utf-8"));
                    dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(dataOutputStream!=null)
                    dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(dataInputStream!=null)
                    dataInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }

}