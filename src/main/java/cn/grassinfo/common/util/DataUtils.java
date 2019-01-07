package cn.grassinfo.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 数据处理类
 * @author luofc
 *
 */
public class DataUtils {
	private static final Logger log = Logger
			.getLogger(DataUtils.class);
	
	
	/**
	 * 
	 * @param rain  降雨量
	 * @param cloud  云量
	 * @return
	 */
	public static Map<String,String> getWeatherByCloudAndRain(Double rain,Double cloud){
		Map<String,String> weather=new HashMap<String,String>();
		String resultCode=null;
		String result=null;
		if (rain < 0.1)
        {
            if (cloud >= 7){
            	result = "阴天";
            	resultCode="2.png";
            }
            	
            if (cloud >= 4 && cloud < 7){
            	result = "多云";
            	resultCode="1.png";
            }
            if (cloud >= 2 && cloud < 4){
            	result = "少云";
            	resultCode="1.png";
            }
            if (cloud < 2){
            	result = "晴天";
            	resultCode="0.png";
            }
        }

        if (rain >= 0.1 && rain <= 1.5){
        	result = "小雨";
        	resultCode="7.png";
        }
        if (rain > 1.5 && rain <= 6.9){
        	result = "中雨";
        	resultCode="8.png";
        }
        if (rain > 6.9 && rain <= 14.9){
        	result = "大雨";
        	resultCode="9.png";
        }
        if (rain > 14.9 && rain <= 39.9){
        	result = "暴雨";
        	resultCode="10.png";
        }
        if (rain > 39.9 && rain <= 49.9){
        	result = "大暴雨";
        	resultCode="11.png";
        }
        if (rain > 49.9){
        	result = "特大暴雨";
        	resultCode="12.png";
        }
	
        weather.put(result, resultCode);
		return weather;
	}
	
	/**
	 * 预警转图片编码
	 * @author wuyh
	 * @param name
	 * @param color
	 * @return
	 */
	public static String getEarlyWarningImgCode(String name,String color){
		String number="";
		switch (name) {
		case "台风":
			number="01";
			break;
		case "暴雨":
			number="02";
			break;
		case "暴雪":
			number="03";
			break;
		case "寒潮":
			number="04";
			break;
		case "大风":
			number="05";
			break;
		case "大雾":
			number="06";
			break;
		case "雷电":
			number="07";
			break;
		case "冰雹":
			number="08";
			break;
		case "霜冻":
			number="09";
			break;
		case "高温":
			number="10";
			break;
		case "干旱":
			number="11";
			break;
		case "道路结冰":
			number="12";
			break;
		case "路面结冰":
			number="12";
			break;
		case "霾":
			number="13";
			break;
		case "沙尘暴":
			number="14";
		case "重污染":
			number="15";
			break;
		default:
			break;
		}
		
		switch (color) {
		case "蓝":
			number+="01";
			break;
		case "黄":
			number+="02";
			break;
		case "橙":
			number+="03";
			break;
		case "红":
			number+="04";
			break;
		default:
			break;
		}
		return number;
		
	}
	
	/**
	 * 时间转字符串
	 * @author wuyh
	 * @param date
	 * @param sf
	 * @return
	 */
	public static String getStringByDate(Date date,String sf) {
		String str="";
		try {
			SimpleDateFormat format = new SimpleDateFormat(sf);
			str= format.format(date);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("getStringByDate", e);
		}
		return str;
		
	}

}
