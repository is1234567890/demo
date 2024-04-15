package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.SmartValidator;

import com.example.demo.constants.DemoConstants;
import com.example.demo.dto.ErrDto;
import com.example.demo.dto.GetRecordsDto;
import com.example.demo.enums.HttpStatusEnum;
import com.example.demo.util.DemoLogger;
import com.example.demo.util.DemoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

/**
 * DemoアプリケーションService実装クラス.<br/>
 * 
 * @author iwamura.sou
 * @since 2024/04/13
 * @version 1.0
 */
@Service
public class DemoServiceImpl implements DemoService {

	// DI系
	@Autowired
	private SmartValidator smartValidator;
	
	/** ログ */
	private DemoLogger log = new DemoLogger(DemoService.class);
	
	// 定数
	/** API名 */
	private final String GET_RECORDS_API_NAME = "レコード取得API";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ErrDto chkCsrf(HttpServletRequest request) {
		String xRequestedWith = request
				.getHeader(DemoConstants.X_REQUESTED_WITH);

		if (!DemoConstants.XML_HTTP_REQUEST.equals(xRequestedWith)) {
			HttpStatusEnum csrfErrEnum = HttpStatusEnum.CSRF_ERR_STATUS;
			log.error(csrfErrEnum.getErrMsgId(), new String[] {GET_RECORDS_API_NAME});
			
			ErrDto errDto = setErrDto(csrfErrEnum.getErrCd(), csrfErrEnum.getErrMsgId());
			return errDto;
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ErrDto chkParams(HttpServletRequest request) throws IOException {

		ErrDto errDto = new ErrDto();

		try {
			// パラメータ取得
			String json = getJsonData(request);
			ObjectMapper mapper = new ObjectMapper();
			GetRecordsDto dto = mapper.readValue(json, GetRecordsDto.class);

			// パラメータチェック
			errDto = validDto(dto);

		} catch (Exception e) {
			HttpStatusEnum validErrEnum = HttpStatusEnum.VALID_ERR_STATUS;
			log.error(validErrEnum.getErrMsgId(), e, GET_RECORDS_API_NAME);
			
			errDto = setErrDto(validErrEnum.getErrCd(),
					validErrEnum.getErrMsgId());

			return errDto;
		}

		return null;
	}

	/**
	 * バリデーションチェック用.<br/>
	 * 
	 * @param dto
	 * @return errDto
	 */
	private ErrDto validDto(GetRecordsDto dto) {
		
		ErrDto errDto = new ErrDto();
		
		BindingResult result = new DataBinder(dto).getBindingResult();
		smartValidator.validate(dto, result);
		if(result.hasErrors()) {
			
		}
		
		return null;
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
	 * エラーDTOのセット.<br/>
	 * 
	 * @param errCd
	 * @param errMsgId
	 * @return errDto
	 */
	private ErrDto setErrDto(int errCd, String errMsgId) {

		ErrDto errDto = new ErrDto();
		errDto.setErrCd(errCd);

		String errMsg = DemoUtil.getMsg(errMsgId, GET_RECORDS_API_NAME);
		errDto.setErrMsg(errMsg);

		return errDto;
	}
}
