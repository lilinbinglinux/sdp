package com.sdp.servflow.pubandorder.security.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.security.model.SecurityCodeBean;

/**
 * 
 * token安全验证Service
 *
 * @author ZY
 * @version 2017年7月24日
 * @see SecurityCodeService
 * @since
 */
public interface SecurityCodeService {
    
    /**
     * 
     * Description:根据条件查询所有 
     * 
     *@param map
     *@return List<SecurityCodeBean>
     *
     * @see
     */
    List<SecurityCodeBean> getAllByCondition(Map<String,String> map);
    
    /**
     * 
     * Description:添加 
     * 
     *@param securityCodeBean void
     *
     * @see
     */
    void insert(SecurityCodeBean securityCodeBean);
    
    /**
     * 
     * Description:验证token是否有效，如果已经存在说明已经申请过，不能重复申请 
     * 
     *@param appId
     *@return String
     *
     * @see
     */
    String getValidateToken(String appId);
    
}
