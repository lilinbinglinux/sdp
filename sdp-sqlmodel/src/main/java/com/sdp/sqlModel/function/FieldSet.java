package com.sdp.sqlModel.function;

import java.io.Serializable;
public class FieldSet
    implements Serializable
{
	private static final long serialVersionUID = 1L;
	private boolean mainset;
    private int fromZd;
    private String fieldsetid;
    private int displayorder;
    private String fieldsetdesc;
    private String useflag;
    private String moduleflag;
    private String changeflag;
    private String customdesc;
    private String reserveitem;
    private int priv_status;

    public FieldSet(String fieldsetid)
    {
        fromZd = 1;
        this.fieldsetid = fieldsetid;
        if(this.fieldsetid.substring(1).equals("01"))
            mainset = true;
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        str.append("{fieldsetid=");
        str.append(fieldsetid);
        str.append(",fieldsetdesc=");
        str.append(fieldsetdesc);
        str.append(",useflag=");
        str.append(useflag);
        str.append("}");
        return str.toString();
    }

//    public ArrayList getFieldItemList(int useflag)
//    {
//        ArrayList list = null;
//        int i = 0;
//        list = DataDictionary.getFieldList(fieldsetid, useflag);
//        return list;
//    }

    public boolean isMainset()
    {
        return mainset;
    }

    public String getChangeflag()
    {
        return changeflag;
    }

    public void setChangeflag(String changeflag)
    {
        this.changeflag = changeflag;
    }

    public String getCustomdesc()
    {
        return customdesc;
    }

    public void setCustomdesc(String customdesc)
    {
        this.customdesc = customdesc;
    }

    public int getDisplayorder()
    {
        return displayorder;
    }

    public void setDisplayorder(int displayorder)
    {
        this.displayorder = displayorder;
    }

    public String getFieldsetdesc()
    {
        return fieldsetdesc;
    }

    public void setFieldsetdesc(String fieldsetdesc)
    {
        this.fieldsetdesc = fieldsetdesc;
    }

    public String getFieldsetid()
    {
        return fieldsetid;
    }

    public void setFieldsetid(String fieldsetid)
    {
        this.fieldsetid = fieldsetid;
    }

    public String getModuleflag()
    {
        return moduleflag;
    }

    public void setModuleflag(String moduleflag)
    {
        this.moduleflag = moduleflag;
    }

    public String getReserveitem()
    {
        return reserveitem;
    }

    public void setReserveitem(String reserveitem)
    {
        this.reserveitem = reserveitem;
    }

    public String getUseflag()
    {
        return useflag;
    }

    public void setUseflag(String useflag)
    {
        this.useflag = useflag;
    }

    public void setPriv_status(int priv_status)
    {
        this.priv_status = priv_status;
    }

    public void setFromZd(int fromZd)
    {
        this.fromZd = fromZd;
    }
    public int getPriv_status()
    {
        return priv_status;
    }

    public int getFromZd()
    {
        return fromZd;
    }
}
