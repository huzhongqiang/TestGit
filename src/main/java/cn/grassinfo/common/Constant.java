package cn.grassinfo.common;

public class Constant {

	public static PropFileManager propFileManager = new PropFileManagerImpl();
	public static PropFileManager nodePoint = new PropFileManagerImpl();
	public static PropFileManager action = new PropFileManagerImpl();
	public static PropFileManager sqlConnection = new PropFileManagerImpl();
	public static PropFileManager ftpFileManager = new PropFileManagerImpl();
	static{
		propFileManager.load("sysparam.properties");
		nodePoint.load("nodepoint.properties");
		action.load("action.properties");
		sqlConnection.load("spring/application.properties");
		ftpFileManager.load("ftpconfig.properties");
	}
	public static void main(String[] args) {
		System.out.println(Constant.sqlConnection.getString("jdbc.username"));
		System.out.println(Constant.sqlConnection.getString("jdbc.name"));
	}
}
