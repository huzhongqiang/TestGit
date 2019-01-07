package cn.grassinfo.common.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.grassinfo.common.Constant;
import cn.grassinfo.wap.entity.StationAqi;

/**
 * 获取aqi 相关数据
 * 
 * @author luofc
 * @date 2015-08-18
 */
@RestController
public class AqiApi {

	private static final Logger log = LoggerFactory.getLogger(AqiApi.class);


	/**
	 * aqi详细数据 点击AQI进入AQI详情页，使用http://10.135.144.204:8080/ex.asmx/
	 * GetStationAQIsRecentTime，
	 * 返回的数据显示的显示的是15时就是15时的数据，就写15点20分更新，然后下面详细站点除了杭州市区的8个，
	 * 再加上萧山的城厢镇，余杭的临平镇，淳安的千岛湖，以及杭州本市市区平均，共12个站点显示
	 * @throws ParseException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "aqi.jspx", method = RequestMethod.GET)
	public  Map<String, Object> createAqiData() throws ParseException, IOException {
			Map<String, Object> result = new HashMap<>();
			URL url = new URL("http://10.135.144.204:8080/ex.asmx/GetStationAQIsRecentTime?time=");
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
			String preWeather = Constant.propFileManager.getString("preWeather");
			if (jsonArray != null && jsonArray.size() > 0) {
				for (int i = 0; i < jsonArray.size(); i++) {
					com.alibaba.fastjson.JSONObject jsonObject = jsonArray.getJSONObject(i);
					String station = jsonObject.getString("Station");
					if (station != null && !"58459".equals(station)) {
						Boolean flag = true;
						String time = jsonObject.getString("Time");
						com.alibaba.fastjson.JSONArray jsonArrayChildren = jsonObject.getJSONArray("StationRealAQIs");
						if (jsonArrayChildren != null && jsonArrayChildren.size() > 0 && flag) {
							for (int y = 0; y < jsonArrayChildren.size(); y++) {
								com.alibaba.fastjson.JSONObject jsonObjectChild = jsonArrayChildren.getJSONObject(y);
								StationAqi sa = new StationAqi();
								sa.setAqi(StringUtils.isEmpty(jsonObjectChild.getString("Value")) ? null
										: Integer.parseInt(jsonObjectChild.getString("Value")));
								sa.setAqiLevel(jsonObjectChild.getString("Level"));
								sa.setMainPollution(jsonObjectChild.getString("MainPollution"));
								sa.setPm25(jsonObjectChild.getString("PM25"));
								sa.setPm10(jsonObjectChild.getString("PM10"));
								sa.setTime(time);
								sa.setTitleImg(preWeather+"/weixin/"+getLevel(sa.getAqiLevel())+".png");
								result.put("info", sa);
								SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
								SimpleDateFormat today = new SimpleDateFormat("yyyy年MM月dd日HH时发布");
								result.put("observTimes", today.format(sdf.parse(sa.getTime())));
								result.put("result", 1);
							}
						}
					}
//					
				}
			}
			return result;
	}

	/**
	 * 对24aqi数据的优化
	 * 
	 * @param args
	 */

//	public static void createAqi24Data() {
//
//		try {
//			// Map<String,String>
//			// stationArea=AreaAction.getAllStationAreasReplacek1207();
//
//			// 获取数据
//			Map<String, Object> results = new HashMap<String, Object>();
//			Map<String, Object> results1 = new HashMap<String, Object>();
//			Map<String, Object> results2 = new HashMap<String, Object>();
//			set24hAqiData(results1);// aqi
//			get24HourPMData(results2);// pm2.5
//			if (null != results1 && results1.size() > 0) {
//				String station = null;
//				List<Object> list = null;
//				for (Map.Entry<String, Object> map : results1.entrySet()) {
//					list = new ArrayList<Object>();
//					station = map.getKey();
//					list.add(results1.get(station));
//					list.add(results2.get(station));
//					results.put(station, list);
//				}
//			}
//
//			if (null != results && results.size() > 0) {
//				List<File> files = new ArrayList<File>();
//				for (Map.Entry<String, Object> map : results.entrySet()) {
//					String jsonText = com.alibaba.fastjson.JSON.toJSONString(map.getValue());
//					String outPutPath = FileUtils.getContextPath() + "aqi/24h_" + map.getKey() + ".json";
//					FileUtils.outPutFile(outPutPath, jsonText);
//					files.add(new File(outPutPath));
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 获取24aqi数据规则 没有数据的站点 找上一个时间的数据，直到找到为止
//	 * 
//	 * @param results
//	 * @param stationArea
//	 */
//	private static void set24hAqiData(Map<String, Object> results) {
//		StringBuilder json = new StringBuilder();
//		Calendar now = Calendar.getInstance();// 3小时预报
//		now.setTime(new Date());
//		String url = Constant.propFileManager.getString("RecentHour");
//		if (null == url || StringUtils.isEmpty(url)) {
//			url = "http://10.135.144.204:8080/ex.asmx/RecentHour?time=";
//		}
//		try {
//			try {
//				URL uri = new URL(url + df3.format(now.getTime()));
//				URLConnection yc = uri.openConnection();
//				InputStreamReader isr = new InputStreamReader(yc.getInputStream(), Charset.forName("UTF-8"));
//				BufferedReader in = new BufferedReader(isr);
//				String inputLine = null;
//				while ((inputLine = in.readLine()) != null) {
//					json.append(inputLine);
//				}
//				in.close();
//
//			} catch (MalformedURLException e) {
//				log.debug(e.getMessage());
//			} catch (IOException e) {
//				log.debug(e.getMessage());
//			}
//
//			// 站点 时次 数据
//			Map<String, LinkedHashMap<String, Object>> mapSc = new HashMap<String, LinkedHashMap<String, Object>>();
//
//			LinkedHashMap<String, Object> sc = new LinkedHashMap<String, Object>();
//			String timeSc = null;
//			Calendar now2 = Calendar.getInstance();// 3小时预报
//			now2.setTime(new Date());
//			Integer emptiy = new Integer(0);
//			String mostLastTime = null;
//			for (int i = 0; i < 24; i++) {
//				timeSc = df3.format(now2.getTime());
//				sc.put(timeSc, emptiy);
//				now2.add(Calendar.HOUR_OF_DAY, -1);
//			}
//
//			mostLastTime = df3.format(now2.getTime());
//
//			JSONArray jsonArray = null;
//			try {
//				jsonArray = new JSONArray(json.toString());
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			int iSize = jsonArray.length();
//			String Station = null;
//			String time = null;
//			List<String> lastStations = new ArrayList<String>();
//			List<String> scLast = new ArrayList<String>();
//			for (int i = 0; i < iSize; i++) {
//				JSONArray jsonA = jsonArray.getJSONArray(i);
//				for (int j = 0; j < jsonA.length(); j++) {
//					JSONObject jo = (JSONObject) jsonA.get(j);
//					Station = jo.get("Station").toString().trim();
//					String childJson = jo.get("Datas").toString().trim();
//					LinkedHashMap<String, Object> sc2 = new LinkedHashMap<String, Object>();
//					sc2.putAll(sc);
//					if (null != childJson && !StringUtils.isEmpty(childJson)) {
//						JSONArray jsonChildArray = new JSONArray(childJson);
//						if (jsonChildArray != null && jsonChildArray.length() > 0) {
//							for (int k = 0; k < jsonChildArray.length(); k++) {
//								JSONObject joB = (JSONObject) jsonChildArray.get(k);
//								time = joB.getString("Time");
//								if (!StringUtils.isEmpty(time)) {
//									time = time.trim();
//								}
//								if (sc2.containsKey(time)) {
//									sc2.put(time, StringUtils.isEmpty(joB.getString("Value")) ? null
//											: joB.getString("Value").trim());
//									if (!scLast.contains(time)) {
//										scLast.add(time);
//									}
//								}
//							}
//						}
//
//						mapSc.put(Station, sc2);
//					} else {
//						// 对于没有数据取上一个时次的数据 直到取到为止
//						lastStations.add(Station);
//					}
//				}
//			}
//
//			// 站点没有数据 最后一个数据要到上个时次取
//			if (null != lastStations && lastStations.size() > 0) {
//				setDataByStations(now, url, lastStations, sc, mapSc);
//			}
//
//			if (null != scLast && scLast.size() != 24) {
//				String timeSc3 = null;
//				Calendar now23 = Calendar.getInstance();
//				now23.setTime(df3.parse(mostLastTime));
//				List<String> scTime2 = new ArrayList<String>();
//				for (Map.Entry<String, Object> map : sc.entrySet()) {
//					if (!scLast.contains(map.getKey())) {
//						scTime2.add(map.getKey());
//					}
//				}
//
//				for (int i = 0; i < scTime2.size(); i++) {
//					timeSc3 = df3.format(now23.getTime());
//					setDataByTimes(timeSc3, url, mapSc);
//					now23.add(Calendar.HOUR_OF_DAY, -1);
//				}
//			}
//
//			// 整理数据
//			if (null != mapSc && mapSc.size() > 0) {
//				LinkedHashMap<String, Object> sc3 = null;
//				LinkedList<Object> list = null;
//				String station = null;
//
//				for (Map.Entry<String, LinkedHashMap<String, Object>> map : mapSc.entrySet()) {
//					station = map.getKey();
//					sc3 = map.getValue();
//					list = new LinkedList<Object>();
//					String dataStr = "";
//					String nowCorrectTime = null;
//					for (Map.Entry<String, Object> map2 : sc3.entrySet()) {
//						if (!"0".equals(String.valueOf(map2.getValue()))) {
//							if (StringUtils.isEmpty(nowCorrectTime)) {
//								nowCorrectTime = map2.getKey();
//							}
//							list.add(map2.getValue());
//						}
//					}
//					Collections.reverse(list);// 倒序
//					Map<String, Object> results1 = new HashMap<String, Object>();
//					results1.put("type", "aqi");
//					results1.put("time", nowCorrectTime);
//					for (int i = 0; i < list.size(); i++) {
//						dataStr += list.get(i);
//						if (i != list.size() - 1) {
//							dataStr += ",";
//						}
//					}
//					results1.put("data", dataStr);
//					results.put(station, results1);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * PM2.5最近24小时浓度
//	 * 
//	 * @param rstation
//	 * @param results
//	 * @throws JSONException
//	 */
//	public static void get24HourPMData(Map<String, Object> results) throws JSONException {
//
//		DecimalFormat dfInt = new DecimalFormat("#");
//		Calendar now = Calendar.getInstance();// 3小时预报
//		now.setTime(new Date());
//		String url = Constant.propFileManager.getString("HourReal");
//		String nowTime = null;// df3.format(now.getTime());
//
//		Map<String, Object> map = new HashMap<String, Object>();
//		LinkedHashMap<String, Object> pm25 = null;
//		int addCount = 0;
//		int m = 0;
//		for (; m < 24; m++) {
//			if (null == url || StringUtils.isEmpty(url)) {
//				url = "http://10.135.144.204:8080/ex.asmx/HourReal?time=";
//			}
//			StringBuilder json = new StringBuilder();
//			Calendar time24 = Calendar.getInstance();
//			time24.setTime(new Date());
//			time24.add(Calendar.HOUR_OF_DAY, -(m + 1));
//			try {
//				URL uri = new URL(url + df3.format(time24.getTime()));
//				URLConnection yc = uri.openConnection();
//				InputStreamReader isr = new InputStreamReader(yc.getInputStream(), Charset.forName("UTF-8"));
//				BufferedReader in = new BufferedReader(isr);
//				String inputLine = null;
//				while ((inputLine = in.readLine()) != null) {
//					json.append(inputLine);
//				}
//				in.close();
//
//			} catch (MalformedURLException e) {
//				log.debug(e.getMessage());
//			} catch (IOException e) {
//				log.debug(e.getMessage());
//			}
//			JSONArray jsonArray = new JSONArray(json.toString());
//			int iSize = jsonArray.length();
//			String Station = null;
//			String time = null;
//
//			for (int i = 0; i < iSize; i++) {
//				JSONArray jsonA = jsonArray.getJSONArray(i);
//				for (int j = 0; j < jsonA.length(); j++) {
//					JSONObject jo = (JSONObject) jsonA.get(j);
//					Station = jo.get("Station").toString().trim();
//					if (StringUtils.isEmpty(nowTime)) {
//						nowTime = jo.get("Time").toString().trim();
//					}
//					Boolean flag = false;
//					if (null != Station) {
//						pm25 = (LinkedHashMap<String, Object>) map.get(Station);
//						if (null == pm25) {
//							flag = true;
//							pm25 = new LinkedHashMap<String, Object>();
//						}
//						time = jo.getString("Time");
//						addCount = addCount + 1;
//						pm25.put(time, dfInt.format(jo.getDouble("PM25") * 1000));
//					}
//					if (flag) {
//						map.put(Station, pm25);
//					}
//				}
//			}
//		}
//
//		if (addCount < 24) {
//			for (int k = 24 - addCount; k > 0; k--) {
//				if (null == url || StringUtils.isEmpty(url)) {
//					url = "http://10.135.144.204:8080/ex.asmx/HourReal?time=";
//				}
//				StringBuilder json = new StringBuilder();
//				Calendar time24 = Calendar.getInstance();
//				time24.setTime(new Date());
//				time24.add(Calendar.HOUR_OF_DAY, -(m + 1));
//				m++;
//				try {
//					URL uri = new URL(url + df3.format(time24.getTime()));
//					URLConnection yc = uri.openConnection();
//					InputStreamReader isr = new InputStreamReader(yc.getInputStream(), Charset.forName("UTF-8"));
//					BufferedReader in = new BufferedReader(isr);
//					String inputLine = null;
//					while ((inputLine = in.readLine()) != null) {
//						json.append(inputLine);
//					}
//					in.close();
//
//				} catch (MalformedURLException e) {
//					log.debug(e.getMessage());
//				} catch (IOException e) {
//					log.debug(e.getMessage());
//				}
//				JSONArray jsonArray = new JSONArray(json.toString());
//				int iSize = jsonArray.length();
//				String Station = null;
//				String time = null;
//
//				for (int i = 0; i < iSize; i++) {
//					JSONArray jsonA = jsonArray.getJSONArray(i);
//					for (int j = 0; j < jsonA.length(); j++) {
//						JSONObject jo = (JSONObject) jsonA.get(j);
//						Station = jo.get("Station").toString().trim();
//						if (StringUtils.isEmpty(nowTime)) {
//							nowTime = jo.get("Time").toString().trim();
//						}
//						Boolean flag = false;
//						if (null != Station) {
//							pm25 = (LinkedHashMap<String, Object>) map.get(Station);
//							if (null == pm25) {
//								flag = true;
//								pm25 = new LinkedHashMap<String, Object>();
//							}
//							time = jo.getString("Time");
//							addCount = addCount + 1;
//							pm25.put(time, dfInt.format(jo.getDouble("PM25") * 1000));
//						}
//						if (flag) {
//							map.put(Station, pm25);
//						}
//					}
//				}
//			}
//		}
//
//		// 整理数据
//
//		if (null != map && map.size() > 0) {
//			String station = null;
//			LinkedHashMap<String, Object> map3 = null;
//			Map<String, Object> result = null;
//			LinkedList<Object> list = null;
//			for (Map.Entry<String, Object> map2 : map.entrySet()) {
//				station = map2.getKey();
//				map3 = (LinkedHashMap<String, Object>) map2.getValue();
//
//				if (null != map3 && map3.size() > 0) {
//					list = new LinkedList<Object>();
//					String dataStr = "";
//					for (Map.Entry<String, Object> map4 : map3.entrySet()) {
//						list.add(map4.getValue());
//					}
//					Collections.reverse(list);
//					result = new HashMap<String, Object>();
//					result.put("type", "pm2.5");
//					result.put("time", nowTime);
//					for (int i = 0; i < list.size(); i++) {
//						dataStr += list.get(i);
//						if (i != list.size() - 1) {
//							dataStr += ",";
//						}
//					}
//					result.put("data", dataStr);
//				}
//				results.put(station, result);
//			}
//		}
//	}

//	private static void setDataByStations(Calendar now, String url, List<String> lastStations, Map<String, Object> sc,
//			Map<String, LinkedHashMap<String, Object>> mapSc) {
//		try {
//			now.add(Calendar.HOUR_OF_DAY, -1);
//			StringBuilder jsonB = new StringBuilder();
//			try {
//				URL uri = new URL(url + df3.format(now.getTime()));
//				URLConnection yc = uri.openConnection();
//				InputStreamReader isr = new InputStreamReader(yc.getInputStream(), Charset.forName("UTF-8"));
//				BufferedReader in = new BufferedReader(isr);
//				String inputLine = null;
//				while ((inputLine = in.readLine()) != null) {
//					jsonB.append(inputLine);
//				}
//				in.close();
//
//			} catch (MalformedURLException e) {
//				log.debug(e.getMessage());
//			} catch (IOException e) {
//				log.debug(e.getMessage());
//			}
//
//			JSONArray jsonArray = null;
//			try {
//				jsonArray = new JSONArray(jsonB.toString());
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			int iSize = jsonArray.length();
//			String Station = null;
//			String time = null;
//			for (int i = 0; i < iSize; i++) {
//				JSONArray jsonA = jsonArray.getJSONArray(i);
//				for (int j = 0; j < jsonA.length(); j++) {
//					JSONObject jo = (JSONObject) jsonA.get(j);
//					Station = jo.get("Station").toString().trim();
//					String childJson = jo.get("Datas").toString().trim();
//					if (lastStations.contains(Station)) {
//						LinkedHashMap<String, Object> sc2 = new LinkedHashMap<String, Object>();
//						sc2.putAll(sc);
//						if (null != childJson && !StringUtils.isEmpty(childJson)) {
//							JSONArray jsonChildArray = new JSONArray(childJson);
//							if (jsonChildArray != null && jsonChildArray.length() > 0) {
//								for (int k = 0; k < jsonChildArray.length(); k++) {
//									JSONObject joB = (JSONObject) jsonChildArray.get(k);
//									time = joB.getString("Time");
//									if (!StringUtils.isEmpty(time)) {
//										time = time.trim();
//									}
//									if (sc2.containsKey(time)) {
//										sc2.put(time, StringUtils.isEmpty(joB.getString("Value")) ? null
//												: joB.getString("Value").trim());
//									}
//								}
//							}
//
//							mapSc.put(Station, sc2);
//							lastStations.remove(Station);
//						}
//					}
//				}
//			}
//
//			// 递归调用上一个时次
//			if (null != lastStations && lastStations.size() > 0) {
//				setDataByStations(now, url, lastStations, sc, mapSc);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

//	private static void setDataByTimes(String time2, String url, Map<String, LinkedHashMap<String, Object>> mapSc) {
//		try {
//			StringBuilder jsonB = new StringBuilder();
//			try {
//				URL uri = new URL(url + time2);
//				URLConnection yc = uri.openConnection();
//				InputStreamReader isr = new InputStreamReader(yc.getInputStream(), Charset.forName("UTF-8"));
//				BufferedReader in = new BufferedReader(isr);
//				String inputLine = null;
//				while ((inputLine = in.readLine()) != null) {
//					jsonB.append(inputLine);
//				}
//				in.close();
//
//			} catch (MalformedURLException e) {
//				log.debug(e.getMessage());
//			} catch (IOException e) {
//				log.debug(e.getMessage());
//			}
//
//			JSONArray jsonArray = null;
//			try {
//				jsonArray = new JSONArray(jsonB.toString());
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			int iSize = jsonArray.length();
//			String Station = null;
//			String time = null;
//			for (int i = 0; i < iSize; i++) {
//				JSONArray jsonA = jsonArray.getJSONArray(i);
//				for (int j = 0; j < jsonA.length(); j++) {
//					JSONObject jo = (JSONObject) jsonA.get(j);
//					Station = jo.get("Station").toString().trim();
//					String childJson = jo.get("Datas").toString().trim();
//					LinkedHashMap<String, Object> sc2 = mapSc.get(Station);
//					if (null != childJson && !StringUtils.isEmpty(childJson)) {
//						JSONArray jsonChildArray = new JSONArray(childJson);
//						if (jsonChildArray != null && jsonChildArray.length() > 0) {
//							for (int k = 0; k < jsonChildArray.length(); k++) {
//								JSONObject joB = (JSONObject) jsonChildArray.get(k);
//
//								time = joB.getString("Time");
//								if (!StringUtils.isEmpty(time)) {
//									time = time.trim();
//								}
//								if (time2.equals(time)) {
//									sc2.put(time, StringUtils.isEmpty(joB.getString("Value")) ? null
//											: joB.getString("Value").trim());
//								}
//							}
//						}
//						mapSc.put(Station, sc2);
//					}
//
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	public static void create7DayData() {
//		try {
//
//			Map<String, Object> datas = new HashMap<String, Object>();
//
//			Map<String, Object> datas1 = new HashMap<String, Object>();
//			int type = 1;
//			String url = Constant.propFileManager.getString("GetIAQIPoconPM");
//			if (null == url || StringUtils.isEmpty(url)) {
//				url = "http://10.135.144.204:8080/ex.asmx/GetIAQIPocon?element=pm25_value&time=";
//			}
//			get7DayData(datas1, url, type);
//
//			type = 2;// api
//			url = Constant.propFileManager.getString("GetIAQIPoconAQI");
//			if (null == url || StringUtils.isEmpty(url)) {
//				url = "http://10.135.144.204:8080/ex.asmx/GetAQIRecent10Day?time=";
//			}
//
//			Map<String, Object> datas2 = new HashMap<String, Object>();
//			get7DayData(datas2, url, type);
//
//			if (null != datas1 && datas1.size() > 0) {
//				String station = null;
//				Map<String, Object> map2 = null;
//				LinkedHashMap<String, Object> map3 = null;
//				LinkedList<Object> list = null;
//				List<Object> resultList = null;
//				for (Map.Entry<String, Object> map1 : datas1.entrySet()) {
//					station = map1.getKey();
//					resultList = new ArrayList<Object>();
//					map2 = (Map<String, Object>) map1.getValue();
//					if (null != map2 && map2.size() > 0) {
//						map3 = (LinkedHashMap<String, Object>) map2.get("data");
//						if (null != map3 && map3.size() > 0) {
//							list = new LinkedList<Object>();
//							for (Map.Entry<String, Object> map4 : map3.entrySet()) {
//								list.add(map4.getValue());
//							}
//						}
//					}
//
//					map2.put("data", list);
//
//					resultList.add(map2);
//
//					map2 = (Map<String, Object>) datas2.get(station);
//					if (null != map2 && map2.size() > 0) {
//						map3 = (LinkedHashMap<String, Object>) map2.get("data");
//						if (null != map3 && map3.size() > 0) {
//							list = new LinkedList<Object>();
//							for (Map.Entry<String, Object> map4 : map3.entrySet()) {
//								list.add(map4.getValue());
//							}
//						}
//					}
//
//					map2.put("data", list);
//					resultList.add(map2);
//					datas.put(station, resultList);
//				}
//			}
//
//			if (null != datas && datas.size() > 0) {
//				List<File> files = new ArrayList<File>();
//				for (Map.Entry<String, Object> map : datas.entrySet()) {
//					String jsonText = com.alibaba.fastjson.JSON.toJSONString(map.getValue());
//					String outPutPath = FileUtils.getContextPath() + "aqi/7d_" + map.getKey() + ".json";
//					FileUtils.outPutFile(outPutPath, jsonText);
//					files.add(new File(outPutPath));
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * 
	 * @param sJson
	 *            7天实况
	 * @param rstation
	 *            区域自动站
	 * @throws JSONException
	 */
	public static void get7DayData(Map<String, Object> datas, String url, int type) throws JSONException {
		SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMdd");
		DecimalFormat dfInt = new DecimalFormat("#");
		StringBuilder json = new StringBuilder();
		Calendar now = Calendar.getInstance();

		now.setTime(new Date());
		//now.add(Calendar.DAY_OF_MONTH, -1);
		
	
		try {
			URL uri = new URL(url + df3.format(now.getTime()));
			URLConnection yc = uri.openConnection();
			InputStreamReader isr = new InputStreamReader(yc.getInputStream(), Charset.forName("UTF-8"));
			BufferedReader in = new BufferedReader(isr);
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				json.append(inputLine);
			}
			in.close();

		} catch (MalformedURLException e) {
			log.debug(e.getMessage());
		} catch (IOException e) {
			log.debug(e.getMessage());
		}
		JSONArray jsonArray = new JSONArray(json.toString());
		int iSize = jsonArray.length();
		String Station = null;

		// 每个站点的时间及数据
		Map<String, Object> result = null;
		// 7天实况数据
		LinkedHashMap<String, Object> oneData = null;

		for (int i = 0; i < iSize; i++) {
			JSONArray jsonA = jsonArray.getJSONArray(i);
			for (int j = 0; j < jsonA.length(); j++) {
				JSONObject jo1 = (JSONObject) jsonA.get(j);
				Station = jo1.get("Station").toString().trim();
				if (null != Station) {
					result = new HashMap<String, Object>();
					JSONObject jo = (JSONObject) jsonA.get(j);
					Station = jo.get("Station").toString().trim();
					if (type == 1) {
						result.put("type", "pm2.5");
					} else {
						result.put("type", "aqi");
					}

					List<String> date = new ArrayList<String>();
					int len = 0;
//					if(type == 1){
//						//pm2.5
//						len = 1;
//					}else{
//						//aqi
//						len = 0;
//					}
					
					for(int k=6; k>=len; k--){
						Calendar d = Calendar.getInstance();
						d.add(Calendar.DAY_OF_MONTH, -1);
						d.add(Calendar.DAY_OF_MONTH, -1*k);
						date.add(df3.format(d.getTime()));
					}
					
					result.put("time", date);
					String childJson = jo.get("IaqiDatas").toString().trim();

					if (null != childJson && !StringUtils.isEmpty(childJson)) {
						oneData = new LinkedHashMap<String, Object>();

						JSONArray jsonChildArray = new JSONArray(childJson);
						if (jsonChildArray != null && jsonChildArray.length() > 0) {
							for (int k = 3; k < jsonChildArray.length(); k++) {
								JSONObject joB = (JSONObject) jsonChildArray.get(k);
								if (type == 2) {
									oneData.put(joB.getString("Time"), StringUtils.isEmpty(joB.getString("Value"))
											? null : Integer.valueOf(dfInt.format(joB.getDouble("Value"))));
								} else {
									oneData.put(joB.getString("Time"), StringUtils.isEmpty(joB.getString("Value"))
											? null : Integer.valueOf(dfInt.format(joB.getDouble("Value") * 1000)));
								}

							}
						}

						result.put("data", oneData);
					}
					datas.put(Station, result);
				}
			}
		}

//		if (type == 2) {
//			StringBuilder jsonB = new StringBuilder();
//			// 1天预报
//			url = Constant.propFileManager.getString("oneDayForecast");
//			if (null == url || StringUtils.isEmpty(url)) {
//				url = "http://10.135.144.204:8080/ex.asmx/DayForecast?time=";
//			}
//			try {
//				URL uri = new URL(url + df3.format(now.getTime()));
//				URLConnection yc = uri.openConnection();
//				InputStreamReader isr = new InputStreamReader(yc.getInputStream(), Charset.forName("UTF-8"));
//				BufferedReader in = new BufferedReader(isr);
//				String inputLine = null;
//				while ((inputLine = in.readLine()) != null) {
//					jsonB.append(inputLine);
//				}
//				in.close();
//
//			} catch (MalformedURLException e) {
//				log.debug(e.getMessage());
//			} catch (IOException e) {
//				log.debug(e.getMessage());
//			}
//			jsonArray = new JSONArray(jsonB.toString());
//			iSize = jsonArray.length();
//			Station = null;
//			Map<String, Object> map = null;
//			for (int i = 0; i < iSize; i++) {
//				JSONArray jsonA = jsonArray.getJSONArray(i);
//				for (int j = 0; j < jsonA.length(); j++) {
//					JSONObject jo = (JSONObject) jsonA.get(j);
//					Station = jo.get("Station").toString().trim();
//					if (null != Station) {
//						map = (Map<String, Object>) datas.get(Station);
//						if (null != map && map.containsKey("data")) {
//							oneData = (LinkedHashMap<String, Object>) map.get("data");
//
//						}
//						oneData.put(jo.get("Time").toString().trim(),
//								StringUtils.isEmpty(jo.getString("Value")) ? null : jo.getInt("Value"));
//					}
//				}
//
//			}
//		}

	}
	
	
	private String getLevel(String string) {
		// TODO Auto-generated method stub
		String img="1";
		switch (string) {
		case "优":
			img="1";
			break;
		case "良":
			img="2";
			break;
		case "轻度污染":
			img="3";
			break;
		case "中度污染":
			img="4";
			break;
		case "重度污染":
			img="5";
			break;
		case "严重污染":
			img="6";
			break;

		default:
			img="1";
			break;
		}
		
		return img;
	}
	
	public static void main(String[] args) {

//		createAqi24Data();
//		create7DayData();

		// create7DayData();

	}

}
