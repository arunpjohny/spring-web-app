package com.arunpjohny.core.spring.security.web.authentication;

import static com.arunpjohny.web.utils.RequestUtils.isAjaxRequest;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.arunpjohny.core.jackson.JacksonUtils;
import com.arunpjohny.web.utils.HttpHeader;

public class AuthenticationSuccessHandler extends
		SimpleUrlAuthenticationSuccessHandler {

	@SuppressWarnings("unused")
	private JacksonUtils jacksonUtils;

	public void setJacksonUtils(JacksonUtils jacksonUtils) {
		this.jacksonUtils = jacksonUtils;
	}

	protected String determineAjaxTargetUrl(HttpServletRequest request,
			HttpServletResponse response) {
		String targetUrl = null;

		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl()
				|| (targetUrlParameter != null && StringUtils.isBlank(request
						.getParameter(targetUrlParameter)))) {
			targetUrl = request.getContextPath()
					+ super.determineTargetUrl(request, response);
		}

		return StringUtils.isBlank(targetUrl) ? request.getContextPath()
				+ super.determineTargetUrl(request, response) : targetUrl;
	}

	protected void setAjaxResponse(HttpServletRequest request,
			HttpServletResponse response, String targetUrl)
			throws JsonGenerationException, JsonMappingException, IOException {
		clearAuthenticationAttributes(request);
		response.setStatus(OK.value());
		response.setHeader(HttpHeader.LOCATION.getName(), targetUrl);
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		if (isAjaxRequest(request)) {
			String targetUrl = determineAjaxTargetUrl(request, response);
			setAjaxResponse(request, response, targetUrl);
			return;
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

	protected void setResponse(HttpServletRequest request,
			HttpServletResponse response, String targetUrl) throws IOException {
		logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
