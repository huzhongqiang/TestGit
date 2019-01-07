package cn.grassinfo.common.utils;

import cn.grassinfo.common.util.Utils;

/**
 * 数据处理工具类
 * @author luofc
 *
 */
public class DataFormateUtils {
	/**
	 * 包含几位小数
	 * @param number
	 * @return
	 */
	public static int positionNumber(String number){
		int position=0;
		if(null!=number&&!Utils.isEmpty(number)&&number.indexOf(".")!=-1){
			position = number.length() - (number.indexOf(".")+1);
			if(position==1&&number.indexOf(".")==1&&Integer.valueOf(number.substring(position+1))==0){
				position=0;
			}
		}
		
		return position;
	}

	public static void main(String[] args) {
		System.out.println(positionNumber("2.0"));
		
	}
}
