package com.sdp.sso.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface IAuthHandle
{
  public abstract boolean onSuccess(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse, String paramString);
}
