package com.sdp.servflow.serlayout.sso.cas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.util.XmlUtils;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.AssertionImpl;
import org.jasig.cas.client.validation.TicketValidationException;

public class LoginCASOnBackstage
{
  public void login(HttpServletRequest request, HttpServletResponse response, String casServerUrlPrefix)
    throws ClientProtocolException, IOException, TicketValidationException
  {
    String server = casServerUrlPrefix + "/v1/tickets";
    String username = (String)request.getAttribute("username");
    String password = (String)request.getAttribute("password");
    String service = request.getRequestURL().toString();
    String serverValidate = casServerUrlPrefix + "/serviceValidate";
    String ticketGrantingTicket = getTicketGrantingTicket(server, username, password);

    String serviceTicket = getServiceTicket(server, ticketGrantingTicket, service);

    String responseXml = ticketValidate(serverValidate, serviceTicket, service);

    Assertion assertion = getAssertion(responseXml);
    request.setAttribute("_const_cas_assertion_", assertion);
    request.getSession().setAttribute("_const_cas_assertion_", assertion);
    Cookie cookie = new Cookie("CASTGC", ticketGrantingTicket);
    cookie.setPath("/cas/");
    response.addCookie(cookie);
  }

  public String getTicketGrantingTicket(String server, String username, String password)
    throws ClientProtocolException, IOException
  {
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost(server);
    List nameValuePairs = new ArrayList();
    nameValuePairs.add(new BasicNameValuePair("username", username));
    nameValuePairs.add(new BasicNameValuePair("password", password));
    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
    HttpResponse response = httpclient.execute(httppost);
    System.out.println("getTicketGrantingTicket-------------"+response.getStatusLine().getStatusCode());
    switch (response.getStatusLine().getStatusCode()) {
    case 201:
      String conResult = EntityUtils.toString(response.getEntity());
      Matcher matcher = Pattern.compile(".*action=\".*/(.*?)\".*").matcher(conResult);
      if (matcher.matches()) {
        String result = matcher.group(1);
        return result;
      }

      break;
    }

    return null;
  }

  private String getServiceTicket(String server, String ticketGrantingTicket, String service)
    throws ClientProtocolException, IOException
  {
    if (ticketGrantingTicket == null) {
      return null;
    }
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost(server + "/" + ticketGrantingTicket);
    List nameValuePairs = new ArrayList();
    nameValuePairs.add(new BasicNameValuePair("service", service));
    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
    HttpResponse response = httpclient.execute(httppost);
    System.out.println("getServiceTicket-----------------"+response.getStatusLine().getStatusCode());
    switch (response.getStatusLine().getStatusCode()) {
    case 200:
      String conResult = EntityUtils.toString(response.getEntity());
      return conResult;
    }

    return null;
  }

  private String ticketValidate(String serverValidate, String serviceTicket, String service)
    throws ClientProtocolException, IOException
  {
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost(serverValidate + "?" + "ticket=" + serviceTicket + "&service=" + URLEncoder.encode(service, "UTF-8"));
    HttpResponse response = httpclient.execute(httppost);
    System.out.println("ticketValidate-------------"+response.getStatusLine().getStatusCode());
    switch (response.getStatusLine().getStatusCode()) {
    case 200:
      String conResult = EntityUtils.toString(response.getEntity());
      return conResult;
    }

    return null;
  }

  private Assertion getAssertion(String response) throws TicketValidationException {
    String error = XmlUtils.getTextForElement(response, 
      "authenticationFailure");

    if (CommonUtils.isNotBlank(error)) {
      throw new TicketValidationException(error);
    }

    String principal = XmlUtils.getTextForElement(response, "user");
    String proxyGrantingTicketIou = XmlUtils.getTextForElement(response, "proxyGrantingTicket");

    if (CommonUtils.isEmpty(principal)) {
      throw new TicketValidationException("No principal was found in the response from the CAS server.");
    }

    Assertion assertion = null;
    Map attributes = extractCustomAttributes(response);
    assertion = new AssertionImpl(new AttributePrincipalImpl(principal, attributes));

    return assertion;
  }

  protected Map<String, Object> extractCustomAttributes(String xml) {
    int pos1 = xml.indexOf("<cas:attributes>");
    int pos2 = xml.indexOf("</cas:attributes>");

    if (pos1 == -1) {
      return Collections.emptyMap();
    }

    String attributesText = xml.substring(pos1 + 16, pos2);

    Map attributes = new HashMap();
    BufferedReader br = new BufferedReader(new StringReader(attributesText));

    List<String> attributeNames = new ArrayList();
    int leftPos;
    try
    {
      String line;
      while ((line = br.readLine()) != null)
      {
        //String line;
        String trimmedLine = line.trim();
        if (trimmedLine.length() > 0) {
          leftPos = trimmedLine.indexOf(":");
          int rightPos = trimmedLine.indexOf(">");
          attributeNames.add(trimmedLine.substring(leftPos + 1, rightPos));
        }
      }
      br.close();
    }
    catch (IOException localIOException)
    {
    }
    for (String name : attributeNames) {
      List values = XmlUtils.getTextForElements(xml, name);

      if (values.size() == 1)
        attributes.put(name, values.get(0));
      else {
        attributes.put(name, values);
      }
    }

    return attributes;
  }
}