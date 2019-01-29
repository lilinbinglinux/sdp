package com.sdp.sqlModel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlTable;

/**
 * 数据源Dao
 */

public interface SqlTableMapper{
	
	String sql = "table_id as tableId,create_by as createBy,create_date as createDate,data_res_id as dataResId,sort_id as sortId,"
			+ "table_main_flag as tableMainFlag,table_name as tableName,table_resume as tableResume,table_sql_name as tableSqlName,"
			+ "table_type_id as tableTypeId,table_x as tableX,table_y as tableY,tenant_id as tenantId,table_hight as tableHight,table_width as tableWidth ";
	
	@Insert("insert into sql_table (table_id,create_by,create_date,data_res_id,sort_id,table_main_flag,table_name,"
			+ "table_resume,table_sql_name,table_type_id,table_x,table_y,tenant_id) VALUES (#{tableId},#{createBy},"
			+ "NOW(),#{dataResId},#{sortId},#{tableMainFlag},#{tableName},#{tableResume},#{tableSqlName},"
			+ "#{tableTypeId},#{tableX},#{tableY},#{tenantId})")
	@SelectKey(keyProperty = "tableId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void savesqltable(SqlTable sqlTable);
	
	@Delete("delete from sql_table where table_id = #{tableId} and tenant_id = #{tenantId}")
	void deleteByTableIdAndTenantId(@Param("tableId")String tableId,@Param("tenantId")String tenantId);

	@Select("select " + sql+ " from sql_table where table_id = #{tableId}")
	SqlTable findByTableId(@Param("tableId")String tableId);
	
	@Select("select " + sql+ " from sql_table where table_sql_name = #{tableSqlName} and tenant_id = #{tenantId} and data_res_id= #{dataResId} limit 1")
	SqlTable findTableName(@Param("tableSqlName")String tableSqlName,@Param("tenantId")String tenantId,@Param("dataResId")String dataResId);
	
	@Select("select count(*) from sql_table where data_res_id = #{dataResId} and tenant_id = #{tenantId}")
	int countByDataResIdAndTenantId(@Param("dataResId")String dataResId,@Param("tenantId")String tenantId);
	
	@Select("select distinct table_type_id from sql_table where tenant_id = #{tenantId}")
	List<String> findByTenantId(@Param("tenantId")String tenantId);
	
	@Select("select " + sql+ " from sql_table where data_res_id = #{dataResId} ")
	List<SqlTable> findByDataResIdAndTenantId(@Param("dataResId")String dataResId);
	
	@Select("select " + sql+ " from sql_table where tenant_id = #{tenantId} and table_type_id = #{tableTypeId}")
	List<SqlTable> findByTenantIdAndTableTypeId(@Param("tenantId")String tenantId,@Param("tableTypeId")String tableTypeId);

	@Update("update sql_table set table_x = #{tableX},table_y = #{tableY},table_hight = #{tableHight},table_width = #{tableWidth} where table_id = #{tableId}") 
	void updateCoordinate(Map<String,Object> map);
	
	@Update("update sql_table set table_type_id= #{tableTypeId} where table_id = #{tableId}") 
	void updateTableTypeId(@Param("tableId")String tableId,@Param("tableTypeId")String tableTypeId);
	
	@Update("update sql_table set create_by= #{createBy},create_date= now(),data_res_id= #{dataResId},sort_id= #{sortId},"
			+ " table_main_flag= #{tableMainFlag},table_name= #{tableName},table_resume= #{tableResume},table_sql_name= #{tableSqlName},"
			+ " table_type_id= #{tableTypeId},table_x= #{tableX},table_y= #{tableY},tenant_id= #{tenantId} where table_id = #{tableId}") 
	void update(SqlTable sqlTable);
	
	
	@Select("select " + sql+ " from sql_table where data_res_id = #{dataResId} and tenant_id = #{tenantId} "
			+ " and table_sql_name = #{tableSqlName} limit 1")
	SqlTable findTableInfo(@Param("dataResId")String dataResId,@Param("tenantId")String tenantId,@Param("tableSqlName")String tableSqlName);
	

	@Delete("delete from sql_table where data_res_id = #{dataResId} and tenant_id = #{tenantId}")
	void deleteByDataResIdAndTenantId(@Param("dataResId")String dataResId,@Param("tenantId")String tenantId);

	@Select("select distinct table_id as tableId from sql_table where data_res_id = #{dataResId} and tenant_id = #{tenantId}")
	List<String> findByDataResIdTenantId(@Param("dataResId")String dataResId,@Param("tenantId")String tenantId);
	
	@Select("select " + sql+ " from sql_table where data_res_id = #{dataResId} and tenant_id = #{tenantId} "
			+ " and table_id <> #{tableId} and table_sql_name = #{tableSqlName} limit 1")
	SqlTable findTable(@Param("dataResId")String dataResId,@Param("tenantId")String tenantId,@Param("tableId")String tableId,@Param("tableSqlName")String tableSqlName);
	
	
//	INSERT INTO `xbconsole`.`sql_table` (
//			`table_id`,
//			`create_by`,
//			`create_date`,
//			`data_res_id`,
//			`sort_id`,
//			`table_main_flag`,
//			`table_name`,
//			`table_resume`,
//			`table_sql_name`,
//			`table_type_id`,
//			`table_x`,
//			`table_y`,
//			`tenant_id`
//		) SELECT (select replace(uuid(), '-', '') from dual) as table_id ,`create_by`,
//			`create_date`,
//			`data_res_id`,
//			`sort_id`,
//			`table_main_flag`,
//			`table_name`,
//			`table_resume`,
//			`table_sql_name`,
//			`table_type_id`,
//			`table_x`,
//			`table_y`,
//			`tenant_id` FROM
//			`sql_table`
//		WHERE
//			table_id = '16bc2726913f11e88b091402ec8b0a88';

	@Insert("insert into sql_table (table_id,create_by,create_date,data_res_id,sort_id,table_main_flag,table_name,"
			+ " table_resume,table_sql_name,table_type_id,table_x,table_y,tenant_id) SELECT #{tableIdNewId},create_by,NOW(),#{dataResId},sort_id,table_main_flag,#{tableSqlName},"
			+ " table_resume,#{tableSqlName},#{dataResId},table_x,table_y,tenant_id FROM sql_table WHERE table_id = #{tableId}")
//	@SelectKey(keyProperty = "tableId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void save(@Param("tableId")String tableId,@Param("tableIdNewId")String tableIdNewId,@Param("dataResId")String dataResId,@Param("tableSqlName")String tableSqlName);
}
