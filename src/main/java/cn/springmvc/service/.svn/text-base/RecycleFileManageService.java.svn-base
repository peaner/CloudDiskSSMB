package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.FileInfo;

/**
 * @author 胡志强 2017年5月23日
 */
public interface RecycleFileManageService {

	/**
	 * 查看回收站文件列表信息
	 * 
	 * @param recycleInfo
	 * @return List<FileInfo>
	 */
	List<FileInfo> queryRecycleTableInfo(Map<String, Object> map);

	/**
	 * 还原文件
	 * 
	 * @param recycleInfo
	 * @return int
	 */
	boolean restoreFile(String loginUserId, List<String> restoreObjectIDList);

	/**
	 * 彻底删除文件
	 * 
	 * @param recycleInfo
	 * @return int
	 */
	boolean absolutelyDeleteFile(String fileID);

	/**
	 * 文件预览
	 * 
	 * @param fileID
	 * @return String
	 */
	String filePreview(String fileID);
}
