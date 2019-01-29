package com.sdp.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.pool2.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Ftp工具类
 * 
 */
public class FtpUtil {

	/**
	 * ftpClient连接池初始化标志
	 */
	private static volatile boolean hasInit = false;
	/**
	 * ftpClient连接池
	 */
	private static ObjectPool<FTPClient> ftpClientPool;

	private static Logger log = LoggerFactory.getLogger(Object.class);

	/**
	 * 初始化ftpClientPool
	 *
	 * @param ftpClientPool
	 */
	public static void init(ObjectPool<FTPClient> ftpClientPool) {
		if (!hasInit) {
			synchronized (FtpUtil.class) {
				if (!hasInit) {
					FtpUtil.ftpClientPool = ftpClientPool;
					hasInit = true;
				}
			}
		}
	}

	/**
	 * 检查ftpClientPool是否可用
	 */
	private static void checkFtpClientPoolAvailable() {
		Assert.state(hasInit, "FTP未启用或连接失败！");
	}

	/**
	 * 获取ftpClient
	 *
	 * @return
	 */
	private static FTPClient getFtpClient() {
		checkFtpClientPoolAvailable();
		FTPClient ftpClient = null;
		Exception ex = null;
		// 获取连接最多尝试3次
		for (int i = 0; i < 3; i++) {
			try {
				ftpClient = ftpClientPool.borrowObject();
				ftpClient.changeWorkingDirectory("/");
				break;
			} catch (Exception e) {
				ex = e;
			}
		}
		if (ftpClient == null) {
			throw new RuntimeException("Could not get a ftpClient from the pool", ex);
		}
		return ftpClient;
	}

	/**
	 * 释放ftpClient
	 */
	private static void releaseFtpClient(FTPClient ftpClient) {
		if (ftpClient == null) {
			return;
		}

		try {
			ftpClientPool.returnObject(ftpClient);
		} catch (Exception e) {
			log.error("Could not return the ftpClient to the pool", e);
			// destoryFtpClient
			if (ftpClient.isAvailable()) {
				try {
					ftpClient.disconnect();
				} catch (IOException io) {
				}
			}
		}
	}

	/**
	 * 上传Ftp文件
	 *
	 * @param localFile        本地文件
	 * @param romotUpLoadePath 上传服务器路径 - 应该以/结束
	 * @return
	 */
	public static boolean uploadFile(File localFile, String romotUpLoadePath) {
		BufferedInputStream inStream = null;
		FTPClient ftpClient = getFtpClient();
		boolean success = false;
		try {
			FtpUtil.createDir(romotUpLoadePath);
			ftpClient.changeWorkingDirectory(romotUpLoadePath);// 改变工作路径
			inStream = new BufferedInputStream(new FileInputStream(localFile));
			log.info(localFile.getName() + "开始上传.....");
			success = ftpClient.storeFile(localFile.getName(), inStream);
			if (success == true) {
				log.info(localFile.getName() + "上传成功");
				return success;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error(localFile + "未找到");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FtpUtil.releaseFtpClient(ftpClient);
		}
		return success;
	}

	public static void createDir(String remote) {
		FTPClient ftpClient = FtpUtil.getFtpClient();
		try {
			String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
			if (!directory.equalsIgnoreCase("/")) {
				// && !ftpClient.changeWorkingDirectory(directory)
				// 如果远程目录不存在，则递归创建远程服务器目录
				int start = 0;
				int end = 0;
				if (directory.startsWith("/")) {
					start = 1;
				} else {
					start = 0;
				}
				end = directory.indexOf("/", start);
				while (true) {
					String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
					if (!ftpClient.changeWorkingDirectory(subDirectory)) {
						if (ftpClient.makeDirectory(subDirectory)) {
							ftpClient.changeWorkingDirectory(subDirectory);
						} else {
							System.out.println("创建目录失败");
						}
					}
					start = end + 1;
					end = directory.indexOf("/", start);
					// 检查所有目录是否创建完毕
					if (end <= start) {
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			FtpUtil.releaseFtpClient(ftpClient);
		}
	}

	/**
	 * 
	 * 【功能描述：删除文件夹】 【功能详细描述：功能详细描述】
	 * 
	 * @see 【类、类#方法、类#成员】
	 * @param ftpClient
	 * @param ftpPath   文件夹的地址
	 * @return true 表似成功，false 失败
	 * @throws IOException
	 */
	public static boolean iterateDelete(FTPClient ftpClient, String ftpPath) throws IOException {
		FTPFile[] files = ftpClient.listFiles(ftpPath);
		boolean flag = false;
		for (FTPFile f : files) {
			String path = ftpPath + File.separator + f.getName();
			if (f.isFile()) {
				// 是文件就删除文件
				ftpClient.deleteFile(path);
			} else if (f.isDirectory()) {
				iterateDelete(ftpClient, path);
			}
		}
		// 每次删除文件夹以后就去查看该文件夹下面是否还有文件，没有就删除该空文件夹
		FTPFile[] files2 = ftpClient.listFiles(ftpPath);
		if (files2.length == 0) {
			flag = ftpClient.removeDirectory(ftpPath);
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * 【功能描述：删除ftp 上指定的文件】 【功能详细描述：功能详细描述】
	 * 
	 * @see 【类、类#方法、类#成员】
	 * @param ftpPath ftp上的文件路径
	 * @return true 成功，false，失败
	 */
	public static boolean deleteDir(String ftpPath) {
		FTPClient ftpClient = new FTPClient();
		boolean flag = false;
		try {
			ftpClient = getFtpClient();
			flag = iterateDelete(ftpClient, ftpPath);
		} catch (IOException e) {
			// TODO 异常处理块
			e.printStackTrace();
		} finally {
			FtpUtil.releaseFtpClient(ftpClient);
		}
		return flag;
	}
}