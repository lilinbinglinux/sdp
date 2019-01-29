package com.sdp.servflow.pubandorder.whitelist.service;

import java.util.List;

import com.sdp.servflow.pubandorder.whitelist.model.WhiteListBean;

/**
 * Description:白名单Service
 *
 * @author Niu Haoxuan
 * @date Created on 2017/11/28.
 */
public interface WhiteListService {

    /**
     * 插入白名单
     * @return
     */
    int insert(WhiteListBean whiteListBean);

    /**
     * 将IP地址分割
     * @param whiteListBean
     * @param ipAddrs
     * @return
     */
    int splitIps(WhiteListBean whiteListBean, String ipAddrs);

    /**
     * 根据orderid删除ip
     * @param orderId
     * @return
     */
    int delete(String orderId);

    /**
     * 查询黑白名单
     * @param whiteListBean
     * @return
     */
    List<WhiteListBean> getAllByCondition(WhiteListBean whiteListBean);

}
