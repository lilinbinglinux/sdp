package com.sdp.servflow.pubandorder.format.imp;

import com.sdp.servflow.pubandorder.util.IContants;
import com.sdp.servflow.pubandorder.util.ParseUtil;
/**
 * 
 * xml格式转换
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see XmlFormat
 * @since
 */
public class XmlFormat {
    /**
     * 
     * Description: json转为xml格式
     * 
     *@param json
     *@return String
     *
     * @see
     */
    public static String jsonToOther(String json) {
        return ParseUtil.jsonToXML(json);
    }

	/**
	 * 
	 * Description: xml转为json格式
	 * 
	 *@param json
	 *@return String
	 *
	 * @see
	 */
    public static String otherToJson(String xml) {
        return ParseUtil.xmlToJson(xml);
    }
    /**
     * 
     * Description: xml和json互转
     * 
     *@param json
     *@return String
     *
     * @see
     */
    public static String format(String resourceString,String resouceFormat,String targetFormat) {
        
        if(resouceFormat.equals(targetFormat))
        {
            return resourceString;
        }
        
        if(IContants.JSON.equals(resouceFormat)&&IContants.XML.equals(targetFormat)) {
            return ParseUtil.jsonToXML(resourceString);
        }
        
        if(IContants.XML.equals(resouceFormat)&&IContants.JSON.equals(targetFormat)) {
            return ParseUtil.xmlToJson(resourceString);
        }
        return resourceString;
    }

}
