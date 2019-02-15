package com.sdp.bcm.api;

import com.alibaba.fastjson.JSONObject;
import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.utils.RestFactory;
import com.sdp.common.entity.NetClientException;

/**
 * @author lumeiling
 * @package com.sdp.bcm.api
 * @create 2019/1/14 下午5:05
 **/
public class StorageClient {

    private StorageApi api;

    /**
     * 文件存储接口动态代理
     * @param url
     * @param factory
     */
    public StorageClient(String url, RestFactory factory) {
        this.api = factory.createStorageApiClient(url);
    }

    /**
     * 新建文件存储卷
     * @param json
     * @return
     * @throws NetClientException
     */
    public ApiResult createFileStorage(JSONObject json) throws NetClientException {
        return api.createFileStorage(json);
    }

    /**
     * 文件存储券列表
     * @return
     * @throws NetClientException
     */
    public ApiResult getFileStorageList(String tenantName, String storageFileName, String projectId, Integer page, Integer size) throws NetClientException {
        return api.getFileStorageList(tenantName, storageFileName, projectId, page, size);
    }

    /**
     * 删除文件存储卷
     * @param storageFileId
     * @return
     * @throws NetClientException
     */
    public ApiResult deleteFileStorageById(String storageFileId) throws NetClientException {
        return api.deleteFileStorageById(storageFileId);
    }

    /**
     * 获取文件存储卷详情
     * @param storageFileId
     * @return
     * @throws NetClientException
     */
    public ApiResult getFileStorageById(String storageFileId) throws NetClientException {
        return api.getFileStorageById(storageFileId);
    }

    /**
     * 格式化存储卷
     * @param storageFileId
     * @param json
     * @return
     * @throws NetClientException
     */
    public ApiResult formatFileStorageById(String storageFileId, JSONObject json) throws NetClientException {
        return api.formatFileStorageById(storageFileId, json);
    }

}
