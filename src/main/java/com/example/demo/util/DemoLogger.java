package com.example.demo.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * ロガークラス.<br/>
 * 
 * @author SI
 * @since 2024/04/13
 * @version 1.0
 */
public class DemoLogger {
	
	private ResourceBundleMessageSource messageSource;
	private Logger logger;
	
	/**
	 * コンストラクタ.<br/>
	 */
	public DemoLogger(Class<?> class1) {
		messageSource = new ResourceBundleMessageSource();
		messageSource.setDefaultEncoding("UTF-8");
	    messageSource.addBasenames("message");
		this.logger = LoggerFactory.getLogger(class1);
	}

	/**
	 * infoレベルのログ出力用.<br/>
	 * 
	 * @param msgId
	 * @param msgArg
	 */
	public void info(String msgId, String... msgArg) {
		String msg = getMessage(msgId, msgArg);
		logger.info(msg);
	}
	
	/**
	 * warnレベルのログ出力用.<br/>
	 * 
	 * @param msgId
	 * @param msgArg
	 */
	public void warn(String msgId, String... msgArg) {
		String msg = getMessage(msgId, msgArg);
		logger.warn(msg);
	}
	
	/**
	 * errorレベルのログ出力用.<br/>
	 * 
	 * @param msgId
	 * @param msgArg
	 */
	public void error(String msgId, String... msgArg) {
		String msg = getMessage(msgId, msgArg);
		logger.error(msg);
	}

	/**
	 * errorレベルのログ出力用.<br/>
	 * 
	 * @param msgId
	 * @param e
	 * @param msgArg
	 */
	public void error(String msgId, Exception e, String... msgArg) {
		String msg = getMessage(msgId, msgArg);
		logger.error(msg, e);
	}
	
	/**
	 * メッセージ取得.<br/>
	 * 
	 * @param msgId
	 * @param msgArg
	 * @return msg
	 */
	private String getMessage(String msgId, String... msgArg) {
		String msg;
		if(msgArg.length > 0) {
			msg = messageSource.getMessage(msgId, msgArg, Locale.getDefault());
		} else {
			msg = messageSource.getMessage(msgId, null,Locale.getDefault());
		}
		return msg;
	}
}
