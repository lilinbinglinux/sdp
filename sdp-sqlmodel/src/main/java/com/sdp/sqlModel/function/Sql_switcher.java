package com.sdp.sqlModel.function;

import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sdp.common.BaseException;
import com.sdp.sqlModel.entity.SqlField;
import com.sdp.sqlModel.entity.SqlTable;



public class Sql_switcher
{
	private static final Logger log = LoggerFactory.getLogger(Sql_switcher.class);
	public int dbflag = 1; 
	private static final int AGE = 0;
	private static final int APPAGE = 1;
	private static final int WORKAGE = 2;
	private static final int APPWORKAGE = 3;
	private static final int MONTHAGE = 4;
	private static final int APPMONTHAGE = 5;
	private static final int AGE_Y = 6;
	public static final int MSSQL = 1;
	public static final int ORACLE = 2;
	public static final int DB2 = 3;
	public static final int SYSBASE = 4;

	public Sql_switcher(int dbflag){
		this.dbflag = dbflag;
	}
	public static Sql_switcher getSqlFunc(int dbflag){
		return new Sql_switcher(dbflag);
	}

	public String toInt(String expr)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
		case 3:
			strvalue.append(" TRUNC(");
			strvalue.append(expr);
			strvalue.append(",0)");
			break;
		default:
			strvalue.append("CAST(");
			strvalue.append(expr);
			strvalue.append(" AS INT)");
		}

		return strvalue.toString();
	}

	public String round(String expr, int deci)
	{
		StringBuffer strvalue = new StringBuffer();
		strvalue.append(" ROUND(");
		strvalue.append(expr);
		strvalue.append("+0.0000000001,");
		strvalue.append(deci);
		strvalue.append(")");
		return strvalue.toString();
	}

	private static void getUpdateFields(String strSet, StringBuffer strDFields, StringBuffer strSFields)
	{
		String[] strArr = StringUtils.split(strSet, "`");
		for (int i = 0; i < strArr.length; ++i)
		{
			String temp = strArr[i];
			String[] strtmp = StringUtils.split(temp, "=", 2);
			strDFields.append(strtmp[0]);
			strDFields.append(",");
			strSFields.append(strtmp[1]);
			strSFields.append(",");
		}
		if (strDFields.length() > 0)
			strDFields.setLength(strDFields.length() - 1);
		if (strSFields.length() > 0)
			strSFields.setLength(strSFields.length() - 1);
	}

	public String getUpdateSqlTwoTable(String destTab, String srcTab, String strJoin, String strSet, String strDWhere, String strSWhere)
	{
		StringBuffer strSQL = new StringBuffer();
		boolean bapp = false;
		switch (dbflag)
		{
		case 2:
		case 3:
			StringBuffer strDFields = new StringBuffer();
			StringBuffer strSFields = new StringBuffer();
			getUpdateFields(strSet, strDFields, strSFields);

			strSQL.append("update ");
			strSQL.append(destTab);
			strSQL.append(" set (");
			strSQL.append(strDFields.toString());
			strSQL.append(")=(select ");
			strSQL.append(strSFields.toString());
			strSQL.append(" from ");
			strSQL.append(srcTab);
			strSQL.append(" where ");
			strSQL.append(strJoin);
			if ((strSWhere != null) && (!(strSWhere.equals(""))))
			{
				strSQL.append(" and ");
				strSQL.append(strSWhere);
			}
			strSQL.append(")");
			if ((strDWhere == null) || (strDWhere.equals("")))
				break ;
			strSQL.append(" where ");
			strSQL.append(strDWhere);
			break;
		default:
			strSet = strSet.replace('`', ',');
			strSQL.append("update ");
			strSQL.append(destTab);
			String strLeft = " left join " + srcTab + " on " + strJoin;
			String strUpdate = " set " + strSet;
			String strFrom = " from " + destTab;
			strSQL.append(strUpdate);
			strSQL.append(strFrom);
			strSQL.append(strLeft);
			if ((strSWhere != null) && (!(strSWhere.equals(""))))
			{
				strSQL.append(" where ");
				strSQL.append(strSWhere);
				bapp = true;
			}
			if ((strDWhere == null) || (strDWhere.equals("")))
				break ;
			if (bapp)
				strSQL.append(" and ");
			else
				strSQL.append(" where ");
			strSQL.append(strDWhere);
		}

		label423: return strSQL.toString();
	}

	public String sanqi(String expr)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
		case 3:
			strvalue.append(" CASE WHEN ");
			strvalue.append(expr);
			strvalue.append("-TRUNC(");
			strvalue.append(expr);
			strvalue.append(",0)<0.3 THEN ");
			strvalue.append("TRUNC(");
			strvalue.append(expr);
			strvalue.append(",0) WHEN");
			strvalue.append(expr);
			strvalue.append("-TRUNC(");
			strvalue.append(expr);
			strvalue.append(",0)>=0.7 THEN ");
			strvalue.append("(TRUNC(");
			strvalue.append(expr);
			strvalue.append(",0)+1) ELSE ");
			strvalue.append("(TRUNC(");
			strvalue.append(expr);
			strvalue.append(",0)+0.5) END");
			break;
		default:
			strvalue.append(" CASE WHEN ");
			strvalue.append(expr);
			strvalue.append("-CAST(");
			strvalue.append(expr);
			strvalue.append(" AS INT)<0.3 THEN ");
			strvalue.append("CAST(");
			strvalue.append(expr);
			strvalue.append(" AS INT) WHEN");
			strvalue.append(expr);
			strvalue.append("-CAST(");
			strvalue.append(expr);
			strvalue.append(" AS INT)>=0.7 THEN ");
			strvalue.append("(CAST(");
			strvalue.append(expr);
			strvalue.append(" AS INT)+1) ELSE ");
			strvalue.append("(CAST(");
			strvalue.append(expr);
			strvalue.append(" AS INT)+0.5) END");
		}

		return strvalue.toString();
	}

	public String yuan(String expr)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
		case 3:
			strvalue.append(" CASE WHEN ");
			strvalue.append(expr);
			strvalue.append("-TRUNC(");
			strvalue.append(expr);
			strvalue.append(",0)<>0 THEN ");
			strvalue.append("(TRUNC(");
			strvalue.append(expr);
			strvalue.append(",0)+1) ELSE ");
			strvalue.append("TRUNC(");
			strvalue.append(expr);
			strvalue.append(",0) END");
			break;
		default:
			strvalue.append(" CASE WHEN ");
			strvalue.append(expr);
			strvalue.append("-CAST(");
			strvalue.append(expr);
			strvalue.append(" AS INT)<>0 THEN ");
			strvalue.append("(CAST(");
			strvalue.append(expr);
			strvalue.append(" AS INT)+1) ELSE ");
			strvalue.append("CAST(");
			strvalue.append(expr);
			strvalue.append(" AS INT) END");
		}

		return strvalue.toString();
	}

	public String jiao(String expr)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
		case 3:
			strvalue.append(" CEIL(TRUNC(");
			strvalue.append(expr);
			strvalue.append("*100,0)/10.0)/10.0");
			break;
		default:
			strvalue.append(" CEILING(ROUND((");
			strvalue.append(expr);
			strvalue.append("*100),0)/10.0)/10.0");
		}

		return strvalue.toString();
	}

	public String max(String expr1, String expr2)
	{
		StringBuffer strvalue = new StringBuffer();
		strvalue.append(" CASE WHEN (");
		strvalue.append(expr1);
		strvalue.append(")>(");
		strvalue.append(expr2);
		strvalue.append(") THEN ");
		strvalue.append(expr1);
		strvalue.append(" ELSE ");
		strvalue.append(expr2);
		strvalue.append(" END ");
		return strvalue.toString();
	}

	public String min(String expr1, String expr2)
	{
		StringBuffer strvalue = new StringBuffer();
		strvalue.append(" CASE WHEN (");
		strvalue.append(expr1);
		strvalue.append(")>(");
		strvalue.append(expr2);
		strvalue.append(") THEN ");
		strvalue.append(expr2);
		strvalue.append(" ELSE ");
		strvalue.append(expr1);
		strvalue.append(" END ");
		return strvalue.toString();
	}

	private String computeAge(String date_expr, int type)
	{
		StringBuffer strvalue = new StringBuffer();

		strvalue.append("(CASE WHEN ");
		strvalue.append(date_expr);
		strvalue.append(" IS NULL THEN 0 ELSE ");
		switch (type)
		{
		case 0:
		case 1:
			strvalue.append("(");
			strvalue.append(sysYear());
			strvalue.append("+");
			strvalue.append(sysMonth());
			strvalue.append("*0.01+");
			strvalue.append(sysDay());
			strvalue.append("*0.0001)-");

			strvalue.append("(");
			strvalue.append(year(date_expr));
			strvalue.append("+");
			strvalue.append(month(date_expr));
			strvalue.append("*0.01+");
			strvalue.append(day(date_expr));
			strvalue.append("*0.0001)");
			break;
		case 2:
		case 3:
			strvalue.append("(");
			strvalue.append(sysYear());

			strvalue.append("-");
			strvalue.append(year(date_expr));
			strvalue.append("+1)");
			break;
		case 4:
		case 5:
			strvalue.append("(");
			strvalue.append(sysYear());
			strvalue.append("+");
			strvalue.append(sysMonth());
			strvalue.append("*0.01)-");

			strvalue.append("(");
			strvalue.append(year(date_expr));
			strvalue.append("+");
			strvalue.append(month(date_expr));
			strvalue.append("*0.01)");
		}

		strvalue.append(" END )");
		return strvalue.toString();
	}

	private String computeAge(String sdate_expr, String edate_expr, int type)
	{
		StringBuffer strvalue = new StringBuffer();

		strvalue.append("(CASE WHEN ");
		strvalue.append(sdate_expr);
		strvalue.append(" IS NULL THEN 0 ELSE ");
		switch (type)
		{
		case 0:
		case 1:
			strvalue.append("(");
			strvalue.append(year(dateValue(edate_expr)));
			strvalue.append("+");
			strvalue.append(month(dateValue(edate_expr)));
			strvalue.append("*0.01+");
			strvalue.append(day(dateValue(edate_expr)));
			strvalue.append("*0.0001)-");

			strvalue.append("(");
			strvalue.append(year(sdate_expr));
			strvalue.append("+");
			strvalue.append(month(sdate_expr));
			strvalue.append("*0.01+");
			strvalue.append(day(sdate_expr));
			strvalue.append("*0.0001)");
			break;
		case 6:
			strvalue.append("(");
			strvalue.append(year(dateValue(edate_expr)));
			strvalue.append("-");
			strvalue.append(year(sdate_expr));
			strvalue.append(")");
			break;
		case 2:
		case 3:
			strvalue.append("(");
			strvalue.append(year(dateValue(edate_expr)));

			strvalue.append("-");
			strvalue.append(year(sdate_expr));
			strvalue.append("+1)");
			break;
		case 4:
		case 5:
			strvalue.append("(");
			strvalue.append(year(dateValue(edate_expr)));
			strvalue.append("+");
			strvalue.append(month(dateValue(edate_expr)));
			strvalue.append("*0.01)-");

			strvalue.append("(");
			strvalue.append(year(sdate_expr));
			strvalue.append("+");
			strvalue.append(month(sdate_expr));
			strvalue.append("*0.01)");
		}

		strvalue.append(" END )");
		return strvalue.toString();
	}

	public String age(String date_expr)
	{
		return computeAge(date_expr, 0);
	}

	public String age(String sdate_expr, String edate_expr)
	{
		return computeAge(sdate_expr, edate_expr, 0);
	}

	public String appAge(String date_expr)
	{
		return computeAge(date_expr, 1);
	}

	public String appAge(String sdate_expr, String edate_expr)
	{
		return computeAge(sdate_expr, edate_expr, 1);
	}

	public String workAge(String date_expr)
	{
		return computeAge(date_expr, 2);
	}

	public String workAge(String sdate_expr, String edate_expr)
	{
		return computeAge(sdate_expr, edate_expr, 2);
	}

	public String age_Y(String sdate_expr, String edate_expr)
	{
		return computeAge(sdate_expr, edate_expr, 6);
	}

	public String workAppAge(String date_expr)
	{
		return computeAge(date_expr, 3);
	}

	public String workAppAge(String sdate_expr, String edate_expr) {
		return computeAge(sdate_expr, edate_expr, 3);
	}

	public String monthAge(String date_expr)
	{
		return computeAge(date_expr, 4);
	}

	public String monthAge(String sdate_expr, String edate_expr) {
		return computeAge(sdate_expr, edate_expr, 4);
	}

	public String appMonthAge(String date_expr)
	{
		return computeAge(date_expr, 5);
	}

	public String appMonthAge(String sdate_expr, String edate_expr)
	{
		return computeAge(sdate_expr, edate_expr, 5);
	}

	public String weekDay(String date_expr)
	{
		StringBuffer strvalue = new StringBuffer();
		strvalue.append("(CASE WHEN ");
		strvalue.append(date_expr);
		strvalue.append(" IS NULL THEN 0 ELSE ");
		switch (dbflag)
		{
		case 1:
			strvalue.append("(DATEPART(");
			strvalue.append("WEEKDAY,");
			strvalue.append(date_expr);
			strvalue.append(")-1)");
			break;
		case 2:
			strvalue.append("TO_NUMBER(TO_CHAR(");
			strvalue.append(date_expr);
			strvalue.append(",\"D\")-1)");
			break;
		case 3:
			strvalue.append(" DAYOFWEEK(");
			strvalue.append(date_expr);
			strvalue.append(")-1");
			break;
		default:
			strvalue.append("(DATEPART(");
			strvalue.append("WEEKDAY,");
			strvalue.append(date_expr);
			strvalue.append(")-1)");
		}

		strvalue.append(" END )");
		return strvalue.toString();
	}

	public String left_join(String ltable, String rtable, String lfield, String rfield)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 2:
		case 3:
			strvalue.append(" LEFT JOIN ");
			strvalue.append(rtable);
			strvalue.append(" ON ");
			strvalue.append(lfield);
			strvalue.append("=");
			strvalue.append(rfield);
			break;
		default:
			strvalue.append(" LEFT JOIN ");
			strvalue.append(rtable);
			strvalue.append(" ON ");
			strvalue.append(lfield);
			strvalue.append("=");
			strvalue.append(rfield);
		}

		return strvalue.toString();
	}

	public String right_join(String ltable, String rtable, String lfield, String rfield)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 2:
		case 3:
			strvalue.append(" RIGHT OUTER JOIN ");
			strvalue.append(rtable);
			strvalue.append(" ON ");
			strvalue.append(lfield);
			strvalue.append("=");
			strvalue.append(rfield);
			break;
		default:
			strvalue.append(" RIGHT JOIN ");
			strvalue.append(rtable);
			strvalue.append(" ON ");
			strvalue.append(lfield);
			strvalue.append("=");
			strvalue.append(rfield);
		}

		return strvalue.toString();
	}

	public String isnull(String fieldname, String repvalue)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("ISNULL(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(repvalue);
			strvalue.append(")");
			break;
		case 3:
			strvalue.append("COALESCE(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(repvalue);
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("NVL(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(repvalue);
			strvalue.append(")");
			break;
		default:
			strvalue.append("ISNULL(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(repvalue);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String today()
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("convert(varchar(10),GETDATE(),20)");
			break;
		case 3:
			strvalue.append("Current Timestampe");
			break;
		case 2:
			strvalue.append("TO_DATE(TO_CHAR(SYSDATE,'YYYY.MM.DD'),'YYYY.MM.DD')");
			break;
		default:
			strvalue.append("GETDATE()");
		}

		return strvalue.toString();
	}

	public String toWeek()
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("DATEPART(WEEK,GETDATE())");
			break;
		case 3:
			strvalue.append("WEEK(Current Timestamp)");
			break;
		case 2:
			strvalue.append("TO_NUMBER(TO_CHAR(SYSDATE,'WW'))");
			break;
		default:
			strvalue.append("DATEPART(WEEK,GETDATE())");
		}

		return strvalue.toString();
	}

	public String toQuarter()
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("DATEPART(QUARTER,GETDATE())");
			break;
		case 3:
			strvalue.append("QUARTER(Current Timestamp)");
			break;
		case 2:
			strvalue.append("TO_NUMBER(TO_CHAR(SYSDATE,'Q'))");
			break;
		default:
			strvalue.append("DATEPART(QUARTER,GETDATE())");
		}

		return strvalue.toString();
	}

	public String toMonth()
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("DATEPART(MONTH,GETDATE())");
			break;
		case 3:
			strvalue.append("MONTH(Current Timestamp)");
			break;
		case 2:
			strvalue.append("TO_NUMBER(TO_CHAR(SYSDATE,'MM'))");
			break;
		default:
			strvalue.append("DATEPART(MONTH,GETDATE())");
		}

		return strvalue.toString();
	}

	public String toYear()
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("DATEPART(YEAR,GETDATE())");
			break;
		case 3:
			strvalue.append("YEAR(Current Timestamp)");
			break;
		case 2:
			strvalue.append("TO_NUMBER(TO_CHAR(SYSDATE,'YYYY'))");
			break;
		default:
			strvalue.append("DATEPART(YEAR,GETDATE())");
		}

		return strvalue.toString();
	}

	public String quarter(String fieldname)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("DATEPART(QUARTER,");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 3:
			strvalue.append("QUARTER(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("TO_NUMBER(TO_CHAR(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append("'Q'))");
			break;
		default:
			strvalue.append("DATEPART(QUARTER,");
			strvalue.append(fieldname);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String week(String fieldname)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("DATEPART(WEEK,");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 3:
			strvalue.append("WEEK(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("TO_NUMBER(TO_CHAR(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append("'WW'))");
			break;
		default:
			strvalue.append("DATEPART(WEEK,");
			strvalue.append(fieldname);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String day(String fieldname)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 3:
			strvalue.append("DAY(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("EXTRACT(DAY FROM ");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		default:
			strvalue.append("DAY(");
			strvalue.append(fieldname);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	private String diff_date(String difftype, String start_date, String end_date)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("DATEDIFF(");
			strvalue.append(difftype);
			strvalue.append(",");
			strvalue.append(end_date);
			strvalue.append(",");
			strvalue.append(start_date);
			strvalue.append(")");
			break;
		case 3:
			if (difftype.equalsIgnoreCase("year"))
			{
				strvalue.append("TRUNC(TIMESTAMPDIFF(64,CHAR(");
				strvalue.append(start_date);
				strvalue.append(",");
				strvalue.append(end_date);
				strvalue.append("))/12.0,0)");
				break ; }
			if (difftype.equalsIgnoreCase("month"))
			{
				strvalue.append("TIMESTAMPDIFF(64,CHAR(");
				strvalue.append(start_date);
				strvalue.append(",");
				strvalue.append(end_date);
				strvalue.append("))");
				break ; }
			if (difftype.equalsIgnoreCase("day"))
			{
				strvalue.append("TIMESTAMPDIFF(16,CHAR(");
				strvalue.append(start_date);
				strvalue.append(",");
				strvalue.append(end_date);
				strvalue.append("))");
				break ; }
			if (difftype.equalsIgnoreCase("quarter"))
			{
				strvalue.append("TIMESTAMPDIFF(128,CHAR(");
				strvalue.append(start_date);
				strvalue.append(",");
				strvalue.append(end_date);
				strvalue.append("))");
				break ; }
			if (!(difftype.equalsIgnoreCase("week")))
				break ;
			strvalue.append("TIMESTAMPDIFF(32,CHAR(");
			strvalue.append(start_date);
			strvalue.append(",");
			strvalue.append(end_date);
			strvalue.append("))");
			break;
		case 2:
			if (difftype.equalsIgnoreCase("day"))
			{
				strvalue.append(start_date);
				strvalue.append("-");
				strvalue.append(end_date);
				break ; }
			if (difftype.equalsIgnoreCase("week"))
			{
				strvalue.append("TRUNC((");
				strvalue.append(start_date);
				strvalue.append("-");
				strvalue.append(end_date);
				strvalue.append(")/7,0)");
				break ;
			}

			strvalue.append("TRUNC(MONTHS_BETWEEN(");
			strvalue.append(start_date);
			strvalue.append(",");
			strvalue.append(end_date);
			strvalue.append(")");
			if (difftype.equalsIgnoreCase("year")) {
				strvalue.append("/12,0)"); break ; }
			if (difftype.equalsIgnoreCase("quarter")) {
				strvalue.append("/3,0)"); break ;
			}
			strvalue.append(",0)");

			break;
		default:
			strvalue.append("DATEDIFF(");
			strvalue.append(difftype);
			strvalue.append(",");
			strvalue.append(end_date);
			strvalue.append(",");
			strvalue.append(start_date);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String diffMinute(String start_date, String end_date)
	{
		return diff_date("Minute", start_date, end_date);
	}

	public String diffHours(String start_date, String end_date)
	{
		return diff_date("Hour", start_date, end_date);
	}

	public String diffDays(String start_date, String end_date)
	{
		return diff_date("day", start_date, end_date);
	}

	public String diffWeeks(String start_date, String end_date) {
		return diff_date("Week", start_date, end_date);
	}

	public String diffMonths(String start_date, String end_date) {
		return diff_date("Month", start_date, end_date);
	}

	public String diffQuarters(String start_date, String end_date) {
		return diff_date("quarter", start_date, end_date);
	}

	public String diffYears(String start_date, String end_date) {
		return diff_date("year", start_date, end_date);
	}

	private String addDate(String addtype, String date_expr, String value)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("DATEADD(");
			strvalue.append(addtype);
			strvalue.append(",");
			strvalue.append(value);
			strvalue.append(",");
			strvalue.append(date_expr);
			strvalue.append(")");
			break;
		case 3:
			if (addtype.equalsIgnoreCase("year"))
			{
				strvalue.append(date_expr);
				strvalue.append("+");
				strvalue.append(value);
				strvalue.append(" YEARS ");
				break ; }
			if (addtype.equalsIgnoreCase("quarter"))
			{
				strvalue.append(date_expr);
				strvalue.append("+(");
				strvalue.append(value);
				strvalue.append("*3) MONTHS ");
				break ; }
			if (addtype.equalsIgnoreCase("month"))
			{
				strvalue.append(date_expr);
				strvalue.append("+");
				strvalue.append(value);
				strvalue.append(" MONTHS ");
				break ; }
			if (addtype.equalsIgnoreCase("week"))
			{
				strvalue.append(date_expr);
				strvalue.append("+(");
				strvalue.append(value);
				strvalue.append("*7) DAYS ");
				break ;
			}

			strvalue.append(date_expr);
			strvalue.append("+");
			strvalue.append(value);
			strvalue.append(" DAYS ");

			break;
		case 2:
			if (addtype.equalsIgnoreCase("year"))
			{
				strvalue.append("ADD_MONTHS(");
				strvalue.append(date_expr);
				strvalue.append(",");
				strvalue.append(value);
				strvalue.append("*12)");
				break ; }
			if (addtype.equalsIgnoreCase("quarter"))
			{
				strvalue.append("ADD_MONTHS(");
				strvalue.append(date_expr);
				strvalue.append(",");
				strvalue.append(value);
				strvalue.append("*3)");
				break ; }
			if (addtype.equalsIgnoreCase("month"))
			{
				strvalue.append("ADD_MONTHS(");
				strvalue.append(date_expr);
				strvalue.append(",");
				strvalue.append(value);
				strvalue.append(")");
				break ; }
			if (addtype.equalsIgnoreCase("week"))
			{
				strvalue.append(date_expr);
				strvalue.append("+");
				strvalue.append(value);
				strvalue.append("*7");
				break ;
			}

			strvalue.append(date_expr);
			strvalue.append("+");
			strvalue.append(value);
		}

		return strvalue.toString();
	}

	public String addYears(String date_expr, String years)
	{
		return addDate("year", date_expr, years);
	}

	public String addQuarters(String date_expr, String quarters)
	{
		return addDate("quarter", date_expr, quarters);
	}

	public String addMonths(String date_expr, String months)
	{
		return addDate("month", date_expr, months);
	}

	public String addWeeks(String date_expr, String weeks)
	{
		return addDate("week", date_expr, weeks);
	}

	public String addDays(String date_expr, String days)
	{
		return addDate("day", date_expr, days);
	}

	public String month(String fieldname)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 3:
			strvalue.append("MONTH(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("EXTRACT(MONTH FROM ");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		default:
			strvalue.append("MONTH(");
			strvalue.append(fieldname);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String trunc(String expr, String nlen)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 3:
			strvalue.append("CAST(");
			strvalue.append(expr);
			strvalue.append(" as INT ");
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("TRUNC(");
			strvalue.append(expr);
			strvalue.append(",");
			strvalue.append(nlen);
			strvalue.append(")");
			break;
		default:
			strvalue.append("CAST(");
			strvalue.append(expr);
			strvalue.append(",");
			strvalue.append(nlen);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String concat()
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
		case 3:
			strvalue.append("||");
			break;
		default:
			strvalue.append("+");
		}

		return strvalue.toString();
	}

	public String sqlNow()
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 3:
			strvalue.append("Current Timestamp");
		case 2:
			strvalue.append("SYSDATE");
			break;
		default:
			strvalue.append("GetDate()");
		}

		return strvalue.toString();
	}

	public String readMemo(ResultSet rset, String columnname)
	{
		StringBuffer content = new StringBuffer();
		Reader is = null;
		BufferedReader br = null;
		try
		{
			switch (dbflag)
			{
			case 1:
				String temp = rset.getString(columnname);
				content.append((temp == null) ? "" : temp);
				break;
			case 2:
			case 3:
				if (rset.getObject(columnname) == null) {
					String str1 = "";
					return str1;
				}
				Clob clob = rset.getClob(columnname);
				if (clob == null) {
					return "";
				}
				is = clob.getCharacterStream();
				br = new BufferedReader(is);
				String s = br.readLine();
				int i = 0;
				while (s != null)
				{
					if (i != 0)
						content.append("\r\n");
					content.append(s);
					s = br.readLine();
					++i;
				}

			}

		}
		catch (SQLException sqle)
		{
			log.info("context", sqle);
		}
		catch (Exception sqle)
		{
			log.info("context", sqle);
		}
		finally
		{
			try
			{
				if (is != null)
					is.close();
				if (br != null)
					br.close();
			}
			catch (Exception ee)
			{
				log.info("context", ee);
			}
		}
		return content.toString();
	}

	public String year(String fieldname)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 3:
			strvalue.append("YEAR(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("EXTRACT(YEAR FROM ");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		default:
			strvalue.append("YEAR(");
			strvalue.append(fieldname);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String sysYear()
	{
		return year(today());
	}

	public String sysMonth() {
		return month(today());
	}

	public String sysDay() {
		return day(today());
	}

	public String trim(String expr)
	{
		StringBuffer strvalue = new StringBuffer();
		strvalue.append("RTRIM(LTRIM(");
		strvalue.append(expr);
		strvalue.append("))");
		return strvalue.toString();
	}

	public String ltrim(String expr)
	{
		StringBuffer strvalue = new StringBuffer();
		strvalue.append("LTRIM(");
		strvalue.append(expr);
		strvalue.append(")");
		return strvalue.toString();
	}

	public String rtrim(String expr)
	{
		StringBuffer strvalue = new StringBuffer();
		strvalue.append("RTRIM(");
		strvalue.append(expr);
		strvalue.append(")");
		return strvalue.toString();
	}

	public String getCatOp()
	{
		String operator = "+";
		switch (dbflag)
		{
		case 1:
			operator = "+";
			break;
		case 2:
		case 3:
			operator = "||";
			break;
		default:
			operator = "+";
		}

		return operator;
	}

	public String right(String expr, String len)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 3:
			strvalue.append("RIGHT(");
			strvalue.append(expr);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("SUBSTR(");
			strvalue.append(expr);
			strvalue.append(",LENGTH(");
			strvalue.append(expr);
			strvalue.append(")-");
			strvalue.append(len);
			strvalue.append("+1,");
			strvalue.append(len);
			strvalue.append(")");
			break;
		default:
			strvalue.append("RIGHT(");
			strvalue.append(expr);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String right(String expr, int len)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 3:
			strvalue.append("RIGHT(");
			strvalue.append(expr);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("SUBSTR(");
			strvalue.append(expr);
			strvalue.append(",LENGTH(");
			strvalue.append(expr);
			strvalue.append(")-");
			strvalue.append(len);
			strvalue.append("+1,");
			strvalue.append(len);
			strvalue.append(")");
			break;
		default:
			strvalue.append("RIGHT(");
			strvalue.append(expr);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String substr(String fieldname, String start, String len)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("SUBSTRING(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(start);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
			break;
		case 2:
		case 3:
			strvalue.append("SUBSTR(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(start);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
			break;
		default:
			strvalue.append("SUBSTRING(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(start);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String datalength(String fieldname)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("datalength(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 2:
		case 3:
			strvalue.append("LENGTH(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		default:
			strvalue.append("LEN(");
			strvalue.append(fieldname);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String length(String fieldname)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
			strvalue.append("LEN(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 2:
		case 3:
			strvalue.append("LENGTH(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		default:
			strvalue.append("LEN(");
			strvalue.append(fieldname);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String left(String fieldname, String len)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 3:
			strvalue.append("LEFT(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("SUBSTR(");
			strvalue.append(fieldname);
			strvalue.append(",1,");
			strvalue.append(len);
			strvalue.append(")");
			break;
		default:
			strvalue.append("LEFT(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String left(String fieldname, int len)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 3:
			strvalue.append("LEFT(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
			break;
		case 2:
			strvalue.append("SUBSTR(");
			strvalue.append(fieldname);
			strvalue.append(",1,");
			strvalue.append(len);
			strvalue.append(")");
			break;
		default:
			strvalue.append("LEFT(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(len);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String sqlNull(String fieldname, String strexpr)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
			strvalue.append("nvl(");
			strvalue.append(fieldname);
			strvalue.append(",'");
			strvalue.append(strexpr);
			strvalue.append("')");
			break;
		case 3:
			strvalue.append("coalesce(");
			strvalue.append(fieldname);
			strvalue.append(",'");
			strvalue.append(strexpr);
			strvalue.append("')");
			break;
		default:
			strvalue.append("isnull(");
			strvalue.append(fieldname);
			strvalue.append(",'");
			strvalue.append(strexpr);
			strvalue.append("')");
		}

		return strvalue.toString();
	}

	public String sqlNull(String fieldname, float strexpr)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
			strvalue.append("nvl(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(strexpr);
			strvalue.append(")");
			break;
		case 3:
			strvalue.append("coalesce(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(strexpr);
			strvalue.append(")");
			break;
		default:
			strvalue.append("isnull(");
			strvalue.append(fieldname);
			strvalue.append(",");
			strvalue.append(strexpr);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String sqlToInt(String fieldname)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
			strvalue.append("to_number(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 3:
			strvalue.append("integer(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		default:
			strvalue.append("convert(integer,");
			strvalue.append(fieldname);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String charToDate(String fieldname)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
		case 3:
			strvalue.append("to_date(");
			strvalue.append(fieldname);
			strvalue.append(",'YYYY.MM.dd')");
			break;
		default:
			strvalue.append("convert(datetime,");
			strvalue.append(fieldname);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String charToFloat(String expr)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
			strvalue.append("TO_NUMBER(");
			strvalue.append(expr);
			strvalue.append(")");
			break;
		case 3:
			strvalue.append("DECIMAL(");
			strvalue.append(expr);
			strvalue.append(")");
			break;
		default:
			strvalue.append("CAST(");
			strvalue.append(expr);
			strvalue.append(" AS FLOAT)");
		}

		return strvalue.toString();
	}

	public String dateToChar(String expr)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
			strvalue.append("TO_CHAR(");
			strvalue.append(expr);
			strvalue.append(",'YYYY-MM-DD')");
			break;
		case 3:
			strvalue.append("LEFT(TO_CHAR(");
			strvalue.append(expr);
			strvalue.append(",");
			strvalue.append("'YYYY-MM-DD HH24:MI:SS),10)");
			break;
		default:
			strvalue.append("LEFT(CONVERT(VARCHAR,");
			strvalue.append(expr);
			strvalue.append(",121),10)");
		}

		return strvalue.toString();
	}

	public String floatToChar(String expr)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
			strvalue.append("TO_CHAR(");
			strvalue.append(expr);
			strvalue.append(",'999999999')");
			break;
		case 3:
			strvalue.append("CHAR(INT(");
			strvalue.append(expr);
			strvalue.append("))");
			break;
		default:
			strvalue.append("CAST(CAST(");
			strvalue.append(expr);
			strvalue.append(" AS DECIMAL) AS VARCHAR)");
		}

		return strvalue.toString();
	}

	public String numberToChar(String fieldname)
	{
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 2:
			strvalue.append("to_char(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		case 3:
			strvalue.append("char(");
			strvalue.append(fieldname);
			strvalue.append(")");
			break;
		default:
			strvalue.append("convert(varchar,");
			strvalue.append(fieldname);
			strvalue.append(")");
		}

		return strvalue.toString();
	}

	public String sql_Case(String strExpr, String srElse, String[][] cases)
	{
		StringBuffer buf = new StringBuffer();
		buf.append(" case ");
		buf.append(strExpr);
		for (int i = 0; i < cases.length; ++i)
		{
			buf.append(" when ");
			buf.append(cases[i][0]);
			buf.append(" then ");
			buf.append(cases[i][1]);
		}
		if (srElse.length() > 0)
		{
			buf.append(" else ");
			buf.append(srElse);
		}
		buf.append(" end ");
		return buf.toString();
	}

	public String sql_NextVal(String seqname)
	{
		StringBuffer strtext = new StringBuffer();
		switch (dbflag)
		{
		case 2:
			strtext.append(seqname);
			strtext.append(".nextval");
			break;
		case 3:
			strtext.append(" nextval for ");
			strtext.append(seqname);
		}

		return strtext.toString();
	}

	public String sqlTop(String strsql, int reccount)
	{
		StringBuffer strtext = new StringBuffer();
		strsql = strsql.toLowerCase();
		int idx = 0;
		switch (dbflag)
		{
		case 2:
			idx = strsql.indexOf("where");
			if (idx == -1)
			{
				strtext.append(strsql);
				strtext.append(" where rownum<=");
				strtext.append(reccount);
				break ;
			}

			strtext.append(strsql.substring(0, idx + 6));
			strtext.append(" rownum<= ");
			strtext.append(reccount);
			strtext.append(" and ");
			strtext.append(strsql.substring(idx + 6));

			break;
		case 3:
			strtext.append(strsql);
			strtext.append(" fetch first ");
			strtext.append(reccount);
			strtext.append(" rows only");
			break;
		default:
			idx = strsql.indexOf("select");
			strtext.append(strsql.substring(0, idx + 7));
			strtext.append(" top ");
			strtext.append(reccount);
			strtext.append(" ");
			strtext.append(strsql.substring(idx + 7));
		}

		return strtext.toString();
	}

	public String dateToChar(String fieldname, String format)
	{
		StringBuffer strvalue = new StringBuffer();
		int len = format.length();
		switch (dbflag)
		{
		case 2:
		case 3:
			strvalue.append("to_char(");
			strvalue.append(fieldname);
			strvalue.append(",'");
			strvalue.append(format);
			strvalue.append("')");
			break;
		default:
			strvalue.append("convert(varchar(");
			strvalue.append(len);
			strvalue.append("),");
			strvalue.append(fieldname);
			strvalue.append(",20)");
		}

		return strvalue.toString();
	}

	public String getFieldType(char fieldtype, int len, int deci)
	{
		StringBuffer buf = new StringBuffer();
		buf.append(" ");
		switch (fieldtype)
		{
		case 'A':
		case 'a':
			switch (dbflag)
			{
			case 2:
				buf.append("VARCHAR2(");
				buf.append(len);
				buf.append(")");
				break;
			default:
				buf.append("VARCHAR(");
				buf.append(len);
				buf.append(")"); }
			break;
		case 'N':
		case 'n':
			if (deci == 0);
			switch (dbflag)
			{
			case 2:
				buf.append("Integer");
				break;
			default:
				buf.append("Int");
				break ;
			}
			buf.append("numeric(");
			buf.append(len + deci);
			buf.append(",");
			buf.append(deci);
			buf.append(")");
			break;
		case 'D':
		case 'd':
			switch (dbflag)
			{
			case 2:
				buf.append("DATE");
				break;
			case 3:
				buf.append("Timestamp");
				break;
			default:
				buf.append("DATETIME"); }
			break;
		case 'M':
		case 'm':
			switch (dbflag)
			{
			case 2:
			case 3:
				buf.append("CLOB");
				break;
			default:
				buf.append("TEXT"); }
			break;
		case 'L':
		case 'l':
			switch (dbflag)
			{
			case 2:
			case 3:
				buf.append("BLOB");
				break;
			default:
				buf.append("IMAGE");
			}

		}
		buf.append(" ");
		return buf.toString();
	}

	public String dateValue(String value)
	{
		if ((value == null) || (value.equals("")))
			return " NULL ";
		value = value.replaceAll("\\.", "-");
		switch (dbflag)
		{
		case 1:
			value = "'" + value + "'";
			break;
		case 2:
			if (value.length() > 10) {
				value = "TO_DATE('" + value + "', 'YYYY-MM-DD HH24:MI:SS')"; break ;
			}
			value = "TO_DATE('" + value + "', 'YYYY-MM-DD')";
			break;
		case 3:
			if (value.length() > 10) {
				value = "TO_DATE('" + value + "', 'YYYY-MM-DD HH24:MI:SS')"; break ;
			}
			value = "TO_DATE('" + value + " 0:0:0', 'YYYY-MM-DD HH24:MI:SS')";
		}

		return value;
	}
	/**
	 * 
	 * @param typeMap
	 * 字段在表中类型
	 * @param sqlFieldNew
	 * 字段信息
	 * @param tableMap
	 * 表信息
	 * @return
	 * @throws BaseException 
	 */
	public String createTable(HashMap<String,Object> typeMap,SqlTable sqlTable,List<SqlField> sqlFieldNew) throws Exception{
		StringBuilder sBuilder = new StringBuilder();
		StringBuilder mainBuilder = new StringBuilder();
		StringBuilder indexBuilder = new StringBuilder();
		sBuilder.append(" CREATE TABLE `"+sqlTable.getTableSqlName()+"`( ");
		int judgeNum = 0 ;
		//DB2:3
		if(dbflag!=3){
			mainBuilder.append(",PRIMARY KEY (");
		}
		for (int i = 0; i < sqlFieldNew.size(); i++) {
			SqlField sqlField = sqlFieldNew.get(i);
			if(sqlField.getFieldLen() != null && !sqlField.getFieldLen().equals("")){
				//mysql 小数点位数
				if((dbflag==1 || dbflag==3)&& (sqlField.getFieldType().toString().equals("1") || sqlField.getFieldType().toString().equals("8"))){
					sBuilder.append(" "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+","+sqlField.getFieldDigit()+")");
				}else{
					//oracle sqlserver 时间类型
					if(dbflag==1 || dbflag==2){
						if(sqlField.getFieldType().toString().equals("3") || sqlField.getFieldType().toString().equals("4")|| sqlField.getFieldType().toString().equals("5")){
							sBuilder.append(" "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType()));
						}else{
							sBuilder.append(" "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+")");
						}
					}else if(dbflag==5){
						if(sqlField.getFieldType().toString().equals("7")||sqlField.getFieldType().toString().equals("6")||sqlField.getFieldType().toString().equals("3")||sqlField.getFieldType().toString().equals("4")|| sqlField.getFieldType().toString().equals("5")){
							sBuilder.append(" "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType()));
						}else{
							sBuilder.append(" "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+")");
						}
					}else if(dbflag==3){
						if(sqlField.getFieldType().toString().equals("7")||sqlField.getFieldType().toString().equals("6")||sqlField.getFieldType().toString().equals("3")){
							sBuilder.append(" "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType()));
						}else{
							sBuilder.append(" "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+")");
						}
					}
				}
			}else{
				sBuilder.append(" "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType()));
			}
			//Oracle ,SqlServer
			if(dbflag==2 || dbflag==5){
				//主键
				if(sqlField.getFieldKey().equals("1")){
					if(i==sqlFieldNew.size()-1){
						sBuilder.append(" NOT NULL");
					}else{
						sBuilder.append(" NOT NULL,");
					}
//					mainBuilder.append(",PRIMARY KEY ("+sqlField.getFieldSqlName()+")");
					if(judgeNum==0){
						mainBuilder.append(sqlField.getFieldSqlName());
					}else{
						mainBuilder.append(","+sqlField.getFieldSqlName());
					}
					judgeNum++;
				}else{ 
					if(sqlField.getIsNull().equals("0")){
						if(i==sqlFieldNew.size()-1){
							sBuilder.append(" NOT NULL");
						}else{
							sBuilder.append(" NOT NULL,");
						}
					}else{
						if(i==sqlFieldNew.size()-1){
							sBuilder.append(" DEFAULT NULL");
						}else{
							sBuilder.append(" DEFAULT NULL,");
						}
					}
				}
			}else if(dbflag==1){//mysql
				//主键
				if(sqlField.getFieldKey().equals("1")){
					if(i==sqlFieldNew.size()-1){
						sBuilder.append(" NOT NULL COMMENT '"+sqlField.getFieldName()+"'");
					}else{
						sBuilder.append(" NOT NULL COMMENT '"+sqlField.getFieldName()+"',");
					}
					if(judgeNum==0){
						mainBuilder.append(sqlField.getFieldSqlName());
					}else{
						mainBuilder.append(","+sqlField.getFieldSqlName());
					}
					judgeNum++;
				}else{
					if(sqlField.getIsNull().equals("0")){
						if(i==sqlFieldNew.size()-1){
							sBuilder.append(" NOT NULL COMMENT '"+sqlField.getFieldName()+"'");
						}else{
							sBuilder.append(" NOT NULL COMMENT '"+sqlField.getFieldName()+"',");
						}
					}else{
						if(i==sqlFieldNew.size()-1){
							sBuilder.append(" DEFAULT NULL COMMENT '"+sqlField.getFieldName()+"'");
						}else{
							sBuilder.append(" DEFAULT NULL COMMENT '"+sqlField.getFieldName()+"',");
						}
					}
				}
			}else if(dbflag==3){//db2
				//主键
				if(sqlField.getFieldKey().equals("1")){
					if(i==sqlFieldNew.size()-1){
						sBuilder.append(" NOT NULL,CONSTRAINT "+sqlTable.getTableSqlName()+"_"+sqlField.getFieldSqlName()+" PRIMARY KEY("+sqlField.getFieldSqlName()+")");
					}else{
						sBuilder.append(" NOT NULL,CONSTRAINT "+sqlTable.getTableSqlName()+"_"+sqlField.getFieldSqlName()+" PRIMARY KEY("+sqlField.getFieldSqlName()+"),");
					}
//					if(judgeNum==0){
//						mainBuilder.append(sqlField.getFieldSqlName());
//					}else{
//						mainBuilder.append(","+sqlField.getFieldSqlName());
//					}
					judgeNum++;
				}else{ 
					if(i==sqlFieldNew.size()-1){
						sBuilder.append(" ");
					}else{
						sBuilder.append(" ,");
					}
				}
			}
			//mysql 索引
			if(dbflag==1 && sqlField.getSortIndex() != null && sqlField.getSortIndex().equals("1") ){
				//非主键普通索引
				indexBuilder.append(", KEY "+sqlField.getFieldSqlName()+" ("+sqlField.getFieldSqlName()+") USING BTREE");
			}
		}
		if(dbflag!=3){
			mainBuilder.append(")");
		}
		if(judgeNum!=0 && dbflag !=5){
			sBuilder.append(mainBuilder.toString());
		}
		//判断mysql是否有索引添加
		if(!sBuilder.toString().equals("") && !indexBuilder.toString().equals("")){
			sBuilder.append(indexBuilder.toString());
		}
		//非mysql
		if(dbflag!=1){
			sBuilder.append(" )");
		}else{//mysql
			sBuilder.append(" ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ");
		}
		return sBuilder.toString();
	}
	/**
	 * 新建表后添加描述等信息（非mysql）
	 * @param sqlDataRes
	 * @param sqlDataResType
	 * @param sqlTable
	 * @param sqlFieldNew
	 */
	public List<String> modifyDescription(SqlTable sqlTable,List<SqlField> sqlFieldNew){
		StringBuilder mainBuilder = new StringBuilder();
		int judgeNum = 0 ;
		List<String> modifyList = new ArrayList<String>();
		for (SqlField sqlField : sqlFieldNew) {
			//Oracle,DB2 描述信息
			if(dbflag==2 || dbflag==3){
				modifyList.add("comment on column "+sqlTable.getTableSqlName()+"."+sqlField.getFieldSqlName()+" is '"+sqlField.getFieldName()+"'");
			}else if(dbflag==5){//SqlServer 描述信息
				modifyList.add("sp_addextendedproperty N'MS_Description', N'"+sqlField.getFieldName()+"', N'user', N'dbo', N'table', N'"+sqlTable.getTableSqlName()+"', N'column', N'"+sqlField.getFieldSqlName()+"';");
			}
			//非mysql索引添加
			if(dbflag==2 || dbflag==5){
				if(sqlField.getSortIndex() != null && sqlField.getSortIndex().toString().equals("1") && !sqlField.getFieldKey().toString().equals("1")){
					modifyList.add("create index "+sqlField.getFieldSqlName()+" on "+sqlTable.getTableSqlName()+"("+sqlField.getFieldSqlName()+")");
				}
			}else if(dbflag==3){
				// DB2 加除了主键以外的索引
				if(sqlField.getSortIndex() != null && sqlField.getSortIndex().toString().equals("1") && !sqlField.getFieldKey().toString().equals("1")){
					modifyList.add("create index "+sqlTable.getTableSqlName()+"_"+sqlField.getFieldSqlName()+" on "+sqlTable.getTableSqlName()+
							"("+sqlField.getFieldSqlName()+") COLLECT SAMPLED DETAILED STATISTICS COMPRESS NO ALLOW REVERSE SCANS");
				}
			}
			//主键处理
			if(dbflag==5){
				//主键
				if(sqlField.getFieldKey().equals("1")){
					if(judgeNum==0){
						mainBuilder.append(sqlField.getFieldSqlName());
					}else{
						mainBuilder.append(","+sqlField.getFieldSqlName());
					}
					judgeNum++;
				}
			}
		}
		if(judgeNum != 0){
			modifyList.add("alter table "+sqlTable.getTableSqlName()+" add constraint pk_name"+sqlTable.getTableSqlName()+" primary key( "+mainBuilder.toString()+")");
		}
		return modifyList;
	}
	/**
	 * 1.修改表名
	 * @param sqlName
	 * 新表名
	 * @param modifyTableName
	 * 旧表名
	 * @return
	 */
	public String modifyName(String sqlName,String modifyTableName){
		String modifyName ="";
		if(!StringUtils.isBlank(modifyTableName)){
			if(dbflag==1){
				//mysql
				modifyName = "alter table `"+modifyTableName+"` rename to `"+sqlName+"`";
//				modifyName = "alter table "+modifyTableName+" rename to "+sqlName;
			}else if(dbflag==2 ){
				modifyName = "alter table "+modifyTableName+" rename to "+sqlName;
			}else if(dbflag==5){
				modifyName = "EXEC sp_rename '"+modifyTableName+"', '"+sqlName+"'";
			}else if(dbflag==3){
				modifyName = "rename table "+modifyTableName+" to "+sqlName;
			}
		}
		return modifyName;
	}
	/**
	 * 
	 * @param deleteSortIndex
	 * 需要删除的索引
	 * @param modifyTableName
	 * 老的表名称oracle使用
	 * @param schemaName
	 * oracle的Schema名称
	 * @param sqlName
	 * 表名称
	 * @return 
	 */
	public List<String> deleteIndex(List<String> deleteSortIndex,String modifyTableName,String schemaName,String sqlName){
		List<String> modifyList = new ArrayList<String>();
		if(deleteSortIndex != null && !deleteSortIndex.isEmpty()){
			for (String string : deleteSortIndex) {
				if(dbflag==2){
//					if(!StringUtils.isBlank(modifyTableName)){
//						modifyList.add("drop index "+userName+"."+modifyTableName+string);
//					}else{
//						modifyList.add("drop index "+userName+"."+sqlName+string);
//					}
					modifyList.add("drop index "+schemaName+"."+string);
				}else if(dbflag==3){
//					modifyList.add("drop index "+ sqlName+"_"+string);
					modifyList.add("drop index "+string);
				}else if(dbflag==1){
					modifyList.add("drop index "+string+" on `"+sqlName+"`");
				}else{
					modifyList.add("drop index "+string+" on "+sqlName);
				}
			}
		}
		return modifyList;
	}
	/**
	 * 新增表字段
	 * @param sqlFieldNew
	 * 新增字段集合
	 * @param typeMap
	 * 字段类型
	 * @param sqlName
	 * 表名
	 * @return
	 */
	public List<String> addField(List<SqlField> sqlFieldNew,HashMap<String,Object> typeMap,String sqlName){
		List<String> modifyList = new ArrayList<String>();
		if(sqlFieldNew != null && !sqlFieldNew.isEmpty()){
			for (SqlField sqlField : sqlFieldNew) {
				if(dbflag==1){
					if(sqlField.getFieldDigit() != null && !sqlField.getFieldDigit().equals("")){
						if(sqlField.getIsNull().equals("0")){
							modifyList.add("alter table `"+sqlName+"` add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+","+sqlField.getFieldDigit()+") NOT NULL)");
						}else{
							modifyList.add("alter table `"+sqlName+"` add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+","+sqlField.getFieldDigit()+") DEFAULT NULL)");
						}
					}else{
						if(sqlField.getFieldKey().equals("1")){
							modifyList.add("alter table `"+sqlName+"` add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") NOT NULL)");
						}else{
							if(sqlField.getIsNull().equals("0")){
									if(sqlField.getFieldType().toString().equals("4") || sqlField.getFieldType().toString().equals("3")|| sqlField.getFieldType().toString().equals("5")){
										modifyList.add("alter table `"+sqlName+"` add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+" NOT NULL)");
									}else{
										modifyList.add("alter table `"+sqlName+"` add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") NOT NULL)");
									}	
								}else{
									if(sqlField.getFieldType().toString().equals("4") || sqlField.getFieldType().toString().equals("3")|| sqlField.getFieldType().toString().equals("5")){
										modifyList.add("alter table `"+sqlName+"` add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+" DEFAULT NULL)");
									}else{
										modifyList.add("alter table `"+sqlName+"` add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") DEFAULT NULL)");
									}
								}
						}
					}
				}else if(dbflag==2){
					if(sqlField.getFieldType().toString().equals("3") || sqlField.getFieldType().toString().equals("4")|| sqlField.getFieldType().toString().equals("5")){
						if(sqlField.getIsNull().equals("0")){
								modifyList.add("alter table "+sqlName+" add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+" NOT NULL)");	
							}else{
								modifyList.add("alter table "+sqlName+" add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+" DEFAULT NULL)");
							}
					}else{
						if(sqlField.getFieldKey().equals("1")){
							modifyList.add("alter table "+sqlName+" add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") NOT NULL)");
						}else{
							if(sqlField.getIsNull().equals("0")){
									modifyList.add("alter table "+sqlName+" add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") NOT NULL)");	
								}else{
									modifyList.add("alter table "+sqlName+" add ("+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") DEFAULT NULL)");
								}
						}
					}
				}else if(dbflag==5){
					if(sqlField.getFieldKey().equals("1")){
						if(sqlField.getFieldType().toString().equals("9") || sqlField.getFieldType().toString().equals("1") || sqlField.getFieldType().toString().equals("7") || sqlField.getFieldType().toString().equals("3") || sqlField.getFieldType().toString().equals("6") || sqlField.getFieldType().toString().equals("4")|| sqlField.getFieldType().toString().equals("5")){
							modifyList.add("alter table "+sqlName+" add "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+" NOT NULL");
						}else{
							modifyList.add("alter table "+sqlName+" add "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") NOT NULL");
						}
					}else{
						if(sqlField.getIsNull().equals("0")){
								if(sqlField.getFieldType().toString().equals("9") || sqlField.getFieldType().toString().equals("1") || sqlField.getFieldType().toString().equals("3") || sqlField.getFieldType().toString().equals("6")||sqlField.getFieldType().toString().equals("7")|| sqlField.getFieldType().toString().equals("4")|| sqlField.getFieldType().toString().equals("5")){
									modifyList.add("alter table "+sqlName+" add "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+" NOT NULL");
								}else{
									modifyList.add("alter table "+sqlName+" add "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") NOT NULL");
								}	
							}else{
								if(sqlField.getFieldType().toString().equals("9") || sqlField.getFieldType().toString().equals("1") || sqlField.getFieldType().toString().equals("3") || sqlField.getFieldType().toString().equals("6")||sqlField.getFieldType().toString().equals("7")|| sqlField.getFieldType().toString().equals("4")|| sqlField.getFieldType().toString().equals("5")){
									modifyList.add("alter table "+sqlName+" add "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+" NULL");
								}else{
									modifyList.add("alter table "+sqlName+" add "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") NULL");
								}
							}
					}
					
				}else if(dbflag==3){
					if(sqlField.getIsNull().equals("0")){
							if(sqlField.getFieldType().toString().equals("3")||sqlField.getFieldType().toString().equals("6")||sqlField.getFieldType().toString().equals("7")||sqlField.getFieldType().toString().equals("9")){//timestamp int bigint类型
								modifyList.add("alter table "+sqlName+" add column "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+" NOT NULL " );
							}else if(sqlField.getFieldType().toString().equals("1")){//decimal类型
								modifyList.add("alter table "+sqlName+" add column "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+
										"("+sqlField.getFieldLen()+","+sqlField.getFieldDigit()+") NOT NULL ");
							}else{
								modifyList.add("alter table "+sqlName+" add column "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+
										"("+sqlField.getFieldLen()+") NOT NULL ");
							}	
						}else{
							if(sqlField.getFieldType().toString().equals("3")||sqlField.getFieldType().toString().equals("6")||sqlField.getFieldType().toString().equals("7")||sqlField.getFieldType().toString().equals("9")){//timestamp int bigint类型
								modifyList.add("alter table "+sqlName+" add column "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType()));
							}else if(sqlField.getFieldType().toString().equals("1")){//decimal类型
								modifyList.add("alter table "+sqlName+" add column "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+
										"("+sqlField.getFieldLen()+","+sqlField.getFieldDigit()+")");
							}else{
								modifyList.add("alter table "+sqlName+" add column "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+
										"("+sqlField.getFieldLen()+")");
							}
						}
						modifyList.add(" comment on column "+ sqlName+"."+sqlField.getFieldSqlName()+" is '"+sqlField.getFieldName()+"'");
				}
			}
		}
		return modifyList;
	}
	

	
	/**
	 * 修改字段名称与类型
	 * @param sqlFieldAlert
	 * 修改字段集合
	 * @param typeMap
	 * 字段类型
	 * @param sqlName
	 * 表名称
	 * @return
	 * @throws BaseException 
	 */
	public List<String> modifyField(List<HashMap<String,Object>> sqlFieldAlert,HashMap<String,Object> typeMap,String sqlName){
		List<String> modifyList = new ArrayList<String>();
		if(sqlFieldAlert != null && !sqlFieldAlert.isEmpty()){
			if(dbflag==2){
				for (HashMap<String,Object> sqlField : sqlFieldAlert) {
					//重命名
					if(!sqlField.get("oldFieldSqlName").equals(sqlField.get("fieldSqlName"))){
						modifyList.add("alter table "+sqlName+" rename column "+sqlField.get("oldFieldSqlName")+" to "+sqlField.get("fieldSqlName"));
					}
					if(sqlField.get("fieldKey").equals("1")){//是否主键
						if(sqlField.get("fieldType").equals("3") || sqlField.get("fieldType").equals("4")|| sqlField.get("fieldType").equals("5")){
							modifyList.add("alter table "+sqlName+" modify ("+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+" NOT NULL)");
						}else{
							modifyList.add("alter table "+sqlName+" modify ("+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+") NOT NULL)");
						}
					}else{
						if(sqlField.get("isNull").equals("0")){
							if(sqlField.get("fieldType").equals("3") || sqlField.get("fieldType").equals("4")|| sqlField.get("fieldType").equals("5")){
								modifyList.add("alter table "+sqlName+" modify ("+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+" NOT NULL)");
							}else{
								modifyList.add("alter table "+sqlName+" modify ("+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+") NOT NULL)");
							}
						}else{
							if(sqlField.get("fieldType").equals("3") || sqlField.get("fieldType").equals("4")|| sqlField.get("fieldType").equals("5")){
								modifyList.add("alter table "+sqlName+" modify ("+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+" DEFAULT NULL)");
							}else{
								modifyList.add("alter table "+sqlName+" modify ("+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+") DEFAULT NULL)");
							}
						}
					}
				}
			}else if(dbflag==1){
				for (HashMap<String,Object> sqlField : sqlFieldAlert) {
					if(sqlField.get("fieldKey").equals("1")){
						if(sqlField.get("fieldType").equals("3") || sqlField.get("fieldType").equals("4")|| sqlField.get("fieldType").equals("5")){
							modifyList.add("alter table `"+sqlName+"` change "+sqlField.get("oldFieldSqlName")+" "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+" NOT NULL");
						}else if(sqlField.get("fieldType").equals("1")){
							modifyList.add("alter table `"+sqlName+"` change "+sqlField.get("oldFieldSqlName")+" "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+","+sqlField.get("fieldDigit")+") NOT NULL");
						}else{
							modifyList.add("alter table `"+sqlName+"` change "+sqlField.get("oldFieldSqlName")+" "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+") NOT NULL");
						}
					}else{
						if(sqlField.get("isNull").equals("0")){
							if(sqlField.get("fieldType").equals("3") || sqlField.get("fieldType").equals("4")|| sqlField.get("fieldType").equals("5")){
								modifyList.add("alter table `"+sqlName+"` change "+sqlField.get("oldFieldSqlName")+" "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+" NOT NULL");
							}else if(sqlField.get("fieldType").equals("1")||sqlField.get("fieldType").equals("8")){
								modifyList.add("alter table `"+sqlName+"` change "+sqlField.get("oldFieldSqlName")+" "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+","+sqlField.get("fieldDigit")+") NOT NULL");
							}else{
								modifyList.add("alter table `"+sqlName+"` change "+sqlField.get("oldFieldSqlName")+" "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+") NOT NULL");
							}
						}else{
							if(sqlField.get("fieldType").equals("3") || sqlField.get("fieldType").equals("4")|| sqlField.get("fieldType").equals("5")){
								modifyList.add("alter table `"+sqlName+"` change "+sqlField.get("oldFieldSqlName")+" "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+" DEFAULT NULL");
							}else if(sqlField.get("fieldType").equals("1")||sqlField.get("fieldType").equals("8")){
								modifyList.add("alter table `"+sqlName+"` change "+sqlField.get("oldFieldSqlName")+" "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+","+sqlField.get("fieldDigit")+") DEFAULT NULL");
							}else{
								modifyList.add("alter table `"+sqlName+"` change "+sqlField.get("oldFieldSqlName")+" "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+") DEFAULT NULL");
							}
						}
					}
				}
			}else if(dbflag==5){
				for (HashMap<String,Object> sqlField : sqlFieldAlert) {//sp_rename 'sql_table_orcl.sss', 'abc','column'
					modifyList.add("sp_rename '"+sqlName+"."+sqlField.get("oldFieldSqlName")+"', '"+sqlField.get("fieldSqlName")+"','column'");
				}
				for(HashMap<String,Object> sqlField : sqlFieldAlert){
					if(sqlField.get("fieldType").toString().equals("9") || sqlField.get("fieldType").equals("3") || sqlField.get("fieldType").equals("1")|| sqlField.get("fieldType").equals("6")|| sqlField.get("fieldType").equals("7") || sqlField.get("fieldType").equals("4")|| sqlField.get("fieldType").equals("5")){
						if(sqlField.get("fieldKey").equals("1")){
							modifyList.add("alter table "+sqlName+" alter column "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+" NOT NULL");
						}else{
							if(sqlField.get("isNull").equals("0")){
								modifyList.add("alter table "+sqlName+" alter column "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+" NOT NULL");
							}else{
								modifyList.add("alter table "+sqlName+" alter column "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+" NULL");
							}
						}
					}else{
						if(sqlField.get("fieldKey").equals("1")){
							modifyList.add("alter table "+sqlName+" alter column "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+") NOT NULL");
						}else{
							if(sqlField.get("isNull").equals("0")){
								modifyList.add("alter table "+sqlName+" alter column "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+") NOT NULL");
							}else{
								modifyList.add("alter table "+sqlName+" alter column "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+") NOT NULL");
							}
						}
					}
				}
			}else  if(dbflag==3){
				for (HashMap<String,Object> sqlField : sqlFieldAlert) {
					// DB2    CALL SYSPROC.ADMIN_CMD('REORG  table DB2ADMIN.D')alter table db2tabletest DROP COLUMN four
					modifyList.add("alter table "+sqlName+" drop column "+sqlField.get("oldFieldSqlName"));
					modifyList.add("CALL SYSPROC.ADMIN_CMD('REORG table " + sqlName+"')");
					if(sqlField.get("fieldType").equals("1")){
						modifyList.add("alter table "+sqlName+" add column "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+","+sqlField.get("fieldDigit")+")");
					}else if(sqlField.get("fieldType").equals("2")||sqlField.get("fieldType").equals("4")||sqlField.get("fieldType").equals("5")){
						modifyList.add("alter table "+sqlName+" add column "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType"))+"("+sqlField.get("fieldLen")+")");
					}else if(sqlField.get("fieldType").equals("3")||sqlField.get("fieldType").equals("6")||sqlField.get("fieldType").equals("7")||sqlField.get("fieldType").equals("9")){
					modifyList.add("alter table "+sqlName+" add column "+sqlField.get("fieldSqlName")+" "+typeMap.get(sqlField.get("fieldType")));
					}
					modifyList.add("comment on column "+sqlName+"."+sqlField.get("fieldSqlName")+" is '"+sqlField.get("fieldName")+"'");
					//如果修改的字段是主键
					if(sqlField.get("isNull").equals("0")){
						if(sqlField.get("fieldKey").equals("1")){
							//alter table DB2ONE alter column two set not null
							modifyList.add("alter table "+sqlName+" alter column "+sqlField.get("fieldSqlName")+" set not null");
							modifyList.add("CALL SYSPROC.ADMIN_CMD('REORG  table "+sqlName+"') ");
							modifyList.add("alter table "+sqlName+" add constraint "+sqlField.get("fieldSqlName")+" primary key("+sqlField.get("fieldSqlName")+")");
							//ALTER TABLE DB2ONE ADD constraint twotwo primary key (twotwo)
						}else{
							modifyList.add("alter table "+sqlName+" alter column "+sqlField.get("fieldSqlName")+" set not null");
						}
					}
					//如果修改的字段是索引
					if(sqlField.get("oldSortIndex").equals("1")){
						modifyList.add("create index "+sqlName+"_"+sqlField.get("fieldSqlName")+" on "+sqlName+
								"("+sqlField.get("fieldSqlName")+") COLLECT SAMPLED DETAILED STATISTICS COMPRESS NO ALLOW REVERSE SCANS");
					
					}
				}
			}
		}
		return modifyList;
	}
	/**
	 * 删除字段
	 * @param sqlFieldIdes
	 * 删除索引集合
	 * @param sqlName
	 * 表名称
	 * @return
	 */
	public List<String> dropField(List<SqlField> sqlFieldIdes,String sqlName){
		List<String> modifyList = new ArrayList<String>();
		if(sqlFieldIdes != null && !sqlFieldIdes.isEmpty()){
			for (SqlField sqlField : sqlFieldIdes) {
				if(dbflag==1){
					modifyList.add("alter table `"+sqlName+"` DROP COLUMN "+sqlField.getFieldSqlName());
				}else{
					modifyList.add("alter table "+sqlName+" DROP COLUMN "+sqlField.getFieldSqlName());
				}
			}
		}
		return modifyList;
	}
	/**
	 * 新增索引
	 * @param newSortIndex
	 * 新增索引集合
	 * @param sqlName
	 * 表名称
	 * @return
	 * @throws BaseException 
	 */
	public List<String> addIndex(List<String> newSortIndex,String sqlName){
		List<String> modifyList = new ArrayList<String>();
		if(newSortIndex != null && !newSortIndex.isEmpty()){
			if(dbflag==2){
				for (String string : newSortIndex) {
					modifyList.add("create index "+sqlName+string+" on "+sqlName+"("+string+")");
				}
			}else if(dbflag==1){
				for (String string : newSortIndex) {
					modifyList.add("alter table `"+sqlName+"` add index "+string+" ("+string+")");
				}
			}else if(dbflag==5){
				for (String string : newSortIndex) {
					modifyList.add("create index "+string+" on "+sqlName+"("+string+")");
				}
			}else if(dbflag==3){
				for (String string : newSortIndex) {
					modifyList.add("create index "+sqlName+"_"+string+" on "+sqlName+"("+string+") "
							+ "COLLECT SAMPLED DETAILED STATISTICS COMPRESS NO ALLOW REVERSE SCANS");
				}
			}
		}
		return modifyList;
	}
	
	
	/**
	 * 新增主键
	 * @param sqlFieldAlert
	 * 修改字段集合
	 * @param typeMap
	 * 字段类型
	 * @param sqlName
	 * 表名称
	 * @return
	 * @throws BaseException 
	 */
	public List<String> createPrimarykey(List<SqlField> newPrimarykey,HashMap<String,Object> typeMap,String sqlName,List<SqlField> primarykey){
		List<String> modifyList = new ArrayList<String>();
		StringBuilder sBuilder = new StringBuilder();
		if(newPrimarykey != null && !newPrimarykey.isEmpty()){
			for (int i = 0; i < newPrimarykey.size(); i++) {
				SqlField sqlField = newPrimarykey.get(i);
				if(sqlField != null ){
					if( i == 0){
						sBuilder.append(sqlField.getFieldSqlName());
					}else{
						sBuilder.append(","+sqlField.getFieldSqlName());
					}
					if(dbflag==5){
						if(sqlField.getFieldType().equals("3") || sqlField.getFieldType().equals("1")|| sqlField.getFieldType().equals("6")|| sqlField.getFieldType().equals("7")){
							modifyList.add("alter table "+sqlName+" alter column "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+" NOT NULL");
						}else{
							modifyList.add("alter table "+sqlName+" alter column "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") NOT NULL");
						}
					}
				}
			}
			if(primarykey != null && !primarykey.isEmpty()){
				for (int i = 0; i < primarykey.size(); i++) {
					SqlField sqlField = primarykey.get(i);
					if(sqlField != null ){
						if( i == 0){
							if(StringUtils.isNotBlank(sBuilder.toString())){
								sBuilder.append(","+sqlField.getFieldSqlName());
							}else{
								sBuilder.append(sqlField.getFieldSqlName());
							}
						}else{
							sBuilder.append(","+sqlField.getFieldSqlName());
						}
						if(dbflag==5){
							if(sqlField.getFieldType().equals("3") || sqlField.getFieldType().equals("1")|| sqlField.getFieldType().equals("6")|| sqlField.getFieldType().equals("7")){
								modifyList.add("alter table "+sqlName+" alter column "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+" NOT NULL");
							}else{
								modifyList.add("alter table "+sqlName+" alter column "+sqlField.getFieldSqlName()+" "+typeMap.get(sqlField.getFieldType())+"("+sqlField.getFieldLen()+") NOT NULL");
							}
						}
					}
				}
			}
			if(sBuilder != null && !sBuilder.toString().equals("")){
				if(dbflag==2){
					//alter table 表名add constraint pk_tab2 primary key (sno,name);
//					modifyList.add("alter table "+sqlName+"  DROP PRIMARY KEY");
					modifyList.add("alter table "+sqlName+" add constraint unionkeyname"+sqlName+" primary key ( "+sBuilder.toString()+")");
				}else if(dbflag==1){
					//Alter table tb add primary key(id);
//					modifyList.add("alter table "+sqlName+"  DROP PRIMARY KEY");
					modifyList.add("alter table `"+sqlName+"` add primary key ("+sBuilder.toString()+")");
				}else if(dbflag==5){
					//alter table table_name add primary key(字段1，字段2)   alter table sql_table_ser drop constraint pk_name
//					modifyList.add("alter table "+sqlName+"  DROP constraint pk_name");
					modifyList.add("alter table "+sqlName+" add constraint pk_name"+sqlName+" primary key( "+sBuilder.toString()+")");
				}else if(dbflag==3){
					modifyList.add("alter table "+sqlName+" alter column "+sBuilder.toString()+" set not null");
					modifyList.add("CALL SYSPROC.ADMIN_CMD('REORG  table "+sqlName+"')");
					modifyList.add("alter table "+sqlName+" add constraint "+ sBuilder.toString() +" primary key ("+sBuilder.toString()+")");
				}
			}
		}
		return modifyList;
	}
	
	/**
	 * 删除主键
	 * @param sqlFieldAlert
	 * 修改字段集合
	 * @param typeMap
	 * 字段类型
	 * @param sqlName
	 * 表名称
	 * @param modifyTableName
	 * 修改之前的表名称
	 * @return
	 * @throws BaseException 
	 */
	public List<String> deletePrimarykey(List<String> deletePrimarykey,HashMap<String,Object> typeMap,String modifyTableName,String sqlName) {
		List<String> modifyList = new ArrayList<String>();
		if(deletePrimarykey != null && !deletePrimarykey.isEmpty()){
			if(dbflag==2){
//				modifyList.add("alter table "+sqlName+" DROP PRIMARY KEY");
				for (String string : deletePrimarykey) {
					modifyList.add("alter table "+sqlName+" DROP constraint "+string);
				}
			}else if(dbflag==1){
				modifyList.add("alter table `"+sqlName+"` DROP PRIMARY KEY");
			}else if(dbflag==5){
//				if(modifyTableName != null && !modifyTableName.equals("")){
//					modifyList.add("alter table "+sqlName+" DROP constraint pk_name"+modifyTableName);
//				}else{
//					modifyList.add("alter table "+sqlName+" DROP constraint pk_name"+sqlName);
//				}
				for (String string : deletePrimarykey) {
					modifyList.add("alter table "+sqlName+" DROP constraint "+string);
				}
			}else  if(dbflag==3){
				modifyList.add("alter table "+sqlName+" DROP PRIMARY KEY");
			}
		}
		return modifyList;
	}
	/**
	 * 添加外键
	 * @param req
	 * @return
	 */
	public String addForeignkey(HashMap<String, Object> req){
		String foreignkey = "";
		if(dbflag == 1){
			foreignkey = "alter table `"+req.get("negativeSqName")+"` add constraint "+req.get("joinField")+req.get("negativeSqName")+" foreign key("+req.get("joinField")+") references `"+req.get("mainSqlName")+"` ("+req.get("mainField")+")";
		}else{
			foreignkey = "alter table "+req.get("negativeSqName")+" add constraint "+req.get("joinField")+req.get("negativeSqName")+" foreign key("+req.get("joinField")+") references "+req.get("mainSqlName")+"("+req.get("mainField")+")";
		}
//		}else{
//			foreignkey = "alter table "+req.get("negativeSqName")+" add foreign key("+req.get("joinField")+") references "+req.get("mainSqlName")+"("+req.get("mainField")+")";
//		}
		
		return foreignkey;
	}
	
	/**
	 * 删除外键
	 * @param req
	 * @throws BaseException 
	 * @returnalter  
	 */
	public String deleteForeignkey(HashMap<String, Object> req){
		String foreignkey = "";
		if(dbflag==2){
			foreignkey = "alter table "+req.get("sqName")+" drop constraint "+req.get("joinField");
		}else if(dbflag==1){
			foreignkey = "alter table `"+req.get("sqName")+"` drop foreign key "+req.get("joinField");
		}else if(dbflag==5){
			foreignkey = "alter table "+req.get("sqName")+" drop constraint "+req.get("joinField");
		}else  if(dbflag==3){
			foreignkey = "alter table "+req.get("sqName")+" drop foreign key "+req.get("joinField");
		}
		return foreignkey;
	}
	/**
	 * 获取最大值
	 * @param fname
	 * @return
	 */
	public String getMax(String fname){
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		default:
			strvalue.append("MAX('"+fname+"') ,");
		}
		return strvalue.toString();
	}
	/**
	 * 获取最小值
	 * @param fname
	 * @return
	 */
	public String getMin(String fname){
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		default:
			strvalue.append("MIN('"+fname+"') ,");
		}
		return strvalue.toString();
	}
	/**
	 * 获取count
	 * @param fname
	 * @return
	 */
	public String getCount(String fname){
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		default:
			if(fname.trim().equals("*") || fname.trim().equals("1")){
				strvalue.append("COUNT(*)");
			}else{
				strvalue.append("COUNT('"+fname+"') ,");
			}
		}
		return strvalue.toString();
	}
	/**
	 * 获取count
	 * @param fname
	 * @return
	 */
	public String getDistinct(String fname){
		StringBuffer strvalue = new StringBuffer();
		switch (dbflag)
		{
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		default:
			strvalue.append("DISTINCT('"+fname+"') ,");
		}
		return strvalue.toString();
	}
	
	/**
	 * 
	 * @param req
	 * @param resp
	 * @param tableName
	 * @param conditiones
	 * @param groupes
	 * @param havinges
	 * @param sortes
	 * @param dbflag
	 * @return
	 */
	public String pageModel(HashMap<String,Object> req,Map<String,Object> resp,String tableName,Map<String,Object> conditiones,List<String> sortes,int dbflag){
		StringBuffer sBufeer = new StringBuffer();
		int start = Integer.parseInt(req.get("pageNum").toString())-1;
		int size = Integer.parseInt(req.get("pageSize").toString());
		String condition =conditiones(conditiones);
		switch (dbflag)
		{
		case 1:
			sBufeer.append("SELECT ");
	    	sBufeer.append(resp(resp,dbflag));
	    	sBufeer.append(" FROM "+tableName+" WHERE ");
	    	if(StringUtils.isBlank(condition)){
	    		sBufeer.append(req.get("id")+" > "+start*size);
	    	}else{
	    		sBufeer.append(condition+" AND "+req.get("id")+" > "+start*size);
	    	}
	    	sBufeer.append(sortes(sortes));
	    	sBufeer.append("  LIMIT "+size);
	    	break;
		case 2://SELECT * FROM	(	SELECT temp.*, ROWNUM RN FROM	(SELECT * FROM 表名) temp	WHERE	ROWNUM <=	END (page * pagesize)	)WHERE RN >= START (page - 1 * pagesize + 1)
			sBufeer.append("SELECT ");
	    	sBufeer.append(resp(resp,dbflag));
	    	sBufeer.append(" FROM (SELECT temp.*, ROWNUM RN FROM (SELECT * FROM "+tableName);
	    	if(StringUtils.isBlank(condition)){
	    		sBufeer.append(sortes(sortes)+") temp WHERE ROWNUM <=("+(start+1)*size+")"+sortes(sortes)+") WHERE RN >= "+start * size + 1);
	    	}else{
	    		sBufeer.append(" WHERE "+condition+sortes(sortes)+") temp WHERE "+condition+" AND ROWNUM <=("+(start+1)*size+")"+sortes(sortes)+") WHERE RN >= "+start * size + 1);
	    	}
	    	break;
		case 3:
//			SELECT * FROM (SELECT ROW_NUMBER() OVER(ORDER BY column1 DESC) AS ROWNUM,column2 from tablethreeDB2) a 
//			WHERE ROWNUM > 5 and ROWNUM <=8
//			row_number() 作为人为的添加一列作为给每一条数据进行编号
//			select row_number() over ( order by date desc ) as r,e.* from emp e 
//			over()中是实现排序的字段和方式，date是字段名，desc是方式，都可以修改，但是over()为必须写的，不写会报错
//			as r是为row_number()这个列取的一个别名 
			sBufeer.append("SELECT * FROM (SELECT ROW_NUMBER() OVER("+sortes(sortes)+") AS ROWNUM, ");
			sBufeer.append(resp(resp,dbflag));
			sBufeer.append(" FROM "+tableName+" ");
			if(StringUtils.isBlank(condition)){
				sBufeer.append(" "+sortes(sortes)+") WHERE ROWNUM > "+start*size+" and ROWNUM <="+(start+1)*size+"");
	    	}else{
	    		sBufeer.append(" WHERE "+condition +" "+sortes(sortes)+") WHERE ROWNUM > "+start*size+" and ROWNUM <="+(start+1)*size+"");
	    	}
			break;
		case 4:
		case 5:
			//SELECT * FROM (SELECT TOP 50 * FROM (SELECT TOP 100050 * FROM infoTab ORDER BY ID ASC)TEMP1 ORDER BY ID DESC)TEMP2 ORDER BY ID ASC
			sBufeer.append("SELECT ");
			sBufeer.append(resp(resp,dbflag));
	    	sBufeer.append(" FROM (SELECT TOP "+size +" * FROM (SELECT TOP "+(start+1)*size+" * FROM "+tableName);
	    	if(StringUtils.isBlank(condition)){
	    		sBufeer.append(" "+sortes(sortes)+") temp "+sortes(sortes)+") temp2 "+sortes(sortes));
	    	}else{
	    		sBufeer.append(" WHERE "+condition+" "+sortes(sortes)+") temp "+sortes(sortes)+") temp2 "+sortes(sortes));
	    	}
	    	break;
		default:
			sBufeer.append("");
		}
		return sBufeer.toString();
	}
	/**
     * 返回字段组装
     * 
     */
	 public String resp(Map<String,Object> resp,int dbflag){
    	StringBuffer sBufeer = new StringBuffer();
    	if(resp != null && !resp.isEmpty()){
    		for (Entry<String, Object> entry : resp.entrySet()) {
    			if(!StringUtils.isBlank(entry.getValue().toString())){
    				if(entry.getKey().equals("default")){//'name','age','a.*'
        				sBufeer.append(" "+entry.getValue()+" ,");
        			}else if(entry.getKey().equals("all")){//value = *
        				sBufeer.append(" * ,");
        			}else if(entry.getKey().equals("max")){
        				String[] comCondition = entry.getValue().toString().split(",");
        				for(String s:comCondition){
        					sBufeer.append(Sql_switcher.getSqlFunc(dbflag).getMax(entry.getValue().toString()));
        				}
        			}else if(entry.getKey().equals("min")){
        				String[] comCondition = entry.getValue().toString().split(",");
        				for(String s:comCondition){
        					sBufeer.append(Sql_switcher.getSqlFunc(dbflag).getMin(entry.getValue().toString()));
        				}
        			}else if(entry.getKey().equals("distinct")){
        				sBufeer.append(Sql_switcher.getSqlFunc(dbflag).getDistinct(entry.getValue().toString()));
        			}else if(entry.getKey().equals("count")){
        				sBufeer.append(Sql_switcher.getSqlFunc(dbflag).getCount(entry.getValue().toString()));
        			}else if(entry.getKey().equals("sum")){//'name','age','a.*'
        				String[] comCondition = entry.getValue().toString().split(",");
        				for(String s:comCondition){
        					sBufeer.append(" "+entry.getValue()+" ,");
        				}
        			}
    			}
    		}
    		if(!StringUtils.isBlank(sBufeer.toString())){
    			String result = sBufeer.toString().trim();
    			return result.substring(0, result.length()-1);
    		}
    	}
		return "";
    }
	
	 /**
     * 查询条件组装
     * @param conditiones
     * @return
     */
    public String conditiones(Map<String,Object> conditiones){
    	StringBuffer sBufeer = new StringBuffer();
    	List<String> condition = new ArrayList<String>();
    	if(conditiones != null && !conditiones.isEmpty()){
    		for (Entry<String, Object> entry : conditiones.entrySet()) {
    			if(!StringUtils.isBlank(entry.getValue().toString())){
    				String[] comCondition = entry.getValue().toString().split(",");
    				if(entry.getKey().equals("default")){
    					for(String s:comCondition){
    						condition.add(s);
        				}
        			}else if(entry.getKey().equals("among")){
        				condition.add(comCondition[0] +" BETWEEN "+comCondition[1] +" AND "+comCondition[2]);
        			}else if(entry.getKey().equals("judge") ){
        				condition.add(comCondition[0] +" "+ comCondition[1] +" "+ comCondition[2]);
        			}else if(entry.getKey().equals("isNull")){
        				for(String s:comCondition){
        					condition.add(s +" IS NULL");
        				}
        			}else if(entry.getKey().equals("isNOTNull") ){
        				for(String s:comCondition){
        					condition.add(s +" IS NOT NULL");
        					condition.add(s +" <> ' '");
        				}
        			}else if(entry.getKey().equals("isLike")){
        				condition.add(comCondition[0]);
        			}else if(entry.getKey().equals("isIn")){
        				condition.add(comCondition[0]);
        			} 
    			}
			}
    		if(!condition.isEmpty()){
    			for (int i=0;i<condition.size();i++) {
					if(i==0){
						sBufeer.append(" "+condition.get(i)+" ");
					}else{
						sBufeer.append("AND "+condition.get(i)+" ");
					} 
				}
    			return sBufeer.toString();
    		}
		}
		return "";
    }

    /**
     * order by
     * @param sortes
     * @return
     */
    public String sortes(List<String> sortes){
    	StringBuffer sBufeer = new StringBuffer();
    	if(sortes != null && !sortes.isEmpty()){
    		for (int i = 0; i < sortes.size(); i++) {
				if(i==0){
					sBufeer.append(" ORDER BY "+sortes.get(i));
				}else{
					sBufeer.append(","+sortes.get(i));
				} 
			}
    	}
    	return sBufeer.toString();
    }
    /**
     * GROUP BY 
     * @param groupes
     * @return
     */
    public String groupes(List<String> groupes){
    	StringBuffer sBufeer = new StringBuffer();
    	if(groupes != null && !groupes.isEmpty()){
    		sBufeer.append(" GROUP BY ");
    		for (int i=0;i<groupes.size();i++) {
    			if(groupes.size() > 1){
    				if(i != 0)sBufeer.append(","+groupes.get(i));
    				sBufeer.append(groupes.get(i));
    			}else{
    				sBufeer.append(groupes.get(i));
    			}
			}
    		return sBufeer.toString();
    	}
		return "";
    }
	/**
	 * 
	 * @param fieldes
	 * 表字段名称
	 * @param tableName
	 * 表名称
	 * @param fieldValues
	 * 需要插入的值
	 * @return
	 */
	public List<String> insertsql(List<String> fieldes,String tableName, List<List<String>> fieldValues){
		List<String> insertList = new ArrayList<String>();
		if(dbflag == 2){
			for (int j = 0; j < fieldValues.size(); j++) {
				StringBuffer sBufeer = new StringBuffer();
				sBufeer.append("INSERT INTO ");
		    	sBufeer.append(tableName);
		    	sBufeer.append(" ( ");
		    	for (int i = 0; i < fieldes.size(); i++) {
					if(i != fieldes.size()-1){
						sBufeer.append(fieldes.get(i)+",");
					}else{
						sBufeer.append(fieldes.get(i));
					}
				}
		    	sBufeer.append(" )VALUES (");
	    		for (int i = 0; i < fieldValues.get(j).size(); i++) {
	    			if(i != fieldValues.get(j).size()-1){
	    				if(StringUtils.isNotBlank(fieldValues.get(j).get(i))){
	    					if(fieldValues.get(j).get(i).startsWith("TO_DATE")){
	    						sBufeer.append(fieldValues.get(j).get(i)+",");
	    					}else{
	    						sBufeer.append("'"+fieldValues.get(j).get(i)+"',");
	    					}
	    				}else{
	    					sBufeer.append("NULL,");
	    				}
	    			}else{
	    				if(StringUtils.isNotBlank(fieldValues.get(j).get(i))){
	    					if(fieldValues.get(j).get(i).startsWith("TO_DATE")){
	    						sBufeer.append(fieldValues.get(j).get(i)+",");
	    					}else{
	    						sBufeer.append("'"+fieldValues.get(j).get(i)+"'");
	    					}
	    				}else{
	    					sBufeer.append("NULL");
	    				}
	    			}
				}
	    		sBufeer.append(")");
	    		insertList.add(sBufeer.toString());
	    	}
		}else{
			StringBuffer sBufeer = new StringBuffer();
			sBufeer.append("INSERT INTO ");
	    	sBufeer.append(tableName);
	    	sBufeer.append(" ( ");
	    	for (int i = 0; i < fieldes.size(); i++) {
				if(i != fieldes.size()-1){
					sBufeer.append(fieldes.get(i)+",");
				}else{
					sBufeer.append(fieldes.get(i));
				}
			}
	    	sBufeer.append(" )VALUES ");
			for (int j = 0; j < fieldValues.size(); j++) {
	    		sBufeer.append("(");
	    		for (int i = 0; i < fieldValues.get(j).size(); i++) {
	    			if(i != fieldValues.get(j).size()-1){
	    				if(StringUtils.isNotBlank(fieldValues.get(j).get(i))){
	    					sBufeer.append("'"+fieldValues.get(j).get(i)+"',");
	    				}else{
	    					sBufeer.append("NULL,");
	    				}
	    			}else{
	    				if(StringUtils.isNotBlank(fieldValues.get(j).get(i))){
	    					sBufeer.append("'"+fieldValues.get(j).get(i)+"'");
	    				}else{
	    					sBufeer.append("NULL");
	    				}
	    			}
				}
	    		if(j != fieldValues.size()-1){
	    			sBufeer.append("),");
	    		}else{
	    			sBufeer.append(")");
	    		}
	    	}
			insertList.add(sBufeer.toString());
		}
		return insertList;
	}
	
	/**
	 * 
	 * @param fieldes
	 * 表字段名称
	 * @param tableName
	 * 表名称
	 * @param fieldValues
	 * 需要插入的值
	 * @return
	 */
	public String insertSingleSql(Map<String,Object> fieldInfoes,String tableName){
		StringBuffer sBufeer = new StringBuffer();
		if(fieldInfoes != null && !fieldInfoes.isEmpty()){
			List<String> fieldes = new ArrayList<String>();
			List<Object> fieldValues = new ArrayList<Object>();
			for (Entry<String, Object> entry : fieldInfoes.entrySet()) {
				fieldes.add(entry.getKey());
				fieldValues.add(entry.getValue());
			}
			sBufeer.append("INSERT INTO ");
			sBufeer.append(tableName);
			sBufeer.append(" ( ");
			for (int i = 0; i < fieldes.size(); i++) {
				if(i != fieldes.size()-1){
					sBufeer.append(fieldes.get(i)+",");
				}else{
					sBufeer.append(fieldes.get(i));
				}
			}
			sBufeer.append(" )VALUES (");
			for (int i = 0; i < fieldValues.size(); i++) {
				if(i != fieldValues.size()-1){
					if(fieldValues.get(i) != null && StringUtils.isNotBlank(String.valueOf(fieldValues.get(i)))){
						if(dbflag == 2 && String.valueOf(fieldValues.get(i)).startsWith("TO_DATE")){
							sBufeer.append(fieldValues.get(i)+",");
						}else{
							sBufeer.append("'"+fieldValues.get(i)+"',");
						}
					}else{
						sBufeer.append("NULL,");
					}
				}else{
					if(fieldValues.get(i) != null && StringUtils.isNotBlank(String.valueOf(fieldValues.get(i)))){
						if(dbflag == 2 && String.valueOf(fieldValues.get(i)).startsWith("TO_DATE")){
							sBufeer.append(fieldValues.get(i)+",");
						}else{
							sBufeer.append("'"+fieldValues.get(i)+"'");
						}
					}else{
						sBufeer.append("NULL");
					}
				}
			}
			sBufeer.append(")");
		}
		return sBufeer.toString();
	}	
			
}