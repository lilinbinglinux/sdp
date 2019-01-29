package com.sdp.bcm.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdp.bcm.api.ImageClient;
import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.entity.UploadImageModel;
import com.sdp.bcm.model.SWCiFileModel;
import com.sdp.bcm.utils.RestFactory;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.FtpConfigProperties;
import com.sdp.common.FtpUtil;
import com.sdp.common.constant.Dictionary;
import com.sdp.frame.util.StringUtil;

/**
 * 镜像
 *
 */
@Controller
@RequestMapping("/bcm/v1/image")
public class ImageController {

	private static final Logger LOG = LoggerFactory.getLogger(ImageController.class);

	@Value("${bcm.context.url}")
	private String bcmUrl;

	@Autowired
	private FtpConfigProperties ftpConfigProperties;

	/**
	 * 公共镜像页面
	 *
	 * @return
	 */
	@RequestMapping(value = { "/public/page" }, method = RequestMethod.GET)
	public String publicPage() {
		return "bcm/image/publicImage";
	}

	/**
	 * 公共镜像-详情页面
	 *
	 * @return
	 */
	@RequestMapping(value = { "/public/detailPage" }, method = RequestMethod.GET)
	public String publicDetailPage() {
		return "bcm/image/publicDetail";
	}

	/**
	 * 我的镜像页面
	 *
	 * @return
	 */
	@RequestMapping(value = { "/individual/page" }, method = RequestMethod.GET)
	public String individualPage() {
		return "bcm/image/myImage";
	}

	@RequestMapping(value = { "/individual/detailPage" }, method = RequestMethod.GET)
	public String individualDetailPage() {
		return "bcm/image/myDetail";
	}

	/**
	 * 镜像部署页面
	 *
	 * @return
	 */
	@RequestMapping(value = { "/deployment/page" }, method = RequestMethod.GET)
	public String deployPage() {
		return "bcm/image/imageDeploy";
	}

	/**
	 * 镜像部署-部署任务页面
	 *
	 * @return
	 */
	@RequestMapping(value = { "/deployment/taskPage" }, method = RequestMethod.GET)
	public String deployTaskPage() {
		return "bcm/image/deployTask";
	}

	/**
	 * 获取镜像列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getImageList(@RequestParam(required = false) String imageName,
			@RequestParam(required = false) String projectId, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) throws Exception {
		try {
			ImageClient client = new ImageClient(bcmUrl, new RestFactory());
			ApiResult apiResult = client.getImageGroup(CurrentUserUtils.getInstance().getUser().getTenantId(),
					projectId, imageName, page, size);
			return apiResult;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 获取镜详情
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/{imageName}" }, method = RequestMethod.GET)
	@ResponseBody
	public ApiResult single(@PathVariable("imageName") String imageName) throws Exception {
		try {
			ImageClient client = new ImageClient(bcmUrl, new RestFactory());
			ApiResult apiResult = client.single(CurrentUserUtils.getInstance().getUser().getTenantId(), imageName);
			return apiResult;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 获取镜像，判断是否已经存在
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/imageName" }, method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getImageByName(@RequestParam(value = "imageName", required = true) String imageName,
			@RequestParam(value = "imageVersion", required = true) String imageVersion) throws Exception {
		try {
			ImageClient client = new ImageClient(bcmUrl, new RestFactory());
			ApiResult apiResult = client.getImageByName(CurrentUserUtils.getInstance().getUser().getTenantId(),
					imageName, imageVersion);
			return apiResult;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 获取公共镜像
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/public" }, method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getImagePublic(@RequestParam String imageName, @RequestParam Integer page,
			@RequestParam Integer size) throws Exception {
		try {
			ImageClient client = new ImageClient(bcmUrl, new RestFactory());
			ApiResult apiResult = client.getImagePublic(CurrentUserUtils.getInstance().getUser().getTenantId(),
					imageName, page, size);
			return apiResult;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 删除镜像
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/{imageId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public ApiResult deleteImage(@PathVariable("imageId") String imageId) throws Exception {
		try {
			ImageClient client = new ImageClient(bcmUrl, new RestFactory());
			ApiResult apiResult = client.deleteImage(imageId);
			return apiResult;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 上传镜像
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "" }, method = RequestMethod.POST)
	@ResponseBody
	public ApiResult uploadImage(String imageString, @RequestParam("imageFile") MultipartFile imageFile,
			HttpServletRequest request) throws Exception {
		try {
			UploadImageModel image = JSON.toJavaObject(JSONObject.parseObject(imageString), UploadImageModel.class);
			String ftpPath = this.getUploadPath();
			String localPath = request.getServletContext().getRealPath("/temp/" + ftpPath + "/");
			if (this.uploadFile(localPath, ftpPath, imageFile)) {
				ImageClient client = new ImageClient(this.bcmUrl, new RestFactory());
				image.setTenantName(CurrentUserUtils.getInstance().getUser().getTenantId());
				image.setCreatedBy(CurrentUserUtils.getInstance().getUser().getLoginId());
				image.setImageFilePath(
						JSON.toJSONString(this.getSWCiFileModel(ftpPath + imageFile.getOriginalFilename())));
				ApiResult result = client.uploadImage(image);
				if (result.getCode() != Dictionary.HttpStatus.OK.value) {
					FtpUtil.deleteDir(ftpPath);
				}
				return result;
			} else {
				return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "上传失败");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	private SWCiFileModel getSWCiFileModel(String path) {
		SWCiFileModel swCiFileModel = new SWCiFileModel();
		swCiFileModel.setFilePath(path);
		swCiFileModel.setFtpHost(ftpConfigProperties.getHost());
		swCiFileModel.setFtpPort(ftpConfigProperties.getPort());
		swCiFileModel.setFtpUser(ftpConfigProperties.getUsername());
		swCiFileModel.setFtpPass(ftpConfigProperties.getPassword());
		return swCiFileModel;
	}

	private boolean uploadFile(String localPath, String ftpPath, MultipartFile upFile) {
		File file = new File(localPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		File uploadFile = new File(localPath + File.separatorChar + upFile.getOriginalFilename());
		try {
			upFile.transferTo(uploadFile);
			return FtpUtil.uploadFile(uploadFile, ftpPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			uploadFile.delete();
			file.delete();
		}
		return false;
	}

	private String getUploadPath() {
		StringBuffer buff = new StringBuffer(Dictionary.BCMConstant.IMAGEFILEPRE.value);
		buff.append("/").append(CurrentUserUtils.getInstance().getUser().getTenantId());
		buff.append("/").append(StringUtil.getUUID().substring(0, 8)).append("/");
		return buff.toString();
	}

	/**
	 * 获取镜详情分页信息
	 * 
	 * @param imageName 镜像名称
	 * @param projectId 项目ID
	 * @param page      页数
	 * @param size      每页数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/{imageName}/page" }, method = RequestMethod.GET)
	public ApiResult getImageByImageName(@PathVariable("imageName") String imageName,
			@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "2000") int size) {
		try {
			ImageClient client = new ImageClient(bcmUrl, new RestFactory());
			ApiResult apiResult = client.getImageByImageName(CurrentUserUtils.getInstance().getUser().getTenantId(),
					imageName, projectId, page, size);
			return apiResult;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}
}
