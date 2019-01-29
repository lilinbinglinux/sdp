package com.sdp.servflow.pubandorder.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import sun.misc.BASE64Encoder;

/**
 * 
 * 生产加密令牌
 *
 * @author ZY
 * @version 2017年7月21日
 * @see TokenProcessor
 * @since
 */
public class TokenProcessor{
    
    private TokenProcessor(){}
    
    /**
     * 实例化
     */
    private static TokenProcessor instance = new TokenProcessor();
    public static TokenProcessor getInstance(){
        return instance;
    }
    
    /**
     * 
     * Description:token生成算法，根据当前时间进行64位加密 
     * 
     *@return String
     *
     * @see
     */
    public String generateTokeCode(){
        String value = System.currentTimeMillis()+new Random().nextInt()+"";
        
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
        Date date = new Date(currentTime);

        //获取数据指纹，指纹是唯一的
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] b = md.digest(value.getBytes());//产生数据的指纹
            //Base64编码
            BASE64Encoder be = new BASE64Encoder();
            be.encode(b);
            
            //用request.getParameter获取参数时识别+为空格，所以进行替换
            if(be.encode(b).contains("+")){
                be.encode(b).replace("+", "A");
            }
            return be.encode(b);//制定一个编码
        } 
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }
    
}