package com.sdp.servflow.pubandorder.init.sysConfig.mapper;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sdp.frame.base.dao.DaoHelper;
import com.sdp.servflow.kafka.KafkaPropertiy;


/**
 * 服务鉴权
 * @author 任壮
 * @version 2017年7月21日
 * @see SysConfigSerMapper
 * @since
 */
@Component
public class SysConfigSerMapper  {
    /***
     * 封装的dao
     */
    @Resource
    private DaoHelper daoHelper;
    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<HashMap<String, Object>> getCodeTable() {
        return (List)daoHelper.queryForList("com.sdp.servflow.pubandorder.init.sysConfig.mapper.SysConfigSerMapper.getCodeTable", null);
    };
    public KafkaPropertiy getKafka() {
        return (KafkaPropertiy)daoHelper.queryOne("com.sdp.servflow.pubandorder.init.sysConfig.mapper.SysConfigSerMapper.getKafka", null);
    }
    public int updateKafka(KafkaPropertiy kafka) {
        return daoHelper.update("com.sdp.servflow.pubandorder.init.sysConfig.mapper.SysConfigSerMapper.updateKafka", kafka);
    }
}
