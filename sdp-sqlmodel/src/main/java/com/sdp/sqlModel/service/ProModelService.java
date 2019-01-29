package com.sdp.sqlModel.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.entity.ProModel;
import com.sdp.sqlModel.mapper.ProModelMapper;

@Service
public class ProModelService {
	 /**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(ProModelService.class);
    
    @Autowired 
    private ProModelMapper proModelMapper;
    
    public List<ProModel> getProModel(){
    	User userInfo = CurrentUserUtils.getInstance().getUser();
    	return proModelMapper.getProModel(userInfo.getTenantId());
    }
    
}
