package com.sdp.cop.octopus.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.sdp.cop.octopus.dao.MessTemplateDAO;
import com.sdp.cop.octopus.entity.MessTemplate;
import com.sdp.frame.util.ResponseMessage;

@Controller
@RequestMapping(value = { "/messageTemplate" })
public class MessageTemplateController {

	@Autowired
	private MessTemplateDAO messTemplateDAO;

	private static final Logger LOG = LoggerFactory.getLogger(MessageTemplateController.class);

	/**
	 * 创建模板
	 * 
	 * @param messageEntity
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Status> createMessTemplate(@RequestBody MessTemplate entity) {
		strongKeys(entity);
		if (messTemplateDAO.insertSelective(entity) < 1) {
			return new ResponseEntity<Status>(new Status("创建失败", 422), HttpStatus.OK);
		} else {
			return new ResponseEntity<Status>(new Status("创建成功", 200), HttpStatus.EXPECTATION_FAILED);
		}
	}

	private void strongKeys(MessTemplate entity) {
		if (entity != null) {
			String contentString = entity.getContent();

			StringBuffer buffer = new StringBuffer();
			long start = System.currentTimeMillis();
			Pattern pattern = Pattern.compile("\\{[^}]*\\}");
			Matcher matcher = pattern.matcher(contentString);
			while (matcher.find()) {
				String s = matcher.group();// 得到匹配的结果
				buffer.append("," + s);
			}
			String keys = buffer.deleteCharAt(0).toString();
			System.out.println(System.currentTimeMillis() - start);
			entity.setMultKeys(keys);
		}

	}

	/**
	 * 更新模板内容
	 * 
	 * @param messages
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Status> update(@RequestBody MessTemplate entity) throws Exception {
		try {
			strongKeys(entity);
			if (messTemplateDAO.updateByPrimaryKey(entity) < 1) {
				return new ResponseEntity<Status>(new Status("更新失败", 422), HttpStatus.EXPECTATION_FAILED);
			} else {
				return new ResponseEntity<Status>(new Status("更新成功", 200), HttpStatus.OK);
			}
		} catch (Exception e) {
			ResponseMessage.createFailMessage(e.getMessage());
			throw e;
		}
	}

	/**
	 * 删除模板内容
	 * 
	 * @param messages
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Status> delete(@PathVariable("id") Long id) throws Exception {
		try {
			if (messTemplateDAO.deleteByPrimaryKey(id) < 1) {
				return new ResponseEntity<Status>(new Status("删除失败", 422), HttpStatus.EXPECTATION_FAILED);
			} else {
				return new ResponseEntity<Status>(new Status("删除成功", 200), HttpStatus.OK);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 根据id得到模板内容
	 * 
	 * @param messages
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<MessTemplate> find(@PathVariable("id") Long id) throws Exception {
		try {
			return new ResponseEntity<MessTemplate>(messTemplateDAO.selectByPrimaryKey(id), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 得到全部模板
	 * 
	 * @param messages
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<MessTemplate>> find() throws Exception {
		try {
			return new ResponseEntity<List<MessTemplate>>(messTemplateDAO.selectAll(), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

	/**
	 * 根据条件查询模板
	 * 
	 * @param messages
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findByCondition", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<MessTemplate>> findByCondition(@RequestBody MessTemplate entity) throws Exception {
		try {
			return new ResponseEntity<List<MessTemplate>>(messTemplateDAO.selectByCondition(entity), HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw e;
		}
	}

}
