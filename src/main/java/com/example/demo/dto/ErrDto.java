package com.example.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * エラーDTO.<br/>
 * 注意）lombok.jarをサイトからインストールしないと使えないwwwww
 * 
 * @author SI
 * @since 2024/04/13
 * @version 1.0
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ErrDto {
	/** エラーコード */
	@JsonProperty("err_cd")
	private int errCd;
	/** エラーメッセージ */
	@JsonProperty("err_msg")
	private String errMsg;
	/** エラーリスト */
	@JsonProperty("err_list")
	private List<ErrListDto> errList;
}
