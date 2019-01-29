package com.sdp.sqlModel.function;


public interface AbstractField
{

    public abstract int getDataType();

    public abstract Object getValue();

    public abstract String getLabel();

    public abstract String getName();

    public abstract int getLength();

    public abstract boolean isNullable();

    public abstract boolean isKeyable();

    public abstract boolean isIndexable();

    public abstract void setDatatype(String s);

    public abstract void setDatatype(int i);

    public abstract void setValue(Object obj);

    public abstract void setLabel(String s);

    public abstract void setName(String s);

    public abstract void setNullable(boolean flag);

    public abstract void setIndexable(boolean flag);

    public abstract void setKeyable(boolean flag);

    public abstract void setLength(int i);

    public abstract String getSqlDataTypeName(int i);
}
