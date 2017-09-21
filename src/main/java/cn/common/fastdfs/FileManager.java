package cn.common.fastdfs;

import java.io.File;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * 文件操作类
 * @author hzq
 */
public class FileManager implements FileManagerConfig {
	private static Logger logger = LoggerFactory.getLogger(FileManager.class);
	private static final long serialVersionUID = 1L;
	private static TrackerClient trackerClient;
	private static TrackerServer trackerServer;
	private static StorageServer storageServer;
	private static StorageClient storageClient;
	static {
		try {
			String classPath = new File(FileManager.class.getResource("/")
					.getFile() + CLIENT_CONFIG_FILE_PATH + File.separator).getCanonicalPath();
			String fdfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;
			ClientGlobal.init(fdfsClientConfigFilePath);
			trackerClient = new TrackerClient();
			trackerServer = trackerClient.getConnection();
			storageClient = new StorageClient(trackerServer, storageServer);
		} catch (Exception e) {
			logger.error("文件操作初始化出现异常：" + e.getMessage());
		}
	}

	/**
	 * 文件上传
	 * @param file 上传文件
	 * @param valuePairs 文件的可选性描述信息
	 * @return
	 */
	public static String[] upload(FastDFSFile file, NameValuePair[] valuePairs) {
		String[] uploadResults = null;
		
		try {
			uploadResults = storageClient.upload_file(file.getContent(),
					file.getExt(), valuePairs);
		} catch (Exception e) {
			logger.error("文件上传出现异常：" + e.getMessage());
		}
		
		return uploadResults;
	}
	
	/**
	 * 文件下载
	 * @param groupName 组名，例如group1
	 * @param remoteFileName 文件路径和名称，例如：M00/00/00/CrEC8VkWuKGANcMsAAApO9CVdGA62.xlsx
	 * @param specFileName 下载后对应文件名
	 * @return
	 */
	public static ResponseEntity<byte[]> download(String groupName,
			String remoteFileName, String specFileName) {
		byte[] content = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			content = storageClient.download_file(groupName, remoteFileName);
			headers.setContentDispositionFormData("attachment", new String(
					specFileName.getBytes("UTF-8"), "iso-8859-1"));
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		} catch (Exception e) {
			logger.error("文件下载出现异常：" + e.getMessage());
			return null;
		}
		return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 文件删除
	 * @param groupName 组名，例如group1
	 * @param remoteFileName 文件路径和名称，例如：M00/00/00/CrEC8VkWuKGANcMsAAApO9CVdGA62.xlsx
	 * @return 0：删除成功  -1：发生异常  其他：删除失败
	 */
	public static Integer delete(String groupName, String remoteFileName) {
		int result = -1;
		try {
			result = storageClient.delete_file(groupName, remoteFileName);
		} catch (Exception e) {
			logger.error("文件下载出现异常：" + e.getMessage());
		}
		
		return result;
	}
	
}
