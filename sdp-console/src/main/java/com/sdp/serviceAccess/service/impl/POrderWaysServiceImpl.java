package com.sdp.serviceAccess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.common.CurrentUserUtils;
import com.sdp.serviceAccess.entity.POrderWays;
import com.sdp.serviceAccess.mapper.POrderWaysMapper;
import com.sdp.serviceAccess.service.IPOrderWaysService;

/**
 * 
* @Description: 订购方式Service
  @ClassName: POrderWaysServiceImpl
* @author zy
* @date 2018年8月9日
* @company:www.bonc.com.cn
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2018年8月9日     zy      v1.0.0               修改原因
 */
@Service
public class POrderWaysServiceImpl implements IPOrderWaysService{
	
	@Autowired
	private POrderWaysMapper pOrderWaysMapper;

	@Override
	public POrderWays findByPriId(String pwaysId) {
		POrderWays orderWays = new POrderWays(pwaysId);
		return (POrderWays)pOrderWaysMapper.findById(orderWays);
	}

	@Override
	public List<POrderWays> allPorderWays(POrderWays pOrderWays) {
		//pOrderWays.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
		return pOrderWaysMapper.findAll(pOrderWays);
	}
	
	

}
