package com.sdp.frame.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sdp.frame.security.repository.ISecurityRepository;
import com.sdp.frame.security.repository.SecurityRepositoryFactory;
import com.sdp.frame.security.util.Constant;
import com.sdp.frame.web.entity.resources.Resources;
import com.sdp.frame.web.entity.user.User;

/**
 * 用户信息工具包,用于从Session中获取用户信息
 * @author 作者: 吕一凡 
 * @date 2017年1月19日 下午2:34:58 
 * @version 版本: 1.0
 */
public class UserUtil {
	
	private static ISecurityRepository securityRepository = SecurityRepositoryFactory.getRepository();
	
	/**
	 * 从Session中获取用户个人信息
	 * @param request
	 * @return
	 */
	public static User getUserResource(HttpServletRequest request) {
		return (User)securityRepository.getResourceByType(Constant.ResourceType.UserResource, request);
	}
	
	/**
	 * 获取登陆用户Id
	 */
	public static String getLoginUserId(HttpServletRequest request){
		User curUser = (User)securityRepository.getResourceByType(Constant.ResourceType.UserResource, request);
		if(curUser==null){
			return "admin";
		}
		return curUser.getUserId();
	}
	
	/**
	 * 从Session中获取用于首页展示的用户菜单
	 * @param request
	 * @return
	 */
	public static List<Resources> getUserMenuResource(HttpServletRequest request) {
		List<Resources> list = (List<Resources>)securityRepository.getResourceByType(Constant.ResourceType.UserMenuResourceList, request);
		return convertTree(list);
	}
	
	/**
	 * 从Session中获取用于内连接权限校验的菜单列表
	 * @param request
	 * @return
	 */
	public static List<Resources> getMenuResource(HttpServletRequest request) {
		return (List<Resources>)securityRepository.getResourceByType(Constant.ResourceType.MenuResourceList, request);
	}
	
	/**
	 * 将资源集合转换为树结构
	 * @param resourceList
	 * @return
	 */
	public static List<Resources> convertTree(List<Resources> resourceList){
		List<Resources> result = new ArrayList<Resources>();
		List<Resources> temp = new ArrayList<Resources>();
		Map<String,Resources> menuMap = new HashMap<String,Resources>();
		if(resourceList!=null&&resourceList.size()>0){
			for(Resources api:resourceList){
				Resources tempResources = api.clone();
				temp.add(tempResources);
				menuMap.put(api.getResourcesId(), tempResources);
			}
			for(Resources api:temp){
				if("ROOT".equals(api.getParentId())){
					result.add(api);
				}else{
					List children = menuMap.get(api.getParentId()).getChildren();
					if(children != null){
						children.add(api);
						menuMap.get(api.getParentId()).setChildren(children);
					}
					else{
						children = new ArrayList();
						children.add(api);
						menuMap.get(api.getParentId()).setChildren(children);
					}
				}
			}
		}
		return result;
	}
}
