package com.sdp.sqlModel.function;

import java.io.Serializable;

public class Field
  implements AbstractField, Serializable, Cloneable
{
  private String name;
  private String label;
  private int datatype;
  private int length;
  private int decimalDigits;
  private boolean nullable;
  private boolean keyable;
  private boolean indexable;
  private Object value;
  private int varible;
  private int nChgstate;
  private String format;
  private String align;
  private boolean readonly;
  private boolean visible;
  private boolean sortable;
  private boolean highlightable;
  private boolean sequenceable;
  private String sequencename;
  private int c_rule;
  private boolean transable;
  private String classname;
  private String codesetid;
  private boolean fillable;
  private int level;

  public boolean isFillable()
  {
    return this.fillable;
  }

  public void setFillable(boolean fillable) {
    this.fillable = fillable;
  }

  public int getLevel()
  {
    return this.level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public Field(String name)
  {
    this.nullable = true;

    this.varible = 0;

    this.nChgstate = 0;

    this.format = "";

    this.align = "";

    this.readonly = false;

    this.visible = true;

    this.sortable = false;

    this.highlightable = false;

    this.sequenceable = false;

    this.c_rule = 0;

    this.transable = false;

    this.codesetid = "0";

    this.fillable = false;

    this.level = 1;

    this.name = name;
    this.label = name;
    this.indexable = false;
    this.keyable = false;
    this.nullable = true;
  }

  public Field(String name, int datatype)
  {
    this(name);
    this.datatype = datatype;
  }

  public Field(String name, String label)
  {
    this.nullable = true;

    this.varible = 0;

    this.nChgstate = 0;

    this.format = "";

    this.align = "";

    this.readonly = false;

    this.visible = true;

    this.sortable = false;

    this.highlightable = false;

    this.sequenceable = false;

    this.c_rule = 0;

    this.transable = false;

    this.codesetid = "0";

    this.fillable = false;

    this.level = 1;

    this.name = name;
    this.label = label;
  }

  public Field(String name, String label, String datatype)
  {
    this.nullable = true;

    this.varible = 0;

    this.nChgstate = 0;

    this.format = "";

    this.align = "";

    this.readonly = false;

    this.visible = true;

    this.sortable = false;

    this.highlightable = false;

    this.sequenceable = false;

    this.c_rule = 0;

    this.transable = false;

    this.codesetid = "0";

    this.fillable = false;

    this.level = 1;

    this.name = name;
    this.label = label;
    this.datatype = DataType.nameToType(datatype);
  }

  public Field(String name, String label, int datatype)
  {
    this.nullable = true;

    this.varible = 0;

    this.nChgstate = 0;

    this.format = "";

    this.align = "";

    this.readonly = false;

    this.visible = true;

    this.sortable = false;

    this.highlightable = false;

    this.sequenceable = false;

    this.c_rule = 0;

    this.transable = false;

    this.codesetid = "0";

    this.fillable = false;

    this.level = 1;

    this.name = name;
    this.label = label;
    this.datatype = datatype;
  }

  public boolean isChangeBefore()
  {
    return (this.nChgstate == 1);
  }

  public boolean isChangeAfter()
  {
    return (this.nChgstate == 2);
  }

  public int getNChgstate()
  {
    return this.nChgstate; }

  public void setNChgstate(int chgstate) {
    this.nChgstate = chgstate; }

  public int getVarible() {
    return this.varible; }

  public void setVarible(int var) {
    this.varible = var;
  }

  public int getLength()
  {
    return this.length;
  }

  public void setLength(int length)
  {
    this.length = length;
  }

  public int getDataType()
  {
    return this.datatype;
  }

  public Object getValue()
  {
    return this.value;
  }

  public String getLabel()
  {
    return this.label;
  }

  public String getName()
  {
    return this.name;
  }

  public boolean isKeyable()
  {
    return this.keyable;
  }

  public boolean isIndexable()
  {
    return this.indexable;
  }

  public boolean isNullable()
  {
    return this.nullable;
  }

  public int getDatatype()
  {
    return this.datatype;
  }

  public int getDecimalDigits()
  {
    return this.decimalDigits;
  }

  public String getFormat()
  {
    return this.format;
  }

  public String getAlign()
  {
    return this.align;
  }

  public boolean isReadonly()
  {
    return this.readonly;
  }

  public boolean isVisible()
  {
    return this.visible;
  }

  public boolean isHighlightable()
  {
    return this.highlightable;
  }

  public boolean isSortable()
  {
    return this.sortable;
  }

  public String getClassname()
  {
    return this.classname;
  }

  public String getSequencename()
  {
    return this.sequencename;
  }

  public boolean isSequenceable()
  {
    return this.sequenceable;
  }

  public boolean isTransable()
  {
    return this.transable;
  }

  public String getCodesetid()
  {
    return this.codesetid;
  }

  public void setDatatype(String s)
  {
    this.datatype = DataType.nameToType(s);
  }

  public void setDatatype(int i)
  {
    this.datatype = i;
  }

  public void setValue(Object obj)
  {
    if (obj != null)
      this.datatype = DataType.parse(obj.getClass());
    this.value = obj;
  }

  public void setLabel(String s)
  {
    this.label = s;
  }

  public void setName(String s)
  {
    this.name = s;
  }

  public void setNullable(boolean flag)
  {
    this.nullable = flag;
  }

  public void setIndexable(boolean flag)
  {
    this.indexable = flag;
  }

  public void setKeyable(boolean flag)
  {
    this.keyable = flag;
  }

  public void setDecimalDigits(int decimalDigits)
  {
    this.decimalDigits = decimalDigits;
  }

  public void setFormat(String format)
  {
    this.format = format;
  }

  public void setAlign(String align)
  {
    this.align = align;
  }

  public void setReadonly(boolean readonly)
  {
    this.readonly = readonly;
  }

  public void setVisible(boolean visible)
  {
    this.visible = visible;
  }

  public void setHighlightable(boolean highlightable)
  {
    this.highlightable = highlightable;
  }

  public void setSortable(boolean sortable)
  {
    this.sortable = sortable;
  }

  public void setClassname(String classname)
  {
    this.classname = classname;
  }

  public void setSequencename(String sequencename)
  {
    this.sequencename = sequencename;
  }

  public void setSequenceable(boolean sequenceable)
  {
    this.sequenceable = sequenceable;
  }

  public void setTransable(boolean transable)
  {
    this.transable = transable;
  }

  public void setCodesetid(String codesetid)
  {
    this.codesetid = codesetid;
  }

  public boolean isCode()
  {
    return ((this.codesetid != null) && (!(this.codesetid.equals(""))) && (!(this.codesetid.equals("0"))));
  }

  private String getMSSQLDataTypeName()
  {
    StringBuffer strdefintion = new StringBuffer();
    switch (this.datatype)
    {
    case 4:
      strdefintion.append("int");
      break;
    case 5:
      strdefintion.append("bigint");
      break;
    case 3:
      strdefintion.append("smallint");
      break;
    case 2:
      strdefintion.append("tinyint");
      break;
    case 9:
      strdefintion.append(" bit ");
      break;
    case 6:
    case 7:
    case 8:
      strdefintion.append("numeric(");
      strdefintion.append(this.length + this.decimalDigits);
      strdefintion.append(",");
      strdefintion.append(this.decimalDigits);
      strdefintion.append(")");

      break;
    case 13:
      strdefintion.append("text");
      break;
    case 14:
    case 20:
      strdefintion.append("image");
      break;
    case 10:
    case 11:
    case 12:
      strdefintion.append("datetime");
      break;
    case 0:
    case 1:
      strdefintion.append("varchar(");
      strdefintion.append(this.length);
      strdefintion.append(") ");
      break;
    case 15:
    case 16:
    case 17:
    case 18:
    case 19:
    default:
      strdefintion.append("varchar(");
      strdefintion.append(this.length);
      strdefintion.append(")");
    }

    return strdefintion.toString();
  }

  private String getOracleDataTypeName()
  {
    StringBuffer strdefintion = new StringBuffer();
    switch (this.datatype)
    {
    case 4:
      strdefintion.append("number(18,0)");
      break;
    case 5:
      strdefintion.append("number(18,0)");
      break;
    case 3:
      strdefintion.append("number(5,0)");
      break;
    case 2:
      strdefintion.append("number(3,0)");
      break;
    case 9:
      strdefintion.append("number(1,0)");
      break;
    case 6:
    case 7:
    case 8:
      strdefintion.append("number(");
      strdefintion.append(this.length + this.decimalDigits);
      strdefintion.append(",");
      strdefintion.append(this.decimalDigits);
      strdefintion.append(")");
      break;
    case 13:
      strdefintion.append("clob");
      break;
    case 14:
    case 20:
      strdefintion.append("blob");
      break;
    case 10:
    case 11:
    case 12:
      strdefintion.append("date");
      break;
    case 0:
    case 1:
      strdefintion.append("varchar2(");
      strdefintion.append(this.length);
      strdefintion.append(") ");
      break;
    case 15:
    case 16:
    case 17:
    case 18:
    case 19:
    default:
      strdefintion.append("varchar2(");
      strdefintion.append(this.length);
      strdefintion.append(")");
    }

    return strdefintion.toString();
  }

  private String getDb2DataTypeName()
  {
    StringBuffer strdefintion = new StringBuffer();
    switch (this.datatype)
    {
    case 4:
      strdefintion.append("int");
      break;
    case 5:
      strdefintion.append("int");
      break;
    case 3:
      strdefintion.append("smallint");
      break;
    case 2:
      strdefintion.append("smallint");
      break;
    case 9:
      strdefintion.append("smallint");
      break;
    case 6:
    case 7:
    case 8:
      strdefintion.append("numeric(");
      strdefintion.append(this.length + this.decimalDigits);
      strdefintion.append(",");
      strdefintion.append(this.decimalDigits);
      strdefintion.append(")");
      break;
    case 13:
      strdefintion.append("clob");
      break;
    case 14:
    case 20:
      strdefintion.append("blob");
      break;
    case 10:
    case 11:
    case 12:
      strdefintion.append("timestamp");
      break;
    case 0:
    case 1:
      strdefintion.append("varchar(");
      strdefintion.append(this.length);
      strdefintion.append(") ");
      break;
    case 15:
    case 16:
    case 17:
    case 18:
    case 19:
    default:
      strdefintion.append("varchar(");
      strdefintion.append(this.length);
      strdefintion.append(")");
    }

    return strdefintion.toString();
  }

  public String getSqlDataTypeName(int dbflag)
  {
    StringBuffer strcond = new StringBuffer();
    strcond.append(this.name);
    strcond.append(" ");

    switch (dbflag)
    {
    case 1:
      strcond.append(getMSSQLDataTypeName());
      break;
    case 2:
      strcond.append(getOracleDataTypeName());
      break;
    case 3:
      strcond.append(getDb2DataTypeName());
      break;
    default:
      strcond.append(getMSSQLDataTypeName());
    }

    if (isNullable())
    {
      if (dbflag == 1)
        strcond.append(" null ");
    }
    else
      strcond.append(" not null ");
    return strcond.toString();
  }

  public String toString() {
    StringBuffer str = new StringBuffer();
    str.append("{");
    str.append("name=");
    str.append(this.name);
    str.append(",label=");
    str.append(this.label);
    str.append(",type=");
    str.append(DataType.typeToName(this.datatype));
    str.append(",length=");
    str.append(this.length);
    str.append("}");
    return str.toString();
  }

  public Object clone()
  {
    try
    {
      return super.clone();
    }
    catch (CloneNotSupportedException e) {
    }
    return null;
  }

  public int getC_rule() {
    return this.c_rule; }

  public void setC_rule(int c_rule) {
    this.c_rule = c_rule;
  }
}