package cn.grassinfo.common.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
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
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.grassinfo.common.Constant;
import cn.grassinfo.common.util.FileUtils;
import cn.grassinfo.common.utils.SqlConnectionUtils;
import cn.grassinfo.wap.entity.InfoTownUtil;

/**
 * 获取乡镇当天，三天七天预报数据
 * @author huhengbo
 *
 */
@RestController
public class ForecastTownApi {

	private static final Logger log = LoggerFactory.getLogger(ForecastTownApi.class);
	private static final SimpleDateFormat df2 = new SimpleDateFormat("yyyy年MM月dd日HH时发布");
	private static final String preWeather = Constant.propFileManager.getString("preWeather");


	
	/**
	 * 获取各乡镇当天预报数据（取最近一条）
	 * @throws SQLException 
	 */
	@RequestMapping(value = "forecastTownDay.jspx", method = RequestMethod.GET)
	public  String forecastTownDay(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, IOException, SQLException {
		String jsoncallback = request.getParameter("jsoncallback");
		String result=getContentByJsonFile(FileUtils.getContextPath()+"townForecast.json");
		outPrintJosn(request, response,result, jsoncallback);
		return null;
		
	}
	
	/**
	 * 根据乡镇站点号获取乡镇3天和7天数据
	 * @throws SQLException 
	 */
	@RequestMapping(value = "forecastTownByStation.jspx", method = RequestMethod.POST)
	public static  Map forecastTownByStation(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="stationN",required = true) String stationN) {		
		
		String jsoncallback = request.getParameter("jsoncallback");
		String result=getContentByJsonFile(FileUtils.getContextPath()+stationN+".json");
		outPrintJosn(request, response,result, jsoncallback);
		return null;
		
//		Map<String, Object> result = new HashMap<>();
//		Map<String, Object> data = new HashMap<>();
//		List<Map<String, Object>> list = new ArrayList<>();
//		Calendar c1 = Calendar.getInstance();
//		Calendar c2 = new GregorianCalendar(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH));	
//		Date now = c2.getTime();
//		
//		/*
//		 * 三天预报开始
//		 */
//		Connection con = SqlConnectionUtils.getConnection("com.microsoft.sqlserver.jdbc.SQLServerDriver",
//				"jdbc:sqlserver://172.41.129.5;DatabaseName=hzqxdb", "zcy", "zcy");
//		PreparedStatement pst = null;
//		ResultSet res = null;
//		//取出最新插入时间
//		String newestFdateTime = "";
//		try {
//			String sqlForNewestDate = "SELECT DISTINCT TOP 1 Fdatetime  FROM forecast_town ORDER BY Fdatetime DESC";
//			pst = con.prepareStatement(sqlForNewestDate.toString());
//			res = pst.executeQuery();
//			while (res.next()) {
//				newestFdateTime = res.getTimestamp("Fdatetime").toString().trim();
//			} 
//		} catch (Exception e) {
//			log.error("取出最新插入时间异常{}"+e.getMessage());
//			result.put("result", 0);
//			return result;
//		}
//		try {
//			String sql = "";
//			sql = "SELECT  * FROM forecast_town WHERE Ldatetime >= GETDATE() AND [Interval] <= '4680' AND Step <= '180' AND Fdatetime='"
//					+ newestFdateTime + "' And stationN='" + stationN + "' ORDER BY Ldatetime ASC";
//			pst = con.prepareStatement(sql.toString());
//			res = pst.executeQuery();
//			while (res.next()) {
//				String date = res.getTimestamp("LdateTime").toString().split(" ")[0];
//				if (new GregorianCalendar(Integer.parseInt(date.split("-")[0]),
//						Integer.parseInt(date.split("-")[1]) - 1, Integer.parseInt(date.split("-")[2])).getTime()
//								.compareTo(now) >= 0) {
//					Map<String, Object> temp3Day = new HashMap<>();
//					if (Integer.parseInt(
//							res.getTimestamp("Ldatetime").toString().split(" ")[0].split("-")[2]) >= new Date()
//									.getDate()) {
//						String dateTime = res.getTimestamp("Ldatetime").toString().split(" ")[0].split("-")[2] + "日"
//								+ res.getTimestamp("LdateTime").toString().split(" ")[1].split(":")[0] + "时";
//						String weatherStr = res.getString("weatherStr").trim();
//						Integer hour = Integer.parseInt(dateTime.substring(3, 5));
//						String icon = preWeather + "/index/weather_icon/na.png";
//						if (hour >= 6 && hour <= 18) {
//							icon = preWeather + "/WForecast/WeatherIcon/64/day/" + res.getInt("weatherCode") + ".png";
//						} else {
//							icon = preWeather + "/WForecast/WeatherIcon/64/night/" + res.getInt("weatherCode") + ".png";
//						}
//						Double tem = res.getDouble("Tem") / 10;
//						temp3Day.put("tem", tem);
//						temp3Day.put("dateTime", dateTime);
//						temp3Day.put("weatherStr", weatherStr);
//						temp3Day.put("icon", icon);
//					}
//					list.add(temp3Day);
//				}
//			} 
//		} catch (Exception e) {
//			log.error("查询三天预报异常{}"+e.getMessage());
//			result.put("result", 0);
//			return result;
//		}
//		data.put("forecast3Day", list);
//		list= new ArrayList<>();
//		/*
//		 * 三天预报结束
//		 */
//		
//		/**
//		 * 七天预报开始
//		 */
//		try {
//			URL url = new URL("http://www.hzqx.com/hztq/data/forecastIndex7DData/"+stationN+".json");
//			StringBuffer bankXmlBuffer = new StringBuffer();
//			// 创建URL连接，提交到数据，获取返回结果
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestProperty("contentType", "UTF-8");
//			connection.setRequestMethod("GET");
//			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//			String inputLine;
//			while ((inputLine = in.readLine()) != null) {
//				bankXmlBuffer.append(inputLine);
//			}
//			in.close();
//			com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray
//					.parseArray(bankXmlBuffer.toString().replace("(", "").replace(")", ""));
//			if (jsonArray != null && jsonArray.size() > 0) {
//				for (int i = 0; i < jsonArray.size(); i++) {
//					Map<String, Object> temp7Day = new HashMap<>();
////				String dayInfo = "";
//					com.alibaba.fastjson.JSONObject jsonObject = jsonArray.getJSONObject(i);
//					String forecastDate = jsonObject.getString("date").trim();
//					Double temMax = jsonObject.getDouble("Tmax");
//					Double temMin = jsonObject.getDouble("Tmin");
//					String wStrD = jsonObject.getString("wStrD").trim();
//					String wStrN = jsonObject.getString("wStrN").trim();
//					String icon = preWeather+"/WForecast/WeatherIcon/64/day/"+jsonObject.getString("weatherN").trim()+".png";
////				String forecastTime = ss.format(df1.parse(jsonObject.getString("date")));
//					String windv = jsonObject.getString("windv").substring(1,2);
//					String windd = jsonObject.getString("windd").trim();
//					temp7Day.put("dateTime", forecastDate);
//					temp7Day.put("temMax", temMax);
//					temp7Day.put("temMin", temMin);
//					temp7Day.put("icon", icon);
//					if (wStrD.equals(wStrD)) {
//						temp7Day.put("weatherStr", wStrD);
//					}else {
//						temp7Day.put("weatherStr", wStrD+"转"+wStrN);
//					}
//					temp7Day.put("windd", windd);
//					temp7Day.put("windv", windv);
//					list.add(temp7Day);
//				}
//			}
//			data.put("forecast7Day", list);
//		} catch (Exception e) {
//			log.error("查询七天预报异常{}"+e.getMessage());
//			result.put("result", 0);
//			return result;
//		}
//		/**
//		 * 七天预报结束
//		 */
//		result.put("titleImg",preWeather+"/QXJ.jpg" );
//		result.put("data", data);
//		result.put("result", 1);
//		return result;
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
	
	public static String getContentByJsonFile(String filePath){
        
        File file = new File(filePath);
        Scanner scanner = null;
        StringBuilder buffer = new StringBuilder();
        try {
            scanner = new Scanner(file, "utf-8");
            while (scanner.hasNextLine()) {
                buffer.append(scanner.nextLine());
            }
 
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block  
 
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
         
        return buffer.toString();
	}
	
	private static void outPrintJosn(HttpServletRequest request,HttpServletResponse response,String str, String jsoncallback) {
		PrintWriter out = null;
		try {
			response.setContentType("application/json; charset=UTF-8");
			out = response.getWriter();
			if (jsoncallback!=null && jsoncallback.length()>0) {
				out.print(jsoncallback + "(" + str + ")");
			} else {
				out.print(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
	

}
