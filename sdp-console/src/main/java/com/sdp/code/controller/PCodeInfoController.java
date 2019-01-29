package com.sdp.code.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sdp.code.constant.CodeDictionary;
import com.sdp.code.entity.PCodeItem;
import com.sdp.code.entity.PCodeSet;
import com.sdp.code.exception.ErrorMessageException;
import com.sdp.code.service.IPCodeInfoService;

@Controller
@RequestMapping("/codeInfo")
public class PCodeInfoController {


	@Autowired
	private IPCodeInfoService codeInfoService;

	/**
	 * 码表管理页面
	 **/
	@RequestMapping(value = {"/codeTablePage"}, method = RequestMethod.GET)
	public String toCodeTablePage(Model model) {
		return "code/codeTable";
	}


	/**
	 * 获取码表类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/getCodeSet"}, method = RequestMethod.GET)
	public Map<String,Object> getSet() {
		Map<String,Object> result = new HashMap<>();
		try {
			result.put("data", codeInfoService.getCodeSet());
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "初始化类型树成功");
		}catch (ErrorMessageException e){
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
			result.put("data", "");
		}
		return result;
	}


	/**
	 * 获取码表值（类型的下一级）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/getCodeItem"}, method = RequestMethod.GET)
	public Map<String,Object> getItem() {
		Map<String,Object> result = new HashMap<>();
		try{
			result.put("data", codeInfoService.getCodeItem());
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "获取所有字典数据成功");
		}catch (ErrorMessageException e){
			result.put("data", "");
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
		}
		return result;
	}


	/**
	 * 新增码表类型
	 * @param codeSet
	 * @return
	 */
	@RequestMapping(value={"/insertCodeSet"},method= RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> createCodeSet(@RequestBody PCodeSet codeSet){
		Map<String,Object> result = new HashMap<>();
		result.put("data", "");
		try{
			codeInfoService.insertCodeSet(codeSet);
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "新建字典类型成功");
		}catch (ErrorMessageException e){
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
		}
		return result;
	}


	/**
	 * 新增码表值
	 * @param codeItem
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value={"/insertCodeItem"},method = RequestMethod.POST)
	public Map<String,Object> createCodeItem(@RequestBody PCodeItem codeItem) {
		Map<String,Object> result = new HashMap<>();
		result.put("data","");
		try{
			codeInfoService.insertCodeItem(codeItem);
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "新建字典数据成功");
		}catch (ErrorMessageException e){
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
		}
		return result;
	}


	/**
	 * 获取父节点的所有下一级节点（p_code_item)
	 * @param parent_id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/initSetTable"},method = RequestMethod.GET)
	public Map<String,Object> initSetTable(String parent_id) {
		Map<String,Object> result = new HashMap<>();
		try{
			result.put("data", codeInfoService.initSetTable(parent_id));
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "初始化字典类型的数据成功");
		}catch (ErrorMessageException e){
			result.put("data", "");
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
		}
		return result;
	}


	/**
	 * 获取当前父节点的所有节点（p_code_item）
	 * @param parent_id
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = {"/initDetailTable"},method = RequestMethod.GET)
    public Map<String,Object> initDetailTable(String parent_id) {
        Map<String,Object> result = new HashMap<>();
        try{
            result.put("data",codeInfoService.initDetailTable(parent_id));
            result.put("code",CodeDictionary.CODE_SUCCESS);
            result.put("message","展开子字典列表成功");
        }catch (ErrorMessageException e){
            result.put("data", "");
            result.put("code",e.getCode());
            result.put("message",e.getMessage());
        }
        return result;
    }


	/**
	 * 根据setId删除codeItem
	 * @param setId
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value = {"/deleteCodeItemBySetId"}, method = RequestMethod.DELETE)
	public Map<String,Object> deleteCodeItemBySetId(@RequestBody String setId){
    	Map<String,Object> result = new HashMap<>();
    	result.put("data", "");
    	try {
    		codeInfoService.deleteCodeItemBySetId(setId);
    		result.put("code", CodeDictionary.CODE_SUCCESS);
    		result.put("message", "根据字典类型删除字典数据成功");
		}catch (ErrorMessageException e){
    		result.put("code", e.getCode());
    		result.put("message", e.getMessage());
		}
		return result;
	}


	/**
	 * 删除一条code_item信息 by id
	 * @param codeItem
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/deleteCodeItemById"}, method = RequestMethod.DELETE)
	public Map<String,Object> deleteCodeItem(@RequestBody PCodeItem codeItem) {
		Map<String,Object> result = new HashMap<>();
		result.put("data", "");
		try {
			codeInfoService.deleteCodeItem(codeItem);
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "删除字典成功");
		}catch (ErrorMessageException e) {
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
		}
		return result;
	}


	/**
	 * 按照typePath删除字典
	 * @param codeItem
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/deleteCodeItemByTypePath"}, method = RequestMethod.DELETE)
	public Map<String,Object> deleteCodeItemByTypePath(@RequestBody PCodeItem codeItem) {
		Map<String,Object> result = new HashMap<>();
		result.put("data", "");
		try {
			codeInfoService.deleteCodeItemByTypePath(codeItem);
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "按照路径删除字典成功");
		}catch (ErrorMessageException e) {
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
		}
		return result;
	}


	/**
	 * 删除一条code_set 信息
	 * @param codeSet
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/deleteCodeSetById"},method = RequestMethod.DELETE)
	public Map<String,Object> deleteCodeSet(@RequestBody PCodeSet codeSet) {
		Map<String,Object> result = new HashMap<>();
		result.put("data", "");
		try {
			codeInfoService.deleteCodeSet(codeSet);
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "删除字典类型成功");
		}catch (ErrorMessageException e){
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
		}
		return result;
	}


	/**
	 * 查询一条 code_item
	 * @param itemId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/getSingleCodeItem"}, method = RequestMethod.GET)
	public Map<String,Object> getSingleItem(String itemId) {
		Map<String,Object> result = new HashMap<>();
		try {
			result.put("data", codeInfoService.singleCodeItem(itemId));
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "查询字典数据成功");
		}catch (ErrorMessageException e){
			result.put("data", "");
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
		}
		return result;
	}


	/**
	 * 更新一条code_item
	 * @param codeItem
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/updateCodeItem"}, method = RequestMethod.PUT)
	public Map<String,Object> updateCodeItem(@RequestBody PCodeItem codeItem) {
		Map<String,Object> result = new HashMap<>();
		result.put("data", "");
		try {
			codeInfoService.updateCodeItem(codeItem);
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "更新字典数据成功");
		}catch (ErrorMessageException e){
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
		}
		return result;
	}


	/**
	 * 更新一条code_set
	 * @param codeSet
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"/updateCodeSet"}, method = RequestMethod.PUT)
	public Map<String,Object> updateCodeSet(@RequestBody PCodeSet codeSet) {
		Map<String,Object> result = new HashMap<>();
		result.put("data", "");
		try {
			codeInfoService.updateCodeSet(codeSet);
			result.put("code", CodeDictionary.CODE_SUCCESS);
			result.put("message", "更新字典类型成功");
		}catch (ErrorMessageException e){
			result.put("code", e.getCode());
			result.put("message", e.getMessage());
		}
		return result;
	}
}

