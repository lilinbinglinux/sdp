package com.sdp.servflow.form.entity;

import java.math.BigDecimal;
import java.util.Date;

public class UdField {
	
	private String id;
	private String templatecode;
	private String fieldename;
	private String fieldcname;
	private String fieldsel;
	private String fielduse;
	private String fieldtype;//字段类型  1 是  字符型  2 大字符型      3数字型    4日期   5 部门  6是用户
	// 7 是 码表   8流程意见 10  取值 11 联动取值（主）  12 联动取值（辅） 13  联动枚举  14 复选类型  15 时间
	private String datatype;
	private String defaultvalue;
	private String datalenth;
	private String mathrule;
	private String delflag;
	private String fieldcode;
	private String fielfashion;
	private String dataDecimal;
	private Date subtime;
	private BigDecimal need;
	private String afterField;
	private String gltable;
	private String glfield;
	private Long sortid;
	private String mainflag;
	private String ztablename;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplatecode() {
		return templatecode;
	}
	public void setTemplatecode(String templatecode) {
		this.templatecode = templatecode;
	}
	public String getFieldename() {
		return fieldename;
	}
	public void setFieldename(String fieldename) {
		this.fieldename = fieldename;
	}
	public String getFieldcname() {
		return fieldcname;
	}
	public void setFieldcname(String fieldcname) {
		this.fieldcname = fieldcname;
	}
	public String getFieldsel() {
		return fieldsel;
	}
	public void setFieldsel(String fieldsel) {
		this.fieldsel = fieldsel;
	}
	public String getFielduse() {
		return fielduse;
	}
	public void setFielduse(String fielduse) {
		this.fielduse = fielduse;
	}
	public String getFieldtype() {
		return fieldtype;
	}
	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getDefaultvalue() {
		return defaultvalue;
	}
	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}
	public String getDatalenth() {
		return datalenth;
	}
	public void setDatalenth(String datalenth) {
		this.datalenth = datalenth;
	}
	public String getMathrule() {
		return mathrule;
	}
	public void setMathrule(String mathrule) {
		this.mathrule = mathrule;
	}
	public String getDelflag() {
		return delflag;
	}
	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}
	public String getFieldcode() {
		return fieldcode;
	}
	public void setFieldcode(String fieldcode) {
		this.fieldcode = fieldcode;
	}
	public String getFielfashion() {
		return fielfashion;
	}
	public void setFielfashion(String fielfashion) {
		this.fielfashion = fielfashion;
	}
	public String getDataDecimal() {
		return dataDecimal;
	}
	public void setDataDecimal(String dataDecimal) {
		this.dataDecimal = dataDecimal;
	}
	public Date getSubtime() {
		return subtime;
	}
	public void setSubtime(Date subtime) {
		this.subtime = subtime;
	}
	public BigDecimal getNeed() {
		return need;
	}
	public void setNeed(BigDecimal need) {
		this.need = need;
	}
	public String getAfterField() {
		return afterField;
	}
	public void setAfterField(String afterField) {
		this.afterField = afterField;
	}
	public String getGltable() {
		return gltable;
	}
	public void setGltable(String gltable) {
		this.gltable = gltable;
	}
	public String getGlfield() {
		return glfield;
	}
	public void setGlfield(String glfield) {
		this.glfield = glfield;
	}
	public Long getSortid() {
		return sortid;
	}
	public void setSortid(Long sortid) {
		this.sortid = sortid;
	}
	public String getMainflag() {
		return mainflag;
	}
	public void setMainflag(String mainflag) {
		this.mainflag = mainflag;
	}
	public String getZtablename() {
		return ztablename;
	}
	public void setZtablename(String ztablename) {
		this.ztablename = ztablename;
	}
}
