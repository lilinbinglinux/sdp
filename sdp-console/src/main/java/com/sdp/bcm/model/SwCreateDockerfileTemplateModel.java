package com.sdp.bcm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 创建Dockerfile模型参数
 * 
 */
public class SwCreateDockerfileTemplateModel {

	/**
	 * 租户名称
	 */
	private String tenantName;

	/**
	 * Dockerfile文件内容
	 */
	private String dockerfileContent;

	/**
	 * Dockerfile文件名称
	 */
	private String dockerfileName;

	/**
	 * 项目Id
	 */
	private String projectId;

	/**
	 * 创建者
	 */
	private String createBy;

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getDockerfileContent() {
		return dockerfileContent;
	}

	public void setDockerfileContent(String dockerfileContent) {
		this.dockerfileContent = dockerfileContent;
	}

	public String getDockerfileName() {
		return dockerfileName;
	}

	public void setDockerfileName(String dockerfileName) {
		this.dockerfileName = dockerfileName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

}
