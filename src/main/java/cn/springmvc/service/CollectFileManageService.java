package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.FileInfo;

/**
 * @author 胡志强 2017年5月23日
 */
public interface CollectFileManageService {
	/**
	 * 收藏夹表格信息初始化
	 * 
	 * @param map
	 * @return
	 */
	List<FileInfo> queryCollectTableInfo(Map<String, Object> map)
			throws Exception;

	/**
	 * 取消收藏文件
	 * 
	 * @param collectInfo
	 * @return int
	 */
	public boolean cancelCollectFile(String loginUserId,
			List<String> collectObjectList);

	/**
	 * 文件下载
	 * 
	 * @param fileID
	 * @return String
	 */
	String fileDownloader(String fileID);

	/**
	 * 文件预览
	 * 
	 * @param fileID
	 * @return String
	 */
	String filePreview(String fileID);

}
