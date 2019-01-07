package cn.grassinfo.wap.entity;

import java.util.List;

/**
 * 首页实况与预报数据结构
 * @author 迪
 */
public class IndexCurrentForecastayData {

	private String stationId;
	private IndexCurrentData currentData;
	private List<IndexForecastData> forecastData;
	private List<IndexForecastData> forecast24HData;
	
	/**
	 * @return 台站号
	 */
	public String getStationId() {
		return stationId;
	}
	/**
	 * 设置台站号
	 * @param stationId
	 */
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	/**
	 * @return 实况数据
	 */
	public IndexCurrentData getCurrentData() {
		return currentData;
	}
	/**
	 * 设置实况数据
	 * @param currentData
	 */
	public void setCurrentData(IndexCurrentData currentData) {
		this.currentData = currentData;
	}
	/**
	 * @return 10天预报数据
	 */
	public List<IndexForecastData> getForecastData() {
		return forecastData;
	}
	/**
	 * 设置10天预报数据
	 * @param forecastData
	 */
	public void setForecastData(List<IndexForecastData> forecastData) {
		this.forecastData = forecastData;
	}
	/**
	 * @return 24小时预报数据
	 */
	public List<IndexForecastData> getForecast24HData() {
		return forecast24HData;
	}
	/**
	 * 设置24小时预报数据
	 * @param forecast24hData
	 */
	public void setForecast24HData(List<IndexForecastData> forecast24hData) {
		forecast24HData = forecast24hData;
	}
	
}
