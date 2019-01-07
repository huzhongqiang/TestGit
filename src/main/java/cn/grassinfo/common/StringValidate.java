/*
 * StringValidate.java created on 05 14, 2010 18:53:34
 * Copyright (c) 2010 by 浙江新能量科技有限公司.
 */
package cn.grassinfo.common;

/**
 * Title：字符串验证
 *
 * @author <a href=mailto:dingw@freshpower.cn>dingw</a>
 * @version 1.0
 */
public class StringValidate {
    /**
     * 是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (null != str && str.length() > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断是否逻辑字符串TRUE ,FALSE
     * @param str
     * @return
     */
    public static boolean isBoolean(String str){
    	if(!isEmpty(str)){
    		if(str.equalsIgnoreCase("true")||str.equalsIgnoreCase("false")){
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * 判断是否文件路径
     *
     * @param str
     * @return
     */
    public static boolean isFilePath(String str) {
        String osName = System.getProperty("os.name");
        if (-1 != osName.toLowerCase().indexOf("windows")) {
            return str.matches("([A-Za-z]:\\\\[^:?\"><*]*)|([A-Za-z]:/[^:?\"><*]*)");
        }else {
            return str.matches("[A-Za-z]:\\\\[^:?\"><*]*");
        }
    }


    /**
     * 是否邮件地址
     *
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        return str.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }


    /**
     * 判断字符串是否全为数字
     *
     * @param str 需要比较的字符串
     * @return boolean        true:是数字;false:不是数字
     */
    public static boolean isNumeric(String str) {
        return str.matches("[-]?\\d+[.]?\\d*");
    }

    private StringValidate() {

    }
}
