package com.sdp.bcm.entity;

/**
 * 上传镜像接口参数
 * 
 */
public class UploadImageModel {

	private String tenantName;

	private byte imageType;// 1公用2私有

	private String imageName;

	private String description;

	private String imageVersion;

	private String imageFilePath;

	private String createdBy;

	private String projectId;

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public byte getImageType() {
		return imageType;
	}

	public void setImageType(byte imageType) {
		this.imageType = imageType;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageVersion() {
		return imageVersion;
	}

	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Override
	public String toString() {
		return "UploadImageModel [tenantName=" + tenantName + ", imageType=" + imageType + ", imageName=" + imageName
				+ ", description=" + description + ", imageVersion=" + imageVersion + ", imageFilePath=" + imageFilePath
				+ "]";
	}

}
