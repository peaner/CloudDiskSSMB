package cn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	private static Logger logger = LoggerFactory.getLogger(FileUtils.class); 
	
	/**
	 * 获取文件类型
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getFileType(MultipartFile multipartFile) {
		// 获取文件后缀名
		String ext = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
		return ext + "文件";
	}
	
	/**
	 * 数据库文件大小显示到页面的转换
	 * @param fileSize
	 * @return
	 */
	public static String formatFileSize(String fileSize) {
		String value = "";
		try{
			long lf = Long.valueOf(fileSize);
			if (lf > 1024 * 1024) {
				lf = Math.round(lf * 100 / (1024 * 1024)) / 100;
				value = String.valueOf(lf) + "MB";
			} else {
				value = String.valueOf((Math.round(lf * 100 / 1024) / 100)) + "KB";
			}
		}catch(Exception e){
			logger.error("文件大小转换出现异常：" + e.getMessage());	
		}
		
		return value;
	}
}
