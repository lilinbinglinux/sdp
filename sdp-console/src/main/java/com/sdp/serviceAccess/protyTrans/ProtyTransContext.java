/**
 * 
 */
package com.sdp.serviceAccess.protyTrans;

import java.util.List;
import java.util.Map;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: ProtyTransContext.java
 * @Description: 策略容器类,决策使用者(ps:策略者模式)
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年7月31日 下午3:13:46 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年7月31日     renpengyuan      v1.0.0               修改原因
 */
public class ProtyTransContext{

	private ProptyTransBase ptBase= null;

	private ProtyTransContext(){};

	public ProtyTransContext (ProptyTransBase ptBase){
		this.ptBase= ptBase;
	}

	public String transToXml(List<Map<String, Map<String,String>>>  proties,String listRoot,String mapRoot) {
		// TODO Auto-generated method stub
		return ptBase.transToXml(proties,listRoot,mapRoot);
	}
    
	public List<Map<String, String>> transToBean(String proXml) {
		// TODO Auto-generated method stub
		return ptBase.transToBean(proXml);
	}


}
