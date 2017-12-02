package com.estore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EStoreUtils {

	public static String encriptText(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}
}
