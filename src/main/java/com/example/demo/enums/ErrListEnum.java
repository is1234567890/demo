package com.example.demo.enums;

/**
 * バリデーションチェックのエラーリスト用Enum.<br/>
 * 
 * @author SI
 * @since 2024/04/15
 * @version 1.0
 */
public enum ErrListEnum {
	required_err(1),
	size_err(2),
	format_err(3),
	else_err(99);
	
	private final int errType;
	
	private ErrListEnum(int errType) {
		this.errType = errType;
	}
	
	public int getErrType() {
		return errType;
	}
}
