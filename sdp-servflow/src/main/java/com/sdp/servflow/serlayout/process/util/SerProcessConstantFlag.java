package com.sdp.servflow.serlayout.process.util;

public class SerProcessConstantFlag {
	
	//是否更新版本信息
	public final static String VERSIONFLAG_NEWVERSION = "newVersion";
	public final static String VERSIONFLAG_OLDVERSION = "oldVersion";
	
	//更新或添加标识
	public final static String ADDUPDATE_ADD = "add";
	public final static String ADDUPDATE_UPDATE = "update";
	
	//商店推送标识
	public final static String PUSHFLAG_ADD = "0"; //添加（未推送）
	public final static String PUSHFLAG_SUCCESS = "1"; //已推送
	public final static String PUSHFLAG_UPDATE = "2";//更新推送

}
