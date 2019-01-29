package com.sdp.servflow.pubandorder.flowchart.xmldatasyc.controller;

import java.util.Properties;

public class JdbcConfig {
    
    
    public static final String JDBCURL = "jdbc.url";
    public static final String JDBCUSERNAME = "jdbc.username"; //正式环境地址
    public static final String JDBCPASSWORD = "jdbc.password"; //产品名称
    
    private static String FILE = "jdbc.properties";
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
