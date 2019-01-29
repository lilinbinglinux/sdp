package com.sdp.servflow.pubandorder.flowchart.xmldatasyc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.sdp.servflow.pubandorder.flowchart.xmldatasyc.controller.JdbcConfig;

public class Connecter {
    private Connection conn = null;
	
	public Connection connMySql() throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
			    JdbcConfig.getConfigValue(JdbcConfig.JDBCURL), 
			    JdbcConfig.getConfigValue(JdbcConfig.JDBCUSERNAME), 
			    JdbcConfig.getConfigValue(JdbcConfig.JDBCPASSWORD)
			    );
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public Connection connOracle() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "scott", "tiger");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
