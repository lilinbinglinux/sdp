package com.sdp.bcm.model;

/**
 * 创建构建任务参数
 * 
 */
public class SwCreateCiModel {

	private String tenantName;
	/**
	 * 构建类型，1代码构建 2DockerFile构建
	 */
	private Byte ciType;

	private String ciName;

	private String imageName;

	private String imageVersion;

	/**
	 * 镜像版本前缀
	 */
	private String imageVersionPre;

	private String ciDescription;

	private String cron;

	private String cronDescription;

	private String filePath;

	private String dockerfileContent;

	private String fileName;

	/**
	 * 代码地址
	 */
	private String codeUrl;

	/**
	 * 代码项目名称
	 */
	private String reposName;

	/**
	 * 代码项目Id
	 */
	private Integer reposId;

	/**
	 * 分支
	 */
	private String codeBranch;

	/**
	 * 代码库类型，0:none 1:git 2:svn
	 */
	private Byte codeControlType;

	/**
	 * 认证方式ID
	 */
	private String ciCodeCredentialsId;

	/**
	 * 代码构建dockerfile路径
	 */
	private String dockerfilePath;

	/**
	 * 代码构建语言类型
	 */
	private String lang;

	/**
	 * 代码构建编译命令
	 */
	private String compile;

	private String projectId;

	private String createdBy;

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public Byte getCiType() {
		return ciType;
	}

	public void setCiType(Byte ciType) {
		this.ciType = ciType;
	}

	public String getCiName() {
		return ciName;
	}

	public void setCiName(String ciName) {
		this.ciName = ciName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageVersion() {
		return imageVersion;
	}

	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}

	public String getImageVersionPre() {
		return imageVersionPre;
	}

	public void setImageVersionPre(String imageVersionPre) {
		this.imageVersionPre = imageVersionPre;
	}

	public String getCiDescription() {
		return ciDescription;
	}

	public void setCiDescription(String ciDescription) {
		this.ciDescription = ciDescription;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getCronDescription() {
		return cronDescription;
	}

	public void setCronDescription(String cronDescription) {
		this.cronDescription = cronDescription;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getDockerfileContent() {
		return dockerfileContent;
	}

	public void setDockerfileContent(String dockerfileContent) {
		this.dockerfileContent = dockerfileContent;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public String getReposName() {
		return reposName;
	}

	public void setReposName(String reposName) {
		this.reposName = reposName;
	}

	public Integer getReposId() {
		return reposId;
	}

	public void setReposId(Integer reposId) {
		this.reposId = reposId;
	}

	public String getCodeBranch() {
		return codeBranch;
	}

	public void setCodeBranch(String codeBranch) {
		this.codeBranch = codeBranch;
	}

	public Byte getCodeControlType() {
		return codeControlType;
	}

	public void setCodeControlType(Byte codeControlType) {
		this.codeControlType = codeControlType;
	}

	public String getCiCodeCredentialsId() {
		return ciCodeCredentialsId;
	}

	public void setCiCodeCredentialsId(String ciCodeCredentialsId) {
		this.ciCodeCredentialsId = ciCodeCredentialsId;
	}

	public String getDockerfilePath() {
		return dockerfilePath;
	}

	public void setDockerfilePath(String dockerfilePath) {
		this.dockerfilePath = dockerfilePath;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getCompile() {
		return compile;
	}

	public void setCompile(String compile) {
		this.compile = compile;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
