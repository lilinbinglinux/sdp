package com.sdp.servflow.pubandorder.orderServer.publish;

import java.util.Observable;

import com.sdp.servflow.pubandorder.orderServer.model.PublishBo;
/**
 * 
 * 发布订阅中的发布者
 *
 * @author 任壮
 * @version 2017年11月28日
 * @see Releaser
 * @since
 */
public class Releaser extends Observable{

    private PublishBo Publisher;
    public PublishBo getPublisher() {
        return Publisher;
    }

    public void setPublisher(PublishBo publisher) {
        Publisher = publisher;
      //  saveMsg(publisher);
        setChanged();
        notifyObservers(publisher);
    }
    /***
     * 
     * Description: 保存信息到kafka(暂时不需要这一步)
     * TODO 
     *@param publisher void
     *
     * @see
     */
    private void saveMsg(PublishBo publisher) {
        
    }
}
