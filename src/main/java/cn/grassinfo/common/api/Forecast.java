package cn.grassinfo.common.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.grassinfo.common.util.FileUtils;
import cn.grassinfo.common.utils.SqlConnectionUtils;
import cn.grassinfo.common.utils.WindUtils;
import cn.grassinfo.wap.entity.ForecastTownUtil;
import cn.grassinfo.wap.entity.InfoTownUtil;

/*
 * 未来7天预报、未来24小时预报
 * @author shenjj
 * 
 */
public class Forecast {
	private static final long serialVersionUID = -9112163784129802296L;
	private static final Logger log = LoggerFactory
			.getLogger(Forecast.class);
	private static final String forecast7DFileName="/forecast7DData";
	private static final String forecastIndex7DFileName="/forecastIndex7DData";
	private static final String forecast24HFileName="/forecast24HData";
	private static final String forecast3DFileName = "/forecast3DData";
	private static final String forecastQgtq="/forecastQgtq";
	private static final String forecast7DQgtq = "/forecast7DQgtq";
	private static final String forecast1HRain="/forecast1HRain";
	private static final String forecast3HRain="/forecast3HRain";

	private static final String[] rstation= {"58449", "58459", "58448", "58542", "58544", "58543", "58444", "58457"};
	private static Map<String, List<Map<String, String>>> rstationInfo = new HashMap<String, List<Map<String, String>>>();
	
	/*
	 * 获取萧山所有的城镇
	 */
	public static List<InfoTownUtil> getInfoTownData(){
		List<InfoTownUtil> infoTownUtils = new ArrayList<InfoTownUtil>();
		Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://172.41.129.5;DatabaseName=hzqxdb", "zcy", "zcy");
		PreparedStatement pst = null;
		ResultSet res = null;

		String sql = "SELECT DISTINCT stationN, rstation FROM info_town where cityn='萧山区'";
		try {
			pst = con.prepareStatement(sql.toString());
			res = pst.executeQuery();
			while (res.next()) {
				InfoTownUtil town = new InfoTownUtil();
				town.setStationN(res.getString("stationN").trim());
				town.setRstation(res.getString("rstation") == null? "":res.getString("rstation").trim());
				infoTownUtils.add(town);
			}
			//单独增加余杭,因为info_town数据库中不存在该条数据
//			InfoTownUtil yuhang = new InfoTownUtil();
//			yuhang.setStationN("58444");
//			infoTownUtils.add(yuhang);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.GetColsed(con, pst, res);
		}
		return infoTownUtils;
	}
	
	public static String getWeatherStrByCode(int code){
		switch(code){
		case 0: return "晴";
		case 1: return "多云";
		case 2: return "阴";
		case 3: return "阵雨";
		case 4: return "雷阵雨";
		case 5: return "冰雹";
		case 6: return "雨夹雪";
		case 7: return "小雨";
		case 8: return "中雨";
		case 9: return "大雨";
		case 10: return "暴雨";
		case 11: return "大暴雨";
		case 12: return "特大暴雨";
		case 13: return "阵雪";
		case 14: return "小雪";
		case 15: return "中雪";
		case 16: return "大雪";
		case 17: return "暴雪";
		case 18: return "雾";
		case 19: return "冻雨";
		case 20: return "沙尘暴";
		case 21: return "小到中雨";
		case 22: return "中到大雨";
		case 23: return "大到暴雨";
		case 24: return "暴雨到大暴雨";
		case 25: return "大暴雨到特大暴雨";
		case 26: return "小到中雪";
		case 27: return "中到大雪";
		case 28: return "大到暴雪";
		case 29: return "沙尘";
		case 30: return "扬沙";
		case 31: return "强沙尘暴";
		case 53: return "霾";
		}
		return "";
	}
	
	public static String dateFormat(String date, int hour){
		Calendar c = new GregorianCalendar(Integer.parseInt(date.substring(0,4)), Integer.parseInt(date.substring(4,6))-1, Integer.parseInt(date.substring(6, 8)));
		int sign = hour / 24;
		if(sign == 1){
			c.add(Calendar.DAY_OF_MONTH, 1);
		}
		else if(sign == 2){
			c.add(Calendar.DAY_OF_MONTH, 2);
		}
		else if(sign == 3){
			c.add(Calendar.DAY_OF_MONTH, 3);
		}
		else if(sign == 4){
			c.add(Calendar.DAY_OF_MONTH, 4);
		}
		else if(sign == 5){
			c.add(Calendar.DAY_OF_MONTH, 5);
		}
		else if(sign == 6){
			c.add(Calendar.DAY_OF_MONTH, 6);
		}
		else if(sign == 7){
			c.add(Calendar.DAY_OF_MONTH, 7);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(c.getTime());
	}
	
	public static Map getTemAndWeather(){
		Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://10.135.144.203;DatabaseName=PubcastBiz", "hzqx", "58457");
		PreparedStatement pst = null;
		ResultSet res = null;
		String sql = "SELECT * FROM Forecast WHERE Intervaltime = '12' ORDER BY PubTime DESC";
		Map<String, String> map = new HashMap<String, String>();
		try {
			pst = con.prepareStatement(sql.toString());
			res = pst.executeQuery();
			while(res.next()){
				if(!map.containsKey(res.getString("StationCode").trim())){
					map.put(res.getString("StationCode").trim(), res.getString("ForecastID").trim());
				}
				else if(map.size() == 8){
					break;
				}
				else{
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, List<Map<String, String>>> data = new HashMap<String, List<Map<String, String>>>();
		try {
			int i = 0;
			while(i < map.size()){
				String sql2 = "SELECT * FROM ForecastInfo WHERE ForecastID = '"+map.get(rstation[i]).toString()+"' ORDER BY Hours ASC";
				//System.out.println(sql2);
				pst = con.prepareStatement(sql2.toString());
				res = pst.executeQuery();
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				Map<String, String> m = new HashMap<String, String>();
				int j = 1;
				while(res.next()){
					if(j % 2 == 1){
						m.put("weatherD", res.getString("Weather").trim());
						m.put("wStrD", getWeatherStrByCode(res.getInt("Weather")));
					}
					else if(j % 2 == 0)
					{
						//m.put("hours", res.getString("Hours").trim());
						m.put("date", dateFormat(res.getString("ForecastID").trim().split("-")[1], Integer.parseInt(res.getString("Hours").trim())));
						m.put("Tmax", String.valueOf(res.getInt("MaxTemp")/10));
						m.put("Tmin", String.valueOf(res.getInt("MinTemp")/10));
						m.put("weatherN", res.getString("Weather").trim());
						m.put("wStrN", getWeatherStrByCode(res.getInt("Weather")));
						m.put("windd", WindUtils.getWindd(res.getInt("WindD")));
						m.put("windv", res.getString("WindV"));
						list.add(m);
						m = new HashMap<String, String>();
					}
					j++;
				}
				data.put(rstation[i], list);
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
		
	}

	/*
	 * 先取出数据库最新插入的时间
	 * 再查询出最新的一周预报、24小时预报、3天预报
	 */
	public static Map<String, List<ForecastTownUtil>> getForecastData(int type){
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = new GregorianCalendar(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));	
		Date now = c2.getTime();
		//System.out.println(now + ", " + now.getTime());

		
		Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://172.41.129.5;DatabaseName=hzqxdb", "zcy", "zcy");
		PreparedStatement pst = null;
		ResultSet res = null;

		Map<String, List<ForecastTownUtil>> map = new HashMap<String, List<ForecastTownUtil>>();
		String sqlForNewestDate = "SELECT DISTINCT TOP 1 Fdatetime  FROM forecast_town ORDER BY Fdatetime DESC";
//		String sql = "SELECT TOP 24 * FROM forecast_town WHERE stationN='" + town.getStationN() + "' AND GETDATE()<=Ldatetime ORDER BY Fdatetime DESC";
		
		try {
			pst = con.prepareStatement(sqlForNewestDate.toString());
			res = pst.executeQuery();
			String newestFdateTime = "";
			while (res.next())
			{
				newestFdateTime = res.getTimestamp("Fdatetime").toString().trim();
			}
			
			String sql = "";
//			//七天预报sql
			if(type == 7){
				//sql = "SELECT * FROM forecast_town WHERE [Interval] <= '10080' AND Step <= '360' AND Fdatetime='" + newestFdateTime  + "' ORDER BY Idatetime DESC";
				sql = "SELECT * FROM forecast_town WHERE [Interval] <= '10800'"
					+ " AND Fdatetime = '"+newestFdateTime+"'"
					+ " AND DATEPART(hh, Ldatetime) IN ('08', '20')"
					+ " ORDER BY Ldatetime ASC";
			}
			//24小时sql
			if(type == 24){
				sql = "SELECT * FROM forecast_town WHERE Ldatetime >= GETDATE() AND [Interval] <= '3960' AND Step <= '180' AND Fdatetime='" + newestFdateTime  + "' ORDER BY Idatetime DESC";
			}
			//3天预报sql
			else if(type == 3){
				sql = "SELECT * FROM forecast_town WHERE Ldatetime >= GETDATE() AND [Interval] <= '4680' AND Step <= '180' AND Fdatetime='" + newestFdateTime  + "' ORDER BY Ldatetime ASC";
			}
			pst = con.prepareStatement(sql.toString());
			res = pst.executeQuery();
			while (res.next()) {
				String date = res.getTimestamp("LdateTime").toString().split(" ")[0];
				if(new GregorianCalendar(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1])-1, Integer.parseInt(date.split("-")[2])).getTime().compareTo(now) >= 0){
				//if(Integer.parseInt(res.getTimestamp("Ldatetime").toString().split(" ")[0].split("-")[2]) >= new Date().getDate()){
					List<ForecastTownUtil> forecastTownUtils = new ArrayList<ForecastTownUtil>();
					ForecastTownUtil forecast = new ForecastTownUtil();
					forecast.setStationN(res.getString("stationN").trim());
					forecast.setLdateTime(res.getTimestamp("Ldatetime").toString().trim().substring(0, res.getTimestamp("Ldatetime").toString().indexOf('.')));
//					//将格式为2000-00-00 00:00:00.0转换为00日00时
					String dateTime = res.getTimestamp("Ldatetime").toString().split(" ")[0].split("-")[2] + "日"
								+ res.getTimestamp("LdateTime").toString().split(" ")[1].split(":")[0] + "时";
					forecast.setFormatLdateTime(dateTime);
					forecast.setTem(Integer.parseInt(res.getString("Tem").trim())/10);
					forecast.setTmax(Integer.parseInt(res.getString("Tmax").trim())/10);
					forecast.setTmin(Integer.parseInt(res.getString("Tmin").trim())/10);
					forecast.setWeatherCode(res.getString("weatherCode").trim());
					forecast.setWeatherStr(res.getString("weatherStr").trim());
					forecast.setRain(res.getInt("rain") == -9990 ? 0 : res.getInt("rain")/10);
					forecast.setRh((int)((double)res.getInt("rh")*0.1));
					forecast.setCloudT(res.getInt("CloudT"));
					forecast.setWindv(res.getString("windv").replaceAll("级", "").trim());
					forecast.setWindd(res.getString("windd").trim());
					forecast.setStep(res.getInt("Step"));
					
					/*
					 * 如果map中已存在站号，则追加
					 * 若不存在，则插入新站号
					 */
					if(type == 7){
						//七天预报只需要早8晚8
						if(forecast.getFormatLdateTime().indexOf("08时") != -1 || forecast.getFormatLdateTime().indexOf("20时") != -1){
							if(map.containsKey(res.getString("stationN").trim()))
							{
								if(map.get(res.getString("stationN").trim()).size() < 14){
									map.get(res.getString("stationN").trim()).add(forecast);
								}
							}
							else
							{
								forecastTownUtils.add(forecast);
								map.put(res.getString("stationN").trim(), forecastTownUtils);
							}
						}
					}
					if(type == 24){
							if(map.containsKey(res.getString("stationN").trim()))
							{
								//24小时预报只需要前8条数据
								if(map.get(res.getString("stationN").trim()).size() < 8){
									map.get(res.getString("stationN").trim()).add(forecast);
								}					
							}
							else
							{
								forecastTownUtils.add(forecast);
								map.put(res.getString("stationN").trim(), forecastTownUtils);
							}
					}
					else if(type == 3){
							if(map.containsKey(res.getString("stationN").trim()))
							{
								map.get(res.getString("stationN").trim()).add(forecast);
							}
							else
							{
								forecastTownUtils.add(forecast);
								map.put(res.getString("stationN").trim(), forecastTownUtils);
							}
					}

				}
			}	
		} catch (Exception e) {
			e.printStackTrace();;
		} finally {
			SqlConnectionUtils.GetColsed(con, pst, res);
		}
		return map;
	}
	
	/*
	 * 生成未来7天
	 */
	public static String forecast7Day(){		
		//获取全部城镇信息 info_town表
		List<InfoTownUtil> towns = new ArrayList<InfoTownUtil>();
		towns = getInfoTownData();
		//获取全部预报信息forecast_town表
		Map root = new HashMap();
		root = getForecastData(7);
		List<File> files = new ArrayList<File>(); 
		for(int i=0; i<towns.size(); i++){
			String jsonText = "";
			//如果站号存在
			if(root.containsKey(towns.get(i).getStationN())){
				jsonText = com.alibaba.fastjson.JSON.toJSONString(root.get(towns.get(i).getStationN()));
			}
			//如果站号不存在，查找rstation
			else if(root.containsKey(towns.get(i).getRstation())){
				jsonText = com.alibaba.fastjson.JSON.toJSONString(root.get(towns.get(i).getRstation()));
			}
			//余杭区处理，用K1020代表站
			else if(towns.get(i).getStationN() == "58444"){
				jsonText = com.alibaba.fastjson.JSON.toJSONString(root.get("K1020"));
			}
			String outPutPath=FileUtils.getContextPath()+forecast7DFileName+ "/" +towns.get(i).getStationN()+".json";
			FileUtils.outPutFile(outPutPath, jsonText);
			files.add(new File(outPutPath));
		}
//		MqDynamicUtils.sendFiles(files.toArray(new File[files.size()]));
		return null;
	}
	
	/*
	 * 首页上7天预报的数据
	 */
	public static String replace7DData(){
		rstationInfo = getTemAndWeather();
		//获取全部城镇信息 info_town表
		List<InfoTownUtil> towns = new ArrayList<InfoTownUtil>();
		towns = getInfoTownData();
		List<File> files = new ArrayList<File>(); 
		for(int i=0; i<towns.size(); i++){
			String jsonText = "";
			SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect; //禁用循环检测
			if(rstationInfo.containsKey(towns.get(i).getStationN())){
				jsonText = com.alibaba.fastjson.JSON.toJSONString(rstationInfo.get(towns.get(i).getStationN()), feature);
			}
			if(rstationInfo.containsKey(towns.get(i).getRstation())){
				jsonText = com.alibaba.fastjson.JSON.toJSONString(rstationInfo.get(towns.get(i).getRstation()), feature);
			}
			String outPutPath=FileUtils.getContextPath()+forecastIndex7DFileName+ "/" +towns.get(i).getStationN()+".json";
			FileUtils.outPutFile(outPutPath, jsonText);
			files.add(new File(outPutPath));
		}
//		MqDynamicUtils.sendFiles(files.toArray(new File[files.size()]));
		return null;
	}
	
	/*
	 * 生成未来24小时
	 */
	public static String forecast24Hour(){
		//获取全部城镇信息 info_town表
		List<InfoTownUtil> towns = new ArrayList<InfoTownUtil>();
		towns = getInfoTownData();
		//获取全部预报信息forecast_town表
		Map root = new HashMap();
		root = getForecastData(24);
		List<File> files = new ArrayList<File>(); 
		for(int i=0; i<towns.size(); i++){
			String jsonText = "";
			//如果站号存在
			if(root.containsKey(towns.get(i).getStationN())){
				jsonText = com.alibaba.fastjson.JSON.toJSONString(root.get(towns.get(i).getStationN()));
			}
			//如果站号不存在，查找rstation
			else if(root.containsKey(towns.get(i).getRstation())){
				jsonText = com.alibaba.fastjson.JSON.toJSONString(root.get(towns.get(i).getRstation()));
			}
			//余杭区处理，用K1020代表站
			else if(towns.get(i).getStationN() == "58444"){
				jsonText = com.alibaba.fastjson.JSON.toJSONString(root.get("K1020"));
			}
			String outPutPath=FileUtils.getContextPath()+forecast24HFileName+ "/" +towns.get(i).getStationN()+".json";
			FileUtils.outPutFile(outPutPath, jsonText);
			files.add(new File(outPutPath));
		}
//		MqDynamicUtils.sendFiles(files.toArray(new File[files.size()]));
		return null;
	}
	
	/*
	 * 生成未来3天
	 */
	public static String forecast3Day(){
		//获取全部城镇信息 info_town表
				List<InfoTownUtil> towns = new ArrayList<InfoTownUtil>();
				towns = getInfoTownData();
				//获取全部预报信息forecast_town表
				Map root = new HashMap();
				root = getForecastData(3);
				List<File> files = new ArrayList<File>(); 
				for(int i=0; i<towns.size(); i++){
					String jsonText = "";
					//如果站号存在
					if(root.containsKey(towns.get(i).getStationN())){
						jsonText = com.alibaba.fastjson.JSON.toJSONString(root.get(towns.get(i).getStationN()));
					}
					//如果站号不存在，查找rstation
					else if(root.containsKey(towns.get(i).getRstation())){
						jsonText = com.alibaba.fastjson.JSON.toJSONString(root.get(towns.get(i).getRstation()));
					}
					//余杭区处理，用K1020代表站
					else if(towns.get(i).getStationN() == "58444"){
						jsonText = com.alibaba.fastjson.JSON.toJSONString(root.get("K1020"));
					}
					String outPutPath=FileUtils.getContextPath()+forecast3DFileName+ "/" +towns.get(i).getStationN()+".json";
					FileUtils.outPutFile(outPutPath, jsonText);
					files.add(new File(outPutPath));
				}
//				MqDynamicUtils.sendFiles(files.toArray(new File[files.size()]));
				return null;
	}
	
	/*
	 * 生成全国天气预报
	 */
	public static String forecastQgtq(){
		Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://172.41.129.125;DatabaseName=CMDMAP.Forecast", "sa", "");
		PreparedStatement pst = null;
		ResultSet res = null;

		//取出最新的时间
		String sqlForNewestDate = "SELECT TOP 1  ProductTime FROM XDYB_Data ORDER BY ProductTime DESC";
		
		try {
			pst = con.prepareStatement(sqlForNewestDate.toString());
			res = pst.executeQuery();
			String newestProductTime = "";
			while (res.next())
			{
				newestProductTime = res.getTimestamp("ProductTime").toString().trim();
			}
			
			String sql = "SELECT * FROM XDYB_Data WHERE ProductTime = '" + newestProductTime + "' AND ForecastSpan = '24' ORDER BY StationID DESC";
			//System.out.println(sql);
			pst = con.prepareStatement(sql.toString());
			res = pst.executeQuery();
			while (res.next()) {
				Map<String, String> data = new HashMap<String, String>();
				data.put("tempHigh", res.getString("TempHigh")==null? "0":res.getString("TempHigh").trim());
				data.put("tempLow", res.getString("TempLow")==null? "0":res.getString("TempLow").trim());
				data.put("weatherCode", res.getString("Weather12Code")==null? "0":res.getString("Weather12Code").trim());
				data.put("weatherStr", res.getString("Weather12")==null? "0":res.getString("Weather12").trim());
				String jsonText = com.alibaba.fastjson.JSON.toJSONString(data);
				String outPutPath=FileUtils.getContextPath()+forecastQgtq+ "/" +res.getString("StationID").trim()+".json";
				FileUtils.outPutFile(outPutPath, jsonText);
			}	
		} catch (Exception e) {
			e.printStackTrace();;
		} finally {
			SqlConnectionUtils.GetColsed(con, pst, res);
		}
		return null;
	}
	
	
	/*
	 * 生成全国7天气预报
	 */
	public static String forecast7DQgtq(){
		Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://172.41.129.125;DatabaseName=CMDMAP.Forecast", "sa", "");
		PreparedStatement pst = null;
		ResultSet res = null;

		//取出最新的时间
		String sqlForNewestDate = "SELECT TOP 1  ProductTime FROM XDYB_Data ORDER BY ProductTime DESC";
		
		try {
			pst = con.prepareStatement(sqlForNewestDate.toString());
			res = pst.executeQuery();
			String newestProductTime = "";
			while (res.next())
			{
				newestProductTime = res.getTimestamp("ProductTime").toString().trim();
			}
			String sql = "SELECT * FROM XDYB_Data WHERE ProductTime = '" + newestProductTime + "' ORDER BY StationID, ForecastSpan ASC";
			
			pst = con.prepareStatement(sql.toString());
			res = pst.executeQuery();
			List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
			Map<String, Object> data = new HashMap<String, Object>();
			while (res.next()) {
				if((res.getInt("ForecastSpan")/12)%2 != 0){
					data.put("weatherD", res.getString("Weather12Code") == null? "":res.getInt("Weather12Code"));
					data.put("wStrD", res.getString("Weather12") == null? "":res.getString("Weather12").trim());
				}else{
					data.put("date", dateFormat(res.getString("StartTime").split(" ")[0].replace("-",""), res.getInt("ForecastSpan")));
					data.put("Tmax", res.getString("TempHigh")==null? "":res.getFloat("TempHigh"));
					data.put("Tmin", res.getString("TempLow")==null? "":res.getFloat("TempLow"));
					data.put("weatherN", res.getString("Weather12Code")==null? "":res.getInt("Weather12Code"));
					data.put("wStrN", res.getString("Weather12")==null? "":res.getString("Weather12").trim());
					l.add(data);
					data = new HashMap<String, Object>();
				}
				
				if(res.getInt("ForecastSpan") == 168){
					SerializerFeature feature = SerializerFeature.DisableCircularReferenceDetect; //禁用循环检测
					String jsonText = com.alibaba.fastjson.JSON.toJSONString(l, feature);
					String outPutPath=FileUtils.getContextPath()+forecast7DQgtq+ "/" +res.getString("StationID").trim()+".json";
					FileUtils.outPutFile(outPutPath, jsonText);
					l.clear();
				}				
			}	
		} catch (Exception e) {
			e.printStackTrace();;
		} finally {
			SqlConnectionUtils.GetColsed(con, pst, res);
		}
		return null;
	}
	
	
	/*
	 * 生成未来0-1小时雨量分布图
	 */
//	public static String forecast1HRain(){
//		String url1HRain = "http://10.135.144.9/stationimg/"+URLEncoder.encode("1小时雨量分布图")+"/";
//		String targetDir1HRain = FileUtils.getContextPath() + forecast1HRain + "/";
//		try {
//			//每个更新先清空此目录
//			if(new File(targetDir1HRain).exists()){
//				org.apache.commons.io.FileUtils.cleanDirectory(new File(targetDir1HRain));
//			}
//
//			//获取所有1小时雨量分布图文件名
//			List<String> rain1H = XmlParse.getURLPath("1小时雨量分布图");
//			for(int i=0; i<rain1H.size(); i++){
//				org.apache.commons.io.FileUtils.copyURLToFile(new java.net.URL(url1HRain.concat(rain1H.get(i))), new File(targetDir1HRain.concat(rain1H.get(i))));
//			}
//			
//			//生成json
//			List<FileData> latestJson = XmlParse.getFileDatas("1小时雨量分布图");
//			
//			String jsonText = com.alibaba.fastjson.JSON.toJSONString(latestJson);
//			String outPutPath=FileUtils.getContextPath()+forecast1HRain + "/latestJson.json";
//			FileUtils.outPutFile(outPutPath, jsonText);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
//
//	/*
//	 * 生成未来3小时雨量分布图
//	 */
//	public static String forecast3HRain(){
//		String url3HRain = "http://10.135.144.9/stationimg/"+URLEncoder.encode("3小时雨量分布图")+"/";
//		String targetDir3HRain = FileUtils.getContextPath() + forecast3HRain + "/";
//		try {
//			//每个更新先清空此目录
//			if(new File(targetDir3HRain).exists()){
//				org.apache.commons.io.FileUtils.cleanDirectory(new File(targetDir3HRain));
//			}
//			
//			//获取所有3小时雨量分布图文件名	
//			List<String> rain3H = XmlParse.getURLPath("3小时雨量分布图");
//			for(int i=0; i<rain3H.size(); i++){
//				org.apache.commons.io.FileUtils.copyURLToFile(new java.net.URL(url3HRain.concat(rain3H.get(i))), new File(targetDir3HRain.concat(rain3H.get(i))));
//			}
//			
//			//生成json
//			List<FileData> latestJson = XmlParse.getFileDatas("3小时雨量分布图");
//			
//			String jsonText = com.alibaba.fastjson.JSON.toJSONString(latestJson);
//			String outPutPath=FileUtils.getContextPath()+forecast3HRain + "/latestJson.json";
//			FileUtils.outPutFile(outPutPath, jsonText);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
//	
//	/*
//	 * 未来3小时短临预报
//	 */
//	public static String forecastText(){
//		String source = "ftp://10.135.145.11/gzhfw/xly/dqyb/dlyb.txt";
//		String target = FileUtils.getContextPath() + "/dlyb.txt";
//		try {
//			List<File> files = new ArrayList<File>(); 
//			org.apache.commons.io.FileUtils.copyURLToFile(new java.net.URL(source), new File(target));
//			
//			File file = new File(target);
//			String jsonText = "";
//			if(file.exists()){
//				 InputStreamReader read = new InputStreamReader(
//		                    new FileInputStream(file),"GBK");//考虑到编码格式
//		         BufferedReader bufferedReader = new BufferedReader(read);
//		         String lineTxt = null;
//		         while((lineTxt = bufferedReader.readLine()) != null){
//		        	 jsonText += lineTxt;
//		        	 System.out.println(lineTxt);
//		         }
//		         read.close();
//			}
//			jsonText = com.alibaba.fastjson.JSON.toJSONString(jsonText);
//			String outPutPath=FileUtils.getContextPath()+ "/dlyb.json";
//			File fileJson = new File(outPutPath);
//			FileUtils.outPutFile(outPutPath, jsonText);
//			files.add(file);
//			files.add(fileJson);
////			MqDynamicUtils.sendFiles(files.toArray(new File[files.size()]));
//			new File(target).delete();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
}
