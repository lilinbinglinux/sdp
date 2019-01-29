package com.sdp.servflow.pubandorder.util;

import java.util.Map;
import java.util.Map.Entry;

import com.sdp.servflow.pubandorder.node.model.node.InterfaceNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;

public class MapUtil {

	/**
	 * 获取map中第一个数据值
	 *
	 * @param <K>
	 *            Key的类型
	 * @param <V>
	 *            Value的类型
	 * @param map
	 *            数据源
	 * @return 返回的值
	 */
	public static <K, V> V getFirstOrNull(Map<K, V> map) {
		V obj = null;
		for (Entry<K, V> entry : map.entrySet()) {
			obj = entry.getValue();
			if (obj != null) {
				break;
			}
		}
		return obj;
	}

	/**
	 * 获取map中第一个数据值
	 *
	 * @param <K>
	 *            Key的类型
	 * @param <V>
	 *            Value的类型
	 * @param map
	 *            数据源
	 * @return 返回的值
	 */
	public static Node getStartNode(Map<String, Node> map) {// 这里将map.entrySet()转换成list
		if (map != null) {
			for (Node node : map.values()) {
				if (node.getNodeStyle() != null && Node.startNodeStyle.equals(node.getNodeStyle())) {
					return node;
				}
			}

		}
		return null;
	}

	/**
	 * 获取map中第一个数据值
	 *
	 * @param <K>
	 *            Key的类型
	 * @param <V>
	 *            Value的类型
	 * @param map
	 *            数据源
	 * @return 返回的值
	 */
	public static Node getEndNode(Map<String, Node> map) {// 这里将map.entrySet()转换成list
		/*
		 * List<Entry<String, Node>> list = new
		 * ArrayList<Map.Entry<String,Node>>(map.entrySet()); //然后通过比较器来实现排序
		 * Collections.sort(list,new Comparator<Map.Entry<String,Node>>() { //升序排序
		 * 
		 * @Override public int compare(Entry<String, Node> o1, Entry<String, Node> o2)
		 * { int type1 = Integer.valueOf(o1.getValue().getNodeStyle()); int type2 =
		 * Integer.valueOf(o2.getValue().getNodeStyle()); return type2-type1; }
		 * 
		 * });
		 * 
		 * Entry<String, Node> obj = list.get(0); return obj.getValue();
		 */

		if (map != null) {
			for (Node node : map.values()) {
				if (node.getNodeStyle() != null && Node.endNodeStyle.equals(node.getNodeStyle())) {
					return node;
				}
			}

		}
		return null;
	}

	/**
	 * 获取callType为1的数量
	 * 
	 * @param map
	 * @return
	 */
	public static int getAsyInterfaceNum(Map<String, Node> map) {
		int sum = 0;
		if (map != null) {
			for (Node node : map.values()) {
				if (node.getNodeStyle() != null && Node.interfaceNodeStyle.equals(node.getNodeStyle())) {
					InterfaceNode interfacenode = (InterfaceNode) node;
					if (interfacenode != null && InterfaceNode.synType.equals(interfacenode))
						sum++;
				}
			}

		}
		return sum;
	}
}
