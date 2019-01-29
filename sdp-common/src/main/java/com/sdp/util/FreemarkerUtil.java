package com.sdp.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.log4j.Logger;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {

	private static String ROOT_PATH = "";

	private static Logger log4j = Logger.getLogger(FreemarkerUtil.class);

	static {
		try {
			ROOT_PATH = URLDecoder.decode(FreemarkerUtil.class.getResource("/").getPath(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Template getTemplate(String relativePath, String name) {
		try {
			// 通过Freemaker的Configuration读取相应的ftl
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
			cfg.setDefaultEncoding("utf-8");
			// 设置模板读取的路径
			cfg.setDirectoryForTemplateLoading(new File(ROOT_PATH + relativePath));

			// 在模板文件目录中找到名称为name的文件
			Template temp = cfg.getTemplate(name);

			return temp;
		} catch (IOException e) {
			 e.printStackTrace();
			log4j.error("读取模板文件出错。。。");
		}
		return null;
	}
	
	public static Template getTemplate(String relativePath, Boolean isFile) {
		try {
			if(isFile){
				File file = new File(ROOT_PATH + relativePath);
				if(file.exists()){
					// 通过Freemaker的Configuration读取相应的ftl
					Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
					cfg.setDefaultEncoding("utf-8");
					// 设置模板读取的路径
					cfg.setDirectoryForTemplateLoading(file.getParentFile());

					// 在模板文件目录中找到名称为name的文件
					Template temp = cfg.getTemplate(file.getName());

					return temp;
				}
			}else{
				getTemplate(relativePath);
			}
		} catch (IOException e) {
			 e.printStackTrace();
			log4j.error("读取模板文件出错。。。");
		}
		return null;
	}
	
	/**
	 * get Temp By TempStr
	 * @param temp
	 * @return
	 */
	public static Template getTemplate(String sqlTempStr) {
		try {
			// 通过Freemaker的Configuration读取相应的ftl
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
			cfg.setDefaultEncoding("utf-8");
			// 设置模板读取的路径
			
			StringTemplateLoader stringLoader = new StringTemplateLoader();  
	        stringLoader.putTemplate("sqlTemp",sqlTempStr);  
			cfg.setTemplateLoader(stringLoader);

			// 在模板文件目录中找到名称为name的文件
			return cfg.getTemplate("sqlTemp");
		} catch (IOException e) {
			e.printStackTrace();
			log4j.error("读取模板文件出错。。。"+sqlTempStr);
		}
		return null;
	}

	/**
	 * 读取模板文件，整合map数据对象，生成新的文件
	 * 
	 * @param relativePath
	 *            模板的相对路径
	 * @param name
	 *            模板文件名称
	 * @param root
	 *            模板数据map对象
	 * @param output
	 *            整合模板文件和数据map对象，生成的文件输入到stringWriter对象中
	 * @return 成功返回true，异常返回false
	 */
	public static boolean print(String relativePath, String name, Map<String, Object> root, StringWriter output) {
		try {
			// 通过Template可以将模板文件输出到相应的流
			Template temp = FreemarkerUtil.getTemplate(relativePath, name);

			if (temp != null) {
				temp.process(root, new PrintWriter(output));
			}
			// temp.process(root, new PrintWriter(new File("E:/1.txt")));
		} catch (TemplateException e) {
			e.printStackTrace();
			log4j.error("模板文件异常。。。");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			log4j.error("IO异常。。。");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			log4j.error("模板文件数据生成文件错误。。。");
			return false;
		}
		return true;
	}

	/**
	 * 根据模板打印内容
	 * @param temp
	 * @param root
	 * @param output
	 * @return
	 */
	public static boolean print(Template temp, Map<String, Object> root, StringWriter output) {
		try {
			if (temp != null) {
				temp.process(root, new PrintWriter(output));
			}
		} catch (TemplateException e) {
			output.getBuffer().delete(0, output.getBuffer().length());
			output.append(e.getBlamedExpressionString()+" var not Exists!");
			log4j.error("模板文件异常。。。");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			log4j.error("IO异常。。。");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			log4j.error("模板文件数据生成文件错误。。。");
			return false;
		}
		return true;
	}

}