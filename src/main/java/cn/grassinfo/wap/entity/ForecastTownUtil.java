package cn.grassinfo.wap.entity;

public class ForecastTownUtil {
	
	private String id;
	private String stationN;
	private String idateTime;
	private String ldateTime;
	private String formatLdateTime;
//	private String fdateTime;
//	private int interval;
	private int step;
//	private String forecaster;
//	private int isCorrect;
	private int rain;
//	private int rain1;
//	private int rain3;
//	private int rain6;
//	private int rain12;
//	private int rain24;
	private String weatherCode;
	private String weatherStr;
	private int cloudT;
//	private int cloudD;
//	private int cloudH;
	private int vv;
	private int rh;
	private String windd;
	private String windv;
	private int tem;
	private int tmax;
	private int tmin;
	private int wd;
	private int ws;
	
	public String getId () {
		return id;
	}
	public void setId (String id) {
		this.id = id;
	}

	public String getStationN(){
		return stationN;
	}
	public void setStationN(String stationN) {
		this.stationN = stationN;
	}

	public String getIdateTime(){
		return idateTime;
	}
	public void setIdateTime (String idateTime) {
		this.idateTime = idateTime;
	}

	public String getLdateTime () {
		return ldateTime;
	}
	public void setLdateTime (String ldateTime) {
		this.ldateTime = ldateTime;
	}
	
	public String getFormatLdateTime () {
		return formatLdateTime;
	}
	public void setFormatLdateTime (String formatLdateTime) {
		this.formatLdateTime = formatLdateTime;
	}

//	public String getFdateTime () {
//		return fdateTime;
//	}
//	public void setFdateTime (String fdateTime) {
//		this.fdateTime = fdateTime;
//	}
//
//	public int getInterval() {
//		return interval;
//	}
//	public void setInterval (int interval) {
//		this.interval = interval;
//	}
//
	public int getStep () {
		return step;
	}
	public void setStep (int step) {
		this.step = step;
	}
//
//	public String getForecaster () {
//		return forecaster;
//	}
//	public void setForecaster(String forecaster) {
//		this.forecaster = forecaster;
//	}
//
//	public int getIsCorrect() {
//		return isCorrect;
//	}
//	public void setIsCorrect (int isCorrect) {
//		this.isCorrect = isCorrect;
//	}
	
	public int getRain() {
		return rain;
	}
	public void setRain (int rain) {
		this.rain = rain;
	}
	
//	public int getRain1() {
//		return rain1;
//	}
//	public void setRain1 (int rain1) {
//		this.rain1 = rain1;
//	}

//	public int getRain3() {
//		return rain3;
//	}
//	public void setRain3 (int rain3) {
//		this.rain3 = rain3;
//	}
//
//	public int getRain6() {
//		return rain6;
//	}
//	public void setRain6 (int rain6) {
//		this.rain6 = rain6;
//	}
//
//	public int getRain12() {
//		return rain12;
//	}
//	public void setRain12 (int rain12) {
//		this.rain12 = rain12;
//	}
//	
//	public int getRain24() {
//		return rain24;
//	}
//	public void setRain24 (int rain24) {
//		this.rain24 = rain24;
//	}
	
	public String getWeatherCode() {
		return weatherCode;
	}
	public void setWeatherCode (String weatherCode) {
		this.weatherCode = weatherCode;
	}

	public String getWeatherStr() {
		return weatherStr;
	}
	public void setWeatherStr (String weatherStr) {
		this.weatherStr = weatherStr;
	}

	public int getCloudT() {
		return cloudT;
	}
	public void setCloudT (int cloudT) {
		this.cloudT = cloudT;
	}
	
//	public int getCloudD() {
//		return cloudD;
//	}
//	public void setCloudD (int cloudD) {
//		this.cloudD = cloudD;
//	}
//
//	public int getCloudH() {
//		return cloudH;
//	}
//	public void setCloudH (int cloudH) {
//		this.cloudH = cloudH;
//	}

	public int getVv() {
		return vv;
	}
	public void setVv (int vv) {
		this.vv = vv;
	}

	public int getRh() {
		return rh;
	}
	public void setRh (int rh) {
		this.rh = rh;
	}

	public String getWindd() {
		return windd;
	}
	public void setWindd (String windd) {
		this.windd = windd;
	}
	
	public String getWindv() {
		return windv;
	}
	public void setWindv(String windv) {
		this.windv = windv;
	}
	
	public int getTem() {
		return tem;
	}
	public void setTem(int tem) {
		this.tem = tem;
	}

	public int getTmax() {
		return tmax;
	}
	public void setTmax(int tmax) {
		this.tmax = tmax;
	}
	
	public int getTmin() {
		return tmin;
	}
	public void setTmin(int tmin) {
		this.tmin = tmin;
	}

	public int getWd() {
		return wd;
	}
	public void setWd(int wd) {
		this.wd = wd;
	}
	
	public int getWs() {
		return ws;
	}
	public void setWs(int ws) {
		this.ws = ws;
	}
}
