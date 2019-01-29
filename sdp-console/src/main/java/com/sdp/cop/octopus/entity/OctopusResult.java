package com.sdp.cop.octopus.entity;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 *  自定义响应结果参数
 *  
 * @author zhangyunzhen
 * @version 2017年3月3日
 * @see OctopusResult
 * @since
 */
@ApiModel
public class OctopusResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    @ApiModelProperty(value="状态码",example="200")
    private Integer status;

    // 响应消息
    @ApiModelProperty(value="响应信息",example="ok")
    private String msg;

    // 响应中的数据
    @ApiModelProperty(value="响应数据")
    private Object data;

    public static OctopusResult build(Integer status, String msg, Object data) {
        return new OctopusResult(status, msg, data);
    }

    public static OctopusResult ok(Object data) {
        return new OctopusResult(data);
    }

    public static OctopusResult ok() {
        return new OctopusResult(null);
    }

    public OctopusResult() {

    }

    public static OctopusResult build(Integer status, String msg) {
        return new OctopusResult(status, msg, null);
    }

    public OctopusResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public OctopusResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为OctopusResult对象
     * 
     * @param jsonData json数据
     * @param clazz OctopusResult中的object类型
     * @return
     */
    public static OctopusResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, OctopusResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *         没有data数据
     * @param json
     * @return
     */
    public static OctopusResult format(String json) {
        try {
            return MAPPER.readValue(json, OctopusResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *      data数据是集合
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static OctopusResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
