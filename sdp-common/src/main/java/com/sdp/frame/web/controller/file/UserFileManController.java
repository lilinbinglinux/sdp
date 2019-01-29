package com.sdp.frame.web.controller.file;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sdp.frame.util.PropertyUtil;

@Controller
@RequestMapping("/file")
public class UserFileManController extends AbstractorUploadController {
	
	private Logger log = Logger.getLogger(UserFileManController.class);
	
	@Override
	protected String getFilePath() {
			return PropertyUtil.getPropertiesValue("system", "CRUSER_FILE");
	}
	
}
