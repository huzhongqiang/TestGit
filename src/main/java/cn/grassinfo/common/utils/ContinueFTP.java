package cn.grassinfo.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.grassinfo.common.util.Utils;


public class ContinueFTP {
	private static final Logger log = LoggerFactory
			.getLogger(ContinueFTP.class);
	public static FTPClient ftpClient = new FTPClient();

	public ContinueFTP() {
		// ftpClient.addProtocolCommandListener(new PrintCommandListener(
		// new PrintWriter(System.out)));
		// ftpClient.addProtocolCommandListener(new PrintCommandListener(
		// new PrintWriter(System.out)));
	}

	public static boolean connect(String hostname, int port, String username,
			String password) {
		try {
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.connect(hostname, port);
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				if (ftpClient.login(username, password)) {
					return true;
				}
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean connect(String hostname, int port, String username,
			String password, String folder) throws IOException {
		try {
			ftpClient.connect(hostname, port);
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				if (ftpClient.login(username, password)) {
					if (folder != null && !"".equals(folder)) {
						ftpClient.makeDirectory(folder);
						ftpClient.changeWorkingDirectory(folder);
					}
					return true;
				}
			}
			disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean download(String remote, String local) throws IOException {
		ftpClient.enterLocalPassiveMode();
		boolean result = false;
		File f = new File(local);
		if (f.exists()) {
			OutputStream out = new FileOutputStream(f, true);
			ftpClient.setRestartOffset(f.length());
			result = ftpClient.retrieveFile(remote, out);
			out.close();
		} else {
			OutputStream out = new FileOutputStream(f);
			result = ftpClient.retrieveFile(remote, out);
			out.close();
		}
		return result;
		
		
	}

	public void upload(String localName) throws Exception {
		upload(localName, "");
	}

	public void upload(String localName, String folder) throws Exception {
		FileInputStream fis = null;
		localName = localName.replaceAll("\\\\", "/");
		String remoteName = localName.substring(localName.lastIndexOf("/") + 1);
		try {
			File file = new File(localName);
			if (file.exists()) {
				fis = new FileInputStream(file);
				if(!isPicture(file.getPath(),null)){
					ftpClient.setControlEncoding("UTF-8");// 这里设置编码
					ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
				}else{
					ftpClient.setBufferSize(1024);
//			           //设置文件类型（二进制）
			        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				}
				ftpClient.storeFile(new String(remoteName.getBytes("UTF-8"),
						"iso-8859-1"), fis);
				
				System.out.println(1111);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("upload", e);
			throw new RuntimeException(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					log.error("upload", e);
				}
			}
		}
	}

	public void uploadDir(String localDir) {
		FileInputStream fis = null;
		try {
			File fileDir = new File(localDir);
			File[] files = fileDir.listFiles();
			for (File file : files) {
				String localName = file.getPath().replaceAll("\\\\", "/");
				String remoteName = localName.substring(localName
						.lastIndexOf("/") + 1);
				fis = new FileInputStream(file);
				ftpClient.storeFile(remoteName, fis);
				fis.close();
			}
		} catch (Exception e) {
			log.error("uploadDir", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void uploadDirFile(String localDir) {
		FileInputStream fis = null;
		try {
			File fileDir = new File(localDir);
			File[] files = fileDir.listFiles();
			System.out.println("size:" + files.length);
			if (null != files && files.length > 0) {
				for (File file : files) {
					if (file.isDirectory()) {
						continue;
					}
					String localName = file.getPath().replaceAll("\\\\", "/");
					String remoteName = localName.substring(localName
							.lastIndexOf("/") + 1);
					fis = new FileInputStream(file);
					// System.out.println("remoteName:"+remoteName);
					ftpClient.storeFile(remoteName, fis);
//					Boolean flag=isImage(file);//isPicture(file.getPath(),null);
//					System.out.println(flag);
//					System.out.println(file.getPath());
//					if (flag) {
//						ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//					} else {
//						ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
//					}
					
//					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
//					ftpClient.setFileTransferMode(FTP.BINARY_FILE_TYPE);
					
//					ftpClient.changeWorkingDirectory("/test");
//			           ftpClient.setBufferSize(1024);
//			           ftpClient.setControlEncoding("GBK");
//			           //设置文件类型（二进制）
//			           ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//					
//					ftpClient.setFileType(FTP.ASCII_FILE_TYPE);
//					ftpClient.setFileTransferMode(FTP.ASCII_FILE_TYPE);
					
					
//					ftpClient.set;//避免传递的图片失真

					fis.close();
				}
			}

		} catch (Exception e) {
			log.error("uploadDir", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	
	 public static void sendSingleFile(String ip,String username,String password,String localFile,String remoteDirectorPath,String remoteName){
		 System.out.println("localFile:"+localFile+"--remoteDirectorPath:"+remoteDirectorPath+"remoteName:"+remoteName);  
		 FTPClient ftpClient = new FTPClient();
		    FileInputStream fis = null;
	       try {
	           ftpClient.connect(ip);
	           ftpClient.login(username,password);
	           File srcFile = new File(localFile);
	           Boolean flag=isPicture(srcFile.getPath(),null);
	           fis = new FileInputStream(srcFile);
	           //设置上传目录
	           ftpClient.changeWorkingDirectory(remoteDirectorPath);
	           ftpClient.setBufferSize(1024);
	           ftpClient.setControlEncoding("GBK");
	           //设置文件类型（二进制）
	           if(flag){
	        	   ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
	           }else{
	        	   ftpClient.setFileType(FTPClient.ASCII_FILE_TYPE);
	           }
	           ftpClient.storeFile(remoteName, fis);
	       }catch(Exception ex){
	    	   System.out.println(ex);
	       }finally {
	           try { 
	        	   if(fis!=null)
	        		   fis.close();
	               ftpClient.disconnect();
	           } catch (IOException e) {
	               e.printStackTrace();
	               throw new RuntimeException("关闭FTP连接发生异常！", e);
	           }
	       }
	 }
	 
	 
	 //多线程上传单个文件
	 public static void sendFileByThread(String ip,String username,String password,String localFile,String remoteDirectorPath){
		 ContinueFTP myFtp = new ContinueFTP();
			try {
				myFtp.connect(ip, 21, username,password,remoteDirectorPath);
				myFtp.upload(localFile, "");
				myFtp.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }

	// 判断是否为图片文件
	private Boolean isImage(File resFile) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(resFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bi == null) {
			return false;
		} else {
			return true;
		}
	}

	public static void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	/**
	 * 判断文件是否为图片<br>
	 * <br>
	 * 
	 * @param pInput
	 *            文件名<br>
	 * @param pImgeFlag
	 *            判断具体文件类型<br>
	 * @return 检查后的结果<br>
	 * @throws Exception
	 */
	public static boolean isPicture(String pInput, String pImgeFlag)
			throws Exception {
		// 文件名称为空的场合
		if (Utils.isEmpty(pInput)) {
			// 返回不和合法
			return false;
		}
		// 获得文件后缀名
		String tmpName = pInput.substring(pInput.lastIndexOf(".") + 1,
				pInput.length());
		// 声明图片后缀名数组
		String imgeArray[][] = { { "bmp", "0" }, { "dib", "1" },
				{ "gif", "2" }, { "jfif", "3" }, { "jpe", "4" },
				{ "jpeg", "5" }, { "jpg", "6" }, { "png", "7" },
				{ "tif", "8" }, { "tiff", "9" }, { "ico", "10" } };
		// 遍历名称数组
		for (int i = 0; i < imgeArray.length; i++) {
			// 判断单个类型文件的场合
			if (!Utils.isEmpty(pImgeFlag)
					&& imgeArray[i][0].equals(tmpName.toLowerCase())
					&& imgeArray[i][1].equals(pImgeFlag)) {
				return true;
			}
			// 判断符合全部类型的场合
			if (Utils.isEmpty(pImgeFlag)
					&& imgeArray[i][0].equals(tmpName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 判断文件是否为视频<br>
	 * <br>
	 * 
	 * @param vInput
	 *            文件名<br>
	 * @param videoFlag
	 *            判断具体文件类型<br>
	 * @return 检查后的结果<br>
	 * @throws Exception
	 */
	public static boolean isVideo(String vInput, String videoFlag)
			throws Exception {
		// 文件名称为空的场合
		if (Utils.isEmpty(vInput)) {
			// 返回不和合法
			return false;
		}
		// 获得文件后缀名
		String tmpName = vInput.substring(vInput.lastIndexOf(".") + 1,
				vInput.length());
		// 声明视频后缀名数组
		String imgeArray[][] = { { "mp4", "0" }, { "avi", "1" },
				{ "mpg", "2" }};
		// 遍历名称数组
		for (int i = 0; i < imgeArray.length; i++) {
			// 判断单个类型文件的场合
			if (!Utils.isEmpty(videoFlag)
					&& imgeArray[i][0].equals(tmpName.toLowerCase())
					&& imgeArray[i][1].equals(videoFlag)) {
				return true;
			}
			// 判断符合全部类型的场合
			if (Utils.isEmpty(videoFlag)
					&& imgeArray[i][0].equals(tmpName.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	/**

	* 返回FTP目录下的文件列表

	* @param ftpDirectory

	* @return

	*/
	public static FTPFile[] getFileNameList(String ftpDirectory){
		ftpClient.enterLocalPassiveMode();
		try{
			FTPFile[] files = ftpClient.listFiles(ftpDirectory);
			return files;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	// 调用的时候 pImgeFlag为空的场合，表示验证全部后缀名，比如为“1”的场合表示判断后缀名是否为"bmp"

	public static void main(String[] args) throws IOException {
		ContinueFTP myFtp = new ContinueFTP();
		try {
			Boolean result = myFtp.connect("10.135.152.139", 21, "xswx", "58459");
			if (result) {
				FTPFile[] files = myFtp.getFileNameList("/public/萧山气象/");
				for (FTPFile file : files) {
					if (ContinueFTP.isVideo(file.getName(), null)) {//判断是否为视频文件
						System.out.println(file.getName());
						myFtp.download("/public/萧山气象/"+file.getName(), "d:/ceshi/"+file.getName());
					}
				}
			}
//			FTPFile[] files = myFtp.getFileNameList("/public/萧山气象");
//			for (FTPFile file : files) {
//				System.out.println(file.getName());
//			}
//			myFtp.download("public/201006下.doc", "d:/201006下.doc");
			
			
//			myFtp.connect("10.135.145.11", 21, "root", "12345678");
//			myFtp.getFileNameList("/pad/dataimg/radar/");
//			myFtp.download("pad/dataimg/radar/qpf00_180.png", "d:/qpf00_180.png");
//			myFtp.download("pad/dataimg/radar/qpf00_180.png", "d:/qpf00_60.png");
			myFtp.disconnect();
		} catch (Exception e) {
			// System.out.println("连接FTP出错:" + e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		
		
		
		
		
		
		
	}
}