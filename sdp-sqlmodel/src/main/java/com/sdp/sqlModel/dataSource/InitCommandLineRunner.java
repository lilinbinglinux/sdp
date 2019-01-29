package com.sdp.sqlModel.dataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitCommandLineRunner implements CommandLineRunner{

	@Autowired
	private DynamicDataSourceRegister register;

	@Override
	public void run(String... arg0) throws Exception {
		register.setEnvironment();
	}

}
