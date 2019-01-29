package com.sdp.sqlModel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.ProDataApi;

public interface ProDataApiMapper {

	String sql = "DATA_API_ID AS dataApiId, CREATE_BY AS createBy, CREATE_DATE AS createDate, DATA_API_NAME AS dataApiName,"
			+ " DATA_RES_ID AS dataResId, INPUT_ATTR AS inputAttr, MODEL_ID AS modelId, OUTPUT_ATTR AS outputAttr,"
			+ " SORT_ID AS sortId, SQL_TEXT AS sqlText, TENANT_ID AS tenantId,output_sample AS outputSample";
	
	@Update("UPDATE PRO_DATA_API SET CREATE_BY =#{createBy}, CREATE_DATE =now(), DATA_API_NAME= #{dataApiName}, output_sample = #{outputSample},"
			+ " DATA_RES_ID= #{dataResId}, INPUT_ATTR= #{inputAttr}, MODEL_ID= #{modelId}, OUTPUT_ATTR= #{outputAttr},"
			+ " SORT_ID= #{sortId}, SQL_TEXT= #{sqlText}, TENANT_ID= #{tenantId}  WHERE DATA_API_ID =#{dataApiId}")
	void updateAll(ProDataApi proDataApi);

	@Insert("INSERT INTO PRO_DATA_API (DATA_API_ID, CREATE_BY, CREATE_DATE, DATA_API_NAME, DATA_RES_ID, INPUT_ATTR, MODEL_ID,"
			+ " OUTPUT_ATTR, SORT_ID, SQL_TEXT, TENANT_ID,output_sample) VALUES (#{dataApiId},#{createBy},NOW(),#{dataApiName},#{dataResId},"
			+ " #{inputAttr},#{modelId},#{outputAttr},#{sortId},#{sqlText},#{tenantId},#{outputSample})")
	@SelectKey(keyProperty = "dataApiId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void save(ProDataApi proDataApi);

	@Select("SELECT " + sql+ " FROM PRO_DATA_API WHERE DATA_API_ID =#{dataApiId}")
	ProDataApi findProDataApi(@Param("dataApiId")String dataApiId);
	
	@Delete("DELETE FROM  PRO_DATA_API WHERE DATA_API_ID =#{dataApiId}")
	void deleteProDataApi(@Param("dataApiId")String dataApiId);
	
	@Select("SELECT " + sql+ " FROM PRO_DATA_API WHERE TENANT_ID =#{tenantId} AND MODEL_ID = #{modelId}")
	List<ProDataApi> getProDataApi(@Param("tenantId")String tenantId,@Param("modelId")String modelId);
	
}
