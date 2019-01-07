package cn.grassinfo.wap.entity;

/**
 * 周边城市天气预报
 * @author ZhangD
 */
public class AroundCityForecastData {

	private String time;
	private String forecastDate;
	private String forecastTime;
	private String stationId;
	private String city;
	private Integer weatherIcon1;//存天气编号
	private Integer weatherIcon2;
	private String weatherStr;
	private Double tmin;//存温度结果
	private Double tmax;
	private Double tempMin1;//存数据
	private Double tempMin2;
	private Double tempMax1;
	private Double tempMax2;
	private Integer windD1;//存编号
	private Integer windD2;
	private String wind_D1;
	private String wind_D2;
	private Integer windS1;//存编号
	private Integer windS2;
	private String wind_S1;
	private String wind_S2;
	private String windDS;
	
	/**
	 * @return 字段“时段”
	 */
	public String getTime() {
		return time;
	}
	/**
	 * 字段“时段”
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return 预报时间
	 */
	public String getForecastDate() {
		return forecastDate;
	}
	/**
	 * 设置预报时间
	 * @param forecastDate
	 */
	public void setForecastDate(String forecastDate) {
		this.forecastDate = forecastDate;
	}
	/**
	 * @return 预报时次
	 */
	public String getForecastTime() {
		return forecastTime;
	}
	/**
	 * 设置预报时次
	 * @param forecastTime
	 */
	public void setForecastTime(String forecastTime) {
		this.forecastTime = forecastTime;
	}
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
	 * @return 城市名称
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置城市名称
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return 字段“天气1”
	 */
	public Integer getWeatherIcon1() {
		return weatherIcon1;
	}
	/**
	 * 字段“天气1”
	 * @param weatherIcon1
	 */
	public void setWeatherIcon1(Integer weatherIcon1) {
		this.weatherIcon1 = weatherIcon1;
	}
	/**
	 * @return 字段“天气2”
	 */
	public Integer getWeatherIcon2() {
		return weatherIcon2;
	}
	/**
	 * 字段“天气2”
	 * @param weatherIcon2
	 */
	public void setWeatherIcon2(Integer weatherIcon2) {
		this.weatherIcon2 = weatherIcon2;
	}
	/**
	 * @return 天气状况
	 */
	public String getWeatherStr() {
		return weatherStr;
	}
	/**
	 * 天气状况
	 * @param weatherStr
	 */
	public void setWeatherStr(String weatherStr) {
		this.weatherStr = weatherStr;
	}
	/**
	 * @return 最低温
	 */
	public Double getTmin() {
		return tmin;
	}
	/**
	 * 最低温
	 * @param tMin
	 */
	public void setTmin(Double tmin) {
		this.tmin = tmin;
	}
	/**
	 * @return 最高温
	 */
	public Double getTmax() {
		return tmax;
	}
	/**
	 * 最高温
	 * @param tMax
	 */
	public void setTmax(Double tmax) {
		this.tmax = tmax;
	}
	/**
	 * @return 字段“最低1”
	 */
	public Double getTempMin1() {
		return tempMin1;
	}
	/**
	 * 字段“最低1”
	 * @param tempMin1
	 */
	public void setTempMin1(Double tempMin1) {
		this.tempMin1 = tempMin1;
	}
	/**
	 * @return 字段“最低2”
	 */
	public Double getTempMin2() {
		return tempMin2;
	}
	/**
	 * 字段“最低2”
	 * @param tempMin2
	 */
	public void setTempMin2(Double tempMin2) {
		this.tempMin2 = tempMin2;
	}
	/**
	 * @return 字段“最高1”
	 */
	public Double getTempMax1() {
		return tempMax1;
	}
	/**
	 * 字段“最高1”
	 * @param tempMax1
	 */
	public void setTempMax1(Double tempMax1) {
		this.tempMax1 = tempMax1;
	}
	/**
	 * @return 字段“最高2”
	 */
	public Double getTempMax2() {
		return tempMax2;
	}
	/**
	 * 字段“最高2”
	 * @param tempMax2
	 */
	public void setTempMax2(Double tempMax2) {
		this.tempMax2 = tempMax2;
	}
	/**
	 * @return 字段“风向1”
	 */
	public Integer getWindD1() {
		return windD1;
	}
	/**
	 * 字段“风向1”
	 * @param windD1
	 */
	public void setWindD1(Integer windD1) {
		this.windD1 = windD1;
	}
	/**
	 * @return 字段“风向2”
	 */
	public Integer getWindD2() {
		return windD2;
	}
	/**
	 * 字段“风向2”
	 * @param windD2
	 */
	public void setWindD2(Integer windD2) {
		this.windD2 = windD2;
	}
	/**
	 * @return 风向说明文字1
	 */
	public String getWind_D1() {
		return wind_D1;
	}
	/**
	 * 风向说明文字1
	 * @param wind_D1
	 */
	public void setWind_D1(String wind_D1) {
		this.wind_D1 = wind_D1;
	}
	/**
	 * @return 风向说明文字2
	 */
	public String getWind_D2() {
		return wind_D2;
	}
	/**
	 * 风向说明文字2
	 * @param wind_D2
	 */
	public void setWind_D2(String wind_D2) {
		this.wind_D2 = wind_D2;
	}
	/**
	 * @return 字段“风速1”
	 */
	public Integer getWindS1() {
		return windS1;
	}
	/**
	 * 字段“风速1”
	 * @param windS1
	 */
	public void setWindS1(Integer windS1) {
		this.windS1 = windS1;
	}
	/**
	 * @return 字段“风速2”
	 */
	public Integer getWindS2() {
		return windS2;
	}
	/**
	 * 字段“风速2”
	 * @param windS2
	 */
	public void setWindS2(Integer windS2) {
		this.windS2 = windS2;
	}
	/**
	 * @return 风速说明文字1
	 */
	public String getWind_S1() {
		return wind_S1;
	}
	/**
	 * 风速说明文字1
	 * @param wind_S1
	 */
	public void setWind_S1(String wind_S1) {
		this.wind_S1 = wind_S1;
	}
	/**
	 * @return 风速说明文字2
	 */
	public String getWind_S2() {
		return wind_S2;
	}
	/**
	 * 风速说明文字2
	 * @param wind_S2
	 */
	public void setWind_S2(String wind_S2) {
		this.wind_S2 = wind_S2;
	}
	/**
	 * @return 风向风速说明文字
	 */
	public String getWindDS() {
		return windDS;
	}
	/**
	 * 风向风速说明文字
	 * @param windDS
	 */
	public void setWindDS(String windDS) {
		this.windDS = windDS;
	}
	
}
