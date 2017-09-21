package cn.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.model.GroupInfo;
import cn.springmvc.model.GroupMemberInfoKey;

@Component
public interface GroupMemberInfoMapper {
	List<GroupInfo> queryByUserId(GroupMemberInfoKey key);
	
	List<CloudDiskUser> queryByGroupId(GroupMemberInfoKey key);
	
	List<CloudDiskUser> selectRestMemByGroupId(GroupMemberInfoKey key);
	
    int deleteByPrimaryKey(String groupid);
    
    int delete(GroupMemberInfoKey key);

    int insert(GroupMemberInfoKey record);

    int insertSelective(GroupMemberInfoKey record);
}