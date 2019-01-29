package com.sdp.cop.octopus.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sdp.common.entity.Status;
import com.sdp.common.page.Pagination;
import com.sdp.cop.octopus.constant.TimeConstant;
import com.sdp.cop.octopus.dao.MessTemplateDAO;
import com.sdp.cop.octopus.dao.MessageDAO;
import com.sdp.cop.octopus.entity.MessTemplate;
import com.sdp.cop.octopus.entity.Message;
import com.sdp.cop.octopus.util.DateUtils;
import com.sdp.frame.util.ResponseMessage;
import com.sdp.serviceAccess.exception.ProductAccessException;
import com.sdp.serviceAccess.util.JsonXMLUtils;

@Controller
@RequestMapping(value = { "/message" })
public class MessageController {

	@Autowired
	private MessageDAO messageDAO;

	@Autowired
	private MessTemplateDAO messTemplateDAO;

	private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

	/**
	 * 发送站内信
	 * 
	 * @param messageEntity
	 * @return
	 */
	@RequestMapping(value = "/send", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Status> sendMail(@RequestBody Message messageEntity) {

		if (messageEntity.getMessSendTime() == null) {
			messageEntity.setMessSendTime(new Date());
		}

		if (messageDAO.insertSelective(messageEntity) > 0) {
			return new ResponseEntity<Status>(new Status("发送成功", 200), HttpStatus.OK);
		} else {
			return new ResponseEntity<Status>(new Status("发送失败", 422), HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * 根据模板发送站内信
	 * 
	 * @param messageEntity
	 * @return
	 */
	@RequestMapping(value = "/sendMailByTempl/{templId}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> sendMailByTempl(@RequestBody Map<String, Object> param,
			@PathVariable("templId") Long templId) {
		try {
			Message messageEntity = JsonXMLUtils.map2obj((Map<String, Object>) param.get("mess"), Message.class);
			Map<String, String> paraMap = JsonXMLUtils.map2obj((Map<String, String>) param.get("paraMap"), Map.class);
			String content = getContent(templId, paraMap);
			if (content == null) {
				return new ResponseEntity<Status>(new Status("发送失败，请检查模板内容", 422), HttpStatus.EXPECTATION_FAILED);
			}

			messageEntity.setMessContent(content);
			if (messageEntity.getMessSendTime() == null) {
				messageEntity.setMessSendTime(new Date());
			}
			if (messageDAO.insertSelective(messageEntity) > 0) {
				return new ResponseEntity<Status>(new Status("发送成功", 200), HttpStatus.OK);
			} else {
				return new ResponseEntity<Status>(new Status("发送失败", 422), HttpStatus.EXPECTATION_FAILED);
			}
		} catch (Exception e) {
			throw new ProductAccessException(e.getMessage(), e);
		}

	}

	/**
	 * 根据模板id得到和参数Map得到处理后的消息
	 * 
	 * @param templId
	 * @param paraMap
	 * @return
	 */
	private String getContent(Long templId, Map<String, String> paraMap) {

		String content = null;
		if (paraMap != null && paraMap.size() > 0) {

			MessTemplate template = messTemplateDAO.selectByPrimaryKey(templId);
			if (template != null) {

				content = template.getContent();
				for (String key : paraMap.keySet()) {
					String value = paraMap.get(key);
					content = content.replace(key, value);
				}

			}
		}
		return content;
	}

	/**
	 * 批量设置消息状态
	 * 
	 * @param messages
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/BatchSetMessFlag", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> BatchSetMessFlag(@RequestBody List<Message> messages) throws Exception {
		try {
			return new ResponseEntity<Status>(messageDAO.bathUpdateFlag(messages), HttpStatus.OK);
		} catch (Exception e) {
			ResponseMessage.createFailMessage(e.getMessage());
			throw e;
		}
	}

	/**
	 * 分页查找站内信
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByPagerMessage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Pagination> findByPagerMessage(@RequestBody Map<String, Object> param) throws Exception {
		try {
			Pagination page = JsonXMLUtils.map2obj((Map<String, Object>) param.get("page"), Pagination.class);
			Message record = JsonXMLUtils.map2obj((Map<String, Object>) param.get("record"), Message.class);
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> spec = searchParam(record);
			return new ResponseEntity<Pagination>(messageDAO.findRecordPage(spec, page), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}

	}

	/**
	 * 根据条件查询结果数量
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/getCnt", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Integer> getCnt(@RequestBody Message record) {

		Map<String, Object> spec = searchParam(record);
		Integer count = messageDAO.findCnt(spec);
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}

	/**
	 * 构建search参数
	 * 
	 * @param mInfo
	 * @return
	 */
	private Map<String, Object> searchParam(Message mInfo) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(mInfo.getMessSenderId())) {
			paraMap.put("messSenderId", mInfo.getMessSenderId());
		}
		if (StringUtils.isNotBlank(mInfo.getMessReceiverId())) {
			paraMap.put("messReceiverId", mInfo.getMessReceiverId());
		}
		if (StringUtils.isNotBlank(mInfo.getMessTitle())) {
			paraMap.put("messTitle", "%" + mInfo.getMessTitle() + "%");
		}
		if (StringUtils.isNotBlank(mInfo.getMessType())) {
			paraMap.put("messType", mInfo.getMessType());
		}
		if (StringUtils.isNotBlank(mInfo.getMessStartSendTime())) {
			Date start = DateUtils.formatStringToDate(mInfo.getMessStartSendTime() + TimeConstant.START_SUFFIX);
			paraMap.put("messStartSendTime", start);
		}
		if (StringUtils.isNotBlank(mInfo.getMessEndSendTime())) {
			Date end = DateUtils.formatStringToDate(mInfo.getMessEndSendTime() + TimeConstant.END_SUFFIX);
			paraMap.put("messEndSendTime", end);
		}
		if (StringUtils.isNotBlank(mInfo.getMessFlag())) {
			paraMap.put("messFlag", mInfo.getMessFlag());
		}
		if (StringUtils.isNotBlank(mInfo.getMessContent())) {
			paraMap.put("messContent", mInfo.getMessContent());
		}
		return paraMap;
	}
}
