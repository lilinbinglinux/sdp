package com.sdp.serviceAccess.entity;

/**
 * 
* @Description: 订购方式信息
  @ClassName: POrderWays
* @author zy
* @date 2018年8月9日
* @company:www.sdp.com.cn
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月9日     zy      v1.0.0               修改原因
 */
public class POrderWays extends BaseInfo{
	/**
	 * 主键
	 */
	private String pwaysId;
	
	/**
	 * 订购方式名称
	 */
	private String pwaysName;
	
	/**
	 * 编码（approve 审批，pay 付费，free 免费）
	 */
	private String pwaysCode;
	
	/**
	 * 订购类型，参看数据字典 Dictionary.Ways.type
	 */
	private String pwaysType;
	
	private String pwaysConfig;
	
	private String pwaysRemark;

	public String getPwaysId() {
		return pwaysId;
	}

	public void setPwaysId(String pwaysId) {
		this.pwaysId = pwaysId;
	}

	public String getPwaysName() {
		return pwaysName;
	}

	public void setPwaysName(String pwaysName) {
		this.pwaysName = pwaysName;
	}

	public String getPwaysCode() {
		return pwaysCode;
	}

	public void setPwaysCode(String pwaysCode) {
		this.pwaysCode = pwaysCode;
	}

	public String getPwaysType() {
		return pwaysType;
	}

	public void setPwaysType(String pwaysType) {
		this.pwaysType = pwaysType;
	}

	public String getPwaysconfig() {
		return pwaysConfig;
	}

	public void setPwaysconfig(String pwaysConfig) {
		this.pwaysConfig = pwaysConfig;
	}

	public String getPwaysRemark() {
		return pwaysRemark;
	}

	public void setPwaysRemark(String pwaysRemark) {
		this.pwaysRemark = pwaysRemark;
	}

	public POrderWays() {
		super();
	}

	public POrderWays(String pwaysId) {
		super();
		this.pwaysId = pwaysId;
	}
	
}
