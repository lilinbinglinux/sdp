package com.sdp.pageModel.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.service.BaseUserInfoMapService;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.pageModel.entity.PageResJoin;

/**
 * 

* <p>Description:PageResJoinService </p>  

* <p>Company: bonc.com</p>  

* @author zy 

* @date 2018年6月5日
 */
@Service
public class PageResJoinService {
	
	 @Autowired
	 private DaoHelper daoHelper;
	 
	 @Autowired
	 private PageModuleTypeService pageModuleTypeService;
	    
	 private static String BaseMapperUrl = "com.bonc.xbconsole.pageModel.PageResJoinMapper.";
	    
    /**
     * 输出日志
     */
     private static final Logger LOG = LoggerFactory.getLogger(PageModuleService.class);

	/**
	 * @param moduleId
	 */
	public void deleteByPageModuleId(String moduleId) {
		daoHelper.delete(BaseMapperUrl+"deleteByPageModuleId", moduleId);
	}
	
	public List<PageResJoin> findByCondition(Map<String,Object> paramsMap){
		return daoHelper.queryForList(BaseMapperUrl+"findByCondition", paramsMap);
	}

	/**
	 * @param moduleId
	 * @return
	 */
	public List<PageResJoin> findByPageModuleId(String moduleId) {
		Map<String,Object> paramsMap = BaseUserInfoMapService.baseUserInfoMap();
		paramsMap.put("pageModuleId", moduleId);
		return findByCondition(paramsMap);
	}
    
	
	 /**
     * 保存组件和资源关联表数据
     * @param pageResIds 资源主键Id
     * @param moduleId 组件Id
     */
    public void savePageResJoinInfo(String pageResIds, String moduleId){
        String[] pageRess =  pageResIds.split(",");
        for(String pageResId : pageRess){
            PageResJoin pageResJoin = new PageResJoin();
            pageResJoin.setPageModuleId(moduleId);
            pageResJoin.setResId(pageResId.substring(pageResId.indexOf(":") + 1));
            pageResJoin.setSortId(Integer.valueOf(pageResId.substring(0,pageResId.indexOf(":"))));
            daoHelper.insert(BaseMapperUrl+"insert", pageResJoin);
        }
    }

}
