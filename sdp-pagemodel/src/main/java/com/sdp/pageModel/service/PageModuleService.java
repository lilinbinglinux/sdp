package com.sdp.pageModel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.BasePageForm;
import com.sdp.common.entity.Status;
import com.sdp.common.entity.TreeNode;
import com.sdp.common.service.BaseUserInfoMapService;
import com.sdp.common.service.BaseUtilsService;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.pageModel.entity.PageModule;
import com.sdp.pageModel.entity.PageModuleType;
import com.sdp.pageModel.entity.PageRes;
import com.sdp.pageModel.entity.PageResJoin;

/**
 * @description:
 * @author: zhoutao
 * @version: 10:52 2018/4/18
 * @see:
 * @since:
 * @modified by:
 */
@Service
public class PageModuleService {

	@Autowired
	private DaoHelper daoHelper;

	@Autowired
	private PageResJoinService pageResJoinService;

	@Autowired
	private PageModuleTypeService pageModuleTypeService;

	@Autowired
	private PageResService pageResService;

	private static String BaseMapperUrl = "com.bonc.xbconsole.pageModel.PageModuleMapper.";

	/**
	 * 输出日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PageModuleService.class);

	/**
	 * 根据modeulTypeId查询
	 * @param moduleTypeId
	 * @return
	 */
	public List<PageModule> findByModuleTypeId(String moduleTypeId){
		Map<String,Object> paramsMap = BaseUserInfoMapService.baseUserInfoMap();
		paramsMap.put("moduleTypeId", moduleTypeId);
		return findByCondition(paramsMap);
	}

	/**
	 * 条件查询
	 * @param paramsMap
	 * @return
	 */
	public List<PageModule> findByCondition(Map<String,Object> paramsMap){
		return daoHelper.queryForList(BaseMapperUrl+"findByCondition", paramsMap);
	}

	/**
	 * 根据主键查询
	 * @return
	 */
	public PageModule findByPrimaryKey(String moduleId){
		return (PageModule)daoHelper.queryOne(BaseMapperUrl+"findByPrimaryKey", moduleId);
	}

	/**
	 * 分页查询当前资源类型下所属的资源
	 * @param baseForm baseForm
	 * @return PagingResult
	 */
	public Map<String,Object> selectPage(BasePageForm baseForm, Map<String, Object> paramMap) {
		return daoHelper.queryForPageList(BaseMapperUrl+"selectPage", paramMap, baseForm.getStart(), baseForm.getLength());
	}

	/**
	 * 查询组件类型数据
	 * 查询当前组件类型所属的子集菜单，查询所属当前组件类型的资源
	 * @param moduleTypeId moduleTypeId
	 */
	public Map<String, Object> findPageMuduleTypeDetailInfo(String moduleTypeId){
		Map<String, Object> result = new HashMap<>();
		try {
			Map<String,Object> paramMap = BaseUserInfoMapService.baseUserInfoMap();
			paramMap.put("moduleParentId", moduleTypeId);
			//查看是否有关联
			List<PageModuleType> pageModuleTypeList = pageModuleTypeService.findByCondition(paramMap);
			List<PageModule> pageModuleList = findByModuleTypeId(moduleTypeId);
			List<PageModule> moduleList = new ArrayList<PageModule>();
			for(PageModule pageModule : pageModuleList){
				String pageResIds = "";
				List<PageRes> pageRess = pageResService.findResByPageModuleId(pageModule.getModuleId());
				for(PageRes pageRes:pageRess) {
					pageResIds = pageResIds +  pageRes.getSortId() + ":" + pageRes.getResId()+",";
				}
				if(pageResIds.length() > 0 ) {
					pageResIds = pageResIds.substring(0,pageResIds.length()-1);
				}
				pageModule.setPageResIds(pageResIds);
				pageModule.setPageRes(pageRess);
				moduleList.add(pageModule);
			}
			List<TreeNode> treeNodeList = new ArrayList<>();
			for (PageModuleType pageModuleType : pageModuleTypeList){
				TreeNode treeNode = new TreeNode();
				paramMap.put("moduleParentId", pageModuleType.getModuleTypeId());
				List<PageModuleType> pageModuleTypeLists = pageModuleTypeService.findByCondition(paramMap);
				//                if(pageModuleType.getIsParentId().equals("1")){
				//                    treeNode.setParent(true);
				//                } else {
				//                    treeNode.setParent(false);
				//                }
				if(pageModuleTypeLists.size()>0) {
					treeNode.setParent(true);
				}else {
					treeNode.setParent(false);
				}
				treeNode.setId(pageModuleType.getModuleTypeId());
				treeNode.setName(pageModuleType.getModuleTypeName());
				treeNode.setpId(pageModuleType.getModuleParentId());
				treeNodeList.add(treeNode);
			}
			result.put("nodes", treeNodeList);
			result.put("pageModuleList",moduleList);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.getStackTrace();
		}
		return result;
	}

	/**
	 * 保存组件数据
	 * @param moduleTypeId 组件类型主键
	 * @param pageModule 组件数据
	 * @return status
	 */
	@Transactional(rollbackFor = Exception.class)
	public Status savePageModuleInfo(String moduleTypeId, PageModule pageModule){
		Status status;
		try {
			pageModule.setModuleTypeId(moduleTypeId);
			BaseUtilsService.saveBaseInfo(pageModule, Dictionary.Directive.SAVE.value);
			daoHelper.insert(BaseMapperUrl+"insert", pageModule);
			if(pageModule.getPageResIds() != null && !"".equals(pageModule.getPageResIds())){
				pageResJoinService.savePageResJoinInfo(pageModule.getPageResIds(), pageModule.getModuleId());
			}
			status = new Status("添加组件数据成功",Dictionary.HttpStatus.CREATED.value );
		} catch (Exception ex) {
			status = new Status("添加组件数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
			ex.getStackTrace();
		}
		return status;
	}

	/**
	 * 删除组件数据
	 * 删除资源关联表数据
	 * @param moduleId 组件Id
	 * @return Status
	 */
	@Transactional(rollbackFor = Exception.class)
	public Status deletePageModuleInfo(String moduleId){
		Status status;
		PageModule pageModuel = findByPrimaryKey(moduleId);
		if (pageModuel != null) {
			daoHelper.delete(BaseMapperUrl+"delete", moduleId);
			pageResJoinService.deleteByPageModuleId(moduleId);
			status = new Status("删除组件数据成功",Dictionary.HttpStatus.CREATED.value );
		} else {
			status = new Status("删除组件数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
		}
		return status;
	}

	/**
	 * 更新资源数据
	 * @param moduleId moduleId
	 * @param pageModule pageModule
	 * @return status
	 */
	@Transactional(rollbackFor = Exception.class)
	public Status updatePageModuleInfo(String moduleId, PageModule pageModule){
		Status status;
		try {
			PageModule oldPageMoudle = findByPrimaryKey(moduleId);
			oldPageMoudle.setModuleName(pageModule.getModuleName());
			oldPageMoudle.setModuleJs(pageModule.getModuleJs());
			oldPageMoudle.setModuleTip(pageModule.getModuleTip());
			oldPageMoudle.setModuleStyle(pageModule.getModuleStyle());
			oldPageMoudle.setModuleText(pageModule.getModuleText());
			oldPageMoudle.setSortId(pageModule.getSortId());
			oldPageMoudle.setPubFlag(pageModule.getPubFlag());
			BaseUtilsService.saveBaseInfo(oldPageMoudle, Dictionary.Directive.UPDATE.value);
			daoHelper.update(BaseMapperUrl+"update", oldPageMoudle);
			pageResJoinService.deleteByPageModuleId(moduleId);
			if(pageModule.getPageResIds() != null && !"".equals(pageModule.getPageResIds())){
				pageResJoinService.savePageResJoinInfo(pageModule.getPageResIds(), moduleId);
			}
			status = new Status("更新组件数据成功",Dictionary.HttpStatus.CREATED.value );
		} catch (Exception ex) {
			status = new Status("更新组件数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
			ex.getStackTrace();
		}
		return status;
	}

	/**
	 * 查询资源数据信息
	 * @param moduleId moduleId
	 * @return PageModule
	 */
	public PageModule findPageModuleInfo(String moduleId){
		PageModule pageModuel = findByPrimaryKey(moduleId);
		List<PageResJoin> pageResJoinList = pageResJoinService.findByPageModuleId(moduleId);
		String pageResIds = "";
		for(PageResJoin pageResJoin : pageResJoinList){
			pageResIds = pageResIds +  pageResJoin.getSortId() + ":" + pageResJoin.getResId()+",";
		}
		if(pageResIds.length() > 0 ) {
			pageResIds = pageResIds.substring(0,pageResIds.length()-1);
		}
		pageModuel.setPageResIds(pageResIds);
		return pageModuel;
	}


}
