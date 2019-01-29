package com.sdp.sqlModel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlDataResType;




/**
 * 数据源类型Dao
 */
public interface SqlDataResTypeMapper{
	
	String sql="DATA_RES_TYPE_ID AS dataResTypeId, DATA_DRIVER AS dataDriver, DATA_RES_TYPE_NAME AS dataResTypeName,"
			+ " DATA_STUTAS AS dataStutas, RES_TYPE AS resType, RESUME AS resume, SORT_NUM AS sortNum, TENANT_ID AS tenantId ";
	
	@Insert("INSERT INTO SQL_DATA_RES_TYPE (DATA_RES_TYPE_ID, DATA_DRIVER, DATA_RES_TYPE_NAME, DATA_STUTAS, RES_TYPE,"
			+ " RESUME, SORT_NUM, TENANT_ID) VALUES (#{dataResTypeId},#{dataDriver},#{dataResTypeName},#{dataStutas},#{resType},"
			+ " #{resume},#{sortNum},#{tenantId})")
	@SelectKey(keyProperty = "dataResTypeId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void save(SqlDataResType sqlDataResType);
	
	@Delete("DELETE FROM SQL_DATA_RES_TYPE WHERE DATA_RES_TYPE_ID=#{dataResTypeId}")
	void deleteByDataResTypeId(@Param("dataResTypeId")String dataResTypeId);
	
	@Select("SELECT "+ sql +" FROM SQL_DATA_RES_TYPE WHERE DATA_RES_TYPE_ID=#{dataResTypeId} AND DATA_STUTAS=#{dataStutas} ")
	SqlDataResType findByDataResTypeIdAndDataStutas(@Param("dataResTypeId")String dataResTypeId,@Param("dataStutas")String dataStutas);
	
	@Select("SELECT "+ sql +" FROM SQL_DATA_RES_TYPE WHERE TENANT_ID=#{tenantId}")
	List<SqlDataResType> findByTenantId(@Param("tenantId")String tenantId);
	
	@Select("SELECT "+ sql +" FROM SQL_DATA_RES_TYPE WHERE DATA_RES_TYPE_ID=#{dataResTypeId} LIMIT 1")
	SqlDataResType findByDataResTypeId(@Param("dataResTypeId")String dataResTypeId);
	
	@Update("UPDATE SQL_DATA_RES_TYPE SET DATA_DRIVER = #{dataDriver}, DATA_RES_TYPE_NAME = #{dataResTypeName}, "
			+ " DATA_STUTAS = #{dataStutas}, RES_TYPE = #{resType},  RESUME = #{resume}, SORT_NUM = #{sortNum},"
			+ " TENANT_ID = #{tenantId} WHERE DATA_RES_TYPE_ID=#{dataResTypeId}")
	void update(SqlDataResType sqlDataResType);
}
