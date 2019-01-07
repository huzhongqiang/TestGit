package cn.grassinfo.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.grassinfo.common.Constant;
import cn.grassinfo.common.util.Utils;

public class FileUtils {
		

	private static final Logger log = Logger
			.getLogger(FileUtils.class);
	

	public static String getContextPath(){
		String filePath=FileUtils.class.getClassLoader().getResource("")  
        .getPath();
		if(filePath.indexOf("WEB-INF")!=-1){
			filePath=filePath.split("WEB-INF")[0];
		}
		filePath=filePath+"data"+"/";
		return filePath; 
	}
	//获取格点文件根路径存放地址
	public static String getRootMICAPSPath(){
		String rootPath=Constant.propFileManager.getString("RootMICAPSPath");
		if(Utils.isEmpty(rootPath)){
			rootPath=getContextPath();
		}
		return rootPath;
	}
	
	public static void outPutFile(String outPutPath, String jsonText) {
//		FileWriter fw;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
    			if (!file.getParentFile().exists()) {
    				file.getParentFile().mkdirs();
    			}
    		}else {
            	file.delete();
            }
			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(outPutPath),"UTF-8");
			
			out.write(jsonText);
			out.flush();
			out.close();
//			fw = new FileWriter(file);// 新建一个FileWriter
//			fw.write(jsonText);//
//			fw.close();
		} catch (Exception e) {
			log.debug("写入文件:" + e.getMessage());
		}

	}
	
	public static void outPutAllFile(Map<String,String> map) {
		OutputStreamWriter out=null;
		try {
			if(null!=map&&map.size()>0){
				Set<String> set=map.keySet();
				Iterator<String> it=set.iterator();
				String key=null;
				String value=null;
				while(it.hasNext()){
					key=it.next();
					value=map.get(key);
					File file = new File(key);
					if (!file.exists()) {
		    			if (!file.getParentFile().exists()) {
		    				file.getParentFile().mkdirs();
		    			}
		    		}else {
		            	file.delete();
		            }
					out = new OutputStreamWriter(
							new FileOutputStream(key),"UTF-8");
					
					out.write(value);
					out.flush();
					
				}
			}
		} catch (Exception e) {
			log.debug("写入文件:" + e.getMessage());
		}finally{
			if(null!=out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	public static void outPutAllFile2(Map<String,Object> map) {
		OutputStreamWriter out=null;
		String sourceFile=getContextPath()+"\\yb";
		String targetDir=getContextPath()+"\\fdata\\yb\\";
		try {
			if(null!=map&&map.size()>0){
				Set<String> set=map.keySet();
				Iterator<String> it=set.iterator();
				String key=null;
				Object value=null;
				while(it.hasNext()){
					key=it.next();
					value=map.get(key);
					String textjson=com.alibaba.fastjson.JSON.toJSONString(value,SerializerFeature.DisableCircularReferenceDetect);
					key=getContextPath() +"\\yb\\7d\\"+ key+ ".json";
					File file = new File(key);
					if (!file.exists()) {
		    			if (!file.getParentFile().exists()) {
		    				file.getParentFile().mkdirs();
		    			}
		    		}else {
		            	file.delete();
		            }
					out = new OutputStreamWriter(
							new FileOutputStream(key),"UTF-8");
					
					out.write(textjson);
					out.flush();
					
				}
			}
			FileCopy.copyFiles(sourceFile, targetDir);
		} catch (Exception e) {
			log.debug("写入文件:" + e.getMessage());
		}finally{
			if(null!=out){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	
	/**
	 * 获取格点文件
	 */
	
	public static String getMICAPSFile(){
		String filePath="";
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.add(Calendar.HOUR_OF_DAY,-3);
		SimpleDateFormat FHF = new SimpleDateFormat("yyMMddHH");
		String nowData = FHF.format(now.getTime());
		String remoteFile = Constant.propFileManager.getString("shareDic")
				+ Constant.propFileManager.getString("kysPath");
		try {
			if(null==remoteFile || Utils.isEmpty(remoteFile)){
				remoteFile="Z:\\ZJWARRS\\DATA_RECENT\\MICAPS_d02\\CLDT";
			}
			File smbFile = new File(
					remoteFile);
			if (smbFile.isDirectory()) {
				List<File> childFile = new ArrayList<File>();
				File[] listFile = smbFile.listFiles();
				String tempName = "";
				String arr[] = new String[2];
				if (null != listFile && listFile.length > 0) {
					for (int i = 0; i < listFile.length; i++) {
						tempName = listFile[i].getName();
						if (!Utils.isEmpty(tempName)
								&& tempName.indexOf(".") != -1) {
							arr = tempName.trim().split("\\.");
							if (null != arr && arr.length == 2&&"003".equals(arr[1])) {
								childFile.add(listFile[i]);
							}

						}

					}
				}

				if (null != childFile && childFile.size() > 0) {
					Collections.sort(childFile, new Comparator<File>() {
						@Override
						public int compare(File o1, File o2) {
							return -o1.getName().compareTo(o2.getName());

						}

					});
					
//					System.out.println(childFile.get(0).getName());
					try {
						FileInputStream input = new FileInputStream(childFile.get(0));
						filePath=FileUtils.getRootMICAPSPath()+childFile.get(0).getName();
						File parentFile=new File(filePath);
						if (!parentFile.exists()) {
			    			if (!parentFile.getParentFile().exists()) {
			    				parentFile.getParentFile().mkdirs();
			    			}
			    		}else {
			    			parentFile.delete();
			            }
						FileOutputStream output = new FileOutputStream(filePath);
						filePath=childFile.get(0).getName();
						byte[] buff = new byte[8192];
						int byteRead = 0;

						while ((byteRead = input.read(buff, 0, 8192)) > 0) {
							output.write(buff, 0, byteRead);
						}
						input.close();
						output.close();
					} catch (FileNotFoundException e) {
						log.error("getMICAPSFile",e);
					} catch (IOException e) {
						log.error("getMICAPSFile",e);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;

	}
	
	
	/**
	 * 获取格点文件rain
	 */
	
	public static String getMICAPSFile2(){
		String filePath="";
		Calendar now = Calendar.getInstance();
		Calendar nowNext = Calendar.getInstance();
		now.setTime(new Date());
		nowNext.setTime(new Date());
		SimpleDateFormat FHF1 = new SimpleDateFormat("yyyyMMddHH");
		SimpleDateFormat Minte = new SimpleDateFormat("mm");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		now.add(Calendar.MINUTE, -10);
		nowNext.add(Calendar.MINUTE, -15);
		int year=now.get(Calendar.YEAR); 
		String mintue=Minte.format(now.getTime()).charAt(0)+"0";
		String mintueNext=Minte.format(nowNext.getTime()).charAt(0)+"0";
		String nowData =FHF1.format(now.getTime())+mintue+"_180_5km.dat";
		String nowNextData =FHF1.format(nowNext.getTime())+mintueNext+"_180_5km.dat";
		String remoteFile = Constant.propFileManager.getString("shareRainDic")
				+ Constant.propFileManager.getString("railPath")+"\\"+year+"\\"+month.format(now.getTime())+"\\"+day.format(now.getTime());
		
		try {
			if(null==remoteFile ||Utils.isEmpty(remoteFile)){
				remoteFile="Y:\\Nowcasting\\rain_5\\"+year+"\\"+month.format(now.getTime())+"\\"+day.format(now.getTime());
			}
			File smbFile = new File(
					remoteFile);
			if (smbFile.isDirectory()) {
				List<File> childFile = new ArrayList<File>();
				File[] listFile = smbFile.listFiles();
				String tempName = "";
				
				if (null != listFile && listFile.length > 0) {
					for (int i = 0; i < listFile.length; i++) {
						tempName = listFile[i].getName();
						if (!Utils.isEmpty(tempName)
								&& tempName.indexOf("_180_5km.dat") != -1) {
								childFile.add(listFile[i]);
						}

					}
				}

				if (null != childFile && childFile.size() > 0) {
					Collections.sort(childFile, new Comparator<File>() {
						@Override
						public int compare(File o1, File o2) {
							return -o1.getName().compareTo(o2.getName());

						}

					});
					filePath=childFile.get(0).getPath();
//					System.out.println(filePath);
					try {
						FileInputStream input = new FileInputStream(childFile.get(0));
						filePath=FileUtils.getRootMICAPSPath()+childFile.get(0).getName();
						File parentFile=new File(filePath);
						if (!parentFile.exists()) {
			    			if (!parentFile.getParentFile().exists()) {
			    				parentFile.getParentFile().mkdirs();
			    			}
			    		}else {
			    			parentFile.delete();
			            }
						FileOutputStream output = new FileOutputStream(filePath);
						filePath=childFile.get(0).getName();
						byte[] buff = new byte[8192];
						int byteRead = 0;

						while ((byteRead = input.read(buff, 0, 8192)) > 0) {
							output.write(buff, 0, byteRead);
						}
						input.close();
						output.close();
					} catch (FileNotFoundException e) {
						log.error("getMICAPSFile",e);
					} catch (IOException e) {
						log.error("getMICAPSFile",e);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filePath;

	}
	
	
	
	public static void main(String[] args) {
//		String aa="forecast/7d_ok.json";
//		System.out.println(FileUtils.getContextPath()+aa);
//		outPutFile(FileUtils.getContextPath()+aa, "asdfgg");
		
		//服務器地址 格式為 smb://电脑用户名:电脑密码@电脑IP地址/IP共享的文件夹 
//		 String remoteUrl = "smb://huangyuewang:iso@127.0.0.1/iso";  
//		 String localFile = "F:/宏茂达/资料/网友/fa8b64f05061c673730eec51.jpg";  //本地要上传的文件
//		 String remoteUrl = "smb://hp:hphp@10.135.145.94/data/HZ_WAP/";  
//		 String localFile = "D:\\workspace\\projectHztq\\intranet\\interfaceServer\\nbtq-web\\target\\classes\\data\\15040808.003";  //本地要上传的文件 
//		 File file = new File(localFile); 
//		 Smb smb = Smb.getInstance(remoteUrl); 
//		 smb.remoteFileUpload(file);// 上传文件 
		
		String aa="01.png";
		String [] bb=aa.split(".png");
		if(Integer.valueOf(bb[0])<10){
			System.out.println(Integer.valueOf(bb[0]));
		}
		String filePath=FileUtils.class.getClassLoader().getResource("")  
		        .getPath();
				if(filePath.indexOf("WEB-INF")!=-1){
					filePath=filePath.split("WEB-INF")[0];
				}
				filePath=filePath+"data"+"/";
		System.out.println(filePath);
	}
}
