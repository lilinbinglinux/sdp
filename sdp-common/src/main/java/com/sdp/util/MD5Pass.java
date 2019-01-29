package com.sdp.util;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sdp.frame.util.MD5Util;

/**
 * 
 * MD5加密
 *
 * @author ZY
 * @version 2017年9月21日
 * @see MD5Pass
 * @since
 */
public class MD5Pass {
    
    private static Log log = LogFactory.getLog(MD5Pass.class);
    
    public static String getMD5Passwd(String orgPasswd){
        try {
            return MD5Util.Bit32(orgPasswd);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("密码转换失败！", e);
            return null;
        }
    }

}
