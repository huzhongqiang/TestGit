package cn.grassinfo.common.utils;

import java.util.HashMap;
import java.util.Map;

public class WindUtils {
	
	//风速m/s
	public static String getWindValueV(Double v) {
		String value = null;
		if (LatLngUtil.compareDouble(v, 0.2) <= 0) {
			value = "0";
		}
		else if (LatLngUtil.compareDouble(v, 1.5) <= 0) {
			value = "1";
		}
		else if (LatLngUtil.compareDouble(v, 3.3) <= 0) {
			value = "2";
		}
		else if (LatLngUtil.compareDouble(v, 5.4) <= 0) {
			value = "3";
		}
		else if (LatLngUtil.compareDouble(v, 7.9) <= 0) {
			value = "4";
		}
		else if (LatLngUtil.compareDouble(v,10.7) <= 0) {
			value = "5";
		}
		else if (LatLngUtil.compareDouble(v,13.8) <= 0) {
			value = "6";
		}
		else if (LatLngUtil.compareDouble(v,17.1) <= 0) {
			value = "7";
		}
		else if (LatLngUtil.compareDouble(v,20.7) <= 0) {
			value = "8";
		}
		else if (LatLngUtil.compareDouble(v,24.4) <= 0) {
			value = "9";
		}
		else if (LatLngUtil.compareDouble(v,28.4) <= 0) {
			value = "10";
		}
		else if (LatLngUtil.compareDouble(v,32.6) <= 0) {
			value = "11";
		}
		else if (LatLngUtil.compareDouble(v,36.9) <= 0) {
			value = "12";
		}
		else if (LatLngUtil.compareDouble(v,41.4) <= 0) {
			value = "13";
		}
		else if (LatLngUtil.compareDouble(v,46.1) <= 0) {
			value = "14";
		}
		else if (LatLngUtil.compareDouble(v,50.9) <= 0) {
			value = "15";
		}
		else if (LatLngUtil.compareDouble(v,56.0) <= 0) {
			value = "16";
		}
		else if (LatLngUtil.compareDouble(v,61.2) <= 0) {
			value = "17";
		}
		return value+"级";
	}
	
	public static String getWindValueD(Double angle) {
		String value = null;
		if (LatLngUtil.compareDouble(angle, 22.5) < 0
				|| LatLngUtil.compareDouble(angle, 337.5) > 0) {
			value = "北风";
		}
		if (LatLngUtil.compareDouble(angle, 22.5) > 0
				&& LatLngUtil.compareDouble(angle, 67.5) < 0) {
			value = "东北风";
		}
		if (LatLngUtil.compareDouble(angle, 67.5) > 0
				&& LatLngUtil.compareDouble(angle, 112.5) < 0) {
			value = "东风";
		}
		if (LatLngUtil.compareDouble(angle, 112.5) > 0
				&& LatLngUtil.compareDouble(angle, 157.5) < 0) {
			value = "东南风";
		}
		if (LatLngUtil.compareDouble(angle, 157.5) > 0
				&& LatLngUtil.compareDouble(angle, 202.5) < 0) {
			value = "南风";
		}
		if (LatLngUtil.compareDouble(angle, 202.5) > 0
				&& LatLngUtil.compareDouble(angle, 247.5) < 0) {
			value = "西南风";
		}
		if (LatLngUtil.compareDouble(angle, 247.5) > 0
				&& LatLngUtil.compareDouble(angle, 292.5) < 0) {
			value = "西风";
		}
		if (LatLngUtil.compareDouble(angle, 292.5) > 0
				&& LatLngUtil.compareDouble(angle, 337.5) < 0) {
			value = "西北风";
		}
		return value;
	}
	
	public final static Map weatherInfoMap = new HashMap() {
		private static final long serialVersionUID = -415466428817287193L;
		{   
		    put("0", "晴");    
		    put("1", "多云");    
		    put("2", "阴");    
		    put("3", "阵雨");
		    put("4", "雷阵雨");    
		    put("5", "冰雹");
		    put("6", "雨夹雪");
		    put("7", "小雨");
		    put("8", "中雨");    
		    put("9", "大雨");    
		    put("10", "暴雨");    
		    put("11", "大暴雨");
		    put("12", "特大暴雨");    
		    put("13", "阵雪");
		    put("14", "小雪");
		    put("15", "中雪");
		    put("16", "大雪");    
		    put("17", "暴雪");
		    put("18", "雾");    
		    put("19", "冻雨");
		    put("20", "沙尘暴");
		    put("21", "小到中雨");
		    put("22", "中到大雨");
		    put("23", "大到暴雨");
		    put("24", "暴雨到大暴雨");
		    put("25", "大暴雨到特大暴雨");
		    put("26", "小到中雪");    
		    put("27", "中到大雪");
		    put("28", "大到暴雪");    
		    put("29", "浮尘");
		    put("30", "扬沙");
		    put("31", "强沙尘暴");
		    put("53", "霾");
		}
	};

	
	/**
	 * 根据天气取CODE
	 */
	public final static Map weatherInfoMap2 = new HashMap() {
		private static final long serialVersionUID = -415466428817287193L;
		{   
			 put("晴","0.png");    
			    put("多云","1.png");    
			    put("阴","2.png");    
			    put("阵雨","3.png");
			    put("雷阵雨","4.png");
			    put("雷阵雨拌冰雹","4.png");    
			    put("冰雹","5.png");
			    put("雨夹雪","6.png");
			    put("小雨","7.png");
			    put("中雨","8.png");    
			    put("大雨","9.png");    
			    put("暴雨","10.png");    
			    put("大暴雨", "11.png");
			    put("特大暴雨","12.png");    
			    put("阵雪","13.png");
			    put("小雪","14.png");
			    put("中雪","15.png");
			    put("大雪","16.png");    
			    put("暴雪","17.png");
			    put("雾","18.png");    
			    put("冻雨","19.png");
			    put("沙尘暴","20.png");
			    put("小到中雨","21.png");
			    put("小雨到中雨","21.png");
			    put("中雨到大雨", "22.png");
			    put("中到大雨", "22.png");
			    put("大雨到暴雨", "23.png");
			    put("大到暴雨", "23.png");
			    put("暴雨到大暴雨", "24.png");
			    put("大暴到特大暴", "25.png");
			    put("大暴雨到特大暴雨", "25.png");
			    put("小到中雪","26.png");    
			    put("中到大雪", "27.png");
			    put("大到暴雪", "28.png");
			    put("小雪到中雪","26.png");    
			    put("中雪到大雪", "27.png");
			    put("大雪到暴雪", "28.png");   
			    put("浮尘", "29.png");
			    put("扬沙", "30.png");
			    put("强沙尘暴", "31.png");
			    put("霾", "53.png");
		}
	};
	
	public final static Map weatherInfoMapWap = new HashMap() {
		private static final long serialVersionUID = -415466428817287193L;
		{   
		    put("晴","00.png");    
		    put("多云","01.png");    
		    put("阴","02.png");    
		    put("阵雨","03.png");
		    put("雷阵雨","04.png");    
		    put("冰雹","05.png");
		    put("雨夹雪","06.png");
		    put("小雨","07.png");
		    put("中雨","08.png");    
		    put("大雨","09.png");    
		    put("暴雨","10.png");    
		    put("大暴雨", "11.png");
		    put("特大暴雨","12.png");    
		    put("阵雪","13.png");
		    put("小雪","14.png");
		    put("中雪","15.png");
		    put("大雪","16.png");    
		    put("暴雪","17.png");
		    put("雾","18.png");    
		    put("冻雨","19.png");
		    put("沙尘暴","20.png");
		    put("小到中雨","21.png");
		    put("中到大雨", "22.png");
		    put("大到暴雨", "23.png");
		    put("暴雨到大暴雨", "24.png");
		    put("大暴雨到特大暴雨", "25.png");
		    put("小到中雪","26.png");    
		    put("中到大雪", "27.png");
		    put("大到暴雪", "28.png");    
		    put("浮尘", "29.png");
		    put("扬沙", "30.png");
		    put("强沙尘暴", "31.png");
		    put("霾", "53.png");
		}
	};

	/**
	 * 风速转风级
	 * @param num 风速
	 * @return
	 */
	public static Integer getWindL(Double num) {
		Integer windLevel = null;
		if (num == null || num<0) {
			return -999;
		}
		if (num >= 61.2) {
			windLevel = 18;
		}
		else if (num >= 56.1 && num < 61.2) {
			windLevel = 17;
		}
		else if (num >= 51.0 && num < 56.1) {
			windLevel = 16;
		}
		else if (num >= 46.2 && num < 51.0) {
			windLevel = 15;
		}
		else if (num >= 41.5 && num < 46.2) {
			windLevel = 14;
		}
		else if (num >= 37.0 && num < 41.5) {
			windLevel = 13;
		}
		else if (num >= 32.7 && num < 37.0) {
			windLevel = 12;
		}
		else if (num >= 28.5 && num < 32.7) {
			windLevel = 11;
		}
		else if (num >= 24.5 && num < 28.5) {
			windLevel = 10;
		}
		else if (num >= 20.8 && num < 24.5) {
			windLevel = 9;
		}
		else if (num >= 17.2 && num < 20.8) {
			windLevel = 8;
		}
		else if (num >= 13.9 && num < 17.2) {
			windLevel = 7;
		}
		else if (num >= 10.8 && num < 13.9) {
			windLevel = 6;
		}
		else if (num >= 8.0 && num < 10.8) {
			windLevel = 5;
		}
		else if (num >= 5.5 && num < 8.0) {
			windLevel = 4;
		}
		else if (num >= 3.4 && num < 5.5) {
			windLevel = 3;
		}
		else if (num >= 1.6 && num < 3.4) {
			windLevel = 2;
		}
		else if (num >= 0.3 && num < 1.6) {
			windLevel = 1;
		}
		else if (num >= 0.0 && num < 0.3) {
			windLevel = 0;
		}
		return windLevel;
	}
	
	/**
	 * 风向角度转方向文字
	 * @param num 风向度数（整数值），
	 * @return
	 */
	public static String getWindD(Integer num){
		if (num == null || num<0) {
			return "无";
		}
		String [] windDirects = new String[] { "北", "东北偏北", "东北", "东北偏东", "东", "东南偏东", "东南", "东南偏南", "南", "西南偏南", "西南", "西南偏西", "西", "西北偏西", "西北", "西北偏北", "北" };
        String result = windDirects[(int)((num + 11.24) / 22.5)];//北风348.76-11.25
		return result;
	}
	
	/**
	 * 风向角度转方向文字
	 * @param num 风向度数（小数值），
	 * @return
	 */
	public static String getWindD(Double num){
		if (num == null || num<0) {
			return "无";
		}
		String [] windDirects = new String[] { "北", "东北偏北", "东北", "东北偏东", "东", "东南偏东", "东南", "东南偏南", "南", "西南偏南", "西南", "西南偏西", "西", "西北偏西", "西北", "西北偏北", "北" };
		String result = windDirects[(int)((num + 11.24) / 22.5)];//北风348.76-11.25
		return result;
	}
	
	/**
	 * 风速转文字描述
	 * @param num
	 * @return
	 */
	public static String getWindS(Double num) {
		String result = null;
		if (num == null || num<0) {
			return "无";
		}
		if (num >= 32.7) {
			result = "飓风";
		}
		else if (num >= 28.5 && num < 32.7) {
			result = "暴风";
		}
		else if (num >= 24.5 && num < 28.5) {
			result = "狂风";
		}
		else if (num >= 20.8 && num < 24.5) {
			result = "烈风";
		}
		else if (num >= 17.2 && num < 20.8) {
			result = "大风";
		}
		else if (num >= 13.9 && num < 17.2) {
			result = "疾风";
		}
		else if (num >= 10.8 && num < 13.9) {
			result = "强风";
		}
		else if (num >= 8.0 && num < 10.8) {
			result = "劲风";
		}
		else if (num >= 5.5 && num < 8.0) {
			result = "和风";
		}
		else if (num >= 3.4 && num < 5.5) {
			result = "微风";
		}
		else if (num >= 1.6 && num < 3.4) {
			result = "轻风";
		}
		else if (num >= 0.3 && num < 1.6) {
			result = "软风";
		}
		else if (num >= 0.0 && num < 0.3) {
			result = "无风";
		}
		return result;
	}
	
	/**
	 * 
	 * @param num 风向度数，
	 * @return
	 */
	public static String getWindd(int num){
		String [] windDirects = new String[] { "北", "东北偏北", "东北", "东北偏东", "东", "东南偏东", "东南", "东南偏南", "南", "西南偏南", "西南", "西南偏西", "西", "西北偏西", "西北", "西北偏北", "北" };
        String result = windDirects[(int)((num + 11.24) / 22.5)];//北风348.76-11.25
		return result+"风";
	}
	
	/**
	 * 根据风向代码转换描述文字
	 * 2017-02-22
	 * huhengbo
	 * 顺时针东北，东。。。。。北
	 * @param code
	 * @return
	 */
	public static String getWindDStr(int code){
		String str = "";
		if (code<1 || code >8) {
			return "无";
		}
		switch (code) {
		case 1:
			str = "东北风";
			break;
		case 2:
//			str = "东风";
			str = "偏东风";
			break;
		case 3:
			str = "东南风";
			break;
		case 4:
//			str = "南风";
			str = "偏南风";
			break;
		case 5:
			str = "西南风";
			break;
		case 6:
//			str = "西风";
			str = "偏西风";
			break;
		case 7:
			str = "西北风";
			break;
		case 8:
//			str = "北风";
			str = "偏北风";
			break;

		default:
			break;
		}
		return str;
		
	}
	
	/**
	 * 根据风速代码转换描述文字
	 * 2017-02-22
	 * huhengbo
	 * 风速0是 小于3级，1是 3-4 ，2时4-5，以此类推
	 * @param code
	 * @return
	 */
	public static String getWindVStr(int code){
		String str = "";
		if (code<0) {
			return "无";
		}
		switch (code) {
		case 0:
//			str = "小于3级"; 
			str = "3级";
			break;
		default:
			str = (code+2)+"到"+(code+3)+"级";
			break;
		}
		return str;
	}
}
