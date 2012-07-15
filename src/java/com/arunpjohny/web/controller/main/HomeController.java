package com.arunpjohny.web.controller.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.arunpjohny.web.utils.RequestUtils;

@Controller
public class HomeController {

	@Autowired
	private RequestUtils requestUtils;

	@RequestMapping("/")
	public ModelAndView defaultpath(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		return requestUtils.getModelAndView(request, model,
				"WEB-INF/templates/main/base");
	}

	@RequestMapping("/home")
	public ModelAndView home(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		return requestUtils.getModelAndView(request, model,
				"WEB-INF/templates/main/home");
	}

}
