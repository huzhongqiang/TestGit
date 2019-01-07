package cn.grassinfo.wap.entity;


/*
 * 过去24小时逐小时预报
 */
public class RealWeather{
	
	/*
	 * 相对湿度
	 */
	private Integer humidity;
	/*
	 * 小时最高温
	 */
	private Float maxTemp;
	/*
	 * 站点大气压
	 */
	private Float press;
	/*
	 * 小时极大风速风向
	 */
	private String maxWindD;
	/*
	 * 小时极大风速风力(等级)
	 */
	private Integer maxWindL;
	/*
	 * 小时极大风速风向（角度）
	 */
	private Integer maxWind_D;
	/*
	 * 小时极大风速风速（m/s）
	 */
	private Float maxWind_L;
	/*
	 * 小时最低温
	 */
	private Float minTemp;
	/*
	 * 小时累积降水
	 */
	private Float precipitation;
	/*
	 * 小时平均温度
	 */
	private Float temp;
	/*
	 * 小时 "2016-06-19 16:00:00"
	 */
	private String times;
	/*
	 * 平均风速风向
	 */
	private String windD;
	/*
	 * 平均风速风力(等级)
	 */
	private Integer windL;
	/*
	 * 平均风速风向（角度）
	 */
	private Integer wind_D;
	
	
	public Integer getHumidity() {
		return humidity;
	}
	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}
	public Float getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(Float maxTemp) {
		this.maxTemp = maxTemp;
	}
	public String getMaxWindD() {
		return maxWindD;
	}
	public void setMaxWindD(String maxWindD) {
		this.maxWindD = maxWindD;
	}
	public Integer getMaxWindL() {
		return maxWindL;
	}
	public void setMaxWindL(Integer maxWindL) {
		this.maxWindL = maxWindL;
	}
	public Integer getMaxWind_D() {
		return maxWind_D;
	}
	public void setMaxWind_D(Integer maxWind_D) {
		this.maxWind_D = maxWind_D;
	}
	public Float getMaxWind_L() {
		return maxWind_L;
	}
	public void setMaxWind_L(Float maxWind_L) {
		this.maxWind_L = maxWind_L;
	}
	public Float getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(Float minTemp) {
		this.minTemp = minTemp;
	}
	
	public Float getTemp() {
		return temp;
	}
	public void setTemp(Float temp) {
		this.temp = temp;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getWindD() {
		return windD;
	}
	public void setWindD(String windD) {
		this.windD = windD;
	}
	public Integer getWindL() {
		return windL;
	}
	public void setWindL(Integer windL) {
		this.windL = windL;
	}
	public Integer getWind_D() {
		return wind_D;
	}
	public void setWind_D(Integer wind_D) {
		this.wind_D = wind_D;
	}
	public Float getPrecipitation() {
		return precipitation;
	}
	public void setPrecipitation(Float precipitation) {
		this.precipitation = precipitation;
	}
	public Float getPress() {
		return press;
	}
	public void setPress(Float press) {
		this.press = press;
	}
	

}
