/*
 * 文件名：WeixinService.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月12日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.service;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdp.cop.octopus.constant.MsgTypeEnum;
import com.sdp.cop.octopus.constant.SendModEnum;
import com.sdp.cop.octopus.dao.SendRecordDao;
import com.sdp.cop.octopus.entity.AccessToken;
import com.sdp.cop.octopus.entity.OctopusResult;
import com.sdp.cop.octopus.entity.SendRecordInfo;
import com.sdp.cop.octopus.util.ExceptionUtil;
import com.sdp.cop.octopus.util.HttpClientUtil;


/**
 * 微信推送消息service
 * @author zhangyunzhen
 * @version 2017年7月12日
 * @see WeixinService
 * @since
 */
@Service
public class WeixinService {

    /**
     * 新增图片素材
     */
    @Value("${add_material_url}")
    private String uploadTempMateria_url;

    /**
     * 群发文本消息API
     */
    @Value("${massMessage_url}")
    private String massMessage_url;

    /**
     * sendRecordDao
     */
    @Autowired
    private SendRecordDao sendRecordDao;

    /**
     * 微信域名
     */
    @Value("${weixin.host}")
    private String host;
    
    /**
     * Description: <br>
     * 上传图片素材
     * @param file  上传文件
     * @param request request
     * @return  media_id
     * @see
     */
    public String uploadPicture(MultipartFile file, HttpServletRequest request)
        throws Exception {
        uploadTempMateria_url = uploadTempMateria_url.replace("ACCESS_TOKEN",
            AccessToken.ACCESS_TOKEN).replace("TYPE", MsgTypeEnum.image.toString());
        //1.先上传到本地临时文件夹
        File tempFile = tempStore(file, request);
        if (tempFile == null) {
            return JSON.toJSONString(OctopusResult.build(400, "未发现图片"));
        }
        //2.调用微信上传图片素材API
        String result = HttpClientUtil.uploadFile(tempFile, uploadTempMateria_url);
        tempFile.delete();

        //3.处理结果
        String media_id = "";
        if (StringUtils.isNoneBlank(result)) {
            JSONObject jsonObject = JSON.parseObject(result);
            media_id = (String)jsonObject.get("media_id");
        }
        return media_id;
    }

    /**
     * Description: <br>
     *  群发文本消息
     * @param content 文本消息
     * @return 
     * @see
     */
    public String massText(String content, String appName) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> textMap = new HashMap<>();
        massMessage_url = massMessage_url.replace("ACCESS_TOKEN", AccessToken.ACCESS_TOKEN);
        SendRecordInfo info = new SendRecordInfo("", "", "文本消息 ： " + content,
            SendModEnum.weiChat.toString(), new Date(), null, appName);

        try {
            //封装请求参数
            filterMap.put("is_to_all", true);
            filterMap.put("tag_id", "");
            textMap.put("content", content);
            map.put("filter", filterMap);
            map.put("text", textMap);
            map.put("msgtype", MsgTypeEnum.text.toString());
            String param = JSON.toJSONString(map);
            String result = HttpClientUtil.doPostJson(massMessage_url, param);
            JSONObject jsonObject = JSON.parseObject(result);
            Integer status = (Integer)jsonObject.get("errcode");
            String msg = (String)jsonObject.get("errmsg");

            if (status != null && status != 0) {
                info.setErrorlog("发送失败 返回码：" + status + "    msg:" + msg);
            }
            sendRecordDao.save(info);
            return JSON.toJSONString(OctopusResult.build(status, msg));
        } catch (Exception e) {
            e.printStackTrace();
            info.setErrorlog(ExceptionUtil.getStackTrace(e).substring(0, 4000));
            sendRecordDao.save(info);
            return JSON.toJSONString(OctopusResult.build(500, "发送失败"));
        }
    }

    /**
     * Description: <br>
     *  群发图片消息
     * 
     * @param file 上传图片
     * @param request request
     * @param appName app
     * @return 
     * @see
     */
    public String massMsg(MultipartFile file, HttpServletRequest request, String appName) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> filterMap = new HashMap<>();
        Map<String, Object> imageMap = new HashMap<>();
        SendRecordInfo info = null;
        try {
            //上传图片素材
            String media_id = uploadPicture(file, request);
            System.out.println("=====media_id: "+media_id);
            
            massMessage_url = massMessage_url.replace("ACCESS_TOKEN",
                AccessToken.ACCESS_TOKEN);
            info = new SendRecordInfo("", "", "图片信息 （media_id）： " + media_id,
                SendModEnum.weiChat.toString(), new Date(), null, appName);

            //封装请求参数
            filterMap.put("is_to_all", true);
            filterMap.put("tag_id", "");
            imageMap.put("media_id", media_id);
            map.put("filter", filterMap);
            map.put("image", imageMap);
            map.put("msgtype", MsgTypeEnum.image.toString());
            String param = JSON.toJSONString(map);
            System.out.println("===群发图片参数："+param);
            //群发
            String result = HttpClientUtil.doPostJson(massMessage_url, param);
            JSONObject jsonObject = JSON.parseObject(result);
            Integer status = (Integer)jsonObject.get("errcode");
            String msg = (String)jsonObject.get("errmsg");

            if (status != null && status != 0) {
                info.setErrorlog("发送失败 返回码：" + status + "    msg:" + msg);
            }
            sendRecordDao.save(info);
            return JSON.toJSONString(OctopusResult.build(status, msg));
        } catch (Exception e) {
            e.printStackTrace();
            info.setErrorlog(ExceptionUtil.getStackTrace(e).substring(0, 4000));
            sendRecordDao.save(info);
            return JSON.toJSONString(OctopusResult.build(500, "发送失败"));
        }
    }

    /**
     * Description: <br>
     *      上传到临时文件夹
     * @param file
     * @return 
     * @throws IOException 
     * @throws IllegalStateException 
     * @see
     */
    private File tempStore(MultipartFile file, HttpServletRequest request)
        throws IllegalStateException, IOException {
        //先上传到本地
        if (file.isEmpty()) {
            return null;
        }
        String path = request.getServletContext().getRealPath("/");
        String orifilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString()
                          + orifilename.substring(orifilename.lastIndexOf("."));
        File fileDir = new File(path + "temp");
        //检查文件夹是否存在
        if (!fileDir.isDirectory()) {
            fileDir.mkdir();
        }
        File tempFile = new File(path + "temp", filename);

        file.transferTo(tempFile);

        return tempFile;
    }

}
