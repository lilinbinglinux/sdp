package com.sdp.servflow.pubandorder.node.model.field;

import java.io.Serializable;
import java.util.List;

import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;

/**
 * 
 * 属性类
 * （在json中代表key的信息
 * 在xml中代表某个节点的信息
 * ）
 *
 * @author 任壮
 * @version 2017年10月20日
 * @see Field
 * @since
 */
public class Field  extends XmlNode implements Serializable{
	private static final long serialVersionUID = 1L;
	/***
     * 属性的信息（xml格式中存在）
     */
    private List<Attribute> attribute;

    public List<Attribute> getAttribute() {
        return attribute;
    }

    public void setAttribute(List<Attribute> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String toString() {
        return "Field [attribute=" + attribute + ", toString()=" + super.toString() + "]";
    }


}
