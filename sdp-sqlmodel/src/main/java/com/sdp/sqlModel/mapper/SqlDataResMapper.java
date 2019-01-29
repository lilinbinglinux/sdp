package com.sdp.sqlModel.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlDataRes;




/**
 * 数据源类型Dao
 */
public interface SqlDataResMapper{
	
	String sql="DATA_RES_ID AS dataResId, DATA_RES_DESC AS dataResDesc, DATA_RES_TYPE_ID AS dataResTypeId, DATA_RES_TYPE_NAME AS dataResTypeName, "
			+ "DATA_RES_URL AS dataResUrl,DATA_STATUS AS dataStatus, IS_DEFAULT AS isDefault, PASSWORD AS password, RESUME AS resume,"
			+ " SORT_NUM AS sortNum, TENANT_ID AS tenantId, USERNAME AS username,TABLE_SCHEMA AS tableSchema";
	
	@Insert("INSERT INTO SQL_DATA_RES (DATA_RES_ID, DATA_RES_DESC, DATA_RES_TYPE_ID, DATA_RES_TYPE_NAME, DATA_RES_URL,"
			+ " DATA_STATUS, IS_DEFAULT, PASSWORD, RESUME, SORT_NUM, TENANT_ID, USERNAME,TABLE_SCHEMA) VALUES (#{dataResId},#{dataResDesc},"
			+ " #{dataResTypeId},#{dataResTypeName},#{dataResUrl},#{dataStatus},#{isDefault},#{password},#{resume},#{sortNum},#{tenantId},#{username},#{tableSchema})")
	@SelectKey(keyProperty = "dataResId", resultType = String.class, before = true,statement = "select replace(uuid(), '-', '') from dual")
//	@SelectKey(statement="select max(empNo)+1 as myNo from emp" , before=true,keyColumn="myNo",resultType=int.class,keyProperty="empNo")
	void save(SqlDataRes sqlDataRes);
	
	@Delete("DELETE FROM SQL_DATA_RES WHERE DATA_RES_ID = #{dataResId}")
	void deleteByDataResId(@Param("dataResId")String dataResId);
	
	@Select("SELECT COUNT(*) FROM SQL_DATA_RES WHERE DATA_RES_TYPE_ID=#{dataResTypeId} AND TENANT_ID=#{tenantId}")
	int countByDataResTypeIdAndTenantId(@Param("dataResTypeId")String dataResTypeId,@Param("tenantId")String tenantId);
	
	@Select("SELECT DATA_RES_ID AS dataResId, DATA_RES_DESC AS dataResDesc, DATA_RES_TYPE_ID AS dataResTypeId, DATA_RES_TYPE_NAME AS dataResTypeName, "
			+ "DATA_RES_URL AS dataResUrl,DATA_STATUS AS dataStatus, IS_DEFAULT AS isDefault, PASSWORD AS password, RESUME AS resume,"
			+ " SORT_NUM AS sortNum, TENANT_ID AS tenantId, USERNAME AS username,table_schema tableSchema FROM SQL_DATA_RES WHERE DATA_RES_ID=#{dataResId} AND DATA_STATUS=#{dataStatus} LIMIT 1")
	SqlDataRes findByDataResIdAndDataStatus(@Param("dataResId")String dataResId,@Param("dataStatus")String dataStatus);
	
	@Select("SELECT "+ sql +" FROM SQL_DATA_RES WHERE TENANT_ID=#{tenantId}")
	List<SqlDataRes> findByTenantId(@Param("tenantId")String tenantId);
	
	@Select("SELECT "+ sql +" FROM SQL_DATA_RES WHERE TENANT_ID=#{tenantId} AND DATA_RES_TYPE_ID=#{dataResTypeId}")
	List<SqlDataRes> findByTenantIdAndDataResTypeId(@Param("tenantId")String tenantId,@Param("dataResTypeId")String dataResTypeId);

	@Select("SELECT "+ sql +" FROM SQL_DATA_RES WHERE DATA_RES_ID=#{dataResId} LIMIT 1 ")
	SqlDataRes findByDataResId(@Param("dataResId")String dataResId);
	
	@Update("UPDATE SQL_DATA_RES SET DATA_RES_DESC = #{dataResDesc}, DATA_RES_TYPE_ID = #{dataResTypeId},TABLE_SCHEMA = #{tableSchema}, "
			+ " DATA_RES_TYPE_NAME = #{dataResTypeName},DATA_RES_URL = #{dataResUrl},DATA_STATUS = #{dataStatus}, "
			+ " IS_DEFAULT = #{isDefault}, PASSWORD = #{password}, RESUME = #{resume},"
			+ " SORT_NUM = #{sortNum}, TENANT_ID = #{tenantId}, USERNAME = #{username} WHERE DATA_RES_ID=#{dataResId}")
	void update(SqlDataRes sqlDataRes);
	
	@Select("SELECT DISTINCT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = #{tableSchema}")
	List<Map<String,Object>> getTableNameMysql(@Param("tableSchema")String tableSchema);
	
	@Select("select t.table_name TABLE_NAME,null TABLE_COMMENT from user_tables t ")//WHERE t.TABLESPACE_NAME=#{tableSchema} 表空间
	List<Map<String,Object>> getTableTableOracle();
	
	@Select("select table_name as TABLE_NAME, null TABLE_COMMENT from user_tables where table_schema = current schema ")
	List<Map<String,Object>> getTableDb();
	
	@Select("select sys.objects.name as TABLE_NAME from sys.objects where sys.objects.type='U' ")
	List<Map<String,Object>> getTableSqlserver();

	@Select("SELECT COLUMN_COMMENT,COLUMN_NAME,COLUMN_TYPE,COLUMN_KEY,CASE IS_NULLABLE WHEN 'NO' THEN 0 ELSE 1 END IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH LENGTH "
			+ " FROM information_schema.COLUMNS WHERE TABLE_NAME = #{tableName} AND TABLE_SCHEMA=#{tableSchema}")
	List<Map<String,Object>> getTableInfoMysql(@Param("tableName")String tableName,@Param("tableSchema")String tableSchema);
	
	@Select("select A.column_name COLUMN_NAME,A.data_type DATA_TYPE,A.data_length LENGTH,"
			+ " A.Data_Scale PRECISION,case A.nullable when 'N' THEN 0 else 1 end as IS_NULLABLE,A.Data_default dataDefault,B.comments COLUMN_COMMENT,C.IndexCount COLUMN_KEY from user_tab_columns A,"
			+ " user_col_comments B,(select count(*) IndexCount,Column_Name from User_Ind_Columns where"
			+ " Table_Name = #{tableName} group by Column_Name) C where A.Table_Name = B.Table_Name and A.Column_Name = B.Column_Name"
			+ " and A.Column_Name = C.Column_Name(+) and A.Table_Name = #{tableName}")
	List<Map<String,Object>> getTableInfoOracle(@Param("tableName")String tableName);
	
	@Select("SELECT d.name tbName,COALESCE(d.remarks, '') tbDesc,a.name COLUMN_NAME,a.coltype DATA_TYPE,decode(a.nulls,'Y','1','0') IS_NULLABLE,"
			+ " decode(a.identity,'Y','1','0') auto,a.longlength LENGTH,a.scale PRECISION,COALESCE(a.remarks, '') COLUMN_COMMENT,"
			+ " decode(n.unique_colcount,'1','1','0') unique,decode(n.uniquerule,'P','1','0') masterKey,COALESCE(n.name, '') COLUMN_KEY"
			+ " FROM sysibm.syscolumns a INNER JOIN sysibm.systables d on a.tbname=d.name LEFT JOIN sysibm.sysindexes n on n.tbname=d.name "
			+ " and SUBSTR(colnames,2)=a.name where d.type='T' and d.name=#{tableName}")
	List<Map<String,Object>> getTableInfoDb(@Param("tableName")String tableName);
	
	@Select("SELECT  d.name tbName, a.name COLUMN_NAME, ( CASE WHEN (SELECT Count(*) FROM sysobjects WHERE ( name IN (SELECT name FROM sysindexes "
			+ " WHERE  ( id = a.id )AND ( indid IN (SELECT indid FROM sysindexkeys WHERE  ( id = a.id ) AND ( colid IN (SELECT colid FROM syscolumns WHERE ( id = a.id )"
			+ " AND ( name = a.name )))))))AND ( xtype = 'PK' )) > 0 THEN '1' ELSE '0' END ) masterKey,( CASE WHEN( SELECT COUNT(*) FROM sysindexes WHERE"
			+ " ( id = a.id ) AND ( indid IN (SELECT indid FROM sysindexkeys WHERE ( id = a.id ) AND ( colid IN (SELECT colid FROM  "
			+ " syscolumns WHERE  ( id = a.id ) AND ( name = a.name )) )) )) > 0 THEN '1' ELSE null END ) COLUMN_KEY,b.name DATA_TYPE,"
			+ " Columnproperty(a.id, a.name, 'PRECISION') AS LENGTH, Isnull(Columnproperty(a.id, a.name, 'Scale'), 0) AS PRECISION,"
			+ " a.isnullable IS_NULLABLE, CONVERT(nvarchar(50),Isnull(g.[value], ' ')) AS COLUMN_COMMENT"
			+ " FROM  syscolumns a LEFT JOIN systypes b ON a.xtype = b.xusertype left JOIN sysobjects d ON a.id = d.id AND d.xtype = 'U'"
			+ " AND d.name <> 'dtproperties' LEFT JOIN syscomments e ON a.cdefault = e.id LEFT JOIN sys.extended_properties g ON a.id = g.major_id"
			+ " AND a.colid = g.minor_id LEFT JOIN sys.extended_properties f ON d.id = f.class AND f.minor_id = 0 WHERE b.name IS NOT NULL"
			+ " and d.name is not null and  d.name=#{tableName} ORDER  BY a.id,a.colorder")
	List<Map<String,Object>> getTableInfoSqlserver(@Param("tableName")String tableName);

	
	@Select("SELECT c.TABLE_SCHEMA , c.TABLE_NAME , c.COLUMN_NAME , c.DATA_TYPE , c.CHARACTER_MAXIMUM_LENGTH as LENGTH,c.COLUMN_DEFAULT ,"
			+ " c.IS_NULLABLE , c.NUMERIC_PRECISION , c.NUMERIC_SCALE FROM [INFORMATION_SCHEMA].[COLUMNS] c WHERE TABLE_NAME =#{tableName}")
	List<Map<String,Object>> getTableInfoesSqlserver(@Param("tableName")String tableName);

	@Select("SELECT DISTINCT O.TABLE_NAME, O.CONSTRAINT_SCHEMA,O.CONSTRAINT_NAME,O.TABLE_SCHEMA,"
			+ " O.COLUMN_NAME,O.REFERENCED_TABLE_SCHEMA,O.REFERENCED_TABLE_NAME,O.REFERENCED_COLUMN_NAME,"
			+ " O.UPDATE_RULE,O.DELETE_RULE,O.UNIQUE_CONSTRAINT_NAME,T.CONSTRAINT_TYPE FROM(SELECT	"
			+ " K.CONSTRAINT_SCHEMA, K.CONSTRAINT_NAME,	K.TABLE_SCHEMA,	K.TABLE_NAME,K.COLUMN_NAME,K.REFERENCED_TABLE_SCHEMA,"
			+ " K.REFERENCED_TABLE_NAME,K.REFERENCED_COLUMN_NAME,R.UPDATE_RULE,R.DELETE_RULE,R.UNIQUE_CONSTRAINT_NAME"
			+ " FROM information_schema.KEY_COLUMN_USAGE K LEFT JOIN information_schema.REFERENTIAL_CONSTRAINTS R ON "
			+ " K.CONSTRAINT_NAME = R.CONSTRAINT_NAME ) AS O INNER JOIN Information_schema.TABLE_CONSTRAINTS T ON "
			+ " O.Table_Name = T.TABLE_NAME AND T.CONSTRAINT_NAME = O.CONSTRAINT_NAME WHERE O.CONSTRAINT_SCHEMA != 'mysql' "
			+ " AND O.CONSTRAINT_SCHEMA != 'sys' AND O.CONSTRAINT_SCHEMA= #{tableSchema} AND T.CONSTRAINT_TYPE='FOREIGN KEY';")
	List<Map<String,Object>> getForeignKeyInfoesMysql(@Param("tableSchema")String tableSchema);
	
	@Select("select b.constraint_name CONSTRAINT_NAME, b.table_name TABLE_NAME,  b.column_name COLUMN_NAME,c.table_name as REFERENCED_TABLE_NAME,"
			+ " c.column_name REFERENCED_COLUMN_NAME,a.delete_rule DELETERULES from user_constraints a left  join user_cons_columns b on"
			+ " a.constraint_name = b.constraint_name left  join user_cons_columns c on a.r_constraint_name = c.constraint_name"
			+ " where a.constraint_type = 'R' and c.table_name = #{tableName} ")
	List<Map<String,Object>> getForeignKeyInfoesOracle(@Param("tableName")String tableName);
	
	@Select("select b.constraint_name CONSTRAINT_NAME, b.table_name TABLE_NAME,  b.column_name COLUMN_NAME,c.table_name as REFERENCED_TABLE_NAME,"
			+ " c.column_name REFERENCED_COLUMN_NAME,a.delete_rule DELETERULES from user_constraints a left  join user_cons_columns b on"
			+ " a.constraint_name = b.constraint_name left  join user_cons_columns c on a.r_constraint_name = c.constraint_name"
			+ " where a.constraint_type = 'R' and b.table_name = #{tableName} ")
	List<Map<String,Object>> pointForeignKeyInfoesOracle(@Param("tableName")String tableName);
	
	@Select("select CONSTNAME CONSTRAINT_NAME,TABNAME TABLE_NAME,FK_COLNAMES COLUMN_NAME,REFTABNAME REFERENCED_TABLE_NAME,"
			+ " REFKEYNAME REFERENCED_COLUMN_NAME from syscat.references where REFTABNAME = #{tableName}")
	List<Map<String,Object>> getForeignKeyInfoesDb(@Param("tableName")String tableName);
	
	@Select("select CONSTNAME CONSTRAINT_NAME,TABNAME TABLE_NAME,FK_COLNAMES COLUMN_NAME,REFTABNAME REFERENCED_TABLE_NAME,"
			+ " REFKEYNAME REFERENCED_COLUMN_NAME from syscat.references where TABNAME = #{tableName}")
	List<Map<String,Object>> pointForeignKeyInfoesDb(@Param("tableName")String tableName);
	
	@Select("select oSub.name AS TABLE_NAME,fk.name AS CONSTRAINT_NAME, SubCol.name AS COLUMN_NAME, oMain.name AS REFERENCED_TABLE_NAME,"
			+ " MainCol.name AS REFERENCED_COLUMN_NAME from sys.foreign_keys fk JOIN sys.all_objects oSub ON (fk.parent_object_id = oSub.object_id)"
			+ " JOIN sys.all_objects oMain ON (fk.referenced_object_id = oMain.object_id) JOIN sys.foreign_key_columns fkCols"
			+ " ON (fk.object_id = fkCols.constraint_object_id) JOIN sys.columns SubCol ON (oSub.object_id = SubCol.object_id"
			+ " AND fkCols.parent_column_id = SubCol.column_id) JOIN sys.columns MainCol ON (oMain.object_id = MainCol.object_id AND"
			+ " fkCols.referenced_column_id = MainCol.column_id)")
	List<Map<String,Object>> getForeignKeyInfoesSqlserver();
	
	@Select("SELECT DISTINCT O.TABLE_NAME, O.CONSTRAINT_SCHEMA,O.CONSTRAINT_NAME,O.TABLE_SCHEMA,"
			+ " O.COLUMN_NAME,O.REFERENCED_TABLE_SCHEMA,O.REFERENCED_TABLE_NAME,O.REFERENCED_COLUMN_NAME,"
			+ " O.UPDATE_RULE,O.DELETE_RULE,O.UNIQUE_CONSTRAINT_NAME,T.CONSTRAINT_TYPE FROM(SELECT	"
			+ " K.CONSTRAINT_SCHEMA, K.CONSTRAINT_NAME,	K.TABLE_SCHEMA,	K.TABLE_NAME,K.COLUMN_NAME,K.REFERENCED_TABLE_SCHEMA,"
			+ " K.REFERENCED_TABLE_NAME,K.REFERENCED_COLUMN_NAME,R.UPDATE_RULE,R.DELETE_RULE,R.UNIQUE_CONSTRAINT_NAME"
			+ " FROM information_schema.KEY_COLUMN_USAGE K LEFT JOIN information_schema.REFERENTIAL_CONSTRAINTS R ON "
			+ " K.CONSTRAINT_NAME = R.CONSTRAINT_NAME ) AS O INNER JOIN Information_schema.TABLE_CONSTRAINTS T ON "
			+ " O.Table_Name = T.TABLE_NAME AND T.CONSTRAINT_NAME = O.CONSTRAINT_NAME WHERE O.CONSTRAINT_SCHEMA != 'mysql' "
			+ " AND O.CONSTRAINT_SCHEMA != 'sys' AND O.CONSTRAINT_SCHEMA= #{tableSchema} AND O.TABLE_NAME=#{tableName} AND T.CONSTRAINT_TYPE='PRIMARY KEY';")
	List<Map<String,Object>> allKeyInfoesMysql(@Param("tableName")String tableName,@Param("tableSchema")String tableSchema);
	
	@Select("select cu.CONSTRAINT_NAME,cu.COLUMN_NAME from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name "
			+ " and au.constraint_type = 'P' and au.table_name = #{tableName}")
	List<Map<String,Object>> allKeyInfoesOracle(@Param("tableName")String tableName);
	
	@Select("select CONSTRAINT_NAME, tabname, COLUMN_NAME, colseq from syscat.keycoluse where TABNAME= #{tableName}")
	List<Map<String,Object>> allKeyInfoesDb(@Param("tableName")String tableName);
	
	@Select("SELECT idx.name AS CONSTRAINT_NAME,"
			+ " col.name AS COLUMN_NAME FROM sys.indexes idx JOIN sys.index_columns idxCol ON (idx.object_id = idxCol.object_id"
			+ " AND idx.index_id = idxCol.index_id AND idx.is_primary_key = 1) JOIN sys.tables tab ON (idx.object_id = tab.object_id)"
			+ " JOIN sys.columns col ON (idx.object_id = col.object_id AND idxCol.column_id = col.column_id) WHERE tab.name = #{tableName}")
	List<Map<String,Object>> allKeyInfoesSqlerver(@Param("tableName")String tableName);

	
	@Select("SELECT DISTINCT O.COLUMN_NAME FROM(SELECT	"
			+ " K.CONSTRAINT_SCHEMA, K.CONSTRAINT_NAME,	K.TABLE_SCHEMA,	K.TABLE_NAME,K.COLUMN_NAME,K.REFERENCED_TABLE_SCHEMA,"
			+ " K.REFERENCED_TABLE_NAME,K.REFERENCED_COLUMN_NAME,R.UPDATE_RULE,R.DELETE_RULE,R.UNIQUE_CONSTRAINT_NAME"
			+ " FROM information_schema.KEY_COLUMN_USAGE K LEFT JOIN information_schema.REFERENTIAL_CONSTRAINTS R ON "
			+ " K.CONSTRAINT_NAME = R.CONSTRAINT_NAME ) AS O INNER JOIN Information_schema.TABLE_CONSTRAINTS T ON "
			+ " O.Table_Name = T.TABLE_NAME AND T.CONSTRAINT_NAME = O.CONSTRAINT_NAME WHERE O.CONSTRAINT_SCHEMA != 'mysql' "
			+ " AND O.CONSTRAINT_SCHEMA != 'sys' AND O.CONSTRAINT_SCHEMA= #{tableSchema} AND O.TABLE_NAME=#{tableName} ;")
	List<String> getKeyString(@Param("tableName")String tableName,@Param("tableSchema")String tableSchema);
	
	@Select("SELECT DISTINCT c.COLUMN_NAME FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS t JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE c "
			+ " USING (constraint_name,table_schema,table_name) WHERE t.CONSTRAINT_TYPE = 'PRIMARY KEY' "
			+ " AND t.TABLE_SCHEMA = #{tableSchema} AND t.TABLE_NAME = #{tableName}")
	List<String> getPrimaryKeyMysql(@Param("tableName")String tableName,@Param("tableSchema")String tableSchema);
	
	@Select("select DISTINCT cu.COLUMN_NAME from user_cons_columns cu, user_constraints au where cu.constraint_name = au.constraint_name "
			+ " and au.constraint_type = 'P' and au.table_name = #{tableName}")
	List<String> getPrimaryKeyOracle(@Param("tableName")String tableName);
	
	@Select("select DISTINCT colname as COLUMN_NAME from syscat.keycoluse where TABNAME= #{tableName}")
	List<String> getPrimaryKeyDb(@Param("tableName")String tableName);
	
	@Select("SELECT DISTINCT col.name as COLUMN_NAME FROM sys.indexes idx JOIN sys.index_columns idxCol ON (idx.object_id = idxCol.object_id"
			+ " AND idx.index_id = idxCol.index_id AND idx.is_primary_key = 1) JOIN sys.tables tab ON (idx.object_id = tab.object_id)"
			+ " JOIN sys.columns col ON (idx.object_id = col.object_id AND idxCol.column_id = col.column_id) WHERE tab.name = #{tableName}")
	List<String> getPrimaryKeySqlserver(@Param("tableName")String tableName);
	
	@Select("SELECT   DISTINCT d.name FROM sysindexes a 	JOIN   sysindexkeys   b   ON   a.id=b.id   AND   a.indid=b.indid"
			+ " 	JOIN   sysobjects   c   ON   b.id=c.id 	JOIN   syscolumns   d   ON   b.id=d.id   AND "
			+ "  b.colid=d.colid WHERE   a.indid   NOT IN(0,255) AND   c.name	= #{tableName}")
	List<String> allSordexInfoesSqlerver(@Param("tableName")String tableName);
	
	@Select("SELECT max(length(${fieldName})) FROM ${tableName}")
	Integer getLengthMysql(HashMap<String, Object> req);
	
	@Select("SELECT max(len(${fieldName})) FROM ${tableName}")
	Integer getLengthSqlserver(HashMap<String, Object> req);
	
	//查询表是否存在
	@Select("SELECT DISTINCT TABLE_NAME,TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = #{tableSchema} AND TABLE_NAME=#{tableName}")
	Map<String,Object> getNameMysql(@Param("tableSchema")String tableSchema,@Param("tableName")String tableName);
	//查询表是否存在
	@Select("select t.table_name TABLE_NAME,null TABLE_COMMENT from user_tables t WHERE t.TABLESPACE_NAME=#{tableSchema} AND t.table_name = #{tableName}")
	Map<String,Object> getNameOracle(@Param("tableSchema")String tableSchema,@Param("tableName")String tableName);
	//查询表是否存在
	@Select("select table_name as TABLE_NAME, null TABLE_COMMENT from user_tables where table_schema = current schema AND table_name = #{tableName}")
	Map<String,Object> getNameDb(@Param("tableName")String tableName);
	//查询表是否存在
	@Select("select sys.objects.name as TABLE_NAME from sys.objects where sys.objects.type='U' and sys.objects.name =#{tableName}")
	Map<String,Object> getNameSqlserver(@Param("tableName")String tableName);

	
}
