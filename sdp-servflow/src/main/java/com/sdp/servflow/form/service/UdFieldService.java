package com.sdp.servflow.form.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.form.entity.Form;
import com.sdp.servflow.form.entity.UdField;

public interface UdFieldService {

	/**
	 * 根据Id查询该字段信息
	 * @param id 字段Id
	 * @return
	 */
	public UdField selectById(String id);
	
	/**
	 * 新增表单字段
	 * @param resource 字段对象
	 * @return
	 */
	public int insert(UdField udField);
	
	
	/**
	 * 停启用字段
	 * @param resource 
	 * @return
	 */
	public int update(UdField udField);
	
	/**
	 * 更新字段
	 * @param resource 
	 * @return
	 */
	public int updateField(UdField udField);
	
	/**
	 * 根据表单查询该表单字段
	 * @param 表单
	 * @return
	 */
	public Map selectByTablename(String start,String  length, Map<String,Object>paramMap);

	
	public List<Map<String, Object>> selectByTablename1(Map<String, Object> paramMap);
}
