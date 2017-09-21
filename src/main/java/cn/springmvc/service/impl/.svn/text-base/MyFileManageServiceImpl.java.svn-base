package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cn.common.CommonConstant;
import cn.springmvc.dao.CloudDiskInfoMapper;
import cn.springmvc.dao.CollectInfoMapper;
import cn.springmvc.dao.FileInfoMapper;
import cn.springmvc.dao.FolderInfoMapper;
import cn.springmvc.dao.FolderRelationMapper;
import cn.springmvc.dao.RecycleInfoMapper;
import cn.springmvc.dao.ShareInfoMapper;
import cn.springmvc.model.CloudDiskInfo;
import cn.springmvc.model.CollectInfo;
import cn.springmvc.model.FileInfo;
import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.FolderRelation;
import cn.springmvc.model.QueryShareObjectInfo;
import cn.springmvc.model.RecycleInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.model.TreeFolderDataInfo;
import cn.springmvc.service.BaseFileService;
import cn.springmvc.service.MyFileManageService;
import cn.utils.CreatUuidUtil;
import cn.utils.DateUtils;
import cn.utils.FileUtils;

@Service
public class MyFileManageServiceImpl extends BaseFileService implements
		MyFileManageService {
	private static Logger logger = LoggerFactory.getLogger(MyFileManageServiceImpl.class);
	@Autowired
	private FileInfoMapper fileInfoMapper;
	@Autowired
	private CollectInfoMapper collectInfoMapper;
	@Autowired
	private CloudDiskInfoMapper cloudDiskInfoMapper;
	@Autowired
	private FolderInfoMapper folderInfoMapper;	
	@Autowired
	private FolderRelationMapper folderRelationMapper;
	@Autowired
	private ShareInfoMapper shareInfoMapper;
	@Autowired
	private RecycleInfoMapper recycleInfoMapper;
	@Override	
	public List<FileInfo> queryMyFileListInfo(Map<String, Object> map) {
		return fileInfoMapper.queryMyFileTableInfo(map);
	}

	@Override
	public List<FileInfo> queryFolderContainInfo(Map<String, Object> map) {
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try{
			FolderRelation folderRelation = new FolderRelation();
			folderRelation.setParentfolderid(map.get("fileObjId").toString());
			//获取用户父目录下所有的子目录Id
			List<FolderRelation> folderRelationList = folderRelationMapper.queryFolderRelation(folderRelation);
			if(folderRelationList != null && folderRelationList.size() > 0){
				String folderRelations = "";
				for (FolderRelation fr : folderRelationList) {
					folderRelations = folderRelations + fr.getChildrenfolderid() + ",";
				}
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("folderRelations", folderRelations);
				if(map.containsKey("sort")){
					paramMap.put("sort", map.get("sort").toString());
					paramMap.put("order", map.get("order").toString());
				}				
				fileInfoList = fileInfoMapper.queryFolderContainInfo(paramMap);
			}			
		}catch(Exception e){
			logger.error("点击目录查询目录下所有文件以及文件目录发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return fileInfoList;
	}	
	

	@Override
	public List<FileInfo> querySearchInfo(Map<String, Object> map) {
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try{
			fileInfoList = fileInfoMapper.querySearchInfo(map);
		}catch(Exception e){
			logger.error("搜索框输入查询功能发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return fileInfoList;
	}
	
	@Transactional
	@Override
	public boolean createFolder(String loginUser, String loginUserId, String folderName, String currentFolderId) {
		boolean createFolderResult = false;
		try{
			//文件目录信息表数据插入
			FolderInfo folderInfo = new FolderInfo();
			String folderid = CreatUuidUtil.getUuid();			
			folderInfo.setFolderid(folderid);
			folderInfo.setFoldername(folderName);
			folderInfo.setFolderState(CommonConstant.fileState.normal.toString());
			folderInfo.setOperator(loginUser);
			folderInfo.setCreatedate(DateUtils.getSystemTime());
			folderInfoMapper.insertFolderInfo(folderInfo);
			//文件目录关系表数据插入
			FolderRelation folderRelation = new FolderRelation();
			folderRelation.setUserid(loginUserId);
			folderRelation.setParentfolderid(currentFolderId);
			folderRelation.setChildrenfolderid(folderid);
			String isRootFolder = CommonConstant.NOT_ROOT_FOLDER;
			if(currentFolderId == null || "".equals(currentFolderId)){
				isRootFolder = CommonConstant.IS_ROOT_FOLDER;
			}
			folderRelation.setIsRootFolder(isRootFolder);
			folderRelationMapper.insertFolderRelation(folderRelation);
			createFolderResult = true;
		}catch(Exception e){
			logger.error("创建文件夹数据库操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		
		return createFolderResult;
	}
	
	@Transactional
	@Override
	public boolean collectObject(String loginUser, String loginUserId,
			List<String> collectObjectList) {
		boolean collectObjectResult = false;
		try{
			if(collectObjectList != null && collectObjectList.size() > 0){
				for(String fileObjId : collectObjectList){
					CollectInfo collectInfo = new CollectInfo();
					collectInfo.setUserid(loginUserId);
					collectInfo.setCollectobjectid(fileObjId);
					List<CollectInfo> collectInfoList = collectInfoMapper.queryCollectInfoInfo(collectInfo);
					//已经共享的文件对象不需要再次操作数据库
					if(collectInfoList != null && collectInfoList.size() > 0){
						continue;
					}					
					collectInfo.setOperator(loginUser);
					collectInfo.setCreatedate(DateUtils.getSystemTime());
					collectInfoMapper.insertCollectInfoInfo(collectInfo);
				}
			}
			collectObjectResult = true;
		}catch(Exception e){
			logger.error("收藏文件对象数据库操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		
		return collectObjectResult;
	}
	@Transactional
	@Override
	public List<String> copyOrmoveTo(String loginUserId, String parentFolderID, List<String> childrenFolderIdList, int controlType) {
		List<String> failedList = new ArrayList<String>();
		try{
			for(String childrenFolderID : childrenFolderIdList){
				FolderRelation folderRelation = new FolderRelation();
				folderRelation.setUserid(loginUserId);
				folderRelation.setParentfolderid(parentFolderID);
				folderRelation.setChildrenfolderid(childrenFolderID);
				List<FolderRelation> frList = folderRelationMapper.queryFolderRelation(folderRelation);
				//将文件对象复制移动过程中，如果复制移动对象目录下已经存在该文件对象，无法复制移动
				if(frList != null && frList.size() > 0){
					failedList.add(childrenFolderID);
				}else{
					String isRootFolder = CommonConstant.NOT_ROOT_FOLDER;
					if(parentFolderID == null || "".equals(parentFolderID)){
						isRootFolder = CommonConstant.IS_ROOT_FOLDER;
					}
					folderRelation.setIsRootFolder(isRootFolder);
					if(controlType == 0){ //复制文件对象
						folderRelationMapper.insertFolderRelation(folderRelation);	
					}else if(controlType == 1){ //移动文件对象
						folderRelationMapper.moveToFolderRelation(folderRelation);
					}
				}
			}			
		}catch(Exception e) {
			logger.error("复制移动文件对象时数据库操作发生异常：" + e.getMessage());
			failedList = new ArrayList<String>();
			throw new RuntimeException();
		}
		
		return failedList;
	}

	@Override
	public int shareFileFromMyFile(ShareInfo shareInfo) {
		// TODO Auto-generated method stub
		return shareInfoMapper.insertShareInfo(shareInfo);
	}

	@Override
	public List<QueryShareObjectInfo> queryShareObjectInfo(QueryShareObjectInfo queryShareObjectInfo) {
		return shareInfoMapper.queryShareObjectInfo(queryShareObjectInfo);
	}

	@Override
	public int renameFile(FileInfo fileInfo) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Transactional
	@Override
	public boolean fileUploader(MultipartFile multipartFile, String loginUser, String loginUserId, String comment, String parentFolderID) {
		boolean uploadResult = false;
		// 文件上传处理
		String[] uploadArr = super.uploadFile(multipartFile, loginUser);
		// 上传成功后数据库对应操作
		if (uploadArr != null && uploadArr.length > 1) {
			try{
				//文件信息表插入数据
				FileInfo fileInfo = new FileInfo();
				String fileUuid = CreatUuidUtil.getUuid();
				fileInfo.setFileid(fileUuid);
				fileInfo.setFilename(multipartFile.getOriginalFilename());
				fileInfo.setFilesize(String.valueOf(multipartFile.getSize()));
				fileInfo.setFiletype(FileUtils.getFileType(multipartFile));
				fileInfo.setFilestate(CommonConstant.fileState.normal.toString());
				fileInfo.setFileuploader(loginUser);
				fileInfo.setFileuploadertime(DateUtils.getSystemTime());
				fileInfo.setFilegroupname(uploadArr[0]);
				fileInfo.setFilepath(uploadArr[1]);
				fileInfo.setComment(comment);
				fileInfoMapper.insertFileInfo(fileInfo);
				//云盘信息剩余空间更新
				CloudDiskInfo cloudDiskInfo = new CloudDiskInfo();
				cloudDiskInfo.setUserid(loginUserId);
				List<CloudDiskInfo> cloudDiskInfoList = cloudDiskInfoMapper.queryCloudDiskInfo(cloudDiskInfo);
				if(cloudDiskInfoList != null && cloudDiskInfoList.size() > 0){
					long usedSize = Long.parseLong(cloudDiskInfoList.get(0).getUsedsize()) + multipartFile.getSize();
					cloudDiskInfo.setUsedsize(String.valueOf(usedSize));
					cloudDiskInfo.setUpdatedate(DateUtils.getSystemTime());
					cloudDiskInfoMapper.updateCloudDiskInfo(cloudDiskInfo);
				}
				//文件目录关系表插入数据
				FolderRelation folderRelation = new FolderRelation();
				folderRelation.setUserid(loginUserId);
				folderRelation.setParentfolderid(parentFolderID);
				folderRelation.setChildrenfolderid(fileUuid);
				String isRootFolder = CommonConstant.NOT_ROOT_FOLDER;
				if(parentFolderID == null || "".equals(parentFolderID)){
					isRootFolder = CommonConstant.IS_ROOT_FOLDER;
				}
				folderRelation.setIsRootFolder(isRootFolder);
				folderRelationMapper.insertFolderRelation(folderRelation);
				//操作完成设置返回成功
				uploadResult = true;
			}catch(Exception e) {
				//上传成功但是更新数据库失败情况删除上传文件
				super.delete(uploadArr[0], uploadArr[1]);
				logger.error("上传成功后数据库操作发生异常：" + e.getMessage());
				throw new RuntimeException();
			}
		}

		return uploadResult;
	}

	@Override
	public ResponseEntity<byte[]> fileDownloader(String loginUser, String loginUserId, FileInfo fileInfo) {	
		return super.downloadFile(fileInfo.getFilegroupname(), fileInfo.getFilepath(), fileInfo.getFilename());
	}

	@Override
	public String filePreview(String fileID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public int deleteFile(String fileID, RecycleInfo recycleInfo) {
		int result = 0;
		try {
			int flag = fileInfoMapper.deleteFile(fileID);
			int flag2 = folderInfoMapper.deleteFile(fileID);
			int flag3 = recycleInfoMapper.insertRecycleInfoInfo(recycleInfo);
			if (flag >= 0 && flag2 >= 0 && flag3 >= 0) {
				result = 1;
			}
		} catch (Exception e) {
			logger.error("删除文件发生异常：" + e.getMessage());
			throw new RuntimeException();
		}
		return result;
	}

	@Override
	public TreeFolderDataInfo queryFolderIds(String loginUserId, String parentFolderID, String selectFolderIds) {	
		TreeFolderDataInfo treeFolderDataInfo = new TreeFolderDataInfo();
		try {
			queryTreeFolderDataInfo(loginUserId, treeFolderDataInfo, selectFolderIds);
		} catch (Exception e) {
			logger.error("目标目录下拉框值获取发生异常：" + e.getMessage());
			treeFolderDataInfo = new TreeFolderDataInfo();
		}
		//增加全部文件为根目录
		TreeFolderDataInfo allFolderIds = new TreeFolderDataInfo();
		List<TreeFolderDataInfo> nodes = new ArrayList<TreeFolderDataInfo>();
		allFolderIds.setId("");
		allFolderIds.setNodes(treeFolderDataInfo.getNodes());
		allFolderIds.setTags("folder");
		allFolderIds.setText("全部文件");	
		nodes.add(allFolderIds);
		
		return allFolderIds;
	}
	
	/**
	 * 递归查询到目录结构
	 * @param loginUserId
	 * @param treeFolderDataInfo
	 * @param selectFolderIds
	 */
	private void queryTreeFolderDataInfo(String loginUserId, TreeFolderDataInfo treeFolderDataInfo, String selectFolderIds) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(treeFolderDataInfo.getNodes() == null || treeFolderDataInfo.getNodes().size() < 1){
			map.put("userID", loginUserId);
			map.put("parentFolderID", "");
			map.put("selectFolderIds", selectFolderIds);
			List<TreeFolderDataInfo> parentInfoList = folderInfoMapper.queryAllFolderInfoByKey(map);
			if(parentInfoList != null && parentInfoList.size() > 0){
				treeFolderDataInfo.setNodes(parentInfoList);
				queryTreeFolderDataInfo(loginUserId, treeFolderDataInfo, selectFolderIds);
			}
		}else{
			List<TreeFolderDataInfo> parentInfoList =  treeFolderDataInfo.getNodes();
			if(parentInfoList != null && parentInfoList.size() > 0){
				for (TreeFolderDataInfo tfd : parentInfoList) {
					map.put("userID", loginUserId);
					map.put("parentFolderID", tfd.getId());	
					map.put("selectFolderIds", selectFolderIds);
					List<TreeFolderDataInfo> nodeList =  folderInfoMapper.queryAllFolderInfoByKey(map);
					if(nodeList != null && nodeList.size() > 0){
						tfd.setNodes(nodeList);
						queryTreeFolderDataInfo(loginUserId, tfd, selectFolderIds);
					}
				}
			}
		}
	}
}
