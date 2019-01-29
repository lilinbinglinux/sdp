package com.sdp.code.exception;

/**
 * @author lumeiling
 * @package com.bonc.code.exception
 * @create 2018-12-2018/12/17 下午3:04
 **/
public class ErrorMessageException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    protected int code;

    public ErrorMessageException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

}
