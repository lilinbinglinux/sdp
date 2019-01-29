package com.sdp.servflow.pubandorder.orderapistore.util;

import java.util.HashMap;
import java.util.Map;

import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.serve.param.ShowFactory;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2018/1/10.
 */
public class GetParam extends ShowFactory{

    @Override
    protected String getParamString(StartNode node) {
        return null;
    }

    public Map<String, String> getParamToPage(String type, String serFlow){
        Map<String, String> getParamMap = new HashMap<String, String>();
        try {
            getParamMap = getParam(type, serFlow);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getParamMap;
    }
}
