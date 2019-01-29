package com.sdp.servflow.pubandorder.serve.param;


import static com.sdp.frame.util.StringUtil.nullToString;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jdom.output.XMLOutputter;

import com.sdp.servflow.pubandorder.node.model.field.Attribute;
import com.sdp.servflow.pubandorder.node.model.field.Field;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;
import com.sdp.servflow.pubandorder.util.IContants;


public class XmlFactory extends ShowFactory {

    @Override
    public String getParamString(StartNode node) {
        String result = null;
        try {
            Element element =  initParamter(node.getParam());
            XMLOutputter xmlOut = new XMLOutputter();
             result = xmlOut.outputString(element);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Element initParamter(Parameter parameter) {
        List<Field> fieldList = parameter.getFildList();

        if (fieldList == null || fieldList.size() == 0) {

            return null;
        }
        //获取父节点的信息
        Field root = null;
        for (Field field : fieldList) {
            String parentId = field.getParentId();
            if (parentId == null || parentId.length() == 0) {
                root = field;
                break;
            }
        }
        if (root == null) {
            return null;
        }
        String name = root.getName();
        // 父节点
        Element element = new Element(name);

        element.addContent(makeUpField(fieldList, root.getId()));
        
        return element;
    }

    /**
     * Description: 根据一系列field信息拼装参数节点信息
     * 
     * @param fildList
     * @return List<Field>
     * @see
     */
    private List<Element> makeUpField(List<Field> fieldList, String parentId) {
        if (fieldList == null || fieldList.size() == 0) return null;
        List<Element> elementList = new ArrayList<Element>();

        for (Field field : fieldList) {
            if (parentId == field.getParentId()
                || (parentId != null && parentId.equals(field.getParentId()))) {
                Element child = field2Element(field);
                elementList.add(child);
                List<Element> childList = makeUpField(fieldList, field.getId());
                child.addContent(childList);
            }
        }

        return elementList;

    }

    /**
     * Description: 拼接节点信息
     * 
     * @param field
     * @return Element
     * @see
     */
    private Element field2Element(Field field) {
        String desc  = field.getDesc() == null?"没有描述信息":field.getDesc();
        Element element = null;
        String location  =nullToString(field.getLocation());
        if(location.equals(IContants.REQBODY)||location.equals(IContants.RESPONSEBODY)) {
            element = new Element(field.getName());
            element.addContent(desc);
        }
        // 标签名为attr的属性值
        List<Attribute> attrList = field.getAttribute();
        if (attrList != null && attrList.size() > 0) {
            List<Element> attrElements = new ArrayList<Element>();
            for (Attribute attr : attrList) {
                Element el = attribute2Elment(attr, element);
                if (el != null) {
                    attrElements.add(el);
                }
            }
            if (attrElements.size() > 0) element.addContent(attrElements);
        }
        return element;
    }

    /***
     * Description: 拼接属性
     * 
     * @param attribute
     * @return List<Element>
     * @see
     */
    private Element attribute2Elment(Attribute attribute,Element element) {
        String desc  = attribute.getDesc() == null?"没有描述信息":attribute.getDesc();
        element.setAttribute(nullToString(attribute.getName()),desc);
        return element;
    }

}
