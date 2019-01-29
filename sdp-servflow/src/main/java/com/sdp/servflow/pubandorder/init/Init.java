package com.sdp.servflow.pubandorder.init;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sdp.servflow.pubandorder.init.sysConfig.SysConfigSer;
/***
 * 
 * 项目初始化信息同步
 *
 * @author 任壮
 * @version 2017年7月27日
 * @see Init
 * @since
 */
@Component("init")
@Order(value=1)
public class Init implements CommandLineRunner {
    private static final Logger logger = Logger.getLogger(InitConsumer.class);
    /**
     * 同步项目配置码表
     * */
    @Autowired
	private SysConfigSer sysConfigSer;
	
    @Override
	public void run(String... args) throws Exception {
        try {
            logger.info("初始化信息码表信息");
            sysConfigSer.syncodeTable();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
    }
    
}
