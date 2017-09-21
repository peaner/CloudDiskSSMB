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
import cn.springmvc.service.CollectFileManageService;
import cn.utils.RequestUtils;

@Controller
@RequestMapping("/collect")
public class CollectManageController {
	private static Logger logger = LoggerFactory
			.getLogger(CollectManageController.class);

	@Autowired
	private CollectFileManageService collectFileManageService;

	@ResponseBody
	@RequestMapping("/collectTbInit")
	public Map<String, Object> getfileTableInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileInfo> collectInfoList = new ArrayList<FileInfo>();
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("userId", loginUserId);
			collectInfoList = collectFileManageService
					.queryCollectTableInfo(paramMap);
		} catch (Exception e) {
			logger.error("查询我的收藏表格数据异常：" + e.getMessage());
		}
		// 设置bootstap table必须要用到的两个字段total和rows，否则表格无法显示数据
		map.put("total", collectInfoList.size());
		map.put("rows", collectInfoList);
		return map;
	}

	@ResponseBody
	@RequestMapping("/cancelMyCollection")
	public Map<String, Object> cancelMyCollection(HttpServletRequest request,
			@RequestParam(value = "rows", required = false) String rows) {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		try {
			String loginUserId = RequestUtils.getSessionKeyValue(request,
					"userId");
			String loginObj = RequestUtils.getRequestKeyValue(request, "rows");
			JSONArray myJsonArray = JSONArray.fromObject(loginObj);
			List<String> collectObjectList = new ArrayList<String>();
			if (myJsonArray != null && myJsonArray.size() > 0) {
				for (Object obj : myJsonArray) {
					JSONObject jsonObject = JSONObject.fromObject(obj);
					collectObjectList.add(jsonObject.getString("fileid"));
				}
				result = collectFileManageService.cancelCollectFile(
						loginUserId, collectObjectList);
			}
		} catch (Exception e) {
			logger.error("删除收藏数据异常：" + e.getMessage());
		}
		map.put("result", result);
		return map;
	}

}
