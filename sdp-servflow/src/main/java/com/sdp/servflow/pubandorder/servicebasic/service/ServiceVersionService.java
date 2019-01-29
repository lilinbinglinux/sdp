package com.sdp.servflow.pubandorder.servicebasic.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;

/**
 * Description:服务版本表的更新
 *
 * @author 牛浩轩
 * @date Created on 2017/11/8.
 */
public interface ServiceVersionService {
	
	/**
	 * 根据主键查询
	 * @param serVerId
	 * @return
	 */
    ServiceVersionBean getByPrimaryKey(String serVerId);
    /**
     * 按照条件查找
     * @param map
     * @return
     */
    List<ServiceVersionBean> getAllByCondition(Map<String, String> map);

    /**
     * 新增
     * @param serviceVersionBean
     * @return
     */
    int insert(ServiceVersionBean serviceVersionBean);

    /**
     * 更新数据
     * @param serviceVersionBean
     * @return
     */
    int update(ServiceVersionBean serviceVersionBean);

    /**
     * 删除数据
     * @param serVersion
     * @return
     */
    int delete(String serVersion);

    /**
     * 根据serId及serVersion删除数据
     * @param serviceVersionBean
     * @return
     */
    int deleteBySerId(ServiceVersionBean serviceVersionBean);
    
    /**
     * 根据serId查找最新版本
     * @param serId
     * @return
     */
	List<ServiceVersionBean> getMaxVersionByCondition(Map<String,String> map);
}
