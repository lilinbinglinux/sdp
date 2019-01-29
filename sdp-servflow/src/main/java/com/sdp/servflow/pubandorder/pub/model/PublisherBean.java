package com.sdp.servflow.pubandorder.pub.model;


import java.util.Date;


/**
 * 服务注册接口
 *
 * @author ZY
 * @version 2017年5月17日
 * @see InterfaceRes
 * @since
 */
public class PublisherBean {

    /**
     * 主键
     */
    private String pubid;

    /**
     * 名称
     */
    private String name;

    /**
     * 服务接口
     */
    private String url;

    /**
     * 服务端口
     */
    private String pubport;

    /**
     * 服务协议
     */
    private String pubprotocal;

    /**
     * 请求方式
     */
    private String method;
    
    /**
     * 请求格式
     */
    private String reqformat;
    
    /**
     * 响应格式
     */
    private String respformat;
    

    /**
     * 请求值类型（Object或List）
     */
    private String reqdatatype;

    /**
     * 返回值类型（Object或List）
     */
    private String returndatatype;

    /**
     * 描述
     */
    private String pubdesc;

    /**
     * 创建日期
     */
    private Date createdate;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 类型(0为项目 1为模块 2为api)
     */
    private String typeId;

    /**
     * 登录用户login_id
     */
    private String login_id;

    /**
     * 租户id
     */
    private String tenant_id;
    
    /**
     * 测试数据
     */
    private String sampledata;
    
    /**
     * 审核状态(未审批000，审批中001，审批通过100，审批不通过999)
     */
    private String checkstatus;
    
    /**
     * 调用方法所属于的类名
     */
    private String className;
    
    /**
     * 方法名称
     */
    private String methodInClass;
    
    /**
     * 文件路径
     */
    private String filePath;
    
    /**
     * api类别编码（api为001,docker为002,openstack为003）
     */
    private String stylecode;
    
    /**
     * 推送状态（）
     */
    private String checkrun;
    
    public String getPubid() {
        return pubid;
    }

    public void setPubid(String pubid) {
        this.pubid = pubid;
    }

    public String getPubport() {
        return pubport;
    }

    public void setPubport(String pubport) {
        this.pubport = pubport;
    }

    public String getPubprotocal() {
        return pubprotocal;
    }

    public void setPubprotocal(String pubprotocal) {
        this.pubprotocal = pubprotocal;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRespformat() {
        return respformat;
    }

    public void setRespformat(String respformat) {
        this.respformat = respformat;
    }

    public String getPubdesc() {
        return pubdesc;
    }

    public void setPubdesc(String pubdesc) {
        this.pubdesc = pubdesc;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getReqdatatype() {
        return reqdatatype;
    }

    public void setReqdatatype(String reqdatatype) {
        this.reqdatatype = reqdatatype;
    }

    public String getReturndatatype(){
        return returndatatype;
    }

    public void setReturndatatype(String returndatatype) {
        this.returndatatype = returndatatype;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getSampledata() {
        return sampledata;
    }

    public void setSampledata(String sampledata) {
        this.sampledata = sampledata;
    }

    public String getReqformat() {
        return reqformat;
    }

    public void setReqformat(String reqformat) {
        this.reqformat = reqformat;
    }

    public String getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(String checkstatus) {
        this.checkstatus = checkstatus;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    
    public String getMethodInClass() {
        return methodInClass;
    }

    public void setMethodInClass(String methodInClass) {
        this.methodInClass = methodInClass;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getStylecode() {
        return stylecode;
    }

    public void setStylecode(String stylecode) {
        this.stylecode = stylecode;
    }

    public String getCheckrun() {
        return checkrun;
    }

    public void setCheckrun(String checkrun) {
        this.checkrun = checkrun;
    }
    
}
