package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.ShareInfoMapper;
import cn.springmvc.model.FileInfo;
import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.service.BaseFileService;
import cn.springmvc.service.MySharedManageService;
@Service
public class MySharedManageServiceImpl extends BaseFileService implements
		MySharedManageService {
    
	@Autowired
	private ShareInfoMapper shareInfoMapper;
	
	@Override
	public int cancelSharedFile(ShareInfo shareInfo) {
		// TODO Auto-generated method stub
		return shareInfoMapper.deleteMySharedInfo(shareInfo);
	}

	@Override
	public String filePreview(FileInfo fileInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fileDownloader(FileInfo fileInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (实现接口方法)
	 * @see cn.springmvc.service.MySharedManageService#queryMySharedFileListInfo(cn.springmvc.model.ShareInfo)
	 */
	@Override
	public List<QueryMySharedFileInfo> queryMySharedFileListInfo(ShareInfo shareInfo) {
		// TODO Auto-generated method stub
		List<QueryMySharedFileInfo> queryMySharedFileInfos1=shareInfoMapper.queryMySharedFileByOperator(shareInfo);
		List<QueryMySharedFileInfo> queryMySharedFileInfos2=shareInfoMapper.queryMySharedFileByShareObjectID(shareInfo);
		List<QueryMySharedFileInfo> resultList=new ArrayList<QueryMySharedFileInfo>();
		if (queryMySharedFileInfos1.size()>0) {
			resultList.addAll(queryMySharedFileInfos1);
		}		
		if (queryMySharedFileInfos2.size()>0) {
			resultList.addAll(queryMySharedFileInfos2);
		}
		return resultList;
	}

	/* (实现接口方法)
	 * @see cn.springmvc.service.MySharedManageService#queryMySharedFileListInfoByFolderID(cn.springmvc.model.ShareInfo)
	 */
	@Override
	public List<QueryMySharedFileInfo> queryNextPageSharedFileInfoByFolderID(
			FolderInfo folderInfo,QueryMySharedFileInfo queryMySharedFileInfo) {
		// TODO Auto-generated method stub
		List<QueryMySharedFileInfo> resultList=new ArrayList<QueryMySharedFileInfo>();
		List<QueryMySharedFileInfo> queryMySharedFileInfos=shareInfoMapper.queryNextPageSharedFileInfoByFolderID(folderInfo);
		if (queryMySharedFileInfos.size()>0) {
			for(QueryMySharedFileInfo model : queryMySharedFileInfos){
				model.setOperator(queryMySharedFileInfo.getOperator());
				model.setShareObjectName(queryMySharedFileInfo.getShareObjectName());
				model.setCreatedate(queryMySharedFileInfo.getCreatedate());
				resultList.add(model);
			}
		}		
		return resultList;
	}

}
