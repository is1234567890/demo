package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * エラーリスト.<br/>
 * 
 * @author SI
 * @since 2024/04/13
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class ErrListDto {
	/** エラー項目 */
	@JsonProperty("err_item")
	private String errItem;
	
	/** エラー種別 */
	@JsonProperty("err_type")
	private int errType;
}
