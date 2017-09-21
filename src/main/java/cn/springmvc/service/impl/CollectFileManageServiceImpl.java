package cn.springmvc.service.impl;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.CollectInfoMapper;
import cn.springmvc.model.CollectInfo;
import cn.springmvc.model.FileInfo;
import cn.springmvc.service.BaseFileService;
import cn.springmvc.service.CollectFileManageService;

@Service
public class CollectFileManageServiceImpl extends BaseFileService implements
		CollectFileManageService {
	private static Logger logger = LoggerFactory
			.getLogger(MyFileManageServiceImpl.class);
	@Autowired
	private CollectInfoMapper collectInfoMapper;

	@Override
	public List<FileInfo> queryCollectTableInfo(Map<String, Object> map)
			throws Exception {
		// TODO Auto-generated method stub
		return collectInfoMapper.queryCollectTableInfo(map);
	}

	@Transactional
	@Override
	public boolean cancelCollectFile(String loginUserId,
			List<String> collectObjectList) {
		// TODO Auto-generated method stub
		boolean cancleCollectResult = false;
		try {
			if (collectObjectList != null && collectObjectList.size() > 0) {
				for (String fileObjId : collectObjectList) {
					CollectInfo collectInfo = new CollectInfo();
					collectInfo.setUserid(loginUserId);
					collectInfo.setCollectobjectid(fileObjId);
					collectInfoMapper.deleteCollectInfoInfo(collectInfo);
				}
			}
			cancleCollectResult = true;
		} catch (Exception e) {
			logger.error("删除收藏文件对象数据库操作发生异常：" + e.getMessage());
			throw new RuntimeException();
		}

		return cancleCollectResult;
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

}
