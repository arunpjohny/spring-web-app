package com.arunpjohny.core.spring.bind.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.Errors;

public class LocalValidatorFactoryBean extends
		org.springframework.validation.beanvalidation.LocalValidatorFactoryBean {

	@Autowired
	private ApplicationContext applicationContext;

	private Map<Class<?>, Validator> validatorMap = new HashMap<Class<?>, Validator>();

	private Collection<Validator> validators = new ArrayList<Validator>();

	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		validators = applicationContext.getBeansOfType(Validator.class)
				.values();
	}

	@Override
	public void validate(Object target, Errors errors,
			Object... validationHints) {
		super.validate(target, errors, validationHints);
		validateLocal(target, errors);
	}

	@Override
	public void validate(Object target, Errors errors) {
		super.validate(target, errors);
		validateLocal(target, errors);
	}

	private void validateLocal(Object target, Errors errors) {
		Validator validator = getLocalValidator(target);
		if (validator != null) {
			validator.validate(target, errors);
		}
	}

	private Validator getLocalValidator(Object target) {
		Validator validator = null;
		if (target != null) {
			Class<? extends Object> targetClass = target.getClass();
			validator = validatorMap.get(targetClass);
			if (validator == null) {
				synchronized (this) {
					validator = validatorMap.get(targetClass);
					if (validator == null) {
						for (Validator v : validators) {
							if (v.supports(targetClass)) {
								validator = v;
								validatorMap.put(targetClass, validator);
								break;
							}
						}
					}
				}
			}
		}
		return validator;
	}
}
