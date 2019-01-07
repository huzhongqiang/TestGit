package cn.grassinfo.common.api;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.grassinfo.common.Constant;
import cn.grassinfo.common.utils.SqlConnectionUtils;

/**
 *  预警信号（全市所有预警）
 * @author luofc
 *
 */
@RestController
public class EarlyWarningApi{

	private static final long serialVersionUID = -2635169115530677334L;
	private static final Logger log = LoggerFactory.getLogger(EarlyWarningApi.class);
	private static final String preWeather = Constant.propFileManager.getString("preWeather");

	/*
	 * 气象预警预警信号获取
	 * @author huhengbo
	 */
	@RequestMapping(value = "forecastSignal.jspx", method = RequestMethod.GET)
	public static  String  getQxyjWarnList(HttpServletResponse response,HttpServletRequest request) throws ParseException {
		String jsoncallback = request.getParameter("jsoncallback");
		Map<String, Object> map = new HashMap<>();
		List<Map<String, String>> list = new ArrayList<>();
		Connection con = SqlConnectionUtils.getEarlyWarningConnection();
		if(con==null){
			return null;
		}
		PreparedStatement pst = null;
		ResultSet res = null;
		/**
		 * 查询48小时以内的未解除的预警信息
		 */ 
		String sql = "SELECT TOP 5 * FROM dbo.CurMessage WHERE (DATEDIFF(SECOND, UnTime, CAST(GETDATE() AS smalldatetime)) < 1800 OR FBStatus='发布') And Station_ID='58459' and SendSwitch ='1' Order by Ltime Desc";
		try {
			pst = con.prepareStatement(sql.toString());
			res = pst.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时");
			SimpleDateFormat today = new SimpleDateFormat("在yyyy年MM月dd日HH时mm分发布");
			Boolean first = true;
			while (res.next()) {
				String text = res.getString("text");
				String gmstext = res.getString("Gmstext");
				if (text.indexOf("test")<0 && text.indexOf("测试")<0 && gmstext.indexOf("test")<0 && gmstext.indexOf("测试")<0) {
					Map<String, String> map1 = new HashMap<String, String>();
					String name = res.getString("Name").trim();
					map1.put("number", res.getString("Number").trim());
					map1.put("unit", res.getString("Station_Name").trim());
					map1.put("type",name.substring(0,name.indexOf("色")-1 ) );
					map1.put("level", name.substring(name.indexOf("色")-1,name.indexOf("色")+1));
					map1.put("time",sdf1.format(sdf.parse(res.getString("Ltime"))));
					map1.put("remark", res.getString("text"));
//				map1.put("content", res.getString("Meaning").trim());
//				Date date = res.getDate("Ltime");
//				map1.put("dateTimes", sdf.format(sdf.parse(res.getString("Ltime"))));
					map1.put("icon",preWeather+"/index/warning/"+res.getString("Code").trim()+".jpg");
//				String temp = today.format(sdf.parse(res.getString("Ltime")));
//				map1.put("title", res.getString("Station_Name").trim()+today.format(sdf.parse(res.getString("Ltime")))+res.getString("Name").trim()+"预警");
					list.add(map1);
				}
			}
			map.put("warns", list);
			if (list.size()>0) {
				map.put("titleImg",preWeather+"/index/logo.png");
			}
			map.put("result", 1);
		} catch (SQLException e) {
			e.printStackTrace();
			//log.error("灾害预警getWarningData debug:" + e.getMessage())-1;
		} finally {
			SqlConnectionUtils.GetColsed(con, pst, res);
		}
		String r = com.alibaba.fastjson.JSON.toJSONString(map);
		outPrintJosn(request, response, r, jsoncallback);
		return null;
	}
	
	
	/*
	 * 气象预警预警信号获取测试
	 * @author huhengbo
	 */
	@RequestMapping(value = "forecastSignalTest.jspx", method = RequestMethod.GET)
	public static  String  getQxyjWarnListTest(HttpServletResponse response,HttpServletRequest request) throws ParseException {
		String jsoncallback = request.getParameter("jsoncallback");
		Map<String, Object> map = new HashMap<>();
		List<Map<String, String>> list = new ArrayList<>();
		Connection con = SqlConnectionUtils.getEarlyWarningConnection();
		if(con==null){
			return null;
		}
		PreparedStatement pst = null;
		ResultSet res = null;
		/**
		 * 查询48小时以内的未解除的预警信息
		 */ 
		String sql = "SELECT TOP 3 * FROM dbo.CurMessage WHERE (DATEDIFF(SECOND, UnTime, CAST(GETDATE() AS smalldatetime)) < 1800 OR FBStatus='发布') And Station_ID='58459' and SendSwitch ='1' Order by Ltime Desc";
		try {
			pst = con.prepareStatement(sql.toString());
			res = pst.executeQuery();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时");
			while (res.next()) {
				String text = res.getString("text");
				String gmstext = res.getString("Gmstext");
				if (text.indexOf("test")<0 && text.indexOf("测试")<0 && gmstext.indexOf("test")<0 && gmstext.indexOf("测试")<0) {
					Map<String, String> map1 = new HashMap<String, String>();
					String name = res.getString("Name").trim();
					map1.put("number", res.getString("Number").trim());
					map1.put("unit", res.getString("Station_Name").trim());
					map1.put("type",name.substring(0,name.indexOf("色")-1 ) );
					map1.put("level", name.substring(name.indexOf("色")-1,name.indexOf("色")+1));
					map1.put("time",sdf1.format(sdf.parse(res.getString("Ltime"))));
					map1.put("remark", res.getString("text"));
					map1.put("icon",preWeather+"/index/warning/"+res.getString("Code").trim()+".jpg");
					list.add(map1);
				}
			}
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("number", "YJBY-459-170426-33");
			map1.put("unit", "萧山区气象台测试");
			map1.put("type","暴雨" );
			map1.put("level", "蓝色");
			map1.put("time","2017年05月03日 04时");
			map1.put("remark", "受低涡切变东移影响，目前我区中东部部分地区已出现暴雨，预计未来3小时降雨仍将持续，并伴有6～8级大风，请注意防范。");
			map1.put("icon","http://115.238.71.190:8080/xstq/resources/static/images/index/warning/0201.jpg");
			map.put("warns", list);
			list.add(map1);
			if (list.size()>0) {
				map.put("titleImg",preWeather+"/index/Information_img.png");
			}
			map.put("result", 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.GetColsed(con, pst, res);
		}
		String r = com.alibaba.fastjson.JSON.toJSONString(map);
		outPrintJosn(request, response, r, jsoncallback);
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
}
