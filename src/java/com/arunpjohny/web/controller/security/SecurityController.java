package com.arunpjohny.web.controller.security;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.arunpjohny.web.utils.RequestUtils;

@Controller
public class SecurityController {

	@Autowired
	private RequestUtils requestUtils;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();

		boolean error = StringUtils.equals(request.getParameter("status"),
				"error");
		if (error) {
			Exception ex = (Exception) request.getSession().getAttribute(
					WebAttributes.AUTHENTICATION_EXCEPTION);
			if (ex != null) {
				if (AuthenticationException.class.isAssignableFrom(ex
						.getClass())) {
					model.put(RequestUtils.ERROR_MESSAGE, ex.getMessage());
				}
			}
		}
		return requestUtils.getModelAndView(request, model,
				"WEB-INF/templates/security/login");
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request) {
		return new ModelAndView(new RedirectView("/j_spring_security_logout",
				true));
	}
}
