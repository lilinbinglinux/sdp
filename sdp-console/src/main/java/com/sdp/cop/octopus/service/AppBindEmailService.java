/*
 * 文件名：AppBindEmailService.java
 * 版权：Copyright by www.sdp.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月7日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.sdp.cop.octopus.dao.AppBindEmailDao;
import com.sdp.cop.octopus.entity.AppBindEmailInfo;
import com.sdp.cop.octopus.entity.OctopusResult;


/**
 * app绑定邮箱service
 * @author zhangyunzhen
 * @version 2017年7月7日
 * @see AppBindEmailService
 * @since
 */
@Service
public class AppBindEmailService {

    /**
     * AppEmailBindDao
     */
    @Autowired
    private AppBindEmailDao dao;

    /**
     * Description: <br>
     *  app邮箱绑定
     * @return 
     * @see
     */
    public String appEmailBind(String app, String emailAddr) {
        OctopusResult octopusResult = null;
        Map<String, String> map = new HashMap<>();
        try {
            List<AppBindEmailInfo> list = dao.findByApp(app);
            if (list != null && list.size() > 0) {//如果app邮箱已存在，修改
                dao.updateEmailByApp(app, emailAddr);
                octopusResult = OctopusResult.build(200, "绑定邮箱已重新设置为：" + emailAddr);
            } else { //不存在,新建
                dao.save(new AppBindEmailInfo(app, emailAddr));
                octopusResult = OctopusResult.build(200, "绑定邮箱已设置为：" + emailAddr);
            }
        } catch (Exception e) {
            octopusResult = OctopusResult.build(500, "绑定邮箱失败");
            return JSON.toJSONString(map);
        }
        return JSON.toJSONString(octopusResult);
    }

    /**
     * Description: <br>
     *  app解除邮箱绑定
     * @param app
     * @param emailAddr
     * @return 
     * @see
     */
    public String appEmailUnbind(String app, String emailAddr) {
        OctopusResult result = null;
        List<AppBindEmailInfo> list = dao.findByApp(app);
        if (list != null && list.size() > 0) {
            if (list.get(0).getEmailAddr().equals(emailAddr)) {
                dao.delete(list.get(0));
                result = OctopusResult.build(200, "已解除邮箱绑定");
            } else {
                result = OctopusResult.build(401, "绑定邮箱号输入不正确");
            }
        } else {//记录为空
            result = OctopusResult.build(404, "app未绑定邮箱，无需解绑");
        }
        return JSON.toJSONString(result);
    }
}
