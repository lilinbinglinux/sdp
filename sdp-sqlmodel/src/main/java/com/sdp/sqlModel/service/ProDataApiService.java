package com.sdp.sqlModel.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.entity.ProDataApi;
import com.sdp.sqlModel.mapper.ProDataApiMapper;

@Service
public class ProDataApiService {
	 /**
     * 输出日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(ProDataApiService.class);
    
    @Autowired 
    private ProDataApiMapper proDataApiMapper;
    
    public List<ProDataApi> getProDataApi(String modelId){
    	User userInfo = CurrentUserUtils.getInstance().getUser();
    	return proDataApiMapper.getProDataApi(userInfo.getTenantId(),modelId);
    }
    
    
    
}
