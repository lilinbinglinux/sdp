package com.sdp.servflow.serlayout.sso.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Service;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.pubandorder.node.analyze.factory.NodeFactoryImp;
import com.sdp.servflow.pubandorder.node.model.field.Field;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.node.model.parameter.Parameter;
import com.sdp.servflow.pubandorder.protocol.HttpUtil;
import com.sdp.servflow.pubandorder.serapitype.model.ServiceTypeBean;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceTypeService;
import com.sdp.servflow.pubandorder.serve.LayoutServer;
import com.sdp.servflow.pubandorder.util.JsonFormatTool;
import com.sdp.servflow.serlayout.datahandler.SerFlowDataConstant;
import com.sdp.servflow.serlayout.sso.cas.CasSimulationLogin;
import com.sdp.servflow.serlayout.sso.model.SerspLoginBean;
import com.sdp.servflow.serlayout.sso.service.SerspLoginBeanService;

import net.sf.json.JSONObject;

@Service
public class SerspLoginBeanServiceImpl implements SerspLoginBeanService{
	
	/**
     * DaoHelper
     */
    @Resource
    private DaoHelper daoHelper;
    
    @Resource
    private LayoutServer layoutServer;
    
    @Resource
    private ServiceTypeService serviceTypeService;
    
    @Resource
    private CasSimulationLogin casSimulationLogin;
    

	@Override
	public Map selectPage(String start, String length, Map<String, Object> paramMap) {
		return daoHelper.queryForPageList("com.bonc.frame.web.mapper.sso.SerspLoginBeanMapper.selectPage", paramMap,start,length);
	}

	@Override
	public SerspLoginBean getAllByPrimaryKey(String spLoginId ) {
		return (SerspLoginBean)daoHelper.queryOne("com.bonc.frame.web.mapper.sso.SerspLoginBeanMapper.getAllByPrimaryKey", spLoginId);
	}

	@Override
	public List<SerspLoginBean> getAllByCondition(Map<String, String> map) {
		return daoHelper.queryForList("com.bonc.frame.web.mapper.sso.SerspLoginBeanMapper.getAllByCondition",map);
	}

	@Override
	public void insert(SerspLoginBean serspLoginBean) {
		daoHelper.insert("com.bonc.frame.web.mapper.sso.SerspLoginBeanMapper.insert", serspLoginBean);
	}

	@Override
	public void update(SerspLoginBean serspLoginBean) {
		daoHelper.update("com.bonc.frame.web.mapper.sso.SerspLoginBeanMapper.update", serspLoginBean);
	}

	@Override
	public void delete(String sploginid) {
		SerspLoginBean serspLoginBean = getAllByPrimaryKey(sploginid);
		serspLoginBean.setDelflag("1");
		update(serspLoginBean);
	}

	@Override
	public void serAnalysis(HttpServletRequest request,HttpServletResponse response,Map<String,Object> paramMap,String busiParm) throws Exception {
		HashMap<String, Object> publicParam = new HashMap<String,Object>();
		publicParam.put("ser_id", paramMap.get("sploginid"));
		publicParam.put("ser_version",paramMap.get("sploginid"));
		publicParam.put("order_id",paramMap.get("sploginid"));
		publicParam.put("order_name",paramMap.get("order_name"));
		publicParam.put("requestID",IdUtil.createId());
		publicParam.put("ipAddr",paramMap.get("ipAddr"));
		publicParam.put("tenant_id",paramMap.get("tenant_id"));
		publicParam.put("urlParam", paramMap.get("urlParam"));
		publicParam.put("sourceType", "2");
		publicParam.put("url", HttpUtil.getUrl(request));
		
		SerspLoginBean serspLoginBean = getAllByPrimaryKey(paramMap.get("sploginid").toString());
		
		String esbXml = serspLoginBean.getSpflow();
		
		HttpClient httpClient = new HttpClient();
		
		JSONObject resobj = JSONObject.fromObject(layoutServer.analysis(publicParam, busiParm, httpClient, esbXml));
		System.err.println(resobj);
		System.err.println(resobj.getString("result"));
		
		ServiceTypeBean typebean = serviceTypeService.selectByPrimaryKey(serspLoginBean.getSptypeId());
		String rootParentId = "";
		if(null != typebean)	{
			String idpathStr = typebean.getIdPath();
			rootParentId = idpathStr.substring(1,idpathStr.indexOf("/", 1));
		}
		
		//如果是cas类型
		if(rootParentId.equals(SerFlowDataConstant.spcas_id)) {
	        if(resobj.getString("respCode").equals("00000")) {
	        		System.out.println(resobj.getString("result"));
	        		JSONObject resultobj = new JSONObject();
	        		if(resobj.getString("result").equals("")) {
	        			resultobj.put("returnurl", "");
	        			resultobj.put("casServerUrlPrefix", "");
	        			resultobj.put("loginId", "");
	        			resultobj.put("password", "");
	        		}else {
	        			resultobj = JSONObject.fromObject(resobj.getString("result"));
	        		}
	        		casSimulationLogin.login(request, response,resultobj);
	        }else {
	        		throw new Exception(resobj.getString("respDesc"));
	        }
		}
				
        
	}

	@Override
	public Map<String,Object> getParams(String spLoginId) {
		SerspLoginBean serspLoginBean = getAllByPrimaryKey(spLoginId);
		Map paramsmap = new HashMap<String,Object>();
		if(serspLoginBean == null) {
			paramsmap.put("inputParam", "");
			paramsmap.put("outputParam", "");
			return paramsmap;
		}
		
		Map<String, String> map = new HashMap<String, String>();
        String Param = null;
        
        try {
			map = getParamStr(serspLoginBean.getSpflow());
			paramsmap.put("inputParam", JsonFormatTool.formatJson(map.get("inputParam")));
			paramsmap.put("outputParam",JsonFormatTool.formatJson(map.get("outputParam")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramsmap;
	}
	
	
    private Map<String, String> getParamStr(String serFlow) throws Exception {
        StringBuilder inputStr = new StringBuilder();
        StringBuilder outputStr = new StringBuilder();
        Map<String, String> map = new HashMap<String, String>();
        String protocal = null;         //请求协议
        String reqformat = null;        //请求参数格式
        String respformat = null;       //相应参数格式

        InputStream is = new ByteArrayInputStream(serFlow.getBytes("utf-8"));
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(is);
        Element sourceRoot = doc.getRootElement();
        List<Element> list = sourceRoot.getChildren();
        NodeFactoryImp factory = NodeFactoryImp.getOneNodeFactory();
        for (Element element: list) {
            Node node = factory.createNode(element);
            //当为开始节点时
            if ((Node.startNodeStyle).equals(node.getNodeStyle())){
                StartNode startNode = (StartNode)node;
                Parameter parameter = startNode.getParam();
                //获取开始节点的入参
                if ((Parameter.inparameter).equals(parameter.getParameterType())) {
                    reqformat = parameter.getType();
                    List<Field> listField= parameter.getFildList();
                    inputStr.append("{");
                    for (Field field : listField) {
                        inputStr.append("\""+field.getName()+"\":\"请输入相应类型的参数, "+field.getType()+"\",");
                    }
                    if(inputStr.length() > 1){
                        inputStr.deleteCharAt(inputStr.length()-1);
                    }
                    inputStr.append("}");
                }
                protocal = startNode.getAgreement();
            }
            //当为结束节点时
            else if ((Node.endNodeStyle).equals(node.getNodeStyle())){
                EndNode endNode = (EndNode) node;
                Parameter parameter = endNode.getParam();
                //获取结束节点的出参
                if ((Parameter.outparameter).equals(parameter.getParameterType())){
                    respformat = parameter.getType();
                    List<Field> listField= parameter.getFildList();
                    outputStr.append("{");
                    for (Field field : listField) {
                        outputStr.append("\""+field.getName()+"\":\"暂无相应的参数\",");
                    }
                    if(outputStr.length() > 1){
                        outputStr.deleteCharAt(outputStr.length()-1);
                    }
                    outputStr.append("}");
                }
            }
        }
        String inputParamStr = inputStr.toString();
        String outputParamStr = outputStr.toString();
        map.put("inputParam",inputParamStr);
        map.put("outputParam",outputParamStr);
        map.put("protocal", protocal);
        map.put("reqformat", reqformat);
        map.put("respformat", respformat);
        return map;
    }
	

}
