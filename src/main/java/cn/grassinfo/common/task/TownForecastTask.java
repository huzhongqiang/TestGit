package cn.grassinfo.common.task;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import cn.grassinfo.common.Constant;
import cn.grassinfo.common.util.FileUtils;
import cn.grassinfo.common.utils.ContinueFTP;
import cn.grassinfo.common.utils.SqlConnectionUtils;
import cn.grassinfo.wap.entity.InfoTownUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

/**
 * @author huhengbo
 * @Type YsjmDownloadTask
 * @Desc
 * @date 2017/2/24
 */
public class TownForecastTask extends TimerTask{

    private static final Logger log = LoggerFactory.getLogger(TownForecastTask.class);
    private static final String preWeather = Constant.propFileManager.getString("preWeather");
    private static final SimpleDateFormat df2 = new SimpleDateFormat("乡镇预报：萧山气象台yyyy年MM月dd日HH时mm分发布");
	 private static boolean isRunning = false;    
	    
	    private ServletContext context = null;    
	    
	    public TownForecastTask() {  
	        super();  
	    }  
	      
	    public TownForecastTask(ServletContext context) {    
	        this.context = context;    
	    }    
	    @Override  
	    public void run() {  
	          
	        if (!isRunning) {  
				log.info("------------------------------定时获取乡镇预报开始------------------------------------");
				/**
				 * 获取整体
				 */
				Map<String, Object> result = new HashMap<>();
				String observTimes = "";
				List<Map<String, Object>> list = new ArrayList<>();
				Calendar c1 = Calendar.getInstance();
				Calendar c2 = new GregorianCalendar(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));	
				Date now = c2.getTime();
				Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
						"jdbc:sqlserver://172.41.129.5;DatabaseName=hzqxdb", "zcy", "zcy");
				PreparedStatement pst = null;
				ResultSet res = null;
				//取出最新插入时间
				String sqlForNewestDate = "SELECT DISTINCT TOP 1 Fdatetime  FROM forecast_town ORDER BY Fdatetime DESC";
				//获取萧山辖区所有乡镇
				List<InfoTownUtil> infoTownUtils = getInfoTownData();
				try {
					pst = con.prepareStatement(sqlForNewestDate.toString());
					res = pst.executeQuery();
					String newestFdateTime = "";
					while (res.next())
					{
						newestFdateTime = res.getTimestamp("Fdatetime").toString().trim();
					}
					for (InfoTownUtil infoTownUtil : infoTownUtils) {
						String sql = "";
						sql = "SELECT top 3 * FROM forecast_town WHERE Ldatetime >= GETDATE() AND [Interval] <= '4680' AND Step <= '180' AND Fdatetime='"
								+ newestFdateTime + "' And stationN='" + infoTownUtil.getStationN()
								+ "' ORDER BY Ldatetime ASC";
						pst = con.prepareStatement(sql.toString());
						res = pst.executeQuery();

						String weatherStr = "";
						Double temMax = -999d;
						Double temMin = 999d;
						String icon = preWeather + "/index/weather_icon/na.png";
						boolean first = true;
						Map<String, Object> temp = new HashMap<>();
						while (res.next()) {
							String date = res.getTimestamp("LdateTime").toString().split(" ")[0];
							if (new GregorianCalendar(Integer.parseInt(date.split("-")[0]),
									Integer.parseInt(date.split("-")[1]) - 1, Integer.parseInt(date.split("-")[2])).getTime()
											.compareTo(now) >= 0) {
								if (Integer.parseInt(
										res.getTimestamp("Ldatetime").toString().split(" ")[0].split("-")[2]) >= new Date()
												.getDate()) {
									if (first) {
										if ("".equals(observTimes)) {
											observTimes = df2.format(res.getTimestamp("Fdatetime"));
										}
										//天气描述
										weatherStr = res.getString("weatherStr").trim();
										//图片
										String dateTime = res.getTimestamp("Ldatetime").toString().split(" ")[0].split("-")[2]
												+ "日" + res.getTimestamp("LdateTime").toString().split(" ")[1].split(":")[0]
												+ "时";
										Integer hour = Integer.parseInt(dateTime.substring(3, 5));
										//判断白天黑夜
										if (hour >= 6 && hour <= 18) {
											icon = preWeather + "/WForecast/WeatherIcon/64/day/" + res.getInt("weatherCode")
													+ ".png";
										} else {
											icon = preWeather + "/WForecast/WeatherIcon/64/night/" + res.getInt("weatherCode")
													+ ".png";
										}
									}
									Double tem = res.getDouble("Tem");
									Double Tmax = res.getDouble("Tmax");
									Double Tmin = res.getDouble("Tmin");
									//查找最大最低气温
									if (tem > temMax) {//最高气温无数据时为 -9990
										temMax = tem;
									}
									if (Tmax > temMax) {
										temMax = Tmax;
									}
									if (tem < temMin) {
										temMin = tem;
									}
									if (Tmin < temMin && Tmin >= -500) {//最低气温无数据时为 -9990
										temMin = Tmin;
									}

								}
							}
						}
						if (temMax!=-999d && temMin != 999d) {
							temp.put("town", infoTownUtil.getTown());
							temp.put("StationN", infoTownUtil.getStationN());
							temp.put("weatherStr", weatherStr);
							temp.put("icon", icon);
							temp.put("temMax", temMax / 10);
							temp.put("temMin", temMin / 10);
							list.add(temp);
							weatherStr = "";
							temMax = -999d;
							temMin = 999d;
							icon = "";
						}
						temp = new HashMap<>();
					} 
				} catch (Exception e) {
					log.error("遍历乡镇实况预报失败{}"+e.getMessage());
					result.put("result", 0);
				}
				result.put("result", 1);
				result.put("data", list);
				result.put("titleImg",preWeather+"/weixin/xzyb.png");
				result.put("observTimes",observTimes);
				String jsonText= com.alibaba.fastjson.JSON.toJSONString(result);
				String outPutPath=FileUtils.getContextPath()+"townForecast.json";
				FileUtils.outPutFile(outPutPath, jsonText);	
				
				/**
				 * 获取各乡镇详细
				 */
				for (InfoTownUtil infoTownUtil : infoTownUtils) {
					String stationN = infoTownUtil.getStationN();
					result = new HashMap<>();
					Map<String, Object> data = new HashMap<>();
					list = new ArrayList<>();
					/*
					 * 三天预报开始
					 */
					//取出最新插入时间
					String newestFdateTime = "";
					try {
						sqlForNewestDate = "SELECT DISTINCT TOP 1 Fdatetime  FROM forecast_town ORDER BY Fdatetime DESC";
						pst = con.prepareStatement(sqlForNewestDate.toString());
						res = pst.executeQuery();
						while (res.next()) {
							newestFdateTime = res.getTimestamp("Fdatetime").toString().trim();
						} 
					} catch (Exception e) {
						log.error("取出最新插入时间异常{}"+e.getMessage());
						result.put("result", 0);
					}
					try {
						String sql = "";
						sql = "SELECT  * FROM forecast_town WHERE Ldatetime >= GETDATE() AND [Interval] <= '4680' AND Step <= '180' AND Fdatetime='"
								+ newestFdateTime + "' And stationN='" + stationN + "' ORDER BY Ldatetime ASC";
						pst = con.prepareStatement(sql.toString());
						res = pst.executeQuery();
						while (res.next()) {
							String date = res.getTimestamp("LdateTime").toString().split(" ")[0];
							if (new GregorianCalendar(Integer.parseInt(date.split("-")[0]),
									Integer.parseInt(date.split("-")[1]) - 1, Integer.parseInt(date.split("-")[2])).getTime()
									.compareTo(now) >= 0) {
								Map<String, Object> temp3Day = new HashMap<>();
								if (Integer.parseInt(
										res.getTimestamp("Ldatetime").toString().split(" ")[0].split("-")[2]) >= new Date()
										.getDate()) {
									String dateTime = res.getTimestamp("Ldatetime").toString().split(" ")[0].split("-")[2] + "日"
											+ res.getTimestamp("LdateTime").toString().split(" ")[1].split(":")[0] + "时";
									String weatherStr = res.getString("weatherStr").trim();
									Integer hour = Integer.parseInt(dateTime.substring(3, 5));
									String icon = preWeather + "/index/weather_icon/na.png";
									if (hour >= 6 && hour <= 18) {
										icon = preWeather + "/WForecast/WeatherIcon/64/day/" + res.getInt("weatherCode") + ".png";
									} else {
										icon = preWeather + "/WForecast/WeatherIcon/64/night/" + res.getInt("weatherCode") + ".png";
									}
									Double tem = res.getDouble("Tem") / 10;
									temp3Day.put("tem", tem);
									temp3Day.put("dateTime", dateTime);
									temp3Day.put("weatherStr", weatherStr);
									temp3Day.put("icon", icon);
								}
								list.add(temp3Day);
							}
						} 
					} catch (Exception e) {
						log.error("查询三天预报异常{}"+e.getMessage());
						result.put("result", 0);
					}
					data.put("forecast3Day", list);
					list= new ArrayList<>();
					/*
					 * 三天预报结束
					 */
					
					/**
					 * 七天预报开始
					 */
						try {
							URL url = new URL("http://www.hzqx.com/hztq/data/forecastIndex7DData/"+stationN+".json");
							StringBuffer bankXmlBuffer = new StringBuffer();
							// 创建URL连接，提交到数据，获取返回结果
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							connection.setRequestProperty("contentType", "UTF-8");
							connection.setRequestMethod("GET");
							BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
							String inputLine;
							while ((inputLine = in.readLine()) != null) {
								bankXmlBuffer.append(inputLine);
							}
							in.close();
							com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
									.parseArray(bankXmlBuffer.toString().replace("(", "").replace(")", ""));
							if (jsonArray != null && jsonArray.size() > 0) {
								for (int i = 0; i < jsonArray.size(); i++) {
									Map<String, Object> temp7Day = new HashMap<>();
									com.alibaba.fastjson.JSONObject jsonObject = jsonArray.getJSONObject(i);
									String forecastDate = jsonObject.getString("date").trim();
									Double temMax = jsonObject.getDouble("Tmax");
									Double temMin = jsonObject.getDouble("Tmin");
									String wStrD = jsonObject.getString("wStrD").trim();
									String wStrN = jsonObject.getString("wStrN").trim();
									String icon = preWeather+"/WForecast/WeatherIcon/64/day/"+jsonObject.getString("weatherN").trim()+".png";
									String windv = jsonObject.getString("windv").substring(1,2);
									String windd = jsonObject.getString("windd").trim();
									temp7Day.put("dateTime", forecastDate);
									temp7Day.put("temMax", temMax);
									temp7Day.put("temMin", temMin);
									temp7Day.put("icon", icon);
									if (wStrD.equals(wStrD)) {
										temp7Day.put("weatherStr", wStrD);
									}else {
										temp7Day.put("weatherStr", wStrD+"转"+wStrN);
									}
									temp7Day.put("windd", windd);
									temp7Day.put("windv", windv);
									list.add(temp7Day);
								}
							}
							data.put("forecast7Day", list);
							result.put("result", 1);
					}catch (Exception e) {
						data.put("result", 0);
						log.error("获取7天预报失败{}"+e.getMessage());
					}
					result.put("titleImg",preWeather+"/QXJ.jpg" );
					result.put("data", data);
					jsonText= com.alibaba.fastjson.JSON.toJSONString(result);
					outPutPath=FileUtils.getContextPath()+stationN+".json";
					FileUtils.outPutFile(outPutPath, jsonText);	
					
				}
				log.info("------------------------------定时获取乡镇预报结束------------------------------------");
	            isRunning = false;
	            SqlConnectionUtils.GetColsed(con, pst, res);
	        } else {    
	            log.debug("上次执行未结束");
	     }  
	    } 
	    /*
		 * 获取萧山所有的城镇
		 */
		public static List<InfoTownUtil> getInfoTownData(){
			List<InfoTownUtil> infoTownUtils = new ArrayList<InfoTownUtil>();
			Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
					"jdbc:sqlserver://172.41.129.5;DatabaseName=hzqxdb", "zcy", "zcy");
			PreparedStatement pst = null;
			ResultSet res = null;
			String sql = "SELECT DISTINCT stationN, rstation,town FROM info_town where rstation='58459'";
			try {
				pst = con.prepareStatement(sql.toString());
				res = pst.executeQuery();
				while (res.next()) {
					InfoTownUtil town = new InfoTownUtil();
					town.setStationN(res.getString("stationN").trim());
					town.setTown(res.getString("town").trim());
					infoTownUtils.add(town);
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("获取城镇列表失败{}"+e.getMessage());
			} finally {
				SqlConnectionUtils.GetColsed(con, pst, res);
			}
			return infoTownUtils;
		}
		
		public static void main(String[] args) {
			String outPutPath=FileUtils.getContextPath();
			System.out.println(outPutPath);
			TownForecastTask task = new TownForecastTask();
			task.run();
		}
}
