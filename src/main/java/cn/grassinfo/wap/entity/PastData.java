package cn.grassinfo.wap.entity;

/**
 *实况数据
 * @author wuyh
 *
 */
public class PastData {

	private Double humidity;//相对湿度
	private String AirTemp;//空气温度
	private Integer windL;//风力等级
	private Double wind_L;//风速
	private Double maxTemp;//最高温
	private Double minTemp;//最低温
	private Double temp;//平均温度
	private String windD;//风向文字值
	private Double wind_D;//风向角度知
	private Integer maxWindL;//风力等级
	private Double maxWind_L;//风速
	private String maxWindD;//风向文字值
	private Double maxWind_D;//风向角度知
	private String times;
	private String dayIcon;
	private Double precipitation;
	
	
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getDayIcon() {
		return dayIcon;
	}
	public void setDayIcon(String dayIcon) {
		this.dayIcon = dayIcon;
	}
	public Double getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(Double maxTemp) {
		this.maxTemp = maxTemp;
	}
	public Double getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(Double minTemp) {
		this.minTemp = minTemp;
	}
	public Double getTemp() {
		return temp;
	}
	public void setTemp(Double temp) {
		this.temp = temp;
	}
	public Double getPrecipitation() {
		return precipitation;
	}
	public void setPrecipitation(Double precipitation) {
		this.precipitation = precipitation;
	}
	public String getWindD() {
		return windD;
	}
	public void setWindD(String windD) {
		this.windD = windD;
	}
	
	public Double getWind_D() {
		return wind_D;
	}
	public void setWind_D(Double wind_D) {
		this.wind_D = wind_D;
	}
	public Integer getWindL() {
		return windL;
	}
	public void setWindL(Integer windL) {
		this.windL = windL;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	public Double getWind_L() {
		return wind_L;
	}
	public void setWind_L(Double wind_L) {
		this.wind_L = wind_L;
	}
	public String getAirTemp() {
		return AirTemp;
	}
	public void setAirTemp(String airTemp) {
		AirTemp = airTemp;
	}
	public Integer getMaxWindL() {
		return maxWindL;
	}
	public void setMaxWindL(Integer maxWindL) {
		this.maxWindL = maxWindL;
	}
	public Double getMaxWind_L() {
		return maxWind_L;
	}
	public void setMaxWind_L(Double maxWind_L) {
		this.maxWind_L = maxWind_L;
	}
	public String getMaxWindD() {
		return maxWindD;
	}
	public void setMaxWindD(String maxWindD) {
		this.maxWindD = maxWindD;
	}
	public Double getMaxWind_D() {
		return maxWind_D;
	}
	public void setMaxWind_D(Double maxWind_D) {
		this.maxWind_D = maxWind_D;
	}

	
}
