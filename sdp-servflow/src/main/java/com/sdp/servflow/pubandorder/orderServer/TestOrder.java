package com.sdp.servflow.pubandorder.orderServer;

import com.sdp.servflow.pubandorder.orderServer.model.PublishBo;
import com.sdp.servflow.pubandorder.orderServer.publish.Order;
import com.sdp.servflow.pubandorder.orderServer.publish.Releaser;
import com.sdp.servflow.pubandorder.orderapistore.model.OrderInterfaceBean;

public class TestOrder {
    public static void main(String[] args) {
        PublishBo publisher = new PublishBo();
        // 1.创建发布者
        Releaser releaser = new Releaser() ;  
        
        // 2.建立观察者和发布者之间的关系（循环）
        // TODO 从数据库中获取不同的bean信息 循环调用
        OrderInterfaceBean bean = null;
        Order observer = new Order(bean);  
        observer.registerSubject(releaser);  
        
        // 3.发布者发布信息()
        releaser.setPublisher(publisher);
    
    }
}
