package com.sdp.bcm.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.entity.UploadImageModel;
import com.sdp.common.entity.NetClientException;

public interface ImageApi {

	/**
	 * 
	 * /** 获取镜像列表
	 */
	@GET
	@Path("/v1/image")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getImageGroup(@QueryParam("tenantName") String tenantName,
			@QueryParam("projectId") String projectId, @QueryParam("imageName") String imageName,
			@QueryParam("page") Integer page, @QueryParam("size") Integer size) throws NetClientException;

	/**
	 * 获取镜像详情
	 */
	@GET
	@Path("/v1/image/{imageName}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult single(@QueryParam("tenantName") String tenantName, @PathParam("imageName") String imageName)
			throws NetClientException;

	/**
	 * 获取公共镜像列表
	 */
	@GET
	@Path("/v1/image/public")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getImagePublic(@QueryParam("tenantName") String tenantName,
			@QueryParam("imageName") String imageName, @QueryParam("page") Integer page,
			@QueryParam("size") Integer size) throws NetClientException;

	/**
	 * 删除镜像
	 */
	@DELETE
	@Path("/v1/image/{imageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult deleteImage(@PathParam("imageId") String imageId) throws NetClientException;

	/**
	 * 上传镜像
	 */
	@POST
	@Path("/v1/image")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult uploadImage(UploadImageModel image) throws NetClientException;

	/**
	 * 获取镜像，判断是否已经存在
	 */
	@GET
	@Path("/v1/image/imageName")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getImageByName(@QueryParam("tenantId") String tenantId, @QueryParam("imageName") String imageName,
			@QueryParam("imageVersion") String imageVersion);

	/**
	 * 获取镜详情分页信息
	 * @param tenantId
	 * @param imageName
	 * @param projectId
	 * @param page
	 * @param size
	 * @return
	 */
	@GET
	@Path("/v1/image/{imageName}/page")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getImageByImageName(@QueryParam("tenantName") String tenantName,
			@PathParam("imageName") String imageName, @QueryParam("projectId") String projectId,
			@QueryParam("page") int page, @QueryParam("size") int size);
}
