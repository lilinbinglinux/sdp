package com.sdp.servflow.pubandorder.orderServer;

import com.sdp.servflow.pubandorder.orderServer.model.PublishBo;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;
/**
 * 
 * 
 *
 * @author 任壮
 * @version 2017年11月28日
 * @see Publish
 * @since
 */
public interface Publish {

    /**
     * 
     * Description: 发布信息调用的接口
     * 
     *@param publisher void
     *
     * @see
     */
    void release(PublishBo publisher);
    /**
     * 
     * Description: 发布信息订阅的接口
     * 
     *@param publisher void
     *
     * @see
     */
    void toOrder(PublishBo publisher);
    /**
     * 
     * Description: 开启线程添加消费者到去消费topic数据
     * 
     *@param publisher void
     *
     * @see
     */
    void addConsumer();
}
