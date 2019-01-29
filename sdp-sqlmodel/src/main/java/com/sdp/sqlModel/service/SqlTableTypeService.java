package com.sdp.sqlModel.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.dataSource.DynamicDataSourceContextHolder;
import com.sdp.sqlModel.entity.SqlDataRes;
import com.sdp.sqlModel.entity.SqlDataResType;
import com.sdp.sqlModel.entity.SqlTableType;
import com.sdp.sqlModel.mapper.SqlDataResMapper;
import com.sdp.sqlModel.mapper.SqlDataResTypeMapper;
import com.sdp.sqlModel.mapper.SqlTableMapper;
import com.sdp.sqlModel.mapper.SqlTableTypeMapper;
import com.sdp.util.DateUtils;

@Service
public class SqlTableTypeService {
	@Autowired SqlTableTypeMapper sqlTableTypeDao;
	@Autowired SqlTableMapper sqlTableDao;
	@Autowired SqlDataResMapper sqlDataResMapper;
	@Autowired SqlDataResTypeMapper sqlDataResTypeMapper;
	
	private static final Logger log = LoggerFactory.getLogger(SqlTableTypeService.class);

	// --- 保存表类型数据 ---
	public void saveSqlTableType(SqlTableType sqlTableType) {

		User userInfo = CurrentUserUtils.getInstance().getUser();
		String createBy = userInfo.getLoginId();
		String tenantId = userInfo.getTenantId();
		Date current = DateUtils.getCurrentDate();
		sqlTableType.setTenantId(tenantId);
		sqlTableType.setCreateDate(current);
		sqlTableType.setCreateBy(createBy);
		if(StringUtils.isNotBlank(sqlTableType.getTableTypeId())){
			sqlTableTypeDao.updateAll(sqlTableType);
			return;
		}else{
			sqlTableTypeDao.save(sqlTableType);
		}
		String parentId = sqlTableType.getParentId();
		String tableTypeIdNew = sqlTableType.getTableTypeId();
		
		log.info("tableTypeIdNew:"+tableTypeIdNew);
		// --- 已知的表类型路径 ---
		String tableTypePathOld;
		// --- 当前即将存放的表类型路径 ---
		String tableTypePathNew;
		log.info("parentId:"+parentId);
		if(parentId==null||parentId.equals("")){
			tableTypePathNew = "/"+ tableTypeIdNew+"/";
		}else{
			tableTypePathOld = sqlTableTypeDao.findByTableTypeIdAndTenantId(parentId,tenantId);
			log.info("tableTypePathOld:"+tableTypePathOld);
			tableTypePathNew = tableTypePathOld + tableTypeIdNew + "/" ;
		}	
		sqlTableType.setTableTypePath(tableTypePathNew);
		sqlTableTypeDao.update(sqlTableType);

	}

	// --- 删除表类型数据 ---
	public Status deleteSqlTableType(String tableTypeId) {
		Status status;
		User userInfo = CurrentUserUtils.getInstance().getUser();
		String tenantId = userInfo.getTenantId();
//		String tenantId = "tenant_system";
		List<String> tableTypeIdList = sqlTableDao.findByTenantId(tenantId);
		if (tableTypeId != null) {
			int num = tableTypeIdList.size();
			log.info("num="+num);
			if( tableTypeIdList.size() != 0){
				if (tableTypeIdList.contains(tableTypeId)) {
					log.info("该表类型正在使用,不能删除。");
					status = new Status("该表类型正在使用,不能删除数据", Dictionary.HttpStatus.INVALID_REQUEST.value);
					return status;
				} 
			}
			sqlTableTypeDao.deleteByTableTypeIdAndTenantId(tableTypeId, tenantId);
			status = new Status("该表类型未被使用,删除数据成功", Dictionary.HttpStatus.OK.value);		
		} else {
			status = new Status("参数有误,删除数据失败", Dictionary.HttpStatus.INVALID_REQUEST.value);
		} 
		return status;
	}

	
	
	public List<Map<String, Object>> selectSqlTableType(String parentId){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		String tenantId = userInfo.getTenantId();
		log.info("tenantId:"+ tenantId);
		List<Map<String,Object>> tableTypeNameList = sqlTableTypeDao.findByTenantId(tenantId);

		return tableTypeNameList;
	}
	
	public SqlTableType findSqlTableType(String tableTypeId){
		return sqlTableTypeDao.findOne(tableTypeId);
	}

	public List<SqlTableType> selectTableTypes(String dataResId) {
		User userInfo = CurrentUserUtils.getInstance().getUser();
		String tenantId = userInfo.getTenantId();
		log.info("tenantId:"+ tenantId);
		
		List<SqlTableType> tableList = sqlTableTypeDao.selectTablesByDtatResId(tenantId,dataResId);
		
		return tableList;
	}
	
	public List<String> findByParentId(String parentId,String dataResId) {
		User userInfo = CurrentUserUtils.getInstance().getUser();
		String tenantId = userInfo.getTenantId();
		List<String> tableList = sqlTableTypeDao.findByParentId(tenantId,parentId,dataResId);
		return tableList;
	}
	
	public int findByFieldName(HashMap<String, Object> req) {
		int length = 0;
		SqlDataRes sqlDataRes = sqlDataResMapper.findByDataResId(req.get("dataResId").toString());
		SqlDataResType sqlDataResType = sqlDataResTypeMapper.findByDataResTypeId(sqlDataRes.getDataResTypeId());
		try{
			DynamicDataSourceContextHolder.setDataSourceType(sqlDataRes.getDataResId());
			if(sqlDataResType.getResType().equals("1")){//mysql
				length = sqlDataResMapper.getLengthMysql(req);
	    	}else if(sqlDataResType.getResType().equals("2")){//oracle
	    		length = sqlDataResMapper.getLengthMysql(req);
	    	}else if(sqlDataResType.getResType().equals("5")){//sqlserver
	    		length = sqlDataResMapper.getLengthSqlserver( req);
	    	}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DynamicDataSourceContextHolder.clearDataSourceType();
		}
		return length;
	}
	

}
