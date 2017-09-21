package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.ShareInfoMapper;
import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.service.PublicSharedManageService;
@Service
public class PublicSharedManageServiceImpl implements PublicSharedManageService {
    
	@Autowired
	private ShareInfoMapper shareInfoMapper;
	@Override
	public List<QueryMySharedFileInfo> queryPublicSharedFileListInfo() {
		// TODO Auto-generated method stub
		return shareInfoMapper.queryPublicSharedFileByShareObjectID();
	}

	@Override
	public int cancelSharedFile(ShareInfo shareInfo) {
		// TODO Auto-generated method stub
		return shareInfoMapper.deleteMySharedInfo(shareInfo);
	}

	@Override
	public String fileDownloader(String fileID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String filePreview(String fileID) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (实现接口方法)
	 * @see cn.springmvc.service.PublicSharedManageService#queryMySharedFileListInfoByFolderID(cn.springmvc.model.FolderInfo, cn.springmvc.model.QueryMySharedFileInfo)
	 */
	@Override
	public List<QueryMySharedFileInfo> queryPublicSharedFileListInfoByFolderID(
			FolderInfo folderInfo, QueryMySharedFileInfo queryMySharedFileInfo) {
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

	/* (实现接口方法)
	 * @see cn.springmvc.service.PublicSharedManageService#checkIsSharedFile(java.lang.String)
	 */
	@Override
	public List<QueryMySharedFileInfo> checkIsSharedFile(String fileID) {
		// TODO Auto-generated method stub
		return shareInfoMapper.querySharedFileByFileID(fileID);
	}

}
