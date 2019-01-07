package cn.grassinfo.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.grassinfo.common.Constant;
import cn.grassinfo.common.util.Utils;

public class SqlConnectionUtils {
	public static Connection getConnection(String JDriver, String connStr,
			String userName, String password) {
		Connection conn = null;
		try {
			Class.forName(JDriver);
			conn = DriverManager.getConnection(connStr, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * 获取行业气象 - 农业气象数据库连接
	 * @return
	 */
	public static Connection getNyqxAgricultureDataConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://10.136.2.131:1433;DatabaseName=NYXQX";
		String userName = "developer";
		String password = "123456";
		if (!Utils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentAgricultureDataStr"))) {
			connStr = Constant.propFileManager.getString("nyqxStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentAgricultureDataUserName"))) {
			userName = Constant.propFileManager.getString("nyqxUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentAgricultureDataPassword"))) {
			password = Constant.propFileManager.getString("nyqxPassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		return con;
	}
	
	/**
	 * 获取嘉善首页预报 数据库连接
	 * @return
	 */
	public static Connection getForecastDataConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://172.21.129.199:1433;DatabaseName=jxh";
		String userName = "jxh";
		String password = "jxh123";
		if (!Utils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentDataStr"))) {
			connStr = Constant.propFileManager.getString("forecastDataStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentDataUserName"))) {
			userName = Constant.propFileManager.getString("forecastDataUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentDataPassword"))) {
			password = Constant.propFileManager.getString("forecastDataPassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		return con;
	}
	
	public static Connection getAQIDataConnection(){
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://218.108.110.7;DatabaseName=JS_AQI";
		String userName = "sa";
		String password = "ns@BDCOM";
		if (!Utils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentDataStr"))) {
			connStr = Constant.propFileManager.getString("forecastDataStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentDataUserName"))) {
			userName = Constant.propFileManager.getString("forecastDataUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentDataPassword"))) {
			password = Constant.propFileManager.getString("forecastDataPassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		return con;
	}
	
	/**
	 * 获取嘉善农业站，范东村数据库连接，K5617
	 * @return
	 */
	public static Connection getFandongDataConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://10.136.2.131:1433;DatabaseName=AWSReal";
		String userName = "developer";
		String password = "123456";
		if (!Utils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("fandongDataStr"))) {
			connStr = Constant.propFileManager.getString("fandongDataStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("fandongDataUserName"))) {
			userName = Constant.propFileManager.getString("fandongDataUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("fandongDataPassword"))) {
			password = Constant.propFileManager.getString("fandongDataPassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		return con;
	}
	
	/**
	 * 获取嘉善首页实况 农气站数据库连接
	 * @return
	 */
	public static Connection getCurrentAgricultureDataConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://10.136.2.131:1433;DatabaseName=AWS352";
		String userName = "developer";
		String password = "123456";
		if (!Utils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentAgricultureDataStr"))) {
			connStr = Constant.propFileManager.getString("currentAgricultureDataStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentAgricultureDataUserName"))) {
			userName = Constant.propFileManager.getString("currentAgricultureDataUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentAgricultureDataPassword"))) {
			password = Constant.propFileManager.getString("currentAgricultureDataPassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		return con;
	}
	
	/**
	 * 获取320国道交通气象数据库连接
	 * @return
	 */
	public static Connection getCurrentTafficDataConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://10.136.2.131:1433;DatabaseName=changwang";
		String userName = "developer";
		String password = "123456";
		if (!Utils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentTafficDataStr"))) {
			connStr = Constant.propFileManager.getString("currentTafficDataStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentTafficDataUserName"))) {
			userName = Constant.propFileManager.getString("currentTafficDataUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentTafficDataPassword"))) {
			password = Constant.propFileManager.getString("currentTafficDataPassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		return con;
	}
	
	/**
	 * 获取嘉善首页实况 中尺度站数据库连接
	 * @return
	 */
	public static Connection getCurrentDataConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://10.136.2.131:1433;DatabaseName=自动站";
		String userName = "developer";
		String password = "123456";
		if (!Utils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentDataStr"))) {
			connStr = Constant.propFileManager.getString("currentDataStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentDataUserName"))) {
			userName = Constant.propFileManager.getString("currentDataUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("currentDataPassword"))) {
			password = Constant.propFileManager.getString("currentDataPassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		return con;
	}
	
	/**
	 * 获取嘉善短时、短期天气预报 数据库连接
	 * @return
	 */
	public static Connection getJxqxtShortForecastConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://172.21.138.11:1433;DatabaseName=业务一体化";
		String userName = "jxqxt";
		String password = "qxt@jx";
		if (!Utils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("jxqxtShortForecastStr"))) {
			connStr = Constant.propFileManager.getString("jxqxtShortForecastStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("jxqxtShortForecastUserName"))) {
			userName = Constant.propFileManager.getString("jxqxtShortForecastUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("jxqxtShortForecastPassword"))) {
			password = Constant.propFileManager.getString("jxqxtShortForecastPassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		return con;
	}
	
	/**
	 * 获取下垫面温度 数据库连接
	 * @return
	 */
	public static Connection getHzsxdmConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://10.135.144.201:1433;DatabaseName=hzsxdm";
		String userName = "hzqx";
		String password = "58457";
		if (!Utils.isEmpty(Constant.propFileManager.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("hzsxdmConnStr"))) {
			connStr = Constant.propFileManager.getString("hzsxdmConnStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("hzsxdmUserName"))) {
			userName = Constant.propFileManager.getString("hzsxdmUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("hzsxdmPassword"))) {
			password = Constant.propFileManager.getString("hzsxdmPassword");
		}
		Connection conn = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		return conn;
	}

	/**
	 * 获取预警 数据库连接
	 * @return
	 */
	
	public static Connection getEarlyWarningConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://172.41.129.5:1433;DatabaseName=WsignalOfMcalamity";
		String userName = "yb";
		String password = "1212";
		
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr, userName, password);
		return con;
	}
	
	/**
	 * 获取生活指数连接
	 * @return
	 */
	public static Connection getWeatherIndexConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://10.135.144.203:1433;DatabaseName=PubcastBiz";
		String userName = "hzqx";
		String password = "58457";
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils
				.isEmpty(Constant.propFileManager.getString("warningConnStr1"))) {
			connStr = Constant.propFileManager.getString("warningConnStr1");
		}
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("warningUserName1"))) {
			userName = Constant.propFileManager.getString("warningUserName1");
		}
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("warningPassword1"))) {
			password = Constant.propFileManager.getString("warningPassword1");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr,
				userName, password);
		return con;
	}
	/**
	 * 获取数据精细化 预报数据连接
	 * @return
	 */
	public static Connection getForeCastConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://172.41.129.5:1433;DatabaseName=hzqxdb";
		String userName = "zcy";
		String password = "zcy";
		
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils
				.isEmpty(Constant.propFileManager.getString("zcyHzqxConnStr"))) {
			connStr = Constant.propFileManager.getString("zcyHzqxConnStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("zcyHzqxUserName"))) {
			userName = Constant.propFileManager.getString("zcyHzqxUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("zcyHzqxPassword"))) {
			password = Constant.propFileManager.getString("zcyHzqxPassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr,
				userName, password);
		return con;
	}

	/**
	 * 获取实况的数据连接
	 * 
	 * @return
	 */
	public static Connection getSKConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		// 实况
		String skStr = "jdbc:sqlserver://10.135.144.201:1433;DatabaseName=hzszdzdb";
		String skuserName = "yb";
		String skpassword = "1212";
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("warningConnStr2"))) {
			skStr = Constant.propFileManager.getString("warningConnStr2");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("warningUserName2"))) {
			skuserName = Constant.propFileManager.getString("warningUserName2");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("warningPassword2"))) {
			skpassword = Constant.propFileManager.getString("warningPassword2");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, skStr,
				skuserName, skpassword);
		return con;
	}
	
	/**
	 * 获取实况的数据连接
	 * 
	 * @return
	 */
	public static Connection getSK2Connection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		// 实况
		String skStr = "jdbc:sqlserver://10.135.144.201:1433;DatabaseName=hzszdzdb";
		String skuserName = "hzqx";
		String skpassword = "58457";
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("hzqxCon"))) {
			skStr = Constant.propFileManager.getString("hzqxCon");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("hzqxUserName"))) {
			skuserName = Constant.propFileManager.getString("hzqxUserName");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("hzqxpassword"))) {
			skpassword = Constant.propFileManager.getString("hzqxpassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, skStr,
				skuserName, skpassword);
		return con;
	}

	public static void GetColsed(Connection conn, PreparedStatement ps,
			ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void ColsedPR(PreparedStatement ps,
			ResultSet rs) {
		try {
			if (ps != null) {
				ps.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getAll7Connection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String connStr = "jdbc:sqlserver://172.21.129.91:1433;DatabaseName=zjsqxtdb";
		String userName = "qxt";
		String password = "qxt1";
		
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("sqlserverJDriver"))) {
			JDriver = Constant.propFileManager.getString("sqlserverJDriver");
		}
		if (!Utils
				.isEmpty(Constant.propFileManager.getString("chinaConnStr"))) {
			connStr = Constant.propFileManager.getString("chinaConnStr");
		}
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("chinaForeUser"))) {
			userName = Constant.propFileManager.getString("chinaForeUser");
		}
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("chinaForePassword"))) {
			password = Constant.propFileManager.getString("chinaForePassword");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, connStr,
				userName, password);
		return con;
	}
	
	/**
	 * 获取台风的数据库连接
	 * @return
	 */
	public static Connection getTyphoonConnection() {
		String JDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		// 实况
		String skStr = "jdbc:sqlserver://172.21.129.125;DatabaseName=zjsqxtdb";
		String skuserName = "dqk";
		String skpassword = "dqk";
		if (!Utils.isEmpty(Constant.propFileManager
				.getString("typhoon.driver"))) {
			JDriver = Constant.propFileManager.getString("typhoon.driver");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("typhoon.url"))) {
			skStr = Constant.propFileManager.getString("typhoon.url");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("typhoon.username"))) {
			skuserName = Constant.propFileManager.getString("typhoon.username");
		}
		if (!Utils.isEmpty(Constant.propFileManager.getString("typhoon.password"))) {
			skpassword = Constant.propFileManager.getString("typhoon.password");
		}
		Connection con = SqlConnectionUtils.getConnection(JDriver, skStr,
				skuserName, skpassword);
		return con;
	}
}
