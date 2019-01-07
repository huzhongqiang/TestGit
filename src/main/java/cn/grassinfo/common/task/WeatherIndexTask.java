package cn.grassinfo.common.task;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.grassinfo.common.Constant;
import cn.grassinfo.common.util.FileUtils;
import cn.grassinfo.common.utils.ContinueFTP;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import javax.servlet.ServletContext;

/**
 * @author huhengbo
 * @Type WeatherIndexTask
 * @Desc
 * @date 2017/3/23
 */
public class WeatherIndexTask extends TimerTask{
	private static final String preWeather = Constant.propFileManager.getString("preWeather");
    private static final Logger LOG = LoggerFactory.getLogger(WeatherIndexTask.class);
    
    
	 private static boolean isRunning = false;    
	    
	    private ServletContext context = null;    
	    
	    public WeatherIndexTask() {  
	        super();  
	    }  
	      
	    public WeatherIndexTask(ServletContext context) {    
	        this.context = context;    
	    }    
	    @Override  
	    public void run() {  
	          
	        if (!isRunning) {  
				LOG.info("------------------------------定时检测生活指数任务开始------------------------------------");
				Map<String,Object> all=new HashMap<String, Object>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			try {
				Boolean con = ContinueFTP.connect("10.135.152.139", 21, "xswx", "58459");
				if (con) {
					FTPFile[] files = ContinueFTP.getFileNameList("");
					Long maxDate = 0l;
					for (FTPFile ftpFile : files) {
						if (ftpFile.isFile()) {
							String name = ftpFile.getName();
							String date = name.substring(0, name.indexOf("."));
							try {
								Date dd = sdf.parse(date);
								if (dd.getTime()>maxDate) {
									maxDate = dd.getTime();
								}
							} catch (Exception e) {
								LOG.debug("无效指数文件：{}"+name);
							}
								
						}
					}
					String trueName = sdf.format(maxDate)+".doc";
					ContinueFTP.ftpClient.enterLocalPassiveMode();
					InputStream in = ContinueFTP.ftpClient.retrieveFileStream(trueName);
					WordExtractor ex = new WordExtractor(in);
					String shzs = ex.getText();
					String detail = shzs.substring(shzs.lastIndexOf("生活气象指数预报："), shzs.length()).replace("\n", "").replace("\r", "");
					String aqi = detail.substring(detail.indexOf("AQI"), detail.indexOf("森林火险等级")).replace("\n", "").replace("\r", "");
					String slhx = detail.substring(detail.indexOf("森林火险等级"),detail.indexOf("人体舒适度")).replace("\n", "").replace("\r", "");
					String rtssd = detail.substring(detail.indexOf("人体舒适度"),detail.indexOf("行车安全")).replace("\n", "").replace("\r", "");
					String xcaq = detail.substring(detail.indexOf("行车安全"),detail.indexOf("空气污染")).replace("\n", "").replace("\r", "");
					String kqwr = detail.substring(detail.indexOf("空气污染"),detail.indexOf("洗车指数")).replace("\n", "").replace("\r", "");
					String xczs = detail.substring(detail.indexOf("洗车指数"),detail.lastIndexOf("生活气象")).replace("\n", "").replace("\r", "");
					List<Map> lists = new ArrayList<Map>();
					Map<String,String> map=new HashMap<String, String>();
					map.put("name","AQI指数");
					map.put("level", aqi.substring(aqi.indexOf("空气质量等级:")+7, aqi.indexOf("。")));
					map.put("content",aqi );
					map.put("imgUrl", preWeather+"/index/loving_img/loving_img09.png");
					lists.add(map);
					map=new HashMap<String, String>();
					map.put("name","森林火险");
					map.put("level", getLevel(slhx.substring(slhx.indexOf("级")+2, slhx.indexOf("级")+3)));
					map.put("content",slhx );
					map.put("imgUrl", preWeather+"/index/loving_img/loving_img05.png");
					lists.add(map);
					map=new HashMap<String, String>();
					map.put("name","人体舒适度");
					map.put("level", getLevel(rtssd.substring(rtssd.indexOf("级")-1, rtssd.indexOf("级"))));
					map.put("content",rtssd );
					map.put("imgUrl", preWeather+"/index/loving_img/loving_img10.png");
					lists.add(map);
					map=new HashMap<String, String>();
					map.put("name","行车安全");
					map.put("level", getLevel(xcaq.substring(xcaq.indexOf("级")-1, xcaq.indexOf("级"))));
					map.put("content",xcaq );
					map.put("imgUrl", preWeather+"/index/loving_img/loving_img07.png");
					lists.add(map);
					map=new HashMap<String, String>();
					map.put("name","空气污染气象条件");
					map.put("level", getLevel(kqwr.substring(kqwr.indexOf("级")-1, kqwr.indexOf("级"))));
					map.put("content",kqwr );
					map.put("imgUrl", preWeather+"/index/loving_img/loving_img11.png");
					lists.add(map);
					map=new HashMap<String, String>();
					map.put("name","洗车指数");
					map.put("level", getLevel(xczs.substring(xczs.indexOf("级")-1, xczs.indexOf("级"))));
					map.put("content",xczs );
					map.put("imgUrl", preWeather+"/index/loving_img/loving_img03.png");
					lists.add(map);
					if(null!=lists&&lists.size()>0){
						all.put("titleImg", preWeather+"/weixin/liveIndex.png");
						all.put("info", lists);
						SimpleDateFormat sdf1 = new SimpleDateFormat("指数预报：萧山气象台yyyy年MM月dd日发布");
						String updateTime =sdf1.format(maxDate);
						all.put("updateTime", updateTime);
					}
					
					String jsonText= com.alibaba.fastjson.JSON.toJSONString(all);
					String outPutPath=FileUtils.getContextPath()+"shzs.json";
					FileUtils.outPutFile(outPutPath, jsonText);	
					LOG.info("------------------------------定时检测指数预报任务结束------------------------------------");
				}else {
					LOG.error("生活指数FTP连接失败!");
					
				}
			    
			} catch (Exception e) {
				LOG.error("生活指数FTP异常.param={}", e);
			}finally {
				try {
					ContinueFTP.disconnect();
				} catch (IOException e) {
					LOG.error("关闭FTP异常.param={}", e);
				}
			}
			isRunning = false;    
	        } else {    
	            LOG.debug("上次执行未结束");
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
//	@Override
//	public void run() {
//		try {
//			Thread.sleep(3);
//			System.out.println(new Date().toLocaleString());
//			/*LOG.info("------------------------------定时检测影视节目任务开始------------------------------------");
//			Boolean result = false;
//			File file=new File(store);
//			if (!file.exists()) {
//				file.mkdirs();
//			}
//			File[] fileList = file.listFiles();
//			List<String> fileLocal = new ArrayList<>();
//			
//			try {
//				for (File f : fileList) {
//					if (ContinueFTP.isVideo(f.getName(), null)) {//判断是否为视频文件
//						fileLocal.add(f.getName());
//					}
//				}
//	        	Boolean con = ContinueFTP.connect(ftpUr, ftpPort, ftpUser, ftpPass);
//	    		if (con) {
//	    			FTPFile[] files = ContinueFTP.getFileNameList(qxjm);
//	    			List<String> fileNet = new ArrayList<>();
//	    			for (FTPFile ftpFile : files) {
//						if (ftpFile.isFile()) {
//							String name = ftpFile.getName();
//							if (ContinueFTP.isVideo(name, null)) {//判断是否为视频文件
//								fileNet.add(name);
//								if (!fileLocal.contains(name)) {
//									result = ContinueFTP.download(qxjm+name, store+name);
//									if (!result) {
//										LOG.debug("下载文件"+name+"失败！");
//									}
//									File fileTemp=new File(store+name);
//									result = fileTemp.delete();
//		    						if (!result) {
//										LOG.debug("删除文件"+name+"失败！");
//									}
//								}
//							}
//						}
//					}
//	    			*//**
//	    			 * 删除FTP不存在的文件
//	    			 *//*
//	    			for (String localName : fileLocal) {
//	    				if (!fileNet.contains(localName)) {
//	    					File fileTemp=new File(store+localName);
//	    					if (fileTemp.isFile()) {
//	    						result = fileTemp.delete();
//	    						if (!result) {
//									LOG.debug("删除文件"+localName+"失败！");
//								}
//	    					}
//	    				}
//	    			}
//	    		}
//	            
//	        } catch (Exception e) {
//	            LOG.error("同步FTP视频异常.param={}", e);
//	        }finally {
//	        	try {
//					ContinueFTP.disconnect();
//				} catch (IOException e) {
//					LOG.error("关闭FTP异常.param={}", e);
//				}
//	        }
//	        LOG.info("------------------------------定时检测影视节目任务结束------------------------------------");*/
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//	}
	    public static void main(String[] args) {
			WeatherIndexTask task = new WeatherIndexTask();
			task.run();
		}
}
