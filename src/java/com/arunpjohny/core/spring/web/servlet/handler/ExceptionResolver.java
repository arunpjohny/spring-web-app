package com.arunpjohny.core.spring.web.servlet.handler;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.arunpjohny.core.exception.ExceptionMetadata;
import com.arunpjohny.core.exception.AppException;
import com.arunpjohny.core.exception.conversion.DefaultExceptionConverter;
import com.arunpjohny.core.spring.web.servlet.view.JSONExceptionView;
import com.arunpjohny.web.utils.RequestUtils;

public class ExceptionResolver extends SimpleMappingExceptionResolver {
	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private RequestUtils requestUtils;

	private Map<Class<?>, ExceptionMetadata> metadataCache = new HashMap<Class<?>, ExceptionMetadata>();

	private Converter<Exception, Map<String, Object>> defaultConverter = new DefaultExceptionConverter();

	private Collection<ExceptionMetadata> metadataDefinitions;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		Collection values = new ArrayList(applicationContext.getBeansOfType(
				ExceptionMetadata.class).values());
		this.metadataDefinitions = values;
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String viewName = determineViewName(ex, request);
		if (viewName != null) {
			// Apply HTTP status code for error views, if specified.
			// Only apply it if we're processing a top-level request.
			Integer statusCode = determineStatusCode(request, ex, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			return getModelAndView(viewName, ex, request);
		} else {
			return null;
		}
	}

	private Integer determineStatusCode(HttpServletRequest request,
			Exception ex, String viewName) {
		if (RequestUtils.isAjaxRequest(request)) {
			ExceptionMetadata metadata = getMetadata(ex.getClass());
			if (metadata == null || metadata.getStatus() == null) {
				return HttpStatus.INTERNAL_SERVER_ERROR.value();
			} else {
				return metadata.getStatus().value();
			}
		}
		return super.determineStatusCode(request, viewName);
	}

	@Override
	protected ModelAndView getModelAndView(String viewName, Exception ex,
			HttpServletRequest request) {
		log.error(ex.getMessage(), ex);

		Map<String, Object> model = new HashMap<String, Object>();

		if (RequestUtils.isAjaxRequest(request)) {
			return new ModelAndView(new JSONExceptionView(getAjaxModel(ex)));
		}

		requestUtils.putContents(request, model);

		if (AppException.class.isAssignableFrom(ex.getClass())) {
			model.put(RequestUtils.ERROR_MESSAGE, ex.getMessage());
		}
		model.put(RequestUtils.DISABLE_AJAX_LOADING, true);
		return new ModelAndView(viewName, model);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> getAjaxModel(Exception ex) {
		Map model = new HashMap();
		Map convert = (Map) getMetadata(ex.getClass()).getConverter().convert(
				ex);
		model.putAll(convert);
		return model;
	}

	@SuppressWarnings("rawtypes")
	private ExceptionMetadata getMetadata(Class<?> clazz) {
		ExceptionMetadata metadata = (ExceptionMetadata) metadataCache
				.get(clazz);
		if (metadata == null) {
			if (clazz
					.isAnnotationPresent(com.arunpjohny.core.exception.annotation.ExceptionMetadata.class)) {
				try {
					com.arunpjohny.core.exception.annotation.ExceptionMetadata annotation = clazz
							.getAnnotation(com.arunpjohny.core.exception.annotation.ExceptionMetadata.class);
					Class<? extends Converter> converterClazz = annotation
							.converter();
					Converter newInstance = converterClazz.newInstance();
					metadata = new ExceptionMetadata(clazz,
							annotation.status(), newInstance);
				} catch (Exception e) {
					logger.error(e.toString(), e);
				}
			} else {
				for (ExceptionMetadata m : metadataDefinitions) {
					if (m.supports(clazz) && m.getConverter() != null
							&& m.getStatus() != null) {
						metadata = m;
						break;
					}
				}
			}

			if (metadata == null && Exception.class.equals(clazz)) {
				metadata = new ExceptionMetadata(clazz,
						HttpStatus.INTERNAL_SERVER_ERROR, defaultConverter);
			}

			if (metadata != null) {
				metadataDefinitions.add(metadata);
				if (!metadataCache.containsKey(clazz)) {
					metadataCache.put(clazz, metadata);
				}
			}
		}

		if (Exception.class.equals(clazz.getClass()) || metadata != null) {
			return metadata;
		} else {
			metadata = getMetadata((Class<?>) clazz.getSuperclass());
			if (!metadataCache.containsKey(clazz)) {
				metadataCache.put(clazz, metadata);
			}
			return metadata;
		}
	}
}
