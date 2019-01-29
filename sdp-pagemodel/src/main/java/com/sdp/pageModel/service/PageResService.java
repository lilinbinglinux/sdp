package com.sdp.pageModel.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.BasePageForm;
import com.sdp.common.entity.Status;
import com.sdp.common.entity.TreeNode;
import com.sdp.common.service.BaseUserInfoMapService;
import com.sdp.common.service.BaseUtilsService;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.pageModel.entity.PageRes;
import com.sdp.pageModel.entity.PageResJoin;
import com.sdp.pageModel.entity.PageResType;

/**
 * @description:
 *  组件资源服务类
 * @author: zhoutao
 * @version: 15:37 2018/4/16
 * @see:
 * @since:
 * @modified by:
 */
@Service
public class PageResService {
	
	@Autowired
	private DaoHelper daoHelper;
	
	@Autowired
	private PageResTypeService pageResTypeService;

	@Autowired
	private PageResJoinService pageResJoinService;
    /**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(PageResService.class);
    
    private static String BaseMapperUrl = "com.bonc.xbconsole.pageModel.PageResMapper.";

    public List<PageRes> findByCondition(Map<String,Object> paramsmap){
    	return daoHelper.queryForList(BaseMapperUrl+"findByCondition", paramsmap);
    }

	public PageRes findByPrimaryKey(String resId) {
		return (PageRes)daoHelper.queryOne(BaseMapperUrl+"findByPrimaryKey", resId);
	}
	
	/**
	 * @param resTypeId
	 * @return
	 */
	public List<PageRes> findByResTypeId(String resTypeId) {
		Map<String,Object> paramsMap = BaseUserInfoMapService.baseUserInfoMap();
		paramsMap.put("resTypeId", resTypeId);
		return findByCondition(paramsMap);
	}

    /**
     * 组件中添加资源，查询资源树结构
     * @param resTypeId resTypeId
     * @return map
     */
    public Map<String, Object> findPageResTreeInfo(String resTypeId){
        Map<String, Object> result = new HashMap<>();
        try {
            List<PageResType> pageResTypeList = pageResTypeService.findByResParentId(resTypeId);
            List<PageRes> pageResList = findByResTypeId(resTypeId);
            List<TreeNode> treeNodeList = new ArrayList<>();
            for (PageResType pageResType : pageResTypeList){
                TreeNode treeNode = new TreeNode();
                treeNode.setParent(true);
                treeNode.setId(pageResType.getResTypeId());
                treeNode.setName(pageResType.getResTypeName());
                treeNode.setpId(pageResType.getResParentId());
                treeNodeList.add(treeNode);
            }
            for(PageRes pageRes : pageResList){
                TreeNode treeNode = new TreeNode();
                treeNode.setParent(false);
                treeNode.setId(pageRes.getResId());
                treeNode.setName(pageRes.getResName());
                treeNode.setpId(pageRes.getResTypeId());
                treeNodeList.add(treeNode);
            }
            result.put("nodes", treeNodeList);
        } catch (Exception ex) {
        	ex.getStackTrace();
        }
        return result;
    }


    /**
     * 分页查询当前资源类型下所属的资源
     * @param baseForm baseForm
     * @param resTypeId resTypeId
     * @return PagingResult
     */
    public Map<String, Object> findPageResInfoing(BasePageForm baseForm, String resTypeId, String selectKey){
        if (StringUtils.isNotBlank(selectKey)) {
            selectKey = selectKey.trim();
        } else {
            selectKey = "";
        }
        Map<String,Object> paramsMap = BaseUserInfoMapService.baseUserInfoMap();
        paramsMap.put("resTypeId", resTypeId);
        paramsMap.put("resName", selectKey);
        return daoHelper.queryForPageList(BaseMapperUrl+"selectPage", paramsMap, baseForm.getStart(), baseForm.getLength());
    }

    /**
     * 保存资源数据
     * @param resTypeId 资源类型主键
     * @param pageRes 资源数据
     * @param uploadFile 文件
     * @return status
     */
    public Status savePageResInfo(String resTypeId, PageRes pageRes, MultipartFile uploadFile){
        Status status;
        try {
            if(uploadFile != null && !uploadFile.isEmpty()){
                pageRes.setResText(uploadFile.getBytes());
                pageRes.setFileSuffix(uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1));
            }
            pageRes.setResTypeId(resTypeId);
            BaseUtilsService.saveBaseInfo(pageRes, Dictionary.Directive.SAVE.value);
            daoHelper.insert(BaseMapperUrl+"insert", pageRes);
            status = new Status("添加资源数据成功",Dictionary.HttpStatus.CREATED.value );
        } catch (Exception ex) {
            status = new Status("添加资源数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
            ex.getStackTrace();
        }
        return status;
    }

    /**
     * 删除资源数据
     * @param resId 资源Id
     * @return Status
     */
    public Status deletePageResInfo(String resId){
        Status status;

        Map<String,Object> paramsMap = BaseUserInfoMapService.baseUserInfoMap();
        paramsMap.put("resId", resId);
        List<PageResJoin> resJoins = pageResJoinService.findByCondition(paramsMap);

        if(resJoins.isEmpty()) {
            PageRes pageRes = findByPrimaryKey(resId);
            if (pageRes != null) {
                daoHelper.delete(BaseMapperUrl+"delete", resId);
                status = new Status("删除资源数据成功",Dictionary.HttpStatus.CREATED.value );
            } else {
                status = new Status("删除资源数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
            }
        } else {
            status = new Status("该资源已被组件引用，无法删除",Dictionary.HttpStatus.INVALID_REQUEST.value );
        }

        return status;
    }

    /**
     * 更新资源数据
     * @param resId resId
     * @param pageRes pageRes
     * @param uploadFile uploadFile
     * @return status
     */
    public Status updatePageResInfo(String resId, PageRes pageRes, MultipartFile uploadFile){
        Status status;
        try {
            PageRes oldPageRes = findByPrimaryKey(resId);
            if(uploadFile != null && !uploadFile.isEmpty()){
                oldPageRes.setResText(uploadFile.getBytes());
                oldPageRes.setFileSuffix(uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".") + 1));
            }
            oldPageRes.setResName(pageRes.getResName());
            oldPageRes.setResType(pageRes.getResType());
            oldPageRes.setSortId(pageRes.getSortId());
            BaseUtilsService.saveBaseInfo(oldPageRes, Dictionary.Directive.UPDATE.value);
            daoHelper.update(BaseMapperUrl+"update", oldPageRes);
            status = new Status("更新资源数据成功",Dictionary.HttpStatus.CREATED.value );
        } catch (Exception ex) {
            status = new Status("更新资源数据失败",Dictionary.HttpStatus.INVALID_REQUEST.value );
            ex.getStackTrace();
        }
        return status;
    }


    public void getResFileInfo(HttpServletResponse response, String resId){
        try {
            PageRes pageRes = findByPrimaryKey(resId);
            if (pageRes.getResText() != null) {
//                response.setContentType("image/png");
                OutputStream output = response.getOutputStream();
                InputStream in = new ByteArrayInputStream(pageRes.getResText());
                int len;
                byte[] buf = new byte[1024];
                while ((len = in.read(buf)) != -1) {
                    output.write(buf, 0, len);
                }
                output.flush();
                in.close();
                output.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 根据modeuleId查询所有资源
     * @param moduleId
     * @return
     */
	public List<PageRes> findResByPageModuleId(@Param("moduleId") String moduleId) {
		return daoHelper.queryForList(BaseMapperUrl+"findResByPageModuleId", moduleId);
	}


}
