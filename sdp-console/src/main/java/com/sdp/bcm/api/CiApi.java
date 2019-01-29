package com.sdp.bcm.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.model.SwCreateCiModel;
import com.sdp.bcm.model.SwCreateCredentialsModel;
import com.sdp.bcm.model.SwCreateDockerfileTemplateModel;
import com.sdp.bcm.model.SwUpdateCiModel;
import com.sdp.bcm.model.SwUpdateDockerfileTemplateModel;
import com.sdp.common.entity.NetClientException;

public interface CiApi {

	/**
	 * 创建构建任务
	 * 
	 * @param ci
	 * @return
	 * @throws NetClientException
	 */
	@POST
	@Path("/v1/ci")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult createCi(SwCreateCiModel ci) throws NetClientException;

	/**
	 * 获取构建任务列表
	 * 
	 * @param tenantName
	 * @param ciName 
	 * @param page
	 * @param size
	 * @param type 
	 * @param projectId
	 * @return
	 * @throws NetClientException
	 */
	@GET
	@Path("/v1/ci")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getCi(@QueryParam("tenantName") String tenantName, @QueryParam("ciName") String ciName, @QueryParam("ciType") Integer ciType, @QueryParam("page") Integer page,
			@QueryParam("size") Integer size, @QueryParam("projectId") String projectId) throws NetClientException;

	/**
	 * 获取构建任务详情
	 * 
	 * @param tenantName
	 * @param page
	 * @param size
	 * @return
	 * @throws NetClientException
	 */
	@GET
	@Path("/v1/ci/{ciId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult single(@PathParam("ciId") String ciId) throws NetClientException;

	/**
	 * 删除构建任务
	 */
	@DELETE
	@Path("/v1/ci/{ciId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult deleteCi(@PathParam("ciId") String ciId) throws NetClientException;

	/**
	 * 构建任务操作：启动，停止，修改
	 * 
	 * @param ciModel
	 */
	@PUT
	@Path("/v1/ci/{ciId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult operateCi(@PathParam("ciId") String ciId, SwUpdateCiModel ciModel) throws NetClientException;

	/**
	 * 创建dockerfile模板
	 * 
	 * @param obj
	 * @return
	 * @throws NetClientException
	 */
	@POST
	@Path("/v1/ci/dockerfile")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult createDockerfile(SwCreateDockerfileTemplateModel obj) throws NetClientException;

	/**
	 * 获取dockerfile模板列表
	 * 
	 * @param tenantName
	 * @param projectId 
	 * @param page
	 * @param size
	 * @return
	 * @throws NetClientException
	 */
	@GET
	@Path("/v1/ci/dockerfile")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getDockerfile(@QueryParam("tenantName") String tenantName, String projectId, @QueryParam("page") Integer page,
			@QueryParam("size") Integer size) throws NetClientException;

	/**
	 * 获取dockerfile模板
	 * 
	 * @param tenantName
	 * @param page
	 * @param size
	 * @return
	 * @throws NetClientException
	 */
	@GET
	@Path("/v1/ci/dockerfile/{dockerfileId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult singleDockerfile(@PathParam("dockerfileId") String dockerfileId) throws NetClientException;

	/**
	 * 删除dockerfile模板
	 */
	@DELETE
	@Path("/v1/ci/dockerfile/{dockerfileId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult deleteDockerfile(@PathParam("dockerfileId") String dockerfileId) throws NetClientException;

	/**
	 * 更新dockerfile模板
	 */
	@PUT
	@Path("/v1/ci/dockerfile/{dockerfileId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult updateDockerfile(@PathParam("dockerfileId") String dockerfileId,
			SwUpdateDockerfileTemplateModel obj) throws NetClientException;

	/**
	 * 删除构建记录
	 */
	@DELETE
	@Path("/v1/ci/record/{ciRecordId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult deleteCiRecords(@PathParam("ciRecordId") String ciRecordId);

	
	/**
	 * 获取构建记录分页列表
	 * @param ciId
	 * @param page
	 * @param size
	 * @return
	 */
	@GET
	@Path("/v1/ci/{ciId}/record")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getCiRecords(@PathParam("ciId")String ciId, @QueryParam("page") int page, @QueryParam("size") int size);

	/**
	 * 获取构建语言版本信息
	 * @param langType 语言类型 java|go|python
	 * @return
	 */
	@GET
	@Path("/v1/ci/lang")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getCiLang(@QueryParam("langType") String langType);

	/**
	 * 获取shera工具版本信息
	 * @param execConfigType Shear工具类型 1:maven|2:ant|3:sonar
	 * @return
	 */
	@GET
	@Path("/v1/ci/shera")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getCiShera(@QueryParam("execConfigType") int execConfigType);

	/**
	 * 添加认证
	 * @param credential
	 * @return
	 */
	@POST
	@Path("/v1/ci/code/credentials")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult createCredentials(SwCreateCredentialsModel credential);

	/**
	 * 获取认证列表
	 * @param tenantId
	 * @param projectId
	 * @param codeControlType 代码托管工具,1:gitlab|2:svn|3:github
	 * @return
	 */
	@GET
	@Path("/v1/ci/code/credentials")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getCredentials(@QueryParam("tenantId") String tenantId, @QueryParam("projectId") String projectId, @QueryParam("codeControlType") byte codeControlType);

	/**
	 * 获取认证详情
	 * @param credentialsId
	 * @return
	 */
	@GET
	@Path("/v1/ci/code/credentials/{credentialsId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getCredentialsDetail(@PathParam("credentialsId") String credentialsId);

	/**
	 * 删除认证
	 * @param credentialsId
	 * @return
	 */
	@DELETE
	@Path("/v1/ci/code/credentials/{credentialsId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult deleteCredentials(@PathParam("credentialsId") String credentialsId);

	/**
	 * 获取github项目
	 * @param credentialsId
	 * @return
	 */
	@GET
	@Path("/v1/ci/code/credentials/github/repos")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getGitHubRepos(@QueryParam("credentialsId") String credentialsId);

	/**
	 * 获取github项目分支
	 * @param credentialsId
	 * @param reposName
	 * @return
	 */
	@GET
	@Path("/v1/ci/code/credentials/github/repos/branch")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getGitHubBranches(@QueryParam("reposName") String credentialsId, @QueryParam("reposName") String reposName);

	/**
	 * 获取gitlab项目
	 * @param credentialsId
	 * @return
	 */
	@GET
	@Path("/v1/ci/code/credentials/gitlab/repos")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getGitlabRepos(@QueryParam("credentialsId") String credentialsId);

	/**
	 * 获取gitlab项目分支
	 * @param credentialsId 认证Id
	 * @param reposId git项目Id
	 * @return
	 */
	@GET
	@Path("/v1/ci/code/credentials/gitlab/repos/branch")
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiResult getGitlabBranches(@QueryParam("credentialsId") String credentialsId, @QueryParam("reposId") int reposId);
}
