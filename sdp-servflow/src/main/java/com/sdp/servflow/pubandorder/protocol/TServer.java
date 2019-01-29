package com.sdp.servflow.pubandorder.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
public class TServer {

    public static void main(String[] args) {
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;
        while (true) {
        try {
            // 启动socket服务端
            ServerSocket serverSocket = new ServerSocket(8888);
            /**
             * 加上while(true),让服务端一直保持运行，负责当有一个客户端连接成功以后，服务端就停止运行了。
             */
                    // 监听客户端连接,accept()方法是阻塞方法，如果没有客户端连接，其后的代码不会执行
                    Socket socket = serverSocket.accept();
                    System.out.println("from client阿什顿饭 ..." );
                    dataInputStream = new DataInputStream(
                            socket.getInputStream());
                    dataOutputStream = new DataOutputStream(
                            socket.getOutputStream());
                    //获得客户端传来的城市名称
                    String cityName = dataInputStream.readUTF();
                    System.out.println("from client..." + cityName);

                    //查询天气并返回查询结果
                    JSONObject json = new JSONObject();
                    json.put("weather", "晴天");
                    
                    String result = "晴天";
                    dataOutputStream.writeUTF(result);
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