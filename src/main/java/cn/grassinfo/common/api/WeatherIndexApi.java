package cn.grassinfo.common.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.grassinfo.common.Constant;
import cn.grassinfo.common.util.FileUtils;
import cn.grassinfo.common.utils.ContinueFTP;
import cn.grassinfo.common.utils.SqlConnectionUtils;

/**
 * 萧山生活指数
 * @author huhengbo
 * @date 2017-02-07
 */
@RestController
public class WeatherIndexApi {
	private static final Logger log = LoggerFactory
			.getLogger(WeatherIndexApi.class);
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH时");
	private static final SimpleDateFormat dfull=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String preWeather = Constant.propFileManager.getString("preWeather");;
	
	//生活指数中文对应英文的名称
	private static final Map<String,String> titleMap=new HashMap<String,String>(){

		private static final long serialVersionUID = 9161066409190656855L;
		{
			put("洗车指数","03");
			put("紫外线指数","06");
			put("行车指数","07");
			put("晨练指数","08");
			put("人体舒适度","10");
			put("空气质量AQI","09");
			put("晾晒指数","04");
			put("流感指数","01");
			put("人体健康指数","02");
			put("森林火险等级","05");
		}
			
	};

//	/**
//	 * 生活指数数据查询
//	 */
//	@SuppressWarnings("unused")
//	@RequestMapping(value = "liveForecast.jspx", method = RequestMethod.GET)
	public static Map weatherIndexList() {
		Map<String,Object> all=new HashMap<String, Object>();
		Connection con = SqlConnectionUtils.getWeatherIndexConnection();
		if(con==null){
			log.error("jdbc连接失败");
			all.put("result", 0);
			return all;
		}
		PreparedStatement pst = null;
		ResultSet res = null;
		List<Map> lists = new ArrayList<Map>();
		try {
			StringBuilder bu=new StringBuilder(" declare  @timestr varchar(100)  ");
			bu.append("set @timestr =(SELECT top 1 CONVERT(varchar(100), wi.[Date],120) as time from [WeatherIndex] wi order by wi.[Date] DESC)");
			bu.append(" SELECT * from  [WeatherIndex] wm where  CONVERT(varchar(100), wm.[Date], 120)=@timestr ");
			pst = con.prepareStatement(bu.toString());
			res = pst.executeQuery();
			String title=null;
			String listTitle=null;
			String updateTime=null;
			while (res.next()) {
				Map<String,String> map=new HashMap<String, String>();
				title=res.getString("IndexName").trim();
				if (title!=null && title.indexOf("预报")>-1) {
					title = title.substring(0, title.indexOf("预报"));
				}
				listTitle=titleMap.get(title);
				if(!StringUtils.isEmpty(listTitle)){
					map.put("name",title);
					map.put("imgUrl", preWeather+"/index/loving_img/loving_img"+listTitle+".png");
					String content = res.getString("IndexYbnr").trim();
					if(content!=null && content.length()>0 && content.indexOf("详情天气咨询请拨电话")<0){
						if(content.indexOf("AQI")>-1 ){
							content = content.substring(content.indexOf("AQI"));
						}
					}
					String levelStr = res.getString("IndexYbdj").trim();
					if(!StringUtils.isEmpty(res.getString("IndexYbdj"))){
						map.put("levelStr",levelStr);
						map.put("level",res.getString("IndexYbdj").trim().substring(0, 1));
					}else{
						if (title.indexOf("AQI")>-1) {
							Integer aqi = (Integer.parseInt(content.substring(4, 6))+ Integer.parseInt(content.substring(7, 9)))/2;
							map.put("levelStr",getAqiLevel(aqi));
							map.put("level",getAqiLevel(aqi));
						}
					}
					map.put("content",content);
					lists.add(map);
				}
				if(StringUtils.isEmpty(updateTime)){
					updateTime=res.getString("Date").trim();
					updateTime=df.format(dfull.parse(updateTime));
				}
			}
			if(null!=lists&&lists.size()>0){
				all.put("titleImg", preWeather+"/weixin/liveIndex.png");
				all.put("info", lists);
				all.put("updateTime", updateTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.debug("生活指数异常:" + e.getMessage());
		} catch (ParseException e) {
			e.printStackTrace();
			log.debug("生活指数异常:" + e.getMessage());
		} finally {
			SqlConnectionUtils.GetColsed(con, pst, res);
		}
		return all;
	}
	
	public static String getAqiLevel(Integer aqi)
    {
    	String level="";
        
        //AQI 等级
        if (0 <= aqi && aqi <= 50)
        {
            level = "优";
        }
        if (51 <= aqi && aqi <= 100)
        {
            level = "良";
        }
        if (101 <= aqi && aqi <= 150)
        {
            level = "轻度污染";
        }
        if (151 <= aqi && aqi <= 200)
        {
            level = "中度污染";
        }
        if (201 <= aqi && aqi <= 300)
        {
            level = "重度污染";
        }
        if (aqi > 300)
        {
            level = "严重污染";
        }
        return level;
    }
	
	/**
	 * 生活指数数据查询
	 * 2017年3月23日15:49:23
	 * 修改为通过ftp获取
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "liveForecast.jspx", method = RequestMethod.GET)
	public  String shzs(HttpServletRequest request,
			HttpServletResponse response) throws ParseException, IOException, SQLException {
		String jsoncallback = request.getParameter("jsoncallback");
		String result=getContentByJsonFile(FileUtils.getContextPath()+"shzs.json");
		outPrintJosn(request, response,result, jsoncallback);
		return null;
		
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
	public static String getLevel(String levelStr){
		String level = levelStr;
		switch (levelStr) {
		case "一":
			level= "1";
			break;
		case "二":
			level= "2";
			break;
		case "三":
			level= "3";
			break;
		case "四":
			level= "4";
			break;
		case "五":
			level= "5";
			break;
		case "六":
			level= "6";
			break;
		case "七":
			level= "7";
			break;
		case "八":
			level= "8";
			break;
		case "九":
			level= "9";
			break;
		case "零":
			level= "0";
			break;
		default:
			break;
		}
		
		return level;
	}
	public static void main(String[] args) {
//		weatherIndexList();
//		shzs();
	}
}
