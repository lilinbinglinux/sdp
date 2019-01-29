package com.sdp.code.service.impl;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;
import com.sdp.code.constant.CodeDictionary;
import com.sdp.code.controller.PCodeInfoController;
import com.sdp.code.entity.PCodeItem;
import com.sdp.code.entity.PCodeSet;
import com.sdp.code.exception.ErrorMessageException;
import com.sdp.code.mapper.PCodeInfoMapper;
import com.sdp.code.service.IPCodeInfoService;
import com.sdp.common.CurrentUserUtils;


@Service("pCodeInfoService")
public class PCodeInfoServiceImpl implements IPCodeInfoService {

	private static final Logger LOG = LoggerFactory.getLogger(PCodeInfoController.class);

	@Autowired
	private PCodeInfoMapper pcodeMapper;


	/**
	 * 查询所有字典类型
	 * @return
	 */
	@Override
	public List<PCodeSet> getCodeSet() {
		try {
			return pcodeMapper.getCodeSet();
		} catch (Exception e) {
			throw new ErrorMessageException(CodeDictionary.CODE_INQUERIY_ALL_CODESET, "初始化类型结构树失败");
		}
	}


	/**
	 * 查询所有字典数据
	 * @return
	 */
	@Override
	public List<PCodeItem> getCodeItem() {
		try {
			return pcodeMapper.getCodeItem();
		} catch (Exception e) {
			LOG.error("查询所有字典数据失败", e);
			throw new ErrorMessageException(CodeDictionary.CODE_INQUERIY_ALL_CODEITEM, "查询所有字典数据失败");
		}
	}


	/**
	 * 创建一条字典类型数据
	 * @param codeSet
	 */
	@Override
	public void insertCodeSet(PCodeSet codeSet) {
		try {
			codeSet.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			codeSet.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			codeSet.setCreateDate(new Date());
			codeSet.setDelFlag("0");
//			codeSet.setId(UUID.randomUUID().toString().replace("-", ""));
//			codeSet.setSortId(2);
//			codeSet.setParentId("-");
//			codeSet.setName("123");
//			codeSet.setTypePath("--");
//			codeSet.setTypeStatus("--");
//			codeSet.setSortId(1);
			pcodeMapper.insertCodeSet(codeSet);
		}catch (Exception e) {
			LOG.error("创建字典类型失败", e);
			throw new ErrorMessageException(CodeDictionary.CODE_CREATED_CODESET_ERROR, "新建字典类型失败");
		}
	}


	/**
	 * 创建一条字典数据
	 * @param codeItem
	 */
	@Override
	public void insertCodeItem(PCodeItem codeItem) {
		try {
			codeItem.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
			codeItem.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			codeItem.setCreateDate(new Date());
			codeItem.setDelFlag("0");
//			codeItem.setItemId(UUID.randomUUID().toString().replace("-", ""));
			pcodeMapper.insertCodeItem(codeItem);
		}catch (Exception e) {
			LOG.error("创建字典失败", e);
			throw new ErrorMessageException(CodeDictionary.CODE_CREATED_CODEITEM_ERROR, "新建字典数据失败");
		}
	}


	/**
	 * 查询一条字典数据
	 * @param id
	 * @return
	 */
	@Override
	public PCodeItem singleCodeItem(String id) {
		if(StringUtils.isNullOrEmpty(id)){
			throw new ErrorMessageException(CodeDictionary.CODE_CHECK_PARAM_IS_NULL, "查询字典时参数缺失");
		}
		try{
			return pcodeMapper.findItemById(id);
		}catch(Exception e){
			LOG.error("get single code_item error", e);
            throw new ErrorMessageException(CodeDictionary.CODE_INQUERIY_ONE_ITEM_ERROR, "查询一条字典数据失败");
		}
	}


	/**
	 * 查询字典类型下的字典数据
	 * @param parentId
	 * @return
	 */
	@Override
	public List<PCodeItem> initSetTable(String parentId) {
		if(StringUtils.isNullOrEmpty(parentId)){
			throw new ErrorMessageException(CodeDictionary.CODE_CHECK_PARAM_IS_NULL, "查询字典时参数缺失");
		}
		try{
			return pcodeMapper.getCodeItemByParentId(parentId);
		}catch (Exception e){
			LOG.error("get code_set by parentId error", e);
			throw new ErrorMessageException(CodeDictionary.CODE_INQUERIY_CODEITEM_PARENTS_SET, "初始化字典类型的数据失败");
		}
	}


	/**
	 * 查询字典数据的下一级字典
	 * @param parentId
	 * @return
	 */
	@Override
	public List<PCodeItem> initDetailTable(String parentId) {
		if(StringUtils.isNullOrEmpty(parentId)){
			throw new ErrorMessageException(CodeDictionary.CODE_CHECK_PARAM_IS_NULL, "查询字典时参数缺失");
		}
		try {
			return pcodeMapper.getCodeItemByParentId(parentId);
		}catch (Exception e){
			LOG.error("get code_item list by parentId error", e);
			throw new ErrorMessageException(CodeDictionary.CODE_INQUERIY_CODEITEM_PARENTS_ITEM, "初始化字典的下一级数据失败");
		}
	}


	/**
	 * 删除相同字典类型下的所有字典数据
	 * @param setId
	 */
	@Override
    public void deleteCodeItemBySetId(String setId) {
	    if(StringUtils.isNullOrEmpty(setId)) {
			throw new ErrorMessageException(CodeDictionary.CODE_CHECK_PARAM_IS_NULL, "删除字典时参数缺失");
		}
        try{
        	String delFlag = "1";
            pcodeMapper.deleteCodeItemBySetId(setId, delFlag);
        }catch (Exception e){
            LOG.error("delete codeItem by set_id error", e);
            throw new ErrorMessageException(CodeDictionary.CODE_DELETE_CODEITEM_SETID_ERROR, "根据类型删除字典失败");
        }
    }


	/**
	 * 删除字典数据
	 * @param codeItem
	 */
	@Override
//	@Transactional(rollbackFor = Exception.class)
	public void deleteCodeItem(PCodeItem codeItem) {
		if(StringUtils.isNullOrEmpty(codeItem.getItemId())){
			throw new ErrorMessageException(CodeDictionary.CODE_CHECK_PARAM_IS_NULL, "删除字典时参数缺失");
		}
		try{
			pcodeMapper.deleteCodeItem(codeItem);
//			throw new Exception();
		}catch (Exception e) {
			LOG.error("删除字典失败", e);
//			手动回滚无效
//			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new ErrorMessageException(CodeDictionary.CODE_DELETE_CODEITEM_ERROR, "删除字典失败");
		}
	}


	/**
	 * 删除相同访问路径下的所有子字典数据
	 * @param codeItem
	 */
	@Override
	public void deleteCodeItemByTypePath(PCodeItem codeItem) {
		try{
			pcodeMapper.deleteCodeItemByTypePath(codeItem);
		}catch (Exception e) {
			LOG.error("删除字典失败", e);
			throw new ErrorMessageException(CodeDictionary.CODE_DELETE_CODEITEM_TYPEPATH_ERROR, "按照路径删除字典失败");
		}
	}

	/**
	 * 删除字典类型
	 * @param codeSet
	 */
	@Override
	public void deleteCodeSet(PCodeSet codeSet) {
		if(StringUtils.isNullOrEmpty(codeSet.getSetId())) {
			throw new ErrorMessageException(CodeDictionary.CODE_CHECK_PARAM_IS_NULL, "删除字典类型时参数缺失");
		}
		try{
			pcodeMapper.deleteCodeSet(codeSet);
		}catch (Exception e){
			LOG.error("删除字典类型失败", e);
			throw new ErrorMessageException(CodeDictionary.CODE_DELETE_CODESET_ERROR, "删除字典类型失败");
		}
	}

	/**
	 * 更新字典数据
	 * @param codeItem
	 */
	@Override
	public void updateCodeItem(PCodeItem codeItem) {
		String itemId = codeItem.getItemId();
		if(StringUtils.isNullOrEmpty(itemId)){
			throw new ErrorMessageException(CodeDictionary.CODE_CHECK_PARAM_IS_NULL, "更新字典时参数缺失");
		}
		try{
			pcodeMapper.updateCodeItem(codeItem);
		}catch (Exception e) {
			LOG.error("更新字典失败", e);
			throw new ErrorMessageException(CodeDictionary.CODE_UPDATE_CODEITEM_ERROR, "更新字典失败");
		}
	}


	/**
	 * 更新字典类型
	 * @param codeSet
	 */
	@Override
	public void updateCodeSet(PCodeSet codeSet) {
		String setId = codeSet.getSetId();
		if(StringUtils.isNullOrEmpty(setId)){
			throw new ErrorMessageException(CodeDictionary.CODE_CHECK_PARAM_IS_NULL, "更新类型时参数缺失");
		}
		try{
			pcodeMapper.updateCodeSet(codeSet);
		}catch (Exception e){
			LOG.error("更新字典类型失败", e);
			throw new ErrorMessageException(CodeDictionary.CODE_UPDATE_CODESET_ERROR, "更新字典类型失败");
		}
	}
}