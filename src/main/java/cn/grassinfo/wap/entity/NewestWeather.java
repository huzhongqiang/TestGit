package cn.grassinfo.wap.entity;

/**
 * 最新天气
 * @author luofc
 *
 */
public class NewestWeather {
	private String humi;//湿度（整数%）
	private String visi;
	private String temp;
	private String rain;//小时累计降水量（mm,当等于0时不显示此项）
	private String windd;//风向
	private String windv;//风力（级）
	private String aqiLevel;
	private String aqiValue;
	private String aiqUrl;
	private String pm2;
	private String weather;//天况（取三小时预报数据）
	private String weatherIcon;
	private String actual;//实况时间（一小时实况时间） 
	private String forecast;//预报时间（三小时预报时间）
	public String getHumi() {
		return humi;
	}
	public void setHumi(String humi) {
		this.humi = humi;
	}
	public String getVisi() {
		return visi;
	}
	public void setVisi(String visi) {
		this.visi = visi;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public String getRain() {
		return rain;
	}
	public void setRain(String rain) {
		this.rain = rain;
	}
	public String getWindd() {
		return windd;
	}
	public void setWindd(String windd) {
		this.windd = windd;
	}
	public String getWindv() {
		return windv;
	}
	public void setWindv(String windv) {
		this.windv = windv;
	}
	public String getAqiLevel() {
		return aqiLevel;
	}
	public void setAqiLevel(String aqiLevel) {
		this.aqiLevel = aqiLevel;
	}
	public String getAqiValue() {
		return aqiValue;
	}
	public void setAqiValue(String aqiValue) {
		this.aqiValue = aqiValue;
	}
	public String getAiqUrl() {
		return aiqUrl;
	}
	public void setAiqUrl(String aiqUrl) {
		this.aiqUrl = aiqUrl;
	}
	public String getPm2() {
		return pm2;
	}
	public void setPm2(String pm2) {
		this.pm2 = pm2;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWeatherIcon() {
		return weatherIcon;
	}
	public void setWeatherIcon(String weatherIcon) {
		this.weatherIcon = weatherIcon;
	}
	public String getActual() {
		return actual;
	}
	public void setActual(String actual) {
		this.actual = actual;
	}
	public String getForecast() {
		return forecast;
	}
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
	@Override
	public String toString() {
		return "NewestWeather [humi=" + humi + ", visi=" + visi + ", temp="
				+ temp + ", rain=" + rain + ", windd=" + windd + ", windv="
				+ windv + ", aqiLevel=" + aqiLevel + ", aqiValue=" + aqiValue
				+ ", aiqUrl=" + aiqUrl + ", pm2=" + pm2 + ", weather="
				+ weather + ", weatherIcon=" + weatherIcon + ", actual="
				+ actual + ", forecast=" + forecast + "]";
	}
	
}
