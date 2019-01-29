package com.sdp.servflow.serlayout.sso.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdp.servflow.serlayout.sso.model.SerspLoginBean;

public interface SerspLoginBeanService {
	
	/**
	 * 分页查询所有
	 * @param start
	 * @param length
	 * @param paramMap
	 * @return
	 */
	 Map selectPage(String start,String length,Map<String,Object>paramMap);
	 
	 /**
	  * 根据主键查询
	  * @param serspLoginBean
	  * @return
	  */
	 SerspLoginBean getAllByPrimaryKey(String spLoginId); 
	 
	 /**
	  * 根据条件查询
	  * @param map
	  * @return
	  */
	 List<SerspLoginBean> getAllByCondition(Map<String,String> map);
	 
	 /**
	  * 添加
	  * @param serspLoginBean
	  */
	 void insert(SerspLoginBean serspLoginBean);
	 
	 /**
	  * 更新
	  * @param serspLoginBean
	  */
	 void update(SerspLoginBean serspLoginBean);
	 
	 /**
	  * 删除
	  * @param serspLoginBean
	  */
	 void delete(String sploginid);
	 
	 /**
	  * 流程解析
	  * @param publicParam
	  * @param busiParm
	  * @param httpClient
	  * @param esbXml
	  * @return
	  */
	 void serAnalysis(HttpServletRequest request,HttpServletResponse response,Map<String,Object> paramMap,String busiParm) throws Exception ;
	 
	 /**
	  * 查看所有参数详情
	  * @param sploginid
	  * @return
	  */
	 Map<String,Object> getParams(String sploginid);
	 
	 
	 
	 
	

}
