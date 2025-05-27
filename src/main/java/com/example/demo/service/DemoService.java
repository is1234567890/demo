package com.example.demo.service;

import java.io.IOException;
import java.util.List;

import com.example.demo.dto.ErrDto;
import com.example.demo.dto.GetRecordsDto;
import com.example.demo.dto.HttpRequestParamDto;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Demoアプリケーション用service.<br/>
 * 
 * @author SI
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
	 * バリデーションチェック.<br/>
	 * 
	 * @param request
	 * @return エラーDTO 
	 * @throws IOException 
	 */
	public ErrDto chkParams(HttpRequestParamDto dto) throws IOException;

	/**
	 * レコード取得.<br/>
	 * 
	 * @param employeeId
	 * @return 取得レコード格納DTO.
	 */
	public List<GetRecordsDto> getRecords(String employeeId);
	
	/**
	 * エラーDTO取得.<br/> 
	 * @param errCd
	 * @param errMsgId
	 * @return エラーDTO
	 */
	public ErrDto setErrDto(int errCd, String errMsgId);

}
