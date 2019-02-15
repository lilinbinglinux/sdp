package com.sdp.bcm.service.impl;

import com.sdp.bcm.constant.ProjectDictionary;
import com.sdp.bcm.entity.ProProject;
import com.sdp.bcm.entity.ProUser;
import com.sdp.bcm.exception.ErrorProjectMessageException;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.entity.BasePageForm;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.security.util.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lumeiling
 * @package com.sdp.bcm.service.impl
 * @create 2018-11-2018/11/23 下午1:51
 **/

@Service
public class ProProjectServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(ProProjectServiceImpl.class);

    @Autowired
    private DaoHelper daoHelper;
    /**
     * 指向 mybatis 命名空间
     */
    private static String BaseMapperUrl = "com.sdp.bcm.ProProjectMapper.";

    @Autowired
    private HttpServletRequest request;


    /**
     * 保存项目ID到session
     * @param projectId
     */
    public void saveProjectId(String projectId) {
        try {
            HttpSession session = request.getSession();
            session.setAttribute(Constant.ResourceType.ProjectIdResource.toString(), projectId);
        }catch (Exception e){
            LOG.error("保存项目ID失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_ID_SAVED_ERROR, "保存项目ID失败");
        }

    }


    /**
     * 获取项目用户
     * @param paramMap
     * @return
     */
    public List<ProUser> findAllProjectUser(Map<String, Object> paramMap) {
        try {
            return daoHelper.queryForList(BaseMapperUrl + "selectProUser", paramMap);
        }catch (Exception e) {
            LOG.error("获取项目人员列表失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_USER_CHECK_SQL_ERROR, "获取项目人员列表失败");
        }
    }


    /**
     * 查询一条项目记录
     * @param projectId
     * @return
     */
    public ProProject findOneProject(String projectId) {
        try {
            return (ProProject) daoHelper.queryOne(BaseMapperUrl+"selectOneProject", projectId);
        }catch (Exception e){
            LOG.error("查询一条项目信息失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_CHECK_ONE_PROJECT_ERROR, "查询一条项目信息失败");
        }
    }

    /**
     * 按照项目编码查询项目
     * @param projectCode
     * @return
     */
    public ProProject checkProjectCode(String projectCode) {
        try {
            return (ProProject) daoHelper.queryOne(BaseMapperUrl+"checkProjectCode", projectCode);
        }catch (Exception e){
            LOG.error("按照项目编码查询项目失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_CHECK_PROJECTCODE_ERROR, "按照项目编码查询项目失败");
        }
    }


    /**
     * 分页查询项目
     * @param baseForm
     * @param paramMap
     * @return
     */
    public Map<String, Object> findAllProject(BasePageForm baseForm, Map<String, Object> paramMap) {
        try {
            return daoHelper.queryForPageList(BaseMapperUrl+"selectPage", paramMap,  baseForm.getStart(), baseForm.getLength());
        }catch (Exception e){
            LOG.error("查询用户参与项目失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_INQUEIRY_PROJECTS_ERROR, "查询用户参与项目失败");
        }
    }


    /**
     * 分页查询有效项目（开发中、上线）
     * @param baseForm
     * @param paramMap
     * @return
     */
    public Map<String, Object> findActiveProProject(BasePageForm baseForm, Map<String, Object> paramMap) {
        try {
            return daoHelper.queryForPageList(BaseMapperUrl+"selectActivePage", paramMap,  baseForm.getStart(), baseForm.getLength());
        }catch (Exception e){
            LOG.error("查询用户参与的活跃项目失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_INQUEIRY_ACTIVE_PROJECTS_ERROR, "查询用户参与的活跃项目失败");
        }
    }


    /**
     * 条件查询项目
     * @param baseForm
     * @param paramMap
     * @return
     */
    public Map<String, Object> selectProjectByCondition(BasePageForm baseForm, Map<String, Object> paramMap) {
        try {
            return daoHelper.queryForPageList(BaseMapperUrl+"selectProByCondition", paramMap, baseForm.getStart(), baseForm.getLength());
        }catch (Exception e){
            LOG.error("条件查询项目失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_INQUEIRY_PROJECTS_CONDITION_ERROR, "条件查询项目失败");
        }
    }


    /**
     * 条件查询活跃项目（开发中、上线）
     * @param baseForm
     * @param paramMap
     * @return
     */
    public Map<String, Object> selectActiveProjectByCondition(BasePageForm baseForm, Map<String, Object> paramMap) {
        try {
            return daoHelper.queryForPageList(BaseMapperUrl+"selectActiveProByCondition", paramMap, baseForm.getStart(), baseForm.getLength());
        }catch (Exception e){
            LOG.error("条件查询活跃项目失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_INQUEIRY_ACTIVE_PROJECTS_CONDITION_ERROR, "条件查询活跃项目失败");
        }
    }


    /**
     * 新建项目人员
     * @param proUser
     */
    public void insertProUser(ProUser proUser) {
        try {
            proUser.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
            //close_flag, sort_id 暂定固定值
            proUser.setCloseFlag("0");
            proUser.setSortId(0);
            daoHelper.insert(BaseMapperUrl+"insertUser",proUser);
        } catch (Exception e) {
            LOG.error("添加项目人员失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_USER_CREATED_ERROR, "添加项目人员失败");
        }
    }


    /**
     * 移除一位项目人员
     * @param proUser
     */
    public void deleteProUser(ProUser proUser) {
        try {
            daoHelper.delete(BaseMapperUrl+"deleteUser", proUser);
        } catch (Exception e) {
            LOG.error("移除项目人员失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_USER_DELETE_SQL_ERROR, "移除项目人员失败");
        }
    }


    /**
     * 新建项目
     * @param project
     */
    public void insertProject(ProProject project) {
        try {
            project.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
            project.setCreateUser(CurrentUserUtils.getInstance().getUser().getLoginId());
            project.setCreateDate(new Date());
//            project.setProjectId(UUID.randomUUID().toString().replace("-", ""));
            project.setDelFlag("0");
            try {
                ProUser proUser = new ProUser();
                proUser.setProjectId(project.getProjectId());
                proUser.setUserId(project.getCreateUser());
                proUser.setTenantId(project.getTenantId());
                /*创建项目用户为项目管理员*/
                proUser.setProjectRoleId("0");
                //close_flag, sort_id 暂定固定值
                proUser.setCloseFlag("0");
                proUser.setSortId(0);
                daoHelper.insert(BaseMapperUrl + "insertUser", proUser);
            }catch (Exception e) {
                LOG.error("初始化项目管理员错误", e);
                throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_USER_CREATED_ERROR, "初始化项目管理员错误");
            }
            daoHelper.insert(BaseMapperUrl+"insert", project);
        } catch (Exception e) {
            LOG.error("新增项目失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_CREATED_ERROR, "新增项目失败");
        }
    }


    /**
     * 更新项目
     * @param project
     */
    public void updateProject(ProProject project) {
        try {
            daoHelper.update(BaseMapperUrl+"update", project);
        } catch (Exception e) {
            LOG.error("更新项目失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_UPDATE_SQL_ERROR, "更新项目失败");
        }
    }


    /**
     * 删除项目，标记del_flag
     * @param project
     */
    public void delFlagProject(ProProject project) {
        try {
            daoHelper.delete(BaseMapperUrl+"updateDelFlag", project);
        } catch (Exception e) {
            LOG.error("删除项目失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_DELETE_SQL_ERROR, "删除项目失败");
        }
    }


    /**
     * 删除项目(真删除)
     * @param project
     */
    public void deleteProject(ProProject project) {
        try {
            daoHelper.delete(BaseMapperUrl+"delete", project);
        } catch (Exception e) {
            LOG.error("删除项目失败", e);
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_DELETE_SQL_ERROR, "删除项目失败");
        }
    }

    /**
     * 获取登陆用户的某个项目的角色
     * @param proUser
     * @return
     */
    public ProUser getUserRole(ProUser proUser) {
        try{
            return (ProUser) daoHelper.queryOne(BaseMapperUrl+"getUserRole", proUser);
        }catch (Exception e){
            LOG.error("获取用户项目角色失败");
            throw new ErrorProjectMessageException(ProjectDictionary.PROJECT_USER_ROLE_ERROR, "获取用户项目角色失败");
        }
    }

}
