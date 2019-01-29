package com.sdp.frame.security.authication.loader;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.web.entity.user.User;

/**
 * @author 作者: 吕一凡 
 * @date 2017年1月9日 下午9:11:37 
 * @version 版本: 1.0
 * 用户资源加载实现类
 */
@Component("userAuthicationLoader")
public class UserAuthicationLoader implements AuthicationLoader{
	
	@Autowired(required=true)
	private DaoHelper daoHelper;
	
	/**
	 * 用户资源加载方法
	 */
	@Override
	public List<Object> authLoader(Object object) {
		if(object instanceof User){			
			return daoHelper.queryForList("com.bonc.frame.web.mapper.resources.ResourcesMapper.selectUserRoleResource", ((User)object).getUserId());
		}
		return null;
	}

}
