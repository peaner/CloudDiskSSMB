package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.FolderInfo;
import cn.springmvc.model.QueryMySharedFileInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.service.PublicSharedManageService;
import cn.utils.JSonUtils;
import cn.utils.RequestUtils;

@Controller
@RequestMapping("/publicShared")
public class PublicSharedManageController {
	@Autowired
	private PublicSharedManageService publicSharedManageService;
	/**
	 * 点击公共共享查询公共共享文件列表
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/queryList")
	public Map<String, Object> queryList(HttpServletRequest request,HttpServletResponse response) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		//调用服务层的查询我的共享信息列表
		List<QueryMySharedFileInfo> querySharedFileTableInfos=new ArrayList<QueryMySharedFileInfo>();
		try {
			querySharedFileTableInfos=publicSharedManageService.queryPublicSharedFileListInfo();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("查询失败！");
			resultMap.put("result", "");
			return resultMap;
		}		
		if (querySharedFileTableInfos.size()>0) {
			resultMap.put("result", "查询成功！");
			resultMap.put("total", querySharedFileTableInfos.size());
			resultMap.put("rows", querySharedFileTableInfos);
		}else {
			resultMap.put("result", "");
		}
		return resultMap;
	}
	
	/**
	 * 选中共享文件列表中的某个目录，双击打开目录，显示其目录下的文件列表信息
	 * @param request
	 * @param folderID
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("/queryNextPageSharedFileInfoByFolderID")
	public Map<String, Object> queryNextPageSharedFileInfoByFolderID(HttpServletRequest request,
			@RequestParam(value="fileid", required=true) String fileid) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		//获取form表单数据
        String mySharedFileInfo = RequestUtils.getRequestKeyValue(request, "mySharedFileInfo");
        //JSON字符串序列化成JSON对象
        JSONObject mySharedFileInfoJson = JSonUtils.objectToJson(mySharedFileInfo);
        QueryMySharedFileInfo queryMySharedFileInfo=new QueryMySharedFileInfo();
        queryMySharedFileInfo.setOperator(RequestUtils.formatBootstrapTableEncoding(mySharedFileInfoJson.getString("operator")));
        queryMySharedFileInfo.setShareObjectName(RequestUtils.formatBootstrapTableEncoding(mySharedFileInfoJson.getString("shareObjectName")));
        queryMySharedFileInfo.setCreatedate(mySharedFileInfoJson.getString("createdate"));
		FolderInfo folderInfo=new FolderInfo();
		folderInfo.setFolderid(fileid);
		//根据选中的文件目录查询当前目录下的子文件列表信息
		List<QueryMySharedFileInfo> queryMySharedFileInfos=new ArrayList<QueryMySharedFileInfo>();
		try {
			queryMySharedFileInfos=publicSharedManageService.queryPublicSharedFileListInfoByFolderID(folderInfo,queryMySharedFileInfo);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("查询失败！");
			resultMap.put("result", "");
			return resultMap;
		}
		if (queryMySharedFileInfos.size()>0) {
			resultMap.put("result", "查询成功！");
			resultMap.put("total", queryMySharedFileInfos.size());
			resultMap.put("rows", queryMySharedFileInfos);
		}else {
			resultMap.put("result", "");
		}
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/cancelPublicShared")
	public Map<String, Object> cancelMyShared(HttpServletRequest request) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		//获取form表单数据
        String mySharedFileInfo = RequestUtils.getRequestKeyValue(request, "mySharedFileInfo");
        //获取用户名
        String userName = RequestUtils.getSessionKeyValue(request, "userName");
        //JSON字符串序列化成JSON对象
        JSONObject mySharedFileInfoJson = JSonUtils.objectToJson(mySharedFileInfo);
        ShareInfo shareInfo=new ShareInfo();
        shareInfo.setFileid(mySharedFileInfoJson.getString("fileID"));
        shareInfo.setOperator(mySharedFileInfoJson.getString("operator"));
        shareInfo.setShareobjectid(mySharedFileInfoJson.getString("shareObjectID"));
        shareInfo.setCreatedate(mySharedFileInfoJson.getString("createDate"));
        resultMap.put("isShareObject", true);
		try {
			if (userName.equals(shareInfo.getOperator())) {
				int result=publicSharedManageService.cancelSharedFile(shareInfo);
				resultMap.put("result", result);
			}else {
				resultMap.put("isShareObject", false);
			}			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("取消共享失败！");
			resultMap.put("result", "");
			return resultMap;
		}		
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("/checkIsSharedFile")
	public Map<String, Object> checkIsSharedFile(HttpServletRequest request,
			@RequestParam(value="fileid", required=true) String fileid,
			HttpServletResponse response) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		//调用服务层的查询我的共享信息列表
		List<QueryMySharedFileInfo> querySharedFileTableInfos=new ArrayList<QueryMySharedFileInfo>();
		try {
			querySharedFileTableInfos=publicSharedManageService.checkIsSharedFile(fileid);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("查询失败！");
			resultMap.put("result", "");
			return resultMap;
		}		
		if (querySharedFileTableInfos.size()>0) {
			resultMap.put("result", "查询成功！");
			resultMap.put("total", querySharedFileTableInfos.size());
			resultMap.put("rows", querySharedFileTableInfos);
		}else {
			resultMap.put("result", "");
		}
		return resultMap;
	}
}