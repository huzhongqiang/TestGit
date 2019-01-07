package cn.grassinfo.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.grassinfo.common.Constant;
public class TyphoonUtil {

	
	
	/**
	 * 创建所有台风xml
	 * @param typhoonInfoList
	 * @param newBianhao
	 */
	public static String createTyphoonItemsXml(List<TyphoonInfo> typhoonInfoList,String newBianhao) {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("ArrayOfTyphoonItem");// 添加文档根
		for (TyphoonInfo typhoonInfo : typhoonInfoList) {
			Element typhoonInfoElement = root.addElement("TyphoonItem");
			typhoonInfoElement.addElement("IsSelected").setText(
					String.valueOf(newBianhao.indexOf(typhoonInfo
							.getBianhao()) != -1));
			typhoonInfoElement.addElement("NO").setText(
					typhoonInfo.getBianhao());
			typhoonInfoElement.addElement("NameEn").setText(
					typhoonInfo.getGuojibianhao());
			typhoonInfoElement.addElement("NameCn").setText(
					typhoonInfo.getZhongwenbianhao());
			typhoonInfoElement.addElement("IsHistory").setText(
					String.valueOf(newBianhao.indexOf(typhoonInfo
							.getBianhao()) == -1));
		}

		OutputFormat format = OutputFormat.createPrettyPrint();// 输出全部原始数据，在编译器中显示
		format.setEncoding("UTF-8");// 根据需要设置编码
		document.normalize();
		String outPath =Constant.ftpFileManager.getString("typhoonLocalPath"); 
		String outFile = outPath + "typhoonItems.xml";
		File file = new File(outFile);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		} else {
			file.delete();
		}
		FileOutputStream fos = null;
		XMLWriter writer = null;
		try{
			fos = new FileOutputStream(file);
			writer = new XMLWriter(fos, format);
			writer.write(document); // 输出到文件
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return outFile;
	}
	
	
	/**
	 * 创建台风详细信息
	 * @param typhoonInfoList 台风详细信息集合
	 */
	public static String createTyphoonInfoXml(List<TyphoonInfo> typhoonInfoList) throws Exception{
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("ArrayOfTyphoonInfo");// 添加文档根
		String bianhao = typhoonInfoList.get(0).getBianhao();
		for (TyphoonInfo typhoonInfo : typhoonInfoList) {
			Element typhoonInfoElement = root.addElement("TyphoonInfo");
			typhoonInfoElement.addElement("NO").setText(
					typhoonInfo.getBianhao());
			typhoonInfoElement.addElement("Publisher").setText(
					typhoonInfo.getFabuzhe());
			typhoonInfoElement.addElement("NameCn").setText(
					typhoonInfo.getZhongwenbianhao());
			typhoonInfoElement.addElement("Type").setText(
					getTypeByFengli(typhoonInfo.getXianzaifengli()));
			typhoonInfoElement.addElement("Time").setText(
					typhoonInfo.getXianzaishijian());
			typhoonInfoElement.addElement("ForecastHours").setText(
					typhoonInfo.getYubaoshixiao());
			typhoonInfoElement.addElement("TimeString").setText(
					typhoonInfo.getTimeString());
			typhoonInfoElement.addElement("Longitude").setText(
					typhoonInfo.getXianzaijindu());
			typhoonInfoElement.addElement("Latitude").setText(
					typhoonInfo.getXianzaiweidu());
			typhoonInfoElement.addElement("ForecastLongitude").setText(
					typhoonInfo.getYubaojindu());
			typhoonInfoElement.addElement("ForecastLatitude").setText(
					typhoonInfo.getYubaoweidu());
			typhoonInfoElement.addElement("ForecastWindPower").setText(
					getWindPowerByFengli(typhoonInfo.getYubaofengli()));
			typhoonInfoElement.addElement("ForecastAirPressure").setText(
					typhoonInfo.getYubaoqiya());
			typhoonInfoElement.addElement("AirPressure").setText(
					typhoonInfo.getXianzaiqiya());
			typhoonInfoElement.addElement("WindVelocity").setText(
					typhoonInfo.getXianzaifengli());
			typhoonInfoElement.addElement("WindPower").setText(
					getWindPowerByFengli(typhoonInfo.getXianzaifengli()));
			typhoonInfoElement.addElement("Level7Distance").setText(
					typhoonInfo.getFenglifor7());
			typhoonInfoElement.addElement("Level10Distance").setText(
					typhoonInfo.getFenglifor10());
			typhoonInfoElement.addElement("MoveVelocity").setText(
					typhoonInfo.getYidongsudu());
			typhoonInfoElement.addElement("Direction").setText(
					typhoonInfo.getYidongfangxiang());
			
		}

		OutputFormat format = OutputFormat.createPrettyPrint();// 输出全部原始数据，在编译器中显示
		format.setEncoding("UTF-8");// 根据需要设置编码
		document.normalize();
		String outPath =Constant.ftpFileManager.getString("typhoonLocalPath"); 
		String outFile = outPath + "typhoonInfo_"+bianhao+".xml";
		File file = new File(outFile);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		} else {
			file.delete();
		}
		FileOutputStream fos = null;
		XMLWriter writer = null;
		try{
			fos = new FileOutputStream(file);
			writer = new XMLWriter(fos, format);
			writer.write(document); // 输出到文件
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				writer.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return outFile;
	}
	
	
	
	/**
	 * 根据风力算台风类型
	 * @param fengliStr
	 * @return
	 */
	public static String getTypeByFengli(String fengliStr) throws Exception{
		String result = "";
		if(!checkNumber(fengliStr)){
			return result;
		}
		double fengli = Double.parseDouble(fengliStr);
		if (fengli < 18) //热带低压
        {
            result = "热带低压";
        }
        else if (fengli >= 18 && fengli < 25) //热带风暴
        {
            result = "热带风暴";
        }
        else if (fengli >= 25 && fengli < 33) //热带风暴
        {
            result = "强热带风暴";
        }
        else if (fengli >= 33 && fengli < 41) //台风
        {
            result = "台风";
        }
        else if (fengli >= 41 && fengli < 51) //强台风
        {
            result = "强台风";
        }
        else
            result = "超强台风";	

		return result;
	}
	
	/**
	 * 根据风速算风力等级
	 * @param fengliStr
	 * @return
	 */
	public static String getWindPowerByFengli(String fengliStr) throws Exception{
		String result = "9999";
		if(!checkNumber(fengliStr)){
			return result;
		}
		double fengli = Double.parseDouble(fengliStr);
		Integer windVelocity =9999;
		if (isWindVelocityValueHit(fengli, 0.0, 0.2))
            windVelocity = 0;
        else if (isWindVelocityValueHit(fengli, 0.3, 1.5))
            windVelocity = 1;
        else if (isWindVelocityValueHit(fengli, 1.6, 3.3))
            windVelocity = 2;
        else if (isWindVelocityValueHit(fengli, 3.4, 5.4))
            windVelocity = 3;
        else if (isWindVelocityValueHit(fengli, 5.5, 7.9))
            windVelocity = 4;
        else if (isWindVelocityValueHit(fengli, 8.0, 10.7))
            windVelocity = 5;
        else if (isWindVelocityValueHit(fengli, 10.8, 13.8))
            windVelocity = 6;
        else if (isWindVelocityValueHit(fengli, 13.9, 17.1))
            windVelocity = 7;
        else if (isWindVelocityValueHit(fengli, 17.2, 20.7))
            windVelocity = 8;
        else if (isWindVelocityValueHit(fengli, 20.8, 24.4))
            windVelocity = 9;
        else if (isWindVelocityValueHit(fengli, 24.5, 28.4))
            windVelocity = 10;
        else if (isWindVelocityValueHit(fengli, 28.5, 32.6))
            windVelocity = 11;
        else if (isWindVelocityValueHit(fengli, 32.7, 36.9))
            windVelocity = 12;
        else if (isWindVelocityValueHit(fengli, 37, 41.4))
            windVelocity = 13;
        else if (isWindVelocityValueHit(fengli, 41.5, 46.1))
            windVelocity = 14;
        else if (isWindVelocityValueHit(fengli, 46.2, 50.9))
            windVelocity = 15;
        else if (isWindVelocityValueHit(fengli, 51, 56))
            windVelocity = 16;
        else if (isWindVelocityValueHit(fengli, 56.1, 61.2))
            windVelocity = 17;
        else if (isWindVelocityValueHit(fengli, 61.3, 999.0))
            windVelocity = 18;
		result = windVelocity.toString();
		return result;
	}
	
	/**
	 * 
	 * @param fengli 风力
	 * @param s 开始值
	 * @param e 结束值
	 * @return 风力等级
	 */
	public static boolean isWindVelocityValueHit(double fengli,double s,double e){
		boolean result = false;
		if(fengli>=s&&fengli<e){
			result = true;
		}
		return result;
	}
	
	public static boolean isHistoryTyphoonExist(String bianhao){
		boolean result = false;
		String outPath =Constant.ftpFileManager.getString("typhoonLocalPath");
		String outFile = outPath + "typhoonInfo_"+bianhao+".xml";
		File file = new File(outFile);
		result = file.exists();
		return result;
	}
	
	public static boolean checkNumber(String value){  
        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";  
        return value.matches(regex);  
    }  
	
	public static void main(String[] args) {
		String fengliStr = "8.88";
		System.out.println(checkNumber(fengliStr));
		
//		List<TyphoonInfo> result = new ArrayList<TyphoonInfo>();
//		for(int i=0;i<10;i++){
//			TyphoonInfo e = new TyphoonInfo();
//			e.setBianhao("201510");
//			e.setGuojibianhao("BABJ");
//			e.setZhongwenbianhao("莲花");
//			e.setXianzaishijian("07031100");
//			e.setXianzaifengli("18");
//			e.setXianzaijindu("126.0");
//			e.setXianzaiweidu("15.3");
//			e.setXianzaiqiya("990");
//			e.setYubaoshixiao("00");
//			e.setFabuzhe("BABJ");
//			e.setYubaofengli("26");
//			e.setYubaojindu("123.9");
//			e.setYubaoweidu("16.9");
//			e.setYubaoqiya("980");
//			e.setYidongfangxiang("");
//			e.setYidongsudu("");
//			e.setFenglifor7("0");
//			e.setFenglifor10("0");
//			result.add(e);
//		}
//		String bianhao = "b0,b1,";
//		createTyphoonItemsXml(result,bianhao);
//		createTyphoonInfoXml(result);
	}
	
}
