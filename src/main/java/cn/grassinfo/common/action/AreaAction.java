package cn.grassinfo.common.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.grassinfo.common.Constant;
import cn.grassinfo.common.util.FileUtils;
import cn.grassinfo.common.utils.SqlConnectionUtils;
import cn.grassinfo.wap.entity.Area;
import cn.grassinfo.wap.entity.Qxkp;

public class AreaAction{
	private static final Logger log = LoggerFactory
			.getLogger(AreaAction.class);
	
	
	public static  void arealist(){
		String connStr=null;
		String userName=null;
		String password=null;
		String JDriver=null;
		String town="留下街道                ";
		Connection foreCon=SqlConnectionUtils.getForeCastConnection();
		List<Qxkp> qxkps=new ArrayList<Qxkp>();
		Connection conn=SqlConnectionUtils.getForeCastConnection();
		if(conn==null){
			return;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		    String sql="SELECT i.region,i.town from zcy.info_town i where i.town is not null  ORDER BY i.region";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
		    TreeMap<String, String> map = new TreeMap<String, String>();
		    while(rs.next()){
		    	String country = rs.getString("region").trim();
		    	String tow = rs.getString("town").trim();
		    	if(map.containsKey(country)){
		    		map.put(country, map.get(country)+","+tow);
		    	}else{
		    		map.put(country,tow);
		    	}
		    }
		    List<Area> areas = new ArrayList<Area>();
		    for (String key : map.keySet()) {
		    	   Area  area = new Area();
		    	   area.setCountry(key);
		    	   area.setTowns(map.get(key));
		    	   areas.add(area);
		    }
		    String jsonText = com.alibaba.fastjson.JSON.toJSONString(areas);
			String outPutPath=FileUtils.getContextPath()+"arealist.json";
			FileUtils.outPutFile(outPutPath, jsonText);
		} catch (Exception e) {
			// TODO: handle exception
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		
	  }
	
	public static List<String> getAllAreas(){
		List<String> areas=new ArrayList<String>();
		Connection conn=SqlConnectionUtils.getForeCastConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		    String sql="SELECT i.region,i.town from zcy.info_town i where i.town is not null and i.region='萧山' ORDER BY i.region";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
		    while(rs.next()){
		    	String tow = rs.getString("town").trim();
		    	areas.add(tow);
		    }
		} catch (Exception e) {
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		return areas;
	}
	
	public static List<String> getAllRAreas(){
		List<String> areas=new ArrayList<String>();
		Connection conn=SqlConnectionUtils.getForeCastConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		    String sql=" SELECT DISTINCT rstation from zcy.info_town where rstation is not null ";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
		    while(rs.next()){
		    	String tow = rs.getString("rstation").trim();
		    	areas.add(tow);
		    }
		} catch (Exception e) {
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		return areas;
	}
	
	public static Map<String,String> getAllRRAreas(){
		Map<String,String> areas=new HashMap<String, String>();
		Map<String,String> areas2=new HashMap<String, String>();
		String oldYH = Constant.propFileManager
				.getString("oldYHStation");
		if (StringUtils.isEmpty(oldYH)) {
			oldYH = "k1207";
		}
		String defaultYHStation = Constant.propFileManager
				.getString("defaultYHStation");
		if (StringUtils.isEmpty(defaultYHStation)) {
			defaultYHStation = "58444";
		}
		Connection conn=SqlConnectionUtils.getForeCastConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		    String sql=" SELECT DISTINCT rstation,region from zcy.info_town where rstation is not null ";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
		    while(rs.next()){
		    	String tow = rs.getString("rstation").trim();
		    	if(oldYH.equals(tow)){
		    		tow=defaultYHStation;
		    	}
		    	areas.put(tow, rs.getString("region").trim());
		    	areas2.put(rs.getString("region").trim(), tow);
		    }
		} catch (Exception e) {
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.ColsedPR(ps, rs);
		}
		
		
		
		StringBuffer sb=new StringBuffer();
		if(null!=areas&&areas.size()>0){
			int count=0;
			for(Map.Entry<String,String> map:areas.entrySet()){
				sb.append("select CCCC,FORECAST1 from FP_local_Short where CCCC like '"+map.getValue()+"%'  and RTIME = (select max(RTIME) from FP_local_Short where CCCC like '"+map.getValue()+"%')");
				if(count!=areas.size()-1){
					sb.append(" union all ");
				}
				count++;
			}
		}
		
		try {
			ps = conn.prepareStatement(sb.toString());
			rs = ps.executeQuery();
			String result = null;
		    while(rs.next()){
		    	String info = rs.getString("FORECAST1");
		    	String tow = rs.getString("CCCC").trim();
				if (!"杭州".equals(tow)) {
					result = info.replaceAll("气象局", "气象台");
				}
				else {
					result = info.substring(0, info.indexOf("%") + 1).replaceAll("气象局", "气象台") + ".";
				}

		    	
		    	areas.put(areas2.get(tow),result);
		    }
		} catch (Exception e) {
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		return areas;
	}
	
	public static List<String> getAllStations(){
		List<String> areas=new ArrayList<String>();
		Connection conn=SqlConnectionUtils.getForeCastConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		    String sql="SELECT DISTINCT i.stationN from zcy.info_town i where i.stationN is not null  ";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
		    while(rs.next()){
		    	String stationNum = rs.getString("stationN")==null?"":rs.getString("stationN").trim();
		    	areas.add(stationNum);
		    }
		} catch (Exception e) {
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		return areas;
	}
	
	public static Map<String,String> getAllStationAreas(){
		Map<String,String> areas=new HashMap<String, String>();
		Connection conn=SqlConnectionUtils.getForeCastConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		    String sql="SELECT i.stationN,i.town from zcy.info_town i where i.town is not null";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			String town=null;
		    while(rs.next()){
		    	town=rs.getString("town").trim();
		    	areas.put(town, rs.getString("stationN").trim());
		    }
		} catch (Exception e) {
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		return areas;
	}
	/**
	 * 获取县市代表站 余杭替换为58444
	 * @return
	 */
	public static List<String> getCityStation(){
		List<String> result=new ArrayList<String>();
		
		Connection conn=SqlConnectionUtils.getForeCastConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String oldYH=Constant.propFileManager
				.getString("oldYHStation");
		if (StringUtils.isEmpty(oldYH)) {
			oldYH = "k1207";
		}
		String defaultYHStation=Constant.propFileManager
				.getString("defaultYHStation");
		if (StringUtils.isEmpty(defaultYHStation)) {
			defaultYHStation = "58444";
		}
		try {
		    String sql="SELECT distinct i.rstation from zcy.info_town i where i.rstation IS NOT NULL";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			String rstation=null;
		    while(rs.next()){
		    	rstation=rs.getString("rstation").trim();
		    	if(oldYH.equals(rstation)){
		    		rstation=defaultYHStation;
		    	}
		    	result.add(rstation);
		    }
		} catch (Exception e) {
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		return result;
	}
	
	/**
	 * 通过乡镇  查找 实况数据库中的  对应乡镇的经纬度
	 * @return
	 */
	public static Map<String,Map<String,String>> getAllSKStationAreas(String stations){
		Map<String,Map<String,String>> areas=new HashMap<String, Map<String,String>>();
		Connection conn=SqlConnectionUtils.getSKConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		    String sql="SELECT tab.* FROM  tabStationInfo tab  WHERE  tab.IIiii IN( "+stations+" )";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			String town=null;
			
		    while(rs.next()){
		    	Map<String,String> map=new HashMap<String, String>();
		    	map.put("stationN", rs.getString("IIiii").trim());
		    	map.put("NNnn", rs.getString("NNnn").trim());
		    	map.put("EEEee", rs.getString("EEEee").trim());
		    	town=rs.getString("town").trim();
		    	map.put("town",town);
		    	map.put("Height", rs.getString("Height").trim());
		    	areas.put(rs.getString("IIiii").trim(),map);
		    }
		} catch (Exception e) {
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		return areas;
	}
	
	public static Map<String,String> getAllRStationAreas(List<String> towns){
		Map<String,String> areas=new HashMap<String, String>();
		Connection conn=SqlConnectionUtils.getForeCastConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
		    String sql="SELECT i.rstation,i.town from zcy.info_town i where i.town is not null";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			String town=null;
		    while(rs.next()){
		    	town=rs.getString("town").trim();
		    	if(towns.contains(town)){
		    		areas.put(town, rs.getString("rstation").trim());
		    	}
		    }
		} catch (Exception e) {
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		return areas;
	}
	/**
	 * 获取经纬度数据
	 * @param stations
	 * @return
	 */
	public static Map<String,String> getAllSKStationLngAreas(String stations){
		Connection conn=SqlConnectionUtils.getSKConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,String> result=new LinkedHashMap<String, String>();
		try {
		    String sql="SELECT tab.* FROM  tabStationInfo tab  WHERE  tab.IIiii IN( "+stations+" )";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			String lng=null;
			String eng=null;
			String tempStr=null;
		    while(rs.next()){
		    	lng=rs.getString("NNnn")==null?"":rs.getString("NNnn").trim();
		    	eng=rs.getString("EEEee")==null?"":rs.getString("EEEee").trim();
		    	if(!StringUtils.isEmpty(lng)&&!StringUtils.isEmpty(eng)){
		    		tempStr=Double.valueOf(eng)/100+","+Double.valueOf(lng)/100;
			    	result.put(rs.getString("IIiii")==null?"":rs.getString("IIiii").trim(), tempStr);
		    	}
		    	
//		    	if(!Utils.isEmpty(result)){
//		    		result+=" ";
//		    	}
//		    	if(!Utils.isEmpty(lng)&&!Utils.isEmpty(eng)){
//		    		result+=Double.valueOf(eng)/100+","+Double.valueOf(lng)/100;
//		    	}
		    }
		} catch (Exception e) {
			log.debug("数据经纬度  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		return result;
	}
	
	
	public static Map<String,String> getAllStationAreasReplacek1207(){
		Map<String,String> areas=new HashMap<String, String>();
		Connection conn=SqlConnectionUtils.getForeCastConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String oldYH=Constant.propFileManager
				.getString("oldYHStation");
		if (StringUtils.isEmpty(oldYH)) {
			oldYH = "k1207";
		}
		
		String defaultYHStation=Constant.propFileManager
				.getString("defaultYHStation");
		
		if (StringUtils.isEmpty(defaultYHStation)) {
			defaultYHStation = "58444";
		}
		String defaultStation = Constant.propFileManager
				.getString("defaultStation");
		if (StringUtils.isEmpty(defaultStation)) {
			defaultStation = "58457";
		}
		
		try {
		    String sql="SELECT i.stationN,i.town from zcy.info_town i where i.town is not null";
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			String town=null;
			String station=null;
		    while(rs.next()){
		    	town=rs.getString("town").trim();
		    	station=rs.getString("stationN").trim();
		    	if(station.equals(oldYH)){
		    		station=defaultYHStation;
		    	}
		    	areas.put(station,town);
		    }
		} catch (Exception e) {
			log.debug("乡镇精细化  degug"+e.getMessage());
		}
		finally{
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		
		return areas;
	}
	
	
	public static void main(String[] args) throws IOException {
		getAllRRAreas();
		
		
	}

}
