package cn.grassinfo.common.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import cn.grassinfo.common.Constant;
import cn.grassinfo.wap.entity.YingshiMedal;


@Service("QxysService")
public class QxysService {
	private static final Logger LOG = LoggerFactory.getLogger(QxysService.class);
	private static final String qxjmStore =Constant.propFileManager.getString("qxjmStore");
	private static final SimpleDateFormat df3 = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat df1 = new SimpleDateFormat("MM/dd");
	
	public List<YingshiMedal> YsList() {
		List<YingshiMedal> list = new ArrayList<>();
        try {
        	
        	File file=new File(qxjmStore);
        	File[] fileList = file.listFiles();
        	for (File f : fileList) {
        		YingshiMedal medal = new YingshiMedal();
        		String name = f.getName();
        		Date time = df3.parse(name.substring(0,8));
        		String trueName ="《" + name.substring(8,name.indexOf("."))+"》";
        		medal.setName(trueName+"-"+df1.format(time));
        		medal.setUrl(f.getName());
        		medal.setTime(time);
        		list.add(medal);
			}
        } catch (Exception e) {
            LOG.error("查询所有节目异常.param={}", e);
        }
        Collections.sort(list, new Comparator<YingshiMedal>() {
			@Override
			public int compare(YingshiMedal obj1, YingshiMedal obj2) {
				
                if(obj2.getTime().getTime()>obj1.getTime().getTime()){  
                    return 1;  
                }  
                if(obj1.getTime().getTime()==obj2.getTime().getTime()){  
                    return 0;  
                }  
                return -1;  
			}
        });
        if (list.size()>=6) {
			return list.subList(0, 6);
		}
        return list;
	}
	
/*	public  boolean runDownload(){
		String rootPath=getClass().getResource("/").getFile().toString();  
		System.out.println(rootPath);
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
									return result;
								}
							}
						}
					}
				}
    			*//**
    			 * 删除FTP不存在的文件
    			 *//*
    			for (String localName : fileLocal) {
    				if (!fileNet.contains(localName)) {
    					File fileTemp=new File(qxjmStore+localName);
    					if (fileTemp.isFile()) {
    						fileTemp.delete();
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
		
		return result;
		
	}*/
	
}
