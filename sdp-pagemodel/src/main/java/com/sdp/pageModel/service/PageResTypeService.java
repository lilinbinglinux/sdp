/**  

* <p>Description: </p>  

* <p>Company: bonc.com</p>  

* @author zy 

* @date 2018年6月6日  

*/  
package com.sdp.pageModel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sdp.pageModel.entity.PageRes;
import com.sdp.pageModel.entity.PageResType;

/**  

* <p>Description:组件资源类型服务类 </p>  

* <p>Company: bonc.com</p>  

* @author zy 

* @date 2018年6月6日  

*/
@Service
public class PageResTypeService {
	
	@Autowired
	private DaoHelper daoHelper;
	
	@Autowired
	private PageResService pageResService;

    /**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(PageResTypeService.class);
    
    private static String BaseMapperUrl = "com.bonc.xbconsole.pageModel.PageResTypeMapper.";
    
    public List<PageResType> findByCondition(Map<String,Object> paramsmap){
    	return daoHelper.queryForList(BaseMapperUrl+"findByCondition", paramsmap);
    }

	public PageResType findByPrimaryKey(String resTypeId) {
		return (PageResType)daoHelper.queryOne(BaseMapperUrl+"findByPrimaryKey", resTypeId);
	}
	
	public List<PageResType> findByResParentId(String resTypeId) {
		Map<String,Object> paramsMap = BaseUserInfoMapService.baseUserInfoMap();
    	paramsMap.put("resParentId", resTypeId);
    	return findByCondition(paramsMap);
	}
    
    /**
     * 新增资源类型数据
     * @param pageResType
     * @return result
     */
    public Status savePageResTypeInfo(PageResType pageResType){
        BaseUtilsService.saveBaseInfo(pageResType, Dictionary.Directive.SAVE.value);
        daoHelper.insert(BaseMapperUrl+"insert", pageResType);
        Status status = new Status("新增资源类型数据成功",Dictionary.HttpStatus.CREATED.value );
        return status;
    }

    /**
     * 删除资源类型数据
     * @param resTypeId resTypeId
     * @return map
     */
    public Status deletePageResTypeInfo(String resTypeId){
        Status status;
        try {
        	List<PageResType> pageResTypes = findByResParentId(resTypeId);
            List<PageRes> pageResList = pageResService.findByResTypeId(resTypeId);
            if (pageResTypes.isEmpty() && pageResList.isEmpty()) {
            	daoHelper.delete(BaseMapperUrl+"delete", resTypeId);
                status = new Status("删除资源类型数据成功",Dictionary.HttpStatus.CREATED.value );
            } else {
                status = new Status("删除资源类型失败，该资源类型有子集菜单或有资源数据",Dictionary.HttpStatus.INVALID_REQUEST.value );
            }
        } catch (Exception ex) {
            status = new Status("删除资源类型失败，该资源类型有子集菜单或有资源数据",Dictionary.HttpStatus.INVALID_REQUEST.value );
            ex.getMessage();
        }
        return status;
    }

    /**
     * 更新资源类型数据
     * @param resTypeId resTypeId
     * @param pageResType pageResType
     * @return Status
     */
    public Status updatePageResTypeInfo(String resTypeId, PageResType pageResType){
        Status status;
        try {
        	PageResType oldPageResType = findByPrimaryKey(resTypeId);
            oldPageResType.setResTypeName(pageResType.getResTypeName());
            oldPageResType.setSortId(pageResType.getSortId());
            oldPageResType.setPubFlag(pageResType.getPubFlag());
            BaseUtilsService.saveBaseInfo(oldPageResType, Dictionary.Directive.UPDATE.value);
            daoHelper.update(BaseMapperUrl+"update", oldPageResType);
            status = new Status("更新资源类型数据成功",Dictionary.HttpStatus.CREATED.value );
        } catch (Exception ex) {
            status = new Status("更新资源类型数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
            ex.getMessage();
        }
        return status;
    }

    /**
     * 查询资源类型数据
     * 查询当前资源类型所属的子集菜单，查询所属当前资源类型的资源
     * @param resTypeId resTypeId
     */
    public Map<String, Object> findPageResTypeDetailInfo(String resTypeId){
        Map<String, Object> result = new HashMap<>();
        try {
            List<PageResType> pageResTypeList = findByResParentId(resTypeId);
            List<TreeNode> treeNodeList = new ArrayList<>();
            for (PageResType pageResType : pageResTypeList){
                TreeNode treeNode = new TreeNode();
                List<PageResType> childrenPageResTypeList = findByResParentId(pageResType.getResTypeId());
                if(childrenPageResTypeList.size() != 0){
                    treeNode.setParent(true);
                } else {
                    treeNode.setParent(false);
                }
                treeNode.setId(pageResType.getResTypeId());
                treeNode.setName(pageResType.getResTypeName());
                treeNode.setpId(pageResType.getResParentId());
                treeNodeList.add(treeNode);
            }
            result.put("nodes", treeNodeList);
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return result;
    }

}
