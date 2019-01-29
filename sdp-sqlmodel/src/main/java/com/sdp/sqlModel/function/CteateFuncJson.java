package com.sdp.sqlModel.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

public class CteateFuncJson {
	/**
	 * 创建函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public String outFunctionTree(){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "0", "1", "2", "3", "4", "10", "5", "6", "7", "8", "9" };
		String[] text = { 
				"数值函数",
				"字符串函数",
				"日期函数",
				"转换函数",
				"类型不定函数",
				"工资函数",
				"常量",
				"逻辑操作符",
				"算术运算符",
				"关系运算符",
				"其他" };
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("isParent", "true");
			list.add(map);
			list.addAll(outMainpTree(id[i]));

		}
		
		JSONArray json = JSONArray.fromObject(list);
		xmls.append(json.toString());
		return xmls.toString();
	}

	/**
	 * 创建函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outMainpTree(String treeid){
		int i = Integer.parseInt(treeid);
		List<Map<String, String>> xmls = null;
		switch (i) {
		case 0:
			xmls = outNumericalTree(treeid);
			break;
		case 1:
			xmls = outStrTree(treeid);
			break;
		case 2:
			xmls = outDateTree(treeid);
			break;
		case 3:
			xmls = outTransferTree(treeid);
			break;
		case 4:
			xmls = outVolatileTree(treeid);
			break;
		case 5:
			xmls = outConstantsTree(treeid);
			break;
		case 6:
			xmls = outLogicTree(treeid);
			break;
		case 7:
			xmls = outOperatorsTree(treeid);
			break;
		case 8:
			xmls = outRelationsTree(treeid);
			break;
		case 9:
			xmls = outOtherTree(treeid);
			break;
		case 10:
			xmls = outSalaryTree(treeid);
			break;
		}

		return xmls;
	}

	/**
	 * 创建数值函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outNumericalTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "N_num0", "N_num1_4", "N_num2_2", "N_num3", "N_num4",
				"N_num5", "NN_num6" };
		String[] text = {
				"取整" + "("
						+ "数值表达式" + ")",
				"取余数" + "("
						+ "数值表达式" + ","
						+ "数值表达式" + ")",
				"四舍五入" + "("
						+ "数值表达式" + ","
						+ "整数"
						+ ")",
				"三舍七入" + "("
						+ "数值表达式" + ")",
				"逢分进元" + "("
						+ "数值表达式" + ")",
				"逢分进角" + "("
						+ "数值表达式" + ")",
				"幂(底数，次方)" };


		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}

	/**
	 * 创建字符串函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outStrTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "A_str0", "A_str1", "A_str2", "A_str3_2_2", "A_str4",
				"A_str5_2", "A_str6_2", "AA_str7" };

		String[] text = {
				"去空格" + "("
						+ "字符串表达式" + ")",
				"去左空格" + "("
						+ "字符串表达式" + ")",
				"去右空格" + "("
						+ "字符串表达式" + ")",
				"子串" + "("
						+ "字符串表达式" + ","
						+ "整数"
						+ ","
						+ "整数"
						+ ")",
				"串长" + "("
						+ "字符串表达式" + ")",
				"左串" + "("
						+ "字符串表达式" + ","
						+ "整数"
						+ ")",
				"右串" + "("
						+ "字符串表达式" + ","
						+ "整数"
						+ ")",
				"登录用户名" + "(1|2)" };

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}

	/**
	 * 创建日期函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outDateTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "D_data0", "D_data1", "D_data2", "D_data3", "D_data4",
				"D_data5", "DD_data6", "DD_data7", "DD_data8", "DD_data9",
				"DD_data10", "DD_data11", "D_data12", "D_data13", "D_data14",
				"D_data15_1", "D_data16_1", "D_data17_1", "D_data18_1",
				"D_data19_1", "D_data20_2", "D_data21_2", "D_data22_2",
				"D_data23_2", "D_data24_2", "DD_data25" };

		String[] text = {
				"年" + "("
						+ "日期" + ")",
				"月" + "("
						+ "日期" + ")",
				"日" + "("
						+ "日期" + ")",
				"季度" + "("
						+ "日期" + ")",
				"周" + "("
						+ "日期" + ")",
				"星期" + "("
						+ "日期" + ")",
				"今天",
				"本周",
				"本月",
				"本季度",
				"今年",
				"截止日期",
				"年龄" + "("
						+ "日期" + ")",
				"工龄" + "("
						+ "日期" + ")",
				"到月年龄" + "("
						+ "日期" + ")",
				"年数" + "("
						+ "日期" + "1,"
						+ "日期" + "2)",
				"月数" + "("
						+ "日期" + "1,"
						+ "日期" + "2)",
				"天数" + "("
						+ "日期" + "1,"
						+ "日期" + "2)",
				"季度数" + "("
						+ "日期" + "1,"
						+ "日期" + "2)",
				"周数" + "("
						+ "日期" + "1,"
						+ "日期" + "2)",
				"增加年数" + "("
						+ "日期" + ","
						+ "整数"
						+ ")",
				"增加月数" + "("
						+ "日期" + ","
						+ "整数"
						+ ")",
				"增加天数" + "("
						+ "日期" + ","
						+ "整数"
						+ ")",
				"增加季度数" + "("
						+ "日期" + ","
						+ "整数"
						+ ")",
				"增加周数" + "("
						+ "日期" + ","
						+ "整数"
						+ ")",
				"归属日期"
						+ "或"
						+ "归属日期"
						+ "()" };

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}

	/**
	 * 创建转换函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outTransferTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "T_str0", "T_str1", "T_data2", "T_num3", "T_vol7_2",
				"T_code4", "T_item6_5", "TT_tra5" };

		String[] text = {
				"字符转日期" + "("
						+ "字符串表达式" + ")",
				"字符转数值"+ "("
						+ "字符串表达式" + ")",
				"日期转字符" + "("
						+ "日期" + ")",
				"数值转字符" + "("
						+ "数值表达式" + ")",
				"数字转汉字"
						+ "("
						+ "指标"
						+ ","
						+ "参数"
						+ ")",
				"代码转名称" + "("
						+ "指标名称" + ")",
				"代码转名称" + "2("
						+ "表达式" + ","
						+ "代码类"
						+ ")",
				"代码指标"};

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}

	/**
	 * 创建类型不定函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outVolatileTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "V_vol6", "VV_vol0", "VV_vol1", "V_vol2_6", "V_vol3_6",
				"V_vol4_2_1", "V_vol5_3_3" };

		String[] text = {
				"为空" + "("
						+ "指标" + ")",
				"如果 那么 否则 结束",
				"分情况 如果 Lexp1 那么 exp1 如果 Lexp2 那么 exp2 [否则expn] 结束",
				"较大值" + "(exp1,exp2)",
				"较小值" + "(exp1,exp2)",
				"取 指标名称 [最近第|最初第] 整数 条记录",
				"统计 指标名称 满足 条件 [的最初一条记录|的最近一条记录|的最大值|的最小值|的总和|的平均值|的个数]" };

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}

	/**
	 * 创建常量函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outConstantsTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "CC_con0", "CC_con1", "CC_con2", "CC_con3", "CC_con4",
		"CC_con5" };

		String[] text = {
				"真",
				"假",
				"空" + "("
						+ "日期" + ")",
				"#2000.3.22#表示2000年3月22日",
				"#3.22#今年3月22号",
				"“张三”表示姓名为张三" };

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}

	/**
	 * 创建逻辑操作符函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outLogicTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "LL_log0", "LL_log1", "LL_log2" };

		String[] text = { "且",
				"或",
				"非" };


		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}

	/**
	 * 创建算术运算符函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outOperatorsTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "OO_opr0", "OO_opr1", "OO_opr2", "OO_opr3", "OO_opr4",
				"OO_opr5", "OO_opr6", "OO_opr7" };

		String[] text = { "+(加)",
				"-(减)",
				"*(乘)",
				"/(除)",
				"\\(整除)",
				"DIV(整除)",
				"%(求余)",
				"MOD(求余)" };

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}

	/**
	 * 创建关系运算符函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outRelationsTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "RR_rel0", "RR_rel1", "RR_rel2", "RR_rel3", "RR_rel4",
				"RR_rel5", "RR_rel6" };

		String[] text = {
				"=(" + "等于" + ")",
				">(" + "大于" + ")",
				">=(" + "大于等于" + ")",
				"<(" + "小于" + ")",
				"<=(" + "小于等于" + ")",
				"<>(" + "不等于" + ")",
				"LIKE(" + "包含"
						+ ")" };

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}

	/**
	 * 创建关系运算符函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outOtherTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "EE_oth0", "EE_oth1", "EE_oth2", "EE_oth3" };

		String[] text = {
				"( )" + "括号",
				"[ ]" + "中括号",
				"{ }" + "大括号",
				"//" + "注释标识" };

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}

	/**
	 * 创建工资函数树
	 * 
	 * @return xmls
	 * @throws Exception
	 */
	public List<Map<String, String>> outSalaryTree(String pid){
		StringBuffer xmls = new StringBuffer();
		String[] id = { "S_stan0", "S_sthl1", "S_sthl2", "S_item3_2_4",
				"S_item4_2_4", "S_item5_7_4_5",
				// "SS_sar6",
				// "SS_sar7",
				"SS_sar8", "SS_sar9" };

		String[] text = {
				"执行标准(标准号,横一,横二,纵一,纵二)",
				"就近就高(标准表号, 纵向指标, 结果指标)",
				"就近就低(标准表号, 纵向指标, 结果指标)",
				"前一个代码(代码指标,增量,极值代码)",
				"后一个代码(代码指标,增量,极值代码)",
				"代码调整(代码指标,增量,极大值代码,极小值代码)",
				// "分段计算",
				// "分段计算"+"2",
				"历史记录最初指标值",
				"上一个历史记录指标值" };

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (int i = 0; i < id.length; i++) {
			map = new HashMap<String, String>();
			map.put("id", id[i]);
			map.put("name", text[i]);
			map.put("pid", pid);
			map.put("isParent", "false");
			list.add(map);
			

		}
		return list;
	}
	/*public static void main(String[] args){
		CteateFuncJson js = new CteateFuncJson();
		System.out.println(js.outFunctionTree());
	}*/
}
