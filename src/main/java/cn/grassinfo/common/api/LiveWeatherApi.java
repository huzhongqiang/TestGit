package cn.grassinfo.common.api;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.grassinfo.common.Constant;
import cn.grassinfo.common.utils.SqlConnectionUtils;
import cn.grassinfo.common.utils.WindUtils;
import cn.grassinfo.wap.entity.RealWeather;
/**
 * 天气实况
 * @author Administrator
 *
 */
@RestController
public class LiveWeatherApi {
	private static final Logger log = LoggerFactory.getLogger(LiveWeatherApi.class);
	private static final String preWeather = Constant.propFileManager.getString("preWeather");
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	private static final SimpleDateFormat today = new SimpleDateFormat("实况监测：萧山气象台yyyy年MM月dd日HH时mm分发布");

	/**
	 * 萧山天气实况
	 * @return
	 */
	@RequestMapping(value = "liveWeatherInfo.jspx", method = RequestMethod.GET)
	public  Map liveWeatherInfo() {
		String connStr = null;
		String userName = null;
		String password = null;
		String JDriver = null;
		if (!StringUtils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!StringUtils.isEmpty(Constant.propFileManager.getString("todayFocusConnStr"))) {
			connStr = Constant.propFileManager.getString("todayFocusConnStr");
		}
		if (!StringUtils.isEmpty(Constant.propFileManager.getString("todayFocusUserName"))) {
			userName = Constant.propFileManager.getString("todayFocusUserName");
		}
		if (!StringUtils.isEmpty(Constant.propFileManager.getString("todayFocuspassword"))) {
			password = Constant.propFileManager.getString("todayFocuspassword");
		}

		Connection conn = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		if (conn == null) {
			return null;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,Object> list = new HashMap<>();
		
		try {
			String sql = "SELECT top 1 * from tabMinuteData where StationNum='58459' order by  ObservTimes desc";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			Map<String, String> map = new HashMap<>();
			if (rs.next()) {
				map.put("RelHumidity", StringUtils.isEmpty(rs.getString("RelHumidity")) ? "" : rs.getString("RelHumidity"));
				map.put("WindDirect", StringUtils.isEmpty(rs.getString("WindDirect")) ? "" : rs.getString("WindDirect"));
				map.put("WindVelocity", StringUtils.isEmpty(rs.getString("WindVelocity")) ? "" : rs.getString("WindVelocity"));
				map.put("DryBulTemp", StringUtils.isEmpty(rs.getString("DryBulTemp")) ? "" : rs.getString("DryBulTemp"));
				map.put("StationPress", StringUtils.isEmpty(rs.getString("StationPress")) ? "" : rs.getString("StationPress"));
				map.put("Precipitation", StringUtils.isEmpty(rs.getString("PrecipitationFromHour")) ? "" : rs.getString("PrecipitationFromHour"));
				map.put("ObservTimes", StringUtils.isEmpty(rs.getString("ObservTimes")) ? "" : rs.getString("ObservTimes"));
			}
			Map<String, String> temp = new HashMap<String, String>();
			List<Map<String, String>> info = new ArrayList<>();
			for (String key : map.keySet()) {
				if ("RelHumidity".equals(key)) {
					temp.put("title", "相对湿度");
					temp.put("value", map.get(key)+"%");
					temp.put("icon", "http://115.238.71.190:8080/xstq/resources/5.png");
				}
				if ("WindDirect".equals(key)) {
					temp.put("title", "阵风风向");
					String d = WindUtils.getWindD(Double.parseDouble(StringUtils.isEmpty(map.get(key)) ? "" : map.get(key)));
					temp.put("value", d);
					temp.put("icon", "http://115.238.71.190:8080/xstq/resources/2.png");
				}
				if ("WindVelocity".equals(key)) {
					temp.put("title", "阵风风速");
					Integer v = WindUtils.getWindL(Double.parseDouble(StringUtils.isEmpty(map.get(key)) ? "" : map.get(key))/10);
					temp.put("value", v+"级");
					temp.put("icon", "http://115.238.71.190:8080/xstq/resources/1.png");
				}
				if ("DryBulTemp".equals(key)) {
					temp.put("title", "空气温度");
					temp.put("value",StringUtils.isEmpty(map.get(key)) ? "无数据": Double.parseDouble(map.get(key))/10+"℃");
					temp.put("icon", "http://115.238.71.190:8080/xstq/resources/6.png");
				}
				if ("StationPress".equals(key)) {
					temp.put("title", "本站气压");
					temp.put("value",StringUtils.isEmpty(map.get(key)) ? "无数据": Double.parseDouble(map.get(key))/10+"hpa");
					temp.put("icon", "http://115.238.71.190:8080/xstq/resources/4.png");
				}
				if ("Precipitation".equals(key)) {
					temp.put("title", "小时累计降水");
					temp.put("value",StringUtils.isEmpty(map.get(key)) ? "无数据":  Double.parseDouble(map.get(key))/10+"mm");
					temp.put("icon", "http://115.238.71.190:8080/xstq/resources/3.png");
				}
				info.add(temp);
				temp = new HashMap<>();
			}
			list.put("info",info);
			list.put("observTimes", today.format(sdf.parse(map.get("ObservTimes"))));
			list.put("result", 1);
			list.put("titleImg",preWeather+"/liveWeather.png");
			
		} catch (Exception e) {
			log.error("天气实况 error" + e.getMessage());
		} finally {
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		return list;
	}
	
	/**
	 * 萧山24H数据
	 * @return
	 */
	@RequestMapping(value = "liveWeatherDetails.jspx", method = RequestMethod.GET)
	public static  String liveWeatherDetails(HttpServletResponse response,HttpServletRequest request) {
		String jsoncallback = request.getParameter("jsoncallback");
		Map<String,Object> result = new HashMap<>();
		String connStr = null;
		String userName = null;
		String password = null;
		String JDriver = null;
		if (!StringUtils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!StringUtils.isEmpty(Constant.propFileManager.getString("todayFocusConnStr"))) {
			connStr = Constant.propFileManager.getString("todayFocusConnStr");
		}
		if (!StringUtils.isEmpty(Constant.propFileManager.getString("todayFocusUserName"))) {
			userName = Constant.propFileManager.getString("todayFocusUserName");
		}
		if (!StringUtils.isEmpty(Constant.propFileManager.getString("todayFocuspassword"))) {
			password = Constant.propFileManager.getString("todayFocuspassword");
		}

		Connection conn = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		if(conn==null){
			return null;
		}
		List<RealWeather> list = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			// 获取当前年-月-日-小时
			Calendar c = Calendar.getInstance();
			Calendar before = Calendar.getInstance();
			c.setTime(new Date());
			before.setTime(new Date());
			SimpleDateFormat observerF = new SimpleDateFormat("yyMMddHH");
			SimpleDateFormat hourF = new SimpleDateFormat("HH");
			String obserTime = observerF.format(c.getTime());
			Integer hour = Integer.valueOf(hourF.format(c.getTime()));
			String beforeOb = null;
			if (hour != 23 && hour != 00) {
				before.add(Calendar.DAY_OF_MONTH, -1);
				before.add(Calendar.HOUR_OF_DAY, +1);
				beforeOb = observerF.format(before.getTime());
			}
			if (hour == 23) {

				before.add(Calendar.DAY_OF_MONTH, -1);
				before.add(Calendar.HOUR_OF_DAY, +1);
				beforeOb = observerF.format(before.getTime());
			}
			if (hour == 0) {
				before.add(Calendar.DAY_OF_MONTH, +1);
				before.add(Calendar.HOUR_OF_DAY, -1);
				beforeOb = observerF.format(before.getTime());
			}
			Calendar date = Calendar.getInstance();
			date.setTime(new Date());
			String sql = "SELECT top 24  t.DryBulTemp,t.Precipitation, t.StationPress, t.RelHumidity, t.ObservTimes FROM tabMinuteData  t WHERE t.StationNum='58459' and ObservTimes like '%00'  order by t.ObservTimes desc";
//			String sql = "SELECT  t.DryBulTemp,t.Precipitation, t.StationPress, t.RelHumidity, t.ObservTimes FROM tabMinuteData  t WHERE t.StationNum='58459' AND t.ObservTimes>=" + beforeOb
//					+ " AND t.ObservTimes <=" + obserTime + " ORDER BY t.ObservTimes ASC";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				RealWeather mws = new RealWeather();
				mws.setTemp((rs.getFloat("DryBulTemp") / 10));
				mws.setPrecipitation(rs.getFloat("Precipitation"));
				mws.setPress(rs.getFloat("StationPress") / 10);
				mws.setHumidity(rs.getInt("RelHumidity"));
				mws.setTimes(rs.getString("ObservTimes") != null ? rs.getString("ObservTimes").trim().substring(0, 10) : null);
				list.add(mws);
			}
			result.put("pastDatas", list);
			result.put("observTimes", today.format(new Date()));
			result.put("result", 1);
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Content-Type", "text/html");
			String r = com.alibaba.fastjson.JSON.toJSONString(result);
			outPrintJosn(request, response, r, jsoncallback);
			return null;
		} catch (Exception e) {
			log.error("查询24小时详细数据异常{}"+e.getMessage());
		} finally {
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		return null;
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
	
	public static void main(String[] args) {
//		liveWeatherDetails();
//		care();
		System.out.println("哈哈哈");
	}
	
}
