package cn.grassinfo.wap.entity;

public class StationAqi {
	
	private String name;
	private String country;
	private Integer aqi;
	private String aqiLevel;
	private String mainPollution;
	private String pm25;
	private String pm10;
	private String time;
	private String titleImg;
	
	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}


	public Integer getAqi() {
		return aqi;
	}

	public void setAqi(Integer aqi) {
		this.aqi = aqi;
	}

	public String getAqiLevel() {
		return aqiLevel;
	}

	public void setAqiLevel(String aqiLevel) {
		this.aqiLevel = aqiLevel;
	}

	public String getMainPollution() {
		return mainPollution;
	}

	public void setMainPollution(String mainPollution) {
		this.mainPollution = mainPollution;
	}

	public String getPm25() {
		return pm25;
	}

	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}

	public String getPm10() {
		return pm10;
	}

	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	
}
