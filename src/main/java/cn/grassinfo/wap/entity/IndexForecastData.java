package cn.grassinfo.wap.entity;

/**
 * 首页预报数据结构
 * @author 迪
 */
public class IndexForecastData {
	
	private String forecastTime;
	private String dayTime;
	private String dayTimeStr;
	private String monthDay;
	private String weekday;
	private String dayIcon;
	private String dayStr;
	private String nightIcon;
	private String nightStr;
	private Double t;
	private Double Tmax;
	private Double Tmin;
	private Double wind_D;
	private String windD;
	private Integer windL;
	private Integer wind_L;
	
	/**
	 * @return 预报时间
	 */
	public String getForecastTime() {
		return forecastTime;
	}
	/**
	 * 设置预报时间
	 * @param forecastTime
	 */
	public void setForecastTime(String forecastTime) {
		this.forecastTime = forecastTime;
	}
	/**
	 * @return 时间（月、日、时）
	 */
	public String getDayTime() {
		return dayTime;
	}
	/**
	 * 设置时间（月、日、时）
	 * @param dayTime
	 */
	public void setDayTime(String dayTime) {
		this.dayTime = dayTime;
	}
	/**
	 * @return 时间段字符
	 */
	public String getDayTimeStr() {
		return dayTimeStr;
	}
	/**
	 * 设置时间段字符
	 * @param dayTimeStr
	 */
	public void setDayTimeStr(String dayTimeStr) {
		this.dayTimeStr = dayTimeStr;
	}
	/**
	 * @return 日期（月、日）
	 */
	public String getMonthDay() {
		return monthDay;
	}
	/**
	 * 设置日期（月、日）
	 * @param monthDay
	 */
	public void setMonthDay(String monthDay) {
		this.monthDay = monthDay;
	}
	/**
	 * @return 星期几
	 */
	public String getWeekday() {
		return weekday;
	}
	/**
	 * 设置星期几
	 * @param weekday
	 */
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	/**
	 * @return 白天天气
	 */
	public String getDayIcon() {
		return dayIcon;
	}
	/**
	 * 设置白天天气
	 * @param dayIcon
	 */
	public void setDayIcon(String dayIcon) {
		this.dayIcon = dayIcon;
	}
	/**
	 * @return 白天天气说明
	 */
	public String getDayStr() {
		return dayStr;
	}
	/**
	 * 设置白天天气说明
	 * @param dayStr
	 */
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	/**
	 * @return 夜间天气
	 */
	public String getNightIcon() {
		return nightIcon;
	}
	/**
	 * 设置夜间天气
	 * @param nightIcon
	 */
	public void setNightIcon(String nightIcon) {
		this.nightIcon = nightIcon;
	}
	/**
	 * @return 夜间天气说明
	 */
	public String getNightStr() {
		return nightStr;
	}
	/**
	 * 设置夜间天气说明
	 * @param nightStr
	 */
	public void setNightStr(String nightStr) {
		this.nightStr = nightStr;
	}
	/**
	 * @return 平均温度
	 */
	public Double getT() {
		return t;
	}
	/**
	 * 设置平均温度
	 * @param t
	 */
	public void setT(Double t) {
		this.t = t;
	}
	/**
	 * @return 最高温
	 */
	public Double getTmax() {
		return Tmax;
	}
	/**
	 * 设置最高温
	 * @param tmax
	 */
	public void setTmax(Double tmax) {
		Tmax = tmax;
	}
	/**
	 * @return 最低温
	 */
	public Double getTmin() {
		return Tmin;
	}
	/**
	 * 设置最低温
	 * @param tmin
	 */
	public void setTmin(Double tmin) {
		Tmin = tmin;
	}
	/**
	 * @return 风向角度
	 */
	public Double getWind_D() {
		return wind_D;
	}
	/**
	 * 设置风向角度
	 * @param wind_D
	 */
	public void setWind_D(Double wind_D) {
		this.wind_D = wind_D;
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
	 * @return 风级
	 */
	public Integer getWindL() {
		return windL;
	}
	/**
	 * 设置风级
	 * @param windL
	 */
	public void setWindL(Integer windL) {
		this.windL = windL;
	}
	public Integer getWind_L() {
		return wind_L;
	}
	public void setWind_L(Integer wind_L) {
		this.wind_L = wind_L;
	}
	
}
