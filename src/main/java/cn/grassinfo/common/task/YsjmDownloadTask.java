package cn.grassinfo.common.task;

import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import cn.grassinfo.common.Constant;
import cn.grassinfo.common.utils.ContinueFTP;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.servlet.ServletContext;

/**
 * @author huhengbo
 * @Type YsjmDownloadTask
 * @Desc
 * @date 2017/2/24
 */
public class YsjmDownloadTask extends TimerTask{

    private static final Logger LOG = LoggerFactory.getLogger(YsjmDownloadTask.class);
    private static final String qxjm = Constant.propFileManager.getString("qxjm");
	private static final String ftpUr = Constant.propFileManager.getString("ftpUr");
	private static final String ftpUser = Constant.propFileManager.getString("ftpUser");
	private static final String ftpPass = Constant.propFileManager.getString("ftpPass");
	private static final Integer ftpPort =Integer.parseInt(Constant.propFileManager.getString("ftpPort"));
	private static final String qxjmStore =Constant.propFileManager.getString("qxjmStore");
    
    
	 private static boolean isRunning = false;    
	    
	    private ServletContext context = null;    
	    
	    public YsjmDownloadTask() {  
	        super();  
	    }  
	      
	    public YsjmDownloadTask(ServletContext context) {    
	        this.context = context;    
	    }    
	    @Override  
	    public void run() {  
	          
	        if (!isRunning) {  
	            System.out.println(new Date().toLocaleString());
				LOG.info("------------------------------定时检测影视节目任务开始------------------------------------");
				Boolean result = false;
				File file=new File(qxjmStore);
				if (!file.exists()) {
					file.mkdirs();
				}
				File[] fileList = file.listFiles();
				List<String> fileLocal = new ArrayList<>();
				
				try {
					for (File f : fileList) {
						if (ContinueFTP.isVideo(f.getName(), null)) {//判断是否为视频文件
							fileLocal.add(f.getName());
						}
					}
					Boolean con = ContinueFTP.connect(ftpUr, ftpPort, ftpUser, ftpPass);
					if (con) {
						FTPFile[] files = ContinueFTP.getFileNameList(qxjm);
						List<String> fileNet = new ArrayList<>();
						for (FTPFile ftpFile : files) {
							if (ftpFile.isFile()) {
								String name = ftpFile.getName();
								if (ContinueFTP.isVideo(name, null)) {//判断是否为视频文件
									fileNet.add(name);
									if (!fileLocal.contains(name)) {
										result = ContinueFTP.download(qxjm+name, qxjmStore+name);
										if (!result) {
											LOG.debug("下载文件"+name+"失败！");
											File fileTemp=new File(qxjmStore+name);
											result = fileTemp.delete();
											if (!result) {
												LOG.debug("删除文件"+name+"失败！");
											}
										}
										
									}
								}
							}
						}
						/**
						 * 删除FTP不存在的文件
						 */
						for (String localName : fileLocal) {
							if (!fileNet.contains(localName)) {
								File fileTemp=new File(qxjmStore+localName);
								if (fileTemp.isFile()) {
									result = fileTemp.delete();
									if (!result) {
										LOG.debug("删除文件"+localName+"失败！");
									}
								}
							}
						}
					}
				    
				} catch (Exception e) {
				    LOG.error("同步FTP视频异常.param={}", e);
				}finally {
					try {
						ContinueFTP.disconnect();
					} catch (IOException e) {
						LOG.error("关闭FTP异常.param={}", e);
					}
				}
				LOG.info("------------------------------定时检测影视节目任务结束------------------------------------");
	            isRunning = false;    
	        } else {    
	            LOG.debug("上次执行未结束");
	     }  
	    } 
	
//	@Override
//	public void run() {
//		try {
//			Thread.sleep(3);
//			System.out.println(new Date().toLocaleString());
//			/*LOG.info("------------------------------定时检测影视节目任务开始------------------------------------");
//			Boolean result = false;
//			File file=new File(qxjmStore);
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
//									result = ContinueFTP.download(qxjm+name, qxjmStore+name);
//									if (!result) {
//										LOG.debug("下载文件"+name+"失败！");
//									}
//									File fileTemp=new File(qxjmStore+name);
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
//	    					File fileTemp=new File(qxjmStore+localName);
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
			YsjmDownloadTask task = new YsjmDownloadTask();
			task.run();
		}
}
