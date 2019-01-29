package com.sdp.bcm.model;


/**
 * 修改Dockerfile模型参数
 * 
 */
public class SwUpdateDockerfileTemplateModel {

	/**
	 * Dockerfile文件内容
	 */
    private String dockerfileContent;

    public String getDockerfileContent() {
        return dockerfileContent;
    }

    public void setDockerfileContent(String dockerfileContent) {
        this.dockerfileContent = dockerfileContent;
    }

}
