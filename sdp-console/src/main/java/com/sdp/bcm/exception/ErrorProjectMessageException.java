package com.sdp.bcm.exception;

/**
 * @author lumeiling
 * @package com.bonc.bcm.exception
 * @create 2018-12-2018/12/18 下午3:40
 **/

public class ErrorProjectMessageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected int code;

    public ErrorProjectMessageException(int code, String message) {
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
