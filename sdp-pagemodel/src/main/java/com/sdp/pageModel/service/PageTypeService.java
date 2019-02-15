/**  

* <p>Description: </p>  

* <p>Company: bonc.com</p>  

* @author zy 

* @date 2018年6月11日  

*/  
package com.sdp.pageModel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.common.entity.TreeNode;
import com.sdp.common.service.BaseUserInfoMapService;
import com.sdp.common.service.BaseUtilsService;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.pageModel.entity.PageModel;
import com.sdp.pageModel.entity.PageType;

/**  

* <p>Description: </p>  

* <p>Company: bonc.com</p>  

* @author zy 

* @date 2018年6月11日  

*/
@Service
public class PageTypeService {
	/**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(PageTypeService.class);
    
    @Autowired
	private DaoHelper daoHelper;

    @Autowired
    private PageModelService pageModelService;
    
    private static String BaseMapperUrl = "com.sdp.xbconsole.pageModel.PageTypeMapper.";
    
    public List<PageType> findByCondition(Map<String,Object> paramsmap){
    	return daoHelper.queryForList(BaseMapperUrl+"findByCondition", paramsmap);
    }

	public PageType findByPrimaryKey(String pageTypeId) {
		return (PageType)daoHelper.queryOne(BaseMapperUrl+"findByPrimaryKey", pageTypeId);
	}
	
	/**
	 * 
	* (树形数据)获取指定类型节点下的所有页面类型
	* @param @param pageTypeId
	* @param @return    参数
	* @return List<Object>    返回类型
	 */
	public Map<String, Object> findTreePageTypeByPageParentId(String pageParentId) {
		Map<String, Object> result = new HashMap<>();
		try {
			Map<String,Object> paramsMap = BaseUserInfoMapService.baseUserInfoMap();
			//根据父类型id查询该用户下所有页面类型
			paramsMap.put("pageParentId", pageParentId);
			List<PageType> pageTypes = findByCondition(paramsMap);
			List<TreeNode> treeNodeList = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(pageTypes)) {
				for(PageType pageType:pageTypes) {
					Map<String,Object> childParamsMap = BaseUserInfoMapService.baseUserInfoMap();
					childParamsMap.put("pageParentId", pageType.getPageTypeId());
					List<PageType> childPageTypes = findByCondition(childParamsMap);
					boolean flag = null != childPageTypes&&childPageTypes.size()>0 ? true:false;
					TreeNode treeNode = new TreeNode(pageType.getPageTypeId(),pageType.getPageTypeName(),
							pageType.getPageParentId(),flag);
					treeNodeList.add(treeNode);
				}
			}
			result.put("nodes", treeNodeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	* 获取指定类型节点下的所有页面类型
	* @param @param pageTypeId
	* @param @return    参数
	* @return List<Object>    返回类型
	 */
	public List<PageType> findPageTypeByPageParentId(String pageParentId) {
		Map<String,Object> paramsMap = BaseUserInfoMapService.baseUserInfoMap();
		//根据父类型id查询该用户下所有页面类型
		paramsMap.put("pageParentId", pageParentId);
		List<PageType> pageTypes = findByCondition(paramsMap);
		return pageTypes;
	}
	
	
	/**
	 * 
	* 根据path模糊查询来获取某类型下所有子节点（包括多级菜单中节点）
	* @param @param pageTypeId
	* @param @return    参数
	* @return List<PageType>    返回类型
	 */
	public List<PageType> getChildDataByPageParentId(String pageTypeId) {
		Map<String,Object> paramsMap = BaseUserInfoMapService.baseUserInfoMap();
		//根据父类型id查询该用户下所有页面类型
		paramsMap.put("pageParentId", pageTypeId);
		return findByCondition(paramsMap);
	}

	/**
	 * 
	* 新增页面模版类型
	* @param @param pageType
	* @param @return    参数
	* @return Status    返回类型
	 */
	public Status savePageType(PageType pageType) {
		Status status ;
		try {
			BaseUtilsService.saveBaseInfo(pageType, Dictionary.Directive.SAVE.value);
			daoHelper.insert(BaseMapperUrl+"insert", pageType);
			status = new Status("新增资源类型数据成功",Dictionary.HttpStatus.CREATED.value );
		} catch (Exception e) {
			status = new Status("新增资源类型数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
			e.printStackTrace();
		}
        return status;
	}

	/**
	 * 
	* 根据页面类型id删除
	* @param @param pageTypeId
	* @param @return    参数
	* @return Status    返回类型
	 */
	public Status deletePageTypeByPageTypeId(String pageTypeId) {
		Status status;
		try {
			
			Map<String,Object> paramsMap = BaseUserInfoMapService.baseUserInfoMap();
			//根据父类型id查询该用户下所有页面类型
			paramsMap.put("pageParentId", pageTypeId);
			List<PageType> pageTypes = findByCondition(paramsMap);
			
			Map<String,Object> modelParamsMap = BaseUserInfoMapService.baseUserInfoMap();
			//根据父类型id查询该用户下所有页面类型
			modelParamsMap.put("pageTypeId", pageTypeId);
			List<PageModel> pageModels = pageModelService.findByCondition(modelParamsMap);
			if((null != pageTypes && pageTypes.size()>0) || (null != pageModels&&pageModels.size()>0)) {
				status = new Status("删除页面类型失败，该资源类型有子集菜单",Dictionary.HttpStatus.INVALID_REQUEST.value );
			}else {
				daoHelper.delete(BaseMapperUrl+"delete", pageTypeId);
			    status = new Status("删除页面类型数据成功",Dictionary.HttpStatus.CREATED.value );
			}
		} catch (Exception e) {
			status = new Status("删除页面类型数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * 
	* 根据pageTypeId修改页面模版类型
	* @param @param pageTypeId
	* @param @param pageType
	* @param @return    参数
	* @return Status    返回类型
	 */
	public Status updatePageTypeByPageTypeId(String pageTypeId, PageType pageType) {
		Status status;
		try {
			PageType initpageType = findByPrimaryKey(pageTypeId);
			if(null != initpageType) {
				initpageType.setPageTypeName(pageType.getPageTypeName());
				initpageType.setPageSortId(pageType.getPageSortId());
				BaseUtilsService.saveBaseInfo(initpageType, Dictionary.Directive.UPDATE.value);
				daoHelper.update(BaseMapperUrl+"update", initpageType);
				status = new Status("更新页面类型数据成功",Dictionary.HttpStatus.CREATED.value );
			}else {
				status = new Status("更新页面类型数据失败，未查到该数据",Dictionary.HttpStatus.INVALID_REQUEST.value );
			}
		} catch (Exception e) {
			status = new Status("更新页面类型数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
			e.printStackTrace();
		}
		
		return status;
	}

}
