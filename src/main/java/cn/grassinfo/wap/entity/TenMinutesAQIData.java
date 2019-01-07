package cn.grassinfo.wap.entity;
/**
 * 红旗塘大桥间隔十分钟AQI实况采集
 * @author Administrator
 *
 */
public class TenMinutesAQIData {
	private Double wind_D_10_angle;//十分风向角度
	private Integer wind_L_10_level;//十分钟风速等级 
	private String wind_D_10;//十分风向
	private Double wind_L_10;//十分风速
	private Double humidity;//相对湿度
	private Integer air_temp;//当前空气温度
	private Double rain;//一分钟雨量
	private Float road_temp;//路面温度
	private Double horizontal;//一分钟平均水平能见度
	private Double air_pressure;//气压
	private String road_condition;//路面状况
	private String time; //时间
	private Double water_thickness;//水层厚度
	private Double ice_thickness;//冰层厚度
	private Double snow_thickness;//雪层厚度
	private Integer anti_slip_coefficient;//防滑系数
	
	private String station_id;//站点编号
	private String station_name;//站点名称
	private String station_address;//站点位置
	private String monitor_type;//监测类型
	private String station_type;//站点监测类型
	private Double station_longitude;//站点经度
	private Double station_latitude;//站点纬度
	public Double getWind_D_10_angle() {
		return wind_D_10_angle;
	}
	public void setWind_D_10_angle(Double wind_D_10_angle) {
		this.wind_D_10_angle = wind_D_10_angle;
	}
	public Integer getWind_L_10_level() {
		return wind_L_10_level;
	}
	public void setWind_L_10_level(Integer wind_L_10_level) {
		this.wind_L_10_level = wind_L_10_level;
	}
	public String getWind_D_10() {
		return wind_D_10;
	}
	public void setWind_D_10(String wind_D_10) {
		this.wind_D_10 = wind_D_10;
	}
	public Double getWind_L_10() {
		return wind_L_10;
	}
	public void setWind_L_10(Double wind_L_10) {
		this.wind_L_10 = wind_L_10;
	}
	public Double getHumidity() {
		return humidity;
	}
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	public Integer getAir_temp() {
		return air_temp;
	}
	public void setAir_temp(Integer air_temp) {
		this.air_temp = air_temp;
	}
	
	public Float getRoad_temp() {
		return road_temp;
	}
	public void setRoad_temp(Float road_temp) {
		this.road_temp = road_temp;
	}
	
	public Double getAir_pressure() {
		return air_pressure;
	}
	public void setAir_pressure(Double air_pressure) {
		this.air_pressure = air_pressure;
	}
	public String getRoad_condition() {
		return road_condition;
	}
	public void setRoad_condition(String road_condition) {
		this.road_condition = road_condition;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getWater_thickness() {
		return water_thickness;
	}
	public void setWater_thickness(Double water_thickness) {
		this.water_thickness = water_thickness;
	}
	
	public Double getIce_thickness() {
		return ice_thickness;
	}
	public void setIce_thickness(Double ice_thickness) {
		this.ice_thickness = ice_thickness;
	}
	public Double getSnow_thickness() {
		return snow_thickness;
	}
	public void setSnow_thickness(Double snow_thickness) {
		this.snow_thickness = snow_thickness;
	}
	public Integer getAnti_slip_coefficient() {
		return anti_slip_coefficient;
	}
	public void setAnti_slip_coefficient(Integer anti_slip_coefficient) {
		this.anti_slip_coefficient = anti_slip_coefficient;
	}
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getStation_address() {
		return station_address;
	}
	public void setStation_address(String station_address) {
		this.station_address = station_address;
	}
	public String getMonitor_type() {
		return monitor_type;
	}
	public void setMonitor_type(String monitor_type) {
		this.monitor_type = monitor_type;
	}
	public String getStation_type() {
		return station_type;
	}
	public void setStation_type(String station_type) {
		this.station_type = station_type;
	}
	public Double getStation_longitude() {
		return station_longitude;
	}
	public void setStation_longitude(Double station_longitude) {
		this.station_longitude = station_longitude;
	}
	public Double getStation_latitude() {
		return station_latitude;
	}
	public void setStation_latitude(Double station_latitude) {
		this.station_latitude = station_latitude;
	}
	public Double getRain() {
		return rain;
	}
	public void setRain(Double rain) {
		this.rain = rain;
	}
	public Double getHorizontal() {
		return horizontal;
	}
	public void setHorizontal(Double horizontal) {
		this.horizontal = horizontal;
	}
	
}
