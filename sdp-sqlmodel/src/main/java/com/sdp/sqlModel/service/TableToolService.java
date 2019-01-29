package com.sdp.sqlModel.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.sdp.sqlModel.entity.SqlDataRes;
import com.sdp.sqlModel.entity.SqlDataResType;
import com.sdp.util.DataBaseUtil;

/**
 * 
 * @author BONC
 *
 */
@Service
public class TableToolService {

	private final static Logger log= LoggerFactory.getLogger(TableToolService.class);
	
	/**
	 * 
	 * @param sqlDataRes
	 * @param sqlData
	 * @param sqlDataResType
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String executeDdl(SqlDataRes sqlDataRes,String sqlData,SqlDataResType sqlDataResType){
		log.info("将要执行的sql语句是："+sqlData);
		Date				dateBegin = new Date();
		Connection connection = null;
		Statement statement =null;
		String		bReturn = "true";
		String url;
		String username;
		String password;
		try{
			url = sqlDataRes.getDataResUrl();
			username = sqlDataRes.getUsername();
			password = sqlDataRes.getPassword();
			log.info("url:" + url + " username:"+username + " password:"+password);
			Class.forName(sqlDataResType.getDataDriver());
			connection = DriverManager.getConnection(sqlDataRes.getDataResUrl(),sqlDataRes.getUsername(),sqlDataRes.getPassword());
//			connection =(Connection)DriverManager.getConnection(url,username,password);
			statement = connection.createStatement();
			//开启事务
			DataBaseUtil.beginTransaction(connection);
			statement.execute(sqlData);
			//提交事务
			DataBaseUtil.commitTransaction(connection);
		}catch(Exception e){
			log.info("在MYSQL OR ORCALE OR XCLOUD上执行DDL语句出错 !!!");
//			bReturn = "false";
			//回滚事务
			DataBaseUtil.rollBackTransaction(connection);
			bReturn = getErrorInfoFromException(e);
		}finally
		{
			if(statement!=null)
			{
				try{
					statement.close();
					connection.close();
				}catch(Exception e){}
			}
		}	
		if(bReturn.equals("true")){
			Date		dateEnd = new Date();
			log.info(" MYSQL OR ORCALE DDL  OR XCLOUD,SQL:{},DURATON TIME:{}",sqlData,dateEnd.getTime()-dateBegin.getTime());
		}
		return bReturn;
	}
	public static String getErrorInfoFromException(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "\r\n" + sw.toString() + "\r\n";
		} catch (Exception e2) {
			return "bad getErrorInfoFromException";
		}
	}
	/**
	 * 
	 * @param sqlDataRes
	 * @param sqlData
	 * @param sqlDataResType
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String executeDdles(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType,List<String> modifyList){
		log.info("将要执行的sql语句是："+JSON.toJSONString(modifyList));
		Date				dateBegin = new Date();
		Connection connection = null;
		Statement statement =null;
		String		bReturn = "true";
		try{
			Class.forName(sqlDataResType.getDataDriver());
			connection = DriverManager.getConnection(sqlDataRes.getDataResUrl(),sqlDataRes.getUsername(),sqlDataRes.getPassword());
			statement = connection.createStatement();
			//开启事务
			DataBaseUtil.beginTransaction(connection);
			connection.setAutoCommit(false);
			for (String string : modifyList) {
				log.info("即将执行sql:"+string);
				statement.execute(string);
			}
			//提交事务
			DataBaseUtil.commitTransaction(connection);
		}catch(Exception e){
			log.info("在MYSQL OR ORCALE OR XCLOUD上执行DDL语句出错 !!!");
//			bReturn = false;
			//回滚事务
			DataBaseUtil.rollBackTransaction(connection);
//			e.printStackTrace();
			bReturn = getErrorInfoFromException(e);
		}finally
		{
			if(statement!=null)
			{
				try{
					statement.close();
					connection.close();
				}catch(Exception e){}
			}
		}	
		if(bReturn.equals("true")){
			Date		dateEnd = new Date();
			log.info(" MYSQL OR ORCALE DDL  OR XCLOUD,SQL:{},DURATON TIME:{}",JSON.toJSONString(modifyList),dateEnd.getTime()-dateBegin.getTime());
		}
		return bReturn;
	}
	
	/**
	 * 
	 * @param sqlDataRes
	 * @param sqlData
	 * @param sqlDataResType
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String getUserGroupInfo(SqlDataRes sqlDataRes,String sqlData,SqlDataResType sqlDataResType) {
		Statement statement = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			Class.forName(sqlDataResType.getDataDriver());
			connection = DriverManager.getConnection(sqlDataRes.getDataResUrl(),sqlDataRes.getUsername(),sqlDataRes.getPassword());
			statement = connection.createStatement();
			rs = statement.executeQuery(sqlData);
			while (rs.next()) {
				return rs.getString("name");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		// return ordermapper.getUserGroupInfo(id);
	}
	public static Connection getCon(SqlDataRes sqlDataRes,SqlDataResType sqlDataResType) throws SQLException{  
        try {  
            Class.forName(sqlDataResType.getDataDriver());  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
            return null;  
        }  
        return DriverManager.getConnection(sqlDataRes.getDataResUrl(),sqlDataRes.getUsername(),sqlDataRes.getPassword());      
    } 
}
