/**
 * 
 */
package com.sdp.serviceAccess.entity;

import java.util.List;

/**   
* Copyright: Copyright (c) 2018 BONC
* 
* @ClassName: PProductNode.java
* @Description: 节点类
*
* @version: v1.0.0
* @author: renpengyuan
* @date: 2018年8月2日 下午3:34:03 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月2日     renpengyuan      v1.0.0               修改原因
* 2018年8月8日     renpengyuan      v1.0.1             增加属性映射字段
*/
public class PProductNode extends BaseInfo{
    
	private String nodeId;
	
	private String caseId;
	
	private String caseAttr;
	
	private String caseStatus;
	
	private List<String> caseIds ;
	
	public static String NODESTATE = "nodeState";//节点状态属性key值
	
	public List<String> getCaseIds() {
		return caseIds;
	}

	public void setCaseIds(List<String> caseIds) {
		this.caseIds = caseIds;
	}

	private List<ProductFieldItem> caseAttrOrm;

	public List<ProductFieldItem> getCaseAttrOrm() {
		return caseAttrOrm;
	}

	public void setCaseAttrOrm(List<ProductFieldItem> caseAttrOrm) {
		this.caseAttrOrm = caseAttrOrm;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getCaseAttr() {
		return caseAttr;
	}

	public void setCaseAttr(String caseAttr) {
		this.caseAttr = caseAttr;
	}

	public String getCaseStatus() {
		return caseStatus;
	}

	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	
}
