package com.sdp.sqlModel.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sdp.sqlModel.entity.SqlLock;




/**
 * 锁表Dao
 */
public interface SqlLockMapper{
	
	String sql="DATA_RES_ID AS dataResId, DATA_STATUS AS dataStatus,CREATE_DATE AS createDate,LOGIN_ID AS loginId";
	
	@Insert("INSERT INTO SQL_LOCK (DATA_RES_ID, DATA_STATUS, CREATE_DATE, LOGIN_ID,uuid) VALUES (#{dataResId},#{dataStatus},"
			+ "NOW(),#{loginId},#{uuid})")
	void save(SqlLock sqlLock);
	
	@Delete("DELETE FROM SQL_LOCK WHERE DATA_RES_ID = #{dataResId}")
	void deleteByDataResId(@Param("dataResId")String dataResId);
	
	@Update("UPDATE SQL_LOCK SET DATA_STATUS = #{dataStatus},CREATE_DATE=NOW(),uuid = #{uuid} WHERE DATA_RES_ID=#{dataResId}")
	void update(SqlLock sqlLock);
	
	@Update("UPDATE SQL_LOCK SET DATA_STATUS = '0',CREATE_DATE=NOW() WHERE uuid=#{uuid}")
	void updateUuid(@Param("uuid")String uuid);
	
	@Select("SELECT "+sql+" FROM SQL_LOCK WHERE DATA_RES_ID=#{dataResId}")
	SqlLock findByDataResId(@Param("dataResId")String dataResId);

	@Select("SELECT "+sql+" FROM SQL_LOCK WHERE DATA_RES_ID=#{dataResId} AND uuid=#{uuid}")
	SqlLock findByUuid(@Param("dataResId")String dataResId,@Param("uuid")String uuid);
}
