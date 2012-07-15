package com.arunpjohny.web.controller.main.form.validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmpty;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.arunpjohny.core.spring.bind.validation.Validator;
import com.arunpjohny.web.controller.main.form.ContactUsMailForm;

@Component("contactUsMailFormValidator")
public class ContactUsMailFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ContactUsMailForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		rejectIfEmpty(errors, "from", "error.v2.field.current.empty",
				"Password cannot be blank");
	}

}
