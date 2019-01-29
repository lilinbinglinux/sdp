package com.sdp.servflow.pubandorder.serapitype.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.serapitype.model.ServiceApiTypeBean;

/**
 * Description:接口类型维护
 *
 * @author niu
 * @date Created on 2017/10/24.
 */
public interface ServiceApiTypeService {

    /**
     * 按照条件查找
     * @param map
     * @return
     */
    List<ServiceApiTypeBean> getAllByCondition(Map<String, String> map);

    /**
     * 新增类型
     * @param serviceApiTypeBean
     * @return
     */
    int insert(ServiceApiTypeBean serviceApiTypeBean);

    /**
     * 更新类型
     * @param serviceApiTypeBean
     * @return
     */
    int update(ServiceApiTypeBean serviceApiTypeBean);

    /**
     * 删除类型
     * @param apiTypeId
     * @return
     */
    int delete(String apiTypeId);

    /**
     * 过滤信息，展示树节点
     * @return
     */
    List<ServiceApiTypeBean> selectFilterDate();

}
