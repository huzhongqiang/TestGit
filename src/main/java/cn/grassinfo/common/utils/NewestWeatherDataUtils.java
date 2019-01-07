package cn.grassinfo.common.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.grassinfo.common.Constant;
import cn.grassinfo.common.util.Utils;

public class NewestWeatherDataUtils {
	private static DecimalFormat dfInt = new DecimalFormat("#");

	public static Map<String, String> getRecentStationWeather(String stationId,
			String rstation) {
		Connection skcon = SqlConnectionUtils.getSK2Connection();
		PreparedStatement skpst = null;
		ResultSet skres = null;
		String sksql = null;
		Map<String, String> result = null;
		List<String> list = new ArrayList<String>();
		// 获取最近站点
		try {
			sksql = "DECLARE @oldLng varchar(50),@@OldLat varchar(50) "
					+ " SET @oldLng=(SELECT stationInfo.NNnn  FROM tabStationInfo stationInfo WHERE stationInfo.IIiii=?)"
					+ " SET @@OldLat=(SELECT stationInfo.EEEee  FROM tabStationInfo stationInfo WHERE stationInfo.IIiii=?)"
					+ " SELECT stationInfo.IIiii FROM tabStationInfo stationInfo WHERE stationInfo.IIiii <>?"
					+ " ORDER BY dbo.f_GetDistance(@oldLng,@@OldLat,stationInfo.NNnn,stationInfo.EEEee) ASC";
			skpst = skcon.prepareStatement(sksql.toString());
			skpst.setMaxRows(10);
			skpst.setString(1, stationId);
			skpst.setString(2, stationId);
			skpst.setString(3, stationId);
			skres = skpst.executeQuery();
			while (skres.next()) {
				String IIiii = skres.getString("IIiii");
				list.add(IIiii);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.ColsedPR(skpst, skres);
		}
		if (null != list && list.size() > 0) {
			for (String station : list) {
				result = getYSMap(skcon, station);
				if (null != result && result.size() > 0) {
					break;
				}
			}
		}

		

		if (null == result
				|| ((result != null && (!result.containsKey("visi") || (result
						.containsKey("visi") && "".equals(result.get("visi")))))
						|| (result != null && (!result.containsKey("humi") || (result
								.containsKey("humi") && "".equals(result.get("humi")))))
								||(result != null && (!result.containsKey("rain") || (result
										.containsKey("rain") && "".equals(result.get("rain")))))
										||
										(result != null && (!result.containsKey("press") || (result
												.containsKey("press") && "".equals(result.get("press")))))
					)) {
			/**
			 * 查区县数据
			 */
			if (null == result) {
				result = new HashMap<String, String>();
			}
			String oldYH=Constant.propFileManager
					.getString("oldYHStation");
			if (Utils.isEmpty(oldYH)) {
				oldYH = "k1207";
			}
			String defaultYHStation=Constant.propFileManager
					.getString("defaultYHStation");
			if (Utils.isEmpty(defaultYHStation)) {
				defaultYHStation = "58444";
			}
			if(oldYH.equals(rstation)){
				rstation=defaultYHStation;
			}
			result=getStationQXMap(skcon, rstation, result);
			if (null == result
					|| ((result != null && (!result.containsKey("visi") || (result
							.containsKey("visi") && "".equals(result.get("visi")))))
							|| (result != null && (!result.containsKey("humi") || (result
									.containsKey("humi") && "".equals(result.get("humi")))))
									||(result != null && (!result.containsKey("rain") || (result
											.containsKey("rain") && "".equals(result.get("rain")))))
											||
											(result != null && (!result.containsKey("press") || (result
													.containsKey("press") && "".equals(result.get("press")))))
						)) {
				
				String defaultStation=Constant.propFileManager.getString("defaultStation");
				if(Utils.isEmpty(defaultStation)){
					rstation="58457";//杭州代表站,对于没有的，如余杭区  默认取杭州的
				}else{
					rstation=defaultStation;//杭州代表站,对于没有的，如余杭区  默认取杭州的
				}
				result=getStationQXMap(skcon, rstation, result);//杭州
			}

		}
		if (null != skcon) {
			try {
				skcon.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

	private static Map<String, String> getStationQXMap(Connection skcon,
			String rstation, Map<String, String> result) {
		PreparedStatement skpst = null;
		ResultSet skres = null;
		String sksql = null;
		// 实况
		try {
			SimpleDateFormat tableF = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat observerF = new SimpleDateFormat("yyMMddHH");
			String database = "tabRealTimeData_" + tableF.format(new Date());
			String obserDateStr = observerF.format(new Date());
			sksql = "SELECT t.DryBulTemp,t.Precipitation,t.Visibility,t.RelHumidity,t.StationPress,"
					+ " t.WindDirect10,t.WindVelocity10,t.ObservTimes "
					+ " FROM "
					+ database
					+ " t WHERE t.StationNum=? ORDER BY  t.ObservTimes DESC ";
			skpst = skcon.prepareStatement(sksql.toString());
			skpst.setString(1, rstation);
//			skpst.setString(2, obserDateStr);
			skres = skpst.executeQuery();
			DecimalFormat df = new DecimalFormat("#.#");
			Double tempDouble = 0D;
			String tempString = "0";
			String temp = null;
			while (skres.next()) {
				if (null == result) {
					result = new HashMap<String, String>();
				}
				if(!result.containsKey("temp") || 
						(result.containsKey("temp")&&Utils.isEmpty(result.get("temp")))){
					temp = skres.getString("DryBulTemp");
					if (null != temp && !Utils.isEmpty(temp)) {
						tempDouble = Double.valueOf(temp) * 0.1;
						tempString = String.valueOf(tempDouble);
						int postion = DataFormateUtils.positionNumber(tempString);
						if (postion > 1) {
							temp = df.format(tempDouble);
						} else if (postion == 0) {
							temp = dfInt.format(tempDouble);
						} else {
							temp = tempString;
						}
					}
					if (null != temp && !Utils.isEmpty(temp)
							&& temp.indexOf("-99") != -1) {
						temp = "";// 对于无效数据 默认为0
					}
					result.put("temp", temp);
				}
				
				
				
				if(!result.containsKey("windd") || 
						(result.containsKey("windd")&&Utils.isEmpty(result.get("windd")))){
					result.put("windd", skres.getString("WindDirect10") == null ? "" : skres
							.getString("WindDirect10").trim());
				}
				if(!result.containsKey("visi")||
						(result.containsKey("visi")&&Utils.isEmpty(result.get("visi")))){
					result.put("visi", skres.getString("Visibility") == null ? ""
							: skres.getString("Visibility").trim());
				}
				if(!result.containsKey("humi")||
						(result.containsKey("humi")&&Utils.isEmpty(result.get("humi")))){
					result.put("humi", skres.getString("RelHumidity") == null ? ""
							: skres.getString("RelHumidity").trim());
				}
				if(!result.containsKey("windv")||
						(result.containsKey("windv")&&Utils.isEmpty(result.get("windv")))){
					result.put("windv", skres.getString("WindVelocity10") == null ? "" : skres
							.getString("WindVelocity10").trim());
				}
				if(!result.containsKey("press")||
						(result.containsKey("press")&&Utils.isEmpty(result.get("press")))){
					result.put("press", skres.getString("StationPress") == null ? "" : skres
							.getString("StationPress").trim());
				}
				if(!result.containsKey("rain")||
						(result.containsKey("rain")&&Utils.isEmpty(result.get("rain")))){
					result.put("rain", skres.getString("Precipitation") == null ? "" : skres
							.getString("Precipitation").trim());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.ColsedPR(skpst, skres);
		}

		return result;
	}

	private static Map<String, String> getYSMap(Connection skcon, String station) {
		Map<String, String> reuslt = null;
		PreparedStatement skpst = null;
		ResultSet skres = null;
		String sksql = null;
		// 实况
		try {
			SimpleDateFormat tableF = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat observerF = new SimpleDateFormat("yyMMddHH");
			String database = "tabRealTimeDataMws_" + tableF.format(new Date());
			String obserDateStr = observerF.format(new Date());
			sksql = "SELECT t.DryBulTemp,t.Precipitation,t.Visibility,t.RelHumidity,t.StationPress,"
					+ " t.WindDirect10,t.WindVelocity10,t.ObservTimes "
					+ " FROM "
					+ database
					+ " t WHERE t.StationNum=? ORDER BY  t.ObservTimes DESC ";
			skpst = skcon.prepareStatement(sksql.toString());
			skpst.setString(1, station);
//			skpst.setString(2, obserDateStr);
			skres = skpst.executeQuery();
			DecimalFormat df = new DecimalFormat("#.#");
			Double tempDouble = 0D;
			String tempString = "0";
			String temp = null;
			String rain = null;
			String visi = null;
			String humi = null;
			String windv = null;
			String windd = null;
			String press=null;
			while (skres.next()) {
				reuslt = new HashMap<String, String>();
				temp = skres.getString("DryBulTemp");
				if (null != temp && !Utils.isEmpty(temp)) {
					tempDouble = Double.valueOf(temp) * 0.1;
					tempString = String.valueOf(tempDouble);
					int postion = DataFormateUtils.positionNumber(tempString);
					if (postion > 1) {
						temp = df.format(tempDouble);
					} else if (postion == 0) {
						temp = dfInt.format(tempDouble);
					} else {
						temp = tempString;
					}
				}
				if (null != temp && !Utils.isEmpty(temp)
						&& temp.indexOf("-99") != -1) {
					temp = "";// 对于无效数据 默认为0
				}
				if(!Utils.isEmpty(temp)){
					reuslt.put("temp", temp);
				}
				windd=skres.getString("WindDirect10") == null ? "" : skres
						.getString("WindDirect10").trim();
				if(!Utils.isEmpty(temp)){
					reuslt.put("windd",
							windd);
				}
				visi=skres.getString("Visibility") == null ? ""
						: skres.getString("Visibility").trim();
				if(!Utils.isEmpty(visi)){
					reuslt.put("visi",
							visi);
				}
				humi=skres.getString("RelHumidity") == null ? ""
						: skres.getString("RelHumidity").trim();
				if(!Utils.isEmpty(humi)){
					reuslt.put("humi",
							humi);
				}
				windv=skres.getString("WindVelocity10") == null ? "" : skres
						.getString("WindVelocity10").trim();
				if(!Utils.isEmpty(windv)){
					reuslt.put("windv",
							windv);
				}
				press=skres.getString("StationPress") == null ? "" : skres
						.getString("StationPress").trim();
				if(!Utils.isEmpty(press)){
					reuslt.put("press",
							press);
				}
				rain=skres.getString("Precipitation") == null ? "" : skres
						.getString("Precipitation").trim();
				if(!Utils.isEmpty(rain)){
					reuslt.put("rain",
							rain);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.ColsedPR(skpst, skres);
		}

		return reuslt;
	}

	public static String getTemp(String stationId, Integer type) {
		Connection skcon = SqlConnectionUtils.getSK2Connection();
		PreparedStatement skpst = null;
		ResultSet skres = null;
		String sksql = null;
		String result = null;
		List<String> list = new ArrayList<String>();
		// 获取最近站点
		try {
			sksql = "DECLARE @oldLng varchar(50),@@OldLat varchar(50) "
					+ " SET @oldLng=(SELECT stationInfo.NNnn  FROM tabStationInfo stationInfo WHERE stationInfo.IIiii=?)"
					+ " SET @@OldLat=(SELECT stationInfo.EEEee  FROM tabStationInfo stationInfo WHERE stationInfo.IIiii=?)"
					+ " SELECT stationInfo.IIiii FROM tabStationInfo stationInfo WHERE stationInfo.IIiii <>?"
					+ " ORDER BY dbo.f_GetDistance(@oldLng,@@OldLat,stationInfo.NNnn,stationInfo.EEEee) ASC";
			skpst = skcon.prepareStatement(sksql.toString());
			skpst.setMaxRows(10);
			skpst.setString(1, stationId);
			skpst.setString(2, stationId);
			skpst.setString(3, stationId);
			skres = skpst.executeQuery();
			while (skres.next()) {
				String IIiii = skres.getString("IIiii");
				list.add(IIiii);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.ColsedPR(skpst, skres);
		}
		if (4 == type) {
			result = getAllYaoSuData(skcon, stationId, type);
		} else {
			if (null != list && list.size() > 0) {
				for (String station : list) {
					result = resultTemp(skcon, station, type);
					if (null != result && !Utils.isEmpty(result)) {
						break;
					}
				}
			}

		}

		if (null != skcon) {
			try {
				skcon.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String resultTemp(Connection skcon, String station,
			Integer type) {
		// Connection skcon = SqlConnectionUtils.getSK2Connection();
		PreparedStatement skpst = null;
		ResultSet skres = null;
		String sksql = null;
		String result = null;
		// 实况
		try {
			SimpleDateFormat tableF = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat observerF = new SimpleDateFormat("yyMMddHH");
			String database = "tabRealTimeDataMws_" + tableF.format(new Date());
			String obserDateStr = observerF.format(new Date());
			sksql = "SELECT t.DryBulTemp,t.Precipitation,t.Visibility,t.RelHumidity,"
					+ " t.WindDirect10,t.WindVelocity10,t.ObservTimes "
					+ " FROM "
					+ database
					+ " t WHERE t.StationNum=? AND t.ObservTimes=?";
			skpst = skcon.prepareStatement(sksql.toString());
			skpst.setString(1, station);
			skpst.setString(2, obserDateStr);
			skres = skpst.executeQuery();
			DecimalFormat df = new DecimalFormat("#.#");
			Double tempDouble = 0D;
			String tempString = "0";

			while (skres.next()) {
				String temp = null;

				if (type == 1) {
					temp = skres.getString("DryBulTemp");
					if (null != temp && !Utils.isEmpty(temp)) {
						tempDouble = Double.valueOf(temp) * 0.1;
						tempString = String.valueOf(tempDouble);
						int postion = DataFormateUtils
								.positionNumber(tempString);
						if (postion > 1) {
							temp = df.format(tempDouble);
						} else if (postion == 0) {
							temp = dfInt.format(tempDouble);
						} else {
							temp = tempString;
						}
					}
					if (null != temp && !Utils.isEmpty(temp)
							&& temp.indexOf("-99") != -1) {
						temp = "";// 对于无效数据 默认为0
					}
				} else if (type == 2) {
					temp = skres.getString("WindDirect10");
				} else if (type == 3) {
					temp = skres.getString("Precipitation");
				} else if (type == 4) {
					temp = skres.getString("Visibility");
				} else if (type == 5) {
					temp = skres.getString("RelHumidity");
				} else if (type == 6) {
					temp = skres.getString("WindVelocity10");
				}
				result = temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.ColsedPR(skpst, skres);
		}
		return result;
	}

	/**
	 * 
	 * @param rstation
	 *            市级站
	 * @param type
	 *            要素类型
	 * @return
	 */
	public static String getYaoSuData(String rstation, Integer type) {
		Connection skcon = SqlConnectionUtils.getSK2Connection();
		PreparedStatement skpst = null;
		ResultSet skres = null;
		String sksql = null;
		// 实况
		try {
			SimpleDateFormat tableF = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat observerF = new SimpleDateFormat("yyMMddHH");
			String database = "tabRealTimeData_" + tableF.format(new Date());
			String obserDateStr = observerF.format(new Date());
			sksql = "SELECT t.DryBulTemp,t.Precipitation,t.Visibility,t.RelHumidity,"
					+ " t.WindDirect10,t.WindVelocity10,t.ObservTimes "
					+ " FROM "
					+ database
					+ " t WHERE t.StationNum=? AND t.ObservTimes=?";
			skpst = skcon.prepareStatement(sksql.toString());
			skpst.setString(1, rstation);
			skpst.setString(2, obserDateStr);
			skres = skpst.executeQuery();
			DecimalFormat df = new DecimalFormat("#.#");
			Double tempDouble = 0D;
			String tempString = "0";

			while (skres.next()) {
				String temp = null;

				if (type == 1) {
					temp = skres.getString("DryBulTemp");
					if (null != temp && !Utils.isEmpty(temp)) {
						tempDouble = Double.valueOf(temp) * 0.1;
						tempString = String.valueOf(tempDouble);
						int postion = DataFormateUtils
								.positionNumber(tempString);
						if (postion > 1) {
							temp = df.format(tempDouble);
						} else if (postion == 0) {
							temp = dfInt.format(tempDouble);
						} else {
							temp = tempString;
						}
					}
					if (null != temp && !Utils.isEmpty(temp)
							&& temp.indexOf("-99") != -1) {
						temp = "";// 对于无效数据 默认为0
					}
				} else if (type == 2) {
					temp = skres.getString("WindDirect10");
				} else if (type == 3) {
					temp = skres.getString("Precipitation");
				} else if (type == 4) {
					temp = skres.getString("Visibility");
				} else if (type == 5) {
					temp = skres.getString("RelHumidity");
				} else if (type == 6) {
					temp = skres.getString("WindVelocity10");
				}
				rstation = temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.GetColsed(skcon, skpst, skres);
		}
		return rstation;
	}

	public static String getAllYaoSuData(Connection skcon, String rstation,
			Integer type) {
		PreparedStatement skpst = null;
		ResultSet skres = null;
		String sksql = null;
		String result = null;
		// 实况
		try {
			SimpleDateFormat tableF = new SimpleDateFormat("yyyyMM");
			SimpleDateFormat observerF = new SimpleDateFormat("yyMMddHH");
			String database = "tabRealTimeData_" + tableF.format(new Date());
			String obserDateStr = observerF.format(new Date());
			sksql = "SELECT t.DryBulTemp,t.Precipitation,t.Visibility,t.RelHumidity,"
					+ " t.WindDirect10,t.WindVelocity10,t.ObservTimes "
					+ " FROM "
					+ database
					+ " t WHERE t.StationNum=? AND t.ObservTimes=?";
			skpst = skcon.prepareStatement(sksql.toString());
			skpst.setString(1, rstation);
			skpst.setString(2, obserDateStr);
			skres = skpst.executeQuery();
			DecimalFormat df = new DecimalFormat("#.#");
			Double tempDouble = 0D;
			String tempString = "0";

			while (skres.next()) {
				String temp = null;

				if (type == 1) {
					temp = skres.getString("DryBulTemp");
					if (null != temp && !Utils.isEmpty(temp)) {
						tempDouble = Double.valueOf(temp) * 0.1;
						tempString = String.valueOf(tempDouble);
						int postion = DataFormateUtils
								.positionNumber(tempString);
						if (postion > 1) {
							temp = df.format(tempDouble);
						} else if (postion == 0) {
							temp = dfInt.format(tempDouble);
						} else {
							temp = tempString;
						}
					}
					if (null != temp && !Utils.isEmpty(temp)
							&& temp.indexOf("-99") != -1) {
						temp = "";// 对于无效数据 默认为0
					}
				} else if (type == 2) {
					temp = skres.getString("WindDirect10");
				} else if (type == 3) {
					temp = skres.getString("Precipitation");
				} else if (type == 4) {
					temp = skres.getString("Visibility");
				} else if (type == 5) {
					temp = skres.getString("RelHumidity");
				} else if (type == 6) {
					temp = skres.getString("WindVelocity10");
				}
				result = temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.ColsedPR(skpst, skres);
		}
		return result;
	}

}
