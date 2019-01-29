package com.sdp.sqlModel.dataSource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import com.sdp.SpringApplicationContext;
import com.sdp.common.BaseException;
import com.sdp.sqlModel.entity.SqlDataRes;
import com.sdp.sqlModel.entity.SqlDataResType;
import com.sdp.sqlModel.mapper.SqlDataResMapper;
import com.sdp.sqlModel.mapper.SqlDataResTypeMapper;



/**
 * 动态数据源注册<br/>
 */

@Component
public class DynamicDataSourceRegister {

	@Autowired
	private SqlDataResMapper sqlDataResMapper;
	
	@Autowired
	private SqlDataResTypeMapper sqlDataResTypeMapper;

	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

	private  PropertiesHelper helper = new PropertiesHelper("/application.properties");

	private ConversionService conversionService = new DefaultConversionService(); 
	private PropertyValues dataSourcePropertyValues;

	private DefaultListableBeanFactory beanFactory;


	// 如配置文件中未指定数据源类型，使用该默认值
	private static final Object DATASOURCE_TYPE_DEFAULT = "org.apache.tomcat.jdbc.pool.DataSource";
	//    private static final Object DATASOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";
	// private static final Object DATASOURCE_TYPE_DEFAULT =
	// "com.zaxxer.hikari.HikariDataSource";

	// 数据源
	private DataSource defaultDataSource;
	private Map<String, DataSource> customDataSources = new HashMap<String, DataSource>();
	private String dataSourceType;

	public void registerBeanDefinitions() {
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		// 将主数据源添加到更多数据源中
		targetDataSources.put("dataSource", defaultDataSource);
		DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
		org.apache.tomcat.jdbc.pool.DataSource datasource = (org.apache.tomcat.jdbc.pool.DataSource) defaultDataSource;
		DynamicDataSourceContextHolder.dataInfos.put("defaultDatasource", datasource.getPoolProperties());
		// 添加更多数据源
		targetDataSources.putAll(customDataSources);
		for (String key : customDataSources.keySet()) {
			DynamicDataSourceContextHolder.dataSourceIds.add(key);
			datasource = (org.apache.tomcat.jdbc.pool.DataSource) customDataSources.get(key);
			DynamicDataSourceContextHolder.dataInfos.put(key, datasource.getPoolProperties());
		}
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringApplicationContext.CONTEXT;
		// 获取bean工厂并转换为DefaultListableBeanFactory
		beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
		// 创建DynamicDataSource
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(DynamicDataSource.class);
		beanDefinition.setSynthetic(true);
		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
		mpv.addPropertyValue("targetDataSources", targetDataSources);
		beanFactory.registerBeanDefinition("dataSource", beanDefinition);
		logger.info("Dynamic DataSource Registry");
	}

	/**
	 * 创建DataSource
	 *
	 * @param type
	 * @param driver-class-name
	 * @param url
	 * @param username
	 * @param password
	 * @return
	 * @author SHANHY
	 * @create 2016年1月24日
	 */
	@SuppressWarnings("unchecked")
	public DataSource buildDataSource(Map<String, Object> dsMap) {
		if(dsMap!=null&&dsMap.size()>=1){
			dataSourceType=dsMap.get("driver-class-name").toString();
			try {
				Object type = dsMap.get("type");
				if (type == null)
					type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource

				Class<? extends DataSource> dataSourceType;


				String driverClassName = dsMap.get("driver-class-name").toString();
				String url = dsMap.get("url").toString();
				String username = dsMap.get("username").toString();
				String password = dsMap.get("password").toString();
				//            if(driver-class-name.equalsIgnoreCase("com.bonc.xcloud.jdbc.XCloudDriver")){
				//            	dataSourceType = (Class<? extends DataSource>) Class.forName((String) DATASOURCE_TYPE_DEFAULT);
				//            }else{
				//            	dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
				//            }

				dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);


				DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
						.username(username).password(password).type(dataSourceType);
				return factory.build();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 加载多数据源配置
	 */
	public void setEnvironment() {
		initDefaultDataSource();
//		initCustomDataSources();
		registerBeanDefinitions();
	}

	//	/**
	//	 * 初始化主数据源
	//	 *
	//	 * @author SHANHY
	//	 * @create 2016年1月24日
	//	 */
	//	private void initDefaultDataSource(Environment env) {
	//		// 读取主数据源
	//		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
	//		Map<String, Object> dsMap = new HashMap<String, Object>();
	//		dsMap.put("type", propertyResolver.getProperty("type"));
	//		dsMap.put("driver-class-name", propertyResolver.getProperty("driver-class-name"));
	//		dsMap.put("url", propertyResolver.getProperty("url"));
	//		dsMap.put("username", propertyResolver.getProperty("username"));
	//		dsMap.put("password", propertyResolver.getProperty("password"));
	//
	//
	//
	//		defaultDataSource = buildDataSource(dsMap);
	//
	//		dataBinder(defaultDataSource, env);
	//
	//		logger.info("defaultDataSource init success,dsMap:{}",dsMap);
	//	}

	/**
	 * 初始化主数据源
	 *
	 * @author rpy
	 * @create 2018年1月27日
	 */
	private void initDefaultDataSource() {
		// 读取主数据源
		Map<String, Object> dsMap = new HashMap<String, Object>();
		dsMap.put("type", helper.getValue("spring.datasource.type"));
		dsMap.put("driver-class-name", helper.getValue("spring.datasource.driverClassName"));
		dsMap.put("url", helper.getValue("spring.datasource.url"));
		dsMap.put("username", helper.getValue("spring.datasource.username"));
		dsMap.put("password", helper.getValue("spring.datasource.password"));
		defaultDataSource = buildDataSource(dsMap);
		dataBinder(defaultDataSource, helper);
		logger.info("defaultDataSource init success,dsMap:{}",dsMap);
	}

	/**
	 * 为DataSource绑定更多数据
	 *
	 * @param dataSource
	 * @param env
	 * @author SHANHY
	 * @create  2016年1月25日
	 */
	private void dataBinder(DataSource dataSource, PropertiesHelper helper){
		RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
		//dataBinder.setValidator(new LocalValidatorFactory().run(this.applicationContext));
		dataBinder.setConversionService(conversionService);
		dataBinder.setIgnoreNestedProperties(false);//false
		dataBinder.setIgnoreInvalidFields(false);//false
		dataBinder.setIgnoreUnknownFields(true);//true
		if(dataSourceType.equals("oracle.jdbc.OracleDriver")){
			//			Map<String, Object> rpr = new RelaxedPropertyResolver(env , "spring.oracle.datasource").getSubProperties(".");
			Map<String, Object> values = new HashMap<String, Object>();
			// 排除已经设置的属性
			values.remove("type");
			values.remove("driver-class-name");
			values.remove("url");
			values.remove("username");
			values.remove("password");
			dataSourcePropertyValues = new MutablePropertyValues(values);
		}else if(dataSourceType.equals("com.bonc.xcloud.jdbc.XCloudDriver")){
			//			Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.xcloud.datasource").getSubProperties(".");
			Map<String, Object> values = new HashMap<String, Object>();
			// 排除已经设置的属性
			values.remove("type");
			values.remove("driver-class-name");
			values.remove("url");
			values.remove("username");
			values.remove("password");
			dataSourcePropertyValues = new MutablePropertyValues(values);
		}else if(dataSourceType.equals("com.mysql.jdbc.Driver")){
			//			Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.mysql.datasource").getSubProperties(".");
			Map<String, Object> values = new HashMap<String, Object>();
			// 排除已经设置的属性
			values.remove("type");
			values.remove("driver-class-name");
			values.remove("url");
			values.remove("username");
			values.remove("password");
			dataSourcePropertyValues = new MutablePropertyValues(values);
		}else{
			//			Map<String, Object> rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
			Map<String, Object> values = new HashMap<String, Object>();
			// 排除已经设置的属性
			values.remove("type");
			values.remove("driver-class-name");
			values.remove("url");
			values.remove("username");
			values.remove("password");
			dataSourcePropertyValues = new MutablePropertyValues(values);
		}


		//        if(dataSourcePropertyValues == null){
		//            rpr = new RelaxedPropertyResolver(env, "spring.datasource").getSubProperties(".");
		//            values = new HashMap<String, Object>(rpr);
		//            // 排除已经设置的属性
		//            values.remove("type");
		//            values.remove("driver-class-name");
		//            values.remove("url");
		//            values.remove("username");
		//            values.remove("password");
		//            dataSourcePropertyValues = new MutablePropertyValues(values);
		//        }




		dataBinder.bind(dataSourcePropertyValues);
	}

	/**
	 * 初始化更多数据源
	 *
	 * @author SHANHY
	 * @throws BaseException 
	 * @create 2016年1月24日
	 */
//	private void initCustomDataSources() {
//		// 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
//		//		RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env, "custom.datasource.");
//		//		String dsPrefixs = propertyResolver.getProperty("names");
//		Tenants tenant = new Tenants();
//		tenant.setStatus("0");
//		List<Tenants> tenants = comDao.getTenant(tenant);
//		DataSource ds;
//		if(tenants!=null&&tenants.size()>=1){
//			for(Tenants tenant1:tenants){
//				Map<String, Object> dsMap= 	initResourcesService.dataSourceConfig(tenant1.getTenant_id());
//				if(dsMap!=null&&dsMap.size()>=1){
//					ds = buildDataSource(dsMap);
//					customDataSources.put(dsMap.get("dsName").toString(), ds);
//					dataBinder(ds, helper);
//					logger.info("customDataSources name:{} init success,dsMap:{}",tenant1.getTenant_id(),dsMap);
//				}
//			}
//		}
//	}

	public void addDataSource(String dataSourceType) throws BaseException{
		Map<String, Object> dsMap= 	new HashMap<String, Object>();
		SqlDataRes sqlDataRes = sqlDataResMapper.findByDataResIdAndDataStatus(dataSourceType,"1");//修改添加启用状态
		SqlDataResType sqlDataResType = null;
		if(sqlDataRes != null){
			sqlDataResType = sqlDataResTypeMapper.findByDataResTypeIdAndDataStutas(sqlDataRes.getDataResTypeId(),"1");//修改添加启用状态
			if(sqlDataResType == null){
				throw new BaseException("sqlDataResType is wrong");
			}
		}else{
			throw new BaseException("sqlDataRes is wrong");
		}
		dsMap.put("dsName", dataSourceType);
		dsMap.put("driver-class-name", sqlDataResType.getDataDriver());
		dsMap.put("url", sqlDataRes.getDataResUrl());
		dsMap.put("username", sqlDataRes.getUsername());
		dsMap.put("password", sqlDataRes.getPassword());
//		dsMap.put("dsName", "123");
//		dsMap.put("driver-class-name", "oracle.jdbc.OracleDriver");
//		dsMap.put("url", "jdbc:oracle:thin:@172.16.91.144:1521:oracle");
//		dsMap.put("username", "system");
//		dsMap.put("password", "admin");
		if(dsMap!=null&&dsMap.size()>=1){
			DataSource ds = buildDataSource(dsMap);
			dataBinder(ds, helper);
			DynamicDataSourceContextHolder.dataSourceIds.add(dataSourceType);
			org.apache.tomcat.jdbc.pool.DataSource datasource = (org.apache.tomcat.jdbc.pool.DataSource) ds;
			DynamicDataSourceContextHolder.dataInfos.put(dataSourceType, datasource.getPoolProperties());
			ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) SpringApplicationContext.CONTEXT;
			// 获取bean工厂并转换为DefaultListableBeanFactory
			beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
			GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("dataSource");
			MutablePropertyValues mpv = beanDefinition.getPropertyValues();
			@SuppressWarnings("unchecked")
			Map<Object, Object> targetDataSources =  (Map<Object, Object>) mpv.get("targetDataSources");
			targetDataSources.put(dsMap.get("dsName").toString(),ds );
			//			mpv.removePropertyValue("targetDataSources");
			//			mpv.addPropertyValue("targetDataSources", targetDataSources);
			//			beanFactory.destroyBean("dataSource");
			//			beanFactory.registerBeanDefinition("dataSource", beanDefinition);
			AbstractRoutingDataSource dbean = (AbstractRoutingDataSource) beanFactory.getBean("dataSource");
			dbean.setTargetDataSources(targetDataSources);
			dbean.afterPropertiesSet();
			logger.info("customDataSources name:{} init success,dsMap:{}");
		}
	}
}
