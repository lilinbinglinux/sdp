	package com.sdp.sqlModel.function;
	
	import java.sql.SQLException;
	
	
	import java.text.SimpleDateFormat;
	import java.util.ArrayList;
	import java.util.Calendar;
	import java.util.Date;
	import java.util.HashMap;
	import java.util.Iterator;
	import java.util.List;
	import java.util.Map;
	
	import org.apache.commons.lang.StringUtils;
	
	
	/**
	 * 查询条件解析器
	 * 
	 * @author lilinbing 
	 * 
	 */
	public class YksjParser implements IParserConstant {
		private String whereText="";
		private String targetFieldDataType;
	
		/**主要用于临时变量中套用临时变量*/
		private String StdTmpTable="";
		/*年月次*/
		private YearMonthCount ymc;
	
//		private RuleTypeService ruleTypeservice;
	
		// 解析结果
		private String result;
		// private ArrayList FCASESQLS = new ArrayList();// 解析后的结果
		// 解析结果(查询条件)//Delphi=FSQL
	
		private StringBuffer SQL = new StringBuffer();
		private String ResultString = "";
	
		// 用于处理select，get特殊函数
		private int CurFuncNum = 0;
	
	
		private FieldItem Field = null;
	
	
		//	/** 语法分析器属性列表(前提条件) */
		//	private UserView userView;    //用户信息及用户权限信息// 解析器调用者信息
	
		private List<FieldItem> fieldItems = null; /**指标集合(数据范围)*/
	
		private int ModeFlag = forNormal;//查询模式是Normal还是Search,用来标识是单表简单查询还是复杂查询
	
		private int VarType = 0;//	 变量类型
	
		private int InfoGroupFlag = forPerson;//目标信息组类型
	
		private String DbPre = "USR"; //应用库前缀
	
		private String TempTableName = "Ht"; //临时表名称
	
		private int DBType = Constant.MSSQL;//数据库类型标记 MSSQL = 1, ORACEL = 2, DB2 = 3;
	
		/** 语法分析器属性列表(语法分析部分) */
		private boolean bVerify = false; //语法效验标识
	
		private String FSource;// 全局:表达式原字符串
	
		private String strError;// 错误信息
	
		private boolean FError = false;// 错误标志
	
		private List<String> UsedSets = new ArrayList<String>();// 所用到的子集编号setid
	
		private Map<String,FieldItem> mapUsedFieldItems = new HashMap<String,FieldItem>();// 所用到的指标代号
	
		// private ArrayList UsedFields = new ArrayList();
		private List<String> SQLS = new ArrayList<String>();//
	
		private List<String> FCTONSQLS = new ArrayList<String>();// 代码转名称函数用到的Sql串
		/**执行标准或代码调整用到的SQL串*/
		private List<String> FSTDSQLS=new ArrayList<String>();
	
		private int nFSourceLen = 0; //要分析的字符串长度
	
		private int nCurPos = 0; //因子累加器
	
		private int tok;
	
		private String token;  //一个因子
	
		//因子数据类型 ( DELIMITER=1 分隔符类型  FIELDITEM=2 参数类型 
		//		       FUNC=3  函数类型   QUOTE=4 引用类型 )
		private int token_type;
		/**代码调整计数器*/
		private int nCodeAddTime=0;
		/**除数标识*/
		private boolean bDivFlag=false;
		/**
		 * 解析器构造函数
		 * 
		 * @param fieldItemList
		 *            指标集合
		 * @param ModeFlag
		 *            查询对象类型forNormal，forSearch
		 * @param VarType
		 *            参数类型
		 * @param InfoGroup
		 *            目标信息组类型 forPerson， forDepartment， forUnit， forParty，
		 *            forWorkParty
		 * @param dbPre
		 *            库前缀
		 */
		public YksjParser(List<FieldItem> fieldItemList, int modeFlag,
				int varType, int infoGroup,String dbPre,int dBType) {
	
			setFieldItems(fieldItemList);// 得到指标列表(数据范围)
	
			setModeFlag(modeFlag);// 设置查询模式是Normal还是Search
	
			setInfoGroupFlag(infoGroup); 
	
			setVarType(varType);
	
			setDbPre(dbPre); //库前缀
	
			// setVariableType(varType);
	
			this.DBType = dBType;
	
			/**年月次*/
			Date sysdate=new Date();
			this.ymc=new YearMonthCount(DateUtils.getYear(sysdate),DateUtils.getMonth(sysdate),DateUtils.getDay(sysdate),1);
	
		}
	
		private boolean Func_TodayPart(int FuncNum, RetValue retValue){
			if (!Get_Token())
				return false;
			if (tok == S_LPARENTHESIS) {
				if (!Get_Token())
					return false;
				if (tok != S_RPARENTHESIS) {
					Putback();
					SError(E_LOSSRPARENTHESE);
					return false;
				}
				if (!Get_Token())
					return false;
			}
			String SystemTime = DateStyle.getSystemTime()
					.substring(0, 10);
			retValue.setValue("'" + SystemTime + "'");
			retValue.setValueType(DATEVALUE);
	
			switch (FuncNum) {
			case FUNCTODAY: {
				SQL = SQL.append(Sql_switcher.getSqlFunc(DBType).getSqlFunc(DBType).today());
				break;
			}
			case FUNCTOWEEK: {
				SQL = SQL.append(Sql_switcher.getSqlFunc(DBType).toWeek());
				break;
			}
			case FUNCTOMONTH: {
				SQL = SQL.append(Sql_switcher.getSqlFunc(DBType).toMonth());
				break;
			}
			case FUNCTOQUARTER: {
				SQL = SQL.append(Sql_switcher.getSqlFunc(DBType).toQuarter());
				break;
			}
			case FUNCTOYEAR: {
				SQL = SQL.append(Sql_switcher.getSqlFunc(DBType).toYear());
				break;
			}
			}
			switch (FuncNum) {
			case FUNCTOWEEK: {
				retValue.setValue(new Integer(1));
				retValue.setValueType(INT);
				break;
			}
			case FUNCTOMONTH: {
				retValue.setValue(new Integer(SystemTime.substring(5, 7)));
				retValue.setValueType(INT);
				break;
			}
			case FUNCTOQUARTER: {
				int nq = Integer.parseInt(SystemTime.substring(5, 7)) / 3 + 1;
				retValue.setValue(new Integer(nq));// Integer.valueOf(SystemTime.substring(4,6)).intValue()
				// / 3);
				retValue.setValueType(INT);
				break;
			}
			case FUNCTOYEAR: {
				retValue.setValue(new Integer(SystemTime.substring(0, 4)));
				retValue.setValueType(INT);
				break;
			}
			}
			return true;
		}
	
		private boolean Func_AppDate(RetValue retValue){
			if (!Get_Token())
				return false;
			if (tok == S_LPARENTHESIS) {
				if (!Get_Token())
					return false;
				if (tok != S_RPARENTHESIS) {
					Putback();
					SError(E_LOSSRPARENTHESE);
					return false;
				}
				if (!Get_Token())
					return false;
			}
			String strDate ="";
			strDate = DateStyle.getSystemTime().substring(0, 10);
			retValue.setValue("'" + strDate + "'");
			retValue.setValueType(DATEVALUE);
			SQL = SQL.append(Sql_switcher.getSqlFunc(DBType).dateValue(strDate));
	
			return true;
		}
	
		private boolean Func_CalcAge(int FuncNum, RetValue retValue)throws Exception {
	
			int nYear1, nMonth1, nDay1;
			int nYear2, nMonth2, nDay2;
			String str, str1;
			str = SQL.toString();
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			str1 = this.SQL.toString();
			if (!retValue.IsDateType()) {
				SError(E_MUSTBEDATE);
				return false;
			}
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			// DecodeDate(retValue,nYear1,nMonth1,nDay1);
			nYear1 = retValue.getYearOfDate();
			nMonth1 = retValue.getMonthOfDate();
			nDay1 = retValue.getDayOfDate();
	
			// nYear2 = Integer.parseInt(Sql_switcher.getSqlFunc(DBType).sysYear());
			// nMonth2 = Integer.parseInt(Sql_switcher.getSqlFunc(DBType).sysMonth());
			// nDay2 = Integer.parseInt(Sql_switcher.getSqlFunc(DBType).sysDay());
	
			String strDate = "";
			if (FuncNum == FUNCAPPAGE) {
				strDate = DateStyle.getSystemTime().substring(0, 10);
			} else {
				strDate = DateStyle.getSystemTime()
						.substring(0, 10);
			}
			nYear2 = Integer.parseInt(strDate.substring(0, 4));
			nMonth2 = Integer.parseInt(strDate.substring(5, 7));
			nDay2 = Integer.parseInt(strDate.substring(8, 10));
	
			// str2 = Sql_switcher.getSqlFunc(DBType).dateValue(strDate);
	
			// System.out.println(retValue.getValue());
			// System.out.println(strDate);
	
			retValue.setValueType(INT);
			switch (FuncNum) {
			case FUNCAGE:
			case FUNCAPPAGE: {
				int nTemp = ((nYear2 - nYear1) * 10000 + (nMonth2 - nMonth1) * 100 + (nDay2 - nDay1)) / 10000;
				retValue.setValue(new Integer(nTemp));
				SQL.setLength(0);
				SQL.append(str);
				SQL.append(Sql_switcher.getSqlFunc(DBType).toInt(Sql_switcher.getSqlFunc(DBType).age(str1)));
				break;
			}
			case FUNCWORKAGE:
			case FUNCAPPWORKAGE: {
				SQL.setLength(0);
				SQL.append(str);
				SQL.append(Sql_switcher.getSqlFunc(DBType).toInt(Sql_switcher.getSqlFunc(DBType).workAge((str1))));
				retValue.setValue(new Integer(nYear2 - nYear1 + 1));
				break;
			}
			case FUNCMONTHAGE:
			case FUNCAPPMONTHAGE: {
				SQL.setLength(0);
				SQL.append(str);
				SQL.append(Sql_switcher.getSqlFunc(DBType).toInt(Sql_switcher.getSqlFunc(DBType).appMonthAge((str1))));
				retValue.setValue(new Integer(
						((nYear2 - nYear1) * 100 + (nMonth2 - nMonth1)) / 100));
				break;
			}
			}
			return Get_Token();
		}
	
		private boolean Func_DatePart(int DatePart, RetValue retValue)throws Exception {
			String str1 = "", str2 = "";// str = "",
			// str = WhereCond.toString();
			if (!Get_Token())
				return false;
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			str2 = SQL.toString();
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			str1 = SQL.toString();
			// System.out.println(retValue.values() + ":" +
			// retValue.getValueType());
			if (!retValue.IsDateType()) {
				SError(E_MUSTBEDATE);
				return false;
			}
			// System.out.println(this.FSource.charAt(nCurPos));
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			int nYear1 = retValue.getYearOfDate();
			int nMonth1 = retValue.getMonthOfDate();
			int nDay1 = retValue.getDayOfDate();
			switch (DatePart) {
			case FUNCYEAR: {
				SQL.setLength(0);
				SQL.append(str2);
				SQL.append(Sql_switcher.getSqlFunc(DBType).year(str1));
				break;
			}
			case FUNCMONTH: {
				SQL.setLength(0);
				SQL.append(str2);
				SQL.append(Sql_switcher.getSqlFunc(DBType).month(str1));
				break;
			}
			case FUNCDAY: {
				SQL.setLength(0);
				SQL.append(str2);
				SQL.append(Sql_switcher.getSqlFunc(DBType).day(str1));
				break;
			}
			case FUNCQUARTER: {
				SQL.setLength(0);
				SQL.append(str2);
				SQL.append(Sql_switcher.getSqlFunc(DBType).quarter(str1));
				break;
			}
			case FUNCWEEK: {
				SQL.setLength(0);
				SQL.append(str2);
				SQL.append(Sql_switcher.getSqlFunc(DBType).week(str1));
				break;
			}
			case FUNCWEEKDAY: {
				SQL.setLength(0);
				SQL.append(str2);
				SQL.append(Sql_switcher.getSqlFunc(DBType).weekDay(str1));
				// System.out.println(SQL.toString());
				break;
			}
			}
	
			switch (DatePart) {
			case FUNCYEAR: {
				retValue.setValue(new Integer(nYear1));
				retValue.setValueType(INT);
				break;
			}
			case FUNCMONTH: {
				retValue.setValue(new Integer(nMonth1));
				retValue.setValueType(INT);
				break;
			}
			case FUNCDAY: {
				retValue.setValue(new Integer(nDay1));
				retValue.setValueType(INT);
				break;
			}
			case FUNCQUARTER: {
	
				retValue.setValue(new Integer(nMonth1 / 3 + 1));
				retValue.setValueType(INT);
				break;
			}
			case FUNCWEEK: {// 本年第xx周
				// DateFormat df = DateFormat.getDateInstance();
				// Date d =
				// df.parse(((String)retValue.getValue()).substring(1,11));
				Calendar c = Calendar.getInstance();
				c.set(nYear1, nMonth1, nDay1);
	
				retValue.setValue(new Integer(c.get(Calendar.WEEK_OF_YEAR)));
				retValue.setValueType(INT);
				break;
			}
			case FUNCWEEKDAY: {// 礼拜xx
				Calendar c = Calendar.getInstance();
				c.set(nYear1, nMonth1, nDay1);
				retValue.setValue(new Integer(c.get(Calendar.DAY_OF_WEEK)));
				retValue.setValueType(INT);
				break;
			}
			}
			return Get_Token();
		}
	
		private boolean Func_DateDiff(int DatePart, RetValue retValue)throws Exception {
			String str, str1, str2;
			RetValue hold = new RetValue();
			str = SQL.toString();
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			str1 = SQL.toString();
			if (!retValue.IsDateType()) {
				SError(E_MUSTBEDATE);
				return false;
			}
			if (tok != S_COMMA) {
				Putback();
				SError(E_LOSSCOMMA);
				return false;
			}
			SQL.setLength(0);
			if (!Get_Token())
				return false;
	
			if (!level0(hold))
				return false;
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			if (!hold.IsDateType()) {
				SError(E_MUSTBEDATE);
				return false;
			}
			str2 = SQL.toString();
			SQL.setLength(0);
			SQL.append(str);
			switch (DatePart) {
			// 处理年数
			case FUNCYEARS: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).diffYears(str1,str2));
				retValue.setValue(new Integer(retValue.diffYear(hold)));
				retValue.setValueType(INT);
				break;
			}
			// 处理月数
			case FUNCMONTHS: {			
				SQL.append(Sql_switcher.getSqlFunc(DBType).diffMonths(str1,str2));
				retValue.setValue(new Integer(retValue.diffMonth(hold)));
				retValue.setValueType(INT);
				break;
			}
			// 处理天数
			case FUNCDAYS: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).diffDays(str1,str2));
				retValue.setValue(new Integer(retValue.diffDay(hold)));
				retValue.setValueType(INT);
				break;
			}
			// 处理季数
			case FUNCQUARTERS: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).diffQuarters(str1,str2));
				retValue.setValue(new Integer(retValue.diffMonth(hold) / 3));
				retValue.setValueType(INT);
				break;
			}
			// 处理周数
			case FUNCWEEKS: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).diffWeeks(str1,str2));
				retValue.setValue(new Integer(retValue.diffDay(hold) / 7));
				retValue.setValueType(INT);
				break;
			}
			}
			return Get_Token();
		}
	
		private boolean Func_DateAdd(int DatePart, RetValue retValue)throws Exception {
			String str, str1, str2;
			RetValue retValue1 = new RetValue();
			str = SQL.toString();
			if (!Get_Token())
				return false;
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			str1 = SQL.toString();
			if (!retValue.IsDateType()) {
				SError(E_MUSTBEDATE);
				return false;
			}
			if (tok != S_COMMA) {
				Putback();
				SError(E_LOSSCOMMA);
				return false;
			}
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if (!level0(retValue1))
				return false;
			str2 = SQL.toString();
			if (!retValue1.isIntType()) {
				SError(E_MUSTBEINTEGER);
				return false;
			}
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			SQL.setLength(0);
			SQL.append(str);
			switch (DatePart) {
			// 增加年数
			case FUNCADDYEAR: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).addYears(str1, str2));
				retValue.addYear(retValue1);
				break;
			}
			// 增加月数
			case FUNCADDMONTH: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).addMonths(str1, str2));
				retValue.addMonth(retValue1);
				break;
			}
			// 增加日数
			case FUNCADDDAY: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).addDays(str1, str2));
				retValue.addDay(retValue1);
				break;
			}
			// 增加季数
			case FUNCADDQUARTER: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).addQuarters(str1, str2));
				retValue.addQuarter(retValue1);
				break;
			}
			// 增加周数
			case FUNCADDWEEK: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).addWeeks(str1, str2));
				retValue.addWeek(retValue1);
				break;
			}
			}
	
			return Get_Token();
		}
	
		private boolean Func_Math(int FuncNum, RetValue retValue)throws Exception {
			String str = null, str1;// str2=null, strfmt=null;
			int nLength = 0;
			// x:Extended;
			// i:integer;
			RetValue retValue1 = new RetValue();
			str = SQL.toString();
			if (!Get_Token())
				return false;
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			str1 = SQL.toString();
			if (!(retValue.isFloatType() || retValue.isIntType())) {
				SError(E_MUSTBENUMBER);
				return false;
			}
	
			if (FuncNum == FUNCROUND) { // 四舍五入有两个参数
				if (tok != S_COMMA) {
					Putback();
					SError(E_LOSSCOMMA);
					return false;
				}
				SQL.setLength(0);
				if (!Get_Token())
					return false;
				if (!level0(retValue1))
					return false;
				// str2 = WhereCond.toString();
				if (!retValue1.isIntType()) {
					SError(E_MUSTBEINTEGER);
					return false;
				}
	
				nLength = ((Integer) retValue1.getValue()).intValue();
	
			}
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			SQL.setLength(0);
			SQL.append(str);
			switch (FuncNum) {
			case FUNCINT: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).toInt(str1));
				break;
			}
			case FUNCROUND: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).round(str1, nLength));
				break;
			}
			case FUNCSANQI: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).sanqi(str1));
				break;
			}
			case FUNCYUAN: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).yuan(str1));
				break;
			}
			case FUNCJIAO: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).jiao(str1));
				break;
			}
			}
	
			switch (FuncNum) {
			case FUNCINT:
				if (retValue.isFloatType()) {
					retValue.setValue(new Integer(((Float) retValue.getValue()).intValue()));
					retValue.setValueType(INT);
				}
				break;
			case FUNCROUND: {
				if (retValue.isFloatType()) {
					// 四舍五入bug！！！
					//retValue.setValue(new Integer(((Float) retValue.getValue()).intValue()));
					if (nLength > 0) 
					{
						retValue.setValueType(FLOAT);
						retValue.setValue(((Float)retValue.getValue()));					
					}
					else 
					{
						retValue.setValueType(INT);
						retValue.setValue(new Integer(((Float) retValue.getValue()).intValue()));
					}
				}
				break;
			}
	
			case FUNCSANQI: {
				if (retValue.isFloatType()) {
					// Float FTemp = (Float) retValue.getValue();
					// retValue.setValue(new Float(().intValue()));
				}
				break;
			}
			case FUNCYUAN: {
				float fYuan;
				if (retValue.isFloatType()) {
					fYuan = ((Float) retValue.getValue()).floatValue();
					fYuan = Math.round(fYuan * 100) / 100;// 逢分进元 ??
					retValue.setValue(new Float(fYuan));
				}
				break;
			}
			case FUNCJIAO: {
				float fYuan;
				if (retValue.isFloatType()) {
					fYuan = ((Float) retValue.getValue()).floatValue();
					fYuan = (fYuan * 10) / 10;// 逢角进元 ？？
					retValue.setValue(new Float(fYuan));
				}
				break;
			}
			}
	
			return Get_Token();
		}
	
		private boolean Func_String(int FuncNum, RetValue retValue)throws Exception {
			String str = "", str1 = "", str2 = "", str3 = "";
			RetValue retValue1 = new RetValue();
			RetValue retValue2 = new RetValue();
			str = SQL.toString();
			if (!Get_Token())
				return false;
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			str1 = SQL.toString();
			if (!retValue.IsStringType()) {
				Putback();
				SError(E_MUSTBESTRING);
			}
			if ((FuncNum == FUNCLEFT) || (FuncNum == FUNCRIGHT)
					|| (FuncNum == FUNCSUBSTR)) {
				if (tok != S_COMMA) {
					Putback();
					SError(E_LOSSCOMMA);
					return false;
				}
				SQL.setLength(0);
				if (!Get_Token())
					return false;
				if (!level0(retValue1))
					return false;
				str2 = SQL.toString();
				if (!retValue1.isIntType()) {
					SError(E_MUSTBEINTEGER);
					return false;
				}
				if (FuncNum == FUNCSUBSTR) {
					if (tok != S_COMMA) {
						Putback();
						SError(E_LOSSCOMMA);
					}
					SQL.setLength(0);
					if (!Get_Token())
						return false;
					if (!level0(retValue2))
						return false;
					str3 = SQL.toString();
					if (!retValue2.isIntType()) {
						SError(E_MUSTBEINTEGER);
						return false;
					}
				}
			}
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			SQL.setLength(0);
			SQL.append(str);
			switch (FuncNum) {
			case FUNCTRIM: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).trim(str1));
				break;
			}
			case FUNCLTRIM: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).ltrim(str1));
				break;
			}
			case FUNCRTRIM: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).rtrim(str1));
				break;
			}
			case FUNCLEN: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).length(str1));
				break;
			}
			case FUNCLEFT: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).left(str1, str2));
				break;
			}
			case FUNCRIGHT: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).right(str1, str2));
				break;
			}
			case FUNCSUBSTR: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).substr(str1, str2, str3));
				break;
			}
			}
			switch (FuncNum) {
			case FUNCTRIM: {
				retValue.setValue(((String) retValue.getValue()).trim());
				break;
			}
			case FUNCLTRIM: {// 模拟String的LeftTrim()!!
				String strTemp = (String) retValue.getValue();
				retValue.setValue(strTemp
						.substring(strTemp.indexOf(strTemp.trim())));
				break;
			}
			case FUNCRTRIM: {// 模拟String的RightTrim()!!
				String strTemp = (String) retValue.getValue();
				retValue.setValue(strTemp.substring(0, strTemp.indexOf(strTemp
						.trim())
						+ strTemp.trim().length()));
				break;
			}
			case FUNCLEN: {
				retValue.setValue(new Integer(((String) retValue.getValue())
						.length()));
				retValue.setValueType(INT);
				break;
			}
			case FUNCLEFT: {
				int i = ((Integer) retValue1.getValue()).intValue();
				String str11 = (String) retValue.getValue();
				if (str11 != null && !str11.trim().equals("")) {
					retValue.setValue(((String) retValue.getValue())
							.substring(0, i));
				}
				break;
			}
			case FUNCRIGHT: {
				int i = ((Integer) retValue1.getValue()).intValue();
				int iLength = ((String) retValue.getValue()).length();
				iLength = iLength > i ? iLength - i : 0;
				retValue
				.setValue(((String) retValue.getValue()).substring(iLength));
				break;
			}
			case FUNCSUBSTR: {
				// System.out.println(retValue.getValue());
				int i = ((Integer) retValue1.getValue()).intValue() - 1;
				int j = i + ((Integer) retValue2.getValue()).intValue();
				String str11 = (String) retValue.getValue();
				if (str11 != null && !str11.trim().equals("")&&str11.length()>j) {
					retValue.setValue(((String) retValue.getValue())
							.substring(i, j));
				}
			}
			}
	
			return Get_Token();
		}
	
		private boolean Func_Convert(int FuncNum, RetValue retValue)throws Exception {
			String str = "", str1 = "";
			str = SQL.toString();
			if (!Get_Token())
				return false;
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			str1 = SQL.toString();
			switch (FuncNum) {
	
			case FUNCCTOD: {
				if (!retValue.IsStringType()) {
					Putback();
					SError(E_MUSTBESTRING);
					return false;
				}
				break;
			}
			case FUNCCTOI: {
				if (!retValue.IsStringType()) {
					Putback();
					SError(E_MUSTBESTRING);
					return false;
				}
				break;
			}
			case FUNCDTOC: {
				if (!retValue.IsDateType()) {
					Putback();
					SError(E_MUSTBEDATE);
					return false;
				}
				break;
			}
			case FUNCITOC: {
				if (!(retValue.isIntType() || retValue.isFloatType())) {
					Putback();
					SError(E_MUSTBENUMBER);
					return false;
				}
				break;
			}
			}
	
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			SQL.setLength(0);
			SQL.append(str);
			switch (FuncNum) {
			case FUNCCTOD: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).charToDate(str1));
				retValue.setValue("'2006.06.30'");
				retValue.setValueType(DATEVALUE);
				break;
			}
			case FUNCCTOI: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).charToFloat(str1));
				// retValue.setValue(new Integer(
				// Integer.parseInt(str1.trim())));
				retValue.setValueType(INT);
				break;
			}
			case FUNCDTOC: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).dateToChar(str1));
				retValue.setValue(str1);
				retValue.setValueType(STRVALUE);
				break;
			}
			case FUNCITOC: {
				SQL.append(Sql_switcher.getSqlFunc(DBType).floatToChar(str1));
				retValue.setValue(str1);
				retValue.setValueType(STRVALUE);
				break;
			}
			}
			return Get_Token();
		}
	
		private boolean Func_IIF(RetValue retValue) throws Exception,
		SQLException {
			String str;
			RetValue retValue1 = new RetValue(), retValue2 = new RetValue();
			if (!token.equalsIgnoreCase("IIF")) {
				return Func_CIIF(retValue);
			}
			str = SQL.toString(); // (
			if (!Get_Token())
				return false;
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
	
			SQL.setLength(0); // Logic expression &,
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			str = str + " CASE WHEN " + SQL;
	
			if (!retValue.isBooleanType()) {
				Putback();
				SError(E_MUSTBEBOOL);
				return false;
			}
			if (tok != S_COMMA) {
				Putback();
				SError(E_LOSSCOMMA);
				return false;
			}
	
			SQL.setLength(0); // expr1 &,
			if (!Get_Token())
				return false;
			if (!level0(retValue1))
				return false;
			if (tok != S_COMMA) {
				Putback();
				SError(E_LOSSCOMMA);
				return false;
			}
			str = str + " THEN " + SQL;
	
			SQL.setLength(0); // expr2 & )
			if (!Get_Token())
				return false;
			if (!level0(retValue2))
				return false;
			if (!(retValue1.IsSameType(retValue2) || retValue1.IsNullType() || retValue2
					.IsNullType())) {
				Putback();
				SError(E_NOTSAMETYPE);
				return false;
			}
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
			}
			str = str + " ELSE " + SQL + " END";
			if (((Boolean) retValue.getValue()).booleanValue()) {
				retValue.setValue(retValue1.getValue());
			} else {
				retValue.setValue(retValue2.getValue());
			}
			retValue.setValueType(retValue1.getValueType());
			SQL.setLength(0);
			SQL.append(str);
			return Get_Token();
		}
	
		private boolean Func_CIIF(RetValue retValue) throws Exception,
		SQLException {
			String str;
			RetValue retValue1 = new RetValue(), retValue2 = new RetValue();
			str = SQL.toString(); // (
			SQL.setLength(0); // Logic expression &,
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			str = str + " CASE WHEN " + SQL;
			if (!retValue.isBooleanType()) {
				Putback();
				SError(E_MUSTBEBOOL);
				return false;
			}
	
			if (tok != S_THEN) {
				Putback();
				SError(E_LOSSTHEN);
				return false;
			}
	
			SQL.setLength(0); // expr1 &,
			if (!Get_Token())
				return false;
			if (!level0(retValue1))
				return false;
			if (tok != S_ELSE) {
				Putback();
				SError(E_LOSSELSE);
				return false;
			}
			str = str + " THEN " + SQL;
	
			SQL.setLength(0); // expr2 & )
			if (!Get_Token())
				return false;
			if (!level0(retValue2))
				return false;
			if (!(retValue1.IsSameType(retValue2) || retValue1.IsNullType() || retValue2
					.IsNullType())) {
				Putback();
				SError(E_NOTSAMETYPE);
				return false;
			}
			if (tok != S_END) {
				Putback();
				SError(E_LOSSEND);
				return false;
			}
			str = str + " ELSE " + SQL + " END";
			// System.out.println(retValue.getValue());
			if (((Boolean) retValue.getValue()).booleanValue()) {
				retValue.setValue(retValue1.getValue());
			} else {
				retValue.setValue(retValue2.getValue());
			}
			retValue.setValueType(retValue1.getValueType());
			SQL.setLength(0);
			SQL.append(str);
			return Get_Token();
		}
	
		private boolean Func_CASE(RetValue retValue) throws Exception,
		SQLException {
			String  str = "";
			RetValue retValue1 = new RetValue(), retValue2 = new RetValue();
			int ntok;
	
			ntok = 0;
			// retValue:=NULL;
			retValue.setValue("NULL");
			retValue.setValueType(NULLVALUE);
			str = str + " CASE ";
			if (!Get_Token())
				return false; // 如果
			do {
				SQL.setLength(0);
				if ((tok == S_END) || (tok == S_ELSE))
					break;
				if (tok != FUNCIIF) {
					Putback();
					SError(E_LOSSIIF);
					return false;
				}
				if (!Get_Token())
					return false; // Logic expression &,
				if (!level0(retValue1))
					return false;
				// System.out.println(retValue1.getValue() + "," +
				// retValue1.getTypeString());
				if (!retValue1.isBooleanType()) {
					Putback();
					SError(E_MUSTBEBOOL);
					return false;
				}
				str = str + " WHEN " + SQL;
	
				if (tok != S_THEN) {
					Putback();
					SError(E_LOSSTHEN);
					return false;
				}
	
				SQL.setLength(0); // exp &,
				if (!Get_Token())
					return false;
				if (!level0(retValue2))
					return false;
				ntok = tok;
				if (!(retValue.IsSameType(retValue2) || retValue.IsNullType() || retValue2
						.IsNullType())) {
					Putback();
					SError(E_NOTSAMETYPE);
					return false;
				}
				str = str + " THEN " + SQL;
				// System.out.println(retValue1.getValue());
				if (((Boolean) retValue1.getValue()).booleanValue()) {
					retValue.setValue(retValue2.getValue());
					retValue.setValueType(retValue2.getValueType());
				}
			} while (ntok == FUNCIIF);
	
			if (retValue.IsNullType()) {
				retValue.setValue(retValue2.getValue());
				retValue.setValueType(retValue2.getValueType());
			}
			if (tok == S_ELSE) { // ELSE
				SQL.setLength(0); // expr1 &,
				if (!Get_Token())
					return false;
				if (!level0(retValue))
					return false;
				str = str + " ELSE " + SQL;
	
				if (tok != S_END) {
					Putback();
					SError(E_LOSSEND);
					return false;
				}
			}
			if (tok != S_END) {
				Putback();
				SError(E_LOSSEND);
				return false;
			}
			str = str + " END";
			SQL.setLength(0);
			// System.out.println( SQL.toString());
			SQL.append(str);
			return Get_Token();
		}
	
		private boolean IsMainSet(String strSet) {
			// System.out.println(strSet.substring(1, 3));
			return strSet.substring(1, 3).equals("01");
		}
	
		private boolean Func_SELECT(RetValue retValue) throws Exception,
		SQLException {
			FieldItem Field1 = new FieldItem();
			RetValue retValue1 = new RetValue();
			int nSQLtype = 0;
	
			if (token.equals("统计")) {
				return Func_CSELECT(retValue);
			}
	
			CurFuncNum = FUNCSELECT;
			if (ModeFlag == forNormal) {
				Putback();
				SError(E_GETSELECT);
				return false;
			}
			if (!Get_Token())
				return false;
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
	
			if (!Get_Token())
				return false; // 指标
	
			if (!level0(retValue))
				return false;
			if (tok != S_COMMA) {
				Putback();
				SError(E_LOSSCOMMA);
				return false;
			}
	
			if (IsMainSet(Field.getFieldsetid())) {
				Putback();
				SError(E_MUSTBESUBSET);
				return false;
			}
			Field1 = new FieldItem();
			if (retValue.IsStringType()) {
				Field1.setItemtype("A");
				Field1.setItemlength(80);
			}
			if (retValue.IsDateType()) {
				Field1.setItemtype("D");
				Field1.setItemlength(10);
			}
			if (retValue.isIntType()) {
				Field1.setItemtype("N");
				Field1.setItemlength(10);
			}
			if (retValue.isFloatType()) {
				Field1.setItemtype("N");
				Field1.setItemlength(10);
			}
			Field1 = (FieldItem) Field.cloneItem();
			Field1.setItemid(SQL.toString());
	
			SQL.setLength(0); // 条件
			if (!Get_Token()) {
				Field1 = null;
				return false;
			}
			if (!level0(retValue1)) {
				Field1 = null;
				return false;
			}
			if (tok != S_COMMA) {
				Field1 = null;
				Putback();
				SError(E_LOSSCOMMA);
				return false;
			}
			if (!retValue1.isBooleanType()) {
				Field1 = null;
				Putback();
				SError(E_MUSTBEBOOL);
				return false;
			}
	
			SQL.setLength(0); // SELECT 类型
			if (!Get_Token()) {
				Field1 = null;
				return false;
			}
			nSQLtype = tok;
			if (!((tok == S_FIRST) || (tok == S_LAST) || (tok == S_MAX)
					|| (tok == S_MIN) || (tok == S_MAX) || (tok == S_AVG)
					|| (tok == S_COUNT) || (tok == S_SUM))) {
				Field1 = null;
				Putback();
				SError(E_MUSTBESQLSYMBOL);
				return false;
			}
			if (!Get_Token()) {
				Field1 = null;
				return false;
			}
			if (tok != S_RPARENTHESIS) {
				Field1 = null;
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			if (!Get_Token()) {
				Field1 = null;
				return false;
			}
	
			SQL.setLength(0);
			switch (nSQLtype) {
			case S_SUM:
			case S_AVG:
			case S_COUNT: {
	
				// 需要判断 retValue的原数据类型
				if (retValue.isFloatType()) {
					retValue.setValue(new Float(123.0));
				} else {
					retValue.setValue(new Integer(123));
				}
			}
			}
			CurFuncNum = 0;
			return true;
		}
	
		private boolean Func_CSELECT(RetValue retValue) throws Exception{
			FieldItem Field1 = new FieldItem();
	
			int nSQLtype;
			RetValue retValue1 = new RetValue();
			SQL.setLength(0);
			CurFuncNum = FUNCSELECT;
			if (ModeFlag == forNormal) {
				Putback();
				SError(E_GETSELECT);
				return false;
			}
			if (!Get_Token())
				return false; // 指标
	
			if (!level0(retValue))
				return false;
			if (tok != S_SATISFY) {
				Putback();
				SError(E_LOSSSATISFY);
				return false;
			}
			if (IsMainSet(Field.getFieldsetid())) {
				Putback();
				SError(E_MUSTBESUBSET);
				return false;
			}
			// FieldCopy(Field,Field1);
			// Field1=Field.clone();
			Field1.setItemdesc(Field.getItemdesc());
			Field1.setItemid(/*Field.getItemid()*/SQL.toString());
			Field1.setItemlength(Field.getItemlength());
			Field1.setItemtype(Field.getItemtype());
			Field1.setCodesetid(Field.getFieldsetid());
			Field1.setFieldsetid(Field.getFieldsetid());
	
			if (retValue.IsStringType()) {
				Field1.setItemtype("A");
				Field1.setItemlength(80);
			}
			if (retValue.IsDateType()) {
				Field1.setItemtype("D");
				Field1.setItemlength(10);
			}
			if (retValue.isIntType()) {
				Field1.setItemtype("N");
				Field1.setItemlength(10);
			}
			if (retValue.isFloatType()) {
				Field1.setItemtype("N");
				Field1.setItemlength(10);
				Field1.setDecimalwidth(Field.getDecimalwidth());
				// Field1.nFldDec :=2;
			}
			// strFldName =
			/**
			 * 分析SQL是否为指标，如果为指标时，前面需要加上子集名或库前缀
			 * 直接根据SQL串的长度来区分
			 */
			if(SQL.length()==5)
				GetCurMenu(true, Field1);
			else
				GetCurMenu(false, Field1);
	
			SQL.setLength(0); // 条件
			if (!Get_Token()) {
				Field1 = null;
				return false;
			}
			if (!level0(retValue1)) {
				Field1 = null;
				return false;
			}
			if (!retValue1.isBooleanType()) {
				Field1 = null;
				Putback();
				SError(E_MUSTBEBOOL);
				return false;
			}
			nSQLtype = tok;
			if (!((tok == S_FIRST) || (tok == S_LAST) || (tok == S_MAX)
					|| (tok == S_MIN) || (tok == S_MAX) || (tok == S_AVG)
					|| (tok == S_COUNT) || (tok == S_SUM))) {
				Field1 = null;
				Putback();
				SError(E_MUSTBESQLSYMBOL);
				return false;
			}
			if (!Get_Token()) {
				Field1 = null;
				return false;
			}
	
			SQL.setLength(0);
			switch (nSQLtype) {
			case S_SUM:
			case S_AVG:
			case S_COUNT: {
				// retValue=123;
				retValue.setValue(new Integer(123));
				retValue.setValueType(INT);
			}
			}
			CurFuncNum = 0;
			return true;
	
		}
	
		private boolean Func_CGET(RetValue retValue) throws Exception{
			RetValue retValue1 = new RetValue();
			CurFuncNum = FUNCGET;
			if (ModeFlag == forNormal) {
				Putback();
				SError(E_GETSELECT);
				return false;
			}
	
			if (!Get_Token())
				return false; // 取一个指标
			if (token_type != FIELDITEM) {
				Putback();
				SError(E_MUSTBEFIELDITEM);
				return false;
			}
			if (!level0(retValue))
				return false;
			if (IsMainSet(Field.getFieldsetid())) {
				Putback();
				SError(E_MUSTBESUBSET);
				return false;
			}
			if (!((tok == S_INCREASE) || (tok == S_DECREASE))) {
				Putback();
				SError(E_MUSTBEGETSYMBOL);
				return false;
			}
	
			SQL.setLength(0);
			if (!Get_Token())
				return false; // 取一个指标
			if (!level0(retValue1))
				return false;
			if (!retValue1.isIntType()) {
				Putback();
				SError(E_MUSTBEINTEGER);
				return false;
			}
			if (tok != S_GETEND) {
				Putback();
				SError(E_LOSSGETEND);
				return false;
			}
	
			return Get_Token();// then exit; //if FFlag=forOddVar,必须结束
		}
	
		private boolean Func_GET(RetValue retValue) throws Exception{
			RetValue retValue1 = new RetValue();
			if (token.equals("取")) {
				Func_CGET(retValue);
				return true;// 如果是中文跳出
			}
			CurFuncNum = FUNCGET;
			if (ModeFlag == forNormal) {
				Putback();
				SError(E_GETSELECT);
				return false;
			}
	
			if (!Get_Token())
				return false; // 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
	
			if (!Get_Token())
				return false; // 取一个指标
			if (token_type != FIELDITEM) {
				Putback();
				SError(E_MUSTBEFIELDITEM);
				return false;
			}
	
			if (!level0(retValue))
				return false;
			if (tok != S_COMMA) {
				Putback();
				SError(E_LOSSCOMMA);
				return false;
			}
			if (IsMainSet(Field.getFieldsetid())) {
				Putback();
				SError(E_MUSTBESUBSET);
				return false;
			}
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			// 取一个整数
			if (!level0(retValue1))
				return false;
			if (tok != S_COMMA) {
				Putback();
				SError(E_LOSSCOMMA);
				return false;
			}
			if (!retValue1.isIntType()) {
				Putback();
				SError(E_MUSTBEINTEGER);
				return false;
			}
	
			SQL.setLength(0);
			// GET类型分INCREASE、DECREASE
			if (!Get_Token())
				return false;
			if (!((tok == S_INCREASE) || (tok == S_DECREASE))) {
				Putback();
				SError(E_MUSTBEGETSYMBOL);
				return false;
			}
			if (!Get_Token())
				return false;
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			// if FFlag=forOddVar,必须结束
			CurFuncNum = 0;
			return true;
		}
	
		private boolean Func_Maxmin(int FuncNum, RetValue retValue)throws Exception {
			String str, str1, str2;
			RetValue retValue1 = new RetValue();
			str = SQL.toString(); // (
			if (!Get_Token())
				return false;
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
	
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			if (tok != S_COMMA) {
				Putback();
				SError(E_LOSSCOMMA);
				return false;
			}
			str1 = SQL.toString();
	
			SQL.setLength(0); // expr2 & )
			if (!Get_Token())
				return false;
			if (!level0(retValue1))
				return false;
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			str2 = SQL.toString();
	
			if (!(retValue.IsSameType(retValue1) || retValue.IsNullType() || retValue
					.IsNullType())) {
				Putback();
				SError(E_NOTSAMETYPE);
				return false;
			}
			SQL.setLength(0);
			switch (FuncNum) {
			case FUNCMAX: {
				SQL.append(str);
				SQL.append(" CASE WHEN (");
				SQL.append(str1);
				SQL.append(")>(");
				SQL.append(str2);
				SQL.append(") THEN ");
				SQL.append(str1);
				SQL.append(" ELSE ");
				SQL.append(str2);
				SQL.append(" END");
				break;
			}
			case FUNCMIN: {
				SQL.append(str);
				SQL.append(" CASE WHEN (");
				SQL.append(str1);
				SQL.append(")<(");
				SQL.append(str2);
				SQL.append(") THEN ");
				SQL.append(str1);
				SQL.append(" ELSE ");
				SQL.append(str2);
				SQL.append(" END");
				break;
			}
			}
			switch (FuncNum) {
			case FUNCMAX: {
				if (retValue.Smaller(retValue1)) {
					retValue.setValue(retValue1.getValue());
				}
				break;
			}
	
			case FUNCMIN: {
				if (retValue.Greater(retValue1)) {
					retValue.setValue(retValue1.getValue());
				}
				break;
			}
			}
			return Get_Token();
		}
		/**
		 * 代码转名称
		 * @param retValue
		 * @return
		 * @throws Exception
		 */
		private boolean Func_CTON(RetValue retValue){
			boolean b;
			b = (!token.equals("~"));
			CurFuncNum = FUNCCTON;
			SQL.setLength(0);
			if (b) {
				if (!Get_Token())
					return false;
				// 处理左括号
				if (tok != S_LPARENTHESIS) {
					Putback();
					SError(E_LOSSLPARENTHESE);
					return false;
				}
			}
			if (!Get_Token())
				return false;
			// 取一个指标
			if (token_type != FIELDITEM) {
				Putback();
				SError(E_MUSTBEFIELDITEM);
				return false;
			}
			if (b) {
				if (!Get_Token())
					return false; // 取)
				if (tok != S_RPARENTHESIS) {
					Putback();
					SError(E_LOSSRPARENTHESE);
					return false;
				}
			}
			if ((Field.getCodesetid().equals("0") || (Field.getCodesetid()
					.equals("")))) {
				Putback();
				SError(E_MUSTBECODEFIELD);
				return false;
			}
			if (!Get_Token())
				return false;
			return true;
		}
		/**
		 * 代码转名称2(表达式,代码类)
		 * @param retValue
		 * @return
		 * @throws Exception
		 */
		private boolean Func_CTON2(RetValue retValue) throws Exception {
			String strSQL="";
			String strCode="";
	
			if(ModeFlag==forSearch)
			{
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;			
			}
	
			CurFuncNum = FUNCCTON2;		
			strSQL=SQL.toString();
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			// 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			/**表达式*/
			if (!Get_Token())
				return false;
			if(!level0(retValue))
				return false;
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}
			/**取代码类*/
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if(!level6(retValue))
				return false;
			strCode=SQL.toString().trim();
			strCode=strCode.replaceAll("'", "");
			/**取右括号*/
			if (tok != S_RPARENTHESIS) {
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			if(strCode.equalsIgnoreCase("")||strCode.equalsIgnoreCase("0"))
			{
				Putback();
				SError(E_MUSTBECODEFIELD);
				return false;			
			}
			if (!Get_Token())
				return false;
			retValue.setValue("");
			retValue.setValueType(STRVALUE);
			SQL.setLength(0);		
			SQL.append(strSQL);
			return true;
		}
		/**
		 * 取余数
		 * @param retValue
		 * @return
		 * @throws Exception
		 */
		private boolean Func_MOD(RetValue retValue) throws Exception
		{
			String strSQL;
			String  FSQL="";
			String sL,sR;
			CurFuncNum = FUNCMOD;
			strSQL = SQL.toString();
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			// 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			//处理第一个参数
			if (!Get_Token())
				return false;
			if (!level6(retValue))
				return false;
			sL = SQL.toString();
			if (tok != S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;
			}
			//处理第二个参数
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if (!level6(retValue))
				return false;
			sR = SQL.toString();
			if(tok!=S_RPARENTHESIS)
			{
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}
			if (!Get_Token())
				return false;
			switch(DBType)
			{
			case Constant.MSSQL:
			{
				FSQL = sL+" % NullIF("+sR+",0)"; //考虑被除数为0的情况,chenmengqing added 20080401
				break;
			}
			case Constant.ORACEL:
			{
				FSQL = "MOD(Round("+sL+",0),NullIF(Round("+sR+",0),0))";
				break;
			}
			case Constant.DB2:
			{
				FSQL = "MOD(Round("+sL+",0),NullIF(Round("+sR+",0),0))";
				break;
			}
			}
			SQL.setLength(0);
			SQL.append(strSQL);
			SQL.append(" "+FSQL);		
			return true;
		}
		/**
		 * 分析某指标是否为空值 IS NULL
		 * @param retValue
		 * @return
		 * @throws Exception
		 * @throws SQLException
		 */
		private boolean Func_ISNNULL(RetValue retValue)throws Exception
		{
			CurFuncNum = FUNCISNULL;
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			// 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			/**指标*/
			if (!Get_Token())
				return false;	
			if (!level6(retValue))
				return false;
			FieldItem item=this.Field;
	
			if(tok!=S_RPARENTHESIS)
			{
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}		
			if (!Get_Token())
				return false;
			SQL.setLength(0);		
			SQL.append(item.getItemid());
			SQL.append(" IS NULL ");
			retValue.setValueType(LOGIC);
			retValue.setValue(new Boolean(true));
			CurFuncNum=0;
			return true;
		}
		/**
		 * 幂函数
		 * @param retValue
		 * @return
		 * @throws Exception
		 * @throws SQLException
		 */
		private boolean Func_POWER(RetValue retValue)throws  Exception
		{
			String sD=null;
			String sM=null;
			CurFuncNum = FUNCPOWER;
			String strSQL=SQL.toString();
			SQL.setLength(0);    	
			if (!Get_Token())
				return false;
			// 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			/**底数*/
			if (!Get_Token())
				return false;
			if(!level3(retValue))
				return false;
			sD=SQL.toString();
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}
			/**幂数*/
			SQL.setLength(0);
			if (!Get_Token())
				return false;	
			if (!level3(retValue))
				return false;
			sM=SQL.toString();
			if(tok!=S_RPARENTHESIS)
			{
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}	
			if (!Get_Token())
				return false;
	
			SQL.setLength(0);
			SQL.append(strSQL);
			SQL.append("POWER(");
			SQL.append(sD);
			SQL.append(",");
			SQL.append(sM);
			SQL.append(")");
	
			CurFuncNum=0;
			return true;    	
		}
		/**
		 * 数字转汉字函数
		 * NumConversion(指标名称,1|2)
		 * 含义：将指标值中的数字转换成汉字
		 *		参数=1：表示将数字替换成（○、一、二…九）
		 *		参数=2：表示将数字替换成（零、壹、贰…玖）
		 * @param retValue
		 * @return
		 * @throws Exception
		 * @throws SQLException
		 */
		private boolean Func_CNTC(RetValue retValue)throws Exception
		{
			String sNum1="○一二三四五六七八九";
			String sNum2="零壹贰叁肆伍陆柒捌玖";
			CurFuncNum = FUNCCNTC;
			String strSQL=SQL.toString();
			SQL.setLength(0);
			if (!Get_Token())
				return false;    	
			// 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			/**指标*/
			SQL.setLength(0);		
			if (!Get_Token())
				return false;
			if(!level6(retValue))
				return false;
			FieldItem item=this.Field;
			if(item.getItemtype().equalsIgnoreCase("M")||item.getItemtype().equalsIgnoreCase(""))
			{
				Putback();
				SError(E_NOTSAMETYPE);
				return false;			
			}
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}
			SQL.setLength(0);		
			if (!Get_Token())
				return false;
			if(this.token_type!=INT)
			{
				Putback();			
				SError(E_MUSTBEINTEGER);
				return false;			   
			}
			if(!level6(retValue))
				return false;
			String sMode=SQL.toString().trim();
			if(tok!=S_RPARENTHESIS)
			{
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}			
			if (!Get_Token())
				return false;
			StringBuffer  sSQL=new StringBuffer();
			if(item.getItemtype().equalsIgnoreCase("A"))
			{
				sSQL.append(item.getItemid());
			}
			else
			{
				switch(DBType)
				{
				case Constant.DB2:
					sSQL.append("Char(");
					break;
				case Constant.ORACEL:
					sSQL.append("To_Char(");
					break;
				default:
					sSQL.append("Convert(Varchar,");
					break;
				}
				sSQL.append(item.getItemid());
				sSQL.append(")");			
			}
			StringBuffer cnNum=new StringBuffer();
			if(sMode.equalsIgnoreCase("1"))
				cnNum.append(sNum1);
			else
				cnNum.append(sNum2);
			String tmp=sSQL.toString();
			sSQL.setLength(0);
			for (int i=0;i<10;i++)
			{
				sSQL.setLength(0);			
				sSQL.append("replace(");
				sSQL.append(tmp);
				sSQL.append(",'");
				sSQL.append(i);
				sSQL.append("','");
				sSQL.append(cnNum.charAt(i));
				sSQL.append("')");
				tmp=sSQL.toString();
			}
			SQL.setLength(0);
			SQL.append(strSQL);
			SQL.append(sSQL.toString());
			retValue.setValue("");
			retValue.setValueType(STRVALUE);		
			CurFuncNum=0;
			return true;
		} 
	
		/**
		 * 执行标准函数
		 * @param retValue
		 * @return
		 * @throws Exception
		 * @throws SQLException
		 */
		private boolean Func_Standard(RetValue retValue)throws Exception,
		SQLException {
			String strOldSQL=SQL.toString();
	
			CurFuncNum=FUNCSTANDARD;
			SQL.setLength(0);
			if (!Get_Token())
				return false;    	
			// 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			if (!Get_Token())
				return false;   
			if(token_type!=INT)
			{
				Putback();
				SError(E_MUSTBEINTEGER);
				return false;			
			}
			/**标准号*/
			if(!level6(retValue))
				return false;
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}
			/**横向指标一*/
			if (!Get_Token())
				return false;  
			if(!level6(retValue))
				return false;
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}		
			/**横向指标二*/
			if (!Get_Token())
				return false;  
			if(!level6(retValue))
				return false;
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}		
			/**纵向指标一*/
			if (!Get_Token())
				return false;  
			if(!level6(retValue))
				return false;
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}
			/**纵向指标二*/
			if (!Get_Token())
				return false;  
			if(!level6(retValue))
				return false;
			if(tok!=S_RPARENTHESIS)
			{
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}	
			if (!Get_Token())
				return false; 		
	
			/**检查结束*/
			retValue.setValueType(NULLVALUE);
	
			/**具体调用*/
			SQL.setLength(0);
			SQL.append(strOldSQL);//?
			CurFuncNum = 0;
			return true;
		}
	
		/**
		 * 就近就高或就近就低函数
		 * 就近就高(标准表号, 纵向指标, 结果指标)
		 * 就近就低(标准表号, 纵向指标, 结果指标)
		 * @param  retValue
		 * @param  mode    低|高 true|false
		 * @return
		 * @throws Exception
		 * @throws SQLException
		 */
		private boolean Func_NearByHight(RetValue retValue ,boolean mode)throws Exception,
		SQLException {
			if(mode)
				CurFuncNum=FUNCNEARBYHIGH;
			else
				CurFuncNum=FUNCNEARBYLOW;
			SQL.setLength(0);
			if (!Get_Token())
				return false;    	
			// 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			if (!Get_Token())
				return false;   
			if(token_type!=INT)
			{
				Putback();
				SError(E_MUSTBEINTEGER);
				return false;			
			}
			/**标准号*/
			if(!level6(retValue))
				return false;
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}
			/**纵向指标*/
			if (!Get_Token())
				return false;  
			if(!level6(retValue))
				return false;
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}
			/**结果指标*/
			if (!Get_Token())
				return false;  
			if(!level6(retValue))
				return false;
			if(tok!=S_RPARENTHESIS)
			{
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}	
			if (!Get_Token())
				return false;  
	
			return true;
		}
	
	
		/**
		 * 代码调整(指标,增量指标,极大值,极小值)
		 * @param retValue
		 * @return
		 * @throws Exception
		 * @throws SQLException
		 */
		private boolean Func_CODEADJUST(RetValue retValue) throws Exception,
		SQLException {
			CurFuncNum = FUNCCODEADJUST;
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			// 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			/**指标*/
			if (!Get_Token())
				return false;		
			if(!level6(retValue))
				return false;
			FieldItem item=this.Field;
			if(item.getCodesetid().equalsIgnoreCase("")||item.getCodesetid().equalsIgnoreCase("0"))
			{
				Putback();
				SError(E_MUSTBECODEFIELD);			
				return false;
			}
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}
			/**增量指标必须是数值型*/
			SQL.setLength(0);
			if (!Get_Token())
				return false;		
			if(!level6(retValue))
				return false;
			FieldItem fldadjust=this.Field;
			if(!(fldadjust.isInt()))
			{
				Putback();
				SError(E_MUSTBEINTEGERMENU);			
				return false;			
			}
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}
	
			SQL.setLength(0);
			if (!Get_Token())
				return false;	
			if(token_type!=QUOTE)
			{
				Putback();
				SError(E_LOSSQUOTE);
				return false;				
			}
			/**最大值*/
			if(!level6(retValue))
				return false;
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}		
			String  sMax=SQL.toString().trim();
			sMax=sMax.replaceAll("'", "");	
			if (!Get_Token())
				return false;	
			if(token_type!=QUOTE)
			{
				Putback();
				SError(E_LOSSQUOTE);
				return false;				
			}
			/**最小值*/
			SQL.setLength(0);
			if(!level6(retValue))
				return false;
			if(tok!=S_RPARENTHESIS)
			{
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}	
			String  sMin=SQL.toString().trim();
			sMin=sMin.replaceAll("'", "");			
			if (!Get_Token())
				return false;
	
			SQL.setLength(0);
			SQL.append(SQL_CodeAdjust(item,fldadjust,sMax,sMin));
			return true;
		}
	
		/**
		 * 前一个代码（指标,增量,极值)
		 * @param retValue
		 * @param mode    (前一个代码|后一个代码)(-1|1)
		 * @return
		 * @throws Exception
		 * @throws SQLException
		 */
		private boolean Func_CODEADD(RetValue retValue,int mode) throws Exception,
		SQLException {
			int flag=1;
			CurFuncNum = FUNCCODEADD;
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			// 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			/**指标*/
			if (!Get_Token())
				return false;		
			if(!level6(retValue))
				return false;
			FieldItem item=this.Field;
			if(item.getCodesetid().equalsIgnoreCase("")||item.getCodesetid().equalsIgnoreCase("0"))
			{
				Putback();
				SError(E_MUSTBECODEFIELD);			
				return false;
			}
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}
			/**增量,必须是整数正或负*/
			SQL.setLength(0);
			if (!Get_Token())
				return false;
			if(tok==S_MINUS)//可以考虑不用负数，要不然和前|后代码函数让用户误解
			{
				flag=-1;
				if (!Get_Token())
					return false;			
			}
			if(this.token_type!=INT)
			{
				Putback();			
				SError(E_MUSTBEINTEGER);
				return false;			   
			}
			if(!level6(retValue))
				return false;
			if(tok!=S_COMMA)
			{
				Putback();
				SError(E_LOSSCOMMA);
				return false;			
			}		
			String sAdd=SQL.toString().trim();
	
	
			SQL.setLength(0);
			if (!Get_Token())
				return false;	
			if(token_type!=QUOTE)
			{
				Putback();
				SError(E_LOSSQUOTE);
				return false;				
			}
	
			if(!level6(retValue))
				return false;
			if(tok!=S_RPARENTHESIS)
			{
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}		
			String  sMax=SQL.toString().trim();
			sMax=sMax.replaceAll("'", "");
			if (!Get_Token())
				return false;
			SQL.setLength(0);		
			int nadd=Integer.parseInt(sAdd);
			if(nadd==0)
				SQL.append(item.getItemid());
			else
			{
				SQL.append(SQL_CodeAdd(item,flag*nadd*mode,sMax));
			}
			CurFuncNum = 0;		
			return true;
		}  
	
		/**
		 * 代码调整
		 * @param item		
		 * @param fldadjust
		 * @param sMax
		 * @param sMin
		 * @return
		 * @throws Exception
		 */
		private String SQL_CodeAdjust(FieldItem item,FieldItem fldadjust,String sMax,String sMin)throws Exception
		{
			StringBuffer strSQL=new StringBuffer();
			String sTmpTb="T_"+TempTableName;
			++nCodeAddTime;
			FieldItem item0=null;
			item0=(FieldItem)item.cloneItem();
			item0.setFieldsetid(TempTableName);
			item0.setItemid("CodeADD_"+nCodeAddTime);
			String sRowFld="CodeRow_"+nCodeAddTime;
			String sAdjFld="CodeAdj";
			switch(DBType)
			{
			case Constant.DB2:
				/**暂时不支挂*/
				break;
			case Constant.ORACEL:
				strSQL.append("create table ");
				strSQL.append(sTmpTb);
				strSQL.append(" as select codeitemid,rownum rowid1 ");
				strSQL.append(" from codeitem where codesetid='");
				strSQL.append(item.getCodesetid());
				strSQL.append("'");
				break;
			default:
				strSQL.append("select codeitemid,Identity(int ,1,1) as rowid1 into ");
				strSQL.append(sTmpTb);
				strSQL.append(" from codeitem where codesetid='");
				strSQL.append(item.getCodesetid());
				strSQL.append("'");
				break;			
			}
			if(sMax.length()>0)
			{
				strSQL.append(" and ");
				strSQL.append(" codeitemid<='");
				strSQL.append(sMax);
				strSQL.append("'");
			}
			if(sMin.length()>0)
			{
				strSQL.append(" and ");
				strSQL.append(" codeitemid>='");
				strSQL.append(sMin);
				strSQL.append("'");
			}
			FSTDSQLS.add("drop table "+sTmpTb);
			FSTDSQLS.add(strSQL.toString());
	
			/**增加临时处理用到的指标*/
			String sConFld=item0.getItemid();
			String strR=sConFld+Sql_switcher.getSqlFunc(DBType).getFieldType('A',item0.getItemlength(), 0);
			strSQL.setLength(0);				
			strSQL.append("alter table ");
			strSQL.append(TempTableName);
			strSQL.append(" drop column ");
			strSQL.append(sConFld);		
			FSTDSQLS.add(strSQL.toString());
	
			strSQL.setLength(0);
			strSQL.append("alter table ");
			strSQL.append(TempTableName);
			strSQL.append(" add ");
			strSQL.append(strR);
			FSTDSQLS.add(strSQL.toString());
	
			strSQL.setLength(0);				
			strSQL.append("alter table ");
			strSQL.append(TempTableName);
			strSQL.append(" drop column ");
			strSQL.append(sRowFld);		
			FSTDSQLS.add(strSQL.toString());	
	
			strR=sRowFld+Sql_switcher.getSqlFunc(DBType).getFieldType('N',10, 0);
			strSQL.setLength(0);
			strSQL.append("alter table ");
			strSQL.append(TempTableName);
			strSQL.append(" add ");
			strSQL.append(strR);
			FSTDSQLS.add(strSQL.toString());
	
			strSQL.setLength(0);				
			strSQL.append("alter table ");
			strSQL.append(TempTableName);
			strSQL.append(" drop column ");
			strSQL.append(sAdjFld);		
			FSTDSQLS.add(strSQL.toString());	
	
			strR=sAdjFld+Sql_switcher.getSqlFunc(DBType).getFieldType('N',10, 0);
			strSQL.setLength(0);
			strSQL.append("alter table ");
			strSQL.append(TempTableName);
			strSQL.append(" add ");
			strSQL.append(strR);
			FSTDSQLS.add(strSQL.toString());
	
			/**和CS语法分析器有区别*/
			strSQL.setLength(0);				
			strSQL.append("update ");
			strSQL.append(TempTableName);
			strSQL.append(" set ");
			strSQL.append(sAdjFld);
			strSQL.append("=");
			strSQL.append(fldadjust.getItemid());
			FSTDSQLS.add(strSQL.toString());
	
			strSQL.setLength(0);				
			strSQL.append("update ");
			strSQL.append(TempTableName);
			strSQL.append(" set ");
			strSQL.append(item0.getItemid());
			strSQL.append("=");
			strSQL.append(item.getItemid());
			FSTDSQLS.add(strSQL.toString());
	
			switch(DBType)
			{
			case Constant.DB2:
				/**暂时先不支持*/
				break;
			case Constant.ORACEL:
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append("(select rowid1 from ");
				strSQL.append(sTmpTb);
				strSQL.append(" where ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());			
				//strSQL.append(sRowFld);
				strSQL.append("=");			
				strSQL.append(sTmpTb);
				strSQL.append(".");
				strSQL.append("codeitemid)");
				FSTDSQLS.add(strSQL.toString());
	
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");			
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append(sRowFld);
				strSQL.append("+");
				strSQL.append(sAdjFld);
	
				FSTDSQLS.add(strSQL.toString());
	
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("=");
				strSQL.append("(select codeitemid from ");
				strSQL.append(sTmpTb);
				strSQL.append(" where ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append(sTmpTb);
				strSQL.append(".rowid1)");
				FSTDSQLS.add(strSQL.toString());			
	
				/**处理运算后超过极值的，直接改为极值*/
				strSQL.setLength(0);
				strSQL.append("update ");			
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("='");
				strSQL.append(sMax);
				strSQL.append("'");
				strSQL.append(" where not (");
				strSQL.append(sRowFld);
				strSQL.append(" is null ) and not ");
				strSQL.append(sRowFld);
				strSQL.append(" in (select rowid1 from ");
				strSQL.append(sTmpTb);
				strSQL.append(") and ");
				strSQL.append(sAdjFld);
				strSQL.append(">0");
				strSQL.setLength(0);
				strSQL.append("update ");			
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("='");
				strSQL.append(sMin);
				strSQL.append("'");
				strSQL.append(" where not (");
				strSQL.append(sRowFld);
				strSQL.append(" is null ) and not ");
				strSQL.append(sRowFld);
				strSQL.append(" in (select rowid1 from ");
				strSQL.append(sTmpTb);
				strSQL.append(") and ");
				strSQL.append(sAdjFld);
				strSQL.append("<0");			
				FSTDSQLS.add(strSQL.toString());			
				break;
			default:
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append(sTmpTb);
				strSQL.append(".rowid1 from ");
				strSQL.append(sTmpTb);
				strSQL.append(" where ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("=");
				strSQL.append(sTmpTb);
				strSQL.append(".");
				strSQL.append("codeitemid");
				FSTDSQLS.add(strSQL.toString());
	
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");			
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append(sRowFld);
				strSQL.append("+");
				strSQL.append(sAdjFld);
				FSTDSQLS.add(strSQL.toString());
	
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("=");
				strSQL.append(sTmpTb);
				strSQL.append(".codeitemid from ");
				strSQL.append(sTmpTb);
				strSQL.append(" where ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append(sTmpTb);
				strSQL.append(".rowid1");
				FSTDSQLS.add(strSQL.toString());
	
				/**处理运算后超过极值的，直接改为极值*/
				strSQL.setLength(0);
				strSQL.append("update ");			
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("='");
				strSQL.append(sMax);
				strSQL.append("'");
				strSQL.append(" where not (");
				strSQL.append(sRowFld);
				strSQL.append(" is null ) and not ");
				strSQL.append(sRowFld);
				strSQL.append(" in (select rowid1 from ");
				strSQL.append(sTmpTb);
				strSQL.append(") and ");
				strSQL.append(sAdjFld);
				strSQL.append(">0");
				strSQL.append("");
				FSTDSQLS.add(strSQL.toString());
	
				strSQL.setLength(0);
				strSQL.append("update ");			
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("='");
				strSQL.append(sMin);
				strSQL.append("'");
				strSQL.append(" where not (");
				strSQL.append(sRowFld);
				strSQL.append(" is null ) and not ");
				strSQL.append(sRowFld);
				strSQL.append(" in (select rowid1 from ");
				strSQL.append(sTmpTb);
				strSQL.append(") and ");
				strSQL.append(sAdjFld);
				strSQL.append("<0");
				strSQL.append("");
				FSTDSQLS.add(strSQL.toString());			
	
				break;			
			}		
	
	
			return item0.getItemid();
		}	
		/**
		 * @param item   	代码型指档
		 * @param nstep		步长
		 * @param sMax		极值代码项
		 * @throws Exception
		 */
		private String SQL_CodeAdd(FieldItem item,int nstep,String sMax)throws Exception
		{
			StringBuffer strSQL=new StringBuffer();
			String sTmpTb="T_"+TempTableName;
			++nCodeAddTime;
			FieldItem item0=null;
			item0=(FieldItem)item.cloneItem();
			item0.setFieldsetid(TempTableName);
			item0.setItemid("CodeADD_"+nCodeAddTime);
			String sRowFld="CodeRow_"+nCodeAddTime;
			switch(DBType)
			{
			case Constant.DB2:
				break;
			case Constant.ORACEL:
				strSQL.append("create table ");
				strSQL.append(sTmpTb);
				strSQL.append(" as select codeitemid,rownum rowid1 ");
				strSQL.append(" from codeitem where codesetid='");
				strSQL.append(item.getCodesetid());
				strSQL.append("'");
				break;
			default:
				strSQL.append("select codeitemid,Identity(int ,1,1) as rowid1 into ");
				strSQL.append(sTmpTb);
				strSQL.append(" from codeitem where codesetid='");
				strSQL.append(item.getCodesetid());
				strSQL.append("'");
				break;			
			}
	
			if(sMax.length()>0)
			{
				if(nstep>0)
					strSQL.append(" and codeitemid<='");
				else
					strSQL.append(" and codeitemid>='");
				strSQL.append(sMax);
				strSQL.append("'");			
			}
			strSQL.append(" order by codeitemid" );
	
			FSTDSQLS.add("drop table "+sTmpTb);
			FSTDSQLS.add(strSQL.toString());
			/**增加临时处理用到的指标*/
			String sConFld=item0.getItemid();
			String strR=sConFld+Sql_switcher.getSqlFunc(DBType).getFieldType('A',item0.getItemlength(), 0);
			strSQL.setLength(0);				
			strSQL.append("alter table ");
			strSQL.append(TempTableName);
			strSQL.append(" drop column ");
			strSQL.append(sConFld);		
			FSTDSQLS.add(strSQL.toString());
	
			strSQL.setLength(0);
			strSQL.append("alter table ");
			strSQL.append(TempTableName);
			strSQL.append(" add ");
			strSQL.append(strR);
			FSTDSQLS.add(strSQL.toString());
	
			strSQL.setLength(0);				
			strSQL.append("alter table ");
			strSQL.append(TempTableName);
			strSQL.append(" drop column ");
			strSQL.append(sRowFld);		
			FSTDSQLS.add(strSQL.toString());	
	
			strR=sRowFld+Sql_switcher.getSqlFunc(DBType).getFieldType('N',10, 0);
			strSQL.setLength(0);
			strSQL.append("alter table ");
			strSQL.append(TempTableName);
			strSQL.append(" add ");
			strSQL.append(strR);
			FSTDSQLS.add(strSQL.toString());
	
			strSQL.setLength(0);				
			strSQL.append("update ");
			strSQL.append(TempTableName);
			strSQL.append(" set ");
			strSQL.append(item0.getItemid());
			strSQL.append("=");
			strSQL.append(item.getItemid());
			FSTDSQLS.add(strSQL.toString());
	
			switch(DBType)
			{
			case Constant.DB2:
				/**暂时先不支持*/
				break;
			case Constant.ORACEL:
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append("(select rowid1 from ");
				strSQL.append(sTmpTb);
				strSQL.append(" where ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(sRowFld);
				strSQL.append("=");			
				strSQL.append(sTmpTb);
				strSQL.append(".");
				strSQL.append("codeitemid)");
				FSTDSQLS.add(strSQL.toString());
	
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");			
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append(sRowFld);
				strSQL.append("+(");
				strSQL.append(nstep);
				strSQL.append(")");
				FSTDSQLS.add(strSQL.toString());
	
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("=");
				strSQL.append("(select codeitemid from ");
				strSQL.append(sTmpTb);
				strSQL.append(" where ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append(sTmpTb);
				strSQL.append(".rowid1)");
				FSTDSQLS.add(strSQL.toString());			
	
				/**处理运算后超过极值的，直接改为极值*/
				strSQL.setLength(0);
				strSQL.append("update ");			
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("='");
				strSQL.append(sMax);
				strSQL.append("'");
				strSQL.append(" where not (");
				strSQL.append(sRowFld);
				strSQL.append(" is null ) and not ");
				strSQL.append(sRowFld);
				strSQL.append(" in (select rowid1 from ");
				strSQL.append(sTmpTb);
				strSQL.append(")");
				FSTDSQLS.add(strSQL.toString());			
				break;
			default:
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append(sTmpTb);
				strSQL.append(".rowid1 from ");
				strSQL.append(sTmpTb);
				strSQL.append(" where ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("=");
				strSQL.append(sTmpTb);
				strSQL.append(".");
				strSQL.append("codeitemid");
				FSTDSQLS.add(strSQL.toString());
	
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");			
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append(sRowFld);
				strSQL.append("+(");
				strSQL.append(nstep);
				strSQL.append(")");
				FSTDSQLS.add(strSQL.toString());
	
				strSQL.setLength(0);
				strSQL.append("update ");
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("=");
				strSQL.append(sTmpTb);
				strSQL.append(".codeitemid from ");
				strSQL.append(sTmpTb);
				strSQL.append(" where ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(sRowFld);
				strSQL.append("=");
				strSQL.append(sTmpTb);
				strSQL.append(".rowid1");
				FSTDSQLS.add(strSQL.toString());
	
				/**处理运算后超过极值的，直接改为极值*/
				strSQL.setLength(0);
				strSQL.append("update ");			
				strSQL.append(TempTableName);
				strSQL.append(" set ");
				strSQL.append(TempTableName);
				strSQL.append(".");
				strSQL.append(item0.getItemid());
				strSQL.append("='");
				strSQL.append(sMax);
				strSQL.append("'");
				strSQL.append(" where not (");
				strSQL.append(sRowFld);
				strSQL.append(" is null ) and not ");
				strSQL.append(sRowFld);
				strSQL.append(" in (select rowid1 from ");
				strSQL.append(sTmpTb);
				strSQL.append(")");
				FSTDSQLS.add(strSQL.toString());
	
				break;			
			}		
	
			return item0.getItemid();
		}
	
		/**
		 * 归属日期
		 * @param retValue
		 * @return
		 * @throws Exception
		 * @throws SQLException
		 */
		private boolean Func_SalaryA00Z0(RetValue retValue)throws  SQLException
		{
			SQL.setLength(0);
			//CurFuncNum=FUNCSALARYA00Z0;
			if (!Get_Token())
				return false;
			if(tok==S_LPARENTHESIS)
			{
				if (!Get_Token())
					return false;
				if(tok!=S_RPARENTHESIS)
				{
					Putback();
					SError(E_LOSSRPARENTHESE);
					return false;				
				}
				if (!Get_Token())
					return false;			
			}
			if(this.ymc==null)
			{
				Date sysdate=new Date();
				this.ymc=new YearMonthCount(DateUtils.getYear(sysdate),DateUtils.getMonth(sysdate),DateUtils.getDay(sysdate),1);			
			}
			String date=this.ymc.getDate();
			SQL.append(Sql_switcher.getSqlFunc(DBType).dateValue(date));	
			retValue.setValue(datePad(date));
			retValue.setValueType(DATEVALUE);
			//CurFuncNum=0;
			return true;
		}
	
		/**
		 * 登录用户名　
		 * @param retValue
		 * @return
		 * @throws Exception
		 * @throws SQLException
		 * 标识＝１，帐号　＝２全称
		 */
		private boolean Func_LoginName(RetValue retValue)throws  Exception
		{
			String sM=null;
			CurFuncNum = FUNCLOGINNAME;
			String strSQL=SQL.toString();
			SQL.setLength(0);    	
			if (!Get_Token())
				return false;
			// 处理左括号
			if (tok != S_LPARENTHESIS) {
				Putback();
				SError(E_LOSSLPARENTHESE);
				return false;
			}
			/**标识*/
			SQL.setLength(0);
			if (!Get_Token())
				return false;	
			if (!level3(retValue))
				return false;
			sM=SQL.toString().trim();
			if(!retValue.isIntType())
			{
				Putback();			
				SError(E_MUSTBEINTEGER);
				return false;			   
			}
			int nM=Integer.parseInt(sM);
			if(!(nM==1||nM==2))
			{
				Putback();			
				SError(E_MUSTBEONETWO);
				return false;				
			}
	
			if(tok!=S_RPARENTHESIS)
			{
				Putback();
				SError(E_LOSSRPARENTHESE);
				return false;
			}	
			if (!Get_Token())
				return false;
	
	
	
			SQL.setLength(0);
			SQL.append(strSQL);
	
			retValue.setValueType(STRVALUE);
			CurFuncNum=0;
			return true;   	
		}    
	
		/**
		 * 按职位统计人数 函数 ?
		 * @param retValue
		 * @return
		 * @throws Exception
		 * @throws SQLException
		 */
		private boolean Func_STATP(RetValue retValue)throws  SQLException
		{
			return true;
		}
	
		private String GetCurMenu(boolean bAddSet, FieldItem Field) {
			String str = "";
			StringBuffer result = new StringBuffer();
			if(Field!=null){
				if(Field.getClassname()!=null&&Field.getClassname().trim().length()>0){
					str = Field.getClassname() + "." + Field.getItemid();
				}else{
					str = Field.getItemid();
				}
			}
	
	
			result.setLength(0);
			result.append(str);
			if (Field.isInt() || Field.isFloat()) {
				switch (DBType) {
				case 1: {
					result.setLength(0);
					if(bDivFlag)
						result.append("NullIF(").append(str).append(",0)");
					else
						//					result.append("ISNULL(").append(str).append(",0)");
						result.append(str);
					break;
				}
				case 2: {
					result.setLength(0);
					if(bDivFlag)
						//					result.append("NullIF(").append(str).append(",0)");
						result.append(str);
					else
						//					result.append("NVL(").append(str).append(",0)");
						result.append(str);
					break;
				}
				case 3: {
					result.setLength(0);
					if(bDivFlag)
						result.append("NullIF(").append(str).append(",0)");
					else
						result.append("COALESCE(").append(str).append(",0)");
					break;
				}
				}
			}
			if (Field.isChar()) {
				switch (DBType) {
				case 1: {
					result.setLength(0);
					//				result.append("ISNULL(").append(str).append(",'')");
					result.append(str);
					break;
				}
				case 2: {
					result.setLength(0);
					//				result.append("NVL(").append(str).append(",'')");
					result.append(str);
					break;
				}
				case 3: {
					result.setLength(0);
					//				result.append("COALESCE(").append(str).append(",'')");
					result.append(str);
					break;
				}
				}
			}
			return result.toString();
		}
	
		private boolean FindMenu(String str, Map<String,FieldItem> Fields) {
			FieldItem Field;
			Iterator<FieldItem> it = Fields.values().iterator();
			while (it.hasNext()) {
				Field = it.next();
				if ((Field.getItemid().equals(str))
						|| (Field.getItemid().equals("现" + str))) {
					return true;
				}
			}
			return false;
		}
	
		public String getSQL() {
			return SQL.toString();
		}
	
		public List<String> getSQLS() {
			return SQLS;
		}
	
		public void init() {
			FError = false;// 错误标志
			UsedSets.clear();// 所用到的子集编号setid
			mapUsedFieldItems.clear();// 所用到的指标代号
			SQLS.clear();//
			SQL.setLength(0);// 待返回生成的查询语句
			setStrError("");// 清空错误信息
			FCTONSQLS.clear();// 代码转名称函数用到的Sql串
			/**代码调整及就近就高函数*/
			FSTDSQLS.clear();	
		}
	
		/**
		 * 公式校验函数
		 * 
		 * @param str
		 *            待校验的公式
		 * @return 公式正确与否 注意：如果校验发现公式错误，需要调用getStrError()获取相关错误信息
		 */
		public boolean Verify(String str) throws Exception{
			try {
				bVerify = true;
				run(str);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	
		/**
		 * 
		 * @param str 			       表达式
		 * @param ymc  			       年月次对象
		 * @param targetField          操作字段
		 * @param targetTable          操作表名称
		 * @param dao  
		 * @param whereText  		   过滤条件
		 * @param con   			   数据库连结
		 * @param targetFieldDataType  字段类型
		 * @param targetFieldLen       字段长度
		 * @param flg                  1.是高级花名册 2.是报表变量 3.条件过滤
		 * @return
		 */
		public String run(String str, YearMonthCount ymc,String whereText,
				String targetFieldDataType, int targetFieldLen,int flg) {
			String s = "";
			this.ymc = ymc;
			this.setWhereText(whereText);
			this.targetFieldDataType = targetFieldDataType;
			try {
				s = run(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;
		}
	
	
	
	
		/**
		 * 通用公式解析函数 注意，该函数不会执行复杂公式(临时表数据更新) 需要调用者通过getSQLS()获取后自行执行更新 简单公式无需关注临时表更新
		 * 
		 * @param str
		 *            待解析的公式
		 * @return 模拟计算公式的结果，该值一般不能直接使用，实际的解析结果sql使用getSQL()方法获取
		 * @throws SQLException
		 */
		public String run(String str) throws Exception {
			RetValue retValue = new RetValue();
			str=str.replaceAll("单位编码", "单位名称");
			str=str.replaceAll("职位编码", "职位名称");		
			setFSource(str); 
			init();// 清空初始化
	
			if ((TempTableName == null) && (ModeFlag == forSearch)) {
				SError(E_LOSSTEMPTABLENAME);
			}
	
			if (!Get_Expr(retValue)) {
				return null;
			}
	
			/**执行代码转换函数*/		
			if(ModeFlag==forNormal)
			{
				run_CTONSQL();		
			}
	
	
			if (bVerify)
				return "";
			if (retValue.isIntType() || retValue.isFloatType()) {
				if (VarType != FLOAT&&VarType != INT) {
					SError(E_FNOTSAMETYPE);
				}
			}
	
			// public static int INT=5;
			// public static int FLOAT=6;
			// public static int STRVALUE=7;
			// public static int LOGIC=8;
			// public static int DATEVALUE=9;
			// public static int NULLVALUE=10;
			// public static int ODDVAR=11;
			// System.out.println(retValue.getValueType());
			if (retValue.IsDateType()) {
				if (VarType != DATEVALUE) {
					SError(E_FNOTSAMETYPE);
				}
			}
			if (retValue.IsStringType()) {
				if (VarType != STRVALUE) {
					SError(E_FNOTSAMETYPE);
				}
			}
			if (retValue.isBooleanType()) {
				if (VarType != LOGIC) {
					SError(E_FNOTSAMETYPE);
				}
			}
	
			ResultString = retValue.ValueToString();
			//System.out.println(UsedSets);
			return result;
		}
	
	
		/**
		 * chenmengqing added. 		forNormal，单表计算
		 * @param str				计算公式
		 * @param strWhere			计算条件
		 * @param tempName			临时表的名称
		 * @return					返回转换后的SQL表达式
		 * @throws Exception
		 */
		public String run(String str,String strWhere,String tempName) throws Exception{
			String s = "";	
			this.setWhereText(strWhere);	
			this.setTempTableName(tempName);
			this.setStdTmpTable(tempName);
			try 
			{
				s = run(str);
	
			} catch (Exception e) {
				e.printStackTrace();
			}
			return s;		
		}
	
	
		/**
		 * 执行代码转名称函数,forNormal状态
		 * @throws Exception
		 */
		private void run_CTONSQL()throws Exception
		{
			int idx=0;
			try
			{
				/**计算条件串*/
				int len=this.whereText.length();
	
				if(FCTONSQLS==null||FCTONSQLS.size()==0)
					return;
				for(int i=0;i<FCTONSQLS.size();i++)
				{
					String tmp=((String)(FCTONSQLS.get(i))).toUpperCase();
					idx=tmp.indexOf("ALTER");
					if(idx==-1)
					{
						if(len>2)//(条件)
							tmp=tmp+" and "+this.whereText;
					}
				}//for i loop end.
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	
	
		/**
		 * 取得公式涉及到的指标信息列表
		 * @param str 公式
		 * @author dengcan
		 * @serialData 2007.06.08
		 * @return
		 */
		public List<FieldItem> getFormulaFieldList(String str) throws Exception 
		{
			List<FieldItem> list=new ArrayList<FieldItem>();
			RetValue retValue = new RetValue();
			setFSource(str);
			init();// 清空初始化
	
			if ((TempTableName == null) && (ModeFlag == forSearch)) {
				SError(E_LOSSTEMPTABLENAME);
			}
	
			if (!Get_Expr(retValue)) {
				return null;
			}
			Map<String,String> setMap=new HashMap<String,String>();
	
			if (ModeFlag == forSearch) {
				Iterator<FieldItem> it = mapUsedFieldItems.values().iterator();
				while (it.hasNext()) {
					FieldItem fieldItem1 = (FieldItem) it.next();
					if (!fieldItem1.getItemid().equalsIgnoreCase("B0110")
							&& !fieldItem1.getItemid().equalsIgnoreCase("E0122")
							&& !fieldItem1.getItemid().equalsIgnoreCase("E01A1")) {
	
						setMap.put(fieldItem1.getFieldsetid().toLowerCase(),"1");
						Field a_field=fieldItem1.cloneField();
						a_field.setLength(50);
						list.add(fieldItem1);
					}
				}
	
			}
			if(setMap.size()>1)
				list=new ArrayList<FieldItem>();
	
			return list;
		}
		/**
		 * 取得公式涉及到的指标信息列表
		 * @param str 公式
		 * @author dengcan
		 * @serialData 2007.06.08
		 * @return
		 */
		public List<FieldItem> getFormulaFieldList1(String str) throws Exception 
		{
			List<FieldItem> list=new ArrayList<FieldItem>();
			RetValue retValue = new RetValue();
			setFSource(str);
			init();// 清空初始化
	
			if ((TempTableName == null) && (ModeFlag == forSearch)) {
				SError(E_LOSSTEMPTABLENAME);
			}
	
			if (!Get_Expr(retValue)) {
				return null;
			}
			if (ModeFlag == forSearch) {
				Iterator<FieldItem> it = mapUsedFieldItems.values().iterator();
				while (it.hasNext()) {
					FieldItem fieldItem1 = (FieldItem) it.next();
					Field a_field=fieldItem1.cloneField();
					a_field.setLength(50);
					list.add(fieldItem1);
				}
	
			}
			return list;
		}
	
	
		public boolean Get_Expr(RetValue retValue) throws Exception,
		SQLException {
	
			if (!Get_Token())
				return false;
			if (!level0(retValue))
				return false;
			Putback();
	
			if (nCurPos != nFSourceLen) {
				SError(E_SYNTAX);
				return false;
			}
			return true;
		}
	
	
	
		private void Putback() {
			nCurPos = nCurPos - token.length();
		}
	
		private boolean level0(RetValue retValue) throws Exception {
			RetValue hold = new RetValue();
			int Op = 0;
			if (!level1(retValue)) {
				return false;
			}
	
			while ((tok == S_AND) || (tok == S_OR)) {
				Op = tok;
				if (tok == S_AND) {
					SQL.append(" AND ");
				} else {
					SQL.append(" OR ");
				}
				if (!Get_Token())
					return false;
				if (!level1(hold))
					return false;
				if (!retValue.isBooleanType() && hold.isBooleanType()) {
					SError(E_MUSTBEBOOL);
					return false;
				}
				if (!Arith(Op, retValue, hold))
					return false;
			}
			return true;
		}
	
	
		/**
		 * 数学操作
		 * @param op
		 * @param retValue
		 * @param hold
		 * @return
		 * @throws Exception
		 */
		public boolean Arith(int op, RetValue retValue, RetValue hold)
				throws Exception {
	
			/*	Object obj1 = retValue.getValue();
			if (obj1 instanceof Integer) {
				Integer i = (Integer)obj1;
				System.out.println("obj1_int" + i.intValue());
	
			} else if (obj1 instanceof Float) {
				Float f = (Float)obj1;
				System.out.println("obj1_float=" + f.floatValue());
			}
	
			Object obj2 = hold.getValue();
			if (obj2 instanceof Integer) {
				Integer i = (Integer)obj2;
				System.out.println("obj2_int" + i.intValue());
	
			} else if (obj2 instanceof Float) {
				Float f = (Float)obj2;
				System.out.println("obj2_float=" + f.floatValue());
			}
			 */
			// System.out.println(retValue.getValue() + ":" +
			// retValue.getValueType()
			// + " " + hold.getValue() + ":" + hold.getValueType());
			if (!(retValue.IsSameType(hold) || retValue.IsNullType() || hold
					.IsNullType())) {
				SError(E_NOTSAMETYPE);
				return false;
			}
			switch (op) {
			case S_ADD: {
				retValue.add(hold);
				break;
			}
			case S_MINUS: {
				retValue.minus(hold);
				break;
			}
			case S_MULTIPLY: {
				retValue.multiply(hold);
				break;
			}
			case S_DIVISION: {
				retValue.division(hold);
				break;
			}
			case S_DIV: {
				if (!retValue.Div(hold)) {
					SError(E_MUSTBEINTEGER);
					return false;
				}
				break;
			}
			case S_MOD: {
				if (!retValue.Mod(hold)) {
					SError(E_MUSTBEINTEGER);
					return false;
				}
				break;
			}
			case S_IN:
			case S_NOTIN:
			case S_LIKE: {
				if (!retValue.IsStringType()) {
					SError(E_MUSTBESTRING);
					return false;
				}
				retValue.setValue(new Boolean(true));
				retValue.setValueType(8);
				break;
			}
			case S_AND: {
				if (!retValue.And(hold)) {
					SError(E_MUSTBEBOOL);
					return false;
				}
				break;
			}
			case S_OR: {
				if (!retValue.Or(hold)) {
					SError(E_MUSTBEBOOL);
					return false;
				}
				break;
			}
			case S_EQUAL: {
				if (!retValue.Equal(hold)) {
					SError(E_NOTSAMETYPE);
					return false;
				}
				break;
			}
			case S_NOTEQUAL: {
				if (!retValue.NotEqual(hold)) {
					SError(E_NOTSAMETYPE);
					return false;
				}
				break;
			}
			case S_GREATER: {
				if (!retValue.Greater(hold)) {
					SError(E_NOTSAMETYPE);
					return false;
				}
				break;
			}
			case S_NOTGREATER: {
				if (!retValue.NotGreater(hold)) {
					SError(E_NOTSAMETYPE);
					return false;
				}
				break;
			}
			case S_SMALLER: {
				if (!retValue.Smaller(hold)) {
					SError(E_NOTSAMETYPE);
					return false;
				}
				break;
			}
			case S_NOTSMALLER: {
				if (!retValue.NotSmaller(hold)) {
					SError(E_NOTSAMETYPE);
					return false;
				}
				break;
			}
	
			default:
				break;
			}
			return true;
		}
	
		private void SQL_ADD(String str) {
			SQL.append(" ");
			SQL.append(str);
		}
	
		private boolean level1(RetValue retValue) throws Exception,
		SQLException {
			int nOp = 0;
			RetValue hold = new RetValue();
			String str;
			if (!level2(retValue))
				return false;
			if ((tok == S_LIKE) || (tok == S_IN)||(tok == S_NOTIN)) {
				nOp = tok;
				SQL_ADD(token);
				if (!Get_Token())
					return false;
				if (!level2(hold))
					return false;
				if (!Arith(nOp, retValue, hold))
					return false;
			}
			if ((tok == S_EQUAL) || (tok == S_GREATER) || (tok == S_SMALLER)) {
				switch (tok) {
				case S_EQUAL: {
					nOp = S_EQUAL;
					if (!Get_Token())
						return false;
					if ((token.equals("NULL")) || (token.equals("空"))) {
						SQL_ADD("IS");
					} else {
						SQL_ADD("=");
					}
					if (!level2(hold))
						return false;
					if (!Arith(nOp, retValue, hold))
						return false;
					break;
				}
				case S_GREATER: {
					if (!Get_Token())
						return false;
					nOp = S_GREATER;
					if (tok == S_EQUAL) {
						SQL_ADD(">=");
						if (!Get_Token())
							return false;
						nOp = S_NOTEQUAL;
					} else {
						SQL_ADD(">");
					}
					if (!level2(hold))
						return false;
					if (!Arith(nOp, retValue, hold)) {
						return false;
					}
					break;
				}
				case S_SMALLER: {
					if (!Get_Token())
						return false;
					nOp = S_SMALLER;
					if ((tok == S_EQUAL) || (tok == S_GREATER)) {
						nOp = S_NOTEQUAL;
						str = "<" + token;
						if (tok == S_EQUAL) {
							nOp = S_NOTGREATER;
						}
						if (!Get_Token())
							return false;
						if ((token.equals("NULL") || (token.equals("空")))) {
							SQL_ADD("IS NOT");
						} else {
							SQL_ADD(str);
						}
					} else {
						SQL_ADD("<");
					}
					if (!level2(hold))
						return false;
					if (!Arith(nOp, retValue, hold)) {
						return false;
					}
					break;
				}
				default:
					break;
				}
			}
			return true;
		}
	
		private boolean level2(RetValue retValue) throws Exception,
		SQLException {
			int nOp = 0;
			RetValue hold = new RetValue();
			if (!level3(retValue))
				return false;
			while ((tok == S_ADD) || (tok == S_MINUS)) {
				nOp = tok;
				if ((retValue.IsStringType()) && ((DBType == 2) || (DBType == 3))) {
					SQL_ADD("||");
				} else {
					SQL_ADD(token);
				}
				if (!Get_Token())
					return false;
				if (!level3(hold))
					return false;
				if (!Arith(nOp, retValue, hold))
					return false;
			}
			return true;
		}
	
		private boolean level3(RetValue retValue) throws Exception {
			int nOp = 0;
			RetValue hold = new RetValue();
			String cTmp = "";
			if (!level4(retValue))
				return false;
	
			if (((DBType==2)||(DBType==3))&& ((tok==S_MOD)|| (tok==S_DIV))|| ((DBType==1) && (tok==S_DIV))) 
			{
				if (tok==S_MOD)
					SError(E_USEFUNCMOD) ;  //  请使用函数:取余数(除数, 被除数);
				else 
					SError(E_USEFUNCINT);
				return false;
			}
			while ((tok == S_MULTIPLY) || (tok == S_DIVISION) || (tok == S_MOD)
					|| (tok == S_DIV)) {
				//  给 / 的除数加零判断  只有效一次.
				bDivFlag = false;
				if (tok!=S_MULTIPLY)
					bDivFlag= true;			
				nOp = tok;
				switch (tok) {
				case S_DIV: {
					if (DBType == 3) {
						cTmp = SQL.toString();
						SQL.setLength(0);
					} else {
						SQL_ADD("\\");//原来是"\\"
					}
					break;
				}
				case S_MOD: {
					switch (DBType) {
					case 1:
					case 2:
						cTmp = SQL.toString();
						SQL.setLength(0);
						break;
					case 3: {
						cTmp = SQL.toString();
						SQL.setLength(0);
						break;
					}
					}
					break;
				}
	
				default:
					SQL_ADD(token);
					break;
				}
	
				if (!Get_Token())
					return false;
				if (!level3(hold))
					return false;
				if (!Arith(nOp, retValue, hold))
					return false;
				if (DBType == 1 && nOp == S_MOD) {
	
					String strTemp = SQL.toString();
					SQL.setLength(0);
					SQL
					.append(("cast(" + cTmp + " as int) % " + "cast("
							+ strTemp + "as int)"));
				}
				if ((DBType == 3) && ((nOp == S_MOD) || (nOp == S_DIV))) {
					if (nOp == S_MOD) {
						String strTemp = SQL.toString();
						SQL.setLength(0);
						SQL.append("MOD(INT(");
						SQL.append(cTmp);
						SQL.append("),INT(");
						SQL.append(strTemp);
						SQL.append("))");
					} else {
						String strTemp = SQL.toString();
						SQL.setLength(0);
						SQL.append("INT(");
						SQL.append(cTmp);
						SQL.append(")/");
						SQL.append(strTemp);
					}
				}
				bDivFlag= false;					
			}
			return true;
		}
	
		private boolean level4(RetValue retValue) throws Exception {
			int nOp = 0;
			RetValue hold = new RetValue();
			if (!level5(retValue))
				return false;
			if (tok == S_POWER) {
				SQL_ADD(token);
				nOp = tok;
				if (!Get_Token())
					return false;
				if (!level4(hold))
					return false;
				if (!Arith(nOp, retValue, hold))
					return false;
			}
			return true;
		}
	
		private boolean level5(RetValue retValue) throws Exception,
		SQLException {
			int nOp = 0;
			if ((tok == S_ADD) || (tok == S_MINUS) || (tok == S_NOT)) {
				if (tok == S_NOT) {
					SQL_ADD("NOT");
				} else {
					SQL_ADD(token);
				}
				nOp = tok;
				if (!Get_Token())
					return false;
			}
			if (!level6(retValue))
				return false;
			if ((nOp == S_MINUS) || (nOp == S_NOT)) {
				if (!Unary(nOp, retValue))
					return false;
			}
			return true;
		}
	
		private boolean level6(RetValue retValue) throws Exception,
		SQLException {
			if ((tok == S_LPARENTHESIS) && (token_type == DELIMITER)) {
				SQL.append("(");
				if (!Get_Token())
					return false;
				if (!level0(retValue))
					return false;
				if (tok == S_COMMA) {
					if (!level7(retValue)) {
						return false;
					}
				}
				if (tok != S_RPARENTHESIS) {
					Putback();
					SError(E_LOSSRPARENTHESE);
					return false;
				}
				SQL.append(")");
				if (!Get_Token()) {
					return false;
				}
			}else if (!Primitive(retValue)) {
				return false;
			}
	
			return true;
		}
		private boolean level7(RetValue retValue) throws Exception,
		SQLException {
			if ((tok == S_COMMA)) {
				SQL.append(",");
				if (!Get_Token())
					return false;
				if (!level0(retValue))
					return false;
				if (tok == S_COMMA) {
					if (!level7(retValue)) {
						return false;
					}
				}
				if (tok != S_RPARENTHESIS) {
					Putback();
					SError(E_LOSSRPARENTHESE);
					return false;
				}
			}else if (!Primitive(retValue)) {
				return false;
			}
	
			return true;
		}
	
		private boolean ProcFunction(RetValue retValue) throws Exception{
			boolean b = false;
			switch (tok) {
			case FUNCTODAY:
				b = Func_TodayPart(FUNCTODAY, retValue);
				break;
	
			case FUNCTOWEEK:
				b = Func_TodayPart(FUNCTOWEEK, retValue);
				break;
			case FUNCTOMONTH:
				b = Func_TodayPart(FUNCTOMONTH, retValue);
				break;
			case FUNCTOQUARTER:
				b = Func_TodayPart(FUNCTOQUARTER, retValue);
				break;
			case FUNCTOYEAR:
				b = Func_TodayPart(FUNCTOYEAR, retValue);
				break;
			case FUNCYEAR:
				b = Func_DatePart(FUNCYEAR, retValue);
				break;
			case FUNCMONTH:
				b = Func_DatePart(FUNCMONTH, retValue);
				break;
			case FUNCDAY:
				b = Func_DatePart(FUNCDAY, retValue);
				break;
			case FUNCQUARTER:
				b = Func_DatePart(FUNCQUARTER, retValue);
				break;
			case FUNCWEEK:
				b = Func_DatePart(FUNCWEEK, retValue);
				break;
			case FUNCWEEKDAY:
				b = Func_DatePart(FUNCWEEKDAY, retValue);
				break;
			case FUNCYEARS:
				b = Func_DateDiff(FUNCYEARS, retValue);
				break;
			case FUNCMONTHS:
				b = Func_DateDiff(FUNCMONTHS, retValue);
				break;
			case FUNCDAYS:
				b = Func_DateDiff(FUNCDAYS, retValue);
				break;
			case FUNCQUARTERS:
				b = Func_DateDiff(FUNCQUARTERS, retValue);
				break;
			case FUNCWEEKS:
				b = Func_DateDiff(FUNCWEEKS, retValue);
				break;
			case FUNCADDYEAR:
				b = Func_DateAdd(FUNCADDYEAR, retValue);
				break;
			case FUNCADDMONTH:
				b = Func_DateAdd(FUNCADDMONTH, retValue);
				break;
			case FUNCADDDAY:
				b = Func_DateAdd(FUNCADDDAY, retValue);
				break;
			case FUNCADDQUARTER:
				b = Func_DateAdd(FUNCADDQUARTER, retValue);
				break;
			case FUNCADDWEEK:
				b = Func_DateAdd(FUNCADDWEEK, retValue);
				break;
			case FUNCAGE:
				// System.out.println(retValue.getValue());
				b = Func_CalcAge(FUNCAGE, retValue);
				break;
			case FUNCAPPAGE:
				b = Func_CalcAge(FUNCAPPAGE, retValue);
				break;
			case FUNCAPPMONTHAGE:
				b = Func_CalcAge(FUNCAPPMONTHAGE, retValue);
				break;
			case FUNCAPPWORKAGE:
				b = Func_CalcAge(FUNCAPPWORKAGE, retValue);
				break;
			case FUNCWORKAGE:
				b = Func_CalcAge(FUNCWORKAGE, retValue);
				break;
			case FUNCMONTHAGE:
				b = Func_CalcAge(FUNCMONTHAGE, retValue);
				break;
			case FUNCAPPDATE:
				b = Func_AppDate(retValue);
				break;
			case FUNCINT:
				b = Func_Math(FUNCINT, retValue);
				break;
			case FUNCROUND:
				b = Func_Math(FUNCROUND, retValue);
				break;
			case FUNCSANQI:
				b = Func_Math(FUNCSANQI, retValue);
				break;
			case FUNCYUAN:
				b = Func_Math(FUNCYUAN, retValue);
				break;
			case FUNCJIAO:
				b = Func_Math(FUNCJIAO, retValue);
				break;
			case FUNCTRIM:
				b = Func_String(FUNCTRIM, retValue);
				break;
			case FUNCLTRIM:
				b = Func_String(FUNCLTRIM, retValue);
				break;
			case FUNCRTRIM:
				b = Func_String(FUNCRTRIM, retValue);
				break;
			case FUNCLEN:
				b = Func_String(FUNCLEN, retValue);
				break;
			case FUNCLEFT:
				b = Func_String(FUNCLEFT, retValue);
				break;
			case FUNCRIGHT:
				b = Func_String(FUNCRIGHT, retValue);
				break;
			case FUNCSUBSTR:
				b = Func_String(FUNCSUBSTR, retValue);
				break;
			case FUNCCTOD:
				b = Func_Convert(FUNCCTOD, retValue);
				break;
			case FUNCCTOI:
				b = Func_Convert(FUNCCTOI, retValue);
				break;
			case FUNCDTOC:
				b = Func_Convert(FUNCDTOC, retValue);
				break;
			case FUNCITOC:
				b = Func_Convert(FUNCITOC, retValue);
				break;
			case FUNCCTON:
				b = Func_CTON(retValue);
				break;
			case FUNCCTON2:
				b = Func_CTON2(retValue);
				break;			
			case FUNCIIF:
	
				b = Func_IIF(retValue);
				break;
			case FUNCCASE:
				b = Func_CASE(retValue);
				break;
			case FUNCMAX:
				b = Func_Maxmin(FUNCMAX, retValue);
				break;
			case FUNCMIN:
				b = Func_Maxmin(FUNCMIN, retValue);
				break;
			case FUNCGET:
				b = Func_GET(retValue);
				break;
			case FUNCSELECT:
				b = Func_SELECT(retValue);
				break;
			case FUNCSTATP:
				b = Func_STATP(retValue);
				break;
			case FUNCMOD:
				b=Func_MOD(retValue);
				break;
			case FUNCISNULL:
				b=Func_ISNNULL(retValue);
				break;
			case FUNCPOWER:
				b=Func_POWER(retValue);
				break;
			case FUNCCNTC:
				b=Func_CNTC(retValue);
				break;
			case FUNCCODENEXT:
				b=Func_CODEADD(retValue,1);
				break;
			case FUNCCODEPRIOR:
				b=Func_CODEADD(retValue,-1);
				break;
			case FUNCCODEADJUST:
				b=Func_CODEADJUST(retValue);
				break;
			case FUNCNEARBYHIGH:
				b=Func_NearByHight(retValue,true);
				break;
			case FUNCNEARBYLOW:
				b=Func_NearByHight(retValue,false);
				break;
			case FUNCSTANDARD:
				b=Func_Standard(retValue);
				break;
			case FUNCSALARYA00Z0:
				b=Func_SalaryA00Z0(retValue);
				break;
			case FUNCLOGINNAME:
				b=Func_LoginName(retValue);
				break;			
			}
			return b;
		}
		private boolean ProcFieldItem(RetValue retValue) {
			// System.out.println(Field.getItemid() + " " + Field.getItemdesc());
			switch (ModeFlag) {
			case forNormal: {
				SQL.append(" ");
				SQL.append(/*Field.getItemSqlExpr(DbPre, false)*/GetCurMenu(false,Field));// GetCurMenu(FALSE,Field);
				break;
			}
			case forSearch: {
				if (!UsedSets.contains(Field.getFieldsetid())) {
					UsedSets.add(Field.getFieldsetid());
				}
				if (!mapUsedFieldItems.containsKey(Field.getItemid())) {
					mapUsedFieldItems.put(Field.getItemid(), Field);
				}
	
				if (CurFuncNum == FUNCSELECT) {
					SQL.append(" ");
					SQL.append(/*Field.getItemSqlExpr(DbPre, true)*/GetCurMenu(true,Field));// GetCurMenu(FALSE,Field); chenmengqing added at 20080401
	
				} else {
					SQL.append(" ");
					SQL.append(/*Field.getItemSqlExpr(DbPre, false)*/GetCurMenu(false,Field));// GetCurMenu(false,Field);
				}
			}
			}
			if (Field.isInt()) {
	
				retValue.setValue(new Integer(10));
				retValue.setValueType(INT);
			} else if (Field.isFloat()) {
				retValue.setValue(new Float(10.0));
				retValue.setValueType(FLOAT);
			} else if (Field.isChar()) {
				retValue.setValue("");
				retValue.setValueType(STRVALUE);
			} else if (Field.isDate()) {
				retValue.setValue("#2006.07.21#");
				retValue.setValueType(DATEVALUE);
			}
	
			return true;
		}
	
		private boolean Primitive(RetValue retValue) throws Exception,
		SQLException {
			// boolean b = true;
			FieldItem field1 = null;		
			try
			{
				switch (token_type) {
				case INT: {
					SQL_ADD(token);
					retValue.setValue(Integer.valueOf(token));
					retValue.setValueType(INT);
					if (!Get_Token())
						return false;
					break;
				}
				case FLOAT: {
					SQL_ADD(token);
					retValue.setValue(Float.valueOf(token));
					retValue.setValueType(FLOAT);
					if (!Get_Token())
						return false;
					break;
				}
				case LOGIC: {
					if (tok == S_TRUE) {
						SQL_ADD("TRUE");
						retValue.setValue(new Boolean(true));
						retValue.setValueType(LOGIC);
					} else {
						SQL_ADD("FALSE");
						retValue.setValue(new Boolean(false));
						retValue.setValueType(LOGIC);
					}
					if (!Get_Token())
						return false;
					break;
				}
				case QUOTE: {
					// SQL_ADD(token);
					SQL_ADD("'" + token + "'");
					retValue.setValue(token);
					retValue.setValueType(STRVALUE);
					if (!Get_Token())
						return false;
					break;
				}
				case NULLVALUE: {
					SQL_ADD("NULL");
					retValue.setValue("NULL");
					retValue.setValueType(NULLVALUE);
					if(CurFuncNum==FUNCSTANDARD)
						this.Field=null;
					if (!Get_Token())
						return false;
					break;
				}
				case DATEVALUE: {
					SQL_ADD(token);
					// // 此刻经过
					// 的处理，token=="'2006.06.28'";
					// Sql_switcher.getSqlFunc(DBType).dateValue(token);
					retValue.setValue(token);
					retValue.setValueType(DATEVALUE);
					if (!Get_Token())
						return false;
					break;
				}
	
				case ODDVAR: {
					SQL.append(" ").append(GetCurMenu(false, Field));
					if (!FindMenu(Field.getItemid(), mapUsedFieldItems)) {
						field1 = (FieldItem) Field.cloneItem();
						mapUsedFieldItems.put(field1.getItemid(), field1);
					}
					if (Field.getItemtype().equals("N")) {
						retValue.setValue(new Integer(1));
						retValue.setValueType(INT);
					} else if (Field.getItemtype().equals("D")) {
						retValue.setValue("'2002.5.14'");
						retValue.setValueType(DATEVALUE);
					} else if (Field.getItemtype().equals("A")) {
						retValue.setValue("");
						retValue.setValueType(STRVALUE);
					}
					if (!Get_Token())
						return false;
					break;
				}
	
				case FIELDITEM: {
					retValue.setValue(token);
					ProcFieldItem(retValue);
					if (!Get_Token()) {
						return false;
					}
					break;
				}
	
				case FUNC: {
	
					return ProcFunction(retValue);
				}
				default: {
					Putback();
					SError(E_SYNTAX);
					return false;
				}
				}
			}catch(Exception e)
			{
				SError(E_SYNTAX);
			}
	
			return true;
		}
	
		private boolean Unary(int nOp, RetValue retValue){
			switch (nOp) {
			case S_MINUS: {
				retValue.MinusValue();
				break;
			}
			case S_NOT: {
				if (!retValue.isBooleanType()) {
					SError(E_MUSTBEBOOL);
					return false;
				}
				retValue.NotValue();
			}
			}
			return true;
		}
	
		private boolean Get_Token(){
			//System.out.println(this.FSource + " " + token);
			// System.out.println("--------->" + FSource.charAt(nCurPos));
			String str;
			tok = 0;
			token = "";
			token_type = 0;
	
			// 判断当前指针是否以指向最后一个字符的后面，即公式已经解析完。
			if (nCurPos == FSource.length()) {
				tok = S_FINISHED; //结束
				token_type = DELIMITER; //分隔符类型
				return true;
			}
	
			// 处理空格、回车、换行等不参与分析的字符。
			while (nCurPos < FSource.length()
					&& (FSource.charAt(nCurPos) == ' '
					|| FSource.charAt(nCurPos) == '\r'
					|| FSource.charAt(nCurPos) == '\t' || FSource
					.charAt(nCurPos) == '\n')) {
				nCurPos++;
			}
	
			// 处理注释( //开始 换行结束,需要考虑超过字串长度 新增 nCurPos+2<=FSource.length() &&)
			if (nCurPos+2<=FSource.length() && FSource.charAt(nCurPos) == '/'&& FSource.charAt(nCurPos + 1) == '/') 
			{
				nCurPos += 2;
				while (!(nCurPos == FSource.length() || (FSource.charAt(nCurPos) == '\n'))) {
					nCurPos++;
				}
	
				if (nCurPos != nFSourceLen) { //不等于总长度 
					nCurPos++;
				}
			}
	
			//  判断当前指针是否以指向最后一个字符的后面，即公式已经解析完。
			if (nCurPos == nFSourceLen) {
				tok = S_FINISHED; //结束
				token_type = DELIMITER; //分隔符
				return true;
			}
	
			// 处理分隔符
			int nPos = "+-*/\\%^;:,=<>()；：，".indexOf(FSource.charAt(nCurPos));
			if (nPos >= 0) {
				switch (nPos) {
				case 0: {
					tok = S_ADD;//加
					break;
				}
				case 1: {
					tok = S_MINUS; //减
					break;
				}
				case 2: {
					tok = S_MULTIPLY; //乘
					break;
				}
				case 3: {
					tok = S_DIVISION; //除
					break;
				}
				case 4: {
					tok = S_DIV; //整除
					break;
				}
				case 5: {
					tok = S_MOD; //求模
					break;
				}
				case 6: {
					tok = S_POWER; //幂
					break;
				}
				case 7: {//半角 分号
					tok = S_SEMICOLON; //分号
					break;
				}
				case 8: {//半角 冒号
					tok = S_COLON; //冒号
					break;
				}
				case 9: { //半角 逗号
					tok = S_COMMA; //逗号
					break;
				}
				case 10: {
					tok = S_EQUAL; //等号
					break;
				}
				case 11: {
					tok = S_SMALLER; //小于号
					break;
				}
				case 12:
					tok = S_GREATER; //大于号
					break;
				case 13: {
					tok = S_LPARENTHESIS;//左括号
					break;
				}
				case 14: {
					tok = S_RPARENTHESIS;//右括号
					break;
				}
				case 15: {//全角 分号
					tok = S_SEMICOLON; //分号
					break;
				}
				case 16: {//全角 冒号
					tok = S_COLON; //冒号
					break;
				}
				case 17: {//全角 逗号
					tok = S_COMMA; //逗号
					break;
				}
	
				default:
					break;
				}
	
				token = "" + FSource.charAt(nCurPos);
				token_type = DELIMITER; //分隔符
				nCurPos++;
	
				return true;
	
			}
	
			// 左引号
			if (FSource.charAt(nCurPos) == '"') {
				token = "";
				nCurPos++;
				while (!((nCurPos == nFSourceLen) || (FSource.charAt(nCurPos) == '"'))) {
					token = token + FSource.charAt(nCurPos);
					nCurPos++;
				}
				int nTemp = nCurPos == nFSourceLen ? 2 : 1; //是否到了结尾
				nCurPos++;
				if (FSource.charAt(nCurPos - nTemp) != '"') {
					Putback();
					SError(E_LOSSQUOTE);
					return false;
				}
				token_type = QUOTE; //引用类型
				return true;
			}
	
			/*******  sunx有关单引号************/
			if (FSource.charAt(nCurPos) == '\'') {
				token = "";
				nCurPos++;
				while (!((nCurPos == nFSourceLen) || (FSource.charAt(nCurPos) == '\''))) {
					token = token + FSource.charAt(nCurPos);
					nCurPos++;
				}
				int nTemp = nCurPos == nFSourceLen ? 2 : 1;
				nCurPos++;
				if (FSource.charAt(nCurPos - nTemp) != '\'') {
					Putback();
					SError(E_LOSSQUOTE);
					return false;
				}
				token_type = QUOTE;
				return true;
			}
	
	
			/*******  sunx有关单引号end************/
			// 处理方括号，指标
			if (FSource.charAt(nCurPos) == '[') {
				token = "";
				nCurPos++;
				while (!((nCurPos == nFSourceLen) || (FSource.charAt(nCurPos) == ']'))) {
					token = token + FSource.charAt(nCurPos);
					nCurPos++;
				}
				int nTemp = nCurPos == nFSourceLen ? 2 : 1;
				nCurPos++;
				if (FSource.charAt(nCurPos - nTemp) != ']') {
					SError(E_LOSSBRACK1);
					return false;
				}
	
				if (IsFieldItem(token)) {
					token_type = FIELDITEM;
					return true;
				} else {
					SError(E_NOTFINDFIELD);
					return false;
				}
			}
	
			// 处理花括号
			if (FSource.charAt(nCurPos) == '{') {
				token = "";
				nCurPos++;
				while (!((nCurPos == nFSourceLen) || (FSource.charAt(nCurPos) == '}'))) {
					token = token + FSource.charAt(nCurPos);
					nCurPos++;
				}
				int nTemp = nCurPos == nFSourceLen ? 2 : 1;
				nCurPos++;
				if (FSource.charAt(nCurPos - nTemp) != '}') {
					SError(E_LOSSBRACK2);
					return false;
				}
				if (IsOddVar(token)) {
					token_type = ODDVAR;
					return true;
				} else {
					SError(E_NOTFINDODDVAR);
					return false;
				}
			}
	
			// 处理数字
			if (IsDigit(FSource.charAt(nCurPos))) {
				token = "" + FSource.charAt(nCurPos);
				nCurPos++;
				while (nCurPos != nFSourceLen && IsDigit(FSource.charAt(nCurPos))) {
					token = token + FSource.charAt(nCurPos);
					nCurPos++;
				}
				token_type = INT;
				if (nCurPos != nFSourceLen && FSource.charAt(nCurPos) == '.') {
	
					token = token + FSource.charAt(nCurPos);
					nCurPos++;
	
					/**zhang feng jin 修改*/
					if(nCurPos == nFSourceLen){
						SError(E_MUSTBENUMBER);
						return false;
					}
					/**zhang feng jin 修改*/
	
					if (!IsDigit(FSource.charAt(nCurPos))) {
						SError(E_MUSTBENUMBER);
						return false;
					}
	
					while (nCurPos != nFSourceLen
							&& IsDigit(FSource.charAt(nCurPos))) {
	
						token = token + FSource.charAt(nCurPos);
						nCurPos++;
					}
					token_type = FLOAT;
				}
				return true;
			}
	
			// 处理日期常量
			if (FSource.charAt(nCurPos) == '#') {
				token = "";
				nCurPos++;
				while (!((nCurPos == nFSourceLen) || (FSource.charAt(nCurPos) == '#'))) {
					token = token + FSource.charAt(nCurPos);
					nCurPos++;
				}
				int nTemp = nCurPos == nFSourceLen ? 2 : 1;
				nCurPos++;
				if (FSource.charAt(nCurPos - nTemp) != '#') {
					SError(E_MUSTBEDATE);
					return false;
				}
	
				token = token.replaceAll("/", ".");
				token = token.replaceAll("-", ".");
				token = token.replaceAll(",", ".");
				int count = 0;
				for (int i = 0; i < token.length(); i++) {
					if (token.charAt(i) == '.')
						count++;
				}
				if (count == 1) {
					token = parseMonthDay(token);
				}
				/**不是合法的日期串*/
				if(!isDateString(token))
				{
					SError(E_MUSTBEDATE);
					return false;				
				}
				token =datePad(token);
				token = Sql_switcher.getSqlFunc(DBType).dateValue(token);
				token_type = DATEVALUE;
				return true;
			}
	
			// 处理代码转名称
			if (FSource.charAt(nCurPos) == '~') {
				token_type = STRVALUE;
				token = "" + FSource.charAt(nCurPos);
				nCurPos++;
				return Lookup(token);
			}
	
			// 判断字符含义
			if (IsAlpha(FSource.charAt(nCurPos))
					|| isChiness(FSource.charAt(nCurPos))) {
	
				token = "" + FSource.charAt(nCurPos);
				nCurPos++;
				while ((nCurPos != nFSourceLen)
						&& !IsDelimiter(FSource.charAt(nCurPos))) {
					token = token + FSource.charAt(nCurPos);
					nCurPos++;
				}
				/**
				 * 新增
				 * */
				if (token.equals("不包含")) {
					token = "NOT IN";
				}
				if (token.equals("包含")) {
					token = "IN";
				}
				if (token.equals("适配")) {
					token = "LIKE";
				}
				if (token.equals("现") || (token.equals("拟"))) {
					str = token;
					if ((FSource.charAt(nCurPos) != '(')
							&& (FSource.charAt(nCurPos) != '[')) {
						SError(E_LOSSLPARENTHESE);
						return false;
					}
					nCurPos++;
					token = "";
					while (!((FSource.charAt(nCurPos) == ')')
							|| (FSource.charAt(nCurPos) == ']') || (nCurPos == nFSourceLen))) {
						token = token + FSource.charAt(nCurPos);
						nCurPos++;
					}
					token = str + token;
					if ((FSource.charAt(nCurPos) != ')')
							|| (FSource.charAt(nCurPos) != ']')) {
						SError(E_LOSSRPARENTHESE);
						return false;
					}
					nCurPos++;
					if (IsFieldItem(token)) {
						token_type = FIELDITEM;
						return true;
					} else {
						SError(E_NOTFINDFIELD);
						return false;
					}
				}
				token_type = STRVALUE;
	
//				if (token_type == STRVALUE) {
//					if(Field!=null&&Field.isCode()){
//						if(token.indexOf("【")==-1&&!IsKey(token)){
//							String codevalue = ruleTypeservice.nameToCode(Field.getClassname(),Field.getItemid(),Field.getTypeflag(),Field.getCodesetid(),token);
//							if(codevalue!=null&&codevalue.trim().length()>0){
//								token = codevalue;
//								token_type = QUOTE;
//								return true;
//							}else{
//								SError(E_CODEVALUE);
//								return false;
//							}
//						}
//					}
//					if (!Lookup(token)) {
//						return false;
//					}
//				}
				return true;
	
			}
	
			return false;
		}
		/**
		 * 把日期串补成10 yyyy.mm.dd
		 * @param datestr
		 * @return
		 */
		private String datePad(String datestr)
		{
			//datestr="#"+datestr+"#"; //for 定义公式为#2008-10-20#出错　at 2008-09-03
			if(datestr.length()==10)
				return datestr;
			datestr=datestr.replaceAll("-", ".");
			String[] tmp=StringUtils.split(datestr, ".");
			StringBuffer buf=new StringBuffer();
			buf.append(tmp[0]);
			buf.append(".");
			if(tmp[1].length()==1)
			{
				buf.append("0");
				buf.append(tmp[1]);
			}
			else
				buf.append(tmp[1]);
			buf.append(".");
			if(tmp[2].length()==1)
			{
				buf.append("0");
				buf.append(tmp[2]);
			}
			else
				buf.append(tmp[2]);
			return buf.toString();
		}
		/**
		 * 当用户只输入月和日时，年默认为今年。 例如输入#1.1#返回 year-01-01
		 * 
		 * @param monthDay
		 * @return
		 */
		private String parseMonthDay(String monthDay) {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
			String year = dateFormat.format(now).toString();
			monthDay = monthDay.replaceAll("#", "");
			monthDay = monthDay.trim();
			String[] monthDayArray = monthDay.split("\\.");
			for (int i = 0; i < monthDayArray.length; i++) {
				while (monthDayArray[i].length() < 2) {
					monthDayArray[i] = "0" + monthDayArray[i];
				}
			}
			String datestr="";
			datestr = year +"."+ monthDayArray[0]+"." + monthDayArray[1];
			//		if(monthDayArray.length>0)   cmq changed at 20080628
			//			datestr = year +"."+ monthDayArray[0]+"." + monthDayArray[1];
			//		else if(monthDayArray.length>1)
			//			datestr = year +"."+ monthDayArray[0]+"." + monthDayArray[1];
			return datestr;
		}
	
		private boolean isChiness(char c) {
			return (int) c > 127;
		}
	
		/**
		 * 是否为合法的日期串
		 * @param date
		 * @return
		 */
		private boolean isDateString(String strdate)
		{
			boolean bflag=true;
			try
			{
				strdate=strdate.replaceAll("\\.", "-");  
				Date date=DateStyle.parseDate(strdate);
				if(date==null)
					bflag=false;
			}
			catch(Exception ex)
			{
				bflag=false;
			}
			return bflag;
		}
		/**
		 * 判断字符串代表什么。可能是函数、关键字、指标等。
		 * 
		 * @param str
		 * @return
		 * @throws Exception
		 */
		private boolean Lookup(String str){ // 关键字指标函数
			// 临时变量
			tok = 0;
			//System.out.println("--->" + str);
			token_type = 0;
			if (IsKey(str) || IsFunction(str) || IsFieldItem(str) || IsOddVar(str)) {
				//System.out.println("true-->" + this.token_type);
				return true;
			} else {
				// System.out.println("false-->" + this.token_type);
				SError(E_UNKNOWNSTR);
				return false;
			}
		}
	
		/**
		 * 判断是否是字符
		 */
		private boolean IsAlpha(char c) {
			return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
					.indexOf(c) >= 0;
		}
	
		/**
		 * 判断是否是关键字
		 */
		private boolean IsKey(String str) {
			if (str.equalsIgnoreCase("NULL") || str.equals("空")) {
				tok = S_NULL;
				token_type = NULLVALUE;
				return true;
			}
	
			if (str.equalsIgnoreCase("AND") || (str.equalsIgnoreCase("且")))
				tok = S_AND;
			if (str.equalsIgnoreCase("OR") || (str.equalsIgnoreCase("或")))
				tok = S_OR;
			if (str.equalsIgnoreCase("NOT") || (str.equalsIgnoreCase("非")))
				tok = S_NOT;
			if (str.equalsIgnoreCase("DIV"))
				tok = S_DIV;
			if (str.equalsIgnoreCase("MOD"))
				tok = S_MOD;
			if (str.equalsIgnoreCase("LIKE")||str.equalsIgnoreCase("适配"))
				tok = S_LIKE;
			if (str.equalsIgnoreCase("IN")||str.equalsIgnoreCase("包含"))
				tok = S_IN;
			if (str.equalsIgnoreCase("NOT IN")||str.equalsIgnoreCase("不包含"))
				tok = S_NOTIN;
			token_type = DELIMITER;
			if (tok != 0)
				return true;
	
			token_type = LOGIC;
			if (str.equalsIgnoreCase("TRUE") || (str.equalsIgnoreCase("真")))
				tok = S_TRUE;
			if (str.equalsIgnoreCase("FALSE") || (str.equalsIgnoreCase("假")))
				tok = S_FALSE;
			if (tok != 0)
				return true;
	
			if (str.equalsIgnoreCase("THEN") || (str.equalsIgnoreCase("那么"))) {
	
				tok = S_THEN;
				token_type = DELIMITER;
				return true;
			}
			if (str.equalsIgnoreCase("ELSE") || (str.equalsIgnoreCase("否则"))) {
				tok = S_ELSE;
				token_type = DELIMITER;
				return true;
			}
	
			if (str.equalsIgnoreCase("END") || (str.equalsIgnoreCase("结束"))) {
				tok = S_END;
				token_type = DELIMITER;
				return true;
			}
	
			if (str.equalsIgnoreCase("FIRST") || (str.equalsIgnoreCase("的最初第一条记录"))) {
				tok = S_FIRST;
				token_type = DELIMITER;
				return true;
			}
			if (str.equalsIgnoreCase("LAST") || (str.equalsIgnoreCase("的最近第一条记录"))) {
				tok = S_LAST;
				token_type = DELIMITER;
				return true;
			}
			if (str.equalsIgnoreCase("MAX") || (str.equalsIgnoreCase("的最大值"))) {
				tok = S_MAX;
				token_type = DELIMITER;
				return true;
			}
			if (str.equalsIgnoreCase("MIN") || (str.equalsIgnoreCase("的最小值"))) {
				tok = S_MIN;
				token_type = DELIMITER;
				return true;
			}
			if (str.equalsIgnoreCase("SUM") || (str.equalsIgnoreCase("的总和"))) {
				tok = S_SUM;
				token_type = DELIMITER;
				return true;
			}
			if (str.equalsIgnoreCase("AVG") || (str.equalsIgnoreCase("的平均值"))) {
				tok = S_AVG;
				token_type = DELIMITER;
				return true;
			}
			if (str.equalsIgnoreCase("COUNT") || (str.equalsIgnoreCase("的个数"))) {
				tok = S_COUNT;
				token_type = DELIMITER;
				return true;
			}
			if (str.equalsIgnoreCase("INCREASE") || (str.equalsIgnoreCase("最初第"))) {
				tok = S_INCREASE;
				token_type = DELIMITER;
				return true;
			}
			if (str.equalsIgnoreCase("DECREASE") || (str.equalsIgnoreCase("最近第"))) {
				tok = S_DECREASE;
				token_type = DELIMITER;
				return true;
			}
			if (str.equals("条记录")) {
				tok = S_GETEND;
				token_type = DELIMITER;
				return true;
			}
	
			if (str.equals("满足")) {
				tok = S_SATISFY;
				token_type = DELIMITER;
				return true;
			}
			return false;
		}
	
		private boolean IsFunction(String str) {
			if (str.equalsIgnoreCase("FORMULA") || str.equalsIgnoreCase("公式")
					|| str.equalsIgnoreCase("执行公式"))
				tok = FUNCFORMULA;
			if (str.equalsIgnoreCase("YEAR") || (str.equalsIgnoreCase("年")))
				tok = FUNCYEAR;
			else if (str.equalsIgnoreCase("MONTH") || (str.equalsIgnoreCase("月")))
				tok = FUNCMONTH;
			else if (str.equalsIgnoreCase("DAY") || (str.equalsIgnoreCase("日")))
				tok = FUNCDAY;
			else if (str.equalsIgnoreCase("TODAY") || (str.equalsIgnoreCase("今天")))
				tok = FUNCTODAY;
			else if (str.equalsIgnoreCase("TOWEEK") || (str.equalsIgnoreCase("本周")))
				tok = FUNCTOWEEK;
			else if (str.equalsIgnoreCase("TOMONTH")
					|| (str.equalsIgnoreCase("本月")))
				tok = FUNCTOMONTH;
			else if (str.equalsIgnoreCase("TOQUARTER")
					|| (str.equalsIgnoreCase("本季度")))
				tok = FUNCTOQUARTER;
			else if (str.equalsIgnoreCase("TOYEAR") || (str.equalsIgnoreCase("今年")))
				tok = FUNCTOYEAR;
			else if (str.equalsIgnoreCase("AGE") || (str.equalsIgnoreCase("年龄")))
				tok = FUNCAGE;
			else if (str.equalsIgnoreCase("APPAGE")
					|| (str.equalsIgnoreCase("到截止日期年龄"))
					|| (str.equalsIgnoreCase("年龄1")))
				tok = FUNCAPPAGE;
			else if (str.equalsIgnoreCase("MONTHAGE")
					|| (str.equalsIgnoreCase("到月年龄")))
				tok = FUNCMONTHAGE;
			else if (str.equalsIgnoreCase("APPMONTHAGE")
					|| (str.equalsIgnoreCase("到月年龄1")))
				tok = FUNCAPPMONTHAGE;
			else if (str.equalsIgnoreCase("WORKAGE")
					|| (str.equalsIgnoreCase("工龄")))
				tok = FUNCWORKAGE;
			else if (str.equalsIgnoreCase("APPWORKAGE")
					|| (str.equalsIgnoreCase("工龄1")))
				tok = FUNCAPPWORKAGE;
			else if (str.equalsIgnoreCase("YEARS") || (str.equalsIgnoreCase("年数")))
				tok = FUNCYEARS;
			else if (str.equalsIgnoreCase("MONTHS") || (str.equalsIgnoreCase("月数")))
				tok = FUNCMONTHS;
			else if (str.equalsIgnoreCase("DAYS") || (str.equalsIgnoreCase("天数")))
				tok = FUNCDAYS;
			else if (str.equalsIgnoreCase("QUARTERS")
					|| (str.equalsIgnoreCase("季度数")))
				tok = FUNCQUARTERS;
			else if (str.equalsIgnoreCase("WEEKS") || (str.equalsIgnoreCase("周数")))
				tok = FUNCWEEKS;
			else if (str.equalsIgnoreCase("ADDYEAR")
					|| (str.equalsIgnoreCase("增加年数")))
				tok = FUNCADDYEAR;
			else if (str.equalsIgnoreCase("ADDQUARTER")
					|| (str.equalsIgnoreCase("增加季度数")))
				tok = FUNCADDQUARTER;
			else if (str.equalsIgnoreCase("ADDMONTH")
					|| (str.equalsIgnoreCase("增加月数")))
				tok = FUNCADDMONTH;
			else if (str.equalsIgnoreCase("ADDWEEK")
					|| (str.equalsIgnoreCase("增加周数")))
				tok = FUNCADDWEEK;
			else if (str.equalsIgnoreCase("ADDDAY")
					|| (str.equalsIgnoreCase("增加天数")))
				tok = FUNCADDDAY;
			else if (str.equalsIgnoreCase("QUARTER")
					|| (str.equalsIgnoreCase("季度")))
				tok = FUNCQUARTER;
			else if (str.equalsIgnoreCase("WEEK") || (str.equalsIgnoreCase("周")))
				tok = FUNCWEEK;
			else if (str.equalsIgnoreCase("WEEKDAY")
					|| (str.equalsIgnoreCase("星期")))
				tok = FUNCWEEKDAY;
			else if (str.equalsIgnoreCase("APPDATE")
					|| (str.equalsIgnoreCase("截止日期")))
				tok = FUNCAPPDATE;
			else if (str.equalsIgnoreCase("INT") || (str.equalsIgnoreCase("取整")))
				tok = FUNCINT;
			else if (str.equalsIgnoreCase("ROUND")
					|| (str.equalsIgnoreCase("四舍五入")))
				tok = FUNCROUND;
			else if (str.equalsIgnoreCase("SANQI")
					|| (str.equalsIgnoreCase("三舍七入")))
				tok = FUNCSANQI;
			else if (str.equalsIgnoreCase("YUAN") || (str.equalsIgnoreCase("逢分进元")))
				tok = FUNCYUAN;
			else if (str.equalsIgnoreCase("JIAO") || (str.equalsIgnoreCase("逢分进角")))
				tok = FUNCJIAO;
			else if (str.equalsIgnoreCase("TRIM") || (str.equalsIgnoreCase("去空格")))
				tok = FUNCTRIM;
			else if (str.equalsIgnoreCase("LTRIM")
					|| (str.equalsIgnoreCase("去左空格")))
				tok = FUNCLTRIM;
			else if (str.equalsIgnoreCase("RTRIM")
					|| (str.equalsIgnoreCase("去右空格")))
				tok = FUNCRTRIM;
			else if (str.equalsIgnoreCase("LEN") || (str.equalsIgnoreCase("串长")))
				tok = FUNCLEN;
			else if (str.equalsIgnoreCase("LEFT") || (str.equalsIgnoreCase("左串")))
				tok = FUNCLEFT;
			else if (str.equalsIgnoreCase("RIGHT") || (str.equalsIgnoreCase("右串")))
				tok = FUNCRIGHT;
			else if (str.equalsIgnoreCase("SUBSTR") || (str.equalsIgnoreCase("子串")))
				tok = FUNCSUBSTR;
			else if (str.equalsIgnoreCase("CTOD")
					|| (str.equalsIgnoreCase("字符转日期")))
				tok = FUNCCTOD;
			else if (str.equalsIgnoreCase("CTOI")
					|| (str.equalsIgnoreCase("字符转数值")))
				tok = FUNCCTOI;
			else if (str.equalsIgnoreCase("DTOC")
					|| (str.equalsIgnoreCase("日期转字符")))
				tok = FUNCDTOC;
			else if (str.equalsIgnoreCase("ITOC")
					|| (str.equalsIgnoreCase("数值转字符")))
				tok = FUNCITOC;
			else if (str.equalsIgnoreCase("NumConversion")
					|| (str.equalsIgnoreCase("数字转汉字")))
				tok = FUNCCNTC;		
			else if (str.equalsIgnoreCase("CTON")
					|| (str.equalsIgnoreCase("代码转名称"))
					|| (str.equalsIgnoreCase("~")))
				tok = FUNCCTON;
			else if (str.equalsIgnoreCase("CTON2")
					|| (str.equalsIgnoreCase("代码转名称2")))
				tok = FUNCCTON2;		
			else if (str.equalsIgnoreCase("IIF") || (str.equalsIgnoreCase("如果")))
				tok = FUNCIIF;
			else if (str.equalsIgnoreCase("CASE") || (str.equalsIgnoreCase("分情况")))
				tok = FUNCCASE;
			else if (str.equalsIgnoreCase("GETMAX")
					|| (str.equalsIgnoreCase("较大值")))
				tok = FUNCMAX;
			else if (str.equalsIgnoreCase("GETMIN")
					|| (str.equalsIgnoreCase("较小值")))
				tok = FUNCMIN;
			else if (str.equalsIgnoreCase("GET") || (str.equalsIgnoreCase("取")))
				tok = FUNCGET;
			else if (str.equalsIgnoreCase("SELECT") || (str.equalsIgnoreCase("统计")))
				tok = FUNCSELECT;
			else if (str.equalsIgnoreCase("STATP")
					|| (str.equalsIgnoreCase("按职位统计人数")))
				tok = FUNCSTATP;
			else if (str.equalsIgnoreCase("EXECUTESTANDARD")
					|| (str.equalsIgnoreCase("执行标准")))
				tok = FUNCSTANDARD;
			else if (str.equalsIgnoreCase("NEARBYHIGH")
					|| (str.equalsIgnoreCase("就近就高")))
				tok = FUNCNEARBYHIGH;	
			else if (str.equalsIgnoreCase("NEARBYLOW")
					|| (str.equalsIgnoreCase("就近就低")))
				tok = FUNCNEARBYLOW;
			else if (str.equalsIgnoreCase("CodeUpDown")
					|| (str.equalsIgnoreCase("代码变档")))
				tok = FUNCCODEADD;			
			else if (str.equalsIgnoreCase("CodeUpDownN")
					|| (str.equalsIgnoreCase("后一个代码")))
				tok = FUNCCODENEXT;			
			else if (str.equalsIgnoreCase("CodeUpDownP")
					|| (str.equalsIgnoreCase("前一个代码")))
				tok = FUNCCODEPRIOR;			
			else if (str.equalsIgnoreCase("CodeAdjuest")
					|| (str.equalsIgnoreCase("代码调整")))
				tok = FUNCCODEADJUST;	
			else if (str.equalsIgnoreCase("FuncHisPriorMenu")
					|| (str.equalsIgnoreCase("上一个历史记录指标值")))
				tok = FUNCHISPRIORMENU;	
			else if (str.equalsIgnoreCase("FuncHisFirstMenu")
					|| (str.equalsIgnoreCase("历史记录最初指标值")))
				tok = FUNCHISFIRSTMENU;			
			else if(str.equalsIgnoreCase("MOD")||str.equalsIgnoreCase("取余数"))
				tok = FUNCMOD;
			else if(str.equalsIgnoreCase("ISNULL")||str.equalsIgnoreCase("为空"))
				tok = FUNCISNULL;			
			else if(str.equalsIgnoreCase("POWER")||str.equalsIgnoreCase("幂"))
				tok = FUNCPOWER;	
			else if(str.equalsIgnoreCase("SalaryA00Z0")||str.equalsIgnoreCase("归属日期"))
				tok= FUNCSALARYA00Z0;
			else if(str.equalsIgnoreCase("LoginName")||str.equalsIgnoreCase("登录用户名"))
				tok= FUNCLOGINNAME;		
			if (tok != 0) {
				token_type = FUNC;
				return true;
			} else {
				return false;
			}
		}
	
		private boolean IsFieldItem(String strToken) {
			List<FieldItem> FFields = getFieldItems();
			for (int i = 0; i < FFields.size(); i++) {
				Field = (FieldItem) FFields.get(i);
				// System.out.println(Field.getItemid() + ":" +
				// Field.getItemdesc());
				if (Field.getVarible() == 0) {// 是指标
	
					if (Field.getItemid().equalsIgnoreCase(strToken)) {
						token_type = FIELDITEM;
						return true;
					} else if (Field.getItemdesc().equalsIgnoreCase(strToken)) {
						token_type = FIELDITEM;
						return true;
					} else if ((('现'+Field.getItemdesc() )
							.equalsIgnoreCase(strToken))
							&& Field.isChangeBefore()) {
						token_type = FIELDITEM;
						return true;
	
					} else if ((('拟'+Field.getItemdesc())
							.equalsIgnoreCase(strToken))
							&& Field.isChangeAfter()) {
						token_type = FIELDITEM;
						return true;
					}
	
				}
			}
			/**如果未找到指标，则为空,chenmengqing added 20071015*/
			Field=null;
			return false;
		}
	
		private boolean IsOddVar(String strToken) {
			List<FieldItem> FFields = getFieldItems();
			for (int i = 0; i < FFields.size(); i++) {
				Field = (FieldItem) FFields.get(i);
				if (Field.getVarible() == 1) { // 是变量
					if (Field.getItemid().equalsIgnoreCase(strToken)) {
						token_type = ODDVAR;
						return true;
					} else if (Field.getItemdesc().equalsIgnoreCase(strToken)) {
						token_type = ODDVAR;
						return true;
					}
				}
			}
			/**如果未找到指标，则为空,chenmengqing added 20071015*/
			Field=null;		
			return false;
		}
	
		private boolean IsDelimiter(char c) {
			boolean b = "+-*/%^;:,=<>()；，： \n\t\r".indexOf(c) >= 0;
			return b;
		}
	
		private boolean IsDigit(char c) {
	
			return "0123456789".indexOf("" + c) >= 0;
		}
	
		public void setFSource(String strCSource) {
			/**对数值型字段,如果表达式为空的话,自动给它赋0,chenmengqing addad*/
			strCSource=strCSource.replaceAll("单位编码", "单位名称");
			strCSource=strCSource.replaceAll("职位编码", "职位名称");			
			if(VarType==FLOAT||VarType==INT)
			{
				if(strCSource.length()==0)
					strCSource="0";
			}
			//cmq end. at 20070910	
			FSource = strCSource;
			nFSourceLen = FSource.length();
			nCurPos = 0;
		}
	
		public String getResult() {
			return result;
		}
	
		/**
		 * 获得计算公式错误描述信息
		 * @return
		 */
		public String getStrError() {
			return strError;
		}
	
		public boolean getFError() {
			return FError;
		}
	
		private void setStrError(String strError) {
			this.strError = strError;
			// System.out.println(strError);
		}
	
		private void SError(int nErrorNo){
			String strMsg = null;
			FError = true;		
			switch (nErrorNo) {
			case E_LOSSQUOTE: {
				strMsg = "此处缺少引号";
				break;
			}
			case E_LOSSLPARENTHESE: {
				strMsg = "此处缺少左括号";
				break;
			}
			case E_LOSSRPARENTHESE: {
				strMsg = "此处缺少右括号";
				break;
			}
			case E_LOSSCOMMA: {
				strMsg = "此处缺少逗号";
				break;
			}
			case E_LOSSCOLON: {
				strMsg = "此处缺少冒号";
				break;
			}
			case E_LOSSSEMICOLON: {
				strMsg = "此处缺少分号";
				break;
			}
			case E_LOSSEND: {
				strMsg = "此处缺少结束";
				break;
			}
			case E_SYNTAX: {
				strMsg = "此处语法错";
				break;
			}
			case E_NOTSAMETYPE: {
				strMsg = "数据类型不一致";
				break;
			}
			case E_FNOTSAMETYPE: {
				strMsg = "公式左边和右边数据类型不一致";
				break;
			}
			case E_MUSTBEDATE: {
				strMsg = "此处必须是日期型，格式为#yyyy.mm.dd#，如#2002.5.16#";
				break;
			}
			case E_MUSTBEINTEGER: {
				strMsg = "此处必须数是整型";
				break;
			}
			case E_MUSTBENUMBER: {
				strMsg = "此处必须是数值型";
				break;
			}
			case E_MUSTBEBOOL: {
				strMsg = "此处必须是逻辑型";
				break;
			}
			case E_MUSTBESTRING: {
				strMsg = "此处必须是字符型";
				break;
			}
			case E_UNKNOWNSTR: {
				strMsg = "条件设置格式不对";
				break;
			}
			case E_GETSELECT: {
				strMsg = "统计、取历史记录和代码转名称能在条件中使用，在临时变量中要单独使用";
				break;
			}
			case E_MUSTBESQLSYMBOL: {
				strMsg = "此处必须是SELECT类型符号：FIRST(的最初第一条记录)，LAST（的最近第一条记录），MAX（的最大值），MIN（的最小值），SUM（的总和），AVG（的平均值），COUNT（的个数）";
				break;
			}
			case E_MUSTBEGETSYMBOL: {
				strMsg = "此处必须是GET类型符号：INCREASE（最初第）、DECREASE（最近第）";
				break;
			}
			case E_MUSTBEFIELDITEM: {
				strMsg = "此处必须是指标";
				break;
			}
			case E_MUSTBEONEFLDSET: {
				strMsg = "条件表达和统计指标必须是同一个子集或主集指标";
				break;
			}
			case E_MUSTBECODEFIELD: {
				strMsg = "此处必须是代码型指标";
				break;
			}
			case E_MUSTBEINTEGERMENU: {
				strMsg = "此处必须是整型指标或临时变量";
				break;
			}		
			case E_MUSTBESUBSET: {
				strMsg = "此处必须子集指标，不能是主集指标";
				break;
			}
			case E_NOTFINDFIELD: {
				strMsg = "没有此指标";
				break;
			}
			case E_NOTFINDODDVAR: {
				strMsg = "不是临时变量";
				break;
			}
			case E_LOSSBRACK1: {
				strMsg = "指标缺右方括号";
				break;
			}
			case E_LOSSBRACK2: {
				strMsg = "指标缺右大括号";
				break;
			}
			case E_LOSSTHEN: {
				strMsg = "缺少那么";
				break;
			}
			case E_LOSSELSE: {
				strMsg = "缺少否则";
				break;
			}
			case E_LOSSIIF: {
				strMsg = "缺少如果";
				break;
			}
			case E_LOSSTEMPTABLENAME: {
				strMsg = "临时表不能为空";
				break;
			}
			case E_LOSSGETEND: {
				strMsg = "缺少取历史记录结束串：条记录";
				break;
			}
			case E_LOSSEQUALE: {
				strMsg = "此处缺少等号";
				break;
			}
			case E_LOSSASIGN: {
				strMsg = "此处缺少赋值号";
			}
			case E_FIELDSETNOTFOUNT: {
				// strMsg = "此处指标集错误";
			}case E_USEFUNCMOD:
			{
				strMsg="Oracle与DB2不支持'%'，请使用'取余数(除数, 被除数)'函数";
				break;
			}case E_USEFUNCINT:
			{
				strMsg="SQL、Oracle、DB2不支持'\'，请使用'取整(数值1)/取整(数值2)";
				break;
			}
			case E_FUNCTON2:
			{
				strMsg="代码转名称2,目前不能用于临时变量之中，可以用代码转名称函数代替!";
				break;
			}	
			case E_STDNDARDNOTEXIST:
			{
				strMsg="标准表不存在!";
				break;			
			}
			case E_STDNDARDNOTTWODIM:
			{
				strMsg="标准表必须是二维！";
				break;
			}
			case E_MENUNOTMATCH:
			{
				strMsg="传入的指标与标准中的指标类型不匹配!";
				break;			
			}
			case E_CODEVALUE:
			{
				strMsg="代码在码表中不存在!";			
				break;
			}
			case E_MUSTBEONETWO:
			{
				strMsg="参数必须为1或2!";			
				break;
			}
			default: {
				strMsg = "未知错误";
				break;
			}
			}
			if (nCurPos <= 200) {
				nCurPos = nCurPos < 0 ? 0 : nCurPos;
				if(nCurPos>=FSource.length())
					strMsg = FSource.substring(0, FSource.length()) + "^^^^" + strMsg;
				else
					strMsg = FSource.substring(0, nCurPos) + "^^^^" + strMsg;
	
			} else {
				strMsg = FSource.substring(nCurPos - 199, nCurPos) + "^^^^"
						+ strMsg;
			}
			setStrError(strMsg);
		}
		public boolean isFError() {
			return FError;
		}
	
		public void setInfoGroupFlag(int infoGroupFlag) {
			InfoGroupFlag = infoGroupFlag;
		}
	
		public void setModeFlag(int modeFlag) {
			ModeFlag = modeFlag;
		}
	
		public void setDbPre(String dbPre) {
			DbPre = dbPre;
		}
	
		public void setDBType(int dbType) {
			DBType = dbType;
		}
	
		public void setTempTableName(String tempTableName) {
			TempTableName = tempTableName;
		}
	
		private List<FieldItem> getFieldItems() {
			return fieldItems;
		}
	
		public void setFieldItems(List<FieldItem> fieldItems) {
			this.fieldItems = fieldItems;
		}
	
		public String getResultString() {
			return ResultString;
		}
	
		public void setMapUsedFieldItems(Map<String,FieldItem> mapUsedFieldItems) {
			this.mapUsedFieldItems = mapUsedFieldItems;
		}
	
		public int getInfoGroupFlag() {
			return InfoGroupFlag;
		}
	
		public List<String> getUsedSets() {
			return UsedSets;
		}
	
		public void setUsedSets(List<String> usedSets) {
			UsedSets = usedSets;
		}
	
		public Map<String,FieldItem> getMapUsedFieldItems() {
			return mapUsedFieldItems;
		}
	
		public void setWhereText(String whereText) {
			this.whereText = "(" + whereText + ")";
		}
	
		public String getTargetFieldDataType() {
			return targetFieldDataType;
		}
	
		public void setTargetFieldDataType(String targetFieldDataType) {
			this.targetFieldDataType = targetFieldDataType;
		}
	
		public int getVarType() {
			return VarType;
		}
	
		public void setVarType(int varType) {
			VarType = varType;
		}
		/**
		 * 对条件过滤的run方法
		 * @param str
		 * @param ymc
		 * @param whereText
		 * @param targetFieldDataType
		 * @return
		 */
	
		public String run_Where(String str, YearMonthCount ymc,String whereText,
				String targetFieldDataType){
			String s = "";
			this.ymc = ymc;
			this.setWhereText(whereText);
			this.targetFieldDataType = targetFieldDataType;
			try {
				s = run_where(str);
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			return s;
		}
	
		/**
		 * 公式校验函数
		 * 
		 * @param     str
		 *            待校验的公式
		 * @return 公式正确与否 注意：如果校验发现公式错误，需要调用getStrError()获取相关错误信息
		 */
		public boolean Verify_where(String str) throws Exception{
			try {
	
				bVerify = true; //语法效验默认为正确
	
				RetValue retValue = new RetValue(); //返回值类型分析类
	
				setFSource(str); //设置全局的要分析的表达式
	
				init();// 清空初始化
	
				//如果临时表名称为空并且查询对象类型为forSearch
				if ((TempTableName == null) && (ModeFlag == forSearch)) {
					SError(E_LOSSTEMPTABLENAME); //临时表不能为空
				}
	
				if (!Get_Expr(retValue)) {
					return false;
				}
				if (retValue.isIntType() || retValue.isFloatType()) {
					if (VarType != FLOAT&&VarType != INT) {
						SError(E_FNOTSAMETYPE);
					}
				}
				if (retValue.IsDateType()) {
					if (VarType != DATEVALUE) {
						SError(E_FNOTSAMETYPE);
					}
				}
				if (retValue.IsStringType()) {
					if (VarType != STRVALUE) {
						SError(E_FNOTSAMETYPE);
					}
				}
				//System.out.println(VarType+"------"+LOGIC);
				if (retValue.isBooleanType()) {
					if (VarType != LOGIC) {
						SError(E_FNOTSAMETYPE);
					}
				}
	
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} 
			return true;
		}
	
	
		/**
		 * 公式校验函数
		 * 
		 * @param str
		 *            待校验的公式(不校验返回类型)
		 * @author lilinbing
		 * @return 公式正确与否 注意：如果校验发现公式错误，需要调用getStrError()获取相关错误信息
		 */
		public boolean Verify_whereNoRetTypte(String str) {
			try {
	
				bVerify = true; //语法效验默认为正确
	
				RetValue retValue = new RetValue(); //返回值类型分析类
	
				setFSource(str); //设置全局的要分析的表达式
	
				init();// 清空初始化
	
				//如果临时表名称为空并且查询对象类型为forSearch
				if ((TempTableName == null) && (ModeFlag == forSearch)) {
					SError(E_LOSSTEMPTABLENAME); //临时表不能为空
				}
	
				if (!Get_Expr(retValue)) {
					return false;
				}		
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	
		/**
		 * 
		 * @param str
		 * @return getSQL()条件语句，TempTableName 临时表格的名字
		 * @throws Exception
		 * @throws SQLException
		 */
		public String run_where(String str) throws Exception{
			RetValue retValue = new RetValue();
			str=str.replaceAll("单位编码", "单位名称");
			str=str.replaceAll("职位编码", "职位名称");	
			setFSource(str);
			init();// 清空初始化
	
			if ((TempTableName == null) && (ModeFlag == forSearch)) {
				SError(E_LOSSTEMPTABLENAME);
			}
	
			if (!Get_Expr(retValue)) {
				return null;
			}
	
			if (bVerify)
				return "";
	
			if (retValue.isIntType() || retValue.isFloatType()) {
				if (VarType != FLOAT&&VarType != INT) {
					SError(E_FNOTSAMETYPE);
				}
			}
			if (retValue.IsDateType()) {
				if (VarType != DATEVALUE) {
					SError(E_FNOTSAMETYPE);
				}
			}
			if (retValue.IsStringType()) {
				if (VarType != STRVALUE) {
					SError(E_FNOTSAMETYPE);
				}
			}
			//System.out.println(VarType+"------"+LOGIC);
			if (retValue.isBooleanType()) {
				if (VarType != LOGIC) {
					SError(E_FNOTSAMETYPE);
				}
			}
	
			ResultString = retValue.ValueToString();
			return result;
		}
	
		public String getTempTableName() {
			return TempTableName;
		}
		/**********添加一个补充过滤条件***********/
		private String renew_term;
	
		public String getRenew_term() {		
			return renew_term;
		}
	
		public void setRenew_term(String renew_term) {
			this.renew_term = renew_term;
		}
	
		public String getStdTmpTable() {
			return StdTmpTable;
		}
	
		public void setStdTmpTable(String stdTmpTable) {
			StdTmpTable = stdTmpTable;
		}
//	
//		public RuleTypeService getRuleTypeservice() {
//			return ruleTypeservice;
//		}
//	
//		public void setRuleTypeservice(RuleTypeService ruleTypeservice) {
//			this.ruleTypeservice = ruleTypeservice;
//		}
	}
