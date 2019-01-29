package com.sdp.bcm.constant;


/**
 * @author lumeiling
 * @package com.bonc.bcm.constant
 * @create 2018-12-2018/12/18 下午3:39
 **/
public class ProjectDictionary {
    /*
     * 接口正常返回值
     * */
    public static final int PROJECT_SUCCESS = 0;

    /*
     * 参数错误
     * */
    public static final int PROJECT_CHECK_PARAM_IS_NULL = 60001;

    /*
     * 查询错误
     * */
    public static final int PROJECT_CHECK_ONE_PROJECT_ERROR = 70001;//项目查询失败
    public static final int PROJECT_USER_CHECK_SQL_ERROR = 70002;//项目成员查询失败
    public static final int PROJECT_INQUEIRY_PROJECTS_ERROR = 70003;//查询用户参与项目
    public static final int PROJECT_INQUEIRY_ACTIVE_PROJECTS_ERROR = 70004;//查询用户参与项目 开发中、上线
    public static final int PROJECT_INQUEIRY_PROJECTS_CONDITION_ERROR = 70005;//按条件查询项目
    public static final int PROJECT_INQUEIRY_ACTIVE_PROJECTS_CONDITION_ERROR = 70006;//按条件查询开发中、上线项目
    public static final int PROJECT_CHECK_PROJECTCODE_ERROR = 70007;//按项目编码查询项目失败
    public static final int PROJECT_USER_ROLE_ERROR = 70008;//获取用户项目角色失败

    /*
     * 删除错误
     * */
    public static final int PROJECT_USER_DELETE_SQL_ERROR = 70007;//删除项目成员失败
    public static final int PROJECT_DELETE_SQL_ERROR = 70008;//删除项目失败

    /*
     * 更新失败
     * */
    public static final int PROJECT_UPDATE_SQL_ERROR = 70009;//更新项目失败

    /*
     * 创建失败
     * */
    public static final int PROJECT_CREATED_ERROR = 20101;//创建项目失败
    public static final int PROJECT_USER_CREATED_ERROR = 20102;//田间项目成员失败

    /*
     * 保存项目ID失败
     * */
    public static final int PROJECT_ID_SAVED_ERROR = 30001;
}
