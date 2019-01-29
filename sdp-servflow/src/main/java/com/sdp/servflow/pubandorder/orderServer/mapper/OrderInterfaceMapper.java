package com.sdp.servflow.pubandorder.orderServer.mapper;


import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.orderServer.model.PublishBo;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/***
 * 
 *
 * @author 任壮
 * @version 2017年12月4日16:31:04
 * @see OrderInterfaceMapper
 * @since
 */
@Component
public class OrderInterfaceMapper {
    /**
     * 封装的dao
     */
    @Resource
    private DaoHelper daoHelper;

    /***
    * 
    * Description:获取服务编排的顺序
    * 
    *@return HashMap<String,Object>
    *
    * @see
    */
    public List<OrderInterfaceBean> getOrderInterfaces(PublishBo publishBo) {
        return daoHelper.queryForList(
            "com.bonc.servflow.pubandorder.serve.mapper.ServerMapper.getOrderInterfaces", publishBo);
    }

    /**
     * 获取订阅审批状态
     * @param paramMap
     * @param start
     * @param length
     * @return
     */
    public Map selectInterfaceOrder(Map<String, Object> paramMap, String start, String length) {
        return daoHelper.queryForPageList(
                "com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.selectInterfaceOrder", paramMap,start,length);
    }

    /**
     * 更新订阅审批状态
     * @param orderInterfaceBean
     */
    public void updateByPrimaryKey(OrderInterfaceBean orderInterfaceBean) {
         daoHelper.update(
                "com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.updateByPrimaryKey", orderInterfaceBean);
    }

    public Map selectMine(Map<String, Object> paramMap, String start, String length) {
        return daoHelper.queryForPageList("com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.selectMine", paramMap,start,length);
    }

    public void deleteOrderInter(Map<String, String> paramMap) {
        daoHelper.delete("com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.deleteByPrimaryKey", paramMap);
    }

    public void deleteBySerIdAndVersion(Map<String, String> paramMap) {
        daoHelper.delete("com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.deleteBySerIdAndVersion", paramMap);
    }

    public OrderInterfaceBean selectByOrderId(String orderId) {
        return (OrderInterfaceBean) daoHelper.queryOne("com.bonc.servflow.pubandorder.serve.mapper.OrderInterfaceMapper.selectByOrderId",orderId);
    }
}
