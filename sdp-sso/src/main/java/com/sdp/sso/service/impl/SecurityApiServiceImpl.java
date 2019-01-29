package com.sdp.sso.service.impl;
//package com.ssoConfig.service.impl;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.alibaba.fastjson.JSONObject;
//import com.ssoConfig.service.ISecurityApiService;
//import com.ssoConfig.settings.SecuritySettings;
//
//
//@Service("securityApiService")
//public class SecurityApiServiceImpl implements ISecurityApiService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    @Override
//    public String findOrgList(String orgType) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("orgType", orgType);
//        return this.findOrgList(params);
//    }
//
//    @Override
//    public String findOrgList(Map<String, Object> params) {
//        String queryParam = JSONObject.toJSONString(params);
//        return restTemplate.getForObject(
//            SecuritySettings.SECURITY_BASE_URL + SecuritySettings.SECURITY_ORG_LIST
//                                         + "?cond={queryParam}",
//            String.class, queryParam);
//    }
//
//    @Override
//    public String findUserList(String loginId) {
//        return restTemplate.getForObject(
//            SecuritySettings.SECURITY_BASE_URL + SecuritySettings.SECURITY_USER_LIST
//                                         + "?p=loginId&v={casLoginId}",
//            String.class, loginId);
//    }
//
//    @Override
//    public String findUserList() {
//        return restTemplate.getForObject(
//            SecuritySettings.SECURITY_BASE_URL + SecuritySettings.SECURITY_USER_LIST, String.class,
//            new String());
//    }
//
//    @Override
//    public String findTenantId(String loginId) {
//        return restTemplate.getForObject(
//            SecuritySettings.SECURITY_BASE_URL + SecuritySettings.SECURITY_USER_FINDTENANTID
//                                         + "?loginId={casLoginId}",
//            String.class, loginId);
//    }
//
//}
