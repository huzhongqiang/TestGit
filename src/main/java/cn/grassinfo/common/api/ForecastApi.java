package cn.grassinfo.common.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.grassinfo.common.Constant;
import cn.grassinfo.common.utils.ContinueFTP;
import cn.grassinfo.common.utils.SqlConnectionUtils;
import cn.grassinfo.common.utils.WindUtils;

/**
 * 获取三天七天预报数据
 * @author huhengbo
 *
 */
@RestController
public class ForecastApi {

	private static final Logger log = LoggerFactory.getLogger(ForecastApi.class);
	private static final SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日HH时发布");
	private static final SimpleDateFormat df4 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMddHHmm");
	private static final SimpleDateFormat df5 = new SimpleDateFormat("MM月dd日");
	private static final String preWeather = Constant.propFileManager.getString("preWeather");
	
	private static final String dqtq = Constant.propFileManager.getString("dqtq");
	private static final String ftpUr = Constant.propFileManager.getString("ftpUr");
	private static final String ftpUser = Constant.propFileManager.getString("ftpUser");
	private static final String ftpPass = Constant.propFileManager.getString("ftpPass");
	private static final Integer ftpPort =Integer.parseInt(Constant.propFileManager.getString("ftpPort"));


	/**
	 * 萧山七天预报
	 * @throws SQLException 
	 */
	@RequestMapping(value = "forecast7Day.jspx", method = RequestMethod.GET)
	public static  Map forecast7Day() throws ParseException, IOException, SQLException {
		
		
		Map<String, Object> result = new HashMap<>();
		List<Map<String, String>> list = new ArrayList<>();
		Date date=new Date();
		String time = df1.format(date);
		Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://10.135.144.203;DatabaseName=PubcastBiz", "hzqx", "58457");
		PreparedStatement pst = null;
		ResultSet res = null;
		String sql = "";
		sql = "select top 14 * from [PubcastBiz].[dbo].[ForecastInfo] where ForecastID like '58459-"+time+"%' order by ForecastID desc , Hours asc";
		pst = con.prepareStatement(sql.toString());
		res = pst.executeQuery();
		String info = "";
		String dd = "";
		String title = "";
		String windDS = "";
		String windDE = "";
		String windVE = "";
		Double maxTemp = -999d;
		Double minTemp = 999d;
		String weatherStrS = "";
		String weatherStrE = "";
		String titleImg = "";
		Boolean first = true;
		Integer day = Integer.parseInt(df1.format(date).substring(6));
		Integer FDday = day;
		while (res.next()){
			Map<String, String> map = new HashMap<>();
			String ForecastID = res.getString("ForecastID").split("-")[1];
			title = "七天天气：萧山气象台" +df2.format(df3.parse(ForecastID));
			Integer hours = res.getInt("Hours");
			if (res.getInt("MaxTemp")!=9999) {
				maxTemp =res.getInt("MaxTemp")/10d;
			}
			if (res.getInt("MinTemp")!=9999) {
				minTemp =res.getInt("MinTemp")/10d;
			}
			Integer weather = res.getInt("Weather");
			String FDTime = df3.format(res.getTimestamp("FDTime")); //yyyyMMddHHmm
			String hour = FDTime.substring(8,10);
			String nowTime = df5.format(res.getTimestamp("FDTime"));
			if (first) {
				FDday = Integer.parseInt(FDTime.substring(6, 8));
				titleImg = preWeather+"/weixin/wither_img/"+res.getString("weather").trim()+".png";
				first = false;
			}
			if ("08".equals(hour)) {
				weatherStrS = (String) WindUtils.weatherInfoMap.get(weather.toString());
				windDS = WindUtils.getWindDStr(res.getInt("WindD"));//风向
			}
			if ("20".equals(hour)) {
				String icon = "";
				weatherStrE = (String) WindUtils.weatherInfoMap.get(weather.toString());
				icon = preWeather+"/WForecast/WeatherIcon/64/day/"+res.getString("weather").trim()+".png";
				if (weatherStrS!="") {
					if (!weatherStrS.equals(weatherStrE)) {
						weatherStrE=weatherStrS+"转"+weatherStrE;
					}
				}
				windDE = WindUtils.getWindDStr(res.getInt("WindD"));//风向
				windVE = WindUtils.getWindVStr(res.getInt("WindV"));//风速
				if (windDS!="") {
					if (!windDS.equals(windDE)) {
						windDE = windDS +"转"+windDE;
					}
				}
				String temp="";
				if (maxTemp!= -999d && minTemp!=999d) {
					temp="，"+minTemp+"~"+maxTemp+"℃";
				}
				
				info=nowTime+"，"+weatherStrE+"，"+windDE+windVE+temp+"。";
				map.put("icon", icon);
				map.put("info", info);
				list.add(map);
				weatherStrE="";
				windDE="";
				windVE="";
				maxTemp = -999d;
				minTemp = 999d;
			}
		}
		int index=-1;
		String firstDay=FDday+"日";
		if (FDday<10) {
			firstDay="0"+FDday+"日";
		}
		for (Map<String, String> fore : list) {
			String infomation = fore.get("info");
			index = infomation.indexOf(firstDay);
			if (index >=0) {
				break;
			}
		}
		//没有当天预报，查找上一次预报信息
		if (index < 0) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(calendar.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
			date=calendar.getTime();
			time = df1.format(date);
			sql = "select top 1 * from [dbo].[ForecastInfo] where ForecastID like '58459-"+time+"%'  and Hours = 24 order by ForecastID desc ";
			pst = con.prepareStatement(sql.toString());
			res = pst.executeQuery();
			while (res.next()){
				Map<String, String> map = new HashMap<>();
				weatherStrE = (String) WindUtils.weatherInfoMap.get(res.getString("Weather"));
				windDE = WindUtils.getWindDStr(res.getInt("WindD"));//风向
				windVE = WindUtils.getWindVStr(res.getInt("WindV"));//风速
				if (res.getInt("MaxTemp")!=9999) {
					maxTemp =res.getInt("MaxTemp")/10d;
				}
				if (res.getInt("MinTemp")!=9999) {
					minTemp =res.getInt("MinTemp")/10d;
				}
				String temp="";
				if (maxTemp!= -999d && minTemp!=999d) {
					temp="，"+minTemp+"~"+maxTemp+"℃";
				}
				String icon = preWeather+"/WForecast/WeatherIcon/64/day/"+res.getString("weather").trim()+".png";
				info=df5.format(new Date())+"，"+weatherStrE+"，"+windDE+windVE+temp+"。";
				map.put("icon", icon);
				map.put("info", info);
				list.add(0,map);
			}
		}
		if (list.size()>7) {
			list.remove(7);
		}
		result.put("result", 1);
		result.put("title", title);
		result.put("titleImg",titleImg);
		result.put("data", list);
		return result;
		
		
		/*String date = df1.format(new Date());
		Map<String, Object> result = new HashMap<>();
		List<Map<String, String>> list = new ArrayList<>();
		URL url = new URL("http://www.hzqx.com/hztq/data/forecastIndex7DData/58459.json");
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
		String title="";
		title = "萧山气象台"+df2.format(jsonArray.getJSONObject(0).getDate("publishTime"))+"一周预报";
		String titleImg = "";
		if (jsonArray != null && jsonArray.size() > 0) {
			for (int i = 0; i < jsonArray.size(); i++) {
				Map<String, String> map = new HashMap<>();
				String dayInfo = "";
				com.alibaba.fastjson.JSONObject jsonObject = jsonArray.getJSONObject(i);
				String forecastDate = jsonObject.getString("date");
				String dd = "";
				String icon = "";
				if (date.equals(forecastDate)) {
					titleImg = preWeather+"/weixin/wither_img/"+jsonObject.getString("weatherN").trim()+".png";
				}
				icon = preWeather+"/WForecast/WeatherIcon/64/day/"+jsonObject.getString("weatherN").trim()+".png";
				String forecastTime = df5.format(df1.parse(jsonObject.getString("date")));
				if ("0".equals(forecastTime.substring(0, 1))) {
					forecastTime=forecastTime.substring(1);
				}
				dayInfo = dayInfo+forecastTime+"，";
				if (jsonObject.getString("wStrD").equals(jsonObject.getString("wStrN"))) {
					dayInfo = dayInfo+ dd+jsonObject.getString("wStrD")+"，"+jsonObject.getString("windd");
				}else {
					dayInfo = dayInfo+ dd+jsonObject.getString("wStrD")+"转"+jsonObject.getString("wStrN")+"，"+jsonObject.getString("windd");
				}
				
				String windv = jsonObject.getString("windv");
				if ("<".equals(windv.substring(0, 1))) {
					dayInfo = dayInfo +"小于"+windv.substring(1,2)+"级";
				}else if (">".equals(windv.substring(0, 1))) {
					dayInfo = dayInfo +"大于"+windv.substring(1,2)+"级";
				}
				dayInfo = dayInfo+"，"+jsonObject.getString("Tmin")+"~"+jsonObject.getString("Tmax")+"℃：";
				map.put("info", dayInfo);
				map.put("icon", icon);
				list.add(map);
			}
		}
		result.put("result", 1);
		result.put("title", title);
		result.put("titleImg",titleImg);
		result.put("data", list);*/
//		return result;
	}
	
	/**
	 * 今明后3天的预报数据
	 * @throws SQLException 
	 */
	@RequestMapping(value = "forecast3Day.jspx", method = RequestMethod.GET)
	public static  Map forecast3Day() throws ParseException, IOException, SQLException {
		/**
		 * 2017/02/23修改数据源\\172.41.129.173\yewuSVR\121voc\58459\xstq58459.txt
		 * 账号：HZQXDOMAIN\xsqx
		 * 密码：58459
		 */
		Map<String, Object> result = new HashMap<>();
		String title = "";
		String info = "";
		Boolean con = ContinueFTP.connect(ftpUr, ftpPort, ftpUser, ftpPass);
		if (con) {
			ContinueFTP.ftpClient.enterLocalPassiveMode();
			InputStream in = ContinueFTP.ftpClient.retrieveFileStream(dqtq);
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"));
			String tt = "";
			while ((tt=br.readLine()) != null) {
				info=info+tt;
			}
			ContinueFTP.disconnect();
		}
		String titleImg = preWeather+"/weixin/wither_img/0.png";	
		try {
			title = "短期天气：萧山气象台"+info.substring(info.indexOf("萧山区气象台")+6, info.indexOf("发布") + 2) ;
			info = info.substring(info.indexOf("天气预报") + 5, info.indexOf("天气趋势预报"));
			info = info.replace("。", "。\r\n").trim();
		} catch (Exception e) {
			result.put("result", 0);
			return result;
		}
		result.put("result", 1);
		result.put("title", title);
		result.put("titleImg",titleImg);
		result.put("info", info);
		return result;
		
		/*
		 * 2017/02/22修改数据源 10.135.144.203
		 */
/*		Map<String, Object> result = new HashMap<>();
		Date date=new Date();
		String time = df1.format(date);
		String info = "";
		String title = "";
		String titleImg = "";
		Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
				"jdbc:sqlserver://10.135.144.203;DatabaseName=PubcastBiz", "hzqx", "58457");
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			String sql = "";
			sql = "select top 6 * from [PubcastBiz].[dbo].[ForecastInfo] where ForecastID like '58459-" + time
					+ "%' order by ForecastID desc , Hours asc";
			pst = con.prepareStatement(sql.toString());
			res = pst.executeQuery();
			String dd = "";
			String windDS = "";
			String windDE = "";
			String windVS = "";
			String windVE = "";
			Double maxTemp = -999d;
			Double minTemp = 999d;
			String weatherStrS = "";
			String weatherStrE = "";
			Boolean first = true;
			Integer day = Integer.parseInt(df1.format(date).substring(6));
			while (res.next()) {
				String ForecastID = res.getString("ForecastID").split("-")[1];
				title = "萧山气象台" + df2.format(df3.parse(ForecastID)) + "短期预报";
				Integer hours = res.getInt("Hours");
				if (res.getInt("MaxTemp") != 9999) {
					maxTemp = res.getInt("MaxTemp") / 10d;
				}
				if (res.getInt("MinTemp") != 9999) {
					minTemp = res.getInt("MinTemp") / 10d;
				}
				Integer weather = res.getInt("Weather");
				String FDTime = df3.format(res.getTimestamp("FDTime")); //yyyyMMddHHmm
				String hour = FDTime.substring(8, 10);
				Integer FDday = Integer.parseInt(FDTime.substring(6, 8));

				if ("08".equals(hour)) {
					weatherStrS = (String) WindUtils.weatherInfoMap.get(weather.toString());
					windDS = WindUtils.getWindDStr(res.getInt("WindD"));//风向
				}
				if ("20".equals(hour)) {
					weatherStrE = (String) WindUtils.weatherInfoMap.get(weather.toString());
					if (weatherStrS != "") {
						if (!weatherStrS.equals(weatherStrE)) {
							weatherStrE = weatherStrS + "转" + weatherStrE;
						}
					}
					windDE = WindUtils.getWindDStr(res.getInt("WindD"));//风向
					windVE = WindUtils.getWindVStr(res.getInt("WindV"));//风速
					if (windDS != "") {
						if (!windDS.equals(windDE)) {
							windDE = windDS + "转" + windDE;
						}
					}
					String temp = "";
					if (maxTemp != -999d && minTemp != 999d) {
						temp = "，最高温度" + maxTemp + "℃，最低温度" + minTemp + "℃";
					}

					if (weatherStrS != "") {
						if ("后天".equals(dd)) {
							info = info + dd + weatherStrE + "，" + windDE + windVE + temp + "。";
						} else {
							info = info + dd + weatherStrE + "，" + windDE + windVE + temp + "；\n";
						}

					} else {
						info = "今天" + weatherStrE + "，" + windDE + windVE + temp + "；";
					}
					weatherStrE = "";
					windDE = "";
					windVE = "";
					maxTemp = -999d;
					minTemp = 999d;
				}
				if (day == FDday) {
					dd = "今天";
				} else if (day == FDday - 1) {
					dd = "明天";
				} else if (day == FDday - 2) {
					dd = "后天";
				} else {
					break;
				}
			}
			//没有当天预报，查找上一次预报信息
			int index = info.indexOf("今天");
			if (index < 0) {
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);
				calendar.add(calendar.DATE, -1);//把日期往后增加一天.整数往后推,负数往前移动
				date = calendar.getTime();
				time = df1.format(date);
				sql = "select top 1 * from [dbo].[ForecastInfo] where ForecastID like '58459-" + time
						+ "%'  and Hours = 24 order by ForecastID desc ";
				pst = con.prepareStatement(sql.toString());
				res = pst.executeQuery();
				while (res.next()) {
					weatherStrE = (String) WindUtils.weatherInfoMap.get(res.getString("Weather"));
					windDE = WindUtils.getWindDStr(res.getInt("WindD"));//风向
					windVE = WindUtils.getWindVStr(res.getInt("WindV"));//风速
					if (res.getInt("MaxTemp") != 9999) {
						maxTemp = res.getInt("MaxTemp") / 10d;
					}
					if (res.getInt("MinTemp") != 9999) {
						minTemp = res.getInt("MinTemp") / 10d;
					}
					info = "今天" + weatherStrE + "，" + windDE + windVE + "，最高温度" + maxTemp + "℃" + "，最低温度" + minTemp
							+ "℃；\n" + info;
				}

			} 
		} catch (Exception e) {
			log.debug("获取短期天气预报异常" + e.getMessage());
		} finally {
			SqlConnectionUtils.GetColsed(con, pst, res);
		}
		titleImg = preWeather+"/weixin/wither_img/0.png";	
		result.put("result", 1);
		result.put("title", title);
		result.put("titleImg",titleImg);
		result.put("info", info);
		return result;*/
		
//		Calendar c1 = Calendar.getInstance();
//		Calendar c2 = new GregorianCalendar(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));	
//		Date now = c2.getTime();
//		Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
//				"jdbc:sqlserver://172.41.129.5;DatabaseName=hzqxdb", "zcy", "zcy");
//		PreparedStatement pst = null;
//		ResultSet res = null;
//		Map<String, List<ForecastTownUtil>> map = new HashMap<String, List<ForecastTownUtil>>();
//		//取出最新插入时间
//		String sqlForNewestDate = "SELECT DISTINCT TOP 1 Fdatetime  FROM forecast_town ORDER BY Fdatetime DESC";
//		pst = con.prepareStatement(sqlForNewestDate.toString());
//		res = pst.executeQuery();
//		String newestFdateTime = "";
//		while (res.next())
//		{
//			newestFdateTime = res.getTimestamp("Fdatetime").toString().trim();
//		}
//		
//		String sql = "";
//		sql = "SELECT * FROM forecast_town WHERE Ldatetime >= GETDATE() AND [Interval] <= '4680' AND Step <= '180' AND Fdatetime='" + newestFdateTime  + "' And stationN='58459' ORDER BY Ldatetime ASC";
//		pst = con.prepareStatement(sql.toString());
//		res = pst.executeQuery();
//		String time = df1.format(new Date());
//		String info = "";
//		String tt = "今天";
//		String dd = "";
//		String timeRange = "";
//		String winddS = "";
//		String winddE = "";
//		Integer windvS = 999;
//		Integer windvE = -999;
//		String weatherStr = "";
//		Double temMax = -999d;
//		Double temMin = 999d;
//		String title = "";
//		String titleImg = "";
//		Boolean first = true;
//		while (res.next()) {
//			title = "萧山气象台" +df2.format(res.getTimestamp("Fdatetime"))+"短期预报";
//			String date = res.getTimestamp("LdateTime").toString().split(" ")[0];
//			if(new GregorianCalendar(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1])-1, Integer.parseInt(date.split("-")[2])).getTime().compareTo(now) >= 0){
//				if(Integer.parseInt(res.getTimestamp("Ldatetime").toString().split(" ")[0].split("-")[2]) >= new Date().getDate()){
//					String hour = res.getTimestamp("Ldatetime").toString().substring(11,13);
//					Integer day = Integer.parseInt(date.substring(8));
//					if ("20".equals(hour)) {
//						day++;
//					}
//					Integer windV = Integer.parseInt(res.getString("windv").replaceAll("级", "").trim());
//					if (tt.equals(dd) || "".equals(dd)) {
//						//查找当天最大最低风速
//						if (windV>windvE) {
//							windvE = windV;
//						}
//						if (windV<windvS) {
//							windvS = windV;
//						}
//						Double tem = res.getDouble("Tem");
//						Double Tmax = res.getDouble("Tmax");
//						Double Tmin = res.getDouble("Tmin");
//						//查找当天最大最低气温
//						if (tem>temMax) {
//							temMax = tem;
//						}
//						if (Tmax>temMax) {
//							temMax = Tmax;
//						}
//						if (tem<temMin) {
//							temMin = tem;
//						}
//						if (Tmin<temMin && Tmin>=-500) {
//							temMin = Tmin;
//						}
//					}else {
//						tt = dd;
//						Double tem = res.getDouble("Tem");
//						Double Tmax = res.getDouble("Tmax");
//						Double Tmin = res.getDouble("Tmin");
//						//查找当天最大最低风速
//						if (windV>windvE) {
//							windvE = windV;
//						}
//						if (windV<windvS) {
//							windvS = windV;
//						}
//						//查找当天最大最低气温
//						if (tem>temMax) {
//							temMax = tem;
//						}
//						if (Tmax>temMax) {
//							temMax = Tmax;
//						}
//						if (tem<temMin) {
//							temMin = tem;
//						}
//						if (Tmin<temMin && Tmin>=-500) {
//							temMin = Tmin;
//						}
//						String windd = "";
//						winddE = res.getString("windd").trim();
//						if (winddS!="" && !"风".equals(winddS.substring(winddS.length()-1))) {
//							winddS = winddS +"风";
//						}
//						if (!"风".equals(winddE.substring(winddE.length()-1))) {
//							winddE = winddE +"风";
//						}
//						if (winddS == "" || winddS.equals(winddE)) {
//							windd = winddE;
//						}else {
//							windd = winddS + "转" + winddE;
//						}
//						weatherStr = res.getString("weatherStr").trim();
//						info = info+dd+"夜间" + weatherStr+",";
//						info = info+windd + windvS +"到" + windvE +"级，最高温度"+temMax/10+"度,最低温度"+temMin/10+"度。";
//						winddS = "";
//						winddE = "";
//						windvS = 999;
//						windvE = -999;
//					}
//					if (Integer.parseInt(time.substring(6)) == day ) {
//						dd = "今天";
//						if (first) {
//							titleImg = preWeather+"/weixin/wither_img/"+res.getInt("weatherCode")+".png";
//							first = false;
//						}
//					}else if (Integer.parseInt(time.substring(6)) == day-1 ) {
//						dd = "明天";				
//					}else if (Integer.parseInt(time.substring(6)) == day-2 ) {
//						dd = "后天";
//					}
//					tt = dd;
//					
//					if ("05".equals(hour) || "14".equals(hour)) {
//						weatherStr = res.getString("weatherStr").trim();
//						if ("05".equals(hour)) {
//							timeRange = "上午";
//							winddS = res.getString("windd").trim();
//							info = info+dd+timeRange + weatherStr+",";
//						}else if ("14".equals(hour)) {
//							timeRange = "下午";
//							if (winddS.length()<=0) {
//								winddS = res.getString("windd").trim();
//							}
//							info = info+timeRange + weatherStr+",";
//						}
//						
//						
//					}
//					if ("17".equals(hour)) {
//						tt = "";
//					}
//				}
//				result.put("result", 1);
//				result.put("title", title);
//				result.put("titleImg",titleImg);
//				result.put("info", info);
//			}
//		}
	}
	
	public static void main(String[] args) throws ParseException, IOException, SQLException {

		forecast7Day();
	}

}
