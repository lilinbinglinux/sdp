package com.sdp.bcm.api;

import com.alibaba.fastjson.JSONObject;
import com.sdp.bcm.common.ApiResult;
import com.sdp.common.entity.NetClientException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author lumeiling
 * @package com.bonc.bcm.api
 * @create 2019/1/14 下午5:04
 **/
public interface StorageApi {

    /**
     * 新建文件存储卷
     * @param json
     * @return
     * @throws NetClientException
     */
    @POST
    @Path("/v1/storage/file")
    @Consumes(MediaType.APPLICATION_JSON)
    ApiResult createFileStorage(JSONObject json) throws NetClientException;

    /**
     * 文件存储券列表
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/storage/file")
    @Consumes(MediaType.APPLICATION_JSON)
    ApiResult getFileStorageList(@QueryParam("tenantName") String tenantName, @QueryParam("storageFileName") String storageFileName,
                                 @QueryParam("projectId") String projectId, @QueryParam("page") Integer page,
                                 @QueryParam("size") Integer size)throws NetClientException;

    /**
     * 删除文件存储卷
     * @param storageFileId
     * @return
     * @throws NetClientException
     */
    @DELETE
    @Path("/v1/storage/file/{storageFileId}")
    @Consumes(MediaType.APPLICATION_JSON)
    ApiResult deleteFileStorageById(@PathParam("storageFileId") String storageFileId) throws NetClientException;

    /**
     * 获取文件存储卷详情
     * @param storageFileId
     * @return
     * @throws NetClientException
     */
    @GET
    @Path("/v1/storage/file/{storageFileId}")
    @Consumes(MediaType.APPLICATION_JSON)
    ApiResult getFileStorageById(@PathParam("storageFileId") String storageFileId) throws NetClientException;

    /**
     * 格式化存储卷
     * @param storageFileId
     * @param json
     * @return
     * @throws NetClientException
     */
    @PUT
    @Path("/v1/storage/file/{storageFileId}")
    @Consumes(MediaType.APPLICATION_JSON)
    ApiResult formatFileStorageById(@PathParam("storageFileId") String storageFileId, JSONObject json) throws NetClientException;

}
