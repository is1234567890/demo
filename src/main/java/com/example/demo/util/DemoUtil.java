package com.example.demo.util;

import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

public class DemoUtil {
	
	public static String getMsg(String msgId, String... msgArgs) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setDefaultEncoding("UTF-8");
	    messageSource.addBasenames("message");
	    return messageSource.getMessage(msgId, msgArgs, Locale.getDefault());
	}
}
