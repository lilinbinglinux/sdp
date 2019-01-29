package com.sdp.servflow.pubandorder.node.model.field;

import java.io.Serializable;

/***
 * 
 * xml 中的属性（可能是命名空间）
 *
 * @author 任壮
 * @version 2017年11月14日
 * @see Attribute
 * @since
 */
public class Attribute extends XmlNode implements Serializable{
    
	private static final long serialVersionUID = 1L;
	private transient Field belongField;
    

    public Field getBelongField() {
        return belongField;
    }

    public void setBelongField(Field belongField) {
        this.belongField = belongField;
    }

	@Override
	public String toString() {
		return "Attribute [belongField=" + belongField + ", getBelongField()=" + getBelongField() + ", getId()="
				+ getId() + ", getName()=" + getName() + ", getType()=" + getType() + ", getLength()=" + getLength()
				+ ", getLocation()=" + getLocation() + ", getPath()=" + getPath() + ", getParamter()=" + getParamter()
				+ ", getValue()=" + getValue() + ", toString()=" + super.toString() + ", isMust()=" + isMust()
				+ ", getDesc()=" + getDesc() + ", getParentId()=" + getParentId() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
    
    


    
}
