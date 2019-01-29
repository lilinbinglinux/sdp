package com.sdp.sso.util;

import java.util.HashMap;

/**
 * @description:线程变量
 * @author yangjian
 */
public class CurrentThreadContext {
    
    private static ThreadLocal<HashMap<String, Object>> threadLocals = new ThreadLocal<HashMap<String, Object>>();
    public static final String CURRENT_USER_ID = "currentUserId";
    public static final String CURRENT_USER_NAME = "currentUserName";
    public static final String CURRENT_USER_TENANTID = "currentUserTenantId";

    /**
     * 取得当前用户id
     */
    public static String getCurrentUserId() {
        Object object = getValue(CURRENT_USER_ID);
        if (object != null) {
            return (String) object;
        }
        return null;
    }

    /**
     * 取得当前用户姓名
     */
    public static String getCurrentUserName() {
        Object object = getValue(CURRENT_USER_NAME);
        if (object != null) {
            return (String) object;
        }
        return null;
    }
    
    /**
     * 取得当前用户租户id
     */
    public static String getCurrentUserTenantId() {
        Object object = getValue(CURRENT_USER_TENANTID);
        if (object != null) {
            return (String) object;
        }
        return null;
    }

    /**
     * 取得线程变量值
     * 
     * @param name
     *            线程变量名
     * @return 线程变量值
     */
    public static Object getValue(String name) {
        if (threadLocals.get() == null) {
            threadLocals.set(new HashMap<String, Object>());
        }
        return threadLocals.get().get(name);
    }

    /**
     * 设置线程变量值
     * 
     * @param name
     *            线程变量名
     * @param value
     *            线程变量值
     */
    public static void setValue(String name, Object value) {
        if (threadLocals.get() == null) {
            threadLocals.set(new HashMap<String, Object>());
        }
        threadLocals.get().put(name, value);
    }

    public static void clear() {
        if (threadLocals.get() != null) {
            threadLocals.get().clear();
        }
    }
}

