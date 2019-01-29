package com.sdp.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 捕捉自定义异常BaseException，并跳转至指定页面，显示报错信息
 *
 * @author ZY
 * @version 2017年7月4日
 * @see GlobalExceptionHandler
 * @since
 */
@ControllerAdvice
class GlobalExceptionHandler {
    
    /**
     * 
     * Description:捕捉自定义异常 
     * 
     *@param req
     *@param e
     *@return
     *@throws Exception ModelAndView
     *
     * @see
     */
    @ExceptionHandler(value = BaseException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("xerror");
        return mav;
    }
}
