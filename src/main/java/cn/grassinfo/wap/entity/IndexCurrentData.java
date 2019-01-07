package cn.grassinfo.wap.entity;

/**
 * 首页实况数据结构
 * @author 迪
 */
public class IndexCurrentData {
	private Double MaxTemp;
	public Double getMaxTemp() {
		return MaxTemp;
	}
	public void setMaxTemp(Double maxTemp) {
		MaxTemp = maxTemp;
	}
	public Double getMinTemp() {
		return MinTemp;
	}
	public void setMinTemp(Double minTemp) {
		MinTemp = minTemp;
	}
	private Double MinTemp;
	private String observTimes;
	private Double curTemp;
	private String todayTempRange;
	private String windD;
	private String windS;
	private Double wind_S;
	private String dayIcon;
	private String aqi;
	private Double humidity;
	private Double pressure;
	private Integer precipitation;
	private Integer AQI;
	private Integer windL;
	public Integer getAQI() {
		return AQI;
	}
	public void setAQI(Integer aQI) {
		AQI = aQI;
	}
	/**
	 * @return 观测时间
	 */
	public String getObservTimes() {
		return observTimes;
	}
	/**
	 * 设置观测时间
	 * @param observTimes
	 */
	public void setObservTimes(String observTimes) {
		this.observTimes = observTimes;
	}
	/**
	 * @return 当前温度
	 */
	public Double getCurTemp() {
		return curTemp;
	}
	/**
	 * 设置当前温度
	 * @param curTemp
	 */
	public void setCurTemp(Double curTemp) {
		this.curTemp = curTemp;
	}
	/**
	 * @return 今日温度范围
	 */
	public String getTodayTempRange() {
		return todayTempRange;
	}
	/**
	 * 设置今日温度范围
	 * @param todayTempRange
	 */
	public void setTodayTempRange(String todayTempRange) {
		this.todayTempRange = todayTempRange;
	}
	/**
	 * @return 风向
	 */
	public String getWindD() {
		return windD;
	}
	/**
	 * 设置风向
	 * @param windD
	 */
	public void setWindD(String windD) {
		this.windD = windD;
	}
	/**
	 * @return 风力
	 */
	public String getWindS() {
		return windS;
	}
	/**
	 * 设置风力
	 * @param windS
	 */
	public void setWindS(String windS) {
		this.windS = windS;
	}
	/**
	 * @return 天气
	 */
	public String getDayIcon() {
		return dayIcon;
	}
	/**
	 * 设置天气
	 * @param dayIcon
	 */
	public void setDayIcon(String dayIcon) {
		this.dayIcon = dayIcon;
	}
	/**
	 * @return 空气质量AQI
	 */
	public String getAqi() {
		return aqi;
	}
	/**
	 * 设置空气质量AQI
	 * @param aqi
	 */
	public void setAqi(String aqi) {
		this.aqi = aqi;
	}
	/**
	 * @return 湿度
	 */
	public Double getHumidity() {
		return humidity;
	}
	/**
	 * 设置湿度
	 * @param humidity
	 */
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	/**
	 * @return 气压
	 */
	public Double getPressure() {
		return pressure;
	}
	/**
	 * 设置气压
	 * @param pressure
	 */
	public void setPressure(Double pressure) {
		this.pressure = pressure;
	}
	public Double getWind_S() {
		return wind_S;
	}
	public void setWind_S(Double windSpeed) {
		this.wind_S = windSpeed;
	}
	public Integer getPrecipitation() {
		return precipitation;
	}
	public void setPrecipitation(Integer precipitation) {
		this.precipitation = precipitation;
	}
	public Integer getWindL() {
		return windL;
	}
	public void setWindL(Integer windL) {
		this.windL = windL;
	}
	
	
	
	
}
