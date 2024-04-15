package com.example.demo.enums;

/**
 * Httpステータス用Enum.<br/>
 * 
 * @author iwamura.sou
 * @since 2024/04/13
 * @version 1.0
 */
public enum HttpStatusEnum {
	SUCCESS_STATUS(200, 0, "info.demo.getrecords.success"),
	CSRF_ERR_STATUS(403, 1002, "err.demo.csrf.chk.err"),
	VALID_ERR_STATUS(406, 1003, "err.demo.valid.chk.err"),
	SYS_ERR_STATUS(500, 1005, "err.demo.sys.err");
	
	private final int httpStatus;
	private final int errCd;
	private final String errMsgId;
	
	private HttpStatusEnum(int httpStatus, int errCd, String errMsgId) {
		this.httpStatus = httpStatus;
		this.errCd = errCd;
		this.errMsgId = errMsgId;
	}
	
	public int getHttpStatus() {
		return httpStatus;
	}
	
	public int getErrCd() {
		return errCd;
	}
	
	public String getErrMsgId() {
		return errMsgId;
	}
}
