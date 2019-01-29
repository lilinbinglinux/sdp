package com.sdp.servflow.logSer.log.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.sdp.common.page.Pagination;
import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.logSer.log.model.*;
import com.sdp.servflow.logSer.log.service.CodeTableService;
import com.sdp.servflow.logSer.log.service.LogRecordService;
import com.sdp.servflow.logSer.log.service.LogSerTotalStatisticsService;
import com.sdp.servflow.logSer.log.service.OrderTotalStatisticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/TBtest")
public class LogPageController {
	
	@Autowired
	private LogRecordService logRecordService;
	
	@Autowired
	private OrderTotalStatisticsService orderTotalStatisticsService;
	
	@Autowired
	private CodeTableService codeTableService;
	
	@Autowired
	private LogSerTotalStatisticsService logSerTotalStatisticsService;
	
	@RequestMapping("/logindex")
    public String index(){
        return "logSer/log/logindex";
    }

    @RequestMapping(value="/serinfo",method = RequestMethod.POST)
	@ResponseBody
	public Pagination serinfo(HttpServletRequest request,Pagination page,String name,String sendDate){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name",name);
		int total = logSerTotalStatisticsService.selectCountSer(map);
		if(total > 0){
			int startNum=(int) (page.getPageSize()*(page.getPageNo()-1));
			int num=page.getPageSize();
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("name",name);
			map1.put("sendDate",sendDate);
			map1.put("startNum",String.valueOf(startNum));
			map1.put("num",String.valueOf(num));
			List<Ser_Id_Version_Statistics> list = logSerTotalStatisticsService.selectserVersion(map1);
			page.setList(list);
			page.setTotalCount(Long.valueOf(total));
		}
    	return page;
	}

	/**
	 * 日志监控页面，，暂时使用三表联查查询，查询效率低时可在订阅表中加一个服务名称的冗余字段
	 * @param request
	 * @param page
	 * @param name
	 * @param sendDate
	 * @return
	 */
	@RequestMapping(value="/listyes",method=RequestMethod.POST)
	@ResponseBody
    public Pagination listyes(HttpServletRequest request,Pagination page,String name, String sendDate, String ser_id,String ser_version){
		String tenant_id=null;
		try {
//			tenant_id=CurrentUserUtils.getInstance().getUser().getTenantId();
		} catch (NullPointerException e) {
		}
		String login_id=null;
		try {
//			login_id=CurrentUserUtils.getInstance().getUser().getLoginId();
		} catch (NullPointerException e) {
		}
		// 卡当前租户和用户下面的日志
		Map<String, String> map0 = new HashMap<String, String>();
		map0.put("tenant_id",tenant_id);
		map0.put("login_id",login_id);
		map0.put("name",name);
		map0.put("ser_id",ser_id);
		map0.put("ser_version",ser_version);
		map0.put("sendDate",sendDate);
		int total=orderTotalStatisticsService.selectCountFromOrderTotalSta(map0);
		if(total > 0){
			int startNum=(int) (page.getPageSize()*(page.getPageNo()-1));
			int num=page.getPageSize();
			Map<String, String> map = new HashMap<String, String>();
			map.put("tenant_id",tenant_id);
			map.put("login_id",login_id);
			map.put("name",name);
			map.put("ser_id",ser_id);
			map.put("ser_version",ser_version);
			map.put("sendDate",sendDate);
			map.put("startNum",String.valueOf(startNum));
			map.put("num",String.valueOf(num));
			List<OrderTotalStatistics> list = orderTotalStatisticsService.selectDataFromOrderTotalSta(map);
			page.setList(list);
			page.setTotalCount(Long.valueOf(total));
		}
        return page;
    }

    @RequestMapping(value="/getMsg",method = RequestMethod.POST)
	@ResponseBody
	public List<CodeTable> getMsg(OrderInfoStatistics serVersionStatistics,HttpServletRequest requet){
		List<CodeTable> list = codeTableService.selectFailMsg(serVersionStatistics);
		return list;
	}

	@RequestMapping(value="/getSendStatus", method = RequestMethod.POST)
	@ResponseBody
	public Pagination getSendStatus(LogBean logBean, Pagination page,String time,Integer count){
		String str = logBean.getDayTime().toString();
		if(StringUtil.isNotEmpty(time)){
			String date = str.substring(0,4) + "-" + str.substring(4,6) + "-" + str.substring(6,8)+ " ";
			logBean.setStartTimeStr(date + time.substring(0,8));
			logBean.setEndTimeStr(date + time.substring(11,19));
		}
		//int total = logRecordService.selectCountSendLog(logBean);
		if(count > 0){
		    int startNum = (int) (page.getPageSize()*(page.getPageNo()-1));
		    int num = page.getPageSize();
		    List<LogBean> list = logRecordService.selectSendLog(logBean,startNum,num);
            page.setList(list);
            page.setTotalCount(Long.valueOf(count));
        }
		return page;
	}

	@RequestMapping(value="/getLogDetail",method = RequestMethod.POST)
	@ResponseBody
	public Pagination selectLogDetail(LogBean logBean,Pagination page){
		List<LogBean> list = logRecordService.selectLogDetail(logBean);
		int total = list.size();
		if(total > 0){
			//List<LogBean> list = logManageService.selectLogDetail(logBean);
			page.setList(list);
			page.setTotalCount(Long.valueOf(total));
		}
		return page;
	}


}