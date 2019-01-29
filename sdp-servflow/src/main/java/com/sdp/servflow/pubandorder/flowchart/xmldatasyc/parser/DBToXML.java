package com.sdp.servflow.pubandorder.flowchart.xmldatasyc.parser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;

public class DBToXML {
	
	private PreparedStatement preStmt = null;
	private ResultSet rs = null;
	private String colName = "";
	private String result = "";
	
	public Document saveXmlFile(Connection conn,Map<String,String> map,Document doc) {		
		
		try {
			preStmt = conn.prepareStatement(map.get("sql"));
			rs = preStmt.executeQuery();
			
			Element relem = new Element(map.get("elementName"));
			doc.getRootElement().addContent(relem);
			
			//获取字段名
			ResultSetMetaData rsmd = rs.getMetaData();			
			
			int i = 0;
			//获取字段数
			int columnsNum = rsmd.getColumnCount();
			
			while(rs.next()) {
				
				//创建元素，生成dom树，如果有其他节点集合需要穿件多个节点，这里只有一个
				Element elem = new Element("row");
				relem.addContent(elem);
				
				for(i=1;i<=columnsNum;i++) {
					colName = rs.getString(i);
					//将表中字段值封装到xml元素中
					Element element = new Element(rsmd.getColumnName(i)).setText(colName);
					elem.addContent(element);
				}
			}
			
			result = "导出成功！！";
			
			/*if(fos != null) {
				fos.close();
				fos = null;
			}*/
			if(rs != null) {
				rs.close();
				rs = null;
			}
			if(preStmt != null) {
				preStmt.close();
				preStmt = null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			result = "导出失败！！";
		} 
		/*catch (FileNotFoundException e) {
			e.printStackTrace();
			result = "导出失败！！";
		} catch (IOException e) {
			e.printStackTrace();
			result = "导出失败！！";
		}*/
		
		return doc;
	}

}
