package com.sdp.servflow.pubandorder.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.sdp.servflow.common.BoncException;
/**
 * 
 * xml一些工具
 *
 * @author 任壮
 * @version 2017年11月7日
 * @see XmlUtil
 * @since
 */
public class XmlUtil {

    /**
     * 
     * Description: Element转为String
     * 
     *@param root
     *@return
     *@throws Exception String
     *
     * @see
     */
    public static String element2String( Element root) throws Exception {  
        if( root == null ){
            return null;
        }
        ByteArrayOutputStream bo=null;
        try {
            Document doc  = new Document(root);
            Format format = Format.getPrettyFormat();  
            format.setEncoding("UTF-8");// 设置xml文件的字符为UTF-8，解决中文问题  
            XMLOutputter xmlout = new XMLOutputter(format);  
            bo = new ByteArrayOutputStream();  
            
            xmlout.output(doc, bo);  
            return bo.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BoncException("xml转为String异常");
        } finally {
            if(null!=bo)
                bo.close();
        } 
    }
    /**
     * 
     * Description: Element转为String
     * 
     *@param root
     *@return
     *@throws Exception String
     *
     * @see
     */
    public static Element String2element(String xml) throws Exception {  
        if( xml == null ){
            return null;
        }
        Element root = null;
        InputStream is = null;
        try {
             is = new ByteArrayInputStream(xml.getBytes("utf-8"));
            SAXBuilder sb = new SAXBuilder();
            Document doc = sb.build(is);
            root = doc.getRootElement();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new BoncException("String转为xml异常");
        }finally {
            if(null!= is)
                is.close();
        }
        return root;  
    }
}
