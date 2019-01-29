package com.sdp.common.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.sdp.common.CurrentUserUtils;
import com.sdp.common.constant.Dictionary;
import com.sdp.common.entity.BaseInfo;
import com.sdp.frame.web.entity.user.User;
import com.sdp.util.DateUtils;

/**
 * @description:
 * BaseUtilsService BaseInfo
 * @author: wangke
 * @version: 11:15 2017/12/28
 * @see:
 * @since:
 * @modified by:
 */
public class BaseUtilsService {

    /**
     * @Description:
     * 在更新和新增数据库记录时，操作创建人和更新人信息工具类
     *
     * @param entity
     * @param directive Dictionary.Directive
     */
    public static void saveBaseInfo(BaseInfo entity, Integer directive) {
    	User userInfo = CurrentUserUtils.getInstance().getUser();
        Date current = DateUtils.getCurrentDate();

        if (directive == Dictionary.Directive.SAVE.value) {
            entity.setCreateDate(current);
            entity.setCreateBy(userInfo.getLoginId());
            entity.setTenantId(userInfo.getTenantId());
        }
        entity.setUpdateDate(current);
        entity.setUpdateBy(userInfo.getLoginId());
    }

    public static void saveBaseInfo(BaseInfo entity, String loginId, Integer directive) {
        Date current = DateUtils.getCurrentDate();
        if (StringUtils.isBlank(loginId)) {
            loginId = CurrentUserUtils.getInstance().getUser().getLoginId();
        }

        if (directive == Dictionary.Directive.SAVE.value) {
            entity.setCreateDate(current);
            entity.setCreateBy(loginId);
            entity.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
        }
        entity.setUpdateDate(current);
        entity.setUpdateBy(loginId);
    }
}
