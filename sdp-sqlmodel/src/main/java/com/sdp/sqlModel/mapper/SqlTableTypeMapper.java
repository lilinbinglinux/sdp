package com.sdp.sqlModel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlTableType;

public interface SqlTableTypeMapper {
	String sql = "table_type_id as tableTypeId ,create_by as createBy,create_date as createDate,parent_id as parentId,"
			+ "sort_id as sortId,table_type_name as tableTypeName,table_type_path as tableTypePath,tenant_id as tenantId,"
			+ "data_res_id as dataResId";
	
	@Insert("insert into sql_table_type (table_type_id,create_by,create_date,parent_id,sort_id,table_type_name,table_type_path, "
			+ "tenant_id,data_res_id) values (#{tableTypeId},#{createBy},NOW(),#{parentId},#{sortId},#{tableTypeName},"
			+ "#{tableTypePath},#{tenantId},#{dataResId})")
	@SelectKey(keyProperty = "tableTypeId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void save(SqlTableType sqlTableType);
	
	@Delete("delete from sql_table_type where table_type_id = #{tableTypeId} and tenant_id = #{tenantId}")
	void deleteByTableTypeIdAndTenantId(@Param("tableTypeId")String tableTypeId,@Param("tenantId")String tenantId);
	
	@Select("select table_type_path as tableTypePath from sql_table_type where table_type_id = #{parentId} and tenant_id = #{tenantId}")
	String findByTableTypeIdAndTenantId(@Param("parentId")String parentId,@Param("tenantId")String tenantId);

	@Select("select table_type_name as tableTypeName,parent_id as parentId from sql_table_type where tenant_id = #{tenantId} and parent_id = #{parentId} order by sort_id")
	List<Map<String, String>> findByTenantIdAndParentId(@Param("tenantId")String tenantId,@Param("parentId")String parentId);
	
	@Select("select table_type_name as tableTypeName,parent_id as parentId,table_type_id as tableTypeId,sort_id as sortId from sql_table_type where tenant_id = #{tenantId} order by sort_id")
	List<Map<String, Object>> findByTenantId(@Param("tenantId")String tenantId);
	
	@Select("select " + sql+ " from sql_table_type where table_type_id = #{tableTypeId}")
	SqlTableType findOne(@Param("tableTypeId")String tableTypeId);
	
	@Update("update sql_table_type set table_type_path = #{tableTypePath} where table_type_id = #{tableTypeId}")
	void update(SqlTableType sqlTableType);

	@Select("select " + sql+ " from sql_table_type where tenant_id = #{tenantId} and data_res_id = #{dataResId}")
	List<SqlTableType> selectTablesByDtatResId(@Param("tenantId")String tenantId,@Param("dataResId")String dataResId);
	
	@Update("update sql_table_type set create_by = #{createBy},create_date = now(),parent_id = #{parentId},"
			+ " sort_id = #{sortId},table_type_name = #{tableTypeName},table_type_path = #{tableTypePath},tenant_id = #{tenantId},"
			+ " data_res_id = #{dataResId} where table_type_id = #{tableTypeId}")
	void updateAll(SqlTableType sqlTableType);
	
	@Select("select " + sql+ " from sql_table_type WHERE table_type_name=#{tableTypeName} AND tenant_id=#{tenantId}"
			+ " AND data_res_id=#{dataResId} AND parent_id =#{dataResId} LIMIT 1")
	SqlTableType findTabeInfo(Map<String,Object> map);
	
	@Select("delete from sql_table_type WHERE table_type_name=#{tableTypeName} AND tenant_id=#{tenantId}"
			+ " AND data_res_id=#{dataResId} AND (parent_id is null OR parent_id='')")
	void deleteTabeInfo(Map<String,Object> map);
	
	@Select("select distinct table_type_name as tableTypeName from sql_table_type where tenant_id = #{tenantId} and parent_id = #{parentId} and data_res_id = #{dataResId} ")
	List<String> findByParentId(@Param("tenantId")String tenantId,@Param("parentId")String parentId,@Param("dataResId")String dataResId);
	
	@Select("delete from sql_table_type WHERE tenant_id=#{tenantId} AND data_res_id=#{dataResId}")
	void deleteDataRes(@Param("tenantId")String tenantId,@Param("dataResId")String dataResId);
}
