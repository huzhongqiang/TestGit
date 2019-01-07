package cn.grassinfo.wap.entity;

import java.util.List;

public class InfoTownUtil {
	private String stationN;
	private String location;
	private String rstation;
	private String town;
	private String cityn;
	private List<ForecastTownUtil> forecast;

	public String getStationN(){
		return stationN;
	}
	public void setStationN(String stationN) {
		this.stationN = stationN;
	}

	public String getRstation () {
		return rstation;
	}
	public void setRstation (String rstation) {
		this.rstation = rstation;
	}
	
	public String getTown () {
		return town;
	}
	public void setTown (String town) {
		this.town = town;
	}
	
	public String getCityn () {
		return cityn;
	}
	public void setCityn (String cityn) {
		this.cityn = cityn;
	}
	
	public String getLocation () {
		return location;
	}
	public void setLocation (String location) {
		this.location = location;
	}
	
	public List<ForecastTownUtil> getForecast () {
		return forecast;
	}
	public void setForecast(List<ForecastTownUtil> forecast) {
		this.forecast = forecast;
	}

}
