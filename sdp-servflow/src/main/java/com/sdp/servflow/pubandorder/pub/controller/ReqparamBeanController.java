package com.sdp.servflow.pubandorder.pub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.JsonUtils;
import com.sdp.servflow.pubandorder.format.imp.XmlFormat;
import com.sdp.servflow.pubandorder.pub.model.InterfaceParamPo;
import com.sdp.servflow.pubandorder.pub.model.ReqparamBean;
import com.sdp.servflow.pubandorder.pub.service.ReqparamBeanService;
import com.sdp.servflow.pubandorder.serve.ShowServer;
import com.sdp.servflow.pubandorder.util.JsonFormatTool;

import net.sf.json.JSONArray;

/**
 * 
 * 处理参数请求Controller
 *
 * @author ZY
 * @version 2017年6月10日
 * @see ReqparamBeanController
 * @since
 */
@Controller
@RequestMapping("/reqparam")
public class ReqparamBeanController {
    
    /**
     * ReqparamBeanService
     */
    @Autowired
    private ReqparamBeanService reqparamService;
    
    @Autowired
    private ShowServer show;
    
    /**
     * 
     * Description:接口跳转至参数显示页 
     * 
     *@param id
     *@return String
     *
     * @see
     */
    @RequestMapping("/index")
    public String index(String id) {
        return "puborder/reqmanage";
    }
    
    /**
     * 
     * Description: 分页获取根节点参数详情
     * 
     *@param start
     *@param length
     *@param jsonStr
     *@return Map
     *
     * @see
     */
    @ResponseBody
    @RequestMapping("/selectRootPage")
    public Map selectRootPage(String start, String length, String jsonStr) {
        @SuppressWarnings("unchecked")
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        return reqparamService.selectAll(start, length, paramMap);
    }
    
    /**
     * 
     * Description:查询参数子节点 (ligerui树形展示)
     * 
     *@param jsonStr
     *@return List<ReqparamBean>
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/selectParams", method = RequestMethod.POST)
    public Map<String, Object> selectParams(String jsonStr){
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", reqparamService.getParamvLayoutParam(paramMap));
        return map;
    }
    
    /**
     * 
     * Description: 拼接服务和参数的树形节点
     * 
     *@param type
     *@param pubids
     *@return List<InterfaceParamPo>
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/selectTreeNode", method = RequestMethod.POST)
    public List<InterfaceParamPo> selectTreeNodesParams(String flowChartId,String type,String pubids){
    	System.out.println(reqparamService.getTreeNodesParams(flowChartId,type,pubids));
        return reqparamService.getTreeNodesParams(flowChartId,type,pubids);
    }
    
    /**
     * 
     * Description: 添加某个API的参数
     * 
     *@param reqobj
     *@param pubid void
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(String reqobj,String pubid) {
        JSONArray json = JSONArray.fromObject(reqobj);
        List<ReqparamBean> list = JSONArray.toList(json, ReqparamBean.class);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("objlist", list);
        map.put("pubid", pubid);
        reqparamService.insertObj(map);
        return "insert";
    }
    
    /**
     * 
     * Description:通过xml的方式来添加 
     * 
     *@param xmlStr
     *@param pubid
     *@throws DocumentException void
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/xmlinsert", method = RequestMethod.POST)
    public void xmlinsert(String xmlStr,String pubid){
        reqparamService.insertByxml(xmlStr,pubid);
    }
    
    /**
     * 
     * Description: 根据Id查询，用于update回显
     * 
     *@param id
     *@return ReqparamBean
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/getParamById",method=RequestMethod.POST)
    public ReqparamBean getParamById(String id){
        return reqparamService.getParamById(id);
    }
    
    /**
     * 
     * Description: 更新数据库信息
     * 
     *@param reqparam
     *@return int
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public int update(ReqparamBean reqparam){
        
        if(StringUtils.isBlank(reqparam.getParentId())){
            if(!reqparam.getReqtype().equalsIgnoreCase("Object")&&!reqparam.getReqtype().equalsIgnoreCase("List")){
                reqparam.setParampath(reqparam.getId());
                reqparam.setEcodepath(reqparam.getEcode());
            }
        }
        
        if(reqparam.getReqtype().equalsIgnoreCase("Object")||reqparam.getReqtype().equalsIgnoreCase("List")){
            reqparam.setParampath("");
            reqparam.setEcodepath("");
        }
        return reqparamService.update(reqparam);
    }
    
    /**
     * 
     * Description: 根据主键删除
     * 
     *@param id
     *@return int
     *@throws Exception int
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public String deleteParams(String id) throws Exception{
        return reqparamService.deleteParams(id);
    }
    
    /**
     * 
     * Description:根据id获取是否有参数 
     * 
     *@param pubid
     *@return int
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/getcountreqparam",method=RequestMethod.POST)
    public int getCountReqParam(String pubid){
        return reqparamService.getCountReqParam(pubid);
    }
    
    /**
     * 
     * Description:json形式展示说明 
     * 
     *@param reqparam
     *@return String
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/getJsonParamBypubid",method=RequestMethod.POST)
    public String getJsonParamBypubid(ReqparamBean reqparam){
        String str =  show.showPubSerParm(reqparam.getPubid(), CurrentUserUtils.getInstance().getUser().getTenantId(),
            reqparam.getType());
        return JsonFormatTool.formatJson(str);
    }
    
    @ResponseBody
    @RequestMapping(value="/getXmlParamBypubid",method=RequestMethod.POST)
    public String getXmlParamBypubid(ReqparamBean reqparam){
        String str =  show.showPubSerParm(reqparam.getPubid(), CurrentUserUtils.getInstance().getUser().getTenantId(),
            reqparam.getType());
        String jsonStr = JsonFormatTool.formatJson(str);
        String xmlStr = XmlFormat.jsonToOther(jsonStr);
        if(xmlStr.contains("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")){
            xmlStr = xmlStr.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", " ");
        }
        if(xmlStr.contains("<xml>")){
            xmlStr = xmlStr.replace("<xml>", " ");
        }
        if(xmlStr.contains("</xml>")){
            xmlStr = xmlStr.replace("</xml>", " ");
        }
        return xmlStr;
    }
    
    /**
     * 
     * Description: 参数排序——向上
     * 
     *@param id
     *@return ReqparamBean
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/upsort",method=RequestMethod.POST)
    public String upsort(String sort, String pubid, String id, String parentId){
        Map<String, Object> map = new HashMap<>();
        map.put("sort", sort);
        map.put("pubid", pubid);
        map.put("parentId", parentId);
        ReqparamBean req = new ReqparamBean();
        ReqparamBean req1 = reqparamService.upsort(map);
        if(req1 == null){
            return "1";
        }
        String change = null;
        change = req1.getSort();
        req1.setSort(sort);
        req.setSort(change);
        req.setId(id);
        reqparamService.update(req);
        reqparamService.update(req1);
        return null;
    }
    
    /**
     * 
     * Description: 参数排序——向下
     * 
     *@param id
     *@return ReqparamBean
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/downSort",method=RequestMethod.POST)
    public String downSort(String sort, String pubid, String id, String parentId){
        Map<String, Object> map = new HashMap<>();
        map.put("sort", sort);
        map.put("pubid", pubid);
        map.put("parentId", parentId);
        ReqparamBean req = new ReqparamBean();
        ReqparamBean req1 = reqparamService.downSort(map);
        if(req1 == null){
            return "2";
        }
        String change = null;
        change = req1.getSort();
        req1.setSort(sort);
        req.setSort(change);
        req.setId(id);
        reqparamService.update(req);
        reqparamService.update(req1);
        return null;
    }
    
    /**
     * 
     * Description:只查请求参数 
     * 
     *@param pubid
     *@param name
     *@return ReqparamBean
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/getByName",method=RequestMethod.POST)
    public ReqparamBean getByName(String pubid,String name){
        ReqparamBean bean = new ReqparamBean();
        Map<String, Object> map = new HashMap<>();
        map.put("pubid", pubid);
        map.put("ecode", name);
        map.put("type", "0");
        map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        map.put("login_id", CurrentUserUtils.getInstance().getUser().getLoginId());
        
        List<ReqparamBean> list = reqparamService.getAllByCondition(map);
        if(null != list&&list.size()>0){
            bean = list.get(0);
        }
        return bean;
        
    }
    
    
    /**
     * 
     * Description:只查响应参数 
     * 
     *@param pubid
     *@param name
     *@return ReqparamBean
     *
     * @see
     */
    @ResponseBody
    @RequestMapping(value="/getByType",method=RequestMethod.POST)
    public List<ReqparamBean> getByType(String pubid,String type){
        Map<String, Object> map = new HashMap<>();
        map.put("pubid", pubid);
        map.put("type", type);
        map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        map.put("login_id", CurrentUserUtils.getInstance().getUser().getLoginId());
        
        List<ReqparamBean> list = reqparamService.getAllByCondition(map);
        return list;
        
    }
}
