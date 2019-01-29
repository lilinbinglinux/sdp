package com.sdp.servflow.pubandorder.node.model.node;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ConditionNode extends Node  implements Serializable{
    
	private static final long serialVersionUID = 1L;
    /***
     * 条件判断的参数来源
     */
    private List<String> fieldList; 
    
    /**
     * 需要判断的java代码
     */
    private String code;
    /***
     * 参数映射
     * 第一个参数为目标的参数id  第二个参数为来源的参数id
     * 
     */
    private Map<String, String> joinfield;
    /***
     * 常量赋值
     * 第一个参数为目标的参数id  第二个参数为常量
     * 
     */
    private Map<String, String> constantField;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public List<String> getFieldList() {
        return fieldList;
    }
    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }
    public Map<String, String> getJoinfield() {
        return joinfield;
    }
    public void setJoinfield(Map<String, String> joinfield) {
        this.joinfield = joinfield;
    }
    @Override
    public String toString() {
        return "ConditionNode [fieldList=" + fieldList + ", code=" + code + ", joinfield="
               + joinfield + ", constantField=" + constantField + ", toString()="
               + super.toString() + "]";
    }
    public Map<String, String> getConstantField() {
        return constantField;
    }
    public void setConstantField(Map<String, String> constantField) {
        this.constantField = constantField;
    }

}
