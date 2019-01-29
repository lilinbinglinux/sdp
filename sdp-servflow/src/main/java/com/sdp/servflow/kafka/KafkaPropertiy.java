package com.sdp.servflow.kafka;

/***
 * 
 * 关于kafka的配置信息（topic）(从数据库中读取的信息)
 *
 * @author 任壮
 * @version 2017年12月1日
 * @see KafkaPropertiy
 * @since
 */
public class KafkaPropertiy {

    /**
     * topic的信息
     * 
     */
    private String topicId;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 用于乐观锁的版本号
     */
    private String version;
    /**
     * 最后更新时间
     */
    private String lastUpdateTime;

    
    private KafkaPropertiy(){}
    private static class InnerClass{
        private  static final  KafkaPropertiy builder = new KafkaPropertiy();
    }
    /**
     * 根据类加载机制生成的单例模式（实现了延迟加载）
     */
    public static KafkaPropertiy getSingleStion(){
        return InnerClass.builder;
    }
    /***
     * 
     * Description: 初始化kafka信息
     * 
     *@param kafka void
     *
     * @see
     */
    public  void setSingleStion(KafkaPropertiy kafka){
        this.topicId =kafka.getTopicId();
        this.tenantId =kafka.getTenantId();
        this.lastUpdateTime =kafka.getLastUpdateTime();
    }
    public String getTopicId() {
        return topicId;
    }
    public String getTenantId() {
        return tenantId;
    }
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }
    public String getVersion() {
        return version;
    }
}
