package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.springmvc.model.FileInfo;

@Component
public interface FileInfoMapper {
    /**
     * 查询文件信息 
     * @param fileInfo
     * @return
     */
	List<FileInfo> queryFileInfo(FileInfo fileInfo);

    /**
     * 查询页面我的文件表格信息 
     * @param map
     * @return
     */
	List<FileInfo> queryMyFileTableInfo(Map<String, Object> map);
	
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
     * 删除文件信息 
     * @param fileInfo
     * @return
     */
	int deleteFileInfo(FileInfo fileInfo);

	/**
	 * 插入文件信息 
	 * @param fileInfo
	 * @return
	 */
    int insertFileInfo(FileInfo fileInfo);

	/**
	 * 更新文件信息 
	 * @param fileInfo
	 * @return
	 */
    int updateFileInfo(FileInfo fileInfo);

	/**
	 * @param fileID
	 * @return int
	 */
	int deleteFile(String fileID);
}