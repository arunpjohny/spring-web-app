package com.arunpjohny.web.controller.main;

import static com.arunpjohny.web.utils.RequestUtils.AJAX_PAGE_MESSAGE;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.arunpjohny.service.MailManager;
import com.arunpjohny.web.controller.main.form.ContactUsMailForm;
import com.arunpjohny.web.utils.RequestUtils;

@Controller
public class ContactUsController {

	@Autowired
	private RequestUtils requestUtils;

	@Autowired
	private MailManager mailManager;

	@Value("${contactus.mail.to}")
	private String contactMailTo;

	@RequestMapping("/contactus")
	public ModelAndView contactus(HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		return requestUtils.getModelAndView(request, model,
				"WEB-INF/templates/main/contactus");
	}

	@RequestMapping(value = "/contactus/mail", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> contactusmail(HttpServletRequest request,
			@ModelAttribute @Valid ContactUsMailForm form) {

		StringBuffer body = new StringBuffer();
		body.append("Name: " + StringUtils.trimToEmpty(form.getName()) + "\n");
		body.append("Email: " + StringUtils.trimToEmpty(form.getFrom()) + "\n");
		body.append("Mobile: " + StringUtils.trimToEmpty(form.getMobile())
				+ "\n");
		body.append("\n");
		body.append(form.getBody());
		mailManager.sendMail(form.getFrom(), contactMailTo, form.getSubject(),
				body.toString());

		Map<String, Object> model = new HashMap<String, Object>();

		model.put(AJAX_PAGE_MESSAGE, "Mail send successfully");
		return model;
	}

}
