package cn.grassinfo.wap.entity;


/*
 * 未来7天预报
 */
public class Real7DWeather{
	private String stationN;//站号
	private float maxTemp;//温度
	private float minTemp;
	private float maxRain;//降水
	private float minRain;
	private float sumRain;//累计降水量
	private String time;//时次
	private float maxPress;//气压
	private float minPress;
	private float avgPress;//平均气压
	private int maxRh;//相对湿度
	private int minRh;
	private int avgRh;//平均湿度
	private int maxVisibility;//能见度
	private int minVisibility;
	private int avgVisibility;//平均能见度
	
	public String getStationN() {
		return stationN;
	}
	public void setStationN(String stationN) {
		this.stationN = stationN;
	}
	
	public float getMaxTemp() {
		return maxTemp;
	}
	public void setMaxTemp(float maxTemp) {
		this.maxTemp = maxTemp;
	}
	public float getMinTemp() {
		return minTemp;
	}
	public void setMinTemp(float minTemp) {
		this.minTemp = minTemp;
	}
	
	public float getMaxRain() {
		return maxRain;
	}
	public void setMaxRain(float maxRain) {
		this.maxRain = maxRain;
	}
	public float getMinRain() {
		return minRain;
	}
	public void setMinRain(float minRain) {
		this.minRain = minRain;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public float getMaxPress() {
		return maxPress;
	}
	public void setMaxPress(float maxPress) {
		this.maxPress = maxPress;
	}
	public float getMinPress() {
		return minPress;
	}
	public void setMinPress(float minPress) {
		this.minPress = minPress;
	}
	
	public int getMaxRh() {
		return maxRh;
	}
	public void setMaxRh(int maxRh) {
		this.maxRh = maxRh;
	}
	public int getMinRh() {
		return minRh;
	}
	public void setMinRh(int minRh) {
		this.minRh = minRh;
	}
	
	public int getMaxVisibility() {
		return maxVisibility;
	}
	public void setMaxVisibility(int maxVisibility) {
		this.maxVisibility = maxVisibility;
	}
	public int getMinVisibility() {
		return minVisibility;
	}
	public void setMinVisibility(int minVisibility) {
		this.minVisibility = minVisibility;
	}
	public float getAvgPress() {
		return avgPress;
	}
	public void setAvgPress(float avgPress) {
		this.avgPress = avgPress;
	}
	public int getAvgRh() {
		return avgRh;
	}
	public void setAvgRh(int avgRh) {
		this.avgRh = avgRh;
	}
	public int getAvgVisibility() {
		return avgVisibility;
	}
	public void setAvgVisibility(int avgVisibility) {
		this.avgVisibility = avgVisibility;
	}
	public float getSumRain() {
		return sumRain;
	}
	public void setSumRain(float sumRain) {
		this.sumRain = sumRain;
	}
	
	
	

}
