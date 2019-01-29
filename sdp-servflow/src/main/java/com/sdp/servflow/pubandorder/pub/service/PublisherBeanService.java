package com.sdp.servflow.pubandorder.pub.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.pub.model.PublisherBean;



/**
 * 
 * PublisherBean对应Service接口
 *
 * @author ZY
 * @version 2017年6月10日
 * @see PublisherBeanService
 * @since
 */
public interface PublisherBeanService {
    
    /**
     * 
     * Description: 分页，条件查询所有 
     * 
     *@param start
     *@param length
     *@param paramMap
     *@return Map
     *
     * @see
     */
    @SuppressWarnings("rawtypes")
    Map selectAll(String start,String length,Map<String,Object>paramMap);
    
    /**
     * 
     * Description:分页查询（连表查推送状态） 
     * 
     *@param start
     *@param length
     *@param paramMap
     *@return Map
     *
     * @see
     */
    @SuppressWarnings("rawtypes")
    Map selectPage(String start,String length,Map<String,Object>paramMap);
	
	/**
	 * 
	 * Description: 插入
	 * 
	 *@param publisherBean
	 *@return int
	 *
	 * @see
	 */
    int insert(PublisherBean publisherBean);
	
	/**
	 * 
	 * Description:更新 
	 * 
	 *@param publisherBean
	 *@return int
	 *
	 * @see
	 */
    int update(PublisherBean publisherBean);
	
	/**
	 * 
	 * Description: 根据id删除
	 * 
	 *@param pubid
	 *@return int
	 *
	 * @see
	 */
    int deleteByPubId(String pubid);
    
    /**
     * 
     * Description: 根据注册服务id删除下面的参数
     * 
     *@param pubid
     *@return int
     *
     * @see
     */
    int deletePubParamByPubCondition(Map<String, Object> condition);
    
    /**
     * 
     * Description:根据条件删除 
     * 
     *@param parammap
     *@return int
     *
     * @see
     */
    int deleteByCondition(Map<String,String> parammap);
    
    /**
     * 
     * Description:根据传入条件查询所有 
     * 
     *@param parammap
     *@return List<PublisherBean>
     *
     * @see
     */
    List<PublisherBean> getAllByCondition(Map<String,String> parammap);
    
    /**
     * 
     * Description: 根据主键查询
     * 
     *@param pubid
     *@return PublisherBean
     *
     * @see
     */
    PublisherBean getPubById(String pubid);
	
}
