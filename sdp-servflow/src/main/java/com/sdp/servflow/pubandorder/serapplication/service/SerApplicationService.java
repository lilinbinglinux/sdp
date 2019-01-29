package com.sdp.servflow.pubandorder.serapplication.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.serapplication.model.SerApplicationBean;

/**
 * Description: 应用的接口
 *
 * @author Niu Haoxuan
 * @date Created on 2017/11/10.
 */
public interface SerApplicationService {
    /**
     * 按条件查询应用
     * @param map
     * @return
     */
    List<SerApplicationBean> getAllByCondition(Map<String, String> map);

    /**
     * 分页查询应用
     * @param paramMap
     * @param start
     * @param length
     * @return
     */
    Map<String, Object> selectPage(Map<String, Object> paramMap, String start, String length);

    /**
     * 新增应用
     * @param serApplicationBean
     * @return
     */
    int insert(SerApplicationBean serApplicationBean);

    /**
     * 更新应用
     * @param serApplicationBean
     * @return
     */
    int update(SerApplicationBean serApplicationBean);

    /**
     * 删除应用
     * @param applicationId
     * @return
     */
    int delete(String applicationId);
}
