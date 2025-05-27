package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;

import com.example.demo.constants.DemoConstants;
import com.example.demo.dto.ErrDto;
import com.example.demo.dto.ErrListDto;
import com.example.demo.dto.GetRecordsDto;
import com.example.demo.dto.HttpRequestParamDto;
import com.example.demo.entity.GetRecords;
import com.example.demo.enums.HttpStatusEnum;
import com.example.demo.repository.GetRecordsRepository;
import com.example.demo.util.DemoLogger;
import com.example.demo.util.DemoUtil;

import jakarta.servlet.http.HttpServletRequest;

/**
 * DemoアプリケーションService実装クラス.<br/>
 * 
 * @author SI
 * @since 2024/04/13
 * @version 1.0
 */
@Service
public class DemoServiceImpl implements DemoService {

	// DI系
	@Autowired
	private SmartValidator smartValidator;
	@Autowired
	private GetRecordsRepository rep;

	/** ログ */
	private DemoLogger log = new DemoLogger(DemoService.class);
	private Logger validLogger = LoggerFactory.getLogger(DemoService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ErrDto chkCsrf(HttpServletRequest request) {
		
		ErrDto errDto = null;
		
		String xRequestedWith = request
				.getHeader(DemoConstants.X_REQUESTED_WITH);
		
		if (!DemoConstants.XML_HTTP_REQUEST.equals(xRequestedWith)) {
			HttpStatusEnum csrfErrEnum = HttpStatusEnum.CSRF_ERR_STATUS;
			log.error(csrfErrEnum.getErrMsgId(),
					new String[] { DemoConstants.GET_RECORDS_API_NAME });

			errDto = setErrDto(csrfErrEnum.getErrCd(),
					csrfErrEnum.getErrMsgId());
			return errDto;
		}

		return errDto;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ErrDto chkParams(HttpRequestParamDto paramDto) {

		ErrDto errDto = null;

		errDto = validDto(paramDto);

		return errDto;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<GetRecordsDto> getRecords(String employeeId) throws DataAccessException {

		List<GetRecords> entityList = rep.getRecords(employeeId);
		List<GetRecordsDto> dtoList = new ArrayList<GetRecordsDto>();
		
		ModelMapper mapper = new ModelMapper();
		for(GetRecords entity : entityList) {
			dtoList.add(mapper.map(entity, GetRecordsDto.class));
		}
		
		return dtoList;
	}
	
	/**
	 * バリデーションチェック用.<br/>
	 * 
	 * @param dto
	 * @return errDto
	 */
	private ErrDto validDto(HttpRequestParamDto dto) {

		ErrDto errDto = null;
		
		BindingResult result = new DataBinder(dto).getBindingResult();
		smartValidator.validate(dto, result);
		if (result.hasErrors()) {
			HttpStatusEnum validErrEnum = HttpStatusEnum.VALID_ERR_STATUS;
			errDto = setErrDto(validErrEnum.getErrCd(),
					validErrEnum.getErrMsgId());
			
			List<ErrListDto> errList = setErrListDto(result);
			errDto.setErrList(errList);
			
		}

		return errDto;
	}

	/**
	 * エラーリストのセット.<br/>
	 * 
	 * @param result
	 * @return errList
	 */
	private List<ErrListDto> setErrListDto(BindingResult result) {
		
		List<ErrListDto> errList = new ArrayList<ErrListDto>();
		List<FieldError> fieldErrors = result.getFieldErrors();
		for(FieldError fieldError : fieldErrors) {
			validLogger.error(fieldError.getDefaultMessage(), fieldError.getField());
			
			ErrListDto errListDto = new ErrListDto();
			errListDto.setErrItem(fieldError.getField());
			
			int errType = getErrType(fieldError);
			errListDto.setErrType(errType);
			
			errList.add(errListDto);
		}
		
		return errList;
	}

	/**
	 * エラー種別取得.<br/>
	 * 
	 * @param fieldError
	 * @return errType
	 */
	private int getErrType(FieldError fieldError) {
		int errType = 0;
		
		String code = fieldError.getCode();
		switch(code) {
		case "NotBlank":
			errType = 1;
			break;
		case "Size":
			errType = 2;
			break;
		case "Pattern":
			errType = 3;
			break;
		default:
			errType = 99;
			break;
		}
		
		return errType;
	}

	/**
	 * エラーDTOのセット.<br/>
	 * 
	 * @param errCd
	 * @param errMsgId
	 * @return errDto
	 */
	public ErrDto setErrDto(int errCd, String errMsgId) {

		ErrDto errDto = new ErrDto();
		errDto.setErrCd(errCd);

		String errMsg = DemoUtil.getMsg(errMsgId, DemoConstants.GET_RECORDS_API_NAME);
		errDto.setErrMsg(errMsg);

		return errDto;
	}
}
