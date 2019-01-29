package com.sdp.servflow.pubandorder.util;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * 判断字符串格式类型
 *
 * @author ZY
 * @version 2017年7月26日
 * @see StringTypeUtil
 * @since
 */
public class StringTypeUtil {
    
    public static final String Number = "Number";
    public static final String Json = "Json";
    public static final String Xml = "Xml";
    public static final String String = "String";
    public static final String isNull = "isNull";
    
    /**
     * 
     * Description:判断字符串格式是数字，Json，xml和普通字符串 
     * 
     *@param string
     *@return String
     *
     * @see
     */
    public static String getType(String string) {
        if(StringUtils.isBlank(string))
            return isNull;
        else if (isNumber(string))
                return Number;
        else if (isJson(string))
                return Json;
        else if (isXML(string))
                return Xml;
        else
                return String;
    }
        
        /**
        * 判断字符串是否是数字
        * @return boolean
        */
    private static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }
    
        /**
        * 判断字符串是否是整数
        * @return boolean
        */
    private static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } 
        catch (NumberFormatException e) {
            return false;
        }
    }

        /**
        * 判断字符串是否是浮点数
        * @return boolean
        */
    private static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                    return true;
            return false;
        } 
        catch (NumberFormatException e) {
            return false;
        }
    }

        /**
        * 判断是否是json结构
        * @return boolean
        */
    private static boolean isJson(String value) {
        try {
            new JSONObject(value);
        } 
        catch (JSONException e) {
            return false;
        }
        return true;
    }

        /**
        * 判断是否是xml结构
        * @return boolean
        */
    private static boolean isXML(String value) {
        try {
            DocumentHelper.parseText(value);
        } 
        catch (DocumentException e) {
            return false;
        }
        return true;
    } 
    

}
