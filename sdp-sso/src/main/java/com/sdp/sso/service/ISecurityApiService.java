package com.sdp.sso.service;

import java.util.Map;

/**
 * 安全门户数据接口
 * 
 * @author yangjian
 */
public interface ISecurityApiService {

    /**
     * 
     * Description:远程查询组织机构列表 
     * 
     *@param params
     *@return String
     *
     * @see
     */
	String findOrgList(Map<String,Object> params);
	
	/**
     * 
     * Description:远程查询组织机构列表(根据组织类型)
     * 
     *@param params
     *@return String
     *
     * @see
     */
    String findOrgList(String orgType);
	
	/**
     * 
     * Description:远程查询用户列表(针对loginId)
     * 
     *@param loginId
     *@return String
     *
     * @see
     */
    String findUserList(String loginId);
    
    /**
     * 
     * Description:远程查询用户列表 
     * 
     *@return String
     *
     * @see
     */
    String findUserList();
    
    /**
     * 
     * Description: 根据loginId远程获取租户id
     * 
     *@param loginId
     *@return String
     *
     * @see
     */
    String findTenantId(String loginId);
    
}
