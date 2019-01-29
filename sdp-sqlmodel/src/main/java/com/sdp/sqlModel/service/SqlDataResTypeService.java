package com.sdp.sqlModel.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.entity.SqlDataResType;
import com.sdp.sqlModel.mapper.SqlDataResTypeMapper;


/**
 * 数据源类型Service
 *
 * @author 
 */
@Service
public class SqlDataResTypeService {
	 /**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SqlDataResTypeService.class);
    
    @Autowired
    private SqlDataResTypeMapper sqlDataResTypeDao;
    
    public void save(SqlDataResType sqlDataResType){
    	User userInfo = CurrentUserUtils.getInstance().getUser();
		sqlDataResType.setTenantId(userInfo.getTenantId());
		if(StringUtils.isNotBlank(sqlDataResType.getDataResTypeId())){
			sqlDataResTypeDao.update(sqlDataResType);
		}else{
			sqlDataResTypeDao.save(sqlDataResType);
		}
    }
    
    public List<SqlDataResType> findByTenantId(){
    	User userInfo = CurrentUserUtils.getInstance().getUser();
    	System.out.println("tenantId:"+userInfo.getTenantId());
		return sqlDataResTypeDao.findByTenantId(userInfo.getTenantId());
    }
    
    public SqlDataResType findSqlDataResType(String dataResTypeId){
    	return sqlDataResTypeDao.findByDataResTypeId(dataResTypeId);
    }
    
}
