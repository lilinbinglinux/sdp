package com.sdp.servflow.pubandorder.apiemploy;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.sdp.servflow.pubandorder.apiemploy.apiversion.ApiVersion;

import net.sf.json.JSONObject;

/**
 * 
 * 对外暴露接口的验证和映射
 *
 * @author ZY
 * @version 2017年7月25日
 * @see ApisEmployController
 * @since
 */
@RestController
@RequestMapping("/{version}/apisEmploy")
public class ApisExampleController {
 
    
    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Object getUser(String phoneNumber) throws Exception{
         System.out.println("----测试接口-"+ phoneNumber);
        JSONObject json = new JSONObject();
        json.put("phoneNumber", phoneNumber);
        json.put("custName", "张三");
        if(phoneNumber.endsWith("4"))
        {
            json.put("serviceType", "4G");
        }else{
            json.put("serviceType", "23G");
        }
        json.put("cityId", "760");
        json.put("payMode", "预付费");
        return json;
    }
    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/preCommit", method = RequestMethod.POST)
    public Object preCommit(@RequestBody String req ) throws Exception{
         System.out.println("----测试接口-"+req);
        JSONObject json = new JSONObject();
        json.put("acctFee","78.89" );
        json.put("orderId", "fsdf"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        return json;
    }
    @ApiVersion(1)
    @ResponseBody
    @RequestMapping(value = "/preCommitList", method = RequestMethod.POST)
    public Object preCommitList(@RequestBody String req ) throws Exception{
         System.out.println("----测试接口-"+req);
        
        
        JSONObject json = new JSONObject();
        json.put("acctFee","78.89" );
        json.put("orderId", "fsdf1"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        
        JSONObject json2 = new JSONObject();
        json2.put("acctFee","1234" );
        json2.put("orderId", "fsdf2"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        JSONObject json3 = new JSONObject();
        json3.put("acctFee","852" );
        json3.put("orderId", "fsdf3"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        
        JSONArray array = new JSONArray();
        array.add(json);
        array.add(json2);
        array.add(json3);
        return array;
    }
    @ResponseBody
    @RequestMapping(value = "/afterCommit", method = RequestMethod.POST)
    public Object afterCommit(@RequestBody String req ) throws Exception{
         System.out.println("----测试接口-"+req);
        JSONObject json = new JSONObject();
        json.put("code","00000" );
        json.put("result", "4G承诺低消办理成功");
        return json;
    }
    
    @ResponseBody
    @RequestMapping(value = "/order23G", method = RequestMethod.POST)
    public Object order23G(@RequestBody String req ) throws Exception{
         System.out.println("----测试接口-"+req);
        JSONObject json = new JSONObject();
        json.put("code","00000" );
        json.put("result", "23G承诺低消办理成功");
        return json;
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public Object getToken(@RequestBody String req ) throws Exception{
        com.alibaba.fastjson.JSONObject reqJSon = com.alibaba.fastjson.JSONObject.parseObject(req);
        JSONObject json = new JSONObject();
        
      /*  if(reqJSon.get("key") ==null
            ||reqJSon.get("appId")==null
            )
        {
            json.put("code", "99999");
            json.put("msg","缺少信息");
        }*/
        
        json.put("key",reqJSon.get("key") );
        json.put("access_token", "fsdf"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        json.put("token_type", "code");
        return json;
    }
}
