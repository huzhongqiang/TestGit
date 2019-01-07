package cn.grassinfo.common.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.grassinfo.common.Constant;
import cn.grassinfo.common.util.Utils;

/**
 * 通过乡镇获取站点编号
 */
public class Info_TownUtils {
	public static String getStationNumByTown(String town) {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://172.41.129.5:1433;DatabaseName=hzqxdb";
		String userName = "zcy";
		String password = "zcy";
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}

		if (!Utils.isEmpty(Constant.propFileManager
				.getString("warningConnStr_infotwon"))) {
			connStr = Constant.propFileManager
					.getString("warningConnStr_infotwon");
		}
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("warningUserName_infotwon"))) {
			userName = Constant.propFileManager
					.getString("warningUserName_infotwon");
		}
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("warningPassword_infotwon"))) {
			password = Constant.propFileManager
					.getString("warningPassword_infotwon");
		}
		Connection conn = SqlConnectionUtils.getConnection(JDriver, connStr,
				userName, password);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String stationNum = null;
		try {
			String sql = "SELECT t.stationN FROM zcy.info_town t WHERE t.town=?";
			ps = conn.prepareStatement(sql.toString());
			if (!Utils.isEmpty(town)) {
				ps.setString(1, town);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				stationNum = (rs.getString("stationN"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}
		if (stationNum != null) {
			return stationNum;
		}
		return null;
	}

	public static Map<String,String> getRStationNumByTown(List<String> town) {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://172.41.129.5:1433;DatabaseName=hzqxdb";
		String userName = "zcy";
		String password = "zcy";
		Map<String,String> result = new HashMap<String, String>();
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}

		if (!Utils.isEmpty(Constant.propFileManager
				.getString("warningConnStr_infotwon"))) {
			connStr = Constant.propFileManager
					.getString("warningConnStr_infotwon");
		}
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("warningUserName_infotwon"))) {
			userName = Constant.propFileManager
					.getString("warningUserName_infotwon");
		}
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("warningPassword_infotwon"))) {
			password = Constant.propFileManager
					.getString("warningPassword_infotwon");
		}
		Connection conn = SqlConnectionUtils.getConnection(JDriver, connStr,
				userName, password);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT t.rstation,t.town FROM zcy.info_town t WHERE t.town in ('";
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < town.size(); i++) {
				sb.append(town.get(i));
				if (i != town.size() - 1) {
					sb.append("','");
				} else {
					sb.append("')");
				}
			}
			ps = conn.prepareStatement(sql.toString() + sb.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				result.put(rs.getString("town")==null?"":rs.getString("town").trim(), rs.getString("rstation")==null?"":rs.getString("rstation").trim());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SqlConnectionUtils.GetColsed(conn, ps, rs);
		}

		return result;
	}

}
