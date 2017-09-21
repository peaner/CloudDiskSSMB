package cn.springmvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.common.fastdfs.FileManagerConfig;
import cn.springmvc.model.CloudDiskInfo;
import cn.springmvc.model.FileInfo;
import cn.springmvc.model.QueryShareObjectInfo;
import cn.springmvc.model.RecycleInfo;
import cn.springmvc.model.ShareInfo;
import cn.springmvc.model.TreeFolderDataInfo;
import cn.springmvc.service.CloudDiskCapacityManageService;
import cn.springmvc.service.MyFileManageService;
import cn.utils.DateUtils;
import cn.utils.JSonUtils;
import cn.utils.RequestUtils;

@Controller
@RequestMapping("/file")
public class MyFileManageController implements FileManagerConfig {
	private static final long serialVersionUID = 2019847978925175089L;
	private static Logger logger = LoggerFactory.getLogger(MyFileManageController.class);
	@Autowired
	private MyFileManageService myFileManageService;
	@Autowired
	private CloudDiskCapacityManageService cloudDiskCapacityManageService;

	/**
	 * 上传到服务器
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("upload")
	public Map<String, Object> upload(
			HttpServletRequest request,
			@RequestParam("fileHandler") MultipartFile multipartFile,
			@RequestParam(value = "parentFolderID", required = false) String parentFolderID,
			@RequestParam(value = "comment", required = false) String comment)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String loginUser = RequestUtils.getSessionKeyValue(request, "userName");
		String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
		boolean upload = myFileManageService.fileUploader(multipartFile, loginUser, loginUserId, comment, parentFolderID);
		map.put("result", upload);
		return map;
	}

	/**
	 * 下载文件操作
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("download")
	public ResponseEntity<byte[]> download(HttpServletRequest request)
			throws Exception {
		String loginUser = RequestUtils.getSessionKeyValue(request, "userName");
		String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
		ResponseEntity<byte[]> content = null;
		try {
			FileInfo fileInfo = new FileInfo();
			fileInfo.setFilegroupname(RequestUtils.getRequestKeyValue(request, "filegroupName"));
			fileInfo.setFilepath(RequestUtils.getRequestKeyValue(request, "filePath"));
			fileInfo.setFilename(RequestUtils.getRequestKeyValue(request, "fileName"));
			content = myFileManageService.fileDownloader(loginUser, loginUserId, fileInfo);
		} catch (Exception e) {
			logger.error("下载文件操作异常：" + e.getMessage());
		}

		return content;
	}

	/**
	 * 云盘剩余大小验证
	 * 
	 * @param request
	 * @param fileSize
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/diskSizeCheck")
	public Map<String, Object> diskSizeCheck(HttpServletRequest request,
			@RequestParam(value = "fileSize", required = true) String fileSize)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
			CloudDiskInfo cloudDiskInfo = new CloudDiskInfo();
			cloudDiskInfo.setUserid(loginUserId);
			List<CloudDiskInfo> cloudDiskInfoList = cloudDiskCapacityManageService.queryClouddiskCapacityInfo(cloudDiskInfo);
			// 云盘剩余大小检查
			if (cloudDiskInfoList != null && cloudDiskInfoList.size() > 0) {
				long totalSize = Long.parseLong(cloudDiskInfoList.get(0).getTotalsize());
				long usedSize = Long.parseLong(cloudDiskInfoList.get(0).getUsedsize());
				if (totalSize >= usedSize + Long.parseLong(fileSize)) {
					map.put("result", true);
				}
			}
		} catch (Exception e) {
			logger.error("云盘剩余大小检查异常：" + e.getMessage());
		}

		return map;
	}

	/**
	 * 我的文件表格数据获取
	 * @param request
	 * @param folderId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/myfileTbInit")
	public Map<String, Object> getfileTableInfo(HttpServletRequest request,
			@RequestParam(value = "fileObjId", required = true) String fileObjId)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", loginUserId);
			getTableSortInfo(request, paramMap);
			if(fileObjId == null || "".equals(fileObjId)){
				fileInfoList = myFileManageService.queryMyFileListInfo(paramMap);
			}else{
				paramMap.put("fileObjId", fileObjId);
				fileInfoList = myFileManageService.queryFolderContainInfo(paramMap);
			}			
		} catch (Exception e) {
			logger.error("查询我的文件表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", fileInfoList.size());
		map.put("rows", fileInfoList);
		return map;
	}
	
	/**
	 * 组合排序的信息 默认时间降序
	 * @param request
	 * @return
	 */
	private void getTableSortInfo(HttpServletRequest request, Map<String, Object> paramMap){
		//排序列名
		String sortName = RequestUtils.getRequestKeyValue(request, "sort");
		//排序顺序
		String sortOrder = RequestUtils.getRequestKeyValue(request, "order");
		if(sortName != null && !"".equals(sortName)
				&& sortOrder != null && !"".equals(sortOrder)){	
			paramMap.put("sort", sortName);
			paramMap.put("order", sortOrder);
		}
	}
	
	
	/**
	 * 搜索框输入查询功能
	 * @param request
	 * @param newFolderName
	 * @param currentFolderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/querySearchInfo")
	public Map<String, Object> querySearchInfo(HttpServletRequest request,
			@RequestParam(value = "fileObjId", required = true) String fileObjId,
			@RequestParam(value = "search", required = true) String search) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", loginUserId);
			paramMap.put("search", RequestUtils.formatBootstrapTableEncoding(search));
			fileInfoList = myFileManageService.querySearchInfo(paramMap);
		} catch (Exception e) {
			logger.error("查询我的文件表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", fileInfoList.size());
		map.put("rows", fileInfoList);
		return map;
	}

	/**
	 * 查询共享对象列表信息
	 * 
	 * @param request
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("/queryShareObjectList")
	public Map<String, Object> queryShareObjectList(HttpServletRequest request) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		QueryShareObjectInfo queryShareObjectInfo = new QueryShareObjectInfo();
		List<QueryShareObjectInfo> queryShareObjectInfos = new ArrayList<QueryShareObjectInfo>();
		try {
			// 获取用户ID
			String userId = RequestUtils.getSessionKeyValue(request, "userId");			
			queryShareObjectInfo.setShareObjectID(userId);			
			resultMap.put("result", "");
			queryShareObjectInfos = myFileManageService.queryShareObjectInfo(queryShareObjectInfo);
		} catch (Exception e) {
			logger.error("查询共享对象列表信息失败：" + e.getMessage());
		}
		if (queryShareObjectInfos.size() > 0) {
			resultMap.put("result", queryShareObjectInfos);
		}

		return resultMap;
	}
	
	/**
	 * 共享文件处理
	 * @param request
	 * @param fileID
	 * @param shareObjectID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/shareFile")
	public Map<String, Object> shareFile(
			HttpServletRequest request) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String shareObjectInfo= RequestUtils.getRequestKeyValue(request, "shareObjectJson");
        //JSON字符串序列化成JSON对象
        JSONObject shareObjectJson = JSonUtils.objectToJson(shareObjectInfo);
        String shareObjectIDs=shareObjectJson.getString("shareObjectIDs");
        JSONArray jsonArray=JSONArray.fromObject(shareObjectIDs);
        Object[] objectIds=jsonArray.toArray();
        String fileIDs=shareObjectJson.getString("fileIDs");
        JSONArray jsonArray2=JSONArray.fromObject(fileIDs);
        Object[] fileIds=jsonArray2.toArray();
        // 获取用户名
     	String operator = RequestUtils.getSessionKeyValue(request, "userName");
     	List<String> success=new ArrayList<String>();
     	List<String> failue=new ArrayList<String>();
		try {			
			ShareInfo shareInfo = new ShareInfo();
			for (int i = 0; i < fileIds.length; i++) {
				for (int j = 0; j < objectIds.length; j++) {
					shareInfo.setFileid(JSONObject.fromObject(fileIds[i]).getString("fileID"));
					shareInfo.setShareobjectid(JSONObject.fromObject(objectIds[j]).getString("shareObjectID"));
					shareInfo.setOperator(operator);
					shareInfo.setCreatedate(DateUtils.getSystemTime());
					int result = myFileManageService.shareFileFromMyFile(shareInfo);
					if (result>0) {
						//记录共享成功的情况
						String message="文件："+JSONObject.fromObject(fileIds[i]).getString("fileName")+"共享给"+JSONObject.fromObject(objectIds[j]).getString("shareObjectName")+" ";
						success.add(message);
						continue;
					}else {
						//记录共享失败的情况
						String message="文件："+JSONObject.fromObject(fileIds[i]).getString("fileName")+"共享给"+JSONObject.fromObject(objectIds[j]).getString("shareObjectName")+" ";
						failue.add(message);
					}
				}
			}
			//共享过程没有发生异常中断，只是有的共享失败，提示哪些文件共享失败
			resultMap.put("failue", failue);
			resultMap.put("isException", false);
		} catch (Exception e) {
			logger.error("共享文件出现异常错误：" + e.getMessage());
			//给前端提示只有哪些文件共享成功，共享过程中发生了中断。
			resultMap.put("isException", true);
			resultMap.put("success", success);
		}		
		return resultMap;
	}
	
	/**
	 * 创建新文件夹处理
	 * @param request
	 * @param newFolderName
	 * @param currentFolderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/createFolder")
	public Map<String, Object> createFolder(HttpServletRequest request,
			@RequestParam(value = "newFolderName", required = true) String newFolderName,
			@RequestParam(value = "currentFolderId", required = false) String currentFolderId) {
		//定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();		
		boolean result = false;
		try {
			String userId = RequestUtils.getSessionKeyValue(request, "userId");
			String userName = RequestUtils.getSessionKeyValue(request, "userName");
			result = myFileManageService.createFolder(userName, userId, newFolderName, currentFolderId);			
		} catch (Exception e) {
			logger.error("创建文件夹发生异常：" + e.getMessage());			
		}
		resultMap.put("result", result);
		return resultMap;
	}
	
	/**
	 * 收藏文件对象
	 * @param request
	 * @param rows
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/collectObject")
	public Map<String, Object> collectObject(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
			String loginUser = RequestUtils.getSessionKeyValue(request, "userName");
			String rows = RequestUtils.getRequestKeyValue(request, "rows");
			JSONArray myJsonArray = JSONArray.fromObject(rows);
			List<String> collectObjectList = new ArrayList<String>();
			if (myJsonArray != null && myJsonArray.size() > 0) {
				for (Object obj : myJsonArray) {
					JSONObject jsonObject = JSONObject.fromObject(obj);
					if(jsonObject.containsKey("fileid")){
						collectObjectList.add(jsonObject.getString("fileid"));
					}
				}
				result = myFileManageService.collectObject(loginUser, loginUserId, collectObjectList);
			}
		} catch (Exception e) {
			logger.error("查询我的文件表格数据异常：" + e.getMessage());
		}
		map.put("result", result);
		return map;
	}
	
	/**
	 * 复制移动文件对象
	 * @param request
	 * @param copyToUuid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/copyOrMoveTo")
	public Map<String, Object> copyOrMoveTo(HttpServletRequest request,
			@RequestParam(value = "parentFolderID", required = true) String parentFolderID,
			@RequestParam(value = "controlType", required = true) String controlType) {
		Map<String, Object> map = new HashMap<String, Object>();		
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
			String rows = RequestUtils.getRequestKeyValue(request, "rows");
			JSONArray myJsonArray = JSONArray.fromObject(rows);
			List<String> childrenFolderIdList = new ArrayList<String>();
			if (myJsonArray != null && myJsonArray.size() > 0) {
				for (Object obj : myJsonArray) {
					JSONObject jsonObject = JSONObject.fromObject(obj);
					if(jsonObject.containsKey("fileid")){
						childrenFolderIdList.add(jsonObject.getString("fileid"));
					}					
				}				
				//进行复制移动操作
				List<String> failedList = new ArrayList<String>();
				if("copy".equals(controlType)){
					failedList = myFileManageService.copyOrmoveTo(loginUserId, parentFolderID, childrenFolderIdList, 0);
				}else if("move".equals(controlType)){
					failedList = myFileManageService.copyOrmoveTo(loginUserId, parentFolderID, childrenFolderIdList, 1);
				}			
				//操作结果处理(如果有失败的，界面提示失败文件已经存在)
				if(failedList.size() > 0){
					List<String> failedNamesList = new ArrayList<String>();
					for (Object obj : myJsonArray) {
						JSONObject jsonObject = JSONObject.fromObject(obj);
						if(jsonObject.containsKey("fileid") 
								&& failedList.contains(jsonObject.getString("fileid"))){
							failedNamesList.add(jsonObject.getString("filename"));
						}					
					}	
					map.put("failed", failedNamesList);
				}
			}						
		} catch (Exception e) {
			logger.error("复制移动文件对象发生异常：" + e.getMessage());
		}
		
		return map;
	}
	
	/**
	 * 目标目录下拉框值
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getFolderIds")
	public List<TreeFolderDataInfo> getFolderIds(HttpServletRequest request,
			@RequestParam(value = "parentFolderID", required = false) String parentFolderID) {
		String loginUserId = RequestUtils.getSessionKeyValue(request, "userId");
		List<TreeFolderDataInfo> modelList = new ArrayList<TreeFolderDataInfo>();
		try {
			if (loginUserId != null) {
				String rows = RequestUtils.getRequestKeyValue(request, "rows");
				JSONArray myJsonArray = JSONArray.fromObject(rows);
				String selectFolderIds = "";
				//获取选中的文件对象ID集合，查询的时候不需要该文件集合
				if (myJsonArray != null && myJsonArray.size() > 0) {
					for (Object obj : myJsonArray) {
						JSONObject jsonObject = JSONObject.fromObject(obj);
						if(jsonObject.containsKey("fileid")){
							selectFolderIds = selectFolderIds + jsonObject.getString("fileid") + ",";
						}					
					}
				}
				//获取对应的目录数据
				TreeFolderDataInfo treeFolderDataInfo = myFileManageService.queryFolderIds(loginUserId, parentFolderID, selectFolderIds);
				if(treeFolderDataInfo != null && treeFolderDataInfo.getNodes() != null && treeFolderDataInfo.getNodes().size() > 0){
					modelList.add(treeFolderDataInfo);
				}
			}
		} catch (Exception e) {
			logger.error("目标目录下拉框值获取发生异常：" + e.getMessage());
		}
		return modelList;
	}
	
	@ResponseBody
	@RequestMapping("/deleteFile")
	public Map<String, Object> deleteFile(HttpServletRequest request,
			@RequestParam(value = "fileID", required = true) String fileID) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", false);
		try {
			String userID = RequestUtils.getSessionKeyValue(request, "userId");
			String userName = RequestUtils.getSessionKeyValue(request, "userName");
			RecycleInfo recycleInfo = new RecycleInfo();
			recycleInfo.setUserid(userID);
			recycleInfo.setRecycleobjectid(fileID);
			recycleInfo.setOperator(userName);
			recycleInfo.setCreatedate(DateUtils.getSystemTime());
			int result = myFileManageService.deleteFile(fileID, recycleInfo);
			if (result == 1) {
				resultMap.put("result", true);
			}
		} catch (Exception e) {
			logger.error("删除文件发生异常：" + e.getMessage());
		}
		
		return resultMap;
	}
}