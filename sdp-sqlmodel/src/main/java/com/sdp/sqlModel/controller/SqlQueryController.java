package com.sdp.sqlModel.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sdp.common.BaseException;
import com.sdp.common.entity.Status;
import com.sdp.sqlModel.entity.ProDataApi;
import com.sdp.sqlModel.entity.ProModel;
import com.sdp.sqlModel.entity.SqlDataRes;
import com.sdp.sqlModel.service.ProDataApiService;
import com.sdp.sqlModel.service.ProModelService;
import com.sdp.sqlModel.service.SqlQueryService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("v1/sqlQuery/")
public class SqlQueryController {
	@Autowired
	private SqlQueryService sqlQueryService;
	
	@Autowired
	private ProModelService proModelService;
	
	@Autowired
	private ProDataApiService proDataApiService;

	/**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(SqlQueryController.class);
	

	/**
	 * 批量插入值--如果表主键是varchar且不为空是值必传
	 * List<map<filedSqlName,value>>--保存表信息--fieldInfoes
	 * map<String,String>--tableName,fieldInfoes
	 * value--分库分表规则实际值
	 * @return
	 * @throws BaseException 
	 */
//	@RequestMapping(value="insertModel/{dataResId}",method=RequestMethod.POST)
//	@ResponseBody
//	public Status insertModel(@PathVariable("dataResId") String dataResId,@RequestBody HashMap<String,Object> req) throws BaseException{
//		Status status = null;
//		try{
//			String tableName = (String) req.get("tableName");
//			List<Map<String,Object>> fieldValues = (List<Map<String,Object>>) req.get("fieldInfoes");
//			String value = (String) req.get("value");
//			status = sqlQueryService.insertModel(tableName, fieldValues,dataResId,value);
//		}catch (Exception ex) {
//			LOG.error(ex.getMessage());
//			throw new BaseException(ex.getMessage());
//		}
//		return status;
//	}
	/**
	 * 单条插入值--如果表主键是varchar且不为空是值必传
	 * map<filedSqlName,value>--保存表信息
	 * map<String,String>--tableName,fieldInfoes
	 * value--分库分表规则实际值
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(value="insertSingle/{dataResId}",method=RequestMethod.POST)
	@ResponseBody
	public Status insertSingle(@PathVariable("dataResId") String dataResId,@RequestBody HashMap<String,Object> req) throws BaseException{
		Status status = null;
		try{
			Map<String,Object> fieldInfoes = (Map<String, Object>) req.get("fieldInfoes");
			String tableName = (String) req.get("tableName");
			String value = (String) req.get("value");
			status = sqlQueryService.insertSingle( fieldInfoes, tableName,dataResId,value);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			throw new BaseException(ex.getMessage());
		}
		return status;
	}
	/**
	 * 分页接口
	 * @param req
	 * @return
	 * @throws BaseException 
	 */
	@RequestMapping(value="pageModel/{dataResId}",method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String,Object> pageModel(@PathVariable("dataResId") String dataResId,@RequestBody HashMap<String,Object> req) throws BaseException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		try{
			req.put("dataResId", dataResId);
			map.put("code", "000000");
			map.put("result",sqlQueryService.pageQuery(req));
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			map.put("code", "999999");
			map.put("message","系统异常");
			throw new BaseException(ex.getMessage());
		}
		return map;
	}
	/**
	 * 单表删除数据
	 * http://ip:port/bconsole/v1/sqlQuery/deleteDataModel
	 * {
     *  "conditions":{"条件字段":"条件（注意是否带引号）"},为空默认清空整张表
     *  "tableName":"表名",
     *  "dataResId":"数据源ID",
     *  "value":""
     * }
     * value 不传的话走默认,传走路由规则
	 * @param tableName
	 * 表名称
	 * @param conditions
	 * 条件
	 * @param dataResId
	 * 数据源ID
	 * @throws BaseException 
	 */
	@RequestMapping(value="deleteDataModel/{dataResId}",method=RequestMethod.POST)
	@ResponseBody
	public Status deleteDataModel(@PathVariable("dataResId") String dataResId,@RequestBody Map<String,Object> conditions) throws BaseException{
		Status status = null;
		try{	
			String tableName = conditions.get("tableName").toString();
			String condition = (String) conditions.get("conditions");
			String value = (String) conditions.get("value");
			status = sqlQueryService.deleteDataModel(tableName,condition,dataResId,value);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			throw new BaseException(ex.getMessage());
		}
		return status;
	}
	
	/**
	 * 单表更新数据
	 * http://ip:port/bconsole/v1/sqlQuery/updateDataModel
	 * {
	 *  "setString":"更新的字段值（注意是否带引号）",
     *  "conditions":{"条件字段":"条件（注意是否带引号）"},
     *  "tableName":"表名",
     *  "dataResId":"数据源ID",
     *  "value":""
     * }
     * value 不传的话走默认,传走路由规则
     * @param setList
	 * 更新的字段值
	 * @param tableName
	 * 表名称
	 * @param conditions
	 * 条件
	 * @param dataResId
	 * 数据源ID
	 * @throws BaseException 
	 */
	@RequestMapping(value="updateDataModel/{dataResId}",method=RequestMethod.POST)
	@ResponseBody
	public Status updateDataModel(@PathVariable("dataResId") String dataResId,@RequestBody Map<String,Object> conditions) throws BaseException{
		Status status = null;
		try{	
			String setString = (String) conditions.get("setString");
			String tableName = conditions.get("tableName").toString();
			String condition = (String) conditions.get("conditions");
			String value = (String) conditions.get("value");
			status = sqlQueryService.updateDataModel(setString,tableName,condition,dataResId,value);
		}catch (Exception ex) {
			LOG.error(ex.getMessage());
			throw new BaseException(ex.getMessage());
		}
		return status;
	}
	
	/**
	 * 数据接口表报存数据接口参数
	 * @param proDataApi
	 * @return
	 */
	@RequestMapping(value="saveProDataApi",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ProDataApi> saveProDataApi(ProDataApi proDataApi){
		try{
			sqlQueryService.saveProDataApi(proDataApi);
		}catch(Exception e){
			LOG.info(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(proDataApi, HttpStatus.OK);
		
	}
	/**
	 * 数据接口表单条查询
	 * @param dataApiId
	 * @return
	 */
	@RequestMapping(value="findProDataApi",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ProDataApi> findProDataApi(String dataApiId){
		ProDataApi proDataApi = null;
		try{
			proDataApi = sqlQueryService.findProDataApi(dataApiId);
		}catch(Exception e){
			LOG.info(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(proDataApi, HttpStatus.OK);
	}
	/**
	 * 数据接口表模块查询
	 * @param dataApiId
	 * @return
	 */
	@RequestMapping(value="getProDataApi",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getProDataApi(String modelId){
		List<ProDataApi> proDataApi = new ArrayList<ProDataApi>();
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			proDataApi = proDataApiService.getProDataApi(modelId);
            map.put("code", 0);
            map.put("msg", "success");
            map.put("count", proDataApi.size());
            map.put("data", proDataApi);
		}catch(Exception e){
			LOG.info(e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 数据接口表删除数据接口
	 * @param dataApiId
	 * @return
	 */
	@RequestMapping(value="deleteProDataApi",method=RequestMethod.POST)
	@ResponseBody
	public Status deleteProDataApi(String dataApiId) {
		Status status = sqlQueryService.deleteProDataApi(dataApiId);
		return status;
	}
	
	/**
	 * 项目模块数据接口参数
	 * @param proDataApi
	 * @return
	 */
	@RequestMapping(value="saveProModel",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ProModel> saveProModel(ProModel proModel){
		try{
			sqlQueryService.saveProModel(proModel);
		}catch(Exception e){
			LOG.info(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(proModel, HttpStatus.OK);
		
	}
	/**
	 * 项目模块列表接口
	 * @param proDataApi
	 * @return
	 */
	@RequestMapping(value="getProModel",method=RequestMethod.POST)
	@ResponseBody
	public List<ProModel> getProModel(){
		List<ProModel> ProModel = new ArrayList<ProModel>();
		try{
			ProModel = proModelService.getProModel();
		}catch(Exception e){
			LOG.info(e.getMessage());
			e.printStackTrace();
		}
		return ProModel;
		
	}
	/**
	 * 项目模块表单条查询
	 * @param dataApiId
	 * @return
	 */
	@RequestMapping(value="findProModel",method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ProModel> findProModel(String modelId){
		ProModel proModel = null;
		try{
			proModel = sqlQueryService.findProModel(modelId);
		}catch(Exception e){
			LOG.info(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<>(proModel, HttpStatus.OK);
	}
	/**
	 * 项目模块表删除数据接口
	 * @param dataApiId
	 * @return
	 */
	@RequestMapping(value="deleteProModel",method=RequestMethod.POST)
	@ResponseBody
	public Status deleteProModel(String modelId) {
		Status status = sqlQueryService.deleteProModel(modelId);
		return status;
	}
	/**
	 * 查询数据库接口
	 * @param map
	 * @return
	 */
	@RequestMapping(value="findDbPost/{dataApiId}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> findDbPost(@PathVariable("dataApiId") String dataApiId,@RequestBody Map<String,Object> map){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			map.put("dataApiId", dataApiId);
			result = sqlQueryService.findDbFreemark(map);
		}catch(Exception e){
			LOG.info(e.getMessage());
			e.printStackTrace();
			result.put("code", "999999");
			result.put("msg", "系统异常");
		}
		return result;
	}
	
	@RequestMapping(value="findDbGet/{dataApiId}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findDbGet(@PathVariable("dataApiId") String dataApiId, String jsonMessage){
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			JSONObject  myJson = JSONObject.fromObject(jsonMessage);
			Map<String, Object> map = myJson; 
			map.put("dataApiId", dataApiId);
			result = sqlQueryService.findDbFreemark(map);
		}catch(Exception e){
			LOG.info(e.getMessage());
			e.printStackTrace();
			result.put("code", "999999");
			result.put("msg", "系统异常");
		}
		return result;
	}	
	
	@RequestMapping(value="findDb",method=RequestMethod.GET)
	public String findDb(Model model){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("aaa", "诉讼费");
		result.put("productnum", "1电饭锅");
		Map<String, Object> result1 = new HashMap<String, Object>();
		result1.put("aaa", "诉讼费1");
		result1.put("productnum", "11的");
		Map<String, Object> result2 = new HashMap<String, Object>();
		result2.put("aaa", "诉讼费2");
		result2.put("productnum", "12感受到");
		Map<String, Object> result3 = new HashMap<String, Object>();
		result3.put("aaa", "诉讼费3");
		result3.put("productnum", "13");
		Map<String, Object> result4 = new HashMap<String, Object>();
		result4.put("aaa", "诉讼费4");
		Map<String, Object> result5 = new HashMap<String, Object>();
		result5.put("productnum", "15发GV撒");
//		result4.put("productnum", "14");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		resultList.add(result3);
		resultList.add(result2);
		resultList.add(result1);
		resultList.add(result4);
		resultList.add(result5);
		resultList.add(result);
		model.addAttribute("item", resultList);
		model.addAttribute("item1", "11");
		return "dataModel/NewFile";
	}
}
