package cn.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.QueryShareObjectInfo;
import cn.springmvc.model.ShareInfo;

@Component
public interface ShareInfoMapper {
    int insertShareInfo(ShareInfo shareInfo);

    int insertSelective(ShareInfo record);

	/**
	 * 查询我共享给别人以及群组的共享文件列表信息
	 * @param shareInfo
	 * @return List<QuerySharedFileTableInfo>
	 */
	List<QueryMySharedFileInfo> queryMySharedFileByOperator(ShareInfo shareInfo);
	
	/**
	 * 查询别人共享给我或者群组，且群组中有我的共享文件列表信息
	 * @param shareInfo
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryMySharedFileByShareObjectID(ShareInfo shareInfo);

	/**
	 * 根据目录ID查询该目录下的子文件列表信息
	 * @param shareInfo
	 * @return List<QuerySharedFileTableInfo>
	 */
	List<QueryMySharedFileInfo> queryNextPageSharedFileInfoByFolderID(
			FolderInfo folderInfo);
	
	/**
	 * 根据当前目录ID查看其父目录下的子文件列表信息
	 * @param folderInfo
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryPreviousPageSharedFileInfoByFolderID(
			FolderInfo folderInfo);

	/**
	 * 删除共享文件
	 * @param queryMySharedFileInfo
	 * @return int
	 */
	int deleteMySharedInfo(ShareInfo shareInfo);

	/**
	 * 查询公共共享文件列表信息
	 * @return List<QueryMySharedFileInfo>
	 */
	List<QueryMySharedFileInfo> queryPublicSharedFileByShareObjectID();

	/**
	 * 查询共享对象列表信息
	 * @param queryShareObjectInfo
	 * @return List<QueryShareObjectInfo>
	 */
	List<QueryShareObjectInfo> queryShareObjectInfo(
			QueryShareObjectInfo queryShareObjectInfo);
	
	List<QueryMySharedFileInfo> querySharedFileByFileID(String fileID);
}