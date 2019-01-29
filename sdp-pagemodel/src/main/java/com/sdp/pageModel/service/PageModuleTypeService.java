package com.sdp.pageModel.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.Status;
import com.sdp.common.service.BaseUserInfoMapService;
import com.sdp.common.service.BaseUtilsService;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.pageModel.entity.PageModule;
import com.sdp.pageModel.entity.PageModuleType;

/**
 * @description:
 * @author: zy
 * @version: 2018/6/05
 * @see:
 * @since:
 * @modified by:
 */
@Service
public class PageModuleTypeService {
	
	@Autowired
	private DaoHelper daoHelper;
	
	@Autowired
	private PageModuleService pageModuleService;
	    
	private static String BaseMapperUrl = "com.bonc.xbconsole.pageModel.PageModuleTypeMapper.";
	    
   /**
    * 输出日志
    */
    private static final Logger LOG = LoggerFactory.getLogger(PageModuleTypeService.class);
    

	/**
     * 新增资源类型数据
     * @param pageModuleType pageModuleType
     * @return result
     */
    public Status savePageModuleTypeInfo(PageModuleType pageModuleType){
    	Status status ;
		try {
			BaseUtilsService.saveBaseInfo(pageModuleType, Dictionary.Directive.SAVE.value);
			daoHelper.insert(BaseMapperUrl+"insert", pageModuleType);
			status = new Status("新增页面模版数据成功",Dictionary.HttpStatus.CREATED.value );
		} catch (Exception e) {
			status = new Status("新增页面模版数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
			e.getStackTrace();
		}
        return status;
    }
    
    /**
     * 删除资源类型数据
     * @param moduleTypeId moduleTypeId
     * @return map
     */
    public Status deletePageModuleTypeInfo(String moduleTypeId){
        Status status;
        try {
        	Map<String,Object> paramMap = BaseUserInfoMapService.baseUserInfoMap();
        	paramMap.put("moduleParentId", moduleTypeId);
        	//查看是否有关联
        	List<PageModuleType> pageModuleTypeList = findByCondition(paramMap);
            List<PageModule> pageModuleList = pageModuleService.findByModuleTypeId(moduleTypeId);
            if (pageModuleTypeList.isEmpty() && pageModuleList.isEmpty()) {
            	daoHelper.delete(BaseMapperUrl+"delete", moduleTypeId);
                status = new Status("删除组件类型数据成功",Dictionary.HttpStatus.CREATED.value );
            } else {
                status = new Status("删除组件类型失败，该组件类型有子集菜单或有组件数据",Dictionary.HttpStatus.INVALID_REQUEST.value );
            }
        } catch (Exception ex) {
            status = new Status("删除组件类型失败，该资源类型有子集菜单",Dictionary.HttpStatus.INVALID_REQUEST.value );
            ex.getStackTrace();
        }
        return status;
    }
    
    /**
     * 更新资源类型数据
     * @param moduleTypeId moduleTypeId
     * @param pageModuleType pageModuleType
     * @return Status
     */
    public Status updatePageModuleTypeInfo(String moduleTypeId, PageModuleType pageModuleType){
        Status status;
        try {
            PageModuleType oldPageModuleType = findByPrimaryKey(moduleTypeId);
            oldPageModuleType.setModuleTypeName(pageModuleType.getModuleTypeName());
            oldPageModuleType.setSortId(pageModuleType.getSortId());
            oldPageModuleType.setPubFlag(pageModuleType.getPubFlag());
            BaseUtilsService.saveBaseInfo(oldPageModuleType, Dictionary.Directive.UPDATE.value);
            daoHelper.update(BaseMapperUrl+"update", oldPageModuleType);
            status = new Status("更新组件类型数据成功",Dictionary.HttpStatus.CREATED.value );
        } catch (Exception ex) {
            status = new Status("更新组件类型数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
            ex.getStackTrace();
        }
        return status;
    }
    
    
    
    public List<PageModuleType> findByCondition(Map<String,Object> paramsmap){
    	return daoHelper.queryForList(BaseMapperUrl+"findByCondition", paramsmap);
    }

	public PageModuleType findByPrimaryKey(String moduleTypeId) {
		return (PageModuleType)daoHelper.queryOne(BaseMapperUrl+"findByPrimaryKey", moduleTypeId);
	}

	public List<PageModuleType> findIsParentByCondition(Map<String, Object> paramMap) {
		return daoHelper.queryForList(BaseMapperUrl+"findIsParentByCondition", paramMap);
	}

}
