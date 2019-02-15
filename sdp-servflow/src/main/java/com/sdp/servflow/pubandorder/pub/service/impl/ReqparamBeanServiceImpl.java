package com.sdp.servflow.pubandorder.pub.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.pubandorder.flowchart.model.ParamFlowChartBean;
import com.sdp.servflow.pubandorder.flowchart.service.ParamFlowChartBeanService;
import com.sdp.servflow.pubandorder.pub.model.InterfaceParamPo;
import com.sdp.servflow.pubandorder.pub.model.PublisherBean;
import com.sdp.servflow.pubandorder.pub.model.ReqParamBeans;
import com.sdp.servflow.pubandorder.pub.model.ReqparamBean;
import com.sdp.servflow.pubandorder.pub.model.ReqparamBeanList;
import com.sdp.servflow.pubandorder.pub.service.PublisherBeanService;
import com.sdp.servflow.pubandorder.pub.service.ReqparamBeanService;
import com.sdp.servflow.pubandorder.util.IContants;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * ReqparamBeanService实现类
 *
 * @author ZY
 * @version 2017年6月10日
 * @see ReqparamBeanServiceImpl
 * @since
 */
@Service
public class ReqparamBeanServiceImpl implements ReqparamBeanService{
    
    /**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;
    
    @Resource
    private PublisherBeanService publisherBeanService;
    
    @Resource
    private ParamFlowChartBeanService paramFlowChartBeanService;
    
    @Override
    public Map selectAll(String start,  String length, Map<String, Object> paramMap) {
        paramMap.put("parentId", "ROOT");
    	return daoHelper.queryForPageList("com.sdp.frame.web.mapper.puborder.ReqparamBean.selectAll", paramMap, start, length);
    }
    
    @Override
    public int insert(ReqparamBean reqModel) {
    	return daoHelper.insert("com.sdp.frame.web.mapper.puborder.ReqparamBean.insert", reqModel);
    }
    	
    /**
     * xml解析后保存
     */
    @Override
    public void insertByxml(String xmlStr, String pubid){
        String type = "";
        XStream xstream = new XStream();
        xstream.alias("params", ReqParamBeans.class); 
        xstream.alias("param", ReqparamBean.class);
        ReqParamBeans paramsNew = (ReqParamBeans)xstream.fromXML(xmlStr);
        
        if(StringUtils.isNotBlank(paramsNew.getType())){
            switch (paramsNew.getType()){
                case "request": type = "0";
                    break;
                case "response": type = "1";
                    break;
            }
        }
        
        List<ReqparamBean> params = paramsNew.getAllparams();
        for(ReqparamBean param : params){
            Map<String, Object> paramMap = new HashMap<>();
            List<ReqparamBean> alls =  getAllByCondition(paramMap);
            int sortmax = 0;
            for(int i=0; i<=alls.size()-2;i++){
                if(alls.get(i).getSort().compareTo(alls.get(i+1).getSort()) > 0){
                    sortmax = Integer.parseInt(alls.get(i).getSort());
                }else{
                    sortmax = Integer.parseInt(alls.get(i+1).getSort());
                }
            }
            
            if(null != param.getSubparam()){
                param.setId(IdUtil.createId());
                param.setType(type);
                param.setPubid(pubid);
                param.setPath(param.getId());
                param.setSort(Integer.toString(sortmax+1));
                param.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
                this.insert(param);
                saveSubParams(param.getSubparam(),type,param.getId(),"",pubid,param.getId(),param.getEcode(),"");
            }else{
                param.setId(IdUtil.createId());
                param.setType(type);
                param.setPubid(pubid);
                param.setPath(param.getId());
                param.setSort(Integer.toString(sortmax+1));
                param.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
                this.insert(param);
            }
        }
    }
    
    /**
     * 
     * Description:递归查询并保存xml参数多层嵌套子参数 
     * 
     *@param params
     *@param type
     *@param parentId
     *@param pubid void
     *
     * @see
     */
    private void saveSubParams(ReqparamBeanList params,String type,String parentId,String preparentId,String pubid
                               ,String parentPath, String ecodePath, String parentEcodePath){
        List<ReqparamBean> subparams =  params.getListParam();
        for(ReqparamBean subparam : subparams){
            Map<String, Object> paramMap = new HashMap<>();
            List<ReqparamBean> alls =  getAllByCondition(paramMap);
            int sortmax = 0;
            for(int i=0; i<=alls.size()-2;i++){
                if(alls.get(i).getSort().compareTo(alls.get(i+1).getSort()) > 0){
                    sortmax = Integer.parseInt(alls.get(i).getSort());
                }else{
                    sortmax = Integer.parseInt(alls.get(i+1).getSort());
                }
            }
            
            subparam.setId(IdUtil.createId());
            subparam.setType(type);
            subparam.setPubid(pubid);
            subparam.setParentId(parentId);
            subparam.setPath(parentPath+"/"+subparam.getId());
            subparam.setSort(Integer.toString(sortmax+1));
            subparam.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
            this.insert(subparam);
            if(null != subparam.getSubparam()){
                saveSubParams(subparam.getSubparam(),type,subparam.getId(),subparam.getParentId(),
                    subparam.getPubid(),subparam.getPath(), subparam.getEcode(),  subparam.getEcode());
            }
            else{
                subparam.setEcodepath(ecodePath+"/"+subparam.getEcode());
                //subparam.setParampath(preparentId+"/"+parentId+"/"+subparam.getId());
                this.update(subparam);
            }
        }
        
    }
    
	
    @Override
    public int update(ReqparamBean reqModel) {
    	return daoHelper.update("com.sdp.frame.web.mapper.puborder.ReqparamBean.updateByPrimaryKey", reqModel);
    }
    
    @Override
    public int deleteByParamId(String id){
        return daoHelper.delete("com.sdp.frame.web.mapper.puborder.ReqparamBean.deleteByPrimaryKey", id);
    }
    
    @Override
    public void deleteByCondition(Map<String, String> map) {
        daoHelper.delete("com.sdp.frame.web.mapper.puborder.ReqparamBean.deleteByCondition", map);
    }
    
    @Override
    public ReqparamBean getParamById(String id) {
        return (ReqparamBean)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.ReqparamBean.getByPrimaryKey", id);
    }

    @Override
    public List<ReqparamBean> getAllByCondition(Map<String, Object> paramMap) {
        return daoHelper.queryForList("com.sdp.frame.web.mapper.puborder.ReqparamBean.getAllByCondition", paramMap);
    }
    
    /**
     * 根据父子级关系删除，如果存在子级，则需先删除子级参数
     */
    @Override
    public String deleteParams(String id) {
        String flag;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("parentId", id);
        List<ReqparamBean> params = getAllByCondition(paramMap);
        if(null == params||params.size()<=0){
            deleteByParamId(id);
            flag = "true";
        }else{
            flag = "false";
        }
        return flag;
    }
    
    /**
     * 判断服务是否有参数
     */
    @Override
    public int getCountReqParam(String pubid) {
        return (int)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.ReqparamBean.getCountReqParam", pubid);
    }

    @Override
    public List<InterfaceParamPo> getTreeNodesParams(String flowChartId,String type,String pubids) {
        String pubidss[] = pubids.split(",");
        List<InterfaceParamPo> pos = new ArrayList<InterfaceParamPo>();
        
        //添加编排出的服务和对应的请求参数
        pos = getRelationParamsTreeData(flowChartId,"0",pos);
        
        for(int i=0;i<pubidss.length;i++){
            //添加之前所有节点服务的响应参数
            pos = getRelationParamsTreeData(pubidss[i],type,pos);
        }
        
        return pos;
    }
    
    
    /*
     * 私有方法，拼接参数映射树形节点
     */
    private List<InterfaceParamPo> getRelationParamsTreeData(String pubid,String type,List<InterfaceParamPo> pos){
        
        InterfaceParamPo layoutpubpo = new InterfaceParamPo();
        PublisherBean layoutpub = publisherBeanService.getPubById(pubid);
        if(null != layoutpub){
            layoutpubpo.setId(layoutpub.getPubid());
            layoutpubpo.setName(layoutpub.getName());
            layoutpubpo.setpId("ROOT");
            layoutpubpo.setFlag("0");
            layoutpubpo.setOpen(true);
            pos.add(layoutpubpo);
        }
        
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("pubid", pubid);
        map.put("tenant_id", CurrentUserUtils.getInstance().getUser().getTenantId());
        List<ReqparamBean> params1 = getAllByCondition(map);
        for(ReqparamBean parambean:params1){
            InterfaceParamPo parampo = new InterfaceParamPo();
            parampo.setFlag("1");
            parampo.setId(parambean.getId());
            parampo.setName(parambean.getEcode());
            parampo.setReqtype(parambean.getReqtype());
            if(StringUtils.isNotBlank(parambean.get_parentId())){
                parampo.setpId(parambean.getParentId());
            }
            else{
                parampo.setpId(parambean.getPubid());
            }
            pos.add(parampo);
        }
        
        return pos;
    }

    /*@Override
    public List<ReqparamBean> getTreeChildrenData(String type, String pubid) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("type", type);
        paramMap.put("pubid", pubid);
        List<ReqparamBean> allparams =  getAllByCondition(paramMap);
        List<ReqparamBean> lists = new ArrayList<ReqparamBean>();
        List<ReqparamBean> sublists = new ArrayList<ReqparamBean>();
        
        for(ReqparamBean bean:allparams){
            //从最底层子节点开始朝上递归
            if(StringUtils.isNotBlank(bean.getParampath())){
                //把最底层子根节点收集一份
                sublists.add(bean);
                //给根节点设置层级子节点
                getChild(allparams,bean,lists);
                //跳出本次循环
                break;
            }
        }
        
        //添加没有子节点的节点
        for(ReqparamBean bean:allparams){
            if(StringUtils.isBlank(bean.getParentId())){
                int i = 0;
                for(ReqparamBean subbean:sublists){
                    if(subbean.getParampath().contains(bean.getId())){
                        i = i+1;
                    }
                }
                if(i<=0){
                    lists.add(bean);
                }
            }
        }
        
        return lists;
    }*/
    
    //给根节点设置层级子节点，并添入list
    private void getChild(List<ReqparamBean> params,ReqparamBean bean,List<ReqparamBean> lists){
        
        ReqparamBean parambean = new ReqparamBean();
        String s = (bean.getPath().split(bean.getId()))[0]; 
        String ss[] = bean.getPath().split("/");
        
        //根据path找到最近一层父节点
        parambean = getParamById(ss[ss.length-2]);
        
        List<ReqparamBean> list = new ArrayList<ReqparamBean>();
        list.add(bean);
        
        //找与自己同级并相同父节点的节点
        for(ReqparamBean subbean :params){
            if(!subbean.getId().equals(bean.getId())&&subbean.getPath().contains("/")){
                if((subbean.getPath().split(subbean.getId()))[0].equals(s)){
                    list.add(subbean);
                }
            }
        }
        
        //给最近一层父节点设置孩子节点
        parambean.setChildren(list);
        
        if(parambean.getPath().contains("/")){
            getChild(params,parambean,lists);
        }else{
            lists.add(parambean);
            return;
        }
        
    }

    @Override
    public List<ReqparamBean> getParamvLayoutParam(Map<String, Object> map) {
        List<ReqparamBean> params = getAllByCondition(map);
        List<ReqparamBean> list = new ArrayList<ReqparamBean>();
        
        if((boolean)map.get("layout")){
            for(ReqparamBean param:params){
                Map<String, String> parammap = new HashMap<String, String>();
                parammap.put("pubid", param.getPubid());
                parammap.put("flowChartId", (String)map.get("flowChartId"));
                parammap.put("next_paramid", param.getId());
                parammap.put("tenant_id", param.getTenant_id());
                
                //查每个参数的映射关系，用于页面回显
                List<ParamFlowChartBean> layoutParams = paramFlowChartBeanService.getAllByCondition(parammap);
                if(null != layoutParams&&layoutParams.size()>0){
                    List<ParamFlowChartBean> paramFlowChartBeans = new ArrayList<ParamFlowChartBean>();
                    for(ParamFlowChartBean layoutParam:layoutParams){
                        ReqparamBean bean = getParamById(layoutParam.getPre_paramid());
                        if(bean != null){
                            layoutParam.setPre_paramname(bean.getEcode());
                        }
                        paramFlowChartBeans.add(layoutParam);
                    }
                    param.setParamFlowChartBeans(paramFlowChartBeans);
                }
                list.add(param);
            }
        }else{
            list = params;
        }
        
        return list;
    }

    @Override
    public void insertObj(Map<String,Object> map) {
        
        List<ReqparamBean> list = (List<ReqparamBean>)map.get("objlist");
        String pubid = (String)map.get("pubid");
        
        Map<String, Object> paramMap = new HashMap<>();
        List<ReqparamBean> alls =  getAllByCondition(paramMap);
        int sortmax = 0;
        for(int i=0; i<=alls.size()-2;i++){
            if(alls.get(i).getSort().compareTo(alls.get(i+1).getSort()) > 0){
                sortmax = Integer.parseInt(alls.get(i).getSort());
            }else{
                sortmax = Integer.parseInt(alls.get(i+1).getSort());
            }
        }
        
        for(ReqparamBean req:list){
            if(StringUtils.isBlank(req.getId())){
                req.setId(IdUtil.createId());
            }
            req.setPubid(pubid);
            req.setSort(Integer.toString(sortmax+1));
            //req.setParentId("ROOT");
            req.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
            
            ReqparamBean parentbean = getParamById(req.getParentId());
            
            if(null != parentbean){
                    req.setPath(parentbean.getPath()+"/"+req.getId());
                    //遍历判断每个节点的情况 并追加到额codepath后面
                    String path = parentbean.getPath();
                    StringBuffer sb =new StringBuffer("");
                    String[] parentbeans = path.split("/");
                    int len =parentbeans.length;
                    for(int i=0;i<len;i++)
                    {
                        ReqparamBean cur = getParamById(parentbeans[i]);
                        //祖先节点中出现list
                        if(IContants.LIST.equals(cur.getReqtype()))
                        {
                            sb =new StringBuffer("");
                            break;
                        }
                        if(StringUtils.isNotBlank(cur.getEcode())){
                            sb.append(cur.getEcode());
                            sb.append("/");
                        }
                    }
                    if(sb.length()>0&&!req.getParampos().equals("5")&&!req.getParampos().equals("6"))
                    {
                        //如果不是属性，则需要拼自己的ecode
                        sb.append(req.getEcode());
                    }else if(sb.length()>0&&(req.getParampos().equals("5")||req.getParampos().equals("6"))){
                        //如果是属性，则不拼接自己，把最后的"/"去掉
                        sb.deleteCharAt(sb.length()-1);
                    }
                    req.setEcodepath(sb.toString());
            }else{
                if(null == req.getReqtype()||(!req.getReqtype().equalsIgnoreCase("Object")&&!req.getReqtype().equalsIgnoreCase("List"))){
                    
                    req.setEcodepath(req.getEcode());
                }
                req.setPath(req.getId());
            }
            
            insert(req);
        }
    }
  /*  @Override
    public void insertObj(Map<String,Object> map) {
        
        List<ReqparamBean> list = (List<ReqparamBean>)map.get("objlist");
        String pubid = (String)map.get("pubid");
        
        for(ReqparamBean req:list){
            req.setId(IdUtil.createId());
            req.setPubid(pubid);
            //req.setParentId("ROOT");
            req.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
            
            ReqparamBean parentbean = getParamById(req.getParentId());
            
            if(null != parentbean){
                if(StringUtils.isBlank(parentbean.getPath())){
                    req.setPath(parentbean.getId()+"/"+req.getId());
                }else{
                    req.setPath(parentbean.getPath()+"/"+req.getId());
                }
                
                String spath = req.getId();
                String secode = req.getEcode();
                Map<String,String> pathmap = getParentPath(req,spath,secode);
                
                req.setParampath(pathmap.get("spath"));
                req.setEcodepath(pathmap.get("secode"));
            }else{
                if(!req.getReqtype().equalsIgnoreCase("Object")&&!req.getReqtype().equalsIgnoreCase("List")){
                    req.setParampath(req.getId());
                    req.setEcodepath(req.getEcode());
                }
                req.setPath(req.getId());
            }
            insert(req);
        }
    }*/

    
    /*//递归拼接path
    private Map<String,String> getParentPath(ReqparamBean bean,String spath,String secode){
        ReqparamBean parentbean = getParamById(bean.getParentId());
        spath = parentbean.getId()+"/"+spath;
        secode = parentbean.getEcode()+"/"+secode;
        
        
        if(StringUtils.isNotBlank(parentbean.getParentId())){
            getParentPath(parentbean,spath,secode);
        }
        
        Map<String,String> map = new HashMap<String,String>();
        map.put("spath", spath);
        map.put("secode", secode);
        
        return map;
        
    }*/
  //递归拼接path
    
    @Override
    public ReqparamBean upsort(Map<String, Object> map) {
        return (ReqparamBean)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.ReqparamBean.upsort", map);
    }

    @Override
    public ReqparamBean downSort(Map<String, Object> map) {
        return (ReqparamBean)daoHelper.queryOne("com.sdp.frame.web.mapper.puborder.ReqparamBean.downSort", map);
    }
}

