package com.sdp.servflow.pubandorder.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * 1.默认限定位数的数字递增
   2.在限定位数数字达到全部为9的时候，用字母替换最后一位，数字归0再次递增
   3.当字母长度为限定位数长度并且字母都为Z的时候，限定长度加1
 *
 * @author ZY
 * @version 2017年7月24日
 * @see SeriNumber
 * @since
 */
public class SeriNumber {
    public static String producerNum(int len,Integer i,String driver){  
        String dr = "";  
        AtomicInteger z = new AtomicInteger(i);  
        z.getAndIncrement();  
        if(z.toString().length()>(len-(driver!=null?driver.length():0))){  
            dr = driverCheck(driver,len);  
            if(dr.equals(".N")){//如超出限定长度并字母都为Z的时候，限定长度加1，dr重新开始，默认为空  
                len++;  
                dr = "";  
            }else{  
                z.set(1);  
            }  
        }else{  
            dr = driver;  
        }  
        
        if(dr.contains("?")){
            dr.replace("?", "#");
        }
        
        if(dr.contains("/")){
            dr.replace("/", "#");
        }
        
        if(dr.length()==len){  
            return dr;
        }else{
            dr = String.format("%0"+(len-dr.length())+"d", z.intValue())+dr;
            return dr;
        }
        
    }  
      
    /** 
     * 字母有效检查 
     * 1.检查字母是否都为Z 
     * 2.检查字母长度 
     * @param driver 
     * @param len 
     * @return 
     */  
    public static String driverCheck(String driver,int len){  
        char[] charArray = driver.toCharArray();  
        AtomicInteger z = new AtomicInteger(0);  
          
        for (char c : charArray) {  
            if(c=='Z'){  
                z.getAndIncrement();  
            }  
        }  
  
        if(z.intValue()==driver.length() && z.intValue()==len){//如所有字母都为Z，并且长度达到限定长度，返回.N  
            return ".N";  
        }else if(z.intValue()==driver.length() && z.intValue()<len){//如果所有字母都为Z，但长度未达到限定长度，则在调用字母递增方法之前加入@用以递增A  
            return driver("@"+driver);  
        }else{//以上两个条件都不满足，则直接递增  
            return driver(driver);  
        }  
          
    }  
      
    /** 
     * 字母递增 
     * @param driver 
     * @return 
     */  
    public static String driver(String driver){  
        if(driver!=null && driver.length()>0){  
            char[] charArray = driver.toCharArray();  
            AtomicInteger z = new AtomicInteger(0);  
            for(int i = charArray.length-1;i>-1;i--){  
                if(charArray[i]=='Z'){  
                    z.set(z.incrementAndGet());  
                }else{  
                    if(z.intValue()>0 || i==charArray.length-1){  
                        AtomicInteger atomic = new AtomicInteger(charArray[i]);  
                        charArray[i]=(char) atomic.incrementAndGet();  
                        z.set(0);  
                    }  
                }  
            }  
              
            return String.valueOf(charArray);  
        }else{  
            return "A";  
        }  
    }  
}
