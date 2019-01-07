package cn.grassinfo.wap.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 农业气象站点实况
 * @author 迪
 */
public class NyqxCurrentData {
	
	private String stationId;//站号
	private String stationName;//站名
	private String address;//地址
	private Double lat;//纬度
	private Double lon;//经度
	private String height;//海拔
	private String observTimes;//监测时间
	private List<NyqxCurrentDataEx> currentData;
	
	/**
	 * @return 站号
	 */
	public String getStationId() {
		return stationId;
	}
	/**
	 * 设置站号
	 * @param stationId
	 */
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	/**
	 * @return 站名
	 */
	public String getStationName() {
		return stationName;
	}
	/**
	 * 设置站名
	 * @param stationName
	 */
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	/**
	 * @return 地址
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置地址
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return 纬度
	 */
	public Double getLat() {
		return lat;
	}
	/**
	 * 设置纬度
	 * @param lat
	 */
	public void setLat(Double lat) {
		this.lat = lat;
	}
	/**
	 * @return 经度
	 */
	public Double getLon() {
		return lon;
	}
	/**
	 * 设置经度
	 * @param lon
	 */
	public void setLon(Double lon) {
		this.lon = lon;
	}
	/**
	 * @return 海拔
	 */
	public String getHeight() {
		return height;
	}
	/**
	 * 设置海拔
	 * @param height
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	/**
	 * @return 监测时间
	 */
	public String getObservTimes() {
		return observTimes;
	}
	/**
	 * 设置监测时间
	 * @param observTimes
	 */
	public void setObservTimes(String observTimes) {
		this.observTimes = observTimes;
	}
	/**
	 * @return 实况数据
	 */
	public List<NyqxCurrentDataEx> getCurrentData() {
		if (currentData == null) {
			currentData = new ArrayList<>();
		}
		return currentData;
	}
	/**
	 * 设置实况数据
	 * @param currentData
	 */
	public void setCurrentData(List<NyqxCurrentDataEx> currentData) {
		this.currentData = currentData;
	}
	
	
}
