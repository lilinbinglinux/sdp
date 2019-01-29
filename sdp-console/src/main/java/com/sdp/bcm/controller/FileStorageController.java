package com.sdp.bcm.controller;

import com.alibaba.fastjson.JSONObject;
import com.sdp.bcm.api.StorageClient;
import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.utils.RestFactory;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.frame.util.StringUtil;
import com.sdp.frame.web.entity.user.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * @author lumeiling
 * @package com.bonc.bcm.controller
 * @create 2019/1/14 下午6:38
 **/

@Controller
@RequestMapping("/bcm/v1/storage/file")
public class FileStorageController {

    private static final Logger LOG = LoggerFactory.getLogger(FileStorageController.class);

    @Value("${bcm.context.url}")
    private String bcmUrl;

    @RequestMapping(value = {"/page"}, method = RequestMethod.GET)
    public String getFileStoragePage() {
        return "bcm/storage/fileStorage";
    }

    /**
     * 新建文件存储卷
     * @param json
     * @return
     */
    @RequestMapping(value = {""}, method = RequestMethod.POST)
    @ResponseBody
    public ApiResult createFileStorage(@RequestBody JSONObject json) {
        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user) {
            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        String projectId = CurrentUserUtils.getInstance().getProjectId();
        if (!StringUtil.isNotEmpty(projectId)){
            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未定位到项目，请先前往项目管理");
        }

        try {
//            TODO 修改项目配额，归为已使用存储
            json.put("tenantName", user.getTenantId());
            json.put("createdBy", user.getUserId());
            json.put("projectId", projectId);
            StorageClient client = new StorageClient(this.bcmUrl, new RestFactory());
            return client.createFileStorage(json);
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 文件存储券列表
     * 当前无项目，查询所有；否则按照项目过滤
     * @return
     */
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getFileStorageList(String storageFileName, Integer page, Integer size) {
        User user = CurrentUserUtils.getInstance().getUser();
        if (null == user) {
            return new ApiResult(Dictionary.HttpStatus.UNAUTHORIZED.value, "未获取到用户信息！");
        }

        try {
            StorageClient client = new StorageClient(this.bcmUrl, new RestFactory());
            return client.getFileStorageList(user.getTenantId(), storageFileName, CurrentUserUtils.getInstance().getProjectId(), page, size);
        }catch (Exception e){
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 删除文件存储卷
     * @param storageFileId
     * @return
     */
    @RequestMapping(value = {"/{storageFileId}"}, method = RequestMethod.DELETE)
    @ResponseBody
    public ApiResult deleteFileStorageById(@PathVariable("storageFileId") String storageFileId) {
        try {
            StorageClient client = new StorageClient(this.bcmUrl, new RestFactory());
            return client.deleteFileStorageById(storageFileId);
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 获取文件存储卷详情
     * @param storageFileId
     * @return
     */
    @RequestMapping(value = {"/{storageFileId}"}, method = RequestMethod.GET)
    @ResponseBody
    public ApiResult getFileStorageById(@PathVariable("storageFileId") String storageFileId) {
        try {
            StorageClient client = new StorageClient(this.bcmUrl, new RestFactory());
            return client.getFileStorageById(storageFileId);
        }catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 格式化存储卷
     * @param storageFileId
     * @return
     */
    @RequestMapping(value = {"/{storageFileId}"}, method = RequestMethod.PUT)
    @ResponseBody
    public ApiResult formatFileStorageById(@PathVariable("storageFileId") String storageFileId) {
        try {
            JSONObject json = new JSONObject();
            json.put("operation", "format");
            StorageClient client = new StorageClient(this.bcmUrl, new RestFactory());
            return client.formatFileStorageById(storageFileId, json);
        }catch (Exception e){
            LOG.error(e.getMessage(), e);
            throw e;
        }
    }

}
