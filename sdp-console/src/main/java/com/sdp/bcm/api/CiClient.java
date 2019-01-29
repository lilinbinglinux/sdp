package com.sdp.bcm.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sdp.bcm.common.ApiResult;
import com.sdp.bcm.model.SwCreateCiModel;
import com.sdp.bcm.model.SwCreateCredentialsModel;
import com.sdp.bcm.model.SwCreateDockerfileTemplateModel;
import com.sdp.bcm.model.SwUpdateCiModel;
import com.sdp.bcm.model.SwUpdateDockerfileTemplateModel;
import com.sdp.bcm.utils.RestFactory;
import com.sdp.common.entity.NetClientException;


public class CiClient {
	private static final Log LOG = LogFactory.getLog(CiClient.class);

	private CiApi api;
	
	public CiClient(String url, RestFactory factory) {
		this.api = factory.creatCiApiClient(url);
	}
	
	public ApiResult createCi(SwCreateCiModel ci) throws NetClientException{
		return api.createCi(ci);
	}
	
	public ApiResult getCi(String tenantName, String ciName, Integer ciType, Integer page, Integer size, String projectId) throws NetClientException {
		return api.getCi(tenantName, ciName, ciType, page, size, projectId);
	}
	
	public ApiResult single(String ciId) throws NetClientException {
		return api.single(ciId);
	}
	
	public ApiResult deleteCi(String ciId) throws NetClientException {
		return api.deleteCi(ciId);
	}
	
	public ApiResult operateCi(String ciId, SwUpdateCiModel ciModel) throws NetClientException {
		return api.operateCi(ciId, ciModel);
	}
	
	public ApiResult createDockerfile(SwCreateDockerfileTemplateModel obj) throws NetClientException {
		return api.createDockerfile(obj);
	}
	
	public ApiResult getDockerfile(String tenantName, String projectId, Integer page, Integer size) throws NetClientException {
		return api.getDockerfile(tenantName, projectId, page, size);
	}
	
	public ApiResult singleDockerfile(String dockerfileId) throws NetClientException {
		return api.singleDockerfile(dockerfileId);
	}
	
	public ApiResult deleteDockerfile(String dockerfileId) throws NetClientException {
		return api.deleteDockerfile(dockerfileId);
	}
	
	public ApiResult deleteCiRecords(String ciRecordId) throws NetClientException {
		return api.deleteCiRecords(ciRecordId);
	}
	
	public ApiResult updateDockerfile(String dockerfileId, SwUpdateDockerfileTemplateModel obj) throws NetClientException {
		return api.updateDockerfile(dockerfileId, obj);
	}

	public ApiResult getCiRecords(String ciId, int page, int size) {
		return api.getCiRecords(ciId, page, size);
	}

	public ApiResult getCiLang(String langType) {
		return api.getCiLang(langType);
	}

	public ApiResult getCiShera(int execConfigType) {
		return api.getCiShera(execConfigType);
	}

	public ApiResult createCredentials(SwCreateCredentialsModel credential) {
		return api.createCredentials(credential);
	}

	public ApiResult getCredentials(String tenantId, String projectId, byte codeControlType) {
		return api.getCredentials(tenantId, projectId, codeControlType);
	}

	public ApiResult getCredentialsDetail(String credentialsId) {
		return api.getCredentialsDetail(credentialsId);
	}

	public ApiResult deleteCredentials(String credentialsId) {
		return api.deleteCredentials(credentialsId);
	}

	public ApiResult getGitHubRepos(String credentialsId) {
		return api.getGitHubRepos(credentialsId);
	}

	public ApiResult getGitHubBranches(String credentialsId, String reposName) {
		return api.getGitHubBranches(credentialsId, reposName);
	}

	public ApiResult getGitlabRepos(String credentialsId) {
		return api.getGitlabRepos(credentialsId);
	}

	public ApiResult getGitlabBranches(String credentialsId, int reposId) {
		return api.getGitlabBranches(credentialsId, reposId);
	}
}
