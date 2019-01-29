package com.sdp.bcm.model;

/**
 * 创建构建任务参数
 * 修改任务模型
 */
public class SwUpdateCiModel {

	/**
	 * 操作，start|stop|modify
	 */
    private String operator;

    /**
     * 构建任务描述
     */
    private String ciDescription;

    // 构建计划
    /**
     * 执行计划表达式
     */
    private String cron;

    /**
     * 执行计划描述
     */
    private String cronDescription;

    // 文件
    /**
     * dockerfile构建上传文件的位置
     */
    private String filePath;

    /**
     * dockerfile文件内容
     */
    private String dockerfileContent;

    /**
     * 构建名称
     */
    private String ciName;
    
    /**
     * 镜像名称
     */
    private String imageName;
    
    /**
     * 镜像版本
     */
    private String imageVersion;
    
    /**
     * 资源名(dockerfile构建上传文件的名字, 多个文件逗号分隔)
     */
    private String fileName;

    // 代码构建
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
     * 代码库类型
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
     * 代码构建编译命令
     */
    private String compile;

    /**
     * 创建者
     */
    private String createdBy;
    
    public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public String getCompile() {
        return compile;
    }

    public void setCompile(String compile) {
        this.compile = compile;
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

}
