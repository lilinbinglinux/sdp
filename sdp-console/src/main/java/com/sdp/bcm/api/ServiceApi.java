package com.sdp.bcm.api;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;
import com.sdp.bcm.common.ApiResult;
import com.sdp.common.entity.NetClientException;

public interface ServiceApi {

    /**
     * 创建服务
     *
     * @param json
     * @return
     * @throws NetClientException
     */
    @POST
    @Path("/v1/service")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult createService(JSONObject json) throws NetClientException;

    /**
     * 启动、停止服务
     *
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateService(@PathParam("serviceId") String serviceId, JSONObject json) throws NetClientException;

    /**
     * 服务删除
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/service/{serviceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteService(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 服务列表
     *
     * @param json
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/page")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getServiceList(@QueryParam("serviceName") String serviceName,
            @QueryParam("tenantName") String tenantName, @QueryParam("projectId") String projectId,
            @QueryParam("page") Integer page, @QueryParam("size") Integer size) throws NetClientException;

    /**
     * 服务详情
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceDetail(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 服务详情(通过服务名称和租户名称)
     *
     * @param serviceName
     * @param tenantName
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/info")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceDetail(@QueryParam("serviceName") String serviceName,
            @QueryParam("tenantName") String tenantName) throws NetClientException;

    /**
     * 弹性伸缩
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/scale")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult serviceScale(@PathParam("serviceId") String serviceId, JSONObject json) throws NetClientException;

    /**
     * 自动伸缩
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/hpa")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult hpa(@PathParam("serviceId") String serviceId, JSONObject json) throws NetClientException;

    /**
     * 更新服务配额
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/quota")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateServiceQuota(@PathParam("serviceId") String serviceId, JSONObject json)
            throws NetClientException;

    /**
     * 服务滚动升级
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/upgrade")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult serviceUpgrade(@PathParam("serviceId") String serviceId, JSONObject json)
            throws NetClientException;

    /**
     * 服务启动日志
     *
     * @param serviceId
     * @param tenantName
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/event")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceStartLog(@PathParam("serviceId") String serviceId,
            @QueryParam("tenantName") String tenantName) throws NetClientException;

    /**
     * 容器启动日志
     *
     * @param serviceId
     * @param tenantName
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/podevent")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getPodLog(@PathParam("serviceId") String serviceId, @QueryParam("tenantName") String tenantName)
            throws NetClientException;

    /**
     * 获取服务访问策略
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/strategy")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceStrategy(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 获取服务的实例详情
     *
     * @param serviceId
     * @param tenantName
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/podinfo")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getPodInfo(@PathParam("serviceId") String serviceId, @QueryParam("tenantName") String tenantName)
            throws NetClientException;

    /**
     * 删除单个pod实例
     *
     * @param tenantName
     * @param podName
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/service/pod")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deletePod(@QueryParam("tenantName") String tenantName, @QueryParam("podName") String podName)
            throws NetClientException;

    /**
     * 获取服务的环境变量
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/env")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceEnv(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 新增/修改/删除服务的环境变量
     *
     * @param serviceId
     * @param envData
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/env")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateServiceEnv(@PathParam("serviceId") String serviceId, Map<String, Object> envData)
            throws NetClientException;

    /**
     * 获取服务的配置文件
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/config")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceConfig(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 新增/修改服务的配置文件
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/config")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateServiceConfig(@PathParam("serviceId") String serviceId, JSONObject json)
            throws NetClientException;

    /**
     * 删除服务的配置文件
     *
     * @param serviceId
     * @param serviceConfigId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/service/{serviceId}/config")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteServiceConfig(@PathParam("serviceId") String serviceId,
            @QueryParam("serviceConfigId") String serviceConfigId) throws NetClientException;

    /**
     * 获取服务的存储卷
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/cephfs")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceCephfs(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 新增/修改服务的存储卷
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/cephfs")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateServiceCephfs(@PathParam("serviceId") String serviceId, JSONObject json)
            throws NetClientException;

    /**
     * 删除服务的存储卷
     *
     * @param serviceId
     * @param serviceCephFileId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/service/{serviceId}/cephfs")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteServiceCephfs(@PathParam("serviceId") String serviceId,
            @QueryParam("serviceCephFileId") String serviceCephFileId) throws NetClientException;

    /**
     * 获取服务的本地存储
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/localstorage")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceLocalStorage(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 新增/修改服务的本地存储
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/localstorage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateServiceLocalStorage(@PathParam("serviceId") String serviceId, JSONObject json)
            throws NetClientException;

    /**
     * 删除服务的本地存储
     *
     * @param serviceId
     * @param serviceHostpathId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/service/{serviceId}/localstorage")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteServiceLocalStorage(@PathParam("serviceId") String serviceId,
            @QueryParam("serviceHostpathId") String serviceHostpathId) throws NetClientException;

    /**
     * 获取服务的块存储
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/cephrbd")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceCephRbd(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 新增/修改服务的块存储
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/cephrbd")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateServiceCephRbd(@PathParam("serviceId") String serviceId, JSONObject json)
            throws NetClientException;

    /**
     * 删除服务的块存储
     *
     * @param serviceId
     * @param serviceCephRbdId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/service/{serviceId}/cephrbd")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteServiceCephRbd(@PathParam("serviceId") String serviceId,
            @QueryParam("serviceCephRbdId") String serviceCephRbdId) throws NetClientException;

    /**
     * 获取服务的节点亲和
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/nodeaffinity")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceNodeAffinity(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 新增/修改服务的节点亲和
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/nodeaffinity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateServiceNodeAffinity(@PathParam("serviceId") String serviceId, JSONObject json)
            throws NetClientException;

    /**
     * 删除服务的节点亲和
     *
     * @param serviceId
     * @param serviceAffinityId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/service/{serviceId}/nodeaffinity")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteServiceNodeAffinity(@PathParam("serviceId") String serviceId,
            @QueryParam("serviceAffinityId") String serviceAffinityId) throws NetClientException;

    /**
     * 获取服务的服务亲和
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/affinity")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceAffinity(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 新增/修改服务的服务亲和
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/affinity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateServiceAffinity(@PathParam("serviceId") String serviceId, JSONObject json)
            throws NetClientException;

    /**
     * 删除服务的服务亲和
     *
     * @param serviceId
     * @param serviceAffinityId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/service/{serviceId}/affinity")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteServiceAffinity(@PathParam("serviceId") String serviceId,
            @QueryParam("serviceAffinityId") String serviceAffinityId) throws NetClientException;

    /**
     * 获取服务的健康检查
     *
     * @param serviceId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/service/{serviceId}/health")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult getServiceHealth(@PathParam("serviceId") String serviceId) throws NetClientException;

    /**
     * 新增/修改服务的健康检查
     *
     * @param serviceId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/service/{serviceId}/health")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateServiceHealth(@PathParam("serviceId") String serviceId, JSONObject json)
            throws NetClientException;

    /**
     * 删除服务的健康检查
     *
     * @param serviceId
     * @param serviceHealthId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/service/{serviceId}/health")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteServiceHealth(@PathParam("serviceId") String serviceId,
            @QueryParam("serviceHealthId") String serviceHealthId) throws NetClientException;

    /**
     * 域名创建
     *
     * @param json
     * @return
     * @throws NetClientException
     */
    @POST
    @Path("/v1/ingress/domain")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult createDomain(JSONObject json) throws NetClientException;

    /**
     * 域名修改
     *
     * @param domainId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/ingress/domain/{domainId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateDomain(@PathParam("domainId") String domainId, JSONObject json) throws NetClientException;

    /**
     * 域名删除
     *
     * @param domainId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/ingress/domain/{domainId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteDomain(@PathParam("domainId") String domainId) throws NetClientException;

    /**
     * 域名查询
     *
     * @param tenantName
     * @param projectId
     * @param templateName
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/ingress/domain")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getDomainList(@QueryParam("tenantName") String tenantName, @QueryParam("page") String page,
            @QueryParam("page") String size) throws NetClientException;

    /**
     * Http代理创建
     *
     * @param json
     * @return
     * @throws NetClientException
     */
    @POST
    @Path("/v1/ingress/proxy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult createProxy(JSONObject json) throws NetClientException;

    /**
     * Http代理修改
     *
     * @param domainId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/ingress/proxy/{ingressProxyId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateProxy(@PathParam("ingressProxyId") String ingressProxyId, JSONObject json)
            throws NetClientException;

    /**
     * Http代理删除
     *
     * @param domainId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/ingress/proxy")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteProxy(@QueryParam("serviceId") String serviceId, @QueryParam("tenantName") String tenantName)
            throws NetClientException;

    /**
     * Http代理查询
     *
     * @param serviceId
     * @param tenantName
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/ingress/domain")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getProxyList(@QueryParam("serviceId") String serviceId,
            @QueryParam("tenantName") String tenantName) throws NetClientException;

    /**
     * 环境变量新增
     *
     * @param json
     * @return
     * @throws NetClientException
     */
    @POST
    @Path("/v1/env")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult createEnv(JSONObject json) throws NetClientException;

    /**
     * 环境变量修改
     *
     * @param envId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/env/{envId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateEnv(@PathParam("envId") String envId, JSONObject json) throws NetClientException;

    /**
     * 环境变量删除
     *
     * @param envId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/env/{envId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteEnv(@PathParam("envId") String envId) throws NetClientException;

    /**
     * 环境变量查询
     *
     * @param tenantName
     * @param projectId
     * @param templateName
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/env")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getEnvList(@QueryParam("tenantName") String tenantName, @QueryParam("projectId") String projectId,
            @QueryParam("templateName") String templateName) throws NetClientException;

    /**
     * 环境变量详情
     *
     * @param envId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/env/{envId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getEnvDetail(@PathParam("envId") String envId) throws NetClientException;

    /**
     * 环境变量详情
     *
     * @param templateName
     * @param tenantName
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/env/templateName")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getEnvDetail(@QueryParam("templateName") String templateName,
            @QueryParam("tenantName") String tenantName) throws NetClientException;

    /**
     * 配置文件新增
     *
     * @param json
     * @return
     * @throws NetClientException
     */
    @POST
    @Path("/v1/config")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult createConfig(JSONObject json) throws NetClientException;

    /**
     * 配置文件修改
     *
     * @param configId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/config/{configId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult updateConfig(@PathParam("configId") String configId, JSONObject json) throws NetClientException;

    /**
     * 配置文件删除
     *
     * @param configId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/config/{configId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public ApiResult deleteConfig(@PathParam("configId") String configId) throws NetClientException;

    /**
     * 配置文件查询
     *
     * @param tenantName
     * @param projectId
     * @param templateName
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/config")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getConfigList(@QueryParam("tenantName") String tenantName,
            @QueryParam("projectId") String projectId, @QueryParam("templateName") String templateName)
            throws NetClientException;

    /**
     * 配置文件详情
     *
     * @param configId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/config/{configId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getConfigDetail(@PathParam("configId") String configId) throws NetClientException;

    /**
     * 配置文件详情
     *
     * @param templateName
     * @param tenantName
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/config/templateName")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResult getConfigDetail(@QueryParam("templateName") String templateName,
            @QueryParam("tenantName") String tenantName) throws NetClientException;
}
