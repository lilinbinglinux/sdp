package com.sdp.sqlModel.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.ProModel;

public interface ProModelMapper {

	String sql = "MODEL_ID AS modelId, CREATE_BY AS createBy, CREATE_DATE AS createDate, MODEL_FLAG AS modelFlag, MODEL_NAME AS modelName,"
			+ " MODEL_TYPE_PATH AS modelTypePath, PARENT_ID AS parentId, RESUME AS 'resume', SORT_ID AS sortId, TENANT_ID AS tenantId";
	
	@Update("UPDATE PRO_MODEL SET CREATE_BY =#{createBy}, CREATE_DATE =now(), MODEL_FLAG= #{modelFlag},"
			+ " MODEL_NAME= #{modelName}, MODEL_TYPE_PATH= #{modelTypePath}, PARENT_ID= #{parentId}, RESUME= #{resume},"
			+ " SORT_ID= #{sortId}, TENANT_ID= #{tenantId}  WHERE MODEL_ID =#{modelId}")
	void updateAll(ProModel proModel);

	@Insert("INSERT INTO PRO_MODEL (MODEL_ID, CREATE_BY, CREATE_DATE, MODEL_FLAG, MODEL_NAME, MODEL_TYPE_PATH, PARENT_ID, RESUME,"
			+ " SORT_ID, TENANT_ID) VALUES (#{modelId},#{createBy},NOW(),#{modelFlag},#{modelName},"
			+ " #{modelTypePath},#{parentId},#{resume},#{sortId},#{tenantId})")
	@SelectKey(keyProperty = "modelId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void save(ProModel proModel);

	@Select("SELECT " + sql+ " FROM PRO_MODEL WHERE MODEL_ID =#{modelId}")
	ProModel findProModel(@Param("modelId")String modelId);
	
	@Delete("DELETE FROM  PRO_MODEL WHERE MODEL_ID =#{modelId}")
	void deleteProModel(@Param("modelId")String modelId);
	
	@Select("SELECT " + sql+ " FROM PRO_MODEL WHERE TENANT_ID =#{tenantId}")
	List<ProModel> getProModel(@Param("tenantId")String tenantId);
	
}
