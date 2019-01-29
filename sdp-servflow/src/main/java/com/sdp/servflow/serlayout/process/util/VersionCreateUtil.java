package com.sdp.servflow.serlayout.process.util;

import org.apache.commons.lang.NumberUtils;

public class VersionCreateUtil {
	
	/**
	    * 版本号累加算法
	    * 
	    * @return
	    */
	   public static String genNewVersion(String oldVersion) {
	        String sys_version_tmp = oldVersion.replaceAll("\\.", "");
	        if (!NumberUtils.isNumber(sys_version_tmp)) {
	            return oldVersion;
	        }
	        long sysVersionNum = Long.valueOf(sys_version_tmp);
	        sysVersionNum += 1;
	        String sys_version = "";
	        for (int i = 0; i < (sysVersionNum + "").length(); i++) {
	            if (i==0) {
	                sys_version += (sysVersionNum + "").charAt(i);
	            } else {
	                sys_version += "." + (sysVersionNum + "").charAt(i);
	            }
	       }
	        return sys_version;
	   }

}
