package com.sdp.frame.web.controller.frame;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.frame.util.JsonUtils;
import com.sdp.frame.util.OnlineUserList;
import com.sdp.frame.util.ResponseMessage;
import com.sdp.frame.util.UserUtil;
import com.sdp.frame.web.entity.log.ResourceLog;
import com.sdp.frame.web.entity.resources.Resources;
import com.sdp.frame.web.service.log.ResourcesLogService;
import com.sdp.frame.web.service.notice.NoticeService;

/** 
 * @author 作者: jxw 
 * @date 创建时间: 2016-10-4 上午11:31:02 
 * @version 版本: 1.0 
 */


@Controller      
@RequestMapping("/")
public class FrameController {
	
	@Autowired
	private ResourcesLogService resourcesLogService;
	
	@Resource
	private NoticeService noticeService;
	
	@RequestMapping(value={"/platform/index"},method=RequestMethod.GET)
	public String index(HttpServletRequest request,Model model){
		List<Resources> list = UserUtil.getUserMenuResource(request);
		String jsonString = JsonUtils.toJSONString(list);
		Map map = noticeService.selectUnreadNotice(UserUtil.getUserResource(request).getUserId());
		model.addAttribute("userMenuTree", jsonString);
		model.addAttribute("onlineUserCount", OnlineUserList.getInstance().getUserMap().size());
		model.addAttribute("unreadNoticeList", map.get("data"));
		model.addAttribute("unreadNoticeCount", map.get("total"));
		return "frame";
	}
	
	
	@RequestMapping(value="/platform/doResourceLog",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage doResourceLog(HttpServletRequest request,String resourcesId){
		ResourceLog log = new ResourceLog();
		log.setResourcesId(resourcesId);
		log.setUserId(UserUtil.getUserResource(request).getLoginId());
		resourcesLogService.doLog(log);
		return ResponseMessage.createSuccessMessage("logSuccess");
	}
	
	
	
}
