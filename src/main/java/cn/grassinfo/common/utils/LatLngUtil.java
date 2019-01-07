package cn.grassinfo.common.utils;

import java.math.BigDecimal;

/**
 * 坐标点计算
 * @author chenlong
 *
 */
public class LatLngUtil {
	
	private static final double EARTH_RADIUS = 6378137;//地球半径
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double GetDistance(double lng1, double lat1, double lng2,
			double lat2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	public static int compareDouble(Double d1, Double d2) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return compare(b1, b2);
	}
	
	public static int compare(BigDecimal val1, BigDecimal val2) {  
	    int result = 0;  
	    if (val1.compareTo(val2) < 0) {  
	        result = -1;  
	    } 
	    if (val1.compareTo(val2) == 0) {
	    	result = 0;
	    }
	    if (val1.compareTo(val2) > 0) {  
	        result = 1;  
	    }  
	    return result;  
	}
	
	public static int getInterval(int year, String date1, String date2) {
		int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		int interval = 0;
		int month1 = Integer.valueOf("0".equals(date1.substring(0, 1))?date1.substring(1, 2):date1.substring(0, 2));
		int day1 = Integer.valueOf("0".equals(date1.substring(2, 3))?date1.substring(3, 4):date1.substring(2, 4));
		int month2 = Integer.valueOf("0".equals(date2.substring(0, 1))?date2.substring(1, 2):date2.substring(0, 2));
		int day2 = Integer.valueOf("0".equals(date2.substring(2, 3))?date2.substring(3, 4):date2.substring(2, 4));
		if (isLeapYear(year)) {
			days[1] = 29;
		}
		if (date1.compareTo(date2) <= 0) {
			for (int i=month1+1;i<month2-1;i++) {
				interval += days[i];
			}
			interval += days[month1] - day1 + day2;
		}
		else {
			for (int i=month1+1;i<12;i++) {
				interval += days[i];
			}
			for (int i=0;i<month2-1;i++) {
				interval += days[i];
			}
			interval += days[month1] - day1 + day2;
		}
		return interval;
	}
	
	public static boolean isLeapYear(int year) {
		boolean flag = false;
		if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
			flag = true;
		}
		return flag;
	}
}
