package com.sdp.servflow.pubandorder.apipush.model;

/**
 * 
 * 数据推送——本地备份
 *
 * @author 牛浩轩
 * @version 2017年9月26日
 * @see DockingBean
 * @since
 */
public class DockingBean {

    /**
     * id
     */
    private String id;
    
    /**
     * 推送名称（注意name为不可重复项）
     */
    private String name;
    
    /**
     * 服务id（流程图id）
     */
    private String serId;

    /**
     * 订阅ID
     */
    private String orderId;
    
    /**
     * 上下文路径（不可重复项）
     */
    private String context;
    
    /**
     * 版本号
     */
    private String version;
    
    /**
     * 可见性
     */
    private String visibility;
    
    /**
     * 创建者
     */
    private String provider;
    
    /**
     * 请求参数
     */
    private String parameters;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
