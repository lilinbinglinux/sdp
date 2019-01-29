package com.sdp.servflow.logSer.util;

import com.sdp.servflow.logSer.InstanceParam;

public class ToolUtils {
	public static String getSpecialStitching(String... args){
		if(args == null || args.length == 0){
			return null;
		}
		String returnMsg="";
		int size = args.length;
		int i=1;
		for(String arg:args){
			returnMsg += arg;
			if(i!=size){
				returnMsg += InstanceParam.SPECIALCHAR;
			}
			i++;
		}
		return returnMsg;
	}
}
