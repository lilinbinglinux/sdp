package com.sdp.servflow.pubandorder.serve.param;


import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdp.servflow.pubandorder.node.model.field.Field;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;
import com.sdp.servflow.pubandorder.util.IContants;


public class JsonFactory extends ShowFactory {

    @Override
    public String getParamString(StartNode node) {
        return initParamter(node.getParam());
    }

    private String initParamter(Parameter parameter) {
        String str = null;
        JSONObject json = new JSONObject();
        
        
        List<Field> fieldList = parameter.getFildList();
        makeUpField(fieldList, json,null);
        
        
        if (parameter.getType().equals(IContants.OBJECT)) {

            // 解析xml格式的描述信息
            str = json.toString();
        }
        else {
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(json);
            // 解析xml格式的描述信息
            str = jsonArray.toString();
        }



        return str;
    }

    /**
     * Description: 根据一系列field信息拼装参数节点信息
     * 
     * @param fildList
     * @return List<Field>
     * @see
     */
    private void makeUpField(List<Field> fieldList, JSONObject parentJSON,String parentId) {
        
        if (fieldList == null || fieldList.size() == 0) return ;

        for (Field field : fieldList) {
            String desc  = field.getDesc() == null?"没有描述信息":field.getDesc();
            
            
            
            if (parentId == field.getParentId()
                || (parentId != null && parentId.equals(field.getParentId()))) {
           
                String location = field.getLocation();
                //目前处理请求体和响应体的信息
                if(location.equals(IContants.REQBODY)||location.equals(IContants.RESPONSEBODY)){
                    
                    if(IContants.OBJECT.equals(field.getType())) {
                        JSONObject  curJSON = new JSONObject();
                        parentJSON.put(field.getName(), curJSON);
                        
                        makeUpField(fieldList, curJSON, field.getId());
                    }else if(IContants.LIST.equals(field.getType())) {
                        JSONObject  curJSON = new JSONObject();
                        JSONArray array = new JSONArray();
                        array.add(curJSON);
                        parentJSON.put(field.getName(), array);
                        makeUpField(fieldList, curJSON, field.getId());
                    }else {
                        parentJSON.put(field.getName(), desc);
                    }
                }
            
            }
            
        }
    }

}
