package com.sdp.cop.octopus.util.oauth;

/**
 * 短信消息实体
 * @author ke_wang
 *@since 2016-05-25
 */
public class SMS {

	/**
	 * mobile
	 */
    private String sms_serv_number;
	
    /**
     * content
     */
    private String sms_content;
    
    public SMS() {
        
    }

    public SMS(String mobile, String content) {
    	this.sms_serv_number = mobile;
    	this.sms_content = content;
    }

    public String getSms_serv_number() {
        return sms_serv_number;
    }

    public void setSms_serv_number(String sms_serv_number) {
        this.sms_serv_number = sms_serv_number;
    }

    public String getSms_content() {
        return sms_content;
    }

    public void setSms_content(String sms_content) {
        this.sms_content = sms_content;
    }
}
