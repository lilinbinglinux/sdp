/*
 * 文件名：EnterpriseWeiChatService.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月18日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sdp.common.entity.EnterpriseWeixinProp;
import com.sdp.cop.octopus.constant.MsgTypeEnum;
import com.sdp.cop.octopus.constant.SendModEnum;
import com.sdp.cop.octopus.dao.AppBindDepartmentDao;
import com.sdp.cop.octopus.dao.SendRecordDao;
import com.sdp.cop.octopus.entity.AccessToken;
import com.sdp.cop.octopus.entity.AppBindDepartmentInfo;
import com.sdp.cop.octopus.entity.EnterWeixinDepartmentBean;
import com.sdp.cop.octopus.entity.EnterWeixinMessageBean;
import com.sdp.cop.octopus.entity.EnterWeixinUserBean;
import com.sdp.cop.octopus.entity.EnterWeixinUserDetailBean;
import com.sdp.cop.octopus.entity.OctopusResult;
import com.sdp.cop.octopus.entity.SendRecordInfo;
import com.sdp.cop.octopus.util.ExceptionUtil;
import com.sdp.cop.octopus.util.HttpClientUtil;


/**
 * 微信企业号Service
 * @author zhangyunzhen
 * @version 2017年7月18日
 * @see EnterpriseWeiChatService
 * @since
 */
@Service
public class EnterpriseWeiChatService {

    /**
     * 企业微信配置文件
     */
    @Autowired
    private EnterpriseWeixinProp prop;

    /**
     * app绑定信息dao
     */
    @Autowired
    private AppBindDepartmentDao dao;

    /**
     * SendRecordDao
     */
    @Autowired
    private SendRecordDao sendDao;

    /**
     * Description: <br>
     * 根据app获取部门信息
     * 
     * @param app
     * @return 
     * @see
     */
    public String getDepartmentAnduserByApp(String app) {
        List<EnterWeixinDepartmentBean> beans = new ArrayList<>();
        Object result = null;
        //1.获取app对应的部门id
        List<AppBindDepartmentInfo> list = dao.findByApp(app);
        if (list == null || list.isEmpty()) {
            result = OctopusResult.build(401, "无发送权限", null);
        } else {
            for (AppBindDepartmentInfo info : list) {
                EnterWeixinDepartmentBean bean = new EnterWeixinDepartmentBean();
                //2.根据部门id获取部门成员
                List<EnterWeixinUserBean> users = getUserListByDepartmentId(
                    info.getDepartmentId());
                //3.组装结果参数
                bean.setDepartmentId(info.getDepartmentId());
                bean.setDepartmentName(info.getDepartmentName());
                bean.setUserlist(users);
                beans.add(bean);
            }
            result = beans;
        }
        return JSON.toJSONString(result);
    }

    /**
     * Description: <br>
     *  获取部门列表
     * @param id 部门id（可选）
     * @return json数据
     * @see
     */
    public String getDepartmentList(String id) {
        if (!StringUtils.isNotBlank(id)) {
            id = "ID";
        }
        String requetUrl = prop.getDepartmentlistUrl().replaceAll("ACCESS_TOKEN",
            AccessToken.ENTERPRISE_WEIXIN_ACCESS_TOKEN_ADDRESSLIST).replaceAll("ID", id);
        String result = HttpClientUtil.doGetWithSSl(requetUrl, prop.getHost());
        return result;
    }

    /**
     * Description: <br>
     *  发送消息
     * @param app app名字
     * @param user userID集合
     * @param partment 部门ID集合
     * @param content 消息内容
     * @return 
     * @see
     */
    public String sendMessage(String app, String user, String partment, String content) {
        OctopusResult octopusResult;
        SendRecordInfo recordInfo = new SendRecordInfo();
        try {
            octopusResult = null;
            //权限检验
            boolean flag = checkDepartment(app, partment);
            if (!flag) return JSON.toJSONString(OctopusResult.build(401, "部门id无效或无权限给某部门发消息"));
            flag = checkUser(app, user);
            if (!flag) return JSON.toJSONString(OctopusResult.build(401, "用户id无效或无权限给某用户发消息"));
            
            //封装发送消息参数
            EnterWeixinMessageBean bean = new EnterWeixinMessageBean();
            bean.setAgentid(prop.getAgentidCopc());
            bean.setMsgtype(MsgTypeEnum.text.toString());
            Map<String, String> map = new HashMap<>();
            map.put("content", content);
            bean.setText(map);
            bean.setSafe(0);
            bean.setToparty(partment);
            bean.setTouser(user);
            String jsonString = JSON.toJSONString(bean);
            //封装发送记录参数
            recordInfo.setAppname(app);
            recordInfo.setContent(content);
            recordInfo.setReceiver("userId: "+user+"  departmentId: " +partment);
            recordInfo.setSender(app);
            recordInfo.setType(SendModEnum.weiChat.toString());
            
            String result = sendMessage(jsonString);
            JSONObject object = JSON.parseObject(result);
            if ((Integer)object.get("errcode") == 0) {
                octopusResult = OctopusResult.build(200, "发送成功");
            } else {
                octopusResult = OctopusResult.build(500, "发送失败");
                recordInfo.setErrorlog((String)object.get("errmsg"));
            }
        } catch (Exception e) {
            recordInfo.setErrorlog(ExceptionUtil.getStackTrace(e).substring(0, 4000));
            sendDao.save(recordInfo);
            octopusResult = OctopusResult.build(500, "发送失败");
            return JSON.toJSONString(octopusResult);
        }
        sendDao.save(recordInfo);
        return JSON.toJSONString(octopusResult);
    }

    /**
     * Description: <br>
     * 获取部门成员
     * @param departmentId 部门id
     * @return json字符串
     * @see
     */
    public List<EnterWeixinUserBean> getUserListByDepartmentId(Integer departmentId) {
        List<EnterWeixinUserBean> list = null;
        String requetUrl = prop.getUserSimplelistUrl().replaceAll("ACCESS_TOKEN",
            AccessToken.ENTERPRISE_WEIXIN_ACCESS_TOKEN_ADDRESSLIST).replaceAll("DEPARTMENT_ID",
                departmentId.toString()).replaceAll("FETCH_CHILD", "1");
        String result = HttpClientUtil.doGetWithSSl(requetUrl, prop.getHost());

        JSONObject jsonObject = JSON.parseObject(result);
        Integer errcode = (Integer)jsonObject.get("errcode");
        if (errcode == 0) {
            String userlist = jsonObject.getString("userlist");
            list = JSON.parseArray(userlist, EnterWeixinUserBean.class);
        }
        return list;
    }

    /**
     * Description: <br>
     * 获取部门成员详情
     * @param departmentId 部门id
     * @return json字符串
     * @see
     */
    public List<EnterWeixinUserDetailBean> getUserDetailListByDepartmentId(Integer departmentId) {
        List<EnterWeixinUserDetailBean> list = null;
        String requetUrl = prop.getUserDetaillistUrl().replaceAll("ACCESS_TOKEN",
            AccessToken.ENTERPRISE_WEIXIN_ACCESS_TOKEN_ADDRESSLIST).replaceAll("DEPARTMENT_ID",
                departmentId.toString()).replaceAll("FETCH_CHILD", "1");
        String result = HttpClientUtil.doGetWithSSl(requetUrl, prop.getHost());

        JSONObject jsonObject = JSON.parseObject(result);
        Integer errcode = (Integer)jsonObject.get("errcode");
        if (errcode == 0) {
            String userlist = jsonObject.getString("userlist");
            list = JSON.parseArray(userlist, EnterWeixinUserDetailBean.class);
        }
        return list;
    }

    /**
     * Description: <br>
     * 获取成员详情
     * @param userId 用户id
     * @return json字符串
     * @see
     */
    public EnterWeixinUserDetailBean getUserDetailInfoByUserId(String userId) {
        EnterWeixinUserDetailBean bean = null;
        String requetUrl = prop.getUserDetailUrl().replaceAll("ACCESS_TOKEN",
            AccessToken.ENTERPRISE_WEIXIN_ACCESS_TOKEN_ADDRESSLIST).replaceAll("USERID", userId);
        String result = HttpClientUtil.doGetWithSSl(requetUrl, prop.getHost());

        JSONObject jsonObject = JSON.parseObject(result);
        Integer errcode = (Integer)jsonObject.get("errcode");
        if (errcode == 0) {
            bean = JSON.parseObject(result, EnterWeixinUserDetailBean.class);
        }
        return bean;
    }

    /**
     * Description: <br>
     * 发消息
     * @param jsonString 请求参数字符串
     * @return 
     * @see
     */
    public String sendMessage(String jsonString) throws Exception {
        String requetUrl = prop.getMessageSendUrl().replaceAll("ACCESS_TOKEN",
            AccessToken.ENTERPRISE_WEIXIN_ACCESS_TOKEN_COPC);
        String result = HttpClientUtil.doPostJsonWithSSl(requetUrl, jsonString, prop.getHost());
        return result;
    }

    /**
     * Description: <br>
     *   检验部门权限
     * @param app
     * @param departments
     * @return 
     * @see
     */
    public boolean checkDepartment(String app, String departments) {
        boolean flag = false;
        if (StringUtils.isNotBlank(departments)) {
            List<Integer> list = dao.findDepartmentIdByApp(app);
            List<String> listStr = new ArrayList<>();
            for (Integer integer : list) {
                listStr.add(integer.toString());
            }
            String[] department = departments.split("\\|");
            List<String> list1 = Arrays.asList(department);
            flag = listStr.containsAll(list1);
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * Description: <br>
     *  检查部门成员权限
     *      1.获取可发送消息所有的成员
     *      2.查看成员中是否包含本次消息接受者
     * @param app
     * @param userid
     * @return 
     * @see
     */
    public boolean checkUser(String app, String userid) {
        boolean flag = false;
        if (StringUtils.isNotBlank(userid)) {
            List<Integer> listDe = dao.findDepartmentIdByApp(app);
            List<String> list = new ArrayList<>();
            for (Integer departmentId : listDe) {
                List<EnterWeixinUserBean> listUse = getUserListByDepartmentId(departmentId);
                for (EnterWeixinUserBean enterWeixinUserBean : listUse) {
                    list.add(enterWeixinUserBean.getUserid());
                }
            }
            String[] split = userid.split("\\|");
            List<String> asList = Arrays.asList(split);
            flag = list.containsAll(asList);
        } else {
            flag = true;
        }
        return flag;
    }
}
