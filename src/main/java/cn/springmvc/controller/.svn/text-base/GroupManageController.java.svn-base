package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.CloudDiskUser;
import cn.springmvc.model.GroupInfo;
import cn.springmvc.model.GroupMemberInfoKey;
import cn.springmvc.service.GroupManageService;
import cn.utils.JSonUtils;
import cn.utils.RequestUtils;

/**
 * Servlet implementation class GroupManageController
 */

@Controller
@RequestMapping("/group")
public class GroupManageController{
	private static Logger logger = LoggerFactory
			.getLogger(RecycleManageController.class);
	@Autowired
	private GroupManageService groupManageService;
	
	@ResponseBody
	@RequestMapping("/queryAllMember")
	public Map<String, Object> queryAllMember (HttpServletRequest request) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CloudDiskUser cloudDiskUser = new CloudDiskUser();
		List<CloudDiskUser> list = new ArrayList<CloudDiskUser>();
		cloudDiskUser.setUsername(RequestUtils.getSessionKeyValue(request, "userName"));
		try {
			list = groupManageService.queryAllUser(cloudDiskUser);
		} catch (Exception e) {
			logger.error("查询所有用户时发生异常:" + e.getMessage());
			return resultMap;
		}
		if (list.size()>0){
			resultMap.put("result", "数据库查询成功!");
			resultMap.put("rows", list);
		}else {
			resultMap.put("result", "");
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/createGroup")
	public Map<String, Object> createGroup(HttpServletRequest request) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String groupObj = RequestUtils.getRequestKeyValue(request, "groupJson");
        //JSON字符串序列化成JSON对象
        JSONObject groupJson = JSonUtils.objectToJson(groupObj);
		GroupInfo groupInfo = new GroupInfo();
		GroupMemberInfoKey groupMemberInfoKey=new GroupMemberInfoKey();
		groupInfo.setGroupname(groupJson.get("groupName").toString());
		groupInfo.setOperator((String)RequestUtils.getSessionKeyValue(request,"userName"));
		ArrayList<String> userId = new ArrayList<String> ();
		for (int i=0;i<groupJson.getJSONArray("userID").size();i++){
			userId.add(groupJson.getJSONArray("userID").get(i).toString());
		}
		userId.add((String)RequestUtils.getSessionKeyValue(request,"userId"));
		
		boolean create = groupManageService.createGroup(groupMemberInfoKey, groupInfo, userId);
		resultMap.put("result", create);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/queryGroup")
	public Map<String, Object> queryGroup(HttpServletRequest request) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		GroupMemberInfoKey groupMemberInfoKey=new GroupMemberInfoKey();
		String userId = (String)RequestUtils.getSessionKeyValue(request,"userId");
		groupMemberInfoKey.setUserid(userId);
		List<GroupInfo> list = new ArrayList<GroupInfo>();
		try {
			list = groupManageService.queryGroupListInfo(groupMemberInfoKey);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询群组列表时发生异常:" + e.getMessage());
			resultMap.put("result", "数据库查询失败!");
			return resultMap;
		}
		if (list.size()>0) {
			resultMap.put("result", "查询成功！");
			resultMap.put("total", list.size());
			resultMap.put("rows", list);
		}else {
			resultMap.put("result", "");
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/queryGroupAllMember")
	public Map<String, Object> queryGroupAllMember(HttpServletRequest request,
			@RequestParam(value="groupId", required=false) String groupid) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		GroupMemberInfoKey groupMemberInfoKey = new GroupMemberInfoKey();
		groupMemberInfoKey.setGroupid(groupid);
		groupMemberInfoKey.setUserid(RequestUtils.getSessionKeyValue(request, "userId"));
		List<CloudDiskUser> list = new ArrayList<CloudDiskUser>();
		try{
			list = groupManageService.queryGroupMemberInfo(groupMemberInfoKey);
			resultMap.put("result", "查询成功！");
			resultMap.put("rows", list);
		}catch(Exception e){
			logger.error("查询群组所有用户时发生异常:" + e.getMessage());
			return resultMap;
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/kickGroupMember")
	public Map<String, Object> kickGroupMember(HttpServletRequest request) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String groupObj = RequestUtils.getRequestKeyValue(request, "groupJson");
        //JSON字符串序列化成JSON对象
        JSONObject groupJson = JSonUtils.objectToJson(groupObj);
		ArrayList<String> userId = new ArrayList<String> ();
		for (int i=0;i<groupJson.getJSONArray("userID").size();i++){
			userId.add(groupJson.getJSONArray("userID").get(i).toString());
		}
		
		boolean create = groupManageService.kickingGroupMember(groupJson.get("groupID").toString(), userId);
		resultMap.put("result", create);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/getPresentUserName")
	public Map<String, Object> getPresentUserName(HttpServletRequest request) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userName = RequestUtils.getSessionKeyValue(request, "userName");
		if (!userName.equals("") && userName !=null){
			resultMap.put("result", "用户名查询成功!");
			resultMap.put("rows", userName);
		}else{
			resultMap.put("result", "");
			resultMap.put("rows", userName);
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/quitOrDissolveGroup")
	public Map<String, Object> quitOrDissolveGroup(HttpServletRequest request,
			@RequestParam(value="groupID",required = false) String groupID){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		GroupMemberInfoKey groupMemberInfoKey = new GroupMemberInfoKey();
		groupMemberInfoKey.setGroupid(groupID);
		groupMemberInfoKey.setUserid(RequestUtils.getSessionKeyValue(request, "userId"));
		
		boolean result = groupManageService.quitGroup(groupMemberInfoKey,RequestUtils.getSessionKeyValue(request, "userName"));
		
		resultMap.put("result", result);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/queryRestGroupMember")
	public Map<String, Object> queryRestGroupMember(HttpServletRequest request,
			@RequestParam(value="groupID",required = false) String groupID){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		GroupMemberInfoKey groupMemberInfoKey = new GroupMemberInfoKey();
		groupMemberInfoKey.setGroupid(groupID);
		List<CloudDiskUser> list = new ArrayList<CloudDiskUser>();
		try{
			list = groupManageService.queryRestGroupMember(groupMemberInfoKey);
			if (list != null){
				resultMap.put("result", "成功查询到剩余成员");
				resultMap.put("rows", list);
			}else{
				resultMap.put("result", "");
			}
		}catch(Exception e){
			logger.error("查询用户时发生异常:" + e.getMessage());
			resultMap.put("result", "");
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/addGroupMember")
	public Map<String, Object> addGroupMember(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String groupObj = RequestUtils.getRequestKeyValue(request, "groupJson");
        //JSON字符串序列化成JSON对象
        JSONObject groupJson = JSonUtils.objectToJson(groupObj);
        GroupMemberInfoKey groupMemberInfoKey = new GroupMemberInfoKey();
        groupMemberInfoKey.setGroupid(groupJson.get("groupID").toString());
		ArrayList<String> userId = new ArrayList<String> ();
		for (int i=0;i<groupJson.getJSONArray("userID").size();i++){
			userId.add(groupJson.getJSONArray("userID").get(i).toString());
		}
		
		boolean add = groupManageService.pullingGroupMember(groupMemberInfoKey, userId);
		resultMap.put("result", add);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/deleteGroup")
	public Map<String, Object> deleteGroup(HttpServletRequest request){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String groupObj = RequestUtils.getRequestKeyValue(request, "groupID");
		//JSON字符串序列化成JSON对象
        JSONObject groupJson = JSonUtils.objectToJson(groupObj);
        
        ArrayList<String> groupId = new ArrayList<String> ();
		for (int i=0;i<groupJson.getJSONArray("groupID").size();i++){
			groupId.add(groupJson.getJSONArray("groupID").get(i).toString());
		}
		
		boolean delete = groupManageService.deleteGroup(groupId);
		resultMap.put("result", delete);
		return resultMap;
	}
}
