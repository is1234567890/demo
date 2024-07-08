package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.DemoConstants;
import com.example.demo.dto.ErrDto;
import com.example.demo.dto.GetRecordsDto;
import com.example.demo.dto.HttpRequestParamDto;
import com.example.demo.enums.HttpStatusEnum;
import com.example.demo.service.DemoService;
import com.example.demo.util.DemoLogger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * コントローラークラス.<br/>
 * 
 * @author iwamura.sou
 * @since 2024/04/13
 * @version 1.0
 */
@RestController
@RequestMapping("/demo/v1")
public class DemoController {

	// DI系
	/** サービス */
	@Autowired
	DemoService service;
	
	/** ロガー */
	private DemoLogger log = new DemoLogger(DemoController.class);
		
	/**
	 * レコード取得.<br/>
	 * 
	 * @param request
	 * @param response
	 * @throws RuntimeException
	 * @throws IOException 
	 */
	@GetMapping("/get/records")
	@CrossOrigin("http://localhost:8081")
	public void getRecords(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		log.info("info.demo.getrecords.start", new String[] {});
		
		// 事前準備
		response.setCharacterEncoding("UTF-8");
		response.setHeader(DemoConstants.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8081");
		
		// 1.リクエスト不正チェック
		ErrDto errDto = service.chkCsrf(request);
		if(errDto != null) {
			response.setStatus(HttpStatusEnum.CSRF_ERR_STATUS.getHttpStatus());
			response =setErrResponse(response, errDto);
			return;
		}
		
		// 2.バリデーションチェック
		HttpRequestParamDto paramDto = null;
		try {
			paramDto = setDto(request);
		} catch(IOException e) {
			// ErrDtoを受け取ることができないので独自で作る
			HttpStatusEnum sysErrEnum = HttpStatusEnum.SYS_ERR_STATUS;
			errDto = service.setErrDto(sysErrEnum.getErrCd(), sysErrEnum.getErrMsgId());
			
			response.setStatus(sysErrEnum.getHttpStatus());
			response =setErrResponse(response, errDto);
			
			return;
		}
		
		errDto = service.chkParams(paramDto);
		if(errDto != null) {
			response.setStatus(HttpStatusEnum.VALID_ERR_STATUS.getHttpStatus());
			response =setErrResponse(response, errDto);
			return;
		}
		
		// 3.レコード取得
		List<GetRecordsDto> dtoList = null;
		try {
			dtoList = service.getRecords(paramDto.getEmployeeId());
		} catch(DataAccessException e) {
			
			// ErrDtoを受け取ることができないので独自で作る
			HttpStatusEnum sysErrEnum = HttpStatusEnum.SYS_ERR_STATUS;
			errDto = service.setErrDto(sysErrEnum.getErrCd(), sysErrEnum.getErrMsgId());
			
			response.setStatus(sysErrEnum.getHttpStatus());
			response = setErrResponse(response, errDto);
			
			log.error(sysErrEnum.getErrMsgId(), e, DemoConstants.GET_RECORDS_API_NAME);
			
			return;
		}
		
		// 4.レスポンス返却（正常時）
		response = setResponse(dtoList, response);
		response.setStatus(HttpStatusEnum.SUCCESS_STATUS.getHttpStatus());
		log.info("info.demo.getrecords.end", new String[] {});
	}

	/**
	 * 正常終了時のレスポンス設定.<br/>
	 * 
	 * @param dtoList
	 * @param response
	 * @return response
	 */
	private HttpServletResponse setResponse(
			List<GetRecordsDto> dtoList, HttpServletResponse response) {
		
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);
		PrintWriter writer = null;
		try {
			String json = om.writeValueAsString(dtoList);
			writer = response.getWriter();
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(HttpStatusEnum.SYS_ERR_STATUS.getErrMsgId(), new String[] {});
			response.setStatus(HttpStatusEnum.SYS_ERR_STATUS.getErrCd());
			return response;
		} finally {
			writer.close();
		}
		
		return response;
	}

	/**
	 * パラメータDTO取得.<br/>
	 * 
	 * @param request
	 * @return paramDto
	 * @throws IOException
	 */
	private HttpRequestParamDto setDto(HttpServletRequest request) throws IOException {
		// パラメータ取得
		String json = getJsonData(request);
		ObjectMapper mapper = new ObjectMapper();
		HttpRequestParamDto paramDto = mapper.readValue(json, HttpRequestParamDto.class);
		return paramDto;
	}
	
	/**
	 * Jsonデータ取得.<br/>
	 * 
	 * @param request
	 * @return json
	 * @throws IOException
	 */
	private String getJsonData(HttpServletRequest request) throws IOException {

		BufferedReader reader = request.getReader();

		Stream<String> stream = reader.lines();
		Iterator<String> it = stream.iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			sb.append(it.next());
		}

		return sb.toString();
	}

	/**
	 * エラーレスポンスの設定.<br/>
	 * 
	 * @param response
	 * @param errDto
	 * @return response
	 */
	private HttpServletResponse setErrResponse(HttpServletResponse response, ErrDto errDto) {
		
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);
		PrintWriter writer = null;
		try {
			String json = om.writeValueAsString(errDto);
			writer = response.getWriter();
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(HttpStatusEnum.SYS_ERR_STATUS.getErrMsgId(), new String[] {});
			response.setStatus(HttpStatusEnum.SYS_ERR_STATUS.getErrCd());
			return response;
		} finally {
			writer.close();
		}
		
		return response;
	}
}
