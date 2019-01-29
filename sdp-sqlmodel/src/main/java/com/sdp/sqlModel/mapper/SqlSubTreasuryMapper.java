package com.sdp.sqlModel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlSubTreasury;

/**
 * 数据源Dao
 */

public interface SqlSubTreasuryMapper{
	
	String sql = "sub_treasury_id as subTreasuryId, create_by as createBy, create_date as createDate, shape_id as shapeId, "
			+ " sub_treasury_type as subTreasuryType, tenant_id as tenantId,field_sql_name as fieldSqlName, type_value1 as typeValue1,"
			+ " type_value2 as typeValue2 ";
	
	@Insert("insert into sql_sub_treasury (sub_treasury_id, create_by, create_date, shape_id, sub_treasury_type, tenant_id,field_sql_name,type_value1,type_value2)"
			+ " VALUES (#{subTreasuryId},#{createBy},NOW(),#{shapeId}, #{subTreasuryType},#{tenantId},#{fieldSqlName},#{typeValue1},#{typeValue2})")
	@SelectKey(keyProperty = "subTreasuryId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void save(SqlSubTreasury sqlSubTreasury);
	
	@Delete("delete from sql_sub_treasury where sub_treasury_id = #{subTreasuryId} ")
	void delete(@Param("subTreasuryId")String subTreasuryId);

	@Select("select " + sql+ " from sql_sub_treasury where sub_treasury_id = #{subTreasuryId}")
	SqlSubTreasury find(@Param("subTreasuryId")String subTreasuryId);
	
	
	@Update("update sql_sub_treasury set create_by = #{createBy}, create_date = #{createDate},field_sql_name = #{fieldSqlName}"
			+ " shape_id = #{shapeId}, sub_treasury_type = #{subTreasuryType}, tenant_id = #{tenantId},type_value1 =#{typeValue1},type_value2 =#{typeValue2}"
			+ " where sub_treasury_id = #{subTreasuryId}") 
	void update(SqlSubTreasury sqlSubTreasury);
	
	 

	@Select("select " + sql+ " from sql_sub_treasury where shape_id = #{shapeId} and tenant_id = #{tenantId} ")
	SqlSubTreasury findSqlSubTreasury(@Param("shapeId")String shapeId,@Param("tenantId")String tenantId);
	
	@Delete("delete from sql_sub_treasury where shape_id = #{shapeId} and tenant_id = #{tenantId} ")
	void deleteSqlSubTreasury(@Param("shapeId")String shapeId,@Param("tenantId")String tenantId);

	
	
	
	
	
	
	
	
	
	
	
}
