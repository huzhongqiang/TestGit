package cn.grassinfo.wap.entity;

import java.util.List;
import java.util.Map;

/**
 * 生活指数
 * @author wuyh
 *
 */
public class LiveIndex {
	private String siteCode;
	private String address;
	private String publishTimes;
	private List<Living> today;
	private List<Living> tomorrow;
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPublishTimes() {
		return publishTimes;
	}
	public void setPublishTimes(String publishTimes) {
		this.publishTimes = publishTimes;
	}
	public List<Living> getToday() {
		return today;
	}
	public void setToday(List<Living> today) {
		this.today = today;
	}
	public List<Living> getTomorrow() {
		return tomorrow;
	}
	public void setTomorrow(List<Living> tomorrow) {
		this.tomorrow = tomorrow;
	}
	
	

}
