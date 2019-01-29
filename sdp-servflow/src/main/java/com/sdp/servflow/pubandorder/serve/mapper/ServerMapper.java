package com.sdp.servflow.pubandorder.serve.mapper;


import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sdp.frame.base.dao.DaoHelper;


/***
 * 
 * 服务编排
 *
 * @author 任壮
 * @version 2017年10月26日
 * @see ServerMapper
 * @since
 */
@Component
public class ServerMapper {
    /**
     * 封装的dao
     */
    @Resource
    private DaoHelper daoHelper;

    /***
    * 
    * Description:获取服务编排的xml描述信息
    * 
    *@param hashMap
    *@return HashMap<String,Object>
    *
    * @see
    */
    @SuppressWarnings("unchecked")
    public HashMap<String, String> getServerFlow(HashMap<String, Object> hashMap) {
        return (HashMap<String, String>)daoHelper.queryOne(
            "com.bonc.servflow.pubandorder.serve.mapper.ServerMapper.getServerFlow", hashMap);
    }
}
