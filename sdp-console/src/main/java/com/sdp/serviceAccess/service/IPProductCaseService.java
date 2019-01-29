/**
 *
 */
package com.sdp.serviceAccess.service;

import java.util.Map;

import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.serviceAccess.entity.PProductCase;

/**
 * Copyright: Copyright (c) 2018 BONC
 *
 * @ClassName: IPProductCaseService.java
 * @Description: 服务实例业务逻辑抽象接口
 *
 * @version: v1.0.0
 * @author: renpengyuan
 * @date: 2018年8月2日 下午4:00:39
 *
 *        Modification History: Date Author Version Description
 *        ---------------------------------------------------------* 2018年8月2日
 *        renpengyuan v1.0.0 修改原因
 */
public interface IPProductCaseService {

    Status saveCase(PProductCase productCase, boolean isCurrentUserFlag);

    @Deprecated
    Status updateCase(PProductCase productCase);

    Status deleteCase(PProductCase productCase);

    Pagination findByProduct(Map<String,Object> params, Pagination page, boolean isNeedCompare);

    PProductCase singleCase(PProductCase productCase);

    Map<String, Object> findQuota(String tenantId, String svcCode);
    
    Pagination findCasePageWithAuth(PProductCase productCase, Pagination page);
    
    Integer finCaseCtnWithAuth(PProductCase productCase);
}
