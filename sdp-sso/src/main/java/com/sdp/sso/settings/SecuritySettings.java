package com.sdp.sso.settings;
//package com.ssoConfig.settings;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SecuritySettings {
//
//	public static String SECURITY_BASE_URL;
//	public static final String SECURITY_ORG_LIST = "/rest/org/list"; //获取组织list
//	public static final String SECURITY_USER_LIST = "/rest/user/list"; //获取用户list
//	public static final String SECURITY_USER_FINDTENANTID = "/rest/user/findTenantId"; //获取租户id
//	
//	public static String ORG_TYPE_ADM;//行政组织机构
//	public static String ORG_TYPE_PROJECT;//项目组织机构
//
//	@Value("${security.api.url}")
//	private String url;
//	@Value("${security.orgType.adm}")
//    private String admOrgType;
//	@Value("${security.orgType.project}")
//    private String projectOrgType;
//
//	@PostConstruct
//	private void initSettings() {
//	    SECURITY_BASE_URL = url;
//	    ORG_TYPE_ADM = admOrgType;
//	    ORG_TYPE_PROJECT = projectOrgType;
//	}
//
//}
