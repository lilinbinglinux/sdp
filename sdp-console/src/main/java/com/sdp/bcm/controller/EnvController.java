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
 * 环境变量
 *
 */
@Controller
@RequestMapping("/bcm/v1/env")
public class EnvController {

    private static final Logger logger = LoggerFactory.getLogger(EnvController.class);

    @Value("${bcm.context.url}")
    private String bcmUrl;

    /**
     * 环境变量页面
     *
     * @return
     */
    @RequestMapping(value = { "/page" }, method = RequestMethod.GET)
    public String envPage() {

        return "bcm/service/env";
    }

    /**
     * 环境变量模板创建
     *
     * @param json
     * @return
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.POST)
    @ResponseBody
    public ApiResult createEnv(@RequestBody JSONObject json) {
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

        logger.info("创建env参数：" + JSON.toJSONString(json));

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            result = serviceClient.createEnv(json);

        } catch (Exception e) {
            logger.error("环境变量创建失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "环境变量创建失败");
        }

        return result;
    }

    /**
     * 环境变量修改
     *
     * @param envId
     * @param json
     * @return
     */
    @RequestMapping(value = { "/{envId}" }, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult updateEnv(@PathVariable("envId") String envId, @RequestBody JSONObject json) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.updateEnv(envId, json);

            return result;

        } catch (Exception e) {
            logger.error("环境变量修改失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "环境变量修改失败");
        }
    }

    /**
     * 环境变量删除
     *
     * @param envId
     * @return
     */
    @RequestMapping(value = { "/{envId}" }, method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResult deleteEnv(@PathVariable("envId") String envId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.deleteEnv(envId);

            return result;

        } catch (Exception e) {
            logger.error("环境变量删除失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "环境变量删除失败");
        }

    }

    /**
     * 环境变量列表
     *
     * @param templateName
     * @return
     */
    @RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult envList(@RequestParam(value = "templateName", required = false) String templateName) {
        ApiResult result = null;

        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user) {

            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        String projectId = CurrentUserUtils.getInstance().getProjectId();

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            result = serviceClient.getEnvList(user.getTenantId(), projectId, templateName);

        } catch (Exception e) {
            logger.error("获取环境变量列表失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取环境变量列表失败");
        }

        return result;
    }

    /**
     * 环境变量详情
     *
     * @param envId
     * @return
     */
    @RequestMapping(value = { "/{envId}" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getEnvDetail(@PathVariable("envId") String envId) {

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getEnvDetail(envId);

            return result;

        } catch (Exception e) {
            logger.error("获取环境变量详情失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取环境变量详情失败");
        }
    }

    /**
     * 环境变量详情
     *
     * @param templateName
     * @return
     */
    @RequestMapping(value = { "/templateName" }, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getEnvDetailByTemplateName(
            @RequestParam(value = "templateName", required = true) String templateName) {

        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user) {

            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        try {
            ServiceClient serviceClient = new ServiceClient(bcmUrl, new RestFactory());
            ApiResult result = serviceClient.getEnvDetail(templateName, user.getTenantId());

            return result;

        } catch (Exception e) {
            logger.error("获取环境变量详情失败：", e);

            return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "获取环境变量详情失败");
        }
    }

}
