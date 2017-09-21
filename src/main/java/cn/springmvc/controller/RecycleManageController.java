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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.springmvc.model.FileInfo;
import cn.springmvc.service.RecycleFileManageService;
import cn.utils.RequestUtils;

@Controller
@RequestMapping("/recycle")
public class RecycleManageController {
	private static Logger logger = LoggerFactory
			.getLogger(RecycleManageController.class);

	@Autowired
	private RecycleFileManageService recycleFileManageService;

	@ResponseBody
	@RequestMapping("/recycleTbInit")
	public Map<String, Object> getfileTableInfo(HttpServletRequest request)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileInfo> recycleInfoList = new ArrayList<FileInfo>();
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", loginUserId);
			recycleInfoList = recycleFileManageService
					.queryRecycleTableInfo(paramMap);
		} catch (Exception e) {
			logger.error("查询我的收藏表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", recycleInfoList.size());
		map.put("rows", recycleInfoList);
		return map;
	}

	@ResponseBody
	@RequestMapping("/restoreFile")
	public Map<String, Object> cancelMyCollection(HttpServletRequest request,
			@RequestParam(value = "rows", required = false) String rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			String loginObj = RequestUtils.getRequestKeyValue(request, "rows");
			JSONArray myJsonArray = JSONArray.fromObject(loginObj);
			List<String> restoreObjectIDList = new ArrayList<String>();
			if (myJsonArray != null && myJsonArray.size() > 0) {
				for (Object obj : myJsonArray) {
					JSONObject jsonObject = JSONObject.fromObject(obj);
					restoreObjectIDList.add(jsonObject.getString("fileid"));
				}
				result = recycleFileManageService.restoreFile(loginUserId,
						restoreObjectIDList);
			}
		} catch (Exception e) {
			logger.error("恢复数据异常：" + e.getMessage());
		}
		map.put("result", result);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/absolutelyDeleteFile")
	public Map<String, Object> absolutelyDeleteFile(HttpServletRequest request,
			@RequestParam(value = "fileID", required = true) String fileID) {
		// 定义返回前台对应集合
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("result", false);
		try {
			boolean result = recycleFileManageService.absolutelyDeleteFile(fileID);	
	     	resultMap.put("result", result);
		} catch (Exception e) {
			logger.error("彻底删除文件发生异常：" + e.getMessage());
		}		
		return resultMap;
	}
}
