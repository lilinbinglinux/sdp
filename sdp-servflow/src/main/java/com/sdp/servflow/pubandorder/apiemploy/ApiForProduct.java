package com.sdp.servflow.pubandorder.apiemploy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.sdp.servflow.pubandorder.apiemploy.apiversion.ApiVersion;
import com.sdp.servflow.pubandorder.util.EncryptionUtil;
@RestController
@RequestMapping("/{version}/apisEmploy/apis")
public class ApiForProduct {

    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/encryptionForMd5", method = {RequestMethod.GET})
    public String apishopEmployGet(String seedStr,String allocationkey)
    		throws Exception {
    	JSONObject json= new JSONObject();
    	String str = seedStr+allocationkey;
    	String key =EncryptionUtil.MD5(str);
    	json.put("key", key);
    	return json.toJSONString();
    }

}
