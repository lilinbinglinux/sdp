/**
 *
 */
package com.sdp.serviceAccess.protyTrans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import net.sf.jsqlparser.expression.operators.arithmetic.Concat;

/**
 * Copyright: Copyright (c) 2018 BONC
 *
 * @ClassName: Test.java
 * @Description: 测试ProtyTransTestJava
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年7月31日 下午3:52:29
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年7月31日     renpengyuan      v1.0.0               修改原因
 */
public class Test {
    public static void main(String[] args) {
        parseXml();
        parseToXml();
    }
    private static void parseXml(){
        ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
        String xml="<service><name>renpengyuan</name><id>1</id><type>bcm1</type><age>23</age></service>";
        String xml1="<services><service><name>renpengyuan</name><id>1</id><age>23</age></service><service1><name>renpengyuan1</name><id>2</id><age>24</age></service1><service2><name>renpengyuan2</name><id>3</id><type>bcm3</type><age>25</age></service2><service4 name=\"renpengyuan3\" id=\"4\" type=\"bcm4\" age=\"26\"/></services>";
//        System.out.println(JSON.toJSONString(context.transToBeanSingle(xml)));
        System.out.println(JSON.toJSONString(context.transToBean(xml1)));
    }

    private static void parseToXml(){
        ProtyTransContext context = new ProtyTransContext(new ProptyTransDom4j());
        Map<String,String> map = new HashMap<String,String>();
        map.put("id", "1");
        map.put("name", "renpengyuan");
        map.put("age", "23");
        map.put("type", "bcm1");
//        System.out.println(context.transToXmlSingle(map, "service"));

        List<Map<String,Map<String,String>>> pro = new ArrayList<>();
        Map<String,Map<String,String>> singlePro = new HashMap<>();
        Map<String,String> atMap = new HashMap<>();
        Map<String,String> vlMap = new HashMap<>();
        atMap.put("type", "bcm1");
        vlMap.put("id", "1");
        vlMap.put("name", "renpengyuan");
        vlMap.put("age", "23");
        singlePro.put("At", atMap);
        singlePro.put("Vl", vlMap);
        pro.add(singlePro);
        Map<String,Map<String,String>> singlePro1 = new HashMap<>();
        Map<String,String> atMap1 = new HashMap<>();
        Map<String,String> vlMap1 = new HashMap<>();
        atMap1.put("type", "bcm2");
        vlMap1.put("id", "2");
        vlMap1.put("name", "renpengyuan1");
        vlMap1.put("age", "24");
        singlePro1.put("At", atMap1);
        singlePro1.put("Vl", vlMap1);
        pro.add(singlePro1);
        Map<String,Map<String,String>> singlePro2 = new HashMap<>();
        Map<String,String> atMap2 = new HashMap<>();
        Map<String,String> vlMap2 = new HashMap<>();
        vlMap2.put("id", "3");
        vlMap2.put("name", "renpengyuan2");
        vlMap2.put("age", "25");
        vlMap2.put("type", "bcm3");
        singlePro2.put("At", null);
        singlePro2.put("Vl", vlMap2);
        pro.add(singlePro2);
        Map<String,Map<String,String>> singlePro3 = new HashMap<>();
        Map<String,String> atMap3 = new HashMap<>();
        Map<String,String> vlMap3 = new HashMap<>();
        atMap3.put("type", "bcm4");
        atMap3.put("id", "4");
        atMap3.put("name", "renpengyuan3");
        atMap3.put("age", "26");
        singlePro3.put("At", atMap3);
        singlePro3.put("Vl", null);
        pro.add(singlePro3);
        System.out.println(context.transToXml(pro, "services", "service"));
    }
}