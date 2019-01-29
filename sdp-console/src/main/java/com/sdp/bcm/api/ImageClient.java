package com.sdp.bcm.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.entity.UploadImageModel;
import com.sdp.bcm.utils.RestFactory;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.entity.NetClientException;

public class ImageClient {
	private static final Log LOG = LogFactory.getLog(ImageClient.class);

	private ImageApi api;

	public ImageClient(String url, RestFactory factory) {
		api = factory.creatImageClient(url);
	}

	public ApiResult getImageGroup(String tenantName, String projectId, String imageName, Integer page, Integer size)
			throws NetClientException {
		return api.getImageGroup(tenantName, projectId, imageName, page, size);
	}

	public ApiResult single(String tenantName, String imageName) throws NetClientException {
		return api.single(tenantName, imageName);
	}

	public ApiResult getImagePublic(String tenantName, String imageName, Integer page, Integer size)
			throws NetClientException {
		return api.getImagePublic(tenantName, imageName, page, size);
	}

	public ApiResult deleteImage(String imageId) throws NetClientException {
		return api.deleteImage(imageId);
	}

	public ApiResult uploadImage(UploadImageModel image) throws NetClientException {
		return api.uploadImage(image);
	}

	public ApiResult getImageByName(String tenantId, String imageName, String imageVersion) {
		return api.getImageByName(tenantId, imageName, imageVersion);
	}

	public ApiResult getImageByImageName(String tenantName, String imageName, String projectId, int page, int size) {
		return api.getImageByImageName(tenantName, imageName, projectId, page, size);
	}
}
