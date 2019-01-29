package com.sdp.servflow.common;

/**
 * 
 * 自定义异常，抛出该异常后，可页面显示指定报错信息
 *
 * @author ZY
 * @version 2017年7月4日
 * @see BaseException
 * @since
 */
@SuppressWarnings("serial")
public class BaseException extends Exception{
    public BaseException(String message) {
        super(message);
    }
}
