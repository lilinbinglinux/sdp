package com.sdp.servflow.pubandorder.serve.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sdp.servflow.pubandorder.serve.ShowServer;
import com.sdp.servflow.pubandorder.serve.mapper.ServeAuthMapper;
import com.sdp.servflow.pubandorder.util.IContants;
@Service
public class ShowServerIml implements ShowServer{

    /***
     * 服务鉴权的mapper
     */
    @Autowired
    private ServeAuthMapper serveAuthMapper;
    /***
     * 根据租户id和注册显示页面需要展示的信息
     */
    @Override
    public String showPubSerParm(String pubid, String tenant_id, String type) {
        String parmShowString =null;
        
           HashMap<String, Object>  pubSer = new HashMap<String, Object>();
           pubSer.put("tenant_id", tenant_id);
           pubSer.put("pubid", pubid);
           pubSer.put("type", type);
            
        
           List<HashMap<String, Object>> parmList = serveAuthMapper.getPubSerParm(pubSer);
           
           Map<String, HashMap<String, Object>> parmMap = new HashMap<String, HashMap<String, Object>>();
           for(HashMap<String, Object> param :parmList)
           {
               parmMap.put((String)param.get("id"), param);
           }
           
           
           Map<String,Object> showMap = new HashMap<String,Object>();
           for(HashMap<String, Object> param :parmList)
           {
              String  path = (String)param.get("path");
              String reqtype =  (String)param.get("reqtype");
               if(!StringUtil.isBlank(path))
               {
                   
                   //拼节点
                   String[] keyArray = path.split("/");
                   //当前节点
                    Map<String,Object> node = showMap;
                    for(int i=0;i<keyArray.length-1;i++)  {
                        
                        String key = (String)parmMap.get(keyArray[i]).get("ecode");
                        String childNodeType = (String)parmMap.get(keyArray[i]).get("reqtype");
                        if(node.get(key) == null) {
                            if(IContants.LISTOBJECT.equals(childNodeType)) {
                                HashMap<String,Object>  childNode =      new HashMap<String,Object>();
                                List<HashMap<String, Object>> childNodeList = new ArrayList<HashMap<String, Object>>();
                                childNodeList.add(childNode);
                                node.put(key, childNodeList);
                            }else if (IContants.OBJECT.equals(childNodeType)) {
                                
                                node.put(key, new HashMap<String,Object>());
                            }
                        }
                        if(IContants.LISTOBJECT.equals(childNodeType)) {
                            node = ((List<HashMap<String, Object>>)node.get(key)).get(0);
                        }else if (IContants.OBJECT.equals(childNodeType)) {
                            node = (Map<String, Object>)node.get(key);
                        }
                    }
                    //这类节点不追加
                    if(!IContants.LISTOBJECT.equals(reqtype)&&!IContants.OBJECT.equals(reqtype))  {
                        
                        
                        String reqdesc =(String)param.get("reqdesc");
                        if(reqdesc==null||reqdesc.trim().length()==0)
                        {
                            reqdesc = "暂无描述细信息";
                        }
                        
                        String reqtypes = (String)param.get("reqtype");
                        if(reqtypes == null){
                            reqtypes = "暂无参数类型";
                        }
                        
                        node.put((String)parmMap.get(keyArray[keyArray.length-1]).get("ecode") ,reqdesc+","+reqtypes);
                    }
               }
           }
           parmShowString =JSONObject.toJSONString(showMap);
           
        return parmShowString;
    }

}
