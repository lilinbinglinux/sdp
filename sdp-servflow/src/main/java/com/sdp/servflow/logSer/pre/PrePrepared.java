package com.sdp.servflow.logSer.pre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.sdp.servflow.logSer.InstanceParam;

//@Component
//@Order(value=1)
public class PrePrepared implements CommandLineRunner {

	@Autowired
	private PreBusi preBusi;
	
	@Override
	public void run(String... args) throws Exception {
		
		InstanceParam.APP_START_FLAG = false;
		
		try {
			// 1.获取该实例全局唯一标识
//			preBusi.assigningGUID();

			// 2.从日志字典表里获取最大日期对应的表，判断与今天的关系， 若相等，则使用最大日期为保存天数，若不等，则注册到字典表中
			preBusi.checkLogDictionaryTable();
			
			// 3.判断字典表中，是否有前一个记录（即倒数第二个），如果有，则判断前一个记录是否统计了日总量，若没统计，则统计并记录
			preBusi.checkLastTwoRecordByLogDicTable();
			
			// 4.加载黑白名单配置与最近一次修改时间
			preBusi.serIpLimitInit();
			
			// 5.限流策略初始化
			preBusi.serLimitInit();
			
			InstanceParam.APP_START_FLAG = true;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("=========>>> This instance execute PrePrepared ERROE !!!");
			return;
		}
		
	}

}
