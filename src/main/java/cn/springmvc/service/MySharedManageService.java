package cn.springmvc.service;

import java.util.List;

import cn.springmvc.model.FileInfo;
import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.ShareInfo;


/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface MySharedManageService {
    /**
     * 查看我的共享文件列表信息 
     * @param shareInfo
     * @return
     */
	 List<QueryMySharedFileInfo> queryMySharedFileListInfo(ShareInfo shareInfo);
	 
	 /**
	  * 根据目录ID查看共享目录中的子文件列表信息
	 * @param shareInfo
	 * @return List<FileInfo>
	 */
	List<QueryMySharedFileInfo> queryNextPageSharedFileInfoByFolderID(FolderInfo folderInfo,QueryMySharedFileInfo queryMySharedFileInfo);
	 
	 /**
	  * 取消共享文件
	 * @param fileInfo
	 * @return int
	 */
	int cancelSharedFile(ShareInfo shareInfo);
	 
	/**
	 * 文件预览
	 * @param fileInfo
	 * @return String
	 */
	String filePreview(FileInfo fileInfo);
	 
	/**
	 * 文件下载
	 * @param fileInfo
	 * @return String
	 */
	String fileDownloader(FileInfo fileInfo);
}
