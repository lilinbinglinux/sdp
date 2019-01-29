package com.sdp.servflow.protocolConversion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

/**
 * 使用HttpURLConnection发送webservice请求
 */
public class HttpURLConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	static String doPost(String urlStr,String data,String xieyi) throws ServletException, IOException {
		
		/*String data ="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" >"
				+ "<soapenv:Header/><soapenv:Body><getCountryRequest><name>"+"Spain"
				+ "</name></getCountryRequest></soapenv:Body></soapenv:Envelope>"; */
		URL url = new URL(urlStr);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestProperty("Content-Type", xieyi);
		
		OutputStream os = connection.getOutputStream();
		os.write(data.getBytes("utf-8"));
		
		//读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null){
				buffer.append(temp);
				buffer.append("\n");
			}
			String str =buffer.toString();
			return str;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
