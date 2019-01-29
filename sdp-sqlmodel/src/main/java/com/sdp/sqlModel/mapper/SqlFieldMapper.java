package com.sdp.sqlModel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlField;
/**
 * 数据源Dao
 */
public interface SqlFieldMapper {
	String sql = "FIELD_ID AS fieldId, FIELD_KEY AS fieldKey, CODESETID AS codesetid, CREATE_BY AS createBy, CREATE_DATE AS createDate, "
			+ "FIELD_DIGIT AS fieldDigit, FIELD_LEN AS fieldLen,FIELD_NAME AS fieldName, FIELD_RESUME AS fieldResume, FIELD_SQL_NAME AS fieldSqlName,"
			+ " FIELD_TYPE AS fieldType, SORT_ID AS sortId, SORT_INDEX AS sortIndex, TABLE_ID AS tableId, TENANT_ID AS tenantId,DATE_TYPE AS dateType,is_null as isNull";
	
	@Insert("INSERT INTO SQL_FIELD (FIELD_ID, FIELD_KEY, CODESETID, CREATE_BY, CREATE_DATE, FIELD_DIGIT, FIELD_LEN,"
			+ " FIELD_NAME, FIELD_RESUME, FIELD_SQL_NAME, FIELD_TYPE, SORT_ID, SORT_INDEX, TABLE_ID, TENANT_ID,DATE_TYPE,is_null) VALUES ("
			+ " #{fieldId},#{fieldKey},#{codesetid},#{createBy},NOW(),#{fieldDigit},#{fieldLen},#{fieldName},#{fieldResume},"
			+ " #{fieldSqlName},#{fieldType},#{sortId},#{sortIndex},#{tableId},#{tenantId},#{dateType},#{isNull})")
	@SelectKey(keyProperty = "fieldId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void save(SqlField sqlField);
	
	@Delete("DELETE FROM SQL_FIELD WHERE FIELD_ID=#{fieldId}")
	void delete(@Param("fieldId")String fieldId);
	
	@Delete("DELETE FROM SQL_FIELD WHERE TABLE_ID=#{tableId} AND TENANT_ID=#{tenantId}")
	void deleteByTableIdAndTenantId(@Param("tableId")String tableId,@Param("tenantId")String tenantId);
	
	@Select("SELECT " + sql+ " FROM SQL_FIELD WHERE FIELD_ID=#{fieldId} LIMIT 1")
	SqlField findByFieldId(@Param("fieldId")String fieldId);
	
	@Select("SELECT FIELD_ID AS fieldId, FIELD_KEY AS fieldKey, CODESETID AS codesetid, CREATE_BY AS createBy, CREATE_DATE AS createDate,"
			+ " FIELD_DIGIT AS fieldDigit, FIELD_LEN AS fieldLen,FIELD_NAME AS fieldName, FIELD_RESUME AS fieldResume, FIELD_SQL_NAME AS fieldSqlName,"
			+ " FIELD_TYPE AS fieldType, SORT_ID AS sortId, SORT_INDEX AS sortIndex, TABLE_ID AS tableId, TENANT_ID AS tenantId,DATE_TYPE AS dateType,is_null as isNull"
			+ " FROM SQL_FIELD WHERE TABLE_ID=#{tableId} AND TENANT_ID=#{tenantId}")
	List<SqlField> findByTableIdTenantId(@Param("tableId")String tableId,@Param("tenantId")String tenantId);
	
	@Update("UPDATE SQL_FIELD SET FIELD_KEY = #{fieldKey}, CODESETID = #{codesetid},"
			+ " CREATE_BY = #{createBy}, CREATE_DATE = NOW(), FIELD_DIGIT = #{fieldDigit}, "
			+ " FIELD_LEN = #{fieldLen},FIELD_NAME = #{fieldName}, FIELD_RESUME = #{fieldResume}, FIELD_SQL_NAME = #{fieldSqlName},"
			+ " FIELD_TYPE = #{fieldType}, SORT_ID = #{sortId}, SORT_INDEX = #{sortIndex}, TABLE_ID = #{tableId},"
			+ " TENANT_ID = #{tenantId},DATE_TYPE = #{dateType},is_null=#{isNull} WHERE FIELD_ID=#{fieldId}")
	void update(SqlField sqlField);
	
	@Select("SELECT DISTINCT idx.name FROM sys.indexes idx JOIN sys.index_columns idxCol ON "
			+ " (idx.object_id = idxCol.object_id AND idx.index_id = idxCol.index_id AND idx.is_primary_key = 1)"
			+ " JOIN sys.tables tab ON (idx.object_id = tab.object_id) JOIN sys.columns col ON (idx.object_id = col.object_id"
			+ " AND idxCol.column_id = col.column_id) WHERE tab.name =#{tableName} AND col.name IN(${colName})")
	List<String> getPrimaryInfoes(@Param("tableName")String tableName,@Param("colName")String colName);
	
	@Select("SELECT DISTINCT cu.CONSTRAINT_NAME FROM user_cons_columns cu,user_constraints au WHERE"
			+ "	cu.constraint_name = au.constraint_name AND au.constraint_type = 'P'"
			+ " AND au.table_name =#{tableName} AND cu.COLUMN_NAME IN(${colName})")
	List<String> getPrimaryOracles(@Param("tableName")String tableName,@Param("colName")String colName);
	
	@Select("SELECT C.REFERENCED_TABLE_NAME  , C.REFERENCED_COLUMN_NAME  , C.TABLE_NAME ,C.COLUMN_NAME , C.CONSTRAINT_NAME  "
			+ " FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE C JOIN INFORMATION_SCHEMA. TABLES T ON T.TABLE_NAME = C.TABLE_NAME"
			+ " JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS R ON R.TABLE_NAME = C.TABLE_NAME AND R.CONSTRAINT_NAME = C.CONSTRAINT_NAME "
			+ " AND R.REFERENCED_TABLE_NAME = C.REFERENCED_TABLE_NAME WHERE C.REFERENCED_TABLE_NAME IS NOT NULL AND C.REFERENCED_TABLE_NAME = #{tableName} "
			+ " AND C.TABLE_SCHEMA  = #{tableSchema} ")
	List<Map<String,Object>> findForefignMysql(@Param("tableName")String tableName,@Param("tableSchema")String tableSchema);
	
	@Select("SELECT C.REFERENCED_TABLE_NAME  , C.REFERENCED_COLUMN_NAME  , C.TABLE_NAME ,C.COLUMN_NAME , C.CONSTRAINT_NAME  "
			+ " FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE C JOIN INFORMATION_SCHEMA. TABLES T ON T.TABLE_NAME = C.TABLE_NAME"
			+ " JOIN INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS R ON R.TABLE_NAME = C.TABLE_NAME AND R.CONSTRAINT_NAME = C.CONSTRAINT_NAME "
			+ " AND R.REFERENCED_TABLE_NAME = C.REFERENCED_TABLE_NAME WHERE C.REFERENCED_TABLE_NAME IS NOT NULL AND C.TABLE_NAME = #{tableName} "
			+ " AND C.TABLE_SCHEMA  = #{tableSchema} ")
	List<Map<String,Object>> pointFindForefignMysql(@Param("tableName")String tableName,@Param("tableSchema")String tableSchema);
	
	@Select("select oSub.name AS TABLE_NAME,fk.name AS CONSTRAINT_NAME, SubCol.name AS COLUMN_NAME, oMain.name AS REFERENCED_TABLE_NAME,"
			+ " MainCol.name AS REFERENCED_COLUMN_NAME from sys.foreign_keys fk JOIN sys.all_objects oSub ON (fk.parent_object_id = oSub.object_id)"
			+ " JOIN sys.all_objects oMain ON (fk.referenced_object_id = oMain.object_id) JOIN sys.foreign_key_columns fkCols"
			+ " ON (fk.object_id = fkCols.constraint_object_id) JOIN sys.columns SubCol ON (oSub.object_id = SubCol.object_id"
			+ " AND fkCols.parent_column_id = SubCol.column_id) JOIN sys.columns MainCol ON (oMain.object_id = MainCol.object_id AND"
			+ " fkCols.referenced_column_id = MainCol.column_id) WHERE oMain.name = #{tableName} ")
	List<Map<String,Object>> findForefignSqlserver(@Param("tableName")String tableName);
	
	@Select("select oSub.name AS TABLE_NAME,fk.name AS CONSTRAINT_NAME, SubCol.name AS COLUMN_NAME, oMain.name AS REFERENCED_TABLE_NAME,"
			+ " MainCol.name AS REFERENCED_COLUMN_NAME from sys.foreign_keys fk JOIN sys.all_objects oSub ON (fk.parent_object_id = oSub.object_id)"
			+ " JOIN sys.all_objects oMain ON (fk.referenced_object_id = oMain.object_id) JOIN sys.foreign_key_columns fkCols"
			+ " ON (fk.object_id = fkCols.constraint_object_id) JOIN sys.columns SubCol ON (oSub.object_id = SubCol.object_id"
			+ " AND fkCols.parent_column_id = SubCol.column_id) JOIN sys.columns MainCol ON (oMain.object_id = MainCol.object_id AND"
			+ " fkCols.referenced_column_id = MainCol.column_id) WHERE oSub.name = #{tableName} ")
	List<Map<String,Object>> pointForefignSqlserver(@Param("tableName")String tableName);
	
	
	@Select("SELECT COLUMN_NAME COLNAMES,INDEX_NAME INDNAME FROM information_schema.statistics WHERE table_schema = 'testbconsole' AND table_name = #{tableName}")
	List<Map<String,Object>> findIndexMysql(@Param("tableName")String tableName);
	
	@Select("select INDEX_NAME INDNAME, COLUMN_NAME COLNAMES from User_Ind_Columns where Table_Name = #{tableName} ")
	List<Map<String,Object>> findIndexOracle(@Param("tableName")String tableName);
	
	@Select("select indschema, INDNAME, COLNAMES from syscat.indexes where tabschema= ${tableSchema} and tabname=${tableName} and indschema=${tableSchema}")
	List<Map<String,Object>> findIndexDb(@Param("tableName")String tableName,@Param("tableSchema")String tableSchema);
	
	@Select("SELECT a.name INDNAME,c.name ,d.name COLNAMES FROM"
			+ " sysindexes a JOIN sysindexkeys b ON a.id=b.id AND a.indid=b.indid JOIN sysobjects c "
			+ " ON b.id=c.id JOIN syscolumns d ON b.id=d.id AND b.colid=d.colid WHERE a.indid "
			+ "  NOT IN(0,255) AND c.name=#{tableName} ORDER BY c.name,a.name,d.name ")
	List<Map<String,Object>> findIndexSqlserver(@Param("tableName")String tableName);
	
	
	@Insert("INSERT INTO SQL_FIELD (FIELD_ID, FIELD_KEY, CODESETID, CREATE_BY, CREATE_DATE, FIELD_DIGIT, FIELD_LEN,"
			+ " FIELD_NAME, FIELD_RESUME, FIELD_SQL_NAME, FIELD_TYPE, SORT_ID, SORT_INDEX, TABLE_ID, TENANT_ID,DATE_TYPE,is_null) "
			+ " SELECT (select replace(uuid(), '-', '') from dual), FIELD_KEY, CODESETID, CREATE_BY, NOW(), FIELD_DIGIT, FIELD_LEN,"
			+ " FIELD_NAME, FIELD_RESUME, FIELD_SQL_NAME, FIELD_TYPE, SORT_ID, SORT_INDEX, #{tableNewId}, TENANT_ID,DATE_TYPE,is_null"
			+ " FROM SQL_FIELD WHERE TABLE_ID=#{tableId} AND TENANT_ID=#{tenantId} ")
//	@SelectKey(keyProperty = "fieldId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
	void saveSqlField(@Param("tableId")String tableId,@Param("tenantId")String tenantId,@Param("tableNewId")String tableNewId);
	
	
	
	
	
}