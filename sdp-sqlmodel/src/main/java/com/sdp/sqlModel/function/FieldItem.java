package com.sdp.sqlModel.function;

import java.io.Serializable;

public class FieldItem
    implements Serializable, Cloneable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fieldsetid;
    private String itemid;
    private boolean nullable;
    private boolean keyable;
    private boolean indexable;
    private int varible;
    private int nChgstate;
    private String format;
    private String align;
    private boolean readonly;
    private boolean visible;
    private boolean sortable;
    private boolean highlightable;
    private boolean fillable;
    private boolean sequenceable;
    private String sequencename;
    private int c_rule;
    private boolean transable;
    private String classname;
    private String itemdesc;
    private String itemtype;
    private int displayid;
    private String useflag;
    private String moduleflag;
    private int itemlength;
    private int decimalwidth;
    private int displaywidth;
    private String codesetid;
    private String formula;
    private String explain;
    private String state;
    private int priv_status;
    private String value;
    private String viewvalue;
    private String auditingFormula;
    private String auditingInformation;
    private String typeflag;
    private int dataBaseType;
    public int getnChgstate() {
		return nChgstate;
	}

	public void setnChgstate(int nChgstate) {
		this.nChgstate = nChgstate;
	}

	public String getTypeflag() {
		return typeflag;
	}

	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public FieldItem(String fieldsetid, String itemid)
    {
        this.fieldsetid = "";
        nChgstate = 0;
        format = "";
        align = "";
        readonly = false;
        visible = true;
        sortable = false;
        highlightable = false;
        fillable = false;
        sequenceable = false;
        c_rule = 0;
        transable = false;
        codesetid = "";
        this.fieldsetid = fieldsetid;
        this.itemid = itemid;
        indexable = false;
        keyable = false;
        nullable = true;
        varible = 0;
    }

    public FieldItem()
    {
        this("", "");
    }

    public boolean isMainSet()
    {
        return fieldsetid.substring(1).equals("01");
    }

    public boolean isPerson()
    {
        return fieldsetid.charAt(0) == 'A';
    }

    public boolean isOrg()
    {
        return fieldsetid.charAt(0) == 'B';
    }

    public boolean isPos()
    {
        return fieldsetid.charAt(0) == 'K';
    }

    public boolean isChangeBefore()
    {
        return nChgstate == 1;
    }

    public boolean isChangeAfter()
    {
        return nChgstate == 2;
    }

    public String getItemdesc()
    {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc)
    {
        this.itemdesc = itemdesc;
    }

    public String getItemtype()
    {
        return itemtype;
    }

    public void setItemtype(String itemtype)
    {
        this.itemtype = itemtype;
    }

    public boolean isCode()
    {
        return codesetid != null && !codesetid.equals("") && !codesetid.equals("0");
    }

    public boolean isDate()
    {
        return itemtype.equalsIgnoreCase("D");
    }

    public boolean isFloat()
    {
        return decimalwidth > 0 && itemtype.equalsIgnoreCase("N");
    }

    public boolean isMemo()
    {
        return itemtype.equalsIgnoreCase("M");
    }

    public boolean isInt()
    {
        return decimalwidth == 0 && itemtype.equalsIgnoreCase("N");
    }

    public boolean isChar()
    {
        return itemtype.equalsIgnoreCase("A");
    }

    public String getItemSqlExpr(String dbpre, boolean bAddSet)
    {
        StringBuffer strexpr = new StringBuffer();
        if(!bAddSet)
            strexpr.append(itemid);
        else
        if(fieldsetid != null && !fieldsetid.equals(""))
        {
            strexpr.append(dbpre);
            strexpr.append(fieldsetid);
            strexpr.append(".");
            strexpr.append(itemid);
        } else
        {
            strexpr.append(itemid);
        }
        if(itemtype.equalsIgnoreCase("N"))
            return Sql_switcher.getSqlFunc(dataBaseType).sqlNull(strexpr.toString(), 0.0F);
        if(itemtype.equalsIgnoreCase("A"))
            return Sql_switcher.getSqlFunc(dataBaseType).sqlNull(strexpr.toString(), "");
        else
            return strexpr.toString();
    }

    public String getAuditingFormula()
    {
        return auditingFormula;
    }

    public void setAuditingFormula(String auditingFormula)
    {
        this.auditingFormula = auditingFormula;
    }

    public String getAuditingInformation()
    {
        return auditingInformation;
    }

    public void setAuditingInformation(String auditingInformation)
    {
        this.auditingInformation = auditingInformation;
    }

    public String getCodesetid()
    {
        return codesetid;
    }

    public void setCodesetid(String codesetid)
    {
        this.codesetid = codesetid;
    }

    public int getDecimalwidth()
    {
        return decimalwidth;
    }

    public void setDecimalwidth(int decimalwidth)
    {
        this.decimalwidth = decimalwidth;
    }

    public int getDisplayid()
    {
        return displayid;
    }

    public void setDisplayid(int displayid)
    {
        this.displayid = displayid;
    }

    public int getDisplaywidth()
    {
        return displaywidth;
    }

    public void setDisplaywidth(int displaywidth)
    {
        this.displaywidth = displaywidth;
    }

    public String getExplain()
    {
        return explain;
    }

    public void setExplain(String explain)
    {
        this.explain = explain;
    }

    public String getFieldsetid()
    {
        return fieldsetid;
    }

    public void setFieldsetid(String fieldsetid)
    {
        this.fieldsetid = fieldsetid;
    }

    public String getFormula()
    {
        return formula;
    }

    public void setFormula(String formula)
    {
        this.formula = formula;
    }

    public String getItemid()
    {
        return itemid;
    }

    public void setItemid(String itemid)
    {
        this.itemid = itemid;
    }

    public int getItemlength()
    {
        return itemlength;
    }

    public void setItemlength(int itemlength)
    {
        this.itemlength = itemlength;
    }

    public String getModuleflag()
    {
        return moduleflag;
    }

    public void setModuleflag(String moduleflag)
    {
        this.moduleflag = moduleflag;
    }

    public Object clone()
    {
        try
        {
            return super.clone();
        }
        catch(CloneNotSupportedException e)
        {
            return null;
        }
    }

    public Object cloneItem()
    {
        FieldItem item = new FieldItem(getItemid(), getItemdesc());
        item.setAlign(getAlign());
        item.setAuditingFormula(getAuditingFormula());
        item.setAuditingInformation(getAuditingInformation());
        item.setCodesetid(getCodesetid());
        item.setDecimalwidth(getDecimalwidth());
        item.setDisplayid(getDisplayid());
        item.setDisplaywidth(getDisplaywidth());
        item.setExplain(getExplain());
        item.setFieldsetid(getFieldsetid());
        item.setFormat(getFormat());
        item.setFormula(getFormula());
        item.setHighlightable(isHighlightable());
        item.setState(getState());
        item.setIndexable(isIndexable());
        item.setItemdesc(getItemdesc());
        item.setItemid(getItemid());
        item.setItemlength(getItemlength());
        item.setItemtype(getItemtype());
        item.setKeyable(isKeyable());
        item.setModuleflag(getModuleflag());
        item.setNChgstate(getNChgstate());
        item.setNullable(isNullable());
        item.setPriv_status(getPriv_status());
        item.setReadonly(isReadonly());
        item.setSortable(isSortable());
        item.setState(getState());
        item.setTransable(isTransable());
        item.setUseflag(getUseflag());
        item.setValue(getValue());
        item.setViewvalue(getViewvalue());
        item.setVarible(getVarible());
        item.setVisible(isVisible());
        item.setC_rule(getC_rule());
        item.setFillable(fillable);
        item.setSequenceable(sequenceable);
        item.setSequencename(sequencename);
        return item;
    }

    public Field cloneField()
    {
        StringBuffer format = new StringBuffer();
        format.append("############");
        Field field = new Field(getItemid(), getItemdesc());
        field.setAlign(getAlign());
        field.setFormat(getFormat());
        field.setIndexable(isIndexable());
        field.setKeyable(isKeyable());
        field.setNullable(isNullable());
        field.setReadonly(readonly);
        field.setVisible(visible);
        field.setHighlightable(highlightable);
        field.setSortable(sortable);
        field.setSequenceable(sortable);
        field.setClassname(classname);
        field.setSequencename(sequencename);
        field.setSequenceable(sequenceable);
        field.setC_rule(c_rule);
        field.setTransable(transable);
        field.setCodesetid(getCodesetid());
        field.setVarible(varible);
        field.setNChgstate(nChgstate);
        field.setFillable(fillable);
        if(getItemtype().equalsIgnoreCase("A"))
        {
            field.setDatatype(1);
            field.setLength(getItemlength());
        } else
        if(getItemtype().equalsIgnoreCase("M"))
            field.setDatatype(13);
        else
        if(getItemtype().equalsIgnoreCase("N"))
        {
            field.setDatatype(6);
            field.setLength(12);
            field.setDecimalDigits(getDecimalwidth());
            int ndec = field.getDecimalDigits();
            if(ndec > 0)
            {
                format.setLength(ndec);
                field.setFormat("####." + format.toString());
            } else
            {
                field.setDatatype(4);
                field.setFormat("####");
            }
        } else
        if(getItemtype().equalsIgnoreCase("D"))
        {
            field.setDatatype(10);
            setFormat("yyyy.MM.dd");
        } else
        if(getItemtype().equalsIgnoreCase("L"))
        {
            field.setDatatype(14);
        } else
        {
            field.setDatatype(1);
            field.setLength(getItemlength());
        }
        return field;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getUseflag()
    {
        return useflag;
    }

    public int getPriv_status()
    {
        return priv_status;
    }

    public String getValue()
    {
        return value;
    }

    public String getViewvalue()
    {
        return viewvalue;
    }

    public boolean isNullable()
    {
        return nullable;
    }

    public boolean isKeyable()
    {
        return keyable;
    }

    public boolean isIndexable()
    {
        return indexable;
    }

    public String getAlign()
    {
        return align;
    }

    public String getFormat()
    {
        return format;
    }

    public boolean isReadonly()
    {
        return readonly;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public boolean isHighlightable()
    {
        return highlightable;
    }

    public boolean isSortable()
    {
        return sortable;
    }

    public String getClassname()
    {
        return classname;
    }

    public String getSequencename()
    {
        return sequencename;
    }

    public boolean isSequenceable()
    {
        return sequenceable;
    }

    public boolean isTransable()
    {
        return transable;
    }

    public void setUseflag(String useflag)
    {
        this.useflag = useflag;
    }

    public void setPriv_status(int priv_status)
    {
        this.priv_status = priv_status;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public void setViewvalue(String viewvalue)
    {
        this.viewvalue = viewvalue;
    }

    public void setNullable(boolean nullable)
    {
        this.nullable = nullable;
    }

    public void setKeyable(boolean keyable)
    {
        this.keyable = keyable;
    }

    public void setIndexable(boolean indexable)
    {
        this.indexable = indexable;
    }

    public void setAlign(String align)
    {
        this.align = align;
    }

    public void setFormat(String format)
    {
        this.format = format;
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

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("{");
        str.append("feildsetid=");
        str.append(fieldsetid);
        str.append(",itemid=");
        str.append(itemid);
        str.append(",itemdesc=");
        str.append(itemdesc);
        str.append("}");
        return str.toString();
    }

    public int getVarible()
    {
        return varible;
    }

    public void setVarible(int var)
    {
        varible = var;
    }

    public int getNChgstate()
    {
        return nChgstate;
    }

    public void setNChgstate(int chgstate)
    {
        nChgstate = chgstate;
    }

    public int getC_rule()
    {
        return c_rule;
    }

    public void setC_rule(int c_rule)
    {
        this.c_rule = c_rule;
    }

    public boolean isFillable()
    {
        return fillable;
    }

    public void setFillable(boolean fillable)
    {
        this.fillable = fillable;
    }

	public int getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(int dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

    
}
