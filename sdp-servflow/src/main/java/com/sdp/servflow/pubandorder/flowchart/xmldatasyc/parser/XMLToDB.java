package com.sdp.servflow.pubandorder.flowchart.xmldatasyc.parser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.sdp.servflow.pubandorder.flowchart.xmldatasyc.dao.TableColumnName;

public class XMLToDB {
	
	private PreparedStatement preStmt = null;
	private String rs = "";
	
	public String saveXMLToDB(String file,Connection conn,Map<String,Object> map) {		
		
		try {
			//指定jdom使用的解析器，这里使用默认解析器
			SAXBuilder sb = new SAXBuilder();
			//得到xml文件
			Document doc = sb.build(file);
			//得到xml的根元素
			Element root = doc.getRootElement();
			Element xroot = root.getChild(map.get("elementName").toString());
			
			Element elem = null;
			//得到根节点下元素的集合（这里只有一个子节点），如果包含多个子节点元素，还需要得到其他子节点集合
			List list = xroot.getChildren("row");
			
			for(int i=0;i<list.size();i++) {
			    //清空
			    //buffer.setLength(0);
			    elem = (Element)list.get(i);
			    
			    List<String> columnNamevalues = TableColumnName.getColumnNames(map.get("tablename").toString(), conn);
			    
			    preStmt = conn.prepareStatement(map.get("sql").toString());
			    int sort = 1;
			    
				for(String columnName:columnNamevalues){
				    String columnNamevalue = elem.getChildText(columnName);
				    preStmt.setString(sort, columnNamevalue);
				    sort++;
				}
				
                //存入数据库
                preStmt.addBatch();
                preStmt.executeBatch();
				rs = "导入成功！！";
				
			}
			
			//关闭资源
			if(preStmt != null) {
				preStmt.close();
				preStmt = null;
			}
						
		} catch (JDOMException e) {
			e.printStackTrace();
			rs = "导入失败！！";
		} catch (IOException e) {
			e.printStackTrace();
			rs = "导入失败！！";
		} catch (SQLException e) {
			e.printStackTrace();
			rs = "导入失败！！";
		}
		return rs;
	}
}
