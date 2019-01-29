package com.sdp.servflow.pubandorder.flowchart.xmldatasyc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableColumnName {
    
    public static List<String> getColumnNames(String tableName,Connection conn) {    
        List<String> columnNames = new ArrayList<String>();
        String sql = "select * from "+tableName;
        PreparedStatement stmt;
        try {
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData data = rs.getMetaData();
            for (int i = 1; i <= data.getColumnCount(); i++) {
                // 获得指定列的列名
                String columnName = data.getColumnName(i);
                columnNames.add(columnName);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return columnNames;
    }

}
