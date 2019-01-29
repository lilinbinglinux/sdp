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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdp.bcm.api.ServiceClient;
import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.utils.RestFactory;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.frame.util.StringUtil;
import com.sdp.frame.web.entity.user.User;

/**
 * 配置文件
 *
 */
@Controller
@RequestMapping("/bcm/v1/config")
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Value("${bcm.context.url}")
    private String bcmUrl;

    /**
     * 配置文件页面
     *
     * @return
     */
    @RequestMapping(value = { "/page" }, method = RequestMethod.GET)
    public String configPage() {

        return "bcm/service/config";
    }

    /**
     * 配置文件模板创建
     *
     * @param json
     * @return
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.POST)
    @ResponseBody
    public ApiResult createConfig(@RequestBody JSONObject json) {
        ApiResult result = null;

        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user) {

            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        String projectId = CurrentUserUtils.getInstance().getProjectId();
        if (!StringUtil.isNotEmpty(projectId)) {

            return new ApiResult(Dictionary.HttpStatus.FORBIDDEN.value, "未获取到项目！");
        }

        json.put("tenantName", user.getTenantId());
        json.put("createdBy", user.getLoginId());
        json.put("projectId", projectId);
        logger.info("配置文件参数：" + JSON.toJSONString(json));
        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            result = serviceClient.createConfig(json);

        } catch (Exception e) {
            logger.error("配置文件创建失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "配置文件创建失败");
        }

        return result;
    }

    /**
     * 配置文件修改
     *
     * @param configId
     * @param json
     * @return
     */
    @RequestMapping(value = { "/{configId}" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult updateConfig(@PathVariable("configId") String configId, @RequestBody JSONObject json) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.updateConfig(configId, json);

            return result;

        } catch (Exception e) {
            logger.error("配置文件修改失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "配置文件修改失败");
        }
    }

    /**
     * 配置文件删除
     *
     * @param configId
     * @return
     */
    @RequestMapping(value = { "/{configId}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResult deleteConfig(@PathVariable("configId") String configId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.deleteConfig(configId);

            return result;

        } catch (Exception e) {
            logger.error("配置文件删除失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "配置文件删除失败");
        }

    }

    /**
     * 配置文件列表
     *
     * @param templateName
     * @return
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult configList(@RequestParam(value = "templateName", required = false) String templateName) {
        ApiResult result = null;

        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user) {

            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        String projectId = CurrentUserUtils.getInstance().getProjectId();

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            result = serviceClient.getConfigList(user.getTenantId(), projectId, templateName);

        } catch (Exception e) {
            logger.error("获取配置文件列表失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取配置文件列表失败");
        }

        return result;
    }

    /**
     * 配置文件详情
     *
     * @param configId
     * @return
     */
    @RequestMapping(value = { "/{configId}" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getConfigDetail(@PathVariable("configId") String configId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getConfigDetail(configId);

            return result;

        } catch (Exception e) {
            logger.error("获取配置文件详情失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取配置文件详情失败");
        }
    }

    /**
     * 配置文件详情
     *
     * @param templateName
     * @return
     */
    @RequestMapping(value = { "/templateName" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getConfigDetailByTemplateName(
            @RequestParam(value = "templateName", required = true) String templateName) {

        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user) {

            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getConfigDetail(templateName, user.getTenantId());

            return result;

        } catch (Exception e) {
            logger.error("获取配置文件详情失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取配置文件详情失败");
        }
    }

}
