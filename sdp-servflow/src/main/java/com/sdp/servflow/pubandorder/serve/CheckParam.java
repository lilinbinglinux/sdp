package com.sdp.servflow.pubandorder.serve;

import java.util.List;

/***
 * 
 * 参数校验
 *
 * @author 任壮
 * @version 2017年9月23日
 * @see CheckParam
 * @since
 */
public interface CheckParam {

    
    /***
     * 
     * Description: 
     * 
     *@param value  需要校验的值
     *@param isMust  是够必传
     *@param paramType  参数类型
     *@param enumString  枚举值（暂时不用，传null）
     *@param maxstringLen  paramType为字符串时候的最大长度
     *@return String  返回值为空，代表没有错误， 如果有值，返回的是错误原因
     *
     * @see
     */
    String checkParam(List<Object> value,boolean isMust,String paramType,String enumString,String maxstringLen);
}
