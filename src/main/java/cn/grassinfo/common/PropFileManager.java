/*
 * PropFileManager.java created on 05 14, 2010 18:53:34
 * Copyright (c) 2010 by 浙江新能量科技有限公司.
 */
package cn.grassinfo.common;

import java.util.Map;
import java.util.Properties;

/**
 * Title
 * @author <a href=mailto:dingw@freshpower.cn>dingw</a>
 * @version 1.0
 */
public interface PropFileManager {
	
	/**
	 * 装载Properties配置文件
	 * @param classPath String 相对classes目录路径
	 * @return　Properties
	 */
	public Properties load(String classPath);
	
	/**
	 * 装载Properties配置文件到ResourceBundle，通过类路径
	 * @param propName String 类路径名
	 * @return 
	 */
	public void loadPropByClassType(String propName);
	
	/**
	 * 得到String属性值
	 * @param property String key值
	 * @return String
	 */
	public String getString(String property);
	
	/**
	 * 得到String属性值
	 * @param property 属性名称
	 * @param defaultValue String 缺省值
	 * @return
	 */
	public String getString(String property, String defaultValue);
	
	/**
	 * 得到int属性值
	 * @param property 属性名称
	 * @param defaultValue int 缺省值
	 * @return int
	 */
	public int getInt(String property, int defaultValue);
	
	/**
	 * 得到long属性值
	 * @param property 属性名称
	 * @param defaultValue long 缺省值
	 * @return long
	 */
	public long getLong(String property, long defaultValue);
	
	/**
	 * 得到boolean属性值
	 * @param property 属性名称
	 * @param defaultValue 缺省值
	 * @return boolean
	 */
	public boolean getBoolean(String property, boolean defaultValue);
	
	/**
	 * 将属性值保存到Map中 name=第一个值 value=第二个值, 类推
	 * @param property 属性名称
	 * @param delim 属性值分隔符
	 * @param properties 属性
	 * @return Map
	 */
	public Map<String, String> toMap(String property, String delim);
	
	/**
	 * 将属性值保存到数组中
	 * @param property 属性名称
	 * @param delim 属性值分隔符
	 * @return
	 */
	public String[] toStringArray(String property, String delim);
	
	/**
	 * 是否没有资源
	 * @return
	 */
	public boolean isNull();
}
