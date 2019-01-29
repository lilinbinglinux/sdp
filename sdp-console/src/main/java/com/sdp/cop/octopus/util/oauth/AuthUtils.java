package com.sdp.cop.octopus.util.oauth;


import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSON;
import com.sdp.common.entity.Auth2Details;



/**
 * 短信发送
 * @author zhangyunzhen
 * @version 2017年5月18日
 * @see AuthUtils
 * @since
 */
public class AuthUtils {

    /**
     * 说明：日志实例
     * 功能：打印日志
     */
    private static Logger LOGGER = Logger.getLogger(AuthUtils.class);

    /**
     * 
     * Description:
     * 通过API 发送短信
     * @param auth2Details
     * @param sms 
     * @throws Exception 
     * @see
     */
    @SuppressWarnings("deprecation")
    public static void getProtectedResource(Auth2Details auth2Details, List<SMS> smses)
        throws Exception {
        HttpPost post = new HttpPost(auth2Details.getPortalSmsUrl());
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
        post.addHeader("Accept", "application/json");
        post.addHeader(AuthConstants.AUTHORIZATION,
            getAuthorizationHeaderForAccessToken(auth2Details.getAccessToken()));
        // 解决中文乱码问题
        StringEntity stringEntity = new StringEntity(JSON.toJSONString(smses), "UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        post.setEntity(stringEntity);

        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        int code = -1;
        try {
            response = client.execute(post);
            code = response.getStatusLine().getStatusCode();
            if (code == 401 || code == 403) {
                // Access token is invalid or expired.Regenerate the access
                // token
                LOGGER.info("Access token is invalid or expired. Regenerating access token....");
                //得到短信权限token
                String accessToken = getAccessToken(auth2Details);
                if (isValid(accessToken)) {
                    // update the access token
                    auth2Details.setAccessToken(accessToken);
                    post.removeHeaders(AuthConstants.AUTHORIZATION);
                    post.addHeader(AuthConstants.AUTHORIZATION,
                        getAuthorizationHeaderForAccessToken(auth2Details.getAccessToken()));
                    post.releaseConnection();
                    response = client.execute(post);
                    code = response.getStatusLine().getStatusCode();
                    if (code >= 400) {
                        throw new RuntimeException(
                            "Could not access protected resource. Server returned http code: "
                                                   + code);
                    }
                } else {
                    throw new RuntimeException("Could not regenerate access token");
                }
            } else if (code >= 400) {
                throw new RuntimeException(
                    "Could not access protected resource. Server returned http code: "
                                           + code);
            }

            //handleResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            post.releaseConnection();
        }
    }

    /**
     * 
     * Description: <br>
     * 验证发短信权限
     * 
     * @param oauthDetails
     * @return 
     * @see
     */
    @SuppressWarnings("deprecation")
    public static String getAccessToken(Auth2Details oauthDetails) {
        HttpPost post = new HttpPost(oauthDetails.getAuthenticationServerUrl());
        String clientId = oauthDetails.getClientId();
        String clientSecret = oauthDetails.getClientSecret();
        String scope = oauthDetails.getScope();
        String grantType = oauthDetails.getGrantType();

        List<BasicNameValuePair> parametersBody = new ArrayList<BasicNameValuePair>();
        parametersBody.add(new BasicNameValuePair(AuthConstants.GRANT_TYPE, grantType));
        parametersBody.add(new BasicNameValuePair(AuthConstants.CLIENT_ID, clientId));
        parametersBody.add(new BasicNameValuePair(AuthConstants.CLIENT_SECRET, clientSecret));
        if (isValid(scope)) {
            parametersBody.add(new BasicNameValuePair(AuthConstants.SCOPE, scope));
        }

        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response = null;
        String accessToken = null;
        try {
            post.setEntity(new UrlEncodedFormEntity(parametersBody, HTTP.UTF_8));
            response = client.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code == AuthConstants.HTTP_UNAUTHORIZED) {
                LOGGER.info("Authorization server expects Basic authentication");
                // Add Basic Authorization header
                post.addHeader(AuthConstants.AUTHORIZATION, getBasicAuthorizationHeader(
                    oauthDetails.getClientId(), oauthDetails.getClientSecret()));
                LOGGER.info("Retry with client credentials");
                post.releaseConnection();
                response = client.execute(post);
                code = response.getStatusLine().getStatusCode();
                if (code == 401 || code == 403) {
                    LOGGER.error("Could not authenticate using client credentials.");
                    throw new RuntimeException("Could not retrieve access token for client: "
                                               + oauthDetails.getClientId());
                }
            }
            Map<String, Object> map = handleResponse(response);
            accessToken = (String)map.get(AuthConstants.ACCESS_TOKEN);
            if (StringUtils.isBlank(accessToken)) {
                accessToken = getAccessToken(oauthDetails);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            post.releaseConnection();
            client.close();
        }

        return accessToken;
    }

    /**
     * 
     * Description:
     * 处理http post请求方式的返回
     * @param response
     * @return 
     * @see
     */
    public static Map<String, Object> handleResponse(HttpResponse response) {
        String contentType = AuthConstants.JSON_CONTENT;
        if (response.getEntity().getContentType() != null) {
            contentType = response.getEntity().getContentType().getValue();
        }
        if (contentType.contains(AuthConstants.JSON_CONTENT)) {
            return handleJsonResponse(response);
        } else if (contentType.contains(AuthConstants.URL_ENCODED_CONTENT)) {
            return handleURLEncodedResponse(response);
        } else if (contentType.contains(AuthConstants.XML_CONTENT)) {
            return handleXMLResponse(response);
        } else {
            // Unsupported Content type
            throw new RuntimeException("Cannot handle " + contentType
                                       + " content type. Supported content types include JSON, XML and URLEncoded");
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> handleJsonResponse(HttpResponse response) {
        Map<String, Object> oauthLoginResponse = null;
        try {
            oauthLoginResponse = (Map<String, Object>)new JSONParser().parse(
                EntityUtils.toString(response.getEntity()));
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        } catch (RuntimeException e) {
            LOGGER.error("Could not parse JSON response");
            throw e;
        }
        LOGGER.info("********** Response Received **********");
        for (Map.Entry<String, Object> entry : oauthLoginResponse.entrySet()) {
            LOGGER.info(String.format("  %s = %s", entry.getKey(), entry.getValue()));
        }
        return oauthLoginResponse;
    }

    public static Map<String, Object> handleURLEncodedResponse(HttpResponse response) {
        Map<String, Charset> map = Charset.availableCharsets();
        Map<String, Object> oauthResponse = new HashMap<String, Object>();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        HttpEntity entity = response.getEntity();

        LOGGER.info("********** Response Received **********");
        for (Map.Entry<String, Charset> entry : set) {
            LOGGER.info(String.format("  %s = %s", entry.getKey(), entry.getValue()));
        }

        try {
            List<NameValuePair> list = URLEncodedUtils.parse(EntityUtils.toString(entity),
                Charset.forName(HTTP.UTF_8));
            for (NameValuePair pair : list) {
                LOGGER.info(String.format("  %s = %s", pair.getName(), pair.getValue()));
                oauthResponse.put(pair.getName(), pair.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not parse URLEncoded Response");
        }
        return oauthResponse;
    }

    public static Map<String, Object> handleXMLResponse(HttpResponse response) {
        Map<String, Object> oauthResponse = new HashMap<String, Object>();
        try {
            String xmlString = EntityUtils.toString(response.getEntity());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource inStream = new InputSource();
            inStream.setCharacterStream(new StringReader(xmlString));
            Document doc = db.parse(inStream);

            LOGGER.info("********** Response Receieved **********");
            parseXMLDoc(null, doc, oauthResponse);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception occurred while parsing XML response");
        }
        return oauthResponse;
    }

    public static void parseXMLDoc(Element element, Document doc,
                                   Map<String, Object> oauthResponse) {
        NodeList child = null;
        if (element == null) {
            child = doc.getChildNodes();

        } else {
            child = element.getChildNodes();
        }
        for (int j = 0; j < child.getLength(); j++ ) {
            if (child.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                org.w3c.dom.Element childElement = (org.w3c.dom.Element)child.item(j);
                if (childElement.hasChildNodes()) {
                    LOGGER.info(childElement.getTagName() + " : " + childElement.getTextContent());
                    oauthResponse.put(childElement.getTagName(), childElement.getTextContent());
                    parseXMLDoc(childElement, null, oauthResponse);
                }
            }
        }
    }

    public static String getAuthorizationHeaderForAccessToken(String accessToken) {
        return AuthConstants.BEARER + " " + accessToken;
    }

    public static String getBasicAuthorizationHeader(String username, String password) {
        return AuthConstants.BASIC + " " + encodeCredentials(username, password);
    }

    public static String encodeCredentials(String username, String password) {
        String cred = username + ":" + password;
        String encodedValue = null;
        byte[] encodedBytes = Base64.encodeBase64(cred.getBytes());
        encodedValue = new String(encodedBytes);
        LOGGER.info("encodedBytes " + new String(encodedBytes));

        byte[] decodedBytes = Base64.decodeBase64(encodedBytes);
        LOGGER.info("decodedBytes " + new String(decodedBytes));
        return encodedValue;
    }

    public static boolean isValidInput(Auth2Details input) {
        if (input == null) {
            return false;
        }

        String grantType = input.getGrantType();
        if (!isValid(grantType)) {
            LOGGER.error("Please provide valid value for grant_type");
            return false;
        }
        if (grantType.equals(AuthConstants.GRANT_TYPE_PASSWORD)) {
            if (!isValid(input.getUsername()) || !isValid(input.getPassword())) {
                LOGGER.error("Please provide valid username and password for password grant_type");
                return false;
            }
        }
        if (grantType.equals(AuthConstants.GRANT_TYPE_CLIENT_CREDENTIALS)) {
            if (!isValid(input.getClientId()) || !isValid(input.getClientSecret())) {
                LOGGER.error(
                    "Please provide valid client_id and client_secret for client_credentials grant_type");
                return false;
            }
        }

        if (!isValid(input.getAuthenticationServerUrl())) {
            LOGGER.error("Please provide valid value for authentication server url");
            return false;
        }
        LOGGER.info("Validated Input");
        return true;
    }

    public static boolean isValid(String str) {
        return (str != null && str.trim().length() > 0);
    }

}
