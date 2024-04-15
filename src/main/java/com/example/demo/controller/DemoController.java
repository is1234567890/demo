package com.example.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.DemoConstants;
import com.example.demo.dto.ErrDto;
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
		
		// 2.パラメータチェック
		errDto = service.chkParams(request);
		if(errDto != null) {
			response.setStatus(HttpStatusEnum.VALID_ERR_STATUS.getHttpStatus());
			response =setErrResponse(response, errDto);
			return;
		}
		
		// 4.レスポンス返却（正常時）
		response.setStatus(HttpStatusEnum.SUCCESS_STATUS.getHttpStatus());
		log.info("info.demo.getrecords.end", new String[] {});
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
