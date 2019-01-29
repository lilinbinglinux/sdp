package com.sdp.servflow.form.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sdp.frame.util.IdUtil;
import com.sdp.frame.util.JsonUtils;
import com.sdp.frame.web.entity.tenant.Tenant;
import com.sdp.servflow.form.entity.Form;
import com.sdp.servflow.form.entity.UdField;
import com.sdp.servflow.form.service.FormService;
import com.sdp.servflow.form.service.UdFieldService;


	@Controller
	@RequestMapping("/form")
	public class FormController {
		

		@Resource
		private FormService formService;
		@Resource
		private UdFieldService udFieldSerice;

		@RequestMapping("/index")
		public String index() {
			return "form/formmake";
		}
		
		@RequestMapping("/design")
		public String index1() {
			return "systemconfig/design";
		}
		
		@ResponseBody
		@RequestMapping("/selectPage")
		public Map selectPage(String start, String length, String jsonStr) {
			Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
			Map map = formService.selectAll(start, length, paramMap);
			return map;
		}
		
		@ResponseBody
		@RequestMapping(value = "/insert", method = RequestMethod.POST)
		public int insert(Form form) {
			form.setId(IdUtil.createId());
			return formService.insert(form);
		}
		
		@ResponseBody
		@RequestMapping(value = "/insertField", method = RequestMethod.POST)
		public int insert(UdField udField) {
			udField.setId(IdUtil.createId());
			udField.setDelflag("0");
			return udFieldSerice.insert(udField);
		}
		
		
		@ResponseBody
		@RequestMapping(value="/updateForm",method=RequestMethod.POST)
		public int updateForm(Form form){
			return formService.update(form);
		}
		
		@ResponseBody
		@RequestMapping("/getFormById")
		public Form selectById(String id) {
			Form form = formService.selectById(id);
			return form;
		}
		
		@ResponseBody
		@RequestMapping("/selectField")
		public Map selectField(String start, String length,String jsonStr){
			String templatecode=jsonStr;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("templatecode",templatecode);
			paramMap.put("delflag", "0");
			Object json =  JSONObject.toJSON(paramMap);
			Map<String, Object> paramMap1 = JsonUtils.stringToCollect(JsonUtils.toJSONString(json));
		
			return udFieldSerice.selectByTablename(start, length, paramMap1);
		}
		//启用停用切换
		@ResponseBody
		@RequestMapping("/fieldState")
		public Map changeSelect(String start, String length,String json){
			Map<String, Object> paramMap1 = JsonUtils.stringToCollect(json);
			return udFieldSerice.selectByTablename(start, length, paramMap1);
		}
		
		@ResponseBody
		@RequestMapping("/getManageByDeflag")
		public Map selectByDeflag(String start, String length,String jsonStr) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			return paramMap;
		}
		
		@ResponseBody
		@RequestMapping("/fieldStateChange")
		public int stopById(String delflag, String id) {
			UdField udField=new UdField();
			udField.setDelflag(delflag);
			udField.setId(id);
			return udFieldSerice.update(udField);
		}
		
		@ResponseBody
		@RequestMapping("/select")
		public List  selectField1(String templatecode){
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("templatecode", templatecode);
			Object json =  JSONObject.toJSON(paramMap);
			Map<String, Object> paramMap1 = JsonUtils.stringToCollect(JsonUtils.toJSONString(json));
			List<Map<String, Object>> m=udFieldSerice.selectByTablename1(paramMap1);
			/*System.err.println(JsonUtils.toJSONString(m));
			JSONArray jsonArray=JSONArray.fromObject(m); */
			return m;
		}
		
		@ResponseBody
		@RequestMapping(value = "/delete", method = RequestMethod.POST)
		public int delete(String id) {
			return formService.deleteByFormId(id);
		}
		
		@ResponseBody
		@RequestMapping("/getManageById")
		public UdField getManageById(String id) {
			UdField udField = udFieldSerice.selectById(id);
			return udField;
		}
		
		@ResponseBody
		@RequestMapping(value="/updateField",method=RequestMethod.POST)
		public int update(UdField udField){
			return udFieldSerice.updateField(udField);
		}

		
		

}
