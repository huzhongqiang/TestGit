package cn.grassinfo.wap.entity;


public class ShortForecastWordData {

	private String datetime;
	private String type;
	private String countyCity;
	private String beginDateStr;
	private String contentStr;
	private String forecasterStr;
	
	/**
	 * @return 发布时间
	 */
	public String getDatetime() {
		return datetime;
	}
	/**
	 * 设置发布时间
	 * @param datetime
	 */
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	/**
	 * @return 类别
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置类别
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return 县市
	 */
	public String getCountyCity() {
		return countyCity;
	}
	/**
	 * 设置县市
	 * @param countyCity
	 */
	public void setCountyCity(String countyCity) {
		this.countyCity = countyCity;
	}
	/**
	 * @return 发布时间文字说明
	 */
	public String getBeginDateStr() {
		return beginDateStr;
	}
	/**
	 * 设置发布时间文字说明
	 * @param beginDateStr
	 */
	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}
	/**
	 * @return 预报内容
	 */
	public String getContentStr() {
		return contentStr;
	}
	/**
	 * 设置预报内容
	 * @param contentStr
	 */
	public void setContentStr(String contentStr) {
		this.contentStr = contentStr;
	}
	/**
	 * @return 预报员说明文字
	 */
	public String getForecasterStr() {
		return forecasterStr;
	}
	/**
	 * 设置预报员说明文字
	 * @param forecasterStr
	 */
	public void setForecasterStr(String forecasterStr) {
		this.forecasterStr = forecasterStr;
	}
	
}
