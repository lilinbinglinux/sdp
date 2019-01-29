package com.sdp.servflow.pubandorder.servicebasic.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;

/**
 * Description:服务表操作
 *
 * @author 牛浩轩
 * @date Created on 2017/11/8.
 */
public interface ServiceMainService {
	
	/**
     * 按照主键查找
     * @param serId
     * @return
     */
    ServiceMainBean getByPrimaryKey(String serId);

    /**
     * 按照条件查找（名称为模糊查询）
     * @param map
     * @return
     */
    List<ServiceMainBean> getAllByConditionLikeName(Map<String, String> map);
    
    /**
     * 按照条件查找
     * @param map
     * @return
     */
    List<ServiceMainBean> getAllByCondition(Map<String, String> map);

    /**
     * 根据名称和版本查找
     * @param map
     * @return
     */
    List<ServiceMainBean> getBySerNameAndVersion(Map<String, String> map);

    /**
     * 按照条件查找(结果包含服务类型)
     * @param paramMap
     * @param start
     * @param length
     * @return
     */
    Map<String, Object> getAllAndSerTypeName(Map<String, Object> paramMap, String start, String length);

    /**
     * 查找某个用户的全部服务
     * @param paramMap
     * @param start
     * @param length
     * @return
     */
    Map<String, Object> selectPage(Map<String,Object> paramMap,String start,String length);

    /**
     * 新增
     * @param serviceMainBean
     * @return
     */
    int insert(ServiceMainBean serviceMainBean);

    /**
     * 更新数据
     * @param serviceMainBean
     * @return
     */
    int update(ServiceMainBean serviceMainBean);

    /**
     * 删除数据
     * @param serId
     * @return
     */
    int delete(String serId);

    /**
     * 暴露异步url
     * @param id
     * @param cgfKey
     * @return
     */
    String getUrl(String id, String cgfKey);

}
