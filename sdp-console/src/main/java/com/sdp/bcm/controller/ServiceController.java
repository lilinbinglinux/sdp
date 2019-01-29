package com.sdp.bcm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sdp.bcm.api.ServiceClient;
import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.utils.RestFactory;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.frame.util.StringUtil;
import com.sdp.frame.web.entity.user.User;

/**
 * 服务
 *
 */
@Controller
@RequestMapping("/bcm/v1/service")
public class ServiceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @Value("${bcm.context.url}")
    private String bcmUrl;

    /**
     * 服务列表页面
     *
     * @return
     */
    @RequestMapping(value = { "/servicePage" }, method = RequestMethod.GET)
    public String servicePage() {

        return "bcm/service/service";
    }

    /**
     * 服务创建页面
     *
     * @return
     */
    @RequestMapping(value = { "/serviceCreatePage" }, method = RequestMethod.GET)
    public String serviceCreatePage() {

        return "bcm/service/service-create";
    }

    /**
     * 服务详情页面
     *
     * @return
     */
    @RequestMapping(value = { "/serviceDetailPage" }, method = RequestMethod.GET)
    public String serviceDetailPage() {

        return "bcm/service/service-detail";
    }

    /**
     * 创建服务
     *
     * @param json
     * @return
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.POST)
    @ResponseBody
    public ApiResult createService(@RequestBody JSONObject json) {
        ApiResult result = null;

        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user) {

            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        String projectId = CurrentUserUtils.getInstance().getProjectId();
        if (!StringUtil.isNotEmpty(projectId)) {

            return new ApiResult(Dictionary.HttpStatus.FORBIDDEN.value, "未获取到项目！");
        }

        // 配额校验
        // TODO

        json.put("tenantName", user.getTenantId());
        json.put("createBy", user.getLoginId());
        json.put("projectId", projectId);

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            result = serviceClient.createService(json);

        } catch (Exception e) {
            logger.error("创建服务失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "创建服务失败");
        }

        return result;
    }

    /**
     * 获取服务列表
     *
     * @param serviceName
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult serviceList(@RequestParam(value = "serviceName", required = false) String serviceName,
            Integer page, Integer size) {
        ApiResult result = null;

        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user) {

            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        String projectId = CurrentUserUtils.getInstance().getProjectId();

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            result = serviceClient.getServiceList(serviceName, user.getTenantId(), projectId, page, size);

        } catch (Exception e) {
            logger.error("获取服务列表失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取服务列表失败");
        }

        return result;
    }

    /**
     * 启动、停止服务
     *
     * @param serviceId
     * @param json
     * @return
     */
    @RequestMapping(value = { "/{serviceId}" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult updateService(@PathVariable("serviceId") String serviceId, @RequestBody JSONObject json) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.updateService(serviceId, json);

            return result;

        } catch (Exception e) {
            logger.error("修改服务失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "修改服务失败");
        }
    }

    /**
     * 删除服务
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResult deleteService(@PathVariable("serviceId") String serviceId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.deleteService(serviceId);

            return result;

        } catch (Exception e) {
            logger.error("删除服务失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "删除服务失败");
        }

    }

    /**
     * 弹性伸缩
     *
     * @param serviceId
     * @param json
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/scale" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult scale(@PathVariable("serviceId") String serviceId, @RequestBody JSONObject json) {
        // 配额校验
        // TODO

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.serviceScale(serviceId, json);

            return result;

        } catch (Exception e) {
            logger.error("弹性伸缩失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "弹性伸缩失败");
        }
    }

    /**
     * 自动伸缩
     *
     * @param serviceId
     * @param json
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/hpa" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult hpa(@PathVariable("serviceId") String serviceId, @RequestBody JSONObject json) {
        // 配额校验
        // TODO

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.hpa(serviceId, json);

            return result;

        } catch (Exception e) {
            logger.error("自动伸缩失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "自动伸缩失败");
        }
    }

    /**
     * 更新服务配额
     *
     * @param serviceId
     * @param json
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/quota" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult updateServiceQuota(@PathVariable("serviceId") String serviceId, @RequestBody JSONObject json) {
        // 配额校验
        // TODO

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.updateServiceQuota(serviceId, json);

            return result;

        } catch (Exception e) {
            logger.error("更新服务配额失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "更新服务配额失败");
        }
    }

    /**
     * 服务滚动升级
     *
     * @param serviceId
     * @param json
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/upgrade" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult serviceUpgrade(@PathVariable("serviceId") String serviceId, @RequestBody JSONObject json) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.serviceUpgrade(serviceId, json);

            return result;

        } catch (Exception e) {
            logger.error("服务滚动升级失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "服务滚动升级失败");
        }
    }

    /**
     * 服务详情
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getServiceDetail(@PathVariable("serviceId") String serviceId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getServiceDetail(serviceId);

            return result;

        } catch (Exception e) {
            logger.error("获取服务详情失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取服务详情失败");
        }
    }

    /**
     * 服务详情
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/info" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getServiceDetailByServiceName(
            @RequestParam(value = "serviceName", required = false) String serviceName) {

        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user) {

            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getServiceDetail(serviceName, user.getTenantId());

            return result;

        } catch (Exception e) {
            logger.error("获取服务详情失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取服务详情失败");
        }
    }

    /**
     * 获取服务启动日志
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/event" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getServiceStartLog(@PathVariable("serviceId") String serviceId) {

        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user || !StringUtil.isNotEmpty(user.getTenantId())) {

            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getServiceStartLog(serviceId, user.getTenantId());

            return result;

        } catch (Exception e) {
            logger.error("获取服务启动日志失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取服务启动日志失败");
        }
    }

    /**
     * 实例列表
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/podInfo" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getPodInfo(@PathVariable("serviceId") String serviceId) {

        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user || !StringUtil.isNotEmpty(user.getTenantId())) {

            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getPodInfo(serviceId, user.getTenantId());

            return result;

        } catch (Exception e) {
            logger.error("获取实例列表失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取实例列表失败");
        }
    }

    /**
     * 获取服务的环境变量
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/env" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getServiceEnv(@PathVariable("serviceId") String serviceId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getServiceEnv(serviceId);

            return result;

        } catch (Exception e) {
            logger.error("获取服务的环境变量失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取服务的环境变量失败");
        }
    }

    /**
     * 修改服务的环境变量
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/env" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult updateServiceEnv(@PathVariable("serviceId") String serviceId, @RequestBody JSONObject envData) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.updateServiceEnv(serviceId, envData);

            return result;

        } catch (Exception e) {
            logger.error("修改服务的环境变量失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "修改服务的环境变量失败");
        }
    }

    /**
     * 获取服务的配置文件
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/config" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getServiceConfig(@PathVariable("serviceId") String serviceId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getServiceConfig(serviceId);

            return result;

        } catch (Exception e) {
            logger.error("获取服务的配置文件失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取服务的配置文件失败");
        }
    }

    /**
     * 修改服务的配置文件
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/config" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult updateServiceConfig(@PathVariable("serviceId") String serviceId, @RequestBody JSONObject json) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.updateServiceConfig(serviceId, json);

            return result;

        } catch (Exception e) {
            logger.error("修改服务的配置文件失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "修改服务的配置文件失败");
        }
    }

    /**
     * 删除服务的配置文件
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/config" }, method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResult deleteServiceConfig(@PathVariable("serviceId") String serviceId,
            @RequestParam(value = "serviceConfigId", required = true) String serviceConfigId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.deleteServiceConfig(serviceId, serviceConfigId);

            return result;

        } catch (Exception e) {
            logger.error("删除服务的配置文件失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "删除服务的配置文件失败");
        }
    }

    /**
     * 获取服务的存储卷
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/cephfs" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getServiceCephfs(@PathVariable("serviceId") String serviceId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getServiceCephfs(serviceId);

            return result;

        } catch (Exception e) {
            logger.error("获取服务的存储卷失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取服务的存储卷失败");
        }
    }

    /**
     * 新增/修改服务的存储卷
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/cephfs" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult updateServiceCephfs(@PathVariable("serviceId") String serviceId, @RequestBody JSONObject json) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.updateServiceCephfs(serviceId, json);

            return result;

        } catch (Exception e) {
            logger.error("修改服务的存储卷失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "修改服务的存储卷失败");
        }
    }

    /**
     * 删除服务的存储卷
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/cephfs" }, method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResult deleteServiceCephfs(@PathVariable("serviceId") String serviceId,
            @RequestParam(value = "serviceConfigId", required = true) String serviceCephFileId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.deleteServiceCephfs(serviceId, serviceCephFileId);

            return result;

        } catch (Exception e) {
            logger.error("删除服务的存储卷失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "删除服务的存储卷失败");
        }
    }

    /**
     * 获取服务的健康检查
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/health" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getServiceHealth(@PathVariable("serviceId") String serviceId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getServiceHealth(serviceId);

            return result;

        } catch (Exception e) {
            logger.error("获取服务的健康检查失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取服务的健康检查失败");
        }
    }

    /**
     * 新增/修改服务的健康检查
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/health" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult updateServiceHealth(@PathVariable("serviceId") String serviceId, @RequestBody JSONObject json) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.updateServiceHealth(serviceId, json);

            return result;

        } catch (Exception e) {
            logger.error("修改服务的健康检查失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "修改服务的健康检查失败");
        }
    }

    /**
     * 删除服务的健康检查
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/health" }, method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResult deleteServiceHealth(@PathVariable("serviceId") String serviceId,
            @RequestParam(value = "serviceConfigId", required = true) String serviceHealthId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.deleteServiceHealth(serviceId, serviceHealthId);

            return result;

        } catch (Exception e) {
            logger.error("删除服务的健康检查失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "删除服务的健康检查失败");
        }
    }

    /**
     * 删除实例
     *
     * @param serviceId
     * @param podname
     * @return
     */
    @RequestMapping(value = { "/{serviceId}/{podname}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResult deletePod(@PathVariable("serviceId") String serviceId, @PathVariable("podname") String podname) {

        if (!StringUtil.isNotEmpty(podname)) {

            return new ApiResult(Dictionary.HttpStatus.UNPROCESABLE.value, "podname不能为空！");
        }

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.deletePod(serviceId, podname);

            return result;

        } catch (Exception e) {
            logger.error("删除实例失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "删除实例失败");
        }
    }

}
