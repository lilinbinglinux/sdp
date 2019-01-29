package com.sdp.servflow.pubandorder.format.imp;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class MapReplace {

    public static void main(String[] args) {
        
        
        
        LinkedHashMap<String,Object> a = new LinkedHashMap<String,Object>();
        LinkedHashMap<String,Object> a1 = new LinkedHashMap<String,Object>();
        LinkedHashMap<String,Object> b = new LinkedHashMap<String,Object>();
        b.put("b", "b");
        a1.put("a", "a值");
        a1.put("c", "c值");
        a.put("d", a1);
        a.put("e", b);
        
        System.out.println(JSONObject.toJSON(a));
        
       Object obj = getNode(a, "d/a");
       Object obj2 = getNode(a, "d/c");
       Object obj3 = getNode(a, "e/b");
        
       System.out.println("-obj-"+JSONObject.toJSON(a));
        
        
       LinkedHashMap<String,Object> resource = new LinkedHashMap<String,Object>();
        AddNode(resource,"f/a",obj);
        AddNode(resource,"f/c",obj2);
        AddNode(resource,"f/b",obj3);
        System.out.println(JSONObject.toJSON(resource));
        
        
        
        
        
    }
    /***
     * 
     * Description: 根据节点路径给节点赋值
     * 
     *@param resource  源Map
     *@param keyPath   节点路径
     *@param value     节点值
     *@return Map<String,Object>
     *
     * @see
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> AddNode(Map<String,Object> resource,String keyPath,Object value) {
        
        String[] keyArray = keyPath.split("/");
       //当前节点
        Map<String,Object> node = resource;
        
        for(int i=0;i<keyArray.length-1;i++)  {
            
            String key = keyArray[i];
            
            if(node.get(key) == null) {
                
                node.put(key, new HashMap<String,Object>());
            }
            
            node = (Map<String, Object>)node.get(key);
        }
        
        node.put(keyArray[keyArray.length-1],value);
        
        return resource;
    }
    /***
     * 
     * Description: 根据节点路径给节点赋值
     * 
     *@param resource  源Map
     *@param keyPath   节点路径
     *@param value     节点值
     *@return Map<String,Object>
     *
     * @see
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, Object> AddNode(LinkedHashMap<String,Object> resource,String keyPath,Object value) {
        
        String[] keyArray = keyPath.split("/");
        //当前节点
        LinkedHashMap<String,Object> node = resource;
        
        for(int i=0;i<keyArray.length-1;i++)  {
            
            String key = keyArray[i];
            
            if(node.get(key) == null) {
                
                node.put(key, new LinkedHashMap<String,Object>());
            }
            
            node = (LinkedHashMap<String, Object>)node.get(key);
        }
        
        node.put(keyArray[keyArray.length-1],value);
        
        return resource;
    }
    
    
    
    /***
     * 
     * Description: 根据节点路径给节点获取对应的值
     * 
     *@param resource  源Map
     *@param keyPath   节点路径
     *@param value     节点值
     *@return Map<String,Object>
     *
     * @see
     */
    @SuppressWarnings("unchecked")
    public static Object getNode(Map<String,Object> resource,String keyPath) {
        
        Object obj = null;
        if(keyPath ==null)
        {
           return null;
        }
        String[] keyArray = keyPath.split("/");
      
       //当前节点
        Map<String,Object> node = resource;
        
        for(int i=0;i<keyArray.length-1;i++)  {
            
            String key = keyArray[i];
            
            if(node.get(key) == null) {
                
                node.put(key, new HashMap<String,Object>());
            }
            
            node = (Map<String, Object>)node.get(key);
            
        }
        obj = node.get(keyArray[keyArray.length-1]);
        
        return obj;
    }
    
    
    
}
