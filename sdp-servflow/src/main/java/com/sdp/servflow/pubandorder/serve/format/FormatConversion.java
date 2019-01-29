package com.sdp.servflow.pubandorder.serve.format;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sdp.frame.util.StringUtil;
import com.sdp.servflow.pubandorder.node.model.field.Attribute;
import com.sdp.servflow.pubandorder.node.model.field.XmlNode;
import com.sdp.servflow.pubandorder.util.IContants;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import static com.sdp.servflow.pubandorder.util.IContants.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 
 * 数据格式转化
 *
 * @author 任壮
 * @version 2017年10月27日
 * @see FormatConversion
 * @since
 */
public class FormatConversion {
	// 参数和参数类型的分隔符
	public final static String splitString = "#@";

	/***
	 * 
	 * Description: 根据node信息获取对应的value集合
	 * 
	 * @param sourParam
	 * @param node
	 * @param values
	 * @return List<Object>
	 * @throws Exception
	 *
	 * @see
	 */
	public static List<Object> getValues(String sourParam, XmlNode node, Map<String, Namespace> nameSpaces)
			throws Exception {

		if (node == null)
			return null;

		String attributeName = node instanceof Attribute ? node.getName() : null;

		String format = node.getParamter().getFormat();
		sourParam = replaceBlank(sourParam);
		// nodeValue有值代表这个值是个固定值（例如xmls:soap的声明）
		List<Object> values = new ArrayList<Object>();
		String nodeValue = node.getValue();
		if (StringUtil.isNotEmpty(nodeValue)) {
			values.add(nodeValue);
			return values;
		}
		try {
			if (format.equals(XML) || format.equals(SOAP_1_1) || format.equals(SOAP_1_2)) {
				InputStream is = new ByteArrayInputStream(sourParam.getBytes("utf-8"));
				SAXBuilder sb = new SAXBuilder();
				Document doc = sb.build(is);
				Element sourceRoot = doc.getRootElement();
				String keyPath = node.getPath();
				// 根节点不存再xml的路径中
				String childPath = keyPath.substring(keyPath.indexOf("/") + 1);
				getValueFromXml(sourceRoot, childPath, values, attributeName, nameSpaces);
			} else if (format.equals(JSON)) {
				String type = node.getParamter().getType();
				// List<Object> list = new ArrayList<Object>();
				if (type.equals(OBJECT)) {
					System.out.println(sourParam);
					JSONObject json = JSONObject.parseObject(sourParam);
					List<Object> childlist = new ArrayList<Object>();
					getValueFromJSON(json, node.getPath(), childlist);
					values.addAll(childlist);
				} else if (IContants.LIST.equals(type) || IContants.LISTOBJECT.equals(type)
						|| IContants.LISTLIST.equals(type)) {
					JSONArray jsonArray = JSONObject.parseArray(sourParam);
					for (Object obj : jsonArray) {
						JSONObject json = (JSONObject) obj;
						List<Object> childlist = new ArrayList<Object>();
						getValueFromJSON(json, node.getPath(), childlist);
						values.addAll(childlist);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return values;
	}

	/***
	 * 
	 * Description: 从json中获取对应的值 key和类型用#@分隔开
	 * 
	 * @param sourParam
	 * @param node
	 * @return List<Object>
	 *
	 * @see
	 */
	// TODO 测试完以后改为private
	public static void getValueFromJSON(JSONObject json, String keyPath, List<Object> list) {
		if (json == null)
			return;
		String[] nodeArray = keyPath.split("/");
		String[] field = nodeArray[0].split(splitString);
		String key = field[0];
		String type = field[1];
		String childPath = keyPath.substring(keyPath.indexOf("/") + 1);
		if (type.equals(OBJECT)) {
			JSONObject jsonObj = json.getJSONObject(key);
			getValueFromJSON(jsonObj, childPath, list);
		} else if (type.equals(LISTOBJECT)) {
			JSONArray array = json.getJSONArray(key);
			for (Object obj : array) {
				JSONObject jsonObj = (JSONObject) obj;
				getValueFromJSON(jsonObj, childPath, list);
			}
		} else if (type.equals(LIST)) {
			String keyPath2 = childPath.substring(childPath.indexOf("/") + 1);
			String[] nodeArray2 = keyPath2.split("/");
			String[] field2 = nodeArray2[0].split(splitString);
			String key2 = field2[0];
			int order = 0;
			try {
				order = Integer.valueOf(field2[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 将虚拟参数拼装为普通的object对象，在进行往下递归
			JSONArray array = json.getJSONArray(key);
			JSONObject jsonObj = new JSONObject();
			Object childObject = order >= array.size() ? null : array.get(order);
			jsonObj.put(key2, childObject);
			getValueFromJSON(jsonObj, childPath, list);
		} else if (type.equals(LISTLIST)) {
			String keyPath2 = childPath.substring(childPath.indexOf("/") + 1);
			String[] nodeArray2 = keyPath2.split("/");
			String[] field2 = nodeArray2[0].split(splitString);
			String key2 = field2[0];
			int order = 0;
			try {
				order = Integer.valueOf(field2[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 将虚拟参数拼装为普通的object对象，在进行往下递归
			JSONArray array = json.getJSONArray(key);
			for (Object obj : array) {
				JSONArray childArray = (JSONArray) obj;
				JSONObject jsonObj = new JSONObject();
				Object childObject = order >= childArray.size() ? null : childArray.get(order);
				jsonObj.put(key2, childObject);
				getValueFromJSON(jsonObj, childPath, list);
			}
		} else {
			list.add(json.get(key));
		}
	}

	/**
	 * 
	 * Description: 从xml获取对应的值 属性名和类型用-分隔开(属性名中可能会出现soap形式的参数 eg:soap:Envolop)
	 * eg:root#@field
	 * 
	 * @param nameSpaces
	 * 
	 * @param sourceRoot
	 * @param node
	 * @return List<Object>
	 *
	 * @see
	 */
	// TODO 测试完以后改为private
	public static void getValueFromXml(Element xml, String keyPath, List<Object> list, String attributeName,
			Map<String, Namespace> nameSpaces) {
		if (xml == null)
			return;
		if (!keyPath.contains("/") && StringUtil.isNotEmpty(attributeName)) {
			// 最后一层并且存在属性名
			list.add(xml.getAttributeValue(attributeName));
			return;
		}
		String[] nodeArray = keyPath.split("/");
		// Element xml = element;
		String[] field = nodeArray[0].split(splitString);
		String keyName = field[0];
		// 对于命名空间获取后面的值( soap:body 存在的情况下 这里的key就是body)
		String key = keyName.split(":").length > 1 ? keyName.split(":")[1] : keyName;

		String keyPrefix = keyName.split(":").length > 1 ? keyName.split(":")[0] : null;

		String type = field[1];

		String childPath = keyPath.substring(keyPath.indexOf("/") + 1);

		if (type.equals(OBJECT)) {
			Element curXml = xml.getChild(key, nameSpaces.get(keyPrefix));
			getValueFromXml(curXml, childPath, list, attributeName, nameSpaces);
		} else if (IContants.LIST.equals(type) || IContants.LISTOBJECT.equals(type)
				|| IContants.LISTLIST.equals(type)) {
			@SuppressWarnings("unchecked")
			List<Element> array = xml.getChildren(key, nameSpaces.get(keyPrefix));
			for (Element obj : array) {
				getValueFromXml(obj, childPath, list, attributeName, nameSpaces);

			}
		} else if (type.equals(ARRAY) || type.equals(FIELD)) {
			if (null != xml && xml.getChild(key, nameSpaces.get(keyPrefix)) != null) {
				String text = xml.getChild(key, nameSpaces.get(keyPrefix)).getText();
				text = text == null ? "" : text;
				list.add(text);
			}
		}
	}

	/***
	 * 
	 * Description: 往json串中传入值(对于list只支持一层嵌套) 目前支持一层list的拼接（多层list可能会造成数据的丢失）
	 * 
	 * @param sourParam
	 * @param node
	 * @return List<Object>
	 *
	 * @see
	 */
	// TODO 测试完以后改为private
	public static void putValueToJSON(JSONArray array, String keyPath, List<Object> list) {
		if (array == null)
			return;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (array.size() <= i) {
				JSONObject jsonObj = new JSONObject();
				array.add(jsonObj);
			}
			JSONObject jsonObj = array.getJSONObject(i);
			putValueToJSON(jsonObj, keyPath, list);
		}
	}

	/***
	 * 
	 * Description: 往json串中传入值(对于list只支持一层嵌套) 目前支持一层list的拼接（多层list可能会造成数据的丢失）
	 * 
	 * @param sourParam
	 * @param node
	 * @return List<Object>
	 *
	 * @see
	 */
	// TODO 测试完以后改为private
	/**
	 * @param json
	 * @param keyPath
	 * @param list
	 */
	public static void putValueToJSON(JSONObject json, String keyPath, List<Object> list) {
		if (json == null)
			return;
		String[] nodeArray = keyPath.split("/");
		String[] field = nodeArray[0].split(splitString);
		String key = field[0];
		String type = field[1];
		String childPath = keyPath.substring(keyPath.indexOf("/") + 1);
		if (type.equals(OBJECT)) {

			JSONObject jsonObj = json.getJSONObject(key);
			if (jsonObj == null)
				jsonObj = new JSONObject();
			putValueToJSON(jsonObj, childPath, list);
			json.put(key, jsonObj);
		} else if (type.equals(LISTOBJECT)) {
			JSONArray array = json.getJSONArray(key);
			// 获取每层list的个数，没心新建jsonArray的时候获取到需要处理的list个数
			if (array == null) {
				array = new JSONArray();
			}
			putValueToJSON(array, childPath, list);
			json.put(key, array);
		}

		else if (type.equals(LIST)) {
			String keyPath2 = childPath.substring(childPath.indexOf("/") + 1);
			String[] nodeArray2 = keyPath2.split("/");
			String[] field2 = nodeArray2[0].split(splitString);
			if(nodeArray2.length<2||field2[2]==null) {
				JSONArray array = json.getJSONArray(key);
				if (array == null) {
					array = new JSONArray();
				}
				for(Object obj:list) {
					array.add(obj);
				}
				json.put(key, array);
				return ;
			}
			//String key2 = field2[0];
			int order = 0;
			try {
				order = Integer.valueOf(field2[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 将虚拟参数拼装为普通的object对象，在进行往下递归
			JSONArray array = json.getJSONArray(key);
			if (array == null) {
				array = new JSONArray();
			}
			// Object childObject = order >= array.size() ? null : array.get(order);
			int childSize = array.size();
		
			if(order>=childSize) {
				//将没有值的数字赋值为null
				for(int k=childSize;k<order;k++) {
					array.add(k, null);
				}	
			}
			array.set(order, list == null ? null : list.get(0));
			json.put(key, array);

		} else if (type.equals(LISTLIST)) {

			String keyPath2 = childPath.substring(childPath.indexOf("/") + 1);
			String[] nodeArray2 = keyPath2.split("/");
			String[] field2 = nodeArray2[0].split(splitString);
			//String key2 = field2[0];
			int order = 0;
			try {
				order = Integer.valueOf(field2[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 将虚拟参数拼装为普通的object对象，在进行往下递归
			JSONArray array = json.getJSONArray(key);
			if (array == null) {
				array = new JSONArray();
			}
			// Object childObject = order >= array.size() ? null : array.get(order);

			int size = list.size();
			if(size!=0) {
				for (int i = 0; i < size; i++) {
					if (array.size()==0 ||array.size()<(i+1)||null == array.get(i)) {
						array.add(i, new JSONArray());
					}
					JSONArray childArray = (JSONArray) array.get(i);
					
					int childSize = childArray.size();
					
					if(order>=childSize) {
						//将没有值的数字赋值为null
						for(int k=childSize;k<order;k++) {
							childArray.add(k, null);
						}	
					}
					
					
					childArray.set(order, list.get(i));
					
				}
			}
			json.put(key, array);

		}

		else {
			Object value = null;
			if (list.size() > 0) {
				value = list.get(0);
				list.remove(0);
			}
			json.put(key, value);
		}
	}

	/**
	 * 
	 * Description: 初始化命名空间参数
	 * 
	 * @deprecated
	 * @param json
	 * @param keyPath
	 * @param nameSpaces
	 * @param list
	 *            void
	 *
	 * @see
	 */
	public static Element putNameSpaceToXmlNode(Element sourceElement, String keyPath,
			Map<String, Namespace> nameSpaces) {

		if (sourceElement == null)
			return null;
		String[] nodeArray = keyPath.split("/");
		String[] field = nodeArray[0].split(splitString);
		String keyName = field[0];
		// 对于命名空间获取后面的值( soap:body 存在的情况下 这里的key就是body)
		String key = keyName.split(":").length > 1 ? keyName.split(":")[1] : keyName;

		String keyPrefix = keyName.split(":").length > 1 ? keyName.split(":")[0] : null;

		String type = field[1];
		String childPath = keyPath.substring(keyPath.indexOf("/") + 1);
		if (keyPath.contains("/") && type.equals(OBJECT)) {

			Element element = sourceElement.getChild(key, nameSpaces.get(keyPrefix));

			putNameSpaceToXmlNode(element, childPath, nameSpaces);
		} else if (keyPath.contains("/") && (IContants.LIST.equals(type) || IContants.LISTOBJECT.equals(type)
				|| IContants.LISTLIST.equals(type))) {
			@SuppressWarnings("unchecked")
			List<Element> array = sourceElement.getChildren(key, nameSpaces.get(keyPrefix));
			if (array == null) {
				array = new ArrayList<Element>();
				sourceElement.addContent(array);
			}
			int size = 1;
			for (int i = 0; i < size; i++) {
				Element child = array.get(i);
				putNameSpaceToXmlNode(child, childPath, nameSpaces);
			}

		} else {
			Namespace nameSpace = nameSpaces.get(keyPrefix);
			sourceElement.addNamespaceDeclaration(nameSpace);

		}
		return sourceElement;
	}

	/***
	 * 
	 * Description: 往xml子节点串中传入值(对于list只支持一层嵌套) 目前支持一层list的拼接（多层list可能会造成数据的丢失）
	 * 
	 * @param nameSpaces
	 * @废弃
	 * @param sourParam
	 * @param node
	 * @return List<Object>
	 *
	 * @see
	 */
	public static Element putValueToXml(Element sourceElement, String keyPath, List<Object> list, String attr,
			Map<String, Namespace> nameSpaces) {
		String[] nodeArray = keyPath.split("/");
		String[] field = nodeArray[0].split(splitString);
		String keyName = field[0];
		// 对于命名空间获取后面的值( soap:body 存在的情况下 这里的key就是body)
		String key = keyName.split(":").length > 1 ? keyName.split(":")[1] : keyName;

		String keyPrefix = keyName.split(":").length > 1 ? keyName.split(":")[0] : null;

		if (sourceElement == null) {
			sourceElement = initRoot(sourceElement, key);
		}
		if (keyPrefix != null) {
			sourceElement.setNamespace(nameSpaces.get(keyPrefix));
		}
		keyPath = keyPath.substring(keyPath.indexOf("/") + 1);

		// 这个表示这个属性位于根节点上的属性
		if (nodeArray.length == 1 && attr != null) {
			sourceElement.setAttribute(attr, (String) list.get(0));
			return sourceElement;
		} else {
			return putValueToXmlNode(sourceElement, keyPath, list, attr, nameSpaces);
		}
	}

	/***
	 * 
	 * Description: 初始换节点信息
	 *
	 * @see
	 */
	private static Element initRoot(Element sourceElement, String key) {
		sourceElement = new Element(key);
		return sourceElement;
	}

	/***
	 * 
	 * Description: 往xml子节点串中传入值(对于list只支持一层嵌套) 目前支持一层list的拼接（多层list可能会造成数据的丢失）
	 * 
	 * @param sourParam
	 * @param node
	 * @return List<Object>
	 *
	 * @see
	 */
	private static Element putValueToXmlNode(Element sourceElement, String keyPath, List<Object> list, String attr,
			Map<String, Namespace> nameSpaces) {

		String[] nodeArray = keyPath.split("/");
		String[] field = nodeArray[0].split(splitString);
		String keyName = field[0];
		// 对于命名空间获取后面的值( soap:body 存在的情况下 这里的key就是body)
		String key = keyName.split(":").length > 1 ? keyName.split(":")[1] : keyName;

		String keyPrefix = keyName.split(":").length > 1 ? keyName.split(":")[0] : null;

		String type = field[1];
		String childPath = keyPath.substring(keyPath.indexOf("/") + 1);
		// nodeArray.length>1 代表还有下层
		if (nodeArray.length > 1 && type.equals(OBJECT)) {

			Element element = sourceElement.getChild(key, nameSpaces.get(keyPrefix));
			if (element == null) {
				element = new Element(key);
				element.setNamespace(nameSpaces.get(keyPrefix));
				sourceElement.addContent(element);
			}
			putValueToXmlNode(element, childPath, list, attr, nameSpaces);
		} else if (nodeArray.length > 1 && type.equals(LIST)) {
			@SuppressWarnings("unchecked")
			List<Element> array = sourceElement.getChildren(key, nameSpaces.get(keyPrefix));
			if (array == null) {
				array = new ArrayList<Element>();
				sourceElement.addContent(array);
			}
			int size = list.size();
			for (int i = 0; i < size; i++) {
				if (array.size() <= i) {
					Element child = new Element(key);
					child.setNamespace(nameSpaces.get(keyPrefix));
					array.add(child);
				}
				Element child = array.get(i);
				putValueToXmlNode(child, childPath, list, attr, nameSpaces);
			}

		} else {
			Element child = sourceElement.getChild(key, nameSpaces.get(keyPrefix));
			if (child == null) {
				child = new Element(key);
				child.setNamespace(nameSpaces.get(keyPrefix));
				sourceElement.addContent(child);
			}

			String value = null;
			if (list.size() > 0) {
				value = (String) list.get(0);
				list.remove(0);
			}
			if (attr != null) {
				child.setAttribute(attr, value);
			} else {
				child.setText(value);
			}
		}
		return sourceElement;
	}

	/***
	 * 
	 * Description: 替换空白区域
	 * 
	 * @param str
	 * @return String
	 *
	 * @see
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}
