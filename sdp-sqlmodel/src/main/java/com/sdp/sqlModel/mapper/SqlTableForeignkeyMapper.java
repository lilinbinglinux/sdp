package com.sdp.sqlModel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlTableForeignkey;

public interface SqlTableForeignkeyMapper{
	
	String sql = "foreign_key_id as foreignKeyId,join_field as joinField,join_table as joinTable,"
			+ "main_field as mainField,main_table as mainTable,tenant_id as tenantId,line_end as lineEnd,"
			+ "line_start as lineStart,foreign_y as foreignY,foreign_x as foreignX";
	
	@Insert("insert into sql_table_foreignkey (foreign_key_id,join_field,join_table,main_field,main_table,tenant_id,line_end,line_start,foreign_y,foreign_x) "
			+ "values (#{foreignKeyId},#{joinField},#{joinTable},#{mainField},#{mainTable},#{tenantId},#{lineEnd},#{lineStart},#{foreignY},#{foreignX})")
	@SelectKey(keyProperty = "foreignKeyId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void save(SqlTableForeignkey sqlTableForeignkey);
	
	@Delete("delete from sql_table_foreignkey where main_table = #{tableId} and tenant_id = #{tenantId}")
	void deleteByMainTableAndTenantId(@Param("tableId")String tableId,@Param("tenantId")String tenantId);

	@Delete("delete from sql_table_foreignkey where foreign_key_id = #{foreignKeyId} and tenant_id = #{tenantId}")
	void deleteByForeignKeyIdAndTenantId(@Param("foreignKeyId")String foreignKeyId,@Param("tenantId")String tenantId);
	
	@Select("select " + sql+ " from sql_table_foreignkey where foreign_key_id = #{foreignKeyId}")
	SqlTableForeignkey findOne(@Param("foreignKeyId")String foreignKeyId);
	
	@Select("select " + sql+ " from sql_table_foreignkey where tenant_id = #{tenantId}")
	List<SqlTableForeignkey> findSqlTableForeignkeyes(@Param("tenantId")String tenantId);
	
	@Update("update sql_table_foreignkey set join_field = #{joinField},join_table = #{joinTable},"
			+ " main_field = #{mainField},main_table = #{mainTable},tenant_id = #{tenantId},line_end = #{lineEnd},"
			+ " line_start = #{lineStart},foreign_y = #{foreignY},foreign_x = #{foreignX} where foreign_key_id = #{foreignKeyId}")
	void update(SqlTableForeignkey sqlTableForeignkey);
	
	@Update("update sql_table_foreignkey set foreign_y = #{foreignY},foreign_x = #{foreignX} where tenant_id = #{tenantId} and main_table=#{mainTable}")
	void updateCode(Map<String,Object> map);
	
	@Update("update sql_table_foreignkey set main_field = #{newMainField},main_table = #{mainTable} "
			+ " where tenant_id = #{tenantId} and main_field=#{mainField} and main_table=#{mainTable}")
	void updateByMainTableMainFieldTenantId(Map<String,Object> map);
	
	@Update("update sql_table_foreignkey set join_field = #{newJoinField},join_table=#{joinTable} "
			+ " where tenant_id = #{tenantId} and join_field=#{joinField} and join_table=#{joinTable}")
	void updateByJoinTableJoinFieldTenantId(Map<String,Object> map);
	
	
	@Select("select " + sql+ " from sql_table_foreignkey where tenant_id = #{tenantId} and main_field=#{mainField} and main_table=#{mainTable}")
	List<SqlTableForeignkey> findForeignkeyes(@Param("tenantId")String tenantId,@Param("mainField")String mainField,@Param("mainTable")String mainTable);
	
	@Select("select " + sql+ " from sql_table_foreignkey where tenant_id = #{tenantId} and join_field=#{joinField} and join_table=#{joinTable}")
	List<SqlTableForeignkey> findForeignkeyInfo(@Param("tenantId")String tenantId,@Param("joinField")String joinField,@Param("joinTable")String joinTable);
	
	@Select("select " + sql+ " from sql_table_foreignkey where tenant_id = #{tenantId} and main_table=#{mainTable}")
	List<SqlTableForeignkey> findForeignkey(@Param("mainTable")String joinTable,@Param("tenantId")String tenantId);
		
	@Select("select " + sql+ " from sql_table_foreignkey where tenant_id = #{tenantId} and join_table=#{joinTable}")
	List<SqlTableForeignkey> findJoinForeignkey(@Param("joinTable")String joinTable,@Param("tenantId")String tenantId);
	
	@Delete("delete from sql_table_foreignkey where tenant_id = #{tenantId} and main_field=#{mainField} and main_table=#{mainTable}")
	void deleteByMainTableMainFieldTenantId(@Param("tenantId")String tenantId,@Param("mainField")String mainField,@Param("mainTable")String mainTable);
	
	@Delete("delete from sql_table_foreignkey where tenant_id = #{tenantId} and join_field=#{joinField} and join_table=#{joinTable}")
	void deleteByJoinTableJoinFieldTenantId(@Param("tenantId")String tenantId,@Param("joinField")String joinField,@Param("joinTable")String joinTable);
	
	@Delete("delete from sql_table_foreignkey where tenant_id = #{tenantId} and join_table=#{joinTable}")
	void deleteByJoinTableTenantId(@Param("joinTable")String joinTable,@Param("tenantId")String tenantId);
	
	@Delete("${sql}")
	void deleteSql(@Param("sql")String sql);
	
	@Select("select " + sql+ " from sql_table_foreignkey where tenant_id = #{tenantId} and (main_table IN (${tableId}) or join_table IN (${tableId}) )")
	List<SqlTableForeignkey> foreignkeyInfo(@Param("tableId")String tableId,@Param("tenantId")String tenantId);
	
}
