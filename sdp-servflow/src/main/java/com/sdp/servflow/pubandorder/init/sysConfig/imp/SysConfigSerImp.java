package com.sdp.servflow.pubandorder.init.sysConfig.imp;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sdp.servflow.kafka.KafkaPropertiy;
import com.sdp.servflow.pubandorder.common.Response;
import com.sdp.servflow.pubandorder.init.sysConfig.SysConfigSer;
import com.sdp.servflow.pubandorder.init.sysConfig.mapper.SysConfigSerMapper;
import com.sdp.servflow.pubandorder.util.IContants;

/**
 * 
 * 系统配置信息
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see SysConfigSerImp
 * @since
 */
@Service
public class SysConfigSerImp implements SysConfigSer{

    /**
     * 系统参数的mapper
     */
    @Autowired
    private SysConfigSerMapper sysConfigSerMapper;
    @Override
    public void syncodeTable() {
        
        List<HashMap<String, Object>> list = sysConfigSerMapper.getCodeTable();
        
        Map<Integer,Response>  codeTable = new HashMap<Integer,Response>();
        
        for( HashMap<String, Object>  hash : list ) {
            Integer key = (Integer)hash.get("recId");
            String  respCode = (String)hash.get("respCode");
            String respDesc = (String)hash.get("respDesc");
            Response response = new Response();
            response.setRespCode(respCode);
            response.setRespDesc(respDesc);
            codeTable.put(key, response);
        }
        synchronized (IContants.CODESTABLE) {
            IContants.CODESTABLE = codeTable;
            
        }
        
    }
    @Override
    public KafkaPropertiy getKafka() {
        return sysConfigSerMapper.getKafka();
    }
    
    @Override
    public int updateKafka(KafkaPropertiy kafka) {
        return sysConfigSerMapper.updateKafka(kafka);
    }

}
