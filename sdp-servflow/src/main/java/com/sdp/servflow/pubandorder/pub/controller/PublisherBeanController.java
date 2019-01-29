package com.sdp.servflow.pubandorder.pub.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.IdUtil;
import com.sdp.frame.util.JsonUtils;
import com.sdp.servflow.pubandorder.pub.model.PublisherBean;
import com.sdp.servflow.pubandorder.pub.service.PublisherBeanService;

/**
 * 
 * 处理发布接口请求Controller
 *
 * @author ZY
 * @version 2017年6月10日
 * @see PublisherBeanController
 * @since
 */
@Controller
@RequestMapping("/publisher")
public class PublisherBeanController {
	
    /**
     * publisherService
     */
    @Autowired
	private PublisherBeanService publisherService;
	
	
	/**
	 * 
	 * Description: 分页获取API详情
	 * 
	 *@param start
	 *@param length
	 *@param jsonStr
	 *@return Map
	 *
	 * @see
	 */
    @ResponseBody
	@RequestMapping("/selectPage")
    public Map selectPage(String start, String length, String jsonStr) {
    	@SuppressWarnings("unchecked")
        Map<String, Object> paramMap = JsonUtils.stringToCollect(jsonStr);
    	//查询当前用户下的数据
    	paramMap.put("login_id",CurrentUserUtils.getInstance().getUser().getLoginId());
    	return publisherService.selectPage(start, length, paramMap);
    }
    
	/**
	 * 
	 * Description: 添加API
	 * 
	 *@param publisher
	 *@param request
	 *@return int
	 *
	 * @see
	 */
    @ResponseBody
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
    public int insert(PublisherBean publisher) {
        if(publisher.getPubid() == "" || publisher.getPubid() == null){
            publisher.setPubid(IdUtil.createId());
        }
        publisher.setCreatedate(new Date());
        publisher.setTenant_id(CurrentUserUtils.getInstance().getUser().getTenantId());
        publisher.setLogin_id(CurrentUserUtils.getInstance().getUser().getLoginId());
        
    	return publisherService.insert(publisher);
    }
	
	/**
	 * 
	 * Description: 根据Id查询，用于update回显 
	 * 
	 *@param id
	 *@return PublisherBean
	 *
	 * @see
	 */
    @ResponseBody
    @RequestMapping(value="/getPubById",method=RequestMethod.POST)
    public PublisherBean getPubById(String id){
        return publisherService.getPubById(id);
    }
	
	/**
	 * 
	 * Description:更新数据库信息 
	 * 
	 *@param publisher
	 *@return int
	 *
	 * @see
	 */
    @ResponseBody
    @RequestMapping(value="/update",method=RequestMethod.POST)
    public int update(PublisherBean publisher){
    	return publisherService.update(publisher);
    }
	
	/**
	 * 
	 * Description:根据主键删除 
	 * 
	 *@param pubid
	 *@return int
	 *@throws Exception int
	 *
	 * @see
	 */
    @ResponseBody
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public int  delete(String pubid) throws Exception{
        return publisherService.deleteByPubId(pubid);
    }
    
}
