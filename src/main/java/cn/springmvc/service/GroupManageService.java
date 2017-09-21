package cn.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.model.GroupInfo;
import cn.springmvc.model.GroupMemberInfoKey;


/**
 * @author 胡志强
 * 2017年5月23日
 */
public interface GroupManageService {
	/**
     * 查看所有用户信息 
     * @param fileInfo
     * @return
     */
	
	List<CloudDiskUser> queryAllUser(CloudDiskUser cloudDiskUser);
	
    /**
     * 查看群组列表信息 
     * @param fileInfo
     * @return
     */
	
	List<GroupInfo> queryGroupListInfo(GroupMemberInfoKey groupMemberInfoKey);
	 
	/**
     * 查看群组所有成员信息 
     * @param fileInfo
     * @return
     */
	
	List<CloudDiskUser> queryGroupMemberInfo(GroupMemberInfoKey groupMemberInfoKey);
	
	/**
     * 创建群组 
     * @param fileInfo
     * @return
     */
	
	boolean createGroup(GroupMemberInfoKey groupMemberInfoKey,GroupInfo groupInfo,ArrayList<String> userId);
	
	/**
	  * 退出群组
	 * @param cloudDiskUser
	 * @return int
	 */
	boolean quitGroup(GroupMemberInfoKey groupMemberInfoKey,String userName);
	 
	/**
	 * 解散群组
	 * @param cloudDiskUser
	 * @return int
	 */
	boolean deleteGroup(ArrayList<String> groupId);
	 
	/**
	 * 修改群组名称
	 * @param groupInfo
	 * @return int
	 */
	int updateGroupName(GroupInfo groupInfo);
	 
	 /**
	  * 踢人出群组
	 * @param groupMemberInfoKey
	 * @return int
	 */
	boolean kickingGroupMember(String groupId,ArrayList<String> userId);
	 
	/**
	 * 拉人进群组
	 * @param groupMemberInfoKey
	 * @return int
	 */
	boolean pullingGroupMember(GroupMemberInfoKey groupMemberInfoKey,ArrayList<String> userId);
	
	/**
	 * 查询剩余群组成员
	 * @param groupMemberInfoKey
	 * @return int
	 */
	List<CloudDiskUser> queryRestGroupMember(GroupMemberInfoKey groupMemberInfoKey);	 
}
