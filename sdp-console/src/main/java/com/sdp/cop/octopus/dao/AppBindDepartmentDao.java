/*
 * 文件名：AppBindDepartmentDao.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.cop.octopus.entity.AppBindDepartmentInfo;
import com.sdp.frame.base.dao.DaoHelper;


/**
 * app部门关系dao
 * @author zhangyunzhen
 * @version 2017年7月20日
 * @see AppBindDepartmentInfoMapper
 * @since
 */
@Service
public class AppBindDepartmentDao{

	
	/**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(AppBindDepartmentDao.class);
    
    @Autowired
    private DaoHelper daoHelper;
    
    private static String BaseMapperUrl = "com.sdp.cop.octopus.dao.AppBindDepartmentDao.";
    /**
     * Description: <br>
     * 根据app名字查询记录
     * @param app
     * @return 
     * @see
     */
    public List<AppBindDepartmentInfo> findByApp(String app){
    	return daoHelper.queryForList(BaseMapperUrl + "selectByApp", app);
    }
    
    /**
     * Description: <br>
     * 根据app名字获取department字段
     * 
     * @param app
     * @return 
     * @see
     */
    public List<Integer> findDepartmentIdByApp(String app){
    	return daoHelper.queryForList(BaseMapperUrl + "selectDepartmentIdByApp", app);
    }
    
    /**
     * Description: <br>
     * 获取全部app
     * @return 
     * @see
     */
    public List<String> getApps(){
    	return daoHelper.queryForList(BaseMapperUrl + "selectApps");
    }
    
    public void delete(AppBindDepartmentInfo appBindDepartmentInfo){
    	daoHelper.delete(BaseMapperUrl + "deleteByPrimaryKey", appBindDepartmentInfo.getId());
    }
    
    public Object save(AppBindDepartmentInfo appBindDepartmentInfo) {
    	if(appBindDepartmentInfo!=null) {
    		if(appBindDepartmentInfo.getId()!=null){
    			return daoHelper.update(BaseMapperUrl + "updateByPrimaryKeySelective", appBindDepartmentInfo);
    		}
    		else{
    			return daoHelper.insert(BaseMapperUrl + "insertSelective", appBindDepartmentInfo);
    		}
    	}
    	return null;
    }
}
