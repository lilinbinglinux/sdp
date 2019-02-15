/*
 * 文件名：SendRecordService.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年5月23日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.sdp.cop.octopus.constant.TimeConstant;
import com.sdp.cop.octopus.dao.SendRecordDao;
import com.sdp.cop.octopus.entity.SendRecordInfo;
import com.sdp.cop.octopus.util.DateUtils;

/**
 * SendRecordService
 * 
 * @author zhangyunzhen
 * @version 2017年5月23日
 * @see SendRecordService
 * @since
 */
@Service
public class SendRecordService {

	/**
	 * SendRecordDao
	 */
	@Autowired
	private SendRecordDao sendDao;

	/**
	 * Description: <br>
	 * 使用datatable对发送日志进行服务端分页操作；
	 * 
	 * @param draw
	 *            ：画板；
	 * @param start
	 *            ：开始记录数；
	 * @param length
	 *            : 每页的长度；
	 * @param request
	 *            ：接受搜索参数；
	 * @param sendRecordInfo
	 *            查询条件
	 * @return String
	 */
	public String findByPagerSendRecordInfo(String draw, int start, int length,
			SendRecordInfo sendRecordInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (start != 0) {
			start = start / length + 1;
		}
		
		// 2.封装查询条件
		Map<String, Object> paraMap = searchParam(sendRecordInfo);
		// 3.获取日志记录列表
		Map<String, Object> sendRecordInfos = sendDao
				.findRecordForPage(paraMap, start, length);
		// 4.返回页面需要参数
		map.put("draw", draw);
		map.put("recordsTotal", sendRecordInfos.get("total"));
		map.put("recordsFiltered", sendRecordInfos.get("total"));
		map.put("data", sendRecordInfos.get("data"));
		return JSON.toJSONString(map,
				SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * Description: <br>
	 * 根据查询条件获取发送记录
	 * 
	 * @param info
	 *            查询条件
	 * @return list
	 * @see
	 */
	public List<SendRecordInfo> findSendRecordInfoByAppname(
			SendRecordInfo info) {
		// 封装查询条件
		Map<String, Object> spec = searchParam(info);
		// 获取数据
		Map<String, Object> sendRecordInfos = sendDao.findRecordForPage(spec,0,0);
		return (List<SendRecordInfo>)sendRecordInfos.get("data");
	}

	/**
	 * Description: <br>
	 * 封装条件参数
	 * 
	 * @param sendRecordInfo
	 * @return
	 * @see
	 */
	private Map<String, Object> searchParam(
			SendRecordInfo sendRecordInfo) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(sendRecordInfo.getAppname())) {
			paraMap.put("appname", "%" + sendRecordInfo.getAppname() + "%");
		}
		if (StringUtils.isNotBlank(sendRecordInfo.getStartTime())) {
			Date start = DateUtils.formatStringToDate(
					sendRecordInfo.getStartTime() + TimeConstant.START_SUFFIX);
			paraMap.put("start", start);
		}
		if (StringUtils.isNotBlank(sendRecordInfo.getEndTime())) {
			Date end = DateUtils.formatStringToDate(
					sendRecordInfo.getEndTime() + TimeConstant.END_SUFFIX);
			paraMap.put("end", end);
		}
		if (StringUtils.isNotBlank(sendRecordInfo.getType())) {
			paraMap.put("type", sendRecordInfo.getType());
		}
		return paraMap;
	}
}
