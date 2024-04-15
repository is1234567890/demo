package com.example.demo.service;

import java.io.IOException;

import com.example.demo.dto.ErrDto;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Demoアプリケーション用service.<br/>
 * 
 * @author iwamura.sou
 * @since 2024/04/13
 * @version 1.0
 */
public interface DemoService {
	
	/**
	 * リクエスト不正チェック.<br/>
	 * 
	 * @param request
	 * @return エラーDTO
	 */
	public ErrDto chkCsrf(HttpServletRequest request);

	/**
	 * リクエストパラメータチェック.<br/>
	 * 
	 * @param request
	 * @return エラーDTO 
	 * @throws IOException 
	 */
	public ErrDto chkParams(HttpServletRequest request) throws IOException;
}
