/*
 * 文件名：MyHostnameVerifier.java
 * 版权：Copyright by www.bonc.com.cn
 * 描述：
 * 修改人：Anchor
 * 修改时间：2017年8月17日
 */

package com.sdp.cop.octopus.util;

import java.io.IOException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.http.conn.ssl.X509HostnameVerifier;

/**
 * Https X509HostnameVerifier
 * use ALLOW_ALL_HOSTNAME_VERIFIER
 * @author Anchor
 * @version 2017年8月17日
 * @see MyHostnameVerifier
 * @since
 */
public class MyHostnameVerifier implements X509HostnameVerifier {
    
    @Override
    public boolean verify(String host, SSLSession session) {
        String sslHost = session.getPeerHost();
        System.out.println("Host=" + host);
        System.out.println("SSL Host=" + sslHost);    
        if (host.equals(sslHost)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void verify(String host, SSLSocket ssl) throws IOException {
        String sslHost = ssl.getInetAddress().getHostName();
        System.out.println("Host=" + host);
        System.out.println("SSL Host=" + sslHost);    
        if (host.equals(sslHost)) {
            return;
        } else {
            throw new IOException("hostname in certificate didn't match: " + host + " != " + sslHost);
        }
    }

    @Override
    public void verify(String host, X509Certificate cert) throws SSLException {
        throw new SSLException("Hostname verification 1 not implemented");
    }

    @Override
    public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
        throw new SSLException("Hostname verification 2 not implemented");
    }
    
/*    public static void main (String[] args) throws Exception {
        org.apache.http.conn.ssl.SSLSocketFactory sf = org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory();
        sf.setHostnameVerifier(new MyHostnameVerifier());
        org.apache.http.conn.scheme.Scheme sch = new Scheme("https", 443, sf);

        org.apache.http.client.HttpClient client = new DefaultHttpClient();
        client.getConnectionManager().getSchemeRegistry().register(sch);
        //org.apache.http.client.methods.HttpPost post = new HttpPost("https://183.192.199.148/cgi-bin/user/get");
        org.apache.http.client.methods.HttpGet post = new HttpGet("https://120.204.11.196/cgi-bin/department/list?access_token=A213123&id=ID");
        post.addHeader("Connection", "keep-alive");    
        post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");    
        //post.addHeader("Host", "qyapi.weixin.qq.com");
        //post.addHeader("Host", "api.weixin.qq.com");
        post.addHeader("X-Requested-With", "XMLHttpRequest");    
        post.addHeader("Cache-Control", "max-age=0");    
        org.apache.http.HttpResponse response = client.execute(post);
        java.io.InputStream is = response.getEntity().getContent();
        java.io.BufferedReader rd = new java.io.BufferedReader(new java.io.InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) { 
            System.out.println(line);
        }
    }*/
}
