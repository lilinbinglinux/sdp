package com.sdp.servflow.pubandorder.pub.service;

import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.sdp.servflow.pubandorder.pub.model.InterfaceParamPo;
import com.sdp.servflow.pubandorder.pub.model.ReqparamBean;

/**
 * 
 * ReqparamBean对应的Service接口
 *
 * @author ZY
 * @version 2017年6月10日
 * @see ReqparamBeanService
 * @since
 */
public interface ReqparamBeanService {
    
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
    Map selectAll(String start,String length,Map<String,Object>paramMap);
    
    /**
     * 
     * Description: 插入
     * 
     *@param reqModel
     *@return int
     *
     * @see
     */
    int insert(ReqparamBean reqModel);
    
    /**
     * 
     * Description: 更新
     * 
     *@param reqModel
     *@return int
     *
     * @see
     */
    int update(ReqparamBean reqModel);
    
    /**
     * 
     * Description: 根据id删除 
     * 
     *@param id
     *@return int
     *
     * @see
     */
    int deleteByParamId(String id);
    
    /**
     * 
     * Description:通过xml的方式插入 
     * 
     *@param xmlStr
     *@param pubid
     *@throws DocumentException void
     *
     * @see
     */
    void insertByxml(String xmlStr, String pubid);
    
    /**
     * 
     * Description: 根据主键查询
     * 
     *@param id
     *@return ReqparamBean
     *
     * @see
     */
    ReqparamBean getParamById(String id);
    
    /**
     * 
     * Description: 根据条件查询参数
     * 
     *@param paramMap
     *@return List<ReqparamBean>
     *
     * @see
     */
    List<ReqparamBean> getAllByCondition(Map<String, Object> paramMap);
    
    /**
     * 
     * Description:根据父子级关系删除 
     * 
     *@param id
     *@return String
     *
     * @see
     */
    String deleteParams(String id);
    
    /**
     * 
     * Description:查看接口是否存在请求参数 
     * 
     *@param pubid
     *@return int
     *
     * @see
     */
    int getCountReqParam(String pubid);
    
    /**
     * 
     * Description:获取ztree结构树形数据 
     * @param flowChartId 
     * 
     *@param paramMap
     *@return List<ReqparamBean>
     *
     * @see
     */
    List<InterfaceParamPo> getTreeNodesParams(String flowChartId,String type,String pubids);
    
    /**
     * 
     * Description:根据条件删除 
     * 
     *@param map void
     *
     * @see
     */
    void deleteByCondition(Map<String, String> map);
    
    /**
     * 
     * Description:查询编排的参数映射和参数 
     * 
     *@param map
     *@return List<ReqparamBean>
     *
     * @see
     */
    List<ReqparamBean> getParamvLayoutParam(Map<String, Object> map);
    
    /*
     * 添加List对象
     */
    void insertObj(Map<String,Object> map);
    
    //List<ReqparamBean> getTreeChildrenData(String type,String pubids);
    
    //参数排序——向上
    ReqparamBean upsort(Map<String,Object> map);
    
    //参数排序——向下
    ReqparamBean downSort(Map<String,Object> map);
    
}
