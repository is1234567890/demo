package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
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
