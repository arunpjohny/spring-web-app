package com.arunpjohny.core.spring.security.authentication.encoding;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PasswordEncoderImpl extends ShaPasswordEncoder {

	private static String SALT = "J0{y&W59ZV>L$5P^3O2Q.d\\~O&S?+EW>ez+|BEBqWo!>9aRn(AlYI<{rBl^)`.;A";

	public PasswordEncoderImpl() {
		super(256);
	}

	@Override
	public String encodePassword(String rawPass, Object salt) {
		if (salt == null) {
			salt = SALT;
		}
		return super.encodePassword(rawPass, salt);
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		if (salt == null) {
			salt = SALT;
		}
		return super.isPasswordValid(encPass, rawPass, salt);
	}

	public static void main(String[] args) {
		System.out.println(RandomStringUtils.randomAscii(64));
		PasswordEncoder encoder = new PasswordEncoderImpl();
		System.out.println(encoder.encodePassword("xxx", null));
	}
}
