package com.sdp.servflow.pubandorder.serve.imp;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sdp.servflow.pubandorder.serve.CheckParam;
import com.sdp.servflow.pubandorder.util.IContants;
@Service
public class CheckParamImp implements CheckParam{

    
    
    public static final String NECESSARYMSG = "参数为必传参数";
    public static final String PARAMtYPEERR = "参数类型出错或者参数值有误";
    public static final String MAXLENERR = "字符串超过最大的长度";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String BOOLEANCLASS = "class java.lang.Boolean";
    
    /***
     * 校验参数是否满足条件
     * 
     * 如果通过校验  则返回空 
     *  不通过检验的返回校验不通过的原因
     * 
     */
    public String checkParam(Object value,boolean isMust,String paramType,String enumString,String maxstringLen)
    {
        String errorMsg = null;
        if(!chekNecessary(value, isMust)) {
            //对必传参数进行校验
            errorMsg =NECESSARYMSG;
        }
        if(value!=null)
        {
            //对数据类型 以及枚举值进行校验
          if (!chekParam(value, paramType, enumString)) {
              //数据格式校验不通过
              errorMsg =  PARAMtYPEERR;
          }else{
              //格式校验通过的情况下  对String类型的长度进行判断
              if(maxstringLen!=null && maxstringLen.length()>0)
              {
              if(IContants.ParamType.STRING.equals(paramType))
              {
                  int len =Integer.parseInt(maxstringLen);
                  if(((String)value).length()>len)
                  {
                      errorMsg =  MAXLENERR+maxstringLen;
                  }
              }
              }
          }
        }
        return errorMsg;
    }
    
    
    
    /***
     * 
     * Description: 校验参数是否必传
     * 
     *@param value
     *@param isMust
     *@return boolean
     *
     * @see
     */
    private boolean chekNecessary(Object value,boolean isMust)
    {
      if(isMust && value == null) {
              return false;
      }else{
          return true;
      }
    }
     /****
      * 
      * Description: 校验参数类型
      * 
      *@param value  传入值
      *@param paramType  参数类型 
      *@param enumString 枚举范围   eg:1,2,3  eg:test,测试1，测试2
      *@return boolean
      *
      * @see
      */
    private boolean chekParam(Object value,String paramType,String enumString)
    {
        try {
            
            if(IContants.ParamType.BOOLEAN.equals(paramType))
            {
                if(!(value instanceof Boolean)){
                    //不是boolean值
                    return false;
                }  
            }
            if(IContants.ParamType.INT.equals(paramType)  || IContants.ParamType.STRING.equals(paramType))
            {
              return   JugeType(value, paramType, enumString);
            }
            return true; 
        }
        catch (Exception e) {
          return false;
        }
    }
    
    /***
     * 
     * Description: 判断类型是否满足 以及对枚举值进行判断
     * 
     *@param value
     *@param paramType
     *@param enumString
     *@return boolean
     *
     * @see
     */
    private  boolean JugeType(Object value,String paramType,String enumString) {
        
            boolean condition =false;
        
            if(IContants.ParamType.STRING.equals(paramType))
            {
                condition =value instanceof String;
            }else if(IContants.ParamType.INT.equals(paramType))
            {
                condition =value instanceof Integer;
            }
            if(!(condition)){
                //不是String值
                return false;
            }else{
                //满足String值  如果枚举值中存在值，String必须是枚举中的一个
                if(enumString!=null&&enumString.length()>0)
                {
                    //枚举值不为空的时候判断枚举值
                    boolean isContains =false;
                    String[]  objs = enumString.split(",");
                    for(String obj:objs)
                    {
                        //如果满足
                        if(((String)value).equals(obj))
                        {
                            isContains = true;
                            break;
                        }
                    }
                    return isContains;
                }else{
                    //枚举值为空的时候不做判断 返回true
                    return true;
                }
                
            }  
        }



    @Override
    public String checkParam(List<Object> value, boolean isMust, String paramType,
                             String enumString, String maxstringLen) {
        
        if(value == null)
            return null;
        for(Object obj :value )
        {
            String    checkResult =  checkParam(obj, isMust, paramType, null, maxstringLen);
            if(checkResult!=null){
             return checkResult;
            }
        }
        return null;
    }
}
