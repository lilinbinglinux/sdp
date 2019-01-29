package com.sdp.frame.web.entity.tenant;

import java.math.BigDecimal;

public class Tenant {

	    private String tenantId;

	    private String tenantName;

	    private String state;

	    private String memo;

	    private BigDecimal ord;

		public String getTenantId() {
			return tenantId;
		}

		public void setTenantId(String tenantId) {
			this.tenantId = tenantId;
		}

		public String getTenantName() {
			return tenantName;
		}

		public void setTenantName(String tenantName) {
			this.tenantName = tenantName;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}


		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public BigDecimal getOrd() {
			return ord;
		}

		public void setOrd(BigDecimal ord) {
			this.ord = ord;
		}

	    }
