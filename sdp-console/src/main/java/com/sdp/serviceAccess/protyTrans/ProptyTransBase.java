/**
 * 
 */
package com.sdp.serviceAccess.protyTrans;

import java.util.List;
import java.util.Map;

/**   
 * Copyright: Copyright (c) 2018 BONC
 * 
 * @ClassName: ProptyTransBase.java
 * @Description: 属性值及各种服务扩展属性值的转换xml 及各种操作接口
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年7月31日 下午2:47:26 
 *
 * Modification History:
 * Date         Author          Version            Description
 *---------------------------------------------------------*
 * 2018年7月31日     renpengyuan       v1.0.0               修改原因
 */
public interface ProptyTransBase {

	String transToXml(List<Map<String, Map<String,String>>>  proties,String listRoot,String mapRoot) throws ProtyTransException;
    
	List<Map<String,String>> transToBean(String proXml) throws ProtyTransException;

}
