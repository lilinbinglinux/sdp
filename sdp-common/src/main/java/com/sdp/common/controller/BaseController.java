package com.sdp.common.controller;

public class BaseController {
	private static String theme = "default";
	public String themePath(String path){
		return "theme/"+theme+"/"+path;
	}
}
