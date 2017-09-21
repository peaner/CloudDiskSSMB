package cn.springmvc.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 初始化处理
 * @author hzq
 * 
 */
@Controller
@RequestMapping("/init")
public class MainPageInitController {

	/**
	 * 跳转主界面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/main")
	public ModelAndView login(HttpServletRequest request, ModelAndView model) {
		model.setViewName("main");
		return model;

	}
}
