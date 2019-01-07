package cn.grassinfo.wap.entity;


/*
 * 未来24小时逐小时预报
 */
public class FutureWeather{
	private int temp;
	private int rain;
	private String time;
	private String weatherCode;
	private String weatherStr;
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public int getRain() {
		return rain;
	}
	public void setRain(int rain) {
		this.rain = rain;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getWeatherCode(){
		return weatherCode;
	}
	public void setWeatherCode(String weatherCode){
		this.weatherCode = weatherCode;
	}
	public String getWeatherStr(){
		return weatherStr;
	}
	public void setWeatherStr(String weatherStr){
		this.weatherStr = weatherStr;
	}
	

}
