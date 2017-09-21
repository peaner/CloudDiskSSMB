package cn.springmvc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.springmvc.dao.CloudDiskUserMapper;
import cn.springmvc.dao.GroupInfoMapper;
import cn.springmvc.dao.GroupMemberInfoMapper;
import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.model.GroupInfo;
import cn.springmvc.model.GroupMemberInfoKey;
import cn.springmvc.service.GroupManageService;
import cn.utils.CreatUuidUtil;
import cn.utils.DateUtils;
@Service
public class GroupManageServiceImpl implements GroupManageService {
	private static Logger logger = LoggerFactory
			.getLogger(RecycleFileManageServiceImpl.class);
	@Autowired
	private GroupMemberInfoMapper groupMemberInfoMapper;
	@Autowired
	private GroupInfoMapper groupInfoMapper;
	@Autowired
	private CloudDiskUserMapper cloudDiskUserMapper;
	
	@Override
	public List<CloudDiskUser> queryAllUser(CloudDiskUser cloudDiskUser){
		return cloudDiskUserMapper.queryCloudAllUser(cloudDiskUser);
	}
	
	@Override
	public List<GroupInfo> queryGroupListInfo(GroupMemberInfoKey groupMemberInfoKey) {
		return groupMemberInfoMapper.queryByUserId(groupMemberInfoKey);
	}

	@Override
	public List<CloudDiskUser> queryGroupMemberInfo(GroupMemberInfoKey groupMemberInfoKey){
		return groupMemberInfoMapper.queryByGroupId(groupMemberInfoKey);
	}
	
	@Transactional
	@Override
	public boolean createGroup(GroupMemberInfoKey groupMemberInfoKey,GroupInfo groupInfo,ArrayList<String> userId) {
		try{
			String groupID = CreatUuidUtil.getUuid();
			groupInfo.setGroupid(groupID);
			groupMemberInfoKey.setGroupid(groupID);
			groupInfo.setCreatedate(DateUtils.getSystemTime());
			for (int i=0; i<userId.size();i++){
				groupMemberInfoKey.setUserid(userId.get(i));
				groupMemberInfoMapper.insertSelective(groupMemberInfoKey);
			}
			groupInfoMapper.insertSelective(groupInfo);
			return true;
		}catch(Exception e){
			throw new RuntimeException();
		}
	}
	
	@Transactional
	@Override
	public boolean quitGroup(GroupMemberInfoKey groupMemberInfoKey,String userName) {
		// TODO Auto-generated method stub
		GroupInfo groupInfo = new GroupInfo();
		groupInfo= groupInfoMapper.selectByPrimaryKey(groupMemberInfoKey.getGroupid());
		if (groupInfo!=null){
			try {
				if (userName.equals(groupInfo.getOperator())){
					groupMemberInfoKey.setUserid(null);
					groupMemberInfoMapper.delete(groupMemberInfoKey);
					groupInfoMapper.deleteByPrimaryKey(groupMemberInfoKey.getGroupid());
				}else{
					groupMemberInfoMapper.delete(groupMemberInfoKey);
				}
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				throw new RuntimeException();
			}
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteGroup(ArrayList<String> groupId) {
		// TODO Auto-generated method stub
		try{
			for (int i=0; i<groupId.size();i++){
				groupMemberInfoMapper.deleteByPrimaryKey(groupId.get(i));
				groupInfoMapper.deleteByPrimaryKey(groupId.get(i));
			}
			return true;
		}catch(Exception e){
			logger.error("删除用户时发生异常:" + e.getMessage());
			throw new RuntimeException();
		}
		
	}

	@Override
	public int updateGroupName(GroupInfo groupInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public boolean kickingGroupMember(String groupId,ArrayList<String> userId) {
		// TODO Auto-generated method stub
		try{
			for (int i=0; i<userId.size();i++){
				groupMemberInfoMapper.deleteByPrimaryKey(groupId);
			}
			return true;
		}catch(Exception e){
			logger.error("删除用户时发生异常:" + e.getMessage());
			throw new RuntimeException();
		}
	}

	@Override
	public List<CloudDiskUser> queryRestGroupMember(GroupMemberInfoKey groupMemberInfoKey) {
		// TODO Auto-generated method stub
		try {
			return groupMemberInfoMapper.selectRestMemByGroupId(groupMemberInfoKey);
		}catch(Exception e){
			logger.error("查询用户时发生异常:" + e.getMessage());
			return null;
		}
	}

	@Transactional
	@Override
	public boolean pullingGroupMember(GroupMemberInfoKey groupMemberInfoKey,ArrayList<String> userId){
		// TODO Auto-generated method stub
		try{
			for (int i=0; i<userId.size();i++){
				groupMemberInfoKey.setUserid(userId.get(i));
				groupMemberInfoMapper.insertSelective(groupMemberInfoKey);
			}
			return true;
		}catch(Exception e){
			logger.error("增加用户时发生异常:" + e.getMessage());
			throw new RuntimeException();
		}
	}
}
