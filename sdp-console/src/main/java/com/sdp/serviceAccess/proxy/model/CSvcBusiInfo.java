package com.sdp.serviceAccess.proxy.model;

import java.util.List;
import java.util.Map;

import com.sdp.serviceAccess.entity.ProductRelyOnItem;

/**
 * @description:
 * 内部服务接口CSvcBusiInfo定义
 * @author: wangke
 * @version: 17:22 2018/1/3
 * @see:
 * @since:
 * @modified by: rpy  serviceId Long to  String
 */
public class CSvcBusiInfo {

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    private String loginId;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 实例编号
     */
    private String instanceNo;

    /**
     * 服务提供方ID ，目前暂时只对接DC/OS平台
     */
    private String serviceProvider;

    /**
     * 服务版本号
     */
    private String serviceVersion;

    /**
     * 服务名称，如mysql、redis、ftp、kafka等
     */
    private String serviceName;

    /**
     * 服务ID（服务唯一标识）
     */
    private String serviceId;

    /**
     * 服务的URL地址
     */
    private String url;

    /**
     * 服务提供方的服务是否异步调用
     */
    private Boolean isAsyn;
    
    /**
     * 是否为依赖服务
     * */
    private Boolean isRelyOnProduct ;
    
    /**
     * 依赖服务属性
     * */
    private List<ProductRelyOnItem> relyOnAttrOrm;

    private String orderServiceName;
    
    public String getOrderServiceName() {
		return orderServiceName;
	}

	public void setOrderServiceName(String orderServiceName) {
		this.orderServiceName = orderServiceName;
	}

	public Boolean getIsRelyOnProduct() {
		return isRelyOnProduct;
	}

	public void setIsRelyOnProduct(Boolean isRelyOnProduct) {
		this.isRelyOnProduct = isRelyOnProduct;
	}

	public List<ProductRelyOnItem> getRelyOnAttrOrm() {
		return relyOnAttrOrm;
	}

	public void setRelyOnAttrOrm(List<ProductRelyOnItem> relyOnAttrOrm) {
		this.relyOnAttrOrm = relyOnAttrOrm;
	}

	public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getAsyn() {
        return isAsyn;
    }

    public void setAsyn(Boolean asyn) {
        isAsyn = asyn;
    }

    public String getInstanceNo() {
        return instanceNo;
    }

    public void setInstanceNo(String instanceNo) {
        this.instanceNo = instanceNo;
    }

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
    
    
}
