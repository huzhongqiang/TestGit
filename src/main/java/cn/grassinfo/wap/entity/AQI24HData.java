package cn.grassinfo.wap.entity;

import java.util.List;

public class AQI24HData {
	private String stationId;//站点编号
	private String observTimes;//发布时间
	private List<AQIData> aqiDatas;//数据
	private AQIData avgAQIData;//过去24小时平均数据
	private PastData avgPastData;//过去24小时平均天气数据
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getObservTimes() {
		return observTimes;
	}
	public void setObservTimes(String observTimes) {
		this.observTimes = observTimes;
	}
	public List<AQIData> getAqiDatas() {
		return aqiDatas;
	}
	public void setAqiDatas(List<AQIData> aqiDatas) {
		this.aqiDatas = aqiDatas;
	}
	public AQIData getAvgAQIData() {
		return avgAQIData;
	}
	public void setAvgAQIData(AQIData avgAQIData) {
		this.avgAQIData = avgAQIData;
	}
	public PastData getAvgPastData() {
		return avgPastData;
	}
	public void setAvgPastData(PastData avgPastData) {
		this.avgPastData = avgPastData;
	}
	
	

}
