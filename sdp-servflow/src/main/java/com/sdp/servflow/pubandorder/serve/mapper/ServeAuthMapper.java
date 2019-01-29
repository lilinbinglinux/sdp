package com.sdp.servflow.pubandorder.serve.mapper;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sdp.frame.base.dao.DaoHelper;


/**
 * 服务鉴权
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see ServeAuthMapper
 * @since
 */
@Component
public class ServeAuthMapper {
    /**
     * 封装的dao
     */
    @Resource
    private DaoHelper daoHelper;

    /***
     * 
     * Description: 获取appId的ip信息
     * 
     *@param hashMap
     *@return HashMap<String,Object>
     *
     * @see
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> getAuth(HashMap<String, Object> hashMap) {
        return (HashMap<String, Object>)daoHelper.queryOne(
            "com.bonc.servflow.pubandorder.serve.mapper.ServeAuthMapper.getAuth", hashMap);
    }
    /***
     * 
     * Description: 获取appId用户的信息
     * 
     *@param res
     *@return HashMap<String,Object>
     *
     * @see
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> getUserAuth(HashMap<String, Object> res) {
        return (HashMap<String, Object>)daoHelper.queryOne(
            "com.bonc.servflow.pubandorder.serve.mapper.ServeAuthMapper.getUserAuth", res);
    };
    /***
     * 
     * Description: 获取注册的服务
     * 
     *@param res
     *@return HashMap<String,Object>
     *
     * @see
     */
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> getPubSer(HashMap<String, Object> res) {
        return (HashMap<String, Object>)daoHelper.queryOne(
            "com.bonc.servflow.pubandorder.serve.mapper.ServeAuthMapper.getPubSer", res);
    };
    public List<HashMap<String, Object>> getPubSerParm(Map<String, Object> pubSer) {
        return daoHelper.queryForList(
            "com.bonc.servflow.pubandorder.serve.mapper.ServeAuthMapper.getPubSerParm", pubSer);
    };

}
