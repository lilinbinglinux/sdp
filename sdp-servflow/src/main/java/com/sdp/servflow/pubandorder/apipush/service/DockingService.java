package com.sdp.servflow.pubandorder.apipush.service;

import net.sf.json.JSON;

import javax.servlet.http.HttpServletRequest;

import com.sdp.servflow.pubandorder.apipush.model.DockingBean;

import java.util.List;
import java.util.Map;

public interface DockingService {

    /**
     * 按条件查询推送表
     * @param map
     * @return
     */
    List<DockingBean> getAllByCondition(Map<String, String> map);

    /**
     * 新增推送数据
     * @param dockingBean
     */
    void insert(DockingBean dockingBean);

    /**
     * 更新推送数据
     * @param dockingBean
     */
    void update(DockingBean dockingBean);

    /**
     * 删除一条推送数据
     * @param orderId
     */
    void delete(String orderId);

    /**
     * 根据id删除推送数据
     * 提供给POC
     * @param id
     * @return
     */
    JSON deleteById(String id);

    /**
     * 根据map中的serName以及serVersion更新状态
     * @param paramMap
     * @return
     */
    JSON updateApiState(Map<String, String> paramMap);

    /**
     * 判断是否是推送服务
     * @param serId
     */
    void selectPushSer(String serId);

    /**
     * 推送数据
     * @param request
     * @param orderId
     * @return
     */
    String execute(HttpServletRequest request, String orderId, String serId, String serVerId);
    
}
