package com.sdp.cop.octopus.util.notify;

import org.apache.commons.lang.StringUtils;


/**
 * 短信发送信息
 * @author ke_wang
 * @since 2016-05-25
 */
public class SMSService{

	/**
	 * jdbcTemplate对象
	 * 功能：向数据库中插入要发送的短信内容
	 */
   // private JdbcTemplate jdbcTemplate;
	
	/**
	 * dbUser,用户发送短信的内容存储
	 */
    private String dbUser;
	
    /**
     * dbLink
     */
    private String dbLink;
	
    /**
     * 
     * @param sms SMS
     * @return int 
     * @throws Exception 数据库插入异常
     */
/*    public int sendSMS(SMS sms) throws Exception {
    	String sql = "insert into " + this.validate("dm_sms_log") + " (log_id, prov_id, sms_serv_number, sms_content, log_time, succ_flag) "
				+ "values(?, '', ?, ?, sysdate, '00')";
		
    	return jdbcTemplate.update(sql,new Object[] {"11", sms.getSms_serv_number(), sms.getSms_content()});
    }*/
	
	/**
	 * 验证参数
	 * @param tableName String
	 * @return finalSql String
	 */
    private String validate(String tableName) {
    	String finalSql = tableName;
    	if(StringUtils.isNotBlank(dbLink)){
            finalSql =  finalSql + dbLink;
    	}
    	if(StringUtils.isNotBlank(dbUser)){
            finalSql =  dbUser + "." + finalSql;
    	}
    	return finalSql;
    }
	
/*    public JdbcTemplate getJdbcTemplate() {
    	return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    	this.jdbcTemplate = jdbcTemplate;
    }
*/
    public String getDbUser() {
    	return dbUser;
    }

    public void setDbUser(String dbUser) {
    	this.dbUser = dbUser;
    }

    public String getDbLink() {
    	return dbLink;
    }

    public void setDbLink(String dbLink) {
    	this.dbLink = dbLink;
    }
}
