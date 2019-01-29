/*
 * 文件名：BouncEmailLogService.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月5日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.service;


import java.util.ArrayList;
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
import com.sdp.cop.octopus.dao.BounceEmailRecordDao;
import com.sdp.cop.octopus.entity.BounceEmailLog;
import com.sdp.cop.octopus.util.DateUtils;


/**
 * 退信Service
 * @author zhangyunzhen
 * @version 2017年7月5日
 * @see BouncEmailLogService
 * @since
 */
@Service
public class BouncEmailLogService {

    @Autowired
    private BounceEmailRecordDao dao;

    /**
     * Description: <br>
     * 使用datatable对发送日志进行服务端分页操作；
     * @param draw ：画板；
     * @param start ：开始记录数；
     * @param length : 每页的长度；
     * @param request ：接受搜索参数；
     * @param sendRecordInfo 查询条件
     * @return String
     */
    public String findByPagerBounceMailInfo(String draw, int start, int length,
                                            BounceEmailLog bounceEmailLog) {
        Map<String, Object> map = new HashMap<String, Object>();
        //判断是第几页
        if (start != 0) {
        	start = start / length + 1;
        }
        //2.封装查询条件
        Map<String, Object> spec = searchParam(bounceEmailLog);
        //3.获取日志记录列表
        Map<String, Object> bounceEmailLogs = dao.findRecordPage(spec, start, length);
        //4.返回页面需要参数
        map.put("draw", draw);
        map.put("recordsTotal", bounceEmailLogs.get("total"));
        map.put("recordsFiltered", bounceEmailLogs.get("total"));
        map.put("data", bounceEmailLogs.get("data"));
        return JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * Description: <br>
     * 根据app和邮箱地址查找回退信息
     * @param app   app名字
     * @param addr  邮箱地址
     * @return 
     * @see
     */
    public String findbackInfoByAppAndEmailAddr(String app, String addr) {
        List<BounceEmailLog> list = new ArrayList<>();
        if (StringUtils.isNoneBlank(addr)) {
            list = dao.findByAppAndOriEmaTo(app, addr);
        } else {
            list = dao.findByApp(app);
        }
        return JSON.toJSONString(list);
    }

    /**
     * Description: <br>
     * 封装条件参数
     * 
     * @param rInfo
     * @return 
     * @see
     */
	private Map<String, Object> searchParam(
			BounceEmailLog rInfo) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(rInfo.getOriEmaSubject())) {
			paraMap.put("oriEmaSubject", "%" + rInfo.getOriEmaSubject() + "%");
		}
		if (StringUtils.isNotBlank(rInfo.getStartTime())) {
			Date start = DateUtils.formatStringToDate(
					rInfo.getStartTime() + TimeConstant.START_SUFFIX);
			paraMap.put("start", start);
		}
		if (StringUtils.isNotBlank(rInfo.getEndTime())) {
			Date end = DateUtils.formatStringToDate(
					rInfo.getEndTime() + TimeConstant.END_SUFFIX);
			paraMap.put("endTime", end);
		}
		if (StringUtils.isNotBlank(rInfo.getOriEmaTo())) {
			paraMap.put("oriEmaTo", rInfo.getOriEmaTo());
		}
		return paraMap;
	}
}
