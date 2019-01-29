package com.sdp.servflow.pubandorder.register.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.servflow.pubandorder.register.service.ServiceRegisterService;
import com.sdp.servflow.pubandorder.serapitype.model.ServiceTypeBean;
import com.sdp.servflow.pubandorder.serapitype.service.ServiceTypeService;
import com.sdp.servflow.serlayout.process.service.SerProcessNodeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author Niu Haoxuan
 * @date Created on 2017/12/27.
 */
@Service
public class ServiceRegisterServiceImpl implements ServiceRegisterService{

    /**
     * SerProcessNodeService
     */
    @Autowired
    private SerProcessNodeService serProcessNodeService;

    @Override
    public String addStartNode(String serNodeArray, String serJoinArray, String serFlowType) {
        serProcessNodeService.addNode(serNodeArray, serJoinArray, serFlowType);
        return "success";
    }
}