package com.sdp.servflow.pubandorder.node.model.parameter;

import java.io.Serializable;
import java.util.List;

import com.sdp.servflow.pubandorder.node.model.field.Field;
import com.sdp.servflow.pubandorder.node.model.node.Node;

/**
 * 
 * 参数类型
 *
 * @author 任壮
 * @version 2017年10月20日
 * @see Parameter
 * @since
 */
public class Parameter implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String inparameter ="inparameter";
    public static final String outparameter ="outparameter";

    /***
     * 参数格式
     */
    private String format;
    
    /***
     * 类型（ Object List）
     */
    private String type;
    
    /***
     *字符集
     */
    private String charset;
  /*  *//***
     * xml中使用的命名空间
     *//*
    private  Map<String,Namespace> nameSpaces;*/
    /***
     * 入参还是出参(inparameter outparameter)
     */
    private String parameterType;
    /***
     * 归属于某个节点
     */
    private Node belongNode;
    
    private List<Field> fildList; 
    
    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getParameterType() {
        return parameterType;
    }
    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }
    public Node getBelongNode() {
        return belongNode;
    }
    public void setBelongNode(Node belongNode) {
        this.belongNode = belongNode;
    }
    public List<Field> getFildList() {
        return fildList;
    }
    public void setFildList(List<Field> fildList) {
        this.fildList = fildList;
    }
    public String getCharset() {
        return charset;
    }
    public void setCharset(String charset) {
        this.charset = charset;
    }
    @Override
    public String toString() {
        return "Parameter [format=" + format + ", type=" + type + ", charset=" + charset
               + ", parameterType=" + parameterType + ", belongNode=" + belongNode + ", fildList="
               + fildList + "]";
    }
}
