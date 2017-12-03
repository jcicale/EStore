package com.estore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EStoreUtils {

	public static String encriptText(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String CalendarToString(Calendar value, String format) {
		return new SimpleDateFormat(format).format(value.getTime());
	}

	public static Calendar StringToCalendar(String value, String format) {
		Calendar cal = Calendar.getInstance();
		try {
			cal = Calendar.getInstance();
			cal.setTime(new SimpleDateFormat(format).parse(value));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cal;
	}
}
