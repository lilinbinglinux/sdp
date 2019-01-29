package com.sdp.code.constant;


/**
 * @author lumeiling
 * @package com.bonc.code.constant
 * @create 2018-12-2018/12/17 下午2:27
 **/
public class CodeDictionary {
    /*
    * 接口正常返回值
    * */
    public static final int CODE_SUCCESS = 0;

    /*
    * 查询类错误
    * */
    public static final int CODE_CHECK_PARAM_IS_NULL = 60001;

    /*
    * 创建失败
    * */
    public static final int CODE_CREATED_CODESET_ERROR = 20101;//创建类型失败
    public static final int CODE_CREATED_CODEITEM_ERROR = 20102;//创建字典数据失败

    /*
    * 修改失败
    * */
    public static final int CODE_UPDATE_CODESET_ERROR = 20103;//修改类型失败
    public static final int CODE_UPDATE_CODEITEM_ERROR = 20104;//修改字典失败

    /*
    * 删除失败
    * */
    public static final int CODE_DELETE_CODESET_ERROR = 20105;//删除类型失败
    public static final int CODE_DELETE_CODEITEM_ERROR = 20106;//删除字典失败
    public static final int CODE_DELETE_CODEITEM_TYPEPATH_ERROR = 20107;//按照路径删除字典失败
    public static final int CODE_DELETE_CODEITEM_SETID_ERROR = 20108;//按照类型删除字典失败

    /*
    * 查询错误
    * */
    public static final int CODE_INQUERIY_ALL_CODESET = 60002;//查询所有类型失败
    public static final int CODE_INQUERIY_ALL_CODEITEM = 60003;//查询所有类型失败
    public static final int CODE_INQUERIY_CODEITEM_PARENTS_SET = 60004;//查询类型的所有字典数据失败
    public static final int CODE_INQUERIY_CODEITEM_PARENTS_ITEM = 60005;//查询字典的下一级数据失败
    public static final int CODE_INQUERIY_ONE_ITEM_ERROR = 60006;//查询一条字典数据失败
}
