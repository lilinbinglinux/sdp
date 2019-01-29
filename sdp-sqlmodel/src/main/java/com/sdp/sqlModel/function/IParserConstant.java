package com.sdp.sqlModel.function;
	
public interface IParserConstant {
		
	public static int forNormal=0;
	public static int forSearch=1;
	
	public static int forPerson=0;
	public static int forPosition=1;
	public static int forDepartment=2;
	public static int forUnit=3;
	/**没有用吧,chenmengqing added*/
	public static int vtFloat=0;
	public static int vtDate=1;
	public static int vtString=2;
	public static int vtLogic=3;	
	
	// 语法逻辑 Logic
	public static int S_NOTHING=0; // 无逻辑(注释之类，(B/S扩展))
	public static int S_TRUE=1;
	public static int S_FALSE=2;
	public static int S_NULL=3;
	public static int S_AND=4;
	public static int S_OR=5;
	public static int S_NOT=6;
	public static int S_ADD=7;
	public static int S_MINUS=8;
	public static int S_MULTIPLY=9;
	public static int S_DIVISION=10;
	public static int S_DIV=11;
	public static int S_MOD=12;
	public static int S_EQUAL=13;
	public static int S_GREATER=14;
	public static int S_NOTGREATER=15;
	public static int S_SMALLER=16;
	public static int S_NOTSMALLER=17;
	public static int S_NOTEQUAL=18;
	public static int S_POWER=19;
	public static int S_LPARENTHESIS=20;
	public static int S_RPARENTHESIS=21;
	public static int S_SEMICOLON=22;
	public static int S_COMMA=23;
	public static int S_EOL=24;
	public static int S_FINISHED=24;
	public static int S_ELSE=25;
	public static int S_COLON=26;
	public static int S_END=27;
	public static int S_FIRST=28;
	public static int S_LAST=29;
	public static int S_MAX=30;
	public static int S_MIN=31;
	public static int S_SUM=32;
	public static int S_AVG=33;
	public static int S_COUNT=34;
	public static int S_INCREASE=35;
	public static int S_DECREASE=36;
	public static int S_LIKE=37;
	public static int S_IN=38;
	public static int S_THEN=39;
	public static int S_GETEND=40;
	public static int S_SATISFY=41;	
	public static int S_NOTIN=42;

	// 语法逻辑符号集合
	public static String REGE="+-*/\\%^;,=<>():";
	
	// 语法符号 Symbol
	public static String ADD ="+";
	public static String MINUS = "-";
	public static String MULTIPLY = "*";
	public static String DIVISION = "/";
	public static String DIV = "\\";
	public static String MOD = "%";
	public static String POWER = "^";
	public static String SEMICOLON = ";";
	public static String COLON = ":";
	public static String COMMA = ",";
	public static String EQUAL = "=";
	public static String SMALLER = "<";
	public static String GREATER = ">";
	public static String LPARENTHESIS = "(";
	public static String RPARENTHESIS = ")";

	// token 类型 Type
//	public static int T_NOTE=0; //注释类型(B/S扩展))
	public static int DELIMITER=1;//分隔符类型
	public static int FIELDITEM=2;//参数类型
	public static int FUNC=3;//函数类型
	public static int QUOTE=4;//引用类型

	// 参数类型 Logic（返回类型）
	public static int INT=5;
	public static int FLOAT=6;
	public static int STRVALUE=7;
	public static int LOGIC=8;
	public static int DATEVALUE=9;
	public static int NULLVALUE=10;
	public static int ODDVAR=11;
	
	// 错误代码 Error
	public static int E_LOSSNEXTSLASH=1; //(注释类型缺少第二个斜杠，B/S扩展)
	public static int E_LOSSQUOTE=2;
	public static int E_LOSSCOMMA=3;
	public static int E_LOSSLPARENTHESE=4;
	public static int E_LOSSRPARENTHESE=5;
	public static int E_SYNTAX=6;
	public static int E_NOTSAMETYPE=7;
	public static int E_MUSTBEDATE=8;
	public static int E_MUSTBEINTEGER=9;
	public static int E_MUSTBEBOOL=10;
	public static int E_MUSTBENUMBER=11;
	public static int E_MUSTBESTRING=12;
	public static int E_UNKNOWNSTR=13;
	public static int E_LOSSCOLON=14;
	public static int E_LOSSEND=15;
	public static int E_GETSELECT=16;
	public static int E_MUSTBESQLSYMBOL=17;
	public static int E_MUSTBEGETSYMBOL=18;
	public static int E_MUSTBEFIELDITEM=19;
	public static int E_MUSTBEONEFLDSET=20;
	public static int E_MUSTBECODEFIELD=21;
	public static int E_LOSSSEMICOLON=22;
	public static int E_FNOTSAMETYPE=23;
	public static int E_MUSTBESUBSET=24;
	public static int E_NOTFINDFIELD=25;
	public static int E_NOTFINDODDVAR=26;
	public static int E_LOSSBRACK1=27;
	public static int E_LOSSBRACK2=28;
	public static int E_LOSSTHEN=29;
	public static int E_LOSSELSE=30;
	public static int E_LOSSIIF=31;
	public static int E_LOSSTEMPTABLENAME=32;
	public static int E_LOSSGETEND=33;
	public static int E_LOSSSATISFY=34;
	public static int E_LOSSEQUALE=35;
	public static int E_LOSSASIGN=36;
	public static int E_FIELDSETNOTFOUNT = 37;
	public static int E_USEFUNCMOD=40;      // 请使用 取余数
	public static int E_USEFUNCINT=41;      //  请使用 取整
	public static int E_FUNCTON2=42;      //  
	public static int E_MUSTBEINTEGERMENU=43;
	public static int E_STDNDARDNOTEXIST=44;
	public static int E_STDNDARDNOTTWODIM=45;
	public static int E_MENUNOTMATCH=46;
	public static int E_MUSTBEONETWO=47;
	public static int E_CODEVALUE=48;
	
	// 函数代码 Function Code
	public static int FUNCTODAY=101;
	public static int FUNCTOWEEK=102;
	public static int FUNCTOMONTH=103;
	public static int FUNCTOQUARTER=104;
	public static int FUNCTOYEAR=105;
	// 函数代码 Function Code
	public static int FUNCYEAR=106;
	public static int FUNCMONTH=107;
	public static int FUNCDAY=108;
	public static int FUNCQUARTER=109;
	public static int FUNCWEEK=110;
	public static int FUNCWEEKDAY=111;
	public static int FUNCYEARS=112;
	public static int FUNCMONTHS=113;
	public static int FUNCDAYS=114;
	public static int FUNCQUARTERS=115;
	public static int FUNCWEEKS=116;
	public static int FUNCADDYEAR=117;
	public static int FUNCADDMONTH=118;
	public static int FUNCADDDAY=119;
	public static int FUNCADDQUARTER=120;
	public static int FUNCADDWEEK=121;
	public static int FUNCAGE=122;
	public static int FUNCMONTHAGE=123;
	public static int FUNCWORKAGE=124;
	public static int FUNCAPPDATE=125;
	// 函数代码 Function Code
	public static int FUNCROUND=126;
	public static int FUNCINT=127;
	public static int FUNCYUAN=128;
	public static int FUNCJIAO=129;
	public static int FUNCSANQI=130;
	// 函数代码 Function Code
	public static int FUNCLTRIM=131;
	public static int FUNCRTRIM=132;
	public static int FUNCTRIM=133;
	public static int FUNCSUBSTR=134;
	public static int FUNCLEN=135;
	public static int FUNCLEFT=136;
	public static int FUNCRIGHT=137;
	public static int FUNCCTOD=138;
	public static int FUNCCTOI=139;
	public static int FUNCDTOC=140;
	public static int FUNCITOC=141;
	public static int FUNCCTON=142;
	// 函数代码 Function Code
	public static int FUNCIIF=143;
	public static int FUNCCASE=144;
	public static int FUNCMAX=145;
	public static int FUNCMIN=146;
	public static int FUNCGET=147;
	public static int FUNCSELECT=148;
	public static int FUNCAPPAGE=149;
	public static int FUNCAPPWORKAGE=150;
	public static int FUNCSTATP=151;        //按职位统计人数
	public static int FUNCAPPMONTHAGE=152;
	public static int FUNCFORMULA=153;
	/**执行标准*/
	public static int FUNCSTANDARD=160;
	/**就近就高*/
	public static int FUNCNEARBYHIGH=161;
	/**就近就低*/
	public static int FUNCNEARBYLOW=162;
	public static int FUNCMOD=163;
	/**代码变档*/
	public static int FUNCCODEADD=164;
	/**后一个代码*/
	public static int FUNCCODENEXT=169;
	/**前一个代码*/
	public static int FUNCCODEPRIOR=170;
	/**代码调整*/
	public static int FUNCCODEADJUST=184;
	/**上一个历史记录指标值(指标,逻辑表达式)*/
	public static int FUNCHISPRIORMENU=171;
	
	/**历史记录最初指标值(指标,逻辑表达式)*/
	public static int FUNCHISFIRSTMENU=172;
	
	/**为空*/
	public static int FUNCISNULL=175;
	/**幂函数*/
	public static int FUNCPOWER=176;
	/**代码转名称2*/
	public static int FUNCCTON2=182;
	/**数字转汉字*/
	public static int FUNCCNTC=183;
	/**归属日期*/
	public static int   FUNCSALARYA00Z0=186;
	/**取登录用户名函数*/
	public static int   FUNCLOGINNAME=187;	
}
