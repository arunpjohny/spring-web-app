package com.arunpjohny.core.spring.security.web.authentication;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.AuthenticationException;

import com.arunpjohny.core.jackson.JacksonUtils;
import com.arunpjohny.web.utils.RequestUtils;
import com.arunpjohny.web.utils.RequestUtils.MessageType;

public class AuthenticationFailureHandler
		extends
		org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler {

	private JacksonUtils jacksonUtils;

	public void setJacksonUtils(JacksonUtils jacksonUtils) {
		this.jacksonUtils = jacksonUtils;
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		if (RequestUtils.isAjaxRequest(request)) {
			String reason = getReason(exception);
			response.setStatus(HttpStatus.BAD_REQUEST.value());

			Map<String, Object> model = new HashMap<String, Object>();
			RequestUtils.addAjaxMessage(model, MessageType.ERROR, reason);

			jacksonUtils.writeValue(model, response.getWriter());
			return;
		}

		super.onAuthenticationFailure(request, response, exception);
	}

	public String getReason(AuthenticationException exception) {
		String reason = "";
		if (exception instanceof BadCredentialsException
				|| exception instanceof ProviderNotFoundException) {
			reason = "Sorry, user name or password is incorrect.";
		} else if (exception instanceof DisabledException) {
			reason = "Your account is disabled.";
		} else if (exception instanceof AccountExpiredException) {
			reason = "Your account is expired.";
		} else {
			reason = "Sorry, user name or password is incorrect.";
		}
		return reason;
	}

}
