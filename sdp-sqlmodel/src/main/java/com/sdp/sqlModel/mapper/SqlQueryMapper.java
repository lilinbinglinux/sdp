package com.sdp.sqlModel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SqlQueryMapper {

	
	@Select("${pageSql}")
	List<Map<String, Object>> pageQuery(@Param("pageSql")String pageSql);
	
	@Insert("${insertSql}")
	void insert(@Param("insertSql")String insertSql);
	
	@Insert("INSERT INTO ${tableSqlNameNew} SELECT * FROM ${tableSqlNameOld} ")
	void insertInfo(@Param("tableSqlNameNew")String tableSqlNameNew,@Param("tableSqlNameOld")String tableSqlNameOld);
	
	@Delete("${deleteSql}")
	void delete(@Param("deleteSql")String deleteSql);
	
	@Update("${updateSql}")
	void update(@Param("updateSql")String updateSql);
	
	
	@Select("${sql}")
	List<Map<String, Object>> listQuery(@Param("sql")String sql);
	
	@Select("${sql}")
	String stringQuery(@Param("sql")String sql);
	
	@Select("${sql}")
	Integer integerQuery(@Param("sql")String sql);
	
	@Select("${sql}")
	Boolean booleanQuery(@Param("sql")String sql);
	
	@Select("${sql}")
	Map<String, Object> mapQuery(@Param("sql")String sql);
	
	@Select("${sql}")
	List<String> listStringQuery(@Param("sql")String sql);
	
	@Select("${sql}")
	List<Integer> listIntegerQuery(@Param("sql")String sql);
	
	@Select("${sql}")
	List<Long> listLongQuery(@Param("sql")String sql);
	
//	@Select("select INDEX_NAME indname, COLUMN_NAME colnames from User_Ind_Columns where Table_Name = ${tableName} ")
//	List<Map<String,Object>> findIndexOracle(@Param("tableName")String tableName);
//	
	@Select("${sqlTest}")
	List<Map<String,Object>> findIndexOracle(@Param("sqlTest")String sqlTest);
	
	
	@Select("select * from ${tableSqlName} limit 1")
	List<Map<String,Object>> findOne(@Param("tableSqlName")String tableSqlName );
}
