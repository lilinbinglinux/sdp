package com.sdp.servflow.pubandorder.pub.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.pub.model.ProjectBean;

/**
 * 
 * ProjectBean对应Service接口
 *
 * @author ZY
 * @version 2017年6月10日
 * @see ProjectBeanService
 * @since
 */
public interface ProjectBeanService {
    
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
     * Description: 插入
     * 
     *@param project
     *@return int
     *
     * @see
     */
    int insert(ProjectBean project);
	
	/**
	 * 
	 * Description: 更新
	 * 
	 *@param project
	 *@return int
	 *
	 * @see
	 */
    int update(ProjectBean project);
    
	/**
	 * 
	 * Description:根据id删除 
	 * 
	 *@param proid
	 *@return int
	 *
	 * @see
	 */
    int deleteByProId(String proid);
    
    
    /**
     * 
     * Description:根据条件查询 
     * 
     *@param paramMap
     *@return List<ProjectBean>
     *
     * @see
     */
    List<ProjectBean> getAllByCondition(Map<String,String>paramMap);
    
    /**
     * 
     * Description: 根据主键查询
     * 
     *@param proid
     *@return ProjectBean
     *
     * @see
     */
    ProjectBean getProById(String proid);
	
}
