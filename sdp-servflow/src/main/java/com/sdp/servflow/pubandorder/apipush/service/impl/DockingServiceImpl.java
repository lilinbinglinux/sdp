package com.sdp.servflow.pubandorder.apipush.service.impl;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.pubandorder.apipush.model.DockingBean;
import com.sdp.servflow.pubandorder.apipush.service.DockingService;
import com.sdp.servflow.pubandorder.apipush.util.PushStaticParameterFlag;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DockingServiceImpl implements DockingService{
    
    @Resource
    private DaoHelper daoHelper;

    @Autowired
    private DockingService dockingService;

    /**
     * ApiPush2
     */
//    @Autowired
//    private ApiPush2 apiPush;

    @Autowired
    private ServiceMainService serviceMainService;

    @Autowired
    private ServiceVersionService serviceVersionService;

    /**
     * 查看推送开关
     */
    @Value("${apiPush.open}")
    private String open;

    @Override
    public List<DockingBean> getAllByCondition(Map<String, String> map) {
        return daoHelper.queryForList("com.sdp.frame.web.mapper.puborder.apiPush.DockingBeanMapper.getAllByCondition", map);
    }

    @Override
    public void insert(DockingBean dockingBean) {
        daoHelper.insert("com.sdp.frame.web.mapper.puborder.apiPush.DockingBeanMapper.insert", dockingBean);
    }

    @Override
    public void update(DockingBean dockingBean) {
        daoHelper.update("com.sdp.frame.web.mapper.puborder.apiPush.DockingBeanMapper.updateByPrimaryKey", dockingBean);
    }

    @Override
    public void delete(String orderId) {
        daoHelper.delete("com.sdp.frame.web.mapper.puborder.apiPush.DockingBeanMapper.deleteByPrimaryKey", orderId);
    }

    @Override
    public JSON deleteById(String id) {
        int n = daoHelper.delete("com.sdp.frame.web.mapper.puborder.apiPush.DockingBeanMapper.deleteById", id);
        Map<String, Object> map = new HashMap<String, Object>();
        if (n > 0){
            map.put("result", "success");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return jsonObject;
        }else{
            map.put("result", "fail");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return jsonObject;
        }
    }

    @Override
    public JSON updateApiState(Map<String, String> paramMap) {
        List<ServiceMainBean> serviceMainBeanList = serviceMainService.getBySerNameAndVersion(paramMap);
        String serId = serviceMainBeanList.get(0).getSerId();

        Map<String, String> serVersionMap = new HashMap<>();
        serVersionMap.put("serId", serId);
        serVersionMap.put("serVersion", paramMap.get("serVersion"));
        List<ServiceVersionBean> serviceVersionBeanList = serviceVersionService.getAllByCondition(serVersionMap);
        String serVerId = serviceVersionBeanList.get(0).getSerVerId();

        ServiceVersionBean serviceVersionBean = new ServiceVersionBean();
        serviceVersionBean.setSerVerId(serVerId);
        serviceVersionBean.setPushState("0");
        
        Map<String, Object> resultMap = new HashMap<>();
        
		try {
			serviceVersionService.update(serviceVersionBean);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", PushStaticParameterFlag.PUSH_FAIL);
			resultMap.put("msg", "服务编排更新状态报错信息:"+e);
			JSONObject jsonObject = JSONObject.fromObject(resultMap);
		    return jsonObject;
		}
      
//        if (n > 0){
//            resultMap.put("result", PushStaticParameterFlag.PUSH_SUCCESS);
//        }else{
//            resultMap.put("result", PushStaticParameterFlag.PUSH_FAIL);
//        }
		
        //因APIManager不对服务来源渠道进行区别，所以只要不抛异常，查到是0条也返回成功
        resultMap.put("result", PushStaticParameterFlag.PUSH_SUCCESS);
        JSONObject jsonObject = JSONObject.fromObject(resultMap);
        return jsonObject;
    }

    @Override
    public void selectPushSer(String serId) {
        Map<String, String> map = new HashMap<>();
        map.put("serId", serId);
        List<DockingBean> dockingBeanList = getAllByCondition(map);
        if (dockingBeanList!=null && !dockingBeanList.isEmpty()){
            
        }

    }

    @Override
    public String execute(HttpServletRequest request, String orderId, String serId, String serVerId) {
//        String result = PushStaticParameterFlag.PUSH_FAIL;
//        if("true".equals(open)){
//            if(StringUtils.isNotBlank(orderId)){
//                result = apiPush.findPublisher(request, orderId, serId, serVerId);
//                if(PushStaticParameterFlag.PUSH_SUCCESS.equals(result)){
//                    result = PushStaticParameterFlag.PUSH_SUCCESS;
//                }
//            }
//        }
//        return result;
    	return null;
    }

}
