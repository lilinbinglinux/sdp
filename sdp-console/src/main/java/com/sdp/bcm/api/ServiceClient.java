package com.sdp.bcm.api;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.utils.RestFactory;
import com.sdp.common.entity.NetClientException;

public class ServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(ServiceClient.class);

    private ServiceApi api;

    public ServiceClient(String url, RestFactory factory) {
        api = factory.creatServiceApiClient(url);
    }

    public ApiResult createService(JSONObject json) throws NetClientException {
        return api.createService(json);
    }

    public ApiResult updateService(String serviceId, JSONObject json) throws NetClientException {
        return api.updateService(serviceId, json);
    }

    public ApiResult getServiceList(String serviceName, String tenantName, String projectId, Integer page, Integer size)
            throws NetClientException {
        return api.getServiceList(serviceName, tenantName, projectId, page, size);
    }

    public ApiResult deleteService(String serviceId) throws NetClientException {
        return api.deleteService(serviceId);
    }

    public ApiResult getServiceDetail(String serviceId) throws NetClientException {
        return api.getServiceDetail(serviceId);
    }

    public ApiResult getServiceDetail(String serviceName, String tenantName) throws NetClientException {
        return api.getServiceDetail(serviceName, tenantName);
    }

    public ApiResult serviceScale(String serviceId, JSONObject json) throws NetClientException {
        return api.serviceScale(serviceId, json);
    }

    public ApiResult serviceUpgrade(String serviceId, JSONObject json) throws NetClientException {
        return api.serviceUpgrade(serviceId, json);
    }

    public ApiResult hpa(String serviceId, JSONObject json) throws NetClientException {
        return api.hpa(serviceId, json);
    }

    public ApiResult updateServiceQuota(String serviceId, JSONObject json) throws NetClientException {
        return api.updateServiceQuota(serviceId, json);
    }

    public ApiResult getPodInfo(String serviceId, String tenantName) throws NetClientException {
        return api.getPodInfo(serviceId, tenantName);
    }

    public ApiResult getServiceStartLog(String serviceId, String tenantName) throws NetClientException {
        return api.getServiceStartLog(serviceId, tenantName);
    }

    public ApiResult getPodLog(String serviceId, String tenantName) throws NetClientException {
        return api.getPodLog(serviceId, tenantName);
    }

    public ApiResult getServiceStrategy(String serviceId) throws NetClientException {
        return api.getServiceStrategy(serviceId);
    }

    public ApiResult deletePod(String serviceId, String podname) throws NetClientException {
        return api.deletePod(serviceId, podname);
    }

    public ApiResult getServiceEnv(String serviceId) throws NetClientException {
        return api.getServiceEnv(serviceId);
    }

    public ApiResult updateServiceEnv(String serviceId, Map<String, Object> envData) throws NetClientException {
        return api.updateServiceEnv(serviceId, envData);
    }

    public ApiResult getServiceConfig(String serviceId) throws NetClientException {
        return api.getServiceConfig(serviceId);
    }

    public ApiResult updateServiceConfig(String serviceId, JSONObject json) throws NetClientException {
        return api.updateServiceConfig(serviceId, json);
    }

    public ApiResult deleteServiceConfig(String serviceId, String serviceConfigId) throws NetClientException {
        return api.deleteServiceConfig(serviceId, serviceConfigId);
    }

    public ApiResult getServiceCephfs(String serviceId) throws NetClientException {
        return api.getServiceCephfs(serviceId);
    }

    public ApiResult updateServiceCephfs(String serviceId, JSONObject json) throws NetClientException {
        return api.updateServiceCephfs(serviceId, json);
    }

    public ApiResult deleteServiceCephfs(String serviceId, String serviceCephFileId) throws NetClientException {
        return api.deleteServiceCephfs(serviceId, serviceCephFileId);
    }

    public ApiResult getServiceLocalStorage(String serviceId) throws NetClientException {
        return api.getServiceLocalStorage(serviceId);
    }

    public ApiResult updateServiceLocalStorage(String serviceId, JSONObject json) throws NetClientException {
        return api.updateServiceLocalStorage(serviceId, json);
    }

    public ApiResult deleteServiceLocalStorage(String serviceId, String serviceHostpathId) throws NetClientException {
        return api.deleteServiceLocalStorage(serviceId, serviceHostpathId);
    }

    public ApiResult getServiceCephRbd(String serviceId) throws NetClientException {
        return api.getServiceCephRbd(serviceId);
    }

    public ApiResult updateServiceCephRbd(String serviceId, JSONObject json) throws NetClientException {
        return api.updateServiceCephRbd(serviceId, json);
    }

    public ApiResult deleteServiceCephRbd(String serviceId, String serviceCephRbdId) throws NetClientException {
        return api.deleteServiceCephRbd(serviceId, serviceCephRbdId);
    }

    public ApiResult getServiceNodeAffinity(String serviceId) throws NetClientException {
        return api.getServiceNodeAffinity(serviceId);
    }

    public ApiResult updateServiceNodeAffinity(String serviceId, JSONObject json) throws NetClientException {
        return api.updateServiceNodeAffinity(serviceId, json);
    }

    public ApiResult deleteServiceNodeAffinity(String serviceId, String serviceAffinityId) throws NetClientException {
        return api.deleteServiceNodeAffinity(serviceId, serviceAffinityId);
    }

    public ApiResult getServiceAffinity(String serviceId) throws NetClientException {
        return api.getServiceAffinity(serviceId);
    }

    public ApiResult updateServiceAffinity(String serviceId, JSONObject json) throws NetClientException {
        return api.updateServiceAffinity(serviceId, json);
    }

    public ApiResult deleteServiceAffinity(String serviceId, String serviceAffinityId) throws NetClientException {
        return api.deleteServiceAffinity(serviceId, serviceAffinityId);
    }

    public ApiResult getServiceHealth(String serviceId) throws NetClientException {
        return api.getServiceHealth(serviceId);
    }

    public ApiResult updateServiceHealth(String serviceId, JSONObject json) throws NetClientException {
        return api.updateServiceHealth(serviceId, json);
    }

    public ApiResult deleteServiceHealth(String serviceId, String serviceHealthId) throws NetClientException {
        return api.deleteServiceHealth(serviceId, serviceHealthId);
    }

    public ApiResult createDomain(JSONObject json) throws NetClientException {
        return api.createDomain(json);
    }

    public ApiResult updateDomain(String domainId, JSONObject json) throws NetClientException {
        return api.updateDomain(domainId, json);
    }

    public ApiResult getDomainList(String tenantName, String page, String size) throws NetClientException {
        return api.getDomainList(tenantName, page, size);
    }

    public ApiResult deleteDomain(String domainId) throws NetClientException {
        return api.deleteDomain(domainId);
    }

    public ApiResult createProxy(JSONObject json) throws NetClientException {
        return api.createProxy(json);
    }

    public ApiResult updateProxy(String ingressProxyId, JSONObject json) throws NetClientException {
        return api.updateProxy(ingressProxyId, json);
    }

    public ApiResult getProxyList(String tenantName, String serviceId) throws NetClientException {
        return api.getProxyList(serviceId, tenantName);
    }

    public ApiResult deleteProxy(String tenantName, String serviceId) throws NetClientException {
        return api.deleteProxy(serviceId, tenantName);
    }

    public ApiResult createEnv(JSONObject json) throws NetClientException {
        return api.createEnv(json);
    }

    public ApiResult updateEnv(String envId, JSONObject json) throws NetClientException {
        return api.updateEnv(envId, json);
    }

    public ApiResult getEnvList(String tenantName, String projectId, String templateName) throws NetClientException {
        return api.getEnvList(tenantName, projectId, templateName);
    }

    public ApiResult deleteEnv(String envId) throws NetClientException {
        return api.deleteEnv(envId);
    }

    public ApiResult getEnvDetail(String envId) throws NetClientException {
        return api.getEnvDetail(envId);
    }

    public ApiResult getEnvDetail(String templateName, String tenantName) throws NetClientException {
        return api.getEnvDetail(templateName, tenantName);
    }

    public ApiResult createConfig(JSONObject json) throws NetClientException {
        return api.createConfig(json);
    }

    public ApiResult updateConfig(String configId, JSONObject json) throws NetClientException {
        return api.updateConfig(configId, json);
    }

    public ApiResult getConfigList(String tenantName, String projectId, String templateName) throws NetClientException {
        return api.getConfigList(tenantName, projectId, templateName);
    }

    public ApiResult deleteConfig(String configId) throws NetClientException {
        return api.deleteConfig(configId);
    }

    public ApiResult getConfigDetail(String configId) throws NetClientException {
        return api.getConfigDetail(configId);
    }

    public ApiResult getConfigDetail(String templateName, String tenantName) throws NetClientException {
        return api.getConfigDetail(templateName, tenantName);
    }
}
