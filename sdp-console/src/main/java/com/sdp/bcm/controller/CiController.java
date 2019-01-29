package com.sdp.bcm.controller;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdp.bcm.api.CiClient;
import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.model.SWCiFileModel;
import com.sdp.bcm.model.SwCreateCiModel;
import com.sdp.bcm.model.SwCreateCredentialsModel;
import com.sdp.bcm.model.SwCreateDockerfileTemplateModel;
import com.sdp.bcm.model.SwUpdateCiModel;
import com.sdp.bcm.model.SwUpdateDockerfileTemplateModel;
import com.sdp.bcm.utils.RestFactory;
import com.sdp.common.CurrentUserUtils;
import com.sdp.common.FtpConfigProperties;
import com.sdp.common.FtpUtil;
import com.sdp.common.constant.Dictionary;
import com.sdp.frame.util.StringUtil;

/**
 * 构建
 *
 */
@Controller
@RequestMapping("/bcm/v1/ci")
public class CiController {

	private static final Logger LOG = LoggerFactory.getLogger(CiController.class);

	@Value("${bcm.context.url}")
	private String bcmUrl;

	@Autowired
	private FtpConfigProperties ftpConfigProperties;

	/*
	 * 代码构建页面
	 */
	@RequestMapping(value = { "/codeConstructs" }, method = RequestMethod.GET)
	public String codeConstructs() {
		return "bcm/ci/codeConstructs";
	}

	@RequestMapping(value = { "/addCodeTask" }, method = RequestMethod.GET)
	public String addCodeTask() {
		return "bcm/ci/addCodeTask";
	}

	/*
	 * dockerfile构建页面
	 */
	@RequestMapping(value = { "/dockerfileConstructs" }, method = RequestMethod.GET)
	public String dockerfileConstructs() {
		return "bcm/ci/dockerfileConstructs";
	}

	@RequestMapping(value = { "/addDockerfile" }, method = RequestMethod.GET)
	public String addDockerfile() {
		return "bcm/ci/addDockerfile";
	}

	@RequestMapping(value = { "/editDockerfile" }, method = RequestMethod.GET)
	public String editDockerfile() {
		return "bcm/ci/editDockerfile";
	}

	@RequestMapping(value = { "/dockerfileTask" }, method = RequestMethod.GET)
	public String dockerfileTask() {
		return "bcm/ci/dockerfileTask";
	}

	/**
	 * 创建构建任务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "" }, method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ApiResult createCi(String ciModelString, @RequestParam("dockerFile") MultipartFile dockerFile,
			HttpServletRequest request) throws Exception {
		try {
			SwCreateCiModel ciModel = JSON.toJavaObject(JSONObject.parseObject(ciModelString), SwCreateCiModel.class);
			String ftpPath = this.getUploadPath();
			String localPath = request.getServletContext().getRealPath("/temp/" + ftpPath + "/");
			if (this.uploadFile(localPath, ftpPath, dockerFile)) {
				CiClient client = new CiClient(this.bcmUrl, new RestFactory());
				ciModel.setTenantName(CurrentUserUtils.getInstance().getUser().getTenantId());
				ciModel.setCreatedBy(CurrentUserUtils.getInstance().getUser().getLoginId());
				ciModel.setFileName(dockerFile.getOriginalFilename());
				ciModel.setFilePath(JSON.toJSONString(this.getSWCiFileModel(ftpPath)));
				ApiResult result = client.createCi(ciModel);
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

	public SWCiFileModel getSWCiFileModel(String path) {
		SWCiFileModel swCiFileModel = new SWCiFileModel();
		swCiFileModel.setFilePath(path);
		swCiFileModel.setFtpHost(ftpConfigProperties.getHost());
		swCiFileModel.setFtpPort(ftpConfigProperties.getPort());
		swCiFileModel.setFtpUser(ftpConfigProperties.getUsername());
		swCiFileModel.setFtpPass(ftpConfigProperties.getPassword());
		return swCiFileModel;
	}

	public boolean uploadFile(String localPath, String ftpPath, MultipartFile dockerFile) {
		File file = new File(localPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		File uploadFile = new File(localPath + File.separatorChar + dockerFile.getOriginalFilename());
		try {
			dockerFile.transferTo(uploadFile);
			return FtpUtil.uploadFile(uploadFile, ftpPath);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			uploadFile.delete();
			file.delete();
		}
		return false;
	}

	public String getUploadPath() {
		StringBuffer buff = new StringBuffer(Dictionary.BCMConstant.CIFILEPRE.value);
		buff.append("/").append(CurrentUserUtils.getInstance().getUser().getTenantId());
		buff.append("/").append(StringUtil.getUUID().substring(0, 8)).append("/");
		return buff.toString();
	}

	/**
	 * 启动、停止构建
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/{ciId}" }, method = RequestMethod.PUT)
	@ResponseBody
	public ApiResult operateCi(@PathVariable("ciId") String ciId, @RequestBody SwUpdateCiModel ciModel)
			throws Exception {
		try {

			CiClient client = new CiClient(this.bcmUrl, new RestFactory());
			ciModel.setCreatedBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			ApiResult result = client.operateCi(ciId, ciModel);

			return result;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 构建任务操作 （使用PUT方法参数接不到，暂时用POST）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/{ciId}" }, method = RequestMethod.POST)
	@ResponseBody
	public ApiResult editCi(@PathVariable("ciId") String ciId, @RequestParam String ciModelString,
			@RequestParam(required = false) MultipartFile dockerFile, HttpServletRequest request) throws Exception {
		try {
			SwUpdateCiModel ciModel = JSON.toJavaObject(JSONObject.parseObject(ciModelString), SwUpdateCiModel.class);
			ciModel.setOperator("modify");
			CiClient client = new CiClient(this.bcmUrl, new RestFactory());

			String deleteFile = null;
			if (dockerFile != null) {
				// 上传了文件
				String ftpPath = this.getUploadPath();
				String localPath = request.getServletContext().getRealPath("/temp" + ftpPath + "/");
				if (this.uploadFile(localPath, ftpPath, dockerFile)) {
					SWCiFileModel fileModel = JSON.toJavaObject(JSONObject.parseObject(ciModel.getFilePath()),
							SWCiFileModel.class);
					deleteFile = fileModel.getFilePath();
					ciModel.setFilePath(JSON.toJSONString(this.getSWCiFileModel(ftpPath)));
					ciModel.setFileName(dockerFile.getOriginalFilename());
				} else {
					return new ApiResult(Dictionary.HttpStatus.SERVER_INTERVAL_ERROR.value, "上传失败");
				}
			}

			ciModel.setCreatedBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			ApiResult result = client.operateCi(ciId, ciModel);
			if (result.getCode() == Dictionary.HttpStatus.OK.value && StringUtil.isNotEmpty(deleteFile)) {
				FtpUtil.deleteDir(deleteFile);
			}

			return result;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 获取构建任务列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getCiList(@RequestParam String ciName, @RequestParam Integer ciType, @RequestParam Integer page,
			@RequestParam Integer size, @RequestParam String projectId) throws Exception {
		try {
			CiClient client = new CiClient(this.bcmUrl, new RestFactory());
			return client.getCi(CurrentUserUtils.getInstance().getUser().getTenantId(), ciName, ciType, page, size,
					projectId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 获取构建任务详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/{ciId}" }, method = RequestMethod.GET)
	@ResponseBody
	public ApiResult single(@PathVariable("ciId") String ciId) throws Exception {
		try {
			CiClient client = new CiClient(this.bcmUrl, new RestFactory());
			return client.single(ciId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 删除构建任务
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/{ciId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public ApiResult deleteCi(@PathVariable("ciId") String ciId) throws Exception {
		try {
			CiClient client = new CiClient(this.bcmUrl, new RestFactory());
			ApiResult cacheObj = client.single(ciId);
			ApiResult result = client.deleteCi(ciId);
			if (result.getCode() != Dictionary.HttpStatus.OK.value) {
				try {
					Map map1 = (Map) (cacheObj.getData());// 得到根节点
					Map map2 = (Map) (map1.get("ciFile"));
					Map map3 = JSON.toJavaObject(JSONObject.parseObject((String) map2.get("filePath")), Map.class);
					FtpUtil.deleteDir((String) (map3.get("filePath")));
				} catch (Exception e) {
					LOG.error("删除id为" + ciId + "的构建ftp文件失败");
				}
			}
			return result;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 创建dockerfile模板
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/dockerfile" }, method = RequestMethod.POST)
	@ResponseBody
	public ApiResult createDockerfile(@RequestBody SwCreateDockerfileTemplateModel obj) throws Exception {
		try {
			CiClient client = new CiClient(this.bcmUrl, new RestFactory());
			obj.setTenantName(CurrentUserUtils.getInstance().getUser().getTenantId());
			obj.setCreateBy(CurrentUserUtils.getInstance().getUser().getLoginId());
			return client.createDockerfile(obj);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 获取dockerfile模板列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/dockerfile" }, method = RequestMethod.GET)
	@ResponseBody
	public ApiResult getDockerfile(@RequestParam String projectId, @RequestParam int page, @RequestParam int size)
			throws Exception {
		try {
			CiClient client = new CiClient(this.bcmUrl, new RestFactory());
			return client.getDockerfile(CurrentUserUtils.getInstance().getUser().getTenantId(), projectId, page, size);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 获取dockerfile模板
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/dockerfile/{dockerfileId}" }, method = RequestMethod.GET)
	@ResponseBody
	public ApiResult singleDockerfile(@PathVariable String dockerfileId) throws Exception {
		try {
			CiClient client = new CiClient(this.bcmUrl, new RestFactory());
			return client.singleDockerfile(dockerfileId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 删除dockerfile模板
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/dockerfile/{dockerfileId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public ApiResult deleteDockerfile(@PathVariable("dockerfileId") String dockerfileId) throws Exception {
		try {
			CiClient client = new CiClient(this.bcmUrl, new RestFactory());
			return client.deleteDockerfile(dockerfileId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 修改dockerfile模板
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/dockerfile/{dockerfileId}" }, method = RequestMethod.PUT)
	@ResponseBody
	public ApiResult updateDockerfile(@PathVariable String dockerfileId,
			@RequestBody SwUpdateDockerfileTemplateModel obj) throws Exception {
		try {
			CiClient client = new CiClient(this.bcmUrl, new RestFactory());
			return client.updateDockerfile(dockerfileId, obj);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 删除构建记录
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/record/{ciRecordId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public ApiResult deleteCiRecords(@PathVariable(value = "ciRecordId") String ciRecordId) throws Exception {
		try {
			CiClient client = new CiClient(this.bcmUrl, new RestFactory());
			return client.deleteCiRecords(ciRecordId);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 获取构建记录分页列表
	 * 
	 * @param ciId
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/{ciId}/record" }, method = RequestMethod.GET)
	public ApiResult getCiRecords(@PathVariable(value = "ciId") String ciId, @RequestParam("page") int page,
			@RequestParam("size") int size) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.getCiRecords(ciId, page, size);
	}

	/**
	 * 获取构建语言版本信息
	 * 
	 * @param langType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/lang" }, method = RequestMethod.GET)
	public ApiResult getCiLang(String langType) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.getCiLang(langType);
	}

	/**
	 * 获取shera工具版本信息 Shear工具类型 1:maven|2:ant|3:sonar
	 * 
	 * @param execConfigType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/shera" }, method = RequestMethod.GET)
	public ApiResult getCiShera(@RequestParam int execConfigType) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.getCiShera(execConfigType);
	}

	/**
	 * 添加认证
	 * 
	 * @param credential
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/code/credentials" }, method = RequestMethod.POST)
	public ApiResult createCredentials(@RequestBody SwCreateCredentialsModel credential) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.createCredentials(credential);
	}

	/**
	 * 获取认证列表
	 * 
	 * @param tenantName
	 * @param projectId
	 * @param codeControlType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/code/credentials" }, method = RequestMethod.GET)
	public ApiResult getCredentials(@RequestParam(value = "projectId", required = false) String projectId,
			@RequestParam(value = "codeControlType", required = true) byte codeControlType) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.getCredentials(CurrentUserUtils.getInstance().getUser().getTenantId(), projectId,
				codeControlType);
	}

	/**
	 * 获取认证详情
	 * 
	 * @param credentialsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/code/credentials/{credentialsId}" }, method = RequestMethod.GET)
	public ApiResult getCredentialsDetail(@PathVariable(value = "credentialsId") String credentialsId) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.getCredentialsDetail(credentialsId);
	}

	/**
	 * 删除认证
	 */
	@ResponseBody
	@RequestMapping(value = { "/code/credentials/{credentialsId}" }, method = RequestMethod.DELETE)
	public ApiResult deleteCredentials(@PathVariable(value = "credentialsId") String credentialsId) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.deleteCredentials(credentialsId);
	}

	/**
	 * 获取github项目
	 * 
	 * @param credentialsId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/code/credentials/github/repos" }, method = RequestMethod.GET)
	public ApiResult getGitHubRepos(@RequestParam(value = "credentialsId", required = true) String credentialsId) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.getGitHubRepos(credentialsId);
	}

	/**
	 * 获取github项目分支
	 * 
	 * @param credentialsId
	 * @param reposName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/code/credentials/github/repos/branch" }, method = RequestMethod.GET)
	public ApiResult getGitHubBranches(@RequestParam(value = "credentialsId", required = true) String credentialsId,
			@RequestParam(value = "reposName", required = true) String reposName) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.getGitHubBranches(credentialsId, reposName);
	}

	/**
	 * 获取gitlab项目
	 */
	@ResponseBody
	@RequestMapping(value = { "/code/credentials/gitlab/repos" }, method = RequestMethod.GET)
	public ApiResult getGitlabRepos(@RequestParam(value = "credentialsId", required = true) String credentialsId) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.getGitlabRepos(credentialsId);
	}

	/**
	 * 获取gitlab项目分支
	 * 
	 * @param credentialsId 认证Id
	 * @param reposId       git项目Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/code/credentials/gitlab/repos/branch" }, method = RequestMethod.GET)
	public ApiResult getGitlabBranches(@RequestParam(value = "credentialsId", required = true) String credentialsId,
			@RequestParam(value = "reposId", required = true) int reposId) {
		CiClient client = new CiClient(this.bcmUrl, new RestFactory());
		return client.getGitlabBranches(credentialsId, reposId);
	}
}
