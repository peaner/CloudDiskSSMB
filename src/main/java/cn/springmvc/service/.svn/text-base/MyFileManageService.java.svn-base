package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import cn.springmvc.model.FileInfo;
import cn.springmvc.model.QueryShareObjectInfo;
import cn.springmvc.model.RecycleInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.model.TreeFolderDataInfo;

/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface MyFileManageService {
    /**
     * 查看我的文件列表信息 
     * @param map
     * @return
     */	
	List<FileInfo> queryMyFileListInfo(Map<String, Object> map);	
	
    /**
     * 点击目录查询目录下所有文件以及文件目录
     * @param map
     * @return
     */
	List<FileInfo> queryFolderContainInfo(Map<String, Object> map);
	
    /**
     * 输入搜索框查询列表信息
     * @param map
     * @return
     */
	List<FileInfo> querySearchInfo(Map<String, Object> map);
	 
	 /**
	  * 创建文件夹
	  * @param loginUser
	  * @param loginUserId
	  * @param folderName
	  * @param currentFolderId
	  * @return
	  */
	boolean createFolder(String loginUser, String loginUserId, String folderName, String currentFolderId);
	
	/**
	 * 收藏文件对象（文件或者文件夹）
	 * @param loginUser
	 * @param loginUserId
	 * @param collectObject
	 * @return
	 */
	boolean collectObject(String loginUser, String loginUserId, List<String> collectObjectList);
	 
	 /**
	  * 复制或者移动文件
	  * @param loginUserId
	  * @param parentFolderID
	  * @param childrenFolderID
	  * @param controlType
	  * @return
	  */
	List<String> copyOrmoveTo(String loginUserId, String parentFolderID, List<String> childrenFolderID, int controlType);
	 
	/**从我的文件中共享文件
	 * @param shareInfo
	 * @return int
	 */
	int shareFileFromMyFile(ShareInfo shareInfo);
	 
	/**
	 * 查询共享对象信息,包括个人，群组，以及all
	 * @param cloudDiskUser
	 * @param groupInfo
	 * @return List<GroupInfo>
	 */
	List<QueryShareObjectInfo> queryShareObjectInfo(QueryShareObjectInfo queryShareObjectInfo);
	 
	/**
	 * 重命名文件
	 * @param fileInfo
	 * @return int
	 */
	int renameFile(FileInfo fileInfo);	
	 
	/**
	 * 文件上传
	 * @param multipartFile
	 * @param loginUser
	 * @param loginUserId
	 * @param comment
	 * @param parentFolderID
	 * @return
	 */
	boolean fileUploader(MultipartFile multipartFile, String loginUser, String loginUserId, String comment, String parentFolderID);
	 
	/**
	 * 文件下载
	 * @param loginUser
	 * @param loginUserId
	 * @param fileInfo
	 * @return
	 */
	ResponseEntity<byte[]> fileDownloader(String loginUser, String loginUserId, FileInfo fileInfo);
	 
	/**
	 * 文件预览
	 * @param fileID
	 * @return String
	 */
	String filePreview(String fileID);
	 
	/**
	 * 删除文件
	 * @param fileID
	 * @return int
	 */
	int deleteFile(String fileID,RecycleInfo recycleInfo);
	
	/**
	 * 目标目录下拉框值
	 * @param loginUserId
	 * @param parentFolderID
	 * @param selectFolderIds
	 * @return
	 */
	TreeFolderDataInfo queryFolderIds(String loginUserId, String parentFolderID, String selectFolderIds);
}
