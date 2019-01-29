package com.sdp.sqlModel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlShape;

/**
 * 形态Dao
 */

public interface SqlShapeMapper{
	
	String sql = " shape_id as shapeId, create_by as createBy, create_date as createDate, shape_resume as shapeResume,"
			+ " shape_type as shapeType, state, table_id as tableId, tenant_id as tenantId,partition_value as partitionValue ";
	
	@Insert("insert into sql_shape (shape_id, create_by, create_date, shape_resume, shape_type, state, table_id, tenant_id,partition_value)"
			+ " VALUES (#{shapeId},#{createBy},NOW(),#{shapeResume},#{shapeType},#{state},#{tableId},#{tenantId},#{partitionValue})")
	@SelectKey(keyProperty = "shapeId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void saveSqlShape(SqlShape sqlShape);
	
	@Update("update sql_shape set create_by = #{createBy}, create_date = NOW(), shape_resume = #{shapeResume},"
			+ " shape_type = #{shapeType}, state = #{state}, table_id = #{tableId}, tenant_id = #{tenantId}, partition_value= #{partitionValue} where shape_id = #{shapeId}")
	void update(SqlShape sqlShape);
	
	
	@Delete("delete from sql_shape where shape_id = #{shapeId}")
	void delete(@Param("shapeId")String shapeId);
	
	@Delete("delete from sql_shape where table_id = #{tableId} and shape_type = #{shapeType} and tenant_id = #{tenantId}")
	void deleteByTableId(@Param("tableId")String tableId,@Param("shapeType")String shapeType,@Param("tenantId")String tenantId);
	
	@Select("select "+ sql +" from sql_shape where table_id = #{tableId} and tenant_id = #{tenantId}")
	List<SqlShape> findByTableId(@Param("tableId")String tableId,@Param("tenantId")String tenantId);
	
	@Select("select "+ sql +" from sql_shape where shape_id = #{shapeId}")
	SqlShape findByShapeId(@Param("shapeId")String shapeId);
	
}
