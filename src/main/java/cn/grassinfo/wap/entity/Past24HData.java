package cn.grassinfo.wap.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 24小时实况数据
 * @author wuyh
 *
 */
public class Past24HData {
	private String stationId;//站点编号
	private String observTimes;//发布时间
	private Map<String, Object> dataMap;//实况数据
//	private List<PastData> pastDatas;//实况数据
	
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getObservTimes() {
		return observTimes;
	}
	public void setObservTimes(String observTimes) {
		this.observTimes = observTimes;
	}
	public Map<String, Object> getDataMap() {
		if (this.dataMap == null) {
			this.dataMap = new HashMap<String, Object>();
		}
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
//	public List<PastData> getPastDatas() {
//		return pastDatas;
//	}
//	public void setPastDatas(List<PastData> pastDatas) {
//		this.pastDatas = pastDatas;
//	}
	
}
