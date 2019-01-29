package com.sdp.servflow.pubandorder.serapitype.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.serapitype.model.ServiceTypeBean;

/**
 * Description:接口类型维护
 *
 * @author 牛浩轩
 * @date Created on 2017/10/31.
 */
public interface ServiceTypeService {
	/**
	 * 根据主键查找
	 * @param id
	 * @return
	 */
	ServiceTypeBean selectByPrimaryKey(String id);
	
    /**
     * 按照条件查找(parentId 为like查询)
     * @param map
     * @return
     */
    List<ServiceTypeBean> getAllByCondition(Map<String, String> map);

    /**
     * 按照条件查找(parentId 为like查询),并输出相应的时间格式
     * @param serviceTypeBean
     * @return
     */
    List<ServiceTypeBean> selectgetTimeByCondition(ServiceTypeBean serviceTypeBean);
    
    /**
     * 按照条件查找（parentId 为相等查询）
     * @param map
     * @return
     */
    List<ServiceTypeBean> getAllEqalsByCondition(Map<String, String> map);
    

    /**
     * 新增类型
     * @param serviceTypeBean
     * @return
     */
    int insert(ServiceTypeBean serviceTypeBean);

    /**
     * 更新类型
     * @param serviceTypeBean
     * @return
     */
    int update(ServiceTypeBean serviceTypeBean);

    /**
     * 删除类型
     * @param serTypeId
     * @return
     */
    int delete(String serTypeId);

    /**
     * 过滤信息，展示树节点
     * @return
     */
    List<ServiceTypeBean> selectFilterDate();
    
    /**
     * 查询一个类型下所有的子类型（包括父节点）
     * @return
     */
    List<ServiceTypeBean> selectCollection(String serTypeId);
}
