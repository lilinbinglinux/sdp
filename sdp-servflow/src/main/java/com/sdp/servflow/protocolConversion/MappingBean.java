package com.sdp.servflow.protocolConversion;

public class MappingBean {

	// 源路径
	String sourPath;
	// 目标路径
	String targetPath;
	// 源xml元素节点的属性名称
	String sourceAttrName;
	// 目标xml元素节点的属性名称
	String targetAttrName;
	// 数据类型
	String dataType;
	// 节点值，赋值时用
	Object value;

	public MappingBean(String sourPath, String targetPath, String sourceAttrName, String targetAttrName,
			String dataType) {
		super();
		this.sourPath = sourPath;
		this.targetPath = targetPath;
		this.sourceAttrName = sourceAttrName;
		this.targetAttrName = targetAttrName;
		this.dataType = dataType;
	}

	public MappingBean(String sourPath, String targetPath) {
		super();
		this.sourPath = sourPath;
		this.targetPath = targetPath;
	}

	public String getSourceAttrName() {
		return sourceAttrName;
	}

	public void setSourceAttrName(String sourceAttrName) {
		this.sourceAttrName = sourceAttrName;
	}

	public String getTargetAttrName() {
		return targetAttrName;
	}

	public void setTargetAttrName(String targetAttrName) {
		this.targetAttrName = targetAttrName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getSourPath() {
		return sourPath;
	}

	public void setSourPath(String sourPath) {
		this.sourPath = sourPath;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

    @Override
    public String toString() {
        return "MappingBean [sourPath=" + sourPath + ", targetPath=" + targetPath
               + ", sourceAttrName=" + sourceAttrName + ", targetAttrName=" + targetAttrName
               + ", dataType=" + dataType + ", value=" + value + "]";
    }
	
}
