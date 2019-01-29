package com.sdp.servflow.transfomat;

import java.io.ByteArrayInputStream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/** 
* @Title: ProtConversion.java 
* @Description: 协议转换
* tinybad 
* 2017年9月24日
*/ 
public class ProtConversion {
	
	public static void main(String[] args) {}
	
	
	
	
	
	
	/** 
	* @Title: putValue 
	* @Description: list MappingBean集合，targetType 目标转换协议，nameSpace soap用到的命名空间 
	* 2017年9月27日
	*/ 
	public static String putValue(List<MappingBean> list,PROTOCOL_TYPE targetType, Map<String, Namespace>    declarationNamespaces){
		String result = null;
		try {
			if(targetType == PROTOCOL_TYPE.JSON){
				JSONObject targetJsonObj = new JSONObject();
				for(MappingBean mb:list){
					putNodeValueToJson(targetJsonObj,mb.getTargetPath(), mb.getValue());
				}
				result = targetJsonObj.toString();
			}else if(targetType == PROTOCOL_TYPE.XML){
				String rootName = list.get(0).getTargetPath().split("/")[0];
				Element targetRoot = new Element(rootName);
				for(MappingBean mb:list){
					putNodeValueToXml(targetRoot,mb.getTargetPath(), mb.getTargetAttrName(),mb.getValue(),null);
				} 
				XMLOutputter xmlOut = new XMLOutputter();
				result = xmlOut.outputString(targetRoot);
			}else if(targetType == PROTOCOL_TYPE.SOAP_1_1||targetType == PROTOCOL_TYPE.SOAP_1_2){
				
				Element targetRoot = null;
				for(MappingBean mb:list){
				    targetRoot=	putNodeValueToXml(targetRoot,mb.getTargetPath(), mb.getTargetAttrName(),mb.getValue(),declarationNamespaces);
				} 
				XMLOutputter xmlOut = new XMLOutputter();
				result = xmlOut.outputString(targetRoot);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static JSONObject returnJSONObjectByString(String sourParam){
		JSONObject jsonObj = null;
		try {
			jsonObj = JSONObject.parseObject(sourParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj;
	}
	
	public static Element returnElementByString(String sourParam){
		Element sourceRoot = null;
		try {
			InputStream is = new ByteArrayInputStream(sourParam.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			sourceRoot = doc.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sourceRoot;
	}
	  public static String replaceBlank(String str) {
	        String dest = "";
	        if (str!=null) {
	            Pattern p = Pattern.compile("\t|\r|\n");
	            Matcher m = p.matcher(str);
	            dest = m.replaceAll("");
	        }
	        return dest;
	    }
	
	public static Object getValueByString(String sourParam,String path,String attrName,PROTOCOL_TYPE sourType,Map<String, Namespace>    declarationNamespaces){
	    sourParam =replaceBlank(sourParam) ;
	    System.out.println("sourParam"+sourParam);
	    System.out.println("path"+path);
	    System.out.println("attrName"+attrName);
	    System.out.println("sourType"+sourType);
	    
		Object o = null;
		try {
			if(sourType == PROTOCOL_TYPE.XML){
				InputStream is = new ByteArrayInputStream(sourParam.getBytes("utf-8"));
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(is);
				Element sourceRoot = doc.getRootElement();
				
				o = getNodeValueFromXml(sourceRoot,path,attrName,declarationNamespaces);
				return o;
			}else if(sourType == PROTOCOL_TYPE.JSON){
				JSONObject jsonObj = JSONObject.parseObject(sourParam);
				o=getNodeValueFromJson(jsonObj,path);
			}else if(sourType == PROTOCOL_TYPE.SOAP_1_1){
			/*	int index1=sourParam.indexOf("<soapenv:Body>")+"<soapenv:Body>".length();
				int index2=sourParam.indexOf("</soapenv:Body>");
				
				sourParam = sourParam.substring(index1,index2);*/
				//String nameSpaceFlag=sourParam.substring(1,sourParam.indexOf(":")+1);
				//sourParam = sourParam.replace(nameSpaceFlag,"");
				o = getValueByString(sourParam,path,attrName,PROTOCOL_TYPE.XML,declarationNamespaces);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
	
	public static Object getValueByObj(JSONObject jsonObj,Element rootElement,String path,String attrName,PROTOCOL_TYPE sourType,Map<String, Namespace>    declarationNamespaces){
		Object o = null;
		try {
			if(sourType == PROTOCOL_TYPE.JSON){
				if(jsonObj == null){
					return null;
				}
				o=getNodeValueFromJson(jsonObj,path);
			}else if(sourType == PROTOCOL_TYPE.XML){
				if(rootElement ==null){
					return null;
				}
				o = getNodeValueFromXml(rootElement,path,attrName,declarationNamespaces);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
	/*
	public static String protocolChange(PROTOCOL_TYPE sourType,PROTOCOL_TYPE targetType,
			String sourParam,List<MappingBean> list,String nameSpace){
		sourParam = sourParam.trim();
		if(judgeObjIsNull(sourType) || judgeObjIsNull(targetType) 
				|| judgeStringIsNull(sourParam) || judgeObjIsNull(list)){
			return null;
		}
		String result = null;
		
		if(sourType == PROTOCOL_TYPE.JSON && targetType == PROTOCOL_TYPE.JSON){
			
			// 1
			result = getAndPut_J_To_J(sourParam,list);
			
		}else if(sourType == PROTOCOL_TYPE.XML && targetType == PROTOCOL_TYPE.XML){
			
			// 2
			result = getAndPut_X_To_X(sourParam,list);
			
		}else if(sourType == PROTOCOL_TYPE.SOAP_1_1 && targetType == PROTOCOL_TYPE.SOAP_1_1){
			
			// 3
			result = getAndPut_So11_To_So11(sourParam,list);
			
		}else if(sourType == PROTOCOL_TYPE.JSON && targetType == PROTOCOL_TYPE.XML){
			
			// 4
			result = getAndPut_J_To_X(sourParam,list);
			
		}else if(sourType == PROTOCOL_TYPE.XML && targetType == PROTOCOL_TYPE.JSON){
			
			// 5
			result = getAndPut_X_To_J(sourParam,list);
			
		}else if(sourType == PROTOCOL_TYPE.JSON && targetType == PROTOCOL_TYPE.SOAP_1_1){
			
			// 6
			result = getAndPut_J_To_So11(sourParam,list,nameSpace);
			
		}else if(sourType == PROTOCOL_TYPE.SOAP_1_1 && targetType == PROTOCOL_TYPE.JSON){
			
			// 7
			result = getAndPut_So11_To_J(sourParam,list);
			
		}else if(sourType == PROTOCOL_TYPE.XML && targetType == PROTOCOL_TYPE.SOAP_1_1){
			
			// 8
			result = getAndPut_X_To_So11(sourParam,list,nameSpace);
			
		}else if(sourType == PROTOCOL_TYPE.SOAP_1_1 && targetType == PROTOCOL_TYPE.XML){
			
			// 9
			result = getAndPut_So11_To_X(sourParam,list);
			
		}
		
		return result;
	}*/
	
	// 1
	public static String getAndPut_J_To_J(String sourParam, List<MappingBean> list) {
		JSONObject jsonObj = JSONObject.parseObject(sourParam);
		JSONObject targetJsonObj = new JSONObject();
		for(MappingBean mb:list){
			putNodeValueToJson(targetJsonObj,mb.getTargetPath(), getNodeValueFromJson(jsonObj,mb.getSourPath()));
		}
		return targetJsonObj.toString();
	}
	
	// 2
/*	public static String getAndPut_X_To_X(String sourParam, List<MappingBean> list) {
		try {
			InputStream is = new ByteArrayInputStream(sourParam.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element sourceRoot = doc.getRootElement();
			
			String rootName = list.get(0).getTargetPath().split("/")[0];
			Element targetRoot = new Element(rootName);
			for(MappingBean mb:list){
				Object o = getNodeValueFromXml(sourceRoot,mb.getSourPath(),mb.getSourceAttrName());
				putNodeValueToXml(targetRoot,mb.getTargetPath(), mb.getTargetAttrName(),o);
			} 
			XMLOutputter xmlOut = new XMLOutputter();
			String res = xmlOut.outputString(targetRoot);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}*/
	
	// 3
	public static String getAndPut_So11_To_So11(String sourParam, List<MappingBean> list) {
		System.out.println("soap 1.1 -- > soap 1.1");
		return null;
	}
	
	// 4
/*	public static String getAndPut_J_To_X(String sourParam, List<MappingBean> list) {
		try {
			JSONObject jsonObj = JSONObject.parseObject(sourParam);
			String rootName = list.get(0).getTargetPath().split("/")[0];
			Element root = new Element(rootName);
			for(MappingBean mb:list){
				Object o=getNodeValueFromJson(jsonObj,mb.getSourPath());
				putNodeValueToXml(root,mb.getTargetPath(), mb.getTargetAttrName(),o);
			} 
			XMLOutputter xmlOut = new XMLOutputter();
			String res = xmlOut.outputString(root);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}*/
	
	// 5
/*	public static String getAndPut_X_To_J(String sourParam, List<MappingBean> list) {
		try {
			InputStream is = new ByteArrayInputStream(sourParam.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			JSONObject targetJsonObj = new JSONObject();
			for(MappingBean mb:list){
				Object o = getNodeValueFromXml(root,mb.getSourPath(),mb.getSourceAttrName());
				putNodeValueToJson(targetJsonObj,mb.getTargetPath(), o);
			} 
			return targetJsonObj.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
	}*/
	
	// 6
/*	public static String getAndPut_J_To_So11(String sourParam, List<MappingBean> list,String nameSpace) {
		System.out.println("json -- > soap 1.1");
		
		for(MappingBean mb:list){
			String path=mb.getTargetPath();
			String[] paths = path.split("/");
			String newPath = "";
			for(String ps:paths){
				ps = "mmkj"+ps;
				newPath += ps+"/";
			}
			newPath = newPath.substring(0,newPath.length()-1);
			mb.setTargetPath(newPath);
		}
		String s=getAndPut_J_To_X(sourParam,list);
		s= s.replace("mmkj", "mmkj:");
		String prefixSoap = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mmkj=\""+nameSpace+"\">"
				+ "<SOAP-ENV:Header/><soapenv:Body>";
		String suffixSoap = "</soapenv:Body></SOAP-ENV:Envelope>";
		String result = prefixSoap + s + suffixSoap;
		
		return s;
	}*/
	
	// 7
	/*public static String getAndPut_So11_To_J(String sourParam, List<MappingBean> list) {
		System.out.println("soap 1.1 -- > json");
		
		int index1=sourParam.indexOf("<soapenv:Body>")+"<soapenv:Body>".length();
		int index2=sourParam.indexOf("</soapenv:Body>");
		
		sourParam = sourParam.substring(index1,index2);
		String nameSpaceFlag=sourParam.substring(1,sourParam.indexOf(":")+1);
		sourParam = sourParam.replace(nameSpaceFlag,"");
		String s=getAndPut_X_To_J(sourParam,list);
		
		return s;
	}*/
	
	// 8
/*	public static String getAndPut_X_To_So11(String sourParam, List<MappingBean> list,String nameSpace) {
		System.out.println("xml -- > soap 1.1");
		
		for(MappingBean mb:list){
			String path=mb.getTargetPath();
			String[] paths = path.split("/");
			String newPath = "";
			for(String ps:paths){
				ps = "mmkj"+ps;
				newPath += ps+"/";
			}
			newPath = newPath.substring(0,newPath.length()-1);
			mb.setTargetPath(newPath);
		}
		String s=getAndPut_X_To_X(sourParam,list);
		s= s.replace("mmkj", "mmkj:");
		String prefixSoap = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mmkj=\""+nameSpace+"\">"
				+ "<SOAP-ENV:Header/><soapenv:Body>";
		String suffixSoap = "</soapenv:Body></SOAP-ENV:Envelope>";
		String result = prefixSoap + s + suffixSoap;
		
		return result;
	}*/
	
/*	// 9
	public static String getAndPut_So11_To_X(String sourParam, List<MappingBean> list) {
		System.out.println("soap 1.1 -- > xml");
		
		int index1=sourParam.indexOf("<soapenv:Body>")+"<soapenv:Body>".length();
		int index2=sourParam.indexOf("</soapenv:Body>");
		
		sourParam = sourParam.substring(index1,index2);
		String nameSpaceFlag=sourParam.substring(1,sourParam.indexOf(":")+1);
		sourParam = sourParam.replace(nameSpaceFlag,"");
		String s=getAndPut_X_To_X(sourParam,list);
		
		return s;
	}*/
	
/*	static void test6() {
		String sourParam = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><soapenv:Body>"
				+ "<ns2:getCountryResponse xmlns:ns2=\"http://www.yourcompany.com/webservice\"><ns2:country><ns2:NAME>Spain</ns2:NAME><ns2:POPULATION>46704314</ns2:POPULATION>"
				+ "<ns2:capital>Madrid</ns2:capital><ns2:currency>EUR</ns2:currency></ns2:country></ns2:getCountryResponse>"
				+ "</soapenv:Body></SOAP-ENV:Envelope>";
//		System.out.println("sourParam : "+sourParam);
		List<MappingBean> list =new ArrayList<MappingBean>();
		MappingBean mb1=new MappingBean("root/a","root/a",null,null,null);
		MappingBean mb2=new MappingBean("root/b","root/b",null,null,null);
		MappingBean mb3=new MappingBean("root/a","root/a","c","c",null);
		list.add(mb1);
		list.add(mb2);
		list.add(mb3);
		getAndPut_So11_To_X(sourParam,list);
		
		String sourParam = "<root><a c=\"5\">a值</a><b>b值</b></root>";
		System.out.println("sourParam : "+sourParam);
		List<MappingBean> list =new ArrayList<MappingBean>();
		MappingBean mb1=new MappingBean("root/a","root/a",null,null,null);
		MappingBean mb2=new MappingBean("root/b","root/b",null,null,null);
		MappingBean mb3=new MappingBean("root/a","root/a","c","c",null);
		list.add(mb1);
		list.add(mb2);
		list.add(mb3);
		String result=protocolChange(PROTOCOL_TYPE.XML,PROTOCOL_TYPE.SOAP_1_1,sourParam,list,null);
		System.out.println("target : "+result);
	}*/
	
	public static Object getNodeValueFromXml(Element root,String jsonPath,String attrName,Map<String, Namespace>    declarationNamespaces){
		try {
			String[] paths = jsonPath.split("/");
			int length = paths.length;
			String path = null ;
			for(int i = 1;i<length;i++) {
				path = paths[i];
				if(root != null) {
					root = getObjFromXml(root,path,declarationNamespaces);
				}
				
				if((i+1) == length){
					if(judgeStringIsNull(attrName)){
						return root.getText();
					}else{
						return root.getAttributeValue(attrName);
					}
				}
			}			
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	public static Element getObjFromXml(Element root, String path,Map<String, Namespace>    declarationNamespaces) {
		try {
			if (path.contains("[")) {
				//数组暂不处理 日后完善
			} else {
			    Namespace belongNamespace = null;
                String[] params = path.split(":");
                String param = params[0];
                if(params.length>1)
                {
                     belongNamespace = declarationNamespaces.get(params[0]);
                     param = params[1];
                }
                root=  getChild(root, param, belongNamespace);
				return root;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/** 
	* 	<root> 
		  <a c="5">a值</a>  
		  <b>b</b> 
		</root>
		
		a: root/a  false
		b: root/b
		c: root/a  true
	*/ 
	public static Element putNodeValueToXml(Element rootResource, String targetPath, String attrName,Object value,
	                                     Map<String, Namespace>    declarationNamespaces                         
	    ) {
		try {
		    String[] paths = targetPath.split("/");
            int length = paths.length;
            String path=null;
            
		    if(rootResource ==null)
		    {
		        //初始化根节点信息
		        Namespace belongNamespace = null;
		        path=paths[0];
                String[] params = path.split(":");
                String param = params[0];
                if(params.length>1)
                {
                     belongNamespace = declarationNamespaces.get(params[0]);
                     param = params[1];
                }
                rootResource =  initRoot(rootResource, declarationNamespaces, param);
                if(belongNamespace!=null) {
                    rootResource.setNamespace(belongNamespace);
                }
		    }
		    
		    Element root = rootResource;
		    
			
			for(int i = 1;i<length;i++) {
				path=paths[i];
				
				Namespace belongNamespace = null;
				String[] params = path.split(":");
				String param = params[0];
				if(params.length>1)
				{
				     belongNamespace = declarationNamespaces.get(params[0]);
				     param = params[1];
				}
				
				if(i== length-1){
				    //末级节点
				    Element childNode = null;
				    
				    childNode =   getChild(root,param,belongNamespace);
					if(childNode == null){
						childNode = new Element(param);
						if(belongNamespace!=null) {
						    childNode.setNamespace(belongNamespace);
						}
						root.addContent(childNode);
					}
					if(judgeStringIsNull(attrName)){
						childNode.setText((String)value);
					}else{
						childNode.setAttribute(attrName,(String)value);
					}
				}else{
					if (path.contains("[")) {
						// xml数组，暂时不考虑，后期完善
					}else{
        						if(getChild(root,param,belongNamespace) == null){
        							Element childNode = new Element(param);
        	                            if(belongNamespace!=null) {
        	                                childNode.setNamespace(belongNamespace);
        	                            }
        							root.addContent(childNode);
        						}
        						root =getChild(root,param,belongNamespace);
					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootResource;
	}
	
	/**
	 * 
	 * Description: 初始化根节点（追加命名空间）
	 * 
	 *@param root
	 *@param declarationNamespaces void
	 *
	 * @see
	 */
	public static Element initRoot(Element root,Map<String, Namespace> declarationNamespaces,String paramKey){
	    
	     root = new Element(paramKey);
	     if(declarationNamespaces!=null&&declarationNamespaces.size()>0)
	     {
	         System.out.println(declarationNamespaces);
	         System.out.println(declarationNamespaces.entrySet());
	         for(Entry<String, Namespace> entry :declarationNamespaces.entrySet())
	         {
	             System.out.println("entry.getValue()---"+entry.getValue());
	             Namespace soapenv = entry.getValue();
	             root.addNamespaceDeclaration(soapenv);
	         }
	     }
	     return root;
	}
	
	/**
	 * 
	 * Description: 获取子节点信息
	 * 
	 *@param element
	 *@param son
	 *@param ns
	 *@return Element
	 *
	 * @see
	 */
	private static Element getChild(Element element,String son ,Namespace ns  )
	{
	    
	    if(ns==null)
	    {
	        return element.getChild(son);
	    }else{
	        return element.getChild(son, ns);
	    }
	}

	public static void putNodeValueToJson(JSONObject obj, String targetPath,Object targetObj) {
		try {
			if(obj == null){
				return;
			}
			String[] paths = targetPath.split("/");
			int length = paths.length;
			String path=null;
			for(int i = 0;i<length;i++) {
				path=paths[i];
				if((i+1) == length){
					obj.put(path, targetObj);
				}else{
					if (path.contains("[")) {
						String node=path.substring(0,path.indexOf("["));
						if(obj.get(node) == null){
							JSONArray arr = new JSONArray();
							obj.put(node, arr);
						}
						
						int index = Integer.valueOf(path.substring(path.indexOf("[")+1,path.indexOf("]")));
						JSONArray arr = obj.getJSONArray(node);
						if(index >= arr.size()){
							JSONObject job=new JSONObject();
							arr.add(job);
						}
						obj = arr.getJSONObject(index);
					}else{
						if(obj.get(path) == null){
							JSONObject job=new JSONObject();
							obj.put(path, job);
						}
						obj = obj.getJSONObject(path);
					}
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static JSONObject getObjFromJson(JSONObject obj, String path) {
		try {
			if (path.contains("[")) {
				JSONArray arr = obj.getJSONArray(path.substring(0,path.indexOf("[")));
				int size=arr.size();
				int index = Integer.valueOf(path.substring(path.indexOf("[")+1,path.indexOf("]")));
				if(index < size){
					return arr.getJSONObject(index);
				}else{
					return null;
				}
			} else {
				return obj.getJSONObject(path);
			}
		} catch (Exception e) {
			return obj;
		}
	}
	
	public static Object getNodeValueFromJson(JSONObject obj,String jsonPath){
		try {
			String[] paths = jsonPath.split("/");
			int length = paths.length;
			for(int i = 0;i<length;i++) {	
				if(obj != null) {
					obj = getObjFromJson(obj,paths[i]);
				}
				
				if((i+1) == length){
					return obj.get(paths[i]);
				}
			}			
		} catch (NullPointerException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static boolean judgeStringIsNull(String s){
		return (s == null) || ("".equals(s)) ? true :false;  
	}
	
	public static boolean judgeObjIsNull(Object o){
		return o == null ? true : false;
	}
	
	
	/***
     * 
     * 获取JSON类型
     * 判断规则
     * 判断第一个字母是否为{或[ 如果都不是则不是一个JSON格式的文本
     *         
     * @param str
     * @return
     */
	public static JSON_TYPE getJSONType(String str)
     {
         if (judgeStringIsNull(str))
         {
             return JSON_TYPE.JSON_TYPE_ERROR;
         }


         String s = str.substring(0, 1);

         if ("{".equals(s))
         {
             return JSON_TYPE.JSON_TYPE_OBJECT;
         }
         else if ("[".equals(s))
         {
             return JSON_TYPE.JSON_TYPE_ARRAY;
         }
         else
         {
             return JSON_TYPE.JSON_TYPE_ERROR;
         }
     }
	
	// xml -- > soap
}
enum JSON_TYPE
{
    /**JSONObject*/
    JSON_TYPE_OBJECT,
    /**JSONArray*/
    JSON_TYPE_ARRAY,
    /**不是JSON格式的字符串*/
    JSON_TYPE_ERROR
}