package com.sdp.bcm.utils;

/**
 * 返回结果实体
 * 格式：{"codeus":0,"message":"xxx","data":xxx}
 * @author lumeiling
 * @package com.sdp.bcm.utils
 * @create 2018-12-2018/12/21 下午2:38
 **/
public class ResponseUtils {
    private static final long serialVersionUID = 1L;

    public static final int SUCCESS_STAUS = 0;

    private int code;//状态,0正确;其他错误
    private String message;//消息
    private Object data;//传出去的数据

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
