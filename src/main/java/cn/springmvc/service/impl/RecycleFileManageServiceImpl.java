package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.springmvc.dao.FileInfoMapper;
import cn.springmvc.dao.FolderInfoMapper;
import cn.springmvc.dao.FolderRelationMapper;
import cn.springmvc.dao.RecycleInfoMapper;
import cn.springmvc.model.FileInfo;
import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.FolderRelation;
import cn.springmvc.model.RecycleInfo;
import cn.springmvc.service.BaseFileService;
import cn.springmvc.service.MyFileManageService;
import cn.springmvc.service.RecycleFileManageService;

@Service
public class RecycleFileManageServiceImpl extends BaseFileService implements
		RecycleFileManageService {
	private static Logger logger = LoggerFactory
			.getLogger(RecycleFileManageServiceImpl.class);
	@Autowired
	private RecycleInfoMapper recycleInfoMapper;
	@Autowired
	private FileInfoMapper fileInfoMapper;
	@Autowired
	private FolderInfoMapper folderInfoMapper;
    @Autowired
	private MyFileManageService myFileManageService;
    @Autowired
    private FolderRelationMapper folderRelationMapper;
	@Override
	public List<FileInfo> queryRecycleTableInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return recycleInfoMapper.queryRecycleTableInfo(map);
	}

	@Transactional
	@Override
	public boolean restoreFile(String loginUserId,
			List<String> restoreObjectIDList) {
		// TODO Auto-generated method stub
		boolean cancleCollectResult = false;
		try {
			if (restoreObjectIDList != null && restoreObjectIDList.size() > 0) {
				for (String fileObjId : restoreObjectIDList) {
					FileInfo fileInfo = new FileInfo();
					fileInfo.setFileid(fileObjId);
					fileInfo.setFilestate("normal");
					fileInfoMapper.updateFileInfo(fileInfo);

					FolderInfo folderInfo = new FolderInfo();
					folderInfo.setFolderid(fileObjId);
					folderInfo.setFolderState("normal");
					folderInfoMapper.updateFolderInfo(folderInfo);

					RecycleInfo recycleInfo = new RecycleInfo();
					recycleInfo.setUserid(loginUserId);
					recycleInfo.setRecycleobjectid(fileObjId);
					recycleInfoMapper.deleteRecycleInfoInfo(recycleInfo);
				}
			}
			cancleCollectResult = true;
		} catch (Exception e) {
			logger.error("恢复删除文件对象数据库操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}

		return cancleCollectResult;
	}
	
    @Transactional
	@Override
	public boolean absolutelyDeleteFile(String fileID) {
		// TODO Auto-generated method stub
    	boolean result = false;
    	try {
    		FileInfo fileInfo=new FileInfo();
			fileInfo.setFileid(fileID);
    		List<FileInfo> fInfos=fileInfoMapper.queryFileInfo(fileInfo);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("fileObjId", fileInfo.getFileid());
			//判断是否是文件，文件直接删除
	    	if (fInfos.size()>0) {
				//1.删除目录关系表的记录
				FolderRelation folderRelation=new FolderRelation();
				folderRelation.setChildrenfolderid(fInfos.get(0).getFileid());
				folderRelationMapper.deleteFolderRelation(folderRelation);
				//2.删除文件表的记录
				fileInfoMapper.deleteFileInfo(fInfos.get(0));
				//3.删除实体文件
	            super.delete(fInfos.get(0).getFilegroupname(), fInfos.get(0).getFilepath());
			}//该文件是目录，且为空目录
	    	else if (myFileManageService.queryFolderContainInfo(paramMap).size()==0) {
				//1.删除目录表的记录即可
				FolderInfo folderInfo=new FolderInfo();
				folderInfo.setFolderid(fileInfo.getFileid());
				folderInfoMapper.deleteFolderInfo(folderInfo);
				//2.删除目录关系表的记录
				FolderRelation folderRelation=new FolderRelation();
				folderRelation.setChildrenfolderid(fileInfo.getFileid());
				folderRelationMapper.deleteFolderRelation(folderRelation);
			}//该文件是目录，且下面还有子文件，进行递归操作
			else {
				Map<String, Object> paramMap2 = new HashMap<String, Object>();
				List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
				paramMap2.put("fileObjId", fileID);
				fileInfoList = myFileManageService.queryFolderContainInfo(paramMap2);
				//循环遍历删除子文件，可能是目录也可能是文件
				for (FileInfo file : fileInfoList) {
					RecycleInfo recycleInfo=new RecycleInfo();
					recycleInfo.setRecycleobjectid(file.getFileid());
					if (recycleInfoMapper.queryRecycleInfoInfo(recycleInfo).size()>0) {
						continue;
					}else {
						absolutelyDeleteFile(file.getFileid());
					}				
				}
				//1.删除目录表的记录即可
				FolderInfo folderInfo=new FolderInfo();
				folderInfo.setFolderid(fileInfo.getFileid());
				folderInfoMapper.deleteFolderInfo(folderInfo);
				//2.删除目录关系表的记录
				FolderRelation folderRelation=new FolderRelation();
				folderRelation.setChildrenfolderid(fileInfo.getFileid());
				folderRelationMapper.deleteFolderRelation(folderRelation);
			}
			//该次文件删除成功
			result=true;
		} catch (Exception e) {
			logger.error("删除文件操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return result;
	}

	@Override
	public String filePreview(String fileID) {
		// TODO Auto-generated method stub
		return null;
	}

}
