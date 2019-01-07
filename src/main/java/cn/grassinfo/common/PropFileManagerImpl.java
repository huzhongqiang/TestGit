/*
 * PropFileManagerImpl.java created on 05 14, 2010 18:53:34
 * Copyright (c) 2010 by 浙江新能量科技有限公司.
 */
package cn.grassinfo.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Title
 * @author <a href=mailto:dingw@freshpower.cn>dingw</a>
 * @version 1.0
 */
public class PropFileManagerImpl implements PropFileManager{
	private Properties prop;
	private ResourceBundle bundle = null;
	private static Logger logger = LoggerFactory.getLogger(PropFileManagerImpl.class);
	
	/**
	 * 装载Properties配置文件
	 * @param classPath String 相对classes目录路径
	 * @return　Properties
	 */
	public Properties load(String classPath) {
		//logger.debug("load properties file:" + classPath);
		try {
			InputStream inputStream = PropFileManagerImpl.class.getClassLoader().getResourceAsStream(classPath);
			if(null != inputStream){
				prop = new Properties();
				/**
				 * 2017/02/24
				 * 添加编码
				 * huhengbo
				 */
				prop.load(new InputStreamReader(inputStream,"UTF-8"));
			}
		}
		catch (IOException ex) {
			//logger.debug(ex);
			logger.error("load", ex);
			prop = null;
		}
		return prop;
	}
	
	/**
	 * 装载Properties配置文件到ResourceBundle，通过类路径
	 * @param propName String 类路径名
	 * @return 
	 */
	public void loadPropByClassType(String propName) {
		bundle = ResourceBundle.getBundle(propName);
	}
	
	/**
	 * 得到String属性值
	 * @param property String key值
	 * @return String
	 */
	public String getString(String property) {
		String propValue = "";
		try {
			if (null != bundle) {
				propValue = bundle.getString(property);
				propValue= new String(propValue.getBytes("iso8859-1"));
			}else if(null != prop){
				propValue = prop.getProperty(property);
			}
		}
		catch(Exception ex) {
			logger.error("getString", ex);
		}
		return propValue;
	}
	
	/**
	 * 得到String属性值
	 * @param property 属性名称
	 * @param defaultValue String 缺省值
	 * @return
	 */
	public String getString(String property, String defaultValue) {
		String propValue = getString(property);
		if(null == propValue || propValue.length()<1){
			propValue = defaultValue;
		}
		return propValue;
	}
	
	
	
	/**
	 * 得到int属性值
	 * @param property 属性名称
	 * @param defaultValue int 缺省值
	 * @return int
	 */
	public int getInt(String property, int defaultValue) {
		String propValue = getString(property);
		return (propValue.equals(""))? defaultValue:Integer.parseInt(propValue);
	}
	
	/**
	 * 得到long属性值
	 * @param property 属性名称
	 * @param defaultValue long 缺省值
	 * @return long
	 */
	public long getLong(String property, long defaultValue) {
		String propValue = getString(property);
		return (propValue.equals("")) ? defaultValue : Long.parseLong(propValue);
	}
	
	/**
	 * 得到boolean属性值
	 * @param property 属性名称
	 * @param defaultValue 缺省值
	 * @return boolean
	 */
	public boolean getBoolean(String property, boolean defaultValue) {
		String propValue = getString(property);
		return (propValue == null) ? defaultValue : Boolean.valueOf(propValue).booleanValue();
		
	}
	
	/**
	 * 将属性值保存到Map中 name=第一个值 value=第二个值, 类推
	 * @param property 属性名称
	 * @param delim 属性值分隔符
	 * @param properties 属性
	 * @return Map
	 */
	public Map<String, String> toMap(String property, String delim) {
		Map<String, String> map = new HashMap<String, String>();
	    String propValue = getString(property);
	    if (propValue != null) {
	    	StringTokenizer tokens = new StringTokenizer(propValue, delim);
	    	while (tokens.hasMoreTokens()) {
	    		map.put(tokens.nextToken(),
	            tokens.hasMoreElements()? tokens.nextToken() : "");
	    	}
	    }
	    return map;
	}
	
	/**
	 * 将属性值保存到数组中
	 * @param property 属性名称
	 * @param delim 属性值分隔符
	 * @return
	 */
	public String[] toStringArray(String property, String delim) {
		String propValue = getString(property);
		return propValue.split(delim);
	}
	
	/**
	 * 是否没有资源
	 * @return
	 */
	public boolean isNull(){
		if(null == prop && null == bundle){
			return true;
		}else{
			return false;
		}
	}
}
