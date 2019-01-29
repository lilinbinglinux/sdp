package com.sdp.common.entity;

import javax.ws.rs.WebApplicationException;


public class NetClientException  extends RuntimeException{

    private static final long serialVersionUID = -7521673271244696905L;

    private Status status;

    public NetClientException(String message,Exception exception) {
        super(message,exception);
        this.setStatus(getResponse(exception));
    }

    public NetClientException(String msg, Status status) {
        super(msg);
        this.status = status;
    }

    public NetClientException(Exception exception) {
        this(buildMessage(exception),exception);
    }

    public NetClientException(String msg) {
        super(msg);
    }

    private static String buildMessage(Exception exception) {
        Status response = getResponse(exception);
        return response != null ? response.getMessage() : null;
    }

    private static Status getResponse(Throwable exception) {
        if (exception instanceof WebApplicationException) {
            WebApplicationException error = (WebApplicationException) exception;
            return error.getResponse().readEntity(Status.class);
        }
        return null;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
