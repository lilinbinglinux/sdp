package com.sdp.frame.web.controller.log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.frame.util.JsonUtils;
import com.sdp.frame.util.SystemPropertiesUtils;
import com.sdp.frame.web.service.log.UserLoginLogService;

/**
 * Description:登录日志
 * 
 * @author niu
 */
@Controller
@RequestMapping("/userLoginLog")
public class UserLoginLogController {
    
    /**
     * UserLoginLogService
     */
    @Autowired
	private UserLoginLogService userLoginLogService;

    /**
     * 
     * Description:返回登录页面
     * 
     *@return String
     *
     * @see
     */
    @RequestMapping("/index")
	public String index() {
        return "log/userLoginLog";
    }

    /**
     * 
     * Description:返回查询值
     * 
     *@return Map
     *
     * @see
     */
    @ResponseBody
	@RequestMapping(value="/selectPage",method=RequestMethod.POST)
	public Map selectPage(String start, String length, String jsonStr) {
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
        HashMap map = (HashMap) userLoginLogService.selectAll(start, length, paramMap);
        return map;
    }
	
    /**
     * 
     * Description:定时清除登录日志
     * 
     * @see
     */
    @Scheduled(cron="0 0 1/360 * * ?")
	public void deleteLoginMessage() throws IOException{
        SystemPropertiesUtils dateNum = new SystemPropertiesUtils();
        int date = Integer.parseInt(dateNum.getDate());
	    userLoginLogService.deleteLoginMessage(date);
    }
	
}
