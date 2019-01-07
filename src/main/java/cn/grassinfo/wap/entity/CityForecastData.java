package cn.grassinfo.wap.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 全国主要城市、周边城市天气预报
 * @author ZhangD
 */
public class CityForecastData {
	
	private Map<String, MainCityForecastData> mainCityForecastData;
	private Map<String, AroundCityForecastData> aroundCityForecastData;
	
	/**
	 * @return 全国主要城市天气预报
	 */
	public Map<String, MainCityForecastData> getMainCityForecastData() {
		if (mainCityForecastData == null) {
			mainCityForecastData = new HashMap<String, MainCityForecastData>();
		}
		return mainCityForecastData;
	}
	/**
	 * 设置全国主要城市天气预报
	 * @param mainCityForecastData
	 */
	public void setMainCityForecastData(Map<String, MainCityForecastData> mainCityForecastData) {
		this.mainCityForecastData = mainCityForecastData;
	}
	/**
	 * @return 周边城市天气预报
	 */
	public Map<String, AroundCityForecastData> getAroundCityForecastData() {
		if (aroundCityForecastData == null) {
			aroundCityForecastData = new HashMap<String, AroundCityForecastData>();
		}
		return aroundCityForecastData;
	}
	/**
	 * 设置周边城市天气预报
	 * @param aroundCityForecastData
	 */
	public void setAroundCityForecastData(Map<String, AroundCityForecastData> aroundCityForecastData) {
		this.aroundCityForecastData = aroundCityForecastData;
	}
	
}
