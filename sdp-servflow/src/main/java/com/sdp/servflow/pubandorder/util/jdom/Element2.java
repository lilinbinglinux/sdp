package com.sdp.servflow.pubandorder.util.jdom;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;


public class Element2{
    
    public static void main(String[] args) {
        
     /*   
        Namespace ns_SOAP_ENV = Namespace.getNamespace("SOAP-ENV", "http://schemas.xmlsoap.org/soap/envelope/");
        Namespace ns_SOAP_ENC = Namespace.getNamespace("SOAP-ENC", "http://schemas.xmlsoap.org/soap/encoding/");
        Namespace ns_xsi = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        Namespace ns_xsd = Namespace.getNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
        Namespace ns_m = Namespace.getNamespace("m", "http://tempuri.org/");*/
        Namespace ns_SOAP_ENV = null;
        Namespace ns_SOAP_ENC = null;
        Namespace ns_xsi = null;
        Namespace ns_xsd = null;
        Namespace ns_m = null;

        Element Envelope = new Element("Envelope");
        Envelope.setNamespace(ns_SOAP_ENV);
        Envelope.addNamespaceDeclaration(ns_SOAP_ENC);
        Envelope.addNamespaceDeclaration(ns_xsi);
        Envelope.addNamespaceDeclaration(ns_xsd);

        Element Body = new Element("Body");
        Body.setNamespace(ns_SOAP_ENV);

        Element Login = new Element("Login");
        Login.setNamespace(ns_m);

        Element UserName = new Element("UserName").addContent("username");
        UserName.setNamespace(ns_m);

        Element PassWord = new Element("PassWord").addContent("password");
        PassWord.setNamespace(ns_m);

        Login.addContent(UserName).addContent(PassWord);
        Body.addContent(Login);
        Envelope.addContent(Body);

        Document soapDoc = new Document(Envelope);
        XMLOutputter xmlOut = new XMLOutputter();
        String  result = xmlOut.outputString(soapDoc);
      System.out.println(result);
        System.out.println(soapDoc);
    }
    
}