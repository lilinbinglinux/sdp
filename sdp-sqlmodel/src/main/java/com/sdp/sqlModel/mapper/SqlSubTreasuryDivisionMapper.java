package com.sdp.sqlModel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlSubTreasuryDivision;


/**
 * 数据源Dao
 */

public interface SqlSubTreasuryDivisionMapper{
	  
	String sql = "sub_treasury_division_id as subTreasuryDivisionId, create_by as createBy, create_date as createDate,"
			+ " data_res_id as dataResId, tenant_id as tenantId, type_value1 as typeValue1, type_value2 as typeValue2,sub_treasury_id as subTreasuryId";
	
	@Insert("insert into sql_sub_treasury_division (sub_treasury_division_id, create_by, create_date, data_res_id, tenant_id, type_value1, type_value2,sub_treasury_id )"
			+ " VALUES (#{subTreasuryDivisionId},#{createBy},NOW(),#{dataResId}, #{tenantId},#{typeValue1},#{typeValue2},#{subTreasuryId})")
	@SelectKey(keyProperty = "subTreasuryDivisionId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void save(SqlSubTreasuryDivision sqlSubTreasuryDivision);
	
	@Delete("delete from sql_sub_treasury_division where sub_treasury_division_id = #{subTreasuryDivisionId} ")
	void delete(@Param("subTreasuryDivisionId")String subTreasuryDivisionId);

	@Select("select " + sql+ " from sql_sub_treasury_division where sub_treasury_division_id = #{subTreasuryDivisionId}")
	SqlSubTreasuryDivision find(@Param("subTreasuryDivisionId")String subTreasuryDivisionId);
	
	
	@Update("update sql_sub_treasury_division set create_by = #{createBy}, create_date = #{createDate},sub_treasury_id = #{subTreasuryId}"
			+ " data_res_id = #{dataResId}, tenant_id = #{tenantId}, type_value1 = #{typeValue1}, type_value2 = #{typeValue2}"
			+ " where sub_treasury_division_id = #{subTreasuryDivisionId}") 
	void update(SqlSubTreasuryDivision sqlSubTreasuryDivision);
	
	@Select("select " + sql+ " from sql_sub_treasury_division where sub_treasury_id = #{subTreasuryId} and tenant_id = #{tenantId}")
	List<SqlSubTreasuryDivision> findDivision(@Param("subTreasuryId")String subTreasuryId,@Param("tenantId")String tenantId);
	
	@Delete("delete from sql_sub_treasury_division where sub_treasury_id = #{subTreasuryId} and tenant_id = #{tenantId} ")
	void deleteDivision(@Param("subTreasuryId")String subTreasuryId,@Param("tenantId")String tenantId);


	
	
	
	
	
	
	
	
	
	
	
}
