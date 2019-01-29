/*
 * 文件名：AppBindDepartmentService.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：zyz
 * 修改时间：2017年7月20日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.sdp.cop.octopus.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.cop.octopus.dao.AppBindDepartmentDao;
import com.sdp.cop.octopus.entity.AppBindDepartmentInfo;
import com.sdp.cop.octopus.entity.AppDepartmentBean;


/**
 * app绑定部门service
 * @author zhangyunzhen
 * @version 2017年7月20日
 * @see AppBindDepartmentService
 * @since
 */
@Service
public class AppBindDepartmentService {

    /**
     * app绑定部门dao
     */
    @Autowired
    private AppBindDepartmentDao dao;

    /**
     * Description: <br>
     *  获取绑定部门信息
     * @return 
     * @see
     */
    public List<AppDepartmentBean> getBindInfo() {
        List<AppDepartmentBean> beans = new ArrayList<>();
        List<String> apps = dao.getApps();
        for (String app : apps) {
            List<AppBindDepartmentInfo> list = getBindInfoByApp(app);
            StringBuffer sb = new StringBuffer();
            for (AppBindDepartmentInfo info : list) {
                sb.append(info.getDepartmentName() + ",");
            }
            AppDepartmentBean bean = new AppDepartmentBean(app,
                sb.substring(0, sb.length() - 1));
            beans.add(bean);
        }
        return beans;
    }

    /**
     * Description: <br>
     *  根据app获取绑定部门信息
     * @param app
     * @return 
     * @see
     */
    public List<AppBindDepartmentInfo> getBindInfoByApp(String app) {
        List<AppBindDepartmentInfo> list = dao.findByApp(app);
        return list;
    }

    /**
     * Description: <br>
     *  app绑定部门
     * @param app app名字
     * @param departments 部门
     * @return 
     * @see
     */
    public boolean bindDepartment(String app, String[] departments) {
        try {
            if (departments != null && departments.length > 0) {
                for (String department : departments) {
                    String[] idAndName = department.split("=");
                    AppBindDepartmentInfo info = new AppBindDepartmentInfo(app,
                        Integer.parseInt(idAndName[0]), idAndName[1]);
                    dao.save(info);
                }
                return true;
            }else{
                return false;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Description: <br>
     *  修改绑定信息
     * @param app
     * @param departments
     * @return 
     * @see
     */
    public boolean updateAppBind(String app, String[] departments) {
        boolean flag = false;
        flag = deleteAppBind(app);
        if (flag) {
            flag = bindDepartment(app, departments);
            return flag;
        }
        return flag;
    }

    /**
     * Description: <br>
     * 根据app名字删除绑定信息
     * @param app app名字
     * @return 
     * @see
     */
    public boolean deleteAppBind(String app) {
        try {
            List<AppBindDepartmentInfo> list = dao.findByApp(app);
            if (list != null && list.size() > 0) {
                for (AppBindDepartmentInfo info : list) {
                    dao.delete(info);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
