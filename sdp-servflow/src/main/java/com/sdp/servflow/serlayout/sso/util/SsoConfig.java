package com.sdp.servflow.serlayout.sso.util;

import java.util.Properties;

public class SsoConfig {
	
    public static final String PREENVUTL = "preEnvurl.url";//测试环境地址
    public static final String PROENVUTL = "proEnvurl.url"; //正式环境地址
    public static final String PRONAME = "proname"; //产品名称
    public static final String PROCHNAME = "proChname"; //产品名称
    public static final String FILESAVEPATH = "filesavepath"; //产品名称
    
    
	private static String FILE = "ssoenv.properties";
	private static Properties props = new Properties();

	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(FILE));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回键值
	 * 
	 * @param Key
	 * @return
	 */
	public static String getConfigValue(String Key) {
		String value = "";
		try {
			if (props != null) {
				value = props.getProperty(Key);
				value = value != null ? value : "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
}
