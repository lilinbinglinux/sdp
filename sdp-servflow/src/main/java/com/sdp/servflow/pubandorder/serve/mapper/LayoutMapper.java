package com.sdp.servflow.pubandorder.serve.mapper;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sdp.frame.base.dao.DaoHelper;


/***
 * 
 * 服务编排
 *
 * @author 任壮
 * @version 2017年8月8日
 * @see LayoutMapper
 * @since
 */
@Component
public class LayoutMapper {
    /**
     * 封装的dao
     */
    @Resource
    private DaoHelper daoHelper;

    /***
    * 
    * Description:获取服务编排的顺序
    * 
    *@param hashMap
    *@return HashMap<String,Object>
    *
    * @see
    */
    public List<HashMap<String, Object>> getLayoutOrder(HashMap<String, Object> hashMap) {
        return daoHelper.queryForList(
            "com.bonc.servflow.pubandorder.serve.mapper.LayoutMapper.getLayoutOrder", hashMap);
    }
    public List<HashMap<String, Object>> getLayoutSort(HashMap<String, Object> hashMap) {
        return daoHelper.queryForList(
            "com.bonc.servflow.pubandorder.serve.mapper.LayoutMapper.getLayoutSort", hashMap);
    }
    /***
     * 
     * Description:获取服务编排的参数映射关系
     * 
     *@param hashMap
     *@return List<HashMap<String,Object>>
     *
     * @see
     */
    public List<HashMap<String, Object>> getLayout(HashMap<String, Object> hashMap) {
        return daoHelper.queryForList(
            "com.bonc.servflow.pubandorder.serve.mapper.LayoutMapper.getLayout", hashMap);
    }
    
}
