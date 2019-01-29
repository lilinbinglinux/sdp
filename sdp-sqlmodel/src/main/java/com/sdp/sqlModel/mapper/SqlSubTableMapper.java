package com.sdp.sqlModel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlSubTable;

/**
 * 数据源Dao
 */

public interface SqlSubTableMapper{
	 
	String sql = "sub_table_id as subTableId, create_by as createBy, create_date as createDate,  "
			+ " field_sql_name as fieldSqlName, shape_id as shapeId, sub_table_type as subTableType, sub_table_type_value1 as subTableTypeValue1,"
			+ " sub_table_type_value2 as subTableTypeValue2, tenant_id as tenantId ";
	
	@Insert("insert into sql_sub_table (sub_table_id, create_by, create_date, field_sql_name, shape_id, sub_table_type,"
			+ " sub_table_type_value1, sub_table_type_value2, tenant_id) VALUES (#{subTableId},#{createBy},NOW(),#{fieldSqlName},"
			+ " #{shapeId},#{subTableType},#{subTableTypeValue1},#{subTableTypeValue2},#{tenantId})")
	@SelectKey(keyProperty = "subTableId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void save(SqlSubTable sqlSubTable);
	
	@Delete("delete from sql_sub_table where sub_table_id = #{subTableId} ")
	void delete(@Param("subTableId")String subTableId);

	@Select("select " + sql+ " from sql_sub_table where sub_table_id = #{subTableId}")
	SqlSubTable find(@Param("subTableId")String subTableId);
	
	
	@Select("select " + sql+ " from sql_sub_table where sub_table_id = #{subTableId}")
	SqlSubTable findSqlTable(@Param("subTableId")String subTableId);
	
	
	@Update("update sql_sub_table set create_by = #{createBy}, create_date = NOW(),  "
			+ " field_sql_name = #{fieldSqlName}, shape_id = #{shapeId}, sub_table_type = #{subTableType}, sub_table_type_value1 = #{subTableTypeValue1},"
			+ " sub_table_type_value2 = #{subTableTypeValue2}, tenant_id = #{tenantId} where sub_table_id = #{subTableId}") 
	void update(SqlSubTable sqlSubTable);
	
	@Select("select " + sql+ " from sql_sub_table where shape_id = #{shapeId} and tenant_id = #{tenantId}")
	SqlSubTable findSqlSubTable(@Param("shapeId")String shapeId,@Param("tenantId")String tenantId);
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
