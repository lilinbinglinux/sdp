package com.sdp.sqlModel.function;

public final class DataType
{
  public static final int BIGDECIMAL = 8;
  public static final String BIGDECIMAL_NAME = "bigdecimal";
  public static final int BINARY = 20;
  public static final String BINARY_NAME = "binary";
  public static final int BOOLEAN = 9;
  public static final String BOOLEAN_NAME = "boolean";
  public static final int BYTE = 2;
  public static final String BYTE_NAME = "byte";
  public static final int DATE = 10;
  public static final String DATE_NAME = "date";
  public static final int DATETIME = 12;
  public static final String DATETIME_NAME = "datetime";
  public static final int DOUBLE = 7;
  public static final String DOUBLE_NAME = "double";
  public static final int FLOAT = 6;
  public static final String FLOAT_NAME = "float";
  public static final int INT = 4;
  public static final String INT_NAME = "int";
  public static final int LONG = 5;
  public static final String LONG_NAME = "long";
  public static final int SHORT = 3;
  public static final String SHORT_NAME = "short";
  public static final int STRING = 1;
  public static final String STRING_NAME = "string";
  public static final int TIME = 11;
  public static final String TIME_NAME = "time";
  public static final int CLOB = 13;
  public static final String CLOB_NAME = "clob";
  public static final int BLOB = 14;
  public static final String BLOB_NAME = "blob";
  public static final int UNKNOWN = 0;
  public static final String UNKNOWN_NAME = "";
  static Class a;
  static Class b;
  static Class c;
  static Class d;
  static Class e;
  static Class f;
  static Class g;
  static Class h;
  static Class i;
  static Class j;
  static Class k;
  static Class l;
  static Class m;
  static Class n;
  static Class x;
  static Class y;

  public static final int sqlTypeToType(String typename)
  {
    if (typename.equalsIgnoreCase("VARCHAR2"))
      return 1;
    if (typename.equalsIgnoreCase("NUMBER"))
      return 4;
    if (typename.equalsIgnoreCase("DATE"))
      return 10;
    if (typename.equalsIgnoreCase("CLOB"))
      return 13;
    if (typename.equalsIgnoreCase("BLOB")) {
      return 14;
    }
    return 0;
  }

  public static final int sqlTypeToType(int sqltype)
  {
    switch (sqltype)
    {
    case -1:
    case 1:
    case 12:
      return 1;
    case 91:
      return 10;
    case 92:
      return 11;
    case 93:
      return 12;
    case -7:
    case 16:
      return 9;
    case 4:
      return 4;
    case -5:
      return 5;
    case 5:
      return 3;
    case -6:
      return 2;
    case 2:
    case 3:
    case 6:
      return 6;
    case 7:
    case 8:
      return 7;
    case 2004:
      return 14;
    case 2005:
      return 13;
    case -2:
      return 20;
    }
    return 0;
  }

  public static final int nameToType(String s)
  {
    if ("string".equals(s))
      return 1;
    if ("boolean".equals(s))
      return 9;
    if ("int".equals(s))
      return 4;
    if ("float".equals(s))
      return 6;
    if ("date".equals(s))
      return 10;
    if ("time".equals(s))
      return 11;
    if ("datetime".equals(s))
      return 12;
    if ("double".equals(s))
      return 7;
    if ("long".equals(s))
      return 5;
    if ("byte".equals(s))
      return 2;
    if ("short".equals(s))
      return 3;
    if ("bigdecimal".equals(s))
      return 8;
    if ("clob".equals(s))
      return 13;
    if ("blob".equals(s))
      return 14;
    return ((!("binary".equals(s))) ? 0 : 20);
  }

  public static final String typeToName(int i1)
  {
    switch (i1) { case 1:
      return "string";
    case 9:
      return "boolean";
    case 4:
      return "int";
    case 6:
      return "float";
    case 10:
      return "date";
    case 11:
      return "time";
    case 12:
      return "datetime";
    case 7:
      return "double";
    case 5:
      return "long";
    case 2:
      return "byte";
    case 3:
      return "short";
    case 8:
      return "bigdecimal";
    case 20:
      return "binary";
    case 13:
      return "clob";
    case 14:
      return "blob";
    case 15:
    case 16:
    case 17:
    case 18:
    case 19: } return "";
  }

  static final Class a(String s)
  {
    try
    {
      return Class.forName(s);
    }
    catch (Exception ex) {
    }
    return null;
  }

  public static final int parse(Class class1)
  {
    if (class1.equals(DataType.i = a("java.lang.String")))
      return 1;
    if (class1.equals(Integer.TYPE))
      return 4;
    if (class1.equals(Boolean.TYPE))
      return 9;
    if (class1.equals(Float.TYPE))
      return 6;
    if (class1.equals(DataType.n = a("java.util.Date")))
      return 10;
    if (class1.equals(DataType.k = a("java.sql.Date")))
      return 10;
    if (class1.equals(DataType.l = a("java.sql.Time")))
      return 11;
    if (class1.equals(DataType.m = a("java.sql.Timestamp")))
      return 12;
    if (class1.equals(Long.TYPE))
      return 5;
    if (class1.equals(Double.TYPE))
      return 7;
    if (class1.equals(Byte.TYPE))
      return 2;
    if (class1.equals(Short.TYPE))
      return 3;
    if (class1.equals(DataType.j = a("java.math.BigDecimal")))
      return 8;
    if (class1.equals(DataType.e = a("java.lang.Integer")))
      return 4;
    if (class1.equals(DataType.a = a("java.lang.Boolean")))
      return 9;
    if (class1.equals(DataType.d = a("java.lang.Float")))
      return 6;
    if (class1.equals(DataType.f = a("java.lang.Long")))
      return 5;
    if (class1.equals(DataType.c = a("java.lang.Double")))
      return 7;
    if (class1.equals(DataType.b = a("java.lang.Byte")))
      return 2;
    if (class1.equals(DataType.x = a("java.sql.Clob")))
      return 13;
    if (class1.equals(DataType.y = a("java.sql.Blob")))
      return 14;
    return ((!(class1.equals(DataType.h = a("java.lang.Short")))) ? 1 : 3);
  }
}