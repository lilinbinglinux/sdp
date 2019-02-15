package com.sdp.pageModel.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.BasePageForm;
import com.sdp.common.entity.Status;
import com.sdp.common.service.BaseUtilsService;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.pageModel.entity.PageModel;

/**
 * 
* @Description: PageModelService
  @ClassName: PageModelService
* @author zy
* @date 2018年4月16日
* @company:www.sdp.com.cn
 */
@Service
public class PageModelService{
	
	/**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(PageModelService.class);
    
    @Autowired
    private DaoHelper daoHelper;
    
    private static String BaseMapperUrl = "com.sdp.xbconsole.pageModel.PageModelMapper.";
	

	/**
	 * 
	* (分页)条件查询该节点的页面模版数据
	* @param @param pageTypeId
	* @param @return    参数
	* @return List<PageModel>    返回类型
	 */
	public Map<String, Object> selectPage(BasePageForm baseForm, Map<String, Object> paramMap) {
		return daoHelper.queryForPageList(BaseMapperUrl+"selectPage", paramMap,  baseForm.getStart(), baseForm.getLength());
	}
	
	/**
	 * 条件查询
	 * @param paramMap
	 * @return
	 */
	public List<PageModel> findByCondition(Map<String,Object> paramMap){
		return daoHelper.queryForList(BaseMapperUrl+"findByCondition", paramMap);
	}
	
	
	/**
	 * 
	* 根据pageId查询页面模版的详细信息
	* @param @param pageId
	* @param @return    参数
	* @return PageType    返回类型
	 */
	public PageModel findByPrimaryKey(String pageId) {
		return (PageModel) daoHelper.queryOne(BaseMapperUrl+"findByPrimaryKey", pageId);
	}
	
	/**
	 * 
	* 新增页面模版
	* @param @param pageModel
	* @param @return    参数
	* @return Status    返回类型
	 */
	public Status savePageModel(PageModel pageModel) {
		Status status ;
		try {
			BaseUtilsService.saveBaseInfo(pageModel, Dictionary.Directive.SAVE.value);
			daoHelper.insert(BaseMapperUrl+"insert", pageModel);
			status = new Status("新增页面模版数据成功",Dictionary.HttpStatus.CREATED.value );
		} catch (Exception e) {
			status = new Status("新增页面模版数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
			e.getStackTrace();
		}
        return status;
	}
	
	/**
	 * 
	* 根据pageId修改页面模版数据
	* @param @param pageId
	* @param @param mageModel
	* @param @return    参数
	* @return Status    返回类型
	 */
	public Status updatePageModelByPageId(PageModel pageModel) {
		Status status;
		try {
			BaseUtilsService.saveBaseInfo(pageModel, Dictionary.Directive.UPDATE.value);
			daoHelper.update(BaseMapperUrl+"update", pageModel);
			status = new Status("更新页面模版数据成功",Dictionary.HttpStatus.CREATED.value );
		} catch (Exception e) {
			status = new Status("更新页面模版数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
			e.getStackTrace();
		}
		return status;
	}
	
	/**
	 * 
	* 根据pageId删除页面模版数据
	* @param @param pageId
	* @param @return    参数
	* @return Status    返回类型
	 */
	public Status deletePageModelByPageId(String pageId) {
		Status status;
		try {
			daoHelper.delete(BaseMapperUrl+"delete", pageId);
		    status = new Status("删除页面模版数据成功",Dictionary.HttpStatus.CREATED.value );
		} catch (Exception e) {
			status = new Status("删除页面模版数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
            e.getStackTrace();
		}
		return status;
	}
	

}
